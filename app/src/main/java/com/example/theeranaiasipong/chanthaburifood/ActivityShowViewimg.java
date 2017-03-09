package com.example.theeranaiasipong.chanthaburifood;

import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.theeranaiasipong.chanthaburifood.model.ReviewFood;

import java.util.ArrayList;
import java.util.List;

public class ActivityShowViewimg extends AppCompatActivity {

    public ArrayList<String> arrayList;
    public ViewPager viewPager;
    public PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_viewimg);

        Bundle bundle = getIntent().getExtras();
        arrayList = new ArrayList<String>();
        arrayList = bundle.getStringArrayList("arrayImg");


        viewPager = (ViewPager) findViewById(R.id.viewPagerimgpager);

        pagerAdapter = new ImagesSlideReview(ActivityShowViewimg.this, arrayList);
        viewPager.setAdapter(pagerAdapter);

    }


    public class ImagesSlideReview extends PagerAdapter {

        public Context context;
        public ArrayList<String> lisImgs;
        LayoutInflater inflater;

        public ImagesSlideReview(Context context, ArrayList<String> lisImgs) {
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

            imgflag = (ImageView) view.findViewById(R.id.imgViewslide);

            Glide.with(context)
                    .load(lisImgs.get(position))
                    .placeholder(R.drawable.ic_panorama_black_24dp)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgflag);

            ((ViewPager) container).addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((RelativeLayout) object);
        }
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(ActivityShowViewimg.this);
    }
}
