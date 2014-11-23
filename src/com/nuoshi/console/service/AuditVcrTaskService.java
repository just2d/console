package com.nuoshi.console.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.nuoshi.console.common.Globals;
import com.nuoshi.console.dao.AgentMasterDao;
import com.nuoshi.console.domain.agent.AgentMaster;
import com.nuoshi.console.domain.audit.AuditVcrTask;
import com.nuoshi.console.domain.base.House;
import com.nuoshi.console.domain.rent.Rent;
import com.nuoshi.console.domain.resale.Resale;
import com.nuoshi.console.persistence.read.admin.audit.AuditVcrTaskReadMapper;
import com.nuoshi.console.persistence.read.taofang.agent.TFUserReadMapper;
import com.nuoshi.console.persistence.read.taofang.rent.RentReadMapper;
import com.nuoshi.console.persistence.read.taofang.resale.ResaleReadMapper;
import com.nuoshi.console.persistence.write.admin.audit.AuditVcrTaskWriteMapper;
import com.nuoshi.console.persistence.write.taofang.rent.RentWriteMapper;
import com.nuoshi.console.persistence.write.taofang.resale.ResaleWriteMapper;

@Service
public class AuditVcrTaskService {

	@Resource
	private AuditVcrTaskReadMapper auditVcrTaskReadMapper;
	
	@Resource
	private AuditVcrTaskWriteMapper auditVcrTaskWriteMapper;
	
	@Resource
	private ResaleReadMapper resaleReadMapper;
	
	@Resource
	private RentReadMapper rentReadMapper;
	
	@Resource
	private ResaleWriteMapper resaleWriteMapper;
	
	@Resource
	private RentWriteMapper rentWriteMapper;
	
	@Resource
	private MessageService messageService;
	
	@Resource
	private TFUserReadMapper tfUserReadMapper;
	
	@Resource
	private AgentMasterDao agentMasterDao;
	
	public List<AuditVcrTask> getAuditTask(int userId, String userName, int houseType, int cityId,Integer houseId) {
		List<Integer> houseIds = new ArrayList<Integer>();
		if(houseId !=null && houseId > 0){
			houseIds.add(houseId);
			List<AuditVcrTask> list = null;
			list = auditVcrTaskReadMapper.getAuditVcrTaskByHouseId(houseId, houseType);
			
			if(CollectionUtils.isEmpty(list)){
				House house =null;
				if(houseType == House.RESALE_TYPE) {
					house = resaleReadMapper.getAuditVcrTaskByHouseId(houseId);
				}else if(houseType == House.RENT_TYPE){
					house = rentReadMapper.getAuditVcrTaskByHouseId(houseId);
				}
				if(house != null){
					auditVcrTaskWriteMapper.addAuditVcrTask(userId, userName, house.getId(), houseType, house.getCityid(), house.getAuthorid(), house.getComplainNum(), house.getVcrSubmitTime());
					list = new ArrayList<AuditVcrTask>();
					AuditVcrTask auditVcrTask = new AuditVcrTask();
					auditVcrTask.setAgentId(house.getAuthorid());
					auditVcrTask.setHouseId(house.getId());
					auditVcrTask.setCityId(house.getCityid());
					auditVcrTask.setVcrTime(house.getVcrSubmitTime());
					auditVcrTask.setComplainCount(house.getComplainNum());
					int auditResult = -1;
					if(house.getVcrCheckStatus() == null){
						auditResult= 2;
					}else{
						switch (house.getVcrCheckStatus()) {
						case 0:
							auditResult = -1;
							break;
						case 1:
							auditResult = 0;
							break;
						case 2:
							auditResult = 1;
							break;
						}
					}
					auditVcrTask.setAuditResult(auditResult);
					list.add(auditVcrTask);
				}
			}
			return list;
		}
	
		int taskCount = auditVcrTaskReadMapper.getAuditVcrTaskCount(userId, houseType, cityId);
		if(taskCount < Globals.AUDIT_HOUSE_COUNT) {
			List<House> houses = null;
			if(houseType == House.RESALE_TYPE) {
				houses = resaleReadMapper.getAuditVcrTask(cityId, Globals.AUDIT_HOUSE_COUNT - taskCount);
				if(houses != null && houses.size() > 0) {
					for(House house : houses) {
						houseIds.add(house.getId());
						auditVcrTaskWriteMapper.addAuditVcrTask(userId, userName, house.getId(), houseType, house.getCityid(), house.getAuthorid(), house.getComplainNum(), house.getVcrSubmitTime());
					}
					resaleWriteMapper.changeVcrAuditSign(houseIds, 1);
				}
			} else {
				houses = rentReadMapper.getAuditVcrTask(cityId, Globals.AUDIT_HOUSE_COUNT - taskCount);
				if(houses != null && houses.size() > 0) {
					for(House house : houses) {
						houseIds.add(house.getId());
						auditVcrTaskWriteMapper.addAuditVcrTask(userId, userName, house.getId(), houseType, house.getCityid(), house.getAuthorid(), house.getComplainNum(), house.getVcrSubmitTime());
					}
					rentWriteMapper.changeVcrAuditSign(houseIds, 1);
				}
			}
		}
		return auditVcrTaskReadMapper.getAuditVcrTaskByUser(userId, houseType, cityId);
	}
	
