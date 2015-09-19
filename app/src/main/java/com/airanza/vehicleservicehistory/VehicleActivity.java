package com.airanza.vehicleservicehistory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.sql.SQLException;


public class VehicleActivity extends ActionBarActivity {

    private VehicleFragment vehicleFragment = null;
    private VehicleServiceHistoryDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        FragmentManager fm = getFragmentManager();
        vehicleFragment = (VehicleFragment)fm.findFragmentById(R.id.vehicle_fragment);

        dataSource = new VehicleServiceHistoryDataSource(this);
        try {
            dataSource.open();
        } catch (SQLException e) {
            Log.e(getClass().getName(), e.getMessage(), e);
        }
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
                onSubmit(null);
                break;
            case R.id.action_vehicle_delete:
                onDeleteVehicle(null);
                break;
            case R.id.action_vehicle_cancel:
                onCancelEnry(null);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onSubmit(View view) {
        Vehicle tmpVehicle = vehicleFragment.getVehicle();

        Intent result = new Intent("com.airanza.vehicleservicehistory.VehicleFragment.VEHICLE_ACTIVITY_REQUEST", Uri.parse("content://result_uri"));
        result.putExtra(VehicleFragment.VEHICLE_INTENT_DATA, tmpVehicle);

        // If vehicle non-exists, create, otherwise update.
        if(dataSource.findVehicles(tmpVehicle.getVin()).isEmpty()) {
            tmpVehicle = dataSource.createVehicle(tmpVehicle.getType(),
                    tmpVehicle.getVin(),
                    tmpVehicle.getPlate(),
                    tmpVehicle.getNickname(),
                    tmpVehicle.getYear(),
                    tmpVehicle.getMake(),
                    tmpVehicle.getModel(),
                    tmpVehicle.getColor(),
                    tmpVehicle.getNotes());
        } else {
            dataSource.update(tmpVehicle);
        }

        Log.i(getClass().getName(), tmpVehicle.getID() + "");

        if(getParent() == null) {
            setResult(Activity.RESULT_OK, result);
        } else {
            getParent().setResult(Activity.RESULT_OK, result);
        }
        finish();
    }

    public void onDeleteVehicle(View view) {
        final Vehicle tmpVehicle = vehicleFragment.getVehicle();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getText(R.string.vehicle_activity_delete_vehicle_confirm_title));
        alertDialog.setMessage(getText(R.string.vehicle_activity_delete_vehicle_confirm_message));
        alertDialog.setIcon(getResources().getDrawable(android.R.drawable.ic_menu_delete));

        alertDialog.setNegativeButton(getText(R.string.vehicle_activity_delete_vehicle_dialog_negative_button_text),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        Toast.makeText(getApplicationContext(), String.format(getString(R.string.vehicle_activity_edit_vehicle_not_deleted), tmpVehicle), Toast.LENGTH_SHORT).show();
                    }
                });

        alertDialog.setPositiveButton(getText(R.string.vehicle_activity_delete_vehicle_dialog_positive_button_text),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.w(this.getClass().getName(), "Deleting resource [" + tmpVehicle + "]");
                        dataSource.deleteVehicle(tmpVehicle);
                        Toast.makeText(getApplicationContext(), String.format(getString(R.string.vehicle_activity_edit_vehicle_deleted), tmpVehicle), Toast.LENGTH_LONG).show();

                        finish();
                    }
                });


        final AlertDialog alert = alertDialog.create();
        alert.show();

    }

    private void onCancelEnry(View view) {
        finish();
    }
}
