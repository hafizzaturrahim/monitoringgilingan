package com.hafizzaturrahim.monitoringgilingan;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hafizzaturrahim.monitoringgilingan.ItemSpinner;

/**
 * Created by PC-34 on 4/20/2017.
 */

public class CustomSpinnerAdapter extends ArrayAdapter<ItemSpinner> {

    private Context context;
    private ItemSpinner[] params;

    public CustomSpinnerAdapter(Context context, int textViewResourceId,
                                ItemSpinner[] params) {
        super(context, textViewResourceId, params);
        this.context = context;
        this.params = params;
    }

    public int getCount(){
        return params.length;
    }

    public ItemSpinner getItem(int position){
        return params[position];
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setText(params[position].getName());
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


