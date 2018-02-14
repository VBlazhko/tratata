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

import com.aaa.politindex.BaseFragment;
import com.aaa.politindex.R;
import com.aaa.politindex.main_screen.tabs.IShowFigureListener;
import com.aaa.politindex.main_screen_for_auth_user.tabs_title_figure.IShowTitleListener;
import com.aaa.politindex.model.Figure;
import com.aaa.politindex.model.TitleEvent;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 11 on 31.01.2018.
 */

public class PhotoFigureFragment extends BaseFragment {
    private Figure mFigure;
    private IShowFigureListener mListener;

    @BindView(R.id.photo_figure)
    ImageView mImageFigure;

    public PhotoFigureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_figure_photo, container, false);
        mUnbinder= ButterKnife.bind(this,view);

        if (mFigure != null) Glide.with(getContext()).load(Uri.parse(mFigure.getAvatar())).into(mImageFigure);
        else mImageFigure.setImageResource(R.drawable.rocket);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) mListener.onShowFigure(mFigure);
        if(mFigure==null)mListener.onShowFigure(null);
    }

    public void setFigure(Figure figure) {
        mFigure = figure;
    }

    public void setListener(IShowFigureListener listener) {
        mListener = listener;
    }

}

