package com.example.knowledge_android.knowledge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Environment;
import android.telephony.SmsManager;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.knowledge_android.OneApplication;
import com.example.knowledge_android.R;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import groovy.lang.Closure;

public class GUtil {


    public static ScheduledExecutorService scheduledExecutorService =
            Executors.newSingleThreadScheduledExecutor();

/*
    static GUtil getInstance() { instance }


    Context context

    def field1 = 'Field1'
    def field2 = 'Field2'

    void testWriteFile() {
        def testFile = ['/mnt/sdcard', 'test_file.txt'] as File
        testFile << 'Hello Groovy!\n'
        Log.i TAG, '>>> Write test file'
    }
*/

    /**
     * Center crop the given image file.
     * 中心裁剪给定的图像文件。
     */
    public static Bitmap centerCropImageFromFile(String filePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        int min = Math.min(bitmap.getWidth(), bitmap.getHeight());
        return ThumbnailUtils.extractThumbnail(bitmap, min, min);
    }

    /**
     * Encode a bitmap into a Base64 string of JPEG format.
     */
    public static String encodeBitmapToBase64StringOfJpegFormat(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] ba = baos.toByteArray();
        return Base64.encodeToString(ba, Base64.DEFAULT);
    }

    /**
     * Decode bitmap from a Base64 string.
     */
    public static Bitmap decodeBitmapFromBase64String(String base64String) {
        final byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    /**
     * Get string from resource.
     */
    public static String getString(int resourceId, Object... formatArgs) {
        return OneApplication.getInstance().getString(resourceId, formatArgs);
    }

    /**
     * Create a centered PopupWindow with the given view inside.
     *
     * @return A map with [popupWindow: popupWindow, innerView: innerView]
     */
    static Map openCancellablePopupView(int containerViewId) {
        View containerView = View.inflate(OneApplication.getInstance().getMainActivity(), containerViewId, null);

        containerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        int height = containerView.getMeasuredHeight();
        int width = containerView.getMeasuredWidth();

        PopupWindow popupWindow = new PopupWindow(containerView, width, height, true);
        popupWindow.setContentView(containerView);

        // Make it click outside to be able to dismiss the popup
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);

        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);

        // Set default action for cancel button
        View viewById = containerView.findViewById(R.id.cancel_button);
        if (!viewById.hasOnClickListeners()) {
            viewById.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });
        }
        popupWindow.showAtLocation(containerView, Gravity.CENTER, 0, 0);
        Map map = new HashMap();
        map.put("popupWindow", popupWindow);
        map.put("innerView", containerView);
        return map;
    }


    /**
     * Open a nested view in the given PopupWindow.
     *
     * @return the container view.
     */
    static View openNextViewInPopupWindow(int containerViewId, PopupWindow popupWindow) {
        View previousView = popupWindow.getContentView();

        View containerView = View.inflate(OneApplication.getInstance().getMainActivity(),
                containerViewId, null);

        popupWindow.dismiss();
        popupWindow.setContentView(containerView);

        // Set default action for Back button
        View viewById = containerView.findViewById(R.id.back_button);
        if (!viewById.hasOnClickListeners()) {
            viewById.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                    popupWindow.setContentView(previousView);
                    popupWindow.setAnimationStyle(0);
                    popupWindow.showAtLocation(containerView, Gravity.CENTER, 0, 0);
                }
            });
        }

        // Set default action for cancel button
        View view = containerView.findViewById(R.id.cancel_button);
        if (!view.hasOnClickListeners()) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });
        }
        popupWindow.setAnimationStyle(0);
        popupWindow.showAtLocation(containerView, Gravity.CENTER, 0, 0);
        return containerView;
    }


    /**
     * Open a OK/Cancel message dialog.
     */
    public static void showOkCancelDialog(int title, int message, List messageParams, Runnable okClosure) {
        Activity context = OneApplication.getInstance().getMainActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context, android.R.style.Theme_Holo_Dialog);
        builder.setTitle(title);

        if (messageParams != null) {
            builder.setMessage(Html.fromHtml(context.getResources().getString(message, messageParams)));
        } else {
            builder.setMessage(Html.fromHtml(context.getResources().getString(message)));
        }

        builder.setCancelable(false);
        builder.setIcon(android.R.drawable.ic_dialog_info);

        //setPositiveButton和setNegativeButton的区别和setNeutralButton的区别
        //三者都是AlertDialog弹出框的按钮，都是封装好的button，只是显示的位置不同，项目中可根据情况选择使用，setNegativeButton一般用于确认，setNegativeButton一般用于取消。
        builder.setPositiveButton("positive", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                okClosure.run();
            }
        });

        builder.setNegativeButton("negative", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNeutralButton("neutral", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.show();

        TextView messageText = (TextView) dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
    }

    /**
     * 根据配置的金额小数位数，设置number的小数位数。
     */
    static BigDecimal carry(BigDecimal number) {
        int decimalDigits = OneApplication.getInstance().getmSettings().getDecimalDigits();
        if (number.scale() == decimalDigits)
            return number;
        else
            return number.setScale(decimalDigits, RoundingMode.HALF_UP);
    }

    /**
     * Turn 1.00 to "1", 1.20 to "1.2", 2.34 to "2.34".
     */
    static String numberToString(BigDecimal value) {
        return value.stripTrailingZeros().toPlainString();
    }


    static String currency(BigDecimal value) {
        return NumberFormat.getCurrencyInstance(Locale.CHINA).format(value.doubleValue());
    }

    /**
     * Get date formatted string in current locale.
     */
    public final static String s = Locale.getDefault().toString();
    public final static String s1 = "1"; //Locale.TRADITIONAL_CHINESE.toString();
    public final static String s2 = "2"; //Locale.SIMPLIFIED_CHINESE.toString();

    static String getLocalizedDateString(Date date) {
        switch (s) {
            case s1:
                return String.format("%1$tY/%1$tm/%1$td", date);
            case s2:
                return String.format("%1$tY-%1$tm-%1$td", date);

            default:
                return DateFormat.getDateFormat(OneApplication.getInstance().getBaseContext()).format(date);
        }
    }

    /**
     * Create a fullscreen spinning wheel popup.
     *
     * @return The spinning wheel popup.
     */
    public static PopupWindow openSpinningWheelPopup() {
        View containerView = View.inflate(OneApplication.getInstance().getMainActivity(), R.layout.spinning_wheel, null);
        Point size = getScreenSizeInPixel(OneApplication.getInstance().getMainActivity());
        PopupWindow popupWindow = new PopupWindow(containerView, size.x, size.y, true);
        popupWindow.setContentView(containerView);
        popupWindow.showAtLocation(containerView, Gravity.FILL, 0, 0);
        return popupWindow;
    }

    /**
     * Version string comparison.
     * <pre>
     * assert compareVersion('2.2.2.', '2.2.2') == 0
     * assert compareVersion('2.2.3.1', '2.2.2') == 1
     * assert compareVersion('2.2', '2.2.2') == -1
     * assert compareVersion('2.2.3', '2.2.2') == 1
     * assert compareVersion('2.2.2.0', '2.2.2') == 1
     * assert compareVersion('2.2.2', '10.2.2') == -1
     * assert compareVersion('a2.2.2', 'a10.2.2') == 1
     * assert compareVersion('a2.2.2', 'a2.02.2') == 0
     * </pre>
     *
     * @return 1 if v1 is larger, -1 if v1 is smaller, 0 otherwise
     */
    public static int compareVersion(String v1, String v2) {
        String[] va1 = v1.split("\\.");
        String[] va2 = v2.split("\\.");
        int i = 0;
        for (String vn1 : va1) {
            if (i >= va2.length)
                return 1;
            try {
                int n1 = Integer.parseInt(vn1);
                int n2 = Integer.parseInt(va2[i]);
                if (n1 > n2)
                    return 1;
                else if (n1 < n2)
                    return -1;
                else
                    i++;
            } catch (Exception e) {
                int c = vn1.compareTo(va2[i]);
                if (c > 0)
                    return 1;
                else if (c < 0)
                    return -1;
                else
                    i++;
            }
        }
        if (i < va2.length)
            return -1;
        else
            return 0;
    }

    public static ScheduledFuture runPeriodically(long delayInSeconds, Runnable command) {
        return scheduledExecutorService.scheduleWithFixedDelay(command, 1,
                delayInSeconds, TimeUnit.SECONDS);
    }

    static void inspectViewTree(ViewGroup rootView) {
        Log.d("ViewInspector", '0' + rootView.toString());
        traverseViews(rootView, 1, "--");
    }

    static void traverseViews(ViewGroup viewGroup, int indent, String tab) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            Log.d("ViewInspector", indent + tab + child.toString());
            if (child instanceof ViewGroup)
                traverseViews((ViewGroup) child, indent + 1, tab + "--");
        }
    }

    static int convertFromHSBtoRGB(float hue, float saturation, float brightness) {
        int r = 0, g = 0, b = 0;
        if (saturation == 0) {
            r = g = b = (int) (brightness * 255.0f + 0.5f);
        } else {
            float h = (hue - (float) Math.floor(hue)) * 6.0f;
            float f = (h - (float) java.lang.Math.floor(h));
            float p = brightness * (1.0f - saturation);
            float q = brightness * (1.0f - saturation * f);
            float t = brightness * (1.0f - (saturation * (1.0f - f)));
            switch ((int) h) {
                case 0:
                    r = (int) (brightness * 255.0f + 0.5f);
                    g = (int) (t * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                    break;
                case 1:
                    r = (int) (q * 255.0f + 0.5f);
                    g = (int) (brightness * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                    break;
                case 2:
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (brightness * 255.0f + 0.5f);
                    b = (int) (t * 255.0f + 0.5f);
                    break;
                case 3:
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (q * 255.0f + 0.5f);
                    b = (int) (brightness * 255.0f + 0.5f);
                    break;
                case 4:
                    r = (int) (t * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (brightness * 255.0f + 0.5f);
                    break;
                case 5:
                    r = (int) (brightness * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (q * 255.0f + 0.5f);
                    break;
            }
        }
        return 0xff000000 | (r << 16) | (g << 8) | (b << 0);
    }


    /**
     * Synchronously run a closure on UI thread.
     */
    public static void runOnUIThread(final Runnable runnable) {
        OneApplication.getInstance().getMainActivity().runOnUiThread(runnable);
    }

    public static String copyAssets(String imageFile) {
        AssetManager assetManager = OneApplication.getInstance().getAssets();
        InputStream ins = null;
        OutputStream out = null;
        try {
            ins = assetManager.open(imageFile);
            File outFile = new File(Environment.getExternalStorageDirectory(), imageFile);
            out = new FileOutputStream(outFile);
            copyFile(ins, out);
            return outFile.getAbsolutePath();
        } catch (IOException e) {
            Log.e("GUtil", "Failed to copy asset file: " + imageFile, e);
            return null;
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
                if (ins != null) {
                    ins.close();
                }
            } catch (IOException e) {
            }
        }
    }

    public static void copyFile(InputStream ins, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = ins.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    public static int getStatusBarHeight(Resources resources) {
        int result = 0;
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        //statusBarHeight = result;
        return result;
    }

    public static int getPixels(int unit, float size) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return (int) TypedValue.applyDimension(unit, size, metrics);
    }

    public static int getNormalTextSize(Activity activity) {
        final double screenInches = getScreenSizeInInch(activity);
        return getPixels(TypedValue.COMPLEX_UNIT_SP, screenInches < 7 ? 14 : 18);
    }

    public static int getNormalTextSize() {
        return getPixels(TypedValue.COMPLEX_UNIT_SP, 20);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        return getPixels(TypedValue.COMPLEX_UNIT_DIP, dpValue);
        //final float scale = context.getResources().getDisplayMetrics().density;
        //return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px(像素)
     */
    public static int sp2px(float spValue) {
        return getPixels(TypedValue.COMPLEX_UNIT_SP, spValue);
    }

    public static Point getScreenSizeInPixel(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return new Point(dm.widthPixels, dm.heightPixels);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static double getScreenSizeInInch(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Double widthPixels = new Double(dm.widthPixels);
        Double xdpi = new Double(dm.xdpi);
        Double heightPixels = new Double(dm.heightPixels);
        Double ydpi = new Double(dm.ydpi);

        double x = Math.pow(widthPixels / xdpi, 2D);
        double y = Math.pow(heightPixels / ydpi, 2D);
        double screenInches = Math.sqrt(x + y);
        screenInches = (double) Math.round(screenInches * 10) / 10;
        //Log.d("Util", "Screen inches : " + screenInches);
        return screenInches;
    }

    public static int getNormalTextAppearance(Activity activity) {
        final double screenInches = getScreenSizeInInch(activity);
        return screenInches < 7 ? android.R.style.TextAppearance_Small : android.R.style.TextAppearance_Medium;
    }

    public static int getDiningTableTextAppearance(Activity activity) {
        //final double screenInches = Util.getScreenSizeInInch(activity)
        return android.R.style.TextAppearance_Medium;
    }

    public static boolean unzip(File file, File outputDir) {
        Log.i("TAG","Unzip file: " + file);
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(file);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                File entryDestination = new File(outputDir, entry.getName());
                if (entry.isDirectory()) {
                    entryDestination.mkdirs();
                } else {
                    entryDestination.getParentFile().mkdirs();
                    InputStream ins = zipFile.getInputStream(entry);
                    OutputStream out = new FileOutputStream(entryDestination);
                    IOUtils.copy(ins, out);
                    IOUtils.closeQuietly(ins);
                    out.close();
                    Log.i("TAG","=> " + entryDestination.getAbsolutePath());
                }
            }
            return true;
        } catch (Exception e) {
            Log.e("TAG","Failed to unzip.", e);
            return false;
        } finally {
            try {
                if (zipFile != null) {
                    zipFile.close();
                }
            } catch (IOException e) {
            }
        }
    }

    //发送短信
    public static void sendSMS(String phoneNumber, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Toast.makeText(new OneApplication().getApplicationContext(), "SMS Sent!", Toast.LENGTH_LONG).show();
    }

    public static Drawable decodeBase64ToDrawable(Resources resources, String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        return new BitmapDrawable(resources, bitmap);
    }

    public static Date getInitialDate() {
        Date initDate = new Date(0);
        int timeOffset = 0;
        TimeZone timeZone = Calendar.getInstance().getTimeZone();
        timeOffset = timeOffset - timeZone.getRawOffset();
        initDate.setTime(timeOffset);
        return initDate;
    }

    public static String reverseString(String string) {
        StringBuilder sb = new StringBuilder(string.length());
        for (char ch : string.toCharArray()) {
            sb.insert(0, ch);
        }
        return sb.toString();
    }

    public static boolean isDeviceSunmi() {
        return Build.BRAND == "SUNMI" || Build.MANUFACTURER == "SUNMI";
    }

    private static Boolean runningInEmulator;

    /**
     * Test if running in an emulator.
     */
    public static boolean isRunningInEmulator() {
        if (runningInEmulator == null)
            runningInEmulator = Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.startsWith("unknown") ||
                    Build.MODEL.contains("google_sdk") || Build.MODEL.contains("Emulator") ||
                    Build.MODEL.contains("Android SDK built for x86") ||
                    Build.MANUFACTURER.contains("Genymotion") ||
                    (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) ||
                    "google_sdk".equals(Build.PRODUCT);
        return true;
    }

    //判断有几个中文字
    public static int checkStringForChinese(String str) {
        int count = 0;
        String regEx = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                count = count + 1;
            }
        }
        return count;
    }
}
