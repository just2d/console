package com.nuoshi.console.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.nuoshi.console.common.Globals;
import com.nuoshi.console.common.Helper;
import com.nuoshi.console.common.NetClient;
import com.nuoshi.console.common.Resources;
import com.nuoshi.console.domain.bbs.ForumBlackUser;
import com.nuoshi.console.domain.user.TFUser;
import com.nuoshi.console.persistence.read.forum.BlackUserReadMapper;
import com.nuoshi.console.persistence.write.forum.BlackUserWriteMapper;

@Service
public class ForumBlackUserService {
	protected static Logger log = Logger.getLogger(ForumBlackUserService.class);
	@Resource
	BlackUserReadMapper blackUserReadMapper;
	@Resource
	BlackUserWriteMapper blackUserWriteMapper;
//	@Resource
//	BbsThreadWriteMapper bbsThreadWriteMapper;
	@Resource
	ForumService forumService;
	@Resource
	private TaofangUserService taofangUserService;
	@Resource
	private MessageService messageService;

	/**
	 * 获得黑名单列表.
	 * @param paramMap
	 * @return
	 */
	public List<ForumBlackUser> getUserListByPage(Map<String, String> paramMap) {
		return blackUserReadMapper.getUserListByPage(paramMap);
	}

	public int updateStatus(Integer status, Integer userId, String userName) {
		int result = blackUserWriteMapper.updateStatus(status, userId);
		if (result == 1) {
			String requestUri = "/api/bbs_blackUser";
			if (status == 1) {
				forumService.updateStatusByAuthorId(userId, Globals.FROUM_STATUS_BLACK_USER, Globals.FROUM_STATUS_NORMAL);
				requestUri += "/add?userId=" + userId ;
			} else {
				forumService.updateStatusByAuthorId(userId, Globals.FROUM_STATUS_NORMAL, Globals.FROUM_STATUS_BLACK_USER);
				requestUri += "/remove?userId=" + userId;
			}
			try {
				NetClient.getHttpResponse(Helper.getConfig("sys.url.bbs") + requestUri);
			} catch (Exception e) {
				log.info(status == 1?"添加":"删除"+"黑名单用户时，更新论坛缓存失败。");
			}
		}
		return result;
	}

	/**
	 * 通过用户名查询黑名单列表
	 * @param userName
	 * @return
	 * @author wangjh
	 * Oct 7, 2012 10:04:41 AM
	 */
	public List<ForumBlackUser> getUserByUserName(String userName) {
		return blackUserReadMapper.getUserByUserName(userName);
	}

	
	/**
	 * 通过id查询黑名单
	 * @param userId
	 * @return
	 * @author wangjh
	 * Oct 7, 2012 10:05:10 AM
	 */
	public List<ForumBlackUser> getUserByUserId(Integer userId) {
		return blackUserReadMapper.getUserByUserId(userId);
	}
	
	/**
	 * 添加黑名单
	 * @param blackUser
	 * @return
	 * @author wangjh
	 * Oct 7, 2012 10:05:33 AM
	 */
	public int insertBlackUser(ForumBlackUser blackUser) {
		int result = blackUserWriteMapper.addBlackUser(blackUser);
		if (result == 1) {
			forumService.updateStatusByAuthorId(blackUser.getUserId(), Globals.FROUM_STATUS_BLACK_USER, Globals.FROUM_STATUS_NORMAL);
			String urlStr = Helper.getConfig("sys.url.bbs");
			try {
				NetClient.getHttpResponse(urlStr + "/api/bbs_blackUser/add?userId=" + blackUser.getUserId());
			} catch (Exception e) {
				log.error(e);
				return 0;
			}
		}
		return result;
	}
	
	
	/**
	 * 通过用户名或者id查询黑名单中是否存在
	 * @param userInfo
	 * @param type  1：为用户名查询  2：为id查询
	 * @param model
	 * @return
	 * @author wangjh
	 * Oct 7, 2012 10:07:27 AM
	 * @throws Exception 
	 */
	public boolean isBlackUserExist(String userInfo, Integer type) throws Exception {
		List<ForumBlackUser> userList=null;
		try {
			if (type.intValue() == 1) {
				userList = this.getUserByUserName(userInfo);
			}
			if (type.intValue() == 2) {
				String str = StringUtils.trimToEmpty(userInfo);
				if (StringUtils.isNotBlank(str)) {
					userList = this.getUserByUserId(Integer.valueOf(str));
				}
			}
		} catch (Exception e) {
			log.error("判断用户是否存在黑名单中时出错！通过"+(type.intValue()==1?"用户名查询。":"ID查询。"));
			throw new Exception("判断用户是否存在黑名单中时出错！");
		}
		if (CollectionUtils.isNotEmpty(userList)) {
		  return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 添加黑名单
	 * @param userInfo
	 * @param role  
	 * @param type 1：为用户名查询  2：为id查询
	 * @param model
	 * @return
	 * @author wangjh
	 * Oct 7, 2012 10:19:50 AM
	 * @throws Exception 
	 */
	public boolean addStatus(String userInfo, Integer role, Integer type) throws Exception {
		TFUser user = null;
		try {
			if (type.intValue() == 1) {
				user = taofangUserService.getUserByUserName(StringUtils.trimToEmpty(userInfo));
			}
			if (type.intValue() == 2) {
				String str = StringUtils.trimToEmpty(userInfo);
				if (StringUtils.isNotBlank(str)) {
					user = taofangUserService.getUserByUserId(Integer.valueOf(str));
				}
			}
		} catch (Exception e) {
			log.error("添加黑名单时，查询用户信息出错！");
			throw new Exception("添加黑名单时，查询用户信息出错！");
		}
		if(user==null){
			return false;
		}
		ForumBlackUser blackUser = new ForumBlackUser();
		blackUser.setCts(new Date());
		blackUser.setStatus(1);
		blackUser.setUserName(user.getUserName());
		if (user != null) {
			blackUser.setUserId(user.getId());
		}
		blackUser.setRole(user.getRole());
		int result = this.insertBlackUser(blackUser);
		if (result > 0 && user != null) {
			try {
				messageService.sendMessageSys(Resources.getString("bbs_blackUser_msg_title"),
						Resources.getString("bbs_blackUser_msg_content"), user);
			} catch (Exception e) {
				log.error("添加黑名单成功发送站内信失败！");
//				throw new Exception("添加黑名单成功发送站内信失败！");
			}
			return true;
		}
		return false;
	}
}
