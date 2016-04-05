package com.qqyx.file.manage.common;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;

import com.qqyx.file.manage.utils.Page;
import com.qqyx.file.manage.utils.PageContext;



public class MongoEntityDao<T, K> extends BasicDAO<T,K> implements LogEnabled{
    
    public MongoEntityDao(Datastore ds) {
        super(ds);
    }
    
    public Page sqlqueryForpage(Query q, Map args, LinkedHashMap<String, String> orderby)
    {
     
      Page page = new Page();
      q.order("-logtime");
      List list = q
        .offset(
        PageContext.getOffset().intValue() > 0 ? (
        PageContext.getOffset().intValue() - 1) * 
        PageContext.getPagesize().intValue() : 
        PageContext.getOffset().intValue())
        .limit(PageContext.getPagesize().intValue())
        .asList();
      long count = q.countAll();
      page.setTotal((int)count);
      page.setItems(list);
      return page;
    }
}
