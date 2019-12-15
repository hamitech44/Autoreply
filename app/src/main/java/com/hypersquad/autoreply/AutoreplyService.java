package com.hypersquad.autoreply;

import android.Manifest;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.provider.CallLog;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
//import android.support.v7.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class AutoreplyService extends Service  {



    Double lat, longg;
    String txtphoneNo1, txtMessage1, uri;
    String sthour, stminute, enhour, enminute, familynumber, familynumber2, familynumber3,familynumber4,familynumber5;
    String blocknumer1,blocknumer2,blocknumer3,driving="no";
    String ff1, ff2, zz1, zz2, aa1, aa2, mm1, mm2, ii1, ii2;
    boolean namazswitch,blockswitch,rejectcalls;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        if (Objects.equals(intent.getAction(), Constants.ACTION.STARTFOREGROUND_ACTION)) {
            final Boolean classtimes = Objects.requireNonNull(intent.getExtras()).getBoolean("needed2", false);
             namazswitch=intent.getExtras().getBoolean("namazswitch2",false);
            blockswitch=intent.getExtras().getBoolean("blkchk");
            rejectcalls=intent.getExtras().getBoolean("rejectcall",false);
            final String msg = intent.getStringExtra("needed");
            familynumber = intent.getStringExtra("famoptionnum");
            familynumber2 = intent.getStringExtra("famoptionnum2");
            familynumber3 = intent.getStringExtra("famoptionnum3");
            familynumber4 = intent.getStringExtra("famoptionnum4");
            familynumber5 = intent.getStringExtra("famoptionnum5");

            blocknumer1=intent.getStringExtra("bnum1");
            blocknumer2=intent.getStringExtra("bnum2");
            blocknumer3=intent.getStringExtra("bnum3");
            final Boolean familyoption = intent.getExtras().getBoolean("famoption", false);
            sthour = intent.getStringExtra("sthour");
            stminute = intent.getStringExtra("stminute");
            enhour = intent.getStringExtra("enhour");
            enminute = intent.getStringExtra("enminute");
            ff1 = intent.getStringExtra("f1");
            ff2 = intent.getStringExtra("f2");
            zz1 = intent.getStringExtra("z1");
            zz2 = intent.getStringExtra("z2");
            aa1 = intent.getStringExtra("a1");
            aa2 = intent.getStringExtra("a2");
            mm1 = intent.getStringExtra("m1");
            mm2 = intent.getStringExtra("m2");
            ii1 = intent.getStringExtra("i1");
            ii2 = intent.getStringExtra("i2");

            final Boolean mybool = intent.getExtras().getBoolean("needed1", false);

            /* Notification builder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.bubbles)
                            .setAutoCancel(true)
                            .setOngoing(true)
                            .setContentTitle("Smart Notifier")
                            .setContentText("App is running").build();
            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,
                    builder);*/
            final TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            final PhoneStateListener callStateListener = new PhoneStateListener() {
                public void onCallStateChanged(int state, String incomingNumber) {
                    // TODO React to incoming call.

                    Toast.makeText(getApplicationContext(), "Monitering incoming calls", Toast.LENGTH_SHORT).show();

                    if (state==TelephonyManager.CALL_STATE_RINGING && blockswitch &&
                            (blocknumer1.equalsIgnoreCase(incomingNumber)||blocknumer2.equalsIgnoreCase(incomingNumber)
                            ||blocknumer3.equalsIgnoreCase(incomingNumber)) )
                    {
                        Class clazz = null;
                        try {
                            clazz = Class.forName(telephonyManager != null ? telephonyManager.getClass().getName() : null);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        Method method = null;
                        try {
                            if (clazz != null) {
                                method = clazz.getDeclaredMethod("getITelephony");
                            }
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        if (method != null) {
                            method.setAccessible(true);
                        }
                        ITelephony telephonyService = null;
                        try {
                            telephonyService = (ITelephony) (method != null ? method.invoke(telephonyManager) : null);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        if (telephonyService != null) {
                            telephonyService.endCall();
                        }
                    }
                    else if (state==TelephonyManager.CALL_STATE_RINGING && rejectcalls)
                    {
                        Class clazz = null;
                        try {
                            clazz = Class.forName(telephonyManager.getClass().getName());
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        Method method = null;
                        try {
                            if (clazz != null) {
                                method = clazz.getDeclaredMethod("getITelephony");
                            }
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        if (method != null) {
                            method.setAccessible(true);
                        }
                        ITelephony telephonyService = null;
                        try {
                            telephonyService = (ITelephony) (method != null ? method.invoke(telephonyManager) : null);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        if (telephonyService != null) {
                            telephonyService.endCall();
                        }
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(incomingNumber, null, msg +
                                "  @Message sent by 'Smart Notifier' App.", null, null);
                        Toast.makeText(getApplicationContext(), "sms sent", Toast.LENGTH_SHORT).show();


                    }
                    if (state == TelephonyManager.CALL_STATE_IDLE) {
                        if ("yes".equals(driving))
                        {
                            SmsManager smsManager = SmsManager.getDefault();

                            smsManager.sendTextMessage(incomingNumber, null, "This person is driving", null, null);

                        }
                        else {
                            try {
                                Thread.sleep(200);
                                locationSender();
                                getCallDetails(msg, mybool, classtimes, familyoption, namazswitch);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            };

            telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);


        } else if (intent.getAction().equals(
                Constants.ACTION.STOPFOREGROUND_ACTION)) {
            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;
    }

    public  void onDestroy(){
        super.onDestroy();
        Process.killProcess(Process.myPid());
    }

    private void locationSender() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                lat = location.getLatitude();
                longg = location.getLongitude();
                uri = "http://maps.google.com/?q=" + lat + "," + longg;


            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);


    }


    private void getCallDetails(String msg1, boolean bool, boolean timing, boolean fam,boolean namaz) {
        try {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding

                return;
            }
//            Toast.makeText(getApplicationContext(), "driving :"+driving, Toast.LENGTH_SHORT).show();

            String where = CallLog.Calls.TYPE + "=" + CallLog.Calls.MISSED_TYPE + " AND IS_READ != 1";
            Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, where, null, null);
            if (managedCursor != null) {

                int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
                int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
                int nam = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
                int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
                managedCursor.moveToFirst();
                String callTypeCode = managedCursor.getString(type);
                String name = managedCursor.getString(nam);
                int callcode = Integer.parseInt(callTypeCode);

                String callDate = managedCursor.getString(date);
                Date callTime = new Date(Long.valueOf(callDate));
                Date newtime = new Date();
                long diffMs = newtime.getTime() - callTime.getTime();

                long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(diffMs);


                if (bool) {
                    if (callcode == CallLog.Calls.MISSED_TYPE && !name.isEmpty()) {
                        if (diffInSeconds < 50) {

                            txtphoneNo1 = managedCursor.getString(number);
                            txtMessage1 = msg1 + "     @Message sent by 'Smart Notifier' App.";
                            managedCursor.close();
                            if (timing||namaz) {
                                sendNamazMessage(txtphoneNo1,txtMessage1,fam);
                            }
                            else {
                                sendSMSMessage(txtphoneNo1, txtMessage1, fam);
                            }
                        }
                    }
                } else {
                    if (callcode == CallLog.Calls.MISSED_TYPE) {
                        if (diffInSeconds < 50) {
                            txtphoneNo1 = managedCursor.getString(number);
                            txtMessage1 = msg1 + "     @Message sent by 'Smart Notifier' App.";
                            managedCursor.close();
                            if (timing||namaz)
                            {
                                sendNamazMessage(txtphoneNo1, txtMessage1,fam);
                            }
                            else
                            {
                                sendSMSMessage(txtphoneNo1, txtMessage1, fam);
                            }

                        }

                    }
                }

            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    protected void sendSMSMessage(String phoneNo, String message, boolean fam2) {
        Log.i("Send SMS", "");
        try {

            SmsManager smsManager = SmsManager.getDefault();
            if (fam2 && (familynumber.equals(phoneNo) || familynumber2.equals(phoneNo) || familynumber3.equals(phoneNo)
                    ||familynumber4.equals(phoneNo)||familynumber5.equals(phoneNo))) {


                smsManager.sendTextMessage(phoneNo, null, "Sorry I cannot take your call if this is an emergency." +
                        " Here is my location.  " + String.valueOf(Uri.parse(uri)), null, null);


                Toast.makeText(getApplicationContext(), "sms sent", Toast.LENGTH_SHORT).show();

                return;
            }

            if (sleepDetect()){
                smsManager.sendTextMessage(txtphoneNo1, null, "This person is sleeping right now. Call later    " +
                        "  @Message sent by 'Smart Notifier' App.", null, null);
                Toast.makeText(getApplicationContext(), "sms sent", Toast.LENGTH_SHORT).show();
            return;
            }

                smsManager.sendTextMessage(phoneNo, null, message, null, null);


            Toast.makeText(getApplicationContext(), "sms sent", Toast.LENGTH_SHORT).show();


        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void sendNamazMessage(String pnum, String defaultmsg,boolean fam) {

        try {
            String string1 = sthour+":"+stminute+":"+"00";
            Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);
            calendar1.add(Calendar.DATE, 1);


            String string2 = enhour+":"+enminute+":"+"00";
            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            calendar2.add(Calendar.DATE, 1);

            Calendar calendar3 = Calendar.getInstance();
            int c1 =calendar3.get(Calendar.HOUR_OF_DAY);
            int c2=calendar3.get(Calendar.MINUTE);
            String someRandomTime=String.valueOf(c1)+":"+String.valueOf(c2)+":"+"00";
            Date d = new SimpleDateFormat("HH:mm:ss").parse(someRandomTime);

            calendar3.setTime(d);
            calendar3.add(Calendar.DATE, 1);

            Date x = calendar3.getTime();
            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                //checks whether the current time is between two times
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(pnum, null, "I'm in Class/Office right now. Call me later    " +
                        "  @Message sent by 'Smart Notifier' App.", null, null);
                Toast.makeText(getApplicationContext(), "sms sent", Toast.LENGTH_SHORT).show();
            return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            String string1 = ff1 + ":" + "00";
            Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);
            calendar1.add(Calendar.DATE, 1);


            String string2 = ff2 + ":" + "00";
            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            calendar2.add(Calendar.DATE, 1);

            Calendar calendar3 = Calendar.getInstance();
            int c1 = calendar3.get(Calendar.HOUR_OF_DAY);
            int c2 = calendar3.get(Calendar.MINUTE);
            String someRandomTime = String.valueOf(c1) + ":" + String.valueOf(c2) + ":" + "00";
            Date d = new SimpleDateFormat("HH:mm:ss").parse(someRandomTime);

            calendar3.setTime(d);
            calendar3.add(Calendar.DATE, 1);

            Date x = calendar3.getTime();
            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime()) && sleepDetect()) {
                //checks whether the current time is between two times
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(pnum, null, "This person is reading Namaz-e-fajar right now. Call later    " +
                        "  @Message sent by 'Smart Notifier' App.", null, null);
                Toast.makeText(getApplicationContext(), "sms sent", Toast.LENGTH_SHORT).show();

                return;
            }
            else if (sleepDetect()){
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(pnum, null, "This person is Sleeping right now. Call later    " +
                        "  @Message sent by 'Smart Notifier' App.", null, null);
                Toast.makeText(getApplicationContext(), "sms sent", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            String string1 = zz1 + ":" + "00";
            Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);
            calendar1.add(Calendar.DATE, 1);


            String string2 = zz2 + ":" + "00";
            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            calendar2.add(Calendar.DATE, 1);

            Calendar calendar3 = Calendar.getInstance();
            int c1 = calendar3.get(Calendar.HOUR_OF_DAY);
            int c2 = calendar3.get(Calendar.MINUTE);
            String someRandomTime = String.valueOf(c1) + ":" + String.valueOf(c2) + ":" + "00";
            Date d = new SimpleDateFormat("HH:mm:ss").parse(someRandomTime);

            calendar3.setTime(d);
            calendar3.add(Calendar.DATE, 1);

            Date x = calendar3.getTime();
            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                //checks whether the current time is between two times
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(pnum, null, "This person is reading Namaz-e-Zohar right now. Call later    " +
                        "  @Message sent by 'Smart Notifier' App.", null, null);
                Toast.makeText(getApplicationContext(), "sms sent", Toast.LENGTH_SHORT).show();

                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            String string1 = aa1 + ":" + "00";
            Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);
            calendar1.add(Calendar.DATE, 1);


            String string2 = aa2 + ":" + "00";
            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            calendar2.add(Calendar.DATE, 1);

            Calendar calendar3 = Calendar.getInstance();
            int c1 = calendar3.get(Calendar.HOUR_OF_DAY);
            int c2 = calendar3.get(Calendar.MINUTE);
            String someRandomTime = String.valueOf(c1) + ":" + String.valueOf(c2) + ":" + "00";
            Date d = new SimpleDateFormat("HH:mm:ss").parse(someRandomTime);

            calendar3.setTime(d);
            calendar3.add(Calendar.DATE, 1);

            Date x = calendar3.getTime();
            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                //checks whether the current time is between two times
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(pnum, null, "This person is reading Namaz-e-Asar right now. Call later    " +
                        "  @Message sent by 'Smart Notifier' App.", null, null);
                Toast.makeText(getApplicationContext(), "sms sent", Toast.LENGTH_SHORT).show();

                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            String string1 = mm1 + ":" + "00";
            Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);
            calendar1.add(Calendar.DATE, 1);


            String string2 = mm2 + ":" + "00";
            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            calendar2.add(Calendar.DATE, 1);

            Calendar calendar3 = Calendar.getInstance();
            int c1 = calendar3.get(Calendar.HOUR_OF_DAY);
            int c2 = calendar3.get(Calendar.MINUTE);
            String someRandomTime = String.valueOf(c1) + ":" + String.valueOf(c2) + ":" + "00";
            Date d = new SimpleDateFormat("HH:mm:ss").parse(someRandomTime);

            calendar3.setTime(d);
            calendar3.add(Calendar.DATE, 1);

            Date x = calendar3.getTime();
            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                //checks whether the current time is between two times
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(pnum, null, "This person is reading Namaz-e-Maghrib right now. Call later    " +
                        "  @Message sent by 'Smart Notifier' App.", null, null);
                Toast.makeText(getApplicationContext(), "sms sent", Toast.LENGTH_SHORT).show();
                return;

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            String string1 = ii1 + ":" + "00";
            Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);
            calendar1.add(Calendar.DATE, 1);


            String string2 = ii2 + ":" + "00";
            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            calendar2.add(Calendar.DATE, 1);

            Calendar calendar3 = Calendar.getInstance();
            int c1 = calendar3.get(Calendar.HOUR_OF_DAY);
            int c2 = calendar3.get(Calendar.MINUTE);
            String someRandomTime = String.valueOf(c1) + ":" + String.valueOf(c2) + ":" + "00";
            Date d = new SimpleDateFormat("HH:mm:ss").parse(someRandomTime);

            calendar3.setTime(d);
            calendar3.add(Calendar.DATE, 1);

            Date x = calendar3.getTime();
            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                //checks whether the current time is between two times
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(pnum, null, "This person is reading Namaz-e-Isha right now. Call later    " +
                        "  @Message sent by 'Smart Notifier' App.", null, null);
                Toast.makeText(getApplicationContext(), "sms sent", Toast.LENGTH_SHORT).show();
                return;

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sendSMSMessage(pnum, defaultmsg, fam);

}


    public boolean sleepDetect(){
        KeyguardManager myKM = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);

        try {
            String string1 = "00:00:00";
            Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);
            calendar1.add(Calendar.DATE, 1);


            String string2 = "07:00:00";
            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            calendar2.add(Calendar.DATE, 1);

            Calendar calendar3 = Calendar.getInstance();
            int c1 =calendar3.get(Calendar.HOUR_OF_DAY);
            int c2=calendar3.get(Calendar.MINUTE);
            String someRandomTime=String.valueOf(c1)+":"+String.valueOf(c2)+":"+"00";
            Date d = new SimpleDateFormat("HH:mm:ss").parse(someRandomTime);

            calendar3.setTime(d);
            calendar3.add(Calendar.DATE, 1);

            Date x = calendar3.getTime();


            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {

                if( myKM.inKeyguardRestrictedInputMode()) {

                    Toast.makeText(getApplicationContext(), "locked sleep", Toast.LENGTH_SHORT).show();

                    return true;

                } else {
                    Toast.makeText(getApplicationContext(), "unlocked not sleep", Toast.LENGTH_SHORT).show();
                    return false;
                }

            }
            else{
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;


    }


}

