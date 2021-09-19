package com.example.knowledge_android.comparator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/***************************
 * @Auther Alex 2018-11-29 *
 ***************************/

public class ReleaseToolKit {

    private static File logFile;
    private static PrintWriter logger;
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    static {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            final String logDir = "./log";
            final String logFileName = "release.log." + df.format(new Date());  /**Release.log按照天记录*/
            new File(logDir).mkdir();

            logFile = new File(logDir, logFileName);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            logger = new PrintWriter(new OutputStreamWriter(
                    new FileOutputStream(logFile, true), StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public PrintWriter getLogger() {
        return logger;
    }

    static public void logMessage(String msg, Throwable e) {
        Date currentTime = new Date();
        String dateString = df.format(currentTime);
        logMessage("[" + dateString + "] "+ msg + " |==Release==| " + e.getMessage());
        if (logger != null) {
            e.printStackTrace(getLogger());
        } else {
            e.printStackTrace();
        }
    }

    static public void logMessage(String msg) {
        Date currentTime = new Date();
        String dateString = df.format(currentTime);
        if (logger != null) {
            logger.print("[" + dateString + "] |==Release==|");
            logger.println(msg);
            logger.flush();
        }
    }

}

