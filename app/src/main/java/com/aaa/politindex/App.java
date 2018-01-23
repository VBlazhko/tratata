package com.aaa.politindex;

import android.app.Application;
import android.util.Log;

import com.aaa.politindex.model.Item;

import java.util.List;
import java.util.Locale;

/**
 * Created by 11 on 21.01.2018.
 */

public class App extends Application {

    private static App mApp;
    private String mLocale="EN";


    private List<Item> mItemList;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
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

}
