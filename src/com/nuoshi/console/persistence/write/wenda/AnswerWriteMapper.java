package com.nuoshi.console.persistence.write.wenda;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AnswerWriteMapper {
	void sign(@Param("sign")String auditSign, @Param("idList")List<Integer> idList);

	void updateStatus(@Param("idList")List<Integer> idList, @Param("status")Integer status,
			@Param("isClearAuditSign")boolean isClearAuditSign);

	void changeAnswerBlack(@Param("userId")int userId, @Param("type")int type);

}
