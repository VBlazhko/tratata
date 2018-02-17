package com.aaa.politindex.time_line_info_activity;

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
import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.aaa.politindex.BaseActivity.TAG;

/**
 * Created by 11 on 14.02.2018.
 */

public class TimeLineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ItemTimeLine> mTimelineList;
    ArrayList<Integer> hourList = new ArrayList<>();
    String hour = null;
    String titleDate;

    Context mContext;


    public TimeLineAdapter(Context context, ArrayList<ItemTimeLine> listItem) {
        mContext = context;
        mTimelineList = listItem;

        for (int i = 0; i < listItem.size(); i++) {
            String buffHour = getHour(listItem.get(i).getFulldate().split("G")[0]);
            if (!buffHour.equals(hour)) {
                hourList.add(Integer.parseInt(buffHour) + 1);
                hour = buffHour;
                Log.w(TAG, "TimeLineAdapter:  add new container "+hour );
            }
        }

        titleDate = getDate(listItem.get(0).getFulldate().split("G")[0]);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new TimeLineAdapter.ItemStandingsStartViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_start_time_line, parent, false));
            default:
                return new TimeLineAdapter.ItemStandingsViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_main_timeline, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (position) {
            case 0:
                ((TimeLineAdapter.ItemStandingsStartViewHolder) holder).bindView(titleDate);
                break;
            default:
                ((TimeLineAdapter.ItemStandingsViewHolder) holder).bindView(hourList.get(position - 1) - 1, mTimelineList);
                Log.w(TAG, "onBindViewHolder: "+hourList.get(position - 1) );
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return 0;
        else return 1;
    }


    public class ItemStandingsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.container)
        protected LinearLayout container;
        @BindView(R.id.time)
        protected TextView time;

        public ItemStandingsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Integer hour, ArrayList<ItemTimeLine> timelineList) {
            time.setText((hour + 1) + ":00");

            for (int i = 0; i < timelineList.size(); i++) {
                if (Integer.parseInt(getHour(timelineList.get(i).getFulldate().split("G")[0])) == hour) {
                    TextView nameT1, nameT2, text, dateT1, dateT2;
                    ImageView photoT1, photoT2, likeImg;
                    LinearLayout mLinearLayoutType1, mLinearLayoutType2, like;

                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.item_child_timeline, null);

                    nameT1 = (TextView) view.findViewById(R.id.user_name_t1);
                    nameT2 = (TextView) view.findViewById(R.id.name_user_t2);
                    text = (TextView) view.findViewById(R.id.text);
                    dateT1 = (TextView) view.findViewById(R.id.date_t1);
                    dateT2 = (TextView) view.findViewById(R.id.date_t2);
                    photoT1 = (ImageView) view.findViewById(R.id.photo_user_t1);
                    likeImg = (ImageView) view.findViewById(R.id.like_image);
                    photoT2 = (ImageView) view.findViewById(R.id.photo_user_t2);
                    like = (LinearLayout) view.findViewById(R.id.icon_indicator_like);
                    mLinearLayoutType1 = (LinearLayout) view.findViewById(R.id.layout_tipe1);
                    mLinearLayoutType2 = (LinearLayout) view.findViewById(R.id.layout_tipe2);


                    if (timelineList.get(i).getType() == 1) {
                        mLinearLayoutType2.setVisibility(View.INVISIBLE);
                        mLinearLayoutType1.setVisibility(View.VISIBLE);
                        nameT1.setText(timelineList.get(i).getName());
                        dateT1.setText(converDateTimezone(timelineList.get(i).getFulldate().split("G")[0]));
                        text.setText(timelineList.get(i).getText().toString());
                        if (timelineList.get(i).getAvatar() != null)
                            Glide.with(mContext).load(Uri.parse(timelineList.get(i).getAvatar().replace(".avatar",".jpg"))).into(photoT1);
                    }
                    if (timelineList.get(i).getType() == 0) {
                        mLinearLayoutType1.setVisibility(View.INVISIBLE);
                        mLinearLayoutType2.setVisibility(View.VISIBLE);
                        nameT2.setText(timelineList.get(i).getName());
                        dateT2.setText(converDateTimezone(timelineList.get(i).getFulldate().split("G")[0]));
                        if (timelineList.get(i).getAvatar() != null)
                            Glide.with(mContext).load(Uri.parse(timelineList.get(i).getAvatar().replace(".avatar",".jpg"))).into(photoT2);
                        if (timelineList.get(i).getLike() == 1) {
                            like.setBackgroundResource(R.drawable.background_btn_like_liked);
                            likeImg.setRotation(180);
                            likeImg.setScaleX(-1);
                        }
                        if (timelineList.get(i).getLike() == 0)
                            like.setBackgroundResource(R.drawable.background_btn_like_disliked);

                    }

                    container.addView(view);
                }
            }
        }
    }


    public class ItemStandingsStartViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.text_event)
        TextView textEvent;


        public ItemStandingsStartViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(String dateText) {
            date.setText(dateText);
        }
    }


    @Override
    public int getItemCount() {
        if(hourList.size()==1)return 2;
        return hourList.size() + 1;
    }

    public static String getHour(String date) {
        Log.w(TAG, "converDateTimezone: " + date);
        String result = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date d = formatter.parse(date);
            SimpleDateFormat showSDF = new SimpleDateFormat("HH");
            showSDF.setTimeZone(TimeZone.getDefault());
            result = showSDF.format(d);
            return result;
        } catch (Exception e) {
            return result;
        }
    }

    public static String getDate(String date) {
        Log.w(TAG, "converDateTimezone: " + date);
        String result = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date d = formatter.parse(date);
            SimpleDateFormat showSDF = new SimpleDateFormat("dd MMM",new Locale(App.getApp().getSharedPreferences(Const.LOCALE)));
            showSDF.setTimeZone(TimeZone.getDefault());
            result = showSDF.format(d);
            return result;
        } catch (Exception e) {
            return result;
        }
    }


    public static String converDateTimezone(String date) {
        String result = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date d = formatter.parse(date);
            SimpleDateFormat showSDF = new SimpleDateFormat("HH:mm:ss");
            showSDF.setTimeZone(TimeZone.getDefault());
            result = showSDF.format(d);
            return result;
        } catch (Exception e) {
            return result;
        }
    }
}
