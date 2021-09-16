package com.example.knowledge_android.webtablayout_viewpager.webtablayout;

import android.graphics.drawable.Drawable;

/**
 * Created to :Tab设置上下左右设置小icon。
 */
public interface IWeTabDrawable {

    /**
     * 通过gravity返回符合该位置处的Drawable对象。
     *
     * @param gravity
     * @return
     */
    Drawable getDrawableByGravity(int gravity) ;

    /**
     * 获取目标Tab的名字。
     *
     * @return
     */
    String getTargetTabName();

}