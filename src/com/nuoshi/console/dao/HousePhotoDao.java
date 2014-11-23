package com.nuoshi.console.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.photo.Photo;
import com.nuoshi.console.persistence.read.taofang.album.HouseAlbumReadMapper;

@Repository
public class HousePhotoDao  {
	@Resource
	private HouseAlbumReadMapper houseAlbumReadMapper ;
	public Photo selectLayoutPhoto(Integer photoid) {
		List<Photo> photoes = houseAlbumReadMapper.selectLayoutPhoto(photoid);
		if(photoes!=null&&photoes.size()>0){
			return photoes.get(0);
		}
		return null;
	}
	public Photo selectCommPhoto(Integer photoid) {
		List<Photo> photoes = houseAlbumReadMapper.selectCommPhoto(photoid);
		if(photoes!=null&&photoes.size()>0){
			return photoes.get(0);
		}
		return null;
	}
	
}