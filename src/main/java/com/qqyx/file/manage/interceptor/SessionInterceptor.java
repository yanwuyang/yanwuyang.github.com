package com.qqyx.file.manage.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.qqyx.file.manage.common.LogEnabled;
import com.qqyx.file.manage.constant.BizConstant;
import com.qqyx.file.manage.model.mysql.User;
import com.qqyx.file.manage.utils.UserUtils;


@SuppressWarnings("all")
public class SessionInterceptor extends HandlerInterceptorAdapter implements  LogEnabled{
	
	/**
	 * 不需要拦截的url
	 */
	private List<String> nofilter_list = new ArrayList<String>();
	
	public void setNofilter_list(List<String> nofilterList) {
		nofilter_list = nofilterList;
	}
	public void init(){
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String envRoot= request.getSession().getServletContext().getContextPath();
		String uri  =  request.getRequestURI();
		//表示是否需要在登录之后重定向到该URL
		String redirect = request.getParameter("redirect");
		log.debug(envRoot);
		if(!"".equals(envRoot)){
			String ruri = uri.replaceAll(envRoot, "-");
			uri  = ruri.substring(ruri.indexOf("-")+1);
		}
		log.debug(uri);
		boolean  exists_flag =  nofilter_list.contains(uri);
		User user = UserUtils.getUser(request);
		
		if(!exists_flag && user==null){
			String referer = request.getHeader("referer");
			StringBuffer url = request.getRequestURL();
			String hostStr = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
			boolean isOurReferer = false;
			if (referer != null){
				referer.indexOf(hostStr);
				isOurReferer = true;
			}
		    //返回可以登录后重定向的登录界面
		    /*if (redirect != null){
		        response.sendRedirect(envRoot+"/login/toLoginWithRedirect?redirectURL=" + request.getRequestURL() + "?" +request.getQueryString());
		    }*/
		    //返回正常的登录界面
		   // else {
		        response.sendRedirect(envRoot+"/login/toLogin");
            //}
			
			return false;
		}
		return true;
	}
	
}
