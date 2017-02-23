package spr.dev.activity.webview;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import spr.dev.R;
import spr.dev.util.ShowToast;
import spr.dev.view.loading.MonIndicator;

/**
 * Created by hanki on 2017/2/23.
 */

public class Web_My extends AppCompatActivity {

    private static final String TAG = "Web_My";
    private Toolbar toolbar;
    private WebView webView;
    private MonIndicator monIndicator;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_my);

        initView();
        initBar();

    }

    private void initView() {
        webView = (WebView) findViewById(R.id.web_my);
        monIndicator = (MonIndicator) findViewById(R.id.monind_loading);


        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);//支持javascript
        webView.requestFocus();//触摸焦点起作用
        //设置允许js弹出alert
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadUrl("http://hankinspan.github.io/");

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view,
                                     String url,
                                     String message,
                                     JsResult result) {

                ShowToast.ColorToast(Web_My.this, message, 1200);

                return true;
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                monIndicator.setVisibility(View.VISIBLE);
                monIndicator.setColors(new int[]{
                        Color.rgb(255, 255, 0),
                        Color.rgb(255, 0, 0),
                        Color.rgb(60, 120, 216),
                        Color.rgb(255, 0, 255),
                        Color.rgb(0, 255, 51)});

                Log.e(TAG, "-- onPageStarted --");

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                url = "https://github.com/HankinsPan";
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e(TAG, "-- onPageFinished --");
                monIndicator.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                Log.e(TAG, "-- onReceivedError -- " + error);
                monIndicator.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                monIndicator.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    private void initBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("");
    }
}