package com.example.knowledge_android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.text.Html;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.multidex.MultiDexApplication;

import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.IntentUtils;
import com.blankj.utilcode.util.ShellUtils;
import com.blankj.utilcode.util.Utils;
import com.example.knowledge_android.apkdownload.ApkDownloader;
import com.example.knowledge_android.apkdownload.NetworkResultCode;
import com.example.knowledge_android.apkdownload.TransferProgressListener;
import com.example.knowledge_android.comparator.PosConstants;
import com.example.knowledge_android.comparator.i18n.I18n;
import com.example.knowledge_android.comparator.i18n.I18n_MobileResource_zh_CN;
import com.example.knowledge_android.comparator.i18n.I18n_MobileResource_zh_TW;
import com.example.knowledge_android.comparator.i18n2.I18n2;
import com.example.knowledge_android.comparator.i18n2.I18n_CommonResourceLawson_zh_CN;
import com.example.knowledge_android.comparator.i18n2.I18n_CommonResourceLawson_zh_TW;
import com.example.knowledge_android.comparator.i18n2.I18n_CommonResource_zh_CN;
import com.example.knowledge_android.comparator.i18n2.I18n_CommonResource_zh_TW;
import com.example.knowledge_android.daosupport_annotation.base.IDatabasePlusHelper;
import com.example.knowledge_android.daosupport_annotation.base.MobileDatabasePlusHelper;
import com.example.knowledge_android.daosupport_annotation.daohelp.DaoLocatorPlus;
import com.example.knowledge_android.eventlistener.MyEvent;
import com.example.knowledge_android.eventlistener.MyListener;
import com.example.knowledge_android.eventlistener.use.AA;
import com.example.knowledge_android.eventlistener.use.BB;
import com.example.knowledge_android.widget.fragment.pos_screen.PosScreenMainActivity;
import com.example.knowledge_android.widget.fragment.pos_screen.posmainfragment.IPosScreen;
import com.example.knowledge_android.knowledge.DevAddrUtil;
import com.example.knowledge_android.knowledge.RunTimer;
import com.example.knowledge_android.mqttforandroid.AndroidMqttService;
import com.example.knowledge_android.msharedpreferences.MSharedPreferences;
import com.example.knowledge_android.statemachine.StateMachine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * 当android程序启动时系统会创建一个 application对象，用来存储系统的一些信息。通常我们是不需要指定一个Application的，
 * 这时系统会自动帮我们创建，如果需要创建自己 的Application，也很简单创建一个类继承 Application并在manifest的application标签中进行注册(只需要给Application标签增加个name属性把自己的 Application的名字定入即可)。
 * <p>
 * android系统会为每个程序运行时创建一个Application类的对象且仅创建一个，所以Application可以说是单例 (singleton)模式的一个类.
 * 且application对象的生命周期是整个程序中最长的，它的生命周期就等于这个程序的生命周期。因为它是全局 的单例的，
 * 所以在不同的Activity,Service中获得的对象都是同一个对象。所以通过Application来进行一些，数据传递，数据共享 等,数据缓存等操作。
 */

public class OneApplication extends MultiDexApplication {


    private static OneApplication instance;

    public static final String USER_FONT = "fonts/Futura.ttc"; //"fonts/PTMono.ttc";

    private Typeface mUserFont;

    private MSharedPreferences mSettings;

    private IPosScreen posScreen;
    private PosScreenMainActivity mainActivity;


    //定时任务工具类(线程池)
    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    /**
     * 数据工具类
     * <p>
     * 包含：主档库,交易库:
     * MasterDatabaseHelper
     * TransDatabaseHelper
     **/
    private IDatabasePlusHelper iDatabasePlusHelper;


    private String bridgeType;
    private String deviceType;
    private String screenType;


    //******************学习设计思想，监听器**********************************
    public static List<MyListener> myListeners = new ArrayList<>();

    static {
        myListeners.add(new AA());
        myListeners.add(new BB());

        fireTransactionEvent(new MyEvent(new Object(), "请接收我传播的信息"));
    }

