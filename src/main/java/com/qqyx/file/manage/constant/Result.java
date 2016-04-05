package com.qqyx.file.manage.constant;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Result<T> implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	private boolean success = false;
	
	private String errorCode;
	
	private String errorMsg;
	
	private Map<String,Object> modelMap = new HashMap<String,Object>();
	
	private T  value;
	
	public Result() {}
	
	public Result(boolean success) {this.success = success;}
	
	public Result(String errorCode) {
		this.success = false;
		this.errorCode = errorCode;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public static <E> Result<E> getDefaultResult() {
		return new Result<E>();
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}


	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Map<String, Object> getModelMap() {
		return modelMap;
	}

	public void setModelMap(Map<String, Object> modelMap) {
		this.modelMap = modelMap;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Result [success=" + success + ", errorCode=" + errorCode
				+ ", modelMap=" + modelMap + ", value=" + value + "]";
	}
	
}
