package com.aaa.politindex;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.aaa.politindex.connection.Request;
import com.aaa.politindex.lounchscreen.FirstDialogFragment;
import com.aaa.politindex.model.Item;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sPrefLounchScreeen = null;
    boolean show;
    String prefKey = "NEVER_SHOW_AGAIN";
    FirstDialogFragment firstDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstDialogFragment = new FirstDialogFragment();
        sPrefLounchScreeen = getPreferences(MODE_PRIVATE);

        //        show = sPrefLounchScreeen.getBoolean(prefKey, false);
//        if (!show) {
        firstDialogFragment.show(getSupportFragmentManager(), "lounchScreen");
//            sPrefLounchScreeen.edit().putBoolean(prefKey, true).commit();
//        }



    }
}
