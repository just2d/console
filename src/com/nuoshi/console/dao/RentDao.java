package com.nuoshi.console.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.taofang.biz.domain.constants.HouseConstant;
import com.taofang.biz.util.BizServiceHelper;
import com.nuoshi.console.common.Globals;
import com.nuoshi.console.common.Resources;
import com.nuoshi.console.domain.estate.EstateInfo;
import com.nuoshi.console.domain.agent.HousePhoto;
import com.nuoshi.console.domain.agent.RejectReason;
import com.nuoshi.console.domain.audit.AuditStep;
import com.nuoshi.console.domain.auditHistory.AuditHistory;
import com.nuoshi.console.domain.auditHistory.HistoryInfo;
import com.nuoshi.console.domain.auditHistory.SubAuditHistory;
import com.nuoshi.console.domain.reason.InvalidReason;
import com.nuoshi.console.domain.rent.Rent;
import com.nuoshi.console.domain.rent.RentInfo;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.persistence.read.admin.agent.RejectReasonReadMapper;
import com.nuoshi.console.persistence.read.admin.audit.AuditTaskReadMapper;
import com.nuoshi.console.persistence.read.taofang.agent.AgentManageReadMapper;
import com.nuoshi.console.persistence.read.taofang.rent.RentReadMapper;
import com.nuoshi.console.persistence.write.admin.audit.AuditTaskWriteMapper;
import com.nuoshi.console.persistence.write.taofang.rent.RentWriteMapper;

/**
 * <b>www.taofang.com</b> <b>function:</b>
 * 
 * @author lizhenmin
 * @createDate 2011 Jul 21, 2011 2:01:05 PM
 * @email lizm@taofang.com
 * @version 1.0
 */
/**
 * @author Administrator
 *
 */
@Repository
public class RentDao {
	@Resource
	private RentReadMapper rentReadMapper;
	@Resource
	private RejectReasonReadMapper rejectReasonReadMapper;
	@Resource
	private RentWriteMapper rentWriteMapper;
	@Resource
	private AgentManageReadMapper agentManageReadMapper;
	@Resource
	private AuditTaskReadMapper auditTaskReadMapper;
	@Resource
	private AuditTaskWriteMapper auditTaskWriteMapper;

	
	public void updateRentAuditFlag(List<Integer> houseIdList,Integer type){
		rentWriteMapper.updateRentAuditFlag(houseIdList,type);
	}
	public List<SubAuditHistory> getSubHistoryInfo(List<Integer> houseIdList) {
		return rentReadMapper.getSubHistoryInfo(houseIdList);
	}
	@SuppressWarnings("rawtypes")
	public List<RentInfo> getRent4VerifyByPage(HashMap params) {
		if (params == null) {
			params = new HashMap<String, Object>();
		}
		List<RentInfo> resultList = rentReadMapper.getRent4VerifyByPage(params);

		for (RentInfo result : resultList) {
			Long hxNum = this.selectRentPhotoCountByHouseId((Integer) result.getId(), "1");// 户型图
			Long snNum = this.selectRentPhotoCountByHouseId((Integer) result.getId(), "2");// 室内图
			Long xqNum = this.selectRentPhotoCountByHouseId((Integer) result.getId(), "3");// 小区图
			String cityDir = agentManageReadMapper.getDirName((Integer) result.getCityid());
			result.setHxNum(hxNum);
			result.setSnNum(snNum);
			result.setXqNum(xqNum);
			result.setCityDir(cityDir);
			

			List<HousePhoto> housePhotoes = this.getHousePhotoByRentId(result.getId());
			// 普通图
			List<HousePhoto> houseImgs = new ArrayList<HousePhoto>();
			List<HousePhoto> estateImgs = new ArrayList<HousePhoto>();
			List<HousePhoto> roomImgs = new ArrayList<HousePhoto>();

			/*
			 * //小图 List<String> sHouseImgs = new ArrayList<String>();
			 * List<String> sEstateImgs = new ArrayList<String>(); List<String>
			 * sRoomImgs = new ArrayList<String>(); //大图 List<String> lHouseImgs
			 * = new ArrayList<String>(); List<String> lEstateImgs = new
			 * ArrayList<String>(); List<String> lRoomImgs = new
			 * ArrayList<String>();
			 */

			for (HousePhoto housePhoto : housePhotoes) {
				if (housePhoto.getCategory().equals("1")) {
					houseImgs.add(housePhoto);

				} else if (housePhoto.getCategory().equals("2")) {
					roomImgs.add(housePhoto);
				} else if (housePhoto.getCategory().equals("3")) {
					estateImgs.add(housePhoto);
				}

			}

			result.setEstateImgUrls(estateImgs);
			result.setHouseImgUrls(houseImgs);
			result.setRoomImgUrls(roomImgs);

			/*
			 * result.setsEstateImgUrls(sEstateImgs);
			 * result.setsHouseImgUrls(sHouseImgs);
			 * result.setsRoomImgUrls(sRoomImgs);
			 * 
			 * result.setlEstateImgUrls(lEstateImgs);
			 * result.setlHouseImgUrls(lHouseImgs);
			 * result.setlRoomImgUrls(lRoomImgs);
			 */

		}

		return resultList;
	}

