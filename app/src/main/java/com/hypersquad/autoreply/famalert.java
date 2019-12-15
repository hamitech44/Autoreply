package com.hypersquad.autoreply;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

public class famalert extends AppCompatActivity {

    EditText pnumber,pnumber2,pnumber3,pnumber4,pnumber5;
    Switch familyalert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_famalert);
        pnumber=(EditText)findViewById(R.id.phonenumber);
        pnumber4=(EditText)findViewById(R.id.viva1);
        pnumber5=(EditText) findViewById(R.id.viva2);
        pnumber2=(EditText)findViewById(R.id.phonenumber2);
        pnumber3=(EditText)findViewById(R.id.phonenumber3);

        assert pnumber != null;
        pnumber.setEnabled(false);
        pnumber2.setEnabled(false);
        pnumber3.setEnabled(false);

        familyalert=(Switch)findViewById(R.id.phoneswitch);
        LoadPreferences();
        familyalert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (familyalert.isChecked()){

                    pnumber.setEnabled(true);
                    pnumber2.setEnabled(true);
                    pnumber3.setEnabled(true);
                }
                else{
                    pnumber.setEnabled(false);
                    pnumber2.setEnabled(false);
                    pnumber3.setEnabled(false);

                }


            }
        });

}

    private void SavePreferences() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("famalert",familyalert.isChecked());
        editor.putString("famnumber",pnumber.getText().toString());
        editor.putString("famnumber2",pnumber2.getText().toString());
        editor.putString("famnumber3",pnumber3.getText().toString());
        editor.putString("famnumber4",pnumber4.getText().toString());
        editor.putString("famnumber5",pnumber5.getText().toString());

        editor.apply();
    }
    private void LoadPreferences() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        Boolean fam1=sharedPreferences.getBoolean("famalert",false);
        String  famnumber=sharedPreferences.getString("famnumber",null);
        String  famnumber2=sharedPreferences.getString("famnumber2",null);
        String  famnumber3=sharedPreferences.getString("famnumber3",null);
        String  famnumber4=sharedPreferences.getString("famnumber4",null);
        String  famnumber5=sharedPreferences.getString("famnumber5",null);

        familyalert.setChecked(fam1);
        pnumber.setText(famnumber);
        pnumber2.setText(famnumber2);
        pnumber3.setText(famnumber3);
    }

    @Override
    public void onBackPressed() {
            SavePreferences();

        Intent returnIntent = new Intent();
         returnIntent.putExtra("famcheck",familyalert.isChecked());
        returnIntent.putExtra("famnum",pnumber.getText().toString());
        returnIntent.putExtra("famnum2",pnumber2.getText().toString());
        returnIntent.putExtra("famnum3",pnumber3.getText().toString());
        returnIntent.putExtra("famnum4",pnumber4.getText().toString());
        returnIntent.putExtra("famnum5",pnumber5.getText().toString());

        setResult(Activity.RESULT_OK,returnIntent);
        finish();

        super.onBackPressed();


    }





}
