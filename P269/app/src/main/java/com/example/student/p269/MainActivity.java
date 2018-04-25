package com.example.student.p269;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickBt(View v){
        Intent intent =null;
        switch(v.getId()){
            case R.id.bt1 :
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.naver.com"));
                break;

            case R.id.bt2 :
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-0000-0000"));
                break;

            case R.id.bt3 :
                intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-5332-6549"));
                break;

        }
        startActivity(intent);
    }
}
