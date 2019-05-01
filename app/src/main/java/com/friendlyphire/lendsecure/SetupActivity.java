package com.friendlyphire.lendsecure;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SetupActivity extends AppCompatActivity {

    private String posButton;
    private ComponentName component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        posButton = getString(R.string.dialogAffirm);
        component = new ComponentName(this,MyReceiver.class);
        ActivityCompat.requestPermissions(SetupActivity.this,
                new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS}, 1);
        openDeviceAdminDialog();
    }

    private void openDeviceAdminDialog(){
        DevicePolicyManager mDPM =
                (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
        if(mDPM.isAdminActive(component))
            openPhoneSMSDialog();
        else{
            DialogInterface.OnClickListener posListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, component);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,getString(R.string.devAdminExplanation));
                    startActivityForResult(intent, 0);
                    openPhoneSMSDialog();
                }
            };
            openDialog(getString(R.string.devAdminDialogMessage),getString(R.string.dialogAffirm),posListener, null, null);
        }
    }

    private void openPhoneSMSDialog(){
        if(hasPhoneSMSPermissions())
            openPinningDialog();
        else{
            DialogInterface.OnClickListener posListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.fromParts("package", getPackageName(), null));
                    startActivity(intent);
                    openPinningDialog();
                }};
            openDialog(getString(R.string.confirmDialogMessage), posButton,posListener, null,null);
        }
    }

    private boolean hasPhoneSMSPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) ==
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
    }

    private void openPinningDialog(){
        DialogInterface.OnClickListener posListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                startActivity(intent);
                openPowerButtonDialog();
            }};
        openDialog(getString(R.string.pinningDialogMessage),posButton,posListener,null,null);
    }

    private void openPowerButtonDialog(){
        DialogInterface.OnClickListener posListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
                openFinishSetupDialog();
            }};
        DialogInterface.OnClickListener negListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                openFinishSetupDialog();
            }};
        openDialog(getString(R.string.powerDialogMessage), posButton,posListener,"Skip",negListener);
    }

    private void openFinishSetupDialog(){
        final Activity ac = this;
        DialogInterface.OnClickListener posListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                startActivity(new Intent(ac,MainActivity.class));
                finish();
            }};
        openDialog("Thank you for choosing "+getString(R.string.app_name)+", you are now set up and " +
                "ready to use the app!\n\nFrom now on, when opening the app you will be prompted to pin the screen. Simply " +
                    "select to pin the screen and then you can securely lend out your phone to anyone!",
                "Continue", posListener,null,null);
    }

    private void openDialog(String message, String posButton, DialogInterface.OnClickListener posAction, String negButton,
                            DialogInterface.OnClickListener negAction){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this).setCancelable(false);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton(posButton,posAction);
        if(negButton!=null)
            alertDialogBuilder.setNegativeButton(negButton,negAction);
        alertDialogBuilder.create().show();
    }
}