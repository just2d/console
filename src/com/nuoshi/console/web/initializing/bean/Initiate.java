package com.nuoshi.console.web.initializing.bean;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import com.nuoshi.console.common.Resources;

/**
 * Created by IntelliJ IDEA.
 * User: liang
 * Date: 2009-8-19
 * Time: 17:58:09
 * To change this template use File | Settings | File Templates.
 */

public class Initiate implements InitializingBean , ServletContextAware{
	public Timer priceTimer;
	public ServletContext context;
	protected Logger log = Logger.getLogger(this.getClass());


	@Override
	public void afterPropertiesSet() throws Exception {
		String name = null;
		try {
            // set jmagick property, not using systemclassloader
            System.setProperty("jmagick.systemclassloader","no");
			InetAddress hostIP = InetAddress.getLocalHost();
			name = hostIP.getHostName();
			this.context.setAttribute("hostName", name);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
        log.info("Init Context");
		this.context.setAttribute("urlMain", Resources.getString("sys.url.main"));
        this.context.setAttribute("headPrefix", "http://"+Resources.getString("sys.domain.head"));
        this.context.setAttribute("taofangUrlMain", Resources.getString("taofang.sys.url.main"));
        this.context.setAttribute("imgUrl58Prefix", Resources.getString("sys.img.58.other.prefix"));
        this.context.setAttribute("adminpicPrefix", "http://"+Resources.getString("sys.domain.adminpic"));
        this.context.setAttribute("urlInfo", "http://"+Resources.getString("sys.domain.info"));
        this.context.setAttribute("sysDomain", Resources.getString("sys.domain.main"));
        this.context.setAttribute("contextPath", Resources.getString("sys.domain.console"));
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.context = servletContext;
	}
}
