package fr.pkshetlie.carnetdepeche.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

import fr.pkshetlie.carnetdepeche.PartiePecheForm;


public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        month++;
        String mday = "";
        String mmonth = "";
        if (day < 10) {
            mday += "0";
        }
        mday += String.valueOf(day);

        if (month < 10) {
            mmonth += "0";
        }
        mmonth += String.valueOf(month);


        PartiePecheForm callingActivity = (PartiePecheForm) getActivity();
        callingActivity.onUserSelectValueDay(mday + "/" + mmonth + "/" + year);
    }
}
