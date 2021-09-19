package com.example.knowledge_android.daosupport_annotation.beandao.master;

import android.util.Log;

import com.example.knowledge_android.daosupport_annotation.base.IDatabasePlusHelper;
import com.example.knowledge_android.daosupport_annotation.bean.master.PluPlus;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;

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


//    GenericRawResults<String[]> rawResults = dao.queryRaw(
//            "select d.detailCode as type, d.pluNo as no, d.pluName as name, sum(d.afterAmount) as anmount, count(*) as count from trandetail d" +
//                    " left join tranhead h on d.transactionNumber = h.transactionNumber" +
//                    " where d.detailCode in ('N', 'T') and d.afterAmount > 0" +
//                    " and h.shiftNumber = ? and h.zNumber = ? group by d.detailCode,d.pluNo,d.pluName" +
//                    " union" +
//                    " select 'C' as type,d.ticketId as no, d.payName as name, sum(d.ticketFaceValue) as amount, count(*) as count from coupondetail d" +
//                    " left join tranhead h on d.transactionNumber = h.transactionNumber" +
//                    " where  (((h.dealtype1 || h.dealtype2 || h.dealtype3) = '000' and h.type != '6') or" +
//                    " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '004' or" +
//                    " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '030' or" +
//                    " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '0L0' or" +
//                    " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '0M4' or" +
//                    " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '*L0' or" +
//                    " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '034') and" +
//                    " d.payId = '003' and h.shiftNumber = ? and h.zNumber = ?" +
//                    " group by d.ticketId,d.payName" +
//                    " ",
//            String.valueOf(shiftNumber), String.valueOf(zNumber), String.valueOf(shiftNumber), String.valueOf(zNumber));
//            if (rawResults != null) {
//        return rawResults.getResults();
//    }



//    GenericRawResults<String[]> rawResults = dao.queryRaw(
//            "select transactionNumber, (dealtype1 || dealtype2 || dealtype3) as sellway," +
//                    " payNo1, payNo2, payNo3, payNo4, payAmount1, payAmount2, payAmount3, payAmount4, changeAmount, type" +
//                    " from tranhead where zNumber = ? and" +
//                    " ((dealtype1 || dealtype2 || dealtype3) = '000' or" +
//                    " (dealtype1 || dealtype2 || dealtype3) = '004' or" +
//                    " (dealtype1 || dealtype2 || dealtype3) = '030' or" +
//                    " (dealtype1 || dealtype2 || dealtype3) = '0L0' or" +
//                    " (dealtype1 || dealtype2 || dealtype3) = '0M4' or" +
//                    " (dealtype1 || dealtype2 || dealtype3) = '*L0' or" +
//                    " (dealtype1 || dealtype2 || dealtype3) = '034') order by transactionNumber",
//            String.valueOf(zNumber));
//            if (rawResults != null) {
//        return rawResults.getResults();
//    }


//    GenericRawResults<String[]> rawResults = dao.queryRaw(
//            "select transactionNumber, (dealtype1 || dealtype2 || dealtype3) as sellway," +
//                    " payNo1, payNo2, payNo3, payNo4, payAmount1, payAmount2, payAmount3, payAmount4, changeAmount," +
//                    " grossSaleAmount, saleAmount, netSaleTotalAmount, mmDiscountAmount, overAmount, taxAmount," +
//                    " roundDownAmount, daiShouAmount, daiShouAmount2, cashierNo, systemDate, type" +
//                    " from tranhead where zNumber = ? order by transactionNumber",
//            String.valueOf(zNumber));
//            if (rawResults != null) {
//        return rawResults.getResults();
//    }

}
