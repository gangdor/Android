package com.example.student.p253;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    LinearLayout container;
    LayoutInflater inflater;
    //Button sbt1, sbt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeUI();
    }

    private void makeUI() {
        container=findViewById(R.id.container);
        inflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //sbt1 = findViewById(R.id.sbt1);
        //sbt2 = findViewById(R.id.sbt2);
    }
    public void clickbt(View v){
        inflater.inflate(R.layout.sub,container,true);
        TextView stv = findViewById(R.id.stv); //findViewBy
        stv.setText("Button Click");
        Button sbt1 = container.findViewById(R.id.sbt1);
        Button sbt2 = container.findViewById(R.id.sbt2);
        sbt1.setText("Sub Button 1");
        sbt2.setText("Sub Button 2");

    }
    public void clickbt2(View v){
        Button s2bt,s3bt;
        s2bt=findViewById(R.id.sbt1);
        s3bt=findViewById(R.id.sbt2);
        s2bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sub2변경
                inflater.inflate(R.layout.sub2,container,true);

            }
        });

        s3bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sub3변경
                inflater.inflate(R.layout.sub3,container,true);
            }
        });
    }
}
