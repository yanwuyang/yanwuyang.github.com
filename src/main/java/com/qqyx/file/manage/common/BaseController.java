package com.qqyx.file.manage.common;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.qqyx.file.manage.common.asserts.AssertObject;


/**
 * 
 * @author liujl
 *
 */
public  abstract class BaseController implements LogEnabled{
	
	
    @Autowired
    public  HttpServletRequest request;
	@ExceptionHandler(Exception.class)
	public void exceptionHandler(Exception e,HttpServletResponse response){
		log.error(ExceptionUtils.getFullStackTrace(e));
		
		try {
			Msg msg = new Msg();
			msg.setMsg("系统出现错误了！");
			response.getWriter().write(msg.toJSONObject().toString());
		} catch (Exception ex) {
			e.printStackTrace();
		}
	}
	


	
	private JSONObject  jsonObject;

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	/**
	 * 附件实体类
	 */
	private List<FileAttach> fileAttachs = null;


	public List<FileAttach> getfiles()
	{
		return this.fileAttachs;
	}
	
	public List<FileAttach> setfiles(List<FileAttach> fileAttachs)
	{
		return this.fileAttachs=fileAttachs;
	}
	
	/**
	 * 跳转页面
	 */
	protected ModelAndView toView(String path, Map<String, Object> model) {
		ModelAndView result = new ModelAndView(path, model);
		result.addAllObjects(model);
		return result;
	}

	/**
	 * 跳转页面
	 */
	protected String toView(String path) {
		return path;
	}
	/**
	 * 重定位
	 * @param path
	 * @return
	 */
	protected String redirect(String path) {
		return "redirect:" + path;
	}
	
	/**
	 *  跳转
	 * @param path
	 * @return
	 */
	protected String forward(String path) {
		return "forward:" + path;
	}
	
	/**
	 * 获取访问地址
	 * @param key
	 * @return
	 */
	protected  String getUrl(String key){
		if(UrlMapping.loadUrlMap!=null){
			if(UrlMapping.loadUrlMap.containsKey(key)){
				return UrlMapping.loadUrlMap.get(key);
			}
		}
		return "";
	}
	
	/**
     * 处理参数-以便获取非空jsonObject
     */
	protected void buildJsonObject() {
        if(jsonObject==null) {
            this.setJsonObject(new JSONObject());
        }
    }
	
	public Msg doExpAssert(AssertObject assertObject){
			Msg msg = new Msg();
		try{
			assertObject.AssertMethod(msg);
		}catch (Exception e) {
			msg.setMsg("系统出错了!");
			log.error(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("系统出错了!", e);
			}
		}
		return 	msg;
	}
	
}
