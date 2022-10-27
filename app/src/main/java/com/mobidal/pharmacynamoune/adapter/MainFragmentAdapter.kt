package com.mobidal.pharmacynamoune.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentAdapter extends FragmentStateAdapter {

    List<Fragment> mFragmentList = new ArrayList<>();
    List<String> mTitleList = new ArrayList<>();
    List<Integer> mIconResourceIdList = new ArrayList<>();

    public MainFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }

    public int getIconResourceId(int position) {
        return mIconResourceIdList.get(position);
    }

    public void addFragment(Fragment fragment, String title, int iconResourceId) {
        mFragmentList.add(fragment);
        mTitleList.add(title);
        mIconResourceIdList.add(iconResourceId);
    }

    public String getName(int position) {
        return mTitleList.get(position);
    }
}
