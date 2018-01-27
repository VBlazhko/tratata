package com.aaa.politindex;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.aaa.politindex.model.Item;
import com.vk.sdk.VKSdk;

import java.util.List;
import java.util.Locale;

/**
 * Created by 11 on 21.01.2018.
 */

public class App extends Application {

    private static App mApp;
    private String mLocale="EN";

    private SharedPreferences mSharedPreferences;


    public static String mUserId;
    public static String mToken;

    private List<Item> mItemList;

    @Override
    public void onCreate() {
        super.onCreate();

        mApp = this;
        //VKSdk.initialize(this);
       mSharedPreferences =  getSharedPreferences("info", MODE_PRIVATE);


        Locale locale=getResources().getConfiguration().locale;
        mLocale=locale.toString().split("_")[0];
        Log.w("log", "onCreate: "+ mLocale.toString() );

    }

    public static App getApp() {
        return mApp;
    }

    public void setItemList(List<Item> itemList) {
        mItemList = itemList;
    }

    public String getValue(String key){
        if(mItemList==null){
            return "null";
        }
        for(int i = 0; i < mItemList.size(); i++){
            if(mItemList.get(i).getKey().equals(key)){
                return mItemList.get(i).getValue();
            }
        }
        return "null";
    }

    public String getLocale() {
        return mLocale;
    }


    public void setSharedPreferences(String key, String value){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }


    public String getSharedPreferences(String key){
        return mSharedPreferences.getString(key,"");
    }

}
