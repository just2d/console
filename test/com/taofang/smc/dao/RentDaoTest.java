package com.nuoshi.console.dao;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

import com.nuoshi.console.domain.agent.HousePhoto;
import com.nuoshi.console.domain.rent.RentInfo;


/**
 * <b>function:</b> AdminMapper JUnit测试类
 
 */

@ContextConfiguration("classpath:config/spring/applicationContext-*.xml")
public class RentDaoTest extends AbstractJUnit38SpringContextTests {
	
	@Inject
	private RentDao rentDao;
	
	
	public void testgetRent4VerifyByPage() {
		StringBuffer condition = new StringBuffer();
		HashMap params = new HashMap();
		if(StringUtils.isNotBlank("70240")){
			condition.append(" and authorid = "+"70240" );
		}
		params.put("condition", " and rs.authorid = "+"70240");
		 List<RentInfo>  rsList = rentDao.getRent4VerifyByPage(params);
		 for (RentInfo result : rsList) {
			}
		System.out.println("test");
	}
	public void testgetAllRent4VerifyByPage() {
		 List<RentInfo>  rsList = rentDao.getAllRent4VerifyByPage(null);
	
		System.out.println("test");
	}
	public void testselectRentPhotoCountByHouseId() {
		Long num = rentDao.selectRentPhotoCountByHouseId(546718,"3");
		System.out.println("test");
	}
	public void testgetHousePhoto() {
		List<HousePhoto> ls= rentDao.getHousePhoto(205,"2");
		System.out.println("test");
	}
	public void testgetAllRent() {
		List<RentInfo> ls= rentDao.getAllRent(473840);
		System.out.println("test");
	}
	public static void main(String[] args) {
		String c = "123";
		String a = "456";
		String b = "123456";
		String d =c+a;
		System.out.println(b.intern()==d.intern());
		
		
	}
	
}