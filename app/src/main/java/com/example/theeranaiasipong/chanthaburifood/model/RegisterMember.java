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

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by TheeranaiAsipong on 4/6/2559.
 */
public class RegisterMember  extends AsyncTask<Object, Integer, List<RegisterMember.ListMember>> {

    ProgressDialog progress;
    Context context;
    List<RegisterMember.ListMember> list;
    private String name;
    private String uername;
    private String pass;
    private String email;
    private String address;
    private String tel;
    SharedPreferences sp;

    public RegisterMember(Context context,String name,String uername,String pass,String email,String address,String tel) {
        this.context = context;
        this.name = name;
        this.uername = uername;
        this.pass = pass;
        this.email = email;
        this.address = address;
        this.tel = tel;
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
            JsonHttp jsonHttp = new JsonHttp();
            Gson gson = new Gson();
            String ressult = jsonHttp.Register(Config.url_regist, name, uername, pass, email, address, tel);
            Log.e("ressult", ressult);
            Type listType = new TypeToken<List<RegisterMember.ListMember>>() {
            }.getType();
            List<RegisterMember.ListMember> posts = gson.fromJson(ressult, listType);

            return posts;
        }catch (RuntimeException e){
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
            list = ob;

            Log.e("username", list.get(0).getUsername());
            Log.e("Name", list.get(0).getName_member());
            Log.e("Id", list.get(0).getId_member());

            sp = context.getSharedPreferences(SharedPreferencesCheck.Shared_PreferencesName,Context.MODE_APPEND);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("usernamme", list.get(0).getName_member());
            editor.putString("user_id", list.get(0).getId_member());
            editor.commit();

            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);

        }else{
            Toast.makeText(context, "No Internet Connect", Toast.LENGTH_LONG).show();
        }



    }


    public class ListMember implements Serializable{

        @SerializedName("id_member")
        private String id_member;
        @SerializedName("Username")
        private String Username;
        @SerializedName("Name_member")
        private String Name_member;
        @SerializedName("Email")
        private String Email;
        @SerializedName("Address")
        private String Address;
        @SerializedName("Tel")
        private String Tel;
        @SerializedName("rs")
        private String rs;


        private String Password;

        public String getPassword() {
            return Password;
        }

        public void setPassword(String password) {
            Password = password;
        }

        public ListMember() {
        }

        public String getId_member() {
            return id_member;
        }

        public void setId_member(String id_member) {
            this.id_member = id_member;
        }

        public String getName_member() {
            return Name_member;
        }

        public void setName_member(String name_member) {
            Name_member = name_member;
        }

        public String getUsername() {
            return Username;
        }

        public void setUsername(String username) {
            Username = username;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String tel) {
            Tel = tel;
        }
        public String getRs() {
            return rs;
        }

        public void setRs(String rs) {
            this.rs = rs;
        }

    }
}
