package com.example.knowledge_android.daosupport_annotation.beandao.tran;

import android.util.Log;

import com.example.knowledge_android.daosupport_annotation.base.IDatabasePlusHelper;
import com.example.knowledge_android.daosupport_annotation.bean.tran.TranDetailPlus;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;

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



//    GenericRawResults<String[]> rawResults = dao.queryRaw(
//            "select d.detailCode as type, d.pluNo as no, d.pluName as name, sum(d.afterAmount) as anmount, count(*) as count from trandetail d" +
//                    " left join tranhead h on d.transactionNumber = h.transactionNumber" +
//                    " where d.detailCode in ('N', 'T') and d.afterAmount > 0" +
//                    " and h.zNumber = ? group by d.detailCode,d.pluNo,d.pluName" +
//                    " union" +
//                    " select 'C' as type,d.ticketId as no, d.payName as name, sum(d.ticketFaceValue) as amount, count(*) as count from coupondetail d" +
//                    " left join tranhead h on d.transactionNumber = h.transactionNumber" +
//                    " where  (((h.dealtype1 || h.dealtype2 || h.dealtype3) = '000' and h.type != '6') or" +
//                    " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '004' or" +
//                    " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '0L0' or" +
//                    " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '030' or" +
//                    " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '0M4' or" +
//                    " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '*L0' or" +
//                    " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '034') and" +
//                    " d.payId = '003' and h.zNumber = ?" +
//                    " group by d.ticketId,d.payName",
//            String.valueOf(zNumber), String.valueOf(zNumber));
//            if (rawResults != null) {
//        return rawResults.getResults();
//    }


//    GenericRawResults<String[]> rawResults = dao.queryRaw(
//            "select d.pluNo, sum(d.afterAmount) as amount, sum(qty) as count from trandetail d" +
//                    " left join tranhead h on d.transactionNumber = h.transactionNumber and d.posNo = h.posNo" +
//                    " where ((h.dealtype1 ||  h.dealtype2 || h.dealtype3) = '000' or" +
//                    " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '004' or" +
//                    " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '030' or" +
//                    " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '0L0' or" +
//                    " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '0M4' or" +
//                    " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '*L0' or" +
//                    " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '034') and" +
//                    " d.detailCode = 'I' and d.itemVoid = 0 and" +
//                    " h.transactionNumber between ? and ? and h.posNo = ? group by d.pluNo",
//            String.valueOf(beginTransactionNumber), String.valueOf(endTransactionNumber), String.valueOf(posNo));
//            if (rawResults != null) {
//        return rawResults.getResults();
//    }

    // 无法保证别的交易类型是否会填afteramount等字段
//    GenericRawResults<String[]> rawResults = dao.queryRaw("select d.taxType,sum(d.taxAmount),sum(d.afterAmount)" +
//            " from trandetail d" +
//            " left join tranhead h on d.storeId = h.storeid and d.posNo = h.posNo and d.transactionNumber = h.transactionNumber" +
//            " where ((h.dealtype1 || h.dealtype2 || h.dealtype3) = '000'" +//正常交易
//            " or (h.dealtype1 || h.dealtype2 || h.dealtype3) = '004'" +//部分退剩余明细
//            " or (h.dealtype1 || h.dealtype2 || h.dealtype3) = '030'" +//退货原交易
////                " or (h.dealtype1 || h.dealtype2 || h.dealtype3) = '0L0'" +//预约下单交易
////                " or (h.dealtype1 || h.dealtype2 || h.dealtype3) = '0M4'" +//预约退货交易
////                " or (h.dealtype1 || h.dealtype2 || h.dealtype3) = '*L0'" +//预约退货原交易
//            " or (h.dealtype1 || h.dealtype2 || h.dealtype3) = '034')" +//退货交易
//            " and h.transactionNumber between ${beginTransactionNumber} and ${endTransactionNumber} and h.posNo = ${posNo}" +
//            " and d.detailCode not in ('O', 'I') and d.itemVoid != '1'" + //税率统计排除代收代售
//            " group by d.taxtype");
//            return rawResults.getResults();



//    @Override
//    public List<String[]> queryByTranNoForY(int beginTransactionNumber, int endTransactionNumber, int posNo) {
//        try {
//            Dao dao = getDatabaseHelper().getDao(TranHead.class);
//            GenericRawResults<String[]> rawResults = dao.queryRaw(
//                    "select transactionNumber, (dealtype1 || dealtype2 || dealtype3) as sellway," +
//                            " payNo1, payNo2, payNo3, payNo4, payAmount1, payAmount2, payAmount3, payAmount4, changeAmount, type" +
//                            " from tranhead where transactionNumber between ? and ?  and" +
//                            " ((dealtype1 || dealtype2 || dealtype3) = '000' or" +
//                            " (dealtype1 || dealtype2 || dealtype3) = '004' or" +
//                            " (dealtype1 || dealtype2 || dealtype3) = '030' or" +
//                            " (dealtype1 || dealtype2 || dealtype3) = '0L0' or" +
//                            " (dealtype1 || dealtype2 || dealtype3) = '0M4' or" +
//                            " (dealtype1 || dealtype2 || dealtype3) = '*L0' or" +
//                            " (dealtype1 || dealtype2 || dealtype3) = '034') and posNo = ? order by transactionNumber",
//                    String.valueOf(beginTransactionNumber), String.valueOf(endTransactionNumber), String.valueOf(posNo));
//            if (rawResults != null) {
//                return rawResults.getResults();
//            }
//        } catch (Exception e) {
//            log.error("queryByTranNoForY", e);
//        }
//        return null;
//    }

}
