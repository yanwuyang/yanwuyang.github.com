package com.qqyx.file.manage.model.mysql;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



/**
 * 字典
 * @author yanwuyang
 * 2015年7月6日
 */
@Entity
@Table(name="f_sys_dict")
@SuppressWarnings("serial")
public class SysDict implements Serializable {
	
	private String id;
	
	private String dictType;
	
	private String dictKey;
	
	private String dictValue;
	
	private int status;

	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	@Column(name="id", nullable=false, length=32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="dictType", nullable=false, length=10)
	public String getDictType() {
		return dictType;
	}

	public void setDictType(String type) {
		this.dictType = type;
	}

	@Column(name="dictKey", nullable=false, length=32)
	public String getDictKey() {
		return dictKey;
	}

	public void setDictKey(String key) {
		this.dictKey = key;
	}

	@Column(name="dictValue", nullable=false, length=32)
	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String value) {
		this.dictValue = value;
	}

	@Column(name="status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
	
	

}
