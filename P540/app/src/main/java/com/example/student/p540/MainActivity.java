package com.example.student.p540;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    TextView txtv1;
    MyHandler myHandler;
    MyHandler2 myHandler2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtv1=findViewById(R.id.textView);
        myHandler = new MyHandler();
        myHandler2 = new MyHandler2();
    }

    public void onClickBtn(View v) {
        t.start();
    }

    Thread t = new Thread(new Runnable() {
        int i = 0;
        @Override
        public void run() {
            while (i<10) {
                i++;
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg = new Message();
                Bundle bundle = msg.getData();
                bundle.putInt("data",i);
                myHandler.sendMessage(msg);
            }
        }

    });


    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Message msg2 = new Message();
            Bundle bundle = msg.getData();
            msg2.setData(bundle);
            myHandler2.sendMessage(msg2);
        }
    }

    class MyHandler2 extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int result = bundle.getInt("data");
            if(result == 11) {
                txtv1.setText("Finish");
            } else {
                txtv1.setText(String.valueOf(bundle.getInt("data")));
            }
        }
    }
}