    //**所有我们管控的监听器都会收到该事件，也就是AA和BB**********************************
    public static void fireTransactionEvent(EventObject eventObject) {
        MyEvent myEvent = (MyEvent) eventObject;
        if (myListeners.size() != 0) {
            Iterator<MyListener> iterator = myListeners.iterator();
            while (iterator.hasNext()) {
                MyListener next = iterator.next();
                next.transactionChanged(myEvent);
            }
        }
    }
    //******************学习设计思想，监听器**********************************


    /**
     * Handler是Android SDK来处理异步消息的核心类。
     * 子线程与主线程通过Handler来进行通信。子线程可以通过Handler来通知主线程进行UI更新。
     **/
    private Handler myTimeHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                //更新显示时间区域
                //((AbstractAndriodPosScreen) app.posScreen)?.setupDateTimeBanner(getSystemDate(), getSystemTime())

                //每隔一秒执行一次
                sendEmptyMessageDelayed(0, 1000);
            }
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate() {
        Log.i("TAG", "全局单实例应用程序加载>>>>>");
        super.onCreate();

        instance = this;

        //***巧妙的时间记录器****
        RunTimer timer = new RunTimer();
        timer.start("TAG", "OneApplication Start >>>");


        Log.i("TAG", "初始化安卓轻量级XML存储器>>>>>");
        mSettings = new MSharedPreferences(this);
        String property = System.getProperty("java.library.path");
        timer.lap("111");
        Log.i("TAG", "路径：" + property);

        Log.i("TAG", "开始打开数据库资源>>>>>");
        openDataBase();
        Log.i("TAG", "结束打开数据库资源<<<<<");

        //******启用定时任务************
        scheduleTask();

        //*********开启状态机************
        new StateMachine().start();


        //********************下载新版本APP*************************************
        //****(要在MainActivity加载后启动，因为这里边逻辑涉及到页面变化，否则会报错)***
//        ApkDownloader pvd = new ApkDownloader();
//        pvd.setTransferProgressListener(new TransferProgressListener() {
//            @Override
//            public void showDataTransferDialog() {
//                Log.i("pvd", "提示程序开始下载");
//            }
//
//            @Override
//            public void updateDataTransferDialog(Integer... values) {
//                Log.i("pvd", "程序更新进度提示");
//            }
//
//            @Override
//            public void onFinishDataTransfer(int result) {
//                if (result == NetworkResultCode.SUCCESSFUL) {
//                    Log.i("pvd", "程序更新成功结束");
//                } else {
//                    Log.i("pvd", "程序更新失败" + NetworkResultCode.getResourceKeyByErrorCode(result));
//                }
//            }
//        });
//        pvd.direct();
//        pvd.setTransferProgressListener(null);
        //********************************************************************


        //初始化安卓崩溃日志记载
        CrashUtils.init("/data/data/com.example.knowledge_android/logfiles");

        //记录APP信息
        loadApplicationInfo(this);

        //设置屏幕方向
        setupScreenOrientation();

        //国际化
        I18n2.setLocaleResources(getI18Maps());
        Locale locale = Locale.getDefault();
        if (locale.getCountry().equals(Locale.TAIWAN.getCountry())) {
            I18n.setLocale(locale);
        } else {
            //如果要使用不同于desktop pos的文字，可使用I18n.setLocale(Locale locale, Map resources)
            I18n.setLocale(Locale.CHINA);
        }
    }


    public void openApkDownload() {
        ApkDownloader pvd = new ApkDownloader();
        pvd.setTransferProgressListener(new TransferProgressListener() {
            @Override
            public void showDataTransferDialog() {
                Log.i("pvd", "提示程序开始下载");
            }

            @Override
            public void updateDataTransferDialog(Integer... values) {
                Log.i("pvd", "程序更新进度提示");
            }

            @Override
            public void onFinishDataTransfer(int result) {
                if (result == NetworkResultCode.SUCCESSFUL) {
                    Log.i("pvd", "程序更新成功结束");
                } else {
                    Log.i("pvd", "程序更新失败" + NetworkResultCode.getResourceKeyByErrorCode(result));
                }
            }
        });
        pvd.direct();
        pvd.setTransferProgressListener(null);
    }


    public static OneApplication getInstance() {
        return instance;
    }


    public void openDataBase() {
        iDatabasePlusHelper = new MobileDatabasePlusHelper(this);
        DaoLocatorPlus.build(iDatabasePlusHelper);
    }


    void scheduleTask() {
        scheduledExecutor.scheduleWithFixedDelay(() -> {
            try {
                Log.i("TAG", "alex is the best");
            } catch (Exception ex) {
                Log.e("TAG", "scheduleGetDate", ex);
            }
        }, 1, 5, TimeUnit.MINUTES);
    }


    private void setupScreenOrientation() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity a, Bundle savedInstanceState) {
                a.setRequestedOrientation(mSettings.isScreenOrientationLandscape() ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE :
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                "unspecified"	默认值，由系统来选择方向。它的使用策略，以及由于选择时特定的上下文环境，可能会因为设备的差异而不同。
//                "user"	使用用户当前首选的方向。
//                "behind"	使用Activity堆栈中与该Activity之下的那个Activity的相同的方向。
//                "landscape"	横向显示（宽度比高度要大）
//                "portrait"	纵向显示（高度比宽度要大）
//                "reverseLandscape"	与正常的横向方向相反显示，在API Level 9中被引入。
//                "reversePortrait"	与正常的纵向方向相反显示，在API Level 9中被引入。
//                "sensorLandscape"	横向显示，但是基于设备传感器，既可以是按正常方向显示，也可以反向显示，在API Level 9中被引入。
//                "sensorPortrait"	纵向显示，但是基于设备传感器，既可以是按正常方向显示，也可以反向显示，在API Level 9中被引入。
//                "sensor"	显示的方向是由设备的方向传感器来决定的。显示方向依赖与用户怎样持有设备；当用户旋转设备时，显示的方向会改变。但是，默认情况下，有些设备不会在所有的四个方向上都旋转，因此要允许在所有的四个方向上都能旋转，就要使用fullSensor属性值。
//                "fullSensor"	显示的方向（4个方向）是由设备的方向传感器来决定的，除了它允许屏幕有4个显示方向之外，其他与设置为“sensor”时情况类似，不管什么样的设备，通常都会这么做。例如，某些设备通常不使用纵向倒转或横向反转，但是使用这个设置，还是会发生这样的反转。这个值在API Level 9中引入。
//                "nosensor"	屏幕的显示方向不会参照物理方向传感器。传感器会被忽略，所以显示不会因用户移动设备而旋转。除了这个差别之外，系统会使用与“unspecified”设置相同的策略来旋转屏幕的方向。
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    private void openMqtt() throws Exception {
        String storeId = "208888";
        //mqtt调用(进行主题订阅)
        Log.i("TAG", "start Android Mqtt");
        AndroidMqttService androidMqttService = new AndroidMqttService();
        String clientId = "pos_" + storeId + "_" + DevAddrUtil.getLocalMacIdFromIp();//规则:
        List<String> topicList = new ArrayList<>();
        topicList.add("pos_sale_status_" + storeId);//菜单更新mqtt
        topicList.add("pos_log_upload_" + storeId);//菜单更新mqtt
        androidMqttService.connectMqttService(mainActivity, topicList, "ssl://123.6.65.230:8888", clientId, "");
    }


    private boolean isActivityActive() {
        return mainActivity != null && mainActivity.hasWindowFocus();
    }

    public String getResourcePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }


    int screenWidth; //屏幕宽
    int screenHeight;//屏幕高

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }


    private void uploadLog() {
        try {
            String fileName = "/data/data/hyi.mobilepos/logfiles/hyi.mobilepos.log";
            if (new File(fileName).exists()) {
                //Log.info("begin Upload logFile");
                //LogFileUploader.runSynchronized(fileName);
                //Log.info("Upload logFile succeed");
            }
        } catch (Exception e) {
            //PosLog.error("Upload logFile fail", e);
        }
        try {
            String fileName = "/data/data/hyi.mobilepos/logfiles/hyi.state.log";
            if (new File(fileName).exists()) {
                //PosLog.info("begin Upload logFile");
                //LogFileUploader.runSynchronized(fileName);
                //PosLog.info("Upload logFile succeed");
            }
        } catch (Exception e) {
            //PosLog.error("Upload logFile fail", e);
        }
        try {
            String fileName = "/data/data/hyi.mobilepos/logfiles/" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".log";
            if (new File(fileName).exists()) {
                //PosLog.info("begin Upload  logFile");
                //LogFileUploader.runSynchronized(fileName);
                //PosLog.info("Upload  logFile succeed");
            }
        } catch (Exception e) {
            //PosLog.error("Upload  logFile fail", e);
        }
        try {
            File path = new File("/data/data/hyi.mobilepos/logfiles");
            File[] crashFiles = path.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".txt");
                }
            });
            for (File file : crashFiles) {
                if (PosConstants.SUCCESSFUL == 1 /*LogFileUploader.runSynchronized(file.getAbsolutePath())*/) {
                    file.renameTo(new File(file.getAbsolutePath() + ".sent"));
                }
            }
        } catch (Exception e) {
        }
    }


    /**
     * 因为onTerminate()会在Android的模拟器上触发回调。
     * 说它不是这样，是因为onTerminate()不会在Android真实的机器设备上触发。
     */
