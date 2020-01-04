package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;

public class semester10 extends AppCompatActivity {

    final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
    private static final String KEY_USERNAME = "username";
    ListView listView;
    String url = "http://192.168.1.4/logbook1/sem10.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester10);
        listView = findViewById(R.id.listview10);
        getStase();

    }

   /* private void getData() {
        JSONObject request = new JSONObject();
        try {
            SessionHandler session= new SessionHandler(getApplicationContext());
            User user= session.getUserDetails();
            String username= user.getUsername();
            request.put(KEY_USERNAME,username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                url, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray kota = response.getJSONArray("m101");
                    JSONArray kota1 = response.getJSONArray("stase1");
                    for (int i = 0; i < kota.length(); i++) {
                        JSONObject j = kota.getJSONObject(i);
                        JSONObject c = kota1.getJSONObject(i);
                        HashMap<String,String> map;
                        map = new HashMap<String, String>();
                        map.put("stase", c.getString("stase"));
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
                        if(c.getString("stase").equals("null")&&j.getString("nim").equals("null")&&j.getString("tgl_mulai").equals("null")&&j.getString("tgl_selesai").equals("null")&&j.getString("status").equals("null")){
                            map.put("s1",(" "));
                            map.put("nim", (" "));
                            map.put("tgl_mulai",(" "));
                            map.put("tgl_selesai",(" "));
                            map.put("st",(" "));
                        }
                        MyArrList.add(map);

                    }
                    SimpleAdapter s4dap;
                    s4dap = new SimpleAdapter(semester10.this, MyArrList, R.layout.listview9, new String[]{"s1","nim", "tgl_mulai", "tgl_selesai", "st"}, new int[]{R.id.stase9,R.id.username9, R.id.tglmulai9, R.id.tglselesai9, R.id.status9});
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
                   for(int j=0;j<MyArrList.size()+3;j++){
                       for (int i = 0; i < MyArrList.size(); i++) {
                           HashMap<String, String> hashMap = MyArrList.get(i);

                           // for(String j : hashMap.values()){
                           if (Objects.equals(MyArrList.get(i).get("st"), "")) {
                               MyArrList.remove(i);


                           }
                       }}
                   int jumlah = MyArrList.size();
                   Collections.sort(MyArrList, new Comparator<HashMap<String, String>>()
                   {
                       @Override
                       public int compare(HashMap<String, String> a, HashMap<String, String> b)
                       {
                           if (a.get("mli") == null || b.get("mli") == null)
                               return 0;
                           return a.get("mli").compareTo(b.get("mli"));
                       }
                   });
                   String jumlaht = Integer.toString(jumlah);
                   Toast.makeText(semester10.this, jumlaht, Toast.LENGTH_SHORT).show();
                   SimpleAdapter s4dap;
                   s4dap = new SimpleAdapter(semester10.this, MyArrList, R.layout.listview9, new String[]{"s1", "n", "mli", "sls", "st"}, new int[]{R.id.stase9, R.id.username9, R.id.tglmulai9, R.id.tglselesai9, R.id.status9});
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
