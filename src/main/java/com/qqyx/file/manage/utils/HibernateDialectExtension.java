package com.qqyx.file.manage.utils;

import java.sql.Types;

import org.hibernate.dialect.MySQLDialect;
import org.hibernate.type.StandardBasicTypes;

public class HibernateDialectExtension extends MySQLDialect {
    
    public HibernateDialectExtension() {   
        super();   
        registerHibernateType(Types.LONGVARCHAR,StandardBasicTypes.TEXT.getName());   
    } 

}
