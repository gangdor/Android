package com.example.student.p221;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Resources res;
   // Button bt1, bt2, bt3;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        res = getResources();
        imageView=findViewById(R.id.iv);
        //makeUI();
    }
  //  public void makeUI(){
  //      bt1=findViewById(R.id.bt1);
  //      bt2=findViewById(R.id.bt2);
  //      bt3=findViewById(R.id.bt3);
  //
  //  }
    public void clickBt(View v){
        BitmapDrawable bitmap=null;
        if(v.getId() == R.id.bt1){
            bitmap = (BitmapDrawable) res.getDrawable(R.drawable.bg4);
        }else if(v.getId() == R.id.bt2){
            bitmap = (BitmapDrawable) res.getDrawable(R.drawable.bg5);
        }else if(v.getId() == R.id.bt3){
            bitmap = (BitmapDrawable) res.getDrawable(R.drawable.bg6);
        }
        imageView.setImageDrawable(bitmap);

    }
}
