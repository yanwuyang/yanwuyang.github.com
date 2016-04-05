package com.qqyx.file.manage.utils;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.qqyx.file.manage.common.LogEnabled;
import com.qqyx.file.manage.constant.BizConstant;
import com.qqyx.file.manage.model.mysql.User;




/**
 * 用户操作工具类
 * @author liujinliang
 *
 */
@SuppressWarnings("all")
public class UserUtils implements LogEnabled{
	
	/**
	 * 判断是否为ajax操作
	 * @param request
	 * @return
	 */
	public static boolean isAjaxRequest(HttpServletRequest request){
		return request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest");
	}
	
	/**
	 * 获取user
	 * @param request
	 * @return
	 */
	public static User getUser(HttpServletRequest  request){
		HttpSession httpSession = request.getSession();
		Object obj = httpSession.getAttribute(BizConstant.SESSION_KEY);
		if(null!=obj && obj instanceof SessionUser){
			SessionUser  sessionUser = (SessionUser)obj;
			return sessionUser.getUser(); 
		}
		return null;
	}
	/**
	 * 设置user
	 * @param request
	 * @return
	 */
	public static void setUser(HttpServletRequest  request,User  user){
		HttpSession httpSession = request.getSession();
		SessionUser  sessionUser = new SessionUser();
		String ip = request.getLocalAddr();
		sessionUser.setUser(user);
		sessionUser.setIp(ip);
		httpSession.setAttribute(BizConstant.SESSION_KEY, sessionUser);
	}
	/**
	 * 获取SessionUser
	 * @param request
	 * @return
	 */
	public static SessionUser getSessonUser(HttpServletRequest  request){
		HttpSession httpSession = request.getSession();
		Object obj = httpSession.getAttribute(BizConstant.SESSION_KEY);
		if(null!=obj){
			SessionUser  sessionUser = (SessionUser)obj;
			return sessionUser; 
		}
		return null;
	}
	
	
}
