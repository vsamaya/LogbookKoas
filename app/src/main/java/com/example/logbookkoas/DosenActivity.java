package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class DosenActivity extends AppCompatActivity {
Button btn_generatecode,btn_generateqr,btn_dftrkeg,btn_rekapkeg;
ImageView iconprofil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosen);
        btn_generatecode=findViewById(R.id.btn_generatecode);
        btn_generateqr=findViewById(R.id.btn_generateqr);
        btn_dftrkeg=findViewById(R.id.btn_dftrkeg);
        btn_rekapkeg=findViewById(R.id.btn_rekapkeg);
        iconprofil=findViewById(R.id.iconprofil);
        iconprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent icon=new Intent(DosenActivity.this,profilds.class);
                startActivity(icon);
            }
        });
        btn_generatecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(DosenActivity.this, MainOTP.class);
                startActivity(a);
            }
        });

        btn_generateqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(DosenActivity.this, MainQR.class);
                startActivity(b);
            }
        });




    }
}
