package com.qqyx.file.manage.model.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;



@Entity
@Table(name="f_attachment")
@SuppressWarnings("serial")
public class Attachment extends BaseModel{
	
	private String fileName;
	private String saveName;
	private long size;
	private String fileType;
	
	@Column(name = "fileName",length = 255)
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Column(name = "saveName",length = 100)
	public String getSaveName() {
		return saveName;
	}
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}
	@Column(name = "size")
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	@Column(name = "fileType",length=10)
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	
}
