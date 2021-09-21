package com.example.knowledge_android.comparator;

public class PosConstants {
    public static final char RIGHT_YES_CHAR = 'Y';
    public static final char RIGHT_TRUE_CHAR = '1';

    public static final int SUCCESSFUL = 0;
    public static final int NETWORK_NG = 1;


    public static final int LOG_NORMAL = 0;
    public static final int LOG_VERBOSE = 1;

    //没有下载到新的Message表数据
    public static final int NO_NEW_MESSAGE = 0;


    public final static String TRAN_DETAIL_CODE_销售 = "S";
    public final static String TRAN_DETAIL_CODE_退货 = "R";
    public final static String TRAN_DETAIL_CODE_SI折扣 = "D";
    public final static String TRAN_DETAIL_CODE_MixMatch = "M";
    public final static String TRAN_DETAIL_CODE_代售 = "I";
    public final static String TRAN_DETAIL_CODE_代收公共事业费 = "O";
    public final static String TRAN_DETAIL_CODE_代付 = "Q";
    public final static String TRAN_DETAIL_CODE_指定更正 = "V";
    public final static String TRAN_DETAIL_CODE_立即更正 = "E";
    public final static String TRAN_DETAIL_CODE_投库 = "G";
    public final static String TRAN_DETAIL_CODE_借零 = "H";
    public final static String TRAN_DETAIL_CODE_PaidIn = "N";
    public final static String TRAN_DETAIL_CODE_PaidOut = "T";
    public final static String TRAN_DETAIL_CODE_舍零去分 = "A";

    public final static String CASHIER_LEVEL_管理员 = "99";

    public final static String BUTTONPANEL_NUMPAD = "numpad";
    public final static String BUTTONPANEL_NUMPAD2 = "numpad2";
    public final static String BUTTONPANEL_ADMINFUNCTION = "adminfunction";
    public final static String BUTTONPANEL_ADMINFUNCTION_SELF = "adminfunction_self";
    public final static String BUTTONPANEL_PLU = "plu";
    public final static String BUTTONPANEL_PAYMENT = "payment";
    public final static String BUTTONPANEL_PLUDISCARD = "discard"; //商品报废面板
    public final static String BUTTONPANEL_ORDER = "order"; //预约下单面板
    public final static String BUTTONPANEL_PAYMENT_THIRD = "Payment_";

    public final static int OPERATION_STATE_登录 = 1;
    public final static int OPERATION_STATE_交易 = 2;
    public final static int OPERATION_STATE_交班 = 3;
    public final static int OPERATION_STATE_日结 = 4;

    public final static int OPERATION_STATE_保留 = 5;
    public final static int OPERATION_STATE_回调 = 6;

    public final static int OPERATION_STATE_扫描退货 = 7;
    public final static int OPERATION_STATE_FF制作 = 8;
    public final static int OPERATION_STATE_小票退货 = 9;

    public final static int SUPER_OPERATION_STATE_正常 = 0;
    public final static int SUPER_OPERATION_STATE_登录窗体展示状态 = 1;
    public final static int SUPER_OPERATION_STATE_锁屏窗体展示状态 = 2;
    public final static int SUPER_OPERATION_STATE_展示更新履历状态 = 3;
    public final static int SUPER_OPERATION_STATE_展示加载窗体状态 = 4;


    // 录制
    public static final int RECORD_STOP = 0;
    public static final int RECORD_START = 1;
    public static final int REPLAYING = 2;

    public final static int DRAWER_REC_OK = 0;
    public final static int DRAWER_OPEN = 1;
    public final static int DRAWER_CLOSE = 2;
    public final static int DRAWER_REC_ERROR = -1 ;//异常情况

    public final static int TRANSACTION_PRINT_开始 = 1;
    public final static int TRANSACTION_PRINT_失败 = 2;
    public final static int TRANSACTION_PRINT_结束 = 3;


    /**====================自定义=================================*/
    public final static int PRINTER_STATUS_OK = 0 ;        /**打印机状态正常*/
    public final static int DONOT_HAVE_PAPER = -3  ;    /**打印机没纸了*/
    public final static int PRINTER_OTHER_ERROR = -4 ;  /**异常情况*/
    public final static int PRINTER_PAPER_JAM = -5 ;    /**纸卡住了*/
    /**=========================================================*/
    /**打印机状态*/
    public final static int PRINTER_COVER_OPEN = -1 ;       /*Printer cover is open*/
    public final static int PTR_SUE_COVER_OK = -2 ;         /*Printer cover is closed*/
    public final static int PTR_SUE_JRN_EMPTY = -3  ;       /*No journal paper*/
    public final static int PTR_SUE_JRN_NEAREMPTY = -4 ;    /*Journal paper is low*/
    public final static int PTR_SUE_JRN_PAPEROK = -5 ;      /*Journal paper is ready*/
    public final static int PTR_SUE_REC_EMPTY = -6 ;        /*No Receipt paper*/
    public final static int PTR_SUE_REC_NEAREMPTY = -7  ;   /*Receipt paper is low*/
    public final static int PTR_SUE_REC_PAPEROK = -8  ;     /*Receipt paper is ready*/

    /**
     * All asynchronous output has finished, either
     * successfully or because output has been cleared. The
     * printer State is now S_IDLE. The FlagWhenIdle
     * property must be true for this event to be delivered,
     * and the property is automatically reset to false just
     * before the event is delivered.
     */
    public final static int PTR_SUE_IDLE = -9;

    public final static int PTR_SUE_ALARM = -10;  /*A hardware alarm or the printer reset in progress*/
    public final static int PTR_SUE_PRNOK = -11;  /*The printer has been reset successfully*/

    /**
     * Printing in progress was suspended.
     */
    public final static int PTR_SUE_SUSPENDED = -12;
    /**
     * Printing is completed. This event is fired if the
     * registry is set to report the output complete by this
     * event (see Output Complete Events/Error Events).
     */
    public final static int PTR_SUE_WRITEEND = -13;

    /**
     * Paper jam has occurred. This event is fired if the
     * JamStatusInfo registry is set to fire this event
     * (see Paper Jam Alarm)
     */
    public final static int PTR_SUE_REC_JAM = -14;

    /**
     * Paper jam has been cleared. This event is fired if
     * paper jam alarm is on and the paper jam is cleared
     * (see Paper Jam Alarm).
     */
    public final static int PTR_SUE_REC_NOTJAM = -15;

    /**
     * pos类型-标准pos机
     */
    public final static String POSTYPE_POS = "Pos";

    /**
     * pos类型-自助pos机
     */
    public final static String POSTYPE_SELF = "Self";

    /**=========================================================*/
    // 自制条码规则
    /**
     * 收银员条码前缀
     */
    public final static String BARCODE_PRE_CASHIER = "HYC";

    /**
     * 堂食外带
     */
    public final static int TRAN_TAKE_IN = 1;
    public final static int TRAN_TAKE_OUT = 2;
}
