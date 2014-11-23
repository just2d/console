package com.nuoshi.console.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoshi.console.domain.estate.LogCondition;
import com.nuoshi.console.domain.estate.UnionLog;
import com.nuoshi.console.persistence.read.taofang.estate.UnionLogReadMapper;
import com.nuoshi.console.persistence.write.taofang.estate.UnionLogWriteMapper;
import com.nuoshi.console.persistence.write.unionhouse.urent.URentWriteMapper;
import com.nuoshi.console.persistence.write.unionhouse.uresale.UResaleWriteMapper;

/**
 * 小区合并日志
 * @author ningt
 *
 */
@Service
@SuppressWarnings("unchecked")
public class UnionLogService {

	@Resource
	private UnionLogWriteMapper unionLogWriteMapper;
	@Resource
	private UnionLogReadMapper unionLogReadMapper;
	@Resource
	private URentWriteMapper  uRentWriteMapper;
	@Resource
	private UResaleWriteMapper  uResaleWriteMapper;
	
	/**
	 * 插入合并日志
	 * @param paramMap
	 */
	public void save(List unionLogList){
		unionLogWriteMapper.save(unionLogList);
	}
	/**
	 * 插入合并日志
	 * @param paramMap
	 */
	public void insert(UnionLog unionLog){
		unionLogWriteMapper.insert(unionLog);
	}
	
	
	/**
	 * 合并日志列表
	 * @param condition
	 * @return
	 */
	public List<UnionLog> list(Map paramMap){
		List list = unionLogReadMapper.getByPage(paramMap);
		return list != null && list.size() > 0 ? list :Collections.EMPTY_LIST; 
	}
	
	/**
	 * 根据条件获得合并日志(获得某一个小区的合并日志)
	 * @param condition
	 * @return
	 */
	public List<UnionLog> getUnionLogByCondition(LogCondition condition){
		List list = unionLogReadMapper.getUnionLogByCondition(condition);
		return list != null && list.size() > 0 ? list :Collections.EMPTY_LIST;
	}
	
	public UnionLog getUnionLogByTargetId(int targetId){
		return unionLogReadMapper.getUnionLogByTargetId(targetId);
	}
	
	

	public  List<UnionLog> getUnionLogBySourceId(int sourceId){
		return unionLogReadMapper.getUnionLogBySourceId(sourceId);
	}
	
	/**
	 * 更新房源小区id（恢复到合并前的状态）
	 * @param condition
	 */
	public void updateEstateId(UnionLog condition){
		if("resale".equals(condition.getFromTable())|| "rent".equals(condition.getFromTable())){
			unionLogWriteMapper.updateEstateId(condition);
		} else if("u_rent".equals(condition.getFromTable())){
			uRentWriteMapper.updateEstateId(condition);
		}else if("u_resale".equals(condition.getFromTable())){
			uResaleWriteMapper.updateEstateId(condition);
		}
		
		
	}
	
	/**
	 * 删除合并日志
	 * @param condition
	 */
	public void delete(LogCondition condition){
		unionLogWriteMapper.delete(condition);
	}
}
