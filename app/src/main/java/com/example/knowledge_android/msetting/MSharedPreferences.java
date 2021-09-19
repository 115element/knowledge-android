package com.example.knowledge_android.msetting;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.knowledge_android.R;

import java.io.File;
import java.util.Calendar;

/*
SharedPreferences介绍
在Android开发中，经常需要将少量简单类型数据保存在本地，如：用户设置。这些需要保存的数据可能一两个字符串，
像这样的数据一般选择使用SharedPreferences来保存。

SharedPreferences：一个轻量级的存储类，特别适合用于保存软件配置参数。
（是用xml文件存放数据，文件存放在/data/data/<package name>/shared_prefs目录下）

SharedPreferences可以保存的数据类型有：int、boolean、float、long、String、StringSet。

----------------使用SharedPreferences存储和读取数据的步骤--------------------
存储数据,保存数据一般分为四个步骤：
1、使用Activity类的getSharedPreferences方法获得SharedPreferences对象；
2、使用SharedPreferences接口的edit获得SharedPreferences.Editor对象；
3、通过SharedPreferences.Editor接口的putXXX方法保存key-value对；
4、通过过SharedPreferences.Editor接口的commit方法保存key-value对。

读取数据、读取数据一般分为两个步骤：
1、使用Activity类的getSharedPreferences方法获得SharedPreferences对象；
2、通过SharedPreferences对象的getXXX方法获取数据；
*/

/**
 * @author Alex
 * @since 2021-09-19
 */
