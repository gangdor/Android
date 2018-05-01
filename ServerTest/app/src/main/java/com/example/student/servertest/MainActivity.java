package com.example.student.servertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    TextView tv_ip;
    TextView tv_msg;
    Server server = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_ip=findViewById(R.id.tv_ip);
        tv_msg=findViewById(R.id.tv_msg);
        Init();
    }
    public void Init(){
        try {
            server = new Server();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    class Server extends Thread{
        ServerSocket serverSocket;
        boolean flag = true;
        boolean rflag = true;
        HashMap<String, DataOutputStream> map = new HashMap<>();

        public Server() throws IOException{
            serverSocket = new ServerSocket(3333);
        }

        @Override
        public void run() {
                try {
                    while(flag) {
                        tv_msg.setText("Ready Accept");
                        Socket socket = serverSocket.accept();
                        Receiver receiver = new Receiver(socket);
                        receiver.start();
                    }
                } catch (Exception e) {

                }
        }

        class Receiver extends Thread{
            InputStream in;
            DataInputStream din;
            OutputStream out;
            DataOutputStream dout;
            Socket socket;
            String ip;

            public Receiver(Socket socket){
                try{
                    in = socket.getInputStream();
                    din = new DataInputStream(in);
                    out = socket.getOutputStream();
                    dout = new DataOutputStream(out);
                    this.socket = socket;
                    //list.add(dout); //dout을 list에 담아 준다.
                    //System.out.println("Connected Count : "+list.size());
                    ip = socket.getInetAddress().toString();
                    SetIP(ip, "Message Input");
                    map.put(ip, dout);
                }catch (Exception e){

                }
            }

            public void SetIP(final String ip, final String msg){
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("testcc",ip);
                                tv_ip.setText(ip);
                                tv_msg.setText(msg);
                            }
                        });
                    }
                };

                new Thread(runnable).start();
            }

            @Override
            public void run() {
                try {
                    while(rflag) {
                        if(socket.isConnected() && din !=null && din.available()>0) {
                            String str = din.readUTF();
                            if(str!=null && str.equals("q")) {
                                map.remove(ip);
                                break;
                            }
                            SetIP(ip, str);
                        }
                    }
                }catch(Exception e) {
                    e.printStackTrace();
                }finally {
                    try {
                        Thread.sleep(200);
                        if(din!=null) {din.close();}
                        if(dout!=null) {dout.close();}
                        if(socket!=null) {socket.close();}
                    }catch(Exception e){}
                }
            }
        }


        }
    }