package com.nuoshi.console.persistence.write.stat;

import com.nuoshi.console.domain.stat.Feedback;

public interface StatWriteMapper {
	public int addUserFeedback(Feedback feedback);
}
