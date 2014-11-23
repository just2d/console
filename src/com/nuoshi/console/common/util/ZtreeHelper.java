package com.nuoshi.console.common.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.nuoshi.console.domain.user.Function;


public class ZtreeHelper {
	private List<Menu> menuList;

	public static String getZtreeNodes(List<Menu> menuls){
		JSONArray jsonTree = JSONArray.fromObject(menuls);
		return jsonTree.toString();
	}
	public static String getZtreeNodes2(List<Function> functions){
		List<Menu> menuList = new ArrayList<Menu>();
		
		for (Function function : functions) {
			Menu mn = new Menu();
			mn.setId(function.getId()+"");
			mn.setName(function.getName());
			mn.setpId(function.getParentId()+"");
			mn.setOpen(true);
			mn.setTarget("main");
			mn.setUrl("/function/sub/"+function.getId());
			menuList.add(mn);
			
		}
		return getZtreeNodes(menuList);
	}
	public static String getZtreeNodes(List<Function> functions,List<Function> roleFunctions){
		List<Menu> menuList = new ArrayList<Menu>();
		
		for (Function function : functions) {
			Menu mn = new Menu();
			mn.setId(function.getId()+"");
			mn.setName(function.getName());
			mn.setpId(function.getParentId()+"");
			mn.setOpen(true);
			mn.setTarget("main");
			mn.setUrl("/function/sub/"+function.getId());
			mn.setChecked(checkChecked(function.getId(),roleFunctions));
			menuList.add(mn);
			
		}
		return getZtreeNodes(menuList);
	}
	private static boolean checkChecked(Long id,List<Function> functions){
		for (Function function : functions) {
			if(function.getId().longValue()==id.longValue()){
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		List<Menu> menuList = new ArrayList<Menu>();
		Menu n = new Menu();
		n.setId("1");
		n.setName("wwww");
		n.setOpen(true);
		n.setpId("2");
		n.setUrl("http://www.baidu.com");
		menuList.add(n);
		System.out.println(getZtreeNodes(menuList));
		
	}
	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	
}
