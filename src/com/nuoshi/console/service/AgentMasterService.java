package com.nuoshi.console.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.taofang.biz.local.AgentPhotoUrlUtil;
import com.nuoshi.console.common.Globals;
import com.nuoshi.console.common.Resources;
import com.nuoshi.console.common.phone.PhoneConstants;
import com.nuoshi.console.common.phone.PhoneService;
import com.nuoshi.console.common.util.ApplicationUtil;
import com.nuoshi.console.common.util.FileHandleUtil;
import com.nuoshi.console.common.util.StringFormat;
import com.nuoshi.console.common.util.Utilities;
import com.nuoshi.console.dao.AgentManageDao;
import com.nuoshi.console.dao.AgentMasterDao;
import com.nuoshi.console.dao.AuditHistoryDao;
import com.nuoshi.console.dao.IdentityPhotoDao;
import com.nuoshi.console.domain.agent.AgentMaster;
import com.nuoshi.console.domain.agent.IdentityPhoto;
import com.nuoshi.console.domain.agent.PhoneVerifyLog;
import com.nuoshi.console.domain.agent.RejectReason;
import com.nuoshi.console.domain.auditHistory.AuditHistory;
import com.nuoshi.console.domain.auditHistory.SubAuditHistory;
import com.nuoshi.console.domain.photo.Photo;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.web.form.AgentVerifyResultForm;
import com.nuoshi.console.web.session.SessionUser;

@Service
public class AgentMasterService extends BaseService {
	Log log=LogFactory.getLog(AgentManageService.class);
	
	@Resource
	private IdentityPhotoDao identityPhotoDao;
	
	@Resource
	private SmsService smsService;
	
	@Resource
	private AgentMasterService agentMasterService;
	
	@Resource
	private AuditHistoryService auditHistoryService;
	
	@Resource
	private AgentMasterDao agentMasterDao;
	
	@Resource
	private AuditHistoryDao auditHistoryDao;
	
	@Resource
	private AgentManageDao agentManageDao;
	
	@Resource
	private TransferPhoneService transferPhoneService;
	
	public List<AgentMaster> getAllAgentMasterByPage() {
		return agentMasterDao.getAllAgentMasterByPage();
	}
	
	public List<AgentMaster> getAllAgentMasterByPage(List<String> conditions){
		return agentMasterDao.getAllAgentMasterByPage(conditions);
	}
	
	public List<AgentMaster> getAgentMasterByConditions(List<String> conditions){
		return agentMasterDao.getAgentMasterByConditions(conditions);
	}
	
	public IdentityPhoto selectIdentityPhotoById(int id, int agentId) {
		return identityPhotoDao.selectIdentityPhotoById(id, agentId);
	}
	
	public Photo selectPhotoById(int id) {
		return identityPhotoDao.selectPhotoById(id);
	}
	public List<Photo> selectPhotoByIdList(List<Integer> idList) {
		return identityPhotoDao.selectPhotoByIdList(idList);
	}
	
	public List<RejectReason> getAllRejectReasonByPage(int type){
		return agentMasterDao.getAllRejectReasonByPage(type);
	}
	
	public List<RejectReason> getAllRejectReason2ByPage(){
		return agentMasterDao.getAllRejectReason2ByPage();
	}
	
	public int addRejectReason(RejectReason rejectReason) {
		return agentMasterDao.addRejectReason(rejectReason);
	}
	
	public List<RejectReason> getAllRejectReason() {
		return agentMasterDao.getAllRejectReason();
	}
	
	public int updateRejectReason(RejectReason reason){
		return agentMasterDao.updateRejectReason(reason);
	}
	
	public int delRejectReason(int id) {
		return agentMasterDao.delRejectReason(id);
	}
	
	public String selServiceLocation(AgentMaster view) {
		StringBuilder sb = new StringBuilder();
		if(view.getCityId() > 0) {
			sb.append(agentManageDao.getDirName(view.getCityId())).append("-");
		}
		if(view.getDistId() > 0) {
			sb.append(agentManageDao.getDirName(view.getDistId())).append("-");
		}
		if(view.getBlockId() > 0) {
			sb.append(agentManageDao.getDirName(view.getBlockId()));
		}
		return sb.toString();
		
	}
	
	public AgentMaster selectAgentMasterById(Integer agentId){
		AgentMaster agentMaster = agentMasterDao.selectAgentMasterById(agentId);
		return agentMaster;
	}
	
