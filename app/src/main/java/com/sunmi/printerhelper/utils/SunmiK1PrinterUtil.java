package com.sunmi.printerhelper.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import com.example.knowledge_android.R;
import com.sunmi.extprinterservice.ExtPrinterService;
import com.sunmi.printerhelper.bean.TableItem;


public class SunmiK1PrinterUtil {
    private static final String SERVICE_PACKAGE = "com.sunmi.extprinterservice";
    private static final String SERVICE_ACTION = "com.sunmi.extprinterservice.PrinterService";
    private static final String TAG = "SunmiK1PrinterUtil";

    private static SunmiK1PrinterUtil instance = new SunmiK1PrinterUtil();

    private ExtPrinterService extPrinterService = null;//k1 打印服务
    private Context context;

    private ServiceConnection connService;

    private SunmiK1PrinterUtil() {
    }

    public static SunmiK1PrinterUtil getInstance() {
        return instance;
    }

    /**
     * 连接服务
     *
     * @param context context
     */
    public void connectPrinterService(Context context, Runnable printerReadyCallback) {
        this.context = context.getApplicationContext();
        Intent intent = new Intent();
        intent.setPackage(SERVICE_PACKAGE);
        intent.setAction(SERVICE_ACTION);
        context.getApplicationContext().startService(intent);
        connService = new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
                extPrinterService = null;
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                extPrinterService = ExtPrinterService.Stub.asInterface(service);
                printerReadyCallback.run();
            }
        };
        context.getApplicationContext().bindService(intent, connService, Context.BIND_AUTO_CREATE);
    }

    /**
     * 断开服务
     *
     * @param context context
     */
    public void disconnectPrinterService(Context context) {
        if (extPrinterService != null) {
            context.getApplicationContext().unbindService(connService);
            extPrinterService = null;
        }
    }

    public boolean isConnected() {
        return extPrinterService != null;
    }

    /**
     * 初始化打印机
     */
    public int initPrinter() throws Exception {
        if (extPrinterService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return -1;
        }

        try {
            return extPrinterService.printerInit();
        } catch (RemoteException e) {
            throw e;
        }
    }

    /**
     * 打印二维码
     * @param data 要打印的⼆维码内容，默认为utf-8字符集
     * @param alignment 0居左（默认）、1居中、2居右
     * @param modulesize ⼆维码块⼤⼩ 1-16 像素点
     * @param errorlevel ⼆维码纠错等级 0-3 四个等级
     */
    public int printQr(String data, int alignment, int modulesize, int errorlevel) throws Exception {
        if (extPrinterService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return -1;
        }

        try {
            if (alignment != 4) {
                extPrinterService.setAlignMode(alignment);
            }
            return extPrinterService.printQrCode(data, modulesize, errorlevel);
        } catch (RemoteException e) {
            throw e;
        }
    }

    /**
     * 打印条形码
     * @param data  条形码内容 （根据条码类型不同，条码内容需满⾜其格式）
     * @param type 条形码类型 0：UPC-A 1：UPC-E 2：EAN13 3：EAN8 4:CODE39 5:ITF 6:CODABAR 7:CODE93 8:CODE128
     * @param width  条形码宽度 2-6 像素 （若条码宽度设置过宽，整个条码超过纸张宽度将不会输出条码内容）
     * @param height 条形码⾼度 1-255 像素
     * @param textposition HRI位置 0：不打印 1：条形码上⽅ 2：条形码下⽅ 3：条形码上下⽅
     */
    public int printBarCode(String data, int type, int width, int height, int textposition) throws Exception {
        if (extPrinterService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return -1;
        }
        try {
            return extPrinterService.printBarCode(data, type, width, height, textposition);
        } catch (RemoteException e) {
            throw e;
        }
    }

    /**
     * 设置打印内容的对⻬⽅式
     *
     * @param type 0居左（默认）、1居中、2居右
     * @return
     */
    public int setAlignMode(int type) throws Exception {
        if (extPrinterService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return -1;
        }
        try {
            return extPrinterService.setAlignMode(type);
        } catch (RemoteException e) {
            throw e;
        }
    }

    /**
     * 设置打印内容的对⻬⽅式
     *
     * @param type 0居左（默认）、1居中、2居右
     * @return
     */
    public int tab() throws Exception {
        if (extPrinterService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return -1;
        }
        try {
            return extPrinterService.tab();
        } catch (RemoteException e) {
            throw e;
        }
    }

    /**
     * 打印文字
     * @param content
     * @param size (1--8) 由于打印机硬字库限制，字体⼤⼩仅能倍数放⼤，本⽅法可以控制字体在横向和纵向⽅向的放⼤倍数
     * @param isBold
     * @param isUnderLine
     * @return
     * @throws Exception
     */
    public int printText(String content, int size, boolean isBold, boolean isUnderLine) throws Exception {
        if (extPrinterService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return -1;
        }

        try {
            extPrinterService.setFontZoom(size, size);
            return extPrinterService.printText(content);
        } catch (RemoteException e) {
            throw e;
        }
    }

    /**
     * 打印图片
     * @param bitmap
     * @param mode 0：普通 1：倍宽 2：倍⾼ 3：倍⾼倍宽
     */
    public int printBitmap(Bitmap bitmap, int mode) throws Exception {
        if (extPrinterService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return -1;
        }
        try {
            return extPrinterService.printBitmap(bitmap, mode);
        } catch (RemoteException e) {
            throw e;
        }
    }

    /**
     * 打印表格
     */
    public int printTable(TableItem table) throws Exception {
        if (extPrinterService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return -1;
        }

        try {
            return extPrinterService.printColumnsText(table.getText(), table.getWidth(), table.getAlign());
        } catch (RemoteException e) {
            throw e;
        }
    }

    /*
     * 空打三行！
     */
    public int printEmptyLine(int count) throws Exception {
        if (extPrinterService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return -1;
        }

        try {
            return extPrinterService.lineWrap(count);
        } catch (RemoteException e) {
            throw e;
        }
    }

    /**
     * 切纸
     * @param m 切纸模式 0：全切 1：半切 2：进纸切纸
     * @param n  进纸距离 此参数只有在设置 m=2时有效， 由于打印机型号不同切⼑到打印头距离不同，当n=0 时将⾃动⾛纸此距离，n>0将⾛额外设置距离
     */
    public int cutPaper(int m, int n) throws Exception {
        if (extPrinterService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return -1;
        }
        try {
            return extPrinterService.cutPaper(m, n);
        } catch (RemoteException e) {
            throw e;
        }
    }

    /**
     * 启⽤事务模式
     */
    public void startTransBuffer() throws Exception {
        if (extPrinterService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return ;
        }

        try {
            extPrinterService.startTransBuffer();
        } catch (RemoteException e) {
            throw e;
        }
    }

    /**
     * 启⽤结束并提交事务
     */
    public void endTransBuffer() throws Exception {
        if (extPrinterService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return ;
        }

        try {
            extPrinterService.endTransBuffer();
        } catch (RemoteException e) {
            throw e;
        }
    }

    public int flush() throws Exception {
        if (extPrinterService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return -1;
        }
        try {
            return extPrinterService.flush();
        } catch (RemoteException e) {
            throw e;
        }
    }

    /**
     * 获取打印机运⾏状态
     * -1 打印机脱机或打印服务还未连接打印机
     * 0 打印机运⾏正常
     * 1 打印机开盖
     * 2 打印机缺纸
     * 3 打印机即将缺纸
     * 4 打印机过热
     */
    public int getStatus() throws Exception {
        if (extPrinterService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return -1;
        }
        try {
            return extPrinterService.getPrinterStatus();
        } catch (RemoteException e) {
            throw e;
        }
    }


    public int sendRawData(byte[] data) throws Exception {
        if (extPrinterService == null) {
            Toast.makeText(context, R.string.无法连接打印机, Toast.LENGTH_LONG).show();
            return -1;
        }
        try {
            return extPrinterService.sendRawData(data);
        } catch (RemoteException e) {
            throw e;
        }
    }
}
