package com.hypersquad.autoreply;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.view.Gravity;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Boolean namazswitch=false;
    String starthour=null,startminute=null,endhour=null,endminute=null,
            famfilternum=null,famfilternum2=null,famfilternum3=null,famfilternum4=null,famfilternum5=null;
    String fjr1,fjr2,zhr1,zhr2,asr1,asr2,isha1,isha2,mgrb1,mgrb2;
    String blocknumber1,blocknumber2,blocknumber3;
    boolean timeaction,famfilter,blockfilter, rejectcall;
    FloatingActionButton fab1;

    Button button;
    EditText edit;
    AlertDialog alertDialog1;
    CharSequence[] values = {" Sorry! I'm busy, Call me later."," I'm in class, Can't take your call. ","" +
            " I'm driving. Please don't disturb. "," I'm sleeping! Please don't Disturb."," I'm studying! Please don't Disturb. ","I'm doing viva! Please don't Disturb.","i'm done viva!."};
    Switch onoff, silentmode,times;
    boolean contactsonly;
    AudioManager myAudioManager;


    private boolean isMyServiceRunning(Class<?> serviceClass) {


        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        assert manager != null;
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {

            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        button = findViewById(R.id.button);

        fab1 = findViewById(R.id.fab);
        onoff = findViewById(R.id.switch1);
        times= findViewById(R.id.timeoption);
        silentmode = findViewById(R.id.silentmode);
        edit = findViewById(R.id.editText2);
        myAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        if (isMyServiceRunning(AutoreplyService.class)) {

            onoff.setChecked(preferences.getBoolean("switch1", true));
            silentmode.setChecked(preferences.getBoolean("switch2", false));
            edit.setText(preferences.getString("text", null));
            edit.setEnabled(false);
            button.setEnabled(false);
            fab1.setClickable(false);
            onoff.setEnabled(false);
            silentmode.setEnabled(false);
            Toast toast = Toast.makeText(getApplicationContext(), "Stop the service first to make changes ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateAlertDialogWithRadioButtonGroup() ;

            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    public void CreateAlertDialogWithRadioButtonGroup(){


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Select Your Message");

        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch(item)
                {
                    case 0:

                        edit.setText(values[0]);
                        break;
                    case 1:

                        edit.setText(values[1]);
                        break;
                    case 2:
                        edit.setText(values[2]);
                        break;
                    case 3:
                        edit.setText(values[3]);
                        break;
                    case 4:
                        edit.setText(values[4]);
                        break;
                    case 5:
                        edit.setText(values[5]);
                        break;
                    case 6:
                        edit.setText(values[6]);
                        break;
                }
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();

    }


    @Override
    protected void onPause()
    {
        super.onPause();

        // Store values between instances here
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();  // Put the values from the UI


        editor.putString("text", edit.getText().toString());

        editor.putBoolean("switch1",onoff.isChecked());
        editor.putBoolean("switch2",silentmode.isChecked());
        editor.apply();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                 timeaction=data.getBooleanExtra("result",false);
                    starthour=data.getStringExtra("starthour");
                    startminute=data.getStringExtra("startminute");
                    endhour=data.getStringExtra("endhour");
                    endminute=data.getStringExtra("endminute");
            }

        }
        else if (requestCode==2)
        {
            if (resultCode==Activity.RESULT_OK)
            {
                famfilter=data.getBooleanExtra("famcheck",false);
                famfilternum=data.getStringExtra("famnum");
                famfilternum2=data.getStringExtra("famnum2");
                famfilternum3=data.getStringExtra("famnum3");
                famfilternum4=data.getStringExtra("famnum4");
                famfilternum5=data.getStringExtra("famnum5");


            }
        }
        else if(requestCode==3)
        {

            if (resultCode==Activity.RESULT_OK)
            {
                namazswitch=data.getBooleanExtra("namazswitch11",false);
                fjr1=data.getStringExtra("stfajar");
                fjr2=data.getStringExtra("enfajar");
                zhr1=data.getStringExtra("stzohar");
                zhr2=data.getStringExtra("enzohar");
                asr1=data.getStringExtra("stasar");
                asr2=data.getStringExtra("enasar");
                mgrb1=data.getStringExtra("stmaghrib");
                mgrb2=data.getStringExtra("enmaghrib");
                isha1=data.getStringExtra("stisha");
                isha2=data.getStringExtra("enisha");
            }
        }
        else if (requestCode==4)
        {
            if (resultCode==Activity.RESULT_OK)
            {
                blockfilter=data.getBooleanExtra("blkcheck",false);
                blocknumber1=data.getStringExtra("blknum1");
                blocknumber2=data.getStringExtra("blknum2");
                blocknumber3=data.getStringExtra("blknum3");

            }
        }
        else if (requestCode==5)
        {
            if (resultCode==Activity.RESULT_OK)
            {
                rejectcall=data.getBooleanExtra("rejectcall",false);

            }
        }

    }
    public void startService1(View view) {


        contactsonly = onoff.isChecked();

        Intent intent = new Intent(this, AutoreplyService.class);
        intent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
            if (edit.getText().toString().equals("")) {


                Toast.makeText(getApplicationContext(), "Please type something first!", Toast.LENGTH_LONG).show();
                return;

            } else {

                intent.putExtra("needed", edit.getText().toString());
                intent.putExtra("needed1", contactsonly);
                intent.putExtra("needed2",timeaction);
                intent.putExtra("sthour",starthour);
                intent.putExtra("stminute",startminute);
                intent.putExtra("enhour",endhour);
                intent.putExtra("enminute",endminute);
                intent.putExtra("famoption",famfilter);
                intent.putExtra("famoptionnum",famfilternum);
                intent.putExtra("famoptionnum2",famfilternum2);
                intent.putExtra("famoptionnum3",famfilternum3);
                intent.putExtra("famoptionnum4",famfilternum4);
                intent.putExtra("famoptionnum5",famfilternum5);
                intent.putExtra("f1",fjr1);
                intent.putExtra("f2",fjr2);
                intent.putExtra("z1",zhr1);
                intent.putExtra("z2",zhr2);
                intent.putExtra("a1",asr1);
                intent.putExtra("a2",asr2);
                intent.putExtra("m1",mgrb1);
                intent.putExtra("m2",mgrb2);
                intent.putExtra("i1",isha1);
                intent.putExtra("i2",isha2);
                intent.putExtra("namazswitch2",namazswitch);
                intent.putExtra("blkchk",blockfilter);
                intent.putExtra("bnum1",blocknumber1);
                intent.putExtra("bnum2",blocknumber2);
                intent.putExtra("bnum3",blocknumber3);
                intent.putExtra("rejectcall",rejectcall);

                startService(intent);
            }


        if (silentmode.isChecked()) {

            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor2 = preferences.edit();  // Put the values from the UI
            editor2.putInt("audiostate", myAudioManager.getRingerMode());
            editor2.apply();
            myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
        fab1.setClickable(false);
        edit.setEnabled(false);
        onoff.setEnabled(false);
        button.setClickable(false);
        silentmode.setEnabled(false);
        Snackbar.make(view, " Service Started ", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();


    }


    public void stopService1(View view) {

        if (isMyServiceRunning(AutoreplyService.class)) {
            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            myAudioManager.setRingerMode(preferences.getInt("audiostate", AudioManager.RINGER_MODE_NORMAL));
            }

        Intent intent = new Intent(this, AutoreplyService.class);
        intent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);

        stopService(intent);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAndRemoveTask();
        } else {
            System.exit(0);
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
         int id = item.getItemId();

        if (id == R.id.help) {
            Intent intent1 = new Intent(this, appinfo.class);
            startActivity(intent1);

        } else if (id == R.id.about_us) {

            Intent intent2=new Intent(this,aboutus.class);
            startActivity(intent2);

        } else if (id == R.id.contact_us) {
            Intent intent3=new Intent(this,contactus.class);
            startActivity(intent3);
        }

        else if (id == R.id.classtime) {
            Intent intent7=new Intent(this,classtime.class);
            startActivityForResult(intent7,1);

        }
        else if (id == R.id.famalert) {

            Intent intent9=new Intent(this,famalert.class);
            startActivityForResult(intent9,2);

        }

        else if (id == R.id.namaztime) {
            Intent intent10=new Intent(this,namaztime.class);
            startActivityForResult(intent10,3);

        }
        else if (id==R.id.rejectcall)
        {
        Intent intent22=new Intent(this,Rejectcalls.class);
            startActivityForResult(intent22,5);
        }

        else if (id==R.id.blacklist)
        {
            Intent intent10=new Intent(this,Blacklist.class);
            startActivityForResult(intent10,4);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }

        return true;
    }




    @Override
    public void onStart() {
        super.onStart();
}

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }


}

