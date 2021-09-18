package com.example.knowledge_android.linkagelistview.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.knowledge_android.R;
import com.example.knowledge_android.databinding.FragmentBottomsheetBinding;
import com.example.linkage_recyclerview.LinkageRecyclerView;
import com.example.linkage_recyclerview.bean.DefaultGroupedItem;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.util.List;


public class BottomSheetSampleFragment extends Fragment {

    private FragmentBottomsheetBinding mBinding;
    private BottomSheetDialog mSheetDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottomsheet, container, false);
        mBinding = FragmentBottomsheetBinding.bind(view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.btnPreview.setOnClickListener(v -> {
            mSheetDialog = new BottomSheetDialog(getContext());
            mSheetDialog.setContentView(R.layout.layout_linkage);
            LinkageRecyclerView linkage = mSheetDialog.findViewById(R.id.linkage);
            initLinkageDatas(linkage);
            mSheetDialog.show();
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
                        if (mSheetDialog != null && mSheetDialog.isShowing()) {
                            mSheetDialog.dismiss();
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
