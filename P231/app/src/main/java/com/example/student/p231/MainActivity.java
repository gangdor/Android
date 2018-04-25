package com.example.student.p231;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    RadioButton rb1, rb2;
    CheckBox cb1, cb2;
    Switch sw1;
    ToggleButton tb1;
    RadioGroup rg;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeUI();
    }
    public void makeUI(){
        rb1=findViewById(R.id.rb1);
        rb2=findViewById(R.id.rb2);
        cb1=findViewById(R.id.cb1);
        cb2=findViewById(R.id.cb2);
        sw1=findViewById(R.id.sw1);
        tb1=findViewById(R.id.tb1);
        rg=findViewById(R.id.rg);
        et=findViewById(R.id.editText);

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = et.getText().toString();
                Toast.makeText(MainActivity.this, start+" "+before+" " +count+" "+ str, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //anonimus class
        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                Toast.makeText(MainActivity.this,"결과 : " +b, Toast.LENGTH_SHORT).show();
                if(b==true){
                    rb1.setVisibility(View.VISIBLE);
                    rb2.setVisibility(View.VISIBLE);
                }else{
                    rb1.setVisibility(View.INVISIBLE);
                    rb2.setVisibility(View.INVISIBLE);
                }
            }
        });
        tb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(MainActivity.this, "결과 : "+isChecked, Toast.LENGTH_SHORT).show();
                if(isChecked==true){
                    rg.setVisibility(View.VISIBLE);
                }else{
                    rg.setVisibility(View.INVISIBLE);
                }
            }
        });

    }
    public void clickBt(View v){
        String str = "";
        if(cb1.isChecked())
            str+="공부";
        if(cb2.isChecked())
            str+="잠자기";
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
    public void clickBt2(View v){
        String str="";
        if(rb1.isChecked())
            str+="남자";
        else if(rb2.isChecked())
            str+="여자";
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();


    }

}
