package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class rotasiinternal extends AppCompatActivity {
ListView listView;
String semester[]=new String[]{
        "Semester 9","Semester 10","Semester 11","semester12"
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotasiinternal);

        listView=findViewById(R.id.listview1);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(rotasiinternal.this,android.R.layout.simple_list_item_1,android.R.id.text1,semester);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Intent p0= new Intent(rotasiinternal.this,semester9.class);
                    startActivity(p0);
                }
               else if(position==1){
                    Intent p1= new Intent(rotasiinternal.this,semester10.class);
                    startActivity(p1);
                }
               else if(position==2){
                    Intent p2= new Intent(rotasiinternal.this,semester11.class);
                    startActivity(p2);
               }
               else if (position==3){
                   Intent p3=new Intent(rotasiinternal.this,semester12.class);
                    startActivity(p3);
                }
            }
        });
    }
}
