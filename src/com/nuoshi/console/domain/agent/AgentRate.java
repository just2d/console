package com.nuoshi.console.domain.agent;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wanghongmeng
 * 
 */
public class AgentRate implements Serializable {
	private static final long serialVersionUID = -3869504709237882375L;
	private int type;
	private String cmt;
	private Date cts;
	private String customername;
	private String customermobile;
	private int flags;

	/**
	 * @return 评价类型 0 好评 1 中评 2 差评
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            评价类型 0 好评 1 中评 2 差评
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return 评价内容
	 */
	public String getCmt() {
		return cmt;
	}

	/**
	 * @param cmt
	 *            评价内容
	 */
	public void setCmt(String cmt) {
		this.cmt = cmt;
	}

	/**
	 * @return 按照yyyy-mm-dd hh:mm:ss格式返回评价时间
	 */
	public String getCts() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(cts);
	}

	/**
	 * @param cts
	 *            按照yyyy-mm-dd hh:mm:ss格式返回评价时间
	 */
	public void setCts(Date cts) {
		this.cts = cts;
	}

	/**
	 * @return 评价人姓名
	 */
	public String getCustomername() {
		return customername;
	}

	/**
	 * @param customername
	 *            评价人姓名
	 */
	public void setCustomername(String customername) {
		this.customername = customername;
	}

	/**
	 * @return 评价人电话
	 */
	public String getCustomermobile() {
		return customermobile;
	}

	/**
	 * @param customermobile
	 *            评价人电话
	 */
	public void setCustomermobile(String customermobile) {
		this.customermobile = customermobile;
	}

	/**
	 * @return 评价标识符 0 未审核评价 1 无效评价
	 */
	public int getFlags() {
		return flags;
	}

	/**
	 * @param flags
	 *            评价标识符 0 未审核评价 1 无效评价
	 */
	public void setFlags(int flags) {
		this.flags = flags;
	}
}
