package com.example.logbookkoas;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainApprove extends AppCompatActivity {
    LinearLayout qr, otp, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_approve);
        pass = findViewById(R.id.pass);
        otp = findViewById(R.id.otp);
        qr = findViewById(R.id.qr);

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getApplicationContext(), PassApprove.class);
                startActivity(a);
            }
        });

        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(getApplicationContext(), OtpApprove.class);
                startActivity(b);
            }
        });

        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(getApplicationContext(), QrApprove.class);
                startActivity(c);
            }
        });
    }
}
