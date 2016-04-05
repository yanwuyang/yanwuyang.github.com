package com.qqyx.file.manage.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	/**
	  * @param key
	  *            主键
	  * @param value
	  *            值
	  * @param maxAge
	  *            有效时间
	  * @param response
	  *            响应
	  * @return void
	  */
	 public static void writeCookie(String key, String value, int maxAge,
	   HttpServletResponse response) {
		  Cookie namecookie = new Cookie(key, value);
		  namecookie.setMaxAge(maxAge);
		  response.addCookie(namecookie);

	 }

	 /**
	  * @param key
	  *            主键
	  * @param value
	  *            值
	  * @param maxAge
	  *            有效时间
	  * @return void
	  */
	 public static void writeCookie(String key, String value,
	   HttpServletResponse response) {
		  Cookie namecookie = new Cookie(key, value);
		  namecookie.setMaxAge(60 * 60 * 24 * 365);
		  response.addCookie(namecookie);
	 }

	 /**
	  * @param request
	  *            请求
	  * @param key
	  *            主键
	  * @return String 读取key 对应的值
	  */
	 public static String readCookie(String key, HttpServletRequest request) {
		  String value = null;
		  Cookie[] cookies = request.getCookies();
		  if (cookies != null) {
	
		   for (int i = 0; i < cookies.length; i++) {
		    Cookie c = cookies[i];
		    if (c.getName().equalsIgnoreCase(key)) {
		     value = c.getValue();
		     return value;
		    }
		   }
		  }
		  return value;
	 }
}
