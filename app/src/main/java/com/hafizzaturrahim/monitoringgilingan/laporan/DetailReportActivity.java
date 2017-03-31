package com.hafizzaturrahim.monitoringgilingan.laporan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.hafizzaturrahim.monitoringgilingan.R;

public class DetailReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_detail_report);

        getSupportActionBar().setTitle(intent.getStringExtra("judul"));

        TextView dateDetail = (TextView) findViewById(R.id.txtDatedetailReport);
        TextView contentDetail = (TextView) findViewById(R.id.txtContentdetailReport);

        dateDetail.setText(intent.getStringExtra("tanggal"));
        contentDetail.setText(intent.getStringExtra("konten"));

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
}
