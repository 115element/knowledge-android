package com.example.knowledge_android.labels_view_improve;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.donkingliang.labels.improve.LabelsView;
import com.donkingliang.labels.improve.TextViewImprove;
import com.example.knowledge_android.R;
import com.example.knowledge_android.widget.view.MarqueeText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import q.rorbin.badgeview.QBadgeView;

public class LabelsImproveActivity extends AppCompatActivity {

    List<TasteImprove> stringList1;
    List<TasteImprove> stringList2;
    LabelsView labelsView1;
    LabelsView labelsView2;
    MarqueeText marqueeText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.labels_view_improve);

        marqueeText = findViewById(R.id.marqueeText2);
        marqueeText.setText("傻蛋");
        marqueeText.setSpeed(10);
        marqueeText.setTextSize(100);

        labelsView1 = findViewById(R.id.labelsview1);
        labelsView1.setMaxLines(20);
        labelsView1.setMaxColumns(3);
        //labelsView1.setWordMargin(10);
        //labelsView1.setLineMargin(10);
        labelsView1.setSelectType(LabelsView.SelectType.SINGLE_IRREVOCABLY);
        labelsView1.setMaxSelect(2);
        labelsView1.setMinSelect(1);
        //labelsView1.setLabelTextSize(40f);
        labelsView1.setLabelBackgroundResource(R.drawable.labelsview_label_bg);
        //labelsView1.setLabelBackgroundColor(Color.WHITE);
        labelsView1.setLabelTextColor(Color.rgb(153, 153, 153));
        labelsView1.setLabelTextPadding(15, 10, 15, 10);

        stringList1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TasteImprove taste = new TasteImprove();
            taste.setAge(i + "");
            taste.setName(i + "");
            taste.setPlu(i + "");
            taste.setNum(i + "");
            taste.setSelected(false);
            stringList1.add(taste);
        }

        //LabelsView创建时调用
        labelsView1.setLabels(stringList1, new LabelsView.LabelTextProvider<TasteImprove>() {
            @Override
            public CharSequence getLabelText(TextViewImprove label, int position, TasteImprove data) {
                // label就是标签项，在这里可以对标签项单独设置一些属性，比如文本样式等。
                labelsView1.setLabelSelect(label, false);


                if (data.isSelected) {
                    label.setTextColor(Color.BLUE);
                }
                //根据data和position返回label需要显示的数据。
                if (data.getAge().equals("1")) {
                    return data.getName();
                }
                return data.getNum() + ":";
                //return htmlString;
            }
        });

        //LabelsView点击label时调用
        labelsView1.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() {
            @Override
            public void onLabelSelectChange(TextViewImprove label, Object data, boolean isSelect, int position) {
                TasteImprove taste = (TasteImprove) data;
                if (isSelect) {
                    taste.setSelected(true);
                    label.setTextColor(Color.YELLOW);
                } else {
                    taste.setSelected(false);
                    label.setTextColor(Color.GRAY);
                }

                //点击左边，改变右边数据
                change2();
            }
        });

        int childCount = labelsView1.getChildCount();
        for (int i = 0; i < childCount; i++) {

            //+++++++++++++++++Use HTML String+++++
            TextViewImprove childAt = (TextViewImprove) labelsView1.getChildAt(i);
//            String htmlString = "ssss<sup><small><font color='red'>+3</font></small></sup>";
//            childAt.setText(Html.fromHtml(htmlString));

            //++++++++++++++++Use SuperscriptionSpan+++++++++++++++++++
//            String baseString = "大";
//            String extendString = "+3元";
//            SpannableStringBuilder cs = new SpannableStringBuilder(baseString + extendString);
//            int start = baseString.length();
//            cs.setSpan(new SuperscriptSpan(), start, start + extendString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//            cs.setSpan(new RelativeSizeSpan(0.75f),start,start+extendString.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            childAt.setText(cs);

////////////////////////以下方法经过测试，不能使用//////////////////////////////////////////////

//            BadgeView badgeView = new BadgeView(getBaseContext());
//            badgeView.setTargetView(childAt);
//            badgeView.setText("+3元");


//            QBadgeView qBadgeView = new QBadgeView(getBaseContext());
//            qBadgeView.bindTarget(childAt);
//            qBadgeView.setBadgeText("+¥3");
//            TextViewImprove targetView = (TextViewImprove) qBadgeView.getTargetView();
        }


        labelsView2 = findViewById(R.id.labelsview2);
        labelsView2.setMaxLines(20);
        labelsView2.setMaxColumns(10);
        labelsView2.setWordMargin(10);
        labelsView2.setLineMargin(10);
        labelsView2.setSelectType(LabelsView.SelectType.SINGLE_IRREVOCABLY);
        labelsView2.setMaxSelect(2);
        labelsView2.setMinSelect(1);
        labelsView2.setLabelTextSize(40f);
        //labelsView2.setLabelBackgroundResource(R.drawable.labelsview_label_bg);
        labelsView2.setLabelBackgroundColor(Color.WHITE);
        labelsView2.setLabelTextColor(Color.rgb(153, 153, 153));
        labelsView2.setLabelTextPadding(15, 10, 15, 10);

        stringList2 = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            TasteImprove taste = new TasteImprove();
            taste.setAge(i + "");
            taste.setName(i + "");
            taste.setPlu(i + "");
            taste.setNum(i + "");
            taste.setSelected(false);
            stringList2.add(taste);
        }

        //LabelsView创建时调用
        labelsView2.setLabels(stringList1, new LabelsView.LabelTextProvider<TasteImprove>() {
            @Override
            public CharSequence getLabelText(TextViewImprove label, int position, TasteImprove data) {
                // label就是标签项，在这里可以对标签项单独设置一些属性，比如文本样式等。
                labelsView2.setLabelSelect(label, false);

//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//                layoutParams.setMargins(5,5,5,5);
//                label.setLayoutParams(layoutParams);
                //label.setBottom(0);

                label.setPadding(5,5,5,5);


                if (data.isSelected) {
                    label.setTextColor(Color.BLUE);
                }
                //根据data和position返回label需要显示的数据。
                if (data.getAge().equals("1")) {
                    return data.getName();
                }
                return data.getNum() + ":::::";
            }
        });

        //LabelsView点击label时调用
        labelsView2.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() {
            @Override
            public void onLabelSelectChange(TextViewImprove label, Object data, boolean isSelect, int position) {
                TasteImprove taste = (TasteImprove) data;
                if (isSelect) {
                    taste.setSelected(true);
                    label.setTextColor(Color.YELLOW);
                } else {
                    taste.setSelected(false);
                    label.setTextColor(Color.GRAY);
                }
            }
        });

        int childCount2 = labelsView2.getChildCount();
        for (int i = 0; i < childCount2; i++) {
            //+++++++++++++++++Use HTML String+++++
            TextViewImprove childAt = (TextViewImprove) labelsView2.getChildAt(i);
            int width = childAt.getWidth();
            int height = childAt.getHeight();

            childAt.setBadgeText("+¥3元");
//            BadgeView badgeView = new BadgeView(getBaseContext());
//            badgeView.setTargetView(childAt);
//            badgeView.setText("+3");

            //ViewParent parent = childAt.getParent();

            //LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //lp.setMargins(100, 100, 100, 100);
            //childAt.setLayoutParams(lp);



//            QBadgeView qBadgeView = new QBadgeView(getBaseContext());
//            qBadgeView.setBadgeGravity(Gravity.TOP | Gravity.END);
//            qBadgeView.bindTarget(childAt);
//            qBadgeView.setBadgeText("+¥3");
//            TextViewImprove targetView = (TextViewImprove) qBadgeView.getTargetView();
            //TODO 可以通过修改LabelsView源码，来处理转换问题
        }
    }


    public void change2() {
        Log.i("TAG", "改变右边数据");
        stringList2 = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Random random = new Random();
            int i1 = random.nextInt(100);
            String s = String.valueOf(i1);
            TasteImprove taste = new TasteImprove();
            taste.setSelected(false);
            taste.setNum(s);
            taste.setPlu(s);
            taste.setName(s);
            taste.setAge(s);
            stringList2.add(taste);
        }

        labelsView2.setLabels(stringList2, new LabelsView.LabelTextProvider<TasteImprove>() {
            @Override
            public CharSequence getLabelText(TextViewImprove label, int position, TasteImprove data) {
                // label就是标签项，在这里可以对标签项单独设置一些属性，比如文本样式等。
                labelsView2.setLabelSelect(label, false);
                if (data.isSelected) {
                    label.setTextColor(Color.BLUE);
                }
                //根据data和position返回label需要显示的数据。
                if (data.getAge().equals("1")) {
                    return data.getName();
                }
                return data.getNum() + ":::::";
            }
        });

        //LabelsView点击label时调用
        labelsView2.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() {
            @Override
            public void onLabelSelectChange(TextViewImprove label, Object data, boolean isSelect, int position) {
                TasteImprove taste = (TasteImprove) data;
                if (isSelect) {
                    taste.setSelected(true);
                    label.setTextColor(Color.YELLOW);
                } else {
                    taste.setSelected(false);
                    label.setTextColor(Color.GRAY);
                }
            }
        });
        labelsView2.postInvalidate();
    }
}
