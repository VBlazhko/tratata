package com.aaa.politindex.locale;


import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aaa.politindex.App;
import com.aaa.politindex.Const;
import com.aaa.politindex.R;
import com.aaa.politindex.model.ItemTimeLine;
import com.aaa.politindex.model.Locale;
import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.aaa.politindex.BaseActivity.TAG;

/**
 * Created by 11 on 14.02.2018.
 */

public class LocaleRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Locale> mLocaleList;
    String key;
    IMyViewHolderClicks mMyViewHolderClicks;
    Context mContext;


    public LocaleRecyclerViewAdapter(Context context, ArrayList<Locale> listLocale, String key) {
        mContext = context;
        this.key=key;
        mLocaleList = listLocale;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new ItemStandingsStartViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_help, parent, false));
            default:
                return new ItemStandingsViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_locale, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (position) {
            case 100:
                ((ItemStandingsStartViewHolder) holder).bindView();
                break;
            default:
                ((ItemStandingsViewHolder) holder).bindView(mLocaleList.get(position));
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        // if (position == mLocaleList.size()) return 0;
        return 1;
    }


    public class ItemStandingsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_upper)
        protected TextView textUpper;
        @BindView(R.id.text_lower)
        protected TextView textLower;
        @BindView(R.id.checkmark)
        protected ImageView checkMark;

        public ItemStandingsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Locale locale) {
            textUpper.setText(locale.getLocaleUpper());
            textLower.setText(locale.getLocaleLower());

            if (locale.isLocale()) {
                checkMark.setVisibility(locale.getLocaleKey().toLowerCase().equals(key.toLowerCase()) ? View.VISIBLE : View.INVISIBLE);
            } else {
                checkMark.setVisibility(locale.getLocaleKey().toLowerCase().equals(key.toLowerCase()) ? View.VISIBLE : View.INVISIBLE);
            }
        }


    }




    public class ItemStandingsStartViewHolder extends RecyclerView.ViewHolder {
        public ItemStandingsStartViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView() {
        }
    }


    @Override
    public int getItemCount() {
        return mLocaleList.size();
    }

}


