package com.qqyx.file.manage.dao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.qqyx.file.manage.model.mysql.SysDict;
import com.qqyx.file.manage.utils.Page;

@Repository
@SuppressWarnings("all")
public class SysDictDao extends BaseDao<SysDict> {
	
	
	public Page querys(Map<String, Object> paramMap,
			LinkedHashMap<String, String> orderby) {
		
		Session  session  = getSession();
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from f_sys_dict t where 1=1 ");
		/*if (null != paramMap) {
			if (paramMap.containsKey("username")&& StringUtils.isNotBlank(paramMap.get("username").toString())) {
				sql.append(" and t.username like :username");
				param.put("username", "%"+paramMap.get("username")+"%");
			}
		}*/
		return this.sqlqueryForpage(sql.toString(), param, orderby);
	}
	
	public void delete(String[] ids){
		Session session = getSession();
		Query query = session.createSQLQuery("delete from f_sys_dict where id in (:ids)");
		query.setParameterList("ids", ids);
		query.executeUpdate();
	}
}
