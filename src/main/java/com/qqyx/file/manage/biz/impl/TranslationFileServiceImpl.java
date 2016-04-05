package com.qqyx.file.manage.biz.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qqyx.file.manage.biz.TranslationFileService;
import com.qqyx.file.manage.dao.TranslationFileDao;
import com.qqyx.file.manage.model.mysql.TranslationFile;
import com.qqyx.file.manage.utils.Page;

@Service("translationFileService")
@Transactional
@SuppressWarnings("all")
public class TranslationFileServiceImpl implements TranslationFileService {

	@Autowired
    private TranslationFileDao translationFileDao;
	
	@Override
	public void removeUnused(String id) {
		// TODO Auto-generated method stub
		
	}
	
	

	@Override
	public TranslationFile get(String id) {
		return translationFileDao.get(id);
	}

	@Override
	public void saveOrUpdate(TranslationFile model) {
		translationFileDao.saveOrUpdate(model);
	}

	@Override
	public List getFiles(HashMap params) {
		return translationFileDao.getFiles(params);
	}

	@Override
	public void delete(TranslationFile translationFile) {
		translationFileDao.delete(translationFile);
	}

	@Override
	public Page querys(Map<String, Object> paramMap,
			LinkedHashMap<String, String> orderby) {
		return translationFileDao.querys(paramMap, orderby);
	}


}
