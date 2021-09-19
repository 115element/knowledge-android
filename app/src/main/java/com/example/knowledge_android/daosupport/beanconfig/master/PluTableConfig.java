package com.example.knowledge_android.daosupport.beanconfig.master;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseFieldConfig;

import java.util.ArrayList;
import java.util.List;

public class PluTableConfig {

    public static List<DatabaseFieldConfig> getConfig() {
        List<DatabaseFieldConfig> fieldConfigs = new ArrayList<DatabaseFieldConfig>();
        DatabaseFieldConfig field = null;

        field = new DatabaseFieldConfig("custId");
        field.setWidth(20);
        field.setUniqueCombo(true);
        fieldConfigs.add(field);

        field = new DatabaseFieldConfig("storeId");
        field.setUniqueCombo(true);
        field.setCanBeNull(false);
        field.setWidth(8);
        fieldConfigs.add(field);


        field = new DatabaseFieldConfig("pluPrice");
        field.setCanBeNull(false);
        field.setColumnDefinition("DECIMAL(8,2) NOT NULL");
        fieldConfigs.add(field);


        field = new DatabaseFieldConfig("beginDate");
        field.setFormat("yyyy-MM-dd");
        field.setDataType(DataType.DATE_STRING);
        fieldConfigs.add(field);


        field = new DatabaseFieldConfig("siGroup");
        field.setWidth(11);
        field.setColumnName("si_group");
        fieldConfigs.add(field);

        field = new DatabaseFieldConfig("promotionPrice");
        field.setWidth(256);
        field.setColumnName("promotion_price");
        fieldConfigs.add(field);


        return fieldConfigs;
    }
}
