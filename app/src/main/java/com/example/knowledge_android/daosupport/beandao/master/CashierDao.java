package com.example.knowledge_android.daosupport.beandao.master;

import com.example.knowledge_android.daosupport.base.IDatabaseHelper;
import com.example.knowledge_android.daosupport.bean.master.CashierDac;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;

import java.sql.SQLException;
import java.util.List;

public class CashierDao {

    public IDatabaseHelper iDatabaseHelper;

    public CashierDao(IDatabaseHelper iDatabaseHelper) {
        this.iDatabaseHelper = iDatabaseHelper;
    }

    /**
     * 根据收银员编号查询出收银员
     * @param cashierNo
     * @return
     */
    public CashierDac queryByCashierNo(String cashierNo) throws SQLException {
        Dao<CashierDac, ?> dao = iDatabaseHelper.getDao(CashierDac.class);
        GenericRawResults<CashierDac> cashierDacs = dao.queryRaw("select c.* from cashier c where c.cashierNo = ?",
                dao.getRawRowMapper(), cashierNo);
        List<CashierDac> results = cashierDacs.getResults();
        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;

//        dao.queryBuilder().where().eq('zNumber', zNumber).and()
//                .ge('endTransactionNumber', transactionNumber).queryForFirst()
    }

    public List<CashierDac> queryAll() {
        List<CashierDac> cashierDacs = null;
        try {
            Dao<CashierDac, ?> dao = iDatabaseHelper.getDao(CashierDac.class);
            cashierDacs = dao.queryForAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cashierDacs;
    }

}
