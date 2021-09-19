package com.example.knowledge_android.daosupport_annotation.daohelp;

import com.example.knowledge_android.OneApplication;
import com.example.knowledge_android.daosupport_annotation.base.IDatabasePlusHelper;
import com.example.knowledge_android.daosupport_annotation.beandao.master.CashierPlusDao;
import com.example.knowledge_android.daosupport_annotation.beandao.master.PluPlusDao;
import com.example.knowledge_android.daosupport_annotation.beandao.tran.TranDetailPlusDao;
import com.example.knowledge_android.daosupport_annotation.beandao.tran.TranHeadPlusDao;

public class DaoLocatorPlus {

    public static OneApplication oneApplication;
    public static IDatabasePlusHelper databaseHelper;

    //主档库表
    public static CashierPlusDao cashierDao;
    public static PluPlusDao pluDao;

    //交易库表
    public static TranHeadPlusDao tranHeadDao;
    public static TranDetailPlusDao tranDetailDao;

    public static void build(IDatabasePlusHelper iDatabaseHelper){
        databaseHelper = iDatabaseHelper;
        //主档库表
        cashierDao = new CashierPlusDao(databaseHelper);
        pluDao = new PluPlusDao(databaseHelper);

        //交易库表
        tranHeadDao = new TranHeadPlusDao(databaseHelper);
        tranDetailDao = new TranDetailPlusDao(databaseHelper);
    }

}
