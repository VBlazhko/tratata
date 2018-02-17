package com.aaa.politindex.figure_main_screen;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aaa.politindex.App;
import com.aaa.politindex.BaseActivity;
import com.aaa.politindex.Const;
import com.aaa.politindex.R;
import com.aaa.politindex.authentication.AuthActivity;
import com.aaa.politindex.biography_activity.BiographyActivity;
import com.aaa.politindex.connection.Request;
import com.aaa.politindex.date_information_activity.DateInfoActivity;
import com.aaa.politindex.figure_main_screen.comment_list.CommentListAdapter;

import com.aaa.politindex.figure_main_screen.comment_list.RecyclerViewDisabler;
import com.aaa.politindex.figure_main_screen.tab_photo_figure.DeactivatableViewPager;
import com.aaa.politindex.figure_main_screen.tab_photo_figure.PagerAdapterPhoto;
import com.aaa.politindex.figure_main_screen.tab_photo_figure.PhotoFigureFragment;
import com.aaa.politindex.figure_main_screen.tab_photo_figure.PhotoTransformer;
import com.aaa.politindex.helper.Md5Helper;
import com.aaa.politindex.main_screen.MainActivity;
import com.aaa.politindex.main_screen.tabs.IShowFigureListener;
import com.aaa.politindex.main_screen_for_auth_user.MainAuthUserActivity;
import com.aaa.politindex.model.Figure;
import com.aaa.politindex.model.FigureData;
import com.aaa.politindex.model.FigureStatistics;
import com.aaa.politindex.model.ItemComment;
import com.aaa.politindex.time_line_info_activity.TimeLineActivity;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnFocusChange;
import butterknife.OnTouch;

public class FigureMainActivity extends BaseActivity {

    private Map<String, String> headers;
    private ArrayList<Figure> mFigureArrayList;
    private String mIdEvent;
    private String mIdFigure;
    private String mIdInList;
    private String mPhotoFigure;
    private LinearLayout myLayout;


    @BindView(R.id.pager)
    DeactivatableViewPager mViewPager;
    @BindView(R.id.pi_today_text)
    TextView mTextPi;

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
    @BindView(R.id.back)
    TextView mBack;
    @BindView(R.id.btn_like)
    View mBtnLike;
    @BindView(R.id.btn_dislike)
    View mBtnDislike;
    @BindView(R.id.edit_comment)
    EditText mEditText;
    @BindView(R.id.scroll)
    NestedScrollView mNestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_figure_main);
        mUnbinder = ButterKnife.bind(this);
        myLayout = (LinearLayout) findViewById(R.id.lay);
        mFigureArrayList = this.getIntent().getParcelableArrayListExtra("figure_list");

        mIdEvent = this.getIntent().getStringExtra("idEvent");
        mIdInList = this.getIntent().getStringExtra("figure_numb_in_list");


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
                    mPhotoFigure=figure.getAvatar();
                    getFigureInfo(mIdFigure, mIdEvent);

                }
            });
            fragments.add(fragment);
        }

        PhotoFigureFragment fragmentEmpty = new PhotoFigureFragment();
        fragmentEmpty.setListener(new IShowFigureListener() {
            @Override
            public void onShowFigure(Figure figure) {

            }
        });
        fragments.add(fragmentEmpty);

        float destiny = App.getApp().getDestiny();
        pagerAdapterPhoto.setFragments(fragments);
        mViewPager.setPadding((int) ((getWidth() / 2) - ((112 + 13) * destiny)), 0, 0, 0);
        mViewPager.setAdapter(pagerAdapterPhoto);
        mViewPager.setPageMargin((int) (0.8 * destiny));
        mViewPager.setClipToPadding(false); //
        mViewPager.setPageTransformer(false, new PhotoTransformer());
        mViewPager.setCurrentItem(Integer.parseInt(mIdInList));
        mViewPager.setMaxCount(mFigureArrayList.size() - 1);

        mBack.setText(App.getApp().getValue("back_button"));
        mTextPi.setText(App.getApp().getValue("lbl_pi_today"));

    }

    private void getFigureInfo(String idFigure, String idEvent) {
        Request.getInstance().getResult("v1/" + idEvent + "/" + idFigure + "/figure.api", new Request.CallBack() {
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

                CommentListAdapter adapter = new CommentListAdapter(getApplicationContext(), comments);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                mLayoutManager.setAutoMeasureEnabled(true);
                mCommentList.setAdapter(adapter);

                mCommentList.setLayoutManager(mLayoutManager);
                mCommentList.setNestedScrollingEnabled(false);


            }
        });
    }

    private void getFigureLove(String idFigure, String idEvent, boolean isLiked) {
        Map<String, String> params = new HashMap<>();
        params.put("idEvent", idEvent);
        params.put("idFigure", idFigure);
        params.put("isLike", isLiked ? "1" : "0");

        Request.getInstance().getResultLove("v1/love.api", params, new Request.CallBack() {
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


    @OnClick(R.id.btn_send_comment)
    protected void onClickSendComment() {


        Map<String, String> params = new HashMap<>();
        if (mEditText.getText().toString() != "") {
            params.put("text", mEditText.getText().toString());
            mEditText.getText().clear();

            Request.getInstance().getResultLove("v1/" + mIdEvent + "/" + mIdFigure + "/figure.api", params, new Request.CallBack() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                }
            });

            try {
                Thread.sleep(1000);
                getFigureInfo(mIdFigure, mIdEvent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(getBaseContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(myLayout.getWindowToken(), 0);
        mEditText.clearFocus();
    }

    @OnFocusChange(R.id.edit_comment)
    protected void onClickEdit() {
        float destiny = App.getApp().getDestiny();
        mNestedScrollView.scrollTo(0, (int) (320 * destiny));
    }

    @OnClick({R.id.back, R.id.icon_back})
    protected void clickBack() {
        onBackPressed();
    }

    @OnClick(R.id.btn_like)
    protected void onClickLike() {
        getFigureLove(mIdFigure, mIdEvent, true);
    }

    @OnClick(R.id.btn_dislike)
    protected void onClickDislike() {
        getFigureLove(mIdFigure, mIdEvent, false);
    }

    @OnClick(R.id.biography)
    protected void onClikBiography() {
        Intent intent = new Intent(FigureMainActivity.this, BiographyActivity.class);
        intent.putExtra("idFigure", mIdFigure);
        intent.putExtra("figureName", mFigureName.getText());
        intent.putExtra("figurePhoto", mPhotoFigure);
        startActivity(intent);
    }

    @OnClick(R.id.time_line)
    protected void onTimeLineClick(){
        Intent intent = new Intent(FigureMainActivity.this, DateInfoActivity.class);
        intent.putExtra("idFigure", mIdFigure);
        intent.putExtra("idEvent", mIdEvent);
        intent.putExtra("figureName", mFigureName.getText());
        startActivity(intent);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mEditText.clearFocus();
        Log.w(TAG, "onBackPressed: " );
    }



}
