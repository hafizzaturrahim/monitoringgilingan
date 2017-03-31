package com.hafizzaturrahim.monitoringgilingan.instruksi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.hafizzaturrahim.monitoringgilingan.R;

public class DetailInstructionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_instruction);

        Intent intent = getIntent();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Instruksi");

        TextView txtTitle = (TextView) findViewById(R.id.txtTitleDetailIns);
        TextView txtRecipient = (TextView) findViewById(R.id.txtRecipientDetailIns);
        TextView txtContent = (TextView) findViewById(R.id.txtContentDetailIns);

        txtTitle.setText(intent.getStringExtra("judul_ins"));
        txtRecipient.setText(intent.getStringExtra("penerima_ins"));
        txtContent.setText(intent.getStringExtra("isi"));
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
