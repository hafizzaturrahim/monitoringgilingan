package com.hafizzaturrahim.monitoringgilingan.instruksi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hafizzaturrahim.monitoringgilingan.Config;
import com.hafizzaturrahim.monitoringgilingan.MainActivity;
import com.hafizzaturrahim.monitoringgilingan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewInstructionActivity extends AppCompatActivity {

    Button confirmBtn;
    String penerima;
    String isi;
    String[] recipient;
    EditText edtContent;
    Spinner spinRecipient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_instruction);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtContent = (EditText) findViewById(R.id.edtNewInst);

        confirmBtn = (Button) findViewById(R.id.btnConfirmInstruction);
        spinRecipient = (Spinner) findViewById(R.id.spRecipient);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, recipient);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinRecipient.setAdapter(adapter);

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

//        penerima = edtRecipient.getText().toString();
        isi = edtContent.getText().toString();


        if (!penerima.equals("") && !isi.equals("")) {
            Intent intent = new Intent(NewInstructionActivity.this, MainActivity.class);
            intent.putExtra("menu", 3);
            startActivity(intent);
        } else {
            if (isi.equals("")) {
                edtContent.setError("Kolom harus diisi");
            }
        }

    }

//    private void requestData() {
//        pDialog.setMessage("Memproses Data...");
//        pDialog.show();
//        /*Json Request*/
//        String url = Config.base_url+ "/getInstruction.php?id=" +sessionManager.getIdLogin()+ "&level=" +sessionManager.getLevel();
//
//        Log.d("url : " ,url);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("response", response);
//                        parseJSON(response);
//                        InstructionAdapter adapter = new InstructionAdapter(getActivity(), instructions);
//                        listInstruction.setAdapter(adapter);
//                        pDialog.dismiss();
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        pDialog.dismiss();
//
//                        if (error != null) {
//                            error.printStackTrace();
//
//                        }
//                    }
//                });
//
//        //add request to queue
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        requestQueue.add(stringRequest);
//
//    }
//
//    private void parseJSON(String result) {
//        if (!result.contains("gagal")) {
//            try {
//                JSONObject data = new JSONObject(result);
//                JSONArray dataAr = data.getJSONArray("data");
//                for (int i = 0; i < dataAr.length(); i++) {
//                    JSONObject insObj = dataAr.getJSONObject(i);
//
//                    Instruction ins = new Instruction();
//                    ins.setTitleInstruction(insObj.getString("judul_instruksi"));
//                    ins.setDetailInstruction(insObj.getString("isi_instruksi"));
//                    ins.setRecipientInstruction(insObj.getString("username"));
//                    ins.setDateInstruction(insObj.getString("tgl"));
//                    ins.setStatusInsruction(insObj.getString("status"));
//
//                    instructions.add(ins);
//
//                }
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        } else {
//            txtNoMsg.setVisibility(View.VISIBLE);
//        }
//
//    }

}
