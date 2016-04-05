package com.qqyx.file.manage.dao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.qqyx.file.manage.model.mysql.User;
import com.qqyx.file.manage.utils.Page;



/**
 * 用户dao
 * 
 * 
 */
@Repository
@SuppressWarnings("all")
public class UserDao extends BaseDao<User> {
	
	
	

    /**
	 * 获取用户列表
	 * 
	 * @param paramMap
	 * @return
	 */
	public Page querys(Map<String, Object> paramMap,
			LinkedHashMap<String, String> orderby) {
		
		Session  session  = getSession();
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from f_user f where 1=1 ");
		if (null != paramMap) {
			if (paramMap.containsKey("username")&& StringUtils.isNotBlank(paramMap.get("username").toString())) {
				sql.append(" and f.username like :username");
				param.put("username", "%"+paramMap.get("username")+"%");
			}
			if (paramMap.containsKey("type")&& StringUtils.isNotBlank(paramMap.get("type").toString())) {
				sql.append(" and f.type = :type");
				param.put("type",paramMap.get("type"));
			}
		}
		return this.sqlqueryForpage(sql.toString(), param, orderby);
	}
	
	public List getUserByType(String type){
		Session  session  = getSession();
		Query query = session.createQuery("select u from User u where u.type=:type");
		query.setParameter("type",type);
		return query.list();
	}
	
	/**
	 * 根据微信号查找用户信息
	 * @param userId
	 * @return
	 */
	public User findByOpenId(String openId) {
		Session  session  = getSession();
		StringBuffer  sql = new StringBuffer(" select a from User a where 1=1 and a.openId=:openId");
		Query  query =  session.createQuery(sql.toString());
		query.setParameter("openId",openId);
		List<User> list = query.list();
		if(null!=list&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public User getUserByName(String userName,String password){
		Session  session  = getSession();
		StringBuffer  sql = new StringBuffer(" select a from User a where  a.userName=:userName and a.password=:password");
		Query  query =  session.createQuery(sql.toString());
		query.setParameter("userName",userName);
		query.setParameter("password",password);
		List<User> list = query.list();
		if(null!=list&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

}

