package com.aaa.politindex.search_by_figures;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.aaa.politindex.App;
import com.aaa.politindex.BaseActivity;
import com.aaa.politindex.R;
import com.aaa.politindex.model.Figure;
import com.aaa.politindex.model.Today;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class SearchByFiguresActivity extends BaseActivity {


    ArrayList<Figure> mFigureArrayList;
    ArrayList<Today> mTodayArrayList;

    @BindView(R.id.figure_list)
    ListView mFigureList;
    @BindView(R.id.edit_search)
    EditText mEditFigure;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_figures);
        mUnbinder= ButterKnife.bind(this);
        mFigureArrayList=this.getIntent().getParcelableArrayListExtra("figure");
        mTodayArrayList=this.getIntent().getParcelableArrayListExtra("today");
        for(int i=0;i<mFigureArrayList.size();i++){
            mFigureArrayList.get(i).setToday(mTodayArrayList.get(i));
        }

        Log.w(TAG, "onCreate: "+mFigureArrayList.get(0).getToday().getRating() );
        SearchListAdapter adapter = new SearchListAdapter(this,mFigureArrayList);
        mFigureList.setAdapter(adapter);

        mEditFigure.setHint(App.getApp().getValue("search_placeholder"));

    }

    @OnTextChanged(R.id.edit_search)
    protected void changeList(){
        ArrayList<Figure> newList = new ArrayList<>();
        for(int i=0;i<mFigureArrayList.size();i++){
            if(mFigureArrayList.get(i).getFirstname().toLowerCase().contains(mEditFigure.getText().toString().toLowerCase()) || mFigureArrayList.get(i).getLastname().toLowerCase().contains(mEditFigure.getText().toString().toLowerCase())){
                newList.add(mFigureArrayList.get(i));
            }
        }
        SearchListAdapter adapter = new SearchListAdapter(this,newList);
        mFigureList.setAdapter(adapter);
    }
    @OnClick(R.id.btn_cancel)
    protected void clickBack(){
        onBackPressed();
    }
}
