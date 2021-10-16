package com.example.knowledge_android.spannable;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

public class SpannableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spannable_layout);
        TextView textView = findViewById(R.id.demo1);
        EditText editText = findViewById(R.id.demo2);

////////////////SpannableString和SpannableStringBuilder用法区别///////////////////////////////////////////////////////////////////////////////////
        //使用SpannableString，必须一次传入，构造完成
        SpannableString word = new SpannableString("欢迎光临Harvic的博客");

        //使用SpannableStringBuilder,可以使用append()再添加
        SpannableStringBuilder multiWord = new SpannableStringBuilder();
        multiWord.append("欢迎光临");
        multiWord.append("Harvic的");
        multiWord.append("博客");


        //////////////+++++++++++++++++Use HTML String+++++////////////////////////////
        String htmlString = "ssss<sup><small><font color='red'>+3</font></small></sup>";
        textView.setText(Html.fromHtml(htmlString));

        //////////////++++++++++++++++Use SuperscriptionSpan+++++++++++++++++++////////
        String baseString = "大";
        String extendString = "+3元";
        SpannableStringBuilder cs = new SpannableStringBuilder(baseString + extendString);
        int start = baseString.length();
        cs.setSpan(new SuperscriptSpan(), start, start + extendString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cs.setSpan(new RelativeSizeSpan(0.75f), start, start + extendString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(cs);


/////////////////////////字体颜色设置（ForegroundColorSpan）//////////////////////////////////////////////////////////////////////////

        //改变字体颜色
        //先构造SpannableString
        SpannableString spanString = new SpannableString("欢迎光临Harvic的博客");
        //再构造一个改变字体颜色的Span
        ForegroundColorSpan span = new ForegroundColorSpan(Color.BLUE);
        //将这个Span应用于指定范围的字体
        spanString.setSpan(span, 1, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置给EditText显示出来
        editText.setText(spanString);

//////////////////////////字体背景颜色（BackgroundColorSpan）/////////////////////////////////////////////////////////////////////////

        SpannableString spanString1 = new SpannableString("欢迎光临Harvic的博客");
        BackgroundColorSpan span1 = new BackgroundColorSpan(Color.YELLOW);
        spanString1.setSpan(span1, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setText(spanString1);

/////////////////////////字体大小（AbsoluteSizeSpan）//////////////////////////////////////////////////////////////////////////

        SpannableString spanString2 = new SpannableString("欢迎光临Harvic的博客");
        AbsoluteSizeSpan span2 = new AbsoluteSizeSpan(16);
        spanString2.setSpan(span2, 2, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        editText.setText(spanString2);

////////////////////////粗体、斜体（StyleSpan）///////////////////////////////////////////////////////////////////////////

        SpannableString spanString3 = new SpannableString("欢迎光临Harvic的博客");
        StyleSpan span3 = new StyleSpan(Typeface.BOLD_ITALIC);
        spanString3.setSpan(span3, 1, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setText(spanString3);

//////////////////////删除线（StrikethroughSpan）/////////////////////////////////////////////////////////////////////////////

        SpannableString spanString4 = new SpannableString("欢迎光临Harvic的博客");
        StrikethroughSpan span4 = new StrikethroughSpan();
        spanString4.setSpan(span4, 2, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setText(spanString4);

//////////////////////下划线（UnderlineSpan）/////////////////////////////////////////////////////////////////////////////

        SpannableString spanString5 = new SpannableString("欢迎光临Harvic的博客");
        UnderlineSpan span5 = new UnderlineSpan();
        spanString5.setSpan(span5, 1, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setText(spanString5);

///////////////////图片置换（ImageSpan）////////////////////////////////////////////////////////////////////////////////

        SpannableString spanString6 = new SpannableString("欢迎光临Harvic的博客");
        Drawable d = getResources().getDrawable(R.drawable.ic_launcher);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span6 = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        spanString6.setSpan(span6, 2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setText(spanString6);

///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////

    }


    /*
    ①SpannableString、SpannableStringBuilder与String的关系
    首先SpannableString、SpannableStringBuilder基本上与String差不多，
    也是用来存储字符串，但它们俩的特殊就在于有一个SetSpan（）函数，能给这些存储的String添加各种格式或者称样式（Span），
    将原来的String以不同的样式显示出来，比如在原来String上加下划线、加背景色、改变字体颜色、用图片把指定的文字给替换掉，等等。
    所以，总而言之，SpannableString、SpannableStringBuilder与String一样，
    首先也是传字符串，但SpannableString、SpannableStringBuilder可以对这些字符串添加额外的样式信息，但String则不行。

    ②注意：如果这些额外信息能被所用的方式支持，比如将SpannableString传给TextView；也有对这些额外信息不支持的，
    比如前一章讲到的Canvas绘制文字，对于不支持的情况，SpannableString和SpannableStringBuilder就是退化为String类型，
    直接显示原来的String字符串，而不会再显示这些附加的额外信息。

    ③SpannableString与SpannableStringBuilder区别
    它们的区别在于 SpannableString像一个String一样，构造对象的时候传入一个String，之后再无法更改String的内容，
    也无法拼接多个 SpannableString；而SpannableStringBuilder则更像是StringBuilder，它可以通过其append()方法来拼接多个String


    因为Spannable等最终都实现了CharSequence接口，
    所以可以直接把SpannableString和SpannableStringBuilder通过TextView.setText()设置给TextView。


函数意义：给SpannableString或SpannableStringBuilder特定范围的字符串设定Span样式，可以设置多个（比如同时加上下划线和删除线等），Falg参数标识了当在所标记范围前和标记范围后紧贴着插入新字符时的动作，即是否对新插入的字符应用同样的样式。（这个后面会具体举例说明）

参数说明：

object what ：对应的各种Span，后面会提到；
int start：开始应用指定Span的位置，索引从0开始
int end：结束应用指定Span的位置，特效并不包括这个位置。比如如果这里数为3（即第4个字符），第4个字符不会有任何特效。从下面的例子也可以看出来。
int flags：取值有如下四个
Spannable.SPAN_EXCLUSIVE_EXCLUSIVE：前后都不包括，即在指定范围的前面和后面插入新字符都不会应用新样式
Spannable.SPAN_EXCLUSIVE_INCLUSIVE ：前面不包括，后面包括。即仅在范围字符的后面插入新字符时会应用新样式
Spannable.SPAN_INCLUSIVE_EXCLUSIVE ：前面包括，后面不包括。
Spannable.SPAN_INCLUSIVE_INCLUSIVE ：前后都包括。
————————————————
     */
}
