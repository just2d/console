package com.nuoshi.console.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.nuoshi.console.domain.agent.AgentMaster;
import com.nuoshi.console.domain.base.HouseEvaluation;
import com.nuoshi.console.domain.rent.Rent;
import com.nuoshi.console.persistence.read.taofang.rent.RentEvaluationReadMapper;
import com.nuoshi.console.persistence.read.taofang.rent.RentReadMapper;
import com.nuoshi.console.persistence.read.unionhouse.urent.URentReadMapper;
import com.nuoshi.console.persistence.write.taofang.rent.RentEvaluationWriteMapper;
import com.nuoshi.console.persistence.write.taofang.rent.RentWriteMapper;
import com.nuoshi.console.persistence.write.unionhouse.urent.URentWriteMapper;

@Service
public class RentEvaluationService {
	
	@Resource
	private MessageService messageService;
	@Resource
	private AgentMasterService agentMasterService;
	
	@Resource
	private RentReadMapper rentReadMapper;
	
	@Resource
	private RentWriteMapper rentWriteMapper;
	
	@Resource
	private RentEvaluationReadMapper rentEvaluationReadMapper;
	
	@Resource
	private RentEvaluationWriteMapper rentEvaluationWriteMapper;
	
	@Resource
	private URentReadMapper urentReadMapper;
	
	@Resource
	private URentWriteMapper urentWriteMapper;
	
	public List<Rent> getRentForEvaluation(int cityId, int sourceId, int queryType, String content,int evalConType) {
		List<String> conditions = new ArrayList<String>();
		conditions.add(" 0 = 0 ");
		if(evalConType ==1 ){
			conditions.add(" r.comments_count > 0 ");
		}else if(evalConType ==2 ){
			conditions.add(" r.comments_count = 0 ");
		}
		if(sourceId == 0) {
			if(cityId > 0) {
				conditions.add("r.city_id = " + cityId);
			}
			if(StringUtils.isNotEmpty(content)) {
				if(queryType == 0) {
					conditions.add("r.id = " + content);
				} else if(queryType == 1) {
					conditions.add("r.author_name = '"+content+"'");
				} else if(queryType == 2) {
					conditions.add("r.author_phone = '"+content+"'");
				} else if(queryType == 3) {
					conditions.add("r.authorid = " + content);
				}
			}
			return rentReadMapper.getRentForEvaluationByPage(conditions);
		} else {
			conditions.add("r.sourceid = " + sourceId);
			if(cityId > 0) {
				conditions.add("r.cityid = " + cityId);
			}
			try {
				if(StringUtils.isNotEmpty(content)) {
					if(queryType == 0) {
						conditions.add("r.id = " + Integer.parseInt(content));
					} else if(queryType == 1) {
						conditions.add("r.authorname = '"+content+"'");
					} else if(queryType == 2) {
						conditions.add("r.authorphone = '"+content+"'");
					} else if(queryType == 3) {
						conditions.add("r.authorid = " + Integer.parseInt(content));
					}
				}
			} catch(Exception e) {
				
			}
			return urentReadMapper.getAllUnionRentByPage(conditions);
		}
	}
	
	public List<HouseEvaluation> getHouseEvaluation(int houseId, int sourceId) {
		return rentEvaluationReadMapper.getHouseEvaluationByPage(houseId, sourceId);
	}
	
	public int changeVisible(int houseId, int sourceId, int status) {
		if(sourceId == 0) {
			return rentWriteMapper.changeVisible(houseId, status);
		} else {
			return urentWriteMapper.changeVisible(houseId, sourceId, status);
		}
	}
	
	public int delHouseEvaluation(String ids) {
		int result = 0;
		if(StringUtils.isNotBlank(ids)) {
			String[] idArray = ids.split("_");
			try {
				for(String id : idArray) {
					result += rentEvaluationWriteMapper.delHouseEvaluation(Integer.parseInt(id));
				}
			} catch(Exception e) {
				
			}
		}
		return result;
	}
	
