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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hafizzaturrahim.monitoringgilingan.karyawan.InstructionActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    Button loginBtn;
    TextView notifTxt;
    EditText usernameEdt, passEdt;

    String username, password, id = null;
    boolean isSuccess;

    String level = "0";

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(this);


        if (sessionManager.isLoggedIn()) {
            Intent intent;
            if ("1".equals(sessionManager.getLevel())) {
                FirebaseMessaging.getInstance().subscribeToTopic("spv");
                intent = new Intent(LoginActivity.this, MainActivity.class);
            } else {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("spv");
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
        password = MD5(passEdt.getText().toString());

        if (!username.equals("") && !password.equals("")) {
            if (username.matches("[a-zA-Z0-9.? ]*") && password.matches("[a-zA-Z0-9.? ]*")) {
                getDataUser();
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

    private void getDataUser() {
        pDialog.setMessage("Memproses Data...");
        pDialog.show();
        /*Json Request*/
        final String token = FirebaseInstanceId.getInstance().getToken();
        String url = Config.base_url + "/login.php?";
        Log.d("login", url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("login response", response);
                        parseJSON(response);
                        newActivity();
                        pDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Terjadi kesalahan, coba lagi", Toast.LENGTH_SHORT).show();
                        if (error != null) {
                            error.printStackTrace();

                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", username);
                params.put("pass",password);
                params.put("token",token);
                return params;
            }
        };
//(
//
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
                id = user.getString("id_user");
                level = user.getString("level");

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
            sessionManager.createLoginSession(username, id, level);
            Intent intent;
            if ("1".equals(level)) {
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

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

}
