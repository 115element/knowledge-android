package com.example.knowledge_android.daosupport_annotation.beandao.tran;

import android.util.Log;

import com.example.knowledge_android.daosupport_annotation.base.IDatabasePlusHelper;
import com.example.knowledge_android.daosupport_annotation.bean.tran.TranDetailPlus;
import com.example.knowledge_android.daosupport_annotation.bean.tran.TranHeadPlus;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.misc.TransactionManager;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class TranHeadPlusDao {

    public IDatabasePlusHelper iDatabaseHelper;

    public TranHeadPlusDao(IDatabasePlusHelper iDatabaseHelper) {
        this.iDatabaseHelper = iDatabaseHelper;
    }

    // Dao.callBatchTasks()和TransactionManager.callInTransaction()之间的Dao.callBatchTasks()
    // 差异取决于您使用的数据库。 在Android下，没有区别。 callBatchTasks(...)的javadoc说：

    /**
     * 事件回滚大大概原理
     * 在开始操作数据库的开始前 首先保存一个SavePoint万一需要回滚就回到这个点
     * DatabaseConnection connection = connectionSource.getReadWriteConnection()；//数据库连接
     * SavePoint  savePoint = connection.setSavePoint("ORMLITE" + savePointCounter.incrementAndGet());//保存一个savePoint
     * <p>
     * 1、出现异常就进行回滚，回滚到执行事务前的状态savePoint   rollBack(connection, savePoint);
     * 2、未出现异常提交事务，只有这句执行完了之后，你所做的操作才会在数据库中生效 commit(connection, savePoint);
     * <p>
     * 关键点 transactionManager.callInTransaction(callable);中callable的call方法如果向上抛出异常  就会被认为事件失败
     * 会触发回滚，如果里面的方法try catch 掉了异常那么 事件管理就认为没有失败，所以不要在call里面将异常给处理掉
     * 要抛上去
     * <p>
     * 当然也可以这样写
     * //事务操作
     * TransactionManager.callInTransaction(helper.getConnectionSource(),
     * new Callable<Boolean>()
     * {
     *
     * @Override public Boolean call() throws Exception
     * {
     * *****写数据库操作方法****
     * return false;
     * }
     * });
     * 这里的泛型可以是void  但还是为了获得是否保存成功的结果就使用了 Boolean
     */
    public void testTransaction() throws SQLException {
        TransactionManager.callInTransaction(iDatabaseHelper.getTranConnection(), new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Dao<TranHeadPlus, ?> tranHeadDao = iDatabaseHelper.getDao(TranHeadPlus.class);
                Dao<TranDetailPlus, ?> tranDetailDao = iDatabaseHelper.getDao(TranDetailPlus.class);

                Random random = new Random(10000);
                int i = random.nextInt();
                String s = String.valueOf(i);

                TranHeadPlus tranHeadPlus = new TranHeadPlus();
                tranHeadPlus.setCustId(s);
                tranHeadPlus.setStoreId(s);
                tranHeadPlus.setTransactionNumber(i);
                tranHeadPlus.setPosNo(i);
                int i1 = tranHeadDao.create(tranHeadPlus);

                TranDetailPlus tranDetailPlus = new TranDetailPlus();
                tranDetailPlus.setSystemDate(new Date());
                tranDetailPlus.setCustId(s);
                tranDetailPlus.setPosNo(i);
                tranDetailPlus.setStoreId(s);
                tranDetailPlus.setTransactionNumber(i);
                int i2 = tranDetailDao.create(tranDetailPlus);

                //throw new RuntimeException("事务抛出异常");

                Log.i("Tran", "" + i2);
                return Boolean.TRUE;
            }
        });
    }


    public void insert() {
        try {
            Random random = new Random(10000);
            int i = random.nextInt();
            String s = String.valueOf(i);

            TranHeadPlus tranHeadPlus = new TranHeadPlus();
            tranHeadPlus.setCustId(s);
            tranHeadPlus.setStoreId(s);
            tranHeadPlus.setTransactionNumber(i);
            tranHeadPlus.setPosNo(i);
            Dao<TranHeadPlus, ?> dao = iDatabaseHelper.getDao(TranHeadPlus.class);
            dao.create(tranHeadPlus);
        } catch (SQLException throwables) {
            Log.e("1", "两个表事务成功提交", throwables);
        }
    }

    public TranHeadPlus query() {
        try {
            Dao<TranHeadPlus, ?> dao = iDatabaseHelper.getDao(TranHeadPlus.class);
            List<TranHeadPlus> tranHeads = dao.queryForAll();
            if (tranHeads != null && tranHeads.size() != 0) {
                return tranHeads.get(0);
            }
        } catch (SQLException throwables) {
            Log.e("1", "1", throwables);
        }
        return null;
    }




