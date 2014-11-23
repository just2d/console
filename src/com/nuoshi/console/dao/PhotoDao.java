package com.nuoshi.console.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.photo.Photo;
import com.nuoshi.console.persistence.read.taofang.photo.PhotoReadMapper;


/**
 *<b>www.taofang.com</b>
 * <b>function:</b>
 * @author lizhenmin
 * @createDate 2011 Jul 21, 2011 2:01:05 PM
 * @email lizm@taofang.com
 * @version 1.0
 */
@Repository
public class PhotoDao {
	@Resource
	private PhotoReadMapper photoReadMapper;
	
	public Photo selectPhotoById(int id) {
		if (photoReadMapper != null) {
			try {
				Photo photo = photoReadMapper.selectPhotoById(id);
				return photo;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
