package com.aaa.politindex;

import android.support.v7.app.AppCompatActivity;

import butterknife.Unbinder;

/**
 * Created by 11 on 27.01.2018.
 */

public class BaseActivity extends AppCompatActivity {
    protected Unbinder mUnbinder;

    public static final String TAG="log";

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mUnbinder!=null){
            mUnbinder.unbind();
        }
    }
}
