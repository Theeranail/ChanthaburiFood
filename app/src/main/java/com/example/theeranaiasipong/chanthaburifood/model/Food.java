package com.example.theeranaiasipong.chanthaburifood.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.theeranaiasipong.chanthaburifood.R;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;


/**
 * Created by TheeranaiAsipong on 4/6/2559.
 */
public class Food extends AsyncTask<Object, Integer, List<Food.listFood>> {

    ProgressDialog progress;
    Context context;
    public static List<Food.listFood> listFoods;

    public Food(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

        progress = new ProgressDialog(context);
        progress.setTitle("Please wait");
        progress.setMessage("Loading please wait...");
        progress.setCancelable(false);
        progress.show();
    }

    @Override
    protected List<Food.listFood> doInBackground(Object... params) {
        JsonHttp jsonHttp = new JsonHttp();
        Gson gson = new Gson();
        String ressult = jsonHttp.LoadFood();
        Log.e("ressult", ressult);
        Type listType = new TypeToken<List<Food.listFood>>() {
        }.getType();
        List<Food.listFood> posts = gson.fromJson(ressult, listType);

        return posts;
    }

    @Override
    protected void onPostExecute(List<Food.listFood> ob) {
        super.onPostExecute(ob);

        if (progress.isShowing()) {
            progress.dismiss();
        }
        if (ob != null) {
            listFoods = ob;
            Toast.makeText(context, listFoods.get(0).getName_food(), Toast.LENGTH_LONG).show();
        }
    }

    public class listFood implements Serializable {

        @SerializedName("id_food")
        private int id_food;
        @SerializedName("name_food")
        private String name_food;
        @SerializedName("address")
        private String address;
        @SerializedName("detail")
        private String detail;
        @SerializedName("lattitude")
        private String lattitude;
        @SerializedName("logtitude")
        private String logtitude;
        @SerializedName("img_food")
        private String img_food;
        @SerializedName("tel")
        private String tel;
        @SerializedName("price")
        private String price;
        @SerializedName("Timeservie")
        private String Timeservie;
        @SerializedName("detail_orther")
        private String detail_orther;
        @SerializedName("score")
        private String score;
        @SerializedName("linkcontact")
        private String linkcontact;
        @SerializedName("link")
        private String link;

        public listFood() {

        }

        public int getId_food() {
            return id_food;
        }

        public void setId_food(int id_food) {
            this.id_food = id_food;
        }

        public String getName_food() {
            return name_food;
        }

        public void setName_food(String name_food) {
            this.name_food = name_food;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getLattitude() {
            return lattitude;
        }

        public void setLattitude(String lattitude) {
            this.lattitude = lattitude;
        }

        public String getLogtitude() {
            return logtitude;
        }

        public void setLogtitude(String logtitude) {
            this.logtitude = logtitude;
        }

        public String getImg_food() {
            return img_food;
        }

        public void setImg_food(String img_food) {
            this.img_food = img_food;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTimeservie() {
            return Timeservie;
        }

        public void setTimeservie(String timeservie) {
            Timeservie = timeservie;
        }

        public String getDetail_orther() {
            return detail_orther;
        }

        public void setDetail_orther(String detail_orther) {
            this.detail_orther = detail_orther;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getLinkcontact() {
            return linkcontact;
        }

        public void setLinkcontact(String linkcontact) {
            this.linkcontact = linkcontact;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }

    public class ListNenu implements Serializable{

        @SerializedName("id_menu")
        private String id_menu;
        @SerializedName("listmenu")
        private String listmenu;
        @SerializedName("price")
        private String price;
        @SerializedName("img_menu")
        private String img_menu;

        public String getId_menu() {
            return id_menu;
        }

        public void setId_menu(String id_menu) {
            this.id_menu = id_menu;
        }

        public String getListmenu() {
            return listmenu;
        }

        public void setListmenu(String listmenu) {
            this.listmenu = listmenu;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getImg_menu() {
            return img_menu;
        }

        public void setImg_menu(String img_menu) {
            this.img_menu = img_menu;
        }

        public ListNenu(){}


    }

    public class LisImg implements Serializable{

        private String id_imgfood;
        private String id_food;
        private String img_name;
        private String img_path;

        public String getId_imgfood() {
            return id_imgfood;
        }

        public void setId_imgfood(String id_imgfood) {
            this.id_imgfood = id_imgfood;
        }

        public String getId_food() {
            return id_food;
        }

        public void setId_food(String id_food) {
            this.id_food = id_food;
        }

        public String getImg_name() {
            return img_name;
        }

        public void setImg_name(String img_name) {
            this.img_name = img_name;
        }

        public String getImg_path() {
            return img_path;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }

        public LisImg(){

        }
    }
    public class Checkvote implements Serializable{

        @SerializedName("st")
        private String st;
        @SerializedName("msg")
        private String msg;

        public String getSt() {
            return st;
        }

        public void setSt(String st) {
            this.st = st;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Checkvote(){

        }

    }


}
