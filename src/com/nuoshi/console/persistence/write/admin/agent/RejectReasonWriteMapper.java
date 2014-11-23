package com.nuoshi.console.persistence.write.admin.agent;

import com.nuoshi.console.domain.agent.RejectReason;

/**
 * @author wanghongmeng
 * 
 */
public interface RejectReasonWriteMapper {

	/**
	 * @param rejectReason
	 *            拒绝理由对象
	 * @return 数据库记录id
	 */
	public int addRejectReason(RejectReason rejectReason);

	/**
	 * @param reason
	 *            拒绝理由对象
	 * @return 数据库记录id
	 */
	public int updateRejectReason(RejectReason reason);

	/**
	 * @param id
	 *            信息id
	 * @return 数据库记录id
	 */
	public int delRejectReason(int id);

	/**
	 * @param rejectReason
	 *            短信信息对象
	 * @return 数据库生成的id
	 */
	public int addMsg(RejectReason rejectReason);

	/**
	 * @param id
	 *            信息id
	 */
	public void delMsg(int id);
}
