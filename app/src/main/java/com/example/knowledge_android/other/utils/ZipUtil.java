package com.example.knowledge_android.other.utils;

import static android.content.ContentValues.TAG;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

//import hyi.mobilepos.POSTerminalApplication;

public class ZipUtil {
    /**
     * 入参
     * <p>
     * 1.zipFileName :
     * <p>
     * 2.(需要打包进.zip的文件),传入的是个完整的路径名字
     */
    public static File ZipFolder(String srcFileName, String zipDir, String zipFileName) {
        //创建ZIP
        try {
            //具体路径 : /storage/emulated/0/Android/data/hyi.mobilepos/files/log_zip
            final String filePath = "";//TODO 正式使用写后边这个路径 Objects.requireNonNull(POSTerminalApplication.getInstance().getBaseContext().getExternalFilesDir(zipDir)).getAbsolutePath();
            File dir = new File(filePath);
            //创建文件夹
            if (!dir.exists()) {
                dir.mkdir();
            }
            //
            File file = new File(srcFileName);
            if (!file.exists()) {
                Log.e(TAG, "无法找到指定文件名字" + srcFileName);
                return null;
            }

            File returnZipFile = new File(filePath + File.separator + zipFileName);
            ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(returnZipFile));
            //创建文件
            //压缩
            ZipFiles(file.getParent() + File.separator, file.getName(), outZip);
            //完成和关闭
            outZip.finish();
            outZip.close();
            return returnZipFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void ZipFiles(String folderString, String fileString, ZipOutputStream zipOutputSteam) {
        try {
            if (zipOutputSteam == null)
                return;
            File file = new File(folderString + fileString);
            if (file.isFile()) {
                ZipEntry zipEntry = new ZipEntry(fileString);
                FileInputStream inputStream = new FileInputStream(file);
                zipOutputSteam.putNextEntry(zipEntry);
                int len;
                byte[] buffer = new byte[4096];
                while ((len = inputStream.read(buffer)) != -1) {
                    zipOutputSteam.write(buffer, 0, len);
                }
                zipOutputSteam.closeEntry();
            } else {
                //文件夹
                String[] fileList = file.list();
                //没有子文件和压缩
                if (fileList.length <= 0) {
                    ZipEntry zipEntry = new ZipEntry(fileString + File.separator);
                    zipOutputSteam.putNextEntry(zipEntry);
                    zipOutputSteam.closeEntry();
                }
                //子文件和递归
                for (String s : fileList) {
                    ZipFiles(folderString + fileString + "/", s, zipOutputSteam);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
