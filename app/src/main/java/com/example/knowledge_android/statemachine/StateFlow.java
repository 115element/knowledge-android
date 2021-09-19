package com.example.knowledge_android.statemachine;

import com.example.knowledge_android.statemachine.mybutton.ClearButton;
import com.example.knowledge_android.statemachine.mybutton.EnterButton;
import com.example.knowledge_android.statemachine.mystate.IdleState;
import com.example.knowledge_android.statemachine.mystate.InitialState;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;


public class StateFlow {

    public static Map<Class<? extends State>, List<Tx>> stateChartMap = Maps.newHashMap();

    static {
        stateChartMap.put(InitialState.class, Lists.newArrayList(new Tx(IdleState.class)));
        stateChartMap.put(IdleState.class, Lists.newArrayList(new Tx(Lists.newArrayList(ClearButton.class, EnterButton.class))));
    }

    public static List<Tx> buildTx(Class... states) {
        List<Tx> txList = Lists.newArrayList();
        for (Class state : states) {
            Tx tx = new Tx(state);
            txList.add(tx);
        }
        return txList;
    }



/*
    @Newify([Tx])
    Map<Class<? extends State>, List<Tx>> stateChartMap = [
            (InitialState)                     : [Tx(IdleState)], // triggerLess to IdleState

            (IdleState)                        : [Tx([PluButton], PluReadyState),
                                                  Tx([Scanner], ScannerCodeDisposeState),
                                                  Tx([NumberButton], NumberingState),
                                                  Tx([ClearButton], IdleState),
                                                  Tx([EnterButton, BackIdleStateButton, MemberShowButton, TakeOutButton,
                                                      TakeOutReturnButton, OffLineTakeWayGoBackButton, OffLinePeddleGoBackButton,
                                                      ReplaceReceiveButton, DaishouButton, ChongminButton, AladingButton]), // IdleState decides where it should go after hitting Enter
                                                  Tx([SwitchToFunctionPanelButton], FunctionReadyState),
                                                  Tx([FFStockButton]),
                                                  Tx([TrainModeLogoutButton]),
                                                  Tx([InTransactionToFunctionPanelButton]),
                                                  Tx([LogoutButton]),
                                                  Tx([ReprintButton], SmallNotesState),
                                                  Tx([SubscribeButton]),
                                                  Tx([ClaimGoodsButton]),
                                                  Tx([SearchPluButton]),
                                                  Tx([ScanOrderButton], ScanOrderState),
                                                  Tx([LineCancelButton], LineCancelState),
                                                  Tx([HoldCallBackButton]),
                                                  Tx([AttendanceButton], CheckOnWorkState),
                                                  Tx([HelpButton], IdleState),
                                                  Tx([ShiftButton], ShiftIdleState),
                                                  Tx([TranCancelButton]),
                                                  Tx([SaveButton]),
                                                  Tx([WuHanTongCommodityButton]),
                                                  Tx([PluOrderBackButton]),
            ],

            (NumberingState)                   : [Tx([NumberButton, ClearButton, BackspaceButton, EnterButton])], // NumberingState decides where to go

            (PluReadyState)                    : [Tx()], // triggerLess to anywhere decided by PluReadyState

            (TrainModelQuitOkCancelState)      : [Tx([EnterButton, ClearButton])],

            (MemberIdReadyState)               : [Tx()], // triggerLess to anywhere decided by MemberIdReadyState

            (MemberIdConfirmState)             : [Tx([EnterButton, ClearButton])], // always go back to IdleState

            (DaishouReadyState)                : [Tx([ClearButton, EnterButton, Scanner])],
            (DaishouProcessState)              : [Tx()],

            (FunctionReadyState)               : [Tx([FunctionButton, BackButton, TrainModeLogoutButton]),
                                                  Tx([ShiftButton], ShiftIdleState),
                                                  Tx([LogoutButton])],

            (CargoReadyState)                  : [Tx([ClearButton, EnterButton], IdleState),
                                                  Tx([Scanner])],
            (CargoProcessState)                : [Tx()],

            (StandByScanerCatgoReadyState)     : [Tx([ClearButton], IdleState),
                                                  Tx([Scanner])],

            (StandByScanerCargoProcessState)   : [Tx()],


            (NegativeInventoryState)           : [Tx([EnterButton, ClearButton], IdleState)],

            (PluDiscardNegativeInventoryState) : [Tx([EnterButton, ClearButton])],


            (LogOutOkCancelState)              : [Tx([EnterButton, ClearButton])],
            (LogOutMemOkCancelState)           : [Tx([EnterButton, ClearButton])],

            (LoginState)                       : [Tx([NumberButton, BackspaceButton, ClearButton], LoginState),
                                                  Tx([VerifyButton]),
                                                  Tx([EnterButton]),
                                                  Tx([Scanner])],

            (LockState)                        : [Tx([NumberButton, ClearButton, BackspaceButton, EnterButton])],

            (SummaryState)                     : [Tx([ClearButton, BackspaceButton]),
                                                  Tx([PaymentButton]),
                                                  Tx([CategoryPaymentButton]),
                                                  Tx([PaymentCategoryButton]),
                                                  Tx([NumberButton, Scanner, MemberShowButton]),// 在結帳界面按數字鍵
                                                  Tx([EnterButton]),
                                                  Tx([ReconnectionDeviceButton]),
                                                  Tx([OffLineConfirmButton]),
                                                  Tx([Scanner]),
                                                  Tx([BackIdleStateButton, CancelPayButton, ZhongBaiMemberButton, DiscountButton])],

            (LawsonUnionPayReadyState)         : [Tx([MSR]),
                                                  Tx([ClearButton, BackIdleStateButton, SwitchToWarningButton])],

            (LawsonZhongBaiCardState)          : [Tx([ClearButton, BackIdleStateButton, SwitchToWarningButton, MSR, NumberButton])],

            (LawsonZhongBaiCardDisposeState)   : [Tx()],

            (OffLineQrCodeConfirmCodeState)    : [Tx([Scanner, BackButton])],

            (FFStockLoginState)                : [Tx([DaishouButton, DaiSaleButton, ClearButton, BackspaceButton, PluButton, SaveButton, Scanner, FFStockButton]),
                                                  Tx([NumberButton], FFStockLoginState),
                                                  Tx([EnterButton]),
                                                  Tx([LineCancelButton], LineCancelState)],

            (ModifiesPluNumberState)           : [Tx([ClearButton, EnterButton])],

            (WarningState)                     : [Tx([EnterButton, ClearButton])],

            (HelpState)                        : [Tx()],

            (AmountInputState)                 : [Tx([NumberButton, BackButton, ClearButton, BackspaceButton, EnterButton])],

            (PasswordState)                    : [Tx([NumberButton, BackButton, ClearButton, BackspaceButton, EnterButton])],

            (SearchPluState)                   : [Tx([NumberButton, BackspaceButton, ClearButton], SearchPluState),
                                                  Tx([PluSearchGoBackButton]),
                                                  Tx([Scanner]),
                                                  Tx([PluButton]),
                                                  Tx([PluSearchConfirmButton]),
                                                  Tx([EnterButton])],

            (Paying1State)                     : [Tx()],
            (Paying3State)                     : [Tx([ClearButton, EnterButton])],

            (TransactionCancelState)           : [Tx([ClearButton, EnterButton])],

            (LawsonDrawerOpenState)            : [Tx([CashDrawer])],

            (SmallNotesState)                  : [Tx([EnterButton], ReprintState),
                                                  Tx([ClearButton, SwitchToWarningButton])],

            (ReprintState)                     : [Tx()],

            (OpenDrawerOkCancelState)          : [Tx([EnterButton, ClearButton])],
            (LawsonOpenCashierState)           : [Tx()],

            (ScanOrderState)                   : [Tx([ClearButton, EnterButton, SwitchToWarningButton]),
                                                  Tx([Scanner])],

            (LawsonRecyclingCashState)         : [Tx([NumberButton, BackButton, ClearButton, BackspaceButton, EnterButton])],
            (RecyclingOkCancelState)           : [Tx([EnterButton, ClearButton])],

            (ShiftIdleState)                   : [Tx()],
            (ShiftCashFormState)               : [Tx([EnterButton, ClearButton, NumberButton, BackButton, SaveButton, BackspaceButton])],
            (ShiftState)                       : [Tx()],

            (AmountConfirmState)               : [Tx([ClearButton, EnterButton])],

            (ZIdleState)                       : [Tx()],
            (ZState)                           : [Tx()],

            (PluDiscardReadyState)             : [Tx([PluButton, EnterButton, Scanner, PluDiscardBackButton, ClearButton, NumberButton, PluDiscardSaveButton, LineCancelButton])],
            (PluDiscardBackOkCancelState)      : [Tx([EnterButton, ClearButton])],
            (PluDiscardNumberingState)         : [Tx([NumberButton, BackspaceButton, EnterButton, ClearButton, ToPluDiscardModifyQtyButton])],
            (ModifiesPluDiscardNumberState)    : [Tx([NumberButton, BackspaceButton, EnterButton, ClearButton])],
            (PluDiscardSaveOkCancelState)      : [Tx([EnterButton, ClearButton])],

            (PluTakeStockStartOkCancelState)  : [Tx([EnterButton, ClearButton])],
            (PluTakeStockEndOkCancelState)    : [Tx([EnterButton, ClearButton])],

            (PreciousPluCheckState)           : [Tx([BackButton, SaveButton, NumberButton, BackButton, ClearButton, BackspaceButton, EnterButton, SwitchToWarningButton])],
            (PreciousPluOkCancelReadyState)   : [Tx([EnterButton, ClearButton])],
            (PreciousPluOkCancelState)        : [Tx()],


            (LineCancelState)                 : [Tx()],

            (LawsonShutdownOkCancelState)     : [Tx([ClearButton, EnterButton])],

            (MasterQueryState)                : [Tx([EnterButton])],

            (OtherPosDailySettlementState)    : [Tx([NumberButton, BackButton, ClearButton, BackspaceButton, EnterButton])],

            (ReservationsState)               : [Tx()],
            (CallbackTransactionState)        : [Tx()],

            (TrainingPatternJurisDictionState): [Tx([NumberButton, BackButton, ClearButton, BackspaceButton, EnterButton])],

            (ExpenseOutJurisdictionState)      : [Tx([NumberButton, BackButton, ClearButton, BackspaceButton, EnterButton, JudgeButton])],
            (ExpenseOutState)                  : [Tx([BackButton, SaveButton, NumberButton, BackButton, ClearButton, BackspaceButton, EnterButton, SwitchToWarningButton])],
            (TakeOutState)                     : [Tx([BackButton, SaveButton, Scanner, TakeOutQueryButton, NumberButton, ClearButton, EnterButton, TakeOutPrintButton, KeyboardButton, BackspaceButton])],
            (TakeOutSaveState)                 : [Tx([BackButton, TakeOutSaveButton, Scanner, TakeOutPrintButton, EnterButton, NumberButton, BackspaceButton, ClearButton, KeyboardButton, CheckOutButton])],
            (TakeOutReturnState)               : [Tx([BackButton, SaveButton, Scanner, TakeOutQueryButton, NumberButton, ClearButton, EnterButton, KeyboardButton, BackspaceButton])],
            (TakeOutReturnSaveState)           : [Tx([BackButton, TakeOutSaveButton, Scanner, TakeOutPrintButton, EnterButton, NumberButton, BackspaceButton, ClearButton, KeyboardButton])],
            (TakeOutEnterState)                : [Tx([NumberButton, BackButton, ClearButton, BackspaceButton, EnterButton])],
            (ExpenseOutSaveOkCancelState)      : [Tx([ClearButton, EnterButton])],

            (ExpenseInputState)                : [Tx([BackButton, SaveButton, NumberButton, BackButton, ClearButton, BackspaceButton, EnterButton, SwitchToWarningButton])],
            (ExpenseInSaveOkCancelState)       : [Tx([ClearButton, EnterButton])],

            (PayCancelState)                   : [Tx()],

            (ReturnNumberState)                : [Tx([NumberButton, ClearButton, EnterButton, BackButton, BackspaceButton])],
            (ReturnNumberOkCancelState)        : [Tx([EnterButton, ClearButton])],


            (ReturnShowState)                  : [Tx([NumberButton, ClearButton, EnterButton, BackspaceButton, BackIdleStateButton, ReversalButton, Scanner])],
            (ReturnScannerState)               : [Tx([NumberButton, ClearButton, EnterButton, BackspaceButton, LineCancelButton, PluButton, Scanner, BackButton])],
            (ReturnScannerQuitOkCancelState)   : [Tx([EnterButton, ClearButton])],
            (ReturnSummaryState)               : [Tx([ClearButton, EnterButton, BackIdleStateButton])],

            (YinLianRefundFailOkCancelState)   : [Tx([EnterButton, ClearButton])],

            (MasterDownloadOkCancelReadyState) : [Tx([EnterButton, ClearButton])],
            (MasterDownloadOkCancelState)      : [Tx()],
            (MasterDownloadState)              : [Tx([ClearButton, EnterButton])],


            (VersionDownloadIdleState)         : [Tx([ClearButton, EnterButton, SwitchToWarningButton])],
            (LawsonVersionDownloadState)       : [Tx([ClearButton, EnterButton])],

            (RestartPosAndStartReleaseState)   : [Tx()],

            (OnlyRestartPosState)              : [Tx()],

            (AdjustVolumeState)                : [Tx([ClearButton, EnterButton], FunctionReadyState)],


            (InitialQuickScanOkCancelState)    : [Tx([ClearButton, EnterButton])],
            (InitialQuickScanState)            : [Tx([ClearButton, NextTipButton])],

            (LogQueryState)                    : [Tx([PluSearchGoBackButton, NumberButton, ClearButton, EnterButton, BackButton, SearchDetailButton, BackspaceButton])],
            (LogDetailState)                   : [Tx([PrintButton, PreviousButton, NextButton, BackButton])],

            (AdminSetIdleState)                : [Tx([EnterButton]),
                                                  Tx([LogoutButton], LogOutOkCancelState),
                                                  Tx([PosNoSetButton], PosNoSetState),
                                                  Tx([DatabaseInitButton], LawsonDatabaseInitState),
                                                  Tx([ParamSetButton], ParamSetState),
                                                  Tx([ScIpButton], ScIpSetState),
                                                  Tx([MasterUpdateButton], AdminMasterDownloadIdleState),
                                                  Tx([RecallButton], RecallOkCancelState),
                                                  Tx([RecalcZButton], RecalcZState),
                                                  Tx([ZUploadButton], ZUploadState)],

            (ScIpSetState)                     : [Tx([NumberButton, BackButton, ClearButton, BackspaceButton, EnterButton])],

            (PosNoSetState)                    : [Tx([NumberButton, BackButton, ClearButton, BackspaceButton, EnterButton])],

            (RemotePrintNoteState)             : [Tx([NumberButton, BackButton, ClearButton, BackspaceButton, EnterButton])],

            (ZhongBaiCardPwdState)             : [Tx([NumberButton, BackButton, ClearButton, BackspaceButton, EnterButton])],

            (ParamSetState)                    : [Tx([NumberButton, ClearButton, BackspaceButton], ParamSetState),
                                                  Tx([BackButton]),
                                                  Tx([EnterButton])],

            (ConfirmPreciousPluAmountState)    : [Tx([EnterButton, ClearButton])],

            (ClearDisposeState)                : [Tx([ClearButton])],

            (CardQueryState)                   : [Tx([ClearButton, BackButton, MSR])],
            (CardQueryZhongbaiState)           : [Tx([ClearButton, BackButton, MSR, EnterButton])],

            (ScannerCodeDisposeState)          : [Tx()],

            (LawsonDatabaseInitState)          : [Tx([ClearButton, EnterButton])],

            (LawsonExportSaleIdleState)        : [Tx([ClearButton, EnterButton])],
            (LawsonExportSaleState)            : [Tx()],

            (LawsonImportMasterIdleState)      : [Tx([ClearButton, EnterButton])],
            (LawsonImportMasterState)          : [Tx()],

            (OtherPosSalesReturnState)         : [Tx([NumberButton, BackButton, ClearButton, BackspaceButton, EnterButton])],

            (CheckOnWorkState)                 : [Tx([SwitchToWarningButton, NumberButton, Scanner, ClearButton, BackspaceButton, EnterButton])],

            (MMConfirmState)                   : [Tx([ClearButton, EnterButton])],

            (OffLinePeddlerSaveOkCancelState)  : [Tx([ClearButton, EnterButton])],
            (OffLinePeddlerGoBackOkCancelState): [Tx([ClearButton, EnterButton])],

            (OffLineTakeWayInputState)         : [Tx([NumberButton, ClearButton, BackButton, BackspaceButton, Scanner, EnterButton])],
            (OffLineTakeWayGoBackOkCancelState): [Tx([ClearButton, EnterButton])],

            (NewStorePrintSmallNote)           : [Tx([ClearButton, EnterButton])],

            (MarsRabbitState)                  : [Tx([ClearButton, EnterButton])],
            (MarsRabbitSaveState)              : [Tx([EnterButton])],
            (DaiJinQuanState)                  : [Tx([Scanner, ClearButton])],
            (ZhongBaiMemberState)              : [Tx([Scanner, ClearButton, BackIdleStateButton, NumberButton, EnterButton,
                                                     BackspaceButton, MSR])],
            (LawsonYinLianSignInState)         : [Tx()],
            (LawsonYinLianSettleState)         : [Tx()],
            (IsToYinLianSignInState)           : [Tx([EnterButton, ClearButton])],
            (IsToYinLianSettleState)           : [Tx([EnterButton, ClearButton])],
            (ConfirmationCodeState)            : [Tx([Scanner, BackButton])],
            (FFStockBackState)                 : [Tx([ClearButton, EnterButton])],
            (ZUploadState)                     : [Tx([BackButton, BackspaceButton, NumberButton, ClearButton, EnterButton])],

            (AdminMasterDownloadIdleState)     : [Tx([SwitchToWarningButton, ClearButton, EnterButton])],
            (AdminMasterDownloadState)         : [Tx([ClearButton, EnterButton])],

            (RecallOkCancelState)              : [Tx([ClearButton, EnterButton])],
            (RecallState)                      : [Tx([ClearButton])],

            (RecalcZState)                     : [Tx([ClearButton, EnterButton])],

            (LawsonPrinterEmptyOperationState) : [Tx([ClearButton, PrinterReadyButton])],

            (TestPopupState)                   : [Tx([EnterButton])],

            (WuHanTongTopUpState)              : [Tx([NumberButton, ClearButton, BackspaceButton, EnterButton, WuHanTongTopUpCancelButton])],

            (WuHanTongPayState)                : [Tx([ClearButton, EnterButton])],

            (WuHanTongBalanceQueryState)       : [Tx([WuHanTongBalanceQueryBackButton, WuHanTongBalanceQueryButton, ClearButton])],
            (FFStockSaveState)                 : [Tx([ClearButton, EnterButton])],
            (SubscribeClaimState)              : [Tx([NumberButton, ClearButton, BackButton, BackspaceButton, EnterButton, Scanner, SaveButton, KeyboardButton])],
            (BookInputState)                   : [Tx([NumberButton, ClearButton, BackButton, BackspaceButton, EnterButton])],
            (PluOrderBackOkCancelState)        : [Tx([ClearButton, EnterButton])],
            (SubscribeClaimSaveState)          : [Tx([BackButton, EnterButton, Scanner, NumberButton, SaveButton, BackspaceButton, ClearButton, KeyboardButton])],
            (SotTransQueryState)               : [Tx([PluSearchGoBackButton, NumberButton, ClearButton, EnterButton, BackButton, SearchDetailButton, BackspaceButton])],
            (MemberConfirmState)               : [Tx([ClearButton, Scanner, NumberButton, EnterButton])],
            (WeiXinScanPayState)               : [Tx([ConfirmButton, ClearButton])],
            ]
            */
}
