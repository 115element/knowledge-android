package com.example.knowledge_android.daosupport.beanconfig.tran;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseFieldConfig;

import java.util.ArrayList;
import java.util.List;

public class TranDetailTableConfig {

    public static List<DatabaseFieldConfig> getConfig() {
        List<DatabaseFieldConfig> fieldConfigs = new ArrayList<DatabaseFieldConfig>();
        DatabaseFieldConfig field = null;
        field = new DatabaseFieldConfig("id");
        field.setCanBeNull(false);
        field.setGeneratedId(true); //TODO 自动生成ID配置
        fieldConfigs.add(field);

        field = new DatabaseFieldConfig("custId");
        field.setCanBeNull(false);
        field.setUniqueCombo(true);
        field.setWidth(20);
        fieldConfigs.add(field);

        field = new DatabaseFieldConfig("storeId");
        field.setCanBeNull(false);
        field.setUniqueCombo(true);
        field.setWidth(8);
        fieldConfigs.add(field);

        field = new DatabaseFieldConfig("posNo");
        field.setCanBeNull(false);
        field.setUniqueCombo(true);
        fieldConfigs.add(field);

        field = new DatabaseFieldConfig("transactionNumber");
        field.setCanBeNull(false);
        field.setUniqueCombo(true);
        fieldConfigs.add(field);

        field = new DatabaseFieldConfig("itemSeq");
        field.setCanBeNull(false);
        field.setUniqueCombo(true);
        fieldConfigs.add(field);

        field = new DatabaseFieldConfig("systemDate");
        field.setFormat("yyyy-MM-dd HH:mm:ss");
        field.setDataType(DataType.DATE_STRING);
        field.setCanBeNull(false);
        fieldConfigs.add(field);
        return fieldConfigs;
    }
}
