package com.nuoshi.console.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.auditHistory.AuditHistory;
import com.nuoshi.console.domain.auditHistory.AuditorInfo;
import com.nuoshi.console.domain.auditHistory.HistoryInfo;
import com.nuoshi.console.domain.auditHistory.NewSubAuditHistory;
import com.nuoshi.console.domain.auditHistory.ReauditHistory;
import com.nuoshi.console.domain.auditHistory.SubAuditHistory;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.persistence.read.admin.auditHistory.AuditHistoryReadMapper;
import com.nuoshi.console.persistence.write.admin.auditHistory.AuditHistoryWriteMapper;

/**
 * <b>www.taofang.com</b> <b>function:</b>
 * 
 * @author lizhenmin
 * @createDate 2011 Jul 21, 2011 2:01:05 PM
 * @email lizm@taofang.com
 * @version 1.0
 */
@Repository
public class AuditHistoryDao {
	private Log log = LogFactory.getLog(AuditHistoryDao.class);
	@Resource
	private AuditHistoryReadMapper auditHistoryReadMapper;

	@Resource
	private AuditHistoryWriteMapper auditHistoryWriteMapper;

	/**
	 * 保存审核历史
	 * 
	 * @param auditHistory
	 *            审核历史
	 * @author wangjh Dec 20, 2011 4:10:35 PM
	 */
	public Integer saveAuditHistory(AuditHistory auditHistory) {
		if (auditHistory == null) {
			return null;
		}
		Integer id = auditHistoryWriteMapper.saveAuditHistory(auditHistory);
		return id;
	}

	/**
	 * 更新历史记录
	 * 
	 * @param auditHistory
	 *            审核历史
	 * @author wangjh Dec 20, 2011 4:14:55 PM
	 */
	public void updateAuditHistory(AuditHistory auditHistory) {
		if (auditHistory != null && auditHistory.getId() >= 0) {
			auditHistoryWriteMapper.updateAuditHistory(auditHistory);
		} else {
			log.info("更新内容不存在。");
		}
	}

	public List<AuditHistory> getLastRecordByHouseIds(List<Integer> houseIdList, Integer houseType) {
		return auditHistoryReadMapper.getLastRecordByHouseIds(houseIdList, houseType);
	}

	public void updateAuditHistoryForResult(List<AuditHistory> auditHistoryList, Integer result, Date dates) {
		auditHistoryWriteMapper.updateAuditHistoryForResult(auditHistoryList, result, dates);
	}

	/**
	 * 获取没有审核记录的房源id
	 * 
	 * @param auditHistoryList
	 * @return
	 * @author wangjh Dec 21, 2011 1:42:04 PM
	 */
	public List<Integer> getHasRecordIds(List<Integer> houseIdList, Integer type) {
		return auditHistoryReadMapper.getHasRecordIds(houseIdList, type);

	}

	/**
	 * @param subAuditHistory
	 * @date 2011-12-21上午10:29:32
	 * @添加审核字表记录
	 */
	public void insertSub(SubAuditHistory subAuditHistory) {
		if(subAuditHistory.getHouseDescribe()!=null && subAuditHistory.getHouseDescribe().length()>19000){
			subAuditHistory.setHouseDescribe(subAuditHistory.getHouseDescribe().substring(0, 18000));
		}
		auditHistoryWriteMapper.insertSub(subAuditHistory);
	}

	/**
	 * 得到审核历史记录
	 * @return
	 * @date 2011-12-22上午10:31:06
	 * @author dongyj
	 * @param params 查询参数
	 */
	public List<SubAuditHistory> getSubListByPage(HashMap<String, Object> params) {
		return auditHistoryReadMapper.getSubListByPage(params);
	}

	public List<HistoryInfo> getHistoryInfoListByPage(HashMap<String, Object> params) {
		return auditHistoryReadMapper.getHistoryInfoListByPage(params);
	}

	public HistoryInfo selectHistoryInfoById(Integer subHistoryId) {
		return auditHistoryReadMapper.selectHistoryInfoById(subHistoryId);

	}

	public void saveReauditHistory(ReauditHistory reauditHistory) {
		auditHistoryWriteMapper.saveReauditHistory(reauditHistory);
	}

	public Integer getReauditHistoryCount(Integer subHistoryId) {
		return auditHistoryReadMapper.getReauditHistoryCount(subHistoryId);
	}

	public void deleteReauditHistory(Integer subHistoryId) {
		auditHistoryWriteMapper.deleteReauditHistory(subHistoryId);
	}

	public Integer processedAuditCount(Integer auditStep, Integer auditUserId, Integer houseType, Date beginTime, Date endTime) {
		return auditHistoryReadMapper.processedAuditCount(auditStep, auditUserId, houseType, beginTime, endTime);
	}

	public Integer badHouseAuditNumber(Integer auditStep, Integer requesterId, Integer houseType) {
		return auditHistoryReadMapper.badHouseAuditNumber(auditStep, requesterId, houseType);
	}

	public List<AuditorInfo> getTotalBadRateByUsers(List<User> users, Date beginTime, Date endTime) {
		return auditHistoryReadMapper.getTotalBadRateByUsers(users, beginTime, endTime);
	}
	public List<AuditorInfo> getNewBadRateByUsers(List<User> users, Date beginTime, Date endTime) {
		return auditHistoryReadMapper.getNewBadRateByUsers(users, beginTime, endTime);
	}
	public List<AuditorInfo> getPhotoBadRateByUsers(List<User> users, Date beginTime, Date endTime) {
		return auditHistoryReadMapper.getPhotoBadRateByUsers(users, beginTime, endTime);
	}
	
