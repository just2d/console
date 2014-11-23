package com.nuoshi.console.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.nuoshi.console.dao.LocaleDao;
import com.nuoshi.console.domain.pckage.AgentPackage;
import com.nuoshi.console.domain.topic.Locale;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.persistence.read.taofang.pckage.AgentPackageReadMapper;

/**
 * 这个类比较特殊，基本启动后，所有的方法都是静态方法
 * 
 * @author shubo
 */
@Service
public class LocaleService implements InitializingBean {
	private static Logger logger = Logger.getLogger(LocaleService.class);
	private static final int ROOT = 0;
	private static Map<Integer, String> localesCodePool;
	private static Map<String, Integer> localesIdPool;
	private static Map<String, Integer> localesIdPool2;
	private static Map<Integer, Locale> localesPool;
	private static Map<Integer, Locale> cityLocalesPool;
	private static Map<Integer, String> localesNamePool;
	private static Map<String, Integer> localesNameIdPool;
	private static Map<Integer, String> zixunClassPool;
	private static Map<Integer, List<AgentPackage>> packagePool;
	private static final int CITY_DEPTH = 0;

	public static Map<Integer, Locale> getLocalesPool() {
		return localesPool;
	}
	public static Map<Integer, Locale> getCityLocalesPool() {
		return cityLocalesPool;
	}


	private static List<User> users;

	@Resource
	private LocaleDao localeDao;
	
	@Resource
	private AgentPackageReadMapper agentPackageReadMapper;

	public static String getCode(Integer localeId) {
		return localesCodePool.get(localeId);
	}
	public static String getFirstName(Integer localeId) {
		String code=localesCodePool.get(localeId);
		if(code!=null&&code.length()>0){
			return  code.substring(0,1);
		}
		return "";
	}

	public static String getName(Integer localeId) {
		return localesNamePool.get(localeId);
	}

	public static List<User> getUsers() {
		return users;
	}
	
	public static String getClassNameById(int classId) {
		return zixunClassPool.get(classId);
	}
	
	public static List<AgentPackage> getCityPackageInfo(int cityId) {
		return packagePool.get(cityId);
	}

	public static int getIdByCode(String code) {
		return localesIdPool.get(code);
	}
	
	public static int getIdByCode(String code, int parentId) {
		String codeKey = getCodeKey(code, parentId);
		Integer id = localesIdPool2.get(codeKey);
		return id == null ? 0 : id;
	}

	public static Locale getLocale(int localeId) {
		return localesPool.get(localeId);
	}

	public List<Locale> getCities() {
		return getChildren(ROOT);
	}

	private Locale get(int localeId) {
		return localeDao.selLocaleById(localeId);
	}

	public List<Locale> getChildren(int pid) {
		return localeDao.selChildren(pid);
	}

	/**
	 * @author ningtao 根据城市code 获得地区信息
	 * @param cityCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Locale> getDistLocalByCityId(int cityId) {
		List distList = null;
		distList = localeDao.getDistLocaleByCityId(cityId);
		return distList != null && distList.size() > 0 ? distList : Collections.EMPTY_LIST;

	}
	
	/**
	 * @author ningtao 根据城市code 获得地区信息
	 * @param cityCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Locale> getDistLocalByCityName(String cityName) {
		Integer cityId = (Integer)localesNameIdPool.get(cityName);
		List distList = null;
		distList = localeDao.getDistLocaleByCityId(cityId);
		return distList != null && distList.size() > 0 ? distList : Collections.EMPTY_LIST;
	}
	
	
	/**
	 * @author miaozhuang 根据城市名称获得城市id（仅限城市名）
	 * @param cityName
	 * @return
	 */
	public Integer getIdByName(String cityName) {
		return localeDao.getCityIdByName(cityName);
	}

	/**
	 * 根据首字母和城市获得热点区域
	 * 
	 * @param cityId
	 * @param lowerCase
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Locale> getCityCapitalBlocks(String lowerCase) {
		List<Locale> list = localeDao.getCityListByChar(lowerCase + "%", CITY_DEPTH);
		return list != null ? list : Collections.EMPTY_LIST;
	}

	private static String getCodeKey(String code, int parentId) {
		return code + "-" + parentId;
	}
	
	public void resetPackageInfo(int cityId) {
		packagePool.remove(cityId);
		packagePool.put(cityId, agentPackageReadMapper.getPackageByCityIdAndDisplayStatus(cityId));
	}

	
	@Override
	public void afterPropertiesSet() throws Exception {
//		this.init(3);
	}

	private void init(int maxDepth) {
		logger.info("加载地标数据");
		localesCodePool = new HashMap<Integer, String>();
		localesIdPool = new HashMap<String, Integer>();
		localesPool = new HashMap<Integer, Locale>();
		cityLocalesPool = new HashMap<Integer, Locale>();
		localesIdPool2 = new HashMap<String, Integer>();
		localesNamePool = new HashMap<Integer, String>();
		localesNameIdPool = new HashMap<String, Integer>();
		zixunClassPool = new HashMap<Integer, String>();
		packagePool = new HashMap<Integer, List<AgentPackage>>();
		List<Locale> locales = localeDao.selAllLocale(maxDepth);
		List<Locale> cityLocales =  localeDao.getCityListByChar(null, CITY_DEPTH);

		for (Locale locale : locales) {
			localesPool.put(locale.getId(), locale);
			localesCodePool.put(locale.getId(), locale.getCode());
			if(locale.getDepth() == 0){
				cityLocalesPool.put(locale.getId(), locale);
				packagePool.put(locale.getId(), agentPackageReadMapper.getPackageByCityIdAndDisplayStatus(locale.getId()));
			}
			localesIdPool.put(locale.getCode(), locale.getId());
			localesNamePool.put(locale.getId(), locale.getName());
			String codeKey = getCodeKey(locale.getCode(), locale.getParentId());
			localesIdPool2.put(codeKey, locale.getId());
			localesNameIdPool.put(locale.getName(), locale.getId());
		}
	}

}
