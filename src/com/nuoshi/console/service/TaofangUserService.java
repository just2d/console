package com.nuoshi.console.service;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.nuoshi.console.domain.user.TFUser;
import com.nuoshi.console.persistence.read.taofang.user.UserReadMapper;

@Service
public class TaofangUserService {
	@Resource
	private UserReadMapper userReadMapper;

	public TFUser getUserByUserName(@Param("userName") String userName) {
		try {
			return userReadMapper.getUserByUserName(userName);
		} catch (Exception e) {
			return null;
		}
	}

	public TFUser getUserByUserId(@Param("userId") int userId) {
		try {
			return userReadMapper.getUserByUseId(userId);
		} catch (Exception e) {
			return null;
		}
	}

}
