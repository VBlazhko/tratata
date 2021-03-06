package com.aaa.politindex.main_screen_for_auth_user;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaa.politindex.App;
import com.aaa.politindex.BaseActivity;
import com.aaa.politindex.Const;
import com.aaa.politindex.R;
import com.aaa.politindex.connection.Request;
import com.aaa.politindex.figure_main_screen.FigureMainActivity;
import com.aaa.politindex.helper.Md5Helper;
import com.aaa.politindex.locale.LocaleActivity;
import com.aaa.politindex.main_screen.tabs.IShowFigureListener;
import com.aaa.politindex.main_screen.tabs.FigureFragment;
import com.aaa.politindex.main_screen.tabs.OnClickFigureListener;
import com.aaa.politindex.main_screen.tabs.PagerAdapter;
import com.aaa.politindex.main_screen_for_auth_user.tabs_title_figure.IShowTitleListener;
import com.aaa.politindex.main_screen_for_auth_user.tabs_title_figure.TitleFigureFragment;
import com.aaa.politindex.model.Figure;
import com.aaa.politindex.model.TitleEvent;
import com.aaa.politindex.model.Today;
import com.aaa.politindex.search_by_figures.SearchByFiguresActivity;
import com.aaa.politindex.user_profile.UserProfileActivity;
import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainAuthUserActivity extends BaseActivity {

    private Map<String, String> headers;
    private ArrayList<Figure> figureList;
    private String idEvent;
    private String idFigure;

    @BindView(R.id.pagerTab)
    ViewPager mViewPagerTabTitle;

    @BindView(R.id.indicator)
    CirclePageIndicator mCirclePageIndicator;

    @BindView(R.id.surname)
    TextView lastname;

    @BindView(R.id.name)
    TextView firstname;

    @BindView(R.id.pager)
    ViewPager mViewPagerFigure;

    @BindView(R.id.pi_procent)
    TextView mPiProcent;

    @BindView(R.id.progress_line)
    ImageView mProgressLine;

    @BindView(R.id.btn_profile)
    ImageView mBtnProfile;

    @BindView(R.id.btn_search)
    TextView mBtnSearch;

    @BindView(R.id.PiToday)
    TextView mPiToday;
    @BindView(R.id.btn_change_language)
    ImageView mChangeLocale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_auth_user);

        mUnbinder = ButterKnife.bind(this);



        Request.getInstance().getResult("v1/ru/event.api", new Request.CallBack() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                List<TitleEvent> titleList = new ArrayList<>();
                Gson gson = new Gson();
                JSONObject data = jsonObject.optJSONObject("data");
                JSONArray items = data.optJSONArray("items");


                for (int i = 0; i < items.length(); i++) {
                    TitleEvent titleEvent = gson.fromJson(items.optJSONObject(i).toString(), TitleEvent.class);
                    titleList.add(titleEvent);
                    if (i == 0) {
                        idEvent = titleEvent.getIdEvent().toString();
                        getFigureRequest(idEvent);
                    }
                }
                List<Fragment> titleFragments = new ArrayList<>();
                for (int i = 0; i < titleList.size(); i++) {
                    TitleFigureFragment titleFigureFragment = new TitleFigureFragment();
                    titleFigureFragment.setTitleEvent(titleList.get(i));
                    titleFigureFragment.setListener(new IShowTitleListener() {
                        @Override
                        public void onShowTitle(TitleEvent titleEvent) {
                            idEvent = titleEvent.getIdEvent().toString();
                            getFigureRequest(idEvent);
                        }
                    });
                    if (i == 0) titleFigureFragment.setFirstImage(true);
                    titleFragments.add(titleFigureFragment);
                }

                PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
                pagerAdapter.setFragments(titleFragments);

                mViewPagerTabTitle.setClipToPadding(false); //
                mViewPagerTabTitle.setPageMargin((int) (20 * App.getApp().getDestiny()));
                mViewPagerTabTitle.setAdapter(pagerAdapter);
                mCirclePageIndicator.setViewPager(mViewPagerTabTitle);



                mPiToday.setText(App.getApp().getValue("lbl_pi_today"));
                mBtnSearch.setText(App.getApp().getValue("search_placeholder"));

            }
        });

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int dpWidth = outMetrics.widthPixels;
        App.getApp().setSharedPreferences("width", dpWidth + "");


        mChangeLocale.setBackgroundResource(App.getApp().getSharedPreferences(Const.LOCALE).toUpperCase().equals("EN") ? R.drawable.english_language_icon : R.drawable.icon_russia_3x);

    }

    private void getFigureRequest(final String idEvent) {
        Request.getInstance().getResult("v1/ru/" + idEvent + "/event.api", new Request.CallBack() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                figureList = new ArrayList<>();
                Gson gson = new Gson();
                JSONObject data = jsonObject.optJSONObject("data");
                JSONArray figures = data.optJSONArray("figures");

                for (int i = 0; i < figures.length(); i++) {
                    Figure figure = gson.fromJson(figures.optJSONObject(i).toString(), Figure.class);
                    figureList.add(figure);

                }

                final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
                final List<Fragment> fragments = new ArrayList<>();
                for (int i = 0; i < figureList.size(); i++) {
                    final FigureFragment fragment = new FigureFragment();
                    fragment.setFigure(figureList.get(i));
                    fragment.setListener(new IShowFigureListener() {
                        @Override
                        public void onShowFigure(Figure figure) {
                            firstname.setVisibility(View.VISIBLE);
                            lastname.setVisibility(View.VISIBLE);
                            mProgressLine.setVisibility(View.VISIBLE);
                            mPiProcent.setVisibility(View.VISIBLE);
                            mPiToday.setVisibility(View.VISIBLE);

                            firstname.setText(figure.getFirstname());
                            lastname.setText(figure.getLastname());
                            mPiProcent.setText(figure.getToday().getRating() + "%");

                            mProgressLine.getLayoutParams().width = 200 + (3 * (figure.getToday().getRating() + 1));
                            mProgressLine.requestLayout();
                        }
                    });

                    final int numb = i;
                    fragment.setOnClickFigureListener(new OnClickFigureListener() {
                        @Override
                        public void onClickFigure(Figure figure) {
                            Intent intent = new Intent(MainAuthUserActivity.this, FigureMainActivity.class);
                            intent.putParcelableArrayListExtra("figure_list", figureList);
                            intent.putExtra("figure_numb_in_list", numb + "");
                            intent.putExtra("idEvent", idEvent);
                            startActivity(intent);
                        }
                    });


                    if (i == 0) fragment.setFirstImage(true);
                    fragments.add(fragment);

                }
                FigureFragment figureFragment = new FigureFragment();
                figureFragment.setListener(new IShowFigureListener() {
                    @Override
                    public void onShowFigure(Figure figure) {
                        Log.w(TAG, "onShowFigure: ");
                        firstname.setVisibility(View.INVISIBLE);
                        lastname.setVisibility(View.INVISIBLE);
                        mProgressLine.setVisibility(View.INVISIBLE);
                        mPiProcent.setVisibility(View.INVISIBLE);
                        mPiToday.setVisibility(View.INVISIBLE);


                    }
                });
                figureFragment.setOnClickFigureListener(new OnClickFigureListener() {
                    @Override
                    public void onClickFigure(Figure figure) {

                    }
                });
                fragments.add(figureFragment);

                mViewPagerFigure.setClipToPadding(false); //
                mViewPagerFigure.setPageMargin((int) (10 * App.getApp().getDestiny()));

                pagerAdapter.setFragments(fragments);
                mViewPagerFigure.setAdapter(pagerAdapter);
            }
        });
    }


    @OnClick(R.id.btn_search)
    protected void clickSearch() {
        Intent intent = new Intent(this, SearchByFiguresActivity.class);

        ArrayList<Today> todayArrayList = new ArrayList<>();
        for (int i = 0; i < figureList.size(); i++) {
            todayArrayList.add(figureList.get(i).getToday());
        }
        intent.putParcelableArrayListExtra("figure", figureList);
        intent.putParcelableArrayListExtra("today", todayArrayList);
        intent.putExtra("idEvent", idEvent);

        startActivity(intent);
    }

    @OnClick(R.id.btn_profile)
    protected void clickProfile() {
        startActivity(new Intent(this, UserProfileActivity.class));
    }

    @OnClick(R.id.btn_change_language)
    protected void clickChangeLanguage() {
        Log.w(TAG, "clickChangeLanguage: " );
        Intent intent = new Intent(this, LocaleActivity.class);
        intent.putExtra("from","auth");
        startActivity(intent);
    }



}
