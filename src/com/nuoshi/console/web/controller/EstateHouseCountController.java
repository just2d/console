package com.nuoshi.console.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.nuoshi.console.domain.estate.EstateHouseCount;
import com.nuoshi.console.service.EstateHouseCountService;

@Controller
@RequestMapping("/statis/estate")
public class EstateHouseCountController extends BaseController {
	private final String TITLE_1 = "推广计划名称";
	private final String TITLE_2 = "推广单元名称";
	private final String TITLE_3 = "关键词";
	private final String TITLE_4 = "关键词访问URL";
	private final String TITLE_5 = "二手房源数";
	private final String TITLE_6 = "出租房源数";
	private final String RESPONSE_TYPE = "application/vnd.ms-excel";

	@Resource
	private EstateHouseCountService estateHouseCountService;

	/**
	 * 房源数量初始页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getcount", method = RequestMethod.GET)
	public String getHouseCount(HttpServletRequest request,HttpServletResponse response, Model model) {
		return "tiles:statis.estateHouseCount";
	}

	@RequestMapping(value = "/postcount", method = RequestMethod.POST)
	public String uploadexe(Model model, HttpServletRequest request, HttpServletResponse response) {
		List<EstateHouseCount> houseCountList = getExcell(request);
		estateHouseCountService.getHouseCount(houseCountList);
		writeExcell(response, houseCountList);
		return null;
	}
	
	private void writeExcell(HttpServletResponse response, List<EstateHouseCount> list) {
		try{
			response.setContentType(RESPONSE_TYPE);
			response.addHeader("Content-Disposition", "attachment;filename=" + new String("estateHouseCount.xls".getBytes("gb2312"), "iso8859-1"));
			response.setCharacterEncoding("gb2312");
			PrintWriter out;
			out = response.getWriter();
			out.println(TITLE_1 + "\t" + TITLE_2 + "\t" + TITLE_3 + "\t" + TITLE_4 + "\t"
					+ TITLE_5 + "\t" + TITLE_6);
			for (EstateHouseCount estateHouse : list) {
				out.println(estateHouse.getPopularizePlan() + "\t"
						+ estateHouse.getPopularizeUnit() + "\t"
						+ estateHouse.getPopularizeKeyword() + "\t"
						+ estateHouse.getUrl() + "\t"
						+ estateHouse.getResaleCount() + "\t"
						+ estateHouse.getRentCount());
			}
			out.flush();
		}catch(Exception e) {
			
		}
	}
	
	private List<EstateHouseCount> getExcell(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile mFile = (CommonsMultipartFile) multipartRequest.getFile("file");
		List<EstateHouseCount> houseCountList = new ArrayList<EstateHouseCount>();
		EstateHouseCount houseCount = null;
		String line = null;
		if (!mFile.isEmpty()) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(mFile.getInputStream(), "GBK"));
				line = in.readLine();
				line = in.readLine();
				while (line != null) {
					String[] array = line.split("\t");
					try{
						houseCount = new EstateHouseCount();
						houseCount.setPopularizePlan(array[0]);
						houseCount.setPopularizeUnit(array[1]);
						houseCount.setPopularizeKeyword(array[2]);
						houseCount.setUrl(array[3]);
						houseCountList.add(houseCount);
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
		return houseCountList;
	}

}
