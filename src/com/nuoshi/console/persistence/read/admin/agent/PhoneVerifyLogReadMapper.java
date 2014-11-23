package com.nuoshi.console.persistence.read.admin.agent;

import java.util.HashMap;
import java.util.List;

import com.nuoshi.console.domain.agent.PhoneVerifyLog;

public interface PhoneVerifyLogReadMapper {

	public List<PhoneVerifyLog> getVerifyPhoneHistoryListByPage(HashMap<String, Object> params);
}