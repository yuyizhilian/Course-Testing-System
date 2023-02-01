package com.example.graduatedesign.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.graduatedesign.fragment.AlreadyIssueFragment;
import com.example.graduatedesign.fragment.WaitIssueFragment;

public class LessenTestFragmentAdapter extends FragmentPagerAdapter {
    public LessenTestFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new AlreadyIssueFragment();
                break;
            case 1:
                fragment = new WaitIssueFragment();
                break;
            default:
                break;
        }
        assert fragment != null;
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
