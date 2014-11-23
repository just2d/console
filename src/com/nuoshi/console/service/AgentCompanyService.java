package com.nuoshi.console.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.nuoshi.console.common.ChangePINYIN;
import com.nuoshi.console.dao.AgentCompanyDao;
import com.nuoshi.console.dao.RentDao;
import com.nuoshi.console.dao.ResaleDao;
import com.nuoshi.console.domain.agent.AgentCompany;

@Service
public class AgentCompanyService {
	@Resource
	AgentCompanyDao agentCompanyDao;
	@Resource
	AgentStoreService agentStoreServcie;
	@Resource
	AgentMasterService agentMasterService;
	@Resource
	ResaleDao resaleDao;
	@Resource
	RentDao rentDao;
	
	
	public int addAgentCompany(AgentCompany agentCompany) {
		agentCompany.setNamePinyin(ChangePINYIN.getPingYin(agentCompany.getName()));
		int companyId = agentCompanyDao.addAgentCompany(agentCompany);
		this.createCompanyAccount(agentCompany,true);
		return companyId;
	}

	public void deleteAgentCompany(String id) {
		agentCompanyDao.deleteAgentCompany(id);
		agentStoreServcie.deleteAgentStoreByAgentCompanyId(id);
	}

	public void updateAgentCompany(AgentCompany agentCompany) {
		if((agentCompany.getAuditId()!=null && agentCompany.getAuditId()>0) || (agentCompany.getAuditTime() !=null && agentCompany.getAuditTime().getTime()>0)){
			this.createCompanyAccount(agentCompany,false);
		}
		AgentCompany companyDB = agentCompanyDao.getAgentCompanyById(agentCompany.getId());
		if(StringUtils.isNotBlank(companyDB.getName()) && StringUtils.isNotBlank(agentCompany.getName()) && !companyDB.getName().trim().equals(agentCompany.getName().trim())){
				resaleDao.updateCompanyName(agentCompany.getId(),agentCompany.getId(),agentCompany.getName());
				rentDao.updateCompanyName(agentCompany.getId(),agentCompany.getId(),agentCompany.getName());
				agentMasterService.updateAgentCompanyByCompanyId(agentCompany.getId(), agentCompany.getId(), agentCompany.getName());
		}
		agentCompany.setNamePinyin(ChangePINYIN.getPingYin(agentCompany.getName()));
		agentCompanyDao.updateAgentCompany(agentCompany);
	}

	private void createCompanyAccount(AgentCompany agentCompany,boolean isAdd) {
		//判断此ID的账号是否已存在
		boolean flag = false;
		if(!isAdd){
			  flag = agentCompanyDao.isCompanyAccountExist(agentCompany);
		}
		
		if(!flag){
				int cityId = 0;
				if(agentCompany.getCityId() != null && agentCompany.getCityId() > 0){
					cityId = agentCompany.getCityId();
				}else{
					cityId = agentCompanyDao.getAgentCompanyById(agentCompany.getId()).getCityId();
				}
				if(cityId > 0){
					agentCompany.setUserName(LocaleService.getName(cityId)+agentCompany.getName());
					agentCompanyDao.addCompanyAccount(agentCompany);
				}
		}else{
			agentCompanyDao.updateTfAsAccountName(LocaleService.getName(agentCompany.getCityId())+agentCompany.getName(),agentCompany.getId());
		}
	}

	public List<AgentCompany> searchAgentCompany() {
		return agentCompanyDao.searchAgentCompanyBySql();
	}

	public AgentCompany getAgentCompanyById(Integer agentCompanyId) {
		return agentCompanyDao.getAgentCompanyById(agentCompanyId);
	}
	
	/**
	 * 重新更新、统计公司包含的门店数量
	 * @param companyIdList
	 * @author wangjh
	 * Jan 11, 2012 1:30:25 PM
	 */
	public void refreshStoreCount(List<Integer> companyIdList){
		agentCompanyDao.refreshStoreCount(companyIdList);
	}

	public void mergeAgentCompany(Integer oldCompanyId, Integer newCompanyId, String companyName) {
		resaleDao.updateCompanyName(oldCompanyId,newCompanyId,companyName);
		rentDao.updateCompanyName(oldCompanyId,newCompanyId,companyName);
		agentMasterService.updateAgentCompanyByCompanyId(oldCompanyId,newCompanyId,companyName);
		agentStoreServcie.updateAgentStoreForCompany(oldCompanyId, newCompanyId);
		agentCompanyDao.updateTfComAccount(newCompanyId,companyName,oldCompanyId);
		agentCompanyDao.deleteTfComAccount(oldCompanyId);
		deleteAgentCompany(oldCompanyId.toString());
	}

}
