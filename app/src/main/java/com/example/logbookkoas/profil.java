package com.example.logbookkoas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class profil extends AppCompatActivity {
Button editprofile;
Button logout;
SessionHandler session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        editprofile=findViewById(R.id.editp);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        logout=findViewById(R.id.logout);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(profil.this, editprofil.class);
                startActivity(edit);

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser();
                Intent i = new Intent(profil.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
