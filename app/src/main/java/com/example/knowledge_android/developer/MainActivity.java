package com.example.knowledge_android.developer;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;

import com.example.knowledge_android.R;


public class MainActivity extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionUtils.isGrantExternalRW(this, 1);//android9.0获取读写和相机权限
        setContentView(R.layout.activity_main);


        /** 添加堆栈改变监听器*/
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Log.i("TAG", "onBackStackChanged....");
                Fragment fragmentById = supportFragmentManager.findFragmentById(R.id.container);
                fragmentById.setUserVisibleHint(true);/**告诉系统该Fragment可见*/

                //以下代码可替代，以上废弃的方法。
                //FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                //fragmentTransaction.setMaxLifecycle(fragmentById, Lifecycle.State.RESUMED).commit();
            }
        });

        //展示主界面，也就是切换Fragment
        showMainScreen(savedInstanceState);

    }


    /***展示主登录界面,切换已有的Fragment*/
    public void showMainScreen(Bundle savedInstanceState) {
        MainPosScreen mainPosScreen = new MainPosScreen();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, mainPosScreen, "currentFragment");
        fragmentTransaction.commit();
    }


    public static class PermissionUtils { //获取读写相机的权限(android9.0)
        private static String[] PERMISSIONS_CAMERA_AND_STORAGE = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};

        @RequiresApi(api = Build.VERSION_CODES.M)
        public static boolean isGrantExternalRW(Activity activity, int requestCode) {
            if (Build.VERSION.SDK_INT >= 21) {
                int storagePermission = activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                int cameraPermission = activity.checkSelfPermission(Manifest.permission.CAMERA);
                if (storagePermission != PackageManager.PERMISSION_GRANTED ||
                        cameraPermission != PackageManager.PERMISSION_GRANTED) {
                    activity.requestPermissions(PERMISSIONS_CAMERA_AND_STORAGE, requestCode);
                    return false;
                }
            }
            return true;
        }
    }
}
