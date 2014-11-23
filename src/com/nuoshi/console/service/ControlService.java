package com.nuoshi.console.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.nuoshi.console.common.Resources;
import com.nuoshi.console.common.util.SmcConst;
import com.nuoshi.console.dao.ControlDao;
import com.nuoshi.console.domain.agent.AgentMaster;
import com.nuoshi.console.domain.control.BlackList;
import com.nuoshi.console.domain.control.BlackListCondition;
import com.nuoshi.console.domain.control.SensitiveWord;
import com.nuoshi.console.domain.user.TFUser;
import com.nuoshi.console.domain.user.User;

/**
 * @author wanghongmeng
 * 
 */
@Service
public class ControlService extends BaseService {
	@Resource
	private ControlDao controlDao;
	@Resource
	private AgentMasterService agentMasterService;
	@Resource
	private WenDaVerifyService wenDaVerifyService;
	@Resource
	private MessageService messageService;

	/**
	 * @param blackList
	 *            查询条件
	 * @return 符合查询条件的黑名单集合
	 */
	public List<BlackList> getAllBlackListByPage(BlackListCondition condition) {
		List<String> conditionList = new ArrayList<String>();
		if (condition != null) {
			if (StringUtils.isNotBlank(condition.getUserType())) {
				conditionList.add(" user_type = " + condition.getUserType());
			}
			if (StringUtils.isNotBlank(condition.getReason())&&!SmcConst.CONTROL_SEARCH_REASON_TXT_NULL.equals(condition.getReason())) {
				conditionList.add(" reason like '%" + condition.getReason()+"%'");
			}
			if (StringUtils.isNotBlank(condition.getComments()) && !SmcConst.CONTROL_SEARCH_COMMENTS_TXT_NULL.equals(condition.getComments())) {
				conditionList.add(" comments like '%" + condition.getComments()	+ "%'");
			}
			if(StringUtils.isNotBlank(condition.getId())){
				conditionList.add(" id= " + condition.getId());
			}
			if(StringUtils.isNotBlank(condition.getBlackUserId())){
				conditionList.add(" black_user_id= " + condition.getBlackUserId());
			}
			if(StringUtils.isNotBlank(condition.getBlackUserName())){
				conditionList.add(" black_user_name= '" + condition.getBlackUserName()+"' ");
			}
			if(StringUtils.isNotBlank(condition.getListType())){
				conditionList.add(" list_type= " + condition.getListType());
			}
			if(StringUtils.isNotBlank(condition.getLoginName())){
				conditionList.add(" login_name= '" + condition.getLoginName()+"' ");
			}
			if(StringUtils.isNotBlank(condition.getStatus())){
				conditionList.add(" status= " + condition.getStatus() );
			}
		}
		if (conditionList.size() == 0) {
			conditionList = null;
		}
		return controlDao.getAllBlackListByPage(conditionList);
	}

