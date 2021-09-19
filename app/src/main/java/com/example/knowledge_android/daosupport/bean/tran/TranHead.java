package com.example.knowledge_android.daosupport.bean.tran;

import java.io.Serializable;

public class TranHead implements Serializable {

    /** 唯一ID */
    Integer id;

    String custId;

    /** 店号 */
    String storeId;

    /** 收款机台号 */
    Integer posNo;

    /** 交易序号 */
    Integer transactionNumber;

    /** 交易明细类别
     * 0一般交易
     * *作废交易,表示本笔为被作废交易
     * 1交易未完成"
     */
    String dealType1;

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

    public String getDealType1() {
        return dealType1;
    }

    public void setDealType1(String dealType1) {
        this.dealType1 = dealType1;
    }

    @Override
    public String toString() {
        return "TranHead{" +
                "id=" + id +
                ", custId='" + custId + '\'' +
                ", storeId='" + storeId + '\'' +
                ", posNo=" + posNo +
                ", transactionNumber=" + transactionNumber +
                ", dealType1='" + dealType1 + '\'' +
                '}';
    }
}
