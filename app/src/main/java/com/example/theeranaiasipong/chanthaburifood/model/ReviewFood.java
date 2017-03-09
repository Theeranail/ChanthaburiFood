package com.example.theeranaiasipong.chanthaburifood.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.theeranaiasipong.chanthaburifood.R;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by TheeranaiAsipong on 12/6/2559.
 */
public class ReviewFood {

    public Context context;
    public ProgressDialog progress;
    public List<ReviewFood.ListReviewMyfood> listReview;

    public ReviewFood(Context context) {
        this.context = context;
    }


    public class ListReviewMyfood implements Serializable {

        @SerializedName("id_reviewpost")
        private String id_reviewpost;
        @SerializedName("id_food")
        private String id_food;
        @SerializedName("id_member")
        private String id_member;
        @SerializedName("name_member")
        private String name_member;
        @SerializedName("time_review")
        private String time_review;
        @SerializedName("title_name")
        private String title_name;
        @SerializedName("price")
        private String price;
        @SerializedName("recommended_menu")
        private String recommended_menu;
        @SerializedName("detail")
        private String detail;
        @SerializedName("ImgReview")
        private List<ReviewFood.ListImgReview> ImgReview;


        public ListReviewMyfood() {
        }

        public String getId_reviewpost() {
            return id_reviewpost;
        }

        public void setId_reviewpost(String id_reviewpost) {
            this.id_reviewpost = id_reviewpost;
        }

        public String getId_food() {
            return id_food;
        }

        public void setId_food(String id_food) {
            this.id_food = id_food;
        }

        public String getId_member() {
            return id_member;
        }

        public String getName_member() {
            return name_member;
        }

        public void setName_member(String name_member) {
            this.name_member = name_member;
        }

        public void setId_member(String id_member) {
            this.id_member = id_member;
        }

        public String getTime_review() {
            return time_review;
        }

        public void setTime_review(String time_review) {
            this.time_review = time_review;
        }

        public String getTitle_name() {
            return title_name;
        }

        public void setTitle_name(String title_name) {
            this.title_name = title_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getRecommended_menu() {
            return recommended_menu;
        }

        public void setRecommended_menu(String recommended_menu) {
            this.recommended_menu = recommended_menu;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public List<ListImgReview> getImgReview() {
            return ImgReview;
        }

        public void setImgReview(List<ListImgReview> imgReview) {
            ImgReview = imgReview;
        }
    }

    public class ListImgReview implements Serializable {

        private String id_imgreviewpost;
        private String id_reviewpost;
        private String img_path;

        public ListImgReview() {
        }

        public String getId_imgreviewpost() {
            return id_imgreviewpost;
        }

        public void setId_imgreviewpost(String id_imgreviewpost) {
            this.id_imgreviewpost = id_imgreviewpost;
        }

        public String getId_reviewpost() {
            return id_reviewpost;
        }

        public void setId_reviewpost(String id_reviewpost) {
            this.id_reviewpost = id_reviewpost;
        }

        public String getImg_path() {
            return img_path;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }
    }

    public class ResultPost implements Serializable {
        @SerializedName("rs")
        private String rs;
        @SerializedName("msg")
        private String msg;

        public String getRs() {
            return rs;
        }

        public void setRs(String rs) {
            this.rs = rs;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public ResultPost() {
        }

    }

    public static class CreviewShowviewimg extends BaseAdapter {

        Context context;
        ArrayList<Uri> list;
        LayoutInflater inflater;

        public CreviewShowviewimg(Context context, ArrayList<Uri> list) {
            this.context = context;
            this.list = list;

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.content_img_view, null);
            }

            ImageView imageView = (ImageView)convertView.findViewById(R.id.img_view);
Log.e("list.get(position)", String.valueOf(list.get(position)));
            Glide.with(context)
                    .load(list.get(position))
                    .placeholder(R.drawable.ic_panorama_black_24dp)
                    .error(R.drawable.ic_panorama_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .override(300, 200)
                    .into(imageView);

            return convertView;
        }
    }


}
