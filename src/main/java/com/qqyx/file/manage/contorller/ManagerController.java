package com.qqyx.file.manage.contorller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qqyx.file.manage.biz.AttachmentService;
import com.qqyx.file.manage.common.BaseController;


@Controller
@Scope("prototype")
@RequestMapping("/crtmanager")
@SuppressWarnings("all")
public class ManagerController extends  BaseController{
	
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("index")
	public String index(ModelMap modelMap) {
		return this.toView(getUrl("manager.index"));
	}

}
