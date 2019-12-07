package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MahasiswaActivity extends AppCompatActivity {
    ImageView profile;
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        profile = findViewById(R.id.img_profile_mhs);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MahasiswaActivity.this, profil.class);
                startActivity(i);
                finish();
            }
        });
    }
}
