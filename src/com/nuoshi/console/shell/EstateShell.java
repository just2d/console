package com.nuoshi.console.shell;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nuoshi.console.common.Helper;
import com.nuoshi.console.domain.topic.Estate;
import com.nuoshi.console.service.EstateService;

/***
 * 初始化小区数据
 * @author ningt
 *
 */
public class EstateShell {
	public static void main(String args[]){
		ApplicationContext  ctx = new ClassPathXmlApplicationContext("classpath:config\\spring\\applicationContext-*.xml");
		if(ctx!=null){
			EstateService estateService = (EstateService)ctx.getBean("estateService");
			int total = estateService.countBasicInfoNum();
			int pageSize = Helper.getConfigAsInt("pageSize");
			for (int startIdx = 0; startIdx < total; startIdx += pageSize) {
				// 每次查询出1000条记录,并更新表格
				List<Estate> basicInfoList = estateService.initBasicInfo(startIdx, pageSize);
			}
		}
		
		
	}
}
