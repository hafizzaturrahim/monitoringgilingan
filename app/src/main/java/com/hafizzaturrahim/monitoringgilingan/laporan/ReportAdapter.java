package com.hafizzaturrahim.monitoringgilingan.laporan;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hafizzaturrahim.monitoringgilingan.R;

import java.util.ArrayList;

/**
 * Created by PC-34 on 3/30/2017.
 */

public class ReportAdapter extends ArrayAdapter<Report> {

    private Context context;
    private ArrayList<Report> reports = new ArrayList<>();

    public ReportAdapter(Context context, ArrayList<Report> reports) {
        super(context, R.layout.item_report,reports);
        this.context = context;
        this.reports = reports;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Load Custom Layout untuk list
        View rowView= inflater.inflate(R.layout.item_report, null, true);

        //Declarasi komponen
        TextView title = (TextView) rowView.findViewById(R.id.txtTitleReport);
        TextView date = (TextView) rowView.findViewById(R.id.txtDateReport);

        //Set Parameter Value
        title.setText(reports.get(position).getTitleReport());
        date.setText(reports.get(position).getDateReport());

        return rowView;
    }
}
