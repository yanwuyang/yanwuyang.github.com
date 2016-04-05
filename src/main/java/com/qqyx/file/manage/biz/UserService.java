package com.qqyx.file.manage.biz;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.qqyx.file.manage.model.mysql.User;
import com.qqyx.file.manage.utils.Page;


/**
 * 用户service
 * @author liujinliang
 *
 */
public interface UserService extends BaseService<User> {

	/**
	 * 获取用户列表(分页)
	 * 
	 * @param paramMap
	 * @return
	 */
	public Page querys(Map<String, Object> paramMap,LinkedHashMap<String, String> orderby);
	
	public User findByOpenId(String openId);
	
	public User getUserByName(String userName,String password);
	
	public List getUserByType(String type);
	
}
