package com.nuoshi.console.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoshi.console.dao.EstateUpcAuditHistoryDao;
import com.nuoshi.console.domain.audit.AuditEstateLog;
import com.nuoshi.console.view.EstateListView;

@Service
public class EstateUpcAuditHistoryService extends BaseService {
	
	@Resource
	private EstateUpcAuditHistoryDao estateUpcAuditHistoryDao;

	public List<AuditEstateLog> getEstateUpcAuditHistoryByEstateId(Integer estateId, Integer actionType) {
		return estateUpcAuditHistoryDao.getEstateUpcAuditHistoryByEstateId(estateId,actionType);
	}

	public List<AuditEstateLog> getEstateUpcAuditHistoryByCondition(EstateListView condition, Integer actionType) {
		return estateUpcAuditHistoryDao.getEstateUpcAuditHistoryByCondition(condition,actionType);
	}


	


}
