package com.aaa.politindex.lounchscreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by 11 on 20.01.2018.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    int mNumOfTabs;

    LaunchScreenOneFragment mLaunchScreenOneFragment;
    LaunchScreenTwoFragment mLaunchScreenTwoFragment;
    LaunchScreenThreeFragment mLaunchScreenThreeFragment;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return mLaunchScreenOneFragment;

            case 1:
                return mLaunchScreenTwoFragment;

            case 2:
                return mLaunchScreenThreeFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }


    public void setLaunchScreenOneFragment(LaunchScreenOneFragment launchScreenOneFragment) {
        mLaunchScreenOneFragment = launchScreenOneFragment;
    }

    public void setLaunchScreenTwoFragment(LaunchScreenTwoFragment launchScreenTwoFragment) {
        mLaunchScreenTwoFragment = launchScreenTwoFragment;
    }

    public void setLaunchScreenThreeFragment(LaunchScreenThreeFragment launchScreenThreeFragment) {
        mLaunchScreenThreeFragment = launchScreenThreeFragment;
    }
}
