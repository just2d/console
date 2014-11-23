package com.nuoshi.console.web.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.nuoshi.console.common.Globals;
import com.nuoshi.console.common.util.SmcConst;
import com.nuoshi.console.domain.user.Function;
import com.nuoshi.console.domain.user.User;
import com.nuoshi.console.service.AuditService;

/**
 *<b>www.taofang.com</b>
 * <b>function:</b>存储用户信息
 * @author lizhenmin
 * @createDate 2011 Jul 27, 2011 9:36:54 AM
 * @email lizm@taofang.com
 * @version 1.0
 */
public class SessionUser implements HttpSessionBindingListener{
	private static PathMatcher pathMatcher = new AntPathMatcher();
	private static Boolean isControl = true;
	private  Boolean isSuperAdmin = false; //是否是超级管理员
	private boolean manager;
	
	
	private AuditService auditService ;//= new AuditService();
	
	
	public AuditService getAuditService() {
		return auditService;
	}
	public void setAuditService(AuditService auditService) {
		this.auditService = auditService;
	}
	public  Boolean getIsSuperAdmin() {
		return isSuperAdmin;
	}
	public  void setIsSuperAdmin(Boolean isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}
	public static Boolean getIsControl() {
		return isControl;
	}
	public static void setIsControl(Boolean isControl) {
		SessionUser.isControl = isControl;
	}

	private User user;
	/**
	 * 权限URL数组
	 */
	private Map<String,String> rights = new HashMap<String,String>();
	private Map<String,String> urlRights = new HashMap<String,String>();//只对全路径 针对没有带* 的路径

	public void setRights(List<Function> funcList) {
		if (rights != null) {
			rights.clear();
		} else {
			rights = new HashMap<String,String>();
		}
		if (urlRights != null) {
			urlRights.clear();
		} else {
			urlRights = new HashMap<String,String>();
		}
		for (Function f : funcList) {
			 if(f==null){
				 continue;
			 }
			String url = f.getUrl();
			if (url != null && !url.trim().equals("")) {
				if(url.indexOf("*")>0){
					rights.put(url,url);
				}else{
					urlRights.put(url,url);
				}
			}
			String urls = f.getFuncs();
			if (urls != null && !urls.trim().equals("")) {
				String[] urlArr = urls.split("@");
				for (String s : urlArr) {
					if (s != null && !s.trim().equals("")) {
						if(s.indexOf("*")>0){
							rights.put(s,s);
						}else{
							urlRights.put(s,s);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 是否通过权限验证
	 * 
	 * @param url
	 *            待验证的URL
	 * @return
	 */
	public boolean isPass(String url) {
		if(!isControl||this.isSuperAdmin){
			return true;
		}
		if (url != null) {
			if(urlRights.get(url)!=null){
				return true;
			}
			Set<String> rightsUrl = rights.keySet();
			for (String s : rightsUrl) {
				if(pathMatcher.matchStart(s, url)){
					return true;
				}
			}
		}
		return false;
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void valueBound(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent arg0) {
		// TODO 清空用户的任务列表： ｛本方法实现  & AuditService.getTaskCountByAuditStep[注释掉清空任务列表] & LoginCotroller line 167 sessionUser.setAuditService(as)};
		HttpSession session = arg0.getSession();
		if(session!=null){
			SessionUser sessionUser = (SessionUser)session.getAttribute(SmcConst.SESSION_USER);
			if(sessionUser!=null){
				user = sessionUser.getUser();
			}
		}
		
		if(user!=null){
			int id = user.getId();
			System.out.println("test...... name="+user.getChnName()+",id="+id);
			List<Integer> list = new ArrayList<Integer>();
			list.add(id);
			System.out.println("when session destoryed ,empty task by use_id !  auditService="+auditService);
			
			auditService.emptyAuditTask(list, Globals.SESSION_DESTORY_EMPTY_TASK_NUM);
			auditService.emptyAgentAuditTask(list, Globals.SESSION_DESTORY_EMPTY_TASK_NUM);
		}
		
	}
	public boolean isManager() {
		return manager;
	}
	public void setManager(boolean manager) {
		this.manager = manager;
	}
	
}
