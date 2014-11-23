package com.nuoshi.console.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoshi.console.domain.photo.Photo;
import com.nuoshi.console.persistence.write.taofang.photo.PhotoWriteMapper;

@Service
public class PhotoService extends BaseService {

	@Resource
	PhotoWriteMapper photoWriteMapper;

	public int save(Photo photo) {
		return photoWriteMapper.save(photo);

	}
}
