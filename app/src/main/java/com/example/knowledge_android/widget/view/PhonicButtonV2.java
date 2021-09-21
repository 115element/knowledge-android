package com.example.knowledge_android.widget.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.arasthel.swissknife.annotations.OnUIThread;
import com.example.knowledge_android.OneApplication;
import com.example.knowledge_android.R;
import com.example.knowledge_android.comparator.ThemeUtil;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class PhonicButtonV2 extends MaterialButton implements View.OnTouchListener {
    private static final String SOUND_FILE = "beep";

    /**
     * Sound pool to keep key sounds.
     */
    private static SoundPool mSoundPool;

    /**
     * Normal key press sound id returning from SoundPool.load().
     */
    private static int mKeyPressStandardSoundId;

    /**
     * Animation Duration Time millisecond
     */
    long durationTime = 3000;

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

    PhonicButtonV2(Context context) {
        super(context);
        init(null);
    }

    PhonicButtonV2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    PhonicButtonV2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @SuppressLint("WrongConstant")
    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PluButtonView);
//            final int buttonColor = a.getColor(R.styleable.PluButtonView_buttonColor, (int) 0xff0d9f67)
//            setButtonColor(buttonColor)
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
//        init2()
    }

    private int animatedValue;
    private int colorEnd;
    private int colorStart;
    private int animatedValue1;

    void init2() {
        postInvalidate();
        ValueAnimator animator = ValueAnimator.ofInt(0, 255);
        animator.setDuration(10000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatedValue = (int) animation.getAnimatedValue();
                if (animatedValue < 255) {
                    colorStart = Color.rgb(255, animatedValue, 255 - animatedValue);
                    colorEnd = Color.rgb(animatedValue, 0, 255 - animatedValue);
                    shapeDrawable.setStroke(getStrokeWidth(), getStrokeColor(), 5f, 3f);
                } else if (animatedValue == 255) {
                    ValueAnimator animator1 = ValueAnimator.ofInt(0, 255);
                    animator1.setDuration(2500);
                    animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animationx) {
                            animatedValue1 = (int) animationx.getAnimatedValue();
                            colorStart = Color.rgb(255 - animatedValue1, 255 - animatedValue1, animatedValue1);
                            colorEnd = Color.rgb(255, 0, animatedValue1);
                            if (animatedValue1 == 255) {
                                ValueAnimator animator2 = ValueAnimator.ofInt(0, 255);
                                animator2.setDuration(2500);
                                animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animationy) {
                                        int animatedValue2 = (int) animationy.getAnimatedValue();
                                        colorStart = Color.rgb(animatedValue2, 0, 255);
                                        colorEnd = Color.rgb(255 - animatedValue2, 0, 255);
                                        invalidate();
                                    }
                                });
                                animator2.start();
                            }
                            invalidate();
                        }
                    });
                    animator1.start();
                }
                invalidate();
            }
        });
        animator.start();
    }

    void setRadius(float radius) {
        this.radius = radius;
        if (radius > 0) {
            getGradientDrawable();
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
    @SuppressLint("WrongConstant")
    private void getGradientDrawable() {
        if (shapeDrawable == null) {
            setBackgroundTintList(null);
            shapeDrawable = new GradientDrawable();
            // prepare
            int strokeWidth = 10; // 3px not dp
            int roundRadius = 15; // 8px not dp
            int strokeColor = Color.parseColor("#FF8C00");
            int fillColor = buttonColor;

            shapeDrawable.setColor(fillColor);
            shapeDrawable.setCornerRadius(roundRadius);
            shapeDrawable.setStroke(strokeWidth, strokeColor, 10f, 10f);

//Create your shape programatically
            ValueAnimator animation = ValueAnimator.ofFloat(1f, 0.66f);
            animation.setDuration(1000);
            animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animationx) {
                    setTransparency(flipper++ % 2 == 0 ? 1 : 0.66f);
//                    shapeDrawable.setStroke(strokeWidth, strokeColor, ((Float)animationx.getAnimatedValue()), 10f)
                }
            });
            animation.setRepeatCount(ValueAnimator.INFINITE);
            animation.setRepeatMode(ValueAnimator.INFINITE);
            animation.start();
//            onOffTimer?.cancel()
//            onOffTimer = new Timer()
//            onOffTimer.schedule(new TimerTask() {
//                void run() {
//                    setTransparency(flipper)
////                    ((GradientDrawable) getBackground()).setStroke(1,
////                            flipper++ % 2 == 0 ? Color.WHITE : buttonColor);
//                    setTransparency(flipper++ % 2 == 0 ? 1 : 0.66f)
////                    setTransparency(flipper++ % 2 == 0 ? Color.WHITE : Color.RED)
//                }
//            }, 0, durationTime)

//            GradientDrawable background = new GradientDrawable();
//            background.setShape(GradientDrawable.OVAL);
//            background.setStroke(10,Color.RED);//设置宽度为10px的红色描边
//            background.setGradientType(GradientDrawable.LINEAR_GRADIENT);//设置线性渐变，除此之外还有：GradientDrawable.SWEEP_GRADIENT（扫描式渐变），GradientDrawable.RADIAL_GRADIENT（圆形渐变）
//            background.setColors(new int[]{Color.RED,Color.BLUE});//增加渐变效果需要使用setColors方法来设置颜色（中间可以增加多个颜色值）
//            view.setBackgroundDrawable(background);
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
        String fromWarningToStill = TextUtils.isEmpty(this.warningMessage) ? "" : warningMessage;
        if (this.stillMessage == stillMessage && !TextUtils.isEmpty(fromWarningToStill))
            return;

        if (!TextUtils.isEmpty(fromWarningToStill)) {
            this.warningMessage = "";
        }

        this.setText(stillMessage);
        this.stillMessage = stillMessage;
        setButtonColor(originColor);
        if (isUsingSlowerAnimation()) {
            if (onOffTimer!=null) {
                onOffTimer.cancel();
            }
        } else {
            this.clearAnimation();
        }
        setAlpha(1f);
    }

    void setWarningMessage(String warningMessage) {
        String fromStillToWarning = TextUtils.isEmpty(this.stillMessage) ? "" : stillMessage;

        if (this.warningMessage.equals(warningMessage) && !TextUtils.isEmpty(fromStillToWarning))
            return;

        if (!TextUtils.isEmpty(fromStillToWarning))
            this.stillMessage = "";

        this.setText(warningMessage);
        this.warningMessage = warningMessage;
        setButtonColor(targetColor);

        if (isUsingSlowerAnimation()) {
//            onOffTimer?.cancel()
//            onOffTimer = new Timer()
//            onOffTimer.schedule(new TimerTask() {
//                void run() {
//                    setTransparency(flipper++ % 2 == 0 ? 1 : 0.66f)
//                }
//            }, 0, 666)


//            ((GradientDrawable) getBackground()).setStroke(1,Color.WHITE);
//            AlphaAnimation blinkanimation= new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
//            blinkanimation.setDuration(500); // duration - half a second
//            blinkanimation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
//            blinkanimation.setRepeatCount(28); // Repeat animation infinitely
//            blinkanimation.setRepeatMode(Animation.REVERSE);
//            this.startAnimation(blinkanimation);

//            onOffTimer?.cancel()
//            onOffTimer = new Timer()
//            onOffTimer.schedule(new TimerTask() {
//                void run() {
////                    ((GradientDrawable) getBackground()).setStroke(1,
////                            flipper++ % 2 == 0 ? Color.WHITE : buttonColor);
////                    setTransparency(flipper++ % 2 == 0 ? 1 : 0.66f)
////                    setTransparency(flipper++ % 2 == 0 ? Color.WHITE : Color.RED)
//                }
//            }, 0, durationTime)
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
//        shapeDrawable.setStroke(strokeWidth, strokeColor, flipper % 2 == 0 ? 0f: 10f, flipper++ % 2 == 0 ? 0f: 20f)
        setAlpha(alpha);
//        setStrokeWidth(flipper++ % 2 == 0 ? 0: 20)
//        ((GradientDrawable) getBackground()).setColor(alpha)//.setStroke(3, alpha)
//        setBackgroundColor(alpha)
//        setButtonColor(alpha)
//        setTextColor(alpha)
//        Drawable bg = ResourcesCompat.getDrawable(getResources(), R.drawable.button_border, null)
//        setBackground(bg)

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
        if (this.buttonColor == buttonColor) {
            return;
        }

        this.buttonColor = buttonColor;
//        GradientDrawable keyBackground = new GradientDrawable()
//        keyBackground.setColor(buttonColor)
////        Drawable keyBackground = resources.getDrawable(backgroundDrawableId)
////         Make it translucent to keep the bottom shadow of the button background image
//        final int translucentColor = (buttonColor & (int) 0x00ffffff) | (int) 0xB9000000
//        keyBackground.setColorFilter(translucentColor, PorterDuff.Mode.SRC_ATOP)
//        setBackground(keyBackground)
    }

    @Override
    public boolean onTouch(View button, MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            if (!TextUtils.isEmpty(warningMessage)) {
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
                Log.e("PhonicButton", "Failed to load sound for name: keypress_sound01", e);
            }
        }
    }

}
