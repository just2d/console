package com.nuoshi.console.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.nuoshi.console.common.Globals;
import com.nuoshi.console.domain.agent.AgentDeleteLog;
import com.nuoshi.console.domain.agent.AgentMaster;
import com.nuoshi.console.domain.agent.Broker;
import com.nuoshi.console.domain.agent.PhoneVerifyLog;
import com.nuoshi.console.domain.agent.RejectReason;
import com.nuoshi.console.domain.agent.Smslog;
import com.nuoshi.console.domain.auditHistory.AuditHistory;
import com.nuoshi.console.domain.pckage.AgentActionLog;
import com.nuoshi.console.domain.pckage.AgentPackage;
import com.nuoshi.console.domain.pckage.AgentPackageLog;
import com.nuoshi.console.domain.pckage.AgentPurchase;
import com.nuoshi.console.persistence.read.admin.agent.PhoneVerifyLogReadMapper;
import com.nuoshi.console.persistence.read.admin.agent.RejectReasonReadMapper;
import com.nuoshi.console.persistence.read.taofang.agent.AgentMasterReadMapper;
import com.nuoshi.console.persistence.read.taofang.pckage.AgentPackageReadMapper;
import com.nuoshi.console.persistence.write.admin.agent.AgentDeleteLogWriteMapper;
import com.nuoshi.console.persistence.write.admin.agent.PhoneVerifyLogWriteMapper;
import com.nuoshi.console.persistence.write.admin.agent.RejectReasonWriteMapper;
import com.nuoshi.console.persistence.write.admin.audit.AuditTaskWriteMapper;
import com.nuoshi.console.persistence.write.taofang.agent.AgentMasterWriteMapper;
import com.nuoshi.console.persistence.write.taofang.agent.TransferPhoneWriteMapper;
import com.nuoshi.console.service.SmsService;

@Repository
public class AgentMasterDao {
	
	protected Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private TransferPhoneWriteMapper transferPhoneWriteMapper;
	
	@Resource
	private RejectReasonReadMapper rejectReasonReadMapper;
	
	@Resource
	private PhoneVerifyLogWriteMapper phoneVerifyLogWriteMapper;
	
	@Resource
	private PhoneVerifyLogReadMapper phoneVerifyLogReadMapper;
	
	@Resource
	private RejectReasonWriteMapper rejectReasonWriteMapper;
	
	@Resource
	private AgentMasterReadMapper agentMasterReadMapper;
	
	@Resource
	private AgentMasterWriteMapper agentMasterWriteMapper;
	
	@Resource
	private AgentActionLogDao agentActionLogDao;
	
	@Resource
	private AgentPackageLogDao agentPackageLogDao;
	
	@Resource
	private AgentPurchaseDao agentPurchaseDao;
	
	@Resource
	private AgentPackageReadMapper agentPackageReadMapper;
	
	@Resource
	private AuditTaskWriteMapper auditTaskWriteMapper;
	
	@Resource
	private AgentDeleteLogWriteMapper agentDeleteLogWriteMapper;
	
	@Resource
	private SmsService smsService;
	
	public AgentMaster selectAgentMasterById(int id) {
		return agentMasterReadMapper.selectAgentMasterById(id);
	}
	public List<AgentMaster> selectAgentMasterByIds(List<Integer> idList) {
		return agentMasterReadMapper.selectAgentMasterForAudit(idList);
	}
	
	public List<AgentMaster> getAllAgentMasterByPage(){
		return agentMasterReadMapper.getAllAgentMasterByPage();
	}
	
	public List<AgentMaster> getAllAgentMasterByPage(List<String> conditions) {
		return agentMasterReadMapper.getAllAgentMaster2ByPage(conditions);
	}
	
	public List<AgentMaster> getAgentMasterByConditions(List<String> conditions) {
		return agentMasterReadMapper.getAgentMasterByConditions(conditions);
	}
	
