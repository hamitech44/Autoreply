package com.hypersquad.autoreply;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

public class Blacklist extends AppCompatActivity {
    EditText bnumber1, bnumber2, bnumber3;
    Switch blockalert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blacklist);
        bnumber1 = (EditText) findViewById(R.id.blocknumber1);
        bnumber2 = (EditText) findViewById(R.id.blocknumber2);
        bnumber3 = (EditText) findViewById(R.id.blocknumber3);
        blockalert = (Switch) findViewById(R.id.blockswitch);
        LoadPreferences();

    }

    private void SavePreferences() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("blkalert", blockalert.isChecked());
        editor.putString("blknumber1", bnumber1.getText().toString());
        editor.putString("blknumber2", bnumber2.getText().toString());
        editor.putString("blknumber3", bnumber3.getText().toString());

        editor.apply();
    }

    private void LoadPreferences() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        Boolean fam1 = sharedPreferences.getBoolean("blkalert", false);
        String blknumber = sharedPreferences.getString("blknumber1", null);
        String blknumber2 = sharedPreferences.getString("blknumber2", null);
        String blknumber3 = sharedPreferences.getString("blknumber3", null);

        blockalert.setChecked(fam1);
        bnumber1.setText(blknumber);
        bnumber2.setText(blknumber2);
        bnumber3.setText(blknumber3);
    }


    public void SaveValues(View view) {
        SavePreferences();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("blkcheck", blockalert.isChecked());
        returnIntent.putExtra("blknum1", bnumber1.getText().toString());
        returnIntent.putExtra("blknum2", bnumber2.getText().toString());
        returnIntent.putExtra("blknum3", bnumber3.getText().toString());

        setResult(Activity.RESULT_OK, returnIntent);


    }

    @Override

    public void onBackPressed() {
        SavePreferences();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("blkcheck", blockalert.isChecked());
        returnIntent.putExtra("blknum1", bnumber1.getText().toString());
        returnIntent.putExtra("blknum2", bnumber2.getText().toString());
        returnIntent.putExtra("blknum3", bnumber3.getText().toString());

        setResult(Activity.RESULT_OK, returnIntent);
        finish();

    }
}
