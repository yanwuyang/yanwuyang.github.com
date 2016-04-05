package com.qqyx.file.manage.contorller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qqyx.file.manage.biz.SysDictService;
import com.qqyx.file.manage.common.BaseController;
import com.qqyx.file.manage.common.Msg;
import com.qqyx.file.manage.common.asserts.AssertObject;
import com.qqyx.file.manage.model.mysql.SysDict;
import com.qqyx.file.manage.utils.JSONTools;
import com.qqyx.file.manage.utils.Page;
import com.qqyx.file.manage.utils.PageContext;
import com.qqyx.file.manage.utils.PageView;

/**
 * 字典管理控制器
 * 
 * @author yanwuyang
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysDict")
@SuppressWarnings("all")
public class SysDictController extends BaseController {

	@Autowired
	private SysDictService sysDictService;

	/**
	 * 拼车管理
	 * 
	 * @return
	 */
	@RequestMapping("list")
	public String list() {
		return "function/manager/dict/list";
	}

	@RequestMapping("dictList")
	@ResponseBody
	public Object dictList() {
		PageContext.setPagesize(JSONTools.getInt(getJsonObject(), "rows"));
		PageContext.setOffset(JSONTools.getInt(getJsonObject(), "page"));
		Page page = sysDictService.querys(getJsonObject(), null);
		HashMap resultMap = new HashMap();
		resultMap.put("total", page.getTotal());
		resultMap.put("rows",page.getItems());
		return resultMap;
	}


	/**
	 * 保存
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Msg save(final HttpServletRequest request) {
		return doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				SysDict sysDict = JSONTools.JSONToBean(getJsonObject(),
						SysDict.class);
				sysDictService.saveOrUpdate(sysDict);
				msg.setSuccess(true);
				msg.setMsg("成功");
			}
		});
	}

	
	/**
	 *删除
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public Msg delete() {
		return doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				String str = getJsonObject().getString("id");
				String[] ids = str.split(",");
				sysDictService.delete(ids);
				msg.setSuccess(true);
				msg.setMsg("成功");
			}
		});
	}
	
	@RequestMapping(value = "edit")
	public String edit(String id,ModelMap map){
		SysDict dict = sysDictService.get(id);
		map.put("dict", dict);
		return this.toView("function/dict/addUI");
	}
}
