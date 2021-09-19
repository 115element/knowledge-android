package com.example.knowledge_android.daosupport_annotation.bean.tran;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "tranhead_plus")
public class TranHeadPlus implements Serializable {

    /** 唯一ID */
    @DatabaseField(generatedId = true,canBeNull = false)
    Integer id;

    @DatabaseField(canBeNull = false,uniqueCombo = false,width = 20)
    String custId;

    @DatabaseField(canBeNull = false,uniqueCombo = false,width = 8)
    String storeId;

    @DatabaseField(canBeNull = false,uniqueCombo = true)
    Integer posNo;

    @DatabaseField(canBeNull = false,uniqueCombo = false)
    Integer transactionNumber;

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

    @Override
    public String toString() {
        return "TranHeadPlus{" +
                "id=" + id +
                ", custId='" + custId + '\'' +
                ", storeId='" + storeId + '\'' +
                ", posNo=" + posNo +
                ", transactionNumber=" + transactionNumber +
                '}';
    }
}
