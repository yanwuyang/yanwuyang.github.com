package com.qqyx.file.manage.biz.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qqyx.file.manage.biz.AttachmentService;
import com.qqyx.file.manage.biz.FileManageService;
import com.qqyx.file.manage.biz.TranslationFileService;
import com.qqyx.file.manage.model.mysql.Attachment;
import com.qqyx.file.manage.model.mysql.TranslationFile;

@Service("fileManageService")
@Transactional
@SuppressWarnings("all")
public class FileManageServiceImpl implements FileManageService {

	@Autowired
	private  AttachmentService attachmentService;
	
	@Autowired
	private  TranslationFileService fileService;
	
	
	
	@Override
	public void save(Attachment attachment, TranslationFile file) {
		attachmentService.saveOrUpdate(attachment);
		file.setAttachId(attachment.getId());
		fileService.saveOrUpdate(file);
	}

}
