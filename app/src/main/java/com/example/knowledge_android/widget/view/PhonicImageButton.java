package com.example.knowledge_android.widget.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.knowledge_android.R;

public class PhonicImageButton extends androidx.appcompat.widget.AppCompatImageButton implements View.OnTouchListener {

    private static final String SOUND_FILE = "beep";

    /** Sound pool to keep key sounds. */
    private static SoundPool mSoundPool;

    /** Normal key press sound id returning from SoundPool.load(). */
    private static int mKeyPressStandardSoundId;

    private Drawable mKeyBackground;

    private Drawable mKeyClickingBackground;

//    private int leftPadding
//    private int rightPadding

    public PhonicImageButton(Context context) {
        super(context);
        init();
    }

    public PhonicImageButton(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
    }

    private void init() {
        //setButtonColor((int) 0xff0d9f67)
        setScaleType(ImageView.ScaleType.FIT_CENTER);
        setOnTouchListener(this);
        loadClickingSound();
    }

/*
    private void drawRandomColorOnBackground() {
        float hue = new Random().nextFloat()
        int buttonColor = convertFromHSBtoRGB(hue, 0.3f, 0.85f)
        setButtonColor(buttonColor)
    }
*/

    /**
     * 设置按键底色。
     */
    void setButtonColor(int backgroundColor) {
        //setBackgroundResource(R.drawable.ic_button_apple_white)
        // Make it translucent to keep the bottom shadow of the button background image
        backgroundColor = (backgroundColor & (int) 0x00ffffff) | (int) 0xB9000000;
        Drawable keyBackground = getBackground().getConstantState().newDrawable();
        keyBackground.setColorFilter(backgroundColor, PorterDuff.Mode.SRC_ATOP);
        setBackgroundDrawable(keyBackground);
        createClickingBackground();
    }

//    int convertFromHSBtoRGB(float hue, float saturation, float brightness) {
//        int r = 0, g = 0, b = 0
//        if (saturation == 0) {
//            r = g = b = (int) (brightness * 255.0f + 0.5f)
//        } else {
//            float h = (hue - (float)Math.floor(hue)) * 6.0f
//            float f = h - (float)java.lang.Math.floor(h)
//            float p = brightness * (1.0f - saturation)
//            float q = brightness * (1.0f - saturation * f)
//            float t = brightness * (1.0f - (saturation * (1.0f - f)))
//            switch ((int) h) {
//                case 0:
//                    r = (int) (brightness * 255.0f + 0.5f)
//                    g = (int) (t * 255.0f + 0.5f)
//                    b = (int) (p * 255.0f + 0.5f)
//                    break
//                case 1:
//                    r = (int) (q * 255.0f + 0.5f)
//                    g = (int) (brightness * 255.0f + 0.5f)
//                    b = (int) (p * 255.0f + 0.5f)
//                    break
//                case 2:
//                    r = (int) (p * 255.0f + 0.5f)
//                    g = (int) (brightness * 255.0f + 0.5f)
//                    b = (int) (t * 255.0f + 0.5f)
//                    break
//                case 3:
//                    r = (int) (p * 255.0f + 0.5f)
//                    g = (int) (q * 255.0f + 0.5f)
//                    b = (int) (brightness * 255.0f + 0.5f)
//                    break
//                case 4:
//                    r = (int) (t * 255.0f + 0.5f)
//                    g = (int) (p * 255.0f + 0.5f)
//                    b = (int) (brightness * 255.0f + 0.5f)
//                    break
//                case 5:
//                    r = (int) (brightness * 255.0f + 0.5f)
//                    g = (int) (p * 255.0f + 0.5f)
//                    b = (int) (q * 255.0f + 0.5f)
//                    break
//            }
//        }
//        return 0xff000000 | (r << 16) | (g << 8) | (b << 0)
//    }

    private void createClickingBackground() {
        mKeyBackground = getBackground();
        mKeyClickingBackground = getBackground().getConstantState().newDrawable();
        mKeyClickingBackground.setColorFilter(0x49FF2626, PorterDuff.Mode.SRC_ATOP);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("deprecation")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final boolean belowSdk16 = android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN;

        //setPadding(leftPadding, getPaddingTop(), rightPadding, getPaddingBottom())

        // Make clicking effect
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                playClickingSound();
//                if (belowSdk16)
//                    setBackgroundDrawable(mKeyClickingBackground)
//                else
//                    setBackground(mKeyClickingBackground)
//                break

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
//                if (belowSdk16)
//                    setBackgroundDrawable(mKeyBackground)
//                else
//                    setBackground(mKeyBackground)
                break;
        }
        return false;
    }

    static void doPlayClickingSound() {
        mSoundPool.play(mKeyPressStandardSoundId, 1f, 1f, 1, 0, 1f);
    }

    private void playClickingSound() {
        if (!isInEditMode())
            mSoundPool.play(mKeyPressStandardSoundId, 1f, 1f, 1, 0, 1f);
    }

    private void loadClickingSound() {
        if (isInEditMode()) return;

        if (mSoundPool == null) {
            mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
            try {
                int soundResId = R.raw.class.getField(SOUND_FILE).getInt(null);
                mKeyPressStandardSoundId = mSoundPool.load(getContext(), soundResId, 1);
                mSoundPool.play(mKeyPressStandardSoundId, 0.01f, 0.01f, 1, 0, 1f); // for faster first sound
            } catch (Exception e) {
                Log.e("PhonicButton", "Failed to load sound for name: keypress_sound01", e);
            }
        }
    }
}
