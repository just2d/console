package com.nuoshi.console.persistence.write.admin.user;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.user.Function;

/**
 *smc
 * <b>function:</b>功能菜单
 * @author lizhenmin
 * @createDate 2011 Jul 20, 2011 2:11:57 PM
 * @email lizm@taofang.com
 * @version 1.0
 */
public interface FunctionWriteMapper {

	public int delFunction(int id);
	public int addFunction(Function function);
	public int  updateFunctionInfo(Function function);
	public int delRoleFunction(@Param("functionId")int functionId);
}
