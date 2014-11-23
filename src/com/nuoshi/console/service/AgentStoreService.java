package com.nuoshi.console.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.nuoshi.console.common.ChangePINYIN;
import com.nuoshi.console.dao.AgentStoreDao;
import com.nuoshi.console.dao.RentDao;
import com.nuoshi.console.dao.ResaleDao;
import com.nuoshi.console.domain.agent.AgentCompany;
import com.nuoshi.console.domain.agent.AgentStore;
import com.nuoshi.console.domain.agent.TfAsAccount;

@Service
public class AgentStoreService {
	@Resource
	AgentStoreDao agentStoreDao;
	@Resource
	AgentCompanyService agentCompanyService;
	@Resource
	AgentMasterService agentMasterService;
	@Resource
	ResaleDao resaleDao;
	@Resource
	RentDao rentDao;
	/**
	 * 门店数量加一
	 */
	private final Integer ADD_ONE=1;
	/**
	 * 门店数量减一
	 */
	private final Integer CUT_ONE=2;
	
	public int addAgentStore(AgentStore agentStore) {
		agentStore.setNamePinyin(ChangePINYIN.getPingYin(agentStore.getName()));
		agentStoreDao.addAgentStore(agentStore);
		this.createStoreAccount(agentStore,true);
		try{
			this.saveAgentStoreCount(agentStore, this.ADD_ONE);
		}catch(RuntimeException e){
			throw new RuntimeException(e.getMessage());
		}
		return agentStore.getId();
	}

	public void deleteAgentStore(String id) {
		//删除门店时更新公司下门店数量
		if(StringUtils.isBlank(id)){
			throw new RuntimeException("id类型错误!");
		}
		try{
			AgentStore agentStore=agentStoreDao.searchAgentStoreById(id);
			agentStoreDao.deleteAgentStore(id);
			if(agentStore!=null){
			this.saveAgentStoreCount(agentStore, this.CUT_ONE);
			}
		}catch(RuntimeException e){
			throw new RuntimeException(e.getMessage());
		}
	}

	public void updateAgentStore(AgentStore agentStore) {
		if((agentStore.getAuditId() != null && agentStore.getAuditId() > 0) || (agentStore.getAuditTime() !=null && agentStore.getAuditTime().getTime() > 0)){
			this.createStoreAccount(agentStore,false);
		}
		AgentStore agentStoreDB = agentStoreDao.searchAgentStoreById(agentStore.getId()+"");
		if(StringUtils.isNotBlank(agentStoreDB.getName()) && StringUtils.isNotBlank(agentStore.getName()) && !agentStoreDB.getName().trim().equals(agentStore.getName().trim())){
			resaleDao.updateStoreNameByStoreId(agentStore.getId(),agentStore.getName());
			rentDao.updateStoreNameByStoreId(agentStore.getId(),agentStore.getName());
			agentMasterService.updateAgentStoreByStoreId(agentStore.getId(), agentStore.getId(), agentStore.getName());
		}
		agentStore.setNamePinyin(ChangePINYIN.getPingYin(agentStore.getName()));
		agentStoreDao.updateAgentStore(agentStore);
	}
	private void createStoreAccount(AgentStore agentStore,boolean isAdd) {
		boolean flag =false;
		//判断此ID的账号是否已存在
		if(!isAdd){
			 flag = agentStoreDao.isStoreAccountExist(agentStore);
		}
		if(!flag){
				int cityId = 0;
				if(agentStore.getCityId() != null && agentStore.getCityId() > 0){
					cityId = agentStore.getCityId();
				}else{
					cityId = agentStoreDao.searchAgentStoreById(agentStore.getId()+"").getCityId();
				}
				if(cityId > 0){
					 TfAsAccount tf = agentStoreDao.getStoreAccountByName(agentStore);
					 agentStore.setUserName(agentStore.getName());
					if(tf!=null){
						agentStore.setUserName(LocaleService.getName(cityId)+agentStore.getName());
					}
					agentStoreDao.addStoreAccount(agentStore);
				}
		}else{
			 TfAsAccount tf = agentStoreDao.getStoreAccountByName(agentStore);
				if(tf==null){//没有账号可以更新账号名字
						agentStoreDao.updateTfAsAccountName(agentStore.getName(),agentStore.getName(),agentStore.getId());
				}else{
					agentStoreDao.updateTfAsAccountName(LocaleService.getName(agentStore.getCityId())+agentStore.getName(),agentStore.getName(),agentStore.getId());
				}
		}
	}

	public void updateAgentStoreForCompany(Integer oleCompanyId,Integer newCompanyId) {
		agentStoreDao.updateAgentStoreForCompany(oleCompanyId,newCompanyId);
		List<Integer> companyIdList=new ArrayList<Integer>();
		companyIdList.add(newCompanyId);
		//刷新公司门店数量
		agentCompanyService.refreshStoreCount(companyIdList);
	}

	public AgentStore searchAgentStoreById(String id) {
		return agentStoreDao.searchAgentStoreById(id);
	}
	
	private AgentCompany agentStoreCount(AgentCompany agentCompany,Integer operateType){
		Integer agentStoreInt = agentCompany.getStoreCnt();
		//操作数量
		if(operateType==this.ADD_ONE){
			
			if(agentStoreInt<0){
				agentStoreInt=0;
			}
			if(agentStoreInt>=0){
				++agentStoreInt;
			}
			
		}
		if(operateType==this.CUT_ONE){
			if(agentStoreInt<=0){
				agentStoreInt=0;
			}
			if(agentStoreInt>0){
				--agentStoreInt;
			}
		}
		agentCompany.setStoreCnt(agentStoreInt);
		return agentCompany;
		
	}
	//保存公司门店数量
	private void saveAgentStoreCount(AgentStore agentStore ,Integer operatorType){
		try {
			Integer agentCompanyId = agentStore.getCompanyId();
			AgentCompany agentCompany;
			if (agentCompanyId != null) {
				agentCompany = agentCompanyService.getAgentCompanyById(agentCompanyId);
			} else {
				throw new RuntimeException("门店不属于任何公司!");
			}
			if (agentCompany != null) {
				agentCompany = this.agentStoreCount(agentCompany, operatorType);
				agentCompanyService.updateAgentCompany(agentCompany);
			} else {
				throw new RuntimeException("公司ID对应的公司不存在!");
			}
		} catch (RuntimeException e) {
			throw new RuntimeException("更新公司门店数量出错!");
		}
	}
	
	/**
	 * 通过公司id删除门店
	 * @param id
	 */
	public void deleteAgentStoreByAgentCompanyId(String id) {
		agentStoreDao.deleteAgentStoreByAgentCompanyId(id);
	}

	public void mergeAgentStore(Integer oldStoreId, Integer newStoreId, String storeName) {
		agentMasterService.updateAgentStoreByStoreId(oldStoreId,newStoreId,storeName);
		deleteAgentStore(oldStoreId.toString());
		resaleDao.updateStore(oldStoreId,newStoreId,storeName);
		rentDao.updateStore(oldStoreId,newStoreId,storeName);
		agentStoreDao.deleteTfAsAccount(oldStoreId);
	}

}
