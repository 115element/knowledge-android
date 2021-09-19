package com.example.knowledge_android.daosupport.base;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public interface IDatabaseHelper {

    void initConnectionSource();

    void initConnectionSource(boolean isServer);

    void createTablesIfNotExists(Class modelClass);

    void createTablesIfNotExists(Class modelClass, ConnectionSource db);

    <D extends Dao<T, ?>, T> D getDao(Class<T> clazz) throws SQLException;

    <D extends Dao<T, ?>, T> D getDao(ConnectionSource connection, Class<T> clazz) throws SQLException;

    ConnectionSource getConnectionSource(Class clazz);

    ConnectionSource getMasterConnection();

    ConnectionSource getTranConnection();

    void close(boolean onlyMaster);

    void close();

    boolean createTransDatabase();

}
