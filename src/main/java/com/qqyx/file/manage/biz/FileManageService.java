package com.qqyx.file.manage.biz;

import com.qqyx.file.manage.model.mysql.Attachment;
import com.qqyx.file.manage.model.mysql.TranslationFile;

public interface FileManageService{
	
	public void save(Attachment attachment,TranslationFile file);
	

}
