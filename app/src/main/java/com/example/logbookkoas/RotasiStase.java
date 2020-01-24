package com.example.logbookkoas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RotasiStase extends AppCompatActivity {
LinearLayout s9,s10,s11,s12;
Button note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotasistase);

        s9=findViewById(R.id.s9);
        s10=findViewById(R.id.s10);
        s11=findViewById(R.id.s11);
        s12=findViewById(R.id.s12);
        note=findViewById(R.id.note);

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
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://logbook.fk.undip.ac.id/koas/rotasi_internal.php?username=mhsw_trial&password=f722e04d8a84f6ce289ffc5034e3a88a"));
                String title = "Pilih browser untuk membuka  tautan";
                Intent chooser = Intent.createChooser(intent, title);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
            }
        });
    }
}
