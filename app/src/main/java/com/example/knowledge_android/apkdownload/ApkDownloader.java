package com.example.knowledge_android.apkdownload;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.example.knowledge_android.OneApplication;
import com.example.knowledge_android.R;
import com.example.knowledge_android.knowledge.DevAddrUtil;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/*
doInBackground 的返回值就是 onPostExecute 的参数啊!
onPostExecute方法用于通知UI进行更改


doInBackground 必须重写,异步执行后台线程将要完成的任务
onPreExecute 执行后台耗时操作前被调用，通常用户完成一些初始化操作
onPostExecute 当doInBackground完成后，系统会自动调用
onPostExecute()方法,并将doInBackground方法返回的值传给该方法
onProgressUpdate 在doInBackground方法中调用publishProgress()方法
*/

/*
AsyncTask介绍:
作用:
1.实现多线程：在工作线程中执行任务，如 耗时任务
2.异步通信、消息传递：实现工作线程 & 主线程（UI线程）之间的通信，即：将工作线程的执行结果传递给主线程，从而在主线程中执行相关的UI操作，保证线程安全。

优点:
1.方便实现异步通信
不需使用 “任务线程（如继承Thread类） + Handler”的复杂组合
2.节省资源
采用线程池的缓存线程 + 复用线程，避免了频繁创建 & 销毁线程所带来的系统资源开销

public abstract class AsyncTask<Params, Progress, Result>
// 类中参数为3种泛型类型
// 整体作用：控制AsyncTask子类执行线程任务时各个阶段的返回类型
// 具体说明：
    // a. Params：开始异步任务执行时传入的参数类型，对应execute()中传递的参数
    // b. Progress：异步任务执行过程中，返回下载进度值的类型
    // c. Result：异步任务执行完成后，返回的结果类型，与doInBackground()的返回值类型保持一致
// 注：
    // a. 使用时并不是所有类型都被使用
    // b. 若无被使用，可用java.lang.Void类型代替
    // c. 若有不同业务，需额外再写1个AsyncTask的子类

*/
public class ApkDownloader extends AsyncTask<String, Integer, Integer> {

    private static final String Tag = "ApkDownloader";

    TransferProgressListener transferProgressListener;

    public ApkDownloader() {
    }

    public static ApkDownloader runAsync() {
        ApkDownloader task = new ApkDownloader();
        task.executeOnExecutor(THREAD_POOL_EXECUTOR, new String[]{});
        return task;
    }

    // 需要返回值的例子
    public static int runDirect() {
        ApkDownloader apkDownloader = new ApkDownloader();
        return apkDownloader.direct();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (transferProgressListener != null) {
            transferProgressListener.showDataTransferDialog();
        }
    }

    //后台执行
    @Override
    protected Integer doInBackground(String... params) {
        return direct();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (transferProgressListener != null) {
            transferProgressListener.updateDataTransferDialog(values);
        }
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        try {
            this.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (transferProgressListener != null) {
            transferProgressListener.onFinishDataTransfer(result);
        }
    }

    public int direct() {
        Snackbar snackbar = null;
        try {
            OneApplication app = OneApplication.getInstance();
            Context context = app.getBaseContext();

            // Get current apk version and do comparison
            ApkContent response = doCheckAppVersion();
            if (response == null) {
                return NetworkResultCode.已是最新版无需下载;
            } else {
                Log.i(Tag, "开始获取新的程序包更新文件");
            }

            //底部提示
            snackbar = app.getMainActivity().createMessageOnSnackbar(R.string.program_downloading);
            snackbar.show();

//            外部存储SD卡(apk目录下)(可用)
//            String localFilePath = context.getExternalFilesDir("apk")

//            获取当前程序路径(Download目录下)(可用)
            String localFilePath = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

            String fileName = download(localFilePath, response.apkUrl);
            if (fileName != null) {
                String localFileName = localFilePath + File.separator + fileName;
                Log.i("TAG","Need to download apk, my version=${app.appVersionCode}, version on server=${fileName}");
                Log.i("TAG","Download " + fileName);
                File apkFile = new File(localFileName);
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW);
                if (Build.VERSION.SDK_INT >= 24) { //Build.VERSION.SDK_INT API版本(我的是30)
                    Log.i(Tag,">>>>>>>=Android N");
                    Uri apkUri = FileProvider.getUriForFile(app, "com.example.knowledge_android.fileprovider", apkFile);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                } else {
                    Log.i(Tag,"<<<<<<<<Android N");
                    // Start apk installation activity 启动APK安装活动
                    intent.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
                }
                app.getApplicationContext().startActivity(intent);

            } else {
                Log.i(Tag, "Download Failed.");
            }
            return 0;
        } catch (Exception e) {
            Log.e(Tag, "downloadMaster failed", e);
            return NetworkResultCode.getErrorCodeFromException(e);
        } finally {
            if (snackbar!=null) {
                if (snackbar.isShown()) {
                    snackbar.dismiss();
                }
            }
        }
    }


