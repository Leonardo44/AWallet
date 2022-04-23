package com.llopez.awallet.ui.on_boarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.llopez.awallet.R;

public class OnBoardingActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private Integer[] titles = {
            R.string.on_boarding_title_one,
            R.string.on_boarding_title_two,
            R.string.on_boarding_title_three,
            R.string.on_boarding_title_four,
            R.string.on_boarding_title_five
    };
    private Integer[] contents = {
            R.string.on_boarding_content_one,
            R.string.on_boarding_content_two,
            R.string.on_boarding_content_three,
            R.string.on_boarding_content_four,
            R.string.on_boarding_content_five
    };
    private Integer[] images = {
            R.drawable.ic_baseline_emoji_emotions_24,
            R.drawable.ic_baseline_payment_24,
            R.drawable.ic_baseline_attach_money_24,
            R.drawable.ic_baseline_money_off_24,
            R.drawable.ic_baseline_pie_chart_24
    };
    private FragmentStateAdapter pagerAdapter;
    private int[] colorsBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        SharedPreferences preferences = getSharedPreferences("credential", Context.MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean("isFirstTime", true);

        if (isFirstTime) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstTime", false);
            editor.apply();
        }

        viewPager = findViewById(R.id.view_pager_on_boarding);
        colorsBackground = getResources().getIntArray(R.array.array_background_on_boarding);

        pagerAdapter = new OnBoardingAdapter(this, titles, contents, images, colorsBackground);
        viewPager.setAdapter(pagerAdapter);
    }
}