package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class rotasiinternal extends AppCompatActivity {
    LinearLayout s9,s10,s11,s12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotasiinternal);

        s9=findViewById(R.id.s9);
        s10=findViewById(R.id.s10);
        s11=findViewById(R.id.s11);
//        s12=findViewById(R.id.s12);

        s9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(rotasiinternal.this, semester9.class);
                startActivity(a);
            }
        });
        s10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(rotasiinternal.this, semester10.class);
                startActivity(b);
            }
        });
        s11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(rotasiinternal.this, semester11.class);
                startActivity(c);
            }
        });
//        s12.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent d = new Intent(rotasiinternal.this, semester12.class);
//                startActivity(d);
//            }
//        });
    }
}


