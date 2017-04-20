package com.hafizzaturrahim.monitoringgilingan.grafik;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by PC-34 on 4/20/2017.
 */

public class SpinnerAdapter extends ArrayAdapter<Parameter> {

    private Context context;
    private Parameter[] params;

    public SpinnerAdapter(Context context, int textViewResourceId,
                            Parameter[] params) {
        super(context, textViewResourceId, params);
        this.context = context;
        this.params = params;
    }

    public int getCount(){
        return params.length;
    }

    public Parameter getItem(int position){
        return params[position];
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setText(params[position].getValue());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setText(params[position].getName());
        return label;
    }
}


