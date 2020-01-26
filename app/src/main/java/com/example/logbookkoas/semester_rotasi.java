package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class semester_rotasi extends AppCompatActivity {
    final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
    final ArrayList<HashMap<String, String>> MyArrList1 = new ArrayList<HashMap<String, String>>();
    private static final String KEY_USERNAME = "username";
    private static final String TAG_ROTASI = "rotasi";
    ListView listView;
    TextView title,empty;
    String main = "http://192.168.0.104/android";
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat convert = new SimpleDateFormat("dd MMM yyyy");
    TextView nim, username, stase, status, mulai, selesai, stase1, nim1, mulai1, selesai1, status1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester_rotasi);
        listView = findViewById(R.id.listview9);
        title = findViewById(R.id.titlr);
        empty = findViewById(R.id.empty);
        Intent intent = getIntent();
        final String semester = intent.getStringExtra("semester");
        String judul = "Semester "+semester;
        title.setText(judul);
        getStase();

    }

    private void getStase() {
        Intent intent = getIntent();
        final String semester = intent.getStringExtra("semester");
        String url = main+"/semester"+semester+".php";
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
                    JSONArray tmp = response.getJSONArray("tmp");
                    JSONArray mulai = response.getJSONArray("mulai");
                    JSONArray selesai = response.getJSONArray("selesai");
                    JSONArray rotasi = response.getJSONArray("rotasi");
                    for (int i = 0; i < tmp.length(); i++) {
                        JSONObject c = tmp.getJSONObject(i);
                        JSONObject cmulai = mulai.getJSONObject(i);
                        JSONObject cselesai = selesai.getJSONObject(i);
                        JSONObject crotasi = rotasi.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("id", c.getString("id"));
                        map.put("kepaniteraan", c.getString("kepaniteraan"));
                        if(crotasi.getString("rotasi").equals("null")) {
                            map.put(TAG_ROTASI, "0");
                        } else {
                            map.put(TAG_ROTASI, crotasi.getString("rotasi"));
                        }
                        if(cmulai.getString("tgl_mulai").equals("null") || cselesai.getString("tgl_selesai").equals("null")){
                            map.put("tgl_mulai"," ");
                            map.put("tgl_selesai"," ");
                        } else {
                            Date tglMulai = format.parse(cmulai.getString("tgl_mulai"));
                            Date tglSelesai = format.parse(cselesai.getString("tgl_selesai"));
                            String tglMulaiText = convert.format(tglMulai);
                            String tglSelesaiText = convert.format(tglSelesai);
                            map.put("tgl_mulai", cmulai.getString("tgl_mulai"));
                            map.put("tgl_selesai", cselesai.getString("tgl_selesai"));
                            map.put("tgl_mulai_show", tglMulaiText);
                            map.put("tgl_selesai_show", tglSelesaiText);
                            Date tglSelesai1 = new Date(tglSelesai.getTime() + (1000 * 60 * 60 * 24));
                            Date now = Calendar.getInstance().getTime();
                            if(now.after(tglMulai) && now.before(tglSelesai1)){
                                map.put("status","Aktif");
                            }
                            else if(now.before(tglMulai)){
                                map.put("status","Belum Aktif");
                            }
                            else{
                                map.put("status", "Sudah Terlewat");
                            }
                        }
                        MyArrList.add(map);

                    }

                        for (int j = 0; j < MyArrList.size() + 3; j++) {
                            for (int i = 0; i < MyArrList.size(); i++) {
                                HashMap<String, String> hashMap = MyArrList.get(i);

                                // for(String j : hashMap.values()){
                                if (Objects.equals(MyArrList.get(i).get("tgl_mulai"), " ")) {
                                    MyArrList.remove(i);
                                }
                            }
                        }
                        Collections.sort(MyArrList, new Comparator<HashMap<String, String>>() {
                            @Override
                            public int compare(HashMap<String, String> a, HashMap<String, String> b) {
                                if (a.get("tgl_mulai") == null || b.get("tgl_mulai") == null)
                                    return 0;
                                return a.get("tgl_mulai").compareTo(b.get("tgl_mulai"));
                            }
                        });

                    if(MyArrList.isEmpty()){
                        empty.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.GONE);
                    } else {
                        SimpleAdapter s4dap;
                        s4dap = new SimpleAdapter(semester_rotasi.this, MyArrList, R.layout.item_row_jurnal,
                                new String[]{"kepaniteraan", "tgl_mulai_show", "tgl_selesai_show", "status"}, new int[]
                                {R.id.tv_judul, R.id.tv_tgl_mulai, R.id.tv_tgl_selesai, R.id.tv_status});
                        listView.setAdapter(s4dap);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                } catch (ParseException e) {
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