	public int updateAgentMasterVerify(AgentMaster agentMaster, String oldStatus) {
		try {
			int oldPackageId = agentMaster.getPackageId();
			if (agentMaster.getVerifyStatus() == 0&&agentMaster.isAgentVerified()) {// 注册用户还没有认证过
				
				// 以前的套餐都设置为过期
				List<AgentPurchase> oldPurchase = agentPurchaseDao.getActiveAndUseStatusPackage(agentMaster.getAgentId());
				if (oldPurchase != null && oldPurchase.size() > 0) {
					for (AgentPurchase para : oldPurchase) {
						agentPurchaseDao.updateActiveStatus(para.getId(), AgentPurchase.PACKAGE_ACTIVE_STATUS_EXPIRE);
					}
				}
				addFreePackage(agentMaster,true);//添加一个免费的套餐
				
				agentActionLogDao.addAgentActionLog(agentMaster.getId(), -1, agentMaster.getPackageId(), AgentActionLog.ACTION_TYPE_AGENT_VERIFY, -1, oldStatus, agentMaster.getStatus());
				log.info("修改经纪人 " + agentMaster.getId() + ",status的变化：" + oldStatus + "-" + agentMaster.getStatus() + ",套餐数目，packageId:" + oldPackageId + "-" + agentMaster.getPackageId() + " ,house_num:" + agentMaster.getHouseNum() + ",label_num:" + agentMaster.getLabelNum());
				//标志为认证通过
				agentMaster.setVerifyStatus(AgentMaster.VERIFY_STATUS_PASS);
				agentMaster.setVerifyPassDate(new Timestamp(System.currentTimeMillis()));
				agentMaster.setBanFlag("N");
			}
			
			int ret = agentMasterWriteMapper.updateAgentMasterVerify(agentMaster);
			
			return ret;
		} catch (Exception e) {
			System.out.println(e);
		}
		return -1;
	}
	//verify　是不是认证时调用
	public void addFreePackage(AgentMaster agentMaster,boolean verify){
		int oldPackageId = agentMaster.getPackageId();
		// 注册用户还没有认证过
		//获取城市免费套餐
		 AgentPackage freePackage = agentPackageReadMapper.getDefaultPackageByCityId(agentMaster.getCityId());
		 
		//城市没有免费套餐　取默认的套餐
		if(freePackage==null){
			freePackage = agentPackageReadMapper.getFullFreePackage();
		}
		
		List<AgentPurchase> availableAndUseStatusPurchase = null;
		
		if(!verify){//认证时会把所有可用的套餐设为过期(处理老数据），认证时肯定要添加一个免费套餐
			//return ; 
			availableAndUseStatusPurchase = agentPurchaseDao.getAvailableAndUseStatusPackage(agentMaster.getAgentId());
		}
		if(availableAndUseStatusPurchase==null||availableAndUseStatusPurchase.size()==0){
				//int totalHouse = agentMasterReadMapper.selectUsedHouseNum(agentMaster.getId());
				//int totalLabel = agentMasterReadMapper.selectUsedLabelNum(agentMaster.getId());
				agentMaster.setHouseNum(freePackage.getHouseAmount());
				agentMaster.setLabelNum(freePackage.getLabelAmount());
				agentMaster.setPackageId(freePackage.getId());
				agentMaster.setAvailableHouseNum(agentMaster.getHouseNum());
				agentMaster.setAvailableLabelNum(agentMaster.getLabelNum());
		
			
				Calendar ca = Calendar.getInstance();
				Timestamp now = new Timestamp(ca.getTime().getTime());
				// 计算套餐过期时间
				ca.add(Calendar.MONTH, freePackage.getEffectiveMonth());
				ca.add(Calendar.DAY_OF_MONTH, freePackage.getEffectiveDays());
				Timestamp expiredDate = new Timestamp(ca.getTime().getTime());
				AgentPurchase newPurchase = new AgentPurchase();
				newPurchase.setAgentId(agentMaster.getAgentId());
				newPurchase.setPackageId(agentMaster.getPackageId());
				newPurchase.setPurchaseDate(now);
				newPurchase.setPurchasePrice(new Double(freePackage.getPrice()));
				newPurchase.setActiveDate(now);
				agentMaster.setPackageActiveDate(now);
				
				//北京，深圳，长沙　免费套餐设置过期日期
				if(freePackage.getEffectiveMonth()>0||freePackage.getEffectiveDays()>0){
					newPurchase.setExpiredDate(expiredDate);
					agentMaster.setPackageExpiredDate(expiredDate);
				}
				newPurchase.setActiveStatus(AgentPurchase.PACKAGE_ACTIVE_STATUS_USE);
				newPurchase.setHouseAmount(agentMaster.getHouseNum());
				newPurchase.setLabelAmount(agentMaster.getLabelNum());
				agentPurchaseDao.addAgentPurchase(newPurchase);
				agentPackageLogDao.addAgentPackageLog(agentMaster.getAgentId(), oldPackageId, AgentPackageLog.PACKAGE_ACTIVE_STATUS_EXPIRE);
				agentPackageLogDao.addAgentPackageLog(agentMaster.getAgentId(), agentMaster.getPackageId(), AgentPackageLog.PACKAGE_ACTIVE_STATUS_USE);
		}

	
	}
	
