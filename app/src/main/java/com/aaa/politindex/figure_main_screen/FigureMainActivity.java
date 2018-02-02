package com.aaa.politindex.figure_main_screen;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.aaa.politindex.App;
import com.aaa.politindex.BaseActivity;
import com.aaa.politindex.R;
import com.aaa.politindex.figure_main_screen.comment_list.Comment;
import com.aaa.politindex.figure_main_screen.comment_list.CommentListAdapter;
import com.aaa.politindex.figure_main_screen.tab_photo_figure.PagerAdapterPhoto;
import com.aaa.politindex.figure_main_screen.tab_photo_figure.PhotoFigureFragment;
import com.aaa.politindex.figure_main_screen.tab_photo_figure.PhotoTransformer;
import com.aaa.politindex.search_by_figures.SearchListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FigureMainActivity extends BaseActivity {

    @BindView(R.id.pager)
    ViewPager mViewPager;
    @BindView(R.id.comment_list)
    ListView mCommentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_figure_main);
        mUnbinder= ButterKnife.bind(this);

        PagerAdapterPhoto pagerAdapterPhoto=new PagerAdapterPhoto(getSupportFragmentManager());
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new PhotoFigureFragment());
        fragments.add(new PhotoFigureFragment());
        fragments.add(new PhotoFigureFragment());
        fragments.add(new PhotoFigureFragment());
        fragments.add(new PhotoFigureFragment());

        int screenWidth = Integer.parseInt(App.getApp().getSharedPreferences("width"));
        float destity = App.getApp().getDestiny();
        pagerAdapterPhoto.setFragments(fragments);
        mViewPager.setPadding((int)((screenWidth/2)-((112+13)*destity)),0,0,0);
        mViewPager.setAdapter(pagerAdapterPhoto);
        mViewPager.setPageMargin((int)(0.8*destity));
        mViewPager.setClipToPadding(false); //
        mViewPager.setPageTransformer(false,new PhotoTransformer());

        ArrayList<Comment>comments = new ArrayList<>();
        comments.add(new Comment());
        comments.add(new Comment());
        comments.add(new Comment());
        comments.add(new Comment());
        CommentListAdapter adapter = new CommentListAdapter(this,comments);
        mCommentList.setScrollContainer(true);
        mCommentList.setAdapter(adapter);
    }
}
