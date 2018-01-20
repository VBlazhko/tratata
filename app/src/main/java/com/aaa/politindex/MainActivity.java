package com.aaa.politindex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.aaa.politindex.lounchscreen.FirstDialogFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirstDialogFragment firstDialogFragment=new FirstDialogFragment();
        firstDialogFragment.show(getSupportFragmentManager(), "asdas");
        Log.w("log", "onCreate: ");
    }
}
