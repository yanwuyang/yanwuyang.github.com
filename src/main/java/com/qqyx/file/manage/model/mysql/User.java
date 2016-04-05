package com.qqyx.file.manage.model.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="f_user")
@SuppressWarnings("serial")
public class User extends BaseModel{
	
	/*用户名*/
	private String userName;
	/*密码*/
	private String password;
	/*姓名*/
	private String realName;
	/* 状态 init：初步关注    pass：完善信息  delete:已删除     */
	private String status;
	//用户类型
	private String type;//客户customer  译员 interpreter  系统用户  system
    //手机号
	private String tel;
	//地址
	private String address;
	
	/*用户信息简介*/
	private String descs;
	
	/* 邮箱 */
	private String email;
	
	private String country;
	
	
	@Column(name = "userName",length = 64)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "password",length = 32)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name = "realName",length = 64)
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	@Column(name = "email",length = 30)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name = "descs",length = 64)
	public String getDescs() {
		return descs;
	}
	public void setDescs(String descs) {
		this.descs = descs;
	}
	@Column(name = "status",length = 10)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "tel",length = 32)
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	@Column(name = "address",length = 64)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "type",length = 15)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(name = "country",length = 20)
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
	
}
