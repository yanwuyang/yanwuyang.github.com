package com.qqyx.file.manage.utils;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * spring上下文工具类
 * @author andy.li
 */
@Component
public class SpringConext implements ApplicationContextAware,ServletContextAware {

	private static ApplicationContext context;
	
	private static ServletContext servletContext;


	public void setApplicationContext(ApplicationContext acx) {
		context = acx;
	}

	/**
	 * 获取本地spring环境
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return context;
	}
	
	public static Object getBean(String name){
		return context.getBean(name);
	}
	
	
	/**
	 * 获取跨spring环境
	 * @param contextName servlet名称
	 * @return
	 */
	public  static  ApplicationContext getApplicationContext(String contextName){
		return (ApplicationContext) servletContext.getAttribute(DispatcherServlet.SERVLET_CONTEXT_PREFIX+contextName);
	}
	

	public ServletContext getServletContext() {
		return servletContext;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext=servletContext;
	}
	

}
