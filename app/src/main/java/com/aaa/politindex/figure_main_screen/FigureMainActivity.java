package com.aaa.politindex.figure_main_screen;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import com.aaa.politindex.App;
import com.aaa.politindex.BaseActivity;
import com.aaa.politindex.Const;
import com.aaa.politindex.R;
import com.aaa.politindex.connection.Request;
import com.aaa.politindex.figure_main_screen.comment_list.CommentListAdapter;

import com.aaa.politindex.figure_main_screen.comment_list.RecyclerViewDisabler;
import com.aaa.politindex.figure_main_screen.tab_photo_figure.PagerAdapterPhoto;
import com.aaa.politindex.figure_main_screen.tab_photo_figure.PhotoFigureFragment;
import com.aaa.politindex.figure_main_screen.tab_photo_figure.PhotoTransformer;
import com.aaa.politindex.helper.Md5Helper;
import com.aaa.politindex.main_screen.tabs.IShowFigureListener;
import com.aaa.politindex.model.Figure;
import com.aaa.politindex.model.FigureData;
import com.aaa.politindex.model.FigureStatistics;
import com.aaa.politindex.model.ItemComment;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FigureMainActivity extends BaseActivity {

    private Map<String, String> headers;
    private ArrayList<Figure> mFigureArrayList;
    private String mIdEvent;
    private String mIdFigure;


    @BindView(R.id.pager)
    ViewPager mViewPager;
    @BindView(R.id.list_comment)
    RecyclerView mCommentList;
    @BindView(R.id.figure_name)
    TextView mFigureName;
    @BindView(R.id.pi_today)
    TextView mPiToday;

    @BindView(R.id.numb_dislike)
    TextView mNumbDislike;
    @BindView(R.id.numb_like)
    TextView mNumbLike;

    @BindView(R.id.btn_like)
    View mBtnLike;
    @BindView(R.id.btn_dislike)
    View mBtnDislike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_figure_main);
        mUnbinder = ButterKnife.bind(this);

        mFigureArrayList = this.getIntent().getParcelableArrayListExtra("figure_list");
        mIdEvent = this.getIntent().getStringExtra("idEvent");
        headers = new HashMap<>();
        headers.put("Token", App.getApp().getSharedPreferences(Const.TOKEN));
        headers.put("Authorization", App.getApp().getSharedPreferences(Const.ID_TOKEN) + "_" +
                Md5Helper.md5(App.getApp().getSharedPreferences(Const.ID_TOKEN) + ":" + App.getApp().getSharedPreferences(Const.ID_USER) + ":" + App.getApp().getSharedPreferences(Const.TOKEN)));

        PagerAdapterPhoto pagerAdapterPhoto = new PagerAdapterPhoto(getSupportFragmentManager());
        List<Fragment> fragments = new ArrayList<>();

        for (int i = 0; i < mFigureArrayList.size(); i++) {
            final PhotoFigureFragment fragment = new PhotoFigureFragment();
            fragment.setFigure(mFigureArrayList.get(i));
            fragment.setListener(new IShowFigureListener() {
                @Override
                public void onShowFigure(Figure figure) {
                    mFigureName.setText(figure.getFirstname() + " " + figure.getLastname());
                    mIdFigure = figure.getIdFigure().toString();
                    getFigureInfo(mIdFigure, mIdEvent);

                }
            });
            fragments.add(fragment);
        }


        int screenWidth = getWidth();
        float destiny = App.getApp().getDestiny();
        pagerAdapterPhoto.setFragments(fragments);
        mViewPager.setPadding((int) ((screenWidth / 2) - ((112 + 13) * destiny)), 0, 0, 0);
        mViewPager.setAdapter(pagerAdapterPhoto);
        mViewPager.setPageMargin((int) (0.8 * destiny));
        mViewPager.setClipToPadding(false); //
        mViewPager.setPageTransformer(false, new PhotoTransformer());


    }

    private void getFigureInfo(String idFigure, String idEvent) {
        Request.getInstance().getResult("v1/" + idEvent + "/" + idFigure + "/figure.api", headers, new Request.CallBack() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Gson gson = new Gson();
                JSONObject data = jsonObject.optJSONObject("data");
                FigureData figureData = gson.fromJson(data.toString(), FigureData.class);

                mPiToday.setText(figureData.getFigure().getRating() + "%");
                mNumbDislike.setText(figureData.getFigure().getDislike().toString());
                mNumbLike.setText(figureData.getFigure().getLike().toString());

                changeLikeBackground(figureData.getFigure().getIsLiked());

                ArrayList<ItemComment> comments = figureData.getItems();

                CommentListAdapter adapter = new CommentListAdapter(getApplicationContext(),comments);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                RecyclerView.OnItemTouchListener disabler = new RecyclerViewDisabler();

                mCommentList.addOnItemTouchListener(disabler);
                mCommentList.setAdapter(adapter);
                mCommentList.setLayoutManager(mLayoutManager);
                mCommentList.stopScroll();






            }
        });
    }

    private void getFigureLove(String idFigure, String idEvent, boolean isLiked) {
        Map<String, String> params = new HashMap<>();
        params.put("idEvent", idEvent);
        params.put("idFigure", idFigure);
        params.put("isLike", isLiked ? "1" : "0");


        Request.getInstance().getResultLove("v1/love.api", headers, params, new Request.CallBack() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Gson gson = new Gson();
                JSONObject data = jsonObject.optJSONObject("data");
                FigureStatistics figure = gson.fromJson(data.toString(), FigureStatistics.class);

                mPiToday.setText(figure.getRating() + "%");
                mNumbDislike.setText(figure.getDislike().toString());
                mNumbLike.setText(figure.getLike().toString());

                changeLikeBackground(figure.getIsLiked());
            }
        });
    }


    private void changeLikeBackground(int isLiked) {
        if (isLiked == 1) {
            mBtnDislike.setBackgroundResource(R.drawable.background_btn_like);
            mBtnLike.setBackgroundResource(R.drawable.background_btn_like_liked);
        }
        if (isLiked == 0) {
            mBtnLike.setBackgroundResource(R.drawable.background_btn_like);
            mBtnDislike.setBackgroundResource(R.drawable.background_btn_like_disliked);
        }
        if (isLiked == -1) {
            mBtnDislike.setBackgroundResource(R.drawable.background_btn_like);
            mBtnLike.setBackgroundResource(R.drawable.background_btn_like);
        }
    }

    private int getWidth() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int dpWidth = outMetrics.widthPixels;
        return dpWidth;
    }

    @OnClick(R.id.btn_like)
    protected void onClickLike() {
        getFigureLove(mIdFigure, mIdEvent, true);
    }

    @OnClick(R.id.btn_dislike)
    protected void onClickDislike() {
        getFigureLove(mIdFigure, mIdEvent, false);
    }


}
