package com.nuoshi.console.persistence.write.admin.audit;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.audit.AuditReject;

public interface AuditRejectWriteMapper {
	public int updateResleRejectPhoto(@Param("houseId")int houseId,int deleteFlag);
	public int updateRentRejectPhoto(@Param("houseId")int houseId,int deleteFlag);
	public int insertAuditReject(AuditReject auditReject);
}
