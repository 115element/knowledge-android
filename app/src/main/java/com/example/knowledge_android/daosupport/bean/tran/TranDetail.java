package com.example.knowledge_android.daosupport.bean.tran;

import java.io.Serializable;
import java.util.Date;

public class TranDetail implements Serializable {

    /** 唯一ID */
    Integer id;

    String custId;

    /** 店号 */
    String storeId;

    /** 收款机台号 */
    Integer posNo;

    /** 交易序号 */
    Integer transactionNumber;

    /** 明细序号 */
    Integer itemSeq;

    /** 交易时间 */
    Date systemDate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getPosNo() {
        return posNo;
    }

    public void setPosNo(Integer posNo) {
        this.posNo = posNo;
    }

    public Integer getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(Integer transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public Integer getItemSeq() {
        return itemSeq;
    }

    public void setItemSeq(Integer itemSeq) {
        this.itemSeq = itemSeq;
    }

    public Date getSystemDate() {
        return systemDate;
    }

    public void setSystemDate(Date systemDate) {
        this.systemDate = systemDate;
    }
}
