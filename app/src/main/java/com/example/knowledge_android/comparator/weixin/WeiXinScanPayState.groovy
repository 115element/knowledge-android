//package com.example.knowledge_android.comparator.weixin
//
//import groovy.transform.CompileStatic
//import groovy.util.logging.Slf4j
//import hyi.cream.PosConstants
//import hyi.cream.dao.DaoLocator
//import hyi.cream.event.POSButtonEvent
//import hyi.cream.model.O2oPayDetail
//import hyi.cream.model.bo.O2oPayResultBo
//import hyi.cream.net.RuOk
//import hyi.cream.ui.button.ClearButton
//import hyi.cream.ui.button.ConfirmButton
//import hyi.cream.util.AbstractWeiXinPayTask
//import hyi.cream.util.WeiXinHttpPostRequest
//import javafx.animation.ScaleTransition
//
//@Slf4j
//@CompileStatic
//@Singleton(strict = false)
//class LawsonWeiXinScanPayState extends WeiXinScanPayState {
//
//    LawsonWeiXinScanPayState() {
//        setPaymentDao(DaoLocator.paymentDao)
//    }
//
//    @Override
//    O2oPayResultBo processWeiXinPay() {
//        AbstractWeiXinPayTask task = new AbstractWeiXinPayTask() {
//            @Override
//            def updateProgress(int progress) {
//                return null
//            }
//        }
//
//        O2oPayResultBo resultBo = null
//        try {
//            resultBo = task.processWXPay(app.currentTransaction, scannerCode, new WeiXinHttpPostRequest() {
//                @Override
//                InputStream getCertFileInputStream() {
//                    return new FileInputStream(new File(param.configDir + File.separator + "weixin.p12"))
//                }
//            })
//        } catch (Exception ex) {
//            log.error("", ex)
//        }
//        posScreen.hideLoadingMessageComponent()
//        if (resultBo && O2oPayResultBo.CODE_SUCCESS == resultBo.getResultCode()) {
//            O2oPayDetail detail = resultBo.getDetail()
//            detail.setPayId(payment.getPayId())
//            detail.setPayName(payment.getPayName())
//            StateMachine.instance.processEvent(new POSButtonEvent(new ConfirmButton()))
//        } else {
//            StateMachine.instance.processEvent(new POSButtonEvent(new ClearButton()))
//        }
//        return null
//    }
//}
