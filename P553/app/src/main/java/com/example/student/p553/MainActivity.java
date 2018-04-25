package com.example.student.p553;


import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//스레드 2개 사용해서 이미지 switch하기
public class MainActivity extends AppCompatActivity {
    ImageView imageView, imageView2, imageView3;
    myHandler myHandler1;
    int arr[];
    String arr2[];
    Intent intent = new Intent( );
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        myHandler1 = new myHandler();
        textView = findViewById(R.id.textView);
        makeImage();
    }

    public void makeImage(){
        arr=new int[]{R.drawable.btn2,R.drawable.btn3,R.drawable.btn4,R.drawable.btn5,
                R.drawable.btn6,R.drawable.btn7};
        arr2=new String[]{"#66ffff","#69f2fc", "#6be6fa", "#6ed9f7", "#70ccf5", "#73bff2", "#75b2f0",
 	                    "#78a6ed", "#7a99eb", "#7d8ce8", "#8080e6",  "#8273e3", "#8566e0", "#8759de",
 	                    "#8a4ddb", "#8c40d9", "#8f33d6", "#9126d4", "#9419d1", "#960dcf", "#9900cc"};
        //arr3= new int[]{ 0x66ffff,        };
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);
    }

    public void processIntent(Intent intent){
        if(intent!=null){
            int data = intent.getIntExtra("data",0);
            //imageView3.setBackgroundColor(Integer.parseInt(arr2[data]));
            imageView3.setColorFilter(Color.parseColor(arr2[data]));
            textView.setText(data+"");
        }
    }

    Runnable r1 = new Runnable() {
        @Override
        public void run() {
            for (int i=0; i<=5; i++){
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final int x = i;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(arr[x]);
                    }
                });
            }
        }
    };

    //Thread t = new Thread();

    Runnable r2 = new Runnable() {
        @Override
        public void run() {
            for (int i =0; i<=5; i++){
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final int x = i;
                Message msg = new Message();
                Bundle bundle = msg.getData();
                bundle.putInt("imgs",arr[x]);
                myHandler1.sendMessage(msg);
            }
        }
    };

    //Thread t2 = new Thread();

    class myHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {

            Bundle bundle =  msg.getData();
            int img = bundle.getInt("imgs");
            imageView2.setImageResource(img);
        }
    }

    public void clickBt(View v){
        Thread t = new Thread(r1);
        Thread t2 = new Thread(r2);
        t.start();
        t2.start();

        intent = new Intent(this, MyService.class);
        startService(intent);
    }

    public void clickBt2(View v){
        stopService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(intent!=null)
            stopService(intent);
    }
}
