package com.airanza.vehicleservicehistory;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddVehicle(View v) {
        // create Intent
        Intent intent = new Intent(this, VehicleActivity.class);

        // start the activity (user can cxl with back key).
        startActivityForResult(intent, VehicleFragment.VEHICLE_ACTIVITY_REQUEST);
    }

    public void onManageVehicles(View v) {
        // create Intent
        Intent intent = new Intent(this, ManageVehiclesActivity.class);

        // start the activity (user can cxl with back key).
        startActivityForResult(intent, ManageVehiclesFragment.MANAGE_VEHICLES_ACTIVITY_REQUEST);
    }

    public void onAddFuel(View v) {
        // create Intent
        Intent intent = new Intent(this, FuelEventActivity.class);

        // start the activity (user can cxl with back key).
        startActivityForResult(intent, FuelEventFragment.FUEL_EVENT_ACTIVITY_REQUEST);
    }

    public void onAddService(View v) {
        // TODO:  create ServiceEvntActivity and associated classes.
//        Intent intent = new Intent(getApplicationContext(), ServiceEventActivity.class);
//        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if(requestCode == FuelEventFragment.FUEL_EVENT_ACTIVITY_REQUEST) {
            if(resultCode == RESULT_OK) {
                FuelEvent fuelEvent = (FuelEvent)data.getSerializableExtra(FuelEventFragment.FUEL_EVENT_INTENT_DATA);
                System.out.println(fuelEvent.getID() + " " + fuelEvent.getVolume() + " " + fuelEvent.getVolume_unit() + " " + fuelEvent.getPrice_per_unit() + " " + fuelEvent.getVolume() * fuelEvent.getPrice_per_unit());
            }
        }
        if(requestCode == VehicleFragment.VEHICLE_ACTIVITY_REQUEST) {
            if(resultCode == RESULT_OK) {
                Vehicle vehicle = (Vehicle)data.getSerializableExtra(VehicleFragment.VEHICLE_INTENT_DATA);
                System.out.println(vehicle.getID() + " " + vehicle.getType() + " " + vehicle.getYear() + " " + vehicle.getMake() + " " + vehicle.getModel() + " " + vehicle.getVin());
            }
        }
    }


}