	public String doVerify(AgentVerifyResultForm avr, int userId) {
		
		String action = "reject";
		AgentMaster agentMaster = agentMasterDao.selectAgentMasterById(avr.getAgentId());
		//AgentMaster verifyview = agentMasterDao.selectAgentMasterById(avr.getAgentId());
		if (null == agentMaster) {
            return Resources.getString("agent.verify.agentnull", new Object[]{"" + avr.getAgentId()});
        }
		String oldStatus = agentMaster.getStatus();
		if(Globals.STATUS_OK.equals(oldStatus)){
			return Resources.getString("agent.verify.success", new Object[]{"" + agentMaster.getId()});
	   	}
		
		if(agentMaster.getNamecardStatusNumber() == 2) {
			agentMaster.setNamecardOk();
			//verifyview.setNamecardOk();
		}
		
		/**
		 * 经纪人门店
		 */
        String field = "";
		if(avr.getIdCardResult() == Globals.AUDIT_RESULT_PASS||avr.getIdCardResult() == 1) {
			agentMaster.setIdcardOk();
			agentMaster.setMsgIdcard("");
		} else if(avr.getIdCardResult() == Globals.AUDIT_RESULT_REJECT||avr.getIdCardResult() == 2) {
			field += field.equals("")? "身份证":"，身份证";
			agentMaster.setIdcardRejected();
            agentMaster.setMsgIdcard(avr.getIdCardMsg());
		}
		
		if(avr.getHeadResult() == Globals.AUDIT_RESULT_PASS||avr.getHeadResult() == 1) {
			agentMaster.setHeadOk();
            agentMaster.setMsgHead("");
		} else if(avr.getHeadResult() == Globals.AUDIT_RESULT_REJECT||avr.getHeadResult() == 2) {
			field += field.equals("")? "头像":"头像";
			agentMaster.setHeadRejected();
            agentMaster.setMsgHead(avr.getHeadMsg());
		}
		
		if (agentMaster.isHeadOk() && agentMaster.isIdcardOk() && agentMaster.isNamecardOk()) {
            action = "approval";
        } else {
            action = "reject";
        }

		if (action.equalsIgnoreCase("approval")) {
            agentMaster.setStatus(Globals.STATUS_OK);
           
//            //更新中介头像
//            IdentityPhoto photo = identityPhotoDao.selectIdentityPhotoById(agentMaster.getImgHeadId(), agentMaster.getAgentId());
//            Photo headPhoto = this.selectPhotoById(agentMaster.getImgHeadId());
//            if (null != photo) {
//                agentMaster.setHeadBrowse(photo.getM());
//                agentMaster.setHeadTiny(photo.getS());
//                //跟新评价列表里该中介的头像
//              //  agentMasterDao.updateRateHeadTiny(agentMaster.getId(), photo.getS());
//            } else if(headPhoto != null){
//            	 agentMaster.setHeadBrowse(headPhoto.getM());
//                 agentMaster.setHeadTiny(headPhoto.getS());
//                 //跟新评价列表里该中介的头像
//                // agentMasterDao.updateRateHeadTiny(agentMaster.getId(), headPhoto.getS());
//            }
//
//            //更新中介信息
//            if (null == photo && headPhoto == null) {
//                return Resources.getString("agent.verify.identity.photonull", new Object[]{"" + avr.getAgentId()});
//            }
        }
		
		 
		//处理过了，从审核队列里去掉

	    agentMaster.setLastUpdateDate(Utilities.getCurrentTimestamp());
        agentMaster.setAdminId(userId);
         moveHeadImg(agentMaster);
        if (agentMasterDao.updateAgentMasterVerify(agentMaster, oldStatus) == -1) {
            return Resources.getString("agent.verify.dberror", new Object[]{"" + agentMaster.getId()});
        }
        
        /**
         * 如果身份认证通过之后，绑定400
         */
//        if (action.equalsIgnoreCase("approval")) {
//        	PhoneService phoneService = new PhoneService();
//            String registerResult = null;
//            int user400Id = 0;
//            
//            //开始绑定400电话
//            try{
//           	 	log.error("当前AgentId为:" + agentMaster.getId() + " +++++++++++++++++++++CallNumber " + verifyview.getV400Number() + " Mobile:" + verifyview.getMobile());
//           	 	if(verifyview.getV400UserId() <=0 && verifyview.getV400Number() != null &&!"".equals(verifyview.getV400Number())) {
//                	registerResult = phoneService.registerUser(new Date().getTime(), PhoneConstants.DEFAULT_USER_FEE, verifyview.getMobile(), verifyview.getV400Number());
//                	//log.error("注册的结果为：" + registerResult);
// 					if (registerResult.length() > 0 && registerResult.contains(",")) {
// 						user400Id = Integer.parseInt(registerResult.split(",")[0]);
// 					}
// 					if (user400Id < 0) {
// 						//log.error("注册400电话失败，请重新尝试或联系客服。");
// 						return Resources.getString("agent.verify.400Error", new Object[]{"" + verifyview.getId()});
// 					}
// 					agentMasterDao.add400UserId(verifyview.getId(), user400Id);
// 					agentMasterDao.updatePhoneStatus(verifyview.getV400Number() , verifyview.getId(), user400Id);
//           	 	}
//            } catch (Exception e) {
//	           	log.error(Resources.getString("agent.verify.400Error", new Object[]{"" + verifyview.getId()}));
//	           	return Resources.getString("agent.verify.400Error", new Object[]{"" + verifyview.getId()});
//            }
//        }
        if (action.equals("reject")){
            //短信提示
        	String sContent = Resources.getString("sms.agentverify.reject", new Object[]{field});
            smsService.sendSmsIgnoreFail(userId,agentMaster.getMobile(),sContent);
        }else if (action.equalsIgnoreCase("approval")) {
        	 //短信提示
        	String sContent = Resources.getString("sms.agentverify.pass."+agentMaster.getCityId());//取各个城市的认证通过的短信
        	if(StringUtils.isBlank(sContent)){//不存在取默认的
        		sContent = Resources.getString("sms.agentverify.pass.default");
        		if(StringUtils.isBlank(sContent)){
        			sContent = "尊敬的淘房网用户您好：您的身份认证已成功，可随时登录www.taofang.com发布房源，精彩等您体验， 如有问题请致电010-51389757，淘房祝您多多开单！";
        		}
        		
        	}
        	if(!Globals.STATUS_OK.equals(oldStatus)){
        		 smsService.sendSmsIgnoreFail(userId,agentMaster.getMobile(),sContent);
        	}
        }
        
//        更改经纪人通知crm注释掉2012-1-4
//    	//通知crm身份证和头像审核通过或者打回
//		//wangjh修改
//		TFUser user=null;
//		if(avr.getAgentId()>0){
//			try{
//				
//				user = userService.selectUserMobileById(avr.getAgentId());
//			}catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		if(user!=null){
//			try{
//				crmService.onlineUpdatePort(user.getMobile(), user.getMobile());
//			}catch(Exception e){
//				log.error("通知crm身份证和头像审核结果出错！" +e);
//			}
//		}
       
        ApplicationUtil.getHttpResponse(Resources.getString("taofang.service.url") + "agent/refresh/" + agentMaster.getAgentId());
        return Resources.getString("agent.verify.success", new Object[]{"" + agentMaster.getId()});
	}
	
	
	/**
	 * 批量处理经纪人审核
	 * 
	 * @param avrList
	 * @param userId
	 * @return
	 * @author wangjh Jan 5, 2012 10:20:03 AM
	 */
	public String doVerifyByList(List<AgentVerifyResultForm> avrList, int userId) {
		//System.out.println("AgentmasterService.doVerifyByList() start.");
		// 查出经济人详情
		/* 该批量 */
		if (CollectionUtils.isEmpty(avrList)) {
			log.error("经济人审核任务为空！");
			//System.out.println("AgentmasterService.avrList="+avrList);
			return Resources.getString("agent.verify.null");
		}
		List<Integer> avrIdList = new ArrayList<Integer>();
		for (AgentVerifyResultForm avr : avrList) {
			if (avr != null && avr.getAgentId() > 0) {
				avrIdList.add(avr.getAgentId());
			}
		}
		List<AgentMaster> agentMasterList = this
				.selectAgentMasterByIds(avrIdList);
		/* 改批量 */

		if (CollectionUtils.isEmpty(agentMasterList)) {
			String ids = "";
			for (Integer id : avrIdList) {
				ids += id + " ;";
			}
			log.error("通过经纪人idList找经济人为空!idList=  " + ids);
			//System.out.println("AgentmasterService.通过经纪人idList找经济人为空!idList=  " + ids);
			return Resources.getString("agent.verify.agentlistnull");
		}
		// agentMap;
		Map<Integer, AgentMaster> agentMap = new HashMap<Integer, AgentMaster>();

		/* 旧状态留下 */
		Map<Integer, String> oldStatusMap = new HashMap<Integer, String>();
		for (AgentMaster agent : agentMasterList) {
			agentMap.put(agent.getAgentId(), agent);
			oldStatusMap.put(agent.getAgentId(), agent.getStatus());
			// 设置名片结果
			if (agent.getNamecardStatusNumber() == 2) {
				agent.setNamecardOk();
			}
		}
		//System.out.println("AgentMasterService.旧状态留下");
		/* 分成两部分 通过的和未通过的 */
		List<AgentMaster> approvalList = new ArrayList<AgentMaster>();
		List<AgentMaster> rejectList = new ArrayList<AgentMaster>();

		/**
		 * 经纪人门店
		 */
		Map<Integer, String> fieldMap = new HashMap<Integer, String>();
		AgentMaster agentMasterResult=null;
		for (AgentVerifyResultForm avr : avrList) {
			agentMasterResult = new AgentMaster();
			// field 在发送短信时使用
			String field = "";
			agentMasterResult = agentMap.get(avr.getAgentId());
			if (agentMasterResult == null) {
				log.info("审核的任务中不存在对应的经纪人。");
				continue;
			}
			if (avr.getIdCardResult() == Globals.AUDIT_RESULT_PASS||avr.getIdCardResult() == 1) {
				agentMasterResult.setIdcardOk();
				agentMasterResult.setMsgIdcard("");
			} else if (avr.getIdCardResult() == Globals.AUDIT_RESULT_REJECT||avr.getIdCardResult() == 2) {
				field += field.equals("") ? "身份证" : "，身份证";
				agentMasterResult.setIdcardRejected();
				agentMasterResult.setMsgIdcard(avr.getIdCardMsg());
				// 放置发短信的信息
				fieldMap.put(agentMasterResult.getAgentId(), field);
			}

			if (avr.getHeadResult() == Globals.AUDIT_RESULT_PASS||avr.getHeadResult() == 1) {
				agentMasterResult.setHeadOk();
				agentMasterResult.setMsgHead("");
			} else if (avr.getHeadResult() == Globals.AUDIT_RESULT_REJECT||avr.getHeadResult() == 2) {
				field += field.equals("") ? "头像" : "头像";
				agentMasterResult.setHeadRejected();
				agentMasterResult.setMsgHead(avr.getHeadMsg());
				// 放置发短信的信息
				fieldMap.put(agentMasterResult.getAgentId(), field);
			}
			// 头像和身份证都通过，设置总的审核结果
			if (agentMasterResult.isHeadOk() && agentMasterResult.isIdcardOk()
					&& agentMasterResult.isNamecardOk()) {
				agentMasterResult.setStatus(Globals.STATUS_OK);
				approvalList.add(agentMap.get(avr.getAgentId()));
			} else {
				rejectList.add(agentMap.get(avr.getAgentId()));
			}

		}
		//System.out.println("AgentMasterService.经纪人门店");

		/*-------------------------通过时处理方式 开始--------------------*/
		if (CollectionUtils.isNotEmpty(approvalList)) {
			// 改为批量获取
			// 更新中介头像
			List<Integer> approvalIdList = new ArrayList<Integer>();
			for (AgentMaster agent : approvalList) {
				if (agent == null) {
					continue;
				}
				approvalIdList.add(agent.getAgentId());
			}
			if (CollectionUtils.isEmpty(approvalIdList)) {
				//System.out.println("AgentMasterService.approvalIdList is empty");
				return Resources.getString("不可能出现!");
			}
			/* 改为批量查询 开始 */
			List<IdentityPhoto> photoList = identityPhotoDao
					.selectIdentityPhotoByIdList(approvalIdList);
			List<Photo> headPhotoList = this
					.selectPhotoByIdList(approvalIdList);
			/* 改为批量查询 结束 */

			if (CollectionUtils.isEmpty(photoList)
					&& CollectionUtils.isEmpty(headPhotoList)) {
				log.error("身份证和头像的信息都为空！");
				//System.out.println("AgentMasterService.身份证和头像的信息都为空");
//				return Resources.getString("agent.verify.identity.null");
			}
			Map<Integer, IdentityPhoto> identityMap = new HashMap<Integer, IdentityPhoto>();
			if (CollectionUtils.isNotEmpty(photoList)) {
				for (IdentityPhoto identity : photoList) {
					identityMap.put(identity.getOwner(), identity);
				}
			}
			Map<Integer, Photo> photoMap = new HashMap<Integer, Photo>();
			if (CollectionUtils.isNotEmpty(headPhotoList)) {
				for (Photo photo : headPhotoList) {
					photoMap.put(photo.getOwner(), photo);
				}
			}
			//System.out.println("AgentMasterService.遍历approvalList start");
			for (AgentMaster agentMaster : approvalList) {
				if (agentMaster == null) {
					continue;
				}
				Integer id = agentMaster.getAgentId();
				Photo photo = photoMap.get(id);
				IdentityPhoto headPhoto = identityMap.get(id);
				if (null != photo) {
					agentMaster.setHeadBrowse(photo.getM());
					agentMaster.setHeadTiny(photo.getS());
					// 跟新评价列表里该中介的头像
					/*agentMasterDao.updateRateHeadTiny(agentMaster.getId(),
							photo.getS());*/
				} else if (headPhoto != null) {
					agentMaster.setHeadBrowse(headPhoto.getM());
					agentMaster.setHeadTiny(headPhoto.getS());
					/*// 跟新评价列表里该中介的头像
					agentMasterDao.updateRateHeadTiny(agentMaster.getId(),
							headPhoto.getS());*/
				}
			}

		}
		
		/*-------------------------通过时处理方式   结束--------------------*/
		//System.out.println("AgentMasterService.更新中介信息");	
		// 更新中介信息
		// 处理过了，从审核队列里去掉
		for (AgentMaster agentMaster : agentMasterList) {
			if (agentMaster == null) {
				continue;
			}
			String oldStatus = oldStatusMap.get(agentMaster.getAgentId());
			agentMaster.setLastUpdateDate(Utilities.getCurrentTimestamp());
			agentMaster.setAdminId(userId);
			moveHeadImg(agentMaster);
			Integer flag2 = agentMasterDao.updateAgentMasterVerify(agentMaster,
					oldStatus);
			if (flag2 == -1) {
				//System.out.println("AgentMasterService:flag1="+flag1+",flag2="+flag2);
				return Resources.getString("agent.verify.dberror",
						new Object[] { "" + agentMaster.getId() });
			}
		}
		// 从任务列表中删除，并更新agent字段
		this.deleteAuditTask(avrIdList);
		// 记录历史 agentMap
		List<Integer> agentIdList = new ArrayList<Integer>(agentMap.keySet());
		List<AuditHistory> auditHistoryList = auditHistoryService
				.getLastRecordByHouseIds(agentIdList, Globals.AGENT_TYPE);
		Map<Integer, AuditHistory> historyMap = new HashMap<Integer, AuditHistory>();
		if (CollectionUtils.isNotEmpty(auditHistoryList)) {
			// 不为空 记记录
			for (AuditHistory auditHistory : auditHistoryList) {
				historyMap.put(auditHistory.getHouseId(), auditHistory);
			}
			// 通过的历史
			List<AuditHistory> passHistory = new ArrayList<AuditHistory>();
			// 打回的历史
			List<AuditHistory> rejectHistory = new ArrayList<AuditHistory>();
			for (AgentMaster agent : approvalList) {
				AuditHistory history = historyMap.get(agent.getAgentId());
				passHistory.add(history);
			}
			for (AgentMaster agent : rejectList) {
				AuditHistory history = historyMap.get(agent.getAgentId());
				rejectHistory.add(history);
			}
			// 处理时间
			Date dealTime = new Date();
			//记录通过的主表历史
			if(CollectionUtils.isNotEmpty(passHistory)){
				auditHistoryService.recordAuditHistoryResult(passHistory,
						Globals.AUDIT_RESULT_PASS, dealTime);
			}
			//记录打回的主表历史
			if(CollectionUtils.isNotEmpty(rejectHistory)){
				auditHistoryService.recordAuditHistoryResult(rejectHistory,
						Globals.AUDIT_RESULT_REJECT, dealTime);
			}

		}
		for (AgentMaster agentMaster : agentMasterList) {

			try {
				// 刷新线上的经纪人状态
				ApplicationUtil.getHttpResponse(Resources
						.getString("taofang.service.url")
						+ "agent/refresh/"
						+ agentMaster.getAgentId());
			} catch (Exception e) {
				// 不让更新经纪人状态影响审核
				log.error("经纪人完成,刷新线上状态失败！");
			}
		}

		return Resources.getString("agent.verify.successList");
	}
	
	
	
	
	private List<AgentMaster> selectAgentMasterByIds(List<Integer> avrIdList) {
		return agentMasterDao.selectAgentMasterByIds(avrIdList);
	}

