package com.friendlyphire.lendsecure;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.WebBackForwardList;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class BrowserActivity extends AppCompatActivity {

    private WebView wv;
    private EditText et;
    private String startUrl = "https://www.google.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        et = findViewById(R.id.searchbar);
        et.setText(startUrl);
        et.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    go(null);
                    return true;
                }
                return false;
            }
        });

        wv = findViewById(R.id.wview);
        wv.setWebViewClient(new MyBrowser());
        wv.getSettings().setLoadsImagesAutomatically(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv.loadUrl(startUrl);
    }

    @Override
    protected void onDestroy(){
        wv.clearCache(true);
        wv.clearHistory();
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
        super.onDestroy();
    }

    @Override
    public void onBackPressed(){
        if(wv.canGoBack()){
            wv.goBack();
            WebBackForwardList prev = wv.copyBackForwardList();
            et.setText(prev.getItemAtIndex(prev.getCurrentIndex()).getUrl());
        }
        else super.onBackPressed();
    }

    public void goBack(View view) {
        super.onBackPressed();
    }

    public void previous(View view){
        onBackPressed();
    }

    public void go(View view){
        String url = et.getText().toString();
        if(url.length()>=4&&!url.substring(0,4).equals("http"))
            url = "https://".concat(url);
        wv.loadUrl(url);
        et.clearFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            et.setText(url);
            wv.loadUrl(url);
            return true;
        }
    }
}