package com.aaa.politindex;

import android.support.v4.app.Fragment;

import butterknife.Unbinder;

/**
 * Created by 11 on 21.01.2018.
 */

public class BaseFragment extends Fragment {
    public Unbinder mUnbinder;

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