    //检查服务器上是否有新版本APP
    ApkContent doCheckAppVersion() {
        try {
            //一个请求头
            String token = "12345678"; //现在直接读数据库中的token值
            //两个请求参数
            String currentVersion = "现在的程序版本";
            String macIp1 = DevAddrUtil.getLocalMacIdFromIp(); //终端MAC IP
            String macIp2 = DevAddrUtil.getMacAddress(); //终端MAC IP

            //发送请求到服务器，携带以上参数
            //...

            //模拟服务器成功返回
            ApkContent apkContent = new ApkContent();
            apkContent.setApkUrl("http:/www.baidu.com/1.apk");
            apkContent.setVersionNo("1.0.1");
            apkContent.setVersionName("哈哈版");
            apkContent.setRemark("注意");
            apkContent.setCreateTime("2021-09-09 11:00:00");
            return apkContent;
        } catch (Exception e) {
            Log.e(Tag, "检查新版本异常", e);
        }
        return null;
    }


    /**
     * @param dirName 下载的文件本地保存目录
     * @param docUrl  文件的网络地址
     * @return 文件名
     */
    public static String download(String dirName, String docUrl) {
        String[] list = docUrl.split("/");
        String fileNameOnly = list[list.length - 1];//文件名(只有文件名)

        //SD卡具有读写权限、指定附件存储路径为SD卡上指定的文件夹
        File dirNameDir = new File(dirName);
        setPermission(dirName);//最高权限
        if (!dirNameDir.exists()) {      //判断文件夹是否存在
            dirNameDir.mkdir();        //如果不存在、则创建一个新的文件夹
            Log.i(Tag, "创建文件夹:" + dirName + "成功");
        }
        File file = new File(dirName + File.separator + fileNameOnly);
        setPermission(dirName + File.separator + fileNameOnly);//最高权限
        if (file.exists()) {//如果目标文件已经存在
            file.delete();    //则删除旧文件
            Log.i(Tag, "删除文件同名文件:" + fileNameOnly + "成功");
        }
        try {
            //1K的数据缓冲
            byte[] bs = new byte[1024];
            //读取到的数据长度
            int len;
            //通过文件地址构建url对象
            URL url = new URL(docUrl);
            //获取链接
            //URLConnection conn = url.openConnection();
            //创建输入流
            InputStream is = url.openStream();
            //获取文件的长度
            //int contextLength = conn.getContentLength();
            //输出的文件流
            OutputStream os = new FileOutputStream(file);
            //开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            //完毕关闭所有连接
            os.close();
            is.close();
            Log.i(Tag, "文件下载成功");
        } catch (Exception e) {
            Log.i(Tag, "文件下载错误", e);
        }
        return fileNameOnly;
    }

    /**
     * 提升读写权限
     *
     * @param filePath 文件路径
     * @return
     * @throws IOException
     */
    public static void setPermission(String filePath) {
        String command = "chmod " + "777" + " " + filePath;
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TransferProgressListener getTransferProgressListener() {
        return transferProgressListener;
    }

    public void setTransferProgressListener(TransferProgressListener transferProgressListener) {
        this.transferProgressListener = transferProgressListener;
    }

    //    /**
//     * Install the app silently.
//     * <p>Without root permission must hold
//     * {@code android:sharedUserId="android.uid.shell"} and
//     * {@code <uses-permissionandroid:name="android.permission.INSTALL_PACKAGES" /  >}</p>
//     *
//     * @param file The file.
//     * @param params The params of installation(e.g.,<code>-r</code>, <code>-s</code>).
//     * @param isRooted True to use root, false otherwise.
//     * @return {@code true}: success<br>{@code false}: fail
//     */
//    public static boolean installAppSilent(final File file,
//                                           final String params,
//                                           final boolean isRooted) {
//        if (!isFileExists(file)) return false;
//        String filePath = '"' + file.getAbsolutePath() + '"';
//        String command = "LD_LIBRARY_PATH=/vendor/lib*:/system/lib* pm install " +
//                (params == null ? "" : params + " ") + filePath;
//        ShellUtils.CommandResult commandResult = ShellUtils.execCmd(command, isRooted);
//        if (commandResult.successMsg != null
//                && commandResult.successMsg.toLowerCase().contains("success")) {
//            return true;
//        } else {
//            Log.i("AppUtils", "installAppSilent successMsg: " + commandResult.successMsg +
//                    ", errorMsg: " + commandResult.errorMsg);
//            return false;
//        }
//    }

//    private static boolean isFileExists(final File file) {
//        return file != null && file.exists();
//    }
//
//    private static boolean isDeviceRooted() {
//        String su = "su";
//        String[] locations = ["/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
//                              "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/",
//                              "/system/sbin/", "/usr/bin/", "/vendor/bin/"];
//        for (String location : locations) {
//            if (new File(location + su).exists()) {
//                return true;
//            }
//        }
//        return false;
//    }
}
