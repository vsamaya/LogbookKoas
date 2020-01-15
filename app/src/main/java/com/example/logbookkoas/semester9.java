package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.se.omapi.Session;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class semester9 extends AppCompatActivity {
    final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
    final ArrayList<HashMap<String, String>> MyArrList1 = new ArrayList<HashMap<String, String>>();
    private static final String KEY_USERNAME = "username";
    ListView listView;
    TextView textView;
    String url = "http://192.168.43.159/logbook/sem9.php";
    TextView nim, username, stase, status, mulai, selesai, stase1, nim1, mulai1, selesai1, status1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester9);
        listView = findViewById(R.id.listview9);
        SessionHandler session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        String username = user.getUsername();
        stase = findViewById(R.id.stase9);
        nim = findViewById(R.id.username9);
        mulai = findViewById(R.id.tglmulai9);
        selesai = findViewById(R.id.tglselesai9);
        status = findViewById(R.id.status9);
        stase1 = findViewById(R.id.stase1);
        nim1 = findViewById(R.id.nim1);
        mulai1 = findViewById(R.id.mulai1);
        selesai1 = findViewById(R.id.selesai1);
        status1 = findViewById(R.id.status1);

        //getData(username);
        getStase();

    }

    /* private void getData(String username) {
         JSONObject request = new JSONObject();
         try {
             request.put(KEY_USERNAME,username);
         } catch (JSONException e) {
             e.printStackTrace();
         }
         JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                 url, request, new Response.Listener<JSONObject>() {
             @Override
             public void onResponse(JSONObject response) {
                 try {
                     JSONArray kota = response.getJSONArray("tabel");
                     JSONArray kota1 = response.getJSONArray("kepaniteraan");
                     for (int i = 0; i < kota.length(); i++) {
                         JSONObject j = kota.getJSONObject(i);
                         JSONObject c = kota1.getJSONObject(i);
                         HashMap<String,String> map;
                         map = new HashMap<String, String>();
                         map.put("stase", c.getString("kepaniteraan"));
                         map.put("nim", j.getString("nim"));
                         map.put("tgl_mulai", j.getString("tgl_mulai"));
                         map.put("tgl_selesai", j.getString("tgl_selesai"));
                         map.put("status", j.getString("status"));
                         if(c.getString("stase").equals("M091")){
                             map.put("s1","Ilmu Penyakit Dalam");
                         }
                        else if(c.getString("stase").equals("M092")){
                             map.put("s1","Neurologi");
                         }
                         else if(c.getString("stase").equals("M093")){
                             map.put("s1","Ilmu Kehatan Jiwa");
                         }
                         else if(c.getString("stase").equals("M094")){
                             map.put("s1","Ilmu Kesehatan THT-KL");
                         }
                         else if(c.getString("stase").equals("M095")){
                             map.put("s1","IKM-KP");
                         }
                         else if(c.getString("stase").equals("M101")){
                             map.put("s1","Ilmu Bedah");
                         }
                         else if(c.getString("stase").equals("M102")){
                             map.put("s1","Anestesi dan Intensive Care");
                         }
                         else if(c.getString("stase").equals("M103")){
                             map.put("s1","Ilmu Sinar");
                         }
                         else if(c.getString("stase").equals("M104")){
                             map.put("s1","Ilmu Kesehatan Mata");
                         }
                         else if(c.getString("stase").equals("M105")){
                             map.put("s1","IKFR");
                         }
                         else if(c.getString("stase").equals("M106")){
                             map.put("s1","IKGM");
                         }
                         else if(c.getString("stase").equals("M111")){
                             map.put("s1","Ilmu Kebidanan dan Penyakit Kandungan");
                         }
                         else if(c.getString("stase").equals("M112")){
                             map.put("s1","Kedokteran Forensik dan Medikolegal");
                         }
                         else if(c.getString("stase").equals("M113")){
                             map.put("s1","Ilmu Kesehatan Anak");
                         }
                         else if(c.getString("stase").equals("M114")){
                             map.put("s1","Ilmu Kesehatan Kulit dan Kelamin");
                         }
                         if(j.getString("status").equals("0")){
                             map.put("st","Belum Masuk");
                         }
                         else if(j.getString("status").equals("1")){
                             map.put("st","Aktif");
                         }
                         else if(j.getString("status").equals("2")){
                             map.put("st","Sudah Lewat");
                         }

                         MyArrList.add(map);

                     }

                     SimpleAdapter s4dap;
                     s4dap = new SimpleAdapter(semester9.this, MyArrList, R.layout.listview9, new String[]{"stase","nim", "tgl_mulai", "tgl_selesai", "st"}, new int[]{R.id.stase9,R.id.username9, R.id.tglmulai9, R.id.tglselesai9, R.id.status9});
                     listView.setAdapter(s4dap);
                 } catch (JSONException e) {
                     e.printStackTrace();

                 }
             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Toast.makeText(getApplicationContext(), "<< DIJADWAL ULANG ATAU BELUM DIJADWALKAN >>", Toast.LENGTH_SHORT).show();
             }
         });
         MySingleton.getInstance(this).addToRequestQueue(json);
     }*/
    private void getStase() {
        JSONObject request = new JSONObject();
        try {
            SessionHandler session = new SessionHandler(getApplicationContext());
            User user = session.getUserDetails();
            String username = user.getUsername();
            request.put(KEY_USERNAME, username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                url, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray kota = response.getJSONArray("kepaniteraan");
                    JSONArray kota1 = response.getJSONArray("mulai");
                    JSONArray kota2 = response.getJSONArray("selesai");
                    JSONArray kota3 = response.getJSONArray("nim");
                    JSONArray kota4 = response.getJSONArray("status");
                    for (int i = 0; i < kota1.length(); i++) {
                        JSONObject j = kota.getJSONObject(i);
                        JSONObject c = kota1.getJSONObject(i);
                        JSONObject d = kota2.getJSONObject(i);
                        JSONObject e = kota3.getJSONObject(i);
                        JSONObject f = kota4.getJSONObject(i);
                        HashMap<String, String> map;
                        map = new HashMap<String, String>();
                        map.put("stase", j.getString("kepaniteraan"));
                        map.put("nim", e.getString("nim"));
                        map.put("status", f.getString("status"));
                        map.put("mulai", c.getString("tgl_mulai"));
                        map.put("selesai", d.getString("tgl_selesai"));
                        if (e.getString("nim").equals("null")) {
                            map.put("n", "");
                            map.put("s1", "");
                            map.put("mli", "");
                            map.put("st", "");
                            map.put("sls", "");

                        } else {
                            map.put("s1", j.getString("kepaniteraan"));
                            map.put("n", e.getString("nim"));
                            map.put("st", f.getString("status"));
                            map.put("mli", c.getString("tgl_mulai"));
                            map.put("sls", d.getString("tgl_selesai"));

                        }
                        MyArrList.add(map);
                    }
                    for (int i = 0; i < MyArrList.size(); i++) {
                        HashMap<String, String> hashMap = MyArrList.get(i);

                        // for(String j : hashMap.values()){
                        if (Objects.equals(MyArrList.get(i).get("st"), "")) {
                            MyArrList.remove(i);


                        }
                    }
                    int jumlah = MyArrList.size();
                    String jumlaht = Integer.toString(jumlah);
                    MyArrList.remove(jumlah - 1);
                    Toast.makeText(semester9.this, jumlaht, Toast.LENGTH_SHORT).show();
                    SimpleAdapter s4dap;
                    s4dap = new SimpleAdapter(semester9.this, MyArrList, R.layout.listview9, new String[]{"s1", "n", "mli", "sls", "st"}, new int[]{R.id.stase9, R.id.username9, R.id.tglmulai9, R.id.tglselesai9, R.id.status9});
                    listView.setAdapter(s4dap);

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