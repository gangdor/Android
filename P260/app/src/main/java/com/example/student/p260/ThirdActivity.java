package com.example.student.p260;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {
    TextView textView5;
    int num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        textView5 = findViewById(R.id.textView5);
        Intent intent = getIntent();
        num = intent.getIntExtra("num2",0);
        textView5.setText(num+"");
    }
    public void clickBtn(View v){
        int result= num * 2000;
        Intent intent = new Intent();
        intent.putExtra("result", result);
        setResult(RESULT_OK, intent);
        finish();
    }
}
