package com.hypersquad.autoreply;

import android.app.Activity;
import android.app.DialogFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
public class classtime extends AppCompatActivity {
    Switch timeoption;
    Button setstarttime,setendtime;
    Boolean classstatus;
    TextView endtext1,endtext2,starttext1,starttext2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classtime);
        setstarttime=(Button)findViewById(R.id.btn);
        setendtime=(Button)findViewById(R.id.btn2);
        assert setendtime != null;
        setendtime.setEnabled(false);
        setstarttime.setEnabled(false);

        timeoption=(Switch)findViewById(R.id.timeoption);
        starttext1=(TextView)findViewById(R.id.txtViewhour1);
        starttext2=(TextView)findViewById(R.id.textViewminute2);
        endtext1=(TextView)findViewById(R.id.txtViewhour2);
        endtext2=(TextView)findViewById(R.id.textViewminute1);

        LoadPreferences();
        timeoption.setChecked(false);
        Toast.makeText(getApplicationContext(), "24 hours Time format", Toast.LENGTH_LONG).show();
        timeoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (timeoption.isChecked()) {
                    setendtime.setEnabled(true);
                    setstarttime.setEnabled(true);

                }else{
                    setendtime.setEnabled(false);
                    setstarttime.setEnabled(false);


                }

                }
        });
    }

    public void onButtonClicked(View v){
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(),"TimePicker");
    }
    public void onButton2Clicked(View v){
        DialogFragment newFragment = new TimePickerFragment2();
        newFragment.show(getFragmentManager(),"TimePicker");
    }
    private void SavePreferences(){
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("state", timeoption.isChecked());
        editor.putString("start1",starttext1.getText().toString());
        editor.putString("start2",starttext2.getText().toString());

        editor.putString("end1",endtext1.getText().toString());
        editor.putString("end2",endtext2.getText().toString());
        editor.putBoolean("timestatus",timeoption.isChecked());
        editor.putBoolean("state1", setstarttime.isEnabled());

        editor.putBoolean("state2", setendtime.isEnabled());


        editor.apply();

    }

    private void LoadPreferences(){
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        Boolean  state = sharedPreferences.getBoolean("state", false);
        Boolean  state1 = sharedPreferences.getBoolean("state1", false);
        Boolean  state2 = sharedPreferences.getBoolean("state2", false);
        String start1= sharedPreferences.getString("start1",null);
        String start2= sharedPreferences.getString("start2",null);

        String end1= sharedPreferences.getString("end1",null);
        String end2= sharedPreferences.getString("end2",null);
        starttext1.setText(start1);
        starttext2.setText(start2);
        endtext1.setText(end1);
        endtext2.setText(end2);

        setstarttime.setEnabled(state1);
        setendtime.setEnabled(state2);
        timeoption.setChecked(state);

    }
    @Override
    public void onBackPressed() {
        SavePreferences();

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",timeoption.isChecked());
        returnIntent.putExtra("starthour",starttext1.getText().toString().trim());
        returnIntent.putExtra("startminute",starttext2.getText().toString().trim());
        returnIntent.putExtra("endhour",endtext1.getText().toString());
        returnIntent.putExtra("endminute",endtext2.getText().toString());

        setResult(Activity.RESULT_OK,returnIntent);
        finish();

        super.onBackPressed();


    }

}

