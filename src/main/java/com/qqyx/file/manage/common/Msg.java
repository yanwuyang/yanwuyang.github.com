package com.qqyx.file.manage.common;

import java.util.Date;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.sun.jmx.snmp.Timestamp;



/**
 *
 *liujl 
 */
@SuppressWarnings("all")
public class Msg {

	/**
	 * 成功标志
	 */
	private boolean success = false;
	/**
	 * 提示信息
	 */
	private String msg;
	//code值ֵ
	private String code;
	/* 数据*/
	private Object data;

	public Msg() {

	}

	/**
	 * 构造方法。标识成功信息
	 * 
	 * @param msg
	 */
	public Msg(String msg) {
		this(true, msg);
	}

	/**
	 * 构造方法.
	 * 
	 * @param success
	 * @param msg
	 */
	public Msg(boolean success, String msg) {
		this(success, msg, null);
	}

	/**
	 * 构造方法.
	 * 
	 * @param msg
	 *            消息
	 * @param data
	 *            数据
	 */
	public Msg(String msg, Object data) {
		this(true, msg, null);
	}

	/**
	 * 构造方法
	 * 
	 * @param success
	 * @param msg
	 * @param data
	 */
	public Msg(boolean success, String msg, Object data) {
		this.msg = msg;
		this.success = success;
		if(null==data || "null".equals(data)){
			this.data = "";
		}else
		this.data = data;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public Msg setData(Object data) {
		this.data = data;
		return this;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public Msg setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	/**
	 * @param success
	 *            the success to set
	 */
	public Msg setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	
	
	public JsonConfig getJsonConfig(String dateFormat) {
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(dateFormat));
		config.registerJsonValueProcessor(Timestamp.class, new JsonDateValueProcessor(dateFormat));
		return config;
	}
	

	/**
	 * 将消息转为JsonObject对象。
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public JSONObject toJSONObject() {
		 String format = "yyyy-MM-dd HH:mm:ss";
		JSONObject jsonObject = new JSONObject();
		String newMsg = this.msg==null?"":this.msg;
		this.setMsg(newMsg);
		jsonObject = jsonObject.fromObject(this,getJsonConfig(format));
		return jsonObject;
	}
	/**
	 * 将消息转为JsonObject对象。
	 * 
	 * @return
	 */
	 @SuppressWarnings("static-access")
	 public JSONObject toJSONObject(String dataFormat) {
		 JSONObject jsonObject = new JSONObject();
		 String newMsg = this.msg == null ? "" : this.msg;
		 this.setMsg(newMsg);
		 jsonObject = jsonObject.fromObject(this,getJsonConfig(dataFormat));
		 return jsonObject;
	 }
}
