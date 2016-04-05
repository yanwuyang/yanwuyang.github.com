package com.qqyx.file.manage.model.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="f_translationfile")
@SuppressWarnings("serial")
public class TranslationFile extends BaseModel {
	
	private String userId;//上传人员Id
	
	private String attachId;//文件ID
	
	private String targetAttachId;//翻译后的文件ID
	
	private String sourceLanguage;//源语言
	
	private String targetLanguage;//目标语言
	
	private Integer emergencyDegree;//紧急程度 10 20 30 40 50 60 70 80 90 100
	
	private String status;//状态  已上传  已保存  已提交  待分配 已分配  已完成
	
	private Integer progress;//进度
	
	private String translationId;//翻译员
	
	private String operatorId;//操作员ID

	private String descs;//描述

	@Column(name = "userId",length = 50)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "attachId",length = 50)
	public String getAttachId() {
		return attachId;
	}

	@Column(name = "targetAttachId",length = 50)
	public String getTargetAttachId() {
		return targetAttachId;
	}

	public void setTargetAttachId(String targetAttachId) {
		this.targetAttachId = targetAttachId;
	}

	@Column(name = "status",length = 10)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "descs",length=255)
	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

	@Column(name = "sourceLanguage",length = 50)
	public String getSourceLanguage() {
		return sourceLanguage;
	}

	public void setSourceLanguage(String sourceLanguage) {
		this.sourceLanguage = sourceLanguage;
	}

	@Column(name = "targetLanguage",length = 50)
	public String getTargetLanguage() {
		return targetLanguage;
	}

	public void setTargetLanguage(String targetLanguage) {
		this.targetLanguage = targetLanguage;
	}

	@Column(name = "emergencyDegree")
	public Integer getEmergencyDegree() {
		return emergencyDegree;
	}

	public void setEmergencyDegree(Integer emergencyDegree) {
		this.emergencyDegree = emergencyDegree;
	}


	@Column(name = "progress")
	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	@Column(name = "translationId",length=50)
	public String getTranslationId() {
		return translationId;
	}

	public void setTranslationId(String translationId) {
		this.translationId = translationId;
	}

	@Column(name = "operatorId",length=50)
	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}


}
