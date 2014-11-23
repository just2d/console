package com.nuoshi.console.persistence.read.taofang.album;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.photo.Photo;

/**
 * Author: CHEN Liang <alinous@gmail.com> Date: 2009-12-12 Time: 15:28:10
 * 
 */

public interface HouseAlbumReadMapper {

	public List<Photo> selectLayoutPhoto( @Param("photoid")Integer photoid);
	public List<Photo> selectCommPhoto(@Param("photoid")Integer photoid);
}
