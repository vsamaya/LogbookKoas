package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MahasiswaActivity extends AppCompatActivity {
    ImageView profile;
    RelativeLayout isiJurnal,CekJurnal;
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        profile = findViewById(R.id.img_profile_mhs);
        isiJurnal = findViewById(R.id.btn_isijurnal);
        CekJurnal = findViewById(R.id.btn_cekjurnal);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser();
                Intent i = new Intent(MahasiswaActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        isiJurnal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(MahasiswaActivity.this, mainIsiJurnal.class);
                startActivity(b);
            }
        });
        CekJurnal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(MahasiswaActivity.this, CekJurnal.class);
                startActivity(c);
            }
        });
    }
}
