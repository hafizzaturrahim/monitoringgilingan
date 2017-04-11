package com.hafizzaturrahim.monitoringgilingan.instruksi;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hafizzaturrahim.monitoringgilingan.R;
import com.hafizzaturrahim.monitoringgilingan.SessionManager;
import com.hafizzaturrahim.monitoringgilingan.laporan.Report;
import com.hafizzaturrahim.monitoringgilingan.laporan.ReportAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstructionFragment extends Fragment {
    private ProgressDialog pDialog;
    SessionManager sessionManager;
    ListView listInstruction;
    ArrayList<Instruction> instructions;

    public InstructionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rowView = inflater.inflate(R.layout.fragment_instruction, container, false);
        pDialog = new ProgressDialog(getActivity());
        instructions = new ArrayList<>();
        sessionManager = new SessionManager(getActivity());

        listInstruction = (ListView) rowView.findViewById(R.id.lvInstruction);

        listInstruction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),DetailInstructionActivity.class);
                intent.putExtra("judul_ins",instructions.get(position).getTitleInstruction());
                intent.putExtra("pengirim_ins",instructions.get(position).getSenderInstriction());
                intent.putExtra("penerima_ins",instructions.get(position).getRecipientInstruction());
                intent.putExtra("isi_ins",instructions.get(position).getDetailInstruction());
                intent.putExtra("status_ins",instructions.get(position).getStatusInsruction());

                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) rowView.findViewById(R.id.fabNewInstruction);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(getActivity(), NewInstructionActivity.class);

                startActivity(intent);
            }
        });
        return rowView;
    }

    private void requestData() {
        pDialog.setMessage("Memproses Data...");
        pDialog.show();
        /*Json Request*/
        String url = "http://192.168.137.1/gilinganlocal/getInstruction.php?id=" +sessionManager.getIdLogin();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        parseJSON(response);
                        InstructionAdapter adapter = new InstructionAdapter(getActivity(), instructions);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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

                    instructions.add(ins);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {

        }

    }

}
