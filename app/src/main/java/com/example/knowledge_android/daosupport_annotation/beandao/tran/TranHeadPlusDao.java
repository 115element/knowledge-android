package com.example.knowledge_android.daosupport_annotation.beandao.tran;

import android.util.Log;

import com.example.knowledge_android.daosupport_annotation.base.IDatabasePlusHelper;
import com.example.knowledge_android.daosupport_annotation.bean.tran.TranDetailPlus;
import com.example.knowledge_android.daosupport_annotation.bean.tran.TranHeadPlus;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;

import java.sql.SQLException;
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
}
