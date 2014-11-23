package com.nuoshi.console.service;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.nuoshi.console.common.Resources;
import com.nuoshi.console.common.util.Escape;
import com.nuoshi.console.common.util.Utilities;
import com.nuoshi.console.dao.MessageDao;
import com.nuoshi.console.dao.TFUserDao;
import com.nuoshi.console.domain.message.Message;
import com.nuoshi.console.domain.user.TFUser;

@Service
public class MessageService extends BaseService {
	@Resource
	private MessageDao messageDao;
	@Resource
	private TFUserDao TFUserDao;
	
	public boolean sendRentViolateMessage(TFUser receiver, int houseid, String reason, String viewUrl) {
        String editurl = Resources.getString("taofang.sys.url.main") + "/rent/edit/"+houseid;
        return sendMessageSys("房源违规通知【"+houseid+"】", Resources.getString("notice.rent.bad", new String[]{"" + houseid, reason, viewUrl, editurl}), receiver );
    }
	public boolean sendResaleViolateMessage(TFUser receiver, int houseid, String reason, String viewUrl) {
        String editurl = Resources.getString("taofang.sys.url.main") + "/resale/edit/"+houseid;
        return sendMessageSys("房源违规通知【"+houseid+"】", Resources.getString("notice.resale.bad", new String[]{""+houseid, reason, viewUrl, editurl}), receiver );
    }
	
	public boolean sendVcrDelMessage(TFUser receiver, int houseid, String viewUrl) {
        return sendMessageSys("房源视频违规删除通知【"+houseid+"】", Resources.getString("vcr.audit.del", new String[]{""+houseid, viewUrl}), receiver );
	}
	public boolean sendReAuditBackMessage(TFUser receiver, int houseid, String viewUrl) {
		return sendMessageSys("违规房源重置为通过房源通知【"+houseid+"】", Resources.getString("house.reAudit.back", new String[]{""+houseid,viewUrl}), receiver );
	}
	
	public boolean sendMessageSys(String title, String content, TFUser receiver) {
        TFUser sender = TFUserDao.selectById(Integer.parseInt(Resources.getString("taofang.admin.account")));
        Message msg = MessageService.createMessage(title, content, sender, receiver, 0,  Message.TYPE_SYS);
        msg.setType(Message.TYPE_SYS);
        messageDao.insertMessage(msg);
        msg.setMessageid(msg.getId());
        if (msg != null) {
            messageDao.insertMessageIntoInbox(msg);
            messageDao.incrementUserStatsInfo(receiver.getId());
        }
        return true;
    }
	
	public static Message createMessage(String title, String content, TFUser sender, TFUser receiver, int parent, short type ) {
       Message msg = new Message();
       msg.setSender(sender.getId());
       msg.setSendernick(sender.getNickName());
       msg.setReceiver(receiver.getId());
       msg.setReceivernick(receiver.getNickName());
       msg.setTitle(Escape.stringToHTMLString(title).trim());
       msg.setContent(content.trim());
       msg.setCts(Utilities.getCurrentTimestamp());
       msg.setReadflag((byte)0);
       msg.setParent(parent);
       msg.setType(type);
       return msg;
   }
	
	public boolean sendSysMessage(String title, String content, String  agentIds) {
        TFUser sender = TFUserDao.selectById(Integer.parseInt(Resources.getString("taofang.admin.account")));
      
        if(agentIds != null && agentIds.trim().length() > 0){
        	String[] agentIdAtr = agentIds.split("[^0-9]+");
        	if(agentIdAtr != null && agentIdAtr.length > 0){
        		for(String agentId: agentIdAtr){
        			if(StringUtils.isNotBlank(agentId)){
	        			TFUser receiver = new TFUser();
	        			receiver.setId(Integer.valueOf(agentId));
	        			receiver.setNickName("");
	        			
	        			Message msg = MessageService.createMessage(title, content, sender, receiver, 0,  Message.TYPE_SYS);
	    		        msg.setType(Message.TYPE_SYS);
	    		        messageDao.insertMessage(msg);
	    		        msg.setMessageid(msg.getId());
	    		        if (msg != null) {
	    		            messageDao.insertMessageIntoInbox(msg);
	    		            messageDao.incrementUserStatsInfo(receiver.getId());
	    		        }
    		        }
        		}
        	}
        }
       
        return true;
    }
	

}
