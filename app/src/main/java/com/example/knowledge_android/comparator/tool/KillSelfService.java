package com.example.knowledge_android.comparator.tool;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

/**
 * 安卓应用重启助手
 */

public class KillSelfService extends Service {

    /**
     * 关闭应用后多久重新启动
     */
    private static long stopDelayed = 50;
    private final Handler handler;
    private String PackageName;

    KillSelfService() {
        handler = new Handler();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        stopDelayed = intent.getLongExtra("Delayed", 50);
        PackageName = intent.getStringExtra("PackageName");

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(PackageName);
                startActivity(LaunchIntent);
                KillSelfService.this.stopSelf();
            }
        }, stopDelayed);


        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
