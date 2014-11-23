package com.nuoshi.console.web.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nuoshi.console.domain.wenda.Category;
import com.nuoshi.console.service.CategoryManageService;
import com.nuoshi.console.service.WenDaVerifyService;

/**
 * @description 问答系统分类管理类
 * @author shanyuqiang
 * @createDate Feb 29, 2012 1:48:37 PM
 * @email shanyq@taofang.com
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/wenda/category")
public class CategoryManageController {
	@Resource
	private CategoryManageService categoryManageService;
	@Resource
	private WenDaVerifyService wenDaVerifyService;
	
	/**
	 * 列表查询(左侧点击,查询点击,一级点击)
	 * @param id 标志
	 * @author shanyq
	 */
	@RequestMapping(value = "/list/{id}")
	public String getCategoryList(
			@ModelAttribute("categoryForm") Category category,
			@PathVariable("id") Integer id, Model model) {
		if(id==null){
			model.addAttribute("categoryList", new ArrayList<Category>());
			model.addAttribute("categoryParam", new ArrayList<Category>());
			return "tiles:category.list";
		}
		if (id.intValue()!=0 && id.intValue()!=-1) {
			// 一级分类作为二级父ID进行查询
			category.setPid(id);
			model.addAttribute("hide", 1);
		}
		if (id.intValue()==0) {
			category.setPid(0);
		}
		if (id.intValue()==-1) {
			category = categoryManageService.dealCategory(category);
			if (category.getFirstid() != null) {
				model.addAttribute("firstId", category.getFirstid());
			}
			if (category.getSecoundid() != null) {
				model.addAttribute("secoundId", category.getSecoundid());
				if(!category.getSecoundid().equals(0)){
					model.addAttribute("hide", 1);
				}
			}
		}
		List<Category> categoryList = categoryManageService
				.getCategoryList(category);
		model.addAttribute("pid",category.getPid());
		//下拉列表
		Category categoryQuery = new Category();
		categoryQuery.setPid(0);
		List<Category> categoryParam = categoryManageService
				.queryCategoryList(categoryQuery);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("categoryParam", categoryParam);
		return "tiles:category.list";
	}

	/**
	 *  一级分类与二级分类关联
	 *  @param pid 一级ID
	 *  @author shanyq
	 */
	@RequestMapping(value = "/change/{pid}")
	public String changeCategory(@PathVariable("pid") Integer pid, Model model) {
		Category category = new Category();
		category.setPid(pid);
		category.setStatus(0);
		List<Category> categoryList = categoryManageService
				.queryCategoryList(category);
		model.addAttribute("categoryList", categoryList);
		return "json";
	}
	
	/**
	 * 保存或者更新操作
	 * @author shanyq
	 */
	@RequestMapping(value = "/save")
	public String saveCategory(
			@ModelAttribute("addCategoryForm") Category category) {
		categoryManageService.saveOrUpdateCategory(category);
		return "redirect:/wenda/category/list/"+category.getPid();
	}

	/**
	 *  开启或者关闭状态修改
	 *  @params id 主键, status 状态(1:关闭,0:开启)
	 *  @author shanyq
	 */
	@RequestMapping(value = "/close/{id}/{pid}/{status}")
	public String closeUp(@PathVariable("id") Integer id,
			@PathVariable("pid")Integer pid,
			@PathVariable("status") Integer status) {
		categoryManageService.updateStatus(id, pid, status);
		return "redirect:/wenda/category/list/"+pid;
	}

	/**
	 *  编辑请求
	 * @param id 主键
	 * @author shanyq
	 */
	@RequestMapping(value = "/modify/{id}")
	public String modify(@PathVariable("id") Integer id, Model model) {
		 Category category = categoryManageService.getCategoryById(id);
		model.addAttribute("category", category);
		return "json";
	}
	/**
	 *  更新问答系统分类缓存
	 * @author wangjh
	 */
	@RequestMapping(value = "/updateCache")
	public String updateCacheForWenDa(Model model,
			@RequestParam(value="ids", required=false)String ids,
			@RequestParam(value="locations",required=false)String locations) {
		boolean result=true;
		try{
			categoryManageService.updateLocation(ids, locations);
			categoryManageService.callFrontend();
		}catch (Exception e) {
			result=false;
		}
		model.addAttribute("result", result);
		return "json";
	}
	/**
	 *  请求分类
	 * @param id 主键
	 * @author wangjh
	 */
	@RequestMapping(value = "/levelOneCategory/{categoryId}")
	public String getLevelOneCategory(
		@PathVariable("categoryId") Integer categoryId, Model model) {
		Category categoryQuery=new Category();
		if(categoryId!=null){
			categoryQuery.setPid(categoryId);
		}
		categoryQuery.setStatus(0);
		List<Category> categoryList = categoryManageService
		.queryCategoryList(categoryQuery);
		model.addAttribute("categoryList", categoryList);
		return "json";
	}
	/**
	 *  修改问题的分类
	 * @param id 主键
	 * @author wangjh
	 */
	@RequestMapping(value = "/changeCategory/{questionIds}")
	public String changeQuestionCategory(
			@PathVariable("questionIds") Integer questionIds, Model model,
			HttpServletRequest request) {
		try {
			Category category = new Category();
			String levelOneStr = request.getParameter("levelOne");
			String levelTwoStr = request.getParameter("levelTwo");
			String categoryName1Str = request.getParameter("categoryName1");
			String categoryName2Str = request.getParameter("categoryName2");
			if (StringUtils.isBlank(levelOneStr)) {
				model.addAttribute("result", false);
				return "json";
			}
			if(StringUtils.isNotBlank(levelOneStr)){
				category.setPid(Integer.valueOf(levelOneStr));
			}else{
				model.addAttribute("result", false);
				return "json";
			}
			if (StringUtils.isBlank(levelTwoStr)||Integer.valueOf(levelTwoStr).intValue()<=0) {
				category.setId(0);
			}else{
				category.setId(Integer.valueOf(levelTwoStr));
			}
			if (StringUtils.isNotBlank(categoryName1Str)) {
				category.setParentName(categoryName1Str);
			}else{
				category.setParentName("分类名称未知");
			}
			if (StringUtils.isNotBlank(categoryName2Str)) {
				category.setName(categoryName2Str);
			}else if(categoryName2Str!=null){
				category.setName(null);
			}
			wenDaVerifyService.changeQuestionCategory(questionIds, category);
			model.addAttribute("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("result", false);
		}
		return "json";
	}
	
}
