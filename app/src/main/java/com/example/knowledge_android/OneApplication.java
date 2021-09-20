package com.example.knowledge_android;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.multidex.MultiDexApplication;

import com.blankj.utilcode.util.CrashUtils;
import com.example.knowledge_android.apkdownload.ApkDownloader;
import com.example.knowledge_android.apkdownload.NetworkResultCode;
import com.example.knowledge_android.apkdownload.TransferProgressListener;
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
import com.example.knowledge_android.fragment.pos_screen.PosScreenMainActivity;
import com.example.knowledge_android.fragment.pos_screen.posmainfragment.IPosScreen;
import com.example.knowledge_android.knowledge.RunTimer;
import com.example.knowledge_android.msharedpreferences.MSharedPreferences;
import com.example.knowledge_android.statemachine.StateMachine;

import java.util.ArrayList;
import java.util.HashMap;
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


    //定时任务工具类
    private ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

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


    public void openApkDownload(){
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
        }, 0, 5, TimeUnit.MINUTES);
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
}
