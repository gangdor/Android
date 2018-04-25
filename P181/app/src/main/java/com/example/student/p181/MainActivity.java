package com.example.student.p181;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button bt2, bt3, bt4, bt5, bt6;
    ImageButton bt1;
    TextView tv1;
    LinearLayout bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setbtn();
    }

    public void setbtn(){
        bt1=findViewById(R.id.bt1);
        bt2=findViewById(R.id.bt2);
        bt3=findViewById(R.id.bt3);
        bt4=findViewById(R.id.bt4);
        bt5=findViewById(R.id.bt5);
        bt6=findViewById(R.id.bt6);
        tv1=findViewById(R.id.textView2);
        bg=findViewById(R.id.bg);
    }

    public void btn1(View v){
        tv1.setTextColor(Color.RED);
        bg.setBackground(getDrawable(R.drawable.bg1));
    }
    public void btn2(View v){
        tv1.setTextColor(Color.BLUE);
        bg.setBackground(getDrawable(R.drawable.bg));
    }
    public void btn3(View v){
        tv1.setTextColor(Color.GREEN);
        bg.setBackground(ContextCompat.getDrawable(this,R.drawable.bg2));
    }
    public void btn4(View v){
        tv1.setTextColor(Color.MAGENTA);
        bg.setBackground(getDrawable(R.drawable.bg3));
    }
    public void btn5(View v){
        tv1.setTextColor(Color.BLACK);
        bg.setBackground(getDrawable(R.drawable.bg4));
    }
    public void btn6(View v){
        tv1.setTextColor(Color.parseColor("#8f33cc"));
        bg.setBackground(getDrawable(R.drawable.bg5));
    }

}
