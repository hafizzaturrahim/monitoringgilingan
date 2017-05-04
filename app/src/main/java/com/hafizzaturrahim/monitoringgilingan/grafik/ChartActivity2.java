package com.hafizzaturrahim.monitoringgilingan.grafik;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

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
import java.util.List;

import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

public class ChartActivity2 extends AppCompatActivity {
    private LineChartView chart;
    private LineChartData data;

    private int numberOfPoints;

    String[] label;
    float[] numbersTab;

    String dataInput[] = new String[7];

    private ProgressDialog pDialog;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        dataInput = intent.getStringArrayExtra("input");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(dataInput[5]);
        setContentView(R.layout.activity_chart);

        pDialog = new ProgressDialog(this);
        sessionManager = new SessionManager(this);

//        Toast.makeText(this, "1 : " + dataInput[0] + " 2 : " + dataInput[1] + " 3 : " + dataInput[2] + " 4 : " + dataInput[3] + " 5: " + dataInput[4], Toast.LENGTH_LONG).show();
        chart = (LineChartView) findViewById(R.id.chart);
        chart.setOnValueTouchListener(new LineChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {

            }

            @Override
            public void onValueDeselected() {

            }
        });
        requestData();

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


    private void generateData() {
        List<Line> lines = new ArrayList<Line>();
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<PointValue> values = new ArrayList<PointValue>();

        for (int i = 0; i < numberOfPoints; ++i) {
            values.add(new PointValue(i, numbersTab[i]));
            axisValues.add(new AxisValue(i).setLabel(label[i]));
        }

        Line line = new Line(values);
        line.setColor(ChartUtils.COLOR_GREEN);
        line.setShape(ValueShape.CIRCLE);
        line.setCubic(false);
        line.setFilled(false);
        line.setHasLabels(true);
        line.setHasLabelsOnlyForSelected(true);
        line.setHasLines(true);
        line.setHasPoints(true);
//            line.setHasGradientToTransparent(hasGradientToTransparent);

        lines.add(line);


        data = new LineChartData(lines);
        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);

        axisX.setName("Tanggal");
        axisY.setName(dataInput[5]);

        data.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        data.setAxisYLeft(axisY);

        data.setBaseValue(Float.NEGATIVE_INFINITY);
        chart.setLineChartData(data);
        chart.setValueSelectionEnabled(true);

    }

    private void requestData() {
        pDialog.setMessage("Memproses Data...");
        pDialog.show();
        /*Json Request*/
        String star_date = dataInput[1] + "%20" + dataInput[3];
        String end_date = dataInput[2] + "%20" + dataInput[4];
        String url = Config.base_url + "/getPeformance.php?param=" + dataInput[0] + "&start=" + star_date + "&stop=" + end_date + "&group=" +dataInput[6];

        Log.d("url : ", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        parseJSON(response);
                        generateData();
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
                numberOfPoints = dataAr.length();
                numbersTab = new float[numberOfPoints];
                label = new String[numberOfPoints];

                for (int i = 0; i < dataAr.length(); i++) {
                    JSONObject Obj = dataAr.getJSONObject(i);
                    numbersTab[i] = Float.parseFloat(Obj.getString(dataInput[0]));
                    label[i] =Obj.getString("tgl");
//                    Log.d("numberstab "+i, String.valueOf(numbersTab[i]));
//                    Instruction ins = new Instruction();
//                    ins.setTitleInstruction(insObj.getString("judul_instruksi"));
//                    ins.setDetailInstruction(insObj.getString("isi_instruksi"));
//                    ins.setRecipientInstruction(insObj.getString("username"));
//                    ins.setDateInstruction(insObj.getString("tgl"));
//                    ins.setStatusInsruction(insObj.getString("status"));


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {

        }

    }

}
