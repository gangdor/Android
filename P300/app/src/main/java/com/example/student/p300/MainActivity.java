package com.example.student.p300;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Intent intent;
    TextView textView;
    ProgressBar pbar;
    ProgressDialog pbar2;
    ImageView iv;
    Resources res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        pbar = findViewById(R.id.progressBar);
        pbar2 = new ProgressDialog(MainActivity.this);
        pbar2.setCancelable(false);
        iv = findViewById(R.id.imageView);
        res= getResources();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);
    }

    private void processIntent(Intent intent) {
        if(intent != null) {
            String command = intent.getStringExtra("command");
            int cnt = intent.getIntExtra("cnt", 0);
            Toast.makeText(this, ""+command+" "+cnt, Toast.LENGTH_SHORT).show();
            textView.setText(command + cnt+"");
            pbar.setProgress(cnt*10);

            if(cnt%2==0){
                iv.setImageDrawable(res.getDrawable(R.drawable.minion2));
            }else{
                iv.setImageResource(R.drawable.minion3);
            }

        }
    }

    public void clickBt(View v) {
        String name = editText.getText().toString();
        // 암시적인 intent, 명시적인 Intent를 통해
        //어떤 activity나 객체를 호출할 때 사용한다.
        // 값을 전달할 때도 사용
        intent = new Intent(this, MyService.class);
        intent.putExtra("command", "show");
        intent.putExtra("name", name);
        // startActivity 아니고 startService
        startService(intent);
    }

    public void clickBt2(View v){
        pbar2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pbar2.setMessage("진행중");
        pbar2.show();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pbar2.dismiss();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(intent != null) {
            stopService(intent);
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("경고!");
        builder.setMessage("끝내시겠습니까?");
        builder.setIcon(R.drawable.btn3);
        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        builder.show();
    }
}