package com.nuoshi.console.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.nuoshi.console.domain.control.HouseDescLinkWord;
import com.nuoshi.console.domain.topic.Locale;
import com.nuoshi.console.service.HouseDescLinkWordService;
import com.nuoshi.console.service.LocaleService;

@Controller
@RequestMapping(value = "/control")
public class HouseDescLinkWordController {
	
	@Resource
	private HouseDescLinkWordService houseDescLinkWordService;
	@Resource
	private LocaleService localeService;

	@RequestMapping(value = "/housedesclinkword/list")
	public String houseDescLinkword(Model model,HttpServletRequest request, @ModelAttribute("searchView")HouseDescLinkWord searchView) {
		// 将简化版的城市List放入application中.
		ServletContext application = request.getSession().getServletContext();
		Map<Integer, Locale> map;
		if (null==application.getAttribute("simpleLocaleMap")) {
			map = LocaleService.getLocalesPool();
			for (Iterator<Entry<Integer, Locale>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry<Integer, Locale> entry = iterator.next();
				Locale locale = entry.getValue();
				if (locale.getParentId() != 0) {
					iterator.remove();
				}
			}
			application.setAttribute("simpleLocaleMap", map);
		}
		List<HouseDescLinkWord> list = houseDescLinkWordService.getHouseDescLinkWordList(searchView);
		model.addAttribute("houseDescLinkWords", list);
		if(searchView.getKeyword() != null && searchView.getKeyword().trim().length()> 0) {
			searchView.setKeyword(searchView.getKeyword().replaceAll("%", ""));
		}
		model.addAttribute("searchView", searchView);
		return "tiles:control.housedesclinkword";
	}
	
	@RequestMapping(value = "/housedesclinkword/add")
	public String addHouseDescLinkWord(Model model, HouseDescLinkWord houseDescLinkWord) {
		String  errorStr = houseDescLinkWordService.addHouseDescLinkWord(houseDescLinkWord);
		if(errorStr == null) {
			model.addAttribute("message", "关键词添加成功");
		} else {
			model.addAttribute("message", errorStr);
		}
		return "json";
	}
	
	@RequestMapping(value = "/housedesclinkword/edit")
	public String updateHouseDescLinkWord(Model model, HouseDescLinkWord houseDescLinkWord) {
		String  errorStr = houseDescLinkWordService.updateHouseDescLinkWord(houseDescLinkWord);
		if(errorStr == null) {
			model.addAttribute("message", "关键词修改成功");
		} else {
			model.addAttribute("message", errorStr);
		}
		return "json";
	}
	
	@RequestMapping(value = "/housedesclinkword/delete/{id}")
	public String deleteLinkWord(Model model, @PathVariable("id") String id) {
		try {
			String[] idArr = id.split(",");
			for (String low : idArr) {
				int delId = Integer.parseInt(low);
				houseDescLinkWordService.deleteHouseDescLinkWord(delId);
			}
			model.addAttribute("message", "关键词删除成功");
		} catch (Exception e) {
			model.addAttribute("message", "关键词删除失败");
			e.printStackTrace();
		}
		return "json";
	}
	
	@RequestMapping(value = "/housedesclinkword/addbatch", method = RequestMethod.POST)
	public String uploadexe(Model model, HttpServletRequest request, HttpServletResponse response) {
		HouseDescLinkWord search =new HouseDescLinkWord();
		List<HouseDescLinkWord> words = null;
		try {
			words = getExcell(request);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "文件解析失败，请修改文件格式");
			return houseDescLinkword(model,request,search);
		}
		String message = "";
		for(HouseDescLinkWord word : words) {
			String errorStr = houseDescLinkWordService.addHouseDescLinkWord(word);
			if(errorStr != null ){
				message += errorStr;
			}
		}
		if("".equals(message)){
			message= "关键词已成功添加";
		}else{
			message= "关键词添加出现以下问题："+message;
		}
		model.addAttribute("message",message);
		return houseDescLinkword(model,request,search);
	}
	
	private List<HouseDescLinkWord> getExcell(HttpServletRequest request) throws UnsupportedEncodingException, IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile mFile = (CommonsMultipartFile) multipartRequest.getFile("file");
		List<HouseDescLinkWord> linkWords = new ArrayList<HouseDescLinkWord>();
		HouseDescLinkWord houseDescLinkWord = null;
		String line = null;
		if (!mFile.isEmpty()) {
				BufferedReader in = new BufferedReader(new InputStreamReader(mFile.getInputStream(), "utf-8"));
				line = in.readLine();
				while (line != null) {
					String[] array = line.split("\\|");
					try{
						houseDescLinkWord = new HouseDescLinkWord();
						System.out.println(array[0]);
						System.out.println("全国".equals(array[0]));
						if("全国".equals(array[0])){
							houseDescLinkWord.setCityId(0);
						}else{
							houseDescLinkWord.setCityId(localeService.getIdByName(array[0]));
						}
						if("二手房".equals(array[1])){
							houseDescLinkWord.setChannel(1);
						}else if("租房".equals(array[1])){
							houseDescLinkWord.setChannel(2);
						}
						houseDescLinkWord.setKeyword(array[2]);
						houseDescLinkWord = houseDescLinkWordService.handleUrl(houseDescLinkWord);
						linkWords.add(houseDescLinkWord);
						line = in.readLine();
					} catch(Exception e) {
						line = in.readLine();
					}
				}
		}
		return linkWords;
	}

}