//    @Override
//    public List<String[]> queryTradingFlow(String custId, String storeId, int posNo, Date date1, Date date2) {
//        try {
//            Dao dao = getDatabaseHelper().getDao(TranHead.class);
//            SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date date = new Date();
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(date);
//            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
//            Date beginDate = dft.parse(dft.format(calendar.getTime()));
//            if (date1 != null && date2 != null) {
//                beginDate = date1;
//                date = date2;
//            }
//            //--> 交易流水包括,（000正常销售,*00作废交易,030被退交易,004退货销售,034全部退货,003交易取消）
//            GenericRawResults<String[]> rawResults = dao.queryRaw(
//                    "select transactionNumber, systemDate, zNumber, cashierNo, netSaleTotalAmount, (dealtype1 || dealtype2 || dealtype3)" +
//                            " from tranhead where dealType2 in ('0', '3') and custId = ? and storeId = ?" +
//                            " and posNo = ? and systemDate between ? and ? order by systemDate desc;"
//                    , custId, storeId, String.valueOf(posNo), dft.format(beginDate), dft.format(date));
//            return rawResults.getResults();
//        } catch (Exception e) {
//            log.error("queryTradingFlow", e);
//            return null;
//        }
//    }
//
//    /**
//     * 查询操作日志（日志查询）
//     *
//     * @param storeId
//     * @param posNo
//     * @param date1
//     * @param date2
//     * @return
//     */
//    @Override
//    public List<String[]> queryOperationLog(String custId, String storeId, int posNo, Date date1, Date date2) {
//        try {
//            Dao dao = getDatabaseHelper().getDao(TranHead.class);
//            SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date date = new Date();
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(date);
//            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
//            Date beginDate = dft.parse(dft.format(calendar.getTime()));
//            if (date1 != null && date2 != null) {
//                beginDate = date1;
//                date = date2;
//            }
//            //--> 操作日志(开班，交班，中间回收[投库]，开钱箱，盘点开始，盘点结束，报废退货，保留，0Q0商品报废 ，*Q0被退报废交易)
//            GenericRawResults<String[]> rawResults = dao.queryRaw(
//                    "select transactionNumber, systemDate, zNumber, cashierNo, netSaleTotalAmount, (dealtype1 || dealtype2 || dealtype3), saleAmount" +
//                            " from tranhead where dealType2 in('8', '9', '6', '7', 'G', '2', 'P', 'T', '5', 'Q', 'L', 'M') and custId = ? and storeId = ?" +
//                            " and posNo = ? and systemDate between ? and ? order by systemDate desc",
//                    custId, storeId, String.valueOf(posNo), dft.format(beginDate), dft.format(date));
//            return rawResults.getResults();
//        } catch (Exception e) {
//            log.error("", e);
//            return null;
//        }
//    }
//
//    @Override
//    public List<String[]> queryTransByShiftNumberAndZNumber(int shiftNumber, int zNumber) {
//        try {
//            Dao dao = getDatabaseHelper().getDao(TranHead.class);
//            GenericRawResults<String[]> rawResults = dao.queryRaw(
//                    "select transactionNumber, (dealtype1 || dealtype2 || dealtype3) as sellway," +
//                            " payNo1, payNo2, payNo3, payNo4, payAmount1, payAmount2, payAmount3, payAmount4, changeAmount, type" +
//                            " from tranhead where shiftNumber = ? and zNumber = ? and" +
//                            " ((dealtype1 || dealtype2 || dealtype3) = '000' or" +
//                            " (dealtype1 || dealtype2 || dealtype3) = '004' or" +
//                            " (dealtype1 || dealtype2 || dealtype3) = '030' or" +
//                            " (dealtype1 || dealtype2 || dealtype3) = '0L0' or" +
//                            " (dealtype1 || dealtype2 || dealtype3) = '0M4' or" +
//                            " (dealtype1 || dealtype2 || dealtype3) = '*L0' or" +
//                            " (dealtype1 || dealtype2 || dealtype3) = '034') order by transactionNumber",
//                    String.valueOf(shiftNumber), String.valueOf(zNumber));
//            if (rawResults != null) {
//                return rawResults.getResults();
//            }
//        } catch (Exception e) {
//            log.error("queryTransByShiftNumberAndZNumber", e);
//        }
//        return null;
//    }
//
//    @Override
//    public List<String[]> queryExpenseOrCouponByShiftNumberAndZNumber(int shiftNumber, int zNumber) {
//        try {
//            Dao dao = getDatabaseHelper().getDao(TranHead.class);
//            GenericRawResults<String[]> rawResults = dao.queryRaw(
//                    "select d.detailCode as type, d.pluNo as no, d.pluName as name, sum(d.afterAmount) as anmount, count(*) as count from trandetail d" +
//                            " left join tranhead h on d.transactionNumber = h.transactionNumber" +
//                            " where d.detailCode in ('N', 'T') and d.afterAmount > 0" +
//                            " and h.shiftNumber = ? and h.zNumber = ? group by d.detailCode,d.pluNo,d.pluName" +
//                            " union" +
//                            " select 'C' as type,d.ticketId as no, d.payName as name, sum(d.ticketFaceValue) as amount, count(*) as count from coupondetail d" +
//                            " left join tranhead h on d.transactionNumber = h.transactionNumber" +
//                            " where  (((h.dealtype1 || h.dealtype2 || h.dealtype3) = '000' and h.type != '6') or" +
//                            " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '004' or" +
//                            " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '030' or" +
//                            " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '0L0' or" +
//                            " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '0M4' or" +
//                            " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '*L0' or" +
//                            " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '034') and" +
//                            " d.payId = '003' and h.shiftNumber = ? and h.zNumber = ?" +
//                            " group by d.ticketId,d.payName" +
//                            " ",
//                    String.valueOf(shiftNumber), String.valueOf(zNumber), String.valueOf(shiftNumber), String.valueOf(zNumber));
//            if (rawResults != null) {
//                return rawResults.getResults();
//            }
//        } catch (Exception e) {
//            log.error("queryExpenseByShiftNumberAndZNumber", e);
//        }
//        return null;
//    }
//
//    @Override
//    public List<String[]> queryTransByZNumber(int zNumber) {
//        try {
//            Dao dao = getDatabaseHelper().getDao(TranHead.class);
//            GenericRawResults<String[]> rawResults = dao.queryRaw(
//                    "select transactionNumber, (dealtype1 || dealtype2 || dealtype3) as sellway," +
//                            " payNo1, payNo2, payNo3, payNo4, payAmount1, payAmount2, payAmount3, payAmount4, changeAmount, type" +
//                            " from tranhead where zNumber = ? and" +
//                            " ((dealtype1 || dealtype2 || dealtype3) = '000' or" +
//                            " (dealtype1 || dealtype2 || dealtype3) = '004' or" +
//                            " (dealtype1 || dealtype2 || dealtype3) = '030' or" +
//                            " (dealtype1 || dealtype2 || dealtype3) = '0L0' or" +
//                            " (dealtype1 || dealtype2 || dealtype3) = '0M4' or" +
//                            " (dealtype1 || dealtype2 || dealtype3) = '*L0' or" +
//                            " (dealtype1 || dealtype2 || dealtype3) = '034') order by transactionNumber",
//                    String.valueOf(zNumber));
//            if (rawResults != null) {
//                return rawResults.getResults();
//            }
//        } catch (Exception e) {
//            log.error("queryTransByZNumber", e);
//        }
//        return null;
//    }
//
//    @Override
//    public List<String[]> queryTransByZNumberForY(int zNumber) {
//        try {
//            Dao dao = getDatabaseHelper().getDao(TranHead.class);
//            GenericRawResults<String[]> rawResults = dao.queryRaw(
//                    "select transactionNumber, (dealtype1 || dealtype2 || dealtype3) as sellway," +
//                            " payNo1, payNo2, payNo3, payNo4, payAmount1, payAmount2, payAmount3, payAmount4, changeAmount," +
//                            " grossSaleAmount, saleAmount, netSaleTotalAmount, mmDiscountAmount, overAmount, taxAmount," +
//                            " roundDownAmount, daiShouAmount, daiShouAmount2, cashierNo, systemDate, type" +
//                            " from tranhead where zNumber = ? order by transactionNumber",
//                    String.valueOf(zNumber));
//            if (rawResults != null) {
//                return rawResults.getResults();
//            }
//        } catch (Exception e) {
//            log.error("queryTransByZNumberForY", e);
//        }
//        return null;
//    }
//
//    @Override
//    public List<String[]> queryExpenseOrCouponByZNumber(int zNumber) {
//        try {
//            Dao dao = getDatabaseHelper().getDao(TranHead.class);
//            GenericRawResults<String[]> rawResults = dao.queryRaw(
//                    "select d.detailCode as type, d.pluNo as no, d.pluName as name, sum(d.afterAmount) as anmount, count(*) as count from trandetail d" +
//                            " left join tranhead h on d.transactionNumber = h.transactionNumber" +
//                            " where d.detailCode in ('N', 'T') and d.afterAmount > 0" +
//                            " and h.zNumber = ? group by d.detailCode,d.pluNo,d.pluName" +
//                            " union" +
//                            " select 'C' as type,d.ticketId as no, d.payName as name, sum(d.ticketFaceValue) as amount, count(*) as count from coupondetail d" +
//                            " left join tranhead h on d.transactionNumber = h.transactionNumber" +
//                            " where  (((h.dealtype1 || h.dealtype2 || h.dealtype3) = '000' and h.type != '6') or" +
//                            " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '004' or" +
//                            " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '0L0' or" +
//                            " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '030' or" +
//                            " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '0M4' or" +
//                            " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '*L0' or" +
//                            " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '034') and" +
//                            " d.payId = '003' and h.zNumber = ?" +
//                            " group by d.ticketId,d.payName",
//                    String.valueOf(zNumber), String.valueOf(zNumber));
//            if (rawResults != null) {
//                return rawResults.getResults();
//            }
//        } catch (Exception e) {
//            log.error("queryExpenseOrCouponByZNumber", e);
//        }
//        return null;
//    }
//
//    @Override
//    public List<String[]> queryDaiShowByZNumberForY(int beginTransactionNumber, int endTransactionNumber, int posNo) {
//        try {
//            Dao dao = getDatabaseHelper().getDao(TranHead.class);
//            GenericRawResults<String[]> rawResults = dao.queryRaw(
//                    "select d.pluNo, sum(d.afterAmount) as amount, sum(qty) as count from trandetail d" +
//                            " left join tranhead h on d.transactionNumber = h.transactionNumber and d.posNo = h.posNo" +
//                            " where ((h.dealtype1 ||  h.dealtype2 || h.dealtype3) = '000' or" +
//                            " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '004' or" +
//                            " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '030' or" +
//                            " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '0L0' or" +
//                            " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '0M4' or" +
//                            " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '*L0' or" +
//                            " (h.dealtype1 || h.dealtype2 || h.dealtype3) = '034') and" +
//                            " d.detailCode = 'I' and d.itemVoid = 0 and" +
//                            " h.transactionNumber between ? and ? and h.posNo = ? group by d.pluNo",
//                    String.valueOf(beginTransactionNumber), String.valueOf(endTransactionNumber), String.valueOf(posNo));
//            if (rawResults != null) {
//                return rawResults.getResults();
//            }
//        } catch (Exception e) {
//            log.error("queryDaiShowByZNumber", e);
//        }
//        return null;
//    }
//
//    @Override
//    public List<String[]> queryDetailTaxTypeByTranNo(int beginTransactionNumber, int endTransactionNumber, int posNo) {
//        try {
//            Dao dao = getDatabaseHelper().getDao(TranDetail.class);
//            // 无法保证别的交易类型是否会填afteramount等字段
//            GenericRawResults<String[]> rawResults = dao.queryRaw("select d.taxType,sum(d.taxAmount),sum(d.afterAmount)" +
//                    " from trandetail d" +
//                    " left join tranhead h on d.storeId = h.storeid and d.posNo = h.posNo and d.transactionNumber = h.transactionNumber" +
//                    " where ((h.dealtype1 || h.dealtype2 || h.dealtype3) = '000'" +//正常交易
//                    " or (h.dealtype1 || h.dealtype2 || h.dealtype3) = '004'" +//部分退剩余明细
//                    " or (h.dealtype1 || h.dealtype2 || h.dealtype3) = '030'" +//退货原交易
////                " or (h.dealtype1 || h.dealtype2 || h.dealtype3) = '0L0'" +//预约下单交易
////                " or (h.dealtype1 || h.dealtype2 || h.dealtype3) = '0M4'" +//预约退货交易
////                " or (h.dealtype1 || h.dealtype2 || h.dealtype3) = '*L0'" +//预约退货原交易
//                    " or (h.dealtype1 || h.dealtype2 || h.dealtype3) = '034')" +//退货交易
//                    " and h.transactionNumber between ${beginTransactionNumber} and ${endTransactionNumber} and h.posNo = ${posNo}" +
//                    " and d.detailCode not in ('O', 'I') and d.itemVoid != '1'" + //税率统计排除代收代售
//                    " group by d.taxtype");
//            return rawResults.getResults();
//        } catch (Exception e) {
//            log.error("", e);
//        }
//        return Collections.EMPTY_LIST;
//    }






}
