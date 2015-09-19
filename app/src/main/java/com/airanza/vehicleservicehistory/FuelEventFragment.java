package com.airanza.vehicleservicehistory;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FuelEventFragment extends Fragment {
    public static int FUEL_EVENT_ACTIVITY_REQUEST = 100;
    public static String FUEL_EVENT_INTENT_DATA = "fuel_event_data";

    VehicleServiceHistoryDataSource dataSource = null;
    FuelEvent fuelEvent = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fuel_event, container, false);

        Intent intent = getActivity().getIntent();
        FuelEvent tmpFuelEvent = (FuelEvent)intent.getSerializableExtra(FUEL_EVENT_INTENT_DATA);

        // If a FuelEvent was sent by caller, then populate the fields.
        if(tmpFuelEvent != null) {
            fuelEvent = tmpFuelEvent;
            updateUIFromEvent(v);
            // populate fields with data from FuelEvent
        } else {
            fuelEvent = new FuelEvent();
            fuelEvent.setTimestamp(System.currentTimeMillis());
        }

        dataSource = new VehicleServiceHistoryDataSource(getActivity().getApplicationContext());

        try {
            dataSource.open();
        }catch (SQLException e) {
            Log.e(e.getClass().getName(), e.getMessage(), e);
        }

//        String [] allVehicles = (String [])(dataSource.getAllVehicles().toArray());
//
        Spinner vehicleSpinner = (Spinner)v.findViewById(R.id.fuel_event_vehicle_id);
//        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, allVehicles);
        ArrayAdapter<Vehicle> spinnerArrayAdapter = new ArrayAdapter<Vehicle>(v.getContext(), android.R.layout.simple_spinner_item, dataSource.getAllVehicles());
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleSpinner.setAdapter(spinnerArrayAdapter);

        EditText time = (EditText)v.findViewById(R.id.fuel_event_timestamp);
//        time.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                DatePickerFragment dpf = new DatePickerFragment();
//                dpf.show(getFragmentManager(), "Fuel Date");
//            }
//        });
        return v;
    }

    public FuelEvent getFuelEvent() {
        updateFuelEventFromUI();
        return fuelEvent;
    }

    private void updateUIFromEvent(View v) {
        ((EditText)v.findViewById(R.id.fuel_event_timestamp)).setText(fuelEvent.getTimestamp() + "");

        ((EditText)v.findViewById(R.id.fuel_event_distance)).setText(fuelEvent.getDistance() + "");
        ((Spinner)v.findViewById(R.id.fuel_event_distance_unit)).setSelection(Utils.findIndexOf(fuelEvent.getDistanceUnit(), getResources(), R.array.distance_units_array));

        ((EditText)v.findViewById(R.id.fuel_event_volume)).setText(fuelEvent.getVolume() + "");
        ((Spinner)v.findViewById(R.id.fuel_event_volume_unit)).setSelection(Utils.findIndexOf(fuelEvent.getVolume_unit(), getResources(), R.array.volume_units_array));

        ((EditText)v.findViewById(R.id.fuel_event_octane)).setText(fuelEvent.getOctane() + "");
        ((Spinner)v.findViewById(R.id.fuel_event_octane_method)).setSelection(Utils.findIndexOf(fuelEvent.getOctane_method(), getResources(), R.array.octane_rating_methods));

        ((EditText)v.findViewById(R.id.fuel_event_price_per_unit)).setText(fuelEvent.getPrice_per_unit() + "");
        ((Spinner)v.findViewById(R.id.fuel_event_currency)).setSelection(Utils.findIndexOf(fuelEvent.getCurrency(), getResources(), R.array.currencies));

        ((EditText)v.findViewById(R.id.fuel_event_notes_edittext)).setText(fuelEvent.getNotes());

        double lat = fuelEvent.getGps_latitude();
        double lon = fuelEvent.getGps_longitude();
        ((EditText)v.findViewById(R.id.fuel_event_gps_latitude_edittext)).setText(Location.convert(lat, Location.FORMAT_SECONDS));
        ((EditText)v.findViewById(R.id.fuel_event_gps_longitude_edittext)).setText(Location.convert(lon, Location.FORMAT_SECONDS));
    }

    private void updateFuelEventFromUI() {
        View v = getView();

        String inDate = ((EditText)v.findViewById(R.id.fuel_event_timestamp)).getText().toString();

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Date d = formatter.parse(inDate);
            fuelEvent.setTimestamp(d.getTime());
        } catch(ParseException e) {
            Log.e(getClass().getName(), e.getMessage());
            // TODO:  Display a message to the user that their date is screwed up.
            fuelEvent.setTimestamp(0);
        }

        fuelEvent.setDistance(Long.parseLong(((EditText) v.findViewById(R.id.fuel_event_distance)).getText().toString()));
        fuelEvent.setDistanceUnit(((Spinner) v.findViewById(R.id.fuel_event_distance_unit)).getSelectedItem().toString());

        fuelEvent.setVolume(Float.parseFloat(((EditText) v.findViewById(R.id.fuel_event_volume)).getText().toString()));
        fuelEvent.setVolume_unit(((Spinner) v.findViewById(R.id.fuel_event_volume_unit)).getSelectedItem().toString());

        fuelEvent.setOctane(Integer.parseInt(((EditText) v.findViewById(R.id.fuel_event_octane)).getText().toString()));
        fuelEvent.setOctane_method(((Spinner) v.findViewById(R.id.fuel_event_octane_method)).getSelectedItem().toString());

        fuelEvent.setPrice_per_unit(Float.parseFloat(((EditText) v.findViewById(R.id.fuel_event_price_per_unit)).getText().toString()));
        fuelEvent.setCurrency(((Spinner) v.findViewById(R.id.fuel_event_currency)).getSelectedItem().toString());

        fuelEvent.setNotes(((EditText) v.findViewById(R.id.fuel_event_notes_edittext)).getText().toString());

        try {
            fuelEvent.setGps_latitude(Double.parseDouble(((EditText) v.findViewById(R.id.fuel_event_gps_latitude_edittext)).getText().toString()));
        } catch(NumberFormatException e) {
            Log.w(getClass().getName(), e.getMessage());
            fuelEvent.setGps_latitude(0);
        }

        try {
            fuelEvent.setGps_longitude(Double.parseDouble(((EditText) v.findViewById(R.id.fuel_event_gps_longitude_edittext)).getText().toString()));
        } catch(NumberFormatException e) {
            Log.w(getClass().getName(), e.getMessage());
            fuelEvent.setGps_longitude(0);
        }
    }
}
