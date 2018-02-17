package com.aaa.politindex.search_by_figures;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.aaa.politindex.App;
import com.aaa.politindex.BaseActivity;
import com.aaa.politindex.Const;
import com.aaa.politindex.R;
import com.aaa.politindex.figure_main_screen.FigureMainActivity;
import com.aaa.politindex.locale.LocaleActivity;
import com.aaa.politindex.main_screen_for_auth_user.MainAuthUserActivity;
import com.aaa.politindex.model.Figure;
import com.aaa.politindex.model.Today;
import com.aaa.politindex.user_profile.UserProfileActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class SearchByFiguresActivity extends BaseActivity {


    ArrayList<Figure> mFigureArrayList;
    ArrayList<Today> mTodayArrayList;

    private String numbFigure;
    private String mIdEvent;

    @BindView(R.id.btn_change_language)
    ImageView mChangeLocale;
    @BindView(R.id.figure_list)
    ListView mFigureList;
    @BindView(R.id.edit_search)
    EditText mEditFigure;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_figures);
        mUnbinder= ButterKnife.bind(this);
        mIdEvent = this.getIntent().getStringExtra("idEvent");
        mFigureArrayList=this.getIntent().getParcelableArrayListExtra("figure");
        mTodayArrayList=this.getIntent().getParcelableArrayListExtra("today");
        for(int i=0;i<mFigureArrayList.size();i++){
            mFigureArrayList.get(i).setToday(mTodayArrayList.get(i));
        }

        Log.w(TAG, "onCreate: "+mFigureArrayList.get(0).getToday().getRating() );
        SearchListAdapter adapter = new SearchListAdapter(this,mFigureArrayList);
        mFigureList.setAdapter(adapter);
        mFigureList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                changeActivity(mFigureArrayList.get(i).getIdFigure().toString());
            }
        });

        mEditFigure.setHint(App.getApp().getValue("search_placeholder"));

        mChangeLocale.setBackgroundResource(App.getApp().getSharedPreferences(Const.LOCALE).toUpperCase().equals("EN") ? R.drawable.english_language_icon : R.drawable.icon_russia_3x);

    }

    @OnTextChanged(R.id.edit_search)
    protected void changeList(){
        final ArrayList<Figure> newList = new ArrayList<>();
        for(int i=0;i<mFigureArrayList.size();i++){
            if(mFigureArrayList.get(i).getFirstname().toLowerCase().contains(mEditFigure.getText().toString().toLowerCase()) || mFigureArrayList.get(i).getLastname().toLowerCase().contains(mEditFigure.getText().toString().toLowerCase())){
                newList.add(mFigureArrayList.get(i));
            }
        }
        SearchListAdapter adapter = new SearchListAdapter(this,newList);
        mFigureList.setAdapter(adapter);
        mFigureList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                changeActivity(newList.get(i).getIdFigure().toString());
            }
        });
    }
    @OnClick(R.id.btn_cancel)
    protected void clickBack(){
        onBackPressed();
    }

    private void changeActivity(String idFigure){
        String curentNumb=null;
        for(int i=0;i<mFigureArrayList.size();i++){
            if(mFigureArrayList.get(i).getIdFigure().toString().equals(idFigure))curentNumb=i+"";
        }
        Intent intent = new Intent(SearchByFiguresActivity.this, FigureMainActivity.class);
        intent.putParcelableArrayListExtra("figure_list", mFigureArrayList);
        intent.putExtra("figure_numb_in_list", curentNumb);
        intent.putExtra("idEvent", mIdEvent);
        startActivity(intent);
    }

    @OnClick(R.id.btn_profile)
    protected void clickProfile() {
        startActivity(new Intent(this, UserProfileActivity.class));
    }



}
