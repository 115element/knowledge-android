package com.example.knowledge_android.widget.fragment.pos_screen.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class MessageDialog extends DialogFragment implements DialogInterface.OnClickListener{

    public static final int TYPE_WARNING = 0;
    public static final int TYPE_MASTER_DOWNLOAD = 1;
    public static final int TYPE_ORDER_UPLOAD = 2;
    public static final int TYPE_INVENTORY_UPLOAD = 3;
    public static final int TYPE_SHELF_UPLOAD = 4;
    public static final int TYPE_SALE_PRICE_CHECK = 5;

    private int mDialogType;
    private String mDialogTitle;
    private String mDialogMessage;
    private DialogInterface.OnClickListener mButtonListener;

    public static MessageDialog newInstance(int type, String title, String message) {
        MessageDialog fragment = new MessageDialog();
        Bundle params = new Bundle();
        params.putInt("dialog_type", type);
        params.putString("dialog_title", title);
        params.putString("dialog_message", message);
        fragment.setArguments(params);
        return fragment;
    }

    public void setButtonListener(DialogInterface.OnClickListener lsnr) {
        mButtonListener = lsnr;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialogType = getArguments().getInt("dialog_type");
        mDialogTitle = getArguments().getString("dialog_title");
        mDialogMessage = getArguments().getString("dialog_message");
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() instanceof AlertDialog) {
            TextView messageText = (TextView)getDialog().findViewById(android.R.id.message);
            messageText.setGravity(Gravity.CENTER);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog;
        switch (mDialogType) {
            case TYPE_MASTER_DOWNLOAD:
            case TYPE_INVENTORY_UPLOAD:
            case TYPE_SHELF_UPLOAD:
            case TYPE_SALE_PRICE_CHECK:
            case TYPE_ORDER_UPLOAD:
                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle(mDialogTitle);
                progressDialog.setMessage(Html.fromHtml(mDialogMessage));
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setMax(100);
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                dialog = progressDialog;
                break;

            case TYPE_WARNING:
            default:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Holo_Dialog);
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setTitle(mDialogTitle);
                builder.setMessage(Html.fromHtml(mDialogMessage));
                if (mButtonListener != null)
                    builder.setPositiveButton(android.R.string.ok, mButtonListener);
                else
                    builder.setPositiveButton(android.R.string.ok, this);
                builder.setCancelable(false);
                dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
        }
        return dialog;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dismiss();
    }
}
