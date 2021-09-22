package com.example.knowledge_android.widget.fragment.pos_screen.fragment;

import android.content.Context;
import android.os.Bundle;
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

import com.arasthel.swissknife.SwissKnife;
import com.arasthel.swissknife.annotations.InjectView;
import com.arasthel.swissknife.annotations.OnClick;
import com.example.knowledge_android.OneApplication;
import com.example.knowledge_android.R;
import com.example.knowledge_android.msharedpreferences.MSharedPreferences;
import com.example.knowledge_android.widget.fragment.pos_screen.PosScreenMainActivity;

public class SignOnFragment extends Fragment {


    @InjectView(R.id.username)
    EditText userName;

    @InjectView(R.id.password)
    EditText password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, R.layout.sign_on, container);
    }

    /**
     * SwissKnife powered view creation.
     */
    public View createView(LayoutInflater inflater, int resource, ViewGroup root) {
        View view = inflater.inflate(resource, root, false);
        SwissKnife.inject(this, view);
        return view;
    }

    @OnClick(R.id.download_button)
    public void onDownloadMaster() {
        downloadMaster();
    }

    void downloadMaster() {
//        final AlertDialog.Builder builder = app.getOkCancelDialogBuilder(activity,
//                R.string.pref_title_download_master, R.string.download_master_confirmation)
//        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
//            try {
//                MasterDownloader masterDownloadTask = new MasterDownloader()
//                masterDownloadTask.setTransferProgressListener(new MasterDownloadProgressListener(this))
//                masterDownloadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, [] as String[])
//            } catch (Exception e) {
//                log.error('Download master failed.', e)
//            }
//        }
//        final AlertDialog dialog = builder.show()
//        TextView messageText = (TextView)dialog.findViewById(android.R.id.message)
//        messageText.setGravity(Gravity.CENTER)
    }

    void updateMasterVersionSummary() {
        MSharedPreferences settings = OneApplication.getInstance().getmSettings();
        String masterVersion = settings.getMasterVersion();
        /*findPreference(getString(R.string.pref_key_download_master))
                .setSummary(getString(R.string.master_version_info, masterVersion))*/
    }

    @OnClick(R.id.btn_signon)
    public void onClickSignOn() {
        hideSoftKeyboard();

        String cashierNumber = userName.getText().toString();
        String cashierPwd = password.getText().toString();

        //Log.i TAG, "Press SignOn: username=$cashierNumber, password==$cashierPwd"
        if (cashierNumber.isEmpty() || cashierPwd.isEmpty()) {
            return;
        }


        PosScreenMainActivity mainActivity = OneApplication.getInstance().getMainActivity();

        // For test ------------------------------------------------
        if (cashierNumber.equals("999")) {
            OneApplication.getInstance().getmSettings().setCurrentCashier("999");

            mainActivity.hideSignOnScreen();
            mainActivity.showInitialScreen(null);
            return;
        }


        mainActivity.hideSignOnScreen();
        mainActivity.showInitialScreen(null);

        // ---------------------------------------------------------

        //try {
        //    def dateCommand = new MposServerCommand(command: 'date', argument: new Date().format('yyyy-MM-dd HH'))
        //    def result = ServerCommand.runAsync(dateCommand).get()
        //    if (result != 0) {
        //        mainActivity.showMessageOnSnackbar R.string.系统日期与后台不一致
        //        return
        //    }
        //} catch (Exception e){
        //    eLog("", e);
        //}
//        Cashier cashier = DaoLocator.cashierDao.queryByCashierNo(cashierNumber)
//        if (cashier == null) { //用户不存在
//            mainActivity.showMessageOnSnackbar R.string.employee_not_exists
//            return
//        }
//        if (!cashierPwd.equals(cashier.getCashierPassword())) { //密码错误
//            mainActivity.showMessageOnSnackbar R.string.password_error
//            return
//        }
////
//        settings.currentCashier = cashier.getCashierNo() //设置当前用户

        //@pingping 斗牛士拿掉登录就开班的功能 2016-06-24
//        // Shift in
//        def shift = DaoLocator.shiftDao.shiftIn cashier
//        if (shift) {
//            settings.currentShift = shift.shiftNumber
//        } else {
//            mainActivity.showMessageOnSnackbar R.string.transaction_save_failed
//            return
//        }

    }


    public void hideSoftKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
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

    int getTitleStringId() {
        return R.string.staff_signon;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        PosScreenMainActivity activity = (PosScreenMainActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle(getTitleStringId());
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_button_apple_green);
    }

    @Override
    public void onStart() {
        super.onStart();
        setUserVisibleHint(true);

        // Reset state
        userName.setText("");
        password.setText("");
    }

}
