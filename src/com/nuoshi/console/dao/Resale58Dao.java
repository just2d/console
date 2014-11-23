package com.nuoshi.console.dao;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.persistence.read.taofang.agent.AgentManageReadMapper;
import com.nuoshi.console.persistence.read.unionhouse.uresale.UResaleReadMapper;
import com.nuoshi.console.persistence.write.unionhouse.uresale.UResaleWriteMapper;


/**
 *<b>www.taofang.com</b>
 * <b>function:</b>
 * @author lizhenmin
 * @createDate 2011 Jul 21, 2011 2:01:05 PM
 * @email lizm@taofang.com
 * @version 1.0
 */
@Repository
public class Resale58Dao {
	@Resource
	private UResaleReadMapper uresaleReadMapper;
	@Resource
	private AgentManageReadMapper agentManageReadMapper;
	@Resource
	private UResaleWriteMapper uresaleWriteMapper;
	public  HashMap<String, Object>  getResaleById(Integer id){
		return uresaleReadMapper.getResaleById(id);
	}
	
	public int deleteResale(int resaleid){
		return uresaleWriteMapper.deleteResale(resaleid);
	}
	
	public List<HashMap<String,Object>> getAll58ResaleByPage(HashMap params){
		if(params==null){
		 params = new HashMap<String, Object>();
		}
		List<HashMap<String,Object>> resultList = uresaleReadMapper.getAll58ResaleByPage(params);
		
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
