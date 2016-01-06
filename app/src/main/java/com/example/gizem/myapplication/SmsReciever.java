package com.example.gizem.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Gizem on 02.01.2016.
 */
public class SmsReciever extends BroadcastReceiver {
        SQLiteDatabase db;
        ContactDB dh;
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle pudsBundle = intent.getExtras();
        Object[] pdus = (Object[]) pudsBundle.get("pdus");
        SmsMessage messages = SmsMessage.createFromPdu((byte[]) pdus[0]);

        String comingNumber = messages.getDisplayOriginatingAddress();
        Toast.makeText(context, "Yeni SMS: " + comingNumber,
                Toast.LENGTH_LONG).show();
        Log.d(getClass().getName().toString(), "huh" + comingNumber);
      //  ContactDB db = new ContactDB(MainActivity.);
             //   db.getReadableDatabase();
           /*     db = context.openOrCreateDatabase("contacts",0,null);
               dh = new ContactDB(context);
              dh.getWritableDatabase();
            Contact con =    dh.findContact(comingNumber);
        if(con!=null)
        {
            con.setComeMes(con.getComeMes()+1);
            dh.updateContact(con);
            dh.close();
        }*/

    }

}

   /* @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING))
        {
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Log.e("numara",incomingNumber);
           // Toast.makeText(context, incomingNumber, Toast.LENGTH_LONG).show();
        }
    }*/
