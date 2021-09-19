package com.example.knowledge_android.daosupport_annotation.beandao.master;

import com.example.knowledge_android.daosupport_annotation.base.IDatabasePlusHelper;
import com.example.knowledge_android.daosupport_annotation.bean.master.CashierPlus;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;

import java.sql.SQLException;
import java.util.List;

public class CashierPlusDao {

    public IDatabasePlusHelper iDatabaseHelper;

    public CashierPlusDao(IDatabasePlusHelper iDatabaseHelper) {
        this.iDatabaseHelper = iDatabaseHelper;
    }

    /**
     * 根据收银员编号查询出收银员
     * @param cashierNo
     * @return
     */
    public CashierPlus queryByCashierNo(String cashierNo) throws SQLException {
        Dao<CashierPlus, ?> dao = iDatabaseHelper.getDao(CashierPlus.class);
        GenericRawResults<CashierPlus> cashierPluses = dao.queryRaw("select c.* from cashier_plus c where c.cashierNo = ?",
                dao.getRawRowMapper(), cashierNo);
        List<CashierPlus> results = cashierPluses.getResults();
        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;

//        dao.queryBuilder().where().eq('zNumber', zNumber).and()
//                .ge('endTransactionNumber', transactionNumber).queryForFirst()
    }

    public List<CashierPlus> queryAll() {
        List<CashierPlus> cashierDacs = null;
        try {
            Dao<CashierPlus, ?> dao = iDatabaseHelper.getDao(CashierPlus.class);
            cashierDacs = dao.queryForAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cashierDacs;
    }

}
