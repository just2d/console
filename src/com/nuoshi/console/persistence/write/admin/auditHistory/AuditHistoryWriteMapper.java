package com.nuoshi.console.persistence.write.admin.auditHistory;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.auditHistory.AuditHistory;
import com.nuoshi.console.domain.auditHistory.HistoryInfo;
import com.nuoshi.console.domain.auditHistory.NewSubAuditHistory;
import com.nuoshi.console.domain.auditHistory.ReauditHistory;
import com.nuoshi.console.domain.auditHistory.SubAuditHistory;
import com.nuoshi.console.domain.stat.HouseQuality;

public interface AuditHistoryWriteMapper {

	public Integer saveAuditHistory(AuditHistory auditHistory);

	public void updateAuditHistory(AuditHistory auditHistory);

	public void updateAuditHistoryForResult(@Param("auditHistoryList") List<AuditHistory> auditHistoryList, @Param("auditResult") Integer auditResult,
			@Param("effectTime") Date effectTime);

	/**
	 * @param subAuditHistory
	 * @date 2011-12-21上午10:30:02
	 * @添加审核字表
	 */
	public void insertSub(SubAuditHistory subAuditHistory);

	public void saveReauditHistory(ReauditHistory reauditHistory);

	public void deleteReauditHistory(@Param("subHistoryId")Integer subHistoryId);

	public int insertNewSubAuditHistory(NewSubAuditHistory newSubAuditHistory);

	public void updateAuditHistoryAtSubIds(@Param("id")String id, @Param("sub_ids")List<String> sub_ids, @Param("house_type")Integer house_type);

	public void updateNewSubHistoryPhotoCount(NewSubAuditHistory newSubAuditHistory);

	public void updateNewSubHistoryPhotoCountMap(Map<String, String> map);

	public void deleteReauditHistoryNew(@Param("id")int id, @Param("photoId")int photoId);

	public void addReauditHistoryNew(ReauditHistory reauditHistory);
	
	public void addAuditHouseQuality(@Param("list")List<HouseQuality> list, @Param("userName")String userName);
	
	public void updateAuditHouseQualityUsingFlag(@Param("list")List<HouseQuality> list, @Param("userName")String userName);
	
	public int deleteAuditHouseQuality(@Param("ts")Timestamp ts);
	public int clearAuditHistoryUsingFlag(@Param("ts")Timestamp ts);

	public int deleteReauditHistoryByLogId(HistoryInfo sah);
}