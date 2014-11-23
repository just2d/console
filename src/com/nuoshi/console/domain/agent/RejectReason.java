package com.nuoshi.console.domain.agent;

import java.io.Serializable;

/**
 * @author wanghongmeng
 * 
 */
public class RejectReason implements Serializable {
	private static final long serialVersionUID = -850498587059785507L;
	/**
	 * 主键
	 */
	private int id;
	/**
	 * 类型：0-身份证；1-头像；2-名片
	 */
	private int type;
	/**
	 * 具体理由
	 */
	private String reason;

	private String title;

	/**
	 * @return 信息id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            信息id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return 信息类型
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            信息类型
	 */

	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return 信息内容
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason
	 *            信息内容
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return 信息标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            信息标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
