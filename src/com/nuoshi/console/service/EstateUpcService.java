package com.nuoshi.console.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.taofang.biz.domain.audit.estate.EstateAuditTaskSubmit;
import com.taofang.biz.domain.audit.estate.EstatePhotoAuditTaskSubmit;
import com.taofang.biz.domain.estate.EstatePhotoUserUpload;
import com.taofang.biz.service.audit.IAuditEstateService;
import com.taofang.biz.util.BizServiceHelper;
import com.nuoshi.console.domain.user.User;

@Service
public class EstateUpcService extends BaseService {

	private IAuditEstateService auditEstateService  = BizServiceHelper.getAuditEstateService();

	public void submitUpcText(String taskIdStr, String checkCountStr, User user, com.taofang.biz.domain.estate.Estate estate) {
		int taskId = 0;
		if(StringUtils.isNotBlank(taskIdStr)){
			taskId = Integer.valueOf(taskIdStr);
		}
		int checkCount = 0;
		if(StringUtils.isNotBlank(checkCountStr)){
			checkCount = Integer.valueOf(checkCountStr);
		}
		EstateAuditTaskSubmit eaSubmit = new EstateAuditTaskSubmit();
		eaSubmit.setAuditorId(user.getId());
		eaSubmit.setAuditorName(user.getChnName());
		eaSubmit.setCheckCount(checkCount);
		eaSubmit.setTaskId(taskId);
		eaSubmit.setEstate(estate);
		if(taskId > 0){
			System.out.println("at once");
			auditEstateService.submitEstateTask(eaSubmit);
		}
	}

	public void submitPhotoAuditTask(List<Integer> idList, List<Integer> unUsingIdList,
			Map<Integer, EstatePhotoUserUpload> photoMap,User user) {
		if(photoMap != null && photoMap.size() > 0 ){
			List<EstatePhotoUserUpload> passedPhotos = new ArrayList<EstatePhotoUserUpload>();
			List<EstatePhotoUserUpload> failedPhotos = new ArrayList<EstatePhotoUserUpload>();
			if(CollectionUtils.isNotEmpty(idList)){
					for(Integer id:idList){
						passedPhotos.add(photoMap.get(id));
					}
			}
			if(CollectionUtils.isNotEmpty(unUsingIdList)){
				for(Integer id:unUsingIdList){
					failedPhotos.add(photoMap.get(id));
				}
			}
			EstatePhotoAuditTaskSubmit submit = new EstatePhotoAuditTaskSubmit();
			submit.setAuditorId(user.getId());
			submit.setAuditorName(user.getChnName());
			submit.setPassedPhotos(passedPhotos);
			submit.setFailedPhotos(failedPhotos);
			auditEstateService.submitEstatePhotoTask(submit);
		}
	}

}
