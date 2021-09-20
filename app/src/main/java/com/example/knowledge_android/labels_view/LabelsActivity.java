package com.example.knowledge_android.labels_view;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.donkingliang.labels.LabelsView;
import com.example.knowledge_android.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LabelsActivity extends AppCompatActivity {

    List<Taste> stringList1;
    List<Taste> stringList2;
    LabelsView labelsView1;
    LabelsView labelsView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.labels_view);

        labelsView1 = findViewById(R.id.labelsview1);
        labelsView1.setMaxLines(20);
        labelsView1.setMaxColumns(10);
        labelsView1.setWordMargin(10);
        labelsView1.setLineMargin(10);
        labelsView1.setSelectType(LabelsView.SelectType.SINGLE_IRREVOCABLY);
        labelsView1.setMaxSelect(2);
        labelsView1.setMinSelect(1);
        labelsView1.setLabelTextSize(40f);
        labelsView1.setLabelBackgroundResource(R.drawable.labelsview_label_bg);
        labelsView1.setLabelBackgroundColor(Color.WHITE);
        labelsView1.setLabelTextColor(Color.rgb(153, 153, 153));
        labelsView1.setLabelTextPadding(15, 10, 15, 10);

        stringList1 = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            Taste taste = new Taste();
            taste.setAge(i + "");
            taste.setName(i + "");
            taste.setPlu(i + "");
            taste.setNum(i + "");
            taste.setSelected(false);
            stringList1.add(taste);
        }

        //LabelsView创建时调用
        labelsView1.setLabels(stringList1, new LabelsView.LabelTextProvider<Taste>() {
            @Override
            public CharSequence getLabelText(TextView label, int position, Taste data) {
                // label就是标签项，在这里可以对标签项单独设置一些属性，比如文本样式等。
                labelsView1.setLabelSelect(label, false);
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
        labelsView1.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() {
            @Override
            public void onLabelSelectChange(TextView label, Object data, boolean isSelect, int position) {
                Taste taste = (Taste) data;
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


        labelsView2 = findViewById(R.id.labelsview2);
        labelsView2.setMaxLines(20);
        labelsView2.setMaxColumns(10);
        labelsView2.setWordMargin(10);
        labelsView2.setLineMargin(10);
        labelsView2.setSelectType(LabelsView.SelectType.SINGLE_IRREVOCABLY);
        labelsView2.setMaxSelect(2);
        labelsView2.setMinSelect(1);
        labelsView2.setLabelTextSize(40f);
        labelsView2.setLabelBackgroundResource(R.drawable.labelsview_label_bg);
        labelsView2.setLabelBackgroundColor(Color.WHITE);
        labelsView2.setLabelTextColor(Color.rgb(153, 153, 153));
        labelsView2.setLabelTextPadding(15, 10, 15, 10);

        stringList2 = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            Taste taste = new Taste();
            taste.setAge(i + "");
            taste.setName(i + "");
            taste.setPlu(i + "");
            taste.setNum(i + "");
            taste.setSelected(false);
            stringList2.add(taste);
        }

        //LabelsView创建时调用
        labelsView2.setLabels(stringList1, new LabelsView.LabelTextProvider<Taste>() {
            @Override
            public CharSequence getLabelText(TextView label, int position, Taste data) {
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
            public void onLabelSelectChange(TextView label, Object data, boolean isSelect, int position) {
                Taste taste = (Taste) data;
                if (isSelect) {
                    taste.setSelected(true);
                    label.setTextColor(Color.YELLOW);
                } else {
                    taste.setSelected(false);
                    label.setTextColor(Color.GRAY);
                }
            }
        });
    }


    public void change2() {
        Log.i("TAG", "改变右边数据");
        stringList2 = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Random random = new Random();
            int i1 = random.nextInt(100);
            String s = String.valueOf(i1);
            Taste taste = new Taste();
            taste.setSelected(false);
            taste.setNum(s);
            taste.setPlu(s);
            taste.setName(s);
            taste.setAge(s);
            stringList2.add(taste);
        }

        labelsView2.setLabels(stringList2, new LabelsView.LabelTextProvider<Taste>() {
            @Override
            public CharSequence getLabelText(TextView label, int position, Taste data) {
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
            public void onLabelSelectChange(TextView label, Object data, boolean isSelect, int position) {
                Taste taste = (Taste) data;
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