	public int add400UserId(int agentId, int user400Id, String number) {
		assert(user400Id > 0 && agentId > 0); 
		try {
			return agentMasterWriteMapper.add400UserId(agentId, user400Id, number);
		} catch (Exception e) {
			
		}
		return -1;
	}
	
	public List<RejectReason> getAllRejectReason2ByPage() {
		return rejectReasonReadMapper.getAllRejectReason2ByPage();
	}
	
	public List<RejectReason> getAllRejectReasonByPage(int type) {
		return rejectReasonReadMapper.getAllRejectReasonByPage(type);
	}
	
	public int addRejectReason(RejectReason rejectReason) {
		return rejectReasonWriteMapper.addRejectReason(rejectReason);
	}
	
	public int updateRejectReason(RejectReason reason) {
		return rejectReasonWriteMapper.updateRejectReason(reason);
	}
	
	public int delRejectReason(int id) {
		return rejectReasonWriteMapper.delRejectReason(id);
	}
	
	public List<RejectReason> getAllRejectReason() {
		return rejectReasonReadMapper.getAllRejectReason();
	}
	
	public String selLocalDir(int id) {
		return agentMasterReadMapper.selLocalDir(id);
	}
	
	public int resetPhoneStatus(String callNumber) {
		return transferPhoneWriteMapper.resetPhoneStatus(callNumber);
	}
	
	public Broker selectBrokerById(int brokerId) {
		return agentMasterReadMapper.selectBrokerById(brokerId);
	}
	
	public int selectBrokerIdByBrandAddr(List<String> conditions) {
		return agentMasterReadMapper.selectBrokerIdByBrandAddr(conditions);
	}
	
	public int updatePhoneStatus(String phone, int agentId, int user400Id) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			int currentTime = Integer.parseInt(sdf.format(new Date()));
			return transferPhoneWriteMapper.updatePhoneStatus(phone, currentTime, agentId,user400Id);
		} catch (Exception e) {
			return -1;
		}
	}
	
	public int insertSmslog(Smslog smslog) {
		return agentMasterWriteMapper.insertSmslog(smslog);
	}
	
	public int incrementHouseNum(int agentId) {
		return agentMasterWriteMapper.incrementHouseNum(agentId);
	}
	
	public int incrementLabelNum(int agentId,int labelNum, int vcrNum) {
		return agentMasterWriteMapper.incrementLabelNum(agentId,labelNum, vcrNum);
	}
	
