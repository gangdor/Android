package com.example.student.p536;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView= findViewById(R.id.textView);
        Toast.makeText(this, textView+"", Toast.LENGTH_SHORT).show();
    }

    public void clickBtn(View v){
        t.start();
    }

    Thread t = new Thread(new Runnable() {
        int i=1;
        @Override
        public void run() {
            while(i<10){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 모든 앱에는 main Thread가 있다. 지금은 t라는 새로운 스레드를 만들었고,
                        // 해당 스레드에서 main Thread의 View를 변경하려 한다.
                        // 안드로이드에서는 이를 원칙적으로 허용하지 않는다.
                        // runOnUiThread를 사용해서 해결할 수 있다.
                        textView.setText(String.valueOf(i));
                    }
                });

            }
        }
    });
}
