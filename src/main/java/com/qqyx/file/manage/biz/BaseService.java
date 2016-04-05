package com.qqyx.file.manage.biz;

import com.qqyx.file.manage.common.LogEnabled;

public interface BaseService<T> extends LogEnabled{
	
	public abstract void removeUnused(String id);

	public abstract T get(String id);

	public abstract void saveOrUpdate(T model);

}
