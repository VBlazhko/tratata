package com.aaa.politindex.figure_main_screen.tab_photo_figure;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.aaa.politindex.App;
import com.aaa.politindex.R;
import com.aaa.politindex.figure_main_screen.FigureMainActivity;

/**
 * Created by 11 on 31.01.2018.
 */

public class PhotoTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View page, float position) {
        int screenWidth = Integer.parseInt(App.getApp().getSharedPreferences("width"));
        float destity = App.getApp().getDestiny();

        float absPosition = Math.abs(position);
        final View photo = page.findViewById(R.id.photo_figure);
        final View arrow = page.findViewById(R.id.arrow);

        if (position <= -1.0f || position >= 1.5f) {

            // Page is not visible -- stop any running animations

        } else if (position == 0.2f) {

            // Page is selected -- reset any views if necessary

        } else if (position < 1.3f && position >= 0.3f) {

            if (photo != null) {
                photo.setScaleX(1.3f - absPosition);
                photo.setScaleY(1.3f - absPosition);
                photo.setAlpha(1.3f - absPosition);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) (225 * destity), (int) (225 * destity));
                layoutParams.gravity = Gravity.CENTER_VERTICAL;
                layoutParams.setMargins((int) (-98 * destity + ((1.3f - absPosition) * 98 * destity)), 0, 0, 0);
                photo.setLayoutParams(layoutParams);
                arrow.setAlpha(absPosition - 0.3f);
            }

        } else if (position < 0.3f && position > 0.1f) {

            if (photo != null) {
                photo.setScaleX(1.0f);
                photo.setScaleY(1.0f);
                photo.setAlpha(1.0f);
                arrow.setAlpha(0.0f);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) (225 * destity), (int) (225 * destity));
                layoutParams.gravity = Gravity.CENTER_VERTICAL;
                layoutParams.setMargins(0, 0, 0, 0);
                photo.setLayoutParams(layoutParams);
            }

        } else if (position <= 0.1f && position >= 0.0f) {

            if (photo != null) {
                photo.setScaleX(0.9f + absPosition);
                photo.setScaleY(0.9f + absPosition);
                photo.setAlpha(0.9f + absPosition / 2);
            }

        } else if (position < 0.0f && position >= -1.0f) {

            if (photo != null) {
                photo.setScaleX(0.9f - absPosition);
                photo.setScaleY(0.9f - absPosition);
                photo.setAlpha(0.9f - absPosition * 3f);

            }
        }
    }
}
