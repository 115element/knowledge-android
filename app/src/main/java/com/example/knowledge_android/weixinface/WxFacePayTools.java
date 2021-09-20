package com.example.knowledge_android.weixinface;

import android.text.TextUtils;
import android.util.Xml;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.tencent.wxpayface.WxfacePayCommonCode;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class WxFacePayTools {
    private static Logger log = LoggerFactory.getLogger(WxFacePayTools.class);

    /**
     * 判断微信人脸识别操作是否成功
     */
    public static boolean isFacePaySuccessInfo(Map info) {
        if (info == null) {
            return false;
        }
        String code = (String) info.get(WxConstant.RETURN_CODE);
        String msg = (String) info.get(WxConstant.RETURN_MSG);
        log.info("信息---" + msg + "---code码---" + code);
        return code != null && code.equals(WxfacePayCommonCode.VAL_RSP_PARAMS_SUCCESS);
    }

    /**
     *
     * 将Map转换为XML格式的字符串
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.newDocument();
        org.w3c.dom.Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key : data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
        try {
            writer.close();
        } catch (Exception ex) {
        }
        return output;
    }

    public static String parseGetAuthInfoXML(String resultText, String indexText) throws Exception {
        InputStream is = new ByteArrayInputStream(resultText.getBytes());
        String result = null;
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "UTF-8");

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (parser.getName().equals(indexText)) {
                        eventType = parser.next();
                        result = parser.getText();
                    }
            }
            eventType = parser.next();
        }

        return result;
    }


    /**
     * 拼接
     *
     * @param infoIds
     * @return
     */
    public static String getStringBuffer(List<Map.Entry<String, String>> infoIds) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < infoIds.size(); i++) {
            Map.Entry<String, String> stringStringEntry = infoIds.get(i);
            if (stringStringEntry.getKey() == null) {
                stringStringEntry.getKey();
            }
            String key = stringStringEntry.getKey();
            Object val = stringStringEntry.getValue();
            if (i != infoIds.size() - 1) {
                if (val != null && !TextUtils.equals("", val.toString())) {
                    sb.append(key).append("=").append(val).append("&");
                }
            } else {
                if (val != null && !TextUtils.equals("", val.toString())) {
                    sb.append(key).append("=").append(val);
                }
            }
        }
        return sb.toString();
    }


    /**
     * 密码的md5加密
     */
    public static String encode(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    sb.append("0");
                }
                sb.append(str);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getSortedHashMapByKey(Map h) {
        String str = "";
        TreeMap treemap = new TreeMap(h);

        Iterator it = treemap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            str += key + "=" + value + "&";
        }
        return str;
    }

    public static String getMD5Str(String str, String partnerKey) {
        MessageDigest messageDigest = null;
        try {
            str += "key=" + partnerKey;
            System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS ").format(new Date())
                    + " --------------------------- process WeiXin ---------------------------");
            log.info("WXHttpPostRequest", " --------------------------- process WeiXin ---------------------------");

            System.out.println("MD5加密数据：" + str);
            log.info("WXHttpPostRequest", "MD5加密数据：" + str);
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            log.info("WXHttpPostRequest", "NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return md5StrBuff.toString();
    }
}
