package com.qqyx.file.manage.contorller;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qqyx.file.manage.biz.UserService;
import com.qqyx.file.manage.common.BaseController;
import com.qqyx.file.manage.common.Msg;
import com.qqyx.file.manage.common.asserts.AssertObject;
import com.qqyx.file.manage.constant.BizConstant;
import com.qqyx.file.manage.model.mysql.User;
import com.qqyx.file.manage.utils.JSONTools;
import com.qqyx.file.manage.utils.MD5;
import com.qqyx.file.manage.utils.Page;
import com.qqyx.file.manage.utils.PageContext;
import com.qqyx.file.manage.utils.PageView;


@Controller
@Scope("prototype")
@RequestMapping("/user")
@SuppressWarnings("all")
public class UserController extends  BaseController{
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private  UserService userService;
	
	/**
	 * 注册
	 * @return
	 */
	@RequestMapping("register")
	public String register(ModelMap map){
		String userId = UUID.randomUUID().toString();
		map.put("id",userId);
		return "function/register_01";
	}
	
	/**
	 * 注册
	 * @return
	 */
	@RequestMapping("register_01")
	public String register_01(String userName,String password,ModelMap map){
		User user = new User();
		String userId= request.getParameter("id");
		user.setUserName(userName);
		user.setPassword(MD5.MD5Encode(password));
		user.setCreateDate(new Date());
		user.setStatus(BizConstant.USER_STATUS_INIT);
		request.getSession().setAttribute(userId, user);
		map.put("id",userId);
		return "function/register_02";
	}
	
	/**
	 * 注册
	 * @return
	 */
	@RequestMapping("register_02")
	public String register_02(String id,String type,ModelMap map){
		map.put("id", id);
		map.put("type", type);
		return "function/register_03";
	}
	
	@RequestMapping("register_03")
	public String register_03(HttpServletRequest request){
		String userId =request.getParameter("id");
		User user=(User)request.getSession().getAttribute(userId);
		user.setType(request.getParameter("type"));
		user.setStatus(BizConstant.USER_STATUS_ENABLE);
		user.setRealName(request.getParameter("realName"));
		user.setCountry(request.getParameter("country"));
		user.setEmail(request.getParameter("email"));
		user.setTel(request.getParameter("tel"));
		user.setAddress(request.getParameter("address"));
		user.setDescs(request.getParameter("descs"));
		userService.saveOrUpdate(user);
		/*if(BizConstant.USER_TYPE_CUSTOMER.equals(user.getType())){
			return this.toView("function/customer/tab");
		}
		return this.toView("function/interpreter/tab");*/
		return this.redirect("/login/toLogin");
	}
	
	/**
	 * 客户列表
	 * @return
	 */
	@RequestMapping("customer_list")
	public String customerList(ModelMap modelMap) {
		return this.toView("function/manager/user/customer_list");
	}
	
	/**
	 * 客户列表
	 * @return
	 */
	@RequestMapping("interpreter_list")
	public String interpreterList(ModelMap modelMap) {
		return this.toView("function/manager/user/interpreter_list");
	}
	
	/**
	 * 客户列表
	 * @return
	 */
	@RequestMapping("system_list")
	public String systemList(ModelMap modelMap) {
		return this.toView("function/manager/user/system_list");
	}
	
	
	
	/**
	 * 管理员登陆
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("login")
	@ResponseBody
 public Msg login(ModelMap modelMap, HttpServletRequest httpRequest, HttpServletResponse response){
		String me = httpRequest.getParameter("userName").toString();
		Msg msg = new Msg();
		if(httpRequest.getParameter("userName")!=null){
			String userName = httpRequest.getParameter("userName");
			String password = httpRequest.getParameter("password");
			
			if(userName.equals("admin")&&StringUtils.isNotEmpty(password)){
					HttpSession httpSession = httpRequest.getSession();
					httpSession.setAttribute("sessionInfo", userName);
				     modelMap.addAttribute("sessionInfo","admin");
					logger.info("userName"+httpSession.getAttribute("sessionInfo"));
					msg.setSuccess(true);
					msg.setMsg("成功");
			}else{
				
				msg.setCode("101");
				msg.setMsg("登陆失败");
				
			}
	  }
		return msg;
	}
	/**
	 * 去用户新增页面
	 * @return
	 */
	@RequestMapping("addUserUI")
	public String addUserUI(ModelMap modelMap) {
		
		return this.toView("function/user/addUserUI");
	}
	
	/**
	 *userlist
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "userlist")
	@ResponseBody
	public Object userlist(ModelMap modelMap) {
		PageContext.setPagesize(JSONTools.getInt(getJsonObject(), "rows"));
		PageContext.setOffset(JSONTools.getInt(getJsonObject(), "page"));
		Page page = userService.querys(getJsonObject(),null);
		HashMap resultMap = new HashMap();
		resultMap.put("total", page.getTotal());
		resultMap.put("rows",page.getItems());
		return resultMap;
	}
	
	@RequestMapping(value = "getInterpreter")
	@ResponseBody
	public Object getInterpreter(){
		return userService.getUserByType(BizConstant.USER_TYPE_INTERPRETER);
	}
	
	/**
	 * 保存
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "save",method=RequestMethod.POST)
	@ResponseBody
	public Msg save(final HttpServletRequest request){
		return doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				User user = JSONTools.JSONToBean(getJsonObject(), User.class);
				//更新
				if(StringUtils.isNotBlank(user.getId())){
					User  model = userService.get(user.getId());
					
					
					msg.setSuccess(true);
					msg.setMsg("成功");
				//新增
				}else{
					if(null!=user){
						userService.saveOrUpdate(user);
						msg.setSuccess(true);
						msg.setMsg("成功");
					}
				}
				
			}
		});
	}
	
	/**
	 *userlist
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public Object list(ModelMap modelMap) {
		User user = new User();//userService.get("11");
		user.setCreateDate(new Date());
		Msg  msg =  new Msg();
		msg.setData(user);
		return msg;
	}
}
