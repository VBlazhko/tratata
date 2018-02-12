package com.aaa.politindex.figure_main_screen;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.aaa.politindex.App;
import com.aaa.politindex.BaseActivity;
import com.aaa.politindex.Const;
import com.aaa.politindex.R;
import com.aaa.politindex.connection.Request;
import com.aaa.politindex.figure_main_screen.comment_list.Comment;
import com.aaa.politindex.figure_main_screen.comment_list.CommentListAdapter;
import com.aaa.politindex.figure_main_screen.tab_photo_figure.PagerAdapterPhoto;
import com.aaa.politindex.figure_main_screen.tab_photo_figure.PhotoFigureFragment;
import com.aaa.politindex.figure_main_screen.tab_photo_figure.PhotoTransformer;
import com.aaa.politindex.helper.Md5Helper;
import com.aaa.politindex.main_screen.tabs.FigureFragment;
import com.aaa.politindex.main_screen.tabs.IShowFigureListener;
import com.aaa.politindex.model.Figure;
import com.aaa.politindex.model.FigureData;
import com.aaa.politindex.search_by_figures.SearchListAdapter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FigureMainActivity extends BaseActivity {

    private Map<String, String> headers;
    private ArrayList<Figure> mFigureArrayList;
    private String mIdEvent;
    private String mIdFigure;


    @BindView(R.id.pager)
    ViewPager mViewPager;
    @BindView(R.id.comment_list)
    ListView mCommentList;
    @BindView(R.id.figure_name)
    TextView mFigureName;
    @BindView(R.id.pi_today)
    TextView mPiToday;

    @BindView(R.id.numb_dislike)
    TextView mNumbDislike;
    @BindView(R.id.numb_like)
    TextView mNumbLike;

    @BindView(R.id.btn_like)
    View mBtnLike;
    @BindView(R.id.btn_dislike)
    View mBtnDislike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_figure_main);
        mUnbinder = ButterKnife.bind(this);

        mFigureArrayList = this.getIntent().getParcelableArrayListExtra("figure_list");
        mIdEvent = this.getIntent().getStringExtra("idEvent");
        headers = new HashMap<>();
        headers.put("Token", App.getApp().getSharedPreferences(Const.TOKEN));
        headers.put("Authorization", App.getApp().getSharedPreferences(Const.ID_TOKEN) + "_" +
                Md5Helper.md5(App.getApp().getSharedPreferences(Const.ID_TOKEN) + ":" + App.getApp().getSharedPreferences(Const.ID_USER) + ":" + App.getApp().getSharedPreferences(Const.TOKEN)));




        PagerAdapterPhoto pagerAdapterPhoto = new PagerAdapterPhoto(getSupportFragmentManager());
        List<Fragment> fragments = new ArrayList<>();

        for (int i=0;i<mFigureArrayList.size();i++) {
            final PhotoFigureFragment fragment = new PhotoFigureFragment();
            fragment.setFigure(mFigureArrayList.get(i));
            fragment.setListener(new IShowFigureListener() {
                @Override
                public void onShowFigure(Figure figure) {
                    mFigureName.setText(figure.getFirstname()+" "+figure.getLastname());
                    mIdFigure=figure.getIdFigure().toString();
                    getFigureInfo(mIdFigure,mIdEvent);

                }
            });
            fragments.add(fragment);
        }


        int screenWidth = getWidth();
        float destiny = App.getApp().getDestiny();
        pagerAdapterPhoto.setFragments(fragments);
        mViewPager.setPadding((int) ((screenWidth / 2) - ((112 + 13) * destiny)), 0, 0, 0);
        mViewPager.setAdapter(pagerAdapterPhoto);
        mViewPager.setPageMargin((int) (0.8 * destiny));
        mViewPager.setClipToPadding(false); //
        mViewPager.setPageTransformer(false, new PhotoTransformer());

        ArrayList<Comment> comments = new ArrayList<>();

        comments.add(new Comment());
        comments.add(new Comment());
        comments.add(new Comment());
        comments.add(new Comment());
        CommentListAdapter adapter = new CommentListAdapter(this, comments);
        mCommentList.setScrollContainer(true);
        mCommentList.setAdapter(adapter);

        getFigureLove(2+"",2+"",true);
    }

    private void getFigureInfo(String idFigure, String idEvent){
        Request.getInstance().getResult( "v1/"+idEvent+"/"+idFigure+"/figure.api", headers, new Request.CallBack() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Gson gson = new Gson();
                JSONObject data = jsonObject.optJSONObject("data");
                FigureData figureData=gson.fromJson(data.toString(), FigureData.class);

                mPiToday.setText(figureData.getFigure().getRating()+"%");
                mNumbDislike.setText(figureData.getFigure().getDislike().toString());
                mNumbLike.setText(figureData.getFigure().getLike().toString());

                if(figureData.getFigure().getIsLiked()==1)mBtnLike.setBackgroundResource(R.drawable.background_btn_like_liked);
                if(figureData.getFigure().getIsLiked()==0)mBtnDislike.setBackgroundResource(R.drawable.background_btn_like_disliked);
                Log.w(TAG, "onResponse: isLiked??? "+figureData.getFigure().getIsLiked());

            }
        });
    }

    private void getFigureLove(String idFigure,String idEvent, boolean isLiked){
        Map<String, String> params=new HashMap<>();
        params.put("idEvent",2+"");
        params.put("idFigure",2+"");
        params.put("isLike",1+"");

        Log.w(TAG, "getFigureLove:wwwwwwwwwwww "+headers.get("Authorization") );
        Request.getInstance().getResultLove( "v1/love.api", headers,params, new Request.CallBack() {
            @Override
            public void onResponse(JSONObject jsonObject) {

            }
        });
    }





    private int getWidth() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int dpWidth = outMetrics.widthPixels;
        return dpWidth;
    }
}
