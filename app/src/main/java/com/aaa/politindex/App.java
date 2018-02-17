package com.aaa.politindex;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

import com.aaa.politindex.model.Item;

import java.util.List;
import java.util.Locale;

/**
 * Created by 11 on 21.01.2018.
 */

public class App extends Application {

    private static App mApp;
    private String mLocale="";

    private SharedPreferences mSharedPreferences;




    private List<Item> mItemList;

    @Override
    public void onCreate() {
        super.onCreate();

        mApp = this;
       mSharedPreferences =  getSharedPreferences("info", MODE_PRIVATE);

       if(!mSharedPreferences.getString(Const.FIRST, "null").equals("firstOK")) {
           Locale locale = getResources().getConfiguration().locale;
           mLocale = locale.toString().split("_")[0];
           setSharedPreferences(Const.LOCALE,mLocale);
       }

        setSharedPreferences(Const.FIRST,"firstOK");

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





    public void setSharedPreferences(String key, String value){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }


    public String getSharedPreferences(String key){
        return mSharedPreferences.getString(key,"");
    }

    public float getDestiny(){
        float density  = getResources().getDisplayMetrics().density;
        return density;
    }



}
