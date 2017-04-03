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

import com.hafizzaturrahim.monitoringgilingan.MainActivity;
import com.hafizzaturrahim.monitoringgilingan.R;

public class NewInstructionActivity extends AppCompatActivity {

    Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_instruction);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    public void confirmInstruction(View view){
        Intent intent = new Intent(NewInstructionActivity.this, MainActivity.class);
        intent.putExtra("menu",3);
        startActivity(intent);
    }

}
