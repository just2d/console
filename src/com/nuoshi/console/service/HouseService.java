package com.nuoshi.console.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.nuoshi.console.common.Globals;
import com.nuoshi.console.common.util.StrUtils;
import com.nuoshi.console.domain.base.House;
import com.nuoshi.console.domain.rent.Rent;
import com.nuoshi.console.domain.resale.Resale;
import com.nuoshi.console.persistence.write.taofang.photo.RejectPhotoWriteMapper;


public abstract class HouseService extends BaseService  {
	@Resource
	RejectPhotoWriteMapper rejectPhotoWriteMapper;
	@Resource
	EstateService estateService;
	
	public Double getHouseScore(House house) {
		//自己发布的房源基础分（必填项18分）
		return getHouseScore(house,  20.00);
	}
	/**
	 * 
	 * @param house
	 * @param baseScore 基础分，如必填项 18分
	 * @return
	 */
	public Double getHouseScore(House house,double baseScore) {

		if (house == null) {
			return 0.0;
		}
		Double score = 0.0;
		Double biTianScore = baseScore; // 必填
		// 根据标题字数算分
		int titleCount = StrUtils.textCounter(house.getTitle());
		if (titleCount >= 2) {
			if (titleCount <= 5) {
				biTianScore += 1;
			} else {
				if (titleCount <= 15) {
					biTianScore += 5;
				} else {

					if (titleCount <= 26) {
						biTianScore += 8;
					} else {
						biTianScore += 5;
					}
				}
			}
		}
		// 根据房源描述字数算分
		int extInfoCount = StrUtils.textCounter(StrUtils.Html2Text(house.getExtinfo()));

		if (extInfoCount >= 50) {
			if (extInfoCount <= 150) {
				biTianScore += 3;
			} else {
				 if(extInfoCount<=500){
					 biTianScore += 8;
				 }else{
					 biTianScore += 5;
				 }
			}
		}

		//根据段落设置房源分数
		int paragraphCount = StrUtils.getParagraphCount(house.getExtinfo());
		
		if(paragraphCount>=1){
			if(paragraphCount<=2){
				biTianScore += 4;
			}else{
				if(paragraphCount<=5){
					biTianScore += 8;
				}else{
					biTianScore += 5;
				}
			}
			
		}
		
		
		Set<Integer> innerTypes = new HashSet<Integer>();
		boolean hasLayoutPhoto = false;
		int layoutNum = 0;
		int innerNum = 0;
		int ownerCommunityNum = 0;
		int communityNum = 0 ;
		int purePhotoNum = 0;
		boolean setCover = false; //是否手动设置了封面
		int directionScore = 0; //是否手动设置了封面
		
		List<com.taofang.biz.domain.house.HousePhoto> housePhotoInfoList = null;
		if(house.getId()!=null){
				housePhotoInfoList = this.getAllHousePhoto(house.getId());
			if (housePhotoInfoList != null) {
				for (com.taofang.biz.domain.house.HousePhoto housePhotoInfo : housePhotoInfoList) {
					if (housePhotoInfo.getRefId() <= 0&&"Y".equals(housePhotoInfo.getHdFlag())) {
						purePhotoNum++;
					}
					if (StringUtils.isNotEmpty(housePhotoInfo.getCoverFlag()) && housePhotoInfo.getCoverFlag().equalsIgnoreCase("Y")) {
						setCover = true;
					}
					if (housePhotoInfo.getCategory() >= 1 && housePhotoInfo.getCategory() <= 99) {
						if ((housePhotoInfo.getCategory() % 10) > 0) {
							directionScore += 1;
						}
						hasLayoutPhoto = true;
						layoutNum++;
					}
					if (housePhotoInfo.getCategory() >= 100 && housePhotoInfo.getCategory() <= 199) {
						innerNum++;
						if ((housePhotoInfo.getCategory() % 10) > 0) {
							innerTypes.add(housePhotoInfo.getCategory() % 10);
						}
					}
					if (housePhotoInfo.getCategory() >= 200 && housePhotoInfo.getCategory() <= 250) {

						if (housePhotoInfo.getRefId() > 0) {
							communityNum++;
						} else {
							ownerCommunityNum++;
						}
					}
				}
			}
		
		}
		double photoScore = 0 ;
		
		//室内图（14分）

		if(innerNum>=1){
			if(innerNum==1){
				photoScore += 3;
			}
			if(innerNum==2){
				photoScore += 6;
			}
			if(innerNum==3){
				photoScore += 9;
			}
			if(innerNum==4){
				photoScore += 12;
			}
			if(innerNum==5){
				photoScore += 13;
			}
			if(innerNum>=6){
				photoScore += 14;
			}
		}
		
		//小区图（8分）
		if(ownerCommunityNum>=4){
			photoScore += 8;
		}else{
			photoScore+=ownerCommunityNum*2;
			if(communityNum<=(4-ownerCommunityNum)){
				photoScore+=communityNum*2*0.8;
			}else{
				photoScore+=(4-ownerCommunityNum)*2*0.8;
			}
		}

		
		//有户型图
		if(hasLayoutPhoto){
			photoScore += 5;
		}
		//设置封面分
		if(setCover){
			photoScore += 3 ;
		}
		//清晰度（12分）
		if(purePhotoNum>=11){
			photoScore += 12 ;
		}else{
			photoScore += purePhotoNum ;
		}
		//室内图类别选择（4分）

		if(innerTypes.size()<=4){
			photoScore += innerTypes.size() ;
		}else{
			photoScore += 4 ;
		}
		
		//房源描述字数50-150字，段落1-2段 →   图片得80%分
		if(paragraphCount<=2&&extInfoCount<=150){
			photoScore = photoScore*0.8 ;
		}
		score += photoScore+directionScore;
		// 选填项
		score += house.getOptionNum()*2;
		//图片小于3必填为80%
		if(housePhotoInfoList==null||housePhotoInfoList.size()<=3){
			score += biTianScore*0.8;
		}else{
			score += biTianScore ;
		}
		if(score>100){
			score = 100.00 ;
		}

		return score;
	
	}
	
