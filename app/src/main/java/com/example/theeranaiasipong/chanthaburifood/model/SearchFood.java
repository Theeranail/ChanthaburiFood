package com.example.theeranaiasipong.chanthaburifood.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.theeranaiasipong.chanthaburifood.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by TheeranaiAsipong on 14/6/2559.
 */
public class SearchFood {

    public static final String[] lismenu = {"ร้านอาหารไกล้ตัว", "ตามคะเเนน Vote", "รีวิวเยอะที่สุด"};
    public static final int[] iconlistmenu = {R.drawable.ic_place_black_24dp, R.drawable.ic_star_black_24dp1, R.drawable.ic_rate_review_black_24dp};
    public double distace = 0;
    public SearchFood() {

    }

    public ArrayList<HashMap<String, Object>> AddrayyObj() {
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = null;

        for (int i = 0; i < lismenu.length; i++) {
            map = new HashMap<String, Object>();
            map.put("name", lismenu[i]);
            map.put("img", iconlistmenu[i]);
            list.add(map);

        }
        return list;
    }



}
