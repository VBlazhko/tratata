package com.aaa.politindex.main_screen_for_auth_user.tabs_title_figure;


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
import com.aaa.politindex.model.TitleEvent;
import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 */
public class TitleFigureFragment extends Fragment {

    private ImageView tabImageFigure;
    private TextView tabTextFigure;
    private boolean m_iAmVisible;
    private boolean mIsFirstImage;
    private TitleEvent mTitleEvent;
    private IShowTitleListener mListener;

    public TitleFigureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_title_figure, container, false);

        tabImageFigure = view.findViewById(R.id.tab_image_figure);
        tabTextFigure = view.findViewById(R.id.tab_text_figure);

        tabTextFigure.setText(mTitleEvent.getTitle());
        Glide.with(getContext()).load(Uri.parse(mTitleEvent.getIcon())).into(tabImageFigure);
        if(!mIsFirstImage) {
            tabTextFigure.setTextColor(getResources().getColor(R.color.light_gray));
            tabImageFigure.setColorFilter(getResources().getColor(R.color.light_gray));
        }else{
            tabTextFigure.setTextColor(getResources().getColor(R.color.grayDefault));
            tabImageFigure.setColorFilter(getResources().getColor(R.color.darkBlue));
        }
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        m_iAmVisible = isVisibleToUser;
        Activity activity = getActivity();
        if (activity != null) {
            if (tabImageFigure != null && tabTextFigure != null) {
                tabTextFigure.setTextColor(m_iAmVisible ? getResources().getColor(R.color.grayDefault) : getResources().getColor(R.color.light_gray));
                tabImageFigure.setColorFilter(m_iAmVisible ? getResources().getColor(R.color.darkBlue) : getResources().getColor(R.color.light_gray));
            }
            if(isVisibleToUser&&mTitleEvent!=null) mListener.onShowTitle(mTitleEvent);
        }


    }

    public void setListener(IShowTitleListener listener) {
        mListener = listener;
    }

    public void setTitleEvent(TitleEvent titleEvent) {
        mTitleEvent = titleEvent;
    }

    public void setFirstImage(boolean firstImage) {
        mIsFirstImage = firstImage;
    }
}
