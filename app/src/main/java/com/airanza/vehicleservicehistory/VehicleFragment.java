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
            updateUIFromVehicle();
        } else {
            vehicle = new Vehicle();
        }

        return v;
    }

    public Vehicle getVehicle() {
        updateVehicleFromUI();
        return vehicle;
    }

    private void updateUIFromVehicle() {
        // TODO: UI element updating logic here, from vehicle. Opposite of updateVehicleFromUI();
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

    private void onSubmit(View v) {
        // gather up variables, if editing existing fuelEvent, update, else create new.
        updateVehicleFromUI();

        // send back the results to the caller, whether they want it or not!
        Intent result = new Intent("com.airanza.vehicleservicehistory.VehicleFragment.VEHICLE_ACTIVITY_REQUEST", Uri.parse("content://result_uri"));
        result.putExtra(VEHICLE_INTENT_DATA, vehicle);

        if(getActivity().getParent() == null) {
            getActivity().setResult(Activity.RESULT_OK, result);
        } else {
            getActivity().getParent().setResult(Activity.RESULT_OK, result);
        }
    }
}
