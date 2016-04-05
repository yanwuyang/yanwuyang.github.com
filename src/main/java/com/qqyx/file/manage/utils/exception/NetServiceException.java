package com.qqyx.file.manage.utils.exception;

public class NetServiceException extends RuntimeException{
	
	public NetServiceException(){
	}
	
	public NetServiceException(String message, Throwable cause){
		super(message, cause);
	}
}
