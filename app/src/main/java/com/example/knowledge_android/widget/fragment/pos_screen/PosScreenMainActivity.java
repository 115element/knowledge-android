package com.example.knowledge_android.widget.fragment.pos_screen;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.arasthel.swissknife.annotations.OnUIThread;
import com.example.knowledge_android.OneApplication;
import com.example.knowledge_android.R;
import com.example.knowledge_android.msharedpreferences.MSharedPreferences;
import com.example.knowledge_android.widget.fragment.pos_screen.fragment.MessageDialog;
import com.example.knowledge_android.widget.fragment.pos_screen.posmainfragment.IPosScreen;
import com.example.knowledge_android.widget.fragment.pos_screen.posmainfragment.PosScreen1;
import com.example.knowledge_android.widget.fragment.pos_screen.fragment.ProtectorFragment;
import com.example.knowledge_android.widget.fragment.pos_screen.fragment.SignOnFragment;
import com.example.knowledge_android.widget.fragment.pos_screen.posmainfragment.PosScreen2;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PosScreenMainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout; /**安卓侧滑菜单**/
    /**
     * 碎片（Fragment）是一种可以嵌入在活动当中的UI片段
     **/
    //PosScreen posScreen; /**碎片（Fragment）是一种可以嵌入在活动当中的UI片段**/
    Fragment fragment;

    private boolean FULLSCREEN = true;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //final TranHead tranHead = (TranHead) savedInstanceState.getSerializable('tranHead')
        //app.currentTransaction = tranHead

//        final TranHead tranHead = app?.currentTransaction
//        if (tranHead) { // if transaction data still exists in memory
//            ((AbstractAndriodPosScreen) app.posScreen)?.setItemListTranHead(tranHead)
//        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PermissionUtils.isGrantExternalRW(this, 1);//android9.0获取读写和相机权限

        if (FULLSCREEN) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }

        //设置当前界面
        OneApplication.getInstance().setMainActivity(this);
        setContentView(R.layout.activity_main);

        //myTimeHandler.sendEmptyMessageDelayed(0, 1000);

        // getSupportFragmentManager()主要用于支持3.0以下android系统API版本，
        // 3.0以上系统可以直接调用getFragmentManager() ，因为fragment是3.0以后才出现的组件
        // FragmentManager顾名思义是用来管理fragment的.
        // getSupportFragmentManager()就是获取所在fragment 的父容器的管理器，getFragmentManager()同理。
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        /** 添加Fragment栈改变监听器*/
        supportFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Log.i("TAG", "onBackStackChanged....");
                Fragment fragmentById = supportFragmentManager.findFragmentById(R.id.container);
                fragmentById.setUserVisibleHint(true);/**告诉系统该Fragment可见*/
            }
        });

        //创建PosScreen[Fragment]
        PosScreen1 posScreen = new PosScreen1();
        fragment = posScreen;
        OneApplication.getInstance().setPosScreen(posScreen);

        //将当前Fragment替换为另外一个Fragment
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, "currentFragment"); //
        fragmentTransaction.commit();

        // 进入初始界面
        showInitialScreen(savedInstanceState);

        Log.i("TAG","Screen Width Pixel = " + getScreenWidthPixels());
        Log.i("TAG","Screen Height Pixel = " + getScreenHeightPixels());

        OneApplication.getInstance().setScreenWidth(getScreenWidthPixels());
        OneApplication.getInstance().setScreenHeight(getScreenHeightPixels());

        //WxFacePayApi.instance.initWxPay();
        Log.i("TA","Out of onCreate");

        //打开新版本APK下载。
        OneApplication.getInstance().openApkDownload();


        // https://docs.sunmi.com/others/scan-code-driver/
