package com.qqyx.file.manage.biz;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.qqyx.file.manage.model.mysql.SysDict;
import com.qqyx.file.manage.utils.Page;

public interface SysDictService extends BaseService<SysDict>{
	
	
	public List getDictsByType(String type);
	

	public Page querys(Map<String, Object> paramMap,LinkedHashMap<String, String> orderby);
	
	public void delete(String[] ids);
	
}
