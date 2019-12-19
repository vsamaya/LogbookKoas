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

    }


    public void qr_click(View view) {
        Intent camera = new Intent(QrApprove.this,CamScanner.class);
        startActivity(camera);
    }

}