package com.example.student.p425;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wv = findViewById(R.id.wv);
        wv.setWebViewClient(new WebViewClient());

        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    public void clickBT(View v){
        if(v.getId() == R.id.button){
            wv.loadUrl("http://m.naver.com");
        }
        else if(v.getId() == R.id.button2){
            wv.loadUrl("http://m.nate.com");
        }
    }

}
