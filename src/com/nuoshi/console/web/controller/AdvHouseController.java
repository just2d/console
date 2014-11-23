package com.nuoshi.console.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nuoshi.console.domain.adv.AdvHouse;
import com.nuoshi.console.domain.adv.AdvHouseSearch;
import com.nuoshi.console.domain.agent.CityDist;
import com.nuoshi.console.domain.topic.Locale;
import com.nuoshi.console.service.AdvHouseService;
import com.nuoshi.console.service.LocaleService;

/**
 * @author lixf
 */
@Controller
@RequestMapping(value = "/adv/advhouse")
public class AdvHouseController extends BaseController {
	@Autowired
	private AdvHouseService advHouseService;
	/**
	 * @param model
	 * @return 广告房源列表页面
	 */
	@RequestMapping(value = "/search",method = {RequestMethod.GET, RequestMethod.POST})
	public String  searchHouses(AdvHouseSearch search, Model model) {
			List<AdvHouse> hlist=advHouseService.getAdvHouseByPage(search);
			model.addAttribute("houseList", hlist);
			model.addAttribute("searchConditions", search);
		    return "tiles:adv.advhouse.houselist";
	}
	@RequestMapping(value = "/add",method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String addAdvHouse(AdvHouse house) {
			AdvHouseSearch search=new AdvHouseSearch();
			search.setSearchCity(house.getCityId());
			search.setSearchDist(house.getDistId());
			search.setSearchBlock(house.getBlockId());
			search.setSearchSdated(house.getSdated());
			search.setSearchSdateh(house.getSdateh());
			search.setSearchEdated(house.getEdated());
			search.setSearchEdateh(house.getEdateh());
			search.setSearchHouseType(house.getHouseType());
			search.setSearchWebsite(house.getWebsite());
			search.setSearchWebsitePos(house.getWebsitePos());
			search.setIsChecked(1);
			int num= advHouseService.getAdvHouseByCount(search);
			if(num>0){
				  return  "have";
			}
			advHouseService.addAdvHouse(house);
			return "json";
	}
	@RequestMapping(value = "/edit",method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String addAdvEdit(AdvHouse house) {
			AdvHouseSearch search=new AdvHouseSearch();
			search.setSearchCity(house.getCityId());
			search.setSearchDist(house.getDistId());
			search.setSearchBlock(house.getBlockId());
			search.setSearchSdated(house.getSdated());
			search.setSearchSdateh(house.getSdateh());
			search.setSearchEdated(house.getEdated());
			search.setSearchEdateh(house.getEdateh());
			search.setSearchHouseType(house.getHouseType());
			search.setSearchWebsite(house.getWebsite());
			search.setSearchWebsitePos(house.getWebsitePos());
			search.setIsChecked(1);
			search.setId(house.getId());
			int num=advHouseService.getAdvHouseByCount(search);
			if(num>0){
				  return  "have";
			}
			advHouseService.updateAdvHouse(house);
		    return "json";
	}
	@RequestMapping(value = "/del/{id}" ,method = {RequestMethod.GET, RequestMethod.POST})
	public String delAdvHouse(@PathVariable("id") String id ) {
			String[] idArr = id.split(",");
			for (String low : idArr) {
				int delId = Integer.parseInt(low);
				advHouseService.delAdvHouse(delId);
			}
		    return "json";
	}
	@RequestMapping(value = "/loc/{pid}", method = RequestMethod.GET)
	public String getCityDist(AdvHouseSearch search,Model model, @PathVariable("pid") int pid) {
			if(pid<0){
				return "json";
			}
			Map<Integer, Locale> locMap = LocaleService.getLocalesPool();
			Map<Integer, Map<Integer, Locale>>  pcMap = new HashMap<Integer, Map<Integer, Locale>>();
			for (Integer key : locMap.keySet()) {
				Locale loc = locMap.get(key);
				Map<Integer, Locale>  map=pcMap.get(loc.getParentId());
				if(map==null){
					 map=new  HashMap<Integer, Locale>();
					 pcMap.put(loc.getParentId(), map);
				}
				map.put(key, loc);	
			}
            

			Map<Integer, Locale> haveCityMap = new HashMap<Integer, Locale>();
			Map<Integer, Locale> haveDistMap = new HashMap<Integer, Locale>();
			search.setIsChecked(1);
			List<AdvHouse> hlist = advHouseService.getAllAdvHouse(search);
		    if(hlist!=null&&hlist.size()>0){
			  for (AdvHouse atmp : hlist) {
				int cityid = atmp.getCityId();
				int distid = atmp.getDistId();
				int blockid = atmp.getBlockId();
				int locationid = atmp.getLocation();
				if (locationid == blockid) {
					 pcMap.get(distid).remove(locationid);
				}
				if (locationid == distid) {
					haveDistMap.put(locationid,LocaleService.getLocale(locationid));
				}
				if (locationid == cityid) {
					haveCityMap.put(locationid,LocaleService.getLocale(locationid));
				}
			 }
			}


			for (Integer key : haveDistMap.keySet()) {
				 int lpid=haveDistMap.get(key).getParentId();
				 if(pcMap.get(lpid)!=null&&(!pcMap.containsKey(key)||pcMap.get(key).size()==0)){
					  pcMap.get(lpid).remove(key);
				 }
				 
			}
			for (Integer key : haveCityMap.keySet()) {
				 if(pcMap.get(key).size()==0){
					  pcMap.get(haveCityMap.get(key).getParentId()).remove(key);
				 }
			}
			
			Map<Integer, Locale>    map=pcMap.get(pid);
			List<CityDist> distList = new ArrayList<CityDist>();
			 if(map!=null){
				for (Integer key : map.keySet()) {
					if(key==0)  continue;
					CityDist cd = new CityDist();
					cd.setLocalid(map.get(key).getId());
					cd.setLocalname(map.get(key).getName());
					distList.add(cd);
				}
			 }
			model.addAttribute("distList", distList);
		    return "json";
	}
    
}