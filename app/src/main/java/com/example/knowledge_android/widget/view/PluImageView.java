package com.example.knowledge_android.widget.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class PluImageView extends AppCompatImageView {
    private String imageProp;

    public PluImageView(Context context) {
        super(context);
    }

    public PluImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PluImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String getImageProp() {
        return imageProp;
    }

    public void setImageProp(String imageProp) {
        this.imageProp = imageProp;
    }
}
