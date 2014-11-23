package com.nuoshi.console.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.taofang.biz.domain.house.HousePhoto;
import com.taofang.biz.service.house.IEstatePhotoService;
import com.taofang.biz.service.house.IHousePhotoService;
import com.taofang.biz.util.BizServiceHelper;
import com.nuoshi.console.domain.estate.EstateChangeMessage;
import com.nuoshi.console.jms.EstateJms;
import com.nuoshi.console.persistence.read.taofang.estate.LayoutReadMapper;
import com.nuoshi.console.persistence.write.taofang.estate.EstateMd5WriteMapper;
import com.nuoshi.console.persistence.write.taofang.estate.EstateWriteMapper;
import com.nuoshi.console.persistence.write.taofang.estate.LayoutWriteMapper;
import com.nuoshi.console.view.EstatePhotoCondition;
import com.nuoshi.console.view.EstatePhotoDetail;

@Service
@SuppressWarnings("unchecked")
public class LayoutService {
	private static Logger logger = Logger.getLogger(CommPhotoService.class);

	@Resource
	private LayoutReadMapper layoutReadMapper;

	@Resource
	private LayoutWriteMapper layoutWriteMapper;
	@Resource
	private EstateWriteMapper estateWriteMapper;
	
	@Resource
	private EstateMd5WriteMapper estateMd5WriteMapper;
	private static final String HOUSE_STATUS = "1";
	private static final String PHOTO_CATEGORY = "3";
	private static final Integer DEFAULT_ORDER_VALUE = 0; // 默认图片的order值
	
	
	private IEstatePhotoService iEstatePhotoService = BizServiceHelper.getEstatePhotoService();
	
	private IHousePhotoService iHousePhotoService = BizServiceHelper.getHousePhotoService();

	/**
	 * 统计备选库图片数量
	 * 
	 * @param map
	 * @return
	 */
	public int countbackPhoto(int estateId) {
		int count = 0;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("houseStatus", HOUSE_STATUS);
		paramMap.put("estateId", estateId);
		List<Integer[]> photoCountList = layoutReadMapper.countbackPhoto(paramMap);
		return count;
	}

	public List<EstatePhotoDetail> getLayoutPhotoByPage(EstatePhotoCondition condition) {
		List<EstatePhotoDetail> list = layoutReadMapper.getLayoutPhotoList(condition);
		if(list != null && list.size() > 0){
			for(EstatePhotoDetail lphoto:list){
			 HousePhoto photo  = iEstatePhotoService.getPhoto(lphoto.getId());
			 lphoto.setlPhoto(photo.getEstateLargePhotoUrl());
			 lphoto.setmPhoto(photo.getEstateMediumPhotoUrl());
			 lphoto.setsPhoto(photo.getEstateSmallPhotoUrl());
			}
		}
		return list != null && list.size() > 0 ? list : Collections.EMPTY_LIST;
	}

	public List<EstatePhotoDetail> getBackupLayoutPhoto(EstatePhotoCondition condition) {
		List<EstatePhotoDetail> list = layoutReadMapper.getBackupLayoutPhoto(condition);
		if(list != null && list.size() > 0){
			for(EstatePhotoDetail lphoto:list){
			 HousePhoto photo  = iHousePhotoService.getResalePhoto(lphoto.getId());
			 lphoto.setlPhoto(photo.getResaleLargePhotoUrl());
			 lphoto.setmPhoto(photo.getResaleMediumPhotoUrl());
			}
		}
		return list != null && list.size() > 0 ? list : Collections.EMPTY_LIST;
	}
	
	public Integer countBackupLayoutPhoto(EstatePhotoCondition condition) {
		return layoutReadMapper.countBackupLayoutPhoto(condition);
	}

	/**
	 * 将备选库图片移入精选库.
	 * 
	 * @param toUsingIds
	 */
	public String batchAddEstatePhoto(List idList, List unUsingIdList, Integer estateId, Integer delNum) {
		int layoutcnt = 0;
		String errorStr = null; 
		if (idList != null && idList.size() > 0) {
			//layoutWriteMapper.batchAddEstatePhoto(idList);
			
			//获得photoId;
			//生成图片md5值
			//将图片标为已用状态.usestatus = 3;
			
			errorStr = batchDelBackupPhoto(idList);
			layoutcnt += idList.size();
		}
		
		
		
		
		if (estateId != null) {
			estateWriteMapper.updateBacupAndLayoutNum(estateId,layoutcnt, -layoutcnt,0,0);
			EstateJms.send(estateId, EstateChangeMessage.ChangeStatus.Modify);
		}
		return errorStr;
	}

	/**
	 * 从备份库中删除数据
	 * 
	 * @param idList
	 * @param estateId
	 */
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
			estateWriteMapper.updateBacupAndLayoutNum(estateId,0, idList.size(), 0,0);
			EstateJms.send(estateId, EstateChangeMessage.ChangeStatus.Modify);
		}
	}

	/**
	 * 将数据从备选选库中复制到精选库 备选库标志设为N
	 * 
	 * @param toUsingIds
	 */
	public String batchDelBackupPhoto(List idList) {
	//	layoutWriteMapper.batchDelBackupPhoto(idList);
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
	 * 将数据从精选库中删除
	 * 
	 * @param toUsingIds
	 */
	public void batchDelLayoutPhoto(Integer estateId, List idList, String category) {
		if (idList != null && idList.size() > 0) {
			for(int i=0;i<idList.size();i++){
				int id=(Integer) idList.get(i);
				iEstatePhotoService.eraseEstatePhoto(id,estateId);
			}
			estateWriteMapper.updateBacupAndLayoutNum(estateId, -idList.size(), 0,0,0);
			EstateJms.send(estateId, EstateChangeMessage.ChangeStatus.Modify);
		}

	}

	/**
	 * 设置默认的户型图
	 * 
	 * @param id
	 */
	public void setDefaultLayoutPhoto(Integer id, String sourceOrder) {
		// 获得之前的默认图片
		/**
		EstatePhotoDetail defaultPhoto = layoutReadMapper.getEstatePhotoByOrder(DEFAULT_ORDER_VALUE);
		Map oldDefaultPhotoMap = new HashMap();
		if (defaultPhoto != null) {
			oldDefaultPhotoMap.put("id", defaultPhoto.getId());
			oldDefaultPhotoMap.put("order", sourceOrder);
			layoutWriteMapper.updateOrderById(oldDefaultPhotoMap);
		}
		*/
		// 设置默认图片.
		Map newDefaultPhotoMap = new HashMap();
		newDefaultPhotoMap.put("id", id);
		//newDefaultPhotoMap.put("order", DEFAULT_ORDER_VALUE);
		layoutWriteMapper.updateOrderById(newDefaultPhotoMap);
	}


	/**
	 * 统计户型图数量
	 * 
	 * @param estateId
	 * @return
	 */
	public int countLayoutNum(Integer estateId) {
		return layoutReadMapper.countLayoutNum(estateId);
	}

	
}
