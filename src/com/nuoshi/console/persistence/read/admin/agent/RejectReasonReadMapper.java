package com.nuoshi.console.persistence.read.admin.agent;

import java.util.List;

import com.nuoshi.console.domain.agent.RejectReason;

/**
 * @author wanghongmeng
 * 
 */
public interface RejectReasonReadMapper {
	/**
	 * @return 拒绝理由集合
	 */
	public List<RejectReason> getAllRejectReason();

	/**
	 * @param type
	 *            类型
	 * @return 相应type的拒绝理由集合
	 */
	public List<RejectReason> getAllRejectReasonByPage(int type);
	/**
	 * @param type
	 *            类型
	 * @return 相应type的拒绝理由集合
	 */
	public List<RejectReason> getAllRejectReasonByType(int type);

	/**
	 * @return 拒绝理由集合
	 */
	public List<RejectReason> getAllRejectReason2ByPage();
	


	/**
	 * @param type
	 *            信息类型
	 * @return 相应tpye的信息集合
	 */
	public List<RejectReason> getAllMsg(int type);
}
