package com.qqyx.file.manage.biz.impl;


/**
 * @author yanwuyang
 * @date 2015-7-6
 */


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qqyx.file.manage.biz.SysDictService;
import com.qqyx.file.manage.dao.SysDictDao;
import com.qqyx.file.manage.model.mysql.SysDict;
import com.qqyx.file.manage.utils.Page;


@Service("sysDictService")
@Transactional
@SuppressWarnings("all")
public class SysDictServiceImpl implements SysDictService {

	
	@Autowired  
	private SysDictDao sysDictDao;
	
	@Override
	public void removeUnused(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public SysDict get(String id) {
		return sysDictDao.get(id);
	}

	@Override
	public void saveOrUpdate(SysDict model) {
		sysDictDao.saveOrUpdate(model);
	}

	@Override
	public List getDictsByType(String type) {
		return sysDictDao.queryForList("from SysDict d where d.dictType=?", new String[]{type}, 0, Integer.MAX_VALUE, null);
	}

	@Override
	public Page querys(Map<String, Object> paramMap,
			LinkedHashMap<String, String> orderby) {
		return sysDictDao.querys(paramMap, orderby);
	}

	@Override
	public void delete(String[] ids) {
		sysDictDao.delete(ids);
	}

	
	
}
