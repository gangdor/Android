package com.example.student.p467;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ItemAdapter itemAdapter;
    ArrayList<Item> list;
    LinearLayout container;
    GridView gv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        list.add(new Item("사람1", "11", 30, R.drawable.btn1));
        list.add(new Item("사람2", "22", 31, R.drawable.btn2));
        list.add(new Item("사람3", "33", 32, R.drawable.btn3));
        list.add(new Item("사람4", "44", 33, R.drawable.btn4));
        list.add(new Item("사람5", "55", 34, R.drawable.btn5));
        list.add(new Item("사람6", "66", 35, R.drawable.btn6));
        list.add(new Item("사람7", "77", 36, R.drawable.btn7));

        itemAdapter = new ItemAdapter(list);
        container = findViewById(R.id.container);
        gv = findViewById(R.id.gridview);
        gv.setAdapter(itemAdapter);
    }

    public void clickBt(View v){
        itemAdapter.addItem(new Item("사람8","88",37, R.drawable.btn4));
        itemAdapter.notifyDataSetChanged(); // 아뎁터에 새로온 데이터 리셋해주기
    }

    //ItemAdapter
    public class ItemAdapter extends BaseAdapter {
        ArrayList<Item> list;
        //Context context;
        public ItemAdapter(){}

        public ItemAdapter(ArrayList<Item> list){
            this.list = list;
            //this.context= context;
        }

        //리스트에 데이터를 뿌려주는 역할
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        public void addItem(Item item){
            list.add(item);
        }

        //하나의 아이템을 불러옴, 10개의 아이템이면 10번 호출됌
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View vw = null;
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vw = inflater.inflate(R.layout.item, container, true);
            TextView name = vw.findViewById(R.id.name);
            TextView num = vw.findViewById(R.id.phone);
            TextView age= vw.findViewById(R.id.age);
            ImageView img = vw.findViewById(R.id.imageView);
            name.setText(list.get(i).getName());
            num.setText(list.get(i).getMobile());
            age.setText(list.get(i).getAge()+"");
            img.setImageResource(list.get(i).getResId());
            return vw;
        }
    }
}
