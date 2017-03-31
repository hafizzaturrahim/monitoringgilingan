package com.hafizzaturrahim.monitoringgilingan.instruksi;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hafizzaturrahim.monitoringgilingan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstructionFragment extends Fragment {

    ListView listInstruction;

    public InstructionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rowView = inflater.inflate(R.layout.fragment_instruction, container, false);

        listInstruction = (ListView) rowView.findViewById(R.id.lvInstruction);

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
