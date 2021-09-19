package com.example.knowledge_android.daosupport.beandao.master;

import android.util.Log;

import com.example.knowledge_android.daosupport.base.IDatabaseHelper;
import com.example.knowledge_android.daosupport.bean.master.PluDac;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class PluDao {

    public IDatabaseHelper iDatabaseHelper;

    public PluDao(IDatabaseHelper iDatabaseHelper) {
        this.iDatabaseHelper = iDatabaseHelper;
    }


    /**
     * 数据库版本
     *
     * @return
     */
    private PluDac queryByAnyNumberByDB(String pluNo) {
        try {
            Dao<PluDac, ?> dao = iDatabaseHelper.getDao(PluDac.class);
            List<PluDac> pluNo1 = dao.queryForEq("pluNo", pluNo);
            if (pluNo1 != null && pluNo1.size() > 0) {
                return pluNo1.get(0);
            }
            return null;
        } catch (Exception e) {
            Log.i("1", "1", e);
        }
        return null;
    }

    public List<PluDac> queryAll(){
        List<PluDac> pluDacs = null;
        try {
            Dao<PluDac, ?> dao2 = iDatabaseHelper.getDao(PluDac.class);
            pluDacs = dao2.queryForAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return pluDacs;
    }
}
