package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class showJurnalPenyakit extends AppCompatActivity {
    private static final String KEY_TANGGAL = "tanggal";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private SessionHandler session;
    ListView lv_penyakit;
    ArrayList<HashMap<String, String>> MyArr;
    final String url_penyakit = "http://192.168.43.44/logbook/penyakit.php";
    final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    final SimpleDateFormat convert = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_jurnal_penyakit);
        MyArr = new ArrayList<HashMap<String, String>>();
        lv_penyakit = findViewById(R.id.lv_penyakit);
        penyakit();
    }

    private void penyakit(){
        JSONObject request = new JSONObject();
        try {
            Intent intent = getIntent();
            final String id = intent.getStringExtra("id");
            final String tgl = intent.getStringExtra("tgl");
            session = new SessionHandler(getApplicationContext());
            User user = session.getUserDetails();
            String username = user.getUsername();
            Date date = null;
            try {
                date = format.parse(tgl);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            final String tanggal = convert.format(date);
            request.put(KEY_USERNAME, username);
            request.put(KEY_ID, id);
            request.put(KEY_TANGGAL, tanggal);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                url_penyakit,request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray tmp = response.getJSONArray("tmp");
                    JSONArray kegiatan = response.getJSONArray("kegiatan");
                    JSONArray lokasi = response.getJSONArray("lokasi");
                    JSONArray dosen = response.getJSONArray("dosen");
                    JSONArray penyakit1 = response.getJSONArray("penyakit1");
                    JSONArray penyakit2 = response.getJSONArray("penyakit2");
                    JSONArray penyakit3 = response.getJSONArray("penyakit3");
                    JSONArray penyakit4 = response.getJSONArray("penyakit4");
                    for (int i = 0; i < tmp.length(); i++) {
                        JSONObject j = tmp.getJSONObject(i);
                        JSONObject j_kegiatan = kegiatan.getJSONObject(i);
                        JSONObject j_lokasi = lokasi.getJSONObject(i);
                        JSONObject j_dosen = dosen.getJSONObject(i);
                        JSONObject j_p1 = penyakit1.getJSONObject(i);
                        JSONObject j_p2 = penyakit2.getJSONObject(i);
                        JSONObject j_p3 = penyakit3.getJSONObject(i);
                        JSONObject j_p4 = penyakit4.getJSONObject(i);
                        HashMap<String, String> item = new HashMap<String, String>();
                        item.put("jam_awal", j.getString("jam_awal"));
                        item.put("jam_akhir", j.getString("jam_akhir"));
                        item.put("nama", j_dosen.getString("nama"));
                        if (j.getString("status").equals("1")) {
                            item.put("status", "Approved");
                        } else item.put("status", "Unapproved");
                        item.put("kegiatan", j_kegiatan.getString("kegiatan"));
                        item.put("lokasi", j_lokasi.getString("lokasi"));
                        item.put("p1", j_p1.getString("penyakit"));
                        if (j_p2.getString("penyakit").equals("null")) {
                            item.put("p2", " ");
                        } else {
                            item.put("p2", j_p2.getString("penyakit"));
                        }
                        if (j_p3.getString("penyakit").equals("null")) {
                            item.put("p3", " ");
                        } else {
                            item.put("p3", j_p3.getString("penyakit"));
                        }
                        if (j_p4.getString("penyakit").equals("null")) {
                            item.put("p4", " ");
                        } else {
                            item.put("p4", j_p4.getString("penyakit"));
                        }
                        MyArr.add(item);

                    }
                    ListAdapter sAdap = new SimpleAdapter(showJurnalPenyakit.this, MyArr, R.layout.item_row_cek,
                            new String[] {"jam_awal","jam_akhir","lokasi","kegiatan","nama","status","p1","p2","p3","p4"},
                            new int[] {R.id.tv_jam,R.id.tv_jam2,R.id.tv_lokasi,R.id.tv_kegiatan,
                                    R.id.tv_dosen,R.id.tv_status,R.id.tv_sumber1,R.id.tv_sumber2,
                                    R.id.tv_sumber3,R.id.tv_sumber4,});
                    lv_penyakit.setAdapter(sAdap);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(json);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}
