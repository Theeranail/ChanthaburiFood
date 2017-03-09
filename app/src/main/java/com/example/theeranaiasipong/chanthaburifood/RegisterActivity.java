package com.example.theeranaiasipong.chanthaburifood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.theeranaiasipong.chanthaburifood.model.CheckAccessInternet;
import com.example.theeranaiasipong.chanthaburifood.model.CreateFuction;
import com.example.theeranaiasipong.chanthaburifood.model.RegisterMember;

public class RegisterActivity extends AppCompatActivity {


    private EditText name, username, pass, email, address, tel;
    private CheckAccessInternet cAn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button btnRegis = (Button) findViewById(R.id.btn_register);
        name = (EditText) findViewById(R.id.edit_fullname);
        username = (EditText) findViewById(R.id.edit_usernameR);
        pass = (EditText) findViewById(R.id.edit_passwordR);
        email = (EditText) findViewById(R.id.edit_emailR);
        address = (EditText) findViewById(R.id.edit_AddressR);
        tel = (EditText) findViewById(R.id.edit_TelR);

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });

    }

    private void Register() {

        if (name.getText().toString().equals("") && username.getText().toString().equals("") && pass.getText().toString().equals("") && email.getText().toString().equals("") && address.getText().toString().equals("") && tel.getText().toString().equals("")) {
            CreateFuction createFuction = new CreateFuction(RegisterActivity.this);
            createFuction.AlertDialogOK("กรอกข้อมูลให้ครบ");
        } else {
            cAn = new CheckAccessInternet(RegisterActivity.this);
            if (cAn.isConnectNet()) {
                RegisterMember registerparam = new RegisterMember(RegisterActivity.this, name.getText().toString(), username.getText().toString(), pass.getText().toString(), email.getText().toString(), address.getText().toString(), tel.getText().toString());
                registerparam.execute();
            }else{
                Toast.makeText(RegisterActivity.this, "No Internet Connected", Toast.LENGTH_LONG).show();
            }
        }

    }
}
