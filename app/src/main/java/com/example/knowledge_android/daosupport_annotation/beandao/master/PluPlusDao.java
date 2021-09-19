package com.example.knowledge_android.daosupport_annotation.beandao.master;

import android.util.Log;

import com.example.knowledge_android.daosupport_annotation.base.IDatabasePlusHelper;
import com.example.knowledge_android.daosupport_annotation.bean.master.PluPlus;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class PluPlusDao {

    public IDatabasePlusHelper iDatabaseHelper;

    public PluPlusDao(IDatabasePlusHelper iDatabaseHelper) {
        this.iDatabaseHelper = iDatabaseHelper;
    }

    /**
     * 数据库版本
     *
     * @return
     */
    private PluPlus queryByAnyNumberByDB(String pluNo) {
        try {
            Dao<PluPlus, ?> dao = iDatabaseHelper.getDao(PluPlus.class);
            List<PluPlus> pluPluses = dao.queryForEq("pluNo", pluNo);
            if (pluPluses != null && pluPluses.size() > 0) {
                return pluPluses.get(0);
            }
            return null;
        } catch (Exception e) {
            Log.i("1", "1", e);
        }
        return null;
    }

    public List<PluPlus> queryAll(){
        List<PluPlus> pluPluses = null;
        try {
            Dao<PluPlus, ?> dao2 = iDatabaseHelper.getDao(PluPlus.class);
            pluPluses = dao2.queryForAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return pluPluses;
    }
}
