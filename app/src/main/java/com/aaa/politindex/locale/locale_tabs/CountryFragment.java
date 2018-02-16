package com.aaa.politindex.locale.locale_tabs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaa.politindex.App;
import com.aaa.politindex.BaseFragment;
import com.aaa.politindex.R;
import com.aaa.politindex.locale.LocaleRecyclerViewAdapter;
import com.aaa.politindex.model.Locale;
import com.aaa.politindex.time_line_info_activity.TimeLineAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;



public class CountryFragment extends BaseFragment {

    @BindView(R.id.recycler_country)
    RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_country, container, false);
        mUnbinder= ButterKnife.bind(this,view);

        ArrayList<Locale> localeArrayList = new ArrayList<>();
        for (int i=0;i<1;i++){
            Locale locale = new Locale();
            locale.setLocaleUpper("Russian Federation");
            locale.setLocaleLower("Российская Федерация");
            locale.setLocaleKey("RU");
            locale.setLocale(false);
            localeArrayList.add(locale);
        }

        LocaleRecyclerViewAdapter adapter = new LocaleRecyclerViewAdapter(getContext(), localeArrayList,"RU");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }
}
