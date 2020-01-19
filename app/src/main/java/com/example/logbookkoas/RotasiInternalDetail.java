package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class RotasiInternalDetail extends AppCompatActivity {
    private SessionHandler session;
    ListView lv_rot;
    private String header = "http://192.168.0.109/logbook/getKepaniteraan.php";
    final String urlsem = "http://192.168.0.109/logbook/rotasi_internal.php";
    private String judul = "http://192.168.0.109/logbook/getJadwal.php";
    private String update_status = "http://192.168.0.109/logbook/updateStatus.php";
    public static final String KEY_ID = "id";
    TextView stase,tanggal,id_stase;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat convert = new SimpleDateFormat("dd MMM yyyy");
    ArrayList<HashMap<String, String>> MyArr= new ArrayList<HashMap<String,String>>();

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_rotasi_internal_detail);
        lv_rot = findViewById(R.id.lv_rotasi);
        stase = findViewById(R.id.stase);
        tanggal = findViewById(R.id.tanggal);
        id_stase = findViewById(R.id.tanggal_mulai);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        judul();
        process();
        staserotasi();
    }

    private void judul() {
        Intent intent = getIntent();
        final String id = intent.getStringExtra(RotasiInternal.KEY_ID);
        String url_judul = header+"?id="+id;

        try {

            JSONArray data = new JSONArray(getJSONUrl(url_judul));

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;
            JSONObject c = data.getJSONObject(0);
            map = new HashMap<String, String>();
            map.put("kepaniteraan", c.getString("kepaniteraan"));
            MyArrList.add(map);
            String namaStrase = c.getString("kepaniteraan");
            stase.setText(namaStrase);


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void process(){
        Intent intent = getIntent();
        final String id = intent.getStringExtra(mainIsiJurnal.KEY_ID);
        final String idStase = "stase_"+id;
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        String username = user.getUsername();
        String url_judul = judul+"?username="+username+"&stase="+idStase;

        try {

            JSONArray data = new JSONArray(getJSONUrl(url_judul));

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;
            JSONObject c = data.getJSONObject(0);
            map = new HashMap<String, String>();
            map.put("tgl_mulai", c.getString("tgl_mulai"));
            map.put("tgl_selesai", c.getString("tgl_selesai"));
            MyArrList.add(map);
            String mulai = c.getString("tgl_mulai");
            String selesai = c.getString("tgl_selesai");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat convert = new SimpleDateFormat("dd MMM yyyy");
            Date tglMulai = format.parse(mulai);
            String tglMulaiText = convert.format(tglMulai);
            Date tglSelesai = format.parse(selesai);
            Date tglSelesai1 = new Date(tglSelesai.getTime() + (1000 * 60 * 60 * 24));;
            String tglSelesaiText = convert.format(tglSelesai);
            String jadwal = tglMulaiText + " - " + tglSelesaiText;
            tanggal.setText(jadwal);
            id_stase.setText(idStase);


            Date now = Calendar.getInstance().getTime();
            if(now.after(tglMulai) && now.before(tglSelesai1)) {
                String status = "1";
                String url_status = update_status+"?username="+username+"&stase="+idStase+"&status="+status;
                try {
                    new JSONArray(getJSONUrl(url_status));

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            else if(now.before(tglMulai)){
                String status = "0";
                lv_rot.setVisibility(LinearLayout.GONE);
                String url_status = update_status+"?username="+username+"&stase="+idStase+"&status="+status;
                try {
                    new JSONArray(getJSONUrl(url_status));

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else{
                String status = "2";
                lv_rot.setVisibility(LinearLayout.GONE);
                String url_status = update_status+"?username="+username+"&stase="+idStase+"&status="+status;
                try {
                    new JSONArray(getJSONUrl(url_status));

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void staserotasi() {
        Intent i = getIntent();
        final String id = i.getStringExtra(RotasiInternal.KEY_ID);
        JSONObject request = new JSONObject();
        try {

            SessionHandler session;
            session = new SessionHandler(getApplicationContext());
            User user = session.getUserDetails();
            String username = user.getUsername();
            request.put("username", username);
            request.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json;
        json = new JsonObjectRequest(Request.Method.POST,
                urlsem, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    MyArr.clear();
                    JSONArray tmp = response.getJSONArray("tmp");
                    JSONArray total = response.getJSONArray("total");
                    JSONArray array1 = response.getJSONArray("array1");
                    JSONArray array2 = response.getJSONArray("array2");
                    JSONArray array3 = response.getJSONArray("array3");
                    JSONArray array4 = response.getJSONArray("array4");
                    JSONArray array5 = response.getJSONArray("array5");
                    JSONArray array6 = response.getJSONArray("array6");
                    for (int i = 0; i < tmp.length(); i++) {
                        JSONObject c = tmp.getJSONObject(i);
                        JSONObject d = total.getJSONObject(i);
                        JSONObject e = array1.getJSONObject(i);
                        JSONObject f = array2.getJSONObject(i);
                        JSONObject g = array3.getJSONObject(i);
                        JSONObject h = array4.getJSONObject(i);
                        JSONObject j = array5.getJSONObject(i);
                        JSONObject k = array6.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("id_rotasi",c.getString("id"));
                        map.put("stase", c.getString("internal"));
                        map.put("hari", c.getString("hari"));
                        map.put("id_internal", d.getString("id"));
                        if (d.getString("id").equals("null")) {
                            map.put("tglmli", "-");
                            map.put("tglsls", "-");
                            map.put("status", "-");
                        } else {
                            String tgl="0000-00-00";
                            Date tglml=format.parse(tgl);
                            Date tglMulai = tglml;
                            Date tglselesai = tglml;
                            String a = "";
                            String b = "";
                            if (i == 0) {
                                tglMulai = format.parse(e.getString("tgl1"));
                                a = e.getString("tgl1");
                                b = e.getString("dosen1");
                                map.put("dosen",b);
                                map.put("tglmli", a);
                                String wktu = c.getString("hari");
                                int waktu = Integer.parseInt(wktu);
                                int sum = 1000 * 60 * 60 * 24 * waktu;
                                int minus = 1000 * 60 * 60 * 24;
                                if (tglMulai != null) {
                                    tglselesai = new Date(tglMulai.getTime() + sum - minus);
                                }
                            } else if (i == 1) {
                                a = f.getString("tgl2");
                                b = f.getString("dosen2");
                                map.put("dosen",b);
                                tglMulai = format.parse(f.getString("tgl2"));
                                map.put("tglmli", a);
                                String wktu = c.getString("hari");
                                int waktu = Integer.parseInt(wktu);
                                int sum = 1000 * 60 * 60 * 24 * waktu;
                                int minus = 1000 * 60 * 60 * 24;
                                if (tglMulai != null) {
                                    tglselesai = new Date(tglMulai.getTime() + sum - minus);
                                }
                            } else if (i == 2) {
                                tglMulai = format.parse(g.getString("tgl3"));
                                a = g.getString("tgl3");
                                b = g.getString("dosen3");
                                map.put("dosen",b);
                                map.put("tglmli", a);
                                String wktu = c.getString("hari");
                                int waktu = Integer.parseInt(wktu);
                                int sum = 1000 * 60 * 60 * 24 * waktu;
                                int minus = 1000 * 60 * 60 * 24;
                                if (tglMulai != null) {
                                    tglselesai = new Date(tglMulai.getTime() + sum - minus);
                                }
                            } else if (i == 3) {
                                tglMulai = format.parse(h.getString("tgl4"));
                                a = h.getString("tgl4");
                                b = h.getString("dosen4");
                                map.put("dosen",b);
                                map.put("tglmli", a);
                                String wktu = c.getString("hari");
                                int waktu = Integer.parseInt(wktu);
                                int sum = 1000 * 60 * 60 * 24 * waktu;
                                int minus = 1000 * 60 * 60 * 24;
                                if (tglMulai != null) {
                                    tglselesai = new Date(tglMulai.getTime() + sum - minus);
                                }
                            } else if (i == 4) {
                                tglMulai = format.parse(j.getString("tgl5"));
                                a = j.getString("tgl5");
                                b = j.getString("dosen5");
                                map.put("dosen",b);
                                map.put("tglmli", a);

                                String wktu = c.getString("hari");
                                int waktu = Integer.parseInt(wktu);
                                int sum = 1000 * 60 * 60 * 24 * waktu;
                                int minus = 1000 * 60 * 60 * 24;
                                if (tglMulai != null) {
                                    tglselesai = new Date(tglMulai.getTime() + sum - minus);
                                }
                            } else if (i == 5) {
                                tglMulai = format.parse(h.getString("tgl6"));
                                b = k.getString("dosen6");
                                map.put("dosen",b);
                                a = k.getString("tgl6");
                                map.put("tglmli", a);

                                String wktu = c.getString("hari");
                                int waktu = Integer.parseInt(wktu);
                                int sum = 1000 * 60 * 60 * 24 * waktu;
                                int minus = 1000 * 60 * 60 * 24;
                                if (tglMulai != null) {
                                    tglselesai = new Date(tglMulai.getTime() + sum - minus);
                                }
                            }

                            String tglSelesaiText = "";
                            if (tglselesai != null) {
                                tglSelesaiText = convert.format(tglselesai);
                            }
                            String tglmulaiText = "";
                            if (tglMulai != null) {
                                tglmulaiText = convert.format(tglMulai);
                            }

                            if(tglmulaiText.equals("30 Nov 0002")){
                                tglmulaiText=" ";
                                tglSelesaiText=" ";
                            }
                            map.put("tglsls", tglSelesaiText);
                            map.put("tglmli", tglmulaiText);
                            Date now = Calendar.getInstance().getTime();
                            String status = "";
                            if (i == 0) {
                                status = e.getString("status1");
                            } else if (i == 1) {
                                status = f.getString("status2");
                            } else if (i == 2) {
                                status = g.getString("status3");
                            } else if (i == 3) {
                                status = h.getString("status4");
                            } else if (i == 4) {
                                status = j.getString("status5");
                            } else if (i == 5) {
                                status = k.getString("status6");
                            }

                            map.put("status", status);
                            map.put("Approve","Approve");
                            switch (status) {
                                case "null":
                                    map.put("status", "-");
                                    break;
                                case "0":
                                    map.put("status", "Unapprove");
                                    break;
                                case "1":
                                    map.put("status", "Approve");
                                    break;
                            }
                        }

                        MyArr.add(map);
                    }
                    SimpleAdapter sAdap;
                    sAdap = new SimpleAdapter(RotasiInternalDetail.this, MyArr, R.layout.item_row_rotasi_internal,
                            new String[]{"stase", "hari", "tglmli", "tglsls","status","dosen","Approve","id_internal","id_rotasi"},
                            new int[]{R.id.tv_judul, R.id.lama, R.id.tv_tgl_mulai, R.id.tv_tgl_selesai,R.id.tv_status,R.id.tv_approval,R.id.btn_approve,R.id.tv_id,R.id.tv_rotasi}) {
                        @Override
                        public View getView(final int position, View convertView, ViewGroup parent) {
                            View lv = super.getView(position, convertView, parent);
                            final TextView stase = lv.findViewById(R.id.tv_judul);
                            final TextView lamahari = lv.findViewById(R.id.lama);
                            final TextView status = lv.findViewById(R.id.tv_status);
                            final TextView tgl_mulai = lv.findViewById(R.id.tv_tgl_mulai);
                            final TextView tgl_selesai = lv.findViewById(R.id.tv_tgl_selesai);
                            final TextView dosen = lv.findViewById(R.id.tv_approval);
                            final TextView id_internal = lv.findViewById(R.id.tv_id);
                            final TextView rotasi= lv.findViewById(R.id.tv_rotasi);
                            final LinearLayout lay_dos = lv.findViewById(R.id.lay_dos);
                            final Button approval = lv.findViewById(R.id.btn_approve);
                            final Button edit = lv.findViewById(R.id.btn_edit);
                            lamahari.append(" hari");
                            if(tgl_mulai.getText().equals(" ")){
                                lay_dos.setVisibility(View.GONE);
                                approval.setVisibility(View.GONE);
                            }
                            if (status.getText().equals("Approve")) {
                                status.setTextColor(getResources().getColor(R.color.md_green_900));
                                lay_dos.setVisibility(View.GONE);
                                approval.setVisibility(View.GONE);
                            } else if (status.getText().equals("Unapprove")) {
                                status.setTextColor(getResources().getColor(R.color.md_red_900));
                            } else if(status.getText().equals("-")){
                                lay_dos.setVisibility(View.GONE);
                                approval.setVisibility(View.GONE);
                            }
                            if(dosen.getText().equals("null")){
                                lay_dos.setVisibility(View.GONE);
                            }

                            approval.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent a=new Intent(RotasiInternalDetail.this, ri_main_approval.class);
                                    a.putExtra("id_internal",id_internal.getText());
                                    a.putExtra("id",id);
                                    a.putExtra("rotasi",rotasi.getText());
                                    a.putExtra("dosen",dosen.getText());
                                    startActivity(a);
                                }
                            });

                            return lv;
                        }
                    };

                    lv_rot.setAdapter(sAdap);

                } catch (JSONException | ParseException e) {
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

    public String getJSONUrl(String url) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Download OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str.toString();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
