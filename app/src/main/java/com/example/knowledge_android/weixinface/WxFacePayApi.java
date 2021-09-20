package com.example.knowledge_android.weixinface;

import android.content.Intent;
import android.os.Handler;
import android.os.RemoteException;
import android.text.TextUtils;

import com.example.knowledge_android.OneApplication;
import com.example.knowledge_android.R;
import com.example.knowledge_android.daosupport.bean.tran.TranHead;
import com.example.knowledge_android.fragment.pos_screen.PosScreenMainActivity;
import com.example.knowledge_android.knowledge.ThreadUtils;
import com.example.knowledge_android.msharedpreferences.MSharedPreferences;
import com.example.knowledge_android.statemachine.StateMachine;
import com.example.knowledge_android.statemachine.mybutton.ClearButton;
import com.example.knowledge_android.statemachine.mybutton.EnterButton;
import com.example.knowledge_android.statemachine.myevent.PosButtonEvent;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tencent.wxpayface.IWxPayfaceCallback;
import com.tencent.wxpayface.WxPayFace;
import com.tencent.wxpayface.WxfacePayCommonCode;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * 微信刷脸支付
 */
public class WxFacePayApi {
    private static Logger log = LoggerFactory.getLogger(WxFacePayApi.class);

    /*获取微信人脸识别凭证*/
    public static final String getFaceAuthInfo = "https://payapp.weixin.qq.com/face/get_wxpayface_authinfo";
    public static final String getFacePay = "https://api.mch.weixin.qq.com/pay/facepay";

    private static WxFacePayApi wxFacePayApi;


    private String appid = "";
    private String mch_id = "";
    private String weixinPartnerKey = "";
    private String store_id = "";
    private String store_name = "";



    private String payAmount;

    public static WxFacePayApi getInstance() {
        if (wxFacePayApi == null) {
            wxFacePayApi = new WxFacePayApi();
        }
        return wxFacePayApi;
    }

    /**
     * 这里判断autoInfo是否过期。如果过期则重新获取rawData
     */
    public void startFacePay(BigDecimal amount) {
        OneApplication app = OneApplication.getInstance();
        TranHead tranhead = new TranHead();//app.getCurrentTransaction();
        String outTradeNo = new SimpleDateFormat("MMddHHmmss").format(new Date()) + tranhead.getStoreId() + "-" + tranhead.getPosNo() + "-" + tranhead.getTransactionNumber();
        this.payAmount = String.valueOf(amount.multiply(new BigDecimal(100)).intValue());

        MSharedPreferences settings = app.getmSettings();
        String autoInfo = settings.getWxFacePayAutoInfo();
        if ("".equals(autoInfo)) {
            //本地储存的没有autoInfo直接去获取rawData
            getFaceRawData(outTradeNo);
        } else {
            long getAutoInfoTime = settings.getWxFacePayRowdataTime();
            long expires_in = settings.getWxFacePayExpiresIn();
            long time_difference = (int) (System.currentTimeMillis() - getAutoInfoTime);
            //expires_in  提供的单位是秒，这里需要转换为毫秒值
            if (time_difference > expires_in * 1000) {
                //autoInfo已经过期，重新获取rawData
                getFaceRawData(outTradeNo);
            } else {
                //autoInfo没有过去。直接调用刷脸支付
                wxFacePayVoucherSuccess(autoInfo, outTradeNo);
            }
        }
    }

    /**
     * 刷脸已经成功，人脸验证成功后进行支付操作
     */
    private void getWxPayFaceCodeSuccess(String faceText, String faceCode, String openId, String outTradeNo) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", appid);
        map.put("mch_id", mch_id);
        map.put("device_info", "" + (System.currentTimeMillis() / 100000));
        map.put("nonce_str", "" + (System.currentTimeMillis() / 100000));
        map.put("body", "test-store");
        map.put("out_trade_no", outTradeNo);
        map.put("total_fee", payAmount);
        map.put("spbill_create_ip", "192.168.56.1");
        map.put("sign_type", "MD5");
        map.put("openid", openId);
        map.put("face_code", faceCode);
        //按字典顺序排序
        //使用&符号进行频率
        String sbR = WxFacePayTools.getSortedHashMapByKey(map) + "key=" + weixinPartnerKey;
        //进行MD5加密之后  转大写
        String sign = WxFacePayTools.encode(sbR).toUpperCase();

