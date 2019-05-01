package com.friendlyphire.lendsecure;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;

public class MyUncaughtHandler implements Thread.UncaughtExceptionHandler {

    private Context context;

    public MyUncaughtHandler(Context context){
        this.context = context;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        ComponentName component = new ComponentName(context,MyReceiver.class);
        DevicePolicyManager mDPM =
                (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        if(mDPM.isAdminActive(component)){
            mDPM.lockNow();
        }
    }
}
