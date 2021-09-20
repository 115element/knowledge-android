package com.example.knowledge_android.apkdownload;

/**
 * User: eric
 * Date: 1/7/13
 * Time: 3:36 PM
 */
public interface TransferProgressListener {
    void showDataTransferDialog();
    void updateDataTransferDialog(Integer... values);
    void onFinishDataTransfer(int result);
}
