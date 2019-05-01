package com.friendlyphire.lendsecure;

import android.Manifest;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ComponentName component = new ComponentName(this,MyReceiver.class);
        DevicePolicyManager mDPM =
                (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
        if(!(hasPermission(Manifest.permission.CALL_PHONE)&&hasPermission(Manifest.permission.SEND_SMS)&&mDPM.isAdminActive(component))){
            startActivity(new Intent(this,SetupActivity.class));
            finish();
        }
        else{
            setContentView(R.layout.activity_main);
            ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            boolean isLocked;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                isLocked = activityManager.getLockTaskModeState() != ActivityManager.LOCK_TASK_MODE_NONE;
            else
                isLocked = activityManager.isInLockTaskMode();
            if(!isLocked)
                startLockTask();
            Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtHandler(this));
        }
    }

    private boolean hasPermission(String permission){
        return ActivityCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void launchCall(View v){
        if(hasPermission(Manifest.permission.CALL_PHONE)){
            startActivity(new Intent(this,CallActivity.class));
        }
        else{
            Toast.makeText(this,"You are not authorized to make calls on this phone.",Toast.LENGTH_LONG).show();
        }
    }

    public void launchSMS(View v){
        if(hasPermission(Manifest.permission.SEND_SMS)){
            startActivity(new Intent(this,SMSActivity.class));
        }
        else{
            Toast.makeText(this,"You are not authorized to send SMS messages on this phone.",Toast.LENGTH_LONG).show();
        }
    }

    public void launchInternet(View v){
        startActivity(new Intent(this, BrowserActivity.class));
    }
}