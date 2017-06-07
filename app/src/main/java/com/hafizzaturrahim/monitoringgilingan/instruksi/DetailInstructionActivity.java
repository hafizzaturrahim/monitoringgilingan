package com.hafizzaturrahim.monitoringgilingan.instruksi;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import static com.hafizzaturrahim.monitoringgilingan.Config.convertDate;

public class DetailInstructionActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    String id, status, judul, isi_pesan, isi_penolakan;
    SessionManager sessionManager;

    TextView txtRejection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_instruction);

        sessionManager = new SessionManager(this);
        String level = sessionManager.getLevel();
        Intent intent = getIntent();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Instruksi");
        pDialog = new ProgressDialog(this);

        id = intent.getStringExtra("id_ins");
        requestData();

        status = intent.getStringExtra("status_ins");
        TextView txtTitle = (TextView) findViewById(R.id.txtTitleDetailIns);
        TextView txtRecipient = (TextView) findViewById(R.id.txtRecipientDetailIns);
        TextView txtContent = (TextView) findViewById(R.id.txtContentDetailIns);
        TextView txtStatus = (TextView) findViewById(R.id.txtStatusDetailIns);
        TextView txtColumnSender = (TextView) findViewById(R.id.txtSenderColumn);

        Button confirmBtn = (Button) findViewById(R.id.btnConfirm);
        Button rejectBtn = (Button) findViewById(R.id.btnReject);
        Button finishBtn = (Button) findViewById(R.id.btnFinish);
        LinearLayout cancelLayout = (LinearLayout) findViewById(R.id.cancelLayout);
        LinearLayout rejectLayout = (LinearLayout) findViewById(R.id.rejectLayout);

        if (level.equals("2")) {
            txtColumnSender.setText("Pengirim");
        }

        switch (status) {
            case "1":
                txtStatus.setText("Menunggu konfirmasi");
                txtStatus.setTextColor(0xFF3F51B5);
                if (level.equals("2")) {
                    confirmBtn.setVisibility(View.VISIBLE);
                    rejectBtn.setVisibility(View.VISIBLE);
                } else {
                    cancelLayout.setVisibility(View.VISIBLE);
                }
                break;
            case "2":
                txtStatus.setText("Dikonfirmasi");
                txtStatus.setTextColor(0xFF3F51B5);
                if (level.equals("2")) {
                    finishBtn.setVisibility(View.VISIBLE);
                }
                break;
            case "3":
                txtStatus.setText("Selesai dikerjakan");
                txtStatus.setTextColor(0xFF4CAF50);
                break;
            case "4":
                txtStatus.setText("Ditolak");
                txtStatus.setTextColor(0xFFFF3300);
                if (level.equals("2")) {
                    rejectLayout.setVisibility(View.VISIBLE);
                    txtRejection = (TextView) findViewById(R.id.txtMsgReject);

                }
                break;
        }

        txtTitle.setText(intent.getStringExtra("judul_ins"));
        txtRecipient.setText(intent.getStringExtra("penerima_ins"));
        txtContent.setText(intent.getStringExtra("isi_ins"));
    }

    public void acceptInstruction(View view) {
        changeStatus("2");
    }

    public void finishInstruction(View view) {
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

    public void rejectInstruction(View view) {
        new MaterialDialog.Builder(this)
                .title("Penolakan")
                .content("Anda akan menolak instruksi. Pastikan untuk memberikan alasan yang jelas terhadap penolakan tersebut")
                .inputType(
                        InputType.TYPE_CLASS_TEXT
                                | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .positiveText("Kirim")
                .negativeText("Batal")
                .input(
                        null,
                        null,
                        false,
                        new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                String message = input.toString();
                                changeStatus("4", message);
//                                Toast.makeText(DetailInstructionActivity.this, "isinya" +message, Toast.LENGTH_SHORT).show();
                            }
                        })
                .show();
    }

    public void cancelInstruction(View view) {
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
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

    private void changeStatus(final String status) {
        pDialog.setMessage("Memproses Data...");
        pDialog.show();
        /*Json Request*/
        String url = Config.base_url + "/changeStatus.php?";

        Log.d("url : ", url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        pDialog.dismiss();
                        returnActivity();
                        Toast.makeText(DetailInstructionActivity.this, "Status instruksi berhasil diubah", Toast.LENGTH_SHORT).show();

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
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("status", status);
                return params;
            }
        };

        //add request to queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void changeStatus(final String status, final String msg) {
        pDialog.setMessage("Memproses Data...");
        pDialog.show();
        /*Json Request*/
        String url = Config.base_url + "/changeStatus.php?";

        Log.d("url : ", url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
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
                        returnActivity();
                        if (error != null) {
                            error.printStackTrace();

                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("status", status);
                params.put("pesan", msg);
                return params;
            }
        };

        //add request to queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void requestData() {
        /*Json Request*/
        pDialog.setMessage("Memproses Data...");
        pDialog.show();
        String url = Config.base_url + "/getDetailInstruction.php?id=" + id;

        Log.d("url : ", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        parseJSON(response);
                        if (status.equals("4")) {
                            txtRejection.setText(isi_penolakan);
                        }
                        pDialog.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        Toast.makeText(DetailInstructionActivity.this, "Terjadi kesalahan, coba lagi", Toast.LENGTH_SHORT).show();
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
                for (int i = 0; i < dataAr.length(); i++) {
                    JSONObject insObj = dataAr.getJSONObject(i);
                    String jenis = insObj.getString("jenis_pesan");
                    if (jenis.equals("2")) {
                        isi_penolakan = insObj.getString("isi_pesan");
                        Log.d("penolakan", isi_penolakan);
                    }


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {

        }

    }

    private void returnActivity() {
        Intent intent = new Intent(DetailInstructionActivity.this, InstructionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
