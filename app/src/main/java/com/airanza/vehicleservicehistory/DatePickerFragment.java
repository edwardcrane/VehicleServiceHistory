package com.airanza.vehicleservicehistory;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by ecrane on 9/18/2015.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private EditText tm = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in he picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void setTimeStampEditText(EditText tm) {
        this.tm = tm;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        System.out.println("PRINGLN:  " + view.getParent().getClass().getName());
        tm.setText(month + "/" + day + "/" + year);
        // Do something with date chosesn by the user
    }
}