	public String connect400Phone(int id, String number) {
		PhoneService phoneService = new PhoneService();
        String registerResult = null;
        int user400Id = 0;
        AgentMaster agent = agentMasterDao.selectAgentMasterById(id);
		if (null == agent) {
            return Resources.getString("agent.verify.agentnull", new Object[]{"" + id});
        }
        //开始绑定400电话
        try{
       	 	if(agent.getV400UserId() <=0 && StringFormat.isValidTransferPhone(number) && transferPhoneService.canBeUsed(number)) {
            	registerResult = phoneService.registerUser(new Date().getTime(), PhoneConstants.DEFAULT_USER_FEE, agent.getMobile(), number);
				if (registerResult.length() > 0 && registerResult.contains(",")) {
					user400Id = Integer.parseInt(registerResult.split(",")[0]);
				}
				if (user400Id < 0) {
					return Resources.getString("agent.verify.400Error", new Object[]{"" + agent.getId()});
				}
				agentMasterDao.add400UserId(id, user400Id, number);
				agentMasterDao.updatePhoneStatus(number, agent.getId(), user400Id);
       	 	}
        } catch (Exception e) {
        	log.error(Resources.getString("agent.verify.400Error", new Object[]{"" + agent.getId()}));
           	return Resources.getString("agent.verify.400Error", new Object[]{"" + agent.getId()});
        }
        return Resources.getString("agent.verify.400OK", new Object[]{"" + agent.getId()});
	}
	
