package com.example.student.p230;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
        LinearLayout ll;
        RelativeLayout rl_apply,rl_login;
        Button bt_naver;
        WebView wv;
        EditText tx_loginid, tx_loginpw, tx_applyid, tx_applypw;
        String id="";
        String pwd="";
        TextView tx_time;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            makeUI();
            getTime();
        }
        public void makeUI(){
            ll=findViewById(R.id.ll);
            bt_naver=findViewById(R.id.bt_naver);
            wv=findViewById(R.id.wv);
            rl_apply=findViewById(R.id.rl_apply);
            rl_login=findViewById(R.id.rl_login);


            wv.getSettings().setJavaScriptEnabled(true);

            wv.setVisibility(View.INVISIBLE);
            rl_apply.setVisibility(View.INVISIBLE);
            rl_login.setVisibility(View.INVISIBLE);
            ll.setVisibility(View.VISIBLE);
            bt_naver.setVisibility(View.VISIBLE);

            wv.setWebViewClient(new WebViewClient());


    }
    public void getTime(){
        tx_time=findViewById(R.id.tx_time);


        Thread thread = new Thread(){
            public void run(){
                while(!isInterrupted()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date d = new Date();
                            String time = sdf.format(d);
                            tx_time.setText(time);
                        }
                    });
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };

        thread.start();

    }
    public void clickBtn(View v){
            //홈
        if(v.getId()==R.id.bt_home){
            //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            //makeUI();
            //startActivity(intent);
            wv.setVisibility(View.INVISIBLE);
            rl_apply.setVisibility(View.INVISIBLE);
            rl_login.setVisibility(View.INVISIBLE);
            ll.setVisibility(View.VISIBLE);
            bt_naver.setVisibility(View.VISIBLE);
        }
        //네이버
        else if(v.getId()==R.id.bt_naver){
            wv.setVisibility(View.VISIBLE);
            wv.loadUrl("http://www.naver.com");
            ll.setVisibility(View.INVISIBLE);
            bt_naver.setVisibility(View.INVISIBLE);
            rl_apply.setVisibility(View.INVISIBLE);
            rl_login.setVisibility(View.INVISIBLE);
        }
        //회원가입
        else if(v.getId()==R.id.bt_apply){
            wv.setVisibility(View.INVISIBLE);
            rl_apply.setVisibility(View.VISIBLE);
            ll.setVisibility(View.INVISIBLE);
            bt_naver.setVisibility(View.INVISIBLE);
            rl_login.setVisibility(View.INVISIBLE);
        }
        //분석
        else if(v.getId()==R.id.bt_analy){
            wv.setVisibility(View.VISIBLE);
            wv.loadUrl("http://70.12.114.149/mv");
            ll.setVisibility(View.INVISIBLE);
            bt_naver.setVisibility(View.INVISIBLE);
            rl_apply.setVisibility(View.INVISIBLE);
            rl_login.setVisibility(View.INVISIBLE);
        }
        //로그인
        else if(v.getId()==R.id.bt_login){
            wv.setVisibility(View.INVISIBLE);
            rl_apply.setVisibility(View.INVISIBLE);
            ll.setVisibility(View.INVISIBLE);
            bt_naver.setVisibility(View.INVISIBLE);
            rl_login.setVisibility(View.VISIBLE);
        }

        else if(v.getId()==R.id.bt_dologin){
            getLoginID();
        }
        else if(v.getId()==R.id.bt_doapply){
            getApplyID();
        }
    }

    public void getApplyID(){

            tx_applyid = findViewById(R.id.tx_applyid);
            tx_applypw = findViewById(R.id.tx_applypw);
            id = tx_applyid.getText().toString();
            pwd = tx_applypw.getText().toString();
            if(id!=null && !pwd.equals("")) {
                tx_applyid.setText("");
                tx_applypw.setText("");
                Toast.makeText(this, "가입완료 : " + id + "     " + pwd, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "뭐라도 입력해주세요." + pwd, Toast.LENGTH_SHORT).show();
            }

    }
    public void getLoginID(){
            tx_loginid=findViewById(R.id.tx_loginid);
            tx_loginpw=findViewById(R.id.tx_loginpw);
            String testid = tx_loginid.getText().toString();
            String testpw = tx_loginpw.getText().toString();
            //Toast.makeText(this, id+"     "+pwd,Toast.LENGTH_SHORT).show();
            if(testid.equals(id) && testpw.equals(pwd)){
                Toast.makeText(this,"로그인 성공", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this,"id, pw가 틀렸습니다. 누구세염.", Toast.LENGTH_SHORT).show();
            }
    }
}
