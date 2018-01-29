package com.aaa.politindex.main_screen_for_auth_user;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.aaa.politindex.App;
import com.aaa.politindex.BaseActivity;
import com.aaa.politindex.Const;
import com.aaa.politindex.R;
import com.aaa.politindex.connection.Request;
import com.aaa.politindex.helper.Md5Helper;
import com.aaa.politindex.main_screen.tabs.PagerAdapter;
import com.aaa.politindex.main_screen_for_auth_user.tabs_title_figure.TitleFigureFragment;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAuthUserActivity extends BaseActivity{

    @BindView(R.id.pagerTab)
    ViewPager mViewPagerTabTitle;

    @BindView(R.id.indicator)
    CirclePageIndicator mCirclePageIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_auth_user);

        mUnbinder = ButterKnife.bind(this);
        Map<String, String> headers = new HashMap<>();

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new TitleFigureFragment());
        fragments.add(new TitleFigureFragment());
        fragments.add(new TitleFigureFragment());
        fragments.add(new TitleFigureFragment());

        pagerAdapter.setFragments(fragments);
        mViewPagerTabTitle.setAdapter(pagerAdapter);

        mViewPagerTabTitle.setClipToPadding(false); //
        mViewPagerTabTitle.setPageMargin(80);
        mCirclePageIndicator.setViewPager(mViewPagerTabTitle);




        headers.put("token", App.getApp().getSharedPreferences(Const.TOKEN));


        headers.put("Authorization",App.getApp().getSharedPreferences(Const.ID_TOKEN) + "_" +
                Md5Helper.md5(App.getApp().getSharedPreferences(Const.ID_TOKEN)+":"+App.getApp().getSharedPreferences(Const.ID_USER) +":"+ App.getApp().getSharedPreferences(Const.TOKEN)));



        Log.w("log", "onCreate: Token= "+App.getApp().getSharedPreferences(Const.TOKEN));
        Log.w("log", "onCreate: Authorization= "+headers.get("Authorization"));

        Request.getInstance().getResultEvent("v1/ru/event.api", headers, new Request.CallBack() {
            @Override
            public void onResponse(JSONObject jsonObject) {

            }
        });
    }
}
