package com.qqyx.file.manage.utils;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {
	private final static Base64 base64encoder = new Base64();
	private final static String encoding = "UTF-8";
	
	/**
	* 加密字符串
	*/
	public static String ebotongEncrypto(String str) {
	   String result = str;
	   if (str != null && str.length() > 0) {
	    try {
	     result = base64encoder.encodeToString(str.getBytes(encoding));
	    } catch (Exception e) {
	     e.printStackTrace();
	    }
	   }
	   return result;
	}

	/**
	* 解密字符串
	*/
	public static String ebotongDecrypto(String str) {
	   String result = str;
	   if (str != null && str.length() > 0) {
	    try {
	     byte[] encodeByte = base64encoder.decode(str);
	     result = new String(encodeByte, encoding);
	    } catch (Exception e) {
	     e.printStackTrace();
	    }
	   }
	   return result;
	}
	
	public static void main(String[] args) {
		String msg = "我是中国人";
		System.out.println(msg);
		String result = ebotongEncrypto(msg);
		System.out.println(result);
		String result2 = ebotongDecrypto(result);
		System.out.println(result2);
	}
	
	
}
