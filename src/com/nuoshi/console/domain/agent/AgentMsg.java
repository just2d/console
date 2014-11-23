package com.nuoshi.console.domain.agent;

import java.io.Serializable;

/**
 * @author wanghongmeng
 * 
 */
public class AgentMsg implements Serializable {
	private static final long serialVersionUID = -8257177868156259603L;
	private String mobiles;
	private String content;

	/**
	 * @return 电话字符串
	 */
	public String getMobiles() {
		return mobiles;
	}

	/**
	 * @param mobiles
	 *            电话字符串
	 */
	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}

	/**
	 * @return 短信内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            短信内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
}
