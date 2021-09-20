//package com.example.knowledge_android.comparator.takeout
//
//import com.google.gson.Gson
//import groovy.transform.CompileStatic
//import groovy.util.logging.Slf4j
//import hyi.cream.inline.AbstractTakeOutClient
//import hyi.cream.inline.request.ITransferData
//import hyi.cream.inline.takeout.CommFieldReq
//import hyi.cream.inline.takeout.CommFieldRsp
//import hyi.cream.model.Param
//import hyi.cream.util.HttpUtil
//import hyi.cream.util.sign.SignUtil
//import org.apache.commons.beanutils.BeanMap
//
//import java.lang.reflect.Type
//
//@CompileStatic
//@Slf4j
//class TakeOutClient extends AbstractTakeOutClient {
//    String serverUrl = Param.instance.takeOutUrl
//    String charset = 'utf-8'
//    String privateKey = "";
//    String publicKey = "";
//
//    TakeOutClient() {
//        try {
//            Properties prop = new Properties()
//            prop.load(new InputStreamReader(new FileInputStream(new File("conf/takeoutKEY")), "UTF-8"))
//            privateKey = prop.getProperty("privateKey")
//            publicKey = prop.getProperty("publicKey")
//        } catch (FileNotFoundException ex) {
//            log.error("conf/takeoutKEY not found", ex)
//            privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMneR+wq50U+ECzQ8Gn57En6pPGUgTBdw5FVODyi8CRruwMOtkq44gsJhMsy+8DvFUJrCtHFCCX68kfQ+mDQY/nBg6M3q2nd+siFtptJo3Ddgd4wq7J6B4AyuguQo//bY9z3OLMFSX5KpuYg8aALRqXLdOWJxuiB9TduabeIETrJAgMBAAECgYEAxfZq18o3JGs5l4mKypKhyua2jIZSrqi02bgVvzkWgND5A4WQ9PQcEDDUfiTQSCLt2UV5xb8QP5a5s/3XG8wgzRQlzjuQzSO4wUQIlyzYL9urJA1EKPVyJUqBLZEzAZaEsGKmHBMO6Tf1/MFsCUzm95+yWcLXdK9gFTXq/jXcPQECQQD3bg4w8gbwhJw0WRdRMk9mOE9smkxp5tzqLf471DRTHknI5/5/foTgfHDSXzt7i3EWroKjVZCh3GtHak0/NnIpAkEA0Nw70NSfA1BuilOSjSvc8UtmKLAtrHAmECd7hMabROHFUyaD/nIgaUvzWfwV76i0niyOGQuRpS5xs1TAgbvXoQJAaplcvdOYD8liLt9vP0kokgaSmVyErV+ML8wu6wHKpUC4Uzk4GR+eCcUzKj9jNh/mUfpPSCVWEZYQFg2DSEehuQJBAM52t0ajuKEdFHj3TmrxTlLVNyLHrg7FOktDzG2fqwO3r0mYTGjuRq3wX5q3gLPN8OZfowSNFCfWtp0RDc/xcaECQG9UdzUTEOYED+m7leD+UtaeX+/+VidqyARRV78sFdF6+npGZM2frEO/W13XNwmcXhHQi/JWe/yJIcY9T3J63IM=";
//            publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDJoWewB32xP2bOfcmLh5hJypNp+xxRv17YHdxlbEI81RN/6+riLpvATmJE+9eFi1aKg+qyhS2WIoSngDWDcAohnnOZI7L8avLoM+vOCgPFQk0kOc3DTL1MxODICRN/N4cJuo47Zoq+wa+gOiOmdmlBze8tStx+vEyMG4UKV3nZhQIDAQA";
//        }
//    }
//    CommFieldRsp doTransfer(ITransferData transferData) throws IOException {
//        String signStr = getSignContent(new BeanMap(transferData.transferData()) as Map<String, String>)
//        log.info('待签名数据：\n' + signStr)
//        String sign = SignUtil.rsa256Sign(signStr, privateKey, charset)
//        ((CommFieldReq)transferData.transferData()).sign = sign
//        ((CommFieldReq)transferData.transferData()).sign_type = 'RSA2'
//        String url = serverUrl + transferData.postMethod()
//        log.info('开始连接：\n' + url)
//        Gson gson = Util.createGson()
//        String json = gson.toJson(transferData.transferData())
//        log.info('发送请求：\n' + json)
//
//        String rspStr = HttpUtil.getHttpPost(url, json)
//
//        log.info('获得返回值：\n' + rspStr.toString())
//        gson = Util.createGson()
//        Type collectionType = transferData.responseTypeToken()
//        gson.fromJson(rspStr.toString(), collectionType)
//    }
//
//    String getSignContent(Map<String, String> sortedParams) {
//        if (sortedParams == null) {
//            return null;
//        } else {
//            StringBuffer content = new StringBuffer();
//            List<String> keys = new ArrayList(sortedParams.keySet());
//            Collections.sort(keys);
//            int index = 0;
//
//            for(int i = 0; i < keys.size(); ++i) {
//                String key = (String)keys.get(i);
//                if (key == 'class' || key == 'metaClass' || key == 'sign' || key == 'sign_type') {
//                    continue
//                }
//                String value = (String)sortedParams.get(key);
//                if (key && value) {
//                    content.append((index == 0 ? "" : "&") + key + "=" + value);
//                    ++index;
//                }
//            }
//
//            return content.toString();
//        }
//    }
//
//
//}
