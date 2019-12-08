package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MahasiswaActivity extends AppCompatActivity {
ImageView iconmahasiswa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);
        iconmahasiswa=findViewById(R.id.img_profile_mhs);
        iconmahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iconms = new Intent(MahasiswaActivity.this, profil.class);
                startActivity(iconms);
            }
        });
    }
}
