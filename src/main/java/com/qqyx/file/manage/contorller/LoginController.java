package com.qqyx.file.manage.contorller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qqyx.file.manage.biz.UserService;
import com.qqyx.file.manage.common.BaseController;
import com.qqyx.file.manage.constant.BizConstant;
import com.qqyx.file.manage.model.mysql.User;
import com.qqyx.file.manage.utils.MD5;
import com.qqyx.file.manage.utils.SessionUser;
import com.qqyx.file.manage.utils.UserUtils;


@Controller
@Scope("prototype")
@RequestMapping("/login")
@SuppressWarnings("all")
public class LoginController extends BaseController {
	
	@Autowired
	private  UserService userService;
	
	@RequestMapping("toLogin")
	public String toLogin(){
		return "function/login";
	}
	
	@RequestMapping("login")
	public String login(String userName,String password,ModelMap map){
		User user=userService.getUserByName(userName, MD5.MD5Encode(password));
		if(user==null){
			map.put("msg", "用户名或密码错误");
			return "function/login";
		}
		if(BizConstant.USER_STATUS_INIT.equals(user.getStatus())){
			map.put("userId", user.getId());
			return "function/register_02";
		}
		UserUtils.setUser(request, user);
		String type = user.getType();
		if(BizConstant.USER_TYPE_CUSTOMER.equals(type)){
			return this.toView("function/customer/tab");
		}else if(BizConstant.USER_TYPE_INTERPRETER.equals(type)){
			return this.toView("function/interpreter/tab");
		}else{
			return redirect("/crtmanager/index");
		}
	}
	
	@RequestMapping("logout")
	public String logout(){
		UserUtils.setUser(request, null);
		return this.redirect("/login/toLogin");
	}
	

}
