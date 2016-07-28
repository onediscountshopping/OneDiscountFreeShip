package com.example.administrator.onediscountfreeship;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class GoodsDetailActivity extends AppCompatActivity {
    private String path="http://h5.m.taobao.com/awp/core/detail.htm?id=%s";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        webView = (WebView) findViewById(R.id.wv_detail);
        String url = getIntent().getStringExtra("url");
        String id = url.substring(url.indexOf("=") + 1);
        path=String.format(path,id);
        Log.i("==", "onCreate: "+path);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
                return false;
            }
        });
        webView.loadUrl(path);
    }

    public void click(View view) {
        switch (view.getId()){
            case R.id.iv_detail_back:
                finish();
                break;
            case R.id.iv_share:
                break;
        }
    }

    @Override//webView支持回退
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode ==KeyEvent.KEYCODE_BACK&&webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
