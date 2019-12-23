package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class DosenActivity extends AppCompatActivity {
Button btn_generatecode,btn_generateqr,btn_dftrkeg,btn_rekapkeg;
    private static final String KEY_USERNAME = "username";
    private static final String KEY_LEVEL = "level";
ImageView profile;
TextView username,namaDosen;
    private SessionHandler session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosen);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        btn_generatecode=findViewById(R.id.btn_generatecode);
        btn_generateqr=findViewById(R.id.btn_generateqr);
        btn_dftrkeg=findViewById(R.id.btn_dftrkeg);
        btn_rekapkeg=findViewById(R.id.btn_rekapkeg);
        profile = findViewById(R.id.img_profile_dsn);
        String nama = user.getNama();
        String nip = user.getUsername();
        namaDosen = findViewById(R.id.namadosen);
        namaDosen.setText(nama);
        username = findViewById(R.id.usernamedosen);
        username.setText(nip);





        btn_generatecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(DosenActivity.this, MainOTP.class);
                startActivity(a);
            }
        });

        btn_generateqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(DosenActivity.this, MainQR.class);
                startActivity(b);
            }
        });

        btn_dftrkeg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent c = new Intent(DosenActivity.this, DaftarKegiatan.class);
                startActivity(c);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser();
                Intent i = new Intent(DosenActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

//    private void setNama(String lv, String nip){
//        JSONObject request = new JSONObject();
//        try {
//            //Populate the request parameters
//            request.put(KEY_USERNAME, nip);
//            request.put(KEY_LEVEL, lv);
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
//                (Request.Method.POST, updateStatusSemua, request, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
////                            ArrayList<HashMap<String,String>> rv_dafkeg1 = new ArrayList<HashMap<String, String>>();
////                            rv_dafkeg1 = rv_dafkeg;
////                            rv_dafkeg.removeAll(rv_dafkeg1);
////                            getArray();
//                            getArray();
//
//                            if (response.getInt(KEY_STATUS) == 0) {
//
//                            }
//                            else{
//                                Toast.makeText(getApplicationContext(),
//                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        //Display error message whenever an error occurs
//                        Toast.makeText(getApplicationContext(),
//                                error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//
//        // Access the RequestQueue through your singleton class.
//        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
//    }

}
