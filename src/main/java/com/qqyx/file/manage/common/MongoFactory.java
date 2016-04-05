package com.qqyx.file.manage.common;

import java.net.UnknownHostException;
import java.util.Arrays;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * Copyright(c) 2015-2015 by yonyouup. 
 * All Rights Reserved
 * MongoDB数据源工厂类
 * @author Mazfa
 * @version v1 , 2015年3月26日
 * @since v1.0
 */
public class MongoFactory implements LogEnabled{
    
    private String ip;
    private String port;
    private String username;
    private String password;
    private String dbname;
    
    private Datastore initMongo() throws IllegalArgumentException,UnknownHostException {
        if(this.ip == null || this.port == null || this.username == null
                || this.password == null || this.dbname == null) {
            throw new IllegalArgumentException(
                    "The argument of Mongodb "
                            + "(ip or port or dbName or userName or password) is null, can not connect to mongodb!");
        }
        log.info(String.format("connect mongodb on %s:%s/%s -u %s -p %s ",
                this.ip, this.port, this.dbname, this.username, this.password));

        // 使用登录验证
        MongoCredential credential = MongoCredential.createMongoCRCredential(this.username, this.dbname, this.password.toCharArray());
        // 开启一个mongo客户端
        MongoClient client = new MongoClient(new ServerAddress(this.ip,Integer.parseInt(this.port)), Arrays.asList(credential));
        Morphia morphia = new Morphia();
        Datastore datastore = morphia.createDatastore(client, this.dbname);
        return datastore;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    public void setPort(String port) {
        this.port = port;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

}
