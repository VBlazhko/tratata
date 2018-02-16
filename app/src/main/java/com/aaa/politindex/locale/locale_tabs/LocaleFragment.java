package com.aaa.politindex.locale.locale_tabs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaa.politindex.App;
import com.aaa.politindex.BaseFragment;
import com.aaa.politindex.Const;
import com.aaa.politindex.R;
import com.aaa.politindex.locale.IMyViewHolderClicks;
import com.aaa.politindex.locale.LocaleRecyclerViewAdapter;
import com.aaa.politindex.locale.RecyclerItemClickListener;
import com.aaa.politindex.model.Locale;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocaleFragment extends BaseFragment {

    private String localeFromFragment;
    IMyViewHolderClicks mListener;

    @BindView(R.id.recycler_locale)
    RecyclerView mRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_locale, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        localeFromFragment=App.getApp().getSharedPreferences(Const.LOCALE);

        final ArrayList<Locale> localeArrayList = new ArrayList<>();

        final Locale locale = new Locale();
        locale.setLocaleUpper("English");
        locale.setLocaleLower("USA/BRITISH");
        locale.setLocaleKey("EN");
        locale.setLocale(true);
        localeArrayList.add(locale);

        Locale locale1 = new Locale();
        locale1.setLocaleUpper("Russian");
        locale1.setLocaleLower("Русский");
        locale1.setLocaleKey("RU");
        locale1.setLocale(true);
        localeArrayList.add(locale1);

        LocaleRecyclerViewAdapter adapter = new LocaleRecyclerViewAdapter(getContext(), localeArrayList,localeFromFragment);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Log.w("log", "onItemClick: "+position  );

                        localeFromFragment=localeArrayList.get(position).getLocaleKey();

                        LocaleRecyclerViewAdapter adapter = new LocaleRecyclerViewAdapter(getContext(), localeArrayList, localeFromFragment);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                        mRecyclerView.setAdapter(adapter);
                        mRecyclerView.setLayoutManager(mLayoutManager);

                        mListener.onClickItem(localeFromFragment);
                    }
                })
        );

        return view;
    }

    public String getLocaleFromFragment() {
        return localeFromFragment;
    }

    public void setListener(IMyViewHolderClicks listener) {
        mListener = listener;
    }
}
