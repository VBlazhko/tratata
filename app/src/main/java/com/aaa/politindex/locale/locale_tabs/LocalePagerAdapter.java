package com.aaa.politindex.locale.locale_tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by 11 on 16.02.2018.
 */
public class LocalePagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private CountryFragment tab1;
    private LocaleFragment tab2;

    public LocalePagerAdapter(FragmentManager fm, int NumOfTabs, LocaleFragment localeFragment, CountryFragment countryFragment) {
        super(fm);
        tab2=localeFragment;
        tab1=countryFragment;
        this.mNumOfTabs = NumOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return tab1;
            case 1:
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}
