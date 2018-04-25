package com.example.student.p246;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Resources res;
    ImageView up,down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);

    }
    public void clickButton(View v){
        BitmapDrawable bitmap =null;
        if(v.getId()==R.id.bt1){
            bitmap=(BitmapDrawable)down.getDrawable();
            up.setImageDrawable(bitmap);
            down.setImageDrawable(null);
        }
        else if(v.getId()==R.id.bt2){
            bitmap=(BitmapDrawable)up.getDrawable();
            down.setImageDrawable(bitmap);
            up.setImageDrawable(null);
        }
    }


}
