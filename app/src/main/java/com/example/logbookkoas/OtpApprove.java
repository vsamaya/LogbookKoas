package com.example.logbookkoas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class OtpApprove extends AppCompatActivity {
    private static final String KEY_USERNAME = "username";
    private static final String KEY_DOSEN = "dosen";
    private static final String KEY_OTP= "otp";
    private static final String KEY_ID= "id";
    private static final String KEY_JURNAL= "jurnal";
    private static final String KEY_STATUS= "status";
    TextView otp_dosen,otp_gagal;
    EditText pin;
    Button otp_approve;
    SessionHandler session;
    private String otp_aprv = "http://192.168.1.7/logbook/otp_aprv.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_approve);
        otp_dosen = findViewById(R.id.otp_dosen);
        otp_gagal = findViewById(R.id.otp_gagal);
        pin = findViewById(R.id.pin);
        otp_approve = findViewById(R.id.otp_approve);
        Intent intent = getIntent();
        final String dosen_lengkap = intent.getStringExtra("dosen_lengkap");
        otp_dosen.setText(dosen_lengkap);
    }


    public void otp_click(View view) {
        String otp = pin.getText().toString();
        if (otp.length() > 0) {
            JSONObject request = new JSONObject();
            try {
                session = new SessionHandler(getApplicationContext());
                User user = session.getUserDetails();
                String username = user.getUsername();
                Intent intent = getIntent();
                final String id = intent.getStringExtra("id_jurnal");
                final String jurnal = intent.getStringExtra("jurnal");
                final String dosen = intent.getStringExtra("dosen");
                request.put(KEY_USERNAME, username);
                request.put(KEY_DOSEN, dosen);
                request.put(KEY_OTP, otp);
                request.put(KEY_ID, id);
                request.put(KEY_JURNAL, jurnal);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                    (Request.Method.POST, otp_aprv, request, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getInt(KEY_STATUS) == 1) {
                                    Toast.makeText(OtpApprove.this, "Approvement Berhasil",
                                            Toast.LENGTH_LONG).show();
                                    finish();

                                } else {
                                    Toast.makeText(OtpApprove.this, "Approvement Gagal",
                                            Toast.LENGTH_LONG).show();
                                    String textgagal = "Maaf OTP Tidak Berlaku";
                                    otp_gagal.setText(textgagal);



                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {


                        }
                    });
            // Access the RequestQueue through your singleton class.
            MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
        }
        else{
            Toast.makeText(OtpApprove.this, "Maaf masih kosong",
                    Toast.LENGTH_LONG).show();
        }
    }
}


