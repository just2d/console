package com.nuoshi.console.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoshi.console.dao.FunctionDao;
import com.nuoshi.console.domain.user.Function;

/**
 *<b>www.taofang.com</b>
 * <b>function:</b>
 * @author lizhenmin
 * @createDate 2011 Jul 21, 2011 10:44:48 AM
 * @email lizm@taofang.com
 * @version 1.0
 */
@Service
public class FunctionService extends BaseService {
	@Resource
	private FunctionDao functionDao;
	public List<Function> getAllByPage() {
		List<Function> functionList = functionDao.getAllFunctionByPage();
		return functionList;
	}


	public Function selectFunctionById(int id) {
		return functionDao.selectFunctionById(id);
	}
	public List<Function> selectFunctionByRoleId(int roleId) {
		return functionDao.selectFunctionByRoleId(roleId);
	}
	public Function getFunctionByName(String name,Integer id) {
		return functionDao.getFunctionByName(name,id);
	}

	public void updateFunctionInfo(Function function) {
		functionDao.updateFunctionInfo(function);
	}
	public void addFunction(Function function) {
		functionDao.addFunction(function);
	}
	public int delFunction(int id) {
		return functionDao.delFunction(id);
	}


	public List<Function> getAll() {
		return functionDao.getAll();
	}


	public List<Function> getAllSubByPage(int id) {
		// TODO Auto-generated method stub
		return functionDao.getAllSubByPage(id);
	}


	public List<Function> getAllSearchFunctionByPage(HashMap params) {
		return functionDao.getAllSearchFunctionByPage(params);
	}


	public List<Function> selectFunctionByUserId(int userId) {
		// TODO Auto-generated method stub
		return functionDao.selectFunctionByUserId(userId);
	}

}
