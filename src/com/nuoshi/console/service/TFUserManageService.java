package com.nuoshi.console.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.nuoshi.console.domain.agent.AgentSearch;
import com.nuoshi.console.domain.user.TFUser;
import com.nuoshi.console.persistence.read.taofang.agent.TFUserReadMapper;
import com.nuoshi.console.persistence.write.taofang.agent.TFUserWriteMapper;

@Service
public class TFUserManageService extends BaseService {
	@Resource
	private TFUserReadMapper tfUserReadMapper;
	
	@Resource
	private TFUserWriteMapper tfUserWriteMapper;
	
	public List<TFUser> getTFUserByCondition(AgentSearch agentSearch) {
		List<TFUser> tfuserList = null;
		if("id".equals(agentSearch.getType()) && StringUtils.isNotBlank(agentSearch.getSearchtxt())){
			tfuserList = tfUserReadMapper.getUserByIdStr(agentSearch.getSearchtxt());
		}else{
			tfuserList = tfUserReadMapper.getUserByConditionByPage(agentSearch);
		}
		return tfuserList;
	}

	public void deleteTFUser(int id) {
		tfUserWriteMapper.delete(id);
		
	}

}
