package com.airanza.vehicleservicehistory;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class FuelEventActivity extends ActionBarActivity {

    private FuelEventFragment fuelEventFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_event);

        FragmentManager fm = getFragmentManager();
        fuelEventFragment = (FuelEventFragment) fm.findFragmentById(R.id.fuel_event_fragment);

        EditText tm = (EditText)findViewById(R.id.fuel_event_timestamp);
        tm.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.setTimeStampEditText((EditText)v.findViewById(R.id.fuel_event_timestamp));
                newFragment.show(getFragmentManager(), "Fuel Date");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fuel_event, menu);
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
            case R.id.action_fuel_event_submit:
                onSubmit();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onSubmit() {
        Intent result = new Intent("com.airanza.vehicleservicehistory.FuelEventFragment.FUEL_EVENT_ACTIVITY_REQUEST", Uri.parse("content://result_uri"));
        result.putExtra(FuelEventFragment.FUEL_EVENT_INTENT_DATA, fuelEventFragment.getFuelEvent());

        if(getParent() == null) {
            setResult(Activity.RESULT_OK, result);
        } else {
            getParent().setResult(Activity.RESULT_OK, result);
        }
        finish();
    }
}
