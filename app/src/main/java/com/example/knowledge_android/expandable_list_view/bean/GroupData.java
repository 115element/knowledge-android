package com.example.knowledge_android.expandable_list_view.bean;

import java.io.Serializable;

public class GroupData implements Serializable {
    private String name;
    private String num;

    public GroupData(String name) {
        this.name = name;
    }

    public GroupData(String name, String num) {
        this.name = name;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