	/** 取审核人的错误审核信息 */
	public List<HistoryInfo> getErrorAuditByUser(int userId, String beginTime, String endTime) {
		return auditHistoryReadMapper.getErrorAuditByUser(userId, beginTime, endTime);
	}
	
	public List<HistoryInfo> getErrorAuditPhotoByUser(int userId, String beginTime, String endTime) {
		return auditHistoryReadMapper.getErrorAuditPhotoByUser(userId, beginTime, endTime);
	}
	
	public List<AuditorInfo> getEveryCountByUsers(Integer auditStep, List<User> users, Date beginTime, Date endTime) {
		return auditHistoryReadMapper.getEveryCountByUsers(auditStep, users, beginTime, endTime);
	}
	public List<AuditorInfo> getEachForHousePhoto(Integer auditStep, 
			List<User> users, Date beginTime, Date endTime) {
		return auditHistoryReadMapper.getEachForHousePhoto(auditStep, users,beginTime, endTime);
	}
	
	public List<AuditorInfo> getEachForHousePhotoNew(Integer auditStep, 
			List<User> users, Date beginTime, Date endTime) {
		return auditHistoryReadMapper.getEachForHousePhotoNew(auditStep, users,beginTime, endTime);
	}

	public HistoryInfo getPhotoInfoHistoryForAgent(HistoryInfo historyInfo) {
		return auditHistoryReadMapper.getPhotoInfoHistoryForAgent(historyInfo);
	}

	public HistoryInfo getHistoryInfoBySubId(int parseInt) {
		return auditHistoryReadMapper.getHistoryInfoBySubId(parseInt);
	}

	public int insertNewSubAuditHistory(NewSubAuditHistory newSubAuditHistory) {
		return auditHistoryWriteMapper.insertNewSubAuditHistory(newSubAuditHistory);
	}

	public void updateAuditHistoryAtSubIds(String id, List<String> sub_ids, Integer house_type) {
		auditHistoryWriteMapper.updateAuditHistoryAtSubIds(id, sub_ids, house_type);
	}

	public List<NewSubAuditHistory> getNewSubHistoryWizoutPhotoCount() {
		return auditHistoryReadMapper.getNewSubHistoryWizoutPhotoCount();
	}

	public void updateNewSubHistoryPhotoCount(List<NewSubAuditHistory> toUpdateList) {
		if (CollectionUtils.isNotEmpty(toUpdateList)) {
			for (NewSubAuditHistory newSubAuditHistory : toUpdateList) {
				auditHistoryWriteMapper.updateNewSubHistoryPhotoCount(newSubAuditHistory);
			}
		}
	}

	public List<HistoryInfo> getNewPhotoHstryListByPage(HashMap<String, Object> params) {
		List<HistoryInfo> newSubAuditHistories = auditHistoryReadMapper.getNewPhotoHstryListByPage(params);
		return newSubAuditHistories;
	}
	
	public List<HistoryInfo> getNewBaseInfoListByPage(HashMap<String, Object> params) {
		List<HistoryInfo> newSubAuditHistories = auditHistoryReadMapper.getNewBaseInfoListByPage(params);
		return newSubAuditHistories;
	}

	public String getSubIdsFromAuditHstryByHouseIdOrAuthor(HashMap<String, Object> params) {
		List<String> sub_ids = auditHistoryReadMapper.getSubIdsFromAuditHstryByHouseIdOrAuthor(params);
		
		// 得到所有字表的主键id,连接成字符串 1,2,3(最多3个id,因为3种图片类型)
		StringBuffer ids = new StringBuffer();
		if (CollectionUtils.isNotEmpty(sub_ids)) {
			for (String string : sub_ids) {
				if (StringUtils.isNotBlank(string)) {
					ids.append(string);
				}
			}
			if (ids.length() > 1) {
				ids.deleteCharAt(ids.length() - 1);
			}
		}
		return ids.toString();
	}

	public List<Integer> getNewPassedPhotoCount(Integer auditStep, Integer auditUserId, Integer houseType, Date beginTime, Date endTime) {
		return auditHistoryReadMapper.getNewPassedPhotoCount(auditStep,auditUserId,houseType,beginTime,endTime);
	}

	public List<SubAuditHistory> getHouseIdsByAuditIds(List<String> auditIdList) {
		return auditHistoryReadMapper.getHouseIdsByAuditIds(auditIdList);
	}

	public NewSubAuditHistory getNewSubAuditHistory(int id) {
		return auditHistoryReadMapper.getNewSubAuditHistory(id);
	}

	public boolean getReauditHistoryCountNew(int id, int photoId) {
		if (auditHistoryReadMapper.getReauditHistoryCountNew(id, photoId) < 1) {
			return false;
		} else {
			return true;
		}
	}

	public void deleteReauditHistoryNew(int id, int photoId) {
		auditHistoryWriteMapper.deleteReauditHistoryNew(id,photoId);
	}

	public void addReauditHistoryNew(ReauditHistory reauditHistory) {
		auditHistoryWriteMapper.addReauditHistoryNew(reauditHistory);
	}
	public List<HistoryInfo> getBaseinfoLastAuditLogByHouse(Integer id, int houseType) {
		return auditHistoryReadMapper.getBaseinfoLastAuditLogByHouse(id,houseType);
	}


	public int getReaudithistoryCount(SubAuditHistory sah,int type) {
		return auditHistoryReadMapper.getReaudithistoryCount(sah,type);
	}

	public void deleteReauditHistoryByLogId(HistoryInfo sah) {
		auditHistoryWriteMapper.deleteReauditHistoryByLogId(sah);
	}

	public List<HistoryInfo> getReAuditHistory(int houseId, int houseType) {
		return auditHistoryReadMapper.getReAuditHistory(houseId,houseType);
	}

}
