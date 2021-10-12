package com.example.knowledge_android.plu_selected;

public class PluBean {
    boolean isSelected;
    String name; //商品名

    public PluBean(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
