package com.qqyx.file.manage.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qqyx.file.manage.biz.AttachmentService;
import com.qqyx.file.manage.dao.AttachmentDao;
import com.qqyx.file.manage.model.mysql.Attachment;

@Service("attachmentService")
@Transactional
@SuppressWarnings("all")
public class AttachmentServiceImpl implements AttachmentService {

	@Autowired
    private AttachmentDao attachmentDao;
	
	@Override
	public void removeUnused(String id) {
	}

	@Override
	public Attachment get(String id) {
		return attachmentDao.get(id);
	}

	@Override
	public void saveOrUpdate(Attachment model) {
		attachmentDao.saveOrUpdate(model);
	}

	@Override
	public void delete(Attachment attachment) {
		attachmentDao.delete(attachment);
	}

}
