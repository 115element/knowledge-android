package com.example.knowledge_android.hyicoupon;

import java.io.Serializable;

public class HyiCoupon implements Serializable {

    private int id;
    private boolean recommend = false;
    private boolean selected = false;
    private String title = "贝瑞咖啡30元代金券(满128元可用)";
    private String surplus = "8张";
    private String startEndTime = "有效期：2021-08-19 00:00:00  2021-08-19 10:00:00";
    private String amount = "30元";
    private String desc = "满99可用";
    private int couponUsed = 1; //优惠券使用数量
    private int couponTotalAmount = 8; //优惠券总数量

    public int getCouponUsed() {
        return couponUsed;
    }

    public void setCouponUsed(int couponUsed) {
        this.couponUsed = couponUsed;
    }

    public int getCouponTotalAmount() {
        return couponTotalAmount;
    }

    public void setCouponTotalAmount(int couponTotalAmount) {
        this.couponTotalAmount = couponTotalAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSurplus() {
        return surplus;
    }

    public void setSurplus(String surplus) {
        this.surplus = surplus;
    }

    public String getStartEndTime() {
        return startEndTime;
    }

    public void setStartEndTime(String startEndTime) {
        this.startEndTime = startEndTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "HyiCoupon{" +
                "id=" + id +
                ", selected=" + selected +
                ", recommend=" + recommend +
                ", title='" + title + '\'' +
                ", surplus='" + surplus + '\'' +
                ", startEndTime='" + startEndTime + '\'' +
                ", amount='" + amount + '\'' +
                ", desc='" + desc + '\'' +
                ", couponUsed=" + couponUsed +
                ", couponTotalAmount=" + couponTotalAmount +
                '}';
    }
}
