package com.example.knowledge_android.daosupport.base;

import com.example.knowledge_android.daosupport.beanconfig.TableList;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public abstract class AbstractDatabaseHelper implements IDatabaseHelper {

    @Override
    public <D extends Dao<T, ?>, T> D getDao(Class<T> clazz) throws SQLException {
        // special reflection fu is now handled internally by create dao calling the database type
        Dao<T, ?> dao = DaoManager.createDao(getConnectionSource(clazz), TableList.getTableConfigMap().get(clazz));
        @SuppressWarnings("unchecked")
        D castDao = (D) dao;
        return castDao;
    }

    @Override
    public <D extends Dao<T, ?>, T> D getDao(ConnectionSource connection, Class<T> clazz) throws SQLException {
        // special reflection fu is now handled internally by create dao calling the database type
        Dao<T, ?> dao = DaoManager.createDao(connection, TableList.getTableConfigMap().get(clazz));
        @SuppressWarnings("unchecked")
        D castDao = (D) dao;
        return castDao;
    }
}