	public List<AgentMaster> getAgentByIds(List<Integer> integers) {
		List<AgentMaster> agentMasters = agentMasterDao.getAgentByIds(integers);
//		
//		for (AgentMaster agentMaster : agentMasters) {
//			int count = agentMasterDao.getPhotoCountForHeadImg(agentMaster.getImgHeadId(),agentMaster.getAgentId());
//			if(count==0){
//				agentMaster.setHeadImg(null);
//			}
//		}
//		
//		// 如果head取得失败,则去photo表内再找.老数据有可能存在photo表内,新的都在identity_photo里
//		int size = agentMasters.size();
//		for (int i = 0; i < size; i++) {
//			AgentMaster agentMaster = agentMasters.get(i);
//			if (StringUtils.isEmpty(agentMaster.getHeadImg())) {
//				String agentHeadImg = "";
//				agentHeadImg = agentMasterService.getAgentHeadFromPhoto(agentMaster.getImgHeadId());
//				agentMaster.setHeadImg(agentHeadImg);
//			}
//		}
		return agentMasters;
	}

	/**
	 * 从photo里得到以前老用户的头像img
	 * @param imgHeadId
	 * @return
	 * @date 2012-1-4下午4:38:36
	 * @author dongyj
	 * @param i 
	 */
	public String getAgentHeadFromPhoto(int imgHeadId) {
		return agentMasterDao.getAgentHeadFromPhoto(imgHeadId);
	}

