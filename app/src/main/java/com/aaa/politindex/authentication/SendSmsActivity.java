package com.aaa.politindex.authentication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aaa.politindex.R;
import com.gmail.samehadar.iosdialog.CamomileSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SendSmsActivity extends AppCompatActivity {

    @BindView(R.id.seconds)
    TextView mSeconds;

    @BindView(R.id.repeatedly)
    TextView mRepeatedly;

    @BindView(R.id.spinner)
    CamomileSpinner mSpinner;

    Handler myHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        ButterKnife.bind(this);
        myHandler=new Handler();

        mSpinner.start();
        stopwatch(myHandler,60);




    }


    public void stopwatch(final Handler handler, int number){
        final int newCounter = -- number;
        if(newCounter==0){
            mSeconds.setVisibility(View.INVISIBLE);
            mRepeatedly.setVisibility(View.INVISIBLE);
            return;
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSeconds.setText(newCounter+"");
                stopwatch(handler,newCounter);
            }
        },1000);
    }
}
