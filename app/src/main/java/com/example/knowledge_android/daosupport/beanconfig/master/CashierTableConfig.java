package com.example.knowledge_android.daosupport.beanconfig.master;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseFieldConfig;

import java.util.ArrayList;
import java.util.List;

public class CashierTableConfig {

    public static List<DatabaseFieldConfig> getConfig() {
        List<DatabaseFieldConfig> fieldConfigs = new ArrayList<DatabaseFieldConfig>();
        DatabaseFieldConfig field = null;

        // columnDefinition = 'DECIMAL(12,2) NOT NULL'

        field = new DatabaseFieldConfig( "custId");
        field.setUniqueCombo(true);
        field.setWidth(20);
        fieldConfigs.add(field);

        field = new DatabaseFieldConfig( "storeId");
        field.setUniqueCombo(true);
        field.setWidth(20);
        fieldConfigs.add(field);

        field = new DatabaseFieldConfig( "cashierNo");
        field.setUniqueCombo(true);
        field.setWidth(20);
        field.setCanBeNull(false);
        fieldConfigs.add(field);

        field = new DatabaseFieldConfig("version");
        field.setFormat("yyyy-MM-dd HH:mm:ss");
        field.setDataType(DataType.DATE_STRING);
        field.setCanBeNull(false);
        fieldConfigs.add(field);

        return fieldConfigs;
    }
}
