package com.example.knowledge_android.daosupport_annotation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.util.Log;

import com.example.knowledge_android.OneApplication;
import com.example.knowledge_android.daosupport.bean.tran.TranDetail;
import com.example.knowledge_android.daosupport.bean.tran.TranHead;
import com.example.knowledge_android.daosupport_annotation.bean.tran.TranDetailPlus;
import com.example.knowledge_android.daosupport_annotation.bean.tran.TranHeadPlus;
import com.example.knowledge_android.daosupport_annotation.tablelistplus.TableListPlus;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;
import java.util.List;

/**
 * Database helper for trans.
 *
 * @author Alex on 2021-09-19
 */

// transDatabaseHelper = new TransDatabaseHelper(application); 这句代码触发该类创建
public class TransDatabasePlusHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = "TransDatabasePlusHelper";
    private static final String DATABASE_NAME = "trans_plus";
    private static final int DATABASE_VERSION = 2021010801;

    private OneApplication application;
    private Context mContext;


    /**
     * 参数说明：
     * context：上下文。
     * databaseName： 数据库名。
     * factory： 游标实例，多数时候设置成NULL。
     * databaseVersion：数据库版本，当数据库版本升高时，会调用onUpgrade（）方法。
     */
    public TransDatabasePlusHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
        this.application = (OneApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            for (Class clazz : TableListPlus.getTranTables()) {
                TableUtils.createTableIfNotExists(connectionSource, clazz);
            }
            Log.i(TAG, "Tran tables were created.");
        } catch (Exception e) {
            Log.e(TAG, "Cannot create database tran_plus.", e);
        }

        try {
            Dao dao = DaoManager.createDao(connectionSource, TranHeadPlus.class);
            List list = dao.queryForAll();
            if (list == null || list.size() == 0) {
                TranHeadPlus tranHead = new TranHeadPlus();
                tranHead.setCustId("111");
                tranHead.setStoreId("2099");
                tranHead.setTransactionNumber(1000);
                tranHead.setPosNo(100);
                dao.create(tranHead);
                Log.i(TAG, "TranHead表数据插入成功");
            }
        } catch (Exception e) {
            Log.e(TAG, "TranHead表数据插入失败", e);
        }

        try {
            Dao dao = DaoManager.createDao(connectionSource, TranDetailPlus.class);
            List list = dao.queryForAll();
            if (list == null || list.size() == 0) {
                TranDetailPlus tranDetail = new TranDetailPlus();
                tranDetail.setSystemDate(new Date());
                tranDetail.setCustId("111");
                tranDetail.setPosNo(111);
                tranDetail.setStoreId("123");
                tranDetail.setTransactionNumber(111);
                dao.create(tranDetail);
                Log.i(TAG, "TranDetail表数据插入成功");
            }
        } catch (Exception e) {
            Log.e(TAG, "TranDetail表数据插入失败", e);
        }
    }


    /**
     * 安卓交易库表结构更新方法
     * 1.修改该类属性DATABASE_VERSION的值
     * 2.下方增加判断更新表结构的条件
     * 3.从上往下执行，最新sql写最后面
     * <p>
     * 注意：只要DATABASE_VERSION不发生变化，则框架底层将不调用onUpgrade方法
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        System.out.println("Tran数据库更新执行开始----------------------------------!");

        if (newVersion == 2021010801) {
            try {
                final String sql = "alter table tranhead add takeInOrOut int not null default 2";
                db.execSQL(sql);
            } catch (Exception e) {
            }
        }

        // 测试数据库更新[成功]
        if (newVersion == 2019021808) {
            try {
                final String sql = "insert into property values ('PingWanOnlineURL','yes')";
                db.execSQL(sql);
            } catch (Exception e) {
            }
        }

        //整合pos新加的字段
        if (oldVersion <= 2019120901) {
            upgrade2019120901(db);
        }

        if (oldVersion <= 2019121601) {
            upgrade2019121601(db);
        }
        if (oldVersion <= 2019122601) {
            upgrade2019122601(db);
        }

        System.out.println("Tran数据库更新执行完毕----------------------------------!");
    }

    private void upgrade2019122601(SQLiteDatabase db) {
        try {
            final String sql = "alter table shiftpayment add preOrderAmount decimal(12,2) not null default 0";
            db.execSQL(sql);
        } catch (Exception e) {
        }
        try {
            final String sql = "alter table shiftpayment add preOrderCount int not null default 0";
            db.execSQL(sql);
        } catch (Exception e) {
        }
    }

    private void upgrade2019121601(SQLiteDatabase db) {
        try {
            final String sql = "alter table trandetail add scanCode varchar(20)";
            db.execSQL(sql);
        } catch (Exception e) {
        }
    }

    private void upgrade2019120901(SQLiteDatabase db) {
        try {
            final String sql = "alter table tranhead add wm_type int";
            db.execSQL(sql);
        } catch (Exception e) {
        }
        try {
            final String sql = "alter table tranhead add refund_id varchar(100)";
            db.execSQL(sql);
        } catch (Exception e) {
        }
        try {
            final String sql = "alter table outsale_head add wm_type_name varchar(100)";
            db.execSQL(sql);
        } catch (Exception e) {
        }
        try {
            final String sql = "alter table outsale_head add refund_id varchar(100)";
            db.execSQL(sql);
        } catch (Exception e) {
        }
        try {
            final String sql = "alter table o2opaydetail add returnFlag varchar(2)";
            db.execSQL(sql);
        } catch (Exception e) {
        }
        try {
            final String sql = "alter table tranhead add pickUpNumber varchar(50) ";
            db.execSQL(sql);
        } catch (Exception e) {
        }
        try {
            final String sql = "alter table tranhead add pickUpPhone varchar(20) ";
            db.execSQL(sql);
        } catch (Exception e) {
        }
        try {
            final String sql = "alter table tranhead add pickUpDate varchar(20) ";
            db.execSQL(sql);
        } catch (Exception e) {
        }
        try {
            final String sql = "alter table tranhead add reserved1 varchar(100) ";
            db.execSQL(sql);
        } catch (Exception e) {
        }
        try {
            final String sql = "alter table tranhead add reserved2 varchar(100) ";
            db.execSQL(sql);
        } catch (Exception e) {
        }
        try {
            final String sql = "alter table tranhead add reserved3 varchar(100) ";
            db.execSQL(sql);
        } catch (Exception e) {
        }
        try {
            final String sql = "alter table shifthead add preOrderAmount decimal(12,2) not null default 0 ";
            db.execSQL(sql);
        } catch (Exception e) {
        }
        try {
            final String sql = "alter table yhead add preOrderAmount decimal(12,2) not null default 0 ";
            db.execSQL(sql);
        } catch (Exception e) {
        }
        try {
            final String sql = "alter table zhead add preOrderAmount decimal(12,2) not null default 0 ";
            db.execSQL(sql);
        } catch (Exception e) {
        }
        try {
            final String sql = "alter table tranhead add printNo varchar(8)";
            db.execSQL(sql);
        } catch (Exception e) {
        }
        try {
            final String sql = "alter table tranHead add memberZongBai varchar(64)";
            db.execSQL(sql);
        } catch (Exception e) {
        }
    }

    private String getAndroidId() {
        return Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    private String getWiFiMac() {
        //老版本获取网路mac地址
        //WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        //return wifiManager.getConnectionInfo().getMacAddress();

        //老版本已经禁用了，现在只能用下边方法
        String mac = "";
        try {
            NetworkInterface networkInterface = NetworkInterface.getByName("wlan0");
            mac = new String(networkInterface.getHardwareAddress());
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return mac;
    }

    public void removeOutdatedData() {
    }

    public void resetDatabase() {
    }

    public void sanityCheck() {
    }

    public void loadDefaults() {
        Log.i(TAG, "loadDefaults啥也没做。。");
    }

}
