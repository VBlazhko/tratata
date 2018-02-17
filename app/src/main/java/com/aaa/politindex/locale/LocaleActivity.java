package com.aaa.politindex.locale;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.aaa.politindex.App;
import com.aaa.politindex.BaseActivity;
import com.aaa.politindex.Const;
import com.aaa.politindex.R;
import com.aaa.politindex.connection.Request;
import com.aaa.politindex.locale.locale_tabs.CountryFragment;
import com.aaa.politindex.locale.locale_tabs.CustomViewPager;
import com.aaa.politindex.locale.locale_tabs.LocaleFragment;
import com.aaa.politindex.locale.locale_tabs.LocalePagerAdapter;
import com.aaa.politindex.main_screen.MainActivity;
import com.aaa.politindex.main_screen_for_auth_user.MainAuthUserActivity;
import com.aaa.politindex.model.Item;
import com.aaa.politindex.search_by_figures.SearchByFiguresActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocaleActivity extends BaseActivity {

    private String localeCurent;
    private String changedLocale;
    private String fromActivity;


    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.pager)
    CustomViewPager mViewPager;
    @BindView(R.id.apply_active)
    TextView mApplyActive;
    @BindView(R.id.apply)
    TextView mApply;
    @BindView(R.id.back)
    TextView mBack;
    @BindView(R.id.title)
    TextView mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locale);

        Intent intent = getIntent();
        fromActivity = intent.getStringExtra("from");

        mUnbinder= ButterKnife.bind(this);

        localeCurent= App.getApp().getSharedPreferences(Const.LOCALE);

        mTabLayout.addTab(mTabLayout.newTab().setText("Выберите страну"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Выберите язык"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        CountryFragment countryFragment=new CountryFragment();
        LocaleFragment localeFragment=new LocaleFragment();
        localeFragment.setListener(new IMyViewHolderClicks() {
            @Override
            public void onClickItem(String locale) {
                if(!localeCurent.toLowerCase().equals(locale.toLowerCase())){
                    mApplyActive.setVisibility(View.VISIBLE);
                    mApply.setVisibility(View.INVISIBLE);
                    changedLocale=locale;

                }else {
                    mApplyActive.setVisibility(View.INVISIBLE);
                    mApply.setVisibility(View.VISIBLE);
                    changedLocale="";
                }
            }
        });




        final LocalePagerAdapter adapter = new LocalePagerAdapter
                (getSupportFragmentManager(), mTabLayout.getTabCount(),localeFragment,countryFragment);
        mViewPager.setAdapter(adapter);

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mBack.setText(App.getApp().getValue("back_button"));
        mTitle.setText(App.getApp().getValue("title_location"));
        mApply.setText(App.getApp().getValue("apply_button"));
        mApplyActive.setText(App.getApp().getValue("apply_button"));
    }

    @OnClick(R.id.apply_active)
    protected void onApplyClick(){
        App.getApp().setSharedPreferences(Const.LOCALE,changedLocale);
        Request.getInstance().getResult("v1/locale.api", new Request.CallBack() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                JSONObject data = jsonObject.optJSONObject("data");
                JSONArray items = data.optJSONArray("items");
                Gson gson = new Gson();
                List<Item> list = new ArrayList<>();
                for (int i = 0; i < items.length(); i++) {
                    Item item = gson.fromJson(items.optJSONObject(i).toString(), Item.class);
                    list.add(item);

                }
                App.getApp().setItemList(list);

                if(fromActivity.equals("main")){
                    startActivity(new Intent(LocaleActivity.this, MainActivity.class));
                    finish();
                }
                if(fromActivity.equals("auth")){
                    startActivity(new Intent(LocaleActivity.this, MainAuthUserActivity.class));
                    finish();
                }
            }});



    }

    @OnClick({R.id.back, R.id.icon_back})
    protected void clickBack() {
        onBackPressed();
    }
}
