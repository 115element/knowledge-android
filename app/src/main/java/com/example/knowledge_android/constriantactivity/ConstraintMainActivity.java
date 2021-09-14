package com.example.knowledge_android.constriantactivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge_android.R;

public class ConstraintMainActivity extends AppCompatActivity implements View.OnClickListener {


    RadioButton username;
    RadioButton password;
    TextView tipsValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_constraint_layout);

        //初始化view总的所有组件对象,并分别设置点击事件
        initView();
    }


    private void initView() {
        //设置各个控件的点击响应
        Button btn_0 = (Button) findViewById(R.id.btn_0);
        btn_0.setOnClickListener(this);

        Button btn_dot = (Button) findViewById(R.id.btn_dot);
        btn_dot.setOnClickListener(this);

        Button btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(this);

        Button btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(this);

        Button btn_enter = (Button) findViewById(R.id.btn_enter);
        btn_enter.setOnClickListener(this);

        Button btn_1 = (Button) findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);

        Button btn_2 = (Button) findViewById(R.id.btn_2);
        btn_2.setOnClickListener(this);

        Button btn_3 = (Button) findViewById(R.id.btn_3);
        btn_3.setOnClickListener(this);

        Button btn_4 = (Button) findViewById(R.id.btn_4);
        btn_4.setOnClickListener(this);

        Button btn_5 = (Button) findViewById(R.id.btn_5);
        btn_5.setOnClickListener(this);

        Button btn_6 = (Button) findViewById(R.id.btn_6);
        btn_6.setOnClickListener(this);

        Button btn_7 = (Button) findViewById(R.id.btn_7);
        btn_7.setOnClickListener(this);

        Button btn_8 = (Button) findViewById(R.id.btn_8);
        btn_8.setOnClickListener(this);

        Button btn_9 = (Button) findViewById(R.id.btn_9);
        btn_9.setOnClickListener(this);

        //登录的账号框
        username = (RadioButton) findViewById(R.id.username);
        username.setOnClickListener(this);
        //登录的密码框
        password = (RadioButton) findViewById(R.id.password);
        password.setOnClickListener(this);

        //最下方提示框
        tipsValue = (TextView) findViewById(R.id.tips_value);
        tipsValue.setOnClickListener(this);
    }


    @SuppressLint({"NonConstantResourceId", "UseCompatLoadingForDrawables"})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_0:
                System.out.println("btn_0");
                break;

            case R.id.btn_1:
                System.out.println("btn_1");
                break;

            case R.id.btn_2:
                System.out.println("btn_2");
                break;

            case R.id.btn_3:
                System.out.println("btn_3");
                break;

            case R.id.btn_4:
                System.out.println("btn_4");
                break;

            case R.id.btn_5:
                System.out.println("btn_5");
                break;

            case R.id.btn_6:
                System.out.println("btn_6");
                break;

            case R.id.btn_7:
                System.out.println("btn_7");
                break;

            case R.id.btn_8:
                System.out.println("btn_8");
                break;

            case R.id.btn_9:
                System.out.println("btn_9");
                break;

            case R.id.btn_dot:
                System.out.println("btn_dot");
                break;

            case R.id.btn_delete:
                System.out.println("btn_delete");
                break;

            case R.id.btn_enter:
                System.out.println("btn_enter");
                break;

            case R.id.btn_clear:
                System.out.println("btn_clear");
                break;

            case R.id.username:
                System.out.println("username");
                break;

            case R.id.password:
                System.out.println("password");
                break;

            default:
                System.out.println("can't find button ");
                break;
        }
    }
}