//    private ServiceConnection mStartupServiceConnection;
//    @Override
//    public void onTerminate() {
//        super.onTerminate();
//        if (iDatabasePlusHelper.getMasterDatabaseHelper() != null) {
//            OpenHelperManager.releaseHelper();
//            mobileDatabaseHelper.setMasterDatabaseHelper(null);
//        }
//        if (mobileDatabaseHelper.getTransDatabaseHelper() != null) {
//            mobileDatabaseHelper.getTransDatabaseHelper().close();
//            mobileDatabaseHelper.setTransDatabaseHelper(null);
//        }
//
//        if (mStartupServiceConnection != null) {
//            unbindService(mStartupServiceConnection);
//            mStartupServiceConnection = null;
//        }
//        //DaoLocator.setPosTerminalApplication(null);
//    }
    public void playNotificationSound() {
        try {
            //1.获取铃声类型
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            //2.获取铃声
            Ringtone r = RingtoneManager.getRingtone(this, notification);
            //3.播放铃声
            r.play();
        } catch (Exception ignore) {
        }
    }

    public AlertDialog.Builder getOkCancelDialogBuilder(Context context, int title, int message) {
        //AlertDialog.Builder builder = new AlertDialog.Builder(context, android.R.style.Theme_Holo_Dialog);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        builder.setTitle(title);
        builder.setMessage(Html.fromHtml(getResources().getString(message)));
        builder.setCancelable(false);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setNegativeButton(android.R.string.no, (dialog, which) -> {
            // default do nothing
        });
        return builder;
    }


    //获取app目录下，build.gradle里边配置的，defaultConfig{ versionName }
    String appVer;

    public String getAppVersion() {
        try {
            if (appVer != null) {
                return appVer;
            } else {
                appVer = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.i("TAG", e.getMessage());
        }
        return appVer;
    }


    //获取app目录下，build.gradle里边配置的，defaultConfig{ versionName }
    String appVerCode;

    public String getAppVersionCode() {
        try {
            if (appVerCode != null) {
                return appVerCode;
            } else {
                appVerCode = Integer.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionCode).toString();
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.i("TAg", e.getMessage());
            return "";
        }
        return appVerCode;
    }


    /**
     * 获取WiFi是否打开了！
     */
    public boolean isWiFiON() {
        WifiManager wifiMan = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMan.getConnectionInfo();
        return wifiInfo != null && wifiInfo.getIpAddress() != 0;
    }


    public void reboot() {
        this.iDatabasePlusHelper.close();
        //重启到fastboot模式
        PowerManager pManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        pManager.reboot("重启");
    }

    public boolean shutdown() {
        this.iDatabasePlusHelper.close();
        try {
            ShellUtils.CommandResult result = ShellUtils.execCmd("reboot -p", true);
            if (result.result == 0) {
                return true;
            }
            Utils.getApp().startActivity(IntentUtils.getShutdownIntent());
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    private void loadApplicationInfo(Context context) {
        try {
            //获取包管理器,这个数据来自AndroidManifest.xml中配置的meta-data
            PackageManager pm = context.getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle metaData = info.metaData;
            bridgeType = (String) metaData.get("bridgeType");
            deviceType = (String) metaData.get("deviceType");
            screenType = (String) metaData.get("screenType");
        } catch (Exception e) {
        }
    }

    public Map getI18Maps() {
        List<Map> chinaList = new ArrayList(3);
        chinaList.add(I18n_CommonResource_zh_CN.getStringResources());
        chinaList.add(I18n_CommonResourceLawson_zh_CN.getStringResources());
        chinaList.add(I18n_MobileResource_zh_CN.getStringResources());
        List<Map> taiwanList = new ArrayList(3);
        taiwanList.add(I18n_CommonResource_zh_TW.getStringResources());
        taiwanList.add(I18n_CommonResourceLawson_zh_TW.getStringResources());
        chinaList.add(I18n_MobileResource_zh_TW.getStringResources());
        Map map = new HashMap();
        map.put(Locale.CHINA.getCountry(), chinaList);
        map.put(Locale.TAIWAN.getCountry(), taiwanList);
        return map;
    }


    /**
     * 获取设备IP地址
     **/
    public String getDeviceIpAddressString() {
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo != null && wifiInfo.getIpAddress() != 0) {
            int ip = wifiInfo.getIpAddress();
            String ipString = String.format("%d.%d.%d.%d",
                    (ip & 0xff),
                    (ip >> 8 & 0xff),
                    (ip >> 16 & 0xff),
                    (ip >> 24 & 0xff));
            return ipString;
        } else {
            return "";
        }
    }

    public boolean pingOuterNet() {
        boolean result;
        try {
            String ip = "www.baidu.com"; // ping的地址，可以换成任何一种可靠的外网
            Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping网址3次

            // 读取ping的内容,可以不加
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();

            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
            //PosLog.info("------ping-----result content : " + stringBuffer.toString());

            final int status = p.waitFor();
            if (status == 0) {
                //PosLog.info("morse -> ping onSuccess!");
                result = true;
            } else {
                //PosLog.info("morse -> ping onFailure!");
                result = false;
            }
        } catch (IOException e) {
            //PosLog.info("morse -> ping onFailure!");
            result = false;
        } catch (InterruptedException e) {
            //PosLog.info("morse -> ping onFailure!");
            result = false;
        } finally {
        }
        return result;
    }

    private boolean sotPingServer(String domain) {
        try {
            URL posHubDomain = new URL(domain);
            HttpURLConnection http = (HttpURLConnection) posHubDomain.openConnection();
            return http.getResponseCode() == HttpURLConnection.HTTP_OK;
        } catch (Exception ex) {
            Log.e("TAG", "无法连接poshub服务器{}" + domain);
        }
        return false;
    }

    public static boolean isNetworkOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("ping -c 3 114.114.114.114");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Typeface getUserFont() {
        this.mUserFont = Typeface.createFromAsset(getResources().getAssets(), USER_FONT);
        return mUserFont;
    }

    public IPosScreen getPosScreen() {
        return posScreen;
    }

    public void setPosScreen(IPosScreen posScreen) {
        this.posScreen = posScreen;
    }

    public PosScreenMainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(PosScreenMainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public MSharedPreferences getmSettings() {
        return mSettings;
    }

    public void setmSettings(MSharedPreferences mSettings) {
        this.mSettings = mSettings;
    }

    public IDatabasePlusHelper getiDatabasePlusHelper() {
        return iDatabasePlusHelper;
    }

    public void setiDatabasePlusHelper(IDatabasePlusHelper iDatabasePlusHelper) {
        this.iDatabasePlusHelper = iDatabasePlusHelper;
    }

    public String getBridgeType() {
        return bridgeType;
    }

    public void setBridgeType(String bridgeType) {
        this.bridgeType = bridgeType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }


    //获取今天星期几
    private final static String[] weekNames = new String[]{"日", "一", "二", "三", "四", "五", "六"};

    public void getWeek() {
        Calendar now = Calendar.getInstance();
        String wk = weekNames[(now.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY)];
        System.out.println("今天星期几：" + wk);
    }

}
