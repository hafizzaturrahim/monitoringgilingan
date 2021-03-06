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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hafizzaturrahim.monitoringgilingan.Config;
import com.hafizzaturrahim.monitoringgilingan.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

import static com.hafizzaturrahim.monitoringgilingan.Config.convertDate;


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
//    Spinner spParam;

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


        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                generateData(position);
            }
        });

//        spParam = (Spinner) rowView.findViewById(R.id.spGilingan);
//        spParam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                generateData(position);
////                ItemSpinner selected = (ItemSpinner) (parentView.getItemAtPosition(position));
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//
//            }
//
//        });


        //set column chart
        chart = (ColumnChartView) rowView.findViewById(R.id.chartBar);
        chart.startDataAnimation();
        chart.setZoomEnabled(!chart.isZoomEnabled());
//        chart.setOnValueTouchListener(new ValueTouchListener());


        chart2 = (ColumnChartView) rowView.findViewById(R.id.chartBar2);
        chart2.startDataAnimation();
        chart2.setZoomEnabled(!chart2.isZoomEnabled());

        getPeformance();

        return rowView;

    }

    private void getPeformance() {
        pDialog.setMessage("Memproses Data...");
        pDialog.show();
        pDialog.setCancelable(false);
        /*Json Request*/
        String url = Config.base_url + "/getCurrentPeformance.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        parseJSON(response);
                        generateData2();
                        String[] param = {"-- Pilih parameter --", "Speed", "Oil Temperature", "Nozzle"};
                        spinner.setItems(param);

//                        spinner.setAdapter(adapter);
//                        spParam.setAdapter(adapter);
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
        int[] color = new int[]{ChartUtils.COLOR_BLUE, ChartUtils.COLOR_GREEN, ChartUtils.COLOR_RED};

        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<>();
            switch (chosenData) {
                case 1:
                    values.add(new SubcolumnValue(speed[i], color[i]));
                    break;
                case 2:
                    values.add(new SubcolumnValue(oil[i], color[i]));
                    break;
                case 3:
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

    private void generateData2() {
        int numColumns = 4;
        String[] label = new String[]{"Imc 1", "Imc 2", "Imc 3", "Imc 4"};
        int[] color = new int[]{ChartUtils.COLOR_ORANGE, ChartUtils.COLOR_VIOLET, ChartUtils.COLOR_BLUE, ChartUtils.COLOR_GREEN};

        // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<>();
            values.add(new SubcolumnValue(imc[i], color[i]));
            axisValues.add(new AxisValue(i).setLabel(label[i]));

            Column column = new Column(values);
            column.setHasLabels(true);
            column.setHasLabelsOnlyForSelected(false);

            columns.add(column);
        }

        data2 = new ColumnChartData(columns);
        data2.setAxisXBottom(new Axis(axisValues).setHasLines(true));

        Axis axisY = new Axis().setHasLines(true);
        data2.setAxisYLeft(axisY);

        chart2.setColumnChartData(data2);

    }


}
