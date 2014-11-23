package com.nuoshi.console.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.nuoshi.console.common.Globals;
import com.nuoshi.console.common.Resources;
import com.nuoshi.console.dao.AuditDao;
import com.nuoshi.console.dao.WenDaDao;
import com.nuoshi.console.domain.audit.WenDaAuditTask;
import com.nuoshi.console.domain.user.TFUser;
import com.nuoshi.console.domain.wenda.Category;

@Service
public class WenDaVerifyService  extends BaseService{
	private Log log = LogFactory.getLog(WenDaVerifyService.class);
	@Resource
	private WenDaDao wenDaDao;
	@Resource
	private AuditDao auditDao;
	@Resource
	private MessageService messageService;

	/**
	 * 获取符合要求的问答id
	 * 
	 * @param auditSign
	 * @param num
	 * @param cityId
	 * @param status
	 * @param type
	 * @return
	 * @author wangjh Mar 1, 2012 6:50:51 PM
	 */
	public List<Integer> getIdForAudit(Integer num, Integer cityId, Integer status, Integer type) {
		return wenDaDao.getIdForAudit(num, cityId, status, type);
	}

	public void sign(String auditSign, List<Integer> idList, int type) {
		wenDaDao.sign(auditSign, idList, type);
	}

	public List<WenDaAuditTask> getWenDaInfo(List<Integer> idList, Integer type) {
		return wenDaDao.getWenDaInfo(idList, type);
	}

	/**
	 * 获取问答审核列表
	 * @param auditUserId
	 * @param type
	 * @param cityId
	 * @param num
	 * @return
	 * @author wangjh
	 * May 3, 2012 5:28:40 PM
	 */
	public List<WenDaAuditTask> getAuditTask(Integer auditUserId, Integer type, Integer cityId, Integer num) {
		List<Integer> idList = auditDao.getWenDaIdList(auditUserId, cityId, num, type);
		List<WenDaAuditTask> taskInfoList = new ArrayList<WenDaAuditTask>();
		if (CollectionUtils.isEmpty(idList)) {
			return taskInfoList;
		}
		taskInfoList = wenDaDao.getWenDaInfo(idList, type);
		
		if (CollectionUtils.isEmpty(taskInfoList) || taskInfoList.size() < idList.size()) {
			List<Integer> ids=new ArrayList<Integer>();
			for (WenDaAuditTask task : taskInfoList) {
				ids.add(task.getId());
			}
			List<Integer> noExistBeanId = new ArrayList<Integer>();
			for (Integer id : idList) {
				if (!ids.contains(id)) {
					noExistBeanId.add(id);
				}
			}
			if(CollectionUtils.isNotEmpty(noExistBeanId)){
				log.info("删除任务列表存在，但是问答数据库中不存在的！");
				auditDao.deleteWenDaTask(noExistBeanId, type);
			}
		}
		return taskInfoList;
	}
	

	/**
	 * 处理问答不通过的
	 * 
	 * @param rejectIdList
	 * @param type
	 * @author wangjh Mar 2, 2012 6:25:30 PM
	 */
	public void reject(List<Integer> rejectIdList, Integer type) {
		// 更改状态并清空审核状态
		Integer status = -1;
		if (Globals.WEN_DA_TYPE_QUESTION == type.intValue()) {
			status = Globals.QUESTION_STATUS_DELETE;
		}
		if (Globals.WEN_DA_TYPE_ANSWER == type.intValue()) {
			status = Globals.ANSWER_STATUS_DELETE;
		}
		boolean flog = true;
		wenDaDao.updateStatus(rejectIdList, status,null, type, flog);
		List<WenDaAuditTask> wenDaList = wenDaDao.getWenDaInfo(rejectIdList, type);
		String content = "";
		TFUser users = new TFUser();
		if (Globals.WEN_DA_TYPE_QUESTION == type.intValue()) {
			// 更新问题的答案数量
			recountAnswerForQuestion(rejectIdList);
			for (WenDaAuditTask task : wenDaList) {
				users.setId(task.getAuthorId());
				if (task == null || task.getAuthorId() == null) {
					continue;
				}
				String title = "淘房问答--问题违规通知";
				content = task.getTitle();
				//发送站内信
				String questionContent = "尊敬的用户，你好：<br/>你的问题【" + content
						+ "】审核未通过，已被管理员删除，特此通知。<br/><br/>淘房网运营中心<br/>客服电话：010-51389757";
				this.sendMessage(users, title, questionContent);
			}
			// 更新分类中的问题数量
		}
		if (Globals.WEN_DA_TYPE_ANSWER == type.intValue()) {
			for (WenDaAuditTask task : wenDaList) {
				users.setId(task.getAuthorId());
				if (task == null || task.getAuthorId() == null) {
					continue;
				}
				String title = "淘房问答--回答违规通知";
				content = task.getTitle();
				String answerContent = "尊敬的用户，你好：<br/>你对【" + content
						+ "】问题的回答审核未通过，已被管理员删除，特此通知。<br/><br/>淘房网运营中心<br/>客服电话：010-51389757";
				this.sendMessage(users, title, answerContent);
			}
		}

	}

