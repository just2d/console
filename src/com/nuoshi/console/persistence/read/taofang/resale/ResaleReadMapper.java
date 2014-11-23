package com.nuoshi.console.persistence.read.taofang.resale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.estate.UnionLog;
import com.nuoshi.console.domain.agent.HousePhoto;
import com.nuoshi.console.domain.audit.AuditVcrTask;
import com.nuoshi.console.domain.auditHistory.AuditHistory;
import com.nuoshi.console.domain.auditHistory.HistoryInfo;
import com.nuoshi.console.domain.auditHistory.SubAuditHistory;
import com.nuoshi.console.domain.base.House;
import com.nuoshi.console.domain.resale.Resale;
import com.nuoshi.console.domain.resale.ResaleInfo;
import com.nuoshi.console.domain.stat.AuditSearch;

@SuppressWarnings("rawtypes")
public interface ResaleReadMapper {
	public List<SubAuditHistory> getSubHistoryInfo(@Param("houseIdList") List<Integer> houseIdList);

	public List<ResaleInfo> getResale4VerifyByPage(HashMap params);

	public List<HashMap<String, Long>> selectResalePhotoCountByHouseId(HashMap params);

//	public List<HousePhoto> getHousePhoto(HashMap params);// 房源图片

	public List<HousePhoto> getAllHousePhoto(Integer houseId);

	public List<ResaleInfo> getAllVerifyResale(HashMap params);// 获取经纪人所有的房源

	public List<ResaleInfo> getAllResale4VerifyByPage(HashMap params);// 所有的房源信息

	public Resale selectResaleById(@Param("id") int id);
	
	public List<ResaleInfo> selectResaleInfoByIds(@Param("list") List<Integer> list);

	public List<Integer> getResaleIdForAudit(@Param("auditSign") String auditSign, @Param("auditStatus") Integer auditStatus,
			@Param("houseStatus") Integer houseStatus, @Param("num") Integer num, @Param("cityId") Integer cityId,@Param("vipFlag")String vipFlag);

	public List<ResaleInfo> getMyResaleInfo(@Param("houseIds") List<Integer> houseIds, @Param("type") Integer type);

	public List<HousePhoto> getAllHousePhotoByType(@Param("houseid") Integer houseid, @Param("photoid") Integer photoid,
			@Param("type") Integer type, @Param("inalbum") Integer inalbum);

	public List<ResaleInfo> getResaleFromAuditTaskByIdList(@Param("resaleInfoIdList") List<Integer> resaleInfoIdList,
			@Param("houseStatus") Integer houseStatus, @Param("auditStatus") Integer auditStatus);

	public List<ResaleInfo> getResaleInfoCity(@Param("resaleIdList") List<Integer> resaleIdList);

	/**
	 * 统计小区二手房数量
	 * 
	 * @param id
	 * @return
	 */
	public int countByEstateId(@Param("estateId") int id);

	/**
	 * 获取二手房审核列表中不符合审核条件的房源id
	 * 
	 * @param houseIds
	 * @return
	 * @author wangjh Dec 14, 2011 6:43:08 PM
	 */
	public List<Integer> getInconformityHouseIds(@Param("houseIds") List<Integer> houseIds);

	/**
	 * 图片数为0的houseId
	 */
	public List<Integer> getPhotoCountOByStep(@Param("auditStep") Integer auditStep, @Param("houseIdList") List<Integer> houseIdList);

	public List<ResaleInfo> getUpdateCoverImgHouseList(@Param("houseIdList") List<String> houseIdList);

	public ResaleInfo getOneCoverImg(@Param("houseid") Integer houseid, @Param("type") int type);

	public List<AuditHistory> getHistoryInfo(@Param("houseIdList") List<Integer> houseIdList, @Param("auditType") Integer auditType);

	public HistoryInfo getPhotoInfoHistory(HistoryInfo historyInfo);

	public List<SubAuditHistory> getResalePhotosByHouseListAndType(@Param("houseIdIntList") List<Integer> houseIdIntList,
			@Param("photoIdList") List<Integer> photoIdList, @Param("type") Integer type);

	public HistoryInfo getRejectPhotoReAudit(HistoryInfo historyInfo);

	public Integer getUnclaimedAwaitHouseAuditCount(@Param("houseStatus") Integer houseStatus, @Param("auditStatus") Integer auditStatus);

	public Integer getPhotoCountByHouseIdList(@Param("list") List<Integer> list, @Param("auditStep") Integer auditStep);

	public Integer getPhotoAuditUnclaimedAwaitCount(@Param("houseStatus") Integer houseStatus, @Param("auditStatus") Integer auditStatus,
			@Param("auditStep") Integer auditStep);

	/**
	 * 获得小区的所有二手房源
	 * 
	 * @param estateId
	 * @param targetId 合并后的小区id
	 * @param targetName 合并后的小区名称
	 * @return
	 */
	public List<UnionLog> getHouseByEstateId(Map paramMap);

	public List<HousePhoto> getAllPhotoInPhotos(@Param("list")List<Integer> inPhotoIntegers);

	public List<Resale> getResaleForScore(@Param("idList")List<Integer> idList,
			@Param("typeList")List<Integer> typeList);
	
	public List<House> getAuditVcrTask(@Param("cityId")int cityId, @Param("num")int num);


	public List<ResaleInfo> getPhotoExtraInfo(@Param("auditStep")int auditType, @Param("houseIdList")List<Integer> houseIdList);
	public List<AuditSearch> getAuditHouseCount(@Param("condition")String condition);
	public List<AuditSearch> getAuditCount(@Param("condition")String condition,@Param("listId")List<Integer> listId);
	
	public List<Integer> getUnauditPhotoCategory(Integer houseId);
	
	public List<Resale> getResaleForEvaluationByPage(@Param("conditions")List<String> conditions);

	public List<Resale> getResalePicModelByIds(List<Integer> houseIdIntList);

	public Resale getDecorationByPhotoId(@Param("photoId")Integer photoId);

	public House getAuditVcrTaskByHouseId(@Param("houseId")Integer houseId);
}