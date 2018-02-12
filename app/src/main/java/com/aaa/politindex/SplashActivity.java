package com.aaa.politindex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.aaa.politindex.connection.Request;
import com.aaa.politindex.main_screen.MainActivity;
import com.aaa.politindex.model.Item;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    private Map<String, String> headers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        headers=new HashMap<>();
        Request.getInstance().getResult("v1/locale.api", headers, new Request.CallBack() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                JSONObject data = jsonObject.optJSONObject("data");
                JSONArray items = data.optJSONArray("items");
                Gson gson = new Gson();
                List<Item> list = new ArrayList<>();
                for(int i = 0; i < items.length(); i++){
                    Item item = gson.fromJson(items.optJSONObject(i).toString(), Item.class);
                    list.add(item);

                }

                App.getApp().setItemList(list);

              startActivity(new Intent(SplashActivity.this,MainActivity.class));
              finish();


            }
        });

    }
}
