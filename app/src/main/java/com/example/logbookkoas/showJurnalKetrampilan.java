package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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

public class showJurnalKetrampilan extends AppCompatActivity {
    private static final String KEY_TANGGAL = "tanggal";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private SessionHandler session;
    ListView lv_ketrampilan;
    ArrayList<HashMap<String, String>> MyArr;
    final String url_ketrampilan = "http://192.168.43.44/logbook/ketrampilan.php";
    final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    final SimpleDateFormat convert = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_jurnal_ketrampilan);
        MyArr = new ArrayList<HashMap<String, String>>();
        lv_ketrampilan=findViewById(R.id.lv_ketrampilan);
        ketrampilan();
    }

    private void ketrampilan(){
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
                url_ketrampilan,request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray tmp = response.getJSONArray("tmp");
                    JSONArray kegiatan = response.getJSONArray("kegiatan");
                    JSONArray lokasi = response.getJSONArray("lokasi");
                    JSONArray kelas = response.getJSONArray("kelas");
                    JSONArray dosen = response.getJSONArray("dosen");
                    JSONArray ketrampilan1 = response.getJSONArray("ketrampilan1");
                    JSONArray ketrampilan2 = response.getJSONArray("ketrampilan2");
                    JSONArray ketrampilan3 = response.getJSONArray("ketrampilan3");
                    JSONArray ketrampilan4 = response.getJSONArray("ketrampilan4");
                    for (int i = 0; i < tmp.length(); i++) {
                        JSONObject j = tmp.getJSONObject(i);
                        JSONObject j_kegiatan = kegiatan.getJSONObject(i);
                        JSONObject j_lokasi = lokasi.getJSONObject(i);
                        JSONObject j_kelas = kelas.getJSONObject(i);
                        JSONObject j_dosen = dosen.getJSONObject(i);
                        JSONObject j_k1 = ketrampilan1.getJSONObject(i);
                        JSONObject j_k2 = ketrampilan2.getJSONObject(i);
                        JSONObject j_k3 = ketrampilan3.getJSONObject(i);
                        JSONObject j_k4 = ketrampilan4.getJSONObject(i);
                        HashMap<String, String> item = new HashMap<String, String>();
                        item.put("jam_awal", j.getString("jam_awal"));
                        item.put("jam_akhir", j.getString("jam_akhir"));
                        item.put("nama", j_dosen.getString("nama"));
                        if (j.getString("status").equals("1")) {
                            item.put("status", "Approved");
                        } else item.put("status", "Unapproved");
                        item.put("kegiatan", j_kegiatan.getString("kegiatan"));
                        item.put("lokasi", j_lokasi.getString("lokasi"));
                        item.put("kelas", j_kelas.getString("kelas"));
                        item.put("k1", j_k1.getString("ketrampilan"));
                        if (j_k2.getString("ketrampilan").equals("null")) {
                            item.put("k2", " ");
                        } else {
                            item.put("k2", j_k2.getString("ketrampilan"));
                        }
                        if (j_k3.getString("ketrampilan").equals("null")) {
                            item.put("k3", " ");
                        } else {
                            item.put("k3", j_k3.getString("ketrampilan"));
                        }
                        if (j_k4.getString("ketrampilan").equals("null")) {
                            item.put("k4", " ");
                        } else {
                            item.put("k4", j_k4.getString("ketrampilan"));
                        }
                        MyArr.add(item);

                    }
                    ListAdapter sAdap = new SimpleAdapter(showJurnalKetrampilan.this, MyArr, R.layout.item_row_cek,
                            new String[] {"jam_awal","jam_akhir","lokasi","kelas","kegiatan","nama","status","k1","k2","k3","k4"},
                            new int[] {R.id.tv_jam,R.id.tv_jam2,R.id.tv_lokasi,R.id.tv_kelas,R.id.tv_kegiatan,
                                    R.id.tv_dosen,R.id.tv_status,R.id.tv_sumber1,R.id.tv_sumber2,
                                    R.id.tv_sumber3,R.id.tv_sumber4,})
                    {
                        @Override
                        public View getView (int position, View convertView, ViewGroup parent)
                        {
                            View v = super.getView(position, convertView, parent);

                            final TextView sumber1=(TextView) v.findViewById(R.id.sumber1);
                            final TextView sumber2=(TextView) v.findViewById(R.id.sumber2);
                            final TextView sumber3=(TextView) v.findViewById(R.id.sumber3);
                            final TextView sumber4=(TextView) v.findViewById(R.id.sumber4);
                            sumber1.setText("Ketrampilan 1 : ");
                            sumber2.setText("Ketrampilan 2 : ");
                            sumber3.setText("Ketrampilan 3 : ");
                            sumber4.setText("Ketrampilan 4 : ");

                            return v;
                        }


                    };
                    lv_ketrampilan.setAdapter(sAdap);

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
