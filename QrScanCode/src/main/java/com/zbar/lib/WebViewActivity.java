package com.zbar.lib;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import plugin.example.commonlib.common.lib.viewioc.ContentViewById;
import plugin.example.commonlib.common.lib.viewioc.ViewById;
import plugin.example.commonlib.view.webview.ProgressWebView;


/**
 * Created by jan on 15/8/21.
 */
@ContentViewById(R.layout.activity_webview)
public class WebViewActivity extends BaseActivity {

    @ViewById(R.id.webview1)
    ProgressWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView.setWebViewClient(new MyWebViewClient());

        Intent intent = getIntent();

        String url = intent.getStringExtra("Url");
        webView.loadUrl(url);
    }


    class MyWebViewClient extends WebViewClient {
        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("tel:") || url.startsWith("sms:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    }


}