	public void reCaculateAvgPoint(int houseId, int sourceId) {
		String commit = "COMMIT";
		int priceCount = 0;
		float priceScore = 0.0f;
		int photoCount = 0;
		float photoScore = 0.0f;
		int infoCount = 0;
		float infoScore = 0.0f;
		int totalItem = 0;
		String startTransanction = "START TRANSACTION;";
		try {
			rentEvaluationWriteMapper.runCommand(startTransanction);			
			//处理Price信息
			String priceCountSql = "SELECT COUNT(1) FROM rent_real_evaluation WHERE house_id = " + houseId + " AND source_id = " + sourceId + " AND valid = 1 AND price_acu > 0";
			priceCount = rentEvaluationReadMapper.runCommand(priceCountSql);
			
			if(priceCount > 0) {
				totalItem++;
				String priceScoreSql = "SELECT SUM(price_acu) / "+priceCount+" FROM rent_real_evaluation WHERE house_id = " + houseId + " AND source_id = "+sourceId+" AND valid = 1 AND price_acu > 0";
				priceScore = rentEvaluationReadMapper.runFloatCommand(priceScoreSql);
				String updatePriceScoreSql = "UPDATE rent_real_score SET price_count = " + priceCount + " , price_score = " + priceScore  + " WHERE house_id = " + houseId + " AND source_id = " + sourceId;
				rentEvaluationWriteMapper.runCommand(updatePriceScoreSql);
			} else {
				String updatePriceScoreSql = "UPDATE rent_real_score SET price_count = 0 ,price_score =  0 WHERE house_id = " + houseId + " AND source_id = " + sourceId;
				rentEvaluationWriteMapper.runCommand(updatePriceScoreSql);
			}
			
			//处理photo信息
			String photoCountSql = "SELECT COUNT(1) FROM rent_real_evaluation WHERE house_id = " + houseId + " AND source_id = "+sourceId+" AND valid = 1 AND photo_acu > 0";
			photoCount = rentEvaluationReadMapper.runCommand(photoCountSql);
			if(photoCount > 0) {
				totalItem++;
				String photoScoreSql = "SELECT SUM(photo_acu) / "+photoCount+" FROM rent_real_evaluation WHERE house_id = " + houseId + " AND source_id = "+sourceId+" AND valid = 1 AND photo_acu > 0";
				photoScore = rentEvaluationReadMapper.runFloatCommand(photoScoreSql);
				String updatePhotoScoreSql = "UPDATE rent_real_score SET photo_count = " + photoCount + " , photo_score = " + photoScore  + " WHERE house_id = " + houseId + " AND source_id = " + sourceId;
				rentEvaluationWriteMapper.runCommand(updatePhotoScoreSql);
			} else {
				String updatePhotoScoreSql = "UPDATE rent_real_score SET photo_count = 0 ,photo_score =  0 WHERE house_id = " + houseId + " AND source_id = " + sourceId;
				rentEvaluationWriteMapper.runCommand(updatePhotoScoreSql);
			}
			
			//处理info信息
			String infoCountSql = "SELECT COUNT(1) FROM rent_real_evaluation WHERE house_id = " + houseId + " AND source_id = "+sourceId+" AND valid = 1 AND info_acu > 0";
			infoCount = rentEvaluationReadMapper.runCommand(infoCountSql);
			if(infoCount > 0) {
				totalItem++;
				String infoScoreSql = "SELECT SUM(info_acu) / "+infoCount+" FROM rent_real_evaluation WHERE house_id = " + houseId + " AND source_id = "+sourceId+" AND valid = 1 AND info_acu > 0";
				infoScore = rentEvaluationReadMapper.runFloatCommand(infoScoreSql);
				String updateInfoScoreSql = "UPDATE rent_real_score SET info_count = " + infoCount + " , info_score = " + infoScore  + " WHERE house_id = " + houseId + " AND source_id = " + sourceId;
				rentEvaluationWriteMapper.runCommand(updateInfoScoreSql);
			} else {
				String updateInfoScoreSql = "UPDATE rent_real_score SET info_count = 0 ,info_score =  0 WHERE house_id = " + houseId + " AND source_id = " + sourceId;
				rentEvaluationWriteMapper.runCommand(updateInfoScoreSql);
			}
			String totalCountSql = "SELECT COUNT(1) FROM rent_real_evaluation WHERE house_id = " + houseId + " AND valid = 1 AND source_id = " + sourceId;
			int totalCount = rentEvaluationReadMapper.runCommand(totalCountSql);
			if(sourceId == 0) {
				totalItem++;
				String updateAvgScoreSql = "UPDATE rent_real_score rrs, rent r SET rrs.total_count = " + totalCount + " , rrs.avg_score = " +
						" (((r.base_score / 20) + " + (photoScore + priceScore + infoScore) + ") / "+ totalItem + ") " +
								" WHERE rrs.house_id = r.id AND rrs.source_id = 0 AND rrs.house_id =  " + houseId + ";";
				rentEvaluationWriteMapper.runCommand(updateAvgScoreSql);
			} else  {
				if(totalItem > 0) {
					String updateAvgScoreSql = "UPDATE rent_real_score rrs SET rrs.total_count = " + totalCount + " , rrs.avg_score = " +
					" (( " + priceScore + photoScore + infoScore + ") / " + totalItem  + ")" + 
							" WHERE rrs.source_id = "+sourceId+" AND rrs.house_id =  " + houseId + ";";
					rentEvaluationWriteMapper.runCommand(updateAvgScoreSql);
				} else {
					String updateAvgScoreSql = "UPDATE rent_real_score rrs SET rrs.total_count = " + totalCount + ", rrs.avg_score = 0 " +
							" WHERE rrs.source_id = "+sourceId+" AND rrs.house_id =  " + houseId + ";";
					rentEvaluationWriteMapper.runCommand(updateAvgScoreSql);
				}
				
			}
			
			String totalExist0Sql = "SELECT count(1) FROM rent_real_evaluation WHERE house_id = " + houseId + " AND source_id = " + sourceId + " AND " +
					" valid = 1 AND exist = 0 ";
			String totalExist1Sql = "SELECT count(1) FROM rent_real_evaluation WHERE house_id = " + houseId + " AND source_id = " + sourceId + " AND " +
			" valid = 1 AND exist = 1 ";
			int totalExist0 = rentEvaluationReadMapper.runCommand(totalExist0Sql);
			int totalExist1 = rentEvaluationReadMapper.runCommand(totalExist1Sql);
			String updateExistSql = "UPDATE rent_real_score SET exist_0 = " + totalExist0 + ", exist_1 = " + totalExist1 + 
			      " WHERE house_id = " + houseId + " AND source_id = " + sourceId;
			rentEvaluationWriteMapper.runCommand(updateExistSql);
			
			rentEvaluationWriteMapper.runCommand(commit);
			
		} catch(Exception e) {
			rentEvaluationWriteMapper.runCommand(commit);
		}
	}

	public int realHouse(int houseId, int sourceId, int status) {
		//0 淘房房源       外部房源暂不处理
		if(sourceId == 0){
			Rent rent = rentReadMapper.selectRentById(houseId);
			AgentMaster author = agentMasterService.selectAgentMasterById(rent.getAuthorid());
			int result = 0;
			switch (status) {
			case 1:
				result = rentWriteMapper.updateStatusAndAppealExpiredTime(null,houseId);
				try {
					messageService.sendMessageSys("房源申诉结果通知 ", "申诉成功：您对被举报房源\""+rent.getEstatename()+"-"+rent.getArea()+"平-"+rent.getPrice()+"元\"进行了 成功申诉，房源已回归正常状态。", author);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 2:
				result = rentWriteMapper.updateStatusAndAppealExpiredTime(2,houseId);
				try {
					messageService.sendMessageSys("房源申诉结果通知 ", "申诉失败： 您对被举报房源\""+rent.getEstatename()+"-"+rent.getArea()+"平-"+rent.getPrice()+"元\"的申诉失败，该房源已被移除至【违规房源】中。", author);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
			return result;
		}
		
		return 1;
	}
}
