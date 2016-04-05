package com.qqyx.file.manage.constant;

/**
 * 错误码全局
 * @author Jacky
 * 2015年7月2日
 */
public enum CodeConstantEnum {
	
	PARAM_ERROR("1001","参数错误"),
	FILE_TYPE_NOT_SUPP("1002","文件类型不支持"),
	UPLOAD_FAILED("1003","上传文件失败"),
	OBJECT_NOT_EXIST("1004","对象不存在");
	
	
	private String code;
	
	private String msg;
	
	private CodeConstantEnum (String code,String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
