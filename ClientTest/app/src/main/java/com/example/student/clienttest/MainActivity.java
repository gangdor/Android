package com.example.student.clienttest;

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
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    TextView tx;
    EditText editText;
    String usertext = null;
    Client client = null;
    Socket socket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        tx = findViewById(R.id.tx);
        client = new Client();
        client.start();
    }

    public void clickBTN(View v){
        if(v.getId()==R.id.btn){
            usertext = editText.getText().toString();
            client.sendMessage(usertext);
        }
    }

    class Client extends Thread{
        boolean flag = true;
        String address = "192.168.0.44";
        boolean cflag = true;
        String msg="hi";

       @Override
        public void run() {
            while(cflag) {
                try {
                    socket = new Socket(address, 3333);
                    tx.setText("Connected Server ..");
                    cflag= false;
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    tx.setText("Connected Retry ..");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }


                try{
                    Log.d("log", "잘드루와");
                    new Receiver(socket).start();
                    Sender sender = new Sender(socket);
                    Thread t = new Thread(sender);
                    sender.setSendMsg(msg);
                    t.start();
                }catch (Exception e){
                    Log.d("log", "잘안되네요");
                }
            }

        }

        public void sendMessage(String msg){
            try{
                Sender sender = new Sender(socket);
                sender.setSendMsg(msg);
                Thread t = new Thread(sender);
                t.start();
            }catch (Exception e){
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
                        Log.d("msgTest", sendMsg);
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
                this.socket = socket;
                in = socket.getInputStream();
                inr = new DataInputStream(in);
            }

            @Override
            public void run() {
                while (inr != null) {
                    try {
                        String str = inr.readUTF();
                        System.out.println(str);
                        if (str.trim().equals("q")) {
                            inr.close();
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Server Closed");
                        break;
                    }
                }

                try {
                    Thread.sleep(1000);
                    flag = false;
                    socket.close();
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
