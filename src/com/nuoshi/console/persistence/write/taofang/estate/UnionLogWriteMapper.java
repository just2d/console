package com.nuoshi.console.persistence.write.taofang.estate;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.nuoshi.console.domain.estate.LogCondition;
import com.nuoshi.console.domain.estate.UnionLog;

/**
 * 小区合并日志
 * @author ningt
 *
 */
public interface UnionLogWriteMapper {

	/**
	 * 插入合并日志
	 * @param paramMap
	 */
	public void save(@Param ("unionLogList")List unionLogList);
	
	/**
	 * 插入合并日志
	 * @param paramMap
	 */
	@Insert("insert into estate_union_log (target_id,source_id,source_name,t_citycode,s_authstatus,cts,operator) values (#{unionLog.targetId},#{unionLog.estateid},#{unionLog.estatename},#{unionLog.t_CityCode},#{unionLog.s_authStatus},now(),#{unionLog.operator})")
	public void insert(@Param ("unionLog") UnionLog  unionLog);
	
	/**
	 * 更新房源小区id（恢复到合并前的状态）
	 * @param condition
	 */
	public void updateEstateId(UnionLog condition);
	
	/**
	 * 删除合并日志
	 * @param condition
	 */
	public void delete(LogCondition condition);
	
	
	
	
}
