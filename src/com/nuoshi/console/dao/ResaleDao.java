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
import com.nuoshi.console.domain.resale.Resale;
import com.nuoshi.console.domain.resale.ResaleInfo;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.persistence.read.admin.agent.RejectReasonReadMapper;
import com.nuoshi.console.persistence.read.admin.audit.AuditTaskReadMapper;
import com.nuoshi.console.persistence.read.taofang.agent.AgentManageReadMapper;
import com.nuoshi.console.persistence.read.taofang.resale.ResaleReadMapper;
import com.nuoshi.console.persistence.write.admin.audit.AuditTaskWriteMapper;
import com.nuoshi.console.persistence.write.taofang.resale.ResaleWriteMapper;


/**
 *<b>www.taofang.com</b>
 * <b>function:</b>
 * @author lizhenmin
 * @createDate 2011 Jul 21, 2011 2:01:05 PM
 * @email lizm@taofang.com
 * @version 1.0
 */
@Repository
public class ResaleDao {
	@Resource
	private ResaleReadMapper resaleReadMapper;
	@Resource
	private ResaleWriteMapper resaleWriteMapper;
	@Resource
	private RejectReasonReadMapper rejectReasonReadMapper;
	@Resource
	private AgentManageReadMapper agentManageReadMapper	;
	@Resource
	private AuditTaskReadMapper auditTaskReadMapper;
	@Resource
	private AuditTaskWriteMapper auditTaskWriteMapper;
	
	public void updateResaleAuditFlag(List<Integer> houseIdList,Integer type){
		resaleWriteMapper.updateResaleAuditFlag(houseIdList,type);
	}
	
	public List<SubAuditHistory> getSubHistoryInfo(List<Integer> houseIdList) {
		return resaleReadMapper.getSubHistoryInfo(houseIdList);
	}
	