	public void auditVcrResult(String[] results, int type) {
		for(String result : results) {
			String[] array = result.split("_");
			auditVcrTaskWriteMapper.changeAuditResult(Integer.parseInt(array[0]), type, Integer.parseInt(array[1]), array.length > 2 ? array[2] : null);
			if(House.RESALE_TYPE == type) {
				Resale resale = resaleReadMapper.selectResaleById(Integer.parseInt(array[0]));
//				if(!resale.isVcr()) {
//					continue;
//				}
				if(AuditVcrTask.AUDIT_OK == Integer.parseInt(array[1])) {
					resaleWriteMapper.approveVcr(Integer.parseInt(array[0]));
				} else {
					AgentMaster author = tfUserReadMapper.selectAgentById(resale.getAuthorid());
					String viewUrl = "http://" + LocaleService.getCode(resale.getCityid()) + ".taofang.com/ershoufang/" + resale.getId() + "-" + 0 + ".html";
					int affect = resaleWriteMapper.rejectVcr(Integer.parseInt(array[0]));
					if(affect > 0) {
						agentMasterDao.incrementLabelNum(resale.getAuthorid(), 1, 1);
					}
					messageService.sendVcrDelMessage(author, Integer.parseInt(array[0]), viewUrl);
				}
				
			} else {
				Rent rent = rentReadMapper.selectRentById(Integer.parseInt(array[0]));
//				if(!rent.isVcr()) {
//					continue;
//				}
				if(AuditVcrTask.AUDIT_OK == Integer.parseInt(array[1])) {
					rentWriteMapper.approveVcr(Integer.parseInt(array[0]));
				} else {
					AgentMaster author = tfUserReadMapper.selectAgentById(rent.getAuthorid());
					String viewUrl = "http://" + LocaleService.getCode(rent.getCityid()) + ".taofang.com/zufang/" + rent.getId() + "-" + 0 + ".html";
					int affect = rentWriteMapper.rejectVcr(Integer.parseInt(array[0]));
					if(affect > 0) {
						agentMasterDao.incrementLabelNum(rent.getAuthorid(), 1, 1);
					}
					messageService.sendVcrDelMessage(author, Integer.parseInt(array[0]), viewUrl);
				}
			}
		}
	}
	
	/**
	 * 获取视频审核历史
	 * @param userId
	 * @param cityId
	 * @param houseType
	 * @param result
	 * @return
	 */
	public List<AuditVcrTask> getAuditVcrHistory(int userId, int cityId, int houseType, int result) {
		return auditVcrTaskReadMapper.getAuditVcrHistoryByPage(userId, houseType, cityId, result);
	}
 }
