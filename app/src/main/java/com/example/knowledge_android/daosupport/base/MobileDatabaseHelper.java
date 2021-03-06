package com.example.knowledge_android.daosupport.base;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.knowledge_android.OneApplication;
import com.example.knowledge_android.daosupport.MasterDatabaseHelper;
import com.example.knowledge_android.daosupport.TransDatabaseHelper;
import com.example.knowledge_android.daosupport.beanconfig.TableList;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.TableUtils;


import java.sql.SQLException;

/**
 * MobileDatabaseHelper.
 *
 * @author Bruce on 2018/06/04.
 */
public class MobileDatabaseHelper extends AbstractDatabaseHelper implements IDatabaseHelper {

    OneApplication application;

    //数据库1
    MasterDatabaseHelper masterDatabaseHelper;
    //数据库2
    TransDatabaseHelper transDatabaseHelper;

    //数据库1的连接
    ConnectionSource masterConnection;
    //数据库2的连接
    ConnectionSource tranConnection;

    public MobileDatabaseHelper(OneApplication application) {
        this.application = application;
        initConnectionSource();
    }

    @Override
    public void initConnectionSource() {
        initConnectionSource(false);
    }

    /*
    Android使用getWritableDatabase()和getReadableDatabase()方法都可以获取一个用于操作数据库的SQLiteDatabase实例。
    (getReadableDatabase()方法中会调用getWritableDatabase()方法)

    其中getWritableDatabase()方法以读写方式打开数据库，一旦数据库的磁盘空间满了，数据库就只能读而不能写，倘若使用的是getWritableDatabase()方法就会出错。

    getReadableDatabase()方法则是先以读写方式打开数据库，如果数据库的磁盘空间满了，就会打开失败，当打开失败后会继续尝试以只读方式打开数据库。
    如果该问题成功解决，则只读数据库对象就会关闭，然后返回一个可读写的数据库对象。
    */
    @Override
    public void initConnectionSource(boolean isServer) {
        try {
            SQLiteDatabase db;
            if (masterConnection == null) {
                masterDatabaseHelper = getMasterDatabaseHelper();
                db = masterDatabaseHelper.getReadableDatabase();
                db.close();
                this.masterConnection = masterDatabaseHelper.getConnectionSource();
            }

            if (transDatabaseHelper == null) {
                transDatabaseHelper = getTransDatabaseHelper();
                db = transDatabaseHelper.getWritableDatabase();
                db.close();
                this.tranConnection = transDatabaseHelper.getConnectionSource();
            }
        } catch (Exception ex) {
            Log.e("1", "2", ex);
        }
    }

    @Override
    public void close() {
        close(false);
    }

    @Override
    public void close(boolean onlyMaster) {
        if (onlyMaster) {
            try {
                if (masterConnection != null) {
                    masterDatabaseHelper.close();
                    masterConnection = null;
                    masterDatabaseHelper = null;
                }
            } catch (Exception e) {
                Log.e("D", "仅仅关闭Master库连接", e);
            }
        } else {
            try {
                if (masterConnection != null) {
                    masterDatabaseHelper.close();
                    masterConnection = null;
                    masterDatabaseHelper = null;
                }
                if (tranConnection != null) {
                    transDatabaseHelper.close();
                    tranConnection = null;
                    transDatabaseHelper = null;
                }
            } catch (Exception e) {
                Log.e("E", "关闭Master库连接和关闭Tran库连接", e);
            }
        }
    }

    @Override
    public ConnectionSource getMasterConnection() {
        return masterConnection;
    }

    @Override
    public ConnectionSource getTranConnection() {
        return tranConnection;
    }


    @Override
    public void createTablesIfNotExists(Class clazz) {
        ConnectionSource db = null;
        if (TableList.getMasterTables().contains(clazz)) {
            db = masterConnection;
        }
        if (TableList.getTranTables().contains(clazz)) {
            db = tranConnection;
        }

        DatabaseTableConfig tableConfig = TableList.getTableConfigMap().get(clazz);
        if (db != null && tableConfig != null) {
            try {
                TableUtils.createTableIfNotExists(db, tableConfig);
            } catch (SQLException e) {
                Log.getStackTraceString(e);
            }
        }
    }

    @Override
    public void createTablesIfNotExists(Class clazz, ConnectionSource db) {
        DatabaseTableConfig tableConfig = TableList.getTableConfigMap().get(clazz);
        if (db != null && tableConfig != null) {
            try {
                TableUtils.createTableIfNotExists(db, tableConfig);
            } catch (SQLException e) {
                Log.getStackTraceString(e);
            }
        }
    }

    @Override
    public ConnectionSource getConnectionSource(Class clazz) {
        if (TableList.getMasterTables().contains(clazz)) {
            return masterConnection;
        }
        if (TableList.getTranTables().contains(clazz)) {
            return tranConnection;
        }
        return null;
    }

    public MasterDatabaseHelper getMasterDatabaseHelper() {
        if (masterDatabaseHelper == null) {
            masterDatabaseHelper = OpenHelperManager.getHelper(application, MasterDatabaseHelper.class);
            masterDatabaseHelper.loadDefaults();
        }
        return masterDatabaseHelper;
    }

    public TransDatabaseHelper getTransDatabaseHelper() {
        if (transDatabaseHelper == null) {
            //TODO Ormlite使用多个数据库时，不能使用下面这行代码，因为先创建的MasterDatabaseHelper，已经设置了Class为MasterDatabaseHelper.class,这里继续设置Class会出错
            //transDatabaseHelper = OpenHelperManager.getHelper(application, TransDatabaseHelper.class);
            transDatabaseHelper = new TransDatabaseHelper(application);
            transDatabaseHelper.loadDefaults();
        }
        return transDatabaseHelper;
    }

    /**
     * Reload master database after overwriting master.db with new version.
     * 用新版本覆盖master.db后重新加载主数据库。
     */
    public void reloadMasterDatabaseHelper() {
        OpenHelperManager.releaseHelper();
        masterDatabaseHelper = OpenHelperManager.getHelper(application, MasterDatabaseHelper.class);
        masterDatabaseHelper.loadDefaults();
    }
}
