package com.example.knowledge_android.widget.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.appcompat.widget.AppCompatButton;

import com.arasthel.swissknife.annotations.OnUIThread;
import com.example.knowledge_android.OneApplication;
import com.example.knowledge_android.R;
import com.example.knowledge_android.comparator.ThemeUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class PhonicVoidButton extends AppCompatButton implements View.OnTouchListener{

    private static final String SOUND_FILE = "beep";

    /** Sound pool to keep key sounds. */
    private static SoundPool mSoundPool;

    /** Normal key press sound id returning from SoundPool.load(). */
    private static int mKeyPressStandardSoundId;

    /** Animation Duration Time millisecond*/
    long durationTime = 300;

    private static Map<Integer, Typeface> fontCache = new HashMap<>();

    AlphaAnimation buttonTouchAnimation = new AlphaAnimation(1f, 0.7f);

    private AlphaAnimation buttonAnimation = new AlphaAnimation(1f, 0.4f);

    int backgroundDrawableId;

    int buttonColor;

    int originColor = ThemeUtil.downInfoButtonOriginColor;
    int targetColor = Color.RED;

    float radius;

    String stillMessage = "";
    String warningMessage = "";

    private Timer onOffTimer;
    private int flipper;

    //形状 图
    private GradientDrawable shapeDrawable;

    PhonicVoidButton(Context context) {
        super(context);
        init(null);
    }

    PhonicVoidButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    PhonicVoidButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @SuppressLint("WrongConstant")
    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PluButtonView);
            final int buttonColor = a.getColor(R.styleable.PluButtonView_buttonColor, (int) 0xff0d9f67);
            setButtonColor(buttonColor);
            if (!isInEditMode()) {
                int userFont = a.getInt(R.styleable.PluButtonView_userFont, 0);
                if (userFont == 1) {
                    Typeface font = fontCache.get(userFont);
                    if (font == null) {
                        font = OneApplication.getInstance().getUserFont();
                        fontCache.put(userFont, font);
                    }
                    int fontStyle = a.getInt(R.styleable.PluButtonView_buttonFontStyle, 0);
                    setTypeface(font, fontStyle);
                }
            }
            a.recycle();
        }
        setOnTouchListener(this);
        loadClickingSound();
        /** Alex 处理按钮中字体自适应按钮大小问题**/
        //setAutoSizeTextTypeWithDefaults(TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        setTextAlignment(Gravity.CENTER);
    }

    void setRadius(float radius) {
        this.radius = radius;
        if (radius > 0) {
            getGradientDrawable();
            shapeDrawable.setColor(Color.BLACK);
            shapeDrawable.setCornerRadius(radius);
            setDrawable(shapeDrawable);
        }
    }

    /**
     * 设置背景
     *
     * @param drawable 背景
     */
    private void setDrawable(Drawable drawable) {
        setBackgroundDrawable(drawable);
    }

    /**
     * 获取需要设置到背景的图片
     */
    private void getGradientDrawable() {
        if (shapeDrawable == null) {
            shapeDrawable = new GradientDrawable();
        }
    }

    void setUseUserFont(boolean useUserFont) {
        if (useUserFont) {
            Typeface font = OneApplication.getInstance().getUserFont();
            setTypeface(font, Typeface.NORMAL);
        }
    }

    boolean isUsingSlowerAnimation() {
        return true;
        // Test if running in an emulator:
        //return Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.startsWith("unknown")||
        //    Build.MODEL.contains("google_sdk") || Build.MODEL.contains("Emulator") ||
        //    Build.MODEL.contains("Android SDK built for x86") ||
        //    Build.MANUFACTURER.contains("Genymotion") ||
        //    (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) ||
        //    "google_sdk".equals(Build.PRODUCT)
    }

    void setStillMessage(String stillMessage) {
        String fromWarningToStill = "";
        if (TextUtils.isEmpty(warningMessage)) {
            fromWarningToStill = warningMessage;
        }
        if (this.stillMessage.equals(stillMessage) && !TextUtils.isEmpty(fromWarningToStill))
          return;

        if (!TextUtils.isEmpty(fromWarningToStill)) {
            this.warningMessage = "";
        }

        this.setText(stillMessage);
        this.stillMessage = stillMessage;
        setButtonColor(originColor);
        if (isUsingSlowerAnimation()){
            if (onOffTimer != null) {
                onOffTimer.cancel();
            }
        } else {
            this.clearAnimation();
        }

        setAlpha(1f);
    }

    void setWarningMessage(String warningMessage) {
        String fromStillToWarning = "";
        if (!TextUtils.isEmpty(this.stillMessage)) {
            fromStillToWarning = this.stillMessage;
        }
        if (this.warningMessage == warningMessage && !TextUtils.isEmpty(fromStillToWarning))
            return;

        if (!TextUtils.isEmpty(fromStillToWarning)) {
            this.stillMessage = "";
        }

        this.setText(warningMessage);
        this.warningMessage = warningMessage;
        setButtonColor(targetColor);

        if (isUsingSlowerAnimation()) {
            if (onOffTimer != null) {
                onOffTimer.cancel();
            }
            onOffTimer = new Timer();
            onOffTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setTransparency(flipper++ % 2 == 0 ? 1 : 0.66f);
                }
            },0,666);
        } else {
            this.clearAnimation();
            buttonAnimation.setRepeatCount(Animation.INFINITE);
            buttonAnimation.setDuration(durationTime);
            buttonAnimation.setFillBefore(true); /** 是否还原到填充之前**/
            buttonAnimation.setRepeatMode(Animation.REVERSE);
            this.startAnimation(buttonAnimation);
        }
    }

    @OnUIThread
    private void setTransparency(float alpha) {
        setAlpha(alpha);
    }

    /**
     * 设置按键基底圖片。
     */
    void setBackgroundDrawableId(int resId) {
        backgroundDrawableId = resId;
    }

    /**
     * 设置按键底色。
     */
    void setButtonColor(int buttonColor) {
        if (this.buttonColor == buttonColor)
            return;

        this.buttonColor = buttonColor;

        Drawable keyBackground = getResources().getDrawable(backgroundDrawableId);
        // Make it translucent to keep the bottom shadow of the button background image
        final int translucentColor = buttonColor;

//        final int translucentColor = (buttonColor & (int) 0x00ffffff) | (int) 0xB9000000
        keyBackground.setColorFilter(translucentColor, PorterDuff.Mode.SRC_ATOP);
        setBackground(keyBackground);
    }

    @Override
    public boolean onTouch(View button, MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            if (warningMessage != null) {
                button.startAnimation(buttonTouchAnimation);
            }
        } else if (action == MotionEvent.ACTION_UP) {
            playClickingSound(); // make clicking sound
        }
        return false;
    }

    static void doPlayClickingSound() {
        mSoundPool.play(mKeyPressStandardSoundId, 1f, 1f, 1, 0, 1f);
    }

    void playClickingSound() {
        if (!isInEditMode())
            mSoundPool.play(mKeyPressStandardSoundId, 1f, 1f, 1, 0, 1f);
    }

    private void loadClickingSound() {
        if (isInEditMode()) {
            return;
        }

        if (mSoundPool == null) {
            mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
            try {
                int soundResId = R.raw.class.getField(SOUND_FILE).getInt(null);
                mKeyPressStandardSoundId = mSoundPool.load(getContext(), soundResId, 1);
                mSoundPool.play(mKeyPressStandardSoundId, 0.01f, 0.01f, 1, 0, 1f);
                // for faster first sound
            } catch (Exception e) {
                //log.error('PhonicButton', 'Failed to load sound for name: keypress_sound01', e)
            }
        }
    }
}
