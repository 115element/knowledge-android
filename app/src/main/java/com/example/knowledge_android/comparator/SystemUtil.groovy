package com.example.knowledge_android.comparator

class SystemUtil {
    static boolean isOSWindows() {
        return System.getProperty("os.name").contains("Windows")
    }

    static boolean isOSLinux() {
        return System.getProperty("os.name").contains("Linux")
    }

    static boolean isOSMac() {
        return System.getProperty("os.name").contains("Mac")
    }
}
