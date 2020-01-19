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

public class ri_OtpApprove extends AppCompatActivity {
    private static final String KEY_USERNAME = "username";
    private static final String KEY_DOSEN = "dosen";
    private static final String KEY_OTP= "otp";
    private static final String KEY_ID= "id";
    private static final String KEY_ID_INTERNAL= "id_internal";
    private static final String KEY_STATUS= "status";
    private static final String KEY_ROTASI= "rotasi";
    TextView otp_gagal;
    EditText pin;
    Button otp_approve;
    SessionHandler session;
    private String otp_aprv = "http://192.168.0.109/logbook/ri_otp_aprv.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ri_otp_approve);
        otp_gagal = findViewById(R.id.otp_gagal);
        pin = findViewById(R.id.pin);
        otp_approve = findViewById(R.id.otp_approve);
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
                final String id_internal = intent.getStringExtra("id_internal");
                final String id = intent.getStringExtra("id");
                final String rotasi = intent.getStringExtra("rotasi");
                final String dosen = intent.getStringExtra("dosen");
                request.put(KEY_USERNAME, username);
                request.put(KEY_DOSEN, dosen);
                request.put(KEY_OTP, otp);
                request.put(KEY_ID, id);
                request.put(KEY_ID_INTERNAL, id_internal);
                request.put(KEY_ROTASI, rotasi);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                    (Request.Method.POST, otp_aprv, request, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getInt(KEY_STATUS) == 1) {
                                    Toast.makeText(ri_OtpApprove.this, "Approvement Berhasil",
                                            Toast.LENGTH_LONG).show();
                                    finish();

                                } else {
                                    Toast.makeText(ri_OtpApprove.this, "Approvement Gagal",
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
            Toast.makeText(ri_OtpApprove.this, "Maaf masih kosong",
                    Toast.LENGTH_LONG).show();
        }
    }
}

