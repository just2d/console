package com.nuoshi.console.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.emay.sdk.client.api.Client;

import com.taofang.service.common.client.SmsClient;
import com.taofang.service.common.client.SmsSendException;
import com.nuoshi.console.common.util.Utilities;
import com.nuoshi.console.dao.AgentMasterDao;
import com.nuoshi.console.domain.agent.Smslog;

/**
 * Created by IntelliJ IDEA. User: pekky Date: 2010-9-29 Time: 19:43:15
 */
@Service
public class SmsService extends BaseService {
	private final static String serial = "3SDK-EMY-0130-LBWNP";
	private final static String key = "806242";
	private static boolean activated = false;
	private Client sdkclient = null;

	@Resource
	private AgentMasterDao agentMasterDao;

	public SmsService() {
		try {
			if (null == sdkclient) {
				sdkclient = new Client(serial, key);
			}
			// 按照sdk的要求，每次启动之前，激活一次就好了。
			if (!activated && sdkclient.registEx(key) != 0) {
				log.error("Sms activate error");
			}
		} catch (Exception e) {
			// throw the sms exception
			e.printStackTrace();
		}
	}

	/**
	 * 取当前余额
	 * 
	 * @return
	 */
	public double getBalance() {
		try {
			return sdkclient.getBalance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 向一堆手机发送消息 ，
	 * 
	 * @param mobiles
	 *            : 手机号列表，按照接口文档要求，单次不要超过200个手机号
	 * @param content
	 * @param priority
	 * @return 0 短信发送成功 16 发送失败，号码多余200 17 发送信息失败 18 发送定时信息失败 101 客户端网络故障 305
	 *         服务器端返回错误，错误的返回值（返回值不是数字字符串） 307 目标电话号码不符合规则，电话号码必须是以0、1开头 997
	 *         平台返回找不到超时的短信，该信息是否成功无法确定 998 由于客户端网络问题导致信息发送超时，该信息是否成功下发无法确定
	 */
	public static int sentSms(String[] mobiles, String content) {
		int res = 1;
			if(mobiles!=null&&mobiles.length>0){
				for (String mobile : mobiles) {
					if(mobile.length()>5){
						sentSms(mobile,content);
					}
				}
			}
			
		return res;
	}

	public static int sentSms(String mobile, String content) {
		int res = 1;
		try {
			if (mobile.length() > 5) {
				SmsClient.send(mobile, content);
			}

		} catch (SmsSendException e) {
		}
		return res;
	}

	public int logSms(Smslog log) {
		return agentMasterDao.insertSmslog(log);
	}

	public void sendSmsIgnoreFail(int sid, String mobile, String content) {
		try {
			Smslog smslog = new Smslog();
			smslog.setUserid(sid);
			smslog.setContent(content);
			smslog.setCts(Utilities.getCurrentTimestamp());
			smslog.setMobiles(mobile);

			if (this.logSms(smslog) <= 0
					|| this.sentSms(new String[] { mobile }, content) != 0) {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean sendSms(int sid, String mobile, String content) {
		Smslog smslog = new Smslog();
		smslog.setUserid(sid);
		smslog.setContent(content);
		smslog.setCts(Utilities.getCurrentTimestamp());
		smslog.setMobiles(mobile);

		if (logSms(smslog) <= 0
				|| sentSms(new String[] { mobile }, content) != 0) {
			return false;
		}
		return true;
	}
}
