package com.aaa.politindex.main_screen.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by 11 on 22.01.2018.
 */


public class PagerAdapter extends FragmentStatePagerAdapter {


    private List<Fragment> mFragments;


    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }


    public void setFragments(List<Fragment> fragments) {
        mFragments = fragments;
    }

    @Override
    public float getPageWidth(int position) {
        return 0.68f;
    }


}

