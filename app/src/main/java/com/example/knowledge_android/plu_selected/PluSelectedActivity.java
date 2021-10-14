package com.example.knowledge_android.plu_selected;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.donkingliang.labels.LabelsView;
import com.example.knowledge_android.R;

import java.util.ArrayList;
import java.util.List;

public class PluSelectedActivity extends AppCompatActivity {

    List<PluBean> s0;
    List<PluBean> s1;
    List<PluBean> s2;
    List<PluBean> s3;

    LabelsView label0,label1,label2,label3;

    LinearLayout choiceLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_plu_simple);
        choiceLayout = findViewById(R.id.choice_layout);
        choiceLayout.setBackgroundResource(R.drawable.choice_border);

        initData();

        label0 = findViewById(R.id.labels0);
        initLabel0();
        label1 = findViewById(R.id.labels1);
        initLabel1();
        label2 = findViewById(R.id.labels2);
        initLabel2();
        label3 = findViewById(R.id.labels3);
        initLabel3();
    }

    private void initData() {
        s0 = new ArrayList<>();
        s0.add(new PluBean("宝石红茶阿萨姆热"));
        s0.add(new PluBean("经典阿萨姆奶茶"));
        s0.add(new PluBean("新鲜滤翠咖啡冷(600)"));

        s1 = new ArrayList<>();
        s1.add(new PluBean("加一份浓度"));
        s1.add(new PluBean("标准"));
        s1.add(new PluBean("半塘"));
        s1.add(new PluBean("无塘"));
        s1.add(new PluBean("全塘"));

        s2 = new ArrayList<>();
        s2.add(new PluBean("标准"));
        s2.add(new PluBean("半塘"));
        s2.add(new PluBean("无糖"));

        s3 = new ArrayList<>();
        s3.add(new PluBean("去冰"));
        s3.add(new PluBean("全冰"));
        s3.add(new PluBean("少冰"));
    }

    void initLabel0(){
        label0.setMaxLines(20);
        label0.setMaxColumns(10);
        label0.setWordMargin(10);
        label0.setLineMargin(10);
        label0.setSelectType(LabelsView.SelectType.SINGLE_IRREVOCABLY);
        label0.setMaxSelect(2);
        label0.setMinSelect(1);
        label0.setLabelTextSize(40f);
        label0.setLabelBackgroundResource(R.drawable.labelsview_label_bg);
        label0.setLabelBackgroundColor(Color.WHITE);
        label0.setLabelTextColor(Color.rgb(153, 153, 153));
        label0.setLabelTextPadding(15, 10, 15, 10);

        //LabelsView创建时调用
        label0.setLabels(s0, new LabelsView.LabelTextProvider<PluBean>() {
            @Override
            public CharSequence getLabelText(TextView label, int position, PluBean data) {
                // label就是标签项，在这里可以对标签项单独设置一些属性，比如文本样式等。
                label0.setLabelSelect(label, false);
                if (data.isSelected) {
                    label.setTextColor(Color.BLUE);
                }
                //根据data和position返回label需要显示的数据。
//                if (data.getAge().equals("1")) {
//                    return data.getName();
//                }
                return data.getName();
            }
        });

        //LabelsView点击label时调用
        label0.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onLabelSelectChange(TextView label, Object data, boolean isSelect, int position) {
                PluBean pluBean = (PluBean) data;
                if (isSelect) {
                    pluBean.setSelected(true);
                    label.setTextColor(Color.parseColor("#c25348"));
                    //label.setBackground(R.id.choice_border);
                    label.setBackgroundResource(R.drawable.choice_border);
                } else {
                    pluBean.setSelected(false);
                    label.setTextColor(Color.GRAY);
                    label.setBackgroundResource(0); //设置为0，表示删除边框
                }

                //点击左边，改变右边数据
                //change2();
            }
        });
    }

    void initLabel1(){
        label1.setMaxLines(20);
        label1.setMaxColumns(10);
        label1.setWordMargin(10);
        label1.setLineMargin(10);
        label1.setSelectType(LabelsView.SelectType.SINGLE_IRREVOCABLY);
        label1.setMaxSelect(2);
        label1.setMinSelect(1);
        label1.setLabelTextSize(40f);
        label1.setLabelBackgroundResource(R.drawable.labelsview_label_bg);
        label1.setLabelBackgroundColor(Color.WHITE);
        label1.setLabelTextColor(Color.rgb(153, 153, 153));
        label1.setLabelTextPadding(15, 10, 15, 10);
        //LabelsView创建时调用
        label1.setLabels(s1, new LabelsView.LabelTextProvider<PluBean>() {
            @Override
            public CharSequence getLabelText(TextView label, int position, PluBean data) {
                // label就是标签项，在这里可以对标签项单独设置一些属性，比如文本样式等。
                label1.setLabelSelect(label, false);
                if (data.isSelected) {
                    label.setTextColor(Color.BLUE);
                }
                //根据data和position返回label需要显示的数据。
//                if (data.getAge().equals("1")) {
//                    return data.getName();
//                }
                return data.getName();
            }
        });

        //LabelsView点击label时调用
        label1.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() {
            @Override
            public void onLabelSelectChange(TextView label, Object data, boolean isSelect, int position) {
                PluBean pluBean = (PluBean) data;
                if (isSelect) {
                    pluBean.setSelected(true);
                    label.setTextColor(Color.parseColor("#c25348"));
                } else {
                    pluBean.setSelected(false);
                    label.setTextColor(Color.GRAY);
                }

                //点击左边，改变右边数据
                //change2();
            }
        });
    }

    void initLabel2(){
        label2.setMaxLines(20);
        label2.setMaxColumns(10);
        label2.setWordMargin(10);
        label2.setLineMargin(10);
        label2.setSelectType(LabelsView.SelectType.SINGLE_IRREVOCABLY);
        label2.setMaxSelect(2);
        label2.setMinSelect(1);
        label2.setLabelTextSize(40f);
        label2.setLabelBackgroundResource(R.drawable.labelsview_label_bg);
        label2.setLabelBackgroundColor(Color.WHITE);
        label2.setLabelTextColor(Color.rgb(153, 153, 153));
        label2.setLabelTextPadding(15, 10, 15, 10);

        //LabelsView创建时调用
        label2.setLabels(s2, new LabelsView.LabelTextProvider<PluBean>() {
            @Override
            public CharSequence getLabelText(TextView label, int position, PluBean data) {
                // label就是标签项，在这里可以对标签项单独设置一些属性，比如文本样式等。
                label2.setLabelSelect(label, false);
                if (data.isSelected) {
                    label.setTextColor(Color.BLUE);
                }
                //根据data和position返回label需要显示的数据。
//                if (data.getAge().equals("1")) {
//                    return data.getName();
//                }
                return data.getName();
            }
        });

        //LabelsView点击label时调用
        label2.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() {
            @Override
            public void onLabelSelectChange(TextView label, Object data, boolean isSelect, int position) {
                PluBean pluBean = (PluBean) data;
                if (isSelect) {
                    pluBean.setSelected(true);
                    label.setTextColor(Color.parseColor("#c25348"));
                } else {
                    pluBean.setSelected(false);
                    label.setTextColor(Color.GRAY);
                }

                //点击左边，改变右边数据
                //change2();
            }
        });
    }

    void initLabel3(){
        label3.setMaxLines(20);
        label3.setMaxColumns(10);
        label3.setWordMargin(10);
        label3.setLineMargin(10);
        label3.setSelectType(LabelsView.SelectType.SINGLE_IRREVOCABLY);
        label3.setMaxSelect(2);
        label3.setMinSelect(1);
        label3.setLabelTextSize(40f);
        label3.setLabelBackgroundResource(R.drawable.labelsview_label_bg);
        label3.setLabelBackgroundColor(Color.WHITE);
        label3.setLabelTextColor(Color.rgb(153, 153, 153));
        label3.setLabelTextPadding(15, 10, 15, 10);

        //LabelsView创建时调用
        label3.setLabels(s3, new LabelsView.LabelTextProvider<PluBean>() {
            @Override
            public CharSequence getLabelText(TextView label, int position, PluBean data) {
                // label就是标签项，在这里可以对标签项单独设置一些属性，比如文本样式等。
                label3.setLabelSelect(label, false);
                if (data.isSelected) {
                    label.setTextColor(Color.BLUE);
                }
                //根据data和position返回label需要显示的数据。
//                if (data.getAge().equals("1")) {
//                    return data.getName();
//                }
                return data.getName();
            }
        });

        //LabelsView点击label时调用
        label3.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() {
            @Override
            public void onLabelSelectChange(TextView label, Object data, boolean isSelect, int position) {
                PluBean pluBean = (PluBean) data;
                if (isSelect) {
                    pluBean.setSelected(true);
                    label.setTextColor(Color.parseColor("#c25348"));
                } else {
                    pluBean.setSelected(false);
                    label.setTextColor(Color.GRAY);
                }

                //点击左边，改变右边数据
                //change2();
            }
        });
    }
}
