package com.qqyx.file.manage.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.BasicTransformerAdapter;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.util.Assert;

import com.qqyx.file.manage.constant.Result;
import com.qqyx.file.manage.utils.Page;
import com.qqyx.file.manage.utils.PageContext;


public abstract class HibernateEntityDao  implements
		LogEnabled {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	protected final Session getSession() {
        return sessionFactory.getCurrentSession();
    }

	protected abstract Class getReferenceClass();

	public Criteria createCriteria() throws HibernateException {
		Session s = getSession();
		return s.createCriteria(getReferenceClass());
	}

	public int update(String hql, Object[] args) {
		Session s = getSession();
		Query query  =  s.createQuery(hql);
		if ((args != null) && (args.length > 0)) {
			for (int i = 0; i < args.length; i++)
				query.setParameter(i, args[i]);
		}
		int ret  =  query.executeUpdate();
		return ret;
	}

	public int delete(String hql, Object[] args) {
		return update(hql, args);
	}

	public Object findForObject(final String select, final Object[] values) {
		Session s = getSession();
		Query query = s.createQuery(select);
		if ((values != null) && (values.length > 0)) {
			for (int i = 0; i < values.length; i++)
				query.setParameter(i, values[i]);
		}
		return query.uniqueResult();
	}

	public Object findForObject(final String select, final Map map) {
		Session s = getSession();
		Query query = s.createQuery(select);
		if ((map != null) && (map.size() > 0)) {
			Set keySet = map.keySet();
			Iterator it = keySet.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = (String) map.get(key);
				query.setParameter(key, value);
			}
		}
		return query.uniqueResult();
	}

	public Query createQuery(String queryString, Map<String, ?> values) {
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	public Page queryForpage(String hql) {
		return queryForpage(hql, null, null);
	}

	public Page queryForpage(String hql, LinkedHashMap<String, String> orderby) {
		return queryForpage(hql, null, orderby);
	}

	public Page queryForpage(String hql, Object[] params,
			LinkedHashMap<String, String> orderby) {
		return queryForpage(hql, params, PageContext.getOffset().intValue(),
				PageContext.getPagesize().intValue(), orderby);
	}

	public Page queryForpage(String hql, int offset, int pagesize,
			LinkedHashMap<String, String> orderby) {
		return queryForpage(hql, null, offset, pagesize, orderby);
	}

	public Page queryForpage(String hql, Object values, int offset,
			int pagesize, LinkedHashMap<String, String> orderby) {
		return queryForpage(hql, new Object[] { values }, offset, pagesize,
				orderby);
	}

	public Page queryForpage(String select, Object[] values, int pagestart,
			int pagesize, LinkedHashMap<String, String> orderby) {
		Page page = new Page();
		String countHql = getHibernateCountQuery(select);
		Long totalCount = (Long) findForObject(countHql, values);
		page.setTotal(totalCount.intValue());
		page.setItems(queryForList(select, values, pagestart, pagesize, orderby));
		return page;
	}

	public List queryForList(final String select, final Object[] values,
			final int pagestart, final int pagesize,
			final LinkedHashMap<String, String> orderby) {
		Session s = getSession();
		Query query = s.createQuery(select
				+ HibernateEntityDao.buildOrderby(orderby));
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query
				.setFirstResult(
						pagestart > 0 ? (pagestart - 1) * pagesize
								: pagestart).setMaxResults(pagesize)
				.list();
		
	}

	public Page queryForPage(String selectCount, String select,
			Object[] values, int pagestart, int pagesize,
			LinkedHashMap<String, String> orderby) {
		Page page = new Page();
		Long totalCount = (Long) findForObject(selectCount, values);
		page.setTotal(totalCount.intValue());
		page.setItems(queryForList(select, values, pagestart, pagesize, orderby));
		return page;
	}

	public Page sqlqueryForpage(String sql, Map args,
			LinkedHashMap<String, String> orderby) {
		String sqlString = sql + buildOrderby(orderby);
		SQLQuery query =getSession().createSQLQuery(sqlString);
		setParames(args, query);
		Page page = new Page();
		List list = query
				.setFirstResult(
						PageContext.getOffset().intValue() > 0 ? (PageContext
								.getOffset().intValue() - 1)
								* PageContext.getPagesize().intValue()
								: PageContext.getOffset().intValue())
				.setMaxResults(PageContext.getPagesize().intValue())
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		long count = countBySql(sql, args);
		page.setTotal((int) count);
		page.setItems(list);
		return page;
	}
	
	@SuppressWarnings("unchecked")
	public <E> Page<E> sqlqueryForpageobj(String sql, Map args,
			LinkedHashMap<String, String> orderby,Class<E> clazz) {
		String sqlString = sql + buildOrderby(orderby);
		SQLQuery query =getSession().createSQLQuery(sqlString).addEntity(clazz);
		setParames(args, query);
		Page<E> page = new Page<E>();
		List<E> list = query
				.setFirstResult(
						PageContext.getOffset().intValue() > 0 ? (PageContext
								.getOffset().intValue() - 1)
								* PageContext.getPagesize().intValue()
								: PageContext.getOffset().intValue())
				.setMaxResults(PageContext.getPagesize().intValue()).list();
		long count = countBySql(sql, args);
		page.setTotal((int) count);
		page.setItems(list);
		return page;
	}

	public List sqlqueryForlist(String sql, Map args,
			LinkedHashMap<String, String> orderby) {
		String sqlString = sql + buildOrderby(orderby);
		SQLQuery query = getSession().createSQLQuery(sqlString);
		setParames(args, query);
		Page page = new Page();
		List list = query
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}

	public Page queryForpageByMap(String sql, Map args,
			LinkedHashMap<String, String> orderby) {
		String sqlString = sql + buildOrderby(orderby);
		Query query = getSession().createQuery(sqlString);
		setParames(args, query);
		Page page = new Page();
		List list = query
				.setFirstResult(
						PageContext.getOffset().intValue() > 0 ? (PageContext
								.getOffset().intValue() - 1)
								* PageContext.getPagesize().intValue()
								: PageContext.getOffset().intValue())
				.setMaxResults(PageContext.getPagesize().intValue()).list();
		String countHql = getHibernateCountQuery(sql);
		Long totalCount = (Long) findForObject(countHql, args);
		page.setTotal(totalCount.intValue());
		page.setItems(list);
		return page;
	}

	public Object sqlfindMap(final String select, final Object[] values) {
		Session s = getSession();
		Query query = s.createSQLQuery(select);
		if (values != null) {
			for (int i = 0; i < values.length; i++)
				query.setParameter(i, values[i]);
		}
		return query.setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
	}

	private long countBySql(String strSql, Map params) {
		strSql = "select count(*) from (" + strSql + ") as totals";
		SQLQuery query = getSession().createSQLQuery(strSql);
		setParames(params, query);
		Integer totalCount = Integer.valueOf(0);
		Object count = query.uniqueResult();

		if ((count instanceof BigDecimal))
			totalCount = Integer.valueOf(count == null ? 0
					: ((BigDecimal) count).intValue());
		else if ((count instanceof BigInteger)) {
			totalCount = Integer.valueOf(count == null ? 0
					: ((BigInteger) count).intValue());
		}

		return totalCount.intValue();
	}

	protected static String buildOrderby(LinkedHashMap<String, String> orderby) {
		StringBuffer orderbyql = new StringBuffer(" ");
		if ((orderby != null) && (orderby.size() > 0)) {
			orderbyql.append(" order by ");
			for (String key : orderby.keySet()) {
				orderbyql.append(key).append(" ")
						.append((String) orderby.get(key)).append(",");
			}
			orderbyql.deleteCharAt(orderbyql.length() - 1);
		}

		return orderbyql.toString();
	}

	private void setParames(Map args, Query query) {
		if ((args != null) && (args.size() > 0)) {
			Set keySet = args.keySet();
			Iterator it = keySet.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				//String value = (String) args.get(key);
				query.setParameter(key, args.get(key));
			}
		}
	}

	private String getHibernateCountQuery(String hql) {
		String temp = StringUtils.defaultIfEmpty(hql, "").toLowerCase();

		int index = temp.indexOf("from");
		if (index != -1) {
			return "select count(*) " + hql.substring(index);
		}
		throw new RuntimeErrorException(null, "无效的sql语句");
	}

	private static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql
				+ " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	private static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", 2);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	private class ExecuteCallback implements HibernateCallback {
		private String hql;
		private Object[] args;

		ExecuteCallback(String hql, Object[] args) {
			this.hql = hql;
			this.args = args;
		}

		public Object[] getArgs() {
			return this.args;
		}

		public void setArgs(Object[] args) {
			this.args = args;
		}

		public String getHql() {
			return this.hql;
		}

		public void setHql(String hql) {
			this.hql = hql;
		}

		private int getParameterCount(String hql) {
			int count = 0;
			if (hql != null) {
				StringBuffer buffer = new StringBuffer(hql);
				int pos = -1;
				while ((pos = buffer.indexOf("?")) != -1) {
					buffer.delete(0, pos + 1);
					count++;
				}
			}
			return count;
		}

		public Object doInHibernate(Session session) throws HibernateException,
				SQLException {
			if (this.hql == null) {
				throw new RuntimeException("nullable statement");
			}
			int paramCount = 0;
			if (((paramCount = getParameterCount(this.hql)) > 0)
					&& ((this.args == null) || (this.args.length != paramCount))) {
				throw new RuntimeException("nullable arguments");
			}
			Query query = session.createQuery(this.hql);

			if (this.args != null) {
				for (int i = 0; i < this.args.length; i++) {
					query.setParameter(i, this.args[i]);
				}
			}
			return Integer.valueOf(query.executeUpdate());
		}
	}

	private static class UpperCasedAliasToEntityMapResultTransformer extends
			BasicTransformerAdapter implements Serializable {
		public Object transformTuple(Object[] tuple, String[] aliases) {
			Map result = new HashMap(tuple.length);
			for (int i = 0; i < tuple.length; i++) {
				String alias = aliases[i];
				if (alias != null) {
					result.put(alias, tuple[i]);
				}
			}
			return result;
		}
	}
}