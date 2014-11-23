package com.nuoshi.console.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoshi.console.domain.stat.Feedback;
import com.nuoshi.console.persistence.write.stat.StatWriteMapper;

@Service
public class UserFeedbackService {
	@Resource
	private StatWriteMapper statWriteMapper;
	
	public int addUserFeedback(Feedback feedback) {
		feedback.setCityName(LocaleService.getName(feedback.getCityId()));
		return statWriteMapper.addUserFeedback(feedback);
	}
}
