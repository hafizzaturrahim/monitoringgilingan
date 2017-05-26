package com.hafizzaturrahim.monitoringgilingan.instruksi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hafizzaturrahim.monitoringgilingan.Config;
import com.hafizzaturrahim.monitoringgilingan.CustomSpinnerAdapter;
import com.hafizzaturrahim.monitoringgilingan.ItemSpinner;
import com.hafizzaturrahim.monitoringgilingan.MainActivity;
import com.hafizzaturrahim.monitoringgilingan.R;
import com.hafizzaturrahim.monitoringgilingan.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewInstructionActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    Button confirmBtn;
    String judul, penerima, isi;

    ItemSpinner[] recipient;
    EditText edtTitle, edtContent;
    Spinner spinRecipient;


    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_instruction);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pDialog = new ProgressDialog(this);
        sessionManager = new SessionManager(this);
        edtTitle = (EditText) findViewById(R.id.edtNewTitle);
        edtContent = (EditText) findViewById(R.id.edtNewInst);
        confirmBtn = (Button) findViewById(R.id.btnConfirmInstruction);
        spinRecipient = (Spinner) findViewById(R.id.spRecipient);

        spinRecipient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                ItemSpinner selected = (ItemSpinner) (parentView.getItemAtPosition(position));
                penerima = String.valueOf(selected.getValue());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
        requestData();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void confirmInstruction(View view) {
        judul = edtTitle.getText().toString();
        isi = edtContent.getText().toString();

        if (!judul.equals("") && !isi.equals("")) {
            sendData();

        } else {
            if (judul.equals("")) {
                edtTitle.setError("Kolom harus diisi");
            }
            if (isi.equals("")) {
                edtContent.setError("Kolom harus diisi");
            }
        }

    }

    private void requestData() {
        pDialog.setMessage("Memproses Data...");
        pDialog.show();
        pDialog.setCancelable(false);
        /*Json Request*/
        String url = Config.base_url + "/getUser.php?level=2";

        Log.d("url : ", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        parseJSON(response);
                        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(NewInstructionActivity.this, android.R.layout.simple_spinner_item, recipient);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinRecipient.setAdapter(adapter);
                        pDialog.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        Toast.makeText(NewInstructionActivity.this, "Terjadi kesalahan, coba lagi", Toast.LENGTH_SHORT).show();
                        if (error != null) {
                            error.printStackTrace();

                        }
                    }
                });

        //add request to queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void parseJSON(String result) {
        if (!result.contains("gagal")) {
            try {
                JSONObject data = new JSONObject(result);
                JSONArray dataAr = data.getJSONArray("data");
                List<ItemSpinner> itemSpinners = new ArrayList<>();
                for (int i = 0; i < dataAr.length(); i++) {
                    JSONObject recObj = dataAr.getJSONObject(i);
                    ItemSpinner item = new ItemSpinner(recObj.getString("username"), recObj.getString("id_user"));
                    itemSpinners.add(item);
                }
                recipient = new ItemSpinner[itemSpinners.size()];
                itemSpinners.toArray(recipient);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {

        }

    }

    private void sendData() {
        pDialog.setMessage("Mengirim Data...");
        pDialog.show();

        String url = Config.base_url + "/newInstruction.php?";
//        judul=" +judul+"&isi="+isi+"&pengirim="+sessionManager.getIdLogin()+"&penerima=" +penerima
        Log.d("url : ", url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Intent intent = new Intent(NewInstructionActivity.this, MainActivity.class);
                        intent.putExtra("menu", 3);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                        // Add new Flag to start new Activity
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("judul", judul);
                params.put("isi", isi);
                params.put("pengirim", sessionManager.getIdLogin());
                params.put("penerima", penerima);
                Log.d("datane ",judul+ " "+isi+ " " +penerima);
                return params;
            }
        };

        //add request to queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