//        Intent intent = new Intent("com.summi.scan");
//        intent.setPackage("com.sunmi.sunmiqrcodescanner");
//        // // 识别反色二维码，默认true
//        intent.putExtra("IDENTIFY_INVERSE_QR_CODE", true);
//        // // 识别画面中多个二维码，默认false
//        intent.putExtra("IDENTIFY_MORE_CODE", false);
//        intent.putExtra("SOURCE_STATE", "SummaryState");
//        PosScreenMainActivity mainActivity1 = OneApplication.getInstance().getMainActivity();
//        mainActivity1.startActivityForResult(intent,2);
//        为了降低开发难度，商米在最新的SUNMI OS(V1固件版本187，M1固件版本37)系统中内置了一个扫码的模块，
//        开发者在项目需要调用扫码的地方通过startActivityForResult()调用商米的扫码模块，
//        然后在onActivityResult()方法中接受扫码结果返回值。
    }


    /*
      我们在android开发中经常会用到fragment，例如侧拉栏的切换，viewPager的切换。
      而我们切换fragment无非就两种方法：
              1.replace（）；
              2.add（），hide（），show（）；
      两种发方法相比较而言，我更加推荐使用第二种方法，因为fragmentTransaction的replace（）方法实际上就是remove（）和add（）的集合，
      每一次fragment的切换都要销毁视图，然后重新创建一个fragment实例，调用fragment的整个生命周期，这样对于性能上来说不是很好。

      以下是使用第二种切换fragment方法的函数：
     */
    private void changeFragment(Fragment fromFragment, Fragment toFragment) {
        if (fromFragment != toFragment) {
            fromFragment = toFragment;
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (toFragment.isAdded() == false) {
            ft.hide(fromFragment).add(R.id.container, toFragment).commit();
        } else {
            ft.hide(fromFragment).show(toFragment).commit();
        }
    }


    private void setupNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        // Setup header view and the logo image inside, logo image is in store table
        final View headerView = getLayoutInflater().inflate(R.layout.nav_header, null);
//        final Bitmap logoImageBitmap = DaoLocator.getStoreDao().queryLogoImage()
//        if (logoImageBitmap != null) {
//            ImageView logoImageView = (ImageView) headerView.findViewById(R.id.logo_image)
//            logoImageView.setImageBitmap(logoImageBitmap)
//        }
        ((TextView) headerView.findViewById(R.id.app_name_and_version)).setText(
                getString(R.string.app_title) + getAppVersion());

        navigationView.addHeaderView(headerView);
    }

    String getAppVersion() {
        try {
            String appVer = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            return " (Ver " + appVer + ")";
        } catch (PackageManager.NameNotFoundException ignored) {
            return "";
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                } else if (drawerLayout != null) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                if (drawerLayout != null)
//                    drawerLayout.openDrawer(GravityCompat.START)
//                return true
//        }
//        return super.onOptionsItemSelected(item)
    }

    private int currentMode = 0;
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        final int menuId = menuItem.getItemId();
        if (currentMode != menuId) {
            MSharedPreferences mSharedPreferences = OneApplication.getInstance().getmSettings();
            switch (menuId) {
                case R.id.action_pos_terminal:
                    showInitialScreen(null);
                    //currentMode = R.id.action_pos_terminal
                    break;
                case R.id.action_shift_in:
                    //shiftIn();
                    break;
                case R.id.action_shift:
                    //shiftOut();
                    break;
            }
        }
        if (drawerLayout != null) {
            drawerLayout.closeDrawers();
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (fragment == null){
            super.onBackPressed();
        }
    }

    void popBackStack() {
        getSupportFragmentManager().popBackStack();
    }

    void clickMenuItemForShowingInitialScreen() {
        final NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.getMenu().performIdentifierAction(R.id.action_pos_terminal, 0);
        navigationView.setCheckedItem(R.id.action_pos_terminal);
    }

    /**
     * 获取Android系统参数
     **/
    void getAndroiodScreenProperty() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();

        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;  // 屏幕宽度（像素）
        int height = dm.heightPixels; // 屏幕高度（像素）
        float density = dm.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        //int densityDpi = dm.densityDpi //屏幕密度dpi（120 / 160 / 240）
        //屏幕宽度算法:屏幕宽度（像素）// 屏幕密度
        int screenWidth = (int) (width / density);//屏幕宽度(dp)
        int screenHeight = (int) (height / density);//屏幕高度(dp)
    }

    int getScreenWidthPixels() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;   // 屏幕宽度（像素）
    }

    int getScreenHeightPixels() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;// 屏幕高度（像素）
    }

    int getScreenWidthDp() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;  // 屏幕宽度（像素）
        float density = dm.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        return (int) (width / density);//屏幕宽度(dp)
    }

    int getScreenHeightDp() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels ; // 屏幕高度（像素）
        float density = dm.density  ;  // 屏幕密度（0.75 / 1.0 / 1.5）
        return (int) (height / density) ;//屏幕高度(dp)
    }


    @Override
    public void onClick(View view) {
        //if (view.getId() == R.id.fab) {
        //    Snackbar
        //        .make(findViewById(R.id.coordinatorLayout), "This is Snackbar", Snackbar.LENGTH_LONG)
        //        .setAction("Action", this)
        //        .show() // Don't forget to show!
        //}
    }


    /**
     * Handler是Android SDK来处理异步消息的核心类。
     * 子线程与主线程通过Handler来进行通信。子线程可以通过Handler来通知主线程进行UI更新。
     **/
