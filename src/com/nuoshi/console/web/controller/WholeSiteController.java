package com.nuoshi.console.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wanghongmeng
 * 
 */
@Controller
@RequestMapping(value = "/statis")
public class WholeSiteController extends BaseController {

	/**
	 * @return 跳转到统计页面
	 */
	@RequestMapping(value = "/wholesite")
	public String forward() {
		return "tiles:statis.wholesite";
	}
}
