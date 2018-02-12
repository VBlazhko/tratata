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

import java.util.ArrayList;

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

    public CommentListAdapter(Context context,ArrayList<ItemComment> commentArrayList) {
        mComments = commentArrayList;
        mContext=context;
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
            holder.date.setText(mComments.get(position).getFulldate());
            if(mComments.get(position).getAvatar()!=null)Glide.with(mContext).load(Uri.parse(mComments.get(position).getAvatar())).into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }


}

