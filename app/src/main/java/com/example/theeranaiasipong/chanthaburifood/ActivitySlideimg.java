package com.example.theeranaiasipong.chanthaburifood;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.theeranaiasipong.chanthaburifood.model.ImagesSlide;

public class ActivitySlideimg extends AppCompatActivity {

    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideimg);

        viewPager = (ViewPager)findViewById(R.id.imgpager);

    }

    public void ShowImge(){

    }
}
