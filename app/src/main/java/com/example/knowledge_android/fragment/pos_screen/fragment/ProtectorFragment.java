package com.example.knowledge_android.fragment.pos_screen.fragment;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.arasthel.swissknife.SwissKnife;
import com.arasthel.swissknife.annotations.InjectView;
import com.arasthel.swissknife.annotations.OnClick;
import com.example.knowledge_android.OneApplication;
import com.example.knowledge_android.R;
import com.example.knowledge_android.fragment.pos_screen.PosScreenMainActivity;
import com.example.knowledge_android.msharedpreferences.MSharedPreferences;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ProtectorFragment extends Fragment {

    @InjectView(R.id.android_id)
    EditText androidIdEditText;

    @InjectView(R.id.license_code)
    EditText licenseCodeEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    static String computeLicenseCode(String id) {
        MessageDigest md5Maker = null;
        try {
            md5Maker = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5Maker.update(id.getBytes());
        byte[] md5 = md5Maker.digest();

        MessageDigest shaMaker = null;
        try {
            shaMaker = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        shaMaker.update(id.getBytes());
        byte[] sha = shaMaker.digest();

        int[] r = new int[Math.max(md5.length, sha.length)];
        int i;
        for (i = 0; i < r.length; i++) {
            if (i < md5.length)
                r[i] += md5[i];
            if (i < sha.length)
                r[i] += sha[i];
        }
        for (i = 0; i < r.length / 2; i++) {
            r[i] += r[r.length - 1 - i];
            r[i] &= 0xff;
            int m = r[i] % 0x10;
            if (m > 9)
                r[i] -= 6;
            if (r[i] >= 0xa0)
                r[i] -= 0x60;
        }
        String retString = "";
        for (i = 0; i < r.length / 2; i++) {
            retString += Integer.toHexString(r[i] & 0xff);
        }
        return retString;
    }

    public static boolean hasLegalLicense(Context context) {
        String androidId = getAndroidId(context);
        String legalLicenseCode = computeLicenseCode(androidId);
        return OneApplication.getInstance().getmSettings().getLicenseCode().equals(legalLicenseCode);
    }

    @OnClick(R.id.btn_ok)
    void onClickOK() {
        String userInputLicenseCode = licenseCodeEditText.getText().toString().trim();
        if (userInputLicenseCode.isEmpty()) {
            return;
        }

        PosScreenMainActivity mainActivity = OneApplication.getInstance().getMainActivity();
        MSharedPreferences settings = OneApplication.getInstance().getmSettings();

        String androidId = getAndroidId(getActivity().getBaseContext());
        String legalLicenseCode = computeLicenseCode(androidId);
        if (!userInputLicenseCode.equals(legalLicenseCode)) {
            mainActivity.showMessageOnSnackbar(R.string.please_input_legal_license);
            return;
        }

        // Memorize the legal license code  存储到XML中
        settings.setLicenseCode(userInputLicenseCode);

        hideSoftKeyboard();

        mainActivity.hideProtectorScreen();

        mainActivity.showInitialScreen(null);
    }


    public void hideSoftKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewGroup = inflater.inflate(R.layout.license_code_input, container, false);
        SwissKnife.inject(this,viewGroup);

        // Show Android ID
        androidIdEditText.setText(getAndroidId(getActivity().getBaseContext()));
        androidIdEditText.setFocusable(false);
        return viewGroup;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_exit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_exit:
                System.exit(0);
                break;
        }
        return true;
    }

    //@Override
    public int getTitleStringId() {
        return R.string.license_check;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        FragmentActivity activity1 = getActivity();

        PosScreenMainActivity activity = (PosScreenMainActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle(getTitleStringId());
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_category_10);
    }

    @Override
    public void onStart() {
        super.onStart();
        setUserVisibleHint(true);

        // Reset state
        licenseCodeEditText.setText("");
    }
}
