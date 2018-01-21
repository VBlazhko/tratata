package com.aaa.politindex.lounchscreen;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aaa.politindex.R;


public class LaunchScreenTwoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lounch_screen_two, container, false);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mImageView.setBackgroundResource(isVisibleToUser ? R.drawable.circle_indicator_balck : R.drawable.circle_indicator_white);
    }

    ImageView mImageView;

    public void setImageView(ImageView imageView) {
        mImageView = imageView;
    }
}
