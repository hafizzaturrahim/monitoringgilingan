package com.hafizzaturrahim.monitoringgilingan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    TextView notifTxt;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button) findViewById(R.id.btnlogin);
        notifTxt = (TextView) findViewById(R.id.notifLgn);
        EditText usernameEdt = (EditText) findViewById(R.id.usernamelogin);
        EditText passEdt = (EditText) findViewById(R.id.passwordlogin);
        username = usernameEdt.getText().toString();
        password = passEdt.getText().toString();

    }

    public void login() {
        if (username.isEmpty() || password.isEmpty()) {

        } else {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }

}
