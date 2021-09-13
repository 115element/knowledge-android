package com.example.knowledge_android.treerecyclerview.tree.bean;


import com.example.knowledge_android.treerecyclerview.base.BaseItemData;

public class TranDetail extends BaseItemData {

    private int id;
    private String pluId;    //商品序号
    private String pluName;  //商品名
    private String fixName1; //固定字符：规格
    private String choice;   //规格值
    private String option;   //属性值
    private String fixName2; //固定字符：数量
    private String quantity; //数量值
    private String fixName3; //固定字符：金额
    private String amount;   //金额值

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPluId() {
        return pluId;
    }

    public void setPluId(String pluId) {
        this.pluId = pluId;
    }

    public String getPluName() {
        return pluName;
    }

    public void setPluName(String pluName) {
        this.pluName = pluName;
    }

    public String getFixName1() {
        return fixName1;
    }

    public void setFixName1(String fixName1) {
        this.fixName1 = fixName1;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getFixName2() {
        return fixName2;
    }

    public void setFixName2(String fixName2) {
        this.fixName2 = fixName2;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getFixName3() {
        return fixName3;
    }

    public void setFixName3(String fixName3) {
        this.fixName3 = fixName3;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


}
