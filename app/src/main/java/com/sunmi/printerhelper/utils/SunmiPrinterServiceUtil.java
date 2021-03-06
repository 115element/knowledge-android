package com.sunmi.printerhelper.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.example.knowledge_android.R;
import com.sunmi.printerhelper.bean.TableItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import woyou.aidlservice.jiuiv5.ICallback;
import woyou.aidlservice.jiuiv5.IWoyouService;

/**
 * Aidl客户端用法实例
 */
public class SunmiPrinterServiceUtil {
    private static final String SERVICE_PACKAGE = "woyou.aidlservice.jiuiv5";
    private static final String SERVICE_ACTION = "woyou.aidlservice.jiuiv5.IWoyouService";
    private static final String TAG = "SunmiPrinterUtil";

    private static SunmiPrinterServiceUtil instance = new SunmiPrinterServiceUtil();

    private IWoyouService woyouService;
    private Context context;

    private Runnable printerReadyCallback;

    private SunmiPrinterServiceUtil() {
    }

    public static SunmiPrinterServiceUtil getInstance() {
        return instance;
    }

    /**
     * 连接服务
     *
     * @param context context
     */
    public void connectPrinterService(Context context, Runnable printerReadyCallback) {
        this.context = context.getApplicationContext();
        this.printerReadyCallback = printerReadyCallback;
        Intent intent = new Intent();
        intent.setPackage(SERVICE_PACKAGE);
        intent.setAction(SERVICE_ACTION);
        context.getApplicationContext().startService(intent);
        context.getApplicationContext().bindService(intent, connService, Context.BIND_AUTO_CREATE);
    }

    /**
     * 断开服务
     *
     * @param context context
     */
    public void disconnectPrinterService(Context context) {
        if (woyouService != null) {
            context.getApplicationContext().unbindService(connService);
            woyouService = null;
        }
    }

    public boolean isConnected() {
        return woyouService != null;
    }

    private ServiceConnection connService = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            woyouService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            woyouService = IWoyouService.Stub.asInterface(service);
            if (printerReadyCallback != null) {
                printerReadyCallback.run();
            }
        }
    };

    public ICallback generateCB(final PrinterCallback printerCallback) {
        return new ICallback.Stub() {


            @Override
            public void onRunResult(boolean isSuccess) throws RemoteException {

            }

            @Override
            public void onReturnString(String result) throws RemoteException {
                printerCallback.onReturnString(result);
            }

            @Override
            public void onRaiseException(int code, String msg) throws RemoteException {

            }

            @Override
            public void onPrintResult(int code, String msg) throws RemoteException {

            }
        };
    }

    /**
     * 设置打印浓度
     */
    private int[] darkness = new int[] {0x0600, 0x0500, 0x0400, 0x0300, 0x0200, 0x0100, 0,
            0xffff, 0xfeff, 0xfdff, 0xfcff, 0xfbff, 0xfaff};

    public void setDarkness(int index) {
        if (woyouService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return;
        }

        int k = darkness[index];
        try {
            woyouService.sendRAWData(ESCUtil.setPrinterDarkness(k), null);
            woyouService.printerSelfChecking(null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取得打印机系统信息，放在list中
     *
     * @return list
     */
    public List<String> getPrinterInfo(PrinterCallback printerCallback) {
        if (woyouService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return null;
        }

        List<String> info = new ArrayList<>();
        try {
            woyouService.getPrintedLength(generateCB(printerCallback));
            info.add(woyouService.getPrinterSerialNo());
            info.add(woyouService.getPrinterModal());
            info.add(woyouService.getPrinterVersion());
            info.add(printerCallback.getResult());
            info.add("");
            //info.add(woyouService.getServiceVersion());
            PackageManager packageManager = context.getPackageManager();
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(SERVICE_PACKAGE, 0);
                if (packageInfo != null) {
                    info.add(packageInfo.versionName);
                    info.add(packageInfo.versionCode + "");
                } else {
                    info.add("");
                    info.add("");
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 初始化打印机
     */
    public boolean initPrinter() {
        if (woyouService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return false;
        }

        try {
            woyouService.printerInit(new ICallback() {
                @Override
                public void onRunResult(boolean isSuccess) throws RemoteException {

                }

                @Override
                public void onReturnString(String result) throws RemoteException {
                    Log.d(TAG, "printer init result = " + result);
                }

                @Override
                public void onRaiseException(int code, String msg) throws RemoteException {
                    Log.w(TAG, "printer init error = " + code + " | " + msg);
                }

                @Override
                public void onPrintResult(int code, String msg) throws RemoteException {

                }

                @Override
                public IBinder asBinder() {
                    return null;
                }
            });
        } catch (RemoteException e) {
            return false;
        }
        return true;
    }

    /**
     * 打印二维码
     */
    public void printQr(String data, int alignment, int modulesize, int errorlevel) {
        if (woyouService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return;
        }

        try {
            woyouService.setAlignment(alignment, null);
            woyouService.printQRCode(data, modulesize, errorlevel, null);
//            woyouService.lineWrap(3, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印条形码
     */
    public void printBarCode(String data, int symbology, int height, int width, int textposition) {
        if (woyouService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return;
        }


        try {
            woyouService.printBarCode(data, symbology, height, width, textposition, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印文字
     */
    public void printText(String content, float size, boolean isBold, boolean isUnderLine) {
        if (woyouService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return;
        }

        try {
            if (isBold) {
                woyouService.sendRAWData(ESCUtil.boldOn(), null);
            } else {
                woyouService.sendRAWData(ESCUtil.boldOff(), null);
            }

            if (isUnderLine) {
                woyouService.sendRAWData(ESCUtil.underlineWithOneDotWidthOn(), null);
            } else {
                woyouService.sendRAWData(ESCUtil.underlineOff(), null);
            }

            woyouService.printTextWithFont(content, null, size, null);
//            woyouService.lineWrap(1, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印图片(居中)
     */
    public void printBitmap(Bitmap bitmap) {
        if (woyouService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return;
        }
        try {
            woyouService.printBitmap(bitmap, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印图片和文字按照指定排列顺序
     */
    public void printBitmap(Bitmap bitmap, int orientation) {
        if (woyouService == null) {
            Toast.makeText(context, "服务已断开！", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            if (orientation == 0) {
                woyouService.printBitmap(bitmap, null);
                woyouService.printText("横向排列\n", null);
                woyouService.printBitmap(bitmap, null);
                woyouService.printText("横向排列\n", null);
            } else {
                woyouService.printBitmap(bitmap, null);
                woyouService.printText("\n纵向排列\n", null);
                woyouService.printBitmap(bitmap, null);
                woyouService.printText("\n纵向排列\n", null);
            }
            woyouService.lineWrap(3, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * 打印表格
     */
    public void printTable(LinkedList<TableItem> list) {
        if (woyouService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return;
        }

        try {
            for (TableItem tableItem : list) {
                Log.i("kaltin", "printTable: " + tableItem.getText()[0] + tableItem.getText()[1] + tableItem.getText()[2]);
                woyouService.printColumnsString(tableItem.getText(), tableItem.getWidth(), tableItem.getAlign(), null);
            }
            woyouService.lineWrap(3, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /*
     * 空打三行！
     */
    public void printEmptyLine(int count) {
        if (woyouService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return;
        }

        try {
            woyouService.lineWrap(count, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void sendRawData(byte[] data) {
        if (woyouService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return;
        }

        try {
            woyouService.sendRAWData(data, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
