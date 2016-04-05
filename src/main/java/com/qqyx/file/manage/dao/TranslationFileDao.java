package com.qqyx.file.manage.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.qqyx.file.manage.model.mysql.TranslationFile;
import com.qqyx.file.manage.utils.Page;

@Repository
@SuppressWarnings("all")
public class TranslationFileDao extends BaseDao<TranslationFile> {

	public List getFiles(HashMap params) {
		Session session = this.getSession();
		String sql = "select" +
				" t.id,f.fileName,(select d.dictvalue from f_sys_dict d where d.dictkey =t.sourceLanguage) as sourceLanguage," +
				" (select d.dictvalue from f_sys_dict d where d.dictkey =t.targetLanguage) as targetLanguage," +
				" t.emergencyDegree,t.status,t.progress,t.create_date,t.update_date,t.descs " +
				" from f_translationfile t, f_attachment f" +
				" where t.attachId=f.id";
		Set keys = params.keySet();
		Iterator it = keys.iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			sql+=" and "+key+"=:"+key;
		}
		Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		it = keys.iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			query.setParameter(key, params.get(key));
		}
		return query.list();
	}
	
	public Page querys(Map<String, Object> paramMap,
			LinkedHashMap<String, String> orderby) {
		
		Session  session  = getSession();
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*,f.fileName,u1.realName as translationName,u2.realName as sourceUserName,(select d.dictvalue from f_sys_dict d where d.dictkey =t.sourceLanguage) as sourceCineLanguage,"+
				" (select d.dictvalue from f_sys_dict d where d.dictkey =t.targetLanguage) as targetCineLanguage from f_translationfile t left join f_user u1 on t.translationId=u1.id"+
				" left join f_user u2 on t.userId = u2.id" +
				" left join f_attachment f on t.attachId=f.id where 1=1");
		if (null != paramMap) {
			if (paramMap.containsKey("status")&& StringUtils.isNotBlank(paramMap.get("status").toString())) {
				sql.append(" and t.status = :status");
				param.put("status", paramMap.get("status"));
			}
		}
		return this.sqlqueryForpage(sql.toString(), param, orderby);
	}
}
