//package com.example.knowledge_android.comparator.weixin;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.UnsupportedEncodingException;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.security.KeyStore;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Random;
//import java.util.TreeMap;
//
//import javax.net.ssl.HttpsURLConnection;
//import javax.net.ssl.KeyManagerFactory;
//import javax.net.ssl.SSLContext;
//
//public abstract class WeiXinHttpPostRequest {
//
//    static Logger log = LoggerFactory.getLogger(WeiXinHttpPostRequest.class);
//    protected Param param = Param.getInstance();
//
////    String appid = "11111111111";//微信分配的公众账号ID
////    String mch_id = "1111111111";//微信支付分配的商户号
////    String sub_mch_id = "1111";//微信支付分配的子商户号，受理模式下必填
////    String device_info = "11111";//终端设备号(商户自定义)
////    String version = "0.1";//版本号
////    String appSecret = "1111111111111111111111111111";
////    String partnerKey = "11111111111111111111111";//签名密钥
////    String weiXinUrl = "https://api.mch.weixin.qq.com/";
////    String weiXinKeyStore = "111111.p12";//证书名
////    String weiXinKeyPassword = "111111";//证书密码
//
//    protected abstract InputStream getCertFileInputStream();
//
//    //https通讯
//    private String getResponse(String str, String urlStr) {
//        try {
//            System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS ").format(new Date()) + "---------------------------------------");
//            log.info("WXHttpPostRequest", "---------------------------------------");
//            // 实例化密钥库
//            KeyStore ks = KeyStore.getInstance("PKCS12");
//            // 获得密钥库文件流
//            InputStream certStream = getCertFileInputStream();
//            // 加载密钥库
//            ks.load(certStream, param.getWeiXinKeyPassword().toCharArray());
//            // 关闭密钥库文件流
//            certStream.close();
//            // 实例化密钥库
//            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//            // 初始化密钥工厂
//            kmf.init(ks, param.getWeiXinKeyPassword().toCharArray());
//
//            int timeout = 3000;
//            // 创建SSLContext
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());
//            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
//
//            URL httpUrl = new URL(urlStr);
//            HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
//
//            httpURLConnection.setDoOutput(true);
//            httpURLConnection.setRequestMethod("POST");
//            httpURLConnection.setConnectTimeout(timeout);
//            httpURLConnection.setReadTimeout(timeout);
//            httpURLConnection.connect();
//            OutputStream outputStream = httpURLConnection.getOutputStream();
//            outputStream.write(str.getBytes("UTF-8"));
//
//            //获取内容
//            InputStream inputStream = httpURLConnection.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//            final StringBuffer stringBuffer = new StringBuffer();
//            String line = null;
//            while ((line = bufferedReader.readLine()) != null) {
//                stringBuffer.append(line);
//            }
//            String strResponse = stringBuffer.toString();
//            if (stringBuffer!=null) {
//                try {
//                    bufferedReader.close();
//                } catch (IOException e) {
//                }
//            }
//            if (inputStream!=null) {
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                }
//            }
//            if (outputStream!=null) {
//                try {
//                    outputStream.close();
//                } catch (IOException e) {
//                }
//            }
//            if (certStream!=null) {
//                try {
//                    certStream.close();
//                } catch (IOException e) {
//                }
//            }
//            System.out.print(strResponse);
//            log.info("WXHttpPostRequest respose = {}", strResponse);
//            System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS ").format(new Date()) + " -----------------------------------------");
//            log.info("WXHttpPostRequest", " -----------------------------------------");
//            return strResponse;
//        } catch (Exception e) {
//            log.error("", e);
//        }
//        System.out.println("WanOfflineCanNotTrade......");
//        log.info("WXHttpPostRequest", "WanOfflineCanNotTrade......");
//        return "";
//    }
//
//    /**
//     * 提交被扫支付接口
//     * auth_code：扫码支付授权码，设备读取用户微信中的条码或者二维码信息
//     * out_trade_no：商户订单号
//     * body：商品描述
//     * total_fee：总金额 以分为单位
//     * spbill_create_ip：订单生成的机器IP
//     */
//    public String micropay(String auth_code, String out_trade_no, String body, String total_fee, String detail) {
//        String url = param.getWeiXinUrl() + "pay/micropay";
//        Map map = new HashMap();
//        map.put("auth_code", auth_code);
//        map.put("out_trade_no", out_trade_no);
//        map.put("body", body);
//        map.put("total_fee", total_fee);
//        map.put("appid", param.getWeixinAppId());
//        map.put("mch_id", param.getWeixinMchId());
//        map.put("detail", detail);
//        map.put("device_info", param.getStoreId() + param.getTerminalNumber());
//        map.put("nonce_str", getRandom());//随机字符串 32位
//        map.put("spbill_create_ip", "192.168.0.1");
//        map.put("version", "0.1");
//        String str = getSortedHashMapByKey(map);
//        String md5Str = getMD5Str(str);
//        String goods = "";
//        if (map.containsKey("goods_tag")) {
//            goods = "<goods_tag>" + map.get("goods_tag") + "</goods_tag>";
//        }
//        String xml = "<xml>" +
//                "<appid>" + map.get("appid") + "</appid>" +
//                "<auth_code>" + map.get("auth_code") + "</auth_code>" +
//                "<body><![CDATA[" + map.get("body") + "]]></body>" +
//                "<device_info>" + map.get("device_info") + "</device_info>" +
//                "<mch_id>" + map.get("mch_id") + "</mch_id>" +
//                "<nonce_str>" + map.get("nonce_str") + "</nonce_str>" +
//                "<out_trade_no>" + map.get("out_trade_no") + "</out_trade_no>" +
//                "<spbill_create_ip>" + map.get("spbill_create_ip") + "</spbill_create_ip>" +
//                "<total_fee>" + map.get("total_fee") + "</total_fee>" + goods +
//                "<detail>" + map.get("detail") + "</detail>" +
//                "<version>" + map.get("version") + "</version>" +
//                "<sign><![CDATA[" + md5Str + "]]></sign>" +
//                "</xml>";
//        log.info("micropay request=" + xml);
//        String res = getResponse(xml, url);
//        return res;
//    }
//
//    /**
//     * 被扫订单查询接口
//     * out_trade_no：商户订单号
//     * transaction_id：微信订单号
//     */
//
//    public String orderquery(String out_trade_no) {
//        String url = param.getWeiXinUrl() + "pay/orderquery";
//        Map map = new HashMap();
//        map.put("out_trade_no", out_trade_no);
//        map.put("appid", param.getWeixinAppId());
//        map.put("mch_id", param.getWeixinMchId());
//        map.put("nonce_str", getRandom());//随机字符串 32位
//        map.put("version", "0.1");
//        String str = getSortedHashMapByKey(map);
//        String md5Str = getMD5Str(str);
//        String xml = "<xml>" +
//                "<appid>" + map.get("appid") + "</appid>" +
//                "<mch_id>" + map.get("mch_id") + "</mch_id>" +
//                "<nonce_str>" + map.get("nonce_str") + "</nonce_str>" +
//                "<out_trade_no>" + map.get("out_trade_no") + "</out_trade_no>" +
//                "<version>" + map.get("version") + "</version>" +
//                "<sign><![CDATA[" + md5Str + "]]></sign>" +
//                "</xml>";
//        String res = getResponse(xml, url);
//        return res;
//    }
//
//    /**
//     * 关闭订单接口*
//     * <p>
//     * out_trade_no：商户订单号
//     */
//    public String closeorder(String out_trade_no) {
//        String url = param.getWeiXinUrl() + "pay/closeorder";
//        Map map = new HashMap();
//        map.put("out_trade_no", out_trade_no);
//        map.put("appid", param.getWeixinAppId());
//        map.put("mch_id", param.getWeixinMchId());
//        map.put("nonce_str", getRandom());//随机字符串 32位
//        String str = getSortedHashMapByKey(map);
//        String md5Str = getMD5Str(str);
//        String xml = "<xml>" +
//                "<appid>" + map.get("appid") + "</appid>" +
//                "<mch_id>" + map.get("mch_id") + "</mch_id>" +
//                "<nonce_str>" + map.get("nonce_str") + "</nonce_str>" +
//                "<out_trade_no>" + map.get("out_trade_no") + "</out_trade_no>" +
//                "<sign><![CDATA[" + md5Str + "]]></sign>" +
//                "</xml>";
//        String res = getResponse(xml, url);
//        return res;
//    }
//
//    /**
//     * 退款申请接口*
//     * <p>
//     * out_trade_no：商户订单号
//     * transaction_id：微信订单号
//     * out_refund_no：商户退款单号
//     * total_fee：总金额
//     * refund_fee：退款金额
//     * op_user_id：操作员
//     */
//    public String refund(String out_trade_no, String transaction_id, String out_refund_no, String total_fee, String refund_fee, String op_user_id) {
//        String url = param.getWeiXinUrl() + "secapi/pay/refund";
//        Map map = new HashMap(11);
//        map.put("out_trade_no", out_trade_no);
//        map.put("transaction_id", transaction_id);
//        map.put("out_refund_no", out_refund_no);
//        map.put("refund_fee", refund_fee);
////        map.put("op_user_id", op_user_id);
//        map.put("total_fee", total_fee);
//        map.put("appid", param.getWeixinAppId());
//        map.put("mch_id", param.getWeixinMchId());
//        map.put("device_info", param.getStoreId() + param.getTerminalNumber());
//        map.put("nonce_str", getRandom());//随机字符串 32位
//        map.put("spbill_create_ip", "192.168.0.1");
//        String str = getSortedHashMapByKey(map);
//        String md5Str = getMD5Str(str);
//        String xml = "<xml>" +
//                "<appid>" + map.get("appid") + "</appid>" +
//                "<device_info>" + map.get("device_info") + "</device_info>" +
//                "<mch_id>" + map.get("mch_id") + "</mch_id>" +
//                "<nonce_str>" + map.get("nonce_str") + "</nonce_str>" +
////                "<op_user_id>" + map.get("op_user_id") + "</op_user_id>" +
//                "<out_refund_no>" + map.get("out_refund_no") + "</out_refund_no>" +
//                "<out_trade_no>" + map.get("out_trade_no") + "</out_trade_no>" +
//                "<refund_fee>" + map.get("refund_fee") + "</refund_fee>" +
//                "<spbill_create_ip>" + map.get("spbill_create_ip") + "</spbill_create_ip>" +
//                "<total_fee>" + map.get("total_fee") + "</total_fee>" +
//                "<transaction_id>" + map.get("transaction_id") + "</transaction_id>" +
//                "<sign><![CDATA[" + md5Str + "]]></sign>" +
//                "</xml>";
//        String res = getResponse(xml, url);
//        return res;
//    }
//
//    /**
//     * 退款查询接口
//     * transaction_id：微信订单号
//     * out_trade_no：商户订单号
//     * out_refund_no：商户退款单号
//     * refund_id：微信退款单号
//     */
//    public String refundquery(String transaction_id, String out_trade_no, String out_refund_no) {
//        String url = param.getWeiXinUrl() + "pay/refundquery";
//        Map map = new HashMap();
//        map.put("transaction_id", transaction_id);
//        map.put("out_trade_no", out_trade_no);
//        map.put("out_refund_no", out_refund_no);
//        map.put("appid", param.getWeixinAppId());
//        map.put("mch_id", param.getWeixinMchId());
//        map.put("device_info", param.getStoreId() + param.getTerminalNumber());
//        map.put("nonce_str", getRandom());//随机字符串 32位
//        String str = getSortedHashMapByKey(map);
//        String md5Str = getMD5Str(str);
//        String xml = "<xml>" +
//                "<appid>" + map.get("appid") + "</appid>" +
//                "<device_info>" + map.get("device_info") + "</device_info>" +
//                "<mch_id>" + map.get("mch_id") + "</mch_id>" +
//                "<nonce_str>" + map.get("nonce_str") + "</nonce_str>" +
//                "<out_refund_no>" + map.get("out_refund_no") + "</out_refund_no>" +
//                "<out_trade_no>" + map.get("out_trade_no") + "</out_trade_no>" +
//                "<transaction_id>" + map.get("transaction_id") + "</transaction_id>" +
//                "<sign><![CDATA[" + md5Str + "]]></sign>" +
//                "</xml>";
//        String res = getResponse(xml, url);
//        return res;
//    }
//
//    /**
//     * 冲正接口
//     * out_trade_no：商户订单号
//     */
//    public String reverse(String out_trade_no) {
//        String url = param.getWeiXinUrl() + "secapi/pay/reverse";
//        Map map = new HashMap(4);
//        map.put("appid", param.getWeixinAppId());
//        map.put("mch_id", param.getWeixinMchId());
//        map.put("out_trade_no", out_trade_no);
//        map.put("nonce_str", getRandom());//随机字符串 32位
//        String str = getSortedHashMapByKey(map);
//        String md5Str = getMD5Str(str);
//        String xml = "<xml>" +
//                "<appid>" + map.get("appid") + "</appid>" +
//                "<mch_id>" + map.get("mch_id") + "</mch_id>" +
//                "<nonce_str>" + map.get("nonce_str") + "</nonce_str>" +
//                "<out_trade_no>" + map.get("out_trade_no") + "</out_trade_no>" +
//                "<sign><![CDATA[" + md5Str + "]]></sign>" +
//                "</xml>";
//        String res = getResponse(xml, url);
//        return res;
//    }
//
//    /**
//     * 对账单接口
//     * <p>
//     * bill_date：对账单日起
//     * bill_type：账单类型  ALL:返回当日所有订单信息,默认值;  SUCCESS:返回当日成功支付的订单;REFUND:返回当日退款订单
//     */
//    public String downloadbill(String bill_date, String bill_type) {
//        String url = param.getWeiXinUrl() + "pay/downloadbill";
//        Map map = new HashMap();
//        map.put("appid", param.getWeixinAppId());
//        map.put("mch_id", param.getWeixinMchId());
//        map.put("device_info", param.getStoreId() + param.getTerminalNumber());
//        map.put("nonce_str", getRandom());//随机字符串 32位
//        map.put("bill_date", bill_date);
//        map.put("bill_type", bill_type);
//        String str = getSortedHashMapByKey(map);
//        String md5Str = getMD5Str(str);
//        String xml = "<xml>" +
//                "<appid>" + map.get("appid") + "</appid>" +
//                "<bill_date>" + map.get("bill_date") + "</bill_date>" +
//                "<bill_type>" + map.get("bill_type") + "</bill_type>" +
//                "<device_info>" + map.get("device_info") + "</device_info>" +
//                "<mch_id>" + map.get("mch_id") + "</mch_id>" +
//                "<nonce_str>" + map.get("nonce_str") + "</nonce_str>" +
//                "<sign><![CDATA[" + md5Str + "]]></sign>" +
//                "</xml>";
//        String res = getResponse(xml, url);
//        return res;
//    }
//
//
//    //随机字符串
//    private String getRandom() {
//        String radStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//        StringBuffer generateRandStr = new StringBuffer();
//        Random rand = new Random();
//        int length = 32;
//        for (int i = 0; i < length; i++) {
//            int randNum = rand.nextInt(36);
//            generateRandStr.append(radStr.substring(randNum, randNum + 1));
//        }
//        return generateRandStr + "";
//    }
//
//    //对map的key进行升序，并转成key1=value1&key2=value2..的形式
//    private String getSortedHashMapByKey(Map h) {
//
//        String str = "";
//        TreeMap treemap = new TreeMap(h);
//
//        Iterator it = treemap.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry entry = (Map.Entry) it.next();
//            String key = entry.getKey().toString();
//            String value = entry.getValue().toString();
//            str += key + "=" + value + "&";
//        }
//        return str;
//    }
//
//    //MD5加密计算
//    private String getMD5Str(String str) {
//        MessageDigest messageDigest = null;
//        try {
//            str += "key=" + param.getWeixinPartnerKey();
//            System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS ").format(new Date())
//                    + " --------------------------- process WeiXin ---------------------------");
//            log.info("WXHttpPostRequest", " --------------------------- process WeiXin ---------------------------");
//
//            System.out.println("MD5加密数据：" + str);
//            log.info("WXHttpPostRequest", "MD5加密数据：" + str);
//            messageDigest = MessageDigest.getInstance("MD5");
//            messageDigest.reset();
//            messageDigest.update(str.getBytes("UTF-8"));
//        } catch (NoSuchAlgorithmException e) {
//            System.out.println("NoSuchAlgorithmException caught!");
//            log.info("WXHttpPostRequest", "NoSuchAlgorithmException caught!");
//            System.exit(-1);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        byte[] byteArray = messageDigest.digest();
//        StringBuffer md5StrBuff = new StringBuffer();
//        for (int i = 0; i < byteArray.length; i++) {
//            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
//                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
//            } else {
//                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
//            }
//        }
//        String res = md5StrBuff.toString().toUpperCase();
//        System.out.println("sign = " + res);
//        log.info("WXHttpPostRequest", "sign = " + res);
//        return res;
//    }
//
//    static void main(String[] args) {
//        try {
////            SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmsss");
////            String str = f.format(new Date());
////            appid = "wxcdd85339688ad174";
////            mch_id = "1488944102";
////            partnerKey = "JYCSMLIwgSWfrIn4l3XkBfFUi31vroiD";
////            weiXinUrl = "https://api.mch.weixin.qq.com/";
////            weiXinKeyStore = "apiclient_cert.p12";
////            weiXinKeyPassword = "1488944102";
////            micropay("111882508230425441",str,"商品","1","");//支付
////            orderquery("201409041349059");//支付查询
////            closeorder("201408181236");//关闭订单
////            refund("10017813337101","4200000013201709264362158725","10017813337115","770","770","00001");//退款
////            refundquery("1006470648201408220005064605","111113258750901","   111113258751101");//退款查询
////            reverse("201409041349059");//冲正订单
////            downloadbill("20140813","ALL");//对账订单
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
