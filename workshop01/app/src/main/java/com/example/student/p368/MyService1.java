package com.example.student.p368;

import android.app.Service;
import android.content.Intent;
import android.nfc.Tag;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.FrameLayout;

public class MyService1 extends Service {
    public MyService1() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MYSERVICE1", "SERVICE ONCREATE ======");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MYSERVICE1", "SERVICE ONSTARTCOMMAND======");

        if(intent==null){
            return Service.START_STICKY;
        }else{
            processCommand(intent);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void processCommand(Intent intent){
        final Intent sintent = new Intent(getApplicationContext(),MainActivity.class);

        // FLAG_ACTIVITY_SINGLE_TOP, FLAG_ACTIVITY_CLEAR_TOP 현재 떠있는 액티비티를 이용할 수 있따.
        sintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Runnable run = new Runnable() {

            @Override
            public void run() {
                    try {
                        Thread.sleep(3000);
                        //fl.setVisibility(View.VISIBLE);
                        sintent.putExtra("command","service1");
                        sintent.putExtra("state",0);
                        startActivity(sintent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

            }
        };
        new Thread(run).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MYSERVICE1","Service onDestroy ....");   //로그에 TAG 붙임

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
