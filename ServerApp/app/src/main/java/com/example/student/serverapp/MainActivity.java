package com.example.student.serverapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    TextView tx1, tx2;
    EditText et1;
    ServerAndroid server;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx1 = findViewById(R.id.tx1);
        tx2 = findViewById(R.id.tx2);
        et1 = findViewById(R.id.et1);
        try {
            server = new ServerAndroid();
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.start();

    }

    public void click(View v){
        String str = et1.getText().toString();
        server.sendAll(str);
    }

    public void setSpeed(final String msg){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tx1.setText(msg);
                    }
                });
            }
        };

        new Thread(r).start();
    }

    public void setConnect(final String ip, final String msg){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(msg.equals("t"))
                            tx2.setText(ip +" : Car Connected");
                        else{
                            tx2.setText("Car Disconnected");
                        }
                    }
                });
            }
        };
        new Thread(r).start();
    }


    // HttpRequest Start
    class SendHttp extends AsyncTask<Void, Void, Void>{
        String surl = "http://70.12.114.149:8000/webserver/main.do?speed=";
        URL url;
        public void SetUrl(String speed){
            surl+=speed;
            try {
                url = new URL(surl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection conn;
            try {
                conn = (HttpURLConnection)url.openConnection();
                if(conn!=null){
                    //conn.setReadTimeout(10000);
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Accept","*/*");
                    conn.getResponseCode(); //getResponseCode를 해야 실제 Request가 전달된다.
                    Log.d("ServerApp::","HTTP REQUEST SUCCESS"+surl);
                    /*if(conn.getResponseCode() != HttpURLConnection.HTTP_OK)
                        return null;*/
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    // HttpRequest End

    // ServerSocket Start....
    class ServerAndroid extends Thread {
        ServerSocket serversocket;
        boolean flag = true;
        boolean rflag = true;
        // String ip = null;
        // ArrayList<DataOutputStream> list = new ArrayList<>();
        HashMap<String, DataOutputStream> hmap = new HashMap<>();

        public ServerAndroid() throws IOException {
            // ServerSocket Create
            serversocket = new ServerSocket(8888);
        }

        @Override
        public void run() {
            // Accept Client Connection
            try {
                while (flag) {
                    //System.out.println("Ready Accept");
                    Socket socket = serversocket.accept(); // 멈춰있다가 반응이 오면 작동
                    // ip = socket.getInetAddress().toString();
                    String ip = socket.getInetAddress().toString();
                    setConnect(ip,"t");
                    Receiver receiver = new Receiver(socket); // socket을 receiver에게 전달
                    receiver.start();
                }
            } catch (Exception e) {

            }

        }

        // Inner Class Receiver & Sender
        class Receiver extends Thread {
            // 여러 클라이언트가 서버에 들어오면 가장 먼저 거치는 스레드
            InputStream in;
            DataInputStream din;
            OutputStream out;
            DataOutputStream dout;
            Socket socket;
            String ip;

            public Receiver(Socket socket) {
                try {
                    in = socket.getInputStream();
                    din = new DataInputStream(in);
                    out = socket.getOutputStream();
                    dout = new DataOutputStream(out);
                    this.socket = socket;
                    // list.add(dout); //dout을 list에 담아 준다.
                    // System.out.println("Connected Count : "+list.size());
                    ip = socket.getInetAddress().toString();
                    hmap.put(ip, dout);



                } catch (IOException e) {
                    e.printStackTrace();
                }
            } // end Receiver

            @Override
            public void run() {
                try {
                    while (rflag) {
                        if (socket.isConnected() && din != null && din.available() > 0) {
                            String str = din.readUTF();
                            // sendAll("["+ip+"] : "+str);
                            setSpeed(str);

                            SendHttp sendHttp = new SendHttp();
                            sendHttp.SetUrl(str);
                            sendHttp.execute();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    setConnect(null, "f");
                    server.stopServer();
                    try {
                        Thread.sleep(200);
                        if (din != null) {
                            din.close();
                        }
                        if (dout != null) {
                            dout.close();
                        }
                        if (socket != null) {
                            socket.close();
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }

        public void sendAll(String msg) {
            Sender sender = new Sender();
            sender.setMsg(msg);
            sender.start();
        }

        // Send Message All clients
        class Sender extends Thread {
            String msg;

            public void setMsg(String msg) {
                this.msg = msg;
            }

            @Override
            public void run() {
                try { // 클라이언트에 보내주기
                    Iterator<String> it = hmap.keySet().iterator();
                    if (hmap.size() > 0) {
                        while (it.hasNext()) {
                            String str = it.next();
                            Log.d("ServerApp ::",str);
                            hmap.get(str).writeUTF(msg);


                        }
                    }
                } catch (Exception e) {
                    // e.printStackTrace();
                }
            }
        }

        public void stopServer() {
            rflag = false;
        }
    }
    // ServerSocket End....

} // end Server MainActivity
