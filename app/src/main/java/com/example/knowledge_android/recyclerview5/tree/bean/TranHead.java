package com.example.knowledge_android.recyclerview5.tree.bean;


import com.example.knowledge_android.recyclerview5.tree.base.BaseItemData;

import java.util.List;


public class TranHead extends BaseItemData {

    private int id;
    private String foodCode; //取餐码
    private String channel;  //渠道
    private String date;     //交易时间
    private String phone;    //手机号码
    private String amount;   //金额
    private String status;   //退货状态

    private List<TranDetail> tranDetails;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodCode() {
        return foodCode;
    }

    public void setFoodCode(String foodCode) {
        this.foodCode = foodCode;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TranDetail> getTranDetails() {
        return tranDetails;
    }

    public void setTranDetails(List<TranDetail> tranDetails) {
        this.tranDetails = tranDetails;
    }

    @Override
    public String toString() {
        return "TranHead{" +
                "id=" + id +
                ", foodCode='" + foodCode + '\'' +
                ", channel='" + channel + '\'' +
                ", date='" + date + '\'' +
                ", phone='" + phone + '\'' +
                ", amount='" + amount + '\'' +
                ", status='" + status + '\'' +
                ", tranDetails=" + tranDetails +
                '}';
    }
}