//    private Handler myTimeHandler = new Handler() {
//        void handleMessage(Message msg) {
//            if (msg.what == 0) {
//                sendEmptyMessageDelayed(0, 1000);
//            }
//        }
//    };
    public void showWarningMessage(String message) {
        showWarningMessage(message, null);
    }

    public void showWarningMessage(int message, int title) {
        showWarningMessage(getString(message), getString(title), null);
    }

    public void showWarningMessage(String message, String title) {
        showWarningMessage(message, title, null);
    }

    public void showWarningMessage(String message, String title, DialogInterface.OnClickListener buttonListener) {
        MessageDialog warningFragment = MessageDialog.newInstance(MessageDialog.TYPE_WARNING,
                title == null ? getString(R.string.warning_dialog_title) : title, message);
        warningFragment.setButtonListener(buttonListener);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        warningFragment.show(fragmentTransaction, "warning_dialog");
    }


    //重启APP
    @OnUIThread
    public void restartApplication() {
        OneApplication.getInstance().getiDatabasePlusHelper().close();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }, 1000);
    }


    public void showMessageOnSnackbar(String message) {
        Snackbar snackBar = Snackbar.make(findViewById(R.id.container), message, Snackbar.LENGTH_LONG);
        View view = snackBar.getView(); // 获取SnackBar的view
        if (view != null) {
            view.setBackgroundColor(Color.DKGRAY); // 修改view的背景色
            TextView tv = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);
            // 获取SnackBar的message控件
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(25);
            snackBar.show();
        }
    }

    /**
     * 从底部显示Snackbar信息，可以向右滑动来关闭信息。
     */
    public void showMessageOnSnackbar(int message) {
        int textColor = getResources().getColor(R.color.my_primary_text);
        Snackbar snackbar = Snackbar.make(findViewById(R.id.container), message, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTextColor(textColor);
        tv.setTextSize(28);
        snackbar.show();
    }

    public Snackbar createMessageOnSnackbar(int message) {
        int textColor = getResources().getColor(R.color.my_primary_text);
        Snackbar snackbar = Snackbar.make(findViewById(R.id.container), message, Snackbar.LENGTH_INDEFINITE);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTextColor(textColor);
        tv.setTextSize(28);
        return snackbar;
    }


    //*********************************Activity中切换Fragment的技术************************************************************
    Fragment protectorFragment;
    Fragment signOnFragment;

    public void showProtectorScreen() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (protectorFragment == null) {
            protectorFragment = new ProtectorFragment();
        }
        fragmentTransaction.replace(R.id.container, protectorFragment, "protectorFragment");
        fragmentTransaction.commit();
        this.protectorFragment = protectorFragment;
    }

    public void showSignOnScreen() {
        if (signOnFragment == null)
            signOnFragment = new SignOnFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, signOnFragment, "currentFragment")
                .commit();
        this.signOnFragment = signOnFragment;
    }


    /**
     * 进入初始界面.
     */
    public void showInitialScreen(Bundle savedInstanceState) {
//        final Settings settings = app.settings

        //showPlayGround(savedInstanceState)

        // 进入初始界面 --  UI
        showPosScreen(savedInstanceState);

        //if (settings.getShouQuanflag() != "ok") {
        //    showProtectorScreen()
        //} else

//        if (settings.getCurrentCashier() == null) {
//            showSignOnScreen()
//
//        } else {
//            //if (settings.isDiningTableManagement()) {
//            //    showDiningTable()
//            //} else {
//
//            // 生成一个空的current transaction
//            //app.createNewCurrentTransaction()
//
//            // 对于先点先结系统，给一个空的DiningTable
//            //showPosTerminal(null)
//
//            showPlayGround(savedInstanceState)
//        }
        logScreenInfo();
    }


    /***展示主登录界面,Fragment切换*/
    public void showPosScreen(Bundle savedInstanceState) {
        hideSoftKeyboard();

        final FragmentManager fragmentManager = getSupportFragmentManager();
        IPosScreen posScreen = null;
        if (savedInstanceState != null) {
            Log.i("SS", "Get posScreen from fragmentManager");
            posScreen = (IPosScreen) fragmentManager.findFragmentByTag("currentFragment");
        } else {
            Log.i("TAG", "Create new posScreen screen={}|device={}|bridge={}}");
            if ("sunss".equals("sunss")) {
                posScreen = new PosScreen2();
            }

            OneApplication.getInstance().setPosScreen(posScreen);
            //POSTerminalApplication.instance.addTransactionListener((TransactionListener) posScreen)
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, (Fragment) posScreen, "currentFragment");
            fragmentTransaction.commit();
        }
    }

    void hideSoftKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void logScreenInfo() {
        String density = getScreenDensityName();
        String sizeName = getScreenSizeName();
        Log.i("TT", "screen size name = $sizeName " + sizeName);
        Log.i("TT", "screnn density = $density " + density);
        //Toast.makeText(this, "$sizeName / $density", Toast.LENGTH_LONG).show()
    }

    private String getScreenDensityName() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int density = metrics.densityDpi;
        switch (density) {
            case DisplayMetrics.DENSITY_HIGH:
                return "hdpi:" + String.valueOf(density);
            case DisplayMetrics.DENSITY_MEDIUM:
                return "mdpi:" + String.valueOf(density);
            case DisplayMetrics.DENSITY_LOW:
                return "ldpi:" + String.valueOf(density);
            case DisplayMetrics.DENSITY_XHIGH:
                return "xhdpi:" + String.valueOf(density);
            case DisplayMetrics.DENSITY_TV:
                return "tv:" + String.valueOf(density);
            case DisplayMetrics.DENSITY_XXHIGH:
                return "xxhdpi:" + String.valueOf(density);
            case DisplayMetrics.DENSITY_XXXHIGH:
                return "xxxhdpi:" + String.valueOf(density);
            default:
                return "DENSITY_UNKNOWN:" + String.valueOf(density);
        }
    }

    private String getScreenSizeName() {
        int screenLayout = getApplicationContext().getResources().getConfiguration().screenLayout;
        screenLayout &= Configuration.SCREENLAYOUT_SIZE_MASK;
        switch (screenLayout) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                return "small";
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                return "normal";
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                return "large";
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                return "xlarge";
            default:
                return "undefined";
        }
    }


    public void hideProtectorScreen() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(protectorFragment);
        fragmentTransaction.commit();
        protectorFragment = null;
    }

    public void hideSignOnScreen() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(signOnFragment);
        fragmentTransaction.commit();
        //floorPlanFragment = null
    }
