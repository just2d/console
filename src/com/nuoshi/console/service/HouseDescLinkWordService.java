package com.nuoshi.console.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.nuoshi.console.common.util.TemplateUtil;
import com.nuoshi.console.dao.HouseDescLinkWordDAO;
import com.nuoshi.console.domain.control.HouseDescLinkWord;

@Service
public class HouseDescLinkWordService extends BaseService {
	
	@Resource
	private HouseDescLinkWordDAO houseDescLinkWordDAO;

	public int add(HouseDescLinkWord houseDescLinkWord) {
		return  houseDescLinkWordDAO.add(houseDescLinkWord);
	}

	public int deleteById(int id) {
		return houseDescLinkWordDAO.deleteById(id);
	}
	
	public HouseDescLinkWord getById(int id) {
		return houseDescLinkWordDAO.getById(id);
	}

	public List<HouseDescLinkWord> getHouseDescLinkWordList(HouseDescLinkWord searchView) {
		if(StringUtils.isNotBlank(searchView.getKeyword()) && !"请输入关键字".equals(searchView.getKeyword())){
			searchView.setKeyword("%"+searchView.getKeyword()+"%");
		}else{
			searchView.setKeyword(null);
		}
		if(StringUtils.isNotBlank(searchView.getStartDate()) && !"起始日期".equals(searchView.getStartDate())){
			
		}else{
			searchView.setStartDate(null);
		}
		
		if(StringUtils.isNotBlank(searchView.getEndDate()) && !"终止日期".equals(searchView.getEndDate())){
			
		}else{
			searchView.setEndDate(null);
		}
		
		return houseDescLinkWordDAO.getHouseDescLinkWordList(searchView);
	}

	public String addHouseDescLinkWord(HouseDescLinkWord houseDescLinkWord) {
		boolean existFlag = houseDescLinkWordDAO.isHouseDescLinkWordExist(houseDescLinkWord);
		if(existFlag){
			return "该关键词\""+houseDescLinkWord.getKeyword()+"\" 已存在  ";
		}
		houseDescLinkWord = handleUrl(houseDescLinkWord);
		int num = houseDescLinkWordDAO.add(houseDescLinkWord);
		if(num != 1) return "关键词\""+houseDescLinkWord.getKeyword()+"\" 添加失败";
		return null;
	}

	public String updateHouseDescLinkWord(HouseDescLinkWord houseDescLinkWord) {
		HouseDescLinkWord houseDescLinkWordDb = houseDescLinkWordDAO.getById(houseDescLinkWord.getId());
		if(houseDescLinkWordDb.getChannel() == houseDescLinkWord.getChannel() && houseDescLinkWordDb.getCityId() == houseDescLinkWord.getCityId() && houseDescLinkWordDb.getKeyword().equals(houseDescLinkWord.getKeyword())){
			return "此关键词无改动，无需更新";
		}
		boolean existFlag = houseDescLinkWordDAO.isHouseDescLinkWordExist(houseDescLinkWord);
		if(existFlag){
			return "该关键词\""+houseDescLinkWord.getKeyword()+"\" 已存在";
		}
		houseDescLinkWord = handleUrl(houseDescLinkWord);
		int num = houseDescLinkWordDAO.updateHouseDescLinkWord(houseDescLinkWord);
		if(num != 1) return "关键词\""+houseDescLinkWord.getKeyword()+"\" 更新失败";
		return null;
	}

	public void deleteHouseDescLinkWord(int delId) {
		houseDescLinkWordDAO.deleteById(delId);
		
	}
	
	public HouseDescLinkWord handleUrl(HouseDescLinkWord houseDescLinkWord){
		String urlTemplate = "http://{code}.taofang.com/{prefix}_{keyword}/";
		TemplateUtil tempUtil = new TemplateUtil();
		if(houseDescLinkWord.getCityId() == 0){
			tempUtil.setParameter("{code}", "code");
		}else{
			tempUtil.setParameter("{code}", LocaleService.getCode(houseDescLinkWord.getCityId()));
		}
		switch (houseDescLinkWord.getChannel()) {
		case 1:
			tempUtil.setParameter("{prefix}", "e");
			break;
		case 2:
			tempUtil.setParameter("{prefix}", "z");
			break;
		default:
			break;
		}
		tempUtil.setParameter("{keyword}", houseDescLinkWord.getKeyword());
		urlTemplate = tempUtil.replace(urlTemplate);
		houseDescLinkWord.setUrl(urlTemplate);
		return houseDescLinkWord;
	}
}
