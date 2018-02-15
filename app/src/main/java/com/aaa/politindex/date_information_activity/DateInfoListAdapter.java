package com.aaa.politindex.date_information_activity;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aaa.politindex.R;
import com.aaa.politindex.model.Figure;
import com.aaa.politindex.model.ItemDiagram;
import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static com.aaa.politindex.BaseActivity.TAG;

/**
 * Created by 11 on 14.02.2018.
 */


public class DateInfoListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<ItemDiagram> mDiagramArrayList;
    private LayoutInflater inflater;

    private View activeLine;
    private View noActiveLine;
    private View spacePi;
    private View piLayout;


    public DateInfoListAdapter(Context context, ArrayList<ItemDiagram> diagrams) {
        mContext = context;
        mDiagramArrayList = diagrams;
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null)
            view = inflater.inflate(R.layout.item_date_info, parent, false);


        ((TextView) view.findViewById(R.id.date)).setText(converDateTimezone(getItem(position).getDate()));
        ((TextView) view.findViewById(R.id.text_like)).setText(getItem(position).getLike().toString());
        ((TextView) view.findViewById(R.id.text_dislike)).setText(getItem(position).getDislike().toString());
        ((TextView) view.findViewById(R.id.text_comment)).setText(getItem(position).getComments().toString());
        ((TextView) view.findViewById(R.id.text_pi)).setText(getItem(position).getRating().toString() + "%");

        activeLine = view.findViewById(R.id.active_line);
        noActiveLine = view.findViewById(R.id.no_active_line);
        piLayout = view.findViewById(R.id.text_pi_layout);
        spacePi = view.findViewById(R.id.space_pi);


        LinearLayout.LayoutParams activeParams = (LinearLayout.LayoutParams) activeLine.getLayoutParams();
        LinearLayout.LayoutParams noActiveParams = (LinearLayout.LayoutParams) noActiveLine.getLayoutParams();
        LinearLayout.LayoutParams spaceParams = (LinearLayout.LayoutParams) spacePi.getLayoutParams();
        LinearLayout.LayoutParams piParams = (LinearLayout.LayoutParams) piLayout.getLayoutParams();

        Log.w(TAG, "getView: " + getItem(position).getRating());
        activeParams.weight = getItem(position).getRating();
        noActiveParams.weight = 100 - getItem(position).getRating();
        spaceParams.weight = getItem(position).getRating();

        if ((105 - getItem(position).getRating()) < 20) piParams.weight = 30;
        else piParams.weight = 105 - getItem(position).getRating();

        if (getItem(position).getRating() == 0) {
            piParams.weight = 1;
        }

        if (getItem(position).getRating() == 0 && getItem(position).getLike() == 0 && getItem(position).getDislike() == 0) {
            piLayout.setVisibility(View.INVISIBLE);
        } else piLayout.setVisibility(View.VISIBLE);

        if (getItem(position).getRating() < 49)
            activeLine.setBackgroundResource(R.drawable.background_progress_red);
        else activeLine.setBackgroundResource(R.drawable.background_progress_blue);

        activeLine.setLayoutParams(activeParams);
        noActiveLine.setLayoutParams(noActiveParams);
        spacePi.setLayoutParams(spaceParams);
        piLayout.setLayoutParams(piParams);


        return view;
    }

    public interface ClickListener {
        void onClick(View view, int position);
    }

    @Override
    public int getCount() {
        return mDiagramArrayList.size();
    }

    @Override
    public ItemDiagram getItem(int i) {
        return mDiagramArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    public static String converDateTimezone(String date) {
        String result = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date d = formatter.parse(date);
            SimpleDateFormat showSDF = new SimpleDateFormat("dd MMM");
            showSDF.setTimeZone(TimeZone.getDefault());
            result = showSDF.format(d);
            return result;
        } catch (Exception e) {
            return result;
        }
    }
}
