package com.example.knowledge_android.widget.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.knowledge_android.tablayout_noscrollviewpager.MainActivity;
import com.example.knowledge_android.R;

import java.math.BigDecimal;

public class PluButtonViewV3 extends ConstraintLayout {
    /**
     * 关联商品。
     */
//    PluButton pluButton
//    AbstractAndriodScreenTheme screenTheme
    MainActivity mainActivity;

    /**
     * 当前此商品的销售数量。
     */
    BigDecimal currentSaleQty = BigDecimal.ZERO;

    public PluButtonViewV3(Context context) {
        super(context);
//        this.mainActivity = (MainActivity) context
//        this.screenTheme = (AbstractAndriodScreenTheme) mainActivity.getScreenTheme()
        initView(context);
    }

    public PluButtonViewV3(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    void initView(Context context) {
        inflate(context, R.layout.plu_button_view, this);
        ImageView imageView = (ImageView) findViewById(R.id.plu_image);
        String imageUrl = "http://image.hongyuan-soft.com/images/pos/plu/twnc/${pluButton?.pluCode}.jpg";
//        Glide.with(mainActivity)
//                .load(imageUrl)
//                 //.listener(requestListener)
//                .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);

        AppCompatTextView nameTextView = (AppCompatTextView) findViewById(R.id.plu_name);
        nameTextView.setText("name");

        AppCompatTextView priceTextView = (AppCompatTextView) findViewById(R.id.plu_price);
        priceTextView.setText("price");

        AppCompatTextView qtyTextView = (AppCompatTextView) findViewById(R.id.plu_qty);
        qtyTextView.setText("qty");
    }


    /**
     * 当前此商品的销售数量。
     */
    void setCurrentSaleQty(BigDecimal currentSaleQty) {
        if (!this.currentSaleQty.equals(currentSaleQty)) {
            this.currentSaleQty = currentSaleQty;
            invalidate();
        }
    }

    RequestListener<String> requestListener = new RequestListener<String>() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<String> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(String resource, Object model, Target<String> target, DataSource dataSource, boolean isFirstResource) {
            return false;
        }
    };

}
