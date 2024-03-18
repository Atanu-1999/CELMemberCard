package com.AltAir.celmembercard.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.AltAir.celmembercard.R;

import java.util.Objects;

public class WebViewActivity extends AppCompatActivity {
    ImageView back;
    TextView title;
    String page,urls;
    WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        title = findViewById(R.id.title);
        back = findViewById(R.id.back);
        webView = findViewById(R.id.webView);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getWindow().getContext(), R.color.black));

        webView.getSettings().setJavaScriptEnabled(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            // getting the string back
        {
            page = bundle.getString("page", null);
            urls = bundle.getString("urls", null);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WebViewActivity.this,MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
        if (Objects.equals(page, "1")){
            title.setText("Terms And Condition");
        }
        else if (Objects.equals(page, "2")){
            title.setText("About Your Card");
        }
        else {
            title.setText("Privacy Policy");
        }

        webView.loadUrl(urls);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(WebViewActivity.this,MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}