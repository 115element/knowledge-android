package com.example.knowledge_android.daosupport.bean.master;

import java.math.BigDecimal;
import java.util.Date;

public class PluDac {

    String custId;

    String storeId;

    /**
     * 系统货号
     */
    String pluNo;

    /**
     * 商品名称
     */
    String pluName;

    /**
     * 单价
     */
    BigDecimal pluPrice = BigDecimal.ZERO;

    /**
     * 特卖促销起始日期
     */
    Date beginDate;


    /**
     * SI群组
     */
    Integer siGroup = 0;

    int promotionPrice; //是 int  集享优惠价（spu\sku）


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

    public String getPluNo() {
        return pluNo;
    }

    public void setPluNo(String pluNo) {
        this.pluNo = pluNo;
    }

    public String getPluName() {
        return pluName;
    }

    public void setPluName(String pluName) {
        this.pluName = pluName;
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

    public int getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(int promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    @Override
    public String toString() {
        return "PluDac{" +
                "custId='" + custId + '\'' +
                ", storeId='" + storeId + '\'' +
                ", pluNo='" + pluNo + '\'' +
                ", pluName='" + pluName + '\'' +
                ", pluPrice=" + pluPrice +
                ", beginDate=" + beginDate +
                ", siGroup=" + siGroup +
                ", promotionPrice=" + promotionPrice +
                '}';
    }
}