	private void sendMessage(TFUser user, String title, String message) {
		try {
			// 问答审核不通过发站内信
			messageService.sendMessageSys(title, message, user);
		} catch (Exception e) {
			log.error("站内信发送失败！" + e.getMessage());
		}
	}

	/**
	 * 重新统计问题的答案数量
	 * 
	 * @param questionId
	 * @author wangjh Feb 29, 2012 11:02:47 AM
	 */
	public void recountAnswerForQuestion(List<Integer> questionId) {
		// 统计问题的数量--除了删除的
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(Globals.QUESTION_STATUS_PASS);
		statusList.add(Globals.QUESTION_STATUS_WAIT_VERIFY);
		wenDaDao.recountAnswerForQuestion(questionId, statusList);
	}

	/**
	 * 处理问答通过的
	 * 
	 * @param passIdList
	 * @param type
	 * @author wangjh Mar 2, 2012 6:25:50 PM
	 */
	public void pass(List<Integer> passIdList, Integer type) {
		// 更改状态并清空审核状态
		Integer status = -1;
		if (Globals.WEN_DA_TYPE_QUESTION == type.intValue()) {
			status = Globals.QUESTION_STATUS_PASS;
		}
		if (Globals.WEN_DA_TYPE_ANSWER == type.intValue()) {
			status = Globals.ANSWER_STATUS_PASS;
		}
		boolean flog = true;
		wenDaDao.updateStatus(passIdList, status,null, type, flog);
		if (Globals.WEN_DA_TYPE_QUESTION == type.intValue()) {
			this.callAddFeed(passIdList);
		}
	}

	/**
	 * 修改问题分类（没有二级分类 则将二级分类设置为null）
	 * @param quesionId
	 * @param category
	 * @author wangjh
	 * Mar 5, 2012 4:17:34 PM
	 */
	public void changeQuestionCategory(Integer quesionId, Category category) {
		wenDaDao.updateQuestionCategory(quesionId, category);
	}

	/**
	 * 获取审核剩余数量
	 * @return
	 * @author wangjh
	 * May 3, 2012 4:15:04 PM
	 */
	public int getRemainingNumByType(int type) {
		List<Integer> list=new ArrayList<Integer>();
		if(Globals.WEN_DA_TYPE_QUESTION==type){
			list.add(Globals.QUESTION_STATUS_WAIT_VERIFY);
			return getQuestionCountByStatus(list,null);
		}else if(Globals.WEN_DA_TYPE_ANSWER==type){
			list.add(Globals.ANSWER_STATUS_WAIT_VERIFY);
			return getAnswerCountByStatus(list);
		}
		return 0;
	}


	public int getQuestionCountByStatus(List<Integer> statusList, List<Integer> solvingStatusList) {
		return wenDaDao.getQuestionCountByStatus(statusList, solvingStatusList);
	}

	public int getAnswerCountByStatus(List<Integer> statusList) {
		return wenDaDao.getAnswerCountByStatus(statusList);
	}
	/**
	 * 拉黑或取消拉黑 用户的问题和回答
	 * @param trim
	 * @author wangjh
	 * Aug 23, 2012 3:27:05 PM
	 */
	private void changeBlack(int userId,int type) {
		wenDaDao.changeQuestionBlack(userId,type);
		wenDaDao.changeAnswerBlack(userId,type);
	}
	
	/**
	 * 拉黑用户所有的问题和答案
	 * @param userId
	 * @param type
	 * @throws Exception
	 * @author wangjh
	 * Oct 30, 2012 6:40:22 PM
	 */
	public void pullBlack(int userId, int type) throws Exception {
		try {
			this.changeBlack(userId, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 重新审核问答
	 * @param type
	 * @param id
	 * @throws Exception
	 * @author wangjh
	 * Oct 31, 2012 7:14:54 PM
	 */
	public void reaudit(Integer type,Integer id) throws Exception{
		if(type==null||id==null){
			throw new Exception("重新审核出错！未知参数。");
		}
		List<Integer> list=null;
		try{
			if(Globals.WEN_DA_TYPE_QUESTION==type){
				list=new ArrayList<Integer>();
				list.add(id);
				wenDaDao.reauditQuestion(list);
			}else if(Globals.WEN_DA_TYPE_ANSWER==type){
				list=new ArrayList<Integer>();
				list.add(id);
				wenDaDao.reauditAnswer(list);
			}else{
				throw new Exception("重新审核出错！未知参数。");
			}
		}catch (Exception e) {
			throw new Exception("重新审核出错！");
		}
	}

	
	/**
	 * 审核通过向uc中添加feed
	 * @author wangjh
	 */
	public void callAddFeed(List<Integer> idList) {
		if (CollectionUtils.isEmpty(idList)) {
			return;
		}
		try {
			Gson gson = new Gson();
			String idStr = gson.toJson(idList);
			StringBuffer wenDaUrl = new StringBuffer();
			wenDaUrl.append(Resources.getString("sys.url.wenda") + "/detail/question/addfeed");
			callService(wenDaUrl.toString(), idStr);
		} catch (Exception e) {
			log.error("审核通过后添加feed失败。");
		}
	}
}
