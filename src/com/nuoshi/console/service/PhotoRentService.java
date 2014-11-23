package com.nuoshi.console.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoshi.console.domain.auditHistory.SubAuditHistory;
import com.nuoshi.console.persistence.read.taofang.photo.PhotoRentReadMapper;

@Service
public class PhotoRentService extends BaseService {

	@Resource
	PhotoRentReadMapper photoRentReadMapper;


	public List<SubAuditHistory> getNewRentPhotosByHouseListAndType(List<Integer> newPhotoRentList,
			List<Integer> photoIdList, Integer type) {
		return photoRentReadMapper.getNewRentPhotosByHouseListAndType(newPhotoRentList,photoIdList,type);
	}
}
