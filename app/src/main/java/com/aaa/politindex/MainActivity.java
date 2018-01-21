package com.aaa.politindex;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.aaa.politindex.lounchscreen.FirstDialogFragment;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sPrefLounchScreeen = null;
    boolean show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirstDialogFragment firstDialogFragment = new FirstDialogFragment();
        sPrefLounchScreeen = getPreferences(MODE_PRIVATE);

//        show = sPrefLounchScreeen.getBoolean("NEVER_SHOW_AGAIN", false);
//        if (!show) {
            firstDialogFragment.show(getSupportFragmentManager(), "lounchScreen");
//            sPrefLounchScreeen.edit().putBoolean("NEVER_SHOW_AGAIN", true).commit();
//        }

        Log.w("log", "onCreate: ");
    }
}
