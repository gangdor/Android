package com.example.student.p554;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    ProgressBar progressBar;
    MyTask myTask;
    Button button;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        progressBar = findViewById(R.id.progressBar);
        button = findViewById(R.id.button);
        myTask = new MyTask("192.168.111.100");
        progressDialog = new ProgressDialog(this);
    }

    public void clickBt(View v){
        myTask = new MyTask("192.168.111.100");
        //execute().get()을 하면 다음 코드를 수행하지 않는다. get()를 리턴 받을 떄 까지!!!!!
        //get()을 수행하면 PreExecute, PostExecute가 수행되지 않는다.
        myTask.execute(); //스레드 시작
        Log.d("click.....","@@@@@@@@@@@@@@@");
    }

    //스레드시작 전, 수행 중, 수행 후 모든 걸 핸들링 할 수 있다.
    //AsyncTask<a, b, c>
    //a는 아규먼트
    //b는 update의 타입
    //c는 return 타입
    class MyTask extends AsyncTask<String, Integer, Integer>{
        String msg;
        public MyTask(){super();}
        public MyTask(String msg){
            this.msg=msg;
        }

        @Override //스레드 시작 전,
        protected void onPreExecute() {
            progressBar.setMax(55);
            textView.setText("start Thread....");
            button.setEnabled(false);
            progressDialog.setTitle("Progress");
            progressDialog.setMessage("Ing...");
            progressDialog.show();
        }

        @Override //스레드 수행 중
        protected Integer doInBackground(String... str) { //String 타입의 가변 변수, 이름은 integers
            //String msg;
            int result=0;
            //Log.d("doInBackground",integers[0]+"Start~~~~~~~~");

            for (int i=1;i<=10; i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                result+=i;
                publishProgress(result); //자동적으로 onProgressUpdate를 호출해준다.
            }
            Log.d("doInBackground","end~~~~~~~~");
            return result;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressBar.setProgress(values[0].intValue());
    }

        @Override //스레드 종료 후
        protected void onPostExecute(Integer result) {
            textView.setText("End Thread : "+ result);
            button.setEnabled(true);
            progressDialog.dismiss();
        }
    }
}
