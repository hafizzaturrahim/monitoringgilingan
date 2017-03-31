package com.hafizzaturrahim.monitoringgilingan.instruksi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hafizzaturrahim.monitoringgilingan.R;
import com.hafizzaturrahim.monitoringgilingan.laporan.Report;

import java.util.ArrayList;

/**
 * Created by PC-34 on 3/31/2017.
 */

public class InstructionAdapter extends ArrayAdapter<Instruction> {

    private Context context;
    private ArrayList<Instruction> instructions = new ArrayList<>();

    public InstructionAdapter(Context context, ArrayList<Instruction> instructions) {
        super(context, R.layout.item_instruction, instructions);
        this.context = context;
        this.instructions = instructions;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Load Custom Layout untuk list
        View rowView = inflater.inflate(R.layout.item_instruction, null, true);

        //Declarasi komponen
        TextView recipient = (TextView) rowView.findViewById(R.id.txtRecipientInstruction);
        TextView title = (TextView) rowView.findViewById(R.id.txtTitleInstruction);
        TextView date = (TextView) rowView.findViewById(R.id.txtDateInstruction);

        //Set Parameter Value
        recipient.setText(instructions.get(position).getRecipientInstruction());
        title.setText(instructions.get(position).getTitleInstruction());
        date.setText(instructions.get(position).getDateInstruction());

        return rowView;
    }
}
