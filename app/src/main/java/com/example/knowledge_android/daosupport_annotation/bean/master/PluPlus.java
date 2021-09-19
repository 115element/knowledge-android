package com.example.knowledge_android.daosupport_annotation.bean.master;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;
import java.util.Date;

@DatabaseTable(tableName = "plu_plus")
public class PluPlus {

    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField(width = 20,uniqueCombo = false)
    String custId;

    @DatabaseField(uniqueCombo = false,canBeNull = false,width = 8)
    String storeId;

    @DatabaseField(canBeNull = false,columnDefinition = "DECIMAL(8,2) NOT NULL")
    BigDecimal pluPrice = BigDecimal.ZERO;

    @DatabaseField(canBeNull = false,format = "yyyy-MM-dd",dataType = DataType.DATE_STRING)
    Date beginDate;

    @DatabaseField(width = 11,columnName = "si_group")
    Integer siGroup = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public BigDecimal getPluPrice() {
        return pluPrice;
    }

    public void setPluPrice(BigDecimal pluPrice) {
        this.pluPrice = pluPrice;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Integer getSiGroup() {
        return siGroup;
    }

    public void setSiGroup(Integer siGroup) {
        this.siGroup = siGroup;
    }

    @Override
    public String toString() {
        return "PluAnnotation{" +
                "id=" + id +
                ", custId='" + custId + '\'' +
                ", storeId='" + storeId + '\'' +
                ", pluPrice=" + pluPrice +
                ", beginDate=" + beginDate +
                ", siGroup=" + siGroup +
                '}';
    }
}
