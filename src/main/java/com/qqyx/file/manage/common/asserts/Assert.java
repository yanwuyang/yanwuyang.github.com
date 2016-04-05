package com.qqyx.file.manage.common.asserts;

import com.qqyx.file.manage.common.BaseException;


/**
 * 
 * @author liujl
 *
 */
public class Assert   {
	
	public static void isTrue(boolean exp,String message){
		if(!exp){
			throw new BaseException(message);
		}
	}
}
