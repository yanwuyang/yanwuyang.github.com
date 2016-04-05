package com.qqyx.file.manage.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 获取上下文路径
 * 获取登陆用户名称
 * @author andy.li
 *
 */
public class NspApplicationContextInterceptor extends HandlerInterceptorAdapter implements LogEnabled {

	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		Map<String, Object> ctxMap = new HashMap<String, Object>();
		if (modelAndView != null) {
			String viewName = modelAndView.getViewName();
			log.info("viewName : "+viewName);
			/*获取mvc的上下文路径*/
			//上下文加 web.xml 的spring 配置路径
			String envRoot= request.getSession().getServletContext().getContextPath();//PagerFilter.getRootPath()+ "/" +SysConfigUtil.get("framework.springContext");  
			ctxMap.put("envRoot",envRoot);
			modelAndView.addObject("_project", ctxMap);
		}
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
