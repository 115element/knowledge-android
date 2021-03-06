package com.example.knowledge_android.daosupport.beandao.tran;

import android.util.Log;

import com.example.knowledge_android.daosupport.base.IDatabaseHelper;
import com.example.knowledge_android.daosupport.bean.tran.TranDetail;
import com.example.knowledge_android.daosupport.bean.tran.TranHead;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TranDetailDao {
    public IDatabaseHelper iDatabaseHelper;

    public TranDetailDao(IDatabaseHelper iDatabaseHelper) {
        this.iDatabaseHelper = iDatabaseHelper;
    }

    public void insert() {
        try {
            Random random = new Random(10000);
            int i = random.nextInt();
            String s = String.valueOf(i);

            TranDetail tranDetail = new TranDetail();
            tranDetail.setItemSeq(i);
            tranDetail.setSystemDate(new Date());
            tranDetail.setCustId(s);
            tranDetail.setPosNo(i);
            tranDetail.setStoreId(s);
            tranDetail.setTransactionNumber(i);
            Dao<TranDetail, ?> dao = iDatabaseHelper.getDao(TranDetail.class);
            dao.create(tranDetail);
        } catch (SQLException throwables) {
            Log.e("1", "1", throwables);
        }
    }

    public TranDetail query() {
        try {
            Dao<TranDetail, ?> dao = iDatabaseHelper.getDao(TranDetail.class);
            List<TranDetail> tranDetails = dao.queryForAll();
            if (tranDetails!=null && tranDetails.size() != 0) {
                return tranDetails.get(0);
            }
        } catch (SQLException throwables) {
            Log.e("1", "1", throwables);
        }
        return null;
    }
}
