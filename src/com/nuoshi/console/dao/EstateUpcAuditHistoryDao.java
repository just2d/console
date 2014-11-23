package com.nuoshi.console.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.audit.AuditEstateLog;
import com.nuoshi.console.persistence.read.admin.auditHistory.EstateUpcAuditHistoryReadMapper;
import com.nuoshi.console.view.EstateListView;

@Repository
public class EstateUpcAuditHistoryDao {
	
	@Resource
	private EstateUpcAuditHistoryReadMapper estateUpcAuditHistoryReadMapper;

	public List<AuditEstateLog> getEstateUpcAuditHistoryByEstateId(Integer estateId, Integer actionType) {
		// TODO Auto-generated method stub
		return estateUpcAuditHistoryReadMapper.getEstateUpcAuditHistoryByEstateIdByPage(estateId,actionType);
	}

	public List<AuditEstateLog> getEstateUpcAuditHistoryByCondition(EstateListView condition, Integer actionType) {
		// TODO Auto-generated method stub
		return estateUpcAuditHistoryReadMapper.getEstateUpcAuditHistoryByConditionByPage(condition,actionType);
	}

}
