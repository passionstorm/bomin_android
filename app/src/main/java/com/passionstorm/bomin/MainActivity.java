package com.passionstorm.bomin;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
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
        Log.d(TAG, "build sdk version: " + Build.VERSION.SDK_INT);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setAllowFileAccessFromFileURLs(true);
        wv.getSettings().setAllowUniversalAccessFromFileURLs(true);
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
        wv.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onConsoleMessage(ConsoleMessage console) {
                Log.d("Webview", console.message() + "  :line "
                        + console.lineNumber() + " of "
                        + console.sourceId());
                return true;
            }

            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                Log.d(TAG, "grant source");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    request.grant(request.getResources());
                }

//                Log.d(TAG, "onPermissionRequest");
//                MainActivity.this.runOnUiThread(new Runnable() {
//                    @TargetApi(Build.VERSION_CODES.M)
//                    @Override
//                    public void run() {
//                        Log.d(TAG, request.getOrigin().toString());
//                        if(request.getOrigin().toString().equals("file:///")) {
//                            Log.d(TAG, "GRANTED");
//                            request.grant(request.getResources());
//                        } else {
//                            Log.d(TAG, "DENIED");
//                            request.deny();
//                        }
//                    }
//                });
            }
        });
        wv.loadUrl("http://localhost");
    }
}