	/**
	 * 获取平均分
	 * @param house
	 * @return
	 * @author wangjh
	 * Feb 22, 2012 1:42:31 PM
	 */
	public Double getAvgPrice(House house){
		Integer estateId=house.getEstateid();
		if(estateId==null){
			log.info("小区id是空！");
			return 0.0;
		}
		Double avgPrice=0.0;
		//月份
		String month=this.getNowOfLastMonth();
		//获取平均分
		if(house instanceof Rent){
			avgPrice=estateService.getHouseAvgPriceByType(estateId, Globals.RENT_TYPE, month);
		}
		if(house instanceof Resale){
			avgPrice=estateService.getHouseAvgPriceByType(estateId, Globals.RESALE_TYPE, month);
		}
		return avgPrice;
		
	}
	/**
	 * 获取上个月“yyyy-MM”
	 * 
	 * @author wangjh
	 * Feb 22, 2012 1:41:48 PM
	 */
	 public String getNowOfLastMonth() {
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
	        GregorianCalendar calendar = new GregorianCalendar();
	        calendar.set(Calendar.MONTH, calendar
	                .get(Calendar.MONTH) - 1);
	        String nowOfLastMonth = format
	                .format(calendar.getTime());
	        return nowOfLastMonth;
	    }
	
	/**
	 * 审核时选择房源是否有朝向
	 * @param status
	 * @param photoId
	 * @author wangjh
	 * Feb 21, 2012 3:42:15 PM
	 */
	public void updateDirection(List<Integer> passIdList, List<Integer> noPassIdList){
		if(CollectionUtils.isNotEmpty(passIdList)){
			rejectPhotoWriteMapper.updateDirection(passIdList, Globals.HAVE_DIRECTION);
		}
		if(CollectionUtils.isNotEmpty(noPassIdList)){
			rejectPhotoWriteMapper.updateDirection(noPassIdList, Globals.NO_DIRECTION);
		}
	}
	
	abstract List<com.taofang.biz.domain.house.HousePhoto> getAllHousePhoto(Integer id) ;
}
