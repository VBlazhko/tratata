package com.aaa.politindex.user_profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.aaa.politindex.BaseActivity;
import com.aaa.politindex.R;
import com.chootdev.blurimg.BlurImage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserProfileActivity extends BaseActivity{


    @BindView(R.id.background_photo)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mUnbinder= ButterKnife.bind(this);

        BlurImage.withContext(this)
                .setBitmapScale(0.1f)
                .blurFromResource(R.drawable.figure2)
                .into(imageView);
    }
}
