package com.hafizzaturrahim.monitoringgilingan.beranda;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hafizzaturrahim.monitoringgilingan.Config;
import com.hafizzaturrahim.monitoringgilingan.R;
import com.hafizzaturrahim.monitoringgilingan.laporan.Report;
import com.hafizzaturrahim.monitoringgilingan.laporan.ReportAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private ProgressDialog pDialog;

//    String tgl,ccr1,ccr2,lvl_bologne,flow_imb,temp_imb,level_imb;
    TextView txtTgl,txtCcr1,txtCcr2,txtLvl_bologne,txtFlow_imb,txtTemp_imb,txtLevel_imb;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rowView = inflater.inflate(R.layout.fragment_home, container, false);

        pDialog = new ProgressDialog(getActivity());

        txtTgl = (TextView) rowView.findViewById(R.id.txttgl);
        txtCcr1 = (TextView) rowView.findViewById(R.id.txtccr1);
        txtCcr2 = (TextView) rowView.findViewById(R.id.txtccr2);
        txtFlow_imb = (TextView) rowView.findViewById(R.id.txtflow);
        txtTemp_imb = (TextView) rowView.findViewById(R.id.txttemp);

        requestData();
        return rowView;

    }

    private void requestData() {

        pDialog.setMessage("Memproses Data...");
        pDialog.show();
        /*Json Request*/
        String url = Config.base_url+ "/getCurrentPeformance.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        parseJSON(response);
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
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        pDialog.dismiss();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        String body = null;
//                        //get status code here
//                        String statusCode = String.valueOf(error.networkResponse.statusCode);
//                        //get response body and parse with appropriate encoding
//                        if(error.networkResponse.data!=null) {
//                            try {
//                                body = new String(error.networkResponse.data,"UTF-8");
//                            } catch (UnsupportedEncodingException e) {
//                                e.printStackTrace();
//                            }
//                        }
////                        Toast.makeText(getActivity(), "Error " +statusCode+ " message " +body, Toast.LENGTH_SHORT).show();
//                    }
//                });
//        //add request to queue
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        requestQueue.add(jsonObjectRequest);



    }

    private void parseJSON(String result) {
        if (!result.contains("gagal")) {
            try {
                JSONObject data = new JSONObject(result);
                JSONArray dataAr = data.getJSONArray("data");
                for (int i = 0; i < dataAr.length(); i++) {
                    JSONObject reportObj = dataAr.getJSONObject(i);

                    String tgl = convertDate(reportObj.getString("tgl"));
                    txtTgl.setText("Last update " +tgl);
                    txtCcr1.setText(reportObj.getString("ccr1"));
                    txtCcr2.setText(reportObj.getString("ccr2"));
                    txtTemp_imb.setText(reportObj.getString("temp_imb"));
                    txtFlow_imb.setText(reportObj.getString("flow_imb"));
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {

        }

    }


    private String convertDate(String oldDate){
        String newDate = null;
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);

        try {
            Date date = formatter.parse(oldDate);
            SimpleDateFormat newFormatter = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss");
            newDate = newFormatter.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  newDate;
    }
}
