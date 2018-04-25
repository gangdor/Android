package com.example.student.p211;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Login_success extends AppCompatActivity {
    TextView ok_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        ok_id = findViewById(R.id.ok_id);
        Intent intent = getIntent();  // Main에 넣었던 intent 불러오기
        String id = intent.getStringExtra("loginID");  // intent에서 ID로 값 추출하기
        ok_id.setText(id);
    }
}
