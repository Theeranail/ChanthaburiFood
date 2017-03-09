package com.example.theeranaiasipong.chanthaburifood.model;

/**
 * Created by TheeranaiAsipong on 4/6/2559.
 */
public class Config {

    //Url ติดต่อกับ server
   // public static final String Baseurl =  "http://follow.16mb.com/JunthaburifoodBackend/";
    public static final String Baseurl =  "http://10.0.2.2/JunthaburifoodBackendM/"; //สำหรับ offline
  //  public static final String Baseurl =  "http://192.168.56.1/JunthaburifoodBackendM/";
    public static final String url_regist = Baseurl+"controller/Member.php";
    public static final String url_login = Baseurl+"controller/Member.php";
    public static final String url_getFood = Baseurl+"controller/Food.php";
    public static final String url_review = Baseurl+"controller/Review.php";

}
