package com.hafizzaturrahim.monitoringgilingan.karyawan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hafizzaturrahim.monitoringgilingan.LoginActivity;
import com.hafizzaturrahim.monitoringgilingan.MainActivity;
import com.hafizzaturrahim.monitoringgilingan.R;
import com.hafizzaturrahim.monitoringgilingan.SessionManager;
import com.hafizzaturrahim.monitoringgilingan.instruksi.DetailInstructionActivity;
import com.hafizzaturrahim.monitoringgilingan.instruksi.Instruction;
import com.hafizzaturrahim.monitoringgilingan.instruksi.InstructionAdapter;

import java.util.ArrayList;

public class InstructionActivity extends AppCompatActivity {
    ListView listInstruction;
    ArrayList<Instruction> instructions;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        sessionManager = new SessionManager(this);
        instructions = new ArrayList<>();
        listInstruction = (ListView) findViewById(R.id.lvInstruction2);
        Instruction ins = new Instruction("Suhu mesin naik", "Suparmo", "12-4-2017");
        instructions.add(ins);
        instructions.add(ins);
        instructions.add(ins);
        instructions.add(ins);
        instructions.add(ins);
        instructions.add(ins);
        instructions.add(ins);
        instructions.add(ins);
        instructions.add(ins);
        instructions.add(ins);

        InstructionAdapter adapter = new InstructionAdapter(this, instructions);
        listInstruction.setAdapter(adapter);

        listInstruction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(InstructionActivity.this, DetailInstructionActivity.class);
                intent.putExtra("judul_ins", instructions.get(position).getTitleInstruction());
                intent.putExtra("pengirim_ins", instructions.get(position).getSenderInstriction());
                intent.putExtra("penerima_ins", instructions.get(position).getRecipientInstruction());
                intent.putExtra("isi_ins", instructions.get(position).getDetailInstruction());
                intent.putExtra("status_ins", instructions.get(position).getStatusInsruction());

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        sessionManager.logoutUser();
        Intent intent = new Intent(InstructionActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
