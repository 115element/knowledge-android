package com.example.knowledge_android.daosupport.beanconfig;

import com.example.knowledge_android.daosupport.bean.master.CashierDac;
import com.example.knowledge_android.daosupport.bean.master.PluDac;
import com.example.knowledge_android.daosupport.bean.tran.TranDetail;
import com.example.knowledge_android.daosupport.bean.tran.TranHead;
import com.example.knowledge_android.daosupport.beanconfig.master.CashierTableConfig;
import com.example.knowledge_android.daosupport.beanconfig.master.PluTableConfig;
import com.example.knowledge_android.daosupport.beanconfig.tran.TranDetailTableConfig;
import com.example.knowledge_android.daosupport.beanconfig.tran.TranHeadTableConfig;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableList {

    //主档数据库
    public static List<Class> masterTables;
    //交易数据库
    public static List<Class> tranTables;

    //主档数据库
    static {
        masterTables = new ArrayList<>();
        masterTables.add(CashierDac.class);
        masterTables.add(PluDac.class);
    }

    //交易数据库
    static {
        tranTables = new ArrayList<>();
        tranTables.add(TranHead.class);
        tranTables.add(TranDetail.class);
    }

    public static Map<Class, DatabaseTableConfig> tableConfigMap = new HashMap<>();
    static {
        tableConfigMap.put(CashierDac.class,new DatabaseTableConfig(CashierDac.class,"cashier", CashierTableConfig.getConfig()));
        tableConfigMap.put(PluDac.class,new DatabaseTableConfig(PluDac.class,"plu", PluTableConfig.getConfig()));
        tableConfigMap.put(TranHead.class,new DatabaseTableConfig(TranHead.class,"tranhead", TranHeadTableConfig.getConfig()));
        tableConfigMap.put(TranDetail.class,new DatabaseTableConfig(TranDetail.class,"trandetail", TranDetailTableConfig.getConfig()));
    }




    public static List<Class> getMasterTables() {
        return masterTables;
    }

    public static void setMasterTables(List<Class> masterTables) {
        TableList.masterTables = masterTables;
    }

    public static Map<Class, DatabaseTableConfig> getTableConfigMap() {
        return tableConfigMap;
    }

    public static void setTableConfigMap(Map<Class, DatabaseTableConfig> tableConfigMap) {
        TableList.tableConfigMap = tableConfigMap;
    }

    public static List<Class> getTranTables() {
        return tranTables;
    }

    public static void setTranTables(List<Class> tranTables) {
        TableList.tranTables = tranTables;
    }
}