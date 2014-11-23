package com.nuoshi.console.view;

import com.nuoshi.console.domain.user.Role;

/**
 *<b>www.taofang.com</b>
 * <b>function:</b>
 * @author lizhenmin
 * @createDate 2011 Aug 23, 2011 1:32:38 PM
 * @email lizm@taofang.com
 * @version 1.0
 */
public class RoleView extends Role{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3861775595636832646L;
	private Integer userId;

	public boolean getChecked(){
		return this.userId!=null;
	}
}
