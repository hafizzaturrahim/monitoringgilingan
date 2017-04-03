package com.hafizzaturrahim.monitoringgilingan.instruksi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hafizzaturrahim.monitoringgilingan.MainActivity;
import com.hafizzaturrahim.monitoringgilingan.R;

public class NewInstructionActivity extends AppCompatActivity {

    Button confirmBtn;
    String penerima;
    String isi;

    EditText edtRecipient, edtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_instruction);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtRecipient = (EditText) findViewById(R.id.edtNewRecipient);
        edtContent = (EditText) findViewById(R.id.edtNewInst);

        confirmBtn = (Button) findViewById(R.id.btnConfirmInstruction);


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

    public void confirmInstruction(View view) {

        penerima = edtRecipient.getText().toString();
        isi = edtContent.getText().toString();


        if (!penerima.equals("") && !isi.equals("")) {
            Intent intent = new Intent(NewInstructionActivity.this, MainActivity.class);
            intent.putExtra("menu", 3);
            startActivity(intent);
        } else {
            if (penerima.equals("")) {
                edtRecipient.setError("Kolom harus diisi");
            }
            if (isi.equals("")) {
                edtContent.setError("Kolom harus diisi");
            }
        }


    }

}
