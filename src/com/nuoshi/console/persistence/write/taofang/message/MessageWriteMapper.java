package com.nuoshi.console.persistence.write.taofang.message;

import org.apache.ibatis.annotations.Param;

import com.nuoshi.console.domain.message.Message;

/**
 *smc
 * <b>function:</b>站内消息
 * @author lizhenmin
 * @createDate 2011 Jul 20, 2011 2:11:57 PM
 * @email lizm@taofang.com
 * @version 1.0
 */
public interface MessageWriteMapper {
	public int  insertMessageIntoInbox(Message msg);
    public int insertMessage(Message msg);
    public int incrementUserStatsInfo(@Param("agentId")int agentId);
}
