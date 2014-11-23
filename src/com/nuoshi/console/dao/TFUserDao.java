package com.nuoshi.console.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.user.TFUser;
import com.nuoshi.console.persistence.read.taofang.agent.TFUserReadMapper;

/**
 *<b>www.taofang.com</b>
 * <b>function:</b>
 * @author lizhenmin
 * @createDate 2011 Jul 21, 2011 2:01:05 PM
 * @email lizm@taofang.com
 * @version 1.0
 */
@Repository
public class TFUserDao {
	@Resource
	private TFUserReadMapper TFUserReadMapper;
	public TFUser selectById(final int id) {
		TFUser res = null;
		if (TFUserReadMapper != null) {
			try {
				res = TFUserReadMapper.selectAgentById(id);
			} catch (Exception e) {
				//
			}
		}
		return res;
	}
//	public TFUser selectUserMobileById(Integer userId){
//		return TFUserReadMapper.selectUserMobileById(userId);
//		
//	}
}
