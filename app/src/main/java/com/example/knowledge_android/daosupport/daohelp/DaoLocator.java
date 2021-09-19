package com.example.knowledge_android.daosupport.daohelp;

import com.example.knowledge_android.OneApplication;
import com.example.knowledge_android.daosupport.base.IDatabaseHelper;
import com.example.knowledge_android.daosupport.beandao.master.CashierDao;
import com.example.knowledge_android.daosupport.beandao.master.PluDao;
import com.example.knowledge_android.daosupport.beandao.tran.TranDetailDao;
import com.example.knowledge_android.daosupport.beandao.tran.TranHeadDao;

public class DaoLocator {

    public static OneApplication oneApplication;
    public static IDatabaseHelper databaseHelper;

    //主档库表
    public static CashierDao cashierDao;
    public static PluDao pluDao;

    //交易库表
    public static TranHeadDao tranHeadDao;
    public static TranDetailDao tranDetailDao;

    public static void build(IDatabaseHelper iDatabaseHelper){
        databaseHelper = iDatabaseHelper;
        //主档库表
        cashierDao = new CashierDao(databaseHelper);
        pluDao = new PluDao(databaseHelper);

        //交易库表
        tranHeadDao = new TranHeadDao(databaseHelper);
        tranDetailDao = new TranDetailDao(databaseHelper);
    }

}
