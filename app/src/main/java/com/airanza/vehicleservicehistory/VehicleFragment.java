package com.airanza.vehicleservicehistory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.Arrays;

public class VehicleFragment extends Fragment {
    public static final int VEHICLE_ACTIVITY_REQUEST = 101;
    public static String VEHICLE_INTENT_DATA = "com.airanza.vehicleservicehistory.VEHICLE";

    private VehicleServiceHistoryDataSource dataSource;
    private VehicleFragment vehicleFragment = null;
    Vehicle vehicle = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_vehicle, container, false);

        Intent intent = getActivity().getIntent();
        vehicle = (Vehicle) intent.getSerializableExtra(VEHICLE_INTENT_DATA);

        dataSource = new VehicleServiceHistoryDataSource(getActivity());
        try {
            dataSource.open();
        } catch (SQLException e) {
            Log.e(getClass().getName(), e.getMessage(), e);
        }

        updateUIFromVehicle(v);

        return v;
    }

    private void updateUIFromVehicle(View v) {
        if(vehicle == null) {
            return;
        }

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

    public void onSubmit(View view) {
        if(vehicle == null || (dataSource.findVehicle(vehicle.getID()) == null)) {
            vehicle = dataSource.createVehicle(
                    ((Spinner) view.findViewById(R.id.vehicle_type_edittext)).getSelectedItem().toString(),
                    ((EditText) view.findViewById(R.id.vehicle_vin_edittext)).getText().toString(),
                    ((EditText) view.findViewById(R.id.vehicle_plate_edittext)).getText().toString(),
                    ((EditText) view.findViewById(R.id.vehicle_nickname_edittext)).getText().toString(),
                    Integer.parseInt(((EditText) view.findViewById(R.id.vehicle_year_edittext)).getText().toString()),
                    ((EditText) view.findViewById(R.id.vehicle_make_edittext)).getText().toString(),
                    ((EditText) view.findViewById(R.id.vehicle_model_edittext)).getText().toString(),
                    ((EditText) view.findViewById(R.id.vehicle_color_edittext)).getText().toString(),
                    ((EditText) view.findViewById(R.id.vehicle_notes_edittext)).getText().toString()
            );
        } else {
            updateVehicleFromUI();
            dataSource.update(vehicle);
        }

        Log.i(getClass().getName(), vehicle.getID() + "");

        Intent result = new Intent("com.airanza.vehicleservicehistory.VehicleFragment.VEHICLE_ACTIVITY_REQUEST", Uri.parse("content://result_uri"));
        result.putExtra(VehicleFragment.VEHICLE_INTENT_DATA, vehicle);

        if(getActivity().getParent() == null) {
            getActivity().setResult(Activity.RESULT_OK, result);
        } else {
            getActivity().getParent().setResult(Activity.RESULT_OK, result);
        }
        getActivity().finish();
    }

    public void onDeleteVehicle(View view) {
//        final Vehicle tmpVehicle = vehicleFragment.getVehicle();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(getText(R.string.vehicle_activity_delete_vehicle_confirm_title));
        alertDialog.setMessage(getText(R.string.vehicle_activity_delete_vehicle_confirm_message));
        alertDialog.setIcon(getResources().getDrawable(android.R.drawable.ic_menu_delete));

        alertDialog.setNegativeButton(getText(R.string.vehicle_activity_delete_vehicle_dialog_negative_button_text),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        Toast.makeText(getActivity().getApplicationContext(), String.format(getString(R.string.vehicle_activity_edit_vehicle_not_deleted), vehicle), Toast.LENGTH_SHORT).show();
                    }
                });

        alertDialog.setPositiveButton(getText(R.string.vehicle_activity_delete_vehicle_dialog_positive_button_text),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.w(this.getClass().getName(), "Deleting resource [" + vehicle + "]");
                        dataSource.deleteVehicle(vehicle);
                        Toast.makeText(getActivity().getApplicationContext(), String.format(getString(R.string.vehicle_activity_edit_vehicle_deleted), vehicle), Toast.LENGTH_LONG).show();

                        getActivity().finish();
                    }
                });

        final AlertDialog alert = alertDialog.create();
        alert.show();
    }

    public void onCancelEnry(View view) {
        getActivity().finish();
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
