package com.nuoshi.console.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nuoshi.console.common.util.SmcConst;
import com.nuoshi.console.domain.estate.UEstateMapping;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.service.EstateMappingService;
import com.nuoshi.console.web.session.SessionUser;

@Controller
@RequestMapping("/estate/mapping")
public class EstateMappingController extends BaseController {

	@Resource
	private EstateMappingService estateMappingService;

	
	/**
	 * 小区映射 显示
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public String mappingList(@RequestParam(value = "ownEstate", required = false) String ownEstate,
			@RequestParam(value = "unionEstate", required = false) String unionEstate,
			@RequestParam(value = "sourceId", required = false) Integer sourceId,
		Model model, HttpServletRequest request) {
		request.getSession().removeAttribute("mapping");
		request.getSession().removeAttribute("oldMapping");
		List<UEstateMapping> mappings = estateMappingService.getEstateMapping(ownEstate,unionEstate,sourceId);
		model.addAttribute("mappings", mappings);
		model.addAttribute("ownEstate", ownEstate);
		model.addAttribute("unionEstate", unionEstate);
		model.addAttribute("sourceId", sourceId);
		return "tiles:estate.mapping.list";
	}
	
	/**
	 * 小区映射跳转到添加页面
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model, HttpServletRequest request) {
		
		return "tiles:estate.mapping.add";
	}
	
	/**
	 * 小区映射添加
	 * 
	 * @return
	 */
	@RequestMapping("/save")
	public String save(UEstateMapping mapping,Model model, HttpServletRequest request) {
		SessionUser sessionUser  = (SessionUser) request.getSession().getAttribute(SmcConst.SESSION_USER);
		User user=sessionUser.getUser();
		mapping.setLastUpdateUserId(user.getId());
		String  errorStr = estateMappingService.save(mapping);
		if(errorStr != null){
			model.addAttribute("result", "fail");
			model.addAttribute("error", errorStr);
			request.getSession().setAttribute("mapping", mapping);
		}
		return "json";
	}

	
	/**
	 * 确认小区映射添加
	 * 
	 * @return
	 */
	@RequestMapping("/confirmsave")
	public String confirmsave(Model model, HttpServletRequest request) {
		UEstateMapping mapping  =  (UEstateMapping) request.getSession().getAttribute("mapping");
		String  errorStr = estateMappingService.confirmsave(mapping);
		if(errorStr != null){
			model.addAttribute("result", "fail");
			model.addAttribute("error", errorStr);
		}else{
			request.getSession().removeAttribute("mapping");
		}
		return "json";
	}


	/**
	 * 小区映射跳转到编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(@RequestParam(value = "id", required = true) Integer id,Model model, HttpServletRequest request) {
		UEstateMapping mapping  =  estateMappingService.getById(id);
		model.addAttribute("mapping", mapping);
		request.getSession().setAttribute("oldMapping", mapping);
		return "tiles:estate.mapping.edit";
	}
	
	/**
	 * 小区映射更新
	 * 
	 * @return
	 */
	@RequestMapping("/update")
	public String update(UEstateMapping mapping,Model model, HttpServletRequest request) {
		SessionUser sessionUser  = (SessionUser) request.getSession().getAttribute(SmcConst.SESSION_USER);
		User user=sessionUser.getUser();
		mapping.setLastUpdateUserId(user.getId());
		UEstateMapping oldMapping  = (UEstateMapping) request.getSession().getAttribute("oldMapping");
		String  errorStr = estateMappingService.update(mapping,oldMapping);
		if(errorStr != null){
			model.addAttribute("result", "fail");
			model.addAttribute("error", errorStr);
			request.getSession().setAttribute("mapping", mapping);
		}else{
			request.getSession().removeAttribute("oldMapping");
		}
		return "json";
	}
	
	/**
	 * 确认小区映射更新
	 * 
	 * @return
	 */
	@RequestMapping("/confirmupdate")
	public String confirmupdate(Model model, HttpServletRequest request) {
		UEstateMapping mapping  =  (UEstateMapping) request.getSession().getAttribute("mapping");
		String  errorStr = estateMappingService.confirmupdate(mapping);
		if(errorStr != null){
			model.addAttribute("result", "fail");
			model.addAttribute("error", errorStr);
		}else{
			request.getSession().removeAttribute("mapping");
		}
		return "json";
	}

	
	/**
	 * 小区映射删除
	 * 
	 * @return
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value = "id", required = true) Integer id,Model model, HttpServletRequest request) {
		estateMappingService.delete(id);
		return "tiles:redirect:list";
	}
	
}
