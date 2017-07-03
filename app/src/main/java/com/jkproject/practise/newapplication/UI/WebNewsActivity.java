package com.jkproject.practise.newapplication.UI;
/*
 *  项目名：  NewApplication 
 *  包名：    com.jkproject.practise.newapplication.UI
 *  文件名:   WebNewsActivity
 *  创建者:   JK
 *  创建时间:  2017/5/19 17:34
 *  描述：    TODO
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.jkproject.practise.newapplication.R;

public class WebNewsActivity extends BaseActivity {

    private ProgressBar progressBar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_news);

        initView();
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.wv_news);
        progressBar = (ProgressBar) findViewById(R.id.pb_news);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        final String url = intent.getStringExtra("url");

        //设置标题；
        getSupportActionBar().setTitle(title);

        //支持JS；
        webView.getSettings().setJavaScriptEnabled(true);

        //支持缩放
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        //接口回调
        webView.setWebChromeClient(new WebViewClient());
        //加载网页
        webView.loadUrl(url);

        //本地显示
        webView.setWebViewClient(new android.webkit.WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                //我接受这个事件
                return true;
            }
        });
    }

    public class WebViewClient extends WebChromeClient {

        //进度变化的监听
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress == 100){
                progressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
