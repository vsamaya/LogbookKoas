package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class profilds extends AppCompatActivity {
    Button editprofile;
    private SessionHandler session;
    Button logout;
    TextView username1, nama1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilds);
        final SessionHandler session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        username1 = findViewById(R.id.usernamedsp);
        nama1 = findViewById(R.id.namadsp);
        Bundle bundleds = getIntent().getExtras();
        assert bundleds != null;
        username1.setText(bundleds.getString("datads"));
        nama1.setText(bundleds.getString("datads1"));
        editprofile = findViewById(R.id.editds);
        logout = findViewById(R.id.logoutds);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundleds=new Bundle();
                bundleds.putString("datads",username1.getText().toString());
                bundleds.putString("datads1",nama1.getText().toString());
                Intent edit = new Intent(profilds.this, editprofilds.class);
                edit.putExtras(bundleds);
                startActivity(edit);


            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                Intent edit = new Intent(profilds.this, MainActivity.class);
                startActivity(edit);
                finish();

            }
        });
    }
}
