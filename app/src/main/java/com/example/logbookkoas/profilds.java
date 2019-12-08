package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class profilds extends AppCompatActivity {
    Button editprofile;
    private SessionHandler session;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilds);
        session = new SessionHandler(getApplicationContext());
        User user= session.getUserDetails();
        editprofile=findViewById(R.id.editds);
        logout=findViewById(R.id.logoutds);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(profilds.this,editprofilds.class);
                startActivity(edit);

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                Intent edit = new Intent(profilds.this,MainActivity.class);
                startActivity(edit);
                finish();

            }
        });
    }
}
