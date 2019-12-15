package com.hypersquad.autoreply;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

public class Rejectcalls extends Activity {
 ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejectcalls);
        toggleButton=(ToggleButton)findViewById(R.id.toggleButton1);
    LoadPreferences();
    }



    private void SavePreferences() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("rejectcall", toggleButton.isChecked());

        editor.apply();
    }

    private void LoadPreferences() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        Boolean fam1 = sharedPreferences.getBoolean("rejectcall", false);

        toggleButton.setChecked(fam1);
    }



    @Override

    public void onBackPressed() {
        SavePreferences();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("rejectcall", toggleButton.isChecked());

        setResult(Activity.RESULT_OK, returnIntent);
        finish();

    }

}
