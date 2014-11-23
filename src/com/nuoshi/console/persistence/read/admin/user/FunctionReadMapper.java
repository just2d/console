package com.nuoshi.console.persistence.read.admin.user;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.user.Function;

/**
 *smc
 * <b>function:</b>管理员Mapper
 * @author lizhenmin
 * @createDate 2011 Jul 20, 2011 2:11:57 PM
 * @email lizm@taofang.com
 * @version 1.0
 */
public interface FunctionReadMapper {

	public List<Function> getAllFunctionByPage();
	public Function selectFunctionById(int id);
	public Function selectFunctionByName(@Param("name")String name,@Param("id") Integer id);
	public List<Function> selectFunctionByRoleId(@Param("roleId")int roleId);
	public List<Function> getAll();
	public List<Function> getAllSubByPage(@Param("id")int id);
	public List<Function> getAllSearchSubFunctionByPage(HashMap params);
	public List<Function> selectFunctionByUserId(@Param("userId")int userId);

}
