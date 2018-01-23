package com.aaa.politindex.main_screen;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaa.politindex.App;
import com.aaa.politindex.R;
import com.aaa.politindex.connection.Request;
import com.aaa.politindex.lounchscreen.FirstDialogFragment;
import com.aaa.politindex.main_screen.diagram.Diagram;
import com.aaa.politindex.main_screen.tabs.FigureFragment;
import com.aaa.politindex.main_screen.tabs.PagerAdapter;
import com.aaa.politindex.model.Figure;
import com.aaa.politindex.model.Graph;
import com.aaa.politindex.model.Item;
import com.google.gson.Gson;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sPrefLounchScreeen = null;
    boolean show;
    String prefKey = "NEVER_SHOW_AGAIN";
    FirstDialogFragment firstDialogFragment;
    ViewPager mViewPager;

    @BindView(R.id.surname)
    TextView lastname;

    @BindView(R.id.name)
    TextView firstname;

    @BindView(R.id.diagram)
    Diagram mDiagram;

    @BindView(R.id.loginform_law_text)
    TextView loginform_law_text;

    @BindView(R.id.loginform_text)
    TextView loginform_text;

    @BindView(R.id.btn_login)
    TextView btn_login;

    @BindView(R.id.falg)
    ImageView flag;

    @BindView(R.id.slidingLayout)
    SlidingUpPanelLayout mSlidingUpPanelLayout;

    @BindView(R.id.arrow)
    ImageView mImageArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        firstDialogFragment = new FirstDialogFragment();
        sPrefLounchScreeen = getPreferences(MODE_PRIVATE);
        mViewPager = (ViewPager) findViewById(R.id.pager);

        Request.getInstance().getResult("v1/ru/event.api", null, new Request.CallBack() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.w("log", "onResponse: " + jsonObject.toString());
                List<Figure> figureList = new ArrayList<>();
                Gson gson = new Gson();
                JSONObject data = jsonObject.optJSONObject("data");
                JSONArray figures = data.optJSONArray("figures");


                for (int i = 0; i < figures.length(); i++) {
                    Figure figure = gson.fromJson(figures.optJSONObject(i).toString(), Figure.class);
                    figureList.add(figure);

                }
                Log.w("log", "onResponse: " + figureList.get(0).getFirstname().toString());
                PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
                List<Fragment> fragments = new ArrayList<>();
                for (int i = 0; i < figureList.size(); i++) {
                    FigureFragment fragment = new FigureFragment();
                    fragment.setFigure(figureList.get(i));
                    fragment.setListener(new IShowFigureListener() {
                        @Override
                        public void onShowFigure(Figure figure) {
                            firstname.setText(figure.getFirstname());
                            lastname.setText(figure.getLastname());
                            mDiagram.setProcent(figure.getGraph().getItems().get(0).getRating(),
                                    figure.getGraph().getItems().get(1).getRating(),
                                    figure.getGraph().getItems().get(2).getRating(),
                                    figure.getGraph().getItems().get(3).getRating(),
                                    figure.getGraph().getItems().get(4).getRating());

                            Log.w("log", "onShowFigure: " + figure.getGraph().getItems().get(0).getRating());

                            mDiagram.setDate(figure.getGraph().getItems().get(0).getDate(),
                                    figure.getGraph().getItems().get(1).getDate(),
                                    figure.getGraph().getItems().get(2).getDate(),
                                    figure.getGraph().getItems().get(3).getDate(),
                                    figure.getGraph().getItems().get(4).getDate());
                        }
                    });
                    if (i == 0) fragment.setFirstImage(true);
                    fragments.add(fragment);

                }

                fragments.add(new FigureFragment());

                mViewPager.setClipToPadding(false); //
                mViewPager.setPageMargin(30);

                pagerAdapter.setFragments(fragments);
                mViewPager.setAdapter(pagerAdapter);


            }
        });

        //        show = sPrefLounchScreeen.getBoolean(prefKey, false);
//        if (!show) {
        //  firstDialogFragment.show(getSupportFragmentManager(), "lounchScreen");
//            sPrefLounchScreeen.edit().putBoolean(prefKey, true).commit();
//        }


        btn_login.setText(App.getApp().getValue("btn_login"));
        loginform_text.setText(App.getApp().getValue("loginform_text").replaceAll("////n","//n"));
        loginform_law_text.setText(App.getApp().getValue("loginform_law_text"));
        flag.setBackgroundResource(App.getApp().getLocale().equals("en") ? R.drawable.english_language_icon : R.drawable.icon_russia_3x);

        mSlidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {

            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if(newState.toString().equals("COLLAPSED")){
                    mImageArrow.setRotation(0);
                }else {
                    mImageArrow.setRotation(180);
                }



            }
        });


    }
}
