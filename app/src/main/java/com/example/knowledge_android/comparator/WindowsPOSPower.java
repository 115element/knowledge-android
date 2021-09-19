//package com.example.knowledge_android.comparator;
//
//import javax.swing.*;
//import java.awt.*;
//import java.lang.reflect.Field;
//
///**
// * Control POS power by Windows API.
// * <p/>
// * Please put POSPowerNative.dll under the current working directory.
// *
// * @author Bruce You @ Hongyuan Software (Shanghai) Co., Ltd., 2019.
// */
//public class WindowsPOSPower {
//
//    static final int EWX_POWEROFF = 0x00000008;
//    static final int EWX_REBOOT = 0x00000002;
//    static final int SHTDN_REASON_MAJOR_APPLICATION = 0x00040000;
//
//    /** Intercept WM_QUERYENDSESSION by subclassing the given window. */
//    private static native void interceptQueryEndSession(long windowHandle);
//
//    /** Tell WM_QUERYENDSESSION handler whether the system can be shutdown. */
//    private static native void allowShutdown(boolean allow);
//
//    /** Calling Win32 API ExitWindowsEx(). */
//    private static native boolean exitWindowsEx(int uFlags, long dwReason);
//
//    /** Calling Win32 API SetSuspendState(). */
//    private static native boolean setSuspendState(boolean hibernate,
//                                                  boolean forceCritical, boolean disableWakeEvent);
//
//    private static boolean is32bit() {
//        return "32".equals(System.getProperty("sun.arch.data.model"));
//    }
//
//    static {
//        if (is32bit()) {
//            System.out.println("Load POSPowerNative.dll ...");
//            System.loadLibrary("POSPowerNative");
//            System.out.println("Load POSPowerNative.dll finished");
//        } else {
//            System.out.println("Load POSPowerNative_x64.dll ...");
//            System.loadLibrary("POSPowerNative_x64");
//            System.out.println("Load POSPowerNative_x64.dll finished");
//        }
//    }
//
//    public void allowShutdown() {
//        allowShutdown(true);
//    }
//
//    public void disallowShutdown(long windowHandle) {
//        try {
//            interceptQueryEndSession(windowHandle);
//            allowShutdown(false);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void shutdownPOS() {
//        exitWindowsEx(EWX_POWEROFF, SHTDN_REASON_MAJOR_APPLICATION);
//    }
//
//    public void restartPOS() {
//        exitWindowsEx(EWX_REBOOT, SHTDN_REASON_MAJOR_APPLICATION);
//    }
//
//    public void standbyPOS() {
//        setSuspendState(
//            false,  // the system is suspended
//            true,   // the system suspends operation immediately
//            true    // the system disables all wake events
//        );
//    }
//
//    public void suspendPOS() {
//        setSuspendState(
//            true,   // the system hibernates
//            true,   // the system suspends operation immediately
//            true    // the system disables all wake events
//        );
//    }
//
//    public static long getHWnd(Frame window) {
//        long winId = 0; //Window ID
//        try {
//            final Class<?> cl = Class.forName("sun.awt.windows.WComponentPeer");
//            Field f = cl.getDeclaredField("hwnd");
//            f.setAccessible(true);
//            winId = f.getLong(window.getPeer());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return winId;  // window handle
//    }
//
//    public static void main(String[] args) throws Exception {
//        final WindowsPOSPower posPower = new WindowsPOSPower();
//        final JFrame frame = new JFrame("Shutdown Test");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(800, 600);
//        frame.setVisible(true);
//        try {
//            long windowHandle = getHWnd(frame);
//            System.out.println("windowHandle=" + windowHandle);
//            posPower.disallowShutdown(windowHandle);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
