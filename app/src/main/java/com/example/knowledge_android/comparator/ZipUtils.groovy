package com.example.knowledge_android.comparator

import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class ZipUtils {
    static String TAG = "ZipUtils"

    static boolean unzip(File fileName, File targetPath) {
        //CreamToolkit.logMessage(TAG, "Unzip file: " + fileName);
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(fileName);
            ZipEntry zipEntry;
            File target;
            String entryName;
            String targetName;
            Enumeration<? extends ZipEntry> en = zipFile.entries();
            while (en.hasMoreElements()) {
                zipEntry = en.nextElement();
                entryName = zipEntry.getName();
                if (zipEntry.isDirectory()) {
                    //logger.debug("is directory");
                    if (targetPath == null) {
                        target = new File(entryName);
                    } else {
                        target = new File(targetPath, entryName);
                    }
                    // path or file.
                    //logger.debug("target: " + target);
                    if (!target.exists()) {
                        //logger.debug("建立路徑:" + target.getAbsolutePath());
                        target.mkdirs();
                    }
                } else {
                    targetName = entryName;
                    if (targetPath == null) {
                        target = new File(targetName);
                    } else {
                        target = new File(targetPath, targetName);
                    }
                    InputStream inputStream = null
                    OutputStream outStream = null;
                    try {
                        //logger.debug("解開檔案至:" + target.getAbsolutePath());
                        inputStream = zipFile.getInputStream(zipEntry);
                        outStream = new FileOutputStream(target);
                        copy( inputStream, outStream );
                    } finally {
                        try {
                            if (outStream != null) {
                                outStream.close();
                            }
                            if (inputStream != null) {
                                inputStream.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            //CreamToolkit.logMessage(TAG, "Unzip file finished successfully!!!" + fileName);
            return true;
        } catch (Exception e) {
            //CreamToolkit.logMessage(TAG, "Unzip file failed", e);
            return false;
        } finally {
            try {
                if (zipFile != null) {
                    zipFile.close();
                }
            } catch (IOException e) {
                //CreamToolkit.logMessage(TAG, "Unzip file failed last", e);
            }
        }
    }

    static void copy(InputStream inputStream, OutputStream out) throws IOException {
        byte[] buffer = new byte[256];
        while (true) {
            int bytesRead = inputStream.read(buffer);
            if (bytesRead == -1)
                break;
            out.write(buffer, 0, bytesRead);
        }
    }
}
