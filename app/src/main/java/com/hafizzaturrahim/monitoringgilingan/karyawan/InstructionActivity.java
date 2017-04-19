package com.hafizzaturrahim.monitoringgilingan.karyawan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hafizzaturrahim.monitoringgilingan.Config;
import com.hafizzaturrahim.monitoringgilingan.LoginActivity;
import com.hafizzaturrahim.monitoringgilingan.MainActivity;
import com.hafizzaturrahim.monitoringgilingan.R;
import com.hafizzaturrahim.monitoringgilingan.SessionManager;
import com.hafizzaturrahim.monitoringgilingan.instruksi.DetailInstructionActivity;
import com.hafizzaturrahim.monitoringgilingan.instruksi.Instruction;
import com.hafizzaturrahim.monitoringgilingan.instruksi.InstructionAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InstructionActivity extends AppCompatActivity {
    ListView listInstruction;
    ArrayList<Instruction> instructions = new ArrayList<>();;
    SessionManager sessionManager;
    private ProgressDialog pDialog;
    TextView txtNoMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        sessionManager = new SessionManager(this);
        pDialog = new ProgressDialog(this);

        listInstruction = (ListView) findViewById(R.id.lvInstruction2);
        txtNoMsg = (TextView) findViewById(R.id.txtNoMessage);
        requestData();
        listInstruction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(InstructionActivity.this, DetailInstructionActivity.class);
                intent.putExtra("judul_ins", instructions.get(position).getTitleInstruction());
                intent.putExtra("pengirim_ins", instructions.get(position).getSenderInstriction());
                intent.putExtra("penerima_ins", instructions.get(position).getRecipientInstruction());
                intent.putExtra("isi_ins", instructions.get(position).getDetailInstruction());
                intent.putExtra("status_ins", instructions.get(position).getStatusInsruction());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        sessionManager.logoutUser();
        Intent intent = new Intent(InstructionActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void requestData() {
        pDialog.setMessage("Memproses Data...");
        pDialog.show();
        /*Json Request*/
        String url = Config.base_url+ "/getInstruction.php?id=" + sessionManager.getIdLogin();

        Log.d("url : ", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        parseJSON(response);
                        InstructionAdapter adapter = new InstructionAdapter(InstructionActivity.this, instructions);
                        listInstruction.setAdapter(adapter);
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

    private void parseJSON(String result) {
        if (!result.contains("gagal")) {
            try {
                JSONObject data = new JSONObject(result);
                JSONArray dataAr = data.getJSONArray("data");
                for (int i = 0; i < dataAr.length(); i++) {
                    JSONObject insObj = dataAr.getJSONObject(i);

                    Instruction ins = new Instruction();
                    ins.setTitleInstruction(insObj.getString("judul_instruksi"));
                    ins.setDetailInstruction(insObj.getString("isi_instruksi"));
                    ins.setRecipientInstruction(insObj.getString("username"));
                    ins.setDateInstruction(insObj.getString("tgl"));
                    ins.setStatusInsruction(insObj.getString("status"));

                    instructions.add(ins);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            txtNoMsg.setVisibility(View.VISIBLE);
        }

    }

}
