package com.nuoshi.console.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nuoshi.console.common.Globals;
import com.nuoshi.console.common.util.SmcConst;
import com.nuoshi.console.domain.stat.AuditSearch;
import com.nuoshi.console.domain.stat.ConvertRate;
import com.nuoshi.console.domain.stat.HouseQuality;
import com.nuoshi.console.domain.stat.PhotoAuditStatis;
import com.nuoshi.console.domain.stat.RateSearch;
import com.nuoshi.console.domain.topic.Locale;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.service.ConvertRateService;
import com.nuoshi.console.service.LocaleService;
import com.nuoshi.console.web.session.SessionUser;

@Controller
@RequestMapping(value = "/rate")
public class ConvertRateController {
	@Autowired
	private ConvertRateService convertRateService;

	/**
	 * @param model
	 * @return 列表页转化率统计
	 */
	@RequestMapping(value = "/rateList")
	public String getList(Model model, HttpServletRequest request) {
		String beginTimeStr = request.getParameter("startTime");
		String endTimeStr = request.getParameter("endTime");
		String cityIdStr = request.getParameter("cityId");
		String rateType = request.getParameter("rateType");
		if (StringUtils.isBlank(rateType)) {
			rateType = "0";
		}

		String beginTimeFormat = null;
		String endTimeFormat = null;
		if (StringUtils.isNotBlank(beginTimeStr)) {
			beginTimeFormat = beginTimeStr.replace("-", "");
		}
		if (StringUtils.isNotBlank(endTimeStr)) {
			endTimeFormat = endTimeStr.replace("-", "");
		}
		RateSearch rateSearch = new RateSearch();
		if (StringUtils.isNotBlank(cityIdStr)) {
			try {
				rateSearch.setCityId(Integer.valueOf(cityIdStr));
			} catch (Exception e) {
			}
		}
		rateSearch.setBeginTime(beginTimeFormat);
		rateSearch.setEndTime(endTimeFormat);
		rateSearch.setRateType(rateType);
		List<ConvertRate> list = convertRateService.queryRateByPage(rateSearch);
		model.addAttribute("rateList", list);
		model.addAttribute("startTime", beginTimeStr);
		model.addAttribute("endTime", endTimeStr);
		model.addAttribute("cityId", cityIdStr);
		model.addAttribute("rateType", rateType);
		return "tiles:rate.rateList";
	}

	/**
	 * 数据管理>房源审核统计
	 * 
	 * @param model
	 * @param request
	 * @param as
	 * @return
	 */
	@RequestMapping(value = "/auditList")
	public String getAuditList(Model model, HttpServletRequest request,
			@ModelAttribute("auditSearch") AuditSearch as) {
		boolean b = true;
		if (as.getRateAgentType() ==null) {
			b = false;
		}

		ServletContext application = request.getSession().getServletContext();
		Map<Integer, Locale> map;
		if (null == application.getAttribute("simpleLocaleMap")) {
			map = LocaleService.getLocalesPool();
			// Iterator<Entry<Integer, Locale>> iterator =
			// map.entrySet().iterator();
			// while(iterator.hasNext()){
			// Entry<Integer, Locale> entry = iterator.next();
			// Locale local = entry.getValue();
			// if(local.getParentId()!=0){iterator.remove();}
			// }
			application.setAttribute("simpleLocaleMap", map);
		}
		System.out.println("@test .... "+as.getRateType());
		List<PhotoAuditStatis> resList = null;
		if (b) {
			resList = convertRateService.getPhotoAuditStatisList(as);
		}

		model.addAttribute("auditSearch", as);
		model.addAttribute("resList", resList);
		return "tiles:rate.auditList";
	}
	
