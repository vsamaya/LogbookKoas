package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ResidenActivity extends AppCompatActivity {
    Button btn_generatecode,btn_generateqr,btn_dftrkeg;
    TextView nama_user,name;
    private static final String KEY_USERNAME="username";
    private String datads = "http://192.168.0.104/android/datads.php";
    ArrayList<HashMap<String,String>> list_data= new ArrayList<HashMap<String, String>>();
    ImageView iconprofil,about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residen);
        SessionHandler session= new SessionHandler(getApplicationContext());
        User user=session.getUserDetails();
        nama_user=findViewById(R.id.usernamedosen);
        name=findViewById(R.id.namadosen);
        String username=user.getUsername();
        String nama=user.getFullName();
        String pass=user.getPassword();
        //   Toast.makeText(DosenActivity.this,pass, Toast.LENGTH_SHORT).show();
        nama_user.setText(username);
        getData1(nama_user.getText().toString());

        if(username == null){
            Intent i= new Intent(ResidenActivity.this,MainActivity.class);
            startActivity(i);
        }
        btn_generatecode=findViewById(R.id.btn_generatecode);
        btn_generateqr=findViewById(R.id.btn_generateqr);
        btn_dftrkeg=findViewById(R.id.btn_dftrkeg);
        about=findViewById(R.id.img_about);
        iconprofil=findViewById(R.id.img_profile_dsn);
        iconprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundleds = new Bundle();
                bundleds.putString("datads", nama_user.getText().toString());
                bundleds.putString("datads1", name.getText().toString());
                Intent icon=new Intent(ResidenActivity.this,profilrs.class);
                icon.putExtras(bundleds);
                startActivity(icon);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ResidenActivity.this,about.class);
                startActivity(i);
            }
        });
        btn_generatecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(ResidenActivity.this, MainOTP.class);
                startActivity(a);
            }
        });

        btn_generateqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(ResidenActivity.this, MainQR.class);
                startActivity(b);
            }
        });

        btn_dftrkeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(ResidenActivity.this, DaftarKegiatan.class);
                startActivity(c);
            }
        });

    }
    public void getData1(String username) {

        JSONObject request = new JSONObject();
        try {
            request.put(KEY_USERNAME, username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                datads, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray kota = response.getJSONArray("datads");
                    for (int i = 0; i < kota.length(); i++) {
                        JSONObject j = kota.getJSONObject(i);
                        HashMap<String, String> map1 = new HashMap<String, String>();
                        map1 = new HashMap<String, String>();
                        map1.put("nama", j.getString("nama"));
                        list_data.add(map1);

                    }
                    name.setText(list_data.get(0).get("nama"));

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(json);
    }
}
