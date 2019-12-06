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
ImageView profile;
    private SessionHandler session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosen);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        btn_generatecode=findViewById(R.id.btn_generatecode);
        btn_generateqr=findViewById(R.id.btn_generateqr);
        btn_dftrkeg=findViewById(R.id.btn_dftrkeg);
        btn_rekapkeg=findViewById(R.id.btn_rekapkeg);
        profile = findViewById(R.id.img_profile_dsn);

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

        btn_dftrkeg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent c = new Intent(DosenActivity.this, DaftarKegiatan.class);
                startActivity(c);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser();
                Intent i = new Intent(DosenActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });




    }
}
