package com.aaa.politindex.figure_main_screen.comment_list;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaa.politindex.R;
import com.aaa.politindex.model.Figure;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by 11 on 02.02.2018.
 */

public class CommentListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Comment> mComments;
    private LayoutInflater inflater;


    public CommentListAdapter(Context context,ArrayList<Comment> commentArrayList) {
        mContext = context;
        mComments=commentArrayList;
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null)
            view = inflater.inflate(R.layout.item_comment, parent, false);


//            ((TextView) view.findViewById(R.id.name)).setText(getItem(position).getFirstname());
//            ((TextView) view.findViewById(R.id.surname)).setText(getItem(position).getLastname());
//            ((TextView) view.findViewById(R.id.pi_procent)).setText(mFigureLIst.get(position).getToday().getRating() + "%");
//            Glide.with(mContext).load(Uri.parse(getItem(position).getAvatar())).into((ImageView)view.findViewById(R.id.photo_figure));


        return view;
    }


    public interface ClickListener {
        void onClick(View view, int position);
    }

    @Override
    public int getCount() {
        return mComments.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }


    @Override
    public long getItemId(int i) {
        return i;
    }
}

