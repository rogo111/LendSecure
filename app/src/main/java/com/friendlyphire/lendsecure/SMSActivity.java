package com.friendlyphire.lendsecure;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SMSActivity extends AppCompatActivity {

    private BroadcastReceiver sentStatusReceiver, deliveredStatusReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
    }

    protected void onResume() {
        super.onResume();
        sentStatusReceiver=new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent arg1) {
                String s = "Unknown Error: Message not sent!";
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        s = "Your sms was sent successfully!";
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        s = "Generic Failure Error: message not sent!";
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        s = "Error: No Service Available, message not sent!";
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        s = "Error: Null PDU, message not sent!";
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        s = "Error: Radio is off, message not sent!";
                        break;
                    default:
                        break;
                }
                Toast.makeText(arg0,s,Toast.LENGTH_LONG).show();
                finish();
            }
        };
        deliveredStatusReceiver=new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent arg1) {
                String s = "Message Not Delivered!";
                switch(getResultCode()) {
                    case Activity.RESULT_OK:
                        s = "Message Delivered Successfully!";
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                }
                Toast.makeText(arg0,s,Toast.LENGTH_LONG).show();
                finish();
            }
        };
        registerReceiver(sentStatusReceiver, new IntentFilter("SMS_SENT"));
        registerReceiver(deliveredStatusReceiver, new IntentFilter("SMS_DELIVERED"));
    }


    protected void onPause() {
        super.onPause();
        unregisterReceiver(sentStatusReceiver);
        unregisterReceiver(deliveredStatusReceiver);
    }

    public void sendSMS(View view) {
        EditText p = findViewById(R.id.smsnumber);
        EditText m = findViewById(R.id.smstext);
        String number = p.getText().toString();
        String message = m.getText().toString();
        if(PhoneUtils.checkPremiumSMS(this,number)){
            String toast = "You are not authorized to send an sms to foreign or premium rate numbers!";
            Toast.makeText(this,toast,Toast.LENGTH_LONG).show();
            finish();
        }
        else{
            SmsManager manager = SmsManager.getDefault();
            PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT"), 0);
            PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED"), 0);
            manager.sendTextMessage(number, null, message, sentIntent, deliveredIntent);
        }
    }
}