package com.example.knowledge_android.daosupport_annotation.beandao.tran;

import android.util.Log;

import com.example.knowledge_android.daosupport_annotation.base.IDatabasePlusHelper;
import com.example.knowledge_android.daosupport_annotation.bean.tran.TranDetailPlus;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TranDetailPlusDao {

    public IDatabasePlusHelper iDatabaseHelper;

    public TranDetailPlusDao(IDatabasePlusHelper iDatabaseHelper) {
        this.iDatabaseHelper = iDatabaseHelper;
    }

    public void insert() {
        try {
            Random random = new Random(10000);
            int i = random.nextInt();
            String s = String.valueOf(i);

            TranDetailPlus tranDetailPlus = new TranDetailPlus();
            tranDetailPlus.setSystemDate(new Date());
            tranDetailPlus.setCustId(s);
            tranDetailPlus.setPosNo(i);
            tranDetailPlus.setStoreId(s);
            tranDetailPlus.setTransactionNumber(i);
            Dao<TranDetailPlus, ?> dao = iDatabaseHelper.getDao(TranDetailPlus.class);
            dao.create(tranDetailPlus);
        } catch (SQLException throwables) {
            Log.e("1", "1", throwables);
        }
    }

    public TranDetailPlus query() {
        try {
            Dao<TranDetailPlus, ?> dao = iDatabaseHelper.getDao(TranDetailPlus.class);
            List<TranDetailPlus> tranDetailPluses = dao.queryForAll();
            if (tranDetailPluses!=null && tranDetailPluses.size() != 0) {
                return tranDetailPluses.get(0);
            }
        } catch (SQLException throwables) {
            Log.e("1", "1", throwables);
        }
        return null;
    }
}
