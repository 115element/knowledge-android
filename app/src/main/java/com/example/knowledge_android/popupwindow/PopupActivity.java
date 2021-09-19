package com.example.knowledge_android.popupwindow;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.knowledge_android.R;
import com.example.knowledge_android.daosupport.bean.tran.TranHead;
import com.example.knowledge_android.daosupport.daohelp.DaoLocator;
import com.example.knowledge_android.daosupport.bean.master.PluDac;
import com.example.knowledge_android.daosupport.beandao.master.PluDao;

import java.util.List;

public class PopupActivity extends Activity implements CustomPopupWindow.OnItemClickListener {

    private CustomPopupWindow mPop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_layout_1);
        mPop = new CustomPopupWindow(this);
        mPop.setOnItemClickListener(this);

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //设置PopupWindow中的位置
                mPop.showAtLocation(PopupActivity.this.findViewById(R.id.textView3), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
    }

    @Override
    public void setOnItemClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.id_btn_take_photo:
                PluDao pluDao = DaoLocator.pluDao;
                List<PluDac> pluDacs = pluDao.queryAll();
                Toast.makeText(getApplicationContext(), pluDacs.toString(), Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), "拍照", Toast.LENGTH_LONG).show();
                break;
            case R.id.id_btn_select:
                TranHead query = DaoLocator.tranHeadDao.query();
                Toast.makeText(getApplicationContext(), query.toString(), Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), "从相册中选择", Toast.LENGTH_LONG).show();
                break;
            case R.id.id_btn_cancelo:
                mPop.dismiss();
                break;
        }
    }
}