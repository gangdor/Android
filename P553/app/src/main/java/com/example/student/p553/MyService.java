package com.example.student.p553;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent == null){
            return Service.START_STICKY;
        }else{
            processCommand(intent);
        }
        return super.onStartCommand(intent,flags, startId);
    }

    private void processCommand(Intent intent){
        final Intent sintent = new Intent(getApplicationContext(), MainActivity.class);
        sintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        Runnable run = new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<21; i++){
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sintent.putExtra("data",i);
                    startActivity(sintent);
                }

            }
        };
        new Thread(run).start();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
