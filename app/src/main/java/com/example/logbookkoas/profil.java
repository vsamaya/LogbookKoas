package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class profil extends AppCompatActivity {
Button editprofile;
private SessionHandler session;
Button logout;
TextView username,nama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        editprofile=findViewById(R.id.editp);
        session= new SessionHandler(getApplicationContext());
        final User user=session.getUserDetails();
        final String username1=user.getUsername();
        String level1=user.getLevel();
        Bundle bundle = getIntent().getExtras();
        username=findViewById(R.id.usernamemahasiswa);
        nama=findViewById(R.id.namamahasiswa);
        username.setText(bundle.getString("data1"));
        nama.setText(bundle.getString("data2"));

        logout=findViewById(R.id.logout);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("data1", username.getText().toString());
                bundle.putString("data2", nama.getText().toString());
                Intent edit = new Intent(profil.this,editprofil.class);
                edit.putExtras(bundle);
                startActivity(edit);

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                user.setUsername("");
                user.setLevel("");

                Intent edit = new Intent(profil.this,MainActivity.class);
                startActivity(edit);
                finish();

            }
        });


    }
}
