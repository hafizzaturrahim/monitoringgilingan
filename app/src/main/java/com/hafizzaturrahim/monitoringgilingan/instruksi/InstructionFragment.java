package com.hafizzaturrahim.monitoringgilingan.instruksi;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
public class InstructionFragment extends Fragment {

    ListView listInstruction;
    ArrayList<Instruction> instructions;

    public InstructionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rowView = inflater.inflate(R.layout.fragment_instruction, container, false);

        instructions = new ArrayList<>();
        listInstruction = (ListView) rowView.findViewById(R.id.lvInstruction);
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

        InstructionAdapter adapter = new InstructionAdapter(getActivity(), instructions);
        listInstruction.setAdapter(adapter);

        listInstruction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),DetailInstructionActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) rowView.findViewById(R.id.fabNewInstruction);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(getActivity(), NewInstructionActivity.class);
                startActivity(intent);
            }
        });
        return rowView;
    }

}
