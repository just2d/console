package com.nuoshi.console.domain.user;

import javax.inject.Inject;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

import com.nuoshi.console.persistence.read.admin.user.UserMapper;


/**
 * <b>function:</b> AdminMapper JUnit测试类
 
 */

@ContextConfiguration("classpath:resources/config/spring/applicationContext-*.xml")
public class UserMapperTest extends AbstractJUnit38SpringContextTests {
	
	@Inject
	private UserMapper mapper;
	
	
	public void testAdd() {
		User user = new User();
		System.out.println("test");
	}
	
}