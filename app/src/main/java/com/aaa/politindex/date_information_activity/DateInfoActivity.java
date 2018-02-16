package com.aaa.politindex.date_information_activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.aaa.politindex.App;
import com.aaa.politindex.BaseActivity;
import com.aaa.politindex.BaseFragment;
import com.aaa.politindex.Const;
import com.aaa.politindex.R;
import com.aaa.politindex.connection.Request;
import com.aaa.politindex.figure_main_screen.FigureMainActivity;
import com.aaa.politindex.helper.Md5Helper;
import com.aaa.politindex.model.Figure;
import com.aaa.politindex.model.FigureStatistics;
import com.aaa.politindex.model.ItemDiagram;
import com.aaa.politindex.search_by_figures.SearchByFiguresActivity;
import com.aaa.politindex.search_by_figures.SearchListAdapter;
import com.aaa.politindex.time_line_info_activity.TimeLineActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DateInfoActivity extends BaseActivity {


    private ArrayList<ItemDiagram> mDiagramArrayList;
    private String mIdEvent;
    private String mIdFigure;
    private String mNameFigure;

    @BindView(R.id.date_info_list)
    ListView mDateInfoList;
    @BindView(R.id.figure_name)
    TextView mFigureName;
    @BindView(R.id.back)
    TextView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_info);

        mUnbinder = ButterKnife.bind(this);

        mIdEvent = this.getIntent().getStringExtra("idEvent");
        mIdFigure = this.getIntent().getStringExtra("idFigure");
        mNameFigure = this.getIntent().getStringExtra("figureName");

        mFigureName.setText(mNameFigure);



        mDiagramArrayList = new ArrayList<>();

        Request.getInstance().getResult("v1/" + mIdEvent + "/" + mIdFigure + "/graph.api", new Request.CallBack() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.w(TAG, "onResponse: " + jsonObject.toString());
                Gson gson = new Gson();
                JSONObject data = jsonObject.optJSONObject("data");
                JSONArray graphs = data.optJSONArray("items");
                for (int i = 0; i < graphs.length(); i++) {
                    ItemDiagram itemDiagram = gson.fromJson(graphs.optJSONObject(i).toString(), ItemDiagram.class);
                    mDiagramArrayList.add(itemDiagram);
                }
                DateInfoListAdapter adapter = new DateInfoListAdapter(getApplicationContext(), mDiagramArrayList);
                mDateInfoList.setAdapter(adapter);
                mDateInfoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (mDiagramArrayList.get(i).getLike() != 0 || mDiagramArrayList.get(i).getDislike() != 0 || mDiagramArrayList.get(i).getComments() != 0) {
                            Intent intent = new Intent(DateInfoActivity.this, TimeLineActivity.class);
                            intent.putExtra("idFigure", mIdFigure);
                            intent.putExtra("idEvent", mIdEvent);
                            intent.putExtra("name", mNameFigure);
                            intent.putExtra("date", mDiagramArrayList.get(i).getDate());
                            startActivity(intent);
                        }
                    }
                });
            }
        });

        mBack.setText(App.getApp().getValue("back_button"));
    }

    @OnClick({R.id.back, R.id.icon_back})
    protected void clickBack() {
        onBackPressed();
    }
}
