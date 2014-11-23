package com.nuoshi.console.persistence.write.taofang.rent;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.estate.EstateInfo;
import com.nuoshi.console.domain.auditHistory.HistoryInfo;
import com.nuoshi.console.domain.reason.InvalidReason;
import com.nuoshi.console.domain.rent.Rent;
import com.nuoshi.console.domain.rent.RentInfo;


@SuppressWarnings("rawtypes")
public interface RentWriteMapper {
	public  List<HashMap<String,Object>>  updateVerify(HashMap params);
	public int updateRent(Rent rent);
	public int insertRentInvalidReason(InvalidReason ir);
	public int deleteRentPhoto(@Param("houseid")int houseid,@Param("photoid") int photoid);
	public int deleteRejectPhoto(@Param("houseid")int houseid,@Param("photoid") int photoid,@Param("inalbum") int inalbum);
	public int deletePhoto(int id);
	public int updateAuditStatus(@Param("id")int id, @Param("auditStatus")int auditStatus);
	public int rejectHouse(@Param("houseId")int houseId);
	public int updateHouseStatus(@Param("id")int id, @Param("houseStatus")int houseStatus);
	public void updatePhotoStatus(@Param("houseid")Integer houseid, @Param("photoStatus")int photoStatus);
	public void signHouse(@Param("auditSign")String auditSign,@Param("sqlList")List<Integer> sqlList);
	
	public void updateAuditStatusByIdList(@Param("houseIdList")List<Integer> houseIdList, @Param("status")Integer status,@Param("houseStatus")Integer houseStatus,@Param("sign")String sign);
	public void updateRentAuditFlag(@Param("houseIdList")List<Integer> houseIdList,@Param("houseResult")Integer type);
	public void moveRent2Reject(@Param("houseid")String houseid,@Param("photoid")String photoid,@Param("photobrowse")String photobrowse, @Param("j")String j,@Param("reason")String reason);
	public void deleteRentPhotoCountStatus(@Param("houseid")String houseid, @Param("type")String type, @Param("num")Integer num);
	public void updateCoverImg(RentInfo rentInfo);
	public void reAuditPhotoReject(HistoryInfo historyInfo);
	/**
	 * 复审时变动
	 * @param historyInfo
	 * @param i
	 * @date 2011-12-23下午6:29:24
	 * @author dongyj
	 */
	public void updateRentPhotoCountAdd(HistoryInfo historyInfo);
	public void updateRentPhotoCountCut(HistoryInfo historyInfo);
//	public void reAuditPhotoPass(HistoryInfo historyInfo);
	
	/**
	 * 合并小区
	 * @author ningt
	 * @param praramMap
	 */
	public void unionEstate(Map praramMap);
	public void outOfStockByAgentId(@Param("agentId")Integer agentId);
	public int updateHouseScore(@Param("houseid")Integer houseid, @Param("score")Double score);
	public int unshelveAll(@Param("agentId")int agentId);
	public void updateRentPhotoCount(@Param("houseId")Integer houseId);
	public int changeVcrAuditSign(@Param("houseIds")List<Integer> houseIds, @Param("status")int status);
	public int approveVcr(@Param("houseId")int houseId);
	public int rejectVcr(@Param("houseId")int houseId);
	public int changeVisible(@Param("houseId")int houseId, @Param("status")int status);

	public int addExtraScore(@Param("houseId")int houseId, @Param("score")BigDecimal score);
	
	public int updateStatusAndAppealExpiredTime(@Param("houseStatus")Integer houseStatus,@Param("houseId")int houseId);
	public void updateStore(@Param("oldStoreId")Integer oldStoreId,  @Param("newStoreId")Integer newStoreId,@Param("storeName")String storeName);
	public void updateCompanyName(@Param("oldCompanyId")Integer oldCompanyId, @Param("newCompanyId")Integer newCompanyId, @Param("companyName")String companyName);
	public void updateStoreNameByStoreId(@Param("storeId")int id,@Param("storeName")String storeName);
	public void updateLocaleByEstate(EstateInfo estateInfo);
	public void updateEstateNameByEstateId(EstateInfo estateInfo);
}