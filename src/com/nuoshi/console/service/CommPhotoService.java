package com.nuoshi.console.service;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.taofang.biz.domain.house.HousePhoto;
import com.taofang.biz.service.house.IEstatePhotoService;
import com.taofang.biz.service.house.IHousePhotoService;
import com.taofang.biz.util.BizServiceHelper;
import com.nuoshi.console.domain.estate.EstateChangeMessage;
import com.nuoshi.console.jms.EstateJms;
import com.nuoshi.console.persistence.read.taofang.estate.CommPhotoReadMapper;
import com.nuoshi.console.persistence.write.taofang.estate.CommPhotoWriteMapper;
import com.nuoshi.console.persistence.write.taofang.estate.EstateMd5WriteMapper;
import com.nuoshi.console.persistence.write.taofang.estate.EstateWriteMapper;
import com.nuoshi.console.view.EstatePhotoCondition;
import com.nuoshi.console.view.EstatePhotoDetail;

@Service
@SuppressWarnings("unchecked")
public class CommPhotoService {

	private static Logger logger = Logger.getLogger(CommPhotoService.class);
	@Resource
	private CommPhotoReadMapper commPhotoReadMapper;
	@Resource
	private CommPhotoWriteMapper commPhotoWriteMapper;
	@Resource
	private EstateWriteMapper estateWriteMapper;
	@Resource
	private EstateMd5WriteMapper estateMd5WriteMapper;

//	private static final String HOUSE_STATUS = "1";
//	private static final String PHOTO_CATEGORY = "1";
//	private static final Integer DEFAULT_ORDER_VALUE = 0; // 默认图片的order值

	private IEstatePhotoService iEstatePhotoService = BizServiceHelper.getEstatePhotoService();
	
	private IHousePhotoService iHousePhotoService = BizServiceHelper.getHousePhotoService();
	/**
	 * 获得小区图.
	 * 
	 * @param condition
	 * @return
	 */
	public List<EstatePhotoDetail> getCommPhotoList(EstatePhotoCondition condition) {
		List<EstatePhotoDetail> list = commPhotoReadMapper.getCommPhotoList(condition);
		if(list != null && list.size() > 0){
			for(EstatePhotoDetail ephoto:list){
				HousePhoto photo = iEstatePhotoService.getPhoto(ephoto.getId());
				ephoto.setlPhoto(photo.getEstateLargePhotoUrl());
				ephoto.setmPhoto(photo.getEstateMediumPhotoUrl());
				ephoto.setsPhoto(photo.getEstateSmallPhotoUrl());
			}
		}
		return list != null && list.size() > 0 ? list : Collections.EMPTY_LIST;
	}

	/**
	 * 获得指定小区的备选库中的小区图.
	 * 
	 * @param condition
	 * @return
	 */
	public List<EstatePhotoDetail> getBackupCommPhoto(EstatePhotoCondition condition) {
		List<EstatePhotoDetail> list = commPhotoReadMapper.getBackupCommPhoto(condition);
		if( list != null && list.size() > 0 ){
			for(EstatePhotoDetail backPhoto:list){
				HousePhoto photo = iHousePhotoService.getResalePhoto(backPhoto.getId());
				backPhoto.setmPhoto(photo.getResaleMediumPhotoUrl());
				backPhoto.setlPhoto(photo.getResaleLargePhotoUrl());
			}
		}
		
		return list != null && list.size() > 0 ? list : Collections.EMPTY_LIST;
	}
	
	public Integer countBackupCommPhoto(EstatePhotoCondition condition) {
		return commPhotoReadMapper.countBackupCommPhoto(condition);
	}

	/**
	 * 将备选库图片移入精选库.
	 * 
	 * @param toUsingIds
	 */
	@SuppressWarnings("rawtypes")
	public String batchAddEstatePhoto(List idList, List unUsingIdList, Integer estateId,Integer delNum) {
		int layoutcnt = 0;
		String errorStr = null; 
		if (idList != null && idList.size() > 0) {
			//commPhotoWriteMapper.batchAddEstatePhoto(idList);
			//获得photoId;
			//生成图片md5值
			//将图片标为已用状态.usestatus = 3;
			
			
			errorStr = batchDelBackupPhoto(idList);
			layoutcnt += idList.size();
		}
		
		if (estateId != null) {
			estateWriteMapper.updateBacupAndLayoutNum(estateId,0,0, layoutcnt,-layoutcnt);
			EstateJms.send(estateId, EstateChangeMessage.ChangeStatus.Modify);
		}
		return errorStr;
	}
	

