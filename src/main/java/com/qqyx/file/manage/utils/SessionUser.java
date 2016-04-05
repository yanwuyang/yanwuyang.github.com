package com.qqyx.file.manage.utils;


import java.io.Serializable;

import com.qqyx.file.manage.model.mysql.User;




/**
 * 会话user
 * @author liujinliang
 *
 */
public class SessionUser implements Serializable{
	private static final long serialVersionUID = -3407978945090028887L;
	/*local user*/
	private User user;
	/*需要字段以下添加*/
	
	/* 登陆的ip地址*/
	private String ip;
	
	
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
