package com.example.knowledge_android.hyicoupon;

import java.io.Serializable;

public class HyiCoupon implements Serializable {
    private boolean selected = false;
    private boolean recommend = false;
    private String title = "贝瑞咖啡30元代金券(满128元可用)";
    private String surplus = "8张";
    private String startEndTime = "有效期：2021-08-19 00:00:00  2021-08-19 10:00:00";
    private String amount = "30元";
    private String desc = "满99可用";

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
}
