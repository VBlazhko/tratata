package com.aaa.politindex.lounchscreen;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaa.politindex.R;


public class LounchScreenOneFragment extends Fragment {

    TextView next;
    ViewPager mPager;
    ImageView mImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lounch_screen_one, container, false);
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.w("log", "onHiddenChanged:1 ");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        Log.w("log", "onHiddenChanged:1 " + isVisibleToUser);

        mImageView.setBackgroundResource(isVisibleToUser ? R.drawable.circle_indicator_balck : R.drawable.circle_indicator_white);

        if (isVisibleToUser) {
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPager.setCurrentItem(1);
                }
            });
        }

    }



    public void setImageView(ImageView imageView) {
        mImageView = imageView;
    }

    public void setNext(TextView next, ViewPager pager) {
        this.next = next;
        mPager = pager;
    }

}
