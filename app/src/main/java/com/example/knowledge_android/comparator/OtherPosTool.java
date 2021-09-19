package com.example.knowledge_android.comparator;

import com.example.knowledge_android.daosupport.beanconfig.TableList;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.io.File;
import java.sql.SQLException;

import groovy.util.ConfigObject;
import groovy.util.ConfigSlurper;


public class OtherPosTool {

    public static ConnectionSource otherPosConnectionSource;

    /**获取指定名字的的POS的交易库连接*/
    static ConnectionSource getOtherPosConn(String posNo) {
        try {
            ConfigObject dbConf = new ConfigSlurper().parse(new File("db.conf").toURI().toURL());
            Object posNo1 = dbConf.get("posNo");
            //otherPosConnectionSource  = new JdbcPooledConnectionSource(dbConf."${posNo}".url,dbConf."${posNo}".user,dbConf."${posNo}".password)
            //otherPosConnectionSource.setMaxConnectionAgeMillis(5000)
            return  otherPosConnectionSource;
        }catch (Exception e){
            return null;
        }
    }

    /** Get Dao object of ORMLite. */
    static Dao getDao(Class modelClass) throws SQLException {
        if (otherPosConnectionSource == null){
            return DaoManager.createDao(otherPosConnectionSource, TableList.tableConfigMap.get(modelClass));
        }else {
            return  null;
        }
    }
}
