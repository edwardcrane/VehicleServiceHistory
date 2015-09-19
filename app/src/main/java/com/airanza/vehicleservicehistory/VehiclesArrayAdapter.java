package com.airanza.vehicleservicehistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ecrane on 7/7/2015.
 */
public class VehiclesArrayAdapter extends ArrayAdapter<Vehicle> {
    public VehiclesArrayAdapter(Context context, List<Vehicle> vehicles) {
        super(context, 0, vehicles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the data item for this position
        Vehicle vehicle = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.vehiclerowlayout, parent, false);
        }
        // Lookup view for data population
        TextView text1 = (TextView) convertView.findViewById(R.id.text1);
        TextView text2 = (TextView) convertView.findViewById(R.id.text2);
        // Populate the data into the template view using the data object
        text1.setText(vehicle.getNickname());
        text2.setText(vehicle.getNotes());

        // return the completed view to render on screen
        return convertView;
    }
}
