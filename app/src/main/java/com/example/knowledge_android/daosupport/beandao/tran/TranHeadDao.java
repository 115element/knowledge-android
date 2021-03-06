package com.example.knowledge_android.daosupport.beandao.tran;

import android.util.Log;

import com.example.knowledge_android.daosupport.base.IDatabaseHelper;
import com.example.knowledge_android.daosupport.bean.tran.TranDetail;
import com.example.knowledge_android.daosupport.bean.tran.TranHead;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class TranHeadDao {

    public IDatabaseHelper iDatabaseHelper;

    public TranHeadDao(IDatabaseHelper iDatabaseHelper) {
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
                Dao<TranHead, ?> tranHeadDao = iDatabaseHelper.getDao(TranHead.class);
                Dao<TranDetail, ?> tranDetailDao = iDatabaseHelper.getDao(TranDetail.class);

                Random random = new Random(10000);
                int i = random.nextInt();
                String s = String.valueOf(i);

                TranHead tranHead = new TranHead();
                tranHead.setCustId(s);
                tranHead.setStoreId(s);
                tranHead.setDealType1(s);
                tranHead.setTransactionNumber(i);
                tranHead.setPosNo(i);
                int i1 = tranHeadDao.create(tranHead);
                TranDetail tranDetail = new TranDetail();
                tranDetail.setItemSeq(i);
                tranDetail.setSystemDate(new Date());
                tranDetail.setCustId(s);
                tranDetail.setPosNo(i);
                tranDetail.setStoreId(s);
                tranDetail.setTransactionNumber(i);
                int i2 = tranDetailDao.create(tranDetail);

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

            TranHead tranHead = new TranHead();
            tranHead.setCustId(s);
            tranHead.setStoreId(s);
            tranHead.setDealType1(s);
            tranHead.setTransactionNumber(i);
            tranHead.setPosNo(i);
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
            if (tranHeads != null && tranHeads.size() != 0) {
                return tranHeads.get(0);
            }
        } catch (SQLException throwables) {
            Log.e("1", "1", throwables);
        }
        return null;
    }
}
