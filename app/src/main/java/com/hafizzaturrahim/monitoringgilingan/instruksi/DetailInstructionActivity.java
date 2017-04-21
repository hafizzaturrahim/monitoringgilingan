package com.hafizzaturrahim.monitoringgilingan.instruksi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hafizzaturrahim.monitoringgilingan.Config;
import com.hafizzaturrahim.monitoringgilingan.CustomSpinnerAdapter;
import com.hafizzaturrahim.monitoringgilingan.R;
import com.hafizzaturrahim.monitoringgilingan.SessionManager;

import org.w3c.dom.Text;

public class DetailInstructionActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_instruction);

        SessionManager sessionManager = new SessionManager(this);
        String level = sessionManager.getLevel();
        Intent intent = getIntent();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Instruksi");
        pDialog = new ProgressDialog(this);

        id = intent.getStringExtra("id_ins");

        TextView txtTitle = (TextView) findViewById(R.id.txtTitleDetailIns);
        TextView txtRecipient = (TextView) findViewById(R.id.txtRecipientDetailIns);
        TextView txtContent = (TextView) findViewById(R.id.txtContentDetailIns);
        TextView txtStatus = (TextView) findViewById(R.id.txtStatusDetailIns);

        TextView txtColumnSender = (TextView) findViewById(R.id.txtSenderColumn);

        Button cancelBtn = (Button) findViewById(R.id.btnCancel);
        Button confirmBtn = (Button) findViewById(R.id.btnConfirm);
        Button finishBtn = (Button) findViewById(R.id.btnFinish);
        LinearLayout cancelLayout = (LinearLayout) findViewById(R.id.cancelLayout);

        if (level.equals("2")) {
            txtColumnSender.setText("Pengirim");
            finishBtn.setVisibility(View.VISIBLE);
            confirmBtn.setVisibility(View.VISIBLE);
        } else {
            if (intent.getStringExtra("status_ins").equals("1")) {
                cancelLayout.setVisibility(View.VISIBLE);
            }
        }

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        txtTitle.setText(intent.getStringExtra("judul_ins"));
        txtRecipient.setText(intent.getStringExtra("penerima_ins"));
        txtContent.setText(intent.getStringExtra("isi_ins"));
        if (intent.getStringExtra("status_ins").equals("4")) {
            txtStatus.setText("Dibatalkan");
        } else if (intent.getStringExtra("status_ins").equals("3")) {
            txtStatus.setText("Selesai dikerjakan");
        } else if (intent.getStringExtra("status_ins").equals("2")) {
            txtStatus.setText("Dikonfirmasi");
        } else {
            txtStatus.setText("Menunggu konfirmasi");
            txtStatus.setTextColor(0xFFFF3300);
        }


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

    private void requestData() {
        pDialog.setMessage("Memproses Data...");
        pDialog.show();
        /*Json Request*/
        String url = Config.base_url + "/deleteInstruction.php?id=" +id;

        Log.d("url : ", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        pDialog.dismiss();

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

        //add request to queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
