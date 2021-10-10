package com.example.knowledge_android.flexbox_layout;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayout;

public class FlexBoxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flex_box_layout_test);

        //或者代码中这样使用：
        FlexboxLayout flexboxLayout = (FlexboxLayout) findViewById(R.id.flexboxLayout);
        flexboxLayout.setFlexDirection(FlexDirection.COLUMN);

        View view = flexboxLayout.getChildAt(0);
        FlexboxLayout.LayoutParams lp = (FlexboxLayout.LayoutParams) view.getLayoutParams();
        lp.setOrder(-1);
        lp.setFlexGrow(2f);
        view.setLayoutParams(lp);
    }
}

//    支持的属性 flexDirection
//
//    flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal。
//
//    有四个值可以选择：
//    row（默认值）：主轴为水平方向，起点在左端。
//    row-reverse：主轴为水平方向，起点在右端。
//    column：主轴为垂直方向，起点在上沿。
//    column-reverse：主轴为垂直方向，起点在下沿。


//    flexWrap
//
//    默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列。有三个值：
//
//    nowrap ：不换行
//    wrap：按正常方向换行
//    wrap-reverse：按反方向换行


//    justifyContent
//
//    justifyContent属性定义了项目在主轴上的对齐方式。
//
//    flex-start（默认值）：左对齐
//    flex-end：右对齐
//    center： 居中
//    space-between：两端对齐，项目之间的间隔都相等。
//    space-around：每个项目两侧的间隔相等。所以，项目之间的间隔比项目与边框的间隔大一倍。


//    alignItems
//
//    alignItems属性定义项目在副轴轴上如何对齐。
//
//    flex-start：交叉轴的起点对齐。
//    flex-end：交叉轴的终点对齐。
//    center：交叉轴的中点对齐。
//    baseline: 项目的第一行文字的基线对齐。
//    stretch（默认值）：如果项目未设置高度或设为auto，将占满整个容器的高度。


//    alignContent
//
//    alignContent属性定义了多根轴线的对齐方式。如果项目只有一根轴线，该属性不起作用。
//
//    flex-start：与交叉轴的起点对齐。
//    flex-end：与交叉轴的终点对齐。
//    center：与交叉轴的中点对齐。
//    space-between：与交叉轴两端对齐，轴线之间的间隔平均分布。
//    space-around：每根轴线两侧的间隔都相等。所以，轴线之间的间隔比轴线与边框的间隔大一倍。
//    stretch（默认值）：轴线占满整个交叉轴。


//子元素属性。请去网上搜索。。。这里不在列举。
