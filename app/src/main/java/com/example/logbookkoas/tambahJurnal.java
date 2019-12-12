package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class tambahJurnal extends AppCompatActivity {
TextView jurnal,potong;
    private SessionHandler session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_jurnal);
        jurnal = findViewById(R.id.jurnal);
        potong = findViewById(R.id.potong);
        Intent intent = getIntent();
        final String jenis_jurnal = intent.getStringExtra("jurnal");
        String nim = "22010118220192";
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        String username = user.getUsername();
        String ang = username.substring(6,8);
        String angkatan = "20"+ang;
        jurnal.setText(jenis_jurnal);
        potong.setText(angkatan);


    }
}