	/**
	 * @param resultList
	 *            基本信息
	 * @return 经过加工过可以显示图片的List
	 */
	public List<RentInfo> getRentInfos_photos(List<RentInfo> resultList) {
		for (RentInfo result : resultList) {
			Long hxNum = this.selectRentPhotoCountByHouseId((Integer) result.getId(), "1");// 户型图
			Long snNum = this.selectRentPhotoCountByHouseId((Integer) result.getId(), "2");// 室内图
			Long xqNum = this.selectRentPhotoCountByHouseId((Integer) result.getId(), "3");// 小区图
			String cityDir = agentManageReadMapper.getDirName((Integer) result.getCityid());
			result.setHxNum(hxNum);
			result.setSnNum(snNum);
			result.setXqNum(xqNum);
			result.setCityDir(cityDir);

			List<HousePhoto> housePhotoes =  this.getHousePhotoByRentId(result.getId());
			// 普通图
			List<HousePhoto> houseImgs = new ArrayList<HousePhoto>();
			List<HousePhoto> estateImgs = new ArrayList<HousePhoto>();
			List<HousePhoto> roomImgs = new ArrayList<HousePhoto>();

			/*
			 * //小图 List<String> sHouseImgs = new ArrayList<String>();
			 * List<String> sEstateImgs = new ArrayList<String>(); List<String>
			 * sRoomImgs = new ArrayList<String>(); //大图 List<String> lHouseImgs
			 * = new ArrayList<String>(); List<String> lEstateImgs = new
			 * ArrayList<String>(); List<String> lRoomImgs = new
			 * ArrayList<String>();
			 */

			for (HousePhoto housePhoto : housePhotoes) {
				if (housePhoto.getCategory().equals("1")) {
					houseImgs.add(housePhoto);

				} else if (housePhoto.getCategory().equals("2")) {
					roomImgs.add(housePhoto);
				} else if (housePhoto.getCategory().equals("3")) {
					estateImgs.add(housePhoto);
				}

			}

			result.setEstateImgUrls(estateImgs);
			result.setHouseImgUrls(houseImgs);
			result.setRoomImgUrls(roomImgs);

			/*
			 * result.setsEstateImgUrls(sEstateImgs);
			 * result.setsHouseImgUrls(sHouseImgs);
			 * result.setsRoomImgUrls(sRoomImgs);
			 * 
			 * result.setlEstateImgUrls(lEstateImgs);
			 * result.setlHouseImgUrls(lHouseImgs);
			 * result.setlRoomImgUrls(lRoomImgs);
			 */

		}
		return resultList;
	}

