package com.example.knowledge_android.daosupport.beandao.tran;

import android.util.Log;

import com.example.knowledge_android.daosupport.base.IDatabaseHelper;
import com.example.knowledge_android.daosupport.bean.tran.TranHead;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class TranHeadDao {

    public IDatabaseHelper iDatabaseHelper;

    public TranHeadDao(IDatabaseHelper iDatabaseHelper) {
        this.iDatabaseHelper = iDatabaseHelper;
    }


    public void insert() {
        try {
            TranHead tranHead = new TranHead();
            tranHead.setCustId("111");
            tranHead.setStoreId("2099");
            tranHead.setDealType1("101");
            tranHead.setTransactionNumber(1000);
            tranHead.setPosNo(100);
            Dao<TranHead, ?> dao = iDatabaseHelper.getDao(TranHead.class);
            dao.create(tranHead);
        } catch (SQLException throwables) {
            Log.e("1", "1", throwables);
        }
    }

    public TranHead query() {
        try {
            Dao<TranHead, ?> dao = iDatabaseHelper.getDao(TranHead.class);
            List<TranHead> tranHeads = dao.queryForAll();
            if (tranHeads!=null && tranHeads.size() != 0) {
                return tranHeads.get(0);
            }
        } catch (SQLException throwables) {
            Log.e("1", "1", throwables);
        }
        return null;
    }
}
