package com.example.knowledge_android.apkdownload;


import com.example.knowledge_android.comparator.i18n.I18n;

import java.net.SocketTimeoutException;

/**
 * Class containing error codes.
 * <p>
 *
 * @author Bruce You
 * @since 2013/02/27 PM01:31
 */
public class NetworkResultCode {
    public static final int SUCCESSFUL = 0;

    public static final int NETWORK_NG = -1;

    public static final int WiFi未连接 = -101;
    public static final int 无法连接到服务器 = -102;
    public static final int 与服务器连接的连接被中断 = -103;
    public static final int 服务器关闭连接 = -104;
    public static final int 服务器回应错误 = -105;
    public static final int 连接超时 = -106;
    public static final int 其他网络错误 = -107;

    public static final int 无法开启数据库 = -201;
    public static final int 无法覆盖数据库文件 = -202;
    public static final int 无法移动数据库文件 = -203;
    public static final int 其他数据库错误 = -204;
    public static final int 文件不存在 = -205;
    public static final int 现在无法下载数据库文件 = -206;
    public static final int 现在无法下载文件 = -207;
    public static final int 无法汇入数据表 = -208;
    public static final int Server端数据库服务关闭 = -210;
    public static final int 已是最新版无需下载 = -211;
    public static final int 最新版未到生效时间无需下载 = -222;
    public static final int 此交易已经被退货 = -212;
    public static final int 非销售交易无法退货 = -213;
    public static final int 下载数据失败 = -214;
    public static final int 无法移动文件 = -215;

    public static final int 无法下载文件 = -301;
    public static final int 未发现现新版本文件 = -302;

    public static final int 广告文件已是最新 = -411;

    public static final int 备份主档失败 = -555;

    public static final int 网络缓慢获取新主档ZIP失败 = -777;

    public static final int 商品未找到 = -801;
    public static final int 文件损坏 = -209;

    public static final int 后台目前没有该机号交易 = -999;

    public static int getErrorCodeFromException(Exception e) {
        if (e instanceof SocketTimeoutException) {
            return 连接超时;
        }
        String message = e.getMessage();
        if (message != null) {
            if (message.toLowerCase().contains("failed to connect")) {
                return 无法连接到服务器;
            } else if (message.toLowerCase().contains("not completed")) {
                return 与服务器连接的连接被中断;
            }
        }
        return 其他网络错误;
    }

    public static String getResourceKeyByErrorCode(int code) {
        switch (code) {
            case SUCCESSFUL:
                return I18n.getString("成功");
            case WiFi未连接:
                return I18n.getString("WiFi未连接");
            case 与服务器连接的连接被中断:
                return I18n.getString("与服务器连接的连接被中断");
            case 服务器关闭连接:
                return I18n.getString("服务器关闭连接");
            case 服务器回应错误:
                return I18n.getString("服务器回应错误");
            case 连接超时:
                return I18n.getString("连接超时");
            case 其他网络错误:
                return I18n.getString("其他网络错误");
            case 无法开启数据库:
                return I18n.getString("无法开启数据库");
            case 无法覆盖数据库文件:
                return I18n.getString("无法覆盖数据库文件");
            case 无法移动数据库文件:
                return I18n.getString("无法移动数据库文件");
            case 其他数据库错误:
                return I18n.getString("其他数据库错误");
            case 文件不存在:
                return I18n.getString("文件不存在");
            case 现在无法下载数据库文件:
                return I18n.getString("现在无法下载数据库文件");
            case 无法汇入数据表:
                return I18n.getString("无法汇入数据表");
            case Server端数据库服务关闭:
                return I18n.getString("Server端数据库服务关闭");
            case 已是最新版无需下载:
                return I18n.getString("已是最新版,无需下载");
            case 最新版未到生效时间无需下载:
                return I18n.getString("最新版未到生效时间无需下载");
            case 此交易已经被退货:
                return I18n.getString("此交易已经被退货");
            case 非销售交易无法退货:
                return I18n.getString("非销售交易,无法退货");
            case 下载数据失败:
                return I18n.getString("下载数据失败");
            case 无法下载文件:
                return I18n.getString("现在无法下载文件");
            case 未发现现新版本文件:
                return I18n.getString("未发现现新版本文件");
            case 无法移动文件:
                return I18n.getString("无法移动文件");
        }
        return "其它错误";
    }
}
