package com.airanza.vehicleservicehistory;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class VehicleActivity extends ActionBarActivity {

    private VehicleFragment vehicleFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        FragmentManager fm = getFragmentManager();
        vehicleFragment = (VehicleFragment)fm.findFragmentById(R.id.vehicle_fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vehicle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.action_settings:
                break;
            case R.id.action_vehicle_submit:
                onSubmit();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onSubmit() {
        Intent result = new Intent("com.airanza.vehicleservicehistory.VehicleFragment.VEHICLE_ACTIVITY_REQUEST", Uri.parse("content://result_uri"));
        result.putExtra(VehicleFragment.VEHICLE_INTENT_DATA, vehicleFragment.getVehicle());

        if(getParent() == null) {
            setResult(Activity.RESULT_OK, result);
        } else {
            getParent().setResult(Activity.RESULT_OK, result);
        }
        finish();
    }
}
