package com.example.theeranaiasipong.chanthaburifood.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.theeranaiasipong.chanthaburifood.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SldeimgaeFoods extends Fragment {
    ViewPager viewPager;
    PagerAdapter pagerAdapter;

    public SldeimgaeFoods() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_sldeimgae_foods,  showinfo(container), false);
    }

    public ViewGroup showinfo(ViewGroup container){
        ViewGroup viewGroup = null;
        if(viewGroup == null)
            viewGroup = container;

        ViewPager  viewPager = (ViewPager)viewGroup.findViewById(R.id.imgpager);

        return viewGroup;
    }

}
