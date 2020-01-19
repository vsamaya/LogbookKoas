package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Rotasi extends AppCompatActivity {
LinearLayout r_stase,r_internal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotasi);
        r_stase=findViewById(R.id.r_stase);
        r_internal=findViewById(R.id.r_internal);

        r_stase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Rotasi.this, RotasiStase.class);
                startActivity(a);
            }
        });

        r_internal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(Rotasi.this, RotasiInternal.class);
                startActivity(b);
            }
        });

    }
}
