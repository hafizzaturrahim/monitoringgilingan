package com.hafizzaturrahim.monitoringgilingan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.hafizzaturrahim.monitoringgilingan.karyawan.InstructionActivity;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class LoginActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    Button loginBtn;
    TextView notifTxt;
    EditText usernameEdt, passEdt;

    String username, password;
    String id = "2";

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(this);
        Intent intent;
        if (sessionManager.isLoggedIn()) {
            if (sessionManager.getIdLogin().equals("1")) {
                intent = new Intent(LoginActivity.this, MainActivity.class);
            } else {
                intent = new Intent(LoginActivity.this, InstructionActivity.class);
            }
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_login);
        loginBtn = (Button) findViewById(R.id.btnlogin);
        notifTxt = (TextView) findViewById(R.id.notifLgn);
        usernameEdt = (EditText) findViewById(R.id.usernamelogin);
        passEdt = (EditText) findViewById(R.id.passwordlogin);
        pDialog = new ProgressDialog(this);


    }

    public void login(View view) {

        username = usernameEdt.getText().toString();
        password = passEdt.getText().toString();


        if (!username.equals("") && !password.equals("")) {
            requestData();

            if (username.equals("tes") && password.equals("tes")) {
                sessionManager.createLoginSession(username, id);
                Intent intent;
                if (id.equals("1")) {
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(LoginActivity.this, InstructionActivity.class);
                }
                startActivity(intent);
                finish();
            } else {
                notifTxt.setVisibility(View.VISIBLE);
            }

        } else {
            if (username.equals("")) {
                usernameEdt.setError("Username harus diisi");
            } else if (password.equals("")) {
                passEdt.setError("Password harus diisi");
            }
        }

    }

    private void requestData() {

        pDialog.setMessage("Memproses Data...");
        pDialog.show();
        /*Json Request*/
        String url = "http://192.168.137.1/gilinganlocal/login.php?name=" + username + "&pass=" + password;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(LoginActivity.this, "berhasil", Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        String body = null;
//                        //get status code here
//                        String statusCode = String.valueOf(error.networkResponse.statusCode);
//                        //get response body and parse with appropriate encoding
//                        if(error.networkResponse.data!=null) {
//                            try {
//                                body = new String(error.networkResponse.data,"UTF-8");
//                            } catch (UnsupportedEncodingException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        Toast.makeText(getActivity(), "Error " +statusCode+ " message " +body, Toast.LENGTH_SHORT).show();
                    }
                });
        //add request to queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }

}
