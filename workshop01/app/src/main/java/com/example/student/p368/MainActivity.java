package com.example.student.p368;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    ProgressDialog pdialog;
    FrameLayout fl;
    ProgressBar pb;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fl=findViewById(R.id.fl);
        pdialog = new ProgressDialog(MainActivity.this);
        pdialog.setCancelable(true);
        pb = findViewById(R.id.progressBar);
        iv = findViewById(R.id.imageView);
        fl.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);
    }

    protected void processIntent(Intent intent){
        if(intent!=null){
            String command=intent.getStringExtra("command");

            if(command.equals("service1")){
                int state = intent.getIntExtra("state",0);
                if(state==0){
                    pdialog.dismiss();
                    stopService(intent);
                    fl.setVisibility(View.VISIBLE);


                }
            }else if(command.equals("service2")){

                 int count= intent.getIntExtra("cnt",0);
                 pb.setProgress(count*10);

            }else if(command.equals("service3")){
                int imagestate = intent.getIntExtra("imagestate",0);
                if(imagestate==0)
                    iv.setImageResource(R.drawable.yuna);
                else if(imagestate==1)
                    iv.setImageResource(R.drawable.thequeen);
            }
        }
    }

    public void clickBtn1(View v){
        pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdialog.setMessage("3초만 기다려주세요");
        pdialog.show();
        intent = new Intent(this,MyService1.class);
        startService(intent);
    }
    public void clickBtn2(View v){
        intent = new Intent(this, MyService2.class);
        startService(intent);
        intent = new Intent(this, MyService3.class);
        startService(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(intent!=null){
            stopService(intent);
        }
    }
}