public class MSharedPreferences implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static String TAG = "Settings";
    private Context mContext;
    private SharedPreferences mSharedPrefs;
    private boolean mDevelopMode;

    public MSharedPreferences(Context context) {
        mContext = context;
        PreferenceManager.setDefaultValues(mContext, R.xml.preferences, false);

        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        mSharedPrefs.registerOnSharedPreferenceChangeListener(this);
        File sdcardPath = Environment.getExternalStorageDirectory();
        if (new File(sdcardPath, "MobilePOS_debug").exists()) {
            Log.i(TAG, "develop mode is on");
            mDevelopMode = true;
        }
    }

    boolean isDevelopMode() {
        return mDevelopMode;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    }

    private String k(int resId) {
        return mContext.getString(resId);
    }

    String getStoreId() {
        return mSharedPrefs.getString(k(R.string.pref_key_store_id), "");
    }

    void setStoreId(String storeId) {
        mSharedPrefs.edit().putString(k(R.string.pref_key_store_id), storeId).apply();
    }

    boolean isDeviceInitialized() {
        return !getDeviceId().isEmpty() && !getStoreId().isEmpty();
    }

    String getDeviceId() {
        try {
            return mSharedPrefs.getString(k(R.string.pref_key_device_id), "");
        } catch (Exception ignored) {
            return "";
        }
    }

    void setDeviceId(String tabletId) {
        mSharedPrefs.edit().putString(k(R.string.pref_key_device_id), tabletId).apply();
    }

    int getServerPort() {
        return Integer.parseInt(mSharedPrefs.getString(k(R.string.pref_key_server_port), "3160"));
    }

    void setShouQuanflag(String serverPort) {
        mSharedPrefs.edit().putString(k(R.string.pref_shouquan), serverPort + "").apply();
    }

    String getShouQuanflag() {
        return mSharedPrefs.getString(k(R.string.pref_shouquan), "");
    }

    void setServerPort(int serverPort) {
        mSharedPrefs.edit().putString(k(R.string.pref_key_server_port), serverPort + "").apply();
    }

    void setShouQuanPort(int serverPort) {
        mSharedPrefs.edit().putString(k(R.string.pref_key_shouquan_port), serverPort + "").apply();
    }

    int getShouQuanPort() {
        return Integer.parseInt(mSharedPrefs.getString(k(R.string.pref_key_shouquan_port), "3168"));
    }

    int getConnectTimeout() {
        return Integer.parseInt(mSharedPrefs.getString(k(R.string.pref_key_server_connect_timeout), "5000"));
    }

    void setConnectTimeout(int connectTimeout) {
        mSharedPrefs.edit().putString(k(R.string.pref_key_server_connect_timeout), connectTimeout + "").apply();
    }

    int getReadTimeout() {
        return Integer.parseInt(mSharedPrefs.getString(k(R.string.pref_key_server_read_timeout), "20000"));
    }

    void setReadTimeout(int readTimeout) {
        mSharedPrefs.edit().putString(k(R.string.pref_key_server_read_timeout), readTimeout + "").apply();
    }

    String getServerIpAddress() {
        return mSharedPrefs.getString(k(R.string.pref_key_server_ip_addr), "");
    }

    void setServerIpAddress(String serverIpAddress) {
        mSharedPrefs.edit().putString(k(R.string.pref_key_server_ip_addr), serverIpAddress + "").apply();
    }

    String getShouquanIpAddress() {
        return mSharedPrefs.getString(k(R.string.pref_key_shouquan_ip_addr), "218.22.45.76");
    }

    void setShouquanIpAddress(String serverIpAddress) {
        mSharedPrefs.edit().putString(k(R.string.pref_key_shouquan_ip_addr), serverIpAddress + "").apply();
    }

    String getSntpServer() {
        return mSharedPrefs.getString(k(R.string.pref_key_sntp_server), "asia.pool.ntp.org");
    }

    int getRebootTimeHour() {
        return mSharedPrefs.getInt(k(R.string.pref_key_reboot_time_hour), 6);
    }

    int getRebootTimeMinute() {
        return mSharedPrefs.getInt(k(R.string.pref_key_reboot_time_minute), 0);
    }

    void setRebootTime(int hour, int minute) {
        SharedPreferences.Editor editor = mSharedPrefs.edit();
        editor.putInt(k(R.string.pref_key_reboot_time_hour), hour);
        editor.putInt(k(R.string.pref_key_reboot_time_minute), minute);
        editor.apply();
    }

    boolean isMasterDownloadEnabled() {
        return mSharedPrefs.getBoolean(k(R.string.pref_key_master_dl_enabled), true);
    }

    void setMasterDownloadEnabled(boolean b) {
        mSharedPrefs.edit().putBoolean(k(R.string.pref_key_master_dl_enabled), b).apply();
    }

    boolean isShowItemImage() {
        return mSharedPrefs.getBoolean(k(R.string.pref_key_show_item_image), false);
    }

    boolean isTransactionPrint() {
        return mSharedPrefs.getBoolean(k(R.string.pref_key_transaction_print), false);
    }

    void setShowItemImage(boolean b) {
        mSharedPrefs.edit().putBoolean(k(R.string.pref_key_show_item_image), b).apply();
    }

    boolean getAppDownloadEnabled() {
        return mSharedPrefs.getBoolean(k(R.string.pref_key_app_dl_enabled), false);
    }

    void setAppDownloadEnabled(boolean b) {
        mSharedPrefs.edit().putBoolean(k(R.string.pref_key_app_dl_enabled), b).apply();
    }

    String getMasterVersion() {
        return mSharedPrefs.getString(k(R.string.pref_key_master_ver), "");
    }

    void setMasterVersion(String masterVersion) {
        Log.i(TAG, "Write preference MasterVersion=" + masterVersion);
        mSharedPrefs.edit().putString(k(R.string.pref_key_master_ver), masterVersion).apply();
    }

    String getAppVersion() {
        return mSharedPrefs.getString(k(R.string.pref_key_app_ver), "");
    }

    void setAppVersion(String appVersion) {
        mSharedPrefs.edit().putString(k(R.string.pref_key_app_ver), appVersion).apply();
    }

    String getMasterDownloadTime() {
        return mSharedPrefs.getString(k(R.string.pref_key_master_dl_time), "");
    }

    void setMasterDownloadTime(String masterDownloadTime) {
        mSharedPrefs.edit().putString(k(R.string.pref_key_master_dl_time), masterDownloadTime).apply();
    }

    String getLastTransUploadTime() {
        return mSharedPrefs.getString(k(R.string.pref_key_last_trans_upload_time), "");
    }

    void setLastTransUploadTime(String lastTransUploadTime) {
        mSharedPrefs.edit().putString(k(R.string.pref_key_last_trans_upload_time), lastTransUploadTime).apply();
    }

    String getAppDownloadTime() {
        return mSharedPrefs.getString(k(R.string.pref_key_app_dl_time), "");
    }

    Calendar getMasterDownloadDatetime() {
        return toDate(getMasterDownloadTime());
    }

    void setAppDownloadTime(String appDownloadTime) {
        mSharedPrefs.edit().putString(k(R.string.pref_key_app_dl_time), appDownloadTime).apply();
    }

