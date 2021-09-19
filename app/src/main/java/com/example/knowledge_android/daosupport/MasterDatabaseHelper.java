package com.example.knowledge_android.daosupport;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.knowledge_android.daosupport.bean.master.CashierDac;
import com.example.knowledge_android.daosupport.bean.master.PluDac;
import com.example.knowledge_android.daosupport.beanconfig.TableList;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Database helper for master.
 * <p/>
 * Sample code:
 * <pre>
 * MasterDatabaseHelper dbHelp = new MasterDatabaseHelper(getApplicationContext())
 * dbHelp.downloadDatabase()
 * SQLiteDatabase db = dbHelp.getWritableDatabase()
 * Cursor cr = db.query("tab_store", new String[]{"store_id"}, null, null, null, null, null)
 * cr.moveToFirst()
 * Log.d(TAG, "store_id=" + cr.getString(0))
 * cr.close()
 * db.close()
 * </pre>
 *
 * @author Bruce You
 */

// masterDatabaseHelper = OpenHelperManager.getHelper(application, MasterDatabaseHelper.class); 这一句代码触发这个类

public class MasterDatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = "MasterDatabaseHelper";
    private static final String DATABASE_NAME = "master";
    private static final int DATABASE_VERSION = 2019021802;

    public MasterDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            for (Class clazz : TableList.getMasterTables()) {
                TableUtils.createTableIfNotExists(connectionSource, TableList.getTableConfigMap().get(clazz));
            }
            Log.i(TAG, "Master tables were created.");
        } catch (Exception e) {
            Log.e(TAG, "Cannot create database master.", e);
        }
        try {
            Dao dao = DaoManager.createDao(connectionSource, TableList.getTableConfigMap().get(CashierDac.class));
            List list = dao.queryForAll();
            if (list == null || list.size() == 0) {
                CashierDac cashierDac = new CashierDac();
                cashierDac.setCashierNo("1111");
                cashierDac.setVersion(new Date());
                cashierDac.setCustId("222");
                cashierDac.setStoreId("666");
                dao.create(cashierDac);
                Log.i(TAG, "CashierDac表数据插入成功");
            }
        } catch (Exception e) {
            Log.e(TAG, "CashierDac表数据插入失败", e);
        }

        try {
            Dao dao = DaoManager.createDao(connectionSource, TableList.getTableConfigMap().get(PluDac.class));
            List list = dao.queryForAll();
            if (list == null || list.size() == 0) {
                PluDac pluDac = new PluDac();
                pluDac.setCustId("fff");
                pluDac.setStoreId("ggg");
                pluDac.setPluNo("11111");
                pluDac.setPluName("ffff");
                pluDac.setPluPrice(BigDecimal.ONE);
                pluDac.setBeginDate(new Date());
                pluDac.setSiGroup(23);
                pluDac.setPromotionPrice(1);
                dao.create(pluDac);
                Log.i(TAG, "PluDac表数据插入成功");
            }
        } catch (Exception e) {
            Log.e(TAG, "PluDac表数据插入失败", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {

        upgrade2019021801(connectionSource, oldVersion);

    }

    void upgrade2019021801(ConnectionSource conn, int oldVersion) {
        if (oldVersion < 2019021801) {
            Log.i(TAG, "执行数据库版本更新，例如更新表结构操作，放在这里。。");
//            try {
//                def dao = DaoManager.createDao(conn, hyi.cream.busi.dao.lawson.TableList.tableConfigMap[Plu])
//                log.info 'upgrade2019021801'
////                dao.executeRaw("alter table plu add verificationMode varchar(1)")
//                updatePosDBVersion(conn, oldVersion as String)
//            } catch (Exception ex) {
//                log.error('', ex)
//            }
        }
    }

    void updatePosDBVersion(ConnectionSource conn, String version) {
//        def dao = DaoManager.createDao(conn, hyi.cream.busi.dao.lawson.TableList.tableConfigMap[Property])
//        dao.createOrUpdate(new Property(name: 'PosMasterDBVersion', value: version))
    }

    public void loadDefaults() {
        Log.i(TAG, "loadDefaults啥也没做。。");
    }

//    private static final String DEFULT_MENU_ID = '1,2,3,11'
//    private static final String MENU_ROLE_TYPE = '10'
//    private static final String CATEGORY_ROLE_TYPE = '20'
//    private static final String COLUMN_VALUE_SEPARATOR = '¦' // Pipe, Broken vertical bar
//    private Context mContext

//    static String getTableName(Class entityClass) {
//        try {
//            Annotation annotation = entityClass.getAnnotation(DatabaseTable.class)
//            if (annotation != null && annotation instanceof DatabaseTable)
//                return ((DatabaseTable) annotation).tableName()
//        } catch (Exception e) {
//            e.printStackTrace()
//        }
//        return ""
//    }
//

//    /**
//     * Download master.db from server and overwrite the database file.
//     */
//    public boolean downloadDatabase() {
//        Log.d(TAG, "downloadDatabase()")
//
//        // Create database if not exist.
//        try {
//            getReadableDatabase()
//            close()
//        } catch (SQLiteException e) {
//            Log.e(TAG, "Cannot open master.db.", e)
//        }
//
//        // Thus it's able to transfer data through network in main thread
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build()
//        StrictMode.setThreadPolicy(policy)
//
//        // Download master.db
//        int retryCount = 0
//        while (++retryCount <= 3) {
//            TabletClient inlineClient = TabletClient.getInstance()
//            inlineClient.setContext(mContext)
//            boolean result = inlineClient.getFile("db/master.db")
//            if (result) {
//                File downloadedMasterDbFile = new File(mContext.getFilesDir(), "master.db")
//                File masterDatabaseFile = mContext.getDatabasePath(DATABASE_NAME)
//                if (!masterDatabaseFile.delete()) {
//                    Log.d(TAG, "Cannot delete database file:" + DATABASE_NAME)
//                    return false
//                }
//                if (!downloadedMasterDbFile.renameTo(masterDatabaseFile)) {
//                    Log.d(TAG, "Cannot overwrite database file:" + DATABASE_NAME)
//                    return false
//                }
//                return true
//            }
//            SystemClock.sleep(1500)
//        }
//        return false
//    }

//    /**
//     * 根據員工號查詢員工的菜單權限
//     */
//    public List<Transaction> queryUserMenu() throws Exception {
//        OrderingApplication mApp = (OrderingApplication) mContext.getApplicationContext()
//        String userId = mApp.getEmployee().user_id
//        List<Menu> menus = new ArrayList()
//
//        Dao<Menu, Integer> dao = getDao(Menu.class)
//        GenericRawResults<String[]> rawResults = dao.queryRaw("select * from tab_menu m where menu_id in (\n" +
//                "\tselect menu_id from tab_menu where tab_menu.menu_id in ("+DEFULT_MENU_ID+") \n" +
//                "\tunion \n" +
//                "\tselect rmr.menu_id from \n" +
//                "\t\t  TAB_EMPLOYEE emp, tab_group_user_relation gur, tab_user_group ug, tab_group_role_relation grr,\n" +
//                "\t\t  tab_role r, tab_role_menu_relation rmr\n" +
//                "\t\twhere emp.user_id = gur.user_id and gur.user_group_id = ug.user_group_id \n" +
//                "\t\t  and ug.user_group_id = grr.user_group_id and grr.role_id = r.role_id\n" +
//                "\t\t  and r.role_type = '"+MENU_ROLE_TYPE+"' and r.role_id = rmr.role_id and emp.user_id = '"+userId+"'\n" +
//                "\t\t  union\n" +
//                "\t\t  select rmr.menu_id from \n" +
//                "\t\t  TAB_EMPLOYEE emp, tab_user_role_relation urr, \n" +
//                "\t\t  tab_role r, tab_role_menu_relation rmr\n" +
//                "\t\twhere emp.user_id = urr.user_id \n" +
//                "\t\t  and urr.role_id = r.role_id\n" +
//                "\t\t  and r.role_type = '"+MENU_ROLE_TYPE+"' and r.role_id = rmr.role_id and emp.user_id = '"+userId+"'\n" +
//                "union\n" +
//                "select tab_menu.menu_id from tab_menu where tab_menu.menu_level in (\n" +
//                "\tselect rmr.menu_id from \n" +
//                "\t\t  TAB_EMPLOYEE emp, tab_group_user_relation gur, tab_user_group ug, tab_group_role_relation grr,\n" +
//                "\t\t  tab_role r, tab_role_menu_relation rmr\n" +
//                "\t\twhere emp.user_id = gur.user_id and gur.user_group_id = ug.user_group_id \n" +
//                "\t\t  and ug.user_group_id = grr.user_group_id and grr.role_id = r.role_id\n" +
//                "\t\t  and r.role_type = '"+MENU_ROLE_TYPE+"' and r.role_id = rmr.role_id and emp.user_id = '"+userId+"'\n" +
//                "\t\t  union\n" +
//                "\t\t  select rmr.menu_id from \n" +
//                "\t\t  TAB_EMPLOYEE emp, tab_user_role_relation urr, \n" +
//                "\t\t  tab_role r, tab_role_menu_relation rmr\n" +
//                "\t\twhere emp.user_id = urr.user_id \n" +
//                "\t\t  and urr.role_id = r.role_id\n" +
//                "\t\t  and r.role_type = '"+MENU_ROLE_TYPE+"' and r.role_id = rmr.role_id and emp.user_id = '"+userId+"'\n" +
//                "\t)\n" +
//                ")")
//
//        List<String[]> results = rawResults.getResults()
//        for (int i = 0 i < results.size() i++) {
//            Menu menu = new Menu()
//            menu.menu_id = Long.parseLong(results.get(i)[0])
//            menu.menu_level = Integer.parseInt(results.get(i)[1])
//            menu.seq = Integer.parseInt(results.get(i)[2])
//            menu.menu_name = results.get(i)[3]
//            menu.activity = results.get(i)[4]
//            menu.check_time = results.get(i)[5]
//            menus.add(menu)
//        }
//        return menus
//    }
//
//    public List<ReprintReason> queryAllReason() {
//        try{
//            Dao<ReprintReason, Integer> dao = getDao(ReprintReason.class)
//            List<ReprintReason> reasonList = dao.queryForAll()
//            /*List<String> reason = new ArrayList<String>()
//            for (ReprintReason reprintReason : reasonList) {
//                reason.add(reprintReason.reason_description)
//            }*/
//            return reasonList
//        }catch (Exception e) {
//            JavaLogger.e(TAG, "", e)
//        }
//        return null
//    }

//    private boolean checkDataBase() {
//        SQLiteDatabase checkDB = null
//
//        try {
//            File dbFile = mContext.getDatabasePath(DATABASE_NAME)
//            if (dbFile.exists())
//                checkDB = SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY)
//        } catch (SQLiteException e) {
//            //Log.i(TAG, "database doesn't exist yet")
//        }
//
//        if (checkDB != null)
//            checkDB.close()
//
//        return checkDB != null
//    }

//    public Employee findEmployeeById(String id) {
//        try {
//            Dao<Employee, Integer> dao = getDao(Employee.class)
//            return dao.queryForEq("user_id", id).get(0)
//        } catch (Exception e) {
//            e.printStackTrace()
//            return null
//        }
//    }

//    public List<String> findOrderArticleIdFromOrderArticle(String where) {
//        List<String> articleIds = new ArrayList<String>()
//        try {
//            if (where.startsWith("work_date")) {
//                Cursor cursor = getReadableDatabase().query("tab_order_article",
//                    new String[] { "article_id" }, where, null, null, null,
//                    "cat01_code, article_id")
//                if (cursor.getCount() > 0 && cursor.moveToFirst()) {
//                    do {
//                        articleIds.add(cursor.getString(0))
//                    } while (cursor.moveToNext())
//                }
//                return articleIds
//            } else {
//                final Dao<OrderArticle, Integer> dao = getDao(OrderArticle.class)
//                GenericRawResults rawResults =  dao.queryRaw("select o.article_id from tab_order_article o, " +
//                        "tab_shelf_article s where o.article_id = s.article_id and  s.shelf_code = '"+where+"' " +
//                        "group by s.article_id order by s.article_sequence")
//                final List<String[]> ret = rawResults.getResults()
//                if (ret != null && !ret.isEmpty()) {
//                    for (int i = 0 i < ret.size() i++)
//                        articleIds.add(ret.get(i)[0])
//                }
//                return articleIds
//            }
//        } catch (Exception e) {
//            e.printStackTrace()
//            return articleIds
//        }
//    }
//    public boolean isShelfArticleExists(String userId) {
//        try {
//            Dao<ShelfArticle, Integer> dao = getDao(ShelfArticle.class)
//            String tn = getTableName(ShelfArticle.class)
//            String sql = "select count(*) from " + tn
//                    + " where update_userid = '" + userId
//                    + "' and upload_flg != 'Y'"
//            GenericRawResults<String[]> rawResults = dao.queryRaw(sql)
//            List<String[]> results = rawResults.getResults()
//            String[] result = results.get(0)
//            return Integer.parseInt(result[0]) > 0
//        } catch (Exception e) {
//            JavaLogger.e(TAG, "isShelfArticleExists failed", e)
//            return false
//        }
//    }
//
//    /** 查询所有盘点货架地区。 */
//    public List<Shelf> findAllInventoryShelves() {
//        try {
//            Dao<Shelf, Integer> dao = getDao(Shelf.class)
//            return dao.queryBuilder().where().eq("shelf_grade", Shelf.SHELF_GRADE_AREA).query()
//        } catch (Exception e) {
//            JavaLogger.e(TAG, "findAllInventoryShelves failed", e)
//            return null
//        }
//    }
//
//    public List<SalesArticle> findSalesArticle(String cat03_code, String cat02_code, String cat01_code) {
//        try {
//            final Store store = Store.getCurrentStore()
//            final String sysDate = store.sys_date
//            long t1 = System.nanoTime()
//            // 用貨號從tab_order_article查出此商品
//            final Dao<SalesArticle, Integer> dao = getDao(SalesArticle.class)
//            List<SalesArticle> salesArticles = new ArrayList<SalesArticle>()
//            String category = " and cat03_code = '"+cat03_code+"'"
//            if (cat02_code != null) {
//                category += " and cat02_code = '"+cat02_code+"'"
//                if (cat01_code != null) {
//                    category += " and cat01_code = '"+cat01_code+"'"
//                }
//            }
//            /*if (cat02_code != null) {
//                if (cat01_code != null) {
//                    ret = dao.query(dao.queryBuilder()
//                         .where()
//                        .eq("work_date", sysDate).and()
//                        .eq("cat03_code", cat03_code).and()
//                        .eq("cat02_code", cat02_code).and()
//                        .eq("cat01_code", cat01_code).and()
//                        .eq("sale_flag","1")
//                        .prepare())
//                } else
//                    ret = dao.query(dao.queryBuilder().where()
//                            .eq("work_date", sysDate).and()
//                            .eq("cat03_code", cat03_code).and()
//                            .eq("cat02_code", cat02_code).and()
//                            .eq("sale_flag", "1")
//                            .prepare())
//            }  else
//                ret = dao.query(dao.queryBuilder().where()
//                        .eq("work_date", sysDate).and()
//                        .eq("cat03_code", cat03_code).and()
//                        .eq("sale_flag","1")
//                        .prepare())
//            Log.d(TAG, " Query elapsed: " + (System.nanoTime() - t1) + "ns")
//            t1 = System.nanoTime()*/
//            GenericRawResults rawResults =  dao.queryRaw("select a.article_id, a.article_name,a.spec,a.brand,a.grade,a.production_location,a.sales_unit,\n" +
//                    "                a.sale_price ,b.barcode from tab_sale_article a  left join tab_barcode b on a.article_id = b.article_id \n" +
//                    "                where  a.work_date = '"+sysDate+"' "+category+"  and a.sale_flag = '1' order by a.cat01_code, a.article_id")
//            List<String[]> ret = rawResults.getResults()
//            Log.d(TAG, " Query elapsed: " + (System.nanoTime() - t1) + "ns")
//            t1 = System.nanoTime()
//            if (ret != null && !ret.isEmpty()) {
//                for (int i = 0 i < ret.size() i++){
//                    SalesArticle salesArticle = new SalesArticle()
//                    salesArticle.article_id = ret.get(i)[0]
//                    salesArticle.article_name = ret.get(i)[1]
//                    salesArticle.spec = ret.get(i)[2]
//                    salesArticle.brand = ret.get(i)[3]
//                    salesArticle.grade = ret.get(i)[4]
//                    salesArticle.production_location = ret.get(i)[5]
//                    salesArticle.sales_unit = ret.get(i)[6]
//                    salesArticle.sale_price = new BigDecimal(ret.get(i)[7])
//                    salesArticle.barcode = ret.get(i)[8]
//                    salesArticles.add(salesArticle)
//                }
//                Log.d(TAG, " Collect elapsed: " + (System.nanoTime() - t1) + "ns")
//                return salesArticles
//            }
//            /*if (ret != null && !ret.isEmpty()) {
//                List<SalesArticle> list =  new ArrayList<SalesArticle>()
//                for (int i = 0 i < ret.size() i++) {
//                    SalesArticle s = salesArticles.get(i)
//                    list.add(s)
//
//                    if (s.cat_order_type.equals("01")) {
//                        Barcode barcode = findBarcodeByArticleId(s.article_id)
//                        if (barcode != null)
//                            s.barcode = barcode.barcode
//                        list.add(s)
//                    } else {
//                        list.add(s)
//                    }
//
//                }
//                Log.d(TAG, "Collect elapsed: " + (System.nanoTime() - t1) + "ns")
//                return list
//            } else {
//                JavaLogger.i(TAG, "Cannot find tab_sale_article with word_date: " +
//                        sysDate + " and cat03_code: " + cat03_code + " and cat02_code：" + cat02_code +
//                        " and cat01_code：" + cat01_code)
//                return null
//            }*/
//            return null
//        } catch (Exception e) {
//            JavaLogger.e(TAG, "Cannot find article with cat03_code: " + cat03_code, e)
//            return null
//        }
//    }
//
//    public Barcode findBarcodeByArticleId(String article_id) {
//        try {
//            final Dao<Barcode, String> barcodeDao = getDao(Barcode.class)
//            List<Barcode> barcodes = barcodeDao.query(barcodeDao.queryBuilder().where().eq("article_id",article_id).prepare())
//            if (barcodes.size() > 0)
//                return barcodes.get(0)
//            return null
//        } catch (Exception e) {
//            JavaLogger.e(TAG,"Cannot find Barcode with acticle_id：" + article_id, e)
//            return null
//        }
//    }
//
//    public List<String> findOrderArticleIdFromInventoryArticle(String where) {
//        List<String> articleIds = new ArrayList<String>()
//        try {
//            if (where.startsWith("work_date")) {
//                Cursor cursor = getReadableDatabase().query("tab_inventory_article",
//                    new String[] { "article_id" }, where, null, null, null,
//                    "cat01_code, article_id")
//                if (cursor.getCount() > 0 && cursor.moveToFirst()) {
//                    do {
//                        articleIds.add(cursor.getString(0))
//                    } while (cursor.moveToNext())
//                }
//                return articleIds
//            }  else {
//                final Dao<InventoryArticle, Integer> dao = getDao(InventoryArticle.class)
//                GenericRawResults rawResults =  dao.queryRaw("select o.article_id from tab_inventory_article o, " +
//                        "tab_shelf_article s where o.article_id = s.article_id and  s.shelf_code = '"+where+"' " +
//                        "group by s.article_id order by s.article_sequence")
//                final List<String[]> ret = rawResults.getResults()
//                if (ret != null && !ret.isEmpty()) {
//                    for (int i = 0 i < ret.size() i++)
//                        articleIds.add(ret.get(i)[0])
//                }
//                return articleIds
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace()
//            return articleIds
//        }
//    }
//
//    public int countOrderArticle() {
//        try {
//            Dao<OrderArticle, Integer> dao = getDao(OrderArticle.class)
//            String tn = getTableName(OrderArticle.class)
//            GenericRawResults<String[]> rawResults = dao.queryRaw("select count(*) from " + tn +
//                " WHERE work_date='" + Store.getCurrentStore().sys_date + "'")
//            List<String[]> results = rawResults.getResults()
//            String[] result = results.get(0)
//            return Integer.parseInt(result[0])
//        } catch (Exception e) {
//            e.printStackTrace()
//            return 0
//        }
//    }
//
//    public int countOrderCategory() {
//        try {
//            Dao<OrderCategory, Integer> dao = getDao(OrderCategory.class)
//            String tn = getTableName(OrderCategory.class)
//            GenericRawResults<String[]> rawResults = dao.queryRaw("select count(*) from " + tn +
//                " WHERE work_date='" + Store.getCurrentStore().sys_date + "'")
//            List<String[]> results = rawResults.getResults()
//            String[] result = results.get(0)
//            return Integer.parseInt(result[0])
//        } catch (Exception e) {
//            e.printStackTrace()
//            return 0
//        }
//    }
//
//    public int countInventoryArticle() {
//        try {
//            Dao<InventoryArticle, Integer> dao = getDao(InventoryArticle.class)
//            String tn = getTableName(InventoryArticle.class)
//            GenericRawResults<String[]> rawResults = dao.queryRaw("select count(*) from " + tn +
//                " WHERE work_date='" + Store.getCurrentStore().sys_date + "'")
//            List<String[]> results = rawResults.getResults()
//            String[] result = results.get(0)
//            return Integer.parseInt(result[0])
//        } catch (Exception e) {
//            e.printStackTrace()
//            return 0
//        }
//    }
//
//    public int countInventoryCategory() {
//        try {
//            Dao<InventoryCategory, Integer> dao = getDao(InventoryCategory.class)
//            String tn = getTableName(InventoryCategory.class)
//            GenericRawResults<String[]> rawResults = dao.queryRaw("select count(*) from " + tn +
//                " WHERE work_date='" + Store.getCurrentStore().sys_date + "'")
//            List<String[]> results = rawResults.getResults()
//            String[] result = results.get(0)
//            return Integer.parseInt(result[0])
//        } catch (Exception e) {
//            e.printStackTrace()
//            return 0
//        }
//    }
//
//    public OrderCategory findOrderCategory(String categoryId) {
//        try {
//            Dao<OrderCategory, Integer> dao = getDao(OrderCategory.class)
//            List<OrderCategory> results = dao.queryForEq("category_id", categoryId)
//            if (results != null && results.size() > 0)
//                return results.get(0)
//        } catch (SQLException e) {
//            e.printStackTrace()
//        }
//        return null
//    }
//
//    public String findCategoryName(String categoryCode) {
//        OrderCategory cat = findOrderCategory(categoryCode)
//        return cat != null ? cat.category_name : ""
//    }
//
//    public ArticleInfo findArticleInfo(String date, String articleId) {
//        try {
//            Dao<ArticleInfo, Integer> dao = getDao(ArticleInfo.class)
//            List<ArticleInfo> results = dao.query(dao.queryBuilder().where()
//                .eq("order_date", date).and()
//                .eq("article_id", articleId)
//                .prepare())
//            if (results != null && results.size() > 0)
//                return results.get(0)
//        } catch (SQLException e) {
//            e.printStackTrace()
//        }
//        return null
//    }
//
//    /** 檢查當前用戶是否有對該商品進行訂貨的權限**/
//    public boolean checkUserPower(OrderArticle orderArticle) {
//        try {
//            OrderingApplication mApp = (OrderingApplication) mContext.getApplicationContext()
//            String userId = mApp.getEmployee().user_id
//            Dao<OrderArticle, Integer> dao = getDao(OrderArticle.class)
//            GenericRawResults<String[]> rawResults = dao.queryRaw("select cat01_code\n" +
//                    "\tfrom TAB_EMPLOYEE emp, tab_group_user_relation gur, tab_user_group ug, tab_group_role_relation grr," +
//                    "\ttab_role r, tab_role_category_relation rcr\n" +
//                    "\twhere emp.user_id = gur.user_id and gur.user_group_id = ug.user_group_id \n" +
//                    "\t  \tand ug.user_group_id = grr.user_group_id and grr.role_id = r.role_id and r.role_id = rcr.role_id\n" +
//                    "\t  \tand r.role_type='"+CATEGORY_ROLE_TYPE+"' and emp.user_id = '"+userId+"' \n" +
//                    "union\t  \t\n" +
//                    "select cat01_code\n" +
//                    "\t\tfrom tab_employee emp, tab_user_role_relation urr, tab_role r, tab_role_category_relation rcr\n" +
//                    "\t\twhere emp.user_id = urr.user_id and urr.role_id = r.role_id and r.role_id = rcr.role_id \n" +
//                    "\t\t\tand r.role_id = rcr.role_id and r.role_type='"+CATEGORY_ROLE_TYPE+"' and emp.user_id = '"+userId+"' \t\t\n")
//
//            List<String[]> results = rawResults.getResults()
//            for (int i = 0 i < results.size() i++) {
//                String code01 = results.get(i)[0]
//                if (code01.endsWith("00")) {
//                    if (code01.endsWith("0000")) {
//                        if (code01.equals("000000"))
//                            return false
//                        if (orderArticle.cat03_code.equals(code01.substring(0,2)))
//                            return false
//                    }
//                    if (orderArticle.cat02_code.equals(code01.substring(0,4)))
//                        return false
//                } else {
//                  if (orderArticle.cat01_code.equals(code01))
//                      return false
//                }
//            }
//        } catch (Exception e) {
//            JavaLogger.e(TAG, "Cannot find UserPower: " + orderArticle.article_id, e)
//            return true
//        }
//        return true
//    }
//
//    /** 從條碼查出商品貨號。 */
//    public String findArticleIdByBarcode(String barcode) {
//        try {
//            // 從條碼查出貨號
//            final Dao<Barcode, String> barcodeDao = getDao(Barcode.class)
//            final Barcode barcodeObj = barcodeDao.queryForId(barcode)
//            if (barcodeObj == null) {
//                JavaLogger.i(TAG, "Cannot find tab_barcode with barcode: " + barcode)
//                return null
//            } else {
//                return barcodeObj.article_id
//            }
//        } catch (Exception e) {
//            JavaLogger.e(TAG, "Cannot find article with barcode: " + barcode, e)
//            return null
//        }
//    }
//
//    /** 從條碼查出訂貨商品. 订单类型必須為”01“： 普通订单（有条形码）。*/
//    public OrderArticle findOrderArticleByBarcode(String barcode) {
//        try {
//            // 從條碼查出貨號
//            final Dao<Barcode, String> barcodeDao = getDao(Barcode.class)
//            final Barcode barcodeObj = barcodeDao.queryForId(barcode)
//            if (barcodeObj == null) {
//                JavaLogger.i(TAG, "Cannot find tab_barcode with barcode: " + barcode)
//                return null
//            }
//
//            // 拿訂貨日期
//            final Store store = Store.getCurrentStore()
//            final String sysDate = store.sys_date
//
//            // 用貨號從tab_order_article查出此商品
//            final Dao<OrderArticle, Integer> dao = getDao(OrderArticle.class)
//            final List<OrderArticle> ret = dao.query(dao.queryBuilder().where()
//                .eq("work_date", sysDate).and()
//                .eq("article_id", barcodeObj.article_id).and()
//                .eq("cat_order_type", "01") // 订单类型, 01：普通订单（有条形码）
//                .prepare())
//            if (ret != null && !ret.isEmpty())
//                return ret.get(0)
//            else {
//                JavaLogger.i(TAG, "Cannot find tab_order_article with word_date: " +
//                    sysDate + " and article_id: " + barcodeObj.article_id)
//                return null
//            }
//        } catch (Exception e) {
//            JavaLogger.e(TAG, "Cannot find article with barcode: " + barcode, e)
//            return null
//        }
//    }
//
//    /** 從貨號查出销售商品. */
//    public SalesArticle findSaleArticleByArticleId(String articleId) {
//        try {
//            // 拿訂貨日期
//            final Store store = Store.getCurrentStore()
//            final String sysDate = store.sys_date
//
//            // 用貨號從tab_order_article查出此商品
//            final Dao<SalesArticle, Integer> dao = getDao(SalesArticle.class)
//            final List<SalesArticle> ret = dao.query(dao.queryBuilder().where()
//                    .eq("work_date", sysDate).and()
//                    .eq("article_id", articleId)
//                    .prepare())
//            if (ret != null && !ret.isEmpty())
//                return ret.get(0)
//            else {
//                JavaLogger.i(TAG, "Cannot find tab_sale_article with word_date: " +
//                        sysDate + " and article_id: " + articleId)
//                return null
//            }
//        } catch (Exception e) {
//            JavaLogger.e(TAG, "Cannot find article with article_id: " + articleId, e)
//            return null
//        }
//    }
//
//
//    /** 從貨號查出訂貨商品. */
//    public OrderArticle findOrderArticleByArticleId(String articleId) {
//        try {
//            // 拿訂貨日期
//            final Store store = Store.getCurrentStore()
//            final String sysDate = store.sys_date
//
//            // 用貨號從tab_order_article查出此商品
//            final Dao<OrderArticle, Integer> dao = getDao(OrderArticle.class)
//            final List<OrderArticle> ret = dao.query(dao.queryBuilder().where()
//                .eq("work_date", sysDate).and()
//                .eq("article_id", articleId)
//                .prepare())
//            if (ret != null && !ret.isEmpty())
//                return ret.get(0)
//            else {
//                JavaLogger.i(TAG, "Cannot find tab_order_article with word_date: " +
//                    sysDate + " and article_id: " + articleId)
//                return null
//            }
//        } catch (Exception e) {
//            JavaLogger.e(TAG, "Cannot find article with article_id: " + articleId, e)
//            return null
//        }
//    }
//
//    /** 從條碼查出盤點商品. 订单类型必須為”01“： 普通订单（有条形码）。*/
//    public InventoryArticle findInventoryArticleByBarcode(String barcode) {
//        try {
//            // 從條碼查出貨號
//            final Dao<Barcode, String> barcodeDao = getDao(Barcode.class)
//            final Barcode barcodeObj = barcodeDao.queryForId(barcode)
//            if (barcodeObj == null) {
//                JavaLogger.i(TAG, "Cannot find tab_barcode with barcode: " + barcode)
//                return null
//            }
//
//            // 拿盤點日期
//            final Store store = Store.getCurrentStore()
//            final String sysDate = store.sys_date
//
//            // 用貨號從tab_order_article查出此商品
//            final Dao<InventoryArticle, Integer> dao = getDao(InventoryArticle.class)
//            final List<InventoryArticle> ret = dao.query(dao.queryBuilder().where()
//                    .eq("work_date", sysDate).and()
//                    .eq("article_id", barcodeObj.article_id).and()
//                    .eq("cat_order_type", "01") // 订单类型, 01：普通订单（有条形码）
//                    .prepare())
//            if (ret != null && !ret.isEmpty())
//                return ret.get(0)
//            else {
//                JavaLogger.i(TAG, "Cannot find tab_inventory_article with word_date: " +
//                        sysDate + " and article_id: " + barcodeObj.article_id)
//                return null
//            }
//        } catch (Exception e) {
//            JavaLogger.e(TAG, "Cannot find article with barcode: " + barcode, e)
//            return null
//        }
//    }
//
//    /** 從貨號查出盤點商品. */
//    public InventoryArticle findInventoryArticleByArticleId(String articleId) {
//        try {
//            // 拿盤點日期
//            final Store store = Store.getCurrentStore()
//            final String sysDate = store.sys_date
//
//            // 用貨號從tab_inventory_article查出此商品
//            final Dao<InventoryArticle, Integer> dao = getDao(InventoryArticle.class)
//            final List<InventoryArticle> ret = dao.query(dao.queryBuilder().where()
//                    .eq("work_date", sysDate).and()
//                    .eq("article_id", articleId)
//                    .prepare())
//            if (ret != null && !ret.isEmpty())
//                return ret.get(0)
//            else {
//                JavaLogger.i(TAG, "Cannot find tab_inventory_article with word_date: " +
//                        sysDate + " and article_id: " + articleId)
//                return null
//            }
//        } catch (Exception e) {
//            JavaLogger.e(TAG, "Cannot find article with article_id: " + articleId, e)
//            return null
//        }
//    }
//
//    /**/
//    public List<String[]> findPOSSalePriceByBarcode(String barcode) {
//        try {
//            // 拿盤點日期
//            final Store store = Store.getCurrentStore()
//            final String sysDate = store.sys_date
//
//            // 用条码從tab_pos_sale_price查出此商品
//            final Dao<SalePrice, Integer> dao = getDao(SalePrice.class)
//            GenericRawResults rawResults =  dao.queryRaw("select psp.barcode,sa.article_name, sa.article_id, sa.brand, sa.grade,sa.spec, sa.production_location, " +
//                    "sa.sales_unit,psp.base__price,psp.sale_status, psp.promotion_price,psp.mix_price,psp.current_price,psp.update_date,psp.update_time " +
//                    "from tab_barcode b,tab_sale_article sa, tab_pos_sale_price psp where b.barcode = '"+barcode+"' " +
//                    "and psp.busi_date = '"+sysDate+"'  and  b.article_id = sa.article_id  and b.barcode = psp.barcode\n")
//            final List<String[]> ret = rawResults.getResults()
//            if (ret != null && !ret.isEmpty())
//                return ret
//            else {
//                JavaLogger.i(TAG, "Cannot find tab_pos_sale_price with word_date: " +
//                        sysDate + " and barcode: " + barcode)
//                return null
//            }
//        } catch (Exception e) {
//            JavaLogger.e(TAG, "Cannot find tab_pos_sale_price with barcode: " + barcode, e)
//            return null
//        }
//
//
//    }
//
//    public List<String[]> countShelfArticleRecordByUserId() {
//        try {
//            Dao<ShelfArticle, Integer> dao = getDao(ShelfArticle.class)
//            String tn = getTableName(ShelfArticle.class)
//            String sql = "select update_userid, count(*) from " + tn
//                    + " where upload_flg != 'Y' group by update_userid"
//            GenericRawResults<String[]> rawResults = dao.queryRaw(sql)
//            return rawResults.getResults()
//        } catch (SQLException e) {
//            JavaLogger.e(TAG, "countShelfArticleRecordByUserId failed", e)
//            return null
//        }
//    }
//
//    /**根据货架号修改货架陈列商品*/
//    public void updateShelfArticleByShelfCode(String shelfCode) {
//        try {
//            final Calendar now = Calendar.getInstance()
//            final Dao<ShelfArticle, Integer> dao = getDao(ShelfArticle.class)
//            UpdateBuilder<ShelfArticle, Integer> db1 = dao.updateBuilder()
//            db1.updateColumnValue("input_type", "2")
//            db1.updateColumnValue("upload_flg", "N")
//            db1.updateColumnValue("update_date", String.format("%1$tY%1$tm%1$td", now))
//            db1.updateColumnValue("update_time", String.format("%1$tH%1$tM%1$tS%1$tL", now))
//            db1.where().eq("shelf_code", shelfCode)
//            db1.update()
//        } catch (Exception e) {
//            JavaLogger.e(TAG, "updateShelfArticleByShelfCode failed", e)
//        }
//    }
//
//    /**根据货架号删除货架陈列商品*/
//    public void deleteShelfArticleByShelfCode(String shelfCode) {
//        try {
//            final Dao<ShelfArticle, Integer> dao = getDao(ShelfArticle.class)
//            DeleteBuilder<ShelfArticle, Integer> db1 = dao.deleteBuilder()
//            db1.where().eq("shelf_code", shelfCode)
//            db1.delete()
///**
// 2014-04-25-002
// 对应，货架管理中增加了删除货架操作的日志记录
//*/
//            JavaLogger.i(TAG, "Order deleteShelfArticleByShelfCode: " + shelfCode)
///**
//
//*/
//        } catch (Exception e) {
//            JavaLogger.e(TAG, "deleteShelfArticleByShelfCode failed", e)
//        }
//    }
//
//    /** 更新或插入新的訂貨數量，如果ordQty為空，表示啥事不幹或要刪除先前此商品的訂貨。 */
//    public int insertShelfArticle(String storeId, String userId, String articleId, String shelfCode, int seq) {
//        try {
//            Dao<ShelfArticle,Integer> dao = getDao(ShelfArticle.class)
//            final String tabletId = ((OrderingApplication) mContext.getApplicationContext()).getSettings().getTabletId()
//
//            final Calendar now = Calendar.getInstance()
//            ShelfArticle shelfArticle = new ShelfArticle()
//            shelfArticle.article_id = articleId
//            shelfArticle.tablet_id = tabletId
//            shelfArticle.update_userid = userId
//            shelfArticle.store_id = storeId
//            shelfArticle.shelf_code = shelfCode
//            shelfArticle.article_sequence = seq
//            shelfArticle.upload_flg = "N"
//            shelfArticle.input_type = "1"
//            shelfArticle.update_date = String.format("%1$tY%1$tm%1$td", now)
//            shelfArticle.update_time = String.format("%1$tH%1$tM%1$tS%1$tL", now)
//            if (dao.create(shelfArticle) != 1) {
//                JavaLogger.i(TAG, "insert fail article：" + articleId + " shelf_code：" + shelfCode)
//                return 0
//            } else {
//                JavaLogger.i(TAG, "Order inserted: " + shelfArticle)
//                return 1
//            }
//        } catch (Exception e) {
//            JavaLogger.e(TAG, "updateOrInsertShelfArticle failed", e)
//            return 0
//        }
//    }
//
//    //修改货架上传状态与时间
//    public int updateShelfArticleRecordState(final List<ShelfArticle> orderInputs) {
//        try {
//            final Dao<ShelfArticle, Integer> dao = getDao(ShelfArticle.class)
//            return dao.callBatchTasks(new Callable<Integer>() {
//                @Override
//                public Integer call() throws Exception {
//                    int rec = 0
//                    for (ShelfArticle shelfArticle : orderInputs) {
//                        shelfArticle.upload_flg = "Y" // 已送信state
//                        GenericRawResults<String[]> rawResults = dao.queryRaw("update tab_shelf_article set upload_flg = 'Y'," +
//                                "upload_date = '"+shelfArticle.upload_date+"', upload_time = '"+shelfArticle.upload_time+"' " +
//                                "where store_id = '"+shelfArticle.store_id+"' and shelf_code = '"+shelfArticle.shelf_code+"' and " +
//                                " article_sequence = '"+shelfArticle.article_sequence+"'")
//                        rawResults.getResults()
//                        rec++
//                        //rec += dao.update(shelfArticle)
//                    }
//                    return rec
//                }
//            })
//        } catch (Exception e) {
//            JavaLogger.e(TAG, "updateShelfArticleRecordState failed", e)
//            return 0
//        }
//    }
//
//    public Shelf findShelfByShelfCode(String shelfCode) {
//        try {
//            final Dao<Shelf, Integer> dao = getDao(Shelf.class)
//            final List<Shelf> ret = dao.query(dao.queryBuilder().where() .eq("shelf_code", shelfCode)
//                    .prepare())
//            if (ret != null && !ret.isEmpty())
//                return ret.get(0)
//        } catch (Exception e) {
//            JavaLogger.e(TAG, "updateShelfArticleRecordState failed", e)
//            return null
//        }
//        return null
//    }
//
//    public List<Shelf> findShelfByParentShelfCode(String shelfCode) {
//        try {
//            final Dao<Shelf, Integer> dao = getDao(Shelf.class)
//            final List<Shelf> ret = dao.query(dao.queryBuilder().where() .like("parent_shelf_code", shelfCode+"%")
//                    .prepare())
//            if (ret != null && !ret.isEmpty())
//                return ret
//        } catch (Exception e) {
//            JavaLogger.e(TAG, "updateShelfArticleRecordState failed", e)
//            return null
//        }
//        return null
//    }
//    /** 查询某日，某货架号的货架商品陈列*/
//    public List<ShelfArticle> findShelfArticle(String storeId, String orderDate, String shelf_code) {
//        List<ShelfArticle> shelfArticles = new ArrayList<ShelfArticle>()
//        try {
//            SQLiteDatabase db = getReadableDatabase()
//            String sql = "select d.barcode, c.article_id, c.article_name from (select * from tab_shelf_article a " +
//                    "where a.store_id = ? and a.shelf_code = ? and input_type = '1') b \n" +
//                    "inner join tab_sale_article c on c.work_date = ? and c.sale_flag = '1' and b.article_id = c.article_id " +
//                    "left join tab_barcode d on c.article_id = d.article_id group by c.article_id order by b.article_sequence"
//            JavaLogger.i(TAG, "findOrderInput(" + orderDate + ", " + shelf_code + "): " + sql)
//            Cursor cursor = db.rawQuery(sql, new String[] {storeId, shelf_code, orderDate})
//            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
//                do {
//                    ShelfArticle orderInput = new ShelfArticle()
//                    orderInput.barcode = cursor.getString(0)
//                    orderInput.article_id = cursor.getString(1)
//                    orderInput.article_name = cursor.getString(2)
//                    // 用貨號從tab_sale_article查出此商品
//                    final Dao<SalesArticle, Integer> dao = getDao(SalesArticle.class)
//                    final List<SalesArticle> ret = dao.query(dao.queryBuilder().where()
//                            .eq("work_date", orderDate).and()
//                            .eq("article_id", orderInput.article_id)
//                            .prepare())
//                    if (ret != null && !ret.isEmpty())
//                        orderInput.setSaleArticle(ret.get(0))
//                    shelfArticles.add(orderInput)
//                } while (cursor.moveToNext())
//            }
//        } catch (Exception e) {
//            JavaLogger.e(TAG, "findShelfArticle failed", e)
//        }
//        return shelfArticles
//    }
//
//    /** 取未上传的数据 */
//    public List<ShelfArticle> findUploadShelfArticle(String userId, String tabletId) {
//        try {
//            Dao<ShelfArticle, Integer> dao = getDao(ShelfArticle.class)
//            return dao.query(dao.queryBuilder().where()
//                    .eq("update_userid", userId).and()
//                    .eq("tablet_id",tabletId).and()
//                    .ne("upload_flg", "Y").prepare())
//        } catch (Exception e) {
//            JavaLogger.e(TAG, "findUploadOrderInput failed", e)
//            return null
//        }
//    }

//    /**
//     * Import table from a data file.
//     * <p>
//     * File format:<br>
//     * column_name_1/column_type_1,column_name_2/column_type_2,column_name_3/column_type_3,...\n<br>
//     * column_value_1,column_value_2,column_value_3,...\n<br>
//     * column_value_1,column_value_2,column_value_3,...\n<br>
//     * ...
//     * <p>
//     * Field type:<br>
//     * S: String, I: Integer, D: Date, F: Decimal
//     */
//    public boolean importTable(Context mContext, String importTable, String importFile,
//                               DataTransferTask asyncTask, int startPerc, int endPerc) {
//        SQLiteDatabase db = getReadableDatabase()
//        try {
//            long fileSize = new File(mContext.getFilesDir(), importFile).length()
//            long filePos = 0
//            BufferedReader reader = new BufferedReader(new InputStreamReader(
//                mContext.openFileInput(importFile), "UTF-8"))
//
//            // Read column definition at first line
//            String columnDefinitions = reader.readLine()
//            filePos += columnDefinitions.length() + 1
//            final String[] columnDefs = columnDefinitions.split(",")
//            String[] columnNames = new String[columnDefs.length]
//            String[] columnTypes = new String[columnDefs.length]
//            int i = 0
//            for (String columnDef : columnDefs) {
//                String[] tokens = columnDef.split("/")
//                columnNames[i] = tokens[0]
//                columnTypes[i] = tokens[1]
//                i++
//            }
//
//            db.beginTransaction()
//
//            // Remove all
//            db.delete(importTable, null, null)
//
//            // Import rows
//            String line
//            int recordCount = 0
//            ContentValues cv = new ContentValues()
//            while ((line = reader.readLine()) != null) {
//                cv.clear()
//                String[] columnValues = line.split(COLUMN_VALUE_SEPARATOR)
//                i = 0
//                for (String columnValue : columnValues) {
//                    if (!"null".equals(columnValue))
//                        switch (columnTypes[i].charAt(0)) {
//                            case 'S':
//                                cv.put(columnNames[i], columnValue)
//                                break
//                            case 'I':
//                                cv.put(columnNames[i], Integer.parseInt(columnValue))
//                                break
//                            case 'F':
//                                cv.put(columnNames[i], Float.parseFloat(columnValue))
//                                break
//                            case 'D':
//                                cv.put(columnNames[i], columnValue)
//                                break // TODO: parse date string
//                            default:
//                                cv.put(columnNames[i], columnValue)
//                                break
//                        }
//                    i++
//                }
//                db.insert(importTable, null, cv)
//                recordCount++
//
//                // Refresh progress
//                filePos += line.getBytes("UTF-8").length + 1
//                float perc = (float) filePos / (float) fileSize
//                asyncTask.publishProgress(startPerc + (int)((float)(endPerc - startPerc) * perc))
//            }
//            cv.clear()
//
//            db.setTransactionSuccessful()
//            JavaLogger.i(TAG, recordCount + " records of " + importTable + " were imported.")
//            return true
//
//        } catch (Exception e) {
//            JavaLogger.e(TAG, "importTable() failed", e)
//            return false
//
//        } finally {
//            db.endTransaction()
//        }
//    }

//    public void resetDatabase() {
//        try {
//            getDao(ArticleInfo.class).deleteBuilder().delete()
//            getDao(Barcode.class).deleteBuilder().delete()
//            getDao(ColorPower.class).deleteBuilder().delete()
//            getDao(ColorTime.class).deleteBuilder().delete()
//            getDao(ColorWifi.class).deleteBuilder().delete()
//            getDao(Employee.class).deleteBuilder().delete()
//            getDao(InventoryArticle.class).deleteBuilder().delete()
//            getDao(InventoryCategory.class).deleteBuilder().delete()
//            getDao(Menu.class).deleteBuilder().delete()
//            getDao(OrderArticle.class).deleteBuilder().delete()
//            getDao(OrderCategory.class).deleteBuilder().delete()
//            getDao(Store.class).deleteBuilder().delete()
//            getDao(TabletLog.class).deleteBuilder().delete()
//            getDao(Shelf.class).deleteBuilder().delete()
//            getDao(ShelfArticle.class).deleteBuilder().delete()
//        } catch (Exception e) {
//            e.printStackTrace()
//        }
//    }
}
