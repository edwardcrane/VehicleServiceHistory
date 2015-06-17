package com.airanza.vehicleservicehistory;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by ecrane on 6/12/2015.
 */
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
            updateUIFromEvent();
            // populate fields with data from FuelEvent
        } else {
            fuelEvent = new FuelEvent();
            fuelEvent.setTimestamp(System.currentTimeMillis());
        }

        dataSource = new VehicleServiceHistoryDataSource(getActivity().getApplicationContext());

        return v;
    }

    public FuelEvent getFuelEvent() {
        updateFuelEventFromUI();
        return fuelEvent;
    }

    private void updateUIFromEvent() {
        // TODO:  Convert all get to set:
        View v = getView();

        ((EditText)v.findViewById(R.id.fuel_event_timestamp)).setText(fuelEvent.getTimestamp() + "");

        ((EditText)v.findViewById(R.id.fuel_event_distance)).setText(fuelEvent.getDistance() + "");
        // TODO:  Create Spinner for miles vs. Kilometers, then find index into string array for selection:
        ((Spinner)v.findViewById(R.id.fuel_event_distance_unit)).setSelection(1);

        ((EditText)v.findViewById(R.id.fuel_event_volume)).setText(fuelEvent.getVolume() + "");
        // TODO: Figure out how to set the Spinner.  Must find selection in array, based on string, I guess.
        ((Spinner)v.findViewById(R.id.fuel_event_volume_unit)).setSelection(1);

        ((EditText)v.findViewById(R.id.fuel_event_octane)).setText(fuelEvent.getOctane() + "");
        // TODO:  look up index into array and then set selection.
        ((Spinner)v.findViewById(R.id.fuel_event_octane_method)).setSelection(1);

        ((EditText)v.findViewById(R.id.fuel_event_price_per_unit)).setText(fuelEvent.getPrice_per_unit() + "");
        // TODO:  Look up index into array and then set selection.
        ((Spinner)v.findViewById(R.id.fuel_event_currency)).setSelection(1);

        ((EditText)v.findViewById(R.id.fuel_event_notes_edittext)).setText(fuelEvent.getNotes());

        double lat = fuelEvent.getGps_latitude();
        double lon = fuelEvent.getGps_longitude();
        ((EditText)v.findViewById(R.id.fuel_event_gps_latitude_edittext)).setText(Location.convert(lat, Location.FORMAT_SECONDS));
        ((EditText)v.findViewById(R.id.fuel_event_gps_longitude_edittext)).setText(Location.convert(lon, Location.FORMAT_SECONDS));
    }

    private void updateFuelEventFromUI() {
        View v = getView();

        fuelEvent.setTimestamp(Integer.parseInt(((EditText) v.findViewById(R.id.fuel_event_timestamp)).getText().toString()));

        fuelEvent.setDistance(Long.parseLong(((EditText) v.findViewById(R.id.fuel_event_distance)).getText().toString()));
        fuelEvent.setDistanceUnit(((Spinner) v.findViewById(R.id.fuel_event_distance_unit)).getSelectedItem().toString());

        fuelEvent.setVolume(Float.parseFloat(((EditText) v.findViewById(R.id.fuel_event_volume)).getText().toString()));
        fuelEvent.setVolume_unit(((Spinner) v.findViewById(R.id.fuel_event_volume_unit)).getSelectedItem().toString());

        fuelEvent.setOctane(Integer.parseInt(((EditText) v.findViewById(R.id.fuel_event_octane)).getText().toString()));
        fuelEvent.setOctane_method(((Spinner) v.findViewById(R.id.fuel_event_octane_method)).getSelectedItem().toString());

        fuelEvent.setPrice_per_unit(Float.parseFloat(((EditText) v.findViewById(R.id.fuel_event_price_per_unit)).getText().toString()));
        fuelEvent.setCurrency(((Spinner) v.findViewById(R.id.fuel_event_currency)).getSelectedItem().toString());

        fuelEvent.setNotes(((EditText) v.findViewById(R.id.fuel_event_notes_edittext)).getText().toString());

        fuelEvent.setGps_latitude(Double.parseDouble(((EditText) v.findViewById(R.id.fuel_event_gps_latitude_edittext)).getText().toString()));
        fuelEvent.setGps_longitude(Double.parseDouble(((EditText) v.findViewById(R.id.fuel_event_gps_longitude_edittext)).getText().toString()));
    }

    private void onSubmit(View v) {
        updateFuelEventFromUI();

        // send back the results to the caller, whether they want it or not!
        Intent result = new Intent("com.airanza.vehicleservicehistory.FuelEvenFragment.FUEL_EVENT_ACTIVITY_REQUEST", Uri.parse("content://result_uri"));
        result.putExtra(FUEL_EVENT_INTENT_DATA, fuelEvent);

        if(getActivity().getParent() == null) {
            getActivity().setResult(Activity.RESULT_OK, result);
        } else {
            getActivity().getParent().setResult(Activity.RESULT_OK, result);
        }

        // TODO:  LET THE CALLING ACTIVITY DECIDE WHEN TO finish():
        //getActivity().finish();
    }
}
