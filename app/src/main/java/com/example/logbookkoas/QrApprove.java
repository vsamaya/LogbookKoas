package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class QrApprove extends AppCompatActivity {
    TextView qr_dosen;
    Button qr_approve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_approve);
        qr_dosen = findViewById(R.id.otp_dosen);
        qr_approve = findViewById(R.id.qr_approve);
        Intent intent = getIntent();
        final String id_jurnal = intent.getStringExtra("id_jurnal");
        final String jurnal = intent.getStringExtra("jurnal");
        final String dosen = intent.getStringExtra("dosen");
        qr_dosen.setText(dosen);
        qr_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera = new Intent(QrApprove.this,CamScanner.class);
                camera.putExtra("id_jurnal",id_jurnal);
                camera.putExtra("jurnal",jurnal);
                camera.putExtra("dosen",dosen);
                startActivity(camera);
            }
        });
    }


}