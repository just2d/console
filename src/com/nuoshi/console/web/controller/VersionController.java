package com.nuoshi.console.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nuoshi.console.common.NetClient;
import com.nuoshi.console.domain.version.ProjectVersion;
import com.nuoshi.console.service.ProjectVersionService;

/**
 * @author wanghongmeng
 * 
 */
@Controller
public class VersionController extends BaseController {
	@Resource
	private ProjectVersionService projectVersionService;

	/**
	 * @param model
	 * @param id
	 *            刷新版本号
	 */
	@RequestMapping(value = "/refresh-versionstamp", method = RequestMethod.GET)
	public String refreshVersionstamp(Model model, HttpServletRequest request, HttpServletResponse response) {
		List<ProjectVersion> pvs = projectVersionService.getAll();
		model.addAttribute("versions", pvs);
		return "tiles:refresh-versionstamp";
	}

	/**
	 * @param model
	 * @param id
	 *            刷新版本号
	 */
	@RequestMapping(value = "/refresh-versionstamp", method = RequestMethod.POST)
	public String doRefreshVersionstamp(Model model, HttpServletRequest request, HttpServletResponse response) {

		String[] urls = request.getParameterValues("url");
		String ver = request.getParameter("ver");
		if (StringUtils.isBlank(ver)) {
			ver = System.currentTimeMillis() + "";
		}
		String error = "";
		if (urls != null && urls.length > 0) {
			for (String url : urls) {
				try {
					if (url.startsWith("cache:")) {
						clearMemCache(url.replace("cache:", ""));
					} else {
						if (StringUtils.isNotBlank(url)) {
							NetClient.getHttpResponse(url + "?ver=" + ver, 1000);
						}
					}
					
				} catch (Exception e) {
					error += url+":刷新失败;\n";
					e.printStackTrace();
				}
			}
		} else {
			model.addAttribute("error", "请选择项目");
		}
		if(StringUtils.isNotBlank(error)){
			model.addAttribute("error", error);
		}else{
			model.addAttribute("success", "success");
		}
		
		return "json";
	}

	private void clearMemCache(String url) {
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(url));

		MemcachedClient memcachedClient = null;
		try {
			memcachedClient = builder.build();
			memcachedClient.flushAll();
		} catch (MemcachedException e) {
			System.err.println("MemcachedClient operation fail");
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (memcachedClient != null) {
					memcachedClient.shutdown();
				}
			} catch (IOException e) {

				System.err.println("Shutdown MemcachedClient fail");

				e.printStackTrace();

			}
		}
	}

	/**
	 * @param model
	 * @param id
	 *            添加项目版本信息
	 */
	@RequestMapping(value = "/refresh-versionstamp/add", method = RequestMethod.POST)
	public String addVersion(Model model, HttpServletRequest request, HttpServletResponse response) {

		String name = request.getParameter("name");
		String versionUrl = request.getParameter("versionUrl");
		if (StringUtils.isBlank(name)) {
			model.addAttribute("error", "名称不能为空");
			return "json";
		}
		if (StringUtils.isBlank(versionUrl)) {
			model.addAttribute("error", "接口路径不能为空");
			return "json";
		}
		ProjectVersion pv = new ProjectVersion();
		pv.setName(name);
		pv.setVersionUrl(versionUrl);
		projectVersionService.add(pv);
		model.addAttribute("info", "添加成功");

		return "json";
	}

	/**
	 * @param model
	 * @param id
	 *            更新项目版本信息
	 */
	@RequestMapping(value = "/refresh-versionstamp/edit/{id}", method = RequestMethod.POST)
	public String doEditeVersion(Model model, HttpServletRequest request, HttpServletResponse response, @PathVariable("id") int id) {

		String name = request.getParameter("name");
		String versionUrl = request.getParameter("versionUrl");
		if (StringUtils.isBlank(name)) {
			model.addAttribute("error", "名称不能为空");
			return "json";
		}
		if (StringUtils.isBlank(versionUrl)) {
			model.addAttribute("error", "接口路径不能为空");
			return "json";
		}
		ProjectVersion pv = new ProjectVersion();
		pv.setId(id);
		pv.setName(name);
		pv.setVersionUrl(versionUrl);
		projectVersionService.update(pv);
		model.addAttribute("info", "更新成功");

		return "json";
	}

	/**
	 * @param model
	 * @param id
	 *            更新项目版本信息
	 */
	@RequestMapping(value = "/refresh-versionstamp/edit/{id}", method = RequestMethod.GET)
	public String editeVersion(Model model, HttpServletRequest request, HttpServletResponse response, @PathVariable("id") int id) {
		ProjectVersion pv = projectVersionService.getById(id);
		model.addAttribute("pv", pv);
		return "json";
	}

	/**
	 * @param model
	 * @param id
	 *            添加项目版本信息
	 */
	@RequestMapping(value = "/refresh-versionstamp/delete/{id}", method = RequestMethod.POST)
	public String delVersion(Model model, HttpServletRequest request, HttpServletResponse response, @PathVariable("id") int id) {
		projectVersionService.deleteById(id);
		try {
			model.addAttribute("info", "删除成功");
		} catch (Exception e) {
			model.addAttribute("info", "删除失败");
		}
		return "json";
	}

}
