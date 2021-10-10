package com.example.knowledge_android.recyclerview4.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 这个MapBean里面关键点其实在于以下几个部分：
 * 1）如何来区分是父标题还是子标题，我们加入了一个viewType，如果是TYPE_PARENT，则是父标题，TYPE_CHILD则是子标题；
 * 2）我们想让父标题包含多个子标题，那么在父标题中我们定义一个List< <<mapBean> >>childList，目的是用列表存放多个子标题实例。
 * 3)当父标题需要折叠或者展开的时候需要区分其状态，因此我们定义了一个boolean expand，用来判断父标题是什么状态，如果是展开则进行折叠，如果折叠则进行展开。
 * 4)当我们需要传输列表中的数据的时候，需要让mapBean继承Serializable接口，这样在后面intent传值的时候才可以使用。
 */
public class MapBeanParent extends BaseBean implements Serializable {

    public static final int TYPE_PARENT = 0;//父类标题，即一级标题
    public static final int TYPE_CHILD = 1;//子类，即二级标题

    private int viewType;   //判断标题类型是TYPE_PARENT还是TYPE_CHILD来对应不同的布局
    private boolean expand = false; //是否已经展开了子项

    private String caption; //标题的内容

    //折叠展开列表
    private List<MapBeanChild> childList;//定义一个装载多个子类的列表

    //表示获得的标题内容以及标题类型（父标题还是子标题）
    public MapBeanParent(String caption, int viewType) {
        this.caption = caption;
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }//获得对应的标题类型

    public String getCaption() { //获得标题内容
        return caption;
    }


    public List<MapBeanChild> getChildList() {
        return childList;
    }

    public void setChildList(List<MapBeanChild> childList) {
        this.childList = childList;
    }

    public boolean isExpand() {//判断现在的状态是扩展还是折叠
        return expand;
    }

    public void setExpand(boolean expand) {//建立父标题的状态
        this.expand = expand;
    }
}
