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



    //一些复杂查询用法
//    GenericRawResults<String[]> rawResults = dao.queryRaw(
//            "select transactionNumber, systemDate, zNumber, cashierNo, netSaleTotalAmount, (dealtype1 || dealtype2 || dealtype3)" +
//                    " from tranhead where dealType2 in ('0', '3') and custId = ? and storeId = ?" +
//                    " and posNo = ? and systemDate between ? and ? order by systemDate desc;"
//            , custId, storeId, String.valueOf(posNo), dft.format(beginDate), dft.format(date));
//            return rawResults.getResults();


//--> 操作日志(开班，交班，中间回收[投库]，开钱箱，盘点开始，盘点结束，报废退货，保留，0Q0商品报废 ，*Q0被退报废交易)
//    GenericRawResults<String[]> rawResults = dao.queryRaw(
//            "select transactionNumber, systemDate, zNumber, cashierNo, netSaleTotalAmount, (dealtype1 || dealtype2 || dealtype3), saleAmount" +
//                    " from tranhead where dealType2 in('8', '9', '6', '7', 'G', '2', 'P', 'T', '5', 'Q', 'L', 'M') and custId = ? and storeId = ?" +
//                    " and posNo = ? and systemDate between ? and ? order by systemDate desc",
//            custId, storeId, String.valueOf(posNo), dft.format(beginDate), dft.format(date));
//            return rawResults.getResults();


//    GenericRawResults<String[]> rawResults = dao.queryRaw(
//            "select transactionNumber, (dealtype1 || dealtype2 || dealtype3) as sellway," +
//                    " payNo1, payNo2, payNo3, payNo4, payAmount1, payAmount2, payAmount3, payAmount4, changeAmount, type" +
//                    " from tranhead where shiftNumber = ? and zNumber = ? and" +
//                    " ((dealtype1 || dealtype2 || dealtype3) = '000' or" +
//                    " (dealtype1 || dealtype2 || dealtype3) = '004' or" +
//                    " (dealtype1 || dealtype2 || dealtype3) = '030' or" +
//                    " (dealtype1 || dealtype2 || dealtype3) = '0L0' or" +
//                    " (dealtype1 || dealtype2 || dealtype3) = '0M4' or" +
//                    " (dealtype1 || dealtype2 || dealtype3) = '*L0' or" +
//                    " (dealtype1 || dealtype2 || dealtype3) = '034') order by transactionNumber",
//            String.valueOf(shiftNumber), String.valueOf(zNumber));
//            if (rawResults != null) {
//        return rawResults.getResults();
//    }


}
