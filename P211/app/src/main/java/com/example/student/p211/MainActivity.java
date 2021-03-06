package com.example.student.p211;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText tx_id, tx_pwd;
    Button bt_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getID();
    }

    public void getID(){
        tx_id = findViewById(R.id.tx_id);
        tx_pwd = findViewById(R.id.tx_pwd);
        bt_login = findViewById(R.id.bt_login);
    }

    public void clickLogin(View v){
        //textedit에서 값을 가지고 온다.
        String id = tx_id.getText().toString();
        String pwd = tx_pwd.getText().toString();
        tx_id.setText("");
        tx_pwd.setText("");

        Toast.makeText(this, id+" "+pwd, Toast.LENGTH_SHORT).show();

        Intent intent=null;
        if(id.equals("qq")&&pwd.equals("11")){
            intent = new Intent(getApplicationContext(), Login_success.class );
            intent.putExtra("loginID",id);  //intent의 형태는 MAP 형태이다.
        }
        else{
            intent = new Intent(getApplicationContext(), Login_fail.class );

        }
        startActivity(intent);
    }
}
