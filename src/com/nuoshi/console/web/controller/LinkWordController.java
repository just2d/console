package com.nuoshi.console.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
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

import com.nuoshi.console.domain.control.LinkWord;
import com.nuoshi.console.service.LinkWordService;

@Controller
@RequestMapping(value = "/control")
public class LinkWordController {
	
	@Resource
	private LinkWordService linkWordService;

	@RequestMapping(value = "/linkword/list")
	public String linkWordList(Model model, @ModelAttribute("searchView")LinkWord searchView) {
		List<LinkWord> list = linkWordService.getLinkWordList(searchView);
		model.addAttribute("linkWords", list);
		model.addAttribute("searchView", searchView);
		return "tiles:control.linkword";
	}
	
	@RequestMapping(value = "/linkword/add")
	public String addLinkWord(Model model, LinkWord linkWord) {
		int result = linkWordService.addLinkWord(linkWord);
		if(result > 0) {
			model.addAttribute("message", "关键词添加成功");
		} else {
			model.addAttribute("message", "关键词添加失败");
		}
		return "json";
	}
	
	@RequestMapping(value = "/linkword/edit")
	public String updateLinkWord(Model model, LinkWord linkWord) {
		int result = linkWordService.updateLinkWord(linkWord);
		if(result > 0) {
			model.addAttribute("message", "关键词修改成功");
		} else {
			model.addAttribute("message", "关键词修改失败");
		}
		return "json";
	}
	
	@RequestMapping(value = "/linkword/delete/{id}")
	public String deleteLinkWord(Model model, @PathVariable("id") String id) {
		try {
			String[] idArr = id.split(",");
			for (String low : idArr) {
				int delId = Integer.parseInt(low);
				linkWordService.deleteLinkWord(delId);
			}
			model.addAttribute("message", "关键词删除成功");
		} catch (Exception e) {
			model.addAttribute("message", "关键词删除失败");
			e.printStackTrace();
		}
		return "json";
	}
	
	@RequestMapping(value = "/linkword/addbatch", method = RequestMethod.POST)
	public String uploadexe(Model model, HttpServletRequest request, HttpServletResponse response) {
		List<LinkWord> words = getExcell(request);
		for(LinkWord word : words) {
			linkWordService.addLinkWord(word);
		}
		return "redirect:/control/linkword/list";
	}
	
	private List<LinkWord> getExcell(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile mFile = (CommonsMultipartFile) multipartRequest.getFile("file");
		List<LinkWord> linkWords = new ArrayList<LinkWord>();
		LinkWord linkWord = null;
		String line = null;
		if (!mFile.isEmpty()) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(mFile.getInputStream(), "utf-8"));
				line = in.readLine();
				while (line != null) {
					String[] array = line.split("\\|");
					try{
						linkWord = new LinkWord();
						linkWord.setKeyword(array[0]);
						linkWord.setUrl(array[1]);
						linkWords.add(linkWord);
						line = in.readLine();
					} catch(Exception e) {
						line = in.readLine();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return linkWords;
	}

}
