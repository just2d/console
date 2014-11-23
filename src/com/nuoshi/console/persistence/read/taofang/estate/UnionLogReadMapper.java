package com.nuoshi.console.persistence.read.taofang.estate;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.nuoshi.console.domain.estate.LogCondition;
import com.nuoshi.console.domain.estate.UnionLog;

/**
 * 小区合并日志
 * @author ningt
 *
 */
public interface UnionLogReadMapper {

	/**
	 * 日志列表
	 * @return
	 */
	public List<UnionLog> getByPage(Map paramMap);
	
	
	/**
	 * 根据条件获得合并日志(获得某一个小区的合并日志)
	 * @param condition
	 * @return
	 */
	public List<UnionLog> getUnionLogByCondition(LogCondition condition);
	
	@Select("select target_id,source_id from estate_union_log  where source_id = #{targetId} group by source_id")
	public UnionLog getUnionLogByTargetId(int targetId);
	
	@Select("select target_id targetId ,source_id estateid from estate_union_log  where target_id = #{sourceId} ")
	public List<UnionLog> getUnionLogBySourceId(int sourceId);
}
