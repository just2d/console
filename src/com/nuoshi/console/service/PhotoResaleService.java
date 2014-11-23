package com.nuoshi.console.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoshi.console.domain.auditHistory.SubAuditHistory;
import com.nuoshi.console.persistence.read.taofang.photo.PhotoResaleReadMapper;

@Service
public class PhotoResaleService extends BaseService {

	@Resource
	PhotoResaleReadMapper photoResaleReadMapper;

	public List<SubAuditHistory> getNewResalePhotosByHouseListAndType(List<Integer> newPhotoResaleList,
			List<Integer> photoIdList, Integer type) {
		return photoResaleReadMapper.getNewResalePhotosByHouseListAndType(newPhotoResaleList, photoIdList, type);
	}
}
