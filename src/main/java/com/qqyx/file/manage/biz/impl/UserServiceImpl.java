package com.qqyx.file.manage.biz.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qqyx.file.manage.biz.UserService;
import com.qqyx.file.manage.dao.UserDao;
import com.qqyx.file.manage.model.mysql.User;
import com.qqyx.file.manage.utils.Page;


@Service("userService")
@Transactional
@SuppressWarnings("all")
public class UserServiceImpl implements UserService {

	@Autowired
    private UserDao userDao;
	
	@Override
	public void removeUnused(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public User get(String id) {
		return userDao.get(id);
	}

	@Override
	public void saveOrUpdate(User model) {
		userDao.saveOrUpdate(model);
	}
	@Override
	public Page querys(Map<String, Object> paramMap,
			LinkedHashMap<String, String> orderby) {
		return userDao.querys(paramMap,orderby);
	}

	@Override
	public User findByOpenId(String openId) {
		return userDao.findByOpenId(openId);
	}

	@Override
	public User getUserByName(String userName, String password) {
		return userDao.getUserByName(userName, password);
	}

	@Override
	public List getUserByType(String type) {
		return userDao.getUserByType(type);
	}

}
