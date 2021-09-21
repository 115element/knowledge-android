//package com.example.knowledge_android.comparator;
//
//import com.sun.jna.Native;
//import hyi.cream.model.YinlianResponse;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Random;
//
///**
// * Created by ping on 2017/8/26.
// */
//public class YinLianCat {
//
//    public static IYinLianCat yinLianCat;
//    public static String path = System.getProperty("user.dir");
//    static Logger logger = LoggerFactory.getLogger(YinLianCat.class);
//
//    static {
//        try {
//            yinLianCat = (IYinLianCat) Native.loadLibrary(path + "\\yinlian\\posinf.dll", IYinLianCat.class);//posinf.dll
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     *
//     * @param type 长度2 应用类型 00银行卡 01 POS通
//     * @param posNo 长度8 POS机号 右补空格
//     * @param cashierId 长度8 pos员工号 右补空格
//     * @param tranType 长度2 交易类型
//     *                          银行卡：
//                                    '00'－消费        '01'－撤消
//                                    '02'－退货        '03'－查余额
//                                    '04'－重打印      '05'－签到
//                                    '06'－结算        '07'－重打结算单
//                                    '21'－预授权        '23'－预授权完成请求
//                                    '25'－预授权撤销    '26'－预授权完成撤销
//                                    '30'－营销联盟银行卡积分消费
//                                    '31'－银联钱包优惠券消费
//                                    '32'－银联钱包积分消费
//                                    '33'－银联钱包积分撤销
//                                    '34'－银联钱包优惠券撤销
//                                    '35'－银联钱包优惠券退货
//                                    '36'－银联钱包积分退货
//                                POS通：
//                                    '00'－消费        '01'－撤消
//                                    '02'－退货        '03'－查余额
//                                    '04'－重打印      '05'－签到
//                                    '06'－结算
//     * @param amount 长度12 金额 单位为分 左补0
//     * @param date 长度8 原交易日期 yyyyMMdd 退货时用 其它交易空格
//     * @param refdata 长度12 原交易参考号 退货时用 其它交易空格
//     * @param trace 长度6 原凭证号 撤消时用 其它交易空格
//     */
//    /*@param message 长度50 增值信息
//                             POS通业务：
//            50 串码 （左对齐，右补空格，pos手输串码时全部传空值）
//            银行卡业务：
//            银行卡扫码付消费，传入付款码
//            银行卡扫码付撤销，退货时传入付款凭证码*/
//
//    public static YinlianResponse call(String type, String posNo, String cashierId, String tranType,
//                                       String amount, String date, String refdata, String trace) {
//        if (yinLianCat == null) {
////            String response = "0001026222021001096339384 000101000000000010交易成功                                8983101739909957009287500000809271357450009689091300123450927062000000000000                                                                                                                                                                                                           ";
//            return null;
//        }
//        try {
//            String str = type
//                    + StringUtils.rightPad(posNo, 8, " ")
//                    + StringUtils.rightPad(cashierId, 8, " ")
//                    + tranType
//                    + StringUtils.leftPad(amount, 12, "0")
//                    + StringUtils.leftPad(date, 8, " ")
//                    + StringUtils.leftPad(refdata, 12, " ")
//                    + StringUtils.leftPad(trace, 6, " ")
//                    + getRandom()
//                    + StringUtils.rightPad(null, 50, " ")
//                    + StringUtils.rightPad(null, 50, " ")
//                    + StringUtils.rightPad(null, 50, " ");
//            System.out.println("YinLianCat request" + str);
//            //PosLog.i("YinLianCat", "request = " + str);
//            logger.info("request = " + str);
//
//            //PosLog.i("YinLianCat", "YinLianCat request" + str);
//            logger.info("YinLianCat request" + str);
//            byte[] resultByte = new byte[564];
//            //调用银联动态时先把运行目录转到银联动态所在目录
//            IWin32.INSTANCE.SetCurrentDirectory(path+"\\yinlian");
//            int result = yinLianCat.bankall(str, resultByte);
//            System.out.println("result = " + result);
//            //解决调用银行dll后，运行路径被改，通过kernel32修改运行路径
//            IWin32.INSTANCE.SetCurrentDirectory(path);
//            //PosLog.i("YinLianCat", "result = " + result);
//            logger.info("result = " + result);
//            String response = new String(resultByte ,"gbk");
//            System.out.println("response = " + response);
//            //PosLog.i("YinLianCat", "response = " + response);
//            logger.info("response = " + response);
//            return new YinlianResponse(resultByte, 1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//    //获取随机数 3码
//    public static String getRandom() {
//        Random r = new Random();
//        String str = "";
//        for(int i = 0; i < 3; i++){
//            str += r.nextInt(10);
//        }
//        return str;
//    }
//
//
//    public static void main(String []args) {
//        if (args.length == 9)
//            call("00", "00000001", "99999990", "00", "000000001000", "20180611", "201806111500", "215236");
//    }
//}
//
//
