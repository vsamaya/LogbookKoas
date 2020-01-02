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
        Intent intent = getIntent();
        final String id_jurnal = intent.getStringExtra("id_jurnal");
        final String jurnal = intent.getStringExtra("jurnal");
        final String dosen = intent.getStringExtra("dosen");

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getApplicationContext(), PassApprove.class);
                a.putExtra("id_jurnal",id_jurnal);
                a.putExtra("jurnal",jurnal);
                a.putExtra("dosen",dosen);
                startActivity(a);
            }
        });

        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(getApplicationContext(), OtpApprove.class);
                b.putExtra("id_jurnal",id_jurnal);
                b.putExtra("jurnal",jurnal);
                b.putExtra("dosen",dosen);
                startActivity(b);
            }
        });

        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(getApplicationContext(), QrApprove.class);
                c.putExtra("id_jurnal",id_jurnal);
                c.putExtra("jurnal",jurnal);
                c.putExtra("dosen",dosen);
                startActivity(c);
            }
        });
    }
}
