//package com.example.knowledge_android.comparator;
//
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
///**
// * 微信支付工具
// */
//public abstract class AbstractWeiXinPayTask {
//
//    public boolean debug = false
//
//    private int seq = 0
//
//    abstract updateProgress(int progress)
//
//    private boolean isCancel = false
//
//    private WeiXinHttpPostRequest weiXinHttpPostRequest
//
//    void cancelTask() {
//        isCancel = true
//    }
//
//    O2oPayResultBo processWXPay(TranHead tranhead, String scannerCode, WeiXinHttpPostRequest weiXinHttpPostRequest) {
//        log.info("check transaction for weixin ...")
//        this.weiXinHttpPostRequest = weiXinHttpPostRequest
//
////        weiXinHttpPostRequest.refund("0806100148208888-1-1000401", "4200000600202008066796039135", "R0806100148208888-1-1000401", "1", "1", "");
//        updateProgress(1)
//        String payingAmount = String.valueOf(tranhead.getBalance().multiply(new BigDecimal(100)).intValue())
//        List<TranDetail> detailList = tranhead.getLineItems()
//        String body = ""
//        String goods_detail = ""
//        boolean ok = false
//        for (int i = 0; i < detailList.size(); i++) {
//            TranDetail l = (TranDetail) detailList.get(i)
//            if (body.length() + l.getPluName().length() > 30) {
//                if (!ok) {
//                    body = body.substring(1) + "等等...";
//                    ok = true
//                }
//            } else {
//                body += "," + l.getPluName();
//            }
//            String detail = ",{\"goods_id\":\"" + l.getPluNo() + "\",\"goods_name\":\"" + l.getPluName() +"\",\"quantity\":" + l.getQty().intValue() + ",\"price\":" + l.getOrigPrice().setScale(2, BigDecimal.ROUND_HALF_UP) + "}";
//            goods_detail += detail;
//        }
//        if (body.length() <= 30) {
//            body = body.substring(1)
//        }
//        goods_detail = "{\"goods_detail\":[" + goods_detail.substring(1) + "],\"cost_price\":" + payingAmount + "}";
//        body = "全额支付,商品:" + body
//        seq++
//        String seqStr = ""
//        if (seq < 10) {
//            seqStr = "0" + seq
//        } else {
//            seqStr = "" + seq
//        }
//
//        String out_trade_no = new SimpleDateFormat("MMddHHmmss").format(new Date()) + tranhead.getStoreId() + "-" + tranhead.getPosNo() + "-" + tranhead.getTransactionNumber() + seqStr
//        log.info("micropy")
//        //支付接口
//        updateProgress(2)
//        String xml = weiXinHttpPostRequest.micropay(scannerCode, out_trade_no, body, payingAmount, goods_detail)
//        updateProgress(3)
//        if (xml == null || xml.isEmpty()) {
//            log.info(" orderQuery ");
//            O2oPayResultBo resultBo = orderQuery(tranhead, scannerCode, payingAmount, out_trade_no, seqStr);
//            if (resultBo == null || resultBo.detail == null) {
//                log.info(" reverse ")
//                //冲正
//                weiXinHttpPostRequest.reverse(out_trade_no)
//                return new O2oPayResultBo(O2oPayResultBo.CODE_NO_RESPONSE_REVERSE, "", null)
//            } else {
//                return resultBo
//            }
//        }
//        O2oPayResultBo failResultVo = new O2oPayResultBo()
//        String isSuccess = getValueFromXml(xml, "<return_code><![CDATA[", "]]></return_code>")
//        if (isSuccess.equals("SUCCESS")) {
//            String result_code = getValueFromXml(xml, "<result_code><![CDATA[", "]]></result_code>")
//            if (result_code.equals("SUCCESS")) {
//                //支付成功
//                String openId = getValueFromXml(xml, "<openid><![CDATA[", "]]></openid>")
//                String transactionId = getValueFromXml(xml, "<transaction_id><![CDATA[", "]]></transaction_id>")
//                String promotionDetail = getValueFromXml(xml, "<promotion_detail><![CDATA[", "]]></promotion_detail>")
//                O2oPayDetail weiXin_detail = addWeiXinDetail(tranhead, scannerCode, payingAmount, transactionId, seqStr, openId, out_trade_no, promotionDetail)
//                return new O2oPayResultBo(O2oPayResultBo.CODE_SUCCESS, "", weiXin_detail)
//            } else {
//                String err_code = getValueFromXml(xml, "<err_code><![CDATA[", "]]></err_code>");
//                if (err_code.equals("USERPAYING")) {
//                    //支付等待，顾客确认密码
//                    log.info(" USERPAYING ");
//                    return processNotiryRequert(tranhead, scannerCode, payingAmount, seqStr, out_trade_no);
//                } else {
//                    //支付失败
//                    String err_code_des = getValueFromXml(xml, "<err_code_des><![CDATA[", "]]></err_code_des>")
//                    log.info(err_code_des)
//                    failResultVo.setResultMsg(err_code_des)
//                }
//            }
//        } else {
//            String return_msg = getValueFromXml(xml, "<return_msg><![CDATA[", "]]></return_msg>");
//            log.info(return_msg);
//            failResultVo.setResultMsg(return_msg)
//        }
//        //支付失败，则再次调用查询和撤销
//        System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS ").format(new Date()) + " orderQuery1 ");
//        log.info(" orderQuery1 ");
//        O2oPayResultBo resultBo = orderQuery(tranhead, scannerCode, payingAmount, out_trade_no, seqStr);
//        if (resultBo == null || resultBo.detail == null) {
//            System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS ").format(new Date()) + " reverse2 ");
//            log.info(" reverse2 ")
//            weiXinHttpPostRequest.reverse(out_trade_no);//冲正
//            failResultVo.setResultCode(O2oPayResultBo.CODE_PAY_FAIL)
//            return failResultVo
//        } else {
//            return resultBo
//        }
//    }
//
//    //异步请求
//    O2oPayResultBo processNotiryRequert(TranHead tranHead, String scannerCode, String payingAmount, String seqStr, String out_trade_no) {
//        for (int i = 0; i < 60 * 5; i++) {//请求五分钟
//            try {
//                Thread.sleep(1000);
//                if (isCancel) {
//                    //如果按了清除键，才停止请求
//                    weiXinHttpPostRequest.reverse(out_trade_no);//冲正
//                    return new O2oPayResultBo(O2oPayResultBo.CODE_PAY_FAIL_CANCEL, "", null)
//                }
//                if ((i + 1) % 5 == 0) {
//                    //5秒请求一次
//                    String xml = weiXinHttpPostRequest.orderquery(out_trade_no);//查询
//                    if (xml == null || xml.equals("")) {
//                        log.info("WanOfflineCanNotTrade... ");
//                        continue
//                    }
//                    String return_code = getValueFromXml(xml, "<return_code><![CDATA[", "]]></return_code>");
//                    if (return_code.equals("SUCCESS")) {
//                        //查询成功
//                        String result_code = getValueFromXml(xml, "<result_code><![CDATA[", "]]></result_code>");
//                        if (result_code.equals("SUCCESS")) {
//                            //查询订单成功
//                            String trade_state = getValueFromXml(xml, "<trade_state><![CDATA[", "]]></trade_state>")
//                            //SUCCESS--支付成功 REFUND--转入退款 NOTPAY--未支付 CLOSED--已关闭 REVERSE--已冲正 REVOK--已撤销
//                            if (trade_state.equals("SUCCESS")) {
//                                String openId = getValueFromXml(xml, "<openid><![CDATA[", "]]></openid>")
//                                String transactionId = getValueFromXml(xml, "<transaction_id><![CDATA[", "]]></transaction_id>")
//                                String promotionDetail = getValueFromXml(xml, "<promotion_detail><![CDATA[", "]]></promotion_detail>")
//                                O2oPayDetail detail = addWeiXinDetail(tranHead, scannerCode, payingAmount, transactionId, seqStr, openId, out_trade_no, promotionDetail)
//                                return new O2oPayResultBo(O2oPayResultBo.CODE_SUCCESS, "", detail)
//                            } else if (trade_state.equals("USERPAYING")) {
//                                continue
//                            } else {
//                                log.info(getTrade_state(trade_state))
//                                //冲正
//                                weiXinHttpPostRequest.reverse(out_trade_no)
//                                return new O2oPayResultBo(O2oPayResultBo.CODE_PAY_FAIL, trade_state, null)
//                            }
//                        } else {
//                            String err_code_des = getValueFromXml(xml, "<err_code_des><![CDATA[", "]]></err_code_des>")
//                            log.info(err_code_des)
//                        }
//                    } else {//查询失败
//                        String return_msg = getValueFromXml(xml, "<return_msg><![CDATA[", "]]></return_msg>")
//                        log.info(return_msg)
//                    }
//                }
//                continue
//            } catch (InterruptedException e) {
//                break
//            }
//        }
//        //冲正
//        weiXinHttpPostRequest.reverse(out_trade_no)
//        return new O2oPayResultBo(O2oPayResultBo.CODE_PAY_FAIL, "", null)
//    }
//
//    O2oPayResultBo orderQuery(TranHead tranhead, String scannerCode, String payingAmount, String out_trade_no, String seqStr) {
//        String xml = weiXinHttpPostRequest.orderquery(out_trade_no);//查询
//        if (xml == null || xml.equals("")) {
//            log.info("WanOfflineCanNotTrade... ");
//            return new O2oPayResultBo(O2oPayResultBo.CODE_NO_RESPONSE_NOT_FOUND, "", null)
//        }
//        String return_code = getValueFromXml(xml, "<return_code><![CDATA[", "]]></return_code>");
//        if (return_code.equals("SUCCESS")) {//查询成功
//            String result_code = getValueFromXml(xml, "<result_code><![CDATA[", "]]></result_code>");
//            if (result_code.equals("SUCCESS")) {//查询订单成功
//                String trade_state = getValueFromXml(xml, "<trade_state><![CDATA[", "]]></trade_state>");
//                //SUCCESS--支付成功 REFUND--转入退款 NOTPAY--未支付 CLOSED--已关闭 REVERSE--已冲正 REVOK--已撤销
//                if (trade_state.equals("SUCCESS")) {
//                    String openId = getValueFromXml(xml, "<openid><![CDATA[", "]]></openid>");
//                    String transactionId = getValueFromXml(xml, "<transaction_id><![CDATA[", "]]></transaction_id>");
//                    String totalFee = getValueFromXml(xml, "<total_fee>", "</total_fee>");
//                    if (new BigDecimal(totalFee).compareTo(new BigDecimal(payingAmount)) != 0) {
////                        BigDecimal t = new BigDecimal(totalFee).divide(new BigDecimal(100), 2, 4);
//                        return new O2oPayResultBo(O2oPayResultBo.CODE_PAY_FAIL_AMOUNT_ERROR, "", null)
//                    }
//                    String promotionDetail = getValueFromXml(xml, "<promotion_detail><![CDATA[", "]]></promotion_detail>");
//                    O2oPayDetail detail = addWeiXinDetail(tranhead, scannerCode, payingAmount, transactionId, seqStr, openId, out_trade_no, promotionDetail)
//                    return new O2oPayResultBo(O2oPayResultBo.CODE_SUCCESS, "", detail)
//                } else {
//                    log.info(getTrade_state(trade_state));
//                    return new O2oPayResultBo(O2oPayResultBo.CODE_PAY_FAIL, "", null)
//                }
//            } else {
//                String err_code_des = getValueFromXml(xml, "<err_code_des><![CDATA[", "]]></err_code_des>");
//                log.info(err_code_des);
//                return new O2oPayResultBo(O2oPayResultBo.CODE_PAY_FAIL, err_code_des, null)
//            }
//        } else {//查询失败
//            String return_msg = getValueFromXml(xml, "<return_msg><![CDATA[", "]]></return_msg>");
//            log.info(return_msg)
//            return new O2oPayResultBo(O2oPayResultBo.CODE_PAY_FAIL, return_msg, null)
//        }
//    }
//
//    O2oPayDetail addWeiXinDetail(TranHead tranhead, String scannerCode, String payingAmount, String transactionId,
//                                 String seq, String openId, String out_trade_no, String promotionDetail) {
//        O2oPayDetail weiXin_detail
//        try {
//            BigDecimal amt = new BigDecimal(payingAmount).divide(new BigDecimal(100), 2, 4).setScale(2, BigDecimal.ROUND_HALF_UP);
//            weiXin_detail = new O2oPayDetail()
//            weiXin_detail.setStoreId(tranhead.getStoreId())
//            weiXin_detail.setPosNo(tranhead.getPosNo())
//            weiXin_detail.setTransactionNumber(tranhead.getTransactionNumber())
//            weiXin_detail.setBuyerPayAmount(amt)
//            weiXin_detail.setPayAmount(amt)
//            weiXin_detail.setCode(scannerCode)
//            weiXin_detail.setTradeNo(out_trade_no)
//            weiXin_detail.setOpenId(openId)
//            weiXin_detail.setO2oPayId(transactionId)
//            weiXin_detail.setSysTradeNo(transactionId)
//            weiXin_detail.setSystemDate(new Date())
//            weiXin_detail.setMerchantDiscount(BigDecimal.ZERO)
//            weiXin_detail.setPlatDiscount(BigDecimal.ZERO)
//            weiXin_detail.setOtherDicount(BigDecimal.ZERO)
//            weiXin_detail.setUploadFlag('0')
//            tranhead.getO2oPayDetails().add(weiXin_detail)
//            log.info("transactionNumber：" + weiXin_detail.getTransactionNumber() + "，transaction_id："
//                    + weiXin_detail.getO2oPayId() + ",systemDate：" + weiXin_detail.getSystemDate().toString()
//                    + " is add trans.addWeiXinDetailList")
//            this.seq = 0
//            return weiXin_detail;
//        } catch (Exception e) {
//            log.error("", e)
//        }
//        return null
//    }
//
//    String getValueFromXml(String xml, String token1, String token2) {
//        String result = "";
//        int a = xml.indexOf(token1);
//        int b = xml.indexOf(token2);
//
//        if (a >= 0 && b >= 0) {
//            result = xml.substring(a + token1.length(), b);
//        }
//        return result;
//    }
//
//    String getTrade_state(String trade_state) {
//        if (trade_state.equals("REFUND"))
//            return "交易转入退款";
//        else if (trade_state.equals("CLOSED"))
//            return "交易已关闭";
//        else if (trade_state.equals("REVERSE"))
//            return "交易已冲正";
//        else if (trade_state.equals("REVOK"))
//            return "交易已撤销";
//        else if (trade_state.equals("PAYERROR"))
//            return "支付失败(其他原因，如银行返回失败)";
//        else if (trade_state.equals("NOPAY"))
//            return "未支付(确认支付超时)";
//        return "";
//    }
//}
