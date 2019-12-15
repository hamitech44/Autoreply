package com.hypersquad.autoreply;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class namaztime extends AppCompatActivity {
    Button fajar1,fajar2,zohar1,zohar2,asar1,asar2,maghrib1,maghrib2,isha1,isha2;
    Switch namaz;
    Boolean namazz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_namaztime);
        namaz=(Switch)findViewById(R.id.namaz1);



        fajar1=(Button)findViewById(R.id.stfajar);
        fajar2=(Button)findViewById(R.id.enfajar);
        zohar1=(Button)findViewById(R.id.stzohar);
        zohar2=(Button)findViewById(R.id.enzohar);
        asar1=(Button)findViewById(R.id.stasar);
        asar2=(Button)findViewById(R.id.enasar);
        maghrib1=(Button)findViewById(R.id.stmaghrib);
        maghrib2=(Button)findViewById(R.id.enmaghrib);
        isha1=(Button)findViewById(R.id.stisha);
        isha2=(Button)findViewById(R.id.enisha);
    LoadPreferences();
    }


    public void onStfajarClicked(View v){
        DialogFragment newFragment = new TimePickerFragment3();
        newFragment.show(getFragmentManager(),"TimePicker");
    }
    public void onEnfajarClicked(View v){
        DialogFragment newFragment = new Fragment4();
        newFragment.show(getFragmentManager(),"TimePicker");
    }
    public void onStZoharClicked(View v){
        DialogFragment newFragment = new Fragment5();
        newFragment.show(getFragmentManager(),"TimePicker");
    } public void onEnZoharClicked(View v){
        DialogFragment newFragment = new Fragment6();
        newFragment.show(getFragmentManager(),"TimePicker");
    } public void onStAsarClicked(View v){
        DialogFragment newFragment = new Fragment7();
        newFragment.show(getFragmentManager(),"TimePicker");
    } public void onEnAsarClicked(View v){
        DialogFragment newFragment = new Fragment8();
        newFragment.show(getFragmentManager(),"TimePicker");
    } public void onStMagribClicked(View v){
        DialogFragment newFragment = new Fragment9();
        newFragment.show(getFragmentManager(),"TimePicker");
    } public void onEnMaghribClicked(View v){
        DialogFragment newFragment = new Fragment10();
        newFragment.show(getFragmentManager(),"TimePicker");
    } public void onStIshaClicked(View v){
        DialogFragment newFragment = new BlankFragment11();
        newFragment.show(getFragmentManager(),"TimePicker");
    } public void onEnIshaClicked(View v){
        DialogFragment newFragment = new Fragment12();
        newFragment.show(getFragmentManager(),"TimePicker");
    }
    private void SavePreferences(){
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        namazz=namaz.isChecked();
        editor.putBoolean("namazswitch1",namazz);
        editor.putString("stfajar",fajar1.getText().toString());
        editor.putString("enfajar",fajar2.getText().toString());
        editor.putString("stzohar",zohar1.getText().toString());
        editor.putString("enzohar",zohar2.getText().toString());
        editor.putString("stasar",asar1.getText().toString());
        editor.putString("enasar",asar2.getText().toString());
        editor.putString("stmaghrib",maghrib1.getText().toString());
        editor.putString("enmaghrib",maghrib2.getText().toString());
        editor.putString("stisha",isha1.getText().toString());
        editor.putString("enisha",isha2.getText().toString());

        editor.apply();

    }

    private void LoadPreferences(){
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
     namaz.setChecked(sharedPreferences.getBoolean("namazswitch1",false));
        fajar1.setText(sharedPreferences.getString("stfajar",fajar1.getText().toString()));
      fajar2.setText(sharedPreferences.getString("enfajar",fajar2.getText().toString()));
        zohar1.setText(sharedPreferences.getString("stzohar",zohar1.getText().toString()));
        zohar2.setText(sharedPreferences.getString("enzohar",zohar2.getText().toString()));
        asar1.setText(sharedPreferences.getString("stasar",asar1.getText().toString()));
        asar2.setText(sharedPreferences.getString("enasar",asar2.getText().toString()));
        maghrib1.setText(sharedPreferences.getString("stmaghrib",maghrib1.getText().toString()));
        maghrib2.setText(sharedPreferences.getString("enmaghrib",maghrib2.getText().toString()));
        isha1.setText(sharedPreferences.getString("stisha",isha1.getText().toString()));
        isha2.setText(sharedPreferences.getString("enisha",isha2.getText().toString()));

    }

    @Override

    public void onBackPressed(){
        SavePreferences();
        Intent returnIntent = new Intent();
        namazz=namaz.isChecked();
        returnIntent.putExtra("namazswitch11",namazz);
        returnIntent.putExtra("stfajar",fajar1.getText().toString());
        returnIntent.putExtra("enfajar",fajar2.getText().toString());
        returnIntent.putExtra("stzohar",zohar1.getText().toString());
        returnIntent.putExtra("enzohar",zohar2.getText().toString());
        returnIntent.putExtra("stasar",asar1.getText().toString());
        returnIntent.putExtra("enasar",asar2.getText().toString());
        returnIntent.putExtra("stmaghrib",maghrib1.getText().toString());
        returnIntent.putExtra("enmaghrib",maghrib2.getText().toString());
        returnIntent.putExtra("stisha",isha1.getText().toString());
        returnIntent.putExtra("enisha",isha2.getText().toString());
        setResult(Activity.RESULT_OK,returnIntent);
        finish();

    }
}
