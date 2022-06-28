package com.mobidal.pharmacynamoune.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mobidal.pharmacynamoune.R;

import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    private Context mContext;

    public HomeFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(mContext)
                .inflate(R.layout.fragment_home, container, false);

        // Bind HomeFragment views
        ButterKnife.bind(this, rootView);

        return rootView;
    }
}
