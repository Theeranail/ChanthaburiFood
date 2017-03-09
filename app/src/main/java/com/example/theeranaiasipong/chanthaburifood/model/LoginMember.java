package com.example.theeranaiasipong.chanthaburifood.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.theeranaiasipong.chanthaburifood.MainActivity;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import com.google.gson.reflect.TypeToken;


import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by TheeranaiAsipong on 4/6/2559.
 */
public class LoginMember extends AsyncTask<Object, Integer, List<RegisterMember.ListMember>> {

    Context context;
    List<RegisterMember.ListMember> users;
    ProgressDialog progress;
    private String Username;
    private String Password;
    SharedPreferences sp;



    public LoginMember(Context context, String Username, String Passwors) {
        this.context = context;
        this.Username = Username;
        this.Password = Passwors;
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
    protected List<RegisterMember.ListMember> doInBackground(Object... params) {

        try {

            Gson gson = new Gson();

            JsonHttp jsonHttp = new JsonHttp();
            String ressult = jsonHttp.sendData(Config.url_login, Username, Password);
            Log.e("ressult", ressult);
            Type listType = new TypeToken<List<RegisterMember.ListMember>>() {
            }.getType();
            List<RegisterMember.ListMember> posts = gson.fromJson(ressult, listType);

            return posts;
        } catch (RuntimeException e) {
            Log.e("RuntimeException", e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<RegisterMember.ListMember> ob) {
        super.onPostExecute(ob);

        if (progress.isShowing()) {
            progress.dismiss();
        }

        if (ob != null) {
            users = ob;

            if (users.get(0).getRs() == "") {
                Toast.makeText(context, "รหัสผ่านหรือชื่อผู้ใช้ไม่ถูกต้อง", Toast.LENGTH_LONG).show();
            } else {
                Log.e("username", users.get(0).getUsername());
                Log.e("Name", users.get(0).getName_member());
                Log.e("Id", users.get(0).getId_member());

                sp = context.getSharedPreferences(SharedPreferencesCheck.Shared_PreferencesName, Context.MODE_APPEND);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("usernamme", users.get(0).getName_member());
                editor.putString("user_id", users.get(0).getId_member());
                editor.commit();


                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);

            }

        } else {
            Toast.makeText(context, "No Internet Connect", Toast.LENGTH_LONG).show();
        }

    }


}
