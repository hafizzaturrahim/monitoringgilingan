package com.hafizzaturrahim.monitoringgilingan.laporan;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hafizzaturrahim.monitoringgilingan.Config;
import com.hafizzaturrahim.monitoringgilingan.R;
import com.hafizzaturrahim.monitoringgilingan.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment {
    private ProgressDialog pDialog;
    ListView listReport;
    ArrayList<Report> reports;
    SessionManager sessionManager;
    SwipeRefreshLayout mSwipeRefreshLayout;

    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rowView = inflater.inflate(R.layout.fragment_report, container, false);
        sessionManager = new SessionManager(getActivity());
        listReport = (ListView) rowView.findViewById(R.id.lvReport);
        pDialog = new ProgressDialog(getActivity());
        reports = new ArrayList<>();

        requestData();
        pDialog.setMessage("Memproses Data...");
        pDialog.show();
        pDialog.setCancelable(false);
        listReport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailReportActivity.class);
                String judul = reports.get(position).getTitleReport();
                String tanggal = reports.get(position).getDateReport();
                String konten = reports.get(position).getContentReport();

                Log.d("tanggal laporan : " + tanggal, "isi : " + konten);
                intent.putExtra("judul", judul);
                intent.putExtra("tanggal", tanggal);
                intent.putExtra("konten", konten);
                startActivity(intent);
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) rowView.findViewById(R.id.swReport);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reports.clear();
                        requestData();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        return rowView;
    }

    private void requestData() {

        /*Json Request*/
        String url = Config.base_url + "/getReport.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        parseJSON(response);
                        setAdapter();
                        pDialog.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        Toast.makeText(getActivity(), "Terjadi kesalahan, coba lagi", Toast.LENGTH_SHORT).show();
                        if (error != null) {
                            error.printStackTrace();

                        }
                    }
                });

        //add request to queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private void setAdapter(){
        ReportAdapter adapter = new ReportAdapter(getActivity(), reports);
        listReport.setAdapter(adapter);
    }

    private void parseJSON(String result) {
        if (!result.contains("gagal")) {
            try {
                JSONObject data = new JSONObject(result);
                JSONArray dataAr = data.getJSONArray("data");
                for (int i = 0; i < dataAr.length(); i++) {
                    JSONObject reportObj = dataAr.getJSONObject(i);

                    Report report = new Report();
                    report.setId(reportObj.getString("id_laporan"));
                    report.setTitleReport(reportObj.getString("judul_laporan"));
                    report.setContentReport(reportObj.getString("detail_laporan"));
                    report.setDateReport(Config.convertDate(reportObj.getString("tgl")));
                    reports.add(report);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {

        }

    }

}
