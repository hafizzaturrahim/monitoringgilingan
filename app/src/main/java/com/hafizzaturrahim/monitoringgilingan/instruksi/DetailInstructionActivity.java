package com.hafizzaturrahim.monitoringgilingan.instruksi;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hafizzaturrahim.monitoringgilingan.R;
import com.hafizzaturrahim.monitoringgilingan.SessionManager;

import org.w3c.dom.Text;

public class DetailInstructionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_instruction);

        SessionManager sessionManager = new SessionManager(this);
        String level = sessionManager.getLevel();
        Intent intent = getIntent();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Instruksi");

        TextView txtTitle = (TextView) findViewById(R.id.txtTitleDetailIns);
        TextView txtRecipient = (TextView) findViewById(R.id.txtRecipientDetailIns);
        TextView txtContent = (TextView) findViewById(R.id.txtContentDetailIns);
        TextView txtStatus = (TextView) findViewById(R.id.txtStatusDetailIns);

        TextView txtColumnSender = (TextView) findViewById(R.id.txtSenderColumn);

        Button cancelBtn = (Button) findViewById(R.id.btnCancel);
        Button confirmBtn = (Button) findViewById(R.id.btnConfirm);
        Button finishBtn = (Button) findViewById(R.id.btnFinish);
        LinearLayout cancelLayout = (LinearLayout) findViewById(R.id.cancelLayout);

        if (level.equals("2")) {
            txtColumnSender.setText("Pengirim");
            finishBtn.setVisibility(View.VISIBLE);
            confirmBtn.setVisibility(View.VISIBLE);
        } else {
            if (intent.getStringExtra("status_ins").equals("1")) {
                cancelLayout.setVisibility(View.VISIBLE);
            }
        }

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        txtTitle.setText(intent.getStringExtra("judul_ins"));
        txtRecipient.setText(intent.getStringExtra("penerima_ins"));
        txtContent.setText(intent.getStringExtra("isi_ins"));
        if (intent.getStringExtra("status_ins").equals("4")) {
            txtStatus.setText("Dibatalkan");
        } else if (intent.getStringExtra("status_ins").equals("3")) {
            txtStatus.setText("Selesai dikerjakan");
        } else if (intent.getStringExtra("status_ins").equals("2")) {
            txtStatus.setText("Dikonfirmasi");
        } else {
            txtStatus.setText("Menunggu konfirmasi");
            txtStatus.setTextColor(0xFFFF3300);
        }


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
