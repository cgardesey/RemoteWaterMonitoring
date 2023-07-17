package com.remote.water.monitoring.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.remote.water.monitoring.R;

public class ChartActivity extends AppCompatActivity {

    ImageView loadinggif;
    private WebView webView;
    LinearLayout error_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        webView = (WebView) findViewById(R.id.webView);
        error_loading = findViewById(R.id.error_loading);
        loadinggif = findViewById(R.id.loadinggif);

        Glide.with(getApplicationContext()).asGif().load(R.drawable.spinner).apply(new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.spinner)
                .error(R.drawable.error)).into(loadinggif);

        webView.loadUrl("http://2schooldirect.net:55554/chart");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                webView.setVisibility(View.VISIBLE);
                view.scrollTo(0, 0);
                loadinggif.setVisibility(View.GONE);
                error_loading.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error){
                //Your code to do
                webView.setVisibility(View.GONE);
                view.scrollTo(0, 0);
                loadinggif.setVisibility(View.GONE);
                error_loading.setVisibility(View.VISIBLE);
                Toast.makeText(ChartActivity.this, "Connection error.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        error_loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("http://2schooldirect.net:55554/chart");
            }
        });
    }
}