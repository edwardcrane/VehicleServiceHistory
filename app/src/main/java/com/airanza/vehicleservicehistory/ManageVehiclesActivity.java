package com.airanza.vehicleservicehistory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

/**
 * Created by ecrane on 9/18/2015.
 */
public class ManageVehiclesActivity extends ActionBarActivity {

    private VehicleServiceHistoryDataSource vshDataSource;

    private List<Vehicle> vehicles = null;
    private ListView listView = null;
    private VehiclesArrayAdapter adapter = null;

    public final static String EXTRA_VEHICLE = "com.airanza.vehicleservicehistory.VEHICLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_vehicles);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_manage_vehicles, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_vehicle) {
            onAddVehicle();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddVehicle() {
        // create Intent
        Intent intent = new Intent(this, VehicleActivity.class);

        // start the activity (user can cxl with back key).
//        startActivityForResult(intent, VehicleFragment.VEHICLE_ACTIVITY_REQUEST);
        startActivity(intent);
    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.i(getClass().getName(), "inside onActivityResult(" + requestCode + ", " + resultCode + ", " + data + ")");
//        switch (requestCode) {
//            case VehicleFragment.VEHICLE_ACTIVITY_REQUEST:
//                if (resultCode == RESULT_OK) {
//                    // get the Vehicle from the Intent.
//                    Vehicle v = (Vehicle) data.getSerializableExtra(VehicleFragment.VEHICLE_INTENT_DATA);
//                }
//                break;
//        }
//        adapter.notifyDataSetChanged();
//    }
}
