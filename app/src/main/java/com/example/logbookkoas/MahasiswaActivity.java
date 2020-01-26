package com.example.logbookkoas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MahasiswaActivity extends AppCompatActivity {
    ImageView iconmahasiswa;
    final ArrayList<HashMap<String, String>> list_data = new ArrayList<HashMap<String, String>>();
    private static final String KEY_USERNAME = "username";
    private String data_url = "http://192.168.0.104/android/getdataprofilms.php";
    private String foto_url = "http://192.168.0.104/android/getdatafoto.php";
    private String foto_image = "http://192.168.0.104/koas/foto/";
    TextView usernamems,nmlengkap,id;
    RelativeLayout rotasiinternal;
    RelativeLayout cekjurnal;
    RelativeLayout rekapjurnal;
    RelativeLayout isijurnal;
    private String username;
    private String nama;
    ImageView foto,about;
    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_mahasiswa);
        SessionHandler session= new SessionHandler(getApplicationContext());
        User user=session.getUserDetails();
        final String username=user.getUsername();
        final String password = user.getPassword();
        String fullname=user.getFullName();
        final String pass=user.getPassword();
        // Toast.makeText(MahasiswaActivity.this,pass, Toast.LENGTH_SHORT).show();
        // Toast.makeText(MahasiswaActivity.this, fullname, Toast.LENGTH_SHORT).show();
        //Toast.makeText(MahasiswaActivity.this, username, Toast.LENGTH_SHORT).show();
        usernamems=findViewById(R.id.usernamemahasiswa);
        nmlengkap=findViewById(R.id.namamahasiswa);
        rotasiinternal=findViewById(R.id.btn_rotasiinternal);
        isijurnal=findViewById(R.id.btn_isijurnal);
        cekjurnal=findViewById(R.id.btn_cekjurnal);
        rekapjurnal=findViewById(R.id.btn_rekapjurnal);
        about=findViewById(R.id.img_about);
        nama=nmlengkap.getText().toString();
        foto=findViewById(R.id.imgmhs);
        usernamems.setText(username);
        getData1(usernamems.getText().toString());
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
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MahasiswaActivity.this,about.class);
                startActivity(i);
            }
        });
        rotasiinternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ri= new Intent(MahasiswaActivity.this, Rotasi.class);
                startActivity(ri);
            }
        });
        cekjurnal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ri= new Intent(MahasiswaActivity.this, CekJurnal.class);
                startActivity(ri);
            }
        });
        isijurnal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ri= new Intent(MahasiswaActivity.this, mainIsiJurnal.class);
                startActivity(ri);
            }
        });
        rekapjurnal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://logbook.fk.undip.ac.id/koas/bridge_rekap_individu.php?usr="+username+"&pwd="+password));
                String title = "Pilih browser untuk membuka  tautan";
                Intent chooser = Intent.createChooser(intent, title);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
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
                foto_url, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray kota = response.getJSONArray("data1");
                    for (int i = 0; i < kota.length(); i++) {
                        JSONObject j = kota.getJSONObject(i);
                        HashMap<String, String> map1 = new HashMap<String, String>();
                        map1 = new HashMap<String, String>();
                        map1.put("foto", j.getString("foto"));
                        map1.put("nama", j.getString("nama"));
                        list_data.add(map1);

                    }
                    nmlengkap.setText(list_data.get(0).get("nama"));
                    Glide.with(getApplicationContext())
                            .load(foto_image + list_data.get(0).get("foto"))
                            .placeholder(R.drawable.ic_account)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)//.placeholder(R.drawable.ic_account)
                            .skipMemoryCache(true)
                            .into(foto);


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
