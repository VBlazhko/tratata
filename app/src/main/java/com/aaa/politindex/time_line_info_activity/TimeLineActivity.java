package com.aaa.politindex.time_line_info_activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.aaa.politindex.App;
import com.aaa.politindex.BaseActivity;
import com.aaa.politindex.Const;
import com.aaa.politindex.R;
import com.aaa.politindex.connection.Request;
import com.aaa.politindex.date_information_activity.DateInfoListAdapter;
import com.aaa.politindex.figure_main_screen.comment_list.CommentListAdapter;
import com.aaa.politindex.helper.Md5Helper;
import com.aaa.politindex.model.ItemDiagram;
import com.aaa.politindex.model.ItemTimeLine;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimeLineActivity extends BaseActivity {

    private Map<String, String> headers;
    private ArrayList<ItemTimeLine> mTimeLines;
    private ArrayList<String> mHourList;
    private String mIdEvent;
    private String mIdFigure;
    private String date;
    private String mNameFigure;

    @BindView(R.id.child_recycler)
    RecyclerView mRecyclerViewChild;
    @BindView(R.id.figure_name)
    TextView mFigureName;
    @BindView(R.id.back)
    TextView mBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        mIdEvent = this.getIntent().getStringExtra("idEvent");
        mIdFigure = this.getIntent().getStringExtra("idFigure");
        mNameFigure = this.getIntent().getStringExtra("name");
        date = this.getIntent().getStringExtra("date");


        mUnbinder= ButterKnife.bind(this);

        mFigureName.setText(mNameFigure);

        headers = new HashMap<>();
        headers.put("Token", App.getApp().getSharedPreferences(Const.TOKEN));
        headers.put("Authorization", App.getApp().getSharedPreferences(Const.ID_TOKEN) + "_" +
                Md5Helper.md5(App.getApp().getSharedPreferences(Const.ID_TOKEN) + ":" + App.getApp().getSharedPreferences(Const.ID_USER) + ":" + App.getApp().getSharedPreferences(Const.TOKEN)));

        mTimeLines = new ArrayList<>();

        Request.getInstance().getResult("v1/" + mIdEvent + "/" + mIdFigure + "/"+date+"/timeline.api", headers, new Request.CallBack() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.w(TAG, "onResponse: " + jsonObject.toString());
                Gson gson = new Gson();
                JSONObject data = jsonObject.optJSONObject("data");
                JSONArray graphs = data.optJSONArray("items");
                for (int i = 0; i < graphs.length(); i++) {
                    ItemTimeLine itemDiagram = gson.fromJson(graphs.optJSONObject(i).toString(), ItemTimeLine.class);
                    mTimeLines.add(itemDiagram);
                }
                Log.w(TAG, "onResponse: "+mTimeLines.size() +"size" );

                TimeLineAdapter adapter = new TimeLineAdapter(getApplicationContext(), mTimeLines);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                mRecyclerViewChild.setAdapter(adapter);
                mRecyclerViewChild.setLayoutManager(mLayoutManager);




            }
        });

        mBack.setText(App.getApp().getValue("back_button"));


    }

    @OnClick({R.id.back, R.id.icon_back})
    protected void clickBack() {
        onBackPressed();
    }
}
