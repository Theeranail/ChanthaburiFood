package com.example.theeranaiasipong.chanthaburifood.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.theeranaiasipong.chanthaburifood.LoginActivity;
import com.example.theeranaiasipong.chanthaburifood.MainActivity;

/**
 * Created by TheeranaiAsipong on 6/6/2559.
 */
public class SharedPreferencesCheck {

    private static Context context;
    private static SharedPreferences sharedPreferences;
    public static final String Shared_PreferencesName = "mySharedpreferences";

    public static  String Name_member;
    public static  String id_user;

    public SharedPreferencesCheck(Context context) {
        this.context = context;

        StartSpf();

    }

    private void StartSpf() {
        sharedPreferences = context.getSharedPreferences(Shared_PreferencesName, Context.MODE_PRIVATE);
        Name_member = sharedPreferences.getString("Name_member", "not found!");
        id_user = sharedPreferences.getString("id_user", "not found!");
    }

    public void checksharedPre() {

        Name_member = sharedPreferences.getString("usernamme", "not found!");
        id_user = sharedPreferences.getString("user_id", "not found!");
        if (Name_member == "not found!" && id_user == "not found!") {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }

    }
    public void checksharedPreLogin(){

        Name_member = sharedPreferences.getString("usernamme", "not found!");
        id_user = sharedPreferences.getString("user_id", "not found!");
        if (Name_member != "not found!" && id_user != "not found!") {
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }

    }

    public static void Logout(){
        sharedPreferences = context.getSharedPreferences(Shared_PreferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

    }

    public String getName_member() {
        return Name_member;
    }

    public String getId_user() {
        return id_user;
    }

}
