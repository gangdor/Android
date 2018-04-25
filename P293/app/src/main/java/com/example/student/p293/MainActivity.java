package com.example.student.p293;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    Date date = new Date();
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Create",Toast.LENGTH_SHORT).show();
        sp = getSharedPreferences("pref",Activity.MODE_PRIVATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "start",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Destory",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "Stop",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Pause",Toast.LENGTH_SHORT).show();
        saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Resume",Toast.LENGTH_SHORT).show();
        restoreState();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "Restart",Toast.LENGTH_SHORT).show();
    }

    public void restoreState(){
        if(sp!=null){
            if(sp.contains("cnt") && sp.contains("date")){
                String rdate = sp.getString("date", "");
                int rcnt= sp.getInt("cnt",0);
                Toast.makeText(this, "  "+rcnt, Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void saveState(){
        SharedPreferences.Editor editor = sp.edit();

        if(sp != null){
            if(sp.contains("cnt")){
                int rcnt = sp.getInt("cnt",0);
                editor.putInt("cnt",++rcnt);

            }else{
                int cnt = 0;
                editor.putInt("cnt",++cnt);
            }
            editor.commit();
        }
    }

    // back버튼을 누르면 이 함수를 거쳐서 나간다.
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        final SharedPreferences.Editor editor = sp.edit();

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Alert Message!!");
        builder.setMessage("Are you want to exit & clear?");
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor.putInt("cnt",0);
                editor.commit();
                finish();
            }
        });
        builder.show();

    }
}
