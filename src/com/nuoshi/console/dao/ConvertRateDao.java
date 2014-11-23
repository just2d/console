package com.nuoshi.console.dao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.taofang.biz.util.BizServiceHelper;
import com.nuoshi.console.domain.agent.HousePhoto;
import com.nuoshi.console.domain.rent.RentInfo;
import com.nuoshi.console.domain.resale.ResaleInfo;
import com.nuoshi.console.domain.stat.AuditSearch;
import com.nuoshi.console.domain.stat.ConvertRate;
import com.nuoshi.console.domain.stat.HouseQuality;
import com.nuoshi.console.domain.stat.PhotoAuditStatis;
import com.nuoshi.console.domain.stat.RateSearch;
import com.nuoshi.console.domain.topic.Locale;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.persistence.read.admin.audit.AuditTaskReadMapper;
import com.nuoshi.console.persistence.read.admin.auditHistory.AuditHistoryReadMapper;
import com.nuoshi.console.persistence.read.admin.user.UserMapper;
import com.nuoshi.console.persistence.read.stat.ConvertRateReadMapper;
import com.nuoshi.console.persistence.read.taofang.rent.RentReadMapper;
import com.nuoshi.console.persistence.read.taofang.resale.ResaleReadMapper;
import com.nuoshi.console.persistence.write.admin.auditHistory.AuditHistoryWriteMapper;
import com.nuoshi.console.service.LocaleService;

@Repository
public class ConvertRateDao {
	protected static Logger LOG = Logger.getLogger(ConvertRateDao.class);
	@Resource
	ConvertRateReadMapper convertRateReadMapper;

	@Resource
	AuditTaskReadMapper auditTaskReadMapper;
	
	@Resource
	AuditHistoryReadMapper auditHistoryReadMapper;
	
	@Resource
	AuditHistoryWriteMapper auditHistoryWriteMapper;

	@Resource
	ResaleReadMapper resaleReadMapper;

	@Resource
	RentReadMapper rentReadMapper;
	
	@Resource
	UserMapper userMapper;	
	
	private String parseDate(String type){
		SimpleDateFormat sdf = new SimpleDateFormat(type);
		String date = sdf.format(new Date());
		return date;
	}
	
	public static void main(String args[]){
		ConvertRateDao c = new ConvertRateDao();
		System.out.println(c.parseDate("yyyy-MM-dd"));
	}
	

