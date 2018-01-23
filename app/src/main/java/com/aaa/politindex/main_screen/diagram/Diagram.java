package com.aaa.politindex.main_screen.diagram;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aaa.politindex.R;

import static android.widget.RelativeLayout.CENTER_IN_PARENT;

/**
 * Created by 11 on 22.01.2018.
 */

public class Diagram extends LinearLayout {

    private static final String TAG = "log";

    private int maxHeight = 2;

    MyHandler mHandler;

    private TextView textColum1;
    private TextView textColum2;
    private TextView textColum3;
    private TextView textColum4;
    private TextView textColum5;

    private ImageView colum1;
    private ImageView colum2;
    private ImageView colum3;
    private ImageView colum4;
    private ImageView colum5;

    private TextView dateColum1;
    private TextView dateColum2;
    private TextView dateColum3;
    private TextView dateColum4;
    private TextView dateColum5;

    private int startProcent1;
    private int startProcent2;
    private int startProcent3;
    private int startProcent4;
    private int startProcent5;

    private int endProcent1;
    private int endProcent2;
    private int endProcent3;
    private int endProcent4;
    private int endProcent5;


    public Diagram(Context context) {
        super(context);
        init(context);
    }

    public Diagram(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Diagram(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.diagram, this);


        textColum1 = (TextView) findViewById(R.id.textColum1);
        textColum2 = (TextView) findViewById(R.id.textColum2);
        textColum3 = (TextView) findViewById(R.id.textColum3);
        textColum4 = (TextView) findViewById(R.id.textColum4);
        textColum5 = (TextView) findViewById(R.id.textColum5);

        colum1 = (ImageView) findViewById(R.id.colum1);
        colum2 = (ImageView) findViewById(R.id.colum2);
        colum3 = (ImageView) findViewById(R.id.colum3);
        colum4 = (ImageView) findViewById(R.id.colum4);
        colum5 = (ImageView) findViewById(R.id.colum5);

        dateColum1 = (TextView) findViewById(R.id.dateColum1);
        dateColum2 = (TextView) findViewById(R.id.dateColum2);
        dateColum3 = (TextView) findViewById(R.id.dateColum3);
        dateColum4 = (TextView) findViewById(R.id.dateColum4);
        dateColum5 = (TextView) findViewById(R.id.dateColum5);


    }

    public void setDate(String date1, String date2, String date3, String date4, String date5) {
        dateColum1.setText(date1);
        dateColum2.setText(date2);
        dateColum3.setText(date3);
        dateColum4.setText(date4);
        dateColum5.setText(date5);
    }


    public void setProcent(int procent1, int procent2, int procent3, int procent4, int procent5) {
        textColum1.setText(procent1 + "%");
        textColum2.setText(procent2 + "%");
        textColum3.setText(procent3 + "%");
        textColum4.setText(procent4 + "%");
        textColum5.setText(procent5 + "%");

        startProcent1 = colum1.getHeight() / maxHeight;
        startProcent2 = colum2.getHeight() / maxHeight;
        startProcent3 = colum3.getHeight() / maxHeight;
        startProcent4 = colum4.getHeight() / maxHeight;
        startProcent5 = colum5.getHeight() / maxHeight;

        endProcent1 = procent1;
        endProcent2 = procent2;
        endProcent3 = procent3;
        endProcent4 = procent4;
        endProcent5 = procent5;

        if (mHandler != null) {
            mHandler.delay = 0;
            mHandler = null;
        }
        mHandler = new MyHandler();

        Log.w(TAG, "setProcent1: " + startProcent1);
        Log.w(TAG, "setProcent2: " + endProcent1);


        changeSize(startProcent1 * maxHeight, (int) ((endProcent1 - startProcent1) / 10), colum1, mHandler, maxHeight * 10 + 1);
        changeSize(startProcent2 * maxHeight, (int) ((endProcent2 - startProcent2) / 10), colum2, mHandler, maxHeight * 10 + 1);
        changeSize(startProcent3 * maxHeight, (int) ((endProcent3 - startProcent3) / 10), colum3, mHandler, maxHeight * 10 + 1);
        changeSize(startProcent4 * maxHeight, (int) ((endProcent4 - startProcent4) / 10), colum4, mHandler, maxHeight * 10 + 1);
        changeSize(startProcent5 * maxHeight, (int) ((endProcent5 - startProcent5) / 10), colum5, mHandler, maxHeight * 10 + 1);


    }

    public void changeSize(final int current, final int delta, final ImageView image, final MyHandler handler, int counter) {
        final int newCounter = --counter;

        if (newCounter == 0) {
            if (current / maxHeight >= 50) {
                image.setBackgroundResource(R.drawable.background_colum_blue);
            } else {
                image.setBackgroundResource(R.drawable.background_column_red);
            }


            return;
        }
        Log.w("log", "changeSize: " + delta);

        image.setBackgroundResource(R.drawable.background_colum_gray);

        handler.myPostDelay(new Runnable() {
            @Override
            public void run() {
                int newStart = current + delta;
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, newStart);
                image.setLayoutParams(lp);
                changeSize(newStart, delta, image, handler, newCounter);
            }
        }, handler.delay);
    }

    class MyHandler extends Handler {
        public int delay = 20;

        public void myPostDelay(Runnable run, int delay) {
            if (delay > 0) {
                postDelayed(run, delay);
            }

        }
    }
}

