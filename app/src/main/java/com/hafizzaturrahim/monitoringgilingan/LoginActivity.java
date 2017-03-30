package com.hafizzaturrahim.monitoringgilingan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    TextView notifTxt;
    EditText usernameEdt,passEdt;

    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button) findViewById(R.id.btnlogin);
        notifTxt = (TextView) findViewById(R.id.notifLgn);
        usernameEdt = (EditText) findViewById(R.id.usernamelogin);
        passEdt = (EditText) findViewById(R.id.passwordlogin);
        usernameEdt.setError("Username harus diisi");
        passEdt.setError("Password harus diisi");


    }

    public void login(View view) {

        username = usernameEdt.getText().toString();
        password = passEdt.getText().toString();

        if (username.equals("") || password.equals("")) {

        } else {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

}