	public List<List<HouseQuality>> getAuditHouseQualityList(HouseQuality hq,String userName){		
		if(hq==null)return null;
		Map<Integer, Locale> map  = LocaleService.getLocalesPool();
		long l = System.currentTimeMillis();
//		long dl = l - 7*24*60*60*1000;
		Timestamp ts = new Timestamp(l);
		List<HouseQuality> list =  new ArrayList<HouseQuality>();//统计城市列表
		List<HouseQuality> other = new ArrayList<HouseQuality>();//非统计城市
		List<HouseQuality> result = new ArrayList<HouseQuality>(); //最终更新数据库的结果集
		List<List<HouseQuality>> listReturn = new ArrayList<List<HouseQuality>>();//最终返回结果
		List<Integer> rentList = new ArrayList<Integer>();
		List<Integer> resaleList = new ArrayList<Integer>();
		final String cityTempId = hq.getCityId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startStr = hq.getSearchStartDate();
		String endStr = hq.getSearchEndDate();
		Date start = null;
		Date end = null;
		try {
			if(StringUtils.isNotBlank(startStr)){
				start = sdf.parse(startStr);
			}else{
				start = new Date();
			}
			if(StringUtils.isNotBlank(endStr)){
				end = sdf.parse(endStr);
			}else{
				end = new Date();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		Calendar s = Calendar.getInstance();
		Calendar e = Calendar.getInstance();
		s.setTime(start);
		e.setTime(end);
		e.add(Calendar.DAY_OF_MONTH, 1);
		do{	
			hq.setCityId(cityTempId);
			hq.setAuditDate(sdf.format(s.getTime()));
			if(StringUtils.isNotBlank(hq.getCityId()) && !"0".equals(hq.getCityId())){
				List<HouseQuality> temp0 = auditHistoryReadMapper.getAuditHouseQualityList(hq);
				if(temp0!=null && temp0.size()>0 )list.addAll(temp0);
			}else{
//				if (StringUtils.isBlank(hq.getAuditUserId())) {
					hq.setCityId("1");
					List<HouseQuality> temp1 = auditHistoryReadMapper.getAuditHouseQualityList(hq);
					if (temp1 != null && temp1.size() > 0) list.addAll(temp1);
					hq.setCityId("2");
					List<HouseQuality> temp2 = auditHistoryReadMapper.getAuditHouseQualityList(hq);
					if (temp2 != null && temp2.size() > 0) list.addAll(temp2);
					hq.setCityId("3");
					List<HouseQuality> temp3 = auditHistoryReadMapper.getAuditHouseQualityList(hq);
					if (temp3 != null && temp3.size() > 0) list.addAll(temp3);
					hq.setCityId("4");
					List<HouseQuality> temp4 = auditHistoryReadMapper.getAuditHouseQualityList(hq);
					if (temp4 != null && temp4.size() > 0)	list.addAll(temp4);
					hq.setCityId("102");
					List<HouseQuality> temp102 = auditHistoryReadMapper.getAuditHouseQualityList(hq);
					if (temp102 != null && temp102.size() > 0) list.addAll(temp102);
	//			}else{
	//				hq.setCityId(null);
	//				list = auditHistoryReadMapper.getAuditHouseQualityList(hq);
	//			}
			}
			List<HouseQuality> tempOther = auditHistoryReadMapper.getAuditHouseQualityOtherList(hq);
			if(tempOther!=null && tempOther.size()>0) other.addAll(tempOther);
			s.add(Calendar.DAY_OF_MONTH, 1);
			System.out.println("<><><><><><><><><><> "+hq.getAuditDate());
		}while(s.before(e));
		
		if(list!=null && list.size()>0){
			for(HouseQuality obj : list){
				String cityId = obj.getCityId();
				if(StringUtils.isNotBlank(cityId)){
					Locale local = map.get(Integer.parseInt(cityId));
					if(local!=null)obj.setCityName(local.getName());
				}
				if(obj.getReAuditStatus()!=0){
					obj.setAuditStatus(obj.getReAuditStatus());
				}
				obj.setEntryTime(ts);
				//设置房源图片
				if(obj.getHouseType()!=0){
					if(obj.getHouseType() == 1){
						this.setRentPhotos(obj);
						rentList.add(Integer.parseInt(obj.getHouseId()));
					}else{
						this.setResalePhotos(obj);
						resaleList.add(Integer.parseInt(obj.getHouseId()));
					}
					
				}
				
			}
			result.addAll(list);
			
			
		}
		
		if(other!=null && other.size()>0){
			for(HouseQuality obj : other){
				String cityId = obj.getCityId();
				if(StringUtils.isNotBlank(cityId)){
					Locale local = map.get(Integer.parseInt(cityId));
					if(local!=null)obj.setCityName(local.getName());
				}
				if(obj.getReAuditStatus()!=0){
					obj.setAuditStatus(obj.getReAuditStatus());
				}
				obj.setEntryTime(ts);
				//设置房源图片
				if(obj.getHouseType()!=0){
					if(obj.getHouseType() == 1){
						this.setRentPhotos(obj);
						rentList.add(Integer.parseInt(obj.getHouseId()));
					}else{
						this.setResalePhotos(obj);
						resaleList.add(Integer.parseInt(obj.getHouseId()));
					}
					
				}
			}
			result.addAll(other);
			
		}
		
//		//每天执行一次 更新
//		String dateStr = this.parseDate("yyyy-MM-dd");
//		if(!dateMap.containsKey(dateStr)){
//			dateMap.clear();
//			dateMap.put(dateStr, null);
//			auditHistoryWriteMapper.clearAuditHistoryUsingFlag(new Timestamp(dl));//首先清除主表using_flag
//			auditHistoryWriteMapper.deleteAuditHouseQuality(new Timestamp(dl));//删除超过一周的记录
//		}
		
		if(result.size()>0){
			auditHistoryWriteMapper.updateAuditHouseQualityUsingFlag(result,userName);//更新主表using_flag
			auditHistoryWriteMapper.addAuditHouseQuality(result,userName);//添加到历史表
		}
		//获取房源的基本信息描述
		Map<String,Object> mapT = new HashMap<String,Object>();
		if(resaleList.size()>0){
			List<ResaleInfo>  resaleResult = resaleReadMapper.selectResaleInfoByIds(resaleList);
			if(resaleResult!=null && resaleResult.size()>0){
				for(ResaleInfo ri : resaleResult){
					mapT.put(ri.getId()+"_2",ri);
				}
			}
		}
		if(rentList.size()>0){
			List<RentInfo>  rentResult = rentReadMapper.selectRentInfoByIds(rentList);
			if(rentResult!=null && rentResult.size()>0){
				for(RentInfo ri : rentResult){
					mapT.put(ri.getId()+"_1", ri);
				}
			}
		}
		//将房源基本信息赋值到结果集列表中
		if(list!=null && list.size()>0){
			for(HouseQuality obj : list){
				if(obj.getHouseType()==1){
					obj.setRent((RentInfo)mapT.get(obj.getHouseId()+"_1"));
				}else{
					obj.setResale((ResaleInfo)mapT.get(obj.getHouseId()+"_2"));
				}
				
			}			
		}
		
		if(other!=null && other.size()>0){
			for(HouseQuality obj : other){
				if(obj.getHouseType()==1){
					obj.setRent((RentInfo)mapT.get(obj.getHouseId()+"_1"));
				}else{
					obj.setResale((ResaleInfo)mapT.get(obj.getHouseId()+"_2"));
				}
			}
		}
		
		//将结果集返回
		listReturn.add(list);
		listReturn.add(other);
		return listReturn;
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
	
	private void setResalePhotos(HouseQuality hq){		
			int id = Integer.parseInt(hq.getHouseId());
			List<HousePhoto> housePhotoes = getHousePhotoByResaleId(id);
//			ResaleInfo resale = resaleReadMapper.selectResaleInfoById(id);
//			if(resale==null)resale = new ResaleInfo();
//			hq.setResale(resale);
			
			//普通图
			List<HousePhoto> houseImgs = new ArrayList<HousePhoto>();
			List<HousePhoto> estateImgs = new ArrayList<HousePhoto>();
			List<HousePhoto> roomImgs = new ArrayList<HousePhoto>();
			for (HousePhoto housePhoto : housePhotoes) {
				if(housePhoto.getCategory().equals("1")){
					houseImgs.add(housePhoto);
					
				}else if(housePhoto.getCategory().equals("2")){
					roomImgs.add(housePhoto);
					
				}else if(housePhoto.getCategory().equals("3")){
					estateImgs.add(housePhoto);
				}
			}
			
			hq.setEstateImgUrls(estateImgs);
			hq.setLayoutImgUrls(houseImgs);
			hq.setInnerImgUrls(roomImgs);
	}
	
	private void setRentPhotos(HouseQuality hq){
		int id = Integer.parseInt(hq.getHouseId());
		List<HousePhoto> housePhotoes = getHousePhotoByRentId(id);
//		RentInfo rent = rentReadMapper.selectRentInfoById(id);
//		if(rent==null)rent = new RentInfo();
//		hq.setRent(rent);
		// 普通图
		List<HousePhoto> houseImgs = new ArrayList<HousePhoto>();
		List<HousePhoto> estateImgs = new ArrayList<HousePhoto>();
		List<HousePhoto> roomImgs = new ArrayList<HousePhoto>();

		for (HousePhoto housePhoto : housePhotoes) {
			if (housePhoto.getCategory().equals("1")) {
				houseImgs.add(housePhoto);

			} else if (housePhoto.getCategory().equals("2")) {
				roomImgs.add(housePhoto);
			} else if (housePhoto.getCategory().equals("3")) {
				estateImgs.add(housePhoto);
			}

		}
		hq.setEstateImgUrls(estateImgs);
		hq.setLayoutImgUrls(houseImgs);
		hq.setInnerImgUrls(roomImgs);
	}
	
	public List<List<HouseQuality>> getAuditHouseQualityHistoryListByPage(HouseQuality hq,String userName){	
		List<Integer> rentList = new ArrayList<Integer>();
		List<Integer> resaleList = new ArrayList<Integer>();
		final String cityTempId = hq.getCityId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startStr = hq.getSearchStartDate();
		String endStr = hq.getSearchEndDate();
		Date start = null;
		Date end = null;
		try {
			if(StringUtils.isNotBlank(startStr)){
				start = sdf.parse(startStr);
			}else{
				start = new Date();
			}
			if(StringUtils.isNotBlank(endStr)){
				end = sdf.parse(endStr);
			}else{
				end = new Date();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		Calendar s = Calendar.getInstance();
		Calendar e = Calendar.getInstance();
		s.setTime(start);
		e.setTime(end);
		e.add(Calendar.DAY_OF_MONTH, 1);
		List<HouseQuality> other = new ArrayList<HouseQuality>();
		List<HouseQuality> list = new ArrayList<HouseQuality>();
		
		do{
			hq.setCityId(cityTempId);
			hq.setAuditDate(sdf.format(s.getTime()));
			List<HouseQuality> otherTemp = auditHistoryReadMapper.getAuditHouseQualityHistoryOtherList(hq, userName);
			if(otherTemp!=null && otherTemp.size()>0) other.addAll(otherTemp);
			List<HouseQuality> listTemp = auditHistoryReadMapper.getAuditHouseQualityHistoryList(hq,userName);//统计城市列表
			if(listTemp!=null && listTemp.size()>0) list.addAll(listTemp);
			s.add(Calendar.DAY_OF_MONTH, 1);
			
		}while(s.before(e));
		
		List<List<HouseQuality>> listReturn = new ArrayList<List<HouseQuality>>();//最终返回结果
		if(list!=null && list.size()>0){
			for(HouseQuality obj : list){
				if(obj.getHouseType()!=0){
					if(obj.getHouseType() == 1){
						this.setRentPhotos(obj);
						rentList.add(Integer.parseInt(obj.getHouseId()));
					}else{
						this.setResalePhotos(obj);
						resaleList.add(Integer.parseInt(obj.getHouseId()));
					}
					
				}
			}
			
		}
		if(other!=null && other.size()>0){
			for(HouseQuality obj : other){
				if(obj.getHouseType()!=0){
					if(obj.getHouseType() == 1){
						this.setRentPhotos(obj);
						rentList.add(Integer.parseInt(obj.getHouseId()));
					}else{
						this.setResalePhotos(obj);
						resaleList.add(Integer.parseInt(obj.getHouseId()));
					}
					
				}
				if(StringUtils.isNotBlank(obj.getCityId())) obj.setCityName(LocaleService.getName(Integer.parseInt(obj.getCityId())));
			}
			
		}		
		//获取房源的基本信息描述
		Map<String,Object> mapT = new HashMap<String,Object>();
		if(resaleList.size()>0){
			List<ResaleInfo>  resaleResult = resaleReadMapper.selectResaleInfoByIds(resaleList);
			if(resaleResult!=null && resaleResult.size()>0){
				for(ResaleInfo ri : resaleResult){
					mapT.put(ri.getId()+"_2",ri);
				}
			}
		}
		if(rentList.size()>0){
			List<RentInfo>  rentResult = rentReadMapper.selectRentInfoByIds(rentList);
			if(rentResult!=null && rentResult.size()>0){
				for(RentInfo ri : rentResult){
					mapT.put(ri.getId()+"_1", ri);
				}
			}
		}
		//将房源基本信息赋值到结果集列表中
		if(list!=null && list.size()>0){
			for(HouseQuality obj : list){
				if(obj.getHouseType()==1){
					obj.setRent((RentInfo)mapT.get(obj.getHouseId()+"_1"));
				}else{
					obj.setResale((ResaleInfo)mapT.get(obj.getHouseId()+"_2"));
				}
				
			}			
		}
		
		if(other!=null && other.size()>0){
			for(HouseQuality obj : other){
				if(obj.getHouseType()==1){
					obj.setRent((RentInfo)mapT.get(obj.getHouseId()+"_1"));
				}else{
					obj.setResale((ResaleInfo)mapT.get(obj.getHouseId()+"_2"));
				}
			}
		}
		listReturn.add(list);
		listReturn.add(other);		
		return listReturn;
	}
	public List<ConvertRate> queryRateByPage(RateSearch rateSearch) {
		if (rateSearch == null)
			return null;
		switch (Integer.valueOf(rateSearch.getRateType())) {
		case 0:
			rateSearch.setTableName("day_conv_rate");
			break;
		case 1:
			rateSearch.setTableName("day_conv_bbs");
			break;
		case 2:
			rateSearch.setTableName("day_conv_detail");
			break;
		case 3:
			rateSearch.setTableName("day_conv_estate");
			break;
		case 4:
			rateSearch.setTableName("day_conv_jjr");
			break;
		case 5:
			rateSearch.setTableName("day_conv_topic");
			break;
		case 6:
			rateSearch.setTableName("day_conv_wenda");
			break;
		case 7:
			rateSearch.setTableName("day_conv_zixun");
			break;
		case 8:
			rateSearch.setTableName("day_conv_58");
			break;
		default:
			rateSearch.setTableName("day_conv_rate");
			break;
		}

		List<ConvertRate> list = convertRateReadMapper
				.queryRateByPage(rateSearch);
		return list;
	}

	public List<AuditSearch> getAuditResaleList(AuditSearch as) {
		if (as == null)
			return null;
		Map<String, Integer> mapInner = new HashMap<String, Integer>();
		Map<String, Integer> mapLayout = new HashMap<String, Integer>();
		Map<String, Integer> mapEstae = new HashMap<String, Integer>();
		Map<String, Integer> mapBase = new HashMap<String, Integer>();

		List<AuditSearch> resList = null;
		List<AuditSearch> list = new ArrayList<AuditSearch>();
		// StringBuffer sb = new StringBuffer("1=1 ");
		// StringBuffer st = new StringBuffer("");
		//

		// if("resale".equals(as.getRateType())){//查询二手房
		// resList = resaleReadMapper.getAuditHouseCount(sb.toString());
		// }
		// if("rent".equals(as.getRateType())){//查询出租房
		// resList = rentReadMapper.getAuditHouseCount(sb.toString());
		// }

		resList = convertRateReadMapper.listAuditRate(as);

		// 租房二手房数据
		if (resList != null && resList.size() > 0) {
			for (AuditSearch ar : resList) {
				String date = ar.getPubDate();
				if (mapBase.containsKey(date)) {
					int value = mapBase.get(date);
					mapBase.put(date, value + ar.getBaseCount());
				} else {
					mapBase.put(date, ar.getBaseCount());
				}
				if (mapEstae.containsKey(date)) {
					int value = mapEstae.get(date);
					mapEstae.put(date, value + ar.getEstateCount());
				} else {
					mapEstae.put(date, ar.getEstateCount());
				}
				if (mapInner.containsKey(date)) {
					int value = mapInner.get(date);
					mapInner.put(date, value + ar.getInnerCount());
				} else {
					mapInner.put(date, ar.getInnerCount());
				}
				if (mapLayout.containsKey(date)) {
					int value = mapLayout.get(date);
					mapLayout.put(date, value + ar.getLayoutCount());
				} else {
					mapLayout.put(date, ar.getLayoutCount());
				}
			}
		}

		// 整合结果集
		Set<String> tempList = mapBase.keySet();
		List<String> listSort = new ArrayList<String>();
		Iterator<String> key = tempList.iterator();
		while (key.hasNext()) {
			String str = key.next();
			listSort.add(str);
		}
		Collections.sort(listSort);// 按发布日期排序
		for (String str : listSort) {
			AuditSearch ast = new AuditSearch();
			ast.setBaseCount(mapBase.get(str));
			ast.setInnerCount(mapInner.get(str));
			ast.setLayoutCount(mapLayout.get(str));
			ast.setEstateCount(mapEstae.get(str));
			ast.setPubDate(str);
			list.add(ast);
		}

		return list;
	}
	
	public List<PhotoAuditStatis> getPhotoAuditStatisListByPage(AuditSearch as) {
		if (as == null) return null;
		String type = as.getRateAgentType();
		StringBuffer sb = new StringBuffer(512);
		if(StringUtils.isNotEmpty(as.getRateAgent()) && StringUtils.isNotEmpty(type)){
			if("0".equals(type)){//手机
				sb.append(" agent_phone = '"+as.getRateAgent()+"' ");
			}else if("1".equals(type)){ //姓名
				sb.append(" agent_name like '%"+as.getRateAgent()+"%' ");
			}else{//id
				sb.append(" agent_id = "+as.getRateAgent()+" ");
			}
		}
		if(sb.length()<1){
			sb.append(" 1=1 ");
		}

		List<PhotoAuditStatis> resList = convertRateReadMapper.getPhotoAuditStatisListByPage(as,sb.toString());

		return resList;
	}
	
	public List<PhotoAuditStatis> getPhotoAuditStatisListToExcel(AuditSearch as) {
		if (as == null) return null;
		String type = as.getRateAgentType();
		StringBuffer sb = new StringBuffer(512);
		if(StringUtils.isNotEmpty(as.getRateAgent()) && StringUtils.isNotEmpty(type)){
			if("0".equals(type)){//手机
				sb.append(" agent_phone = '"+as.getRateAgent()+"' ");
			}else if("1".equals(type)){ //姓名
				sb.append(" agent_name like '%"+as.getRateAgent()+"%' ");
			}else{//id
				sb.append(" agent_id = "+as.getRateAgent()+" ");
			}
		}
		if(sb.length()<1){
			sb.append(" 1=1 ");
		}

		List<PhotoAuditStatis> resList = convertRateReadMapper.getPhotoAuditStatisListToExcel(as,sb.toString());

		return resList;
	}
	
	
	public List<User> getAllUsersByRoleId(int id){
		return userMapper.getAllUsersByRoleId(id);
	}

}
