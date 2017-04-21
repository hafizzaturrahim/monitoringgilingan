package com.hafizzaturrahim.monitoringgilingan.beranda;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
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
import com.hafizzaturrahim.monitoringgilingan.CustomSpinnerAdapter;
import com.hafizzaturrahim.monitoringgilingan.ItemSpinner;
import com.hafizzaturrahim.monitoringgilingan.R;
import com.hafizzaturrahim.monitoringgilingan.laporan.Report;
import com.hafizzaturrahim.monitoringgilingan.laporan.ReportAdapter;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private ProgressDialog pDialog;

    private ColumnChartView chart;
    private ColumnChartData data;

    private ColumnChartView chart2;
    private ColumnChartData data2;

    String tgl, ccr1, ccr2, lvl_bologne, flow_imb, temp_imb, level_imb;
    int[] speed, oil, nozzle, imc;

    TextView txtTgl, txtCcr1, txtCcr2, txtLvl_bologne, txtFlow_imb, txtTemp_imb, txtLevel_imb;
    Spinner spParam;

    MaterialSpinner spinner;
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
        txtLevel_imb = (TextView) rowView.findViewById(R.id.txtlevel);


        spinner = (MaterialSpinner) rowView.findViewById(R.id.spinner);
        spParam = (Spinner) rowView.findViewById(R.id.spGilingan);

        spParam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                generateData(position);
//                ItemSpinner selected = (ItemSpinner) (parentView.getItemAtPosition(position));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        //set column chart
        chart = (ColumnChartView) rowView.findViewById(R.id.chartBar);
        chart.startDataAnimation();
        chart.setZoomEnabled(!chart.isZoomEnabled());
//        chart.setOnValueTouchListener(new ValueTouchListener());

        requestData();
        return rowView;

    }

    private void requestData() {
        pDialog.setMessage("Memproses Data...");
        pDialog.show();
        /*Json Request*/
        String url = Config.base_url + "/getCurrentPeformance.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        parseJSON(response);

                        ItemSpinner[] parameters = {
                                new ItemSpinner("Speed", "1"),
                                new ItemSpinner("Oil Temperature", "2"),
                                new ItemSpinner("Nozzle", "3"),
                        };
                        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, parameters);
                        spinner.setItems(parameters);
//                        spinner.setAdapter(adapter);
//                        spParam.setAdapter(adapter);
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
                    JSONObject reportObj = dataAr.getJSONObject(i);

                    tgl = convertDate(reportObj.getString("tgl"));
                    txtTgl.setText("Last update " + tgl);
                    txtCcr1.setText(reportObj.getString("ccr1"));
                    txtCcr2.setText(reportObj.getString("ccr2"));
                    String degree = "\u2103";
                    txtTemp_imb.setText(reportObj.getString("temp_imb") + " " + degree);
                    txtFlow_imb.setText(reportObj.getString("flow_imb"));
                    txtLevel_imb.setText(reportObj.getString("level_imb"));

                    speed = new int[]{
                            Integer.parseInt(reportObj.getString("speed_gil3")),
                            Integer.parseInt(reportObj.getString("speed_gil4")),
                            Integer.parseInt(reportObj.getString("speed_gil5"))
                    };

                    oil = new int[]{
                            Integer.parseInt(reportObj.getString("oil_temp_gil3")),
                            Integer.parseInt(reportObj.getString("oil_temp_gil4")),
                            Integer.parseInt(reportObj.getString("oil_temp_gil5"))
                    };

                    nozzle = new int[]{
                            Integer.parseInt(reportObj.getString("nozzle_gil3")),
                            Integer.parseInt(reportObj.getString("nozzle_gil4")),
                            Integer.parseInt(reportObj.getString("nozzle_gil5"))
                    };

                    imc = new int[]{
                            Integer.parseInt(reportObj.getString("rpm_imc1")),
                            Integer.parseInt(reportObj.getString("rpm_imc2")),
                            Integer.parseInt(reportObj.getString("rpm_imc3")),
                            Integer.parseInt(reportObj.getString("rpm_imc4"))
                    };
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getActivity(), "Tidak bisa mendapat data, coba lagi", Toast.LENGTH_SHORT).show();
        }
    }

    private void generateData(int chosenData) {
        int numColumns = 3;
        String[] label = new String[]{"Gilingan 3", "Gilingan 4", "Gilingan 5"};
        int[] color = new int[]{ChartUtils.COLOR_BLUE, ChartUtils.COLOR_GREEN, ChartUtils.COLOR_ORANGE, ChartUtils.COLOR_RED};

        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<>();
            switch (chosenData) {
                case 0:
                    values.add(new SubcolumnValue(speed[i], color[i]));
                    break;
                case 1:
                    values.add(new SubcolumnValue(oil[i], color[i]));
                    break;
                case 2:
                    values.add(new SubcolumnValue(nozzle[i], color[i]));
                    break;
            }
            axisValues.add(new AxisValue(i).setLabel(label[i]));

            Column column = new Column(values);
            column.setHasLabels(true);
            column.setHasLabelsOnlyForSelected(false);

            columns.add(column);
        }

        data = new ColumnChartData(columns);
        data.setAxisXBottom(new Axis(axisValues).setHasLines(true));

        Axis axisY = new Axis().setHasLines(true);
        data.setAxisYLeft(axisY);

        chart.setColumnChartData(data);

    }

    private String convertDate(String oldDate) {
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
        return newDate;
    }


}
