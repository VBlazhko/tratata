package com.aaa.politindex.figure_main_screen.tab_photo_figure;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.aaa.politindex.App;

public class DeactivatableViewPager extends ViewPager {
    private int maxCount = 2;
    private float start;


    public DeactivatableViewPager(Context context) {
        super(context);
    }

    public DeactivatableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 0) {
            start = event.getX();
        }
        if (getCurrentItem() == maxCount && start + 16* App.getApp().getDestiny() > event.getX()) {
            if (event.getAction() == 1) {
                return super.onTouchEvent(MotionEvent.obtain(event.getDownTime(), event.getEventTime(), event.getAction(), start, event.getY(), event.getMetaState()));
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }
}