	public Resale selectResaleById(int id) {
		if (resaleReadMapper != null) {
			try {
				Resale resale = resaleReadMapper.selectResaleById(id);
				return resale;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public int updateResale(Resale resale) {
		assert (resale != null);
		if (null != resaleWriteMapper) {
			try {
				resale.decodeBeforeSave();
				return resaleWriteMapper.updateResale(resale);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	@SuppressWarnings("rawtypes")
	public List<ResaleInfo> getResale4VerifyByPage(HashMap params){
		if(params==null){
		 params = new HashMap<String, Object>();
		}
		List<ResaleInfo> resultList = resaleReadMapper.getResale4VerifyByPage(params);
		
		for (ResaleInfo resaleInfo : resultList) {
			Long hxNum = this.selectResalePhotoCountByHouseId((Integer) resaleInfo.getId(), "1");//户型图
			Long snNum = this.selectResalePhotoCountByHouseId((Integer) resaleInfo.getId(), "2");//室内图
			Long xqNum = this.selectResalePhotoCountByHouseId((Integer) resaleInfo.getId(), "3");//小区图
			String cityDir = agentManageReadMapper.getDirName((Integer) resaleInfo.getCityid());
			resaleInfo.setHxNum(hxNum);
			resaleInfo.setSnNum( snNum);
			resaleInfo.setXqNum( xqNum);
			resaleInfo.setCityDir(cityDir);
			
			
			List<HousePhoto> housePhotoes = getHousePhotoByResaleId(resaleInfo.getId());
			//普通图
			List<HousePhoto> houseImgs = new ArrayList<HousePhoto>();
			List<HousePhoto> estateImgs = new ArrayList<HousePhoto>();
			List<HousePhoto> roomImgs = new ArrayList<HousePhoto>();
			
			/*//小图
			List<String> sHouseImgs = new ArrayList<String>();
			List<String> sEstateImgs = new ArrayList<String>();
			List<String> sRoomImgs = new ArrayList<String>();
			//大图
			List<String> lHouseImgs = new ArrayList<String>();
			List<String> lEstateImgs = new ArrayList<String>();
			List<String> lRoomImgs = new ArrayList<String>();*/
			
			
		
			
			for (HousePhoto housePhoto : housePhotoes) {
				if(housePhoto.getCategory().equals("1")){
					houseImgs.add(housePhoto);
					
				}else if(housePhoto.getCategory().equals("2")){
					roomImgs.add(housePhoto);
					
				}else if(housePhoto.getCategory().equals("3")){
					estateImgs.add(housePhoto);
				}
				
			}
			
			resaleInfo.setEstateImgUrls(estateImgs);
			resaleInfo.setHouseImgUrls(houseImgs);
			resaleInfo.setRoomImgUrls(roomImgs);
			
		/*	resaleInfo.setsEstateImgUrls(sEstateImgs);
			resaleInfo.setsHouseImgUrls(sHouseImgs);
			resaleInfo.setsRoomImgUrls(sRoomImgs);
			
			resaleInfo.setlEstateImgUrls(lEstateImgs);
			resaleInfo.setlHouseImgUrls(lHouseImgs);
			resaleInfo.setlRoomImgUrls(lRoomImgs);*/
			
		}
		
		return resultList;
	}
	public List<RejectReason> getAllRejectReason(int type) {
		return rejectReasonReadMapper.getAllRejectReasonByType(type);
	}
	public int insertResaleInvalidReason(InvalidReason invalidReason) {
		return resaleWriteMapper.insertResaleInvalidReason(invalidReason);
	}

	@SuppressWarnings("rawtypes")
	public List<ResaleInfo> getAllResale4VerifyByPage(HashMap params){
		if(params==null){
			 params = new HashMap<String, Object>();
			}

		List<ResaleInfo> resultList = resaleReadMapper.getAllResale4VerifyByPage(params);
		
		for (ResaleInfo resaleInfo : resultList) {
			Long hxNum = Long.valueOf(resaleInfo.getLayoutCount()==null?0:resaleInfo.getLayoutCount());//户型图
			Long snNum = Long.valueOf(resaleInfo.getInnerCount()==null?0:resaleInfo.getInnerCount());//室内图
			Long xqNum = Long.valueOf(resaleInfo.getCommunityCount()==null?0:resaleInfo.getCommunityCount());//小区图
			String cityDir = agentManageReadMapper.getDirName((Integer) resaleInfo.getCityid());
			resaleInfo.setHxNum(hxNum);
			resaleInfo.setSnNum( snNum);
			resaleInfo.setXqNum( xqNum);
			resaleInfo.setCityDir(cityDir);
			
			resaleInfo.setSearchScore(resaleInfo.getSearchScore(HouseConstant.RESALE));
			
			
			List<HousePhoto> housePhotoes = getHousePhotoByResaleId(resaleInfo.getId());
			//普通图
			List<HousePhoto> houseImgs = new ArrayList<HousePhoto>();
			List<HousePhoto> estateImgs = new ArrayList<HousePhoto>();
			List<HousePhoto> roomImgs = new ArrayList<HousePhoto>();
			
			/*//小图
			List<String> sHouseImgs = new ArrayList<String>();
			List<String> sEstateImgs = new ArrayList<String>();
			List<String> sRoomImgs = new ArrayList<String>();
			//大图
			List<String> lHouseImgs = new ArrayList<String>();
			List<String> lEstateImgs = new ArrayList<String>();
			List<String> lRoomImgs = new ArrayList<String>();*/
			
			
		
			
			for (HousePhoto housePhoto : housePhotoes) {
				if(housePhoto.getCategory().equals("1")){
					houseImgs.add(housePhoto);
					
				}else if(housePhoto.getCategory().equals("2")){
					roomImgs.add(housePhoto);
					
				}else if(housePhoto.getCategory().equals("3")){
					estateImgs.add(housePhoto);
				}
				
			}
			
			resaleInfo.setEstateImgUrls(estateImgs);
			resaleInfo.setHouseImgUrls(houseImgs);
			resaleInfo.setRoomImgUrls(roomImgs);
			
		/*	resaleInfo.setsEstateImgUrls(sEstateImgs);
			resaleInfo.setsHouseImgUrls(sHouseImgs);
			resaleInfo.setsRoomImgUrls(sRoomImgs);
			
			resaleInfo.setlEstateImgUrls(lEstateImgs);
			resaleInfo.setlHouseImgUrls(lHouseImgs);
			resaleInfo.setlRoomImgUrls(lRoomImgs);*/
			
		}
		
		
		return resultList;
	}

	public List<ResaleInfo> getAllVerifyResale(Integer authorId){
		 HashMap<String, Object> params = new HashMap<String, Object>();
		 params.put("authorid", authorId);
		List<ResaleInfo> resales = resaleReadMapper.getAllVerifyResale(params);
		for (ResaleInfo resaleInfo : resales) {
			List<HousePhoto> housePhotoes = getHousePhotoByResaleId(resaleInfo.getId());
			//普通图
			List<HousePhoto> houseImgs = new ArrayList<HousePhoto>();
			List<HousePhoto> estateImgs = new ArrayList<HousePhoto>();
			List<HousePhoto> roomImgs = new ArrayList<HousePhoto>();
			
			/*//小图
			List<String> sHouseImgs = new ArrayList<String>();
			List<String> sEstateImgs = new ArrayList<String>();
			List<String> sRoomImgs = new ArrayList<String>();
			//大图
			List<String> lHouseImgs = new ArrayList<String>();
			List<String> lEstateImgs = new ArrayList<String>();
			List<String> lRoomImgs = new ArrayList<String>();*/
			
			
		
			
			for (HousePhoto housePhoto : housePhotoes) {
				if(housePhoto.getCategory().equals("1")){
					houseImgs.add(housePhoto);
					
				}else if(housePhoto.getCategory().equals("2")){
					roomImgs.add(housePhoto);
					
				}else if(housePhoto.getCategory().equals("3")){
					estateImgs.add(housePhoto);
				}
				
			}
			
			resaleInfo.setEstateImgUrls(estateImgs);
			resaleInfo.setHouseImgUrls(houseImgs);
			resaleInfo.setRoomImgUrls(roomImgs);
			
		/*	resaleInfo.setsEstateImgUrls(sEstateImgs);
			resaleInfo.setsHouseImgUrls(sHouseImgs);
			resaleInfo.setsRoomImgUrls(sRoomImgs);
			
			resaleInfo.setlEstateImgUrls(lEstateImgs);
			resaleInfo.setlHouseImgUrls(lHouseImgs);
			resaleInfo.setlRoomImgUrls(lRoomImgs);*/
			
		}
		return resales;
	}
	public List<HousePhoto> getHousePhoto(Integer houseId,String category){
		HashMap<String, Object> params = new HashMap<String, Object>();
		List<HousePhoto> list = new ArrayList<HousePhoto>();
		params.put("houseid", houseId);
		params.put("category", category);
		
		List<com.taofang.biz.domain.house.HousePhoto> photoList = BizServiceHelper.getHousePhotoService().getResalePhotos(houseId);
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
				housePhoto.setsURL(photo.getResaleSmallPhotoUrl());
				housePhoto.setmURL(photo.getResaleMediumPhotoUrl());
				housePhoto.setlURL(photo.getResaleLargePhotoUrl());
				list.add(housePhoto);
			}
		}
		return list;
	}
	public Long selectResalePhotoCountByHouseId(Integer houseId,String category){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("houseid", houseId);
		params.put("category", category);
		List<HashMap<String,Long>> result = resaleReadMapper.selectResalePhotoCountByHouseId(params);
		
		return result.get(0).get("num");
	}
	public void deleteHousePhoto(int houseid, int photoid, int category) {
		Resale resale =  resaleReadMapper.selectResaleById(houseid);
		resaleWriteMapper.deleteHousePhoto(houseid,photoid);
		resaleWriteMapper.deletePhoto(photoid);
		resale.setPhotoCount(resale.getPhotoCount()-1);
		
			if(category == 1){
				resale.setLayoutCount(resale.getLayoutCount()-1);
			}else if(category == 2){
				resale.setInnerCount(resale.getInnerCount()-1);
			}else if(category == 3){
				resale.setCommunityCount(resale.getCommunityCount()-1);
			}
			
		
		resaleWriteMapper.updateResale(resale);
	}

	
	public int updateHouseStatus(int houseId, int houseStatus) {
		return resaleWriteMapper.updateHouseStatus(houseId, houseStatus);
	} 
	
	public int updateHouseAuditStatus(int houseId, int auditStatus) {
		return resaleWriteMapper.updateAuditStatus(houseId, auditStatus);
	}
	
	public int rejectHouse(int houseId) {
		return resaleWriteMapper.rejectHouse(houseId);
	}

	public void updatePhotoStatus(Integer houseid, int photoStatus) {
			assert (houseid != null);
		resaleWriteMapper.updatePhotoStatus(houseid,photoStatus);


	}
	public void signHouse(String auditSign,List<Integer> sqlList) {
		resaleWriteMapper.signHouse(auditSign,sqlList);
	}

	

	/**
	 * 向审核任务中添加
	 * @param auditSign house中的审核占用标记
	 * @param auditStatus 审核状态
	 * @param num 任务数量
	 * @author wangjh
	 * Dec 3, 2011 6:57:23 PM
	 */
	public void buildResaleTask(String auditSign, Integer auditStatus,
			Integer houseStatus, Integer num,Integer auditStep,Integer auditUserId,Integer cityId) {
		auditTaskWriteMapper.buildTask(auditSign,auditStatus,
				houseStatus, num,auditStep,auditUserId,Globals.HOUSE_TYPE_RESALE,cityId);

	}
	/**
	 * 向审核任务中添加
	 * @param idList resaleId
	 * @param auditUserId 审核人
	 * @author wangjh
	 * Dec 8, 2011 9:46:56 AM
	 */
	public void buileTaskByParam(ResaleInfo info, 
			Integer auditStep,
			Integer auditUserId,AuditStep as){
		auditTaskWriteMapper.buildResaleTaskByParam(info, auditStep, auditUserId, Globals.HOUSE_TYPE_RESALE,as);
	}

	public List<Integer> getUnauditPhotoCategory(Integer houseId){
		return resaleReadMapper.getUnauditPhotoCategory(houseId);
	}

	public List<Integer> getResaleIdForAudit(String auditSign,Integer auditStatus, Integer houseStatus, Integer num,Integer cityId,String vipFlag) {
		return resaleReadMapper.getResaleIdForAudit(auditSign,
				auditStatus, houseStatus, num,cityId,vipFlag);
	}

	public void setAuditTaskResult(Integer auditStep, List<Integer> houseIdList,
			Integer result) {
		auditTaskWriteMapper.setAuditTaskResult(auditStep, houseIdList,result,Globals.HOUSE_TYPE_RESALE);		
	}

	public void updateAuditTaskUser(List<Integer> taskIdList,
			Integer auditUserId,Integer auditStep) {
		auditTaskWriteMapper.updateAuditTaskUser(taskIdList,auditUserId,auditStep);
	}

	public void updateAuditTaskStatus(List<Integer> houseIdList,
			Integer auditStep, Integer auditType, Integer auditResult) {
		auditTaskWriteMapper.updateAuditTaskStatus(houseIdList,auditStep,auditType,auditResult);
		
	}

	public List<Integer> getPassHouseIdList(List<Integer> houseIdList,
			Integer houseTypeResale,Integer passSign,Integer auditStep) {
		return auditTaskReadMapper.getPassHouseIdList(houseIdList,houseTypeResale,passSign,auditStep);
	}

	public void updateAuditStatusByIdList(List<Integer> houseIdList, Integer status,Integer houseStatus,String sign) {
	
		resaleWriteMapper.updateAuditStatusByIdList(houseIdList,status,houseStatus,sign);
	}
	
	public void deleteAuditTask(List<Integer> houseIdList, Integer auditType) {
		auditTaskWriteMapper.deleteAuditTask(houseIdList, auditType);
	}


	/**
	 * @param resaleList
	 * @return 根据房源List得到需要审查的photoList
	 */
	public List<HousePhoto> getVerifyingPhotos(List<ResaleInfo> resaleList, Integer type) {
		List<HousePhoto> housePhotos = new ArrayList<HousePhoto>();

		List<Integer> houseIds = new ArrayList<Integer>();
		for (ResaleInfo resaleInfo : resaleList) {
			houseIds.add(resaleInfo.getId());
		}
		if (CollectionUtils.isEmpty(houseIds)) {
			return housePhotos;
		}
		
		List<com.taofang.biz.domain.house.HousePhoto> photoList = new ArrayList<com.taofang.biz.domain.house.HousePhoto>();
		
		for(int resaleId: houseIds){
			List<com.taofang.biz.domain.house.HousePhoto> templist = BizServiceHelper.getHousePhotoService().getResalePhotos(resaleId);
			Resale resale = resaleReadMapper.selectResaleById(resaleId);
			if (CollectionUtils.isNotEmpty(templist)) {
				for(com.taofang.biz.domain.house.HousePhoto photo:templist){
					if(photo.getCategory()/100+1 == type){
						//用displayOrder暂存一下装修
						photo.setDisplayOrder(resale.getDecoration().intValue());
						photoList.add(photo);
					}
				}
			}
		}
		
		for (com.taofang.biz.domain.house.HousePhoto photo: photoList) {
			HousePhoto housePhoto = new HousePhoto();
			housePhoto.setmURL(photo.getResaleMediumPhotoUrl());
			housePhoto.setDirection(photo.getCategory()%10);
			housePhoto.setHouseid(photo.getHouseId());
			housePhoto.setCategory(Resources.getString("house.decoration."+photo.getDisplayOrder()));
			housePhoto.setPhotoid(photo.getId()+"");
			
			housePhotos.add(housePhoto);
		}
		return housePhotos;
	}

	public List<ResaleInfo> getMyResaleInfo(Integer type, List<Integer> houseIds) {
		return resaleReadMapper.getMyResaleInfo(houseIds, type);
	}

	/**
	 * @param resaleList
	 * @return 得到所有房源ID
	 */
	public String getVerifyHouseIdList(List<ResaleInfo> resaleInfos) {
		StringBuffer sb = new StringBuffer();
		for(ResaleInfo resaleInfo:resaleInfos){
			sb.append(","+resaleInfo.getId());
		}
		// 去掉第一个","
		if(sb.length()>1){
			return sb.substring(1, sb.length());
		}
		return sb.toString();
	}

	/**
	 * @param blockList
	 * @param j sessionUser的id
	 * <br>删除resale_photo里的数据,转移到reject_photo里,更新resale表
	 */
	public List<Integer> handleRejectPhotos(String blockList, int j) {
		String[] blockListArray = blockList.split(",");
		String sign="#_#";
		// 违规房源idList
		List<String> houseIdList = new ArrayList<String>();
		//拒绝图片的ids,返回前台计算审核通过的图片
		List<Integer> rejectPhotoIds = new ArrayList<Integer>();
		
		for (int i = 0; i < blockListArray.length; i++) {
			String[] reasonArr=blockListArray[i].split(sign);
			String reasonString=reasonArr[1];
			String[] house_photo = reasonArr[0].split("_");
			houseIdList.add(house_photo[0]);
			rejectPhotoIds.add(Integer.parseInt(house_photo[1]));
			// 拼出mURL字符串
			StringBuffer photobrowse = new StringBuffer();
			for (int k = 2; k < house_photo.length; k++) {
				photobrowse.append(house_photo[k]+"_");
			}
			
			// 删除最后一个"_"
			photobrowse.deleteCharAt(photobrowse.length()-1);
			
			// moveResale2Reject
			this.handleRejectPhoto(house_photo[0], house_photo[1], photobrowse.toString(), j,reasonString);

			// 循环更新resale表内的各种照片数量..封面图片(如果删除了封面图片)
			Integer house_id = Integer.parseInt(house_photo[0]);
			this.updateResalePhotoCount(house_id);
		}

		// 更新房源封面图片
		this.updateCoverImgList(houseIdList);
		
		return rejectPhotoIds;
		
	}
	
	public void updateCoverImgList(List<String> houseIdList) {
		try {
			if(CollectionUtils.isEmpty(houseIdList)){
				//System.out.println("ResaleDao-updateCoverImgList：houseIdList is null ");
				return;
			}
			// 得到应该更新封面的house的List
			List<ResaleInfo> updateCoverImgHouseList = new ArrayList<ResaleInfo>();
			updateCoverImgHouseList = resaleReadMapper.getUpdateCoverImgHouseList(houseIdList);
			if(null == updateCoverImgHouseList || updateCoverImgHouseList.size()<1) return ; 
			for (ResaleInfo resaleInfo : updateCoverImgHouseList) {
				
				if(resaleInfo.getInnerCount()==null){
					resaleInfo.setInnerCount(0);
				}
				if(resaleInfo.getLayoutCount()==null){
					resaleInfo.setLayoutCount(0);
				}
				if(resaleInfo.getCommunityCount()==null){
					resaleInfo.setCommunityCount(0);
				}
				// 取图片类型
				int type = 2;
				if (resaleInfo.getPhotoCount()==null || resaleInfo.getPhotoCount() == 0) {
					resaleInfo.setPhotoid(0);
					resaleInfo.setPhototiny("/s/house_s.png");
					resaleInfo.setPhotobrowse("/m/house_m.png");
					resaleWriteMapper.updateCoverImg(resaleInfo);
					continue;
				} else if (resaleInfo.getInnerCount() > 0) {// 先从室内图取
					type = 2;
				} else if (resaleInfo.getCommunityCount() > 0) {// 再从小区图
					type = 3;
				} else {// 再从户型图取
					type = 1;
				}
				//System.out.println("ResaleDao-updateCoverImgList:type="+type+",time="+new Date());
				try {
					List<HousePhoto> housePhotos = null;
					ResaleInfo tempObj = resaleReadMapper.getOneCoverImg(resaleInfo.getId(), type);
					housePhotos = getAllHousePhotoByType(tempObj.getId(), tempObj.getPhotoid(), type, tempObj.getInalbum());
					Resale info = new Resale();
					HousePhoto housePhoto = housePhotos.get(0);
					//System.out.println("ResaleDao-updateCoverImgList: housePhotos.get(0) is not null ");
					info.setId(housePhoto.getHouseid());
					info.setPhotoid(Integer.parseInt(housePhoto.getPhotoid()));
					info.setPhototiny(housePhoto.getsURL());
					info.setPhotobrowse(housePhoto.getmURL());
					resaleWriteMapper.updateCoverImg(info);
				} catch (Exception e) {
					//System.out.println("ResaleDao-updateCoverImgList:exception = "+e+",time="+new Date());
					resaleInfo.setPhotoid(0);
					resaleInfo.setPhototiny("/s/house_s.png");
					resaleInfo.setPhotobrowse("/m/house_m.png");
					resaleWriteMapper.updateCoverImg(resaleInfo);
				}
			}
		} catch (Exception e) {
			//System.out.println("ResaleDao-updateCoverImgList :" + new Date() + ",exception = "+e);
			e.printStackTrace();
			
		}
	}

	/**
	 * @param houseid
	 * @param photoid
	 * @param photobrowse 图片地址
	 * @param j sessionUser的id
	 * @param type 图片类型/户型,室内,小区,用来更新resale中图片的photo_count
	 */
	private void handleRejectPhoto(String houseid,String photoid,String photobrowse, int j,String reason) {
		try {
			resaleWriteMapper.moveResale2Reject(houseid,photoid,photobrowse,""+j,reason);
			resaleWriteMapper.deleteResalePhoto(Integer.parseInt(houseid), Integer.parseInt(photoid));
			resaleWriteMapper.updatePhotoStatus(Integer.parseInt(houseid), 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Integer> getResaleIdList(Integer auditUserId, Integer num,
			Integer auditStep, Integer houseStatus, Integer auditStatus,
			Integer cityId) {
		return auditTaskReadMapper.getHouseIdList(auditUserId,auditStep,Globals.HOUSE_TYPE_RESALE, num,cityId);
	}

	public List<ResaleInfo> getResaleFromAuditTask(
			List<Integer> resaleInfoIdList, Integer houseStatus,
			Integer auditStatus) {
		return resaleReadMapper.getResaleFromAuditTaskByIdList(resaleInfoIdList, houseStatus,auditStatus);
	}
	
	/**
	 * 在audit_task表中查询指定人员，指定审核步骤的任务
	 * @param auditUserId 审核人员
	 * @param auditStep 审核步骤（基本信息，户型图，小区图，室内图）
	 * @return
	 * @author wangjh
	 * Dec 5, 2011 10:17:39 AM
	 */
	public List<Integer> getResaleTaskIdList(Integer auditUserId, Integer auditStep ,Integer type,Integer num,Integer cityId) {
		return auditTaskReadMapper.getHouseIdListFromTask(auditUserId,auditStep,type,Globals.AUDIT_RESULT_UNDISTRIBUTED,num,cityId);
	}
	
	public List<ResaleInfo> getResaleInfoCity(List<Integer> resaleIdList){
		return resaleReadMapper.getResaleInfoCity(resaleIdList);
	}

	public List<Integer> getInconformityHouseIds(List<Integer> houseIds) {
		return resaleReadMapper.getInconformityHouseIds(houseIds);
	}
	
	/**
	 * 图片数为0的houseId
	 * @param auditStep 审核步骤
	 * @param houseIdList 房源id
	 * @return
	 * @author wangjh
	 * Dec 19, 2011 4:41:52 PM
	 */
	public List<Integer> getPhotoCountOByStep(Integer auditStep,
			List<Integer> houseIdList) {
		return resaleReadMapper.getPhotoCountOByStep(auditStep,houseIdList);
	}

		public List<AuditHistory> getHistoryInfo(List<Integer> houseIdList) {
			return resaleReadMapper.getHistoryInfo(houseIdList,Globals.HOUSE_TYPE_RESALE);
		}
		
		
		public List<SubAuditHistory> getResalePhotosByHouseListAndType(List<Integer> houseIdIntList, List<Integer> photoIdList, Integer type) {
			return resaleReadMapper.getResalePhotosByHouseListAndType(houseIdIntList, photoIdList, type);
		}

		public HistoryInfo getPhotoInfoHistory(HistoryInfo historyInfo) {
			return resaleReadMapper.getPhotoInfoHistory(historyInfo);
		}

		
		public boolean reAuditPhoto(HistoryInfo historyInfo, int result , User user) throws Exception {

		try {
			boolean updateCoverFlag = false;
			if (result == Globals.AUDIT_RESULT_PASS) {
				// 转移reject_photo到resale_photo,增加相应photo_count
				historyInfo = resaleReadMapper.getRejectPhotoReAudit(historyInfo);
				reAuditPhotoPass(historyInfo);
				//resaleWriteMapper.reAuditPhotoPass(historyInfo);
				resaleWriteMapper.deleteRejectPhoto(historyInfo.getHouseId(), historyInfo.getPhotoId(), historyInfo.getInalbum());
				// resaleWriteMapper.updateResalePhotoCountAdd(historyInfo);
			} else if (result == Globals.AUDIT_RESULT_REJECT) {
				// 转移resale_photo到reject_photo里,减少相应photo_count
				List<HousePhoto> housePhotos =getAllHousePhotoByType(historyInfo.getHouseId(), historyInfo.getPhotoId(), historyInfo.getAuditStep(),
						historyInfo.getInalbum());
				if (housePhotos.size() < 1) {
					throw new Exception("没有找到图片@ResaleDao!reAuditPhoto");
				}
				historyInfo.setHouseDescribe(housePhotos.get(0).getmURL());
				historyInfo.setAuditStep(Integer.parseInt(housePhotos.get(0).getCategory()));
				if (null != user) {
					historyInfo.setDealerId(user.getId());
				} else {
					throw new Exception("没有User传进来@ResaleDao!reAuditPhoto");
				}
				// 由于前期在建表时弄反了.
				historyInfo.setHouseType(Globals.RESALE_TYPE);
				resaleWriteMapper.reAuditPhotoReject(historyInfo);
				resaleWriteMapper.deleteResalePhoto(historyInfo.getHouseId(), historyInfo.getPhotoId());
				resaleWriteMapper.updatePhotoStatus(historyInfo.getHouseId(), 1);
				// resaleWriteMapper.updateResalePhotoCountCut(historyInfo);
				updateCoverFlag = true;

			}

			// 更新photo_count.
			this.updateResalePhotoCount(historyInfo.getHouseId());
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
			Resale resale = resaleReadMapper.selectResaleById(historyInfo.getHouseId());
			photo.setAgentId(resale.getAuthorid());
			photo.setEstateId(resale.getEstateid());
			photo.setCityId(resale.getCityid());
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
			BizServiceHelper.getHousePhotoService().storeToResale(photo);
		}

		public void updateResalePhotoCount(Integer houseId) {
			resaleWriteMapper.updateResalePhotoCount(houseId);
			
		}

		public List<HousePhoto> getAllHousePhotoByType(Integer houseid, Integer photoid, Integer type, Integer inalbum) {
			List<HousePhoto> list =  resaleReadMapper.getAllHousePhotoByType(houseid, photoid, type, inalbum);
			if(CollectionUtils.isNotEmpty(list)){
				for(HousePhoto rphoto:list){
					com.taofang.biz.domain.house.HousePhoto photo = BizServiceHelper.getHousePhotoService().getResalePhoto(Integer.valueOf(rphoto.getPhotoid()));
					rphoto.setlURL(photo.getResaleLargePhotoUrl());
					rphoto.setmURL(photo.getResaleMediumPhotoUrl());
					rphoto.setsURL(photo.getResaleSmallPhotoUrl());
				}
			}
			return list;
		}

		public Integer getUnclaimedAwaitHouseAuditCount(Integer houseStatus,
				Integer auditStatus) {
			return resaleReadMapper.getUnclaimedAwaitHouseAuditCount(houseStatus,auditStatus);
		}
		public Integer getPhotoCountByHouseIdList(List<Integer> list,
				Integer auditStep) {
			return resaleReadMapper.getPhotoCountByHouseIdList(list,auditStep);
		}

		public Integer getPhotoAuditUnclaimedAwaitCount(Integer houseStatus,
				Integer auditStatus, Integer auditStep) {
			return resaleReadMapper.getPhotoAuditUnclaimedAwaitCount(
					houseStatus, auditStatus,auditStep);
		}

		public List<HousePhoto> getAllHousePhoto(Integer houseid) {
			return getHousePhotoByResaleId(houseid);
		}

		public int  updateHouseScore(Integer houseid, Double score) {
			return resaleWriteMapper.updateHouseScore(houseid,score);
			
		}

		public int unshelveAll(int agentId) {
			return resaleWriteMapper.unshelveAll(agentId);
			
		}

		public List<ResaleInfo> getPhotoExtraInfo(int auditType, List<Integer> houseIdList) {
			return resaleReadMapper.getPhotoExtraInfo(auditType,houseIdList);
		}
		
		private List<HousePhoto> getHousePhotoByResaleId(int rentId){
			List<HousePhoto> housePhotoList = new ArrayList<HousePhoto>();
			List<com.taofang.biz.domain.house.HousePhoto> bphotoList = BizServiceHelper.getHousePhotoService().getResalePhotos(rentId);
			if(CollectionUtils.isNotEmpty(bphotoList)){
				for(com.taofang.biz.domain.house.HousePhoto bphoto: bphotoList ){
					HousePhoto housePhoto = new HousePhoto();
					
					housePhoto.setCategory(bphoto.getCategory()/100+1+"");
					housePhoto.setInalbum(0);
					housePhoto.setInnertype(0);
					housePhoto.setCover(bphoto.getCoverFlag()=="Y"?1:0);
					housePhoto.setPhotoid(bphoto.getId()+"");
					housePhoto.setlURL(bphoto.getResaleLargePhotoUrl());
					housePhoto.setmURL(bphoto.getResaleMediumPhotoUrl());
					housePhoto.setsURL(bphoto.getResaleSmallPhotoUrl());
					housePhotoList.add(housePhoto);
				}
			}
			return housePhotoList;
		}

		public Resale getDecorationByPhotoId(Integer photoId) {
			return resaleReadMapper.getDecorationByPhotoId(photoId);
		}

		public void updateStore(Integer oldStoreId, Integer newStoreId,String storeName) {
			resaleWriteMapper.updateStore(oldStoreId,newStoreId,storeName);			
		}

		public void updateCompanyName(Integer oldCompanyId, Integer newCompanyId, String companyName) {
			resaleWriteMapper.updateCompanyName(oldCompanyId, newCompanyId,companyName);	
			
		}

		public void updateStoreNameByStoreId(int id,String storeName) {
			resaleWriteMapper.updateStoreNameByStoreId(id,storeName);	
			
		}
		
		public void updateLocaleByEstate(EstateInfo estateInfo){
			resaleWriteMapper.updateLocaleByEstate(estateInfo);	
		}

		public void updateEstateNameByEstateId(EstateInfo estateInfo) {
			resaleWriteMapper.updateEstateNameByEstateId(estateInfo);	
			
		}

}