	/**
	 * @param loginName 登录名或手机号
	 * @param listType  列表类型（经纪人，问答）
	 * @return 该用户记录的id
	 */
	public boolean isUserExistByLoginName(String loginName,int listType) {
		int result = controlDao.isUserExistByLoginName(loginName,listType);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * @param id 用户id
	 * @return 
	 */
	public boolean isUserExistByUserId(int userId,int listType) {
		int result = controlDao.isUserExistByUserId(userId,listType);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param mobile
	 *            用户电话
	 * @return 该用户的角色
	 */
	public Integer getUserRole(String mobile) {
		Integer role = controlDao.getUserRole(mobile);
		if (null == role) {
			return SmcConst.USER_ROLE_NOT_REGISTER;
		} else {
			if (role == SmcConst.USER_ROLE_NORMAL) {
				return SmcConst.USER_ROLE_NORMAL;
			} else {
				return SmcConst.USER_ROLE_AGENT;
			}
		}
	}

	/**
	 * @param blackList
	 *            要添加的黑名单对象
	 */
	private void addBlack(BlackList blackList) {
		controlDao.addBlack(blackList);
	}
	
	/**
	 * 添加黑白名单
	 * @param blackList
	 * @return
	 * @author wangjh
	 * Aug 21, 2012 4:08:56 PM
	 */
	public String  addBlackUser(BlackList blackList,User user){
		String msg="";
		try{
		List<AgentMaster> agent=this.getUserInfo(blackList);
		if (CollectionUtils.isEmpty(agent)) {
			msg = "用户不存在！";
			if(StringUtils.isNotBlank(blackList.getLoginName())){
				msg+="帐号为：'"+blackList.getLoginName()+"'。  ";
			}
			if(blackList.getBlackUserId()>0){
				msg+="用户ID为：'"+blackList.getBlackUserId()+"'。  ";
			}
		}else if(agent.size()>1){
			msg="添加出错，查询到多个结果。";
		} else{
			AgentMaster agentMaster=agent.get(0);
			BlackListCondition condition=new BlackListCondition();
			condition.setBlackUserId(""+agentMaster.getAgentId());
			condition.setListType(""+blackList.getListType());
			List<BlackList> black= this.getAllBlackListByPage(condition);
			if(CollectionUtils.isNotEmpty(black)){
				msg = Resources.getString("blacklist.add.exist");
			}else{
				blackList.setLoginName(agentMaster.getUserName());
				blackList.setBlackUserName(agentMaster.getName());
				blackList.setBlackUserId(agentMaster.getAgentId());
				java.util.Date date=new java.util.Date();
				blackList.setEntryDate(date);
				blackList.setLastEntryId(user.getId());
				blackList.setLastEntryName(user.getChnName());
				blackList.setUpdateDate(date);
				blackList.setUserType(agentMaster.getRole());
				this.addBlack(blackList);
				//更新该用户问题和答案的状态
				
				wenDaVerifyService.pullBlack(agentMaster.getAgentId(), SmcConst.BLACK_TYPE_BLACK);
				if(blackList.getListType()==SmcConst.USER_BLACKLIST_LIST_TYPE_WENDA){
					this.sendMessage(agentMaster.getAgentId(), agentMaster.getUserName());
				}
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
			return "添加黑名单失败！";
		}
		return msg;
	}

	/**
	 * @param blackList
	 *            要更新的黑名单对象
	 */
	public void updateBlack(BlackList blackList) {
		controlDao.updateBlack(blackList);
		if(blackList!=null){
			int userId=blackList.getBlackUserId();
			int type=blackList.getStatus();
			//更新该用户问题和答案的状态
			try {
				if(type==SmcConst.USER_BLACKLIST_INVALID){
					wenDaVerifyService.pullBlack(userId,SmcConst.BLACK_TYPE_UNBLACK);
				}
				if(type==SmcConst.USER_BLACKLIST_VALID){
					wenDaVerifyService.pullBlack(userId,SmcConst.BLACK_TYPE_BLACK);
					this.sendMessage(userId, blackList.getLoginName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param id
	 *            要删除的黑名单id
	 */
	public void deleteBlack(int id) {
		BlackListCondition condition=new BlackListCondition();
		condition.setId(""+id);
		List<BlackList> list=this.getAllBlackListByPage(condition);
		BlackList blackList=null;
		if(CollectionUtils.isNotEmpty(list)){
			blackList=list.get(0);
		}
		try {
			wenDaVerifyService.pullBlack(blackList.getBlackUserId(), SmcConst.BLACK_TYPE_UNBLACK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		controlDao.deleteBlack(id);
	}

	/**
	 * @param sensitiveWord
	 *            查询条件
	 * @return 符合查询条件的关键词集合
	 */
	public List<SensitiveWord> getAllSensitiveWordByPage(
			SensitiveWord sensitiveWord) {
		List<String> conditionList = new ArrayList<String>();
		if (sensitiveWord != null) {
			if (SmcConst.CONTROL_SEARCH_SELECT_NULL != sensitiveWord.getType()) {
				conditionList.add(" word_type = " + sensitiveWord.getType());
			}
			if (SmcConst.CONTROL_SEARCH_SELECT_NULL != sensitiveWord
					.getSensitiveWordType()) {
				conditionList.add(" sensitive_word_type = "
						+ sensitiveWord.getSensitiveWordType());
			}
			if (!"".equals(sensitiveWord.getContent())
					&& !SmcConst.CONTROL_SEARCH_COMMENTS_TXT_NULL.equals(sensitiveWord
							.getContent())) {
				conditionList.add(" content like '%"
						+ sensitiveWord.getContent() + "%'");
			}
			if(sensitiveWord.getUserType() > -1) {
				conditionList.add(" user_type = " + sensitiveWord.getUserType());
			}
		}
		if (conditionList.size() == 0) {
			conditionList = null;
		}
		return controlDao.getAllSensitiveWordByPage(conditionList);
	}

	/**
	 * @param sensitiveWord
	 *            要添加的关键词对象
	 */
	public void addSensitiveWord(SensitiveWord sensitiveWord) {
		controlDao.addSensitiveWord(sensitiveWord);
	}

	/**
	 * @param sensitiveWord
	 *            要更新的关键词对象
	 */
	public void updateSensitiveWord(SensitiveWord sensitiveWord) {
		controlDao.updateSensitiveWord(sensitiveWord);
	}

	/**
	 * @param id
	 *            要删除的关键词id
	 */
	public void deleteSensitiveWord(int id) {
		controlDao.deleteSensitiveWord(id);
	}

	/**
	 * @param mobile
	 *            经纪人电话
	 * @return 经纪人的id
	 */
	public Integer getAgentIdByMobile(String mobile) {
		return controlDao.getAgentIdByMobile(mobile);
	}
	
	/**
	 * 获取用户信息
	 * @param blackList
	 * @return
	 * @author wangjh
	 * Aug 21, 2012 3:28:56 PM
	 */
	public List<AgentMaster> getUserInfo(BlackList blackList){
		List<String> conditions = new ArrayList<String>();
		if(StringUtils.isNotBlank(blackList.getLoginName())){
			conditions.add(" u.user_name = '" + blackList.getLoginName().trim()+"' ");
		}
		if(blackList.getBlackUserId()>0){
			conditions.add(" u.id = " + blackList.getBlackUserId());
		}
		if(StringUtils.isNotBlank(blackList.getBlackUserName())){
			conditions.add(" u.name = '" + blackList.getBlackUserName().trim()+"' ");
		}
		return agentMasterService.getAgentMasterByConditions(conditions);
	}
	
	/**
	 * 发送站内信
	 * @param userId
	 * @param accounts
	 * @author wangjh
	 * Aug 23, 2012 5:56:26 PM
	 */
	private void sendMessage(int userId,String accounts){
		String title = "淘房问答--帐号违规通知";
		TFUser users = new TFUser();
		users.setId(userId);
		//发送站内信
		String content = 
		"尊敬的用户，你好： <br/>" +
		"你的账号（" +accounts +"）已被管理员冻结，取消淘房问答的所有行为权限，特此通知。 <br/><br/>" +
		"淘房网运营中心<br/>" +
		"客服电话：010-51389757 ";
		this.sendMessage(users, title, content);
	}
	
	private void sendMessage(TFUser user, String title, String message) {
		try {
			// 问答审核不通过发站内信
			messageService.sendMessageSys(title, message, user);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("站内信发送失败！" + e.getMessage());
		}
	}
}
