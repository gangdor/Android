package com.example.student.clientapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    TextView speed;
    ImageView iv;
    ClientAndroid client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speed = findViewById(R.id.speed);
        iv = findViewById(R.id.iv);
        client = new ClientAndroid();
        client.start();
    }

    public void click(View v){
        String msg = speed.getText().toString();
        client.sendMsg(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.StopClient();
    }

    public void convertImg(final String str){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(str.equals("1")){
                            iv.setImageResource(R.drawable.car1);
                        }else if(str.equals("2")){
                            iv.setImageResource(R.drawable.car2);
                        }else if(str.equals("3")){
                            iv.setImageResource(R.drawable.car3);
                        }

                    }
                });
            }
        };
        new Thread(r).start();
    }

    public class ClientAndroid extends Thread {
        // 1. 소켓을 만드는 역할
        // 2. Receiver
        // 3. Sender
        String address = "70.12.114.150";
        Socket socket;
        boolean cflag = true;
        boolean flag = true;

        @Override
        public void run() {
            while (cflag) {
                try {
                    socket = new Socket(address, 8888); // 소켓이 만들어지는 시점
                    Log.d("[Client App Log]","Connected Server ..");
                    cflag = false;
                    break; // 소켓이 연결되면 탈출
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Toast.makeText(MainActivity.this, "Connected Retry ..", Toast.LENGTH_SHORT).show();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            // 연결이 이루어 진 이후
            try {
                new Receiver(socket).start();  // 데이터 받는 스레드
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public void sendMsg(String msg) {
            try {
                Sender sender = new Sender(socket); // sender를 만들어서 소켓에 담아서
                sender.setSendMsg(msg); // 메세지를 넣어주고
                new Thread(sender).start(); // 스레드를 돌리라
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        class Sender implements Runnable {
            Socket socket;
            OutputStream out;
            DataOutputStream outw;
            String sendMsg;

            public Sender(Socket socket) throws IOException {
                this.socket = socket;
                out = socket.getOutputStream();
                outw = new DataOutputStream(out);
            }

            public void setSendMsg(String sendMsg) {
                this.sendMsg = sendMsg;
            }

            @Override
            public void run() {
                try {
                    if (outw != null) {
                        // 계속 보내는 역할을 한다.
                        outw.writeUTF(sendMsg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        class Receiver extends Thread {
            Socket socket;
            InputStream in;
            DataInputStream inr;

            public Receiver(Socket socket) throws IOException {
                // Input의 역할을 한다.
                this.socket = socket;
                in = socket.getInputStream();
                inr = new DataInputStream(in);
            }

            @Override
            public void run() {
                // 계속 스레드가 실행하며 문자열을 받는다.
                try {
                    while (flag == true && inr != null) {
                        String str = inr.readUTF();
                        Log.d("[Client App Log]",str);
                        convertImg(str);
                    }
                } catch (Exception e) {
                    Log.d("[Client App Log]","Server Closed ..");

                }finally { //while루프 탈출하면 수행
                    try {
                        if(inr!= null)
                            inr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void StopClient() {
            try {
                Thread.sleep(1000);
                flag = false;
                if (socket != null) {
                    socket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