//*********************************************************************************************


//    void showCategoryForm() {
//        if (categoryFormFragment == null)
//            categoryFormFragment = new CategoryFormFragment()
//
//        supportFragmentManager.beginTransaction()
//            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
//            .add(R.id.container, categoryFormFragment, 'currentFragment')
//            .addToBackStack(null)
//            .commit()
//        activeFragment = null
//    }

//    void showImageChooser() {
//        if (imageChooserFragment == null)
//            imageChooserFragment = new ImageChooserFragment()
//
//        supportFragmentManager.beginTransaction()
//                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
//                .add(R.id.container, imageChooserFragment, 'currentFragment')
//                .addToBackStack(null)
//                .commit()
//        activeFragment = null
//    }

//    void hideFloorPlan(Map<Integer, Fragment> floorPlanAreaFragments) {
//        //FragmentManager supportFragmentManager = getSupportFragmentManager()
//        //supportFragmentManager.popBackStack()
//
//        //FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction()
//        //fragmentTransaction.remove(floorPlanFragment)
//        //fragmentTransaction.remove(floorPlanAreaFragments.get(0))
//        //fragmentTransaction.remove(floorPlanAreaFragments.get(1))
//        //fragmentTransaction.commit()
//        //floorPlanFragment = null
//    }

    @Override
    public void onResume() {
        Log.i("TT", "MainActivity onResume currentStateClass ---");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i("AAA", "MainActivity onPause --- closeScanner!");
        super.onPause();
        try {
            //app?.closeScanners()
        } catch (Throwable e) {
            Log.e("FG", "Activity onPause --- closeScanner fail", e);
        } finally {
            Log.i("GB", "MainActivity onPause --- closeScanner finished");
        }
    }


    boolean isClickShift = false;//是否按了shift键

    /**
     * Intercept all key events and forward to KeyboardScanner.
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_SHIFT_LEFT) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                isClickShift = true;
            } else if (event.getAction() == KeyEvent.ACTION_UP) {
                isClickShift = false;
            }
        }
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() != KeyEvent.KEYCODE_SHIFT_LEFT) {
            //log.info("dispatchKeyEvent(): displayLabel=${event.displayLabel} keyCode=${event.keyCode}")
            //final scanner = hyi.mobilepos.device.KeyboardScanner.instance
            //if (scanner)
            //scanner.addData(event.displayLabel, isClickShift)
        }
        return super.dispatchKeyEvent(event);
    }


    /**
     * 通过socket检查外网的连通性
     */
    private static Socket s;

    public static boolean hasNetworkConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean connected = (null != activeNetworkInfo) && activeNetworkInfo.isConnected();
        if (!connected) return false;
        boolean routeExists;
        try {
            if (s == null) {
                s = new Socket();
            }
            InetAddress host = InetAddress.getByName("114.114.114.114");
            //国内使用114.114.114.114，如果全球通用google：8.8.8.8
            s.connect(new InetSocketAddress(host, 80), 5000);//google:53
            routeExists = true;
            s.close();
        } catch (IOException e) {
            routeExists = false;
        }
        return connected && routeExists;
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
