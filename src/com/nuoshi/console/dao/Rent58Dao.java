package com.nuoshi.console.dao;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.persistence.read.taofang.agent.AgentManageReadMapper;
import com.nuoshi.console.persistence.read.unionhouse.urent.URentReadMapper;
import com.nuoshi.console.persistence.write.unionhouse.urent.URentWriteMapper;


/**
 *<b>www.taofang.com</b>
 * <b>function:</b>
 * @author lizhenmin
 * @createDate 2011 Jul 21, 2011 2:01:05 PM
 * @email lizm@taofang.com
 * @version 1.0
 */
@Repository
public class Rent58Dao {
	@Resource
	private URentReadMapper urentReadMapper;
	@Resource
	private AgentManageReadMapper agentManageReadMapper;
	@Resource
	private URentWriteMapper urentWriteMapper;

	public  HashMap<String, Object>  getRentById(Integer id){
		return urentReadMapper.getRentById(id);
	}

	public int deleteRent(int rentid){
		return urentWriteMapper.deleteRent(rentid);
	}
	
	public List<HashMap<String,Object>> getAll58RentByPage(HashMap params){
		if(params==null){
		 params = new HashMap<String, Object>();
		}
		List<HashMap<String,Object>> resultList = urentReadMapper.getAll58RentByPage(params);
		
		for (HashMap<String, Object> result : resultList) {
			try {
				String cityDir = agentManageReadMapper.getDirName((Integer) result.get("cityid"));
				result.put("cityDir", cityDir);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return resultList;
	}
}
