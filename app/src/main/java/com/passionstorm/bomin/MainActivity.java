package com.passionstorm.bomin;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    final String TAG = "Bomin";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WebView wv = findViewById(R.id.webview);
        Log.d(TAG, wv.getSettings().getUserAgentString());
        Log.d(TAG, "sdk version: " + Build.VERSION.SDK_INT);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setAllowFileAccessFromFileURLs(true);
        wv.getSettings().setAllowUniversalAccessFromFileURLs(true);
        wv.getSettings().setDomStorageEnabled(true);
        wv.getSettings().setAppCacheEnabled(true);
        wv.getSettings().setAppCachePath(getApplicationContext().getFilesDir().getAbsolutePath() + "/cache");
        wv.getSettings().setDatabaseEnabled(true);
        wv.getSettings().setDatabasePath(getApplicationContext().getFilesDir().getAbsolutePath() + "/databases");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wv.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW );
        }
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError er) {
                handler.proceed();  // Ignore SSL certificate errors
            }
        });
        wv.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onConsoleMessage(ConsoleMessage console) {
                Log.d(console.sourceId() + "", console.message() + ":line " + console.lineNumber());
                return true;
            }

            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                Log.d(TAG, "Granting source...");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Log.d(TAG, "Camera granted");
                    request.grant(request.getResources());
                }
            }
        });
        wv.loadUrl("https://192.168.1.43");
    }
}
