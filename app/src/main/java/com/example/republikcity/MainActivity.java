package com.example.republikcity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Website url
    private static String mUrl = "https://therepublikcity.com";
    //WebView
    private WebView myWebView;
    private SwipeRefreshLayout mySwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Load webView
        myWebView = findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        myWebView.loadUrl(mUrl);

        //Enabling JavaScript in webView
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        //Navigation in webView
        myWebView.setWebViewClient(new MyWebViewClient());

        mySwipeRefreshLayout = findViewById(R.id.swipeContainer);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        //Checks for network connection
                        ConnectivityManager cm
                                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                        boolean isConnected = activeNetwork != null &&
                                activeNetwork.isConnectedOrConnecting();
                        //Does not load url if no connection
                        if(isConnected == false){
                            mySwipeRefreshLayout.setRefreshing(false);
                            Toast.makeText(MainActivity.this, "No Network Connection!", Toast.LENGTH_LONG).show();
                        }else {
                            myWebView.reload();
                        }
                    }
                }
        );
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        myWebView = findViewById(R.id.webview);
                // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    public class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().contains("therepublikcity")) {
                // This is my website, so do not override; let my WebView load the page

                //Checks for network connection
                ConnectivityManager cm
                        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                //Does not load url if no connection
                if(isConnected == false){
                   Toast.makeText(MainActivity.this, "No Network Connection!", Toast.LENGTH_LONG).show();
                   return true;
                }
                //Returns false if there's connection
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            if(Uri.parse(url).getHost().contains("twitter") || Uri.parse(url).getHost().contains("facebook") ||
                    Uri.parse(url).getHost().contains("instagram") || Uri.parse(url).getHost().contains("pinterest")){
                //Intent takes social media links to web Browser
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            mySwipeRefreshLayout.setRefreshing(false);
        }


    }

}
