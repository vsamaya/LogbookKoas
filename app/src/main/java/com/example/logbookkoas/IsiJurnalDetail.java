package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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

public class IsiJurnalDetail extends AppCompatActivity {
    private SessionHandler session;
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ANGKATAN = "angkatan";
    private static final String KEY_STASE = "stase";
    private static final String KEY_TANGGAL = "tanggal";
    private static final String KEY_SEVALUASI = "SEvaluasi";
    private static final String KEY_SRENCANA = "SRencana";
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private String updateEntry = "http://10.104.22.143/updateEntry.php";
    public static final String KEY_ID = "id";
    TextView stase,tanggal;
    EditText evaluasi,rencana;
    LinearLayout MainLayout;
    ListView lv_penyakit,lv_ketrampilan;
    Button buttonPenyakit, buttonKetrampilan, entry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_jurnal_detail);
        stase = findViewById(R.id.stase);
        MainLayout = findViewById(R.id.MainLayout);
        evaluasi = findViewById(R.id.evaluasi);
        rencana = findViewById(R.id.rencana);
        buttonPenyakit = findViewById(R.id.buttonPenyakit);
        buttonKetrampilan = findViewById(R.id.buttonKetrampilan);
        entry = findViewById(R.id.entry);
        lv_penyakit = findViewById(R.id.lv_penyakit);
        lv_ketrampilan = findViewById(R.id.lv_ketrampilan);
        tanggal = findViewById(R.id.tanggal);
        Intent intent = getIntent();
        final String id = intent.getStringExtra(mainIsiJurnal.KEY_ID);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        MainLayout.setVisibility(LinearLayout.GONE);
        judul();
        process();
        buttonKetrampilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(IsiJurnalDetail.this, tambahJurnal.class);
                a.putExtra ("jurnal","jurnal_ketrampilan");
                startActivity(a);
            }
        });

        buttonPenyakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(IsiJurnalDetail.this, tambahJurnal.class);
                b.putExtra ("jurnal","jurnal_penyakit");
                startActivity(b);
            }
        });

    }


        private void judul() {
            Intent intent = getIntent();
            final String id = intent.getStringExtra(mainIsiJurnal.KEY_ID);
            String url_judul = "http://10.104.22.143/getKepaniteraan.php?id="+id;

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
            String url_judul = "http://10.104.22.143/getJadwal.php?username="+username+"&stase="+idStase;

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


                Date now = Calendar.getInstance().getTime();
                if(now.after(tglMulai) && now.before(tglSelesai1)) {
                    MainLayout.setVisibility(LinearLayout.VISIBLE);
                    String status = "1";
                    String url_status = "http://10.104.22.143/updateStatus.php?username="+username+"&stase="+idStase+"&status="+status;
                    try {
                        new JSONArray(getJSONUrl(url_status));

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
                else if(now.after(tglMulai)){
                    String status = "0";
                    String url_status = "http://10.104.22.143/updateStatus.php?username="+username+"&stase="+idStase+"&status="+status;
                    try {
                        new JSONArray(getJSONUrl(url_status));

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                else{
                    String status = "2";
                    String url_status = "http://10.104.22.143/updateStatus.php?username="+username+"&stase="+idStase+"&status="+status;
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

    public void generate(View view) {
        String SEvaluasi = evaluasi.getText().toString();
        String SRencana = rencana.getText().toString();
        if(SEvaluasi.trim().length() == 0 || SRencana.trim().length() == 0){
            Toast.makeText(IsiJurnalDetail.this, "Maaf Entry masih kosong",
                    Toast.LENGTH_LONG).show();
        }
        else if(SEvaluasi.trim().length() > 0 && SRencana.trim().length() > 0) {

            JSONObject request = new JSONObject();
            try {
                //Populate the request parameters
                Intent intent = getIntent();
                final String stase = intent.getStringExtra(mainIsiJurnal.KEY_ID);
                session = new SessionHandler(getApplicationContext());
                User user = session.getUserDetails();
                String username = user.getUsername();
                String ang = username.substring(6,8);
                String angkatan = "20"+ang;
                SimpleDateFormat convert = new SimpleDateFormat("yyyy-MM-dd");
                Date now = Calendar.getInstance().getTime();
                String tanggal = convert.format(now);
                request.put(KEY_USERNAME, username);
                request.put(KEY_ANGKATAN, angkatan);
                request.put(KEY_STASE, stase);
                request.put(KEY_TANGGAL, tanggal);
                request.put(KEY_SEVALUASI, SEvaluasi);
                request.put(KEY_SRENCANA, SRencana);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                    (Request.Method.POST, updateEntry, request, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {


                        }

                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {



                        }
                    });
            // Access the RequestQueue through your singleton class.
            MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }


        }

    }