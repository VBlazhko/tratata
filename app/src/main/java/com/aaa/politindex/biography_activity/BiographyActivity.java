package com.aaa.politindex.biography_activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaa.politindex.App;
import com.aaa.politindex.BaseActivity;
import com.aaa.politindex.Const;
import com.aaa.politindex.R;
import com.aaa.politindex.connection.Request;
import com.aaa.politindex.helper.Md5Helper;
import com.aaa.politindex.model.Biography;
import com.aaa.politindex.model.ItemTimeLine;
import com.aaa.politindex.time_line_info_activity.TimeLineAdapter;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BiographyActivity extends BaseActivity {

    private Map<String, String> headers;
    private String mIdFigure;
    private String mNameFigure;
    private String mPhoto;


    @BindView(R.id.title)
    TextView mTitleBiography;
    @BindView(R.id.photo_figure)
    ImageView mFigurePhoto;
    @BindView(R.id.figure_name)
    TextView mFigureName;
    @BindView(R.id.figure_middle_name)
    TextView mFigureMiddleName;
    @BindView(R.id.birth_day)
    TextView mBirthDayText;
    @BindView(R.id.date)
    TextView mDate;
    @BindView(R.id.text_biography)
    TextView mTextBiography;
    @BindView(R.id.backText)
    TextView mBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biography);
        mUnbinder = ButterKnife.bind(this);

        mIdFigure = this.getIntent().getStringExtra("idFigure");
        mNameFigure = this.getIntent().getStringExtra("figureName");
        mPhoto = this.getIntent().getStringExtra("figurePhoto");


        headers = new HashMap<>();
        headers.put("Token", App.getApp().getSharedPreferences(Const.TOKEN));
        headers.put("Authorization", App.getApp().getSharedPreferences(Const.ID_TOKEN) + "_" +
                Md5Helper.md5(App.getApp().getSharedPreferences(Const.ID_TOKEN) + ":" + App.getApp().getSharedPreferences(Const.ID_USER) + ":" + App.getApp().getSharedPreferences(Const.TOKEN)));

        Request.getInstance().getResult("v1/" + mIdFigure + "/bio.api", headers, new Request.CallBack() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.w(TAG, "onResponse: " + jsonObject.toString());
                Gson gson = new Gson();
                JSONObject data = jsonObject.optJSONObject("data");

                Biography biography = gson.fromJson(jsonObject.toString(), Biography.class);

                mDate.setText(getDate(biography.getBirth()));
                mTextBiography.setText(biography.getBio());
                if (mPhoto != null)
                    Glide.with(getApplicationContext()).load(Uri.parse(mPhoto)).into(mFigurePhoto);
                mFigureName.setText(mNameFigure);


            }
        });


        mBack.setText(App.getApp().getValue("back_button"));
    }

    public static String getDate(String date) {
        String result = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date d = formatter.parse(date);
            SimpleDateFormat showSDF = new SimpleDateFormat("dd MMM yyyy");
            result = showSDF.format(d);
            return result;
        } catch (Exception e) {
            return result;
        }
    }

    @OnClick({R.id.backText, R.id.icon_back})
    protected void clickBack() {
        onBackPressed();
    }
}
