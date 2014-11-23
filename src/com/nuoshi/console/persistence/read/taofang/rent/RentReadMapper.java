package com.nuoshi.console.persistence.read.taofang.rent;

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
import com.nuoshi.console.domain.rent.Rent;
import com.nuoshi.console.domain.rent.RentInfo;
import com.nuoshi.console.domain.stat.AuditSearch;

@SuppressWarnings("rawtypes")
public interface RentReadMapper {

	public List<SubAuditHistory> getSubHistoryInfo(@Param("houseIdList")List<Integer> houseIdList);
	
	public List<RentInfo> getRent4VerifyByPage(HashMap params);

	public List<HashMap<String, Long>> selectRentPhotoCountByHouseId(HashMap params);

	public List<HousePhoto> getRentPhoto(HashMap params);// 租房图片

	public List<HousePhoto> getAllHousePhoto(Integer houseId);

	public List<RentInfo> getAllVerifyRent(HashMap params);// 获取经纪人所有的租房

	public List<RentInfo> getAllRent4VerifyByPage(HashMap params);// 所有的租房信息
	
	public Rent selectRentById(@Param("id") int id);
	
	public List<RentInfo> selectRentInfoByIds(@Param("list") List<Integer> list);

	public List<Integer> getRentIdForAudit(@Param("auditSign") String auditSign, @Param("auditStatus") Integer auditStatus, @Param("houseStatus") Integer houseStatus,
			@Param("num") Integer num, @Param("cityId") Integer cityId,@Param("vipFlag")String vipFlag);

	public List<HousePhoto> getAllHousePhotoByType(@Param("houseid") Integer houseid, @Param("photoid") Integer photoid, @Param("type") Integer type,
			@Param("inalbum") Integer inalbum);

	public Integer getRentInfoInalbum(@Param("houseid") Integer houseid, @Param("photoid") Integer photoid);

	public List<RentInfo> getMyRentInfo(@Param("houseIds") List<Integer> houseIds, @Param("type") Integer type);

	public List<RentInfo> getRentFromAuditTaskByIdList(@Param("rentInfoIdList") List<Integer> rentInfoIdList, @Param("houseStatus") Integer houseStatus,
			@Param("auditStatus") Integer auditStatus);

	public List<RentInfo> getRentInfoCity(@Param("rentIdList") List<Integer> rentIdList);

	/**
	 * 统计小区租房数量
	 * 
	 * @param id
	 * @return
	 */
	public int countByEstateId(@Param("estateId") int id);
	
	/**
	 * 获得小区租房
	 * @param paramMap
	 * @return
	 */
	public List<UnionLog> getRentByEstateId(Map paramMap);

	public List<Integer> getInconformityHouseIds(@Param("houseIds") List<Integer> houseIds);

	/**
	 * 图片数为0的houseId
	 */
	public List<Integer> getPhotoCountOByStep(@Param("auditStep") Integer auditStep, @Param("houseIdList") List<Integer> houseIdList);

	/**
	 * @param houseIdList
	 *            有删除图片操作的房源id
	 * @return 得到应该更新封面的houseid的List
	 */
	public List<RentInfo> getUpdateCoverImgHouseList(@Param("houseIdList") List<String> houseIdList);

	public RentInfo getOneCoverImg(@Param("houseid") Integer houseid, @Param("type") int type);

	public List<AuditHistory> getHistoryInfo(@Param("houseIdList") List<Integer> houseIdList, @Param("auditType") Integer auditType);

	/**
	 * @param houseIdIntList
	 * @param photoIdList
	 * @param type
	 * @return
	 * @date 2011-12-21上午11:13:53 @
	 */
	public List<SubAuditHistory> getRentPhotosByHouseListAndType(@Param("houseIdIntList") List<Integer> houseIdIntList, @Param("photoIdList") List<Integer> photoIdList,
			@Param("type") Integer type);

	public HistoryInfo getPhotoInfoHistory(HistoryInfo historyInfo);

	public RentInfo getRejectPhoto(RentInfo rentInfo);

	public HistoryInfo getRejectPhotoReAudit(HistoryInfo historyInfo);

	public HistoryInfo getRentPhotoReAudit(HistoryInfo historyInfo);

	public Integer getUnclaimedAwaitHouseAuditCount(@Param("houseStatus")Integer houseStatus,@Param("auditStatus")Integer auditStatus);

	public Integer getPhotoCountByHouseIdList(@Param("list")List<Integer> list,
			@Param("auditStep")Integer auditStep);

	public Integer getPhotoAuditUnclaimedAwaitCount(@Param("houseStatus")Integer houseStatus,
			@Param("auditStatus")Integer auditStatus, @Param("auditStep")Integer auditStep);

	public List<HousePhoto> getAllPhotoInPhotos(@Param("list")List<Integer> inPhotoIntegers);

	public List<HousePhoto> getCommPhotos(@Param("list")List<Integer> comm_Integers);

	public List<HousePhoto> getLayoutPhotos(@Param("list")List<Integer> layout_Integers);

	public List<Rent> getRentForScore(@Param("idList")List<Integer> idList,
			@Param("typeList")List<Integer> typeList);
	
	public List<House> getAuditVcrTask(@Param("cityId")int cityId, @Param("num")int num);
	
	public List<AuditSearch> getAuditHouseCount(@Param("condition")String condition);
	public List<AuditSearch> getAuditCount(@Param("condition")String condition,@Param("listId")List<Integer> listId);

	public List<HistoryInfo> getNewPhotos(@Param("photoIdList") List<String> photoIdList, @Param("auditStep") String auditStep, @Param("houseType") int houseType,
			@Param("result") int result);

	public List<RentInfo> getPhotoExtraInfo(@Param("auditStep")int auditType, @Param("houseIdList")List<Integer> houseIdList);
	
	public List<Integer> getUnauditPhotoCategory(Integer houseId);
	
	public List<Rent> getRentForEvaluationByPage(@Param("conditions")List<String> conditions);

	public List<Rent> getRentPicModelByIds(List<Integer> houseIdIntList);
	public Rent getRentPicModelById(Integer houseId);

	public Rent getDecorationByPhotoId(@Param("photoId")Integer photoId);

	public House getAuditVcrTaskByHouseId(@Param("houseId")Integer houseId);

}