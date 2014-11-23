package com.nuoshi.console.persistence.write.admin.control;

import com.nuoshi.console.domain.control.BlackList;
import com.nuoshi.console.domain.control.SensitiveWord;

/**
 * @author wanghongmeng
 * 
 */
public interface ControlWriteMapper {
	/**
	 * @param blackList
	 *            要添加的黑名单对象
	 */
	public void addBlack(BlackList blackList);

	/**
	 * @param blackList
	 *            要更新的黑名单对象
	 */
	public void updateBlack(BlackList blackList);

	/**
	 * @param id
	 *            要删除的黑名单id
	 */
	public void deleteBlack(int id);

	/**
	 * @param sensitiveWord
	 *            要添加的关键词对象
	 */
	public void addSensitiveWord(SensitiveWord sensitiveWord);

	/**
	 * @param sensitiveWord
	 *            要更新的关键词对象
	 */
	public void updateSensitiveWord(SensitiveWord sensitiveWord);

	/**
	 * @param id
	 *            要删除的关键词id
	 */
	public void deleteSensitiveWord(int id);
}
