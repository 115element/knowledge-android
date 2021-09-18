package com.example.knowledge_android.linkagelistview.bean;


import com.example.linkage_recyclerview.bean.BaseGroupedItem;

public class GreatPhoneGroupedItem extends BaseGroupedItem<GreatPhoneGroupedItem.ItemInfo> {

    public GreatPhoneGroupedItem(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public GreatPhoneGroupedItem(ItemInfo item) {
        super(item);
    }

    public static class ItemInfo extends BaseGroupedItem.ItemInfo {
        private String content;
        private String imgUrl;
        private String cost;

        public ItemInfo(String title, String group, String content) {
            super(title, group);
            this.content = content;
        }

        public ItemInfo(String title, String group, String content, String imgUrl) {
            this(title, group, content);
            this.imgUrl = imgUrl;
        }

        public ItemInfo(String title, String group, String content, String imgUrl, String cost) {
            this(title, group, content, imgUrl);
            this.cost = cost;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }
    }
}