	public int insertRentInvalidReason(InvalidReason invalidreason) {
		assert (null != invalidreason);
		if (rentWriteMapper != null) {
			try {
				return rentWriteMapper.insertRentInvalidReason(invalidreason);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

	public List<RejectReason> getAllRejectReason(int type) {
		return rejectReasonReadMapper.getAllRejectReasonByType(type);
	}

	@SuppressWarnings("rawtypes")
	public List<RentInfo> getAllRent4VerifyByPage(HashMap params) {
		if (params == null) {
			params = new HashMap<String, Object>();
		}

		List<RentInfo> resultList = rentReadMapper.getAllRent4VerifyByPage(params);

		for (RentInfo rentInfo : resultList) {

			Long hxNum = Long.valueOf(rentInfo.getLayoutCount() == null ? 0 : rentInfo.getLayoutCount());// 户型图
			Long snNum = Long.valueOf(rentInfo.getInnerCount() == null ? 0 : rentInfo.getInnerCount());// 室内图
			Long xqNum = Long.valueOf(rentInfo.getCommunityCount() == null ? 0 : rentInfo.getCommunityCount());// 小区图
			String cityDir = agentManageReadMapper.getDirName((Integer) rentInfo.getCityid());
			rentInfo.setHxNum(hxNum);
			rentInfo.setSnNum(snNum);
			rentInfo.setXqNum(xqNum);
			rentInfo.setCityDir(cityDir);

			List<HousePhoto> housePhotoes = this.getHousePhotoByRentId(rentInfo.getId()); 
			rentInfo.setSearchScore(rentInfo.getSearchScore(HouseConstant.RENT));
			// 普通图
			List<HousePhoto> houseImgs = new ArrayList<HousePhoto>();
			List<HousePhoto> estateImgs = new ArrayList<HousePhoto>();
			List<HousePhoto> roomImgs = new ArrayList<HousePhoto>();

			/*
			 * //小图 List<String> sHouseImgs = new ArrayList<String>();
			 * List<String> sEstateImgs = new ArrayList<String>(); List<String>
			 * sRoomImgs = new ArrayList<String>(); //大图 List<String> lHouseImgs
			 * = new ArrayList<String>(); List<String> lEstateImgs = new
			 * ArrayList<String>(); List<String> lRoomImgs = new
			 * ArrayList<String>();
			 */

			for (HousePhoto housePhoto : housePhotoes) {
				if (housePhoto.getCategory().equals("1")) {
					houseImgs.add(housePhoto);

				} else if (housePhoto.getCategory().equals("2")) {
					roomImgs.add(housePhoto);
				} else if (housePhoto.getCategory().equals("3")) {
					estateImgs.add(housePhoto);
				}

			}

			rentInfo.setEstateImgUrls(estateImgs);
			rentInfo.setHouseImgUrls(houseImgs);
			rentInfo.setRoomImgUrls(roomImgs);

			/*
			 * result.setsEstateImgUrls(sEstateImgs);
			 * result.setsHouseImgUrls(sHouseImgs);
			 * result.setsRoomImgUrls(sRoomImgs);
			 * 
			 * result.setlEstateImgUrls(lEstateImgs);
			 * result.setlHouseImgUrls(lHouseImgs);
			 * result.setlRoomImgUrls(lRoomImgs);
			 */

		}

		return resultList;
	}

	@SuppressWarnings("rawtypes")
	public String doVerify(Integer reslutType, HashMap params) {

		return null;
	}

	public int updateRent(Rent rent) {
		assert (rent != null);
		if (null != rentWriteMapper) {
			try {
				rent.decodeBeforeSave();
				return rentWriteMapper.updateRent(rent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

	@SuppressWarnings("unused")
	public List<RentInfo> getAllRent(Integer authorId) {
		List<RentInfo> rentsInfos = new ArrayList<RentInfo>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("authorid", authorId);
		List<RentInfo> rents = rentReadMapper.getAllVerifyRent(params);
		for (RentInfo result : rents) {
			List<HousePhoto> housePhotoes = getHousePhotoByRentId(result.getId());
			// 普通图
			List<HousePhoto> houseImgs = new ArrayList<HousePhoto>();
			List<HousePhoto> estateImgs = new ArrayList<HousePhoto>();
			List<HousePhoto> roomImgs = new ArrayList<HousePhoto>();

			/*
			 * //小图 List<String> sHouseImgs = new ArrayList<String>();
			 * List<String> sEstateImgs = new ArrayList<String>(); List<String>
			 * sRoomImgs = new ArrayList<String>(); //大图 List<String> lHouseImgs
			 * = new ArrayList<String>(); List<String> lEstateImgs = new
			 * ArrayList<String>(); List<String> lRoomImgs = new
			 * ArrayList<String>();
			 */

			for (HousePhoto housePhoto : housePhotoes) {
				if (housePhoto.getCategory().equals("1")) {
					houseImgs.add(housePhoto);

				} else if (housePhoto.getCategory().equals("2")) {
					roomImgs.add(housePhoto);
				} else if (housePhoto.getCategory().equals("3")) {
					estateImgs.add(housePhoto);
				}

			}

			result.setEstateImgUrls(estateImgs);
			result.setHouseImgUrls(houseImgs);
			result.setRoomImgUrls(roomImgs);

			/*
			 * result.setsEstateImgUrls(sEstateImgs);
			 * result.setsHouseImgUrls(sHouseImgs);
			 * result.setsRoomImgUrls(sRoomImgs);
			 * 
			 * result.setlEstateImgUrls(lEstateImgs);
			 * result.setlHouseImgUrls(lHouseImgs);
			 * result.setlRoomImgUrls(lRoomImgs);
			 */

		}
		return rents;
	}

	public List<HousePhoto> getHousePhoto(Integer houseId, String category) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		List<HousePhoto> list = new ArrayList<HousePhoto>();
		params.put("houseid", houseId);
		params.put("category", category);
		List<com.taofang.biz.domain.house.HousePhoto> photoList = BizServiceHelper.getHousePhotoService().getRentPhotos(houseId);
		for(com.taofang.biz.domain.house.HousePhoto photo: photoList){
			if(category.equals((photo.getCategory()/100+1+""))){
				HousePhoto housePhoto = new HousePhoto();
				housePhoto.setPhotoid(photo.getId()+"");
				housePhoto.setHouseid(photo.getHouseId());
				housePhoto.setCategory(photo.getCategory()/100+1+"");
				if("2".equals(category)){
					housePhoto.setInnertype(photo.getCategory()%10);
				}
				housePhoto.setCover(photo.getCoverFlag()=="Y"?1:0);
				housePhoto.setsURL(photo.getRentSmallPhotoUrl());
				housePhoto.setmURL(photo.getRentMediumPhotoUrl());
				housePhoto.setlURL(photo.getRentLargePhotoUrl());
				list.add(housePhoto);
			}
		}
		return list;
	}

	public Long selectRentPhotoCountByHouseId(Integer houseId, String category) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("houseid", houseId);
		params.put("category", category);
		List<HashMap<String, Long>> result = rentReadMapper.selectRentPhotoCountByHouseId(params);

		return result.get(0).get("num");
	}

	public void deleteHousePhoto(int houseid, int photoid, int category) {
		Rent rent = rentReadMapper.selectRentById(houseid);
		rentWriteMapper.deleteRentPhoto(houseid, photoid);
		rentWriteMapper.deletePhoto(photoid);
		rent.setPhotoCount(rent.getPhotoCount() - 1);

		if (category == 1) {
			rent.setLayoutCount(rent.getLayoutCount() - 1);
		} else if (category == 2) {
			rent.setInnerCount(rent.getInnerCount() - 1);
		} else if (category == 3) {
			rent.setCommunityCount(rent.getCommunityCount() - 1);
		}

		rentWriteMapper.updateRent(rent);

	}

	public Rent selectRentById(int id) {
		return rentReadMapper.selectRentById(id);
	}

	public int updateAuditStatus(int id, int status) {
		return rentWriteMapper.updateAuditStatus(id, status);
	}

	public int updateHouseStatus(int id, int houseStatus) {
		return rentWriteMapper.updateHouseStatus(id, houseStatus);
	}

	public int rejectHouse(int houseId) {
		return rentWriteMapper.rejectHouse(houseId);
	}

	public void updatePhotoStatus(Integer houseid, int photoStatus) {
		assert (houseid != null);
		rentWriteMapper.updatePhotoStatus(houseid, photoStatus);

	}

	public void signHouse(String auditSign, List<Integer> sqlList) {
		rentWriteMapper.signHouse(auditSign, sqlList);
	}

	/**
	 * 向审核任务中添加
	 * 
	 * @param auditSign
	 *            house中的审核占用标记
	 * @param auditStatus
	 *            审核状态
	 * @param num
	 *            任务数量
	 * @author wangjh Dec 3, 2011 6:57:23 PM
	 */
	public void buildRentTask(String auditSign, Integer auditStatus, Integer houseStatus, Integer num, Integer auditStep, Integer auditUserId, Integer cityId) {
		auditTaskWriteMapper.buildTask(auditSign, auditStatus, houseStatus, num, auditStep, auditUserId, Globals.HOUSE_TYPE_RENT, cityId);

	}

	public void buileTaskByParam(RentInfo info, Integer auditStep, Integer auditUserId,AuditStep as) {
		auditTaskWriteMapper.buildRentTaskByParam(info, auditStep, auditUserId, Globals.HOUSE_TYPE_RENT,as);
	}

	/**
	 * 在audit_task表中查询指定人员，指定审核步骤的任务
	 * 
	 * @param auditUserId
	 *            审核人员
	 * @param auditStep
	 *            审核步骤（基本信息，户型图，小区图，室内图）
	 * @return
	 * @author wangjh Dec 5, 2011 10:17:39 AM
	 */
	public List<Integer> getRentTaskIdList(Integer auditUserId, Integer auditStep, Integer type, Integer num, Integer cityId) {
		return auditTaskReadMapper.getHouseIdListFromTask(auditUserId, auditStep, type, Globals.AUDIT_RESULT_UNDISTRIBUTED, num, cityId);
	}

	public List<Integer> getRentIdForAudit(String auditSign, Integer auditStatus, Integer houseStatus, Integer num, Integer cityId,String vipFlag) {
		return rentReadMapper.getRentIdForAudit(auditSign, auditStatus, houseStatus, num, cityId,vipFlag);
	}

	/**
	 * 设置audit_task某一步审核结果
	 * 
	 * @param auditStep
	 * @param hasPass
	 * @param pass
	 * @author wangjh Dec 6, 2011 3:12:30 PM
	 */
	public void setAuditTaskResult(Integer auditStep, List<Integer> houseIdList, Integer result) {
		auditTaskWriteMapper.setAuditTaskResult(auditStep, houseIdList, result, Globals.HOUSE_TYPE_RENT);
	}

	public void updateAuditTaskUser(List<Integer> houseIdList, Integer auditUserId, Integer auditStep) {
		auditTaskWriteMapper.updateAuditTaskUser(houseIdList, auditUserId, auditStep);

	}

	public void updateAuditTaskStatus(List<Integer> houseIdList, Integer auditStep, Integer auditType, Integer auditResult) {
		auditTaskWriteMapper.updateAuditTaskStatus(houseIdList, auditStep, auditType, auditResult);
	}

	public List<Integer> getPassHouseIdList(List<Integer> houseIdList, Integer houseTypeRent, Integer passSign,Integer auditStep) {
		return auditTaskReadMapper.getPassHouseIdList(houseIdList, houseTypeRent, passSign,auditStep);
	}

	public void updateAuditStatusByIdList(List<Integer> houseIdList, Integer status, Integer houseStatus, String sign) {
		rentWriteMapper.updateAuditStatusByIdList(houseIdList, status, houseStatus, sign);
	}
	

	/**
	 * @param rentList
	 * @return 根据房源List得到需要审查的photoList
	 */
	public List<HousePhoto> getVerifyingPhotos(List<RentInfo> rentList, Integer type) {
		List<HousePhoto> housePhotos = new ArrayList<HousePhoto>();

		List<Integer> houseIds = new ArrayList<Integer>();
		for (RentInfo rentInfo : rentList) {
			houseIds.add(rentInfo.getId());
		}
		if (CollectionUtils.isEmpty(houseIds)) {
			return housePhotos;
		}
		
		List<com.taofang.biz.domain.house.HousePhoto> photoList = new ArrayList<com.taofang.biz.domain.house.HousePhoto>();
		
		for(int rentId: houseIds){
			List<com.taofang.biz.domain.house.HousePhoto> templist = BizServiceHelper.getHousePhotoService().getRentPhotos(rentId);
			Rent rent = rentReadMapper.selectRentById(rentId);
			if (CollectionUtils.isNotEmpty(templist)) {
				for(com.taofang.biz.domain.house.HousePhoto photo:templist){
					if(photo.getCategory()/100+1 == type){
						//用displayOrder暂存一下装修
						photo.setDisplayOrder(rent.getDecoration().intValue());
						photoList.add(photo);
					}
				}
			}
		}
		
		for (com.taofang.biz.domain.house.HousePhoto photo: photoList) {
			HousePhoto housePhoto = new HousePhoto();
			housePhoto.setmURL(photo.getRentMediumPhotoUrl());
			housePhoto.setDirection(photo.getCategory()%10);
			housePhoto.setHouseid(photo.getHouseId());
			housePhoto.setCategory(Resources.getString("house.decoration."+photo.getDisplayOrder()));
			housePhoto.setPhotoid(photo.getId()+"");
			
			housePhotos.add(housePhoto);
		}
		return housePhotos;
	}
	public List<RentInfo> getMyRentInfo(Integer type, List<Integer> houseIds) {
		return rentReadMapper.getMyRentInfo(houseIds, type);
	}

	/**
	 * @param rentList
	 * @return 得到所有房源ID
	 */
	public String getVerifyHouseIdList(List<RentInfo> rentList) {
		StringBuffer sb = new StringBuffer();
		for (RentInfo rentInfo : rentList) {
			sb.append("," + rentInfo.getId());
		}
		// 去掉第一个","
		if (sb.length() > 1) {
			return sb.substring(1, sb.length());
		}
		return sb.toString();
	}

	/**
	 * @param rentInfo
	 * @param j
	 *            sessionUser的id
	 */
	private void handleRejectPhoto(String houseid, String photoid, String photobrowse, int j,String reason) {
		try {
			rentWriteMapper.moveRent2Reject(houseid, photoid, photobrowse, "" + j,reason);
			rentWriteMapper.deleteRentPhoto(Integer.parseInt(houseid), Integer.parseInt(photoid));
			rentWriteMapper.updatePhotoStatus(Integer.parseInt(houseid), 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Integer> getUnauditPhotoCategory(Integer houseId){
		return rentReadMapper.getUnauditPhotoCategory(houseId);
	}

	/**
	 * @param blockList
	 *            数据结构:houseid_photoid_mUrl
	 * @param j
	 *            sessionUser的id <br>
	 *            删除rent_photo里的数据,转移到reject_photo里
	 */
	public List<Integer> handleRejectPhotos(String blockList, int j) {
		//TODO 将拒绝的图片id列表返回，以便计算通过的图片数，更新审核标记
		String[] blockListArray = blockList.split(",");
		String sign="#_#";
		// 违规房源idList
		List<String> houseIdList = new ArrayList<String>();
		List<Integer> rejectPhotoIds = new ArrayList<Integer>();//被拒绝的图片Ids,返回到前台计算通过的图片
		for (int i = 0; i < blockListArray.length; i++) {
			String[] reasonArr=blockListArray[i].split(sign);
			String reasonString=reasonArr[1];
			String[] house_photo = reasonArr[0].split("_");
			houseIdList.add(house_photo[0]);
			rejectPhotoIds.add(Integer.parseInt(house_photo[1]));
			// 拼出mURL字符串
			StringBuffer photobrowse = new StringBuffer();
			for (int k = 2; k < house_photo.length; k++) {
				photobrowse.append(house_photo[k] + "_");
			}

			// 去掉最后一个"_"
			photobrowse.deleteCharAt(photobrowse.length() - 1);

			// 移动rent_photo信息到reject_photo,删除rent_photo关联信息
			this.handleRejectPhoto(house_photo[0], house_photo[1], photobrowse.toString(), j,reasonString);

			// 循环更新rent表内的各种照片数量
			Integer house_id = Integer.parseInt(house_photo[0]);
			this.updateRentPhotoCount(house_id);
		}

		// 更新房源封面图片
		this.updateCoverImgList(houseIdList);
		return rejectPhotoIds;

	}

	/**
	 * @param houseIdList
	 *            // 更新房源封面图片
	 */
	public void updateCoverImgList(List<String> houseIdList) {
		
		if(CollectionUtils.isEmpty(houseIdList)){
			return;
		}

		// 得到应该更新封面的house的List(photoid不为0,但是rent_photo没有此数据)
		List<RentInfo> updateCoverImgHouseList = new ArrayList<RentInfo>();
		updateCoverImgHouseList = rentReadMapper.getUpdateCoverImgHouseList(houseIdList);
		if(updateCoverImgHouseList == null || updateCoverImgHouseList.size() < 1) return;
		for (RentInfo rentInfo : updateCoverImgHouseList) {
			if(rentInfo.getInnerCount()==null){
				rentInfo.setInnerCount(0);
			}
			if(rentInfo.getLayoutCount()==null){
				rentInfo.setLayoutCount(0);
			}
			if(rentInfo.getCommunityCount()==null){
				rentInfo.setCommunityCount(0);
			}
			// 取图片类型
			int type = 2;
			if (rentInfo.getPhotoCount()==null || rentInfo.getPhotoCount() == 0) {
				rentInfo.setPhotoid(0);
				rentInfo.setPhototiny("/s/house_s.png");
				rentInfo.setPhotobrowse("/m/house_m.png");
				rentWriteMapper.updateCoverImg(rentInfo);
				continue;
			} else if (rentInfo.getInnerCount() > 0) {// 先从室内图取
				type = 2;
			} else if (rentInfo.getCommunityCount() > 0) {// 再从小区图
				type = 3;
			} else {// 再从户型图取
				type = 1;
			}
			
			try {
				List<HousePhoto> housePhotos = null;
				RentInfo tempObj = rentReadMapper.getOneCoverImg(rentInfo.getId(), type);
				housePhotos = getAllHousePhotoByType(tempObj.getId(), tempObj.getPhotoid(), type, tempObj.getInalbum());
				RentInfo info = new RentInfo();
				HousePhoto housePhoto = housePhotos.get(0);
				//TODO
				info.setId(housePhoto.getHouseid());
				info.setPhotoid(Integer.parseInt(housePhoto.getPhotoid()));
				info.setPhototiny(housePhoto.getsURL());
				info.setPhotobrowse(housePhoto.getmURL());
				rentWriteMapper.updateCoverImg(info);
			} catch (Exception e) {
				rentInfo.setPhotoid(0);
				rentInfo.setPhototiny("/s/house_s.png");
				rentInfo.setPhotobrowse("/m/house_m.png");
				rentWriteMapper.updateCoverImg(rentInfo);
			}
		}
	}

	public void deleteAuditTask(List<Integer> houseIdList, Integer auditType) {
		auditTaskWriteMapper.deleteAuditTask(houseIdList, auditType);
	}
	
	public List<Integer> getRentIdList(Integer auditUserId, Integer num, Integer auditStep, Integer houseStatus, Integer auditStatus, Integer cityId) {
		return auditTaskReadMapper.getHouseIdList(auditUserId, auditStep, Globals.HOUSE_TYPE_RENT, num, cityId);
	}

	public List<RentInfo> getRentFromAuditTask(List<Integer> rentInfoIdList, Integer houseStatus, Integer auditStatus) {
		return rentReadMapper.getRentFromAuditTaskByIdList(rentInfoIdList, houseStatus, auditStatus);
	}

	public List<RentInfo> getRentInfoCity(List<Integer> rentIdList) {
		return rentReadMapper.getRentInfoCity(rentIdList);
	}

	public List<Integer> getInconformityHouseIds(List<Integer> houseIds) {
		return rentReadMapper.getInconformityHouseIds(houseIds);
	}

	/**
	 * 图片数为0的houseId
	 * 
	 * @param auditStep
	 *            审核步骤
	 * @param houseIdList
	 *            房源id
	 * @return
	 * @author wangjh Dec 19, 2011 4:41:52 PM
	 */
	public List<Integer> getPhotoCountOByStep(Integer auditStep, List<Integer> houseIdList) {
		return rentReadMapper.getPhotoCountOByStep(auditStep, houseIdList);
	}

	public List<AuditHistory> getHistoryInfo(List<Integer> houseIdList) {
		return rentReadMapper.getHistoryInfo(houseIdList, Globals.HOUSE_TYPE_RENT);
	}

	public List<SubAuditHistory> getRentPhotosByHouseListAndType(List<Integer> houseIdIntList, List<Integer> photoIdList, Integer type) {
		return rentReadMapper.getRentPhotosByHouseListAndType(houseIdIntList, photoIdList, type);
	}
	public HistoryInfo getPhotoInfoHistory(HistoryInfo historyInfo){
		return rentReadMapper.getPhotoInfoHistory(historyInfo);
		
	}

	/**
	 * 搬运rent_photo和reject_photo,要更新rent里边的各种photo_count字段
	 * @param historyInfo
	 * @param houseTypeRent
	 * @param result
	 * @return
	 * @date 2011-12-23下午5:58:40
	 * @author dongyj
	 * @throws Exception 
	 */
	public boolean reAuditPhoto(HistoryInfo historyInfo, int result,User user) throws Exception {

		try {
			// 是否更新房源封面
			boolean updateCoverFlag = false;
			if (result == Globals.AUDIT_RESULT_PASS) {
				// 转移reject_photo到rent_photo,增加相应photo_count
				historyInfo = rentReadMapper.getRejectPhotoReAudit(historyInfo);
				
				reAuditPhotoPass(historyInfo);
//				rentWriteMapper.reAuditPhotoPass(historyInfo);
				rentWriteMapper.deleteRejectPhoto(historyInfo.getHouseId(), historyInfo.getPhotoId(), historyInfo.getInalbum());
				
			} else if (result == Globals.AUDIT_RESULT_REJECT) {
				// 转移rent_photo到reject_photo里,减少相应photo_count
				List<HousePhoto> housePhotos = getAllHousePhotoByType(historyInfo.getHouseId(), historyInfo.getPhotoId(), historyInfo.getAuditStep(), historyInfo.getInalbum());
				if (housePhotos.size() < 1) {
					throw new Exception("没有找到图片@RentDao!reAuditPhoto");
				}
				historyInfo.setHouseDescribe(housePhotos.get(0).getmURL());
				historyInfo.setAuditStep(Integer.parseInt(housePhotos.get(0).getCategory()));
				
				// 由于前期在建表时弄反了.
				historyInfo.setHouseType(Globals.RENT_TYPE);
				rentWriteMapper.reAuditPhotoReject(historyInfo);
				rentWriteMapper.deleteRentPhoto(historyInfo.getHouseId(), historyInfo.getPhotoId());
				// 设置photo_status为1
				rentWriteMapper.updatePhotoStatus(historyInfo.getHouseId(), 1);
				updateCoverFlag = true;
			}
			
			// 更新所有图片数量,而不是直接加减
			this.updateRentPhotoCount(historyInfo.getHouseId());
			
			// 更新封面
			if(updateCoverFlag){
				List<String> list = new ArrayList<String>();
				list.add(""+historyInfo.getHouseId());
				this.updateCoverImgList(list);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	private void reAuditPhotoPass(HistoryInfo historyInfo){
		com.taofang.biz.domain.house.HousePhoto photo =new com.taofang.biz.domain.house.HousePhoto();
		photo.setId(historyInfo.getPhotoId());
		photo.setHouseId(historyInfo.getHouseId());
		Rent rent = rentReadMapper.selectRentById(historyInfo.getHouseId());
		photo.setAgentId(rent.getAuthorid());
		photo.setEstateId(rent.getEstateid());
		photo.setCityId(rent.getCityid());
		int category=0;
		switch (historyInfo.getAuditStep()) {
		case 1:
			category =10;
			break;
		case 2:
			category =100;
			break;
		case 3:
			category =200;
			break;
		default:
			break;
		}
		photo.setCategory(category);
		BizServiceHelper.getHousePhotoService().storeToRent(photo);
	}
	
	
	public void updateRentPhotoCount(Integer houseId) {
		rentWriteMapper.updateRentPhotoCount(houseId);
	}
	
	public List<HousePhoto> getAllHousePhotoByType(Integer houseid, Integer photoid, Integer type, Integer inalbum) {
		List<HousePhoto> list = rentReadMapper.getAllHousePhotoByType(houseid, photoid, type, inalbum);
		if(CollectionUtils.isNotEmpty(list)){
			for(HousePhoto rphoto:list){
				com.taofang.biz.domain.house.HousePhoto photo = BizServiceHelper.getHousePhotoService().getRentPhoto(Integer.valueOf(rphoto.getPhotoid()));
				rphoto.setlURL(photo.getRentLargePhotoUrl());
				rphoto.setmURL(photo.getRentMediumPhotoUrl());
				rphoto.setsURL(photo.getRentSmallPhotoUrl());
			}
		}
		return list;
	}
	public Integer getUnclaimedAwaitHouseAuditCount(Integer houseStatus,Integer auditStatus) {
		return rentReadMapper.getUnclaimedAwaitHouseAuditCount(houseStatus,auditStatus);
	}
	public Integer getPhotoCountByHouseIdList(List<Integer> list,
			Integer auditStep) {
		return rentReadMapper.getPhotoCountByHouseIdList(list,auditStep);
	}
	public Integer getPhotoAuditUnclaimedAwaitCount(Integer houseStatus,
			Integer auditStatus, Integer auditStep) {
		return rentReadMapper.getPhotoAuditUnclaimedAwaitCount(
				houseStatus, auditStatus,auditStep);
	}
	public List<HousePhoto> getAllHousePhoto(Integer houseid) {
		return getHousePhotoByRentId(houseid);
	}
	public int updateHouseScore(Integer houseid, Double score) {
		return rentWriteMapper.updateHouseScore(houseid,score);
	}
	public int unshelveAll(int agentId) {
		return rentWriteMapper.unshelveAll(agentId);
		
	}

	public List<HistoryInfo> getNewPhotos(List<String> photoIdList, String auditStep, int houseType, int result) {
		return rentReadMapper.getNewPhotos(photoIdList, auditStep, houseType, result);
	}
	public List<RentInfo> getPhotoExtraInfo(int auditType, List<Integer> houseIdList) {
		return rentReadMapper.getPhotoExtraInfo(auditType,houseIdList);
	}
	private List<HousePhoto> getHousePhotoByRentId(int rentId){
		List<HousePhoto> housePhotoList = new ArrayList<HousePhoto>();
		List<com.taofang.biz.domain.house.HousePhoto> bphotoList = BizServiceHelper.getHousePhotoService().getRentPhotos(rentId);
		if(CollectionUtils.isNotEmpty(bphotoList)){
			for(com.taofang.biz.domain.house.HousePhoto bphoto: bphotoList ){
				HousePhoto housePhoto = new HousePhoto();
				
				housePhoto.setCategory(bphoto.getCategory()/100+1+"");
				housePhoto.setInalbum(0);
				housePhoto.setInnertype(0);
				housePhoto.setCover(bphoto.getCoverFlag()=="Y"?1:0);
				housePhoto.setPhotoid(bphoto.getId()+"");
				housePhoto.setlURL(bphoto.getRentLargePhotoUrl());
				housePhoto.setmURL(bphoto.getRentMediumPhotoUrl());
				housePhoto.setsURL(bphoto.getRentSmallPhotoUrl());
				housePhotoList.add(housePhoto);
			}
		}
		return housePhotoList;
	}
	public Rent getDecorationByPhotoId(Integer photoId) {
		return rentReadMapper.getDecorationByPhotoId(photoId);
	}
	public void updateStore(Integer oldStoreId, Integer newStoreId,String storeName) {
		 rentWriteMapper.updateStore(oldStoreId,newStoreId,storeName);
		
	}
	public void updateCompanyName(Integer oldCompanyId, Integer newCompanyId,String companyName) {
		 rentWriteMapper.updateCompanyName( oldCompanyId,  newCompanyId,companyName);
	}
	public void updateStoreNameByStoreId(int id,String storeName) {
		rentWriteMapper.updateStoreNameByStoreId(id,storeName);
		
	}
	
	public void updateLocaleByEstate(EstateInfo estateInfo){
		rentWriteMapper.updateLocaleByEstate(estateInfo);	
	}
	public void updateEstateNameByEstateId(EstateInfo estateInfo) {
		rentWriteMapper.updateEstateNameByEstateId(estateInfo);
		
	}
}
