package com.example.knowledge_android.daosupport_annotation.base;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public abstract class AbstractPlusDatabaseHelper implements IDatabasePlusHelper {

    @Override
    public <D extends Dao<T, ?>, T> D getDao(Class<T> clazz) throws SQLException {
        // special reflection fu is now handled internally by create dao calling the database type
        Dao<T, ?> dao = DaoManager.createDao(getConnectionSource(clazz), clazz);
        @SuppressWarnings("unchecked")
        D castDao = (D) dao;
        return castDao;
    }

    @Override
    public <D extends Dao<T, ?>, T> D getDao(ConnectionSource connection, Class<T> clazz) throws SQLException {
        // special reflection fu is now handled internally by create dao calling the database type
        Dao<T, ?> dao = DaoManager.createDao(connection, clazz);
        @SuppressWarnings("unchecked")
        D castDao = (D) dao;
        return castDao;
    }
}
