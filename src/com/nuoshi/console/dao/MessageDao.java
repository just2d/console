package com.nuoshi.console.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.nuoshi.console.domain.message.Message;
import com.nuoshi.console.persistence.write.taofang.message.MessageWriteMapper;

/**
 *<b>www.taofang.com</b>
 * <b>function:</b>
 * @author lizhenmin
 * @createDate 2011 Aug 17, 2011 2:42:56 PM
 * @email lizm@taofang.com
 * @version 1.0
 */
@Repository
public class MessageDao {
	@Resource
	private MessageWriteMapper messageWriteMapper;

    public int insertMessageIntoInbox(Message msg) {
        assert(msg != null);
        if (messageWriteMapper != null) {
            try {
                return messageWriteMapper.insertMessageIntoInbox(msg);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
    
    public int incrementUserStatsInfo(int agentId) {
    	 if (messageWriteMapper != null) {
             try {
                 return messageWriteMapper.incrementUserStatsInfo(agentId);
             }
             catch (Exception e) {
                 e.printStackTrace();
             }
         }
         return -1;
    }
    
    public int insertMessage(Message msg) {
        assert(msg != null);
        int res = -1;
        if (messageWriteMapper != null) {
            try {
                return messageWriteMapper.insertMessage(msg);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }
}
