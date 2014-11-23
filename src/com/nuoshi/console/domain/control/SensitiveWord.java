package com.nuoshi.console.domain.control;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wanghongmeng
 * 
 */
public class SensitiveWord {
	private int id;
	private int type;
	private int sensitiveWordType;
	private int illegalType;
	private int userType;
	private String content;
	private int entryId;
	private String madeby;
	private Date entryDate;

	/**
	 * @return 关键词id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            设置关键词id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return 关键词所属类别
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            设置关键词所属类别(0全局关键词 1房源信息 2评论 3小区内容)
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return 关键字类型
	 */
	public int getSensitiveWordType() {
		return sensitiveWordType;
	}

	/**
	 * @param sensitiveWordType
	 *            设置关键字类型(0敏感词 1非法词)
	 */
	public void setSensitiveWordType(int sensitiveWordType) {
		this.sensitiveWordType = sensitiveWordType;
	}

	/**
	 * @return 非法类型
	 */
	public int getIllegalType() {
		return illegalType;
	}

	/**
	 * @param illegalType
	 *            设置非法类型(0涉政 1黄赌毒 2灌水 3其他)
	 */
	public void setIllegalType(int illegalType) {
		this.illegalType = illegalType;
	}

	/**
	 * @return 关键词内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            设置关键词内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return 关键词添加人id
	 */
	public int getEntryId() {
		return entryId;
	}

	/**
	 * @return 关键词添加人id
	 */
	public void setEntryId(int entryId) {
		this.entryId = entryId;
	}

	/**
	 * @return 关键词添加人
	 */
	public String getMadeby() {
		return madeby;
	}

	/**
	 * @param madeby
	 *            设置关键词添加人
	 */
	public void setMadeby(String madeby) {
		this.madeby = madeby;
	}

	/**
	 * @return 添加日期，按照yyyy-mm-dd hh:mm的格式返回
	 */
	public String getEntryDate() {
		if (null == entryDate) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd HH:mm");
		return sdf.format(entryDate);
	}

	/**
	 * @param entryDate
	 *            设置添加日期
	 */
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}
}