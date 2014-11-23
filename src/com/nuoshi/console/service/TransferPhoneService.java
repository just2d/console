package com.nuoshi.console.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuoshi.console.domain.agent.TransferPhone;
import com.nuoshi.console.persistence.read.taofang.agent.TransferPhoneReadMapper;
import com.nuoshi.console.persistence.write.taofang.agent.TransferPhoneWriteMapper;

@Service
public class TransferPhoneService extends BaseService {
	protected static Logger logger = Logger.getLogger(TransferPhoneService.class);
	@Autowired
	private TransferPhoneReadMapper transferPhoneReadMapper;
	
	@Autowired
	private TransferPhoneWriteMapper transferPhoneWriteMapper;
	
	public int getBoundDate(String phone) {
		return transferPhoneReadMapper.getBoundDate(phone);
	}
	
	public TransferPhone selectPhoneByPhone(String phone) {
		return transferPhoneReadMapper.selectPhoneByPhone(phone);
	}
	
	public boolean canBeUsed(String phone) {
		TransferPhone dbPhone = transferPhoneReadMapper.selectPhoneByPhone(phone);
		if(dbPhone != null && dbPhone.getStatus() == 0) {
			return true;
		}
		return false;
	}
	
	public int updatePhoneStatus(String phone, int agentId, int user400Id) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		int currentTime = Integer.parseInt(sdf.format(new Date()));
		return transferPhoneWriteMapper.updatePhoneStatus(phone, agentId,currentTime, user400Id);
	}
	
	public TransferPhone selPhoneByAgentId(int agentId) {
		return transferPhoneReadMapper.selPhoneByAgentId(agentId);
	}
	
	public int resetPhoneStatus(String phone) {
		return transferPhoneWriteMapper.resetPhoneStatus(phone);
	}
}