	/**
	 * 将数据从备选选库中复制到精选库 备选库标志设为N
	 * 
	 * @param toUsingIds
	 */
	@SuppressWarnings("rawtypes")
	public String batchDelBackupPhoto(List idList) {
		//commPhotoWriteMapper.batchDelBackupPhoto(idList);
		boolean flag = false;
		if (idList != null && idList.size() > 0) {
			for(int i=0;i<idList.size();i++){
				int id=(Integer) idList.get(i);
				try {
					iEstatePhotoService.copyFromResale(id);
				} catch (Exception e) {
					flag = true;
					System.out.println(e.getMessage());
					logger.error("复制图片到精选库时出错："+e.getMessage());
				}
				HousePhoto photo = new HousePhoto();
				photo.setId(id);
				photo.setReserveFlag("N");
				iHousePhotoService.updateResalePhoto(photo);
			}
		}
		if(flag) return "图片已存在于精选库，无须再次移入";
		return null;
	}

	/**
	 * 从备份库中删除数据
	 * 
	 * @param idList
	 * @param estateId
	 */
	@SuppressWarnings("rawtypes")
	public void delFromBackupPhoto(List idList, Integer estateId) {
		if (idList != null && idList.size() > 0) {
			for(int i=0;i<idList.size();i++){
				int id=(Integer) idList.get(i);
				HousePhoto photo = new HousePhoto();
				photo.setId(id);
				photo.setReserveFlag("N");
				iHousePhotoService.updateResalePhoto(photo);
			}
		}
		if (estateId != null) {
			estateWriteMapper.updateBacupAndLayoutNum(estateId,0,0,0, idList.size());
			EstateJms.send(estateId, EstateChangeMessage.ChangeStatus.Modify);
		}
	}

	/**
	 * 将数据从精选库中移动到备选库中
	 * 
	 * @param toUsingIds
	 */
	@SuppressWarnings("rawtypes")
	public void batchDelLayoutPhoto(Integer estateId, List idList, String category) {
		if (idList != null && idList.size() > 0) {
			for(int i=0;i<idList.size();i++){
				int id=(Integer) idList.get(i);
				iEstatePhotoService.eraseEstatePhoto(id,estateId);
			}
			
			estateWriteMapper.updateBacupAndLayoutNum(estateId,0,0, -idList.size(), 0);
			EstateJms.send(estateId, EstateChangeMessage.ChangeStatus.Modify);
		}

	}

	/**
	 * 设置默认的户型图
	 * 
	 * @param id
	 */
	public void setDefaultLayoutPhoto(Integer id, String sourceOrder) {
		
		
		/**
			//流程错误
		// 获得之前的默认图片
		EstatePhotoDetail defaultPhoto = commPhotoReadMapper.getCommPhotoByOrder(DEFAULT_ORDER_VALUE);
		Map oldDefaultPhotoMap = new HashMap();
		if (defaultPhoto != null) {
			oldDefaultPhotoMap.put("id", defaultPhoto.getId());
			oldDefaultPhotoMap.put("order", sourceOrder);
			commPhotoWriteMapper.updateOrderById(oldDefaultPhotoMap);
		}
		// 设置默认图片.
		Map newDefaultPhotoMap = new HashMap();
		newDefaultPhotoMap.put("id", id);
		newDefaultPhotoMap.put("order", DEFAULT_ORDER_VALUE);
		commPhotoWriteMapper.updateOrderById(newDefaultPhotoMap);
		*/
	}

	/**
	 * 统计小区图数量
	 * 
	 * @param estateId
	 * @return
	 */
	public int countCommNum(Integer estateId) {
		return commPhotoReadMapper.countCommNum(estateId);
	}


	
}
