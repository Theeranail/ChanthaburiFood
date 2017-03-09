package com.example.theeranaiasipong.chanthaburifood;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.theeranaiasipong.chanthaburifood.model.CheckAccessInternet;
import com.example.theeranaiasipong.chanthaburifood.model.CreateFuction;
import com.example.theeranaiasipong.chanthaburifood.model.LoginMember;
import com.example.theeranaiasipong.chanthaburifood.model.SharedPreferencesCheck;


public class LoginActivity extends AppCompatActivity {

    private EditText editTextUser, editTextPass;
    String textUsername = "";
    String textPassword = "";
    private SharedPreferencesCheck sp;
    private CheckAccessInternet cAn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sp = new SharedPreferencesCheck(LoginActivity.this);
        sp.checksharedPreLogin();

        Button btn_login = (Button) findViewById(R.id.bun_login);
        editTextUser = (EditText) findViewById(R.id.editText_username);
        editTextPass = (EditText) findViewById(R.id.editText_password);

        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                textUsername = editTextUser.getText().toString();
                textPassword = editTextPass.getText().toString();

                if (textUsername.equals("") && textPassword.equals("")) {
                   CreateFuction createFuction = new CreateFuction(LoginActivity.this);
                    createFuction.AlertDialogOK("กรอกข้อมูลให้ครบ");
                } else {
                    cAn = new CheckAccessInternet(LoginActivity.this);
                    if (cAn.isConnectNet()) {
                        LoginMember loginMember = new LoginMember(LoginActivity.this, editTextUser.getText().toString(), editTextPass.getText().toString());
                        loginMember.execute();
                    }else{
                        Toast.makeText(LoginActivity.this, "No Internet Connected", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        TextView textRegister = (TextView) findViewById(R.id.infoTxtCredits);
        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}
