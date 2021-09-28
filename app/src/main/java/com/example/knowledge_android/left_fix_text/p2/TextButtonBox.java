package com.example.knowledge_android.left_fix_text.p2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.knowledge_android.R;

public class TextButtonBox extends RelativeLayout {

    private TextView left;
    private Button right;

    public TextButtonBox(Context context) {
        this(context,null);
    }

    public TextButtonBox(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TextButtonBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.button_input_box, this);
        init(context,attrs,defStyleAttr);
    }

    @SuppressLint("NonConstantResourceId")
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        left = findViewById(R.id.fixed_left_text);
        right = findViewById(R.id.fixed_right_button);

        //使用自定义组件属性时，需用到这个类
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextButtonBox, defStyleAttr, 0);
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.TextButtonBox_leftFixText:
                    left.setText(array.getString(attr));
                    break;
                case R.styleable.TextButtonBox_leftFixTextColor:
                    left.setTextColor(array.getColor(attr, Color.BLACK));
                    break;
                case R.styleable.TextButtonBox_leftFixTextSize:
                    left.setTextSize(TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(attr, 0));
                    break;
                case R.styleable.TextButtonBox_rightButtonText:
                    right.setText(array.getString(attr));
                    break;
                case R.styleable.TextButtonBox_rightButtonHint:
                    right.setHint(array.getString(attr));
                    break;
                case R.styleable.TextButtonBox_rightButtonTextColor:
                    right.setTextColor(array.getColor(attr, Color.BLACK));
                    break;
                case R.styleable.TextButtonBox_rightButtonTextSize:
                    right.setTextSize(TypedValue.COMPLEX_UNIT_PX, array.getDimensionPixelSize(attr, 0));
                    break;
                case R.styleable.TextButtonBox_rightButtonBg:
                    right.setBackgroundColor(array.getColor(attr, Color.BLACK));
                    break;
            }
        }
        array.recycle();
    }
}
