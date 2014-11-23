package com.nuoshi.console.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import com.nuoshi.console.common.util.MessageResolver;
import com.nuoshi.console.common.util.SmcConst;
import com.nuoshi.console.web.session.SessionUser;

/**
 * 
 * @author Administrator 权限拦截器
 */
public class AccessInterceptor extends HandlerInterceptorAdapter {
	private static PathMatcher pathMatcher = new AntPathMatcher();
	private String[] excludeUrls;// 不需要过虑的url
	private Boolean isControl;// 是否使用权限控制

	public Boolean getIsControl() {
		return isControl;
	}

	public void setIsControl(Boolean isControl) {
		SessionUser.setIsControl(isControl);
		this.isControl = isControl;
	}

	public String[] getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(String[] excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String uri = getURI(request);
		// 不在验证的范围内
		if (exclude(uri)) {
			return true;
		}
		HttpSession session = request.getSession();
		SessionUser sessionUser = (SessionUser) session
				.getAttribute(SmcConst.SESSION_USER);
		if (sessionUser == null) {// 用户没有登录或者登录超时
			request.setAttribute(SmcConst.MESSAGE,"没有登录或登录超时");
			request.getRequestDispatcher("/loginTimeOut").forward(request,response);
			return false;
		}
		if (!sessionUser.isPass(uri)) {
			request.setAttribute(SmcConst.MESSAGE,
					MessageResolver.getMessage(request, "message.nopermission"));
			request.getRequestDispatcher("/nopermission").forward(request,
					response);
			return false;
		}
		return true;
	}

	/**
	 * 获得访问路径，去除访问路径的后缀
	 * 
	 * @param request
	 * 
	 */
	private static String getURI(HttpServletRequest request)
			throws IllegalStateException {
		UrlPathHelper helper = new UrlPathHelper();
		String uri = helper.getRequestUri(request);

		// 处理后缀，去掉后缀. /user/list.do ===> /user/list
		int extensionIndex = uri.lastIndexOf(".");
		if (extensionIndex > 0) {
			uri = uri.substring(0, extensionIndex);
		}
		return uri;
	}

	private boolean exclude(String uri) {
		// 去除"/" 访问首页
		if ("/".equals(uri) || "/loginTimeOut".equals(uri)) {
			return true;
		}

		if (excludeUrls != null) {
			for (String exc : excludeUrls) {
				if (pathMatcher.match(exc, uri)) {
					return true;
				}
			}
		}
		return false;
	}
}