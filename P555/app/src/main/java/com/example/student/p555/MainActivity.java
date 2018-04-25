package com.example.student.p555;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    EditText editText1, editText2;
    TextView textView;
    LoginTask loginTask;
    ProgressDialog progressDialog;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        textView = findViewById(R.id.textView);
        btn = findViewById(R.id.button);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Process");
        progressDialog.setMessage("wait a second");
        progressDialog.setCancelable(false);
    }

    public void clickLoging(View v) throws ExecutionException, InterruptedException {
        progressDialog.show();
        loginTask = new LoginTask();
        String id = editText1.getText().toString();
        String pw = editText2.getText().toString();
        String result = "";

        loginTask.execute(id,pw);


    }

    class LoginTask extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            btn.setEnabled(false);
        }

        @Override
        protected String doInBackground(String... strings) {
            String id = strings[0];
            String pw = strings[1];
            String result="";
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(id.equals("qq")&&pw.equals("11")){
                result= "1";
            }else{
                result= "0";
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            btn.setEnabled(true);
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            if(s.equals("1")){
                textView.setText("Login OK");
                dialog.setTitle("ALERT");
                dialog.setMessage("Login Success");
                dialog.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
                AlertDialog alert = dialog.create();
                alert.show();
            }else{
                textView.setText("Login Fail, Please Try again");
                dialog.setTitle("ALERT");
                dialog.setMessage("Login Fail");
                dialog.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                AlertDialog alert = dialog.create();
                alert.show();
            }
        }
    }
}
