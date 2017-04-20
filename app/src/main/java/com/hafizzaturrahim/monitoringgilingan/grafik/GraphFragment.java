package com.hafizzaturrahim.monitoringgilingan.grafik;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
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
import com.hafizzaturrahim.monitoringgilingan.instruksi.Instruction;
import com.hafizzaturrahim.monitoringgilingan.instruksi.InstructionAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class GraphFragment extends Fragment implements View.OnClickListener {
    //UI References
    private EditText fromDateEtxt;
    private EditText toDateEtxt;
    private EditText fromTimeExt;
    private EditText toTimeExt;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private TimePickerDialog fromTimePickerDialog;
    private TimePickerDialog toTimePickerDialog;

    private Button mulaiBtn;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;

    // 0 = parameter
    // 1 = tanggal mulai
    // 2 = tanggal berakhir
    // 3 = jam mulai
    // 4 = jam berakhir
    String[] selectedInput = new String[5];

    private ProgressDialog pDialog;
    SessionManager sessionManager;

    public GraphFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rowView = inflater.inflate(R.layout.fragment_graph, container, false);
        sessionManager = new SessionManager(getActivity());
        pDialog = new ProgressDialog(getActivity());

        Spinner spinnerParam = (Spinner) rowView.findViewById(R.id.spParam);
        Parameter[] parameters = {
                new Parameter("Speed Gilingan 4", "speed_gil4"),
                new Parameter("CCR 1", "ccr1"),
                new Parameter("CCR 2", "ccr2"),
                new Parameter("RPM Imc 1", "rpm_imc1"),
                new Parameter("RPM Imc 2", "rpm_imc2"),
                new Parameter("RPM Imc 3", "rpm_imc3"),
                new Parameter("RPM Imc 4", "rpm_imc4"),
                new Parameter("Flow Imb", "flow_imb"),
                new Parameter("Temperature Imb", "temp_imb"),
                new Parameter("Level Imb", "level_imb")
        };
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, parameters);
        spinnerParam.setAdapter(adapter);

        spinnerParam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                Parameter selected = (Parameter) (parentView.getItemAtPosition(position));
                selectedInput[0] = String.valueOf(selected.getValue());
//                Toast.makeText(getActivity(), "The planet is " +
//                        selectedInput[0], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        mulaiBtn = (Button) rowView.findViewById(R.id.btnMulai);

        mulaiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notif = "Wajib diisi";

                if (selectedInput[1] == null || selectedInput[2] == null) {
                    if (selectedInput[1] == null) {
                        fromDateEtxt.setError(notif);
                    }
                    if (selectedInput[2] == null) {
                        toDateEtxt.setError(notif);
                    }
//                    if (selectedInput[3] == null) {
//                        fromTimeExt.setError(notif);
//                    }
//                    if (selectedInput[4] == null) {
//                        toTimeExt.setError(notif);
//                    }
                } else {
                    Toast.makeText(getActivity(), "input " +selectedInput[1], Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getActivity(), ChartActivity.class);
//                    intent.putExtra("input", selectedInput);
//                    startActivity(intent);

                }
            }
        });

        fromDateEtxt = (EditText) rowView.findViewById(R.id.etxt_fromdate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();

        toDateEtxt = (EditText) rowView.findViewById(R.id.etxt_todate);
        toDateEtxt.setInputType(InputType.TYPE_NULL);

        fromTimeExt = (EditText) rowView.findViewById(R.id.etxt_fromtime);
        fromTimeExt.setInputType(InputType.TYPE_NULL);

        toTimeExt = (EditText) rowView.findViewById(R.id.etxt_totime);
        toTimeExt.setInputType(InputType.TYPE_NULL);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        timeFormatter = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        setDateTimeField();
        return rowView;
    }

    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);

        fromTimeExt.setOnClickListener(this);
        toTimeExt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
                selectedInput[1] = dateFormatter.format(newDate.getTime());
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
                selectedInput[2] = dateFormatter.format(newDate.getTime());
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fromTimePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                newDate.set(Calendar.MINUTE, minute);
                fromTimeExt.setText(timeFormatter.format(newDate.getTime()));
                selectedInput[3] = timeFormatter.format(newDate.getTime());
            }
        }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);

        toTimePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                newDate.set(Calendar.MINUTE, minute);
                toTimeExt.setText(timeFormatter.format(newDate.getTime()));
                selectedInput[4] = timeFormatter.format(newDate.getTime());
            }
        }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), true);
    }

    @Override
    public void onClick(View v) {
        if (v == fromDateEtxt) {
            fromDatePickerDialog.show();
        } else if (v == toDateEtxt) {
            toDatePickerDialog.show();
        } else if (v == fromTimeExt) {
            fromTimePickerDialog.show();
        } else if (v == toTimeExt) {
            toTimePickerDialog.show();
        }
    }

    private void requestData() {
        pDialog.setMessage("Memproses Data...");
        pDialog.show();
        /*Json Request*/
        String url = Config.base_url + "/getInstruction.php?id=" + sessionManager.getIdLogin() + "&level=" + sessionManager.getLevel();

        Log.d("url : ", url);
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


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {

        }

    }

}
