package com.qqyx.file.manage.biz;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.qqyx.file.manage.model.mysql.TranslationFile;
import com.qqyx.file.manage.utils.Page;


public interface TranslationFileService  extends BaseService<TranslationFile> {
	

	public List getFiles(HashMap params);
	
	public void delete(TranslationFile translationFile);
	
	public Page querys(Map<String, Object> paramMap,LinkedHashMap<String, String> orderby);
}
