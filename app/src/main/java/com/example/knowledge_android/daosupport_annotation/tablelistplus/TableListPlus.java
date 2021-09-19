package com.example.knowledge_android.daosupport_annotation.tablelistplus;

import com.example.knowledge_android.daosupport_annotation.bean.master.CashierPlus;
import com.example.knowledge_android.daosupport_annotation.bean.master.PluPlus;
import com.example.knowledge_android.daosupport_annotation.bean.tran.TranDetailPlus;
import com.example.knowledge_android.daosupport_annotation.bean.tran.TranHeadPlus;

import java.util.ArrayList;
import java.util.List;

public class TableListPlus {

    //主档数据库
    public static List<Class> masterTables;
    //交易数据库
    public static List<Class> tranTables;

    //主档数据库
    static {
        masterTables = new ArrayList<>();
        masterTables.add(CashierPlus.class);
        masterTables.add(PluPlus.class);
    }

    //交易数据库
    static {
        tranTables = new ArrayList<>();
        tranTables.add(TranHeadPlus.class);
        tranTables.add(TranDetailPlus.class);
    }


    public static List<Class> getMasterTables() {
        return masterTables;
    }

    public static void setMasterTables(List<Class> masterTables) {
        TableListPlus.masterTables = masterTables;
    }

    public static List<Class> getTranTables() {
        return tranTables;
    }

    public static void setTranTables(List<Class> tranTables) {
        TableListPlus.tranTables = tranTables;
    }
}