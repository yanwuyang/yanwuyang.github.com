package com.qqyx.file.manage.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.qqyx.file.manage.common.HibernateEntityDao;
import com.qqyx.file.manage.common.LogEnabled;
import com.qqyx.file.manage.utils.GenericsUtils;
import com.qqyx.file.manage.utils.PageContext;


public abstract class BaseDao<T> extends HibernateEntityDao implements LogEnabled{
	protected Class<T> entityClass;

	public Class<T> getReferenceClass(){
	    return this.entityClass;
	}

	public BaseDao() {
	    this.entityClass = GenericsUtils.getGenericClass(getClass());
	}
	

	public List findAll() {
		Session s =  getSession();
		Query  query = s.createQuery("from " + getReferenceClass().getName());
		List  list = query.list();
		return list;
	}
	public T get(Serializable key){
		return  (T)getSession().get(getReferenceClass(), key);
	}
	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
	}
	public void delete(T entity) {
		getSession().delete(entity);
	}
	
	/**
	 * 获取当前页的数据
	 * @param 所有页的数据 allList
	 * @return
	 */
	public List getPagedList(List allList) {
	    int startI =  PageContext.getOffset().intValue() > 0 ?
	            (PageContext.getOffset() - 1) * PageContext.getPagesize() : 0;
        int endI = startI + PageContext.getPagesize();
	            
	    List resList = new ArrayList();
	    for(int i = startI; i < allList.size() && i < endI; i++) {
            resList.add(allList.get(i));
        }
	    
        return resList;
    }
}
