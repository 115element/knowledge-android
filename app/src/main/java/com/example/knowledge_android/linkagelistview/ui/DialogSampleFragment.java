package com.example.knowledge_android.linkagelistview.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.knowledge_android.R;
import com.example.knowledge_android.databinding.FragmentDialogBinding;
import com.example.linkage_recyclerview.LinkageRecyclerView;
import com.example.linkage_recyclerview.bean.DefaultGroupedItem;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class DialogSampleFragment extends Fragment {

    private FragmentDialogBinding mBinding;
    private static float DIALOG_HEIGHT = 400;
    private AlertDialog mDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        mBinding = FragmentDialogBinding.bind(view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.btnPreview.setOnClickListener(v -> {
            View view2 = View.inflate(getContext(), R.layout.layout_linkage, null);
            LinkageRecyclerView linkage = view2.findViewById(R.id.linkage);
            initLinkageDatas(linkage);
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
            mDialog = builder.setView(linkage).show();
            linkage.setLayoutHeight(DIALOG_HEIGHT);
        });
    }

    private void initLinkageDatas(LinkageRecyclerView linkage) {
        Gson gson = new Gson();
        List<DefaultGroupedItem> items = gson.fromJson(getString(R.string.operators_json),
                new TypeToken<List<DefaultGroupedItem>>() {
                }.getType());

        linkage.init(items);
        linkage.setScrollSmoothly(false);
        linkage.setDefaultOnItemBindListener(
                (primaryClickView, title, position) -> {
                    Snackbar.make(primaryClickView, title, Snackbar.LENGTH_SHORT).show();
                },
                (primaryHolder, title, position) -> {
                    //TODO
                },
                (secondaryHolder, item, position) -> {
                    secondaryHolder.getView(R.id.level_2_item).setOnClickListener(v -> {
                        if (mDialog != null && mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                    });
                },
                (headerHolder, item, position) -> {
                    //TODO
                },
                (footerHolder, item, position) -> {
                    //TODO
                }
        );
    }
}
