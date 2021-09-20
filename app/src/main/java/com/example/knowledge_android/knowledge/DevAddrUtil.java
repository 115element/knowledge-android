package com.example.knowledge_android.knowledge;

import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class DevAddrUtil {

    /**
     * 按照"XX-XX-XX-XX-XX-XX"格式，获取本机MAC地址
     * @return
     * @throws Exception
     */
    public static String getMacAddress() throws Exception {
        Enumeration<NetworkInterface> ni = NetworkInterface.getNetworkInterfaces();

        while (ni.hasMoreElements()) {
            NetworkInterface netI = ni.nextElement();

            byte[] bytes = netI.getHardwareAddress();
            if (netI.isUp() && netI != null && bytes != null && bytes.length == 6) {
                StringBuffer sb = new StringBuffer();
                for (byte b : bytes) {
                    //与11110000作按位与运算以便读取当前字节高4位
                    sb.append(Integer.toHexString((b & 240) >> 4));
                    //与00001111作按位与运算以便读取当前字节低4位
                    sb.append(Integer.toHexString(b & 15));
                    sb.append("-");
                }
                sb.deleteCharAt(sb.length() - 1);
                return sb.toString().toUpperCase();
            }
        }
        return null;
    }


    //安卓7.0以下及以上获取mac地址的方法
    public static String getLocalMacIdFromIp() {
        String strMacAddr = "";
        try {
            InetAddress ip = getLocalInetAddress();
            /*
            .getHardwareAddress() 返回null?
            引用来自“with_you”的评论
            linux你配了localhost的hosts映射，用localhost方法估计取不到网卡信息．用你的虚拟机真实ＩＰ地址取才行：我试了下是可以的
            InetAddress address = InetAddress.getByName("192.168.2.106");
            引用来自“kerwin612”的评论
            十分感谢！！！
             */
            byte[] b = NetworkInterface.getByInetAddress(ip)
                    .getHardwareAddress();

            if (b == null) {
                Log.i("TAG",".getHardwareAddress(),返回null 》》》");
                return "";
            }

            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }

                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toLowerCase();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return strMacAddr;
    }


    /**
     * 获取设备本地IP
     */
    protected static InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            //列举
            Enumeration en_netInterface = NetworkInterface.getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {//是否还有元素
                NetworkInterface ni = (NetworkInterface) en_netInterface.nextElement();//得到下一个元素
                Enumeration en_ip = ni.getInetAddresses();//得到一个ip地址的列举
                while (en_ip.hasMoreElements()) {
                    ip = (InetAddress) en_ip.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1)
                        break;
                    else
                        ip = null;
                }

                if (ip != null) {
                    break;
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return ip;
    }


}
