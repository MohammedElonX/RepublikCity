package com.example.republikcity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    //Website url
    private static String mUrl = "https://therepublikcity.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Load webView
        WebView myWebView = new WebView(this);
        setContentView(myWebView);
        myWebView.loadUrl(mUrl);

        //Navigation in webView
        myWebView.setWebViewClient(new WebViewClient());
    }
}
