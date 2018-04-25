package com.example.student.p368;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyService2 extends Service {
    public MyService2() {
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
                for (int i =1; i<=10; i++){
                    sintent.putExtra("command","service2");
                    sintent.putExtra("cnt",i);
                    startActivity(sintent);
                    try{
                        Thread.sleep(1500);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
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
