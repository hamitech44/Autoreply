package com.hypersquad.autoreply;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.app.DialogFragment;
import android.app.Dialog;
import java.util.Calendar;
import android.widget.TimePicker;

import static com.hypersquad.autoreply.R.id.timeoption;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment2 extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current time as the default values for the time picker

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //Create and return a new instance of TimePickerDialog
        return new TimePickerDialog(getActivity(),this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    //onTimeSet() callback method
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        //Do something with the user chosen time
        //Get reference of host activity (XML Layout File) TextView widget
        TextView hr2 = (TextView) getActivity().findViewById(R.id.txtViewhour2);
        TextView mn2 = (TextView) getActivity().findViewById(R.id.textViewminute1);

        //Display the user changed time on TextView
        hr2.setText(String.valueOf(hourOfDay));
        mn2.setText(String.valueOf(minute));

    }
}