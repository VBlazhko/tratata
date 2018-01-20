package com.aaa.politindex.lounchscreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ImageView;

import com.aaa.politindex.R;

/**
 * Created by 11 on 20.01.2018.
 */

public class PagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;

    ImageView firstPoint;


    LounchScreenOneFragment mLounchScreenOneFragment;
    LounchScreenTwoFragment mLounchScreenTwoFragment;
    LounchScreenThreeFragment mLounchScreenThreeFragment;

    public void setFirstPoint(ImageView firstPoint) {
        this.firstPoint = firstPoint;
    }



    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return mLounchScreenOneFragment;

            case 1:
                return mLounchScreenTwoFragment;

            case 2:
                return mLounchScreenThreeFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }


    public void setLounchScreenOneFragment(LounchScreenOneFragment lounchScreenOneFragment) {
        mLounchScreenOneFragment = lounchScreenOneFragment;
    }

    public void setLounchScreenTwoFragment(LounchScreenTwoFragment lounchScreenTwoFragment) {
        mLounchScreenTwoFragment = lounchScreenTwoFragment;
    }

    public void setLounchScreenThreeFragment(LounchScreenThreeFragment lounchScreenThreeFragment) {
        mLounchScreenThreeFragment = lounchScreenThreeFragment;
    }
}
