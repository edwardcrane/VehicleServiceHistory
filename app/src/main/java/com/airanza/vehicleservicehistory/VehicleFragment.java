package com.airanza.vehicleservicehistory;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Arrays;

public class VehicleFragment extends Fragment {
    public static int VEHICLE_ACTIVITY_REQUEST = 101;
    public static String VEHICLE_INTENT_DATA = "vehicle_data";

    private VehicleFragment vehicleFragment = null;
    Vehicle vehicle = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_vehicle, container, false);

        Intent intent = getActivity().getIntent();
        Vehicle tmpVehicle = (Vehicle)intent.getSerializableExtra(VEHICLE_INTENT_DATA);

        if(tmpVehicle != null) {
            vehicle = tmpVehicle;
            updateUIFromVehicle(v);
        } else {
            vehicle = new Vehicle();
        }

        return v;
    }

    public Vehicle getVehicle() {
        updateVehicleFromUI();
        return vehicle;
    }

    private void updateUIFromVehicle(View v) {
        ((Spinner) v.findViewById(R.id.vehicle_type_edittext)).setSelection(Utils.findIndexOf(vehicle.getType(), getResources(), R.array.vehicle_types));

        ((EditText) v.findViewById(R.id.vehicle_vin_edittext)).setText(vehicle.getVin());
        ((EditText) v.findViewById(R.id.vehicle_plate_edittext)).setText(vehicle.getPlate());

        ((EditText) v.findViewById(R.id.vehicle_nickname_edittext)).setText(vehicle.getNickname());
        ((EditText) v.findViewById(R.id.vehicle_year_edittext)).setText(vehicle.getYear() + "");
        ((EditText) v.findViewById(R.id.vehicle_make_edittext)).setText(vehicle.getMake());
        ((EditText) v.findViewById(R.id.vehicle_model_edittext)).setText(vehicle.getModel());
        ((EditText) v.findViewById(R.id.vehicle_color_edittext)).setText(vehicle.getColor());
        ((EditText) v.findViewById(R.id.vehicle_notes_edittext)).setText(vehicle.getNotes());
    }

    private void updateVehicleFromUI() {
        View v = getView();

        vehicle.setType(((Spinner) v.findViewById(R.id.vehicle_type_edittext)).getSelectedItem().toString());
        vehicle.setVin(((EditText) v.findViewById(R.id.vehicle_vin_edittext)).getText().toString());
        vehicle.setPlate(((EditText) v.findViewById(R.id.vehicle_plate_edittext)).getText().toString());

        vehicle.setNickname(((EditText) v.findViewById(R.id.vehicle_nickname_edittext)).getText().toString());
        vehicle.setYear(Integer.parseInt(((EditText) v.findViewById(R.id.vehicle_year_edittext)).getText().toString()));
        vehicle.setMake(((EditText) v.findViewById(R.id.vehicle_make_edittext)).getText().toString());
        vehicle.setModel(((EditText) v.findViewById(R.id.vehicle_model_edittext)).getText().toString());
        vehicle.setColor(((EditText) v.findViewById(R.id.vehicle_color_edittext)).getText().toString());
        vehicle.setNotes(((EditText) v.findViewById(R.id.vehicle_notes_edittext)).getText().toString());
    }
}
