package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MahasiswaActivity extends AppCompatActivity {
ImageView iconmahasiswa;
    final ArrayList<HashMap<String, String>> list_data = new ArrayList<HashMap<String, String>>();
    private static final String KEY_USERNAME = "username";
    private String data_url = "http://192.168.69.122/getdataprofilms.php";
    TextView usernamems,nmlengkap,id;
    private String username;
    private String nama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);
        SessionHandler session= new SessionHandler(getApplicationContext());
        User user=session.getUserDetails();
        String username=user.getUsername();
        String fullname=user.getFullName();
        Toast.makeText(MahasiswaActivity.this, fullname, Toast.LENGTH_SHORT).show();
        Toast.makeText(MahasiswaActivity.this, username, Toast.LENGTH_SHORT).show();
        usernamems=findViewById(R.id.usernamemahasiswa);
        nmlengkap=findViewById(R.id.namamahasiswa);
        nama=nmlengkap.getText().toString();
        usernamems.setText(username);
        nmlengkap.setText(fullname);
        iconmahasiswa=findViewById(R.id.img_profile_mhs);
        iconmahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("data1", usernamems.getText().toString());
                bundle.putString("data2", nmlengkap.getText().toString());
                Intent iconms = new Intent(MahasiswaActivity.this, profil.class);
                iconms.putExtras(bundle);
                startActivity(iconms);
            }
        });

    }
 /*   public void getData(String user1){

        JSONObject request = new JSONObject();
        try {
            request.put(KEY_USERNAME, user1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                data_url, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray kota = response.getJSONArray("data");
                    for (int i = 0; i < kota.length(); i++) {
                        JSONObject j = kota.getJSONObject(i);
                        HashMap<String, String> map1  = new HashMap<String, String>();
                        map1 = new HashMap<String, String>();
                        map1.put("username",j.getString("username"));
                        map1.put("nama",j.getString("nama"));
                        list_data.add(map1);

                    }

                    usernamems.setText(list_data.get(0).get("username"));
                    nmlengkap.setText(list_data.get(0).get("nama"));




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
        MySingleton.getInstance(this).addToRequestQueue(json);}*/
}