        map.put("sign", sign);
        try {
            String toXml = WxFacePayTools.mapToXml(map);
            /**
             *  第五步：人脸验证已经成功开始 调用支付接口进行扣款
             */
            startWxPayFace(toXml);
        } catch (Exception e) {
            log.error("", e);
            showMessageOnSnackbar("支付失败");
            payFailAndReturn();
        }
    }

    /**
     * 开始支付
     */
    private void startWxPayFace(String toXml) throws Exception {
        log.info("startWxPayFace=", toXml);
        RequestBody body = RequestBody.create(null, toXml);
        OkGo.<String>post(getFacePay)
                .tag(getFacePay)
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String payVoucher = response.body();
                        log.info("微信支付流程完毕" + payVoucher);
                        String result = null;
                        try {
                            result = WxFacePayTools.parseGetAuthInfoXML(payVoucher, WxConstant.RETURN_CODE);
                            if (WxConstant.SUCCESS.equals(result)) {
                                result = WxFacePayTools.parseGetAuthInfoXML(payVoucher, WxConstant.RESULT_CODE);
                                if (WxConstant.SUCCESS.equals(result)) {
//                                    showMessageOnSnackbar("刷脸支付成功");
                                    paySuccessAndReturn();
                                } else {
//                                    showMessageOnSnackbar("刷脸支付失败");
                                    payFailAndReturn();
                                }
                            } else {
//                                showMessageOnSnackbar("刷脸支付失败");
                                payFailAndReturn();
                            }
                        } catch (Exception e) {
                            log.error("", e);
//                            showMessageOnSnackbar("刷脸支付失败");
                            payFailAndReturn();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        log.info("微信支付流程失败" + response);
                        showMessageOnSnackbar("刷脸支付失败");
                        payFailAndReturn();
                    }
                });
    }

    /**
     * 用户支付失败
     */
    private void userCancelFacePay(int cancelType) {
        if (cancelType == WxConstant.USER_CANCEL_FACE_PAY) {
            //用户取消支付
            log.warn("用户取消了支付");
//            showMessageOnSnackbar("用户取消了支付");
            payFailAndReturn();
        } else if (cancelType == WxConstant.USER_BAR_FACE_PAY) {
            //用户使用扫码支付
            log.warn("用户点击了扫码支付");
//            showWarningMessage("用户点击了扫码支付");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    PosScreenMainActivity activity = getMainActivity();
                    initWxScanBar();
                    Intent intent = activity.getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(activity.getBaseContext().getPackageName());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivity(intent);
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }, 500);

        } else if (cancelType == WxConstant.USER_NO_PERMISSION) {
            //用户没有权限
            log.warn("没有权限");
            showMessageOnSnackbar("没有权限,刷脸支付失败");
            payFailAndReturn();
        } else if (cancelType == WxConstant.USER_PAY_FAILED) {
            //用户支付失败
            log.warn("支付失败");
            showMessageOnSnackbar("刷脸支付失败");
            payFailAndReturn();
        }
    }

    /**
     * 开启扫码支付
     */
    private void initWxScanBar() {
        WxPayFace.getInstance().startCodeScanner(new IWxPayfaceCallback() {
            @Override
            public void response(Map info) throws RemoteException {
                if (WxFacePayTools.isFacePaySuccessInfo(info)) {
                    String code_msg = (String) info.get(WxConstant.CODE_MSG);
                    showMessageOnSnackbar("获取到二维码信息" + code_msg);
                    log.info("获取到二维码信息" + code_msg);
                    /**
                     * 拿到二维码信息之后，去做支付操作
                     */
//                    ThreadUtils.runOnMainThread(
//                            new Runnable() {
//                                @Override
//                                void run() {
//                                    scanBarCode.setText("获取到二维码信息" + code_msg)
//                                }
//                            }
//                    );

                } else {
                    showMessageOnSnackbar("扫码失败");
                    log.warn("扫码失败");
                }
            }
        });
    }

    /**
     * 调用人脸识别
     */
    private void getWxPayFaceCode(HashMap<String, String> params, String outTradeNo) {
        WxPayFace.getInstance().getWxpayfaceCode(params, new IWxPayfaceCallback() {
            @Override
            public void response(Map map) {
                if (!WxFacePayTools.isFacePaySuccessInfo(map)) {
                    showMessageOnSnackbar("支付失败");
                    payFailAndReturn();
                    return;
                }
                final String code = (String) map.get(WxConstant.RETURN_CODE);
                ThreadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.equals(code, WxfacePayCommonCode.VAL_RSP_PARAMS_SUCCESS)) {
                            String faceCode = (String) map.get(WxConstant.FACE_CODE);
                            String openId = (String) map.get(WxConstant.OPEN_ID);
                            getWxPayFaceCodeSuccess(map.toString(), faceCode, openId, outTradeNo);
                        } else if (TextUtils.equals(code, WxfacePayCommonCode.VAL_RSP_PARAMS_USER_CANCEL)) {
//                            showWarningMessage("用户取消");
                            userCancelFacePay(WxConstant.USER_CANCEL_FACE_PAY);
                        } else if (TextUtils.equals(code, WxfacePayCommonCode.VAL_RSP_PARAMS_SCAN_PAYMENT)) {
//                            showWarningMessage("扫码支付");
                            userCancelFacePay(WxConstant.USER_BAR_FACE_PAY);
                        } else if (TextUtils.equals(code, "FACEPAY_NOT_AUTH")) {
//                            showWarningMessage("无即时支付无权限");
                            userCancelFacePay(WxConstant.USER_NO_PERMISSION);
                        } else {
//                            showWarningMessage("刷脸支付失败");
                            userCancelFacePay(WxConstant.USER_PAY_FAILED);
                        }
                    }
                });
            }
        });
    }

    /**
     * 获取到人脸支付凭证  调用微信刷脸支付程序进行刷脸动作
     */
    private void wxFacePayVoucherSuccess(String authinfo, String outTradeNo) {
        HashMap<String, String> map = new HashMap<>();
        map.put("appid", appid);
        map.put("mch_id", mch_id);
        map.put("store_id", store_id);
        map.put("out_trade_no", outTradeNo);
        map.put("total_fee", payAmount);
        map.put("face_code_type", "0");
        map.put("ignore_update_pay_result", "0");
        map.put("face_authtype", "FACEPAY");
        map.put("authinfo", authinfo);
        map.put("ask_face_permit", "0");
        map.put("ask_ret_page", "1");
        /**
         * 人脸支付第四步，调用摄像头进行刷脸
         */
        getWxPayFaceCode(map, outTradeNo);
    }


    /**
     * 获取微信人脸支付凭证
     */
    private void getWxFacePayVoucher(String rawdata, String outTradeNo) {
        //储存RawData的时间。
        OneApplication app = OneApplication.getInstance();
        MSharedPreferences settings = app.getmSettings();
        settings.setWxFacePayRowdataTime(System.currentTimeMillis());
        Map<String, String> map = new HashMap<>();
        map.put("appid", appid);
        map.put("mch_id", mch_id);
        map.put("now", "" + (int) (System.currentTimeMillis() / 1000));
        map.put("version", "1");
        map.put("sign_type", "MD5");
        map.put("nonce_str", "" + (int) (System.currentTimeMillis() / 100000));
        map.put("store_id", store_id);
        map.put("store_name", store_name);
        map.put("device_id", "" + (System.currentTimeMillis() / 100000));
        map.put("rawdata", rawdata);
        //     map.put("spbill_create_ip",  (null == ipAddress) ? "" : ipAddress);

        //按字典顺序排序
        //使用&符号进行频率
        String sbR = WxFacePayTools.getSortedHashMapByKey(map) + "key=" + weixinPartnerKey;
        //进行MD5加密之后  转大写
        String sign = WxFacePayTools.encode(sbR).toUpperCase();
        map.put("sign", sign);
        log.info("认证参数" + map.toString());
        try {
            String toXml = WxFacePayTools.mapToXml(map);
            log.info("认证参数XMl" + toXml);
            RequestBody body = RequestBody.create(null, toXml);
            OkGo.<String>post(WxFacePayApi.getFaceAuthInfo)
                    .tag(WxFacePayApi.getFaceAuthInfo)
                    .upRequestBody(body)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            String payVoucher = response.body();
                            log.info("获取到的微信支付凭证" + payVoucher);
                            try {
                                String return_code = WxFacePayTools.parseGetAuthInfoXML(payVoucher, WxConstant.RETURN_CODE);
                                if (WxConstant.SUCCESS.equals(return_code)) {
                                    //获取微信凭证成功
                                    String authinfo = WxFacePayTools.parseGetAuthInfoXML(payVoucher, "authinfo");
                                    /*获取到authInfo有效时间。在这个有效时间只能就可以不用重复获取autoInfo*/
                                    String expires_in = WxFacePayTools.parseGetAuthInfoXML(payVoucher, "expires_in");
                                    //这里使用sp储存这个时间和autoInfo(这里储存在sp中方便，只做演示，如果考虑安全性可以放到加密数据库中)
                                    settings.setWxFacePayAutoInfo(authinfo);
                                    //储存过期时间.用来判断是否需要重新获取autoInfo
                                    settings.setWxFacePayExpiresIn((expires_in == null) ? 0L : Long.valueOf(expires_in));
                                    log.info("获取到的微信支付凭证infoXML" + authinfo);
                                    wxFacePayVoucherSuccess(authinfo, outTradeNo);
                                } else {
                                    String return_msg = WxFacePayTools.parseGetAuthInfoXML(payVoucher, WxConstant.RETURN_MSG);
                                    log.warn("return_msg=" + return_msg);
                                    showMessageOnSnackbar("获取支付凭证失败");
                                    payFailAndReturn();
                                }
                            } catch (Exception e) {
                                log.error("", e);
                                showMessageOnSnackbar("获取微信凭证失败");
                                payFailAndReturn();
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            log.error("获取微信凭证失败" + response.body());
                            showMessageOnSnackbar("获取微信凭证失败");
                            payFailAndReturn();
                        }
                    });
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void getFaceRawData(String outTradeNo) {
        /*开始获取RawData*/
        WxPayFace.getInstance().getWxpayfaceRawdata(new IWxPayfaceCallback() {
            @Override
            public void response(Map map) throws RemoteException {
                if (!WxFacePayTools.isFacePaySuccessInfo(map)) {
                    showWarningMessage(R.string.facepay_raw_data_failed);
                } else {
                    //获取到RAW_DATA
                    if (map.get(WxConstant.RAW_DATA) != null) {
                        String rawdata = map.get(WxConstant.RAW_DATA).toString();
                        getWxFacePayVoucher(rawdata, outTradeNo);
                    } else {
                        showWarningMessage(R.string.facepay_raw_data_failed);
                        payFailAndReturn();
                    }
                }
            }
        });
    }

    /**
     * 初始化微信刷脸支付
     */
    public void initWxPay() {
        OneApplication app = OneApplication.getInstance();
        //if ("facepos".equals(app.getDeviceType())) {
        if ("facepos".equals("facepos")) {
            Map<String, String> agencyMap = new HashMap<>(0);
            WxPayFace.getInstance().initWxpayface(getMainActivity().getApplicationContext(), agencyMap, new IWxPayfaceCallback() {
                @Override
                public void response(Map map) throws RemoteException {
                    /**
                     * 拿到初始化结果
                     *     对比code是否为SUCCESS  详细可以查看官方错误码
                     */
                    if (map != null) {
                        String code = (String) map.get(WxConstant.RETURN_CODE);
                        String msg = (String) map.get(WxConstant.RETURN_MSG);
                        log.info("initWxpayface code={} msg={}", code, msg);
                        if (!(code != null && code.equals(WxfacePayCommonCode.VAL_RSP_PARAMS_SUCCESS))) {
                            showWarningMessage(R.string.facepay_init_failed);
                        } else {
                            showMessageOnSnackbar(R.string.facepay_init_success);
                        }
                    }
                }
            });
        }
    }

    void showWarningMessage(int message) {
        showWarningMessage(getMainActivity().getString(message));
    }

    void showWarningMessage(String message) {
        getMainActivity().showWarningMessage(message);
    }

    void showMessageOnSnackbar(int message) {
        getMainActivity().showMessageOnSnackbar(getMainActivity().getString(message));
    }

    void showMessageOnSnackbar(String message) {
        getMainActivity().showMessageOnSnackbar(message);
    }

    private PosScreenMainActivity getMainActivity() {
        return OneApplication.getInstance().getMainActivity();
    }

    /**
     * 支付失败并退出
     */
    private void payFailAndReturn() {
        StateMachine.getInstance().processEvent(new PosButtonEvent(new ClearButton()));
    }

    /**
     * 支付成功并退出
     */
    private void paySuccessAndReturn() {
        StateMachine.getInstance().processEvent(new PosButtonEvent(new EnterButton()));
    }
}
