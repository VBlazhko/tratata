package com.aaa.politindex.search_by_figures;

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
import com.makeramen.roundedimageview.RoundedImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 11 on 30.01.2018.
 */

public class SearchListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Figure> mFigureLIst;
    private LayoutInflater inflater;


    public SearchListAdapter(Context context, ArrayList<Figure> figures) {
        mContext = context;
        mFigureLIst = figures;
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null)
            view = inflater.inflate(R.layout.figure_list_item, parent, false);


        ((TextView) view.findViewById(R.id.name)).setText(getItem(position).getFirstname());
        ((TextView) view.findViewById(R.id.surname)).setText(getItem(position).getLastname());
        ((TextView) view.findViewById(R.id.pi_procent)).setText(mFigureLIst.get(position).getToday().getRating() + "%");
        Glide.with(mContext).load(Uri.parse(getItem(position).getAvatar())).into((ImageView)view.findViewById(R.id.photo_figure));


        return view;
    }

    static class ViewHolder {
        TextView mSurname;
        TextView mName;
        TextView mPiProcent;
        ImageView mFigureImage;
    }

    public interface ClickListener {
        void onClick(View view, int position);
    }

    @Override
    public int getCount() {
        return mFigureLIst.size();
    }

    @Override
    public Figure getItem(int i) {
        return mFigureLIst.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}
