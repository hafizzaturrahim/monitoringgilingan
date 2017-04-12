package com.hafizzaturrahim.monitoringgilingan.instruksi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.hafizzaturrahim.monitoringgilingan.MainActivity;
import com.hafizzaturrahim.monitoringgilingan.R;

public class NewInstructionActivity extends AppCompatActivity {

    Button confirmBtn;
    String penerima;
    String isi;
    String[] recipient;
    EditText edtContent;
    Spinner spinRecipient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_instruction);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtContent = (EditText) findViewById(R.id.edtNewInst);

        confirmBtn = (Button) findViewById(R.id.btnConfirmInstruction);
        spinRecipient = (Spinner) findViewById(R.id.spRecipient);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, recipient);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinRecipient.setAdapter(adapter);

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

//        penerima = edtRecipient.getText().toString();
        isi = edtContent.getText().toString();


        if (!penerima.equals("") && !isi.equals("")) {
            Intent intent = new Intent(NewInstructionActivity.this, MainActivity.class);
            intent.putExtra("menu", 3);
            startActivity(intent);
        } else {
            if (isi.equals("")) {
                edtContent.setError("Kolom harus diisi");
            }
        }


    }

}
