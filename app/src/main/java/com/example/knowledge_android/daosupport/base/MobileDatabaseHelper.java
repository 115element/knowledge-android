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
        init();
    }

    public void init() {
        initConnectionSource();
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
    public void close(boolean onlyMaster) {
        try {
            if (masterConnection != null) {
                masterDatabaseHelper.close();
                masterConnection = null;
                masterDatabaseHelper = null;
            }
        } catch (Exception e) {
            Log.i("1","1",e);
        }
        if (!onlyMaster) {
            try {
                if (masterConnection != null) {
                    masterDatabaseHelper.close();
                    masterConnection = null;
                    masterDatabaseHelper = null;
                }
            } catch (Exception e) {
                Log.e("d","d",e);
            }
        }
    }

    @Override
    public void close() {
        close(false);
    }

    @Override
    public boolean createTransDatabase() {
        return false;
    }

    public void setMasterDatabaseHelper(MasterDatabaseHelper masterDatabaseHelper) {
        this.masterDatabaseHelper = masterDatabaseHelper;
    }


    @Override
    public void initConnectionSource() {
        initConnectionSource(false);
    }

    @Override
    public void initConnectionSource(boolean isServer) {
        try {
            SQLiteDatabase db;
            if (masterConnection == null) {
                masterDatabaseHelper = getMasterDatabaseHelper();
                //获取只读数据库
                db = masterDatabaseHelper.getReadableDatabase();
                db.close();
                this.masterConnection = masterDatabaseHelper.getConnectionSource();
            }

            if (transDatabaseHelper == null) {
                transDatabaseHelper = getTransDatabaseHelper();
                //获取可写数据库
                db = transDatabaseHelper.getWritableDatabase();
                db.close();
                this.tranConnection = transDatabaseHelper.getConnectionSource();
//                for (Class table : TableList.getTranTables()) {
//                    createTablesIfNotExists(table, tranConnection);
//                }
            }
        } catch (Exception ex) {
           Log.e("1","2",ex);
        }
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

    /**
     * Get MasterDatabaseHelper.
     */
    public MasterDatabaseHelper getMasterDatabaseHelper() {
        if (masterDatabaseHelper == null) {
            masterDatabaseHelper = OpenHelperManager.getHelper(application, MasterDatabaseHelper.class);
            masterDatabaseHelper.loadDefaults();
        }
        return masterDatabaseHelper;
    }

    public TransDatabaseHelper getTransDatabaseHelper() {
        if (transDatabaseHelper == null) {
            //TODO 使用多个数据库时，不能使用下面这行代码，因为先创建的MasterDatabaseHelper，已经设置了Class为MasterDatabaseHelper.class
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


//    public void removeOutdatedData() {
//        if (masterDatabaseHelper != null) {
//            new Thread("removeOutdatedData") {
//                @Override
//                public void run() {
//                    try {
//                        sleep(2000); // make startup faster
//                        masterDatabaseHelper.removeOutdatedData();
//                    } catch (InterruptedException ignore) {
//                    }
//                }
//            }.start();
//        }
//    }
}