	/**
	 * 数据管理>房源审核统计
	 * 
	 * @param model
	 * @param request
	 * @param as
	 * @return
	 */
	@RequestMapping(value = "/exportExcel")
	public void getAuditListExportExcel(Model model, HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("auditSearch") AuditSearch as) {
		try {
			boolean b = true;
			if (as.getRateAgentType() ==null) {
				b = false;
			}

			List<PhotoAuditStatis> resList = null;
			if (b) {
				resList = convertRateService.getPhotoAuditStatisListToExcel(as);
			}
			String title = "时间\t经纪人ID\t";
			if (StringUtils.isNotEmpty(as.getRateType())) {
				if ("0".equals(as.getRateType())) {
					title += "全部-已审核量\t";
				} else if ("1".equals(as.getRateType())) {
					title += "租房-已审核量\t";
				} else {
					title += "二手房-已审核量\t";
				}
			}
			if(as.getRateStatus()!=null){
				switch (as.getRateStatus()) {
				case -1:
					title +="上传总量\t总上传-小区图\t总上传-室内图\t总上传-户型图";
					break;
				case 0:
					title +="待审率\t待审-小区图\t待审-室内图\t待审-户型图";
					break;
				case 1:
					title +="通过率\t通过-小区图\t通过-室内图\t通过-户型图";
					break;
				case 2:
					title +="违规率\t拒绝-小区图\t拒绝-室内图\t拒绝-户型图";
					break;
				default:
					break;
				}
			}
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition","attachment;filename=" + new String("photoStatis.xls".getBytes("gb2312"), "iso8859-1"));
			response.setCharacterEncoding("gb2312");
			PrintWriter out;
			out = response.getWriter();
			out.println(title);
			if (resList != null && resList.size() > 0) {
				System.out.println("房源图片审核统计：导出excel列表个数为: "+resList.size());
				for (PhotoAuditStatis obj : resList) {
					String put = obj.getUploadDate() + "\t" + obj.getAgentId()	+ "\t";
					put += (obj.getpAllCount() + obj.getrAllCount()) + "\t";
					if (as.getRateStatus() != null) {
						switch (as.getRateStatus()) {
						case -1:
							put += obj.getAllCount() + "\t"+(obj.getwEstateCount()+obj.getpEstateCount()+obj.getrEstateCount())+"\t"+(obj.getwInnerCount()+obj.getpInnerCount()+obj.getrInnerCount())+"\t"+(obj.getwLayoutCount()+obj.getpLayoutCount()+obj.getrLayoutCount());
							break;
						case 0:
							if(obj.getAllCount()!=0){
								put += (new BigDecimal((obj.getwAllCount()*100)+"")).divide(new BigDecimal(obj.getAllCount()+""),1,BigDecimal.ROUND_HALF_UP)+"%\t"; 
							}else{
								put += "\t";
							}
							put += obj.getwEstateCount()+"\t"+obj.getwInnerCount()+"\t"+obj.getwLayoutCount();
							break;
						case 1:
							if((obj.getrAllCount()+obj.getpAllCount())!=0){
								put += (new BigDecimal((obj.getpAllCount()*100)+"")).divide(new BigDecimal((obj.getrAllCount()+obj.getpAllCount())+""),1,BigDecimal.ROUND_HALF_UP)+"%\t"; 
							}else{
								put += "\t";
							}
							put += obj.getpEstateCount()+"\t"+obj.getpInnerCount()+"\t"+obj.getpLayoutCount();
							break;
						case 2:
							if((obj.getrAllCount()+obj.getpAllCount())!=0){
								put += (new BigDecimal((obj.getrAllCount()*100)+"")).divide(new BigDecimal((obj.getrAllCount()+obj.getpAllCount())+""),1,BigDecimal.ROUND_HALF_UP)+"%\t"; 
								System.out.println((new BigDecimal((obj.getrAllCount()*100)+"")).divide(new BigDecimal((obj.getrAllCount()+obj.getpAllCount())+""),1,BigDecimal.ROUND_HALF_UP)+"%");
							}else{
								put += "\t";
							}
							put += obj.getrEstateCount()+"\t"+obj.getrInnerCount()+"\t"+obj.getrLayoutCount();
							break;
						default:
							break;
						}
					}
					out.println(put);
				}
			}
			out.flush();
			out.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e){
			System.out.println("房源图片审核统计：抛出异常 "+e+",时间是："+new Date());
		}
		
		

	}

	/**
	 * 房源质量审核统计
	 * 
	 * @return
	 */
	@RequestMapping(value = "/auditQualityStatis")
	public String auditQualityStatis(Model model, HttpServletRequest request) {
		String searchStartDate = request.getParameter("searchStartDate");
		String searchEndDate = request.getParameter("searchEndDate");

		String cityId = request.getParameter("cityId");
		String auditUserId = request.getParameter("auditUserId");
		String tableType = request.getParameter("tableType");
		if (StringUtils.isBlank(tableType))	tableType = "1";
		// if("1".equals(tableType)){
		//
		// }else{
		// if(searchDate==null||"".equals(searchDate)){
		// searchDate = this.getToday();
		// }
		// }
		// int isFlag = 0;
		// if(StringUtils.isNotBlank(auditUserId)){
		// cityId="0";
		// isFlag = 1;
		// }
		HouseQuality hq = new HouseQuality();
		hq.setSearchStartDate(searchStartDate);
		hq.setSearchEndDate(searchEndDate);
		hq.setCityId(cityId);
		hq.setAuditUserId(auditUserId);

		List<HouseQuality> list = null;
		List<HouseQuality> other = null;

		// 取得房源审核的所有人员
		List<User> userList = convertRateService.getAllUsersByRoleId(Globals.PERMISSIOONS_HOUSE_MANAGE);//16

		// if(StringUtils.isNotBlank(cityId)){
		SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(SmcConst.SESSION_USER);
		String userName = sessionUser.getUser().getUserName();
		if (StringUtils.isNotBlank(tableType) && "0".equals(tableType)) {
			List<List<HouseQuality>> temp = convertRateService.getAuditHouseQualityList(hq, userName);
			list = (List<HouseQuality>) temp.get(0);
			other = (List<HouseQuality>) temp.get(1);

		} else if (StringUtils.isNotBlank(tableType) && "1".equals(tableType)) {// 历史记录
			hq.setSearchType(2);// 设置非0数字
			List<List<HouseQuality>> temp = convertRateService
					.getAuditHouseQualityHistoryListByPage(hq, userName);
			list = (List<HouseQuality>) temp.get(0);
			other = (List<HouseQuality>) temp.get(1);
		}
		// }

		model.addAttribute("resList", list);
		model.addAttribute("otherList", other);
		model.addAttribute("searchStartDate", StringUtils.isNotBlank(searchStartDate)?searchStartDate:this.getToday());
		model.addAttribute("searchEndDate",StringUtils.isNotBlank(searchEndDate)?searchEndDate:this.getToday());
		model.addAttribute("auditUserId", auditUserId);
		model.addAttribute("cityId", cityId);
		model.addAttribute("tableType", tableType);
		model.addAttribute("userList", userList);
		return "tiles:rate.auditQualityStatis";
	}

	private String getToday() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		return date;
	}
	public static void main(String[] args) {
		int i = 4;
		int j = 9;
		BigDecimal ii = new BigDecimal(i*100+"");
		BigDecimal jj = new BigDecimal(j+"");
		System.out.println(ii+","+jj);
		BigDecimal obj = ii.divide(jj,1,BigDecimal.ROUND_HALF_UP);
		System.out.println(obj.toString()+"%");
	}
}
