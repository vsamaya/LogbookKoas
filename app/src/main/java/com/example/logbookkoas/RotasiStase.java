package com.example.logbookkoas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class RotasiStase extends AppCompatActivity {
LinearLayout s9,s10,s11,s12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotasiinternal);

        s9=findViewById(R.id.s9);
        s10=findViewById(R.id.s10);
        s11=findViewById(R.id.s11);
        s12=findViewById(R.id.s12);

        s9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(RotasiStase.this, semester_rotasi.class);
                a.putExtra("semester","9");
                startActivity(a);
            }
        });
        s10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(RotasiStase.this, semester_rotasi.class);
                b.putExtra("semester","10");
                startActivity(b);
            }
        });
        s11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(RotasiStase.this, semester_rotasi.class);
                c.putExtra("semester","11");
                startActivity(c);
            }
        });
        s12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent d = new Intent(RotasiStase.this, semester_rotasi.class);
                d.putExtra("semester","12");
                startActivity(d);
            }
        });
    }
}
