package com.aaa.politindex.figure_main_screen.comment_list;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaa.politindex.R;
import com.aaa.politindex.model.Figure;
import com.aaa.politindex.model.ItemComment;
import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static com.aaa.politindex.BaseActivity.TAG;

/**
 * Created by 11 on 02.02.2018.
 */

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.MyViewHolder> {
    private ArrayList<ItemComment> mComments;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, text, date;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            text = (TextView) view.findViewById(R.id.text);
            date = (TextView) view.findViewById(R.id.date);
            image = (ImageView) view.findViewById(R.id.photo_user);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;

    }

    public CommentListAdapter(Context context, ArrayList<ItemComment> commentArrayList) {
        mComments = commentArrayList;
        mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (holder != null) {

            holder.name.setText(mComments.get(position).getName());
            holder.text.setText(mComments.get(position).getText());
            holder.date.setText(checkTime(mComments.get(position).getFulldate()));

            if (mComments.get(position).getAvatar() != null)
                Glide.with(mContext).load(Uri.parse(mComments.get(position).getAvatar())).into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    private String checkTime(String dateServer) {

        String dateReturn = null, servYear, servMonth, servDay, servHour, servMin, servSec;
        String cYear, cMonth, cDay, cHour, cMin, cSec;
        String GMTHour, GMTMin;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String cuerentDate = df.format(Calendar.getInstance().getTime());

        String[] array = dateServer.split("-");
        servYear = array[0];
        servMonth = array[1];
        servDay = array[2].split("T")[0];
        servHour = array[2].split("T")[1].split(":")[0];
        servMin = array[2].split("T")[1].split(":")[1];
        servSec = array[2].split("T")[1].split(":")[2].split("\\+")[0];

        String[] array2 = cuerentDate.split("-");
        cYear = array2[0];
        cMonth = array2[1];
        cDay = array2[2].split("T")[0];
        cHour = array2[2].split("T")[1].split(":")[0];
        cMin = array2[2].split("T")[1].split(":")[1];
        cSec = array2[2].split("T")[1].split(":")[2];

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"),
                Locale.getDefault());
        Date currentLocalTime = calendar.getTime();
        DateFormat date = new SimpleDateFormat("z", Locale.getDefault());
        String localTime = date.format(currentLocalTime);

        String[] array3 = localTime.split("\\+");
        GMTHour = array3[1].split(":")[0];
        GMTMin = array3[1].split(":")[1];
        Log.w(TAG, "checkTime: GMTH" + GMTHour);


        int min = Integer.parseInt(servMin) + Integer.parseInt(GMTMin);
        int hour = Integer.parseInt(servHour) + Integer.parseInt(GMTHour);
        if (min > 60) {
            hour++;
            min = min - 60;
            if (min < 10) servMin = "0"+min;
            else servMin = min + "";
            servHour = hour + "";
        } else {
            if (min < 10) servMin = "0"+min;
            else servMin = min + "";
            servHour = hour + "";
        }

        if (cMin.equals(servMin)&&cHour.equals(servHour)&&cDay.equals(servDay)){
            return "just now";

        }


            return servMonth + " " + servDay + " " + servHour + ":" + servMin;

    }


}

