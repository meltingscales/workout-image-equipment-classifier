package com.scanfit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.scanfit.modeling.fitness.Equipment;

import java.util.List;

public class EquipmentArrayAdapter extends ArrayAdapter<Equipment> {

    public EquipmentArrayAdapter(@NonNull Context context, int resource, @NonNull List<Equipment> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Equipment equipment = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_equipment, parent, false);
        }

        TextView textViewEquipmentName = convertView.findViewById(R.id.textViewEquipmentName);
        TextView textViewNumberExercises = convertView.findViewById(R.id.textViewNumberExercises);

        textViewEquipmentName.setText(equipment.getName());
        textViewNumberExercises.setText(String.valueOf(equipment.getExerciseSet().size()));

        return convertView;
    }
}
