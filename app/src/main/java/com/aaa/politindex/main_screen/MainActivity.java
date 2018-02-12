package com.aaa.politindex.main_screen;

import android.content.Intent;
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
import com.aaa.politindex.authentication.AuthActivity;
import com.aaa.politindex.Const;
import com.aaa.politindex.R;
import com.aaa.politindex.connection.Request;
import com.aaa.politindex.lounchscreen.FirstDialogFragment;
import com.aaa.politindex.main_screen.diagram.Diagram;
import com.aaa.politindex.main_screen.tabs.FigureFragment;
import com.aaa.politindex.main_screen.tabs.IShowFigureListener;
import com.aaa.politindex.main_screen.tabs.PagerAdapter;
import com.aaa.politindex.model.Figure;
import com.google.gson.Gson;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private Map<String, String> headers;

    SharedPreferences sPrefLounchScreeen = null;
    boolean show;
    String prefKey = "NEVER_SHOW_AGAIN";
    FirstDialogFragment firstDialogFragment;
    ViewPager mViewPager;
    private String[] scope;

    @BindView(R.id.slidingLayout)
    SlidingUpPanelLayout slidingLayout;

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

    @BindView(R.id.arrow)
    ImageView mImageArrow;

    @BindView(R.id.fb_auth_button)
    ImageView fb_auth_button;

    @BindView(R.id.vk_auth_button)
    ImageView vk_auth_button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        firstDialogFragment = new FirstDialogFragment();
        sPrefLounchScreeen = getPreferences(MODE_PRIVATE);
        mViewPager = (ViewPager) findViewById(R.id.pager);

        headers=new HashMap<>();

        Request.getInstance().getResult("v1/ru/event.api", headers, new Request.CallBack() {
            @Override
            public void onResponse(JSONObject jsonObject) {
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
                            firstname.setVisibility(View.VISIBLE);
                            lastname.setVisibility(View.VISIBLE);
                            mDiagram.setVisibility(View.VISIBLE);
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
                FigureFragment figureFragment = new FigureFragment();
                figureFragment.setListener(new IShowFigureListener() {
                    @Override
                    public void onShowFigure(Figure figure) {
                        firstname.setVisibility(View.INVISIBLE);
                        lastname.setVisibility(View.INVISIBLE);
                        mDiagram.setVisibility(View.INVISIBLE);
                        return;
                    }
                });
                fragments.add(figureFragment);

                mViewPager.setClipToPadding(false); //
                mViewPager.setPageMargin(30);

                pagerAdapter.setFragments(fragments);
                mViewPager.setAdapter(pagerAdapter);


            }
        });

        slidingLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {


                if (!newState.toString().equals("COLLAPSED")) {
                    mImageArrow.setRotation(180);
                } else {
                    mImageArrow.setRotation(0);
                }
            }
        });

        show = sPrefLounchScreeen.getBoolean(prefKey, false);
        if (!show) {
            firstDialogFragment.show(getSupportFragmentManager(), "lounchScreen");
            sPrefLounchScreeen.edit().putBoolean(prefKey, true).commit();
        }


        btn_login.setText(App.getApp().getValue("btn_login"));

        String s = (App.getApp().getValue("loginform_text"));
        loginform_text.setText(s.replaceAll("\\\\n", "\n"));

        loginform_law_text.setText(App.getApp().getValue("loginform_law_text"));

        flag.setBackgroundResource(App.getApp().getLocale().equals("en") ? R.drawable.english_language_icon : R.drawable.icon_russia_3x);



        fb_auth_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AuthActivity.class).putExtra("authorization", Const.FACEBOOK));
            }
        });
        vk_auth_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AuthActivity.class).putExtra("authorization",Const.VKONTAKTE));
            }
        });




    }




    }





