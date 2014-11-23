package com.nuoshi.console.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.nuoshi.console.common.Resources;

/**
 * 与crm通讯的服务
 * @author wangjh
 *
 */
@Service
public class CrmService extends BaseService{
	Log log=LogFactory.getLog(CrmService.class);
	/**
	 * 保存user时，传给crm，新旧mobile
	 */
	public void onlineUpdatePort(String oldMobile,String newMobile){
		String urlStr=Resources.getString("sys.url.crm")+"/contract/onlineUpdatePort?oldMobile="+oldMobile+"&newMobile="+newMobile;
		try{
			super.callService(urlStr);
		}catch (RuntimeException e) {
			log.error("保存或更新user，时传给crm新旧mobile时异常！", e);
		}
	}

}
