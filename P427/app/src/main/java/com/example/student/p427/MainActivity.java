package com.example.student.p427;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    WebView wv;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        wv = findViewById(R.id.wv);
        wv.setWebViewClient(new WebViewClient());
        wv.addJavascriptInterface(new JS(), "js");
        handler = new Handler();
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    public void clickBt(View v){
        wv.loadUrl("http://70.12.114.149/AD/sample.html");
    }

    public void clickBt2(View v){
        //현재 스레드에서 동작함
        handler.post(new Runnable() {
            @Override
            public void run() {
                wv.loadUrl("javascript:changeImg()");
            }
        });

    }

    final class JS{
        JS(){}
        //자바스크립트에서 안드로이드로 호출할 수 있는 어노테이션
        @android.webkit.JavascriptInterface
        public void clickJS(int i){

            textView.setText(String.valueOf(i));
            Log.d("[ JS ]" , "Event process ........."+i);
            Toast.makeText(MainActivity.this, "test : "+i , Toast.LENGTH_SHORT).show();
        }
    }

}
