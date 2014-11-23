package com.nuoshi.console.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuoshi.console.dao.QueryBeanDao;
import com.nuoshi.console.domain.topic.Locale;
import com.nuoshi.console.domain.topic.QueryBean;

@Service
public abstract class QueryBeanBuilder {
	protected static Logger logger = Logger.getLogger(QueryBeanBuilder.class);
	@Resource
	private QueryBeanDao queryBeanDao;

	@Autowired
	private LocaleService locales;
	protected List<QueryBean> areas;
	protected List<QueryBean> beds;
	protected List<QueryBean> types;
	protected List<QueryBean> decorations;
	private Map<Integer, List<Locale>> localesChildrenPool = new HashMap<Integer, List<Locale>>();

	public List<Locale> getChildrenLocals(int cityId) {
		List<Locale> list = localesChildrenPool.get(cityId);
		if(list==null){
			list = locales.getChildren(cityId);
			Locale locale = new Locale();
			locale.setId(0);
			locale.setName("不限");
			locale.setCode("");
			list.add(0, locale);
			localesChildrenPool.put(cityId, list);
		}
		return list;
	}
	
	
	public List<QueryBean> getDecorations() {
		return this.decorations;
	}

	public List<QueryBean> getBedsConditions() {
		return beds;
	}

	public List<QueryBean> getAreaConditions() {
		return areas;
	}

	public List<QueryBean> getTypeConditions() {
		return types;
	}

	public abstract QueryBean getQueryBean(String key);

	public abstract List<QueryBean> getPriceConditions(int cityScale);

	protected void init() throws Exception {
		this.areas = initGlobalCondition("a");
		this.beds = initGlobalCondition("b");
		this.types = initGlobalCondition("t");
		this.decorations = initGlobalCondition("de");
	}

	protected List<QueryBean> initGlobalCondition(String type) {
		return queryBeanDao.selQueryBeanByType(type);
	}

}