//    public int getCurrentTransactionNumber() {
//        return mSharedPrefs.getInt(k(R.string.pref_key_current_transaction_number), 1)
//    }
//
//    public void setCurrentTransactionNumber(int currentTransactionNumber) {
//        mSharedPrefs.edit().putInt(k(R.string.pref_key_current_transaction_number), currentTransactionNumber).apply()
//    }

    String getInvUploadTime() {
        return mSharedPrefs.getString(k(R.string.pref_key_inv_ul_time), "");
    }

    Calendar getInvUploadDatetime() {
        return toDate(getInvUploadTime());
    }

    void setInvUploadTime(String invUploadTime) {
        mSharedPrefs.edit().putString(k(R.string.pref_key_inv_ul_time), invUploadTime).apply();
    }

    String getOrderUploadTime() {
        return mSharedPrefs.getString(k(R.string.pref_key_ord_ul_time), "");
    }

    String getSalePriceCheckUploadTime() {
        return mSharedPrefs.getString(k(R.string.pref_key_sale_price_check_ul_time), "");
    }

    String getShelfUploadTime() {
        return mSharedPrefs.getString(k(R.string.pref_key_shelf_ul_time), "");
    }

    Calendar getOrderUploadDatetime() {
        return toDate(getOrderUploadTime());
    }

    Calendar getSalePriceCheckUploadDatetime() {
        return toDate(getSalePriceCheckUploadTime());
    }

    Calendar getShelfUploadDatetime() {
        return toDate(getShelfUploadTime());
    }

    void setSalePriceCheckUploadTime(String orderUploadTime) {
        mSharedPrefs.edit().putString(k(R.string.pref_key_sale_price_check_ul_time), orderUploadTime).apply();
    }

    void setOrderUploadTime(String orderUploadTime) {
        mSharedPrefs.edit().putString(k(R.string.pref_key_ord_ul_time), orderUploadTime).apply();
    }

    void setShelfUploadTime(String orderUploadTime) {
        mSharedPrefs.edit().putString(k(R.string.pref_key_shelf_ul_time), orderUploadTime).apply();
    }

    boolean isLeftHandedMode() {
        return mSharedPrefs.getBoolean(k(R.string.pref_key_lefthanded_mode), false);
    }

    void setLeftHandedMode(boolean b) {
        mSharedPrefs.edit().putBoolean(k(R.string.pref_key_lefthanded_mode), b).apply();
    }

    String getUseTabOrDropdown() {
        return mSharedPrefs.getString(k(R.string.pref_key_menu_style), "2");
    }

    boolean isUseTabInsteadOfDropdown() {
        return "1" == getUseTabOrDropdown();
    }

    /** 1: 桌次管理, 2: 先点先结 */
    String getBusinessType() {
        return mSharedPrefs.getString(k(R.string.pref_key_business_type), "1");
    }

    /** 金额小数位数 */
    int getDecimalDigits() {
        return Integer.parseInt(mSharedPrefs.getString(k(R.string.pref_key_decimal_digits), "0"));
    }

    int getIndexs() {
        return Integer.parseInt(mSharedPrefs.getString(k(R.string.indexs), "0"));
    }

    void setIndexs(String indexs) {
        mSharedPrefs.edit().putString(k(R.string.indexs), indexs).apply();
    }

    boolean isDiningTableManagement() {
        return ("1" == getBusinessType() || "3" == getBusinessType());
    }

    String getScreenOrientation() {
        return mSharedPrefs.getString(k(R.string.pref_key_screen_orientation), "1");
    }

    public boolean isScreenOrientationLandscape() {
        return "1" == getScreenOrientation();
    }

    void setScreenOrientation(String orientation) {
        mSharedPrefs.edit().putString(k(R.string.pref_key_screen_orientation), orientation).commit();
    }

    void setScreenOrientationPortrait() {
        setScreenOrientation("2");
    }

    void setScreenOrientationLandscape() {
        setScreenOrientation("1");
    }

    void setCurrentCashier(String cashierNumber) {
        mSharedPrefs.edit().putString(k(R.string.pref_key_current_cashier_no), cashierNumber).apply();
    }


    void setCurrentShift(String shiftNumber) {
        mSharedPrefs.edit().putString(k(R.string.pref_key_current_shift_number), shiftNumber).commit();
    }

