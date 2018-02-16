package com.aaa.politindex.user_profile;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aaa.politindex.App;
import com.aaa.politindex.BaseActivity;
import com.aaa.politindex.Const;
import com.aaa.politindex.R;
import com.aaa.politindex.connection.Request;
import com.aaa.politindex.figure_main_screen.comment_list.CommentListAdapter;
import com.aaa.politindex.helper.Md5Helper;
import com.aaa.politindex.model.FigureData;
import com.aaa.politindex.model.FigureStatistics;
import com.aaa.politindex.model.ItemComment;
import com.aaa.politindex.model.UserProfile;
import com.chootdev.blurimg.BlurImage;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserProfileActivity extends BaseActivity {

    private Map<String, String> headers;
    static final int DATE_PICKER_ID = 1111;
    private int year;
    private int month;
    private int day;


    @BindView(R.id.title)
    TextView mTitle;

    @BindView(R.id.background_photo)
    ImageView imageView;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.btn_edit_name)
    ImageView mBtnEditName;
    @BindView(R.id.user_name_layout)
    LinearLayout mLayoutName;
    @BindView(R.id.edit_name)
    EditText mEditName;


    @BindView(R.id.mail)
    TextView mMail;
    @BindView(R.id.btn_edit_mail)
    ImageView mBtnEditMail;
    @BindView(R.id.user_mail_layout)
    LinearLayout mLayoutMail;
    @BindView(R.id.edit_mail)
    EditText mEditMail;


    @BindView(R.id.teleg)
    TextView mTeleg;
    @BindView(R.id.btn_edit_teleg)
    ImageView mBtnEditTeleg;
    @BindView(R.id.user_teleg_layout)
    LinearLayout mLayoutTeleg;
    @BindView(R.id.edit_teleg)
    EditText mEditTeleg;


    @BindView(R.id.prof)
    TextView mProf;
    @BindView(R.id.btn_edit_prof)
    ImageView mBtnEditProf;
    @BindView(R.id.user_prof_layout)
    LinearLayout mLayoutProf;
    @BindView(R.id.edit_prof)
    EditText mEditProf;

    @BindView(R.id.date)
    TextView mDate;
    @BindView(R.id.btn_edit_date)
    ImageView mBtnEditDate;
    @BindView(R.id.user_prof_date)
    LinearLayout mLayoutDate;
    @BindView(R.id.back)
    TextView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mUnbinder = ButterKnife.bind(this);
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        BlurImage.withContext(this)
                .setBitmapScale(0.1f)
                .blurFromResource(R.drawable.figure2)
                .into(imageView);



        Request.getInstance().getResult("v1/user.api", new Request.CallBack() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Gson gson = new Gson();
                JSONObject data = jsonObject.optJSONObject("data");
                UserProfile userProfile = gson.fromJson(data.toString(), UserProfile.class);
                mTitle.setText(userProfile.getName());
                mName.setText(userProfile.getName());
                mMail.setText(userProfile.getEmail() != null ? userProfile.getEmail() : "");
                mTeleg.setText(userProfile.getTelegram() != null ? userProfile.getTelegram() : "");
                mProf.setText(userProfile.getProfession() != null ? userProfile.getProfession() : "");
                mDate.setText(!userProfile.getBirth().equals("0000-00-00") ? converDate(userProfile.getBirth()) : "");
            }
        });

        mBack.setText(App.getApp().getValue("back_button"));
    }


    @OnClick(R.id.btn_edit_name)
    protected void onEditNameClick() {
        changeField(mLayoutName, mEditName, mBtnEditName, mName, "name");
    }

    @OnClick(R.id.btn_edit_mail)
    protected void onEditMailClick() {
        changeField(mLayoutMail, mEditMail, mBtnEditMail, mMail, "email");
    }

    @OnClick(R.id.btn_edit_teleg)
    protected void onEditTelegClick() {
        changeField(mLayoutTeleg, mEditTeleg, mBtnEditTeleg, mTeleg, "telegram");
    }

    @OnClick(R.id.btn_edit_prof)
    protected void onEditProfClick() {
        changeField(mLayoutProf, mEditProf, mBtnEditProf, mProf, "profession");
    }

    @OnClick(R.id.btn_edit_date)
    protected void onEditDateClick() {
        showDialog(DATE_PICKER_ID);
    }


    private void changeField(final LinearLayout layout, final EditText editText, final ImageView btnEdit, final TextView fieldTextView, final String key) {
        layout.setVisibility(View.INVISIBLE);
        editText.setVisibility(View.VISIBLE);
        btnEdit.setVisibility(View.INVISIBLE);
        editText.setText(fieldTextView.getText());
        editText.setFocusable(true);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);

        InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Map<String, String> params = new HashMap<>();
                    params.put(key, editText.getText().toString());
                    layout.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.INVISIBLE);
                    btnEdit.setVisibility(View.VISIBLE);
                    editText.clearFocus();
                    setField(params, fieldTextView);
                }
                return false;
            }
        });
    }

    private void setField(final Map<String, String> params, final TextView field) {
        Request.getInstance().getResultLove("v1/user.api", params, new Request.CallBack() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    String json = jsonObject.getString("value");
                    field.setText(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:
                return new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, pickerListener, year, month, day);

        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            Map<String, String> params = new HashMap<>();
            params.put("birth", selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay);

            Request.getInstance().getResultLove("v1/user.api", params, new Request.CallBack() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        String json = jsonObject.getString("value");
                        mDate.setText(converDate(json));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        }
    };

    public static String converDate(String date) {
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

    @OnClick({R.id.back, R.id.icon_back})
    protected void clickBack() {
        onBackPressed();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {

        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            mLayoutName.setVisibility(View.VISIBLE);
            mLayoutMail.setVisibility(View.VISIBLE);
            mLayoutTeleg.setVisibility(View.VISIBLE);
            mLayoutProf.setVisibility(View.VISIBLE);

            mBtnEditName.setVisibility(View.VISIBLE);
            mBtnEditMail.setVisibility(View.VISIBLE);
            mBtnEditTeleg.setVisibility(View.VISIBLE);
            mBtnEditProf.setVisibility(View.VISIBLE);

            mEditName.setVisibility(View.INVISIBLE);
            mEditTeleg.setVisibility(View.INVISIBLE);
            mEditProf.setVisibility(View.INVISIBLE);
            mBtnEditMail.setVisibility(View.INVISIBLE);
        }
    }
}





