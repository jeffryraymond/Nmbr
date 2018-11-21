package com.jeffryraymond.nmbr.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by Jeffry Raymond on 2018-11-19.
 */

public class PhoneCallReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
            showToast(context, "Call started...");
        } else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)){
            showToast(context, "Call ended...");
        } else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)){
            String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            showToast(context, "Incoming call...");
            Log.i("Number", "onReceive: the incoming number is: " + number);
        }
    }

    public void showToast(Context context, String message){
        Toast toast = Toast.makeText(context, message,Toast.LENGTH_SHORT);
        toast.show();

    }


}
