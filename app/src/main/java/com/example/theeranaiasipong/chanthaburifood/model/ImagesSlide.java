package com.example.theeranaiasipong.chanthaburifood.model;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.theeranaiasipong.chanthaburifood.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TheeranaiAsipong on 13/6/2559.
 */
public class ImagesSlide extends PagerAdapter {

    public Context context;
    public List<Food.LisImg> lisImgs;
    LayoutInflater inflater;

    public ImagesSlide(Context context, List<Food.LisImg> lisImgs) {
        this.context = context;
        this.lisImgs = lisImgs;
    }

    @Override
    public int getCount() {
        return lisImgs.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == ((RelativeLayout) object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imgflag;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.content_slideimages_food, container, false);

        imgflag = (ImageView)view.findViewById(R.id.imgViewslide);

        Glide.with(context)
                .load(lisImgs.get(position).getImg_path())
                .placeholder(R.drawable.ic_panorama_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgflag);

        ((ViewPager) container).addView(view);

        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        ((ViewPager) container).removeView((RelativeLayout) object);
    }
}


