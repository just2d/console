package com.nuoshi.console.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.nuoshi.console.dao.QueryBeanDao;
import com.nuoshi.console.domain.topic.Locale;
import com.nuoshi.console.domain.topic.QueryBean;

@Service
public class RentQueryBeanBuilder extends QueryBeanBuilder implements InitializingBean {
	@Resource
	private QueryBeanDao queryBeanDao;
	
	private Map<Integer, List<QueryBean>> priceRent;
	private Map<String, QueryBean> constants;
	private List<QueryBean> rentTypes;

	@Override
	public QueryBean getQueryBean(String key) {
		return constants.get(key);
	}

	@Override
	public List<QueryBean> getPriceConditions(int cityScale) {
		return this.priceRent.get(cityScale);
	}

	public List<QueryBean> getRentTypes() {
		return this.rentTypes;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
//		this.constants = initConstants();
//		this.priceRent = initPrices();
//		this.rentTypes = this.initGlobalCondition("fl");
//		super.init();
	}

	private Map<String, QueryBean> initConstants() {
		Map<String, QueryBean> constant = new HashMap<String, QueryBean>();
		List<QueryBean> beans = queryBeanDao.selQueryBeanByScope(0, 2);
		for(QueryBean bean : beans) {
			constant.put(bean.getKey(), bean);
		}
		return constant;
	}

	private Map<Integer, List<QueryBean>> initPrices() {
		Map<Integer, List<QueryBean>> prices = new HashMap<Integer, List<QueryBean>>();
		
		List<QueryBean> list0 = queryBeanDao.selResalePrices("p", 2, Locale.SCALE_MAJOR);
		prices.put(Locale.SCALE_MAJOR, list0);
		
		List<QueryBean> list1 = queryBeanDao.selResalePrices("p", 2, Locale.SCALE_PRIMARY);
		prices.put(Locale.SCALE_PRIMARY, list1);

		List<QueryBean> list2 = queryBeanDao.selResalePrices("p", 2, Locale.SCALE_SECONDARY);
		prices.put(Locale.SCALE_SECONDARY, list2);

		List<QueryBean> list3 = queryBeanDao.selResalePrices("p", 2, Locale.SCALE_SMALL);
		prices.put(Locale.SCALE_SMALL, list3);

		return prices;
	}
}
