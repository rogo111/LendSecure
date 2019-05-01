package com.friendlyphire.lendsecure;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
    }

    @Override
    public void onBackPressed(){
        if(!isCallActive(this))
            super.onBackPressed();
    }
    private static boolean isCallActive(Context context) {
        AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (manager.getMode() == AudioManager.MODE_IN_CALL) {
            return true;
        } else {
            return false;
        }
    }

    protected void makeCall(View v){
        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        EditText et = findViewById(R.id.editText);
        String text = et.getText().toString();
        if (!PhoneUtils.checkValid(text) || PhoneUtils.checkForeign(this, text) || PhoneUtils.checkPremium(this, text)){
            String toast = "You are not authorized to call foreign or premium rate numbers!";
            Toast.makeText(this,toast,Toast.LENGTH_LONG).show();
            finish();
        }
        else{
            et.setText("");
            et.setHint(text);
            phoneIntent.setData(Uri.parse("tel:"+text));
            if (ActivityCompat.checkSelfPermission(CallActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            ((Button)findViewById(R.id.cbutton)).setText("Calling...");
            et.setEnabled(false);
            findViewById(R.id.cbutton).setEnabled(false);
            findViewById(R.id.textView).setVisibility(View.VISIBLE);
            findViewById(R.id.backview).setVisibility(View.VISIBLE);
            startActivity(phoneIntent);
        }
    }
}