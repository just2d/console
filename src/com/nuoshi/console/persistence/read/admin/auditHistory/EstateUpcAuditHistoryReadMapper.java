package com.nuoshi.console.persistence.read.admin.auditHistory;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.audit.AuditEstateLog;
import com.nuoshi.console.view.EstateListView;

public interface EstateUpcAuditHistoryReadMapper {

	List<AuditEstateLog> getEstateUpcAuditHistoryByEstateIdByPage(@Param("estateId")Integer estateId, @Param("actionType")Integer actionType);

	List<AuditEstateLog> getEstateUpcAuditHistoryByConditionByPage(@Param("condition")EstateListView condition, @Param("actionType")Integer actionType);

}
