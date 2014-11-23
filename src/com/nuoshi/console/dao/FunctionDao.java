package com.nuoshi.console.dao;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.user.Function;
import com.nuoshi.console.persistence.read.admin.user.FunctionReadMapper;
import com.nuoshi.console.persistence.write.admin.user.FunctionWriteMapper;

/**
 *<b>www.taofang.com</b>
 * <b>function:</b>
 * @author lizhenmin
 * @createDate 2011 Jul 21, 2011 2:01:05 PM
 * @email lizm@taofang.com
 * @version 1.0
 */
@Repository
public class FunctionDao {
	@Resource
	private FunctionReadMapper functionReadMapper;
	@Resource
	private FunctionWriteMapper functionWriteMapper;
	public List<Function> getAllFunctionByPage() {
		return functionReadMapper.getAllFunctionByPage();
	}
	public Function selectFunctionById(int id) {
		return functionReadMapper.selectFunctionById(id);
	}
	public Function getFunctionByName(String name, Integer id) {
		return functionReadMapper.selectFunctionByName(name,id);
	}
	public int delFunction(int id) {
		  functionWriteMapper.delRoleFunction(id);
		return functionWriteMapper.delFunction(id);
	}

	public int updateFunctionInfo(Function function) {
		return functionWriteMapper.updateFunctionInfo(function);
	}
	public int addFunction(Function function) {
		return functionWriteMapper.addFunction(function);
	}
	public List<Function> selectFunctionByRoleId(int roleId) {
		return functionReadMapper.selectFunctionByRoleId(roleId);
	}
	public List<Function> getAll() {
		return functionReadMapper.getAll();
	}
	public List<Function> getAllSubByPage(int id) {
		return functionReadMapper.getAllSubByPage(id);
	}
	public List<Function> getAllSearchFunctionByPage(HashMap params) {
		return functionReadMapper.getAllSearchSubFunctionByPage(params);
	}
	public List<Function> selectFunctionByUserId(int userId) {
		return functionReadMapper.selectFunctionByUserId(userId);
	}

	
}
