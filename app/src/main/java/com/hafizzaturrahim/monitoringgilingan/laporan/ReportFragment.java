package com.hafizzaturrahim.monitoringgilingan.laporan;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hafizzaturrahim.monitoringgilingan.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment {

    ListView listReport;
    ArrayList<Report> reports;

    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rowView = inflater.inflate(R.layout.fragment_report, container, false);
        listReport = (ListView) rowView.findViewById(R.id.lvReport);
        reports = new ArrayList<>();
        Report report = new Report();
        report.setTitleReport("Judul Laporan");
        report.setDateReport("12 Maret 2017");
        report.setContentReport("sgdfgdf fdgdgdfgfdgfdg");
        reports.add(report);
        reports.add(report);
        ReportAdapter adapter = new ReportAdapter(getActivity(), reports);
        listReport.setAdapter(adapter);

        listReport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),DetailReportActivity.class);
                String judul = reports.get(position).getTitleReport();
                String tanggal = reports.get(position).getDateReport();
                String konten = reports.get(position).getContentReport();
                intent.putExtra("judul",judul);
                intent.putExtra("tanggal",tanggal);
                intent.putExtra("konten",konten);
                startActivity(intent);
            }
        });

        return rowView;
    }

}
