package com.example.theeranaiasipong.chanthaburifood.model;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by TheeranaiAsipong on 4/6/2559.
 */
public class JsonHttp {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient okHttpClient;
    public String resulst = "";

    public String getResulst() {
        return resulst;
    }

    public void setResulst(String resulst) {
        this.resulst = resulst;
    }

    public JsonHttp() {

    }


    public String sendData(String url, String Username, String password) {
        okHttpClient = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("function", "Login")
                .add("Username", Username)
                .add("Password", password)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            resulst = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resulst;
    }

    public String Register(String url, String Name_member, String Username, String password, String Email, String Address, String Tel) {

        okHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("function", "Register")
                .add("Username", Username)
                .add("Password", password)
                .add("Name_member", Name_member)
                .add("Email", Email)
                .add("Address", Address)
                .add("Tel", Tel)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            resulst = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resulst;

    }

    public String getData(String url, String json) {

        okHttpClient = new OkHttpClient();


        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();


        return resulst;
    }

    public String LoadFood() {

        okHttpClient = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("function", "getLisfood")
                .build();
        Request request = new Request.Builder()
                .url(Config.url_getFood)
                .post(formBody)
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            resulst = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resulst;
    }

    public String LoadListMenu(int id_food) {

        okHttpClient = new OkHttpClient();
        String id_foods = String.valueOf(id_food);
        RequestBody formbody = new FormBody.Builder()
                .add("function", "getListMenu")
                .add("id_food", id_foods)
                .build();
        Request request = new Request.Builder()
                .url(Config.url_getFood)
                .post(formbody)
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            resulst = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resulst;
    }

    public String LoadReviewMyfood(int id_food) {

        okHttpClient = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("function", "LoadReviewMyfood")
                .add("id_food", String.valueOf(id_food))
                .build();
        Request request = new Request.Builder()
                .url(Config.url_getFood)
                .post(formBody)
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            resulst = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resulst;
    }

    public String Loadcountreview(int id_food){
        okHttpClient = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("function", "getCountReview")
                .add("id_food", String.valueOf(id_food))
                .build();
        Request request = new Request.Builder()
                .url(Config.url_getFood)
                .post(formBody)
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            resulst = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resulst;
    }

    public String SyncTaskOkHttp(RequestBody requestBody, String url) {

        okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            resulst = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resulst;

    }

}
