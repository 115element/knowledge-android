package com.example.knowledge_android.daosupport.bean.master;

import java.util.Date;

public class CashierDac {

    String custId;

    String storeId;
    /**
     * 收银员编号
     */
    String cashierNo;
    /**
     * 主档版本
     */
    Date version;

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

    public String getCashierNo() {
        return cashierNo;
    }

    public void setCashierNo(String cashierNo) {
        this.cashierNo = cashierNo;
    }


    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "CashierDac{" +
                "custId='" + custId + '\'' +
                ", storeId='" + storeId + '\'' +
                ", cashierNo='" + cashierNo + '\'' +
                ", version=" + version +
                '}';
    }
}
