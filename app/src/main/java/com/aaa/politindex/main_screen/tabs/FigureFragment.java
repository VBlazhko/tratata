package com.aaa.politindex.main_screen.tabs;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aaa.politindex.R;
import com.aaa.politindex.main_screen.IShowFigureListener;
import com.aaa.politindex.model.Figure;
import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 */
public class FigureFragment extends Fragment {

    private boolean m_iAmVisible;
    private boolean mIsFirstImage;
    private ImageView mImageView;
    private Figure mFigure;
    private IShowFigureListener mListener;
    private CardView mCardView;

    public FigureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_figure, container, false);
        mImageView = view.findViewById(R.id.image);
        mCardView= view.findViewById(R.id.cardView);
        if (mFigure != null) {
            Glide.with(getContext()).load(Uri.parse(mFigure.getAvatar())).into(mImageView);
        }else {
            mImageView.setBackgroundResource(R.drawable.background_gray_white);
            mCardView.setRadius(0);
        }

        if (mIsFirstImage) {
            mImageView.setAlpha(1.0f);
            mIsFirstImage = false;
        }

        return view;
    }

    public void setFigure(Figure figure) {
        mFigure = figure;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        m_iAmVisible = isVisibleToUser;
        if (mImageView != null) mImageView.setAlpha(m_iAmVisible ? 1.0f : 0.7f);
        if(isVisibleToUser) mListener.onShowFigure(mFigure);


    }


    public void setListener(IShowFigureListener listener) {
        mListener = listener;
    }

    public void setFirstImage(boolean firstImage) {
        mIsFirstImage = firstImage;
    }
}
