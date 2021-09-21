package com.example.knowledge_android.widget.view;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.knowledge_android.R;

//**可播放声音的View

public class SoundButtonView extends View {
    private static final String SOUND_FILE = "beep";

    /** Sound pool to keep key sounds. */
    private static SoundPool mSoundPool;

    /** Normal key press sound id returning from SoundPool.load(). */
    private static int mKeyPressStandardSoundId;

    public SoundButtonView(Context context) {
        super(context);
        loadClickingSound();
    }

    public SoundButtonView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        loadClickingSound();
    }

    public static void doPlayClickingSound() {
        mSoundPool.play(mKeyPressStandardSoundId, 1f, 1f, 1, 0, 1f);
    }

    public void playClickingSound() {
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
                Log.e("TAG","Failed to load sound for name: key press_sound01", e);
            }
        }
    }
}
