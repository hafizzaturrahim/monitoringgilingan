package com.hafizzaturrahim.monitoringgilingan.instruksi;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
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
import com.hafizzaturrahim.monitoringgilingan.MainActivity;
import com.hafizzaturrahim.monitoringgilingan.R;
import com.hafizzaturrahim.monitoringgilingan.SessionManager;
import com.hafizzaturrahim.monitoringgilingan.karyawan.InstructionActivity;

import org.w3c.dom.Text;

public class DetailInstructionActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    String id, status;

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
        status = intent.getStringExtra("status_ins");
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
            if (status.equals("1")) {
                confirmBtn.setVisibility(View.VISIBLE);
            } else if (status.equals("2")) {
                finishBtn.setVisibility(View.VISIBLE);
            }
        } else {
            if (status.equals("1")) {
                cancelLayout.setVisibility(View.VISIBLE);
            }
        }

//        if (status.equals("4")) {
//            txtStatus.setText("Dibatalkan");
//        }
        if (status.equals("3")) {
            txtStatus.setText("Selesai dikerjakan");
            txtStatus.setTextColor(0xFF4CAF50);
        } else if (status.equals("2")) {
            txtStatus.setText("Dikonfirmasi");
            txtStatus.setTextColor(0xFF3F51B5);
        } else {
            txtStatus.setText("Menunggu konfirmasi");
            txtStatus.setTextColor(0xFFFF3300);
        }
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(DetailInstructionActivity.this);
                alert.setTitle("Konfirmasi Pembatalan");
                alert.setMessage("Apakah anda akan membatalkan instruksi ini?");
                alert.setNegativeButton("Tidak",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        });
                alert.setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                deleteData();
                                dialog.dismiss();
                            }
                        });

                alert.show();

            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatus("2");
            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(DetailInstructionActivity.this);
                alert.setTitle("Selesai");
                alert.setMessage("Apakah anda yakin telah melaksanakan instruksi ini?");
                alert.setNegativeButton("Tidak",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        });
                alert.setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                changeStatus("3");
                                dialog.dismiss();
                            }
                        });

                alert.show();

            }
        });

        txtTitle.setText(intent.getStringExtra("judul_ins"));
        txtRecipient.setText(intent.getStringExtra("penerima_ins"));
        txtContent.setText(intent.getStringExtra("isi_ins"));


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

    private void deleteData() {
        pDialog.setMessage("Memproses Data...");
        pDialog.show();
        /*Json Request*/
        String url = Config.base_url + "/deleteInstruction.php?id=" + id;

        Log.d("url : ", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        pDialog.dismiss();
                        Intent intent = new Intent(DetailInstructionActivity.this, MainActivity.class);
                        intent.putExtra("menu", 3);
                        startActivity(intent);
                        finish();

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

    private void changeStatus(String status) {
        pDialog.setMessage("Memproses Data...");
        pDialog.show();
        /*Json Request*/
        String url = Config.base_url + "/changeStatus.php?id=" + id + "&status=" + status;

        Log.d("url : ", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        pDialog.dismiss();
                        Intent intent = new Intent(DetailInstructionActivity.this, InstructionActivity.class);
                        startActivity(intent);
                        finish();

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
