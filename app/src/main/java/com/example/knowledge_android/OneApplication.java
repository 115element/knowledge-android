package com.example.knowledge_android;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.example.knowledge_android.daosupport_annotation.base.IDatabasePlusHelper;
import com.example.knowledge_android.daosupport_annotation.base.MobileDatabasePlusHelper;
import com.example.knowledge_android.daosupport_annotation.daohelp.DaoLocatorPlus;
import com.example.knowledge_android.drawscreen.PosScreen;
import com.example.knowledge_android.msharedpreferences.MSharedPreferences;
import com.example.knowledge_android.statemachine.StateMachine;

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

    private PosScreen posScreen;


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

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        Log.i("TAG", "应用程序加载。。。。");

        mSettings = new MSharedPreferences(this);
        String property = System.getProperty("java.library.path");
        Log.i("TAG", "路径：" + property);

        Log.i("TAG","开始打开数据");
        openDataBase();
        Log.i("TAG","完毕打开数据");

        //启用定时任务
        scheduleGetDate();

        new StateMachine().start();
    }

    public static OneApplication getInstance() {
        return instance;
    }


    public void openDataBase(){
        iDatabasePlusHelper = new MobileDatabasePlusHelper(this);
        DaoLocatorPlus.build(iDatabasePlusHelper);
    }


    void scheduleGetDate() {
        scheduledExecutor.scheduleWithFixedDelay(() -> {
            try {
                Log.i("TAG","alex is the best");
            } catch (Exception ex) {
                Log.e("TAG","scheduleGetDate", ex);
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

    public Typeface getUserFont() {
        this.mUserFont = Typeface.createFromAsset(getResources().getAssets(), USER_FONT);
        return mUserFont;
    }

    public PosScreen getPosScreen() {
        return posScreen;
    }

    public void setPosScreen(PosScreen posScreen) {
        this.posScreen = posScreen;
    }
}
