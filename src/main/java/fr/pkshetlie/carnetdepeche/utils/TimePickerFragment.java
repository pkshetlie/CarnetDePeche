package fr.pkshetlie.carnetdepeche.utils;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

import fr.pkshetlie.carnetdepeche.PartiePecheForm;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        String min = "";
        String hour = "";
        if (hourOfDay < 10) {
            hour += "0";
        }
        hour += String.valueOf(hourOfDay);

        if (minute < 10) {
            min += "0";
        }
        min += String.valueOf(minute);


        PartiePecheForm callingActivity = (PartiePecheForm) getActivity();
        callingActivity.onUserSelectValueTime(hour + ":" + min);
    }
}