/*	public int updateRateHeadTiny(int agentId, String url) {
		return agentMasterWriteMapper.updateRateHeadTiny(agentId, url);
	}*/

	public List<AgentMaster> getAgentByIds(List<Integer> integers) {
		return agentMasterReadMapper.getAgentByIds(integers);
	}

	public String getAgentHeadFromPhoto(int imgHeadId) {
		return agentMasterReadMapper.getAgentHeadFromPhoto(imgHeadId);
	}

	public List<RejectReason> getAllRejectReasonByType(int i) {
		return rejectReasonReadMapper.getAllRejectReasonByType(i);
	}

	public List<AgentMaster> getVerifyMobileAgentByPage(HashMap<String, Object> params) {
		return agentMasterReadMapper.getVerifyMobileAgentByPage(params);
	}

	public AgentPackage selAgentPackageById(int i) {
		return agentMasterReadMapper.selAgentPackageById(i);
	}

	public void updateForMobilePass(List<Integer> integers) {
		agentMasterWriteMapper.updateForMobilePass(integers);
	}

	/**
	 * 是0则被处理了
	 * @param agentId
	 * @return
	 * @date 2012-1-7下午4:45:36
	 * @author dongyj
	 */
	public boolean ifVerifiedPhone(Integer agentId) {
		return agentMasterReadMapper.ifVerifiedPhone(agentId) == 0;
	}

	public List<Integer> getAgentIdForAudit(String auditSign, Integer num,
			Integer cityId,List<Integer> auditStatus) {
		return agentMasterReadMapper.getAgentIdForAudit(auditSign,num,cityId,auditStatus);
	}

	public void signAgent(String auditSign, List<Integer> idList) {
		agentMasterWriteMapper.signAgent(auditSign,idList);
	}

	public List<AgentMaster> getAgentInfoCity(List<Integer> idList) {
		return agentMasterReadMapper.getAgentInfoCity(idList);
	}

	public List<Integer> getInconformityAgentIds(List<Integer> agentIdList,List<Integer> auditStatus) {
		return agentMasterReadMapper.getInconformityAgentIds(agentIdList,auditStatus);
	}

	public void deleteAuditTask(List<Integer> agentIdList) {
		auditTaskWriteMapper.deleteAgentAuditTask(agentIdList);
	}

	public void updateAuditStatusByIdList(List<Integer> agentIdList,Integer status,
			Integer  auditSign) {
		agentMasterWriteMapper.updateAuditStatusByIdList(agentIdList,status,auditSign);
	}

	public String getMobileById(Integer agentId) {
		return agentMasterReadMapper.getMobileById(agentId);
	}
	public List<AuditHistory> getHistoryInfo(List<Integer> idList) {
		return agentMasterReadMapper.getHistoryInfo(idList,Globals.AGENT_TYPE);
	}
	public void insertPhoneVerifyLog(PhoneVerifyLog phoneVerifyLog) {
		phoneVerifyLogWriteMapper.insertPhoneVerifyLog(phoneVerifyLog);
	}
	public List<PhoneVerifyLog> getVerifyPhoneHistoryListByPage(HashMap<String, Object> params) {
		return phoneVerifyLogReadMapper.getVerifyPhoneHistoryListByPage(params);
	}
	public Integer getUnclaimedAwaitAuditCount(List<Integer> agentAuditStatus) {
		return agentMasterReadMapper.getUnclaimedAwaitAuditCount(agentAuditStatus);
	}
	public int  updateAgentForStartStop(Integer agentId,String type,int tipType) {
		return agentMasterWriteMapper.updateAgentForStartStop(agentId,type,tipType);
	}
	public int getPhotoCountForHeadImg(int imgHeadId, int agentId) {
		return agentMasterReadMapper.getPhotoCountForHeadImg(imgHeadId,agentId);
	}
	
	public int addAgentDeleteLog(AgentDeleteLog agentDeleteLog) {
		return agentDeleteLogWriteMapper.addAgentDeleteLog(agentDeleteLog);
	}
	public AgentMaster selectAgentMasterByMobile(String agentPhone) {
		return agentMasterReadMapper.selectAgentMasterByMobile(agentPhone);
	}
	public void updateAgentStoreByStoreId(Integer oldStoreId, Integer newStoreId, String storeName) {
		agentMasterWriteMapper.updateAgentStoreByStoreId(oldStoreId,newStoreId,storeName);
		
	}
	public void updateAgentCompanyByCompanyId(Integer oldCompanyId, Integer newCompanyId, String companyName) {
		agentMasterWriteMapper.updateAgentCompanyByCompanyId(oldCompanyId,newCompanyId,companyName);
		
	}
	public List<Integer> selectAgentsByCompanyId(Integer oldCompanyId) {
		return agentMasterReadMapper.selectAgentsByCompanyId(oldCompanyId);
	}
	
}
