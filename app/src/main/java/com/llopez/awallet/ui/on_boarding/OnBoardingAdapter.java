package com.llopez.awallet.ui.on_boarding;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import com.llopez.awallet.R;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingAdapter extends FragmentStateAdapter {
    private Integer[] titles;
    private Integer[] contents;
    private Integer[] images;
    private int[] colorsBackground;

    public OnBoardingAdapter(FragmentActivity fa, Integer[] titles, Integer[] contents, Integer[] images, int[] colorsBackground) {
        super(fa);
        this.titles = titles;
        this.contents = contents;
        this.images = images;
        this.colorsBackground = colorsBackground;
    }

    @Override
    public Fragment createFragment(int position) {
        SliderOnBoardingFragment fragment = new SliderOnBoardingFragment(colorsBackground[position], images[position], titles[position], contents[position], position == 4);
        return fragment;
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
