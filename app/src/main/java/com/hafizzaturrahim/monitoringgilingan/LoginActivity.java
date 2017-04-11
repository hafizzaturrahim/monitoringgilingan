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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hafizzaturrahim.monitoringgilingan.karyawan.InstructionActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    Button loginBtn;
    TextView notifTxt;
    EditText usernameEdt, passEdt;

    String username, password, id = null;
    boolean isSuccess;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(this);
        if (sessionManager.isLoggedIn()) {
            Intent intent;
            if ("1".equals(sessionManager.getIdLogin())) {
                intent = new Intent(LoginActivity.this, MainActivity.class);
            } else {
                intent = new Intent(LoginActivity.this, InstructionActivity.class);
            }
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_login);
        loginBtn = (Button) findViewById(R.id.btnlogin);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        notifTxt = (TextView) findViewById(R.id.notifLgn);
        usernameEdt = (EditText) findViewById(R.id.usernamelogin);
        passEdt = (EditText) findViewById(R.id.passwordlogin);
        pDialog = new ProgressDialog(this);

    }

    public void login() {

        username = usernameEdt.getText().toString();
        password = passEdt.getText().toString();

        if (!username.equals("") && !password.equals("")) {
            requestData();

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

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        parseJSON(response);
                        pDialog.dismiss();
                        newActivity();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        if (error != null) {
                            error.printStackTrace();

                        }
                    }
                });
//(
//            @Override
//            protected Map<String,String> getParams(){
////                Map<String,String> params = new HashMap<String, String>();
////
////                params.put("&code=",accessToken);
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "Basic " + base64);
//                params.put("Content-Type", "application/x-www-form-urlencoded");
//                params.put("Accept", "*/*" );
//                return super.getHeaders();
//            })

        //add request to queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void parseJSON(String result) {
        if (!result.contains("gagal")) {
            try {
                JSONObject data = new JSONObject(result);
                JSONArray dataAr = data.getJSONArray("data");
                JSONObject user = dataAr.getJSONObject(0);

                username = user.getString("username");
                password = user.getString("password");
                id = user.getString("level");

                Log.d("username : " + username, "id " + id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            isSuccess = true;
        } else {
            isSuccess = false;
        }

    }

    private void newActivity() {
        if (isSuccess) {
            sessionManager.createLoginSession(username, id);
            Intent intent;
            if ("1".equals(id)) {
                intent = new Intent(LoginActivity.this, MainActivity.class);
            } else {
                intent = new Intent(LoginActivity.this, InstructionActivity.class);
            }
            startActivity(intent);
            finish();
        } else {
            notifTxt.setVisibility(View.VISIBLE);
        }
    }

}
