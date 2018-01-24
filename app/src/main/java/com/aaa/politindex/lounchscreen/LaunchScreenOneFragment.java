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

import com.aaa.politindex.App;
import com.aaa.politindex.BaseFragment;
import com.aaa.politindex.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class LaunchScreenOneFragment extends BaseFragment {

    TextView next;
    ViewPager mPager;
    ImageView mImageView;

    @BindView(R.id.help1_text)
    TextView help1_text;

    @BindView(R.id.hello_title)
    TextView hello_title;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lounch_screen_one, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        help1_text.setText(App.getApp().getValue("help1_text"));
        hello_title.setText(App.getApp().getValue("hello_title"));
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (mImageView != null)
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
