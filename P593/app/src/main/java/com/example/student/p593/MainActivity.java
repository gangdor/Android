package com.example.student.p593;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;

//HTTP 통신하기
public class MainActivity extends AppCompatActivity {
    EditText editText1, editText2;
    LoginTask loginTask;
    LocationTask locationTask;
    ProgressDialog progressDialog;
    AlertDialog dialog;
    AlertDialog.Builder alBuilder;
    boolean flag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1 = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        progressDialog = new ProgressDialog(this);
        alBuilder = new AlertDialog.Builder(this);
        new Thread(r).start();
    }

    @Override
    public void onBackPressed() {
        alBuilder.setTitle("Alert");
        alBuilder.setMessage("Are you finish App?");
        alBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                flag=false;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
        alBuilder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });

        dialog=alBuilder.create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            while(flag){
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                locationTask = new LocationTask("http://70.12.114.149/android/location.jsp");
                locationTask.execute(37.12, 127.123);

            }
        }
    };

    public void clickBt(View v){
        loginTask = new LoginTask("http://70.12.114.149/android/login.jsp");
        String id = editText1.getText().toString();
        String pwd = editText2.getText().toString();
        if(id == null || pwd ==null || id.equals("") || pwd.equals("")){
            return;
        }
        loginTask.execute(id.trim(),pwd.trim());



    }

    class LocationTask extends  AsyncTask<Double, Void, String>{
        String url;
        public LocationTask() {}

        public LocationTask(String url) {
            this.url=url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }



        @Override
        protected String doInBackground(Double... doubles) {
            double lat = doubles[0];
            double lon = doubles[1];

            url += "?lat="+lat+"&lon="+lon;
            //http 통신
            StringBuilder sb = new StringBuilder();
            HttpURLConnection conn=null;
            try{

                URL url = new URL(this.url);
                conn = (HttpURLConnection)url.openConnection();

                if(conn!=null){
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(10000);
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Accept", "*/*");
                    if(conn.getResponseCode() != HttpURLConnection.HTTP_OK)
                        return null;
                }

            }catch (Exception e){
                return e.getMessage();
            }finally {
                conn.disconnect();
            }


            return "";
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
        }

    }


    class LoginTask extends AsyncTask<String, String, String>{
        String url ="";
        public LoginTask(){}
        public LoginTask(String url) {
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Login ...");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            String id = strings[0];
            String pwd = strings[1];
            url += "?id="+id+"&pwd="+pwd;

            //http 통신
            StringBuilder sb = new StringBuilder();
            HttpURLConnection conn=null;
            BufferedReader reader=null;
            try{
                URL url = new URL(this.url);
                conn = (HttpURLConnection)url.openConnection();
                if(conn!=null){
                   // conn.setConnectTimeout(5000);
                    conn.setReadTimeout(10000); //값읽는 제한시간
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Accept", "*/*");
                    if(conn.getResponseCode() != HttpURLConnection.HTTP_OK)
                        return null;
                    reader= new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = null;
                    while(true){
                        line= reader.readLine();
                        if(line==null)
                            break;
                        sb.append(line);
                    }
                }
            }catch (Exception e){
                progressDialog.dismiss();
                return e.getMessage();
            }finally {
                try {
                    if(reader!=null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                conn.disconnect();
            }
            return sb.toString();

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {

            progressDialog.dismiss();
            Log.d("whatthe...",s);
            if(s.trim().equals("1")){

                Toast.makeText(MainActivity.this, "LOGIN OK",Toast.LENGTH_SHORT).show();
            }
            else if(s.trim().equals("0")){
                Toast.makeText(MainActivity.this, "LOGIN FAIL",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, s,Toast.LENGTH_SHORT).show();
            }
        }
    }
}

