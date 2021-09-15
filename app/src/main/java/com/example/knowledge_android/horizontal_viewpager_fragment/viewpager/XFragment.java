package com.example.knowledge_android.horizontal_viewpager_fragment.viewpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.knowledge_android.R;

public class XFragment extends Fragment {

    private static final String LAYOUT_RES = "LAYOUT_RES";
    private int layoutRes;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param layoutRes layout resource.
     * @return A new instance of fragment MyFragment.
     */
    public static XFragment newInstance(int layoutRes) {
        XFragment fragment = new XFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES, layoutRes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            layoutRes = getArguments().getInt(LAYOUT_RES, R.layout.viewpager_fragment1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(layoutRes, container, false);
    }
}
