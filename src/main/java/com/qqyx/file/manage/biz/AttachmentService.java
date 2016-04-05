package com.qqyx.file.manage.biz;

import com.qqyx.file.manage.model.mysql.Attachment;


public interface AttachmentService  extends BaseService<Attachment> {
	
	public void delete(Attachment attachment);

}
