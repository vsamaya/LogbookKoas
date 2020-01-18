package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class menurotasi extends AppCompatActivity {
    LinearLayout rtssts,rtsinter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menurotasi);
        rtssts=findViewById(R.id.rtsts);
        rtsinter=findViewById(R.id.rtinter);
        rtssts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(menurotasi.this,rotasiinternal.class);
                startActivity(i);
            }
        });
        rtsinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(menurotasi.this,rtsinternal.class);
                startActivity(i);
            }
        });
    }
}