	public List<RejectReason> getAllRejectReasonByType(int i) {
		return agentMasterDao.getAllRejectReasonByType(i);
	}

	public List<AgentMaster> getVerifyMobileAgentByPage(HashMap<String,Object> params) {
		return agentMasterDao.getVerifyMobileAgentByPage(params);
	}

	/**
	 * 手动验证手机号
	 * @param blockList
	 * @param passList 字符串格式为:agentId_cityId_status_mobile_name
	 * @throws Exception
	 * @date 2012-1-5下午2:14:07
	 * @author dongyj
	 * @param sessionUser 
	 */
	public void verifyMobile(String blockList, String passList, SessionUser sessionUser) throws Exception {
		try {
			List<PhoneVerifyLog> phoneVerifyLogs = new ArrayList<PhoneVerifyLog>();
			User user = sessionUser.getUser();

			List<Integer> blockIntList = new ArrayList<Integer>();
			if (StringUtils.isNotBlank(blockList)) {
				String[] blockArray = blockList.split(",");
				for (int i = 0; i < blockArray.length; i++) {
					blockIntList.add(Integer.parseInt(blockArray[i].split("_")[0]));
					PhoneVerifyLog phoneVerifyLog = new PhoneVerifyLog();
					phoneVerifyLog.setAuditorId(user.getId());
					phoneVerifyLog.setAgentId(Integer.parseInt(blockArray[i].split("_")[0]));
					String submitMobileDateString = blockArray[i].split("_")[5];
					if (!submitMobileDateString.equals("T.nul")) {
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						Date submitMobileDate = simpleDateFormat.parse(submitMobileDateString);
						phoneVerifyLog.setInsertDate(submitMobileDate);
					}
					phoneVerifyLog.setAuditorName(user.getChnName());
					phoneVerifyLog.setDate(new Date());
					phoneVerifyLog.setMobile(blockArray[i].split("_")[3].equals("T.nul")  ? "" : blockArray[i].split("_")[3]);
					phoneVerifyLog.setResult(new Byte("2"));
					phoneVerifyLog.setUserName(blockArray[i].split("_")[4].equals("T.nul") ? "" : blockArray[i].split("_")[4]);
					phoneVerifyLogs.add(phoneVerifyLog);
				}
			}

			// 拒绝逻辑
			if (null != blockIntList && blockIntList.size() > 0) {
				// 直接删除agent_master等表数据
				for (Integer integer : blockIntList) {
					agentManageDao.deleteAgent(integer);
				}
			}

			String[] passArray = null;
			if (StringUtils.isNotBlank(passList)) {
				passArray = passList.split(",");
			}

			// 通过逻辑(参见的为:AgentService工程内UserService内的addAgentMaster方法)
			if (null != passArray && passArray.length > 0) {
				// 所有pass的id
				List<Integer> integers = new ArrayList<Integer>();
				// 更新agent_purchase表字段
				for (int i = 0; i < passArray.length; i++) {
					Integer agentId = Integer.parseInt(passArray[i].split("_")[0]);

					// 在更新的时候,如果有人同步操作,可能会有重复提交.所以先检查本条是否已经操作了,如果操作了.直接返回.
					if (ifVerifiedPhone(agentId)) {
						break;
					}
					String mobile = passArray[i].split("_")[3].equals("T.nul") ? "" : passArray[i].split("_")[3];
					integers.add(agentId);
					try {
						if (null != mobile && !"".equals(mobile)) {
							// 发手机号验证成功短消息用户手机
							smsService.sendSms(agentId, mobile, "尊敬的淘房网用户您好：手机验证已成功，现可登录淘房进行身份认证，通过即可获得发布在线房源、标签等服务，感谢支持！");
						}
					} catch (Exception e) {
						throw new Exception("人工验证手机号码成功,发送短信时出错!----------->" + e.getMessage());
					}

					PhoneVerifyLog phoneVerifyLog = new PhoneVerifyLog();
					phoneVerifyLog.setAuditorId(user.getId());
					phoneVerifyLog.setAuditorName(user.getChnName());
					String submitMobileDateString = passArray[i].split("_")[5];
					if (!submitMobileDateString.equals("T.nul")) {
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						Date submitMobileDate = simpleDateFormat.parse(submitMobileDateString);
						phoneVerifyLog.setInsertDate(submitMobileDate);
					}
					phoneVerifyLog.setDate(new Date());
					phoneVerifyLog.setMobile(passArray[i].split("_")[3] .equals("T.nul") ? "" : passArray[i].split("_")[3]);
					phoneVerifyLog.setResult(new Byte("1"));
					phoneVerifyLog.setAgentId(Integer.parseInt(passArray[i].split("_")[0]));
					phoneVerifyLog.setUserName(passArray[i].split("_")[4].equals("T.nul") ? "" : passArray[i].split("_")[4]);
					phoneVerifyLogs.add(phoneVerifyLog);

				}

				// 更新agent_master表字段
				if (null != integers && integers.size() > 0) {//手机验证通过，不改变套餐
					agentMasterDao.updateForMobilePass(integers);
				}
			}
			
			// 添加审核日志
			for (PhoneVerifyLog phoneVerifyLog : phoneVerifyLogs) {
				agentMasterDao.insertPhoneVerifyLog(phoneVerifyLog);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	/**
	 * 被处理的情况:1,没有此agentId,2,此id对应mobile_check_status不为1
	 * @param agentId
	 * @return
	 * @date 2012-1-7下午4:43:09
	 * @author dongyj
	 */
	private boolean ifVerifiedPhone(Integer agentId) {
		return agentMasterDao.ifVerifiedPhone(agentId);
	}

	/**
	 * 组成查询条件
	 * @param agentId
	 * @param phone
	 * @param conditions
	 * @param params
	 * @param model
	 * @date 2012-1-5下午3:59:10
	 * @author dongyj
	 * @throws Exception 
	 */
	public void consistQuery(String agentId, String phone, StringBuffer conditions, HashMap<String, Object> params, Model model) throws Exception {
		model.addAttribute("phone", phone);
		model.addAttribute("agentId", agentId);
		if (StringUtils.isNotBlank(agentId)) {
			String regex = "^\\d+$";
			if(!agentId.matches(regex)){
				throw new Exception("输入经纪人id错误.");
			}
			conditions.append(" AND agent_id = " + agentId);
		}
		if (StringUtils.isNotBlank(phone)) {
			conditions.append(" AND t_user.mobile = '" + phone + "'");
		}
		params.put("conditions", conditions);
	}

	public List<Integer> getAgentIdForAudit(String auditSign, Integer num,
			Integer cityId,List<Integer> auditStatus) {
		return agentMasterDao.getAgentIdForAudit(auditSign,num,cityId,auditStatus);
	}

	public void signAgent(String auditSign, List<Integer> idList) {
		agentMasterDao.signAgent(auditSign,idList);
	}

	/**
	 * 通过获得的经纪人id  查处少量需要的信息
	 * @param idList 经纪人id（agentId）
	 * @return
	 * @author wangjh
	 * Jan 10, 2012 3:36:35 PM
	 */
	public  List<AgentMaster> getAgentInfoCity(List<Integer> idList) {
		return agentMasterDao.getAgentInfoCity(idList);
	}

	/**
	 * 判断经纪人是否符合审核条件
	 * @param agentIdList
	 * @return
	 * @author wangjh
	 * Jan 10, 2012 4:14:46 PM
	 */
	public List<Integer> getInconformityAgentIds(List<Integer> agentIdList,List<Integer> auditStatus) {
		return agentMasterDao.getInconformityAgentIds(agentIdList,auditStatus);
	}

	public void deleteAuditTask(List<Integer> agentIdList) {
		/* 删除任务 */
		agentMasterDao.deleteAuditTask(agentIdList);
		/* 只清空标记，不改变房源状态 */
		agentMasterDao.updateAuditStatusByIdList(agentIdList,null, null);		
	}

	public List<AuditHistory> getHistoryInfo(List<Integer> idList) {
		return agentMasterDao.getHistoryInfo(idList);
	}
	
	
	

	/**
	 * 保存经纪人图片审核历史
	 * @param totalResult 前台所有数据
	 * @param sessionUser
	 * @date 2012-1-11下午1:43:40
	 * @author dongyj
	 * @throws Exception 
	 */
	public void verifyPhotos(String totalResult, SessionUser sessionUser) throws Exception {
		String[] result = totalResult.split("_placeHolder2_");
		List<Integer> allAgentIdList = new ArrayList<Integer>();
		List<SubAuditHistory> subAuditHistories = new ArrayList<SubAuditHistory>();
		for (int i = 0; i < result.length; i++) {
			SubAuditHistory subAuditHistory = new SubAuditHistory();
			
			String[] typeAndId_imgAndId_reason_name_describe = result[i].split("_placeHolder1_");
			if (typeAndId_imgAndId_reason_name_describe[2].equals("Tnul")) {
				// 没有拒绝原因
				subAuditHistory.setAuditResult(Globals.AUDIT_RESULT_PASS);
			} else {
				// 有拒绝原因
				subAuditHistory.setAuditResult(Globals.AUDIT_RESULT_REJECT);
				String reason = typeAndId_imgAndId_reason_name_describe[2].equals("Tnul")?"":typeAndId_imgAndId_reason_name_describe[2];
				subAuditHistory.setRejectReason(reason);
			}
			subAuditHistory.setAuthorName(typeAndId_imgAndId_reason_name_describe[3].equals("Tnul")?"":typeAndId_imgAndId_reason_name_describe[3]);
			String type = typeAndId_imgAndId_reason_name_describe[0].split("_")[0];
			Integer agentId = Integer.parseInt(typeAndId_imgAndId_reason_name_describe[0].split("_")[1]);
			String imgUrl = typeAndId_imgAndId_reason_name_describe[1].split("_imgSpliter_")[0];
			String describe = typeAndId_imgAndId_reason_name_describe[4].equals("Tnul")?"":typeAndId_imgAndId_reason_name_describe[4];

			if (!allAgentIdList.contains(agentId)) {
				allAgentIdList.add(agentId);
			}
			if ("idCard".equals(type)) {
				subAuditHistory.setAuditStep(Globals.AUDIT_HISTORY_AGENT_IDCARD_IMG);
				subAuditHistory.setExtra("经纪人身份证");
			} else if ("head".equals(type)) {
				subAuditHistory.setAuditStep(Globals.AUDIT_HISTORY_AGENT_HEAD_IMG);
				subAuditHistory.setExtra("经纪人头像");
			}
			subAuditHistory.setPhotoUrl(imgUrl);
			subAuditHistory.setHouseId(agentId);
			subAuditHistory.setHouseDescribe(describe);
			
			subAuditHistories.add(subAuditHistory);
		}
		// 设置auditId和历史主表的插入工作
		insertIntoHistory(allAgentIdList,subAuditHistories,sessionUser);
	}

	private void insertIntoHistory(List<Integer> allAgentIdList, List<SubAuditHistory> subAuditHistories, SessionUser sessionUser) throws Exception {
		// 添加审核主表
		auditHistoryService.foundHistory(allAgentIdList, Globals.AGENT_TYPE);
		List<AuditHistory> auditHistories = auditHistoryService.getLastRecordByHouseIds(allAgentIdList, Globals.AGENT_TYPE);
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (AuditHistory auditHistory : auditHistories) {
			map.put(auditHistory.getHouseId(), auditHistory.getId());
		}

		if (null == map || map.size() < 1) {
			throw new Exception("请求插入经纪人图片审核记录主表时出错!@AgentMasterService!insertIntoHistory");
		}

		// 审核信息
		List<AgentVerifyResultForm> agentVerifyResultForms = new ArrayList<AgentVerifyResultForm>();

		for (SubAuditHistory subAuditHistory : subAuditHistories) {
			subAuditHistory.setAuditId(map.get(subAuditHistory.getHouseId()));
			subAuditHistory.setAuditTime(new Date());
			subAuditHistory.setDealerId(sessionUser.getUser().getId());
			subAuditHistory.setDealerName(sessionUser.getUser().getChnName());
			subAuditHistory.setPhotoUrl(subAuditHistory.getPhotoUrl());
			auditHistoryDao.insertSub(subAuditHistory);
			
			AgentVerifyResultForm agentVerifyResultForm = new AgentVerifyResultForm();
			agentVerifyResultForm.setAgentId(subAuditHistory.getHouseId());
			boolean isExists = false;
			for (int i = 0; i < agentVerifyResultForms.size(); i++) {
				if (agentVerifyResultForm.equals(agentVerifyResultForms.get(i))) {
					agentVerifyResultForm = agentVerifyResultForms.get(i);
					isExists = true;
					break;
				}
			}
			
			if (subAuditHistory.getAuditResult() == Globals.AUDIT_RESULT_REJECT) {
				switch (subAuditHistory.getAuditStep()) {
				case Globals.AUDIT_HISTORY_AGENT_HEAD_IMG:
					agentVerifyResultForm.setHeadMsg(subAuditHistory.getRejectReason());
					agentVerifyResultForm.setHeadResult(Globals.AUDIT_RESULT_REJECT);
					break;
				case Globals.AUDIT_HISTORY_AGENT_IDCARD_IMG:
					agentVerifyResultForm.setIdCardMsg(subAuditHistory.getRejectReason());
					agentVerifyResultForm.setIdCardResult(Globals.AUDIT_RESULT_REJECT);
					break;
				default:
					break;
				}
			}

			if (!isExists) {
				agentVerifyResultForms.add(agentVerifyResultForm);
			}
		}
		/*if(agentVerifyResultForms.size()>0){
			for(AgentVerifyResultForm d:agentVerifyResultForms){
				System.out.println("AgentMasterService:agentId="+d.getAgentId()+",headMsg"+d.getHeadMsg()+",getNameCardMsg"+d.getNameCardMsg());
			}
			
		}*/
		
		agentMasterService.doVerifyByList(agentVerifyResultForms, sessionUser.getUser().getId());
	}

	public List<PhoneVerifyLog> getVerifyPhoneHistoryListByPage(HashMap<String, Object> params) {
		return agentMasterDao.getVerifyPhoneHistoryListByPage(params);
	}

	public Integer getUnclaimedAwaitAuditCount(List<Integer> agentAuditStatus) {
		
		return agentMasterDao.getUnclaimedAwaitAuditCount(agentAuditStatus);
	}
	public void updateAgentForStartStop(Integer agentId,String type,int tipType) {
		int result = agentMasterDao.updateAgentForStartStop(agentId,type,tipType);
		if(result>0){
			if("N".equals(type)){//开启用户,检查用户是不是有免费套餐，没有，添加新的套餐
				AgentMaster agentMaster = agentMasterDao.selectAgentMasterById(agentId);
				agentMasterDao.addFreePackage(agentMaster, false);
				
			}
		}
		
	}
	
	public boolean moveHeadImg(AgentMaster agent){
		boolean isLargeMove  =  FileHandleUtil.moveToDest(Resources.getString("sys.path.identity.photo")+AgentPhotoUrlUtil.getPendingAgentHeadPortraitRelativeUrlLarge(agent.getCityId(), agent.getAgentId()), (Resources.getString("sys.path.identity.photo")+AgentPhotoUrlUtil.getAgentHeadPortraitRelativeUrlLarge(agent.getCityId(), agent.getAgentId())), false);
		boolean isSmallMove  =  FileHandleUtil.moveToDest(Resources.getString("sys.path.identity.photo")+AgentPhotoUrlUtil.getPendingAgentHeadPortraitRelativeUrlSmall(agent.getCityId(), agent.getAgentId()), (Resources.getString("sys.path.identity.photo")+AgentPhotoUrlUtil.getAgentHeadPortraitRelativeUrlSmall(agent.getCityId(), agent.getAgentId())), false);
		if(isLargeMove && isSmallMove) return true;
		return false;
	}

	public AgentMaster selectAgentMasterByMobile(String agentPhone) {
		return agentMasterDao.selectAgentMasterByMobile(agentPhone);
	}

	public void updateAgentStoreByStoreId(Integer oldStoreId, Integer newStoreId, String storeName) {
		agentMasterDao.updateAgentStoreByStoreId(oldStoreId,newStoreId,storeName);
		
	}

	public void updateAgentCompanyByCompanyId(Integer oldCompanyId, Integer newCompanyId, String companyName) {
		agentMasterDao.updateAgentCompanyByCompanyId(oldCompanyId,newCompanyId,companyName);
		
	}

	public List<Integer> selectAgentsByCompanyId(Integer oldCompanyId) {
		return agentMasterDao.selectAgentsByCompanyId(oldCompanyId);
	}
}
