package com.qqyx.file.manage.utils.exception;

public class BusinessException extends RuntimeException{
	
	/**
     * TODO
     */
    private static final long serialVersionUID = -989402318244901346L;

    public BusinessException(){
	}
	
	public BusinessException(String message, Throwable cause){
		super(message, cause);
	}
}
