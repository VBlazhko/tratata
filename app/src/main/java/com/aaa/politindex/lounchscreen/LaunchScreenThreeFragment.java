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


public class LaunchScreenThreeFragment extends BaseFragment {

    TextView next;
    FirstDialogFragment.ICloseListener mCloseListener;
    ViewPager mPager;

    @BindView(R.id.help3_text)
    TextView help3_text;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lounch_screen_three, container, false);
        mUnbinder= ButterKnife.bind(this,view);
        help3_text.setText(App.getApp().getValue("help3_text"));
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        mImageView.setBackgroundResource(isVisibleToUser ? R.drawable.circle_indicator_balck : R.drawable.circle_indicator_white);

        if (isVisibleToUser) {
            next.setText("Готово");
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCloseListener.onClose();

                }
            });
        } else {
            next.setText("Далее");
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPager.setCurrentItem(2);

                }
            });
        }
    }

    ImageView mImageView;

    public void setImageView(ImageView imageView) {
        mImageView = imageView;
    }

    public void setNext(TextView next, ViewPager pager, FirstDialogFragment.ICloseListener close) {
        this.next = next;
        mCloseListener = close;
        mPager = pager;
    }
}
