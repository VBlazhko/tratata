package com.aaa.politindex.lounchscreen;


import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaa.politindex.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FirstDialogFragment extends DialogFragment {

    @BindView(R.id.pager)
    ViewPager mPager;
    @BindView(R.id.first_point)

    ImageView firstPoint;
    @BindView(R.id.second_point)
    ImageView secondPoint;
    @BindView(R.id.third_point)
    ImageView thirdPoint;
    @BindView(R.id.next)

    TextView next;
    @BindView(R.id.skip)
    TextView skip;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_first_dialog, null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LaunchScreenOneFragment mLaunchScreenOneFragment = new LaunchScreenOneFragment();
        LaunchScreenTwoFragment mLaunchScreenTwoFragment = new LaunchScreenTwoFragment();
        LaunchScreenThreeFragment mLaunchScreenThreeFragment = new LaunchScreenThreeFragment();

        mLaunchScreenOneFragment.setImageView(firstPoint);
        mLaunchScreenTwoFragment.setImageView(secondPoint);
        mLaunchScreenThreeFragment.setImageView(thirdPoint);

        mLaunchScreenOneFragment.setNext(next, mPager);
        mLaunchScreenThreeFragment.setNext(next, mPager, new ICloseListener() {
            @Override
            public void onClose() {
                getDialog().dismiss();
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager(), 3);

        adapter.setLaunchScreenOneFragment(mLaunchScreenOneFragment);
        adapter.setLaunchScreenTwoFragment(mLaunchScreenTwoFragment);
        adapter.setLaunchScreenThreeFragment(mLaunchScreenThreeFragment);

        mPager.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        float density = getActivity().getResources().getDisplayMetrics().density;
        int width = (int) (size.x - 40 * density);
        int height = (int) (size.y - 64 * density);

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);


        }
    }

    interface ICloseListener {
        void onClose();
    }

}
