package com.nuoshi.console.persistence.read.admin.auditHistory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.auditHistory.AuditHistory;
import com.nuoshi.console.domain.auditHistory.AuditorInfo;
import com.nuoshi.console.domain.auditHistory.HistoryInfo;
import com.nuoshi.console.domain.auditHistory.NewSubAuditHistory;
import com.nuoshi.console.domain.auditHistory.SubAuditHistory;
import com.nuoshi.console.domain.stat.HouseQuality;
import com.nuoshi.console.domain.user.User;

public interface AuditHistoryReadMapper {

	public List<AuditHistory> getLastRecordByHouseIds(
			@Param("houseIdList")List<Integer> houseIdList, @Param("houseType")Integer houseType);
	public List<AuditHistory> selectById();
	public List<Integer> getHasRecordIds(@Param("houseIdList")List<Integer> houseIdList,
			@Param("houseType")Integer houseType);
	/**
	 * 得到审核历史记录
	 * @return
	 * @date 2011-12-22上午10:32:21
	 * @author dongyj
	 * @param params 查询参数
	 */
	public List<SubAuditHistory> getSubListByPage(HashMap<String,Object> params);
	
	public List<HistoryInfo> getHistoryInfoListByPage(
			HashMap<String, Object> params);
	/**
	 * 通过记录表id查找审核历史信息
	 * @param id
	 * @return
	 * @author wangjh
	 * Dec 22, 2011 5:16:24 PM
	 */
	public HistoryInfo selectHistoryInfoById(Integer id);
	public Integer getReauditHistoryCount(@Param("subHistoryId")Integer subHistoryId);
	public Integer processedAuditCount(@Param("auditStep")Integer auditStep,@Param("auditUserId")Integer auditUserId,@Param("houseType")Integer houseType, @Param("beginTime")Date beginTime,
			@Param("endTime")Date endTime);
	public Integer badHouseAuditNumber(@Param("auditStep")Integer auditStep,@Param("requesterId")Integer requesterId,@Param("houseType")Integer houseType);
	public List<AuditorInfo> getTotalBadRateByUsers(@Param("userList")List<User> userList,
			@Param("beginTime")Date beginTime, @Param("endTime")Date endTime);
	public List<AuditorInfo> getNewBadRateByUsers(@Param("userList")List<User> userList,
			@Param("beginTime")Date beginTime, @Param("endTime")Date endTime);
	public List<AuditorInfo> getPhotoBadRateByUsers(@Param("userList")List<User> userList,@Param("beginTime")Date beginTime, @Param("endTime")Date endTime);
	
	public List<HistoryInfo> getErrorAuditByUser(@Param("id")int id,@Param("beginTime")String beginTime, @Param("endTime")String endTime);
	
	public List<HistoryInfo> getErrorAuditPhotoByUser(@Param("id")int id,@Param("beginTime")String beginTime, @Param("endTime")String endTime);	
	
	public List<AuditorInfo> getEveryCountByUsers(@Param("auditStep")Integer auditStep,
			@Param("userList")List<User> userList, @Param("beginTime")Date beginTime,  @Param("endTime")Date endTime);
	public List<AuditorInfo> getEachForHousePhoto(@Param("auditStep")Integer auditStep,
			@Param("userList")List<User> userList, @Param("beginTime")Date beginTime,  @Param("endTime")Date endTime);
	public List<AuditorInfo> getEachForHousePhotoNew(@Param("auditStep")Integer auditStep,
			@Param("userList")List<User> userList, @Param("beginTime")Date beginTime,  @Param("endTime")Date endTime);
	public HistoryInfo getPhotoInfoHistoryForAgent(HistoryInfo historyInfo);
	public HistoryInfo getHistoryInfoBySubId(@Param("parseInt")int parseInt);
	public List<NewSubAuditHistory> getNewSubHistoryWizoutPhotoCount();
	public List<HistoryInfo> getNewPhotoHstryListByPage(HashMap<String, Object> params);
	public List<HistoryInfo> getNewBaseInfoListByPage(HashMap<String, Object> params);
	public List<String> getSubIdsFromAuditHstryByHouseIdOrAuthor(HashMap<String, Object> params);
	public List<Integer> getNewPassedPhotoCount(@Param("auditStep") Integer auditStep, @Param("auditUserId") Integer auditUserId, @Param("houseType") Integer houseType,
			@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
	public List<SubAuditHistory> getHouseIdsByAuditIds(@Param("auditIdList")List<String> auditIdList);
	public NewSubAuditHistory getNewSubAuditHistory(@Param("id")int id);
	public int getReauditHistoryCountNew(@Param("id")int id, @Param("photoId")int photoId);
	
	public List<HouseQuality> getAuditHouseQualityList(HouseQuality hq);
	public List<HouseQuality> getAuditHouseQualityOtherList(HouseQuality hq);
	
	public List<HouseQuality> getAuditHouseQualityHistoryList(@Param("hq")HouseQuality hq,@Param("name")String name);
	public List<HouseQuality> getAuditHouseQualityHistoryOtherList(@Param("hq")HouseQuality hq,@Param("name")String name);
	public List<HistoryInfo> getBaseinfoLastAuditLogByHouse(@Param("houseId")Integer id, @Param("houseType")int houseType);
	public int getReaudithistoryCount(@Param("sah")SubAuditHistory sah,@Param("auditresult")int type);
	public List<HistoryInfo> getReAuditHistory(@Param("houseId")int houseId, @Param("houseType")int houseType);
}