//    ShiftHead getCurrentShift() {
//        String shiftNumber = mSharedPrefs.getString(k(R.string.pref_key_current_shift_number), "")
////        if (TextUtils.isEmpty(shiftNumber))
////            return new MposShift()
////        else {
//            IShiftDao dao = DaoLocator.getShiftDao()
//            return dao.queryByZAndShift(shiftNumber as int, 0)
////        }
//    }

    void setLicenseCode(String licenseCode) {
        mSharedPrefs.edit().putString(k(R.string.pref_key_license_code), licenseCode).commit();
    }

    String getLicenseCode() {
        return mSharedPrefs.getString(k(R.string.pref_key_license_code), "");
    }

    String getWxFacePayAutoInfo() {
        return mSharedPrefs.getString("WxFacePayAutoInfo", "");
    }

    public void setWxFacePayAutoInfo(String autoInfo) {
        mSharedPrefs.edit().putString("WxFacePayAutoInfo", autoInfo).commit();
    }

    public long getWxFacePayExpiresIn() {
        return mSharedPrefs.getLong("WxFacePayExpiresIn", 0L);
    }

    public void setWxFacePayExpiresIn(long expiresIn) {
        mSharedPrefs.edit().putLong("WxFacePayExpiresIn", expiresIn).commit();
    }

    public long getWxFacePayRowdataTime() {
        return mSharedPrefs.getLong("WxFacePayRawdataTime", 0L);
    }

    public void setWxFacePayRowdataTime(long rawdataTime) {
        mSharedPrefs.edit().putLong("WxFacePayRawdataTime", rawdataTime).commit();
    }

//    public String getBeiRuiOrderId() {//2021/7/28
//        return mSharedPrefs.getString("orderId", "")
//    }

//    public void setBeiRuiOrderId(String orderId) {//2021/7/28
//        mSharedPrefs.edit().putString("orderId", orderId).apply()
//    }

//    public String getMenberToken() {//2021/7/24
//        return mSharedPrefs.getString("menberToken", "")
//    }

//    public void setMenberToken(String menberToken) {
//        mSharedPrefs.edit().putString("menberToken", menberToken).apply()
//    }

    public boolean isCanUseMenuVersionUpdateButton() {//2021/7/24
        return mSharedPrefs.getBoolean("CanUseMenuVersionUpdateButton", false);
    }

    public void switchCanOrNotUseMenuVersionUpdateButton(boolean tnf) {
        mSharedPrefs.edit().putBoolean("CanUseMenuVersionUpdateButton", tnf).apply();
    }

    private static Calendar toDate(String s) {
        try {
            if (TextUtils.isEmpty(s))
                return null;

            Calendar cal = Calendar.getInstance();
            cal.clear();
            cal.set(Calendar.YEAR, Integer.parseInt(s.substring(0, 4)));
            cal.set(Calendar.MONTH, Integer.parseInt(s.substring(4, 6)) - 1);
            cal.set(Calendar.DATE, Integer.parseInt(s.substring(6, 8)));
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(s.substring(8, 10)));
            cal.set(Calendar.MINUTE, Integer.parseInt(s.substring(10, 12)));
            cal.set(Calendar.SECOND, Integer.parseInt(s.substring(12)));
            return cal;
        } catch (Exception e) {
            Log.e("1", "Invalid date:" + s, e);
            //e.printStackTrace()
            return null;
        }
    }
}
