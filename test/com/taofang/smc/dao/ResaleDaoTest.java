package com.nuoshi.console.dao;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

import com.nuoshi.console.domain.agent.HousePhoto;
import com.nuoshi.console.domain.resale.ResaleInfo;


/**
 * <b>function:</b> AdminMapper JUnit测试类
 
 */

@ContextConfiguration("classpath:config/spring/applicationContext-*.xml")
public class ResaleDaoTest extends AbstractJUnit38SpringContextTests {
	
	@Inject
	private ResaleDao resaleDao;
	
	
	public void testgetResale4VerifyByPage() {
		StringBuffer condition = new StringBuffer();
		HashMap params = new HashMap();
		if(StringUtils.isNotBlank("70240")){
			condition.append(" and authorid = "+"70240" );
		}
		params.put("condition", " and rs.authorid = "+"70240");
		 List<ResaleInfo>  rsList = resaleDao.getResale4VerifyByPage(params);
		 for (ResaleInfo result : rsList) {
			}
		System.out.println("test");
	}
	public void testgetAllResale4VerifyByPage() {
		 List<ResaleInfo>  rsList = resaleDao.getAllResale4VerifyByPage(null);
		 for (ResaleInfo result : rsList) {
			}
		System.out.println("test");
	}
	public void testselectResalePhotoCountByHouseId() {
		Long num = resaleDao.selectResalePhotoCountByHouseId(546718,"3");
		System.out.println("test");
	}
	public void testgetHousePhoto() {
		List<HousePhoto> ls= resaleDao.getHousePhoto(205,"2");
		System.out.println("test");
	}
	public void testgetAllResale() {
		List<ResaleInfo> ls= resaleDao.getAllVerifyResale(473840);
		System.out.println("test");
	}
	
}