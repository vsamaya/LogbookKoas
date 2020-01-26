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

import cyd.awesome.material.AwesomeText;
import cyd.awesome.material.FontCharacterMaps;

public class PassApprove extends AppCompatActivity {
    private static final String KEY_USERNAME = "username";
    private static final String KEY_DOSEN = "dosen";
    private static final String KEY_PASSWORDMD5= "passwordmd5";
    private static final String KEY_ID= "id";
    private static final String KEY_JURNAL= "jurnal";
    private static final String KEY_STATUS= "status";
    AwesomeText imgShowhidepassword;
    boolean pwd_status = true;
    TextView dosen,gagal;
    EditText pass;
    Button approve;
    SessionHandler session;
    private String pass_aprv = "http://192.168.0.104/android/pass_aprv.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_approve);
        dosen = findViewById(R.id.dosen);
        gagal = findViewById(R.id.gagal);
        pass = findViewById(R.id.pass);
        approve = findViewById(R.id.approve);
        Intent intent = getIntent();
        final String dosen_lengkap = intent.getStringExtra("dosen_lengkap");
        dosen.setText(dosen_lengkap);
        imgShowhidepassword = findViewById(R.id.ImgShowPassword);
        imgShowhidepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pwd_status) {
                    pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    pwd_status = false;
                    imgShowhidepassword.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY);
                    pass.setSelection(pass.length());
                } else {
                    pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    pwd_status = true;
                    imgShowhidepassword.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY_OFF);
                    pass.setSelection(pass.length());
                }
            }
        });
    }


    public void pass_click(View view) {
        String password = pass.getText().toString();
        if (password.trim().length() > 0) {
            JSONObject request = new JSONObject();
            try {
                session = new SessionHandler(getApplicationContext());
                User user = session.getUserDetails();
                String username = user.getUsername();
                String passwordmd5 = md5(password);
                Intent intent = getIntent();
                final String id = intent.getStringExtra("id_jurnal");
                final String jurnal = intent.getStringExtra("jurnal");
                final String dosen = intent.getStringExtra("dosen");
                request.put(KEY_USERNAME, username);
                request.put(KEY_DOSEN, dosen);
                request.put(KEY_PASSWORDMD5, passwordmd5);
                request.put(KEY_ID, id);
                request.put(KEY_JURNAL, jurnal);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                    (Request.Method.POST, pass_aprv, request, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getInt(KEY_STATUS) == 1) {
                                    Toast.makeText(PassApprove.this, "Approvement Berhasil",
                                            Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(PassApprove.this,IsiJurnalDetail.class);
                                    finish();

                                } else {
                                    Toast.makeText(PassApprove.this, "Approvement Gagal",
                                            Toast.LENGTH_LONG).show();
                                    String textgagal = "Maaf Password yang Anda Masukkan Salah";
                                    gagal.setText(textgagal);



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
            Toast.makeText(PassApprove.this, "Maaf masih kosong",
                    Toast.LENGTH_LONG).show();
        }
    }


        public static final String md5 ( final String s){
            final String MD5 = "MD5";
            try {
                // Create MD5 Hash
                MessageDigest digest = MessageDigest
                        .getInstance(MD5);
                digest.update(s.getBytes());
                byte messageDigest[] = digest.digest();

                // Create Hex String
                StringBuilder hexString = new StringBuilder();
                for (byte aMessageDigest : messageDigest) {
                    String h = Integer.toHexString(0xFF & aMessageDigest);
                    while (h.length() < 2)
                        h = "0" + h;
                    hexString.append(h);
                }
                return hexString.toString();

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return "";
        }

}

