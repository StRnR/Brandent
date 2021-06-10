package com.pixium.clinitick.views;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.pixium.clinitick.R;

public class WebViewActivity extends AppCompatActivity {
    public static String EXTRA_REQUEST = "com.pixium.clinitick.EXTRA_REQUEST";
    public static String ABOUT_REQUEST = "com.pixium.clinitick.ABOUT_REQUEST";
    public static String MEMBERSHIP_REQUEST = "com.pixium.clinitick.MEMBERSHIP_REQUEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        WebView webView = findViewById(R.id.wv_about);

        String request = getIntent().getStringExtra(EXTRA_REQUEST);

        if (request.equals(ABOUT_REQUEST)) {
            webView.loadUrl("https://clinitick.com/");
        } else if (request.equals(MEMBERSHIP_REQUEST)) {
            webView.loadUrl("https://auth.clinitick.com");
        }
    }
}