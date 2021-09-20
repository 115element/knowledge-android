package com.example.knowledge_android.zhifubao

import android.util.Log

import javax.net.ssl.HttpsURLConnection
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * 支付宝接口
 */
class AlipayHttpPostRequest {
    public static String TAG = 'AlipayHttpPostRequest'

    public static String sign_type = 'MD5'              // 签名方式
    public static String format_type = 'xml'            //请求格式
    public static String _input_charset = 'GBK'         //参数编码字符集
    public static String key = '1111111'                  //商户密钥
    public static String partner = '1111111'              //商户ID
    public static String dynamic_id_type = 'barcode'
    // soundwave：声波 qrcode：二维码 barcode：条码

    //SOUNDWAVE_PAY_OFFLINE：声波支付  FINGERPRINT_FAST_PAY：指纹支付 BARCODE_PAY_OFFLINE：条码支付
    public static String product_code = 'BARCODE_PAY_OFFLINE'
    public static String notify_url = ''
    public static String it_b_pay = '5m'

    public static String urlStr = 'https://mapi.alipay.com/gateway.do?'

    public static boolean getPropery() {
        if (!key)
            return false
        if (!partner)
            return false
        return true
    }

    //下单和并支付
    public static String getCreateAndPay(String out_trade_no, String total_fee, String dynamic_id, String body,
                                         String goods_detail, String extend_params) {
        Log.i(TAG, "调用支付接口----------------")
        String service = 'alipay.acquire.createandpay'//接口名称
        String subject = 'C-Store'
        //把请求参数打包成数组
        Map sParaTemp = [service      : service, partner: partner, _input_charset: _input_charset,
                         out_trade_no : out_trade_no, subject: subject, total_fee: total_fee,
                         product_code : product_code, dynamic_id_type: dynamic_id_type,
                         dynamic_id   : dynamic_id, body: body, notify_url: notify_url,
                         it_b_pay     : it_b_pay, format_type: format_type, goods_detail: goods_detail,
                         extend_params: extend_params]
        Map sPara = buildRequestPara(sParaTemp)
        String parm =  mapToString(sPara)
        return getReposnes(parm)
    }


//退款
    public static String  getRefund(String out_trade_no,String refund_amount) {
        Log.i(TAG, "调用退款接口----------------")
        String  service = "alipay.acquire.refund"//接口名称
        //把请求参数打包成数组
        Map sParaTemp = [service      : service, partner: partner, _input_charset: _input_charset,
                         out_trade_no : out_trade_no, refund_amount: refund_amount]
        Map sPara = buildRequestPara(sParaTemp)
        String parm =  mapToString(sPara)
        return getReposnes(parm)
    }

    //撤销
    public static String  getCancel(String out_trade_no) {
        Log.i(TAG, "调用撤销接口----------------")
        String  service = "alipay.acquire.cancel"//接口名称
        //把请求参数打包成数组
        Map sParaTemp = [service      : service, partner: partner, _input_charset: _input_charset,
                         out_trade_no : out_trade_no,]
        Map sPara = buildRequestPara(sParaTemp)
        String parm =  mapToString(sPara)
        return getReposnes(parm)
    }

    //查询
    public static String  getQuery(String out_trade_no) {
        Log.i(TAG, "调用查询接口----------------")
        String  service = "alipay.acquire.query"//接口名称
        //把请求参数打包成数组
        Map sParaTemp = [service      : service, partner: partner, _input_charset: _input_charset,
                         out_trade_no : out_trade_no]
        Map sPara = buildRequestPara(sParaTemp)
        String parm =  mapToString(sPara)
        return getReposnes(parm)
    }

    public static String getReposnes(String str) {
        try {
            Log.i(TAG, "${urlStr}")
            // 服务地址
            URL url = new URL("${urlStr}")
            // 设定连接的相关参数
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection()
            urlConnection.doOutput = true
            urlConnection.doInput = true
            urlConnection.requestMethod = "POST"
            urlConnection.connectTimeout = 30000
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; text/html; charset=${_input_charset}")
            urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0")
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream(), _input_charset)
            Log.i(TAG, str)
            out.write(str)
            out.flush()
            out.close()
            def strResponse = ""
            def strLine = ""
            InputStream inputStream = urlConnection.getInputStream()
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, _input_charset))
            while ((strLine = reader.readLine()) != null) {
                strResponse += strLine + "\n"
            }
            Log.i(TAG, strResponse)
            return strResponse
        } catch (Exception e) {
            e.printStackTrace()
            Log.e(TAG, "", e)
        }
        Log.i(TAG, "WanOfflineCanNotTrade......")
        return ""
    }

    public static String mapToString(Map sPara) {
        List keys = new ArrayList(sPara.keySet())
        String prestr = ""

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i).toString()
            String value = sPara.get(key).toString()
//            if (key == 'body' || key == 'subject' || key == 'goods_detail') {
//                try {
//                    value = URLEncoder.encode(value, _input_charset);
//                } catch (Exception e) {}
//            }
            prestr += "&" + key + "=" + value
        }
        return prestr.substring(1)
    }

    private static Map buildRequestPara(Map sParaTemp) {
        //除去数组中的空值和签名参数
        Map sPara = paraFilter(sParaTemp)
        //生成签名结果
        String mysign = buildRequestMysign(sPara)
        //签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign)
        sPara.put("sign_type", sign_type)

        return sPara
    }

    public static Map paraFilter(Map sArray) {

        Map result = new HashMap()

        if (sArray == null || sArray.size() <= 0) {
            return result
        }
        Iterator it = sArray.entrySet().iterator()
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next()
            Object key = entry.getKey()
            Object value = sArray.get(key)
            if (value == null || value.toString().equals("") || key.toString().equalsIgnoreCase("sign")
                    || key.toString().equalsIgnoreCase("sign_type")) {
                continue
            }
            result.put(key, value)
        }

        return result
    }

    public static String createLinkString(Map params) {

        List keys = new ArrayList(params.keySet())
        Collections.sort(keys)

        String prestr = ""

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i).toString()
            String value = params.get(key).toString()

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value
            } else {
                prestr = prestr + key + "=" + value + "&"
            }
        }
        return prestr
    }

    public static String buildRequestMysign(Map sPara) {
        String prestr = createLinkString(sPara) //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        if (sign_type == 'MD5') {
            MessageDigest messageDigest = null
            try {
                prestr += key
                messageDigest = MessageDigest.getInstance(sign_type)
                messageDigest.reset()
                messageDigest.update(prestr.getBytes(_input_charset))
            } catch (NoSuchAlgorithmException e) {
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace()
            }

            byte[] byteArray = messageDigest.digest()
            StringBuffer md5StrBuff = new StringBuffer()
            byteArray.each {
                if (Integer.toHexString(0xFF & it).length() == 1)
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & it))
                else
                    md5StrBuff.append(Integer.toHexString(0xFF & it))
            }
            String res = md5StrBuff.toString()
            return res
        }
        return ''
    }

    public static String getErrorMessage(String errorCode) {
        errorCode = errorCode.toUpperCase()
        if (errorCode == 'ILLEGAL_SIGN')
            return "签名不正确"
        if (errorCode == 'ILLEGAL_DYN_MD5_KEY')
            return "动态密钥信息错误"
        if (errorCode == 'ILLEGAL_ENCRYPT')
            return "加密不正确"
        if (errorCode == 'ILLEGAL_ARGUMENT')
            return "参数不正确"
        if (errorCode == 'ILLEGAL_SERVICE')
            return "Service参数不正确"
        if (errorCode == 'ILLEGAL_USER')
            return "用户ID不正确"
        if (errorCode == 'ILLEGAL_PARTNER')
            return "合作伙伴ID不正确"
        if (errorCode == 'ILLEGAL_EXTERFACE')
            return "接口配置不正确"
        if (errorCode == 'ILLEGAL_PARTNER_EXTERFACE')
            return "合作伙伴接口信息不正确"
        if (errorCode == 'ILLEGAL_SECURITY_PROFILE')
            return "未找到匹配的密钥配置"
        if (errorCode == 'ILLEGAL_AGENT')
            return "代理ID不正确"
        if (errorCode == 'ILLEGAL_SIGN_TYPE')
            return "签名类型不正确"
        if (errorCode == 'ILLEGAL_CHARSET')
            return "字符集不合法"
        if (errorCode == 'HAS_NO_PRIVILEGE')
            return "无权访问"
        if (errorCode == 'INVALID_CHARACTER_SET')
            return "字符集无效"
        if (errorCode == 'TRADE_BUYER_NOT_MATCH')
            return "交易买家不匹配"
        if (errorCode == 'CONTEXT_INCONSISTENT')
            return "交易信息被篡改"
        if (errorCode == 'TRADE_HAS_SUCCESS')
            return "交易已经支付"
        if (errorCode == 'TRADE_NOT_EXIST')
            return "交易不存在"
        if (errorCode == 'TRADE_HAS_CLOSE')
            return "交易已经关闭"
        if (errorCode == 'REASON_ILLEGAL_STATUS')
            return "交易的状态不合法"
        if (errorCode == 'EXIST_FORBIDDEN_WORD')
            return "订单信息中包含违禁词"
        if (errorCode == 'PARTNER_ERROR')
            return "合作伙伴信息不正确"
        if (errorCode == 'ACCESS_FORBIDDEN')
            return "没有权限使用该产品"
        if (errorCode == 'SELLER_NOT_EXIST')
            return "卖家不存在"
        if (errorCode == 'BUYER_NOT_EXIST')
            return "买家不存在"
        if (errorCode == 'BUYER_ENABLE_STATUS_FORBID')
            return "买家状态非法，无法继续交易"
        if (errorCode == 'BUYER_SELLER_EQUAL')
            return "卖家买家账号相同，不能进行交易"
        if (errorCode == 'INVALID_PARAMETER')
            return "参数无效"
        if (errorCode == 'UN_SUPPORT_BIZ_TYPE')
            return "不支持的业务类型"
        if (errorCode == 'INVALID_RECEIVE_ACCOUNT')
            return "卖家不在设置的收款账户列表之中"
        if (errorCode == 'ROYALTY_INFO_VALIDATE_FAIL')
            return "分账信息校验失败"
        if (errorCode == 'SYSTEM_ERROR')
            return "支付宝系统错误"
        if (errorCode == 'SESSION_TIMEOUT')
            return "session超时"
        if (errorCode == 'ILLEGAL_TARGET_SERVICE')
            return "错误的target_service"
        if (errorCode == 'ILLEGAL_ACCESS_SWITCH_SYSTEM')
            return "partner不允许访问该类型的系统"
        if (errorCode == 'EXTERFACE_IS_CLOSED')
            return "接口已关闭"
        if (errorCode == 'TRADE_SETTLE_ERROR')
            return "分账信息校验失败"
        if (errorCode == 'SOUNDWAVE_PARSER_FAIL')
            return "动态ID解析失败"
        if (errorCode == 'REFUND_AMT_NOT_EQUAL_TOTAL')
            return "撤销或退款金额与订单金额不一致"
        if (errorCode == 'DISCORDANT_REPEAT_REQUEST')
            return "同一笔退款或撤销单号金额不一致"
        if (errorCode == 'TRADE_ROLE_ERROR')
            return "没有该笔交易的退款或撤销权限"
        if (errorCode == 'REASON_TRADE_BEEN_FREEZEN')
            return "交易已经被冻结"
        if (errorCode == 'TRADE_FINISHED')
            return "交易成功且结束，即不可再做任何操作";
        if (errorCode == 'TRADE_PENDING')
            return "等待卖家收款（买家付款后，如果卖家账号被冻结）";
        if (errorCode == 'TRADE_STATUS_ERROR')
            return "交易状态不合法"
        if (errorCode == 'TRADE_HAS_FINISHED')
            return "交易已结束";
        return "未知错误，${errorCode }，支付失败}"
    }
}
