package com.aaa.politindex.figure_main_screen.tab_photo_figure;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaa.politindex.R;
import com.aaa.politindex.main_screen_for_auth_user.tabs_title_figure.IShowTitleListener;
import com.aaa.politindex.model.TitleEvent;
import com.bumptech.glide.Glide;

/**
 * Created by 11 on 31.01.2018.
 */

public class PhotoFigureFragment extends Fragment {


    public PhotoFigureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_figure_photo, container, false);

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

//    public void setListener(IShowTitleListener listener) {
//        mListener = listener;
//    }
//
//    public void setTitleEvent(TitleEvent titleEvent) {
//        mTitleEvent = titleEvent;
//    }
//
//    public void setFirstImage(boolean firstImage) {
//        mIsFirstImage = firstImage;
//    }
}

