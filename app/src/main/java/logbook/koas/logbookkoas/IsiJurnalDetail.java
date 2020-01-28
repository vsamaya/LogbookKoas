package logbook.koas.logbookkoas;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class IsiJurnalDetail extends AppCompatActivity {
    private SessionHandler session;
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ANGKATAN = "angkatan";
    private static final String KEY_STASE = "stase";
    private static final String KEY_TANGGAL = "tanggal";
    private static final String KEY_SEVALUASI = "SEvaluasi";
    private static final String KEY_SRENCANA = "SRencana";
    private String header = "https://logbook.fk.undip.ac.id/koas/android/getKepaniteraan.php";
    private String judul = "https://logbook.fk.undip.ac.id/koas/android/getJadwal.php";
    private String update_status = "https://logbook.fk.undip.ac.id/koas/android/updateStatus.php";
    private String updateEntry = "https://logbook.fk.undip.ac.id/koas/android/updateEntry.php";
    private String create_evaluasi = "https://logbook.fk.undip.ac.id/koas/android/create_evaluasi.php";
    private String url_re = "https://logbook.fk.undip.ac.id/koas/android/show_re.php";
    public static final String KEY_ID = "id";
    TextView stase,tanggal,id_stase,coba;
    EditText evaluasi,rencana;
    LinearLayout MainLayout, j_penyakit, j_ketrampilan;
    ListView lv_penyakit,lv_ketrampilan;
    Button entry;
    ArrayList<HashMap<String, String>> MyArr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_jurnal_detail);
        stase = findViewById(R.id.stase);
        MainLayout = findViewById(R.id.MainLayout);
        evaluasi = findViewById(R.id.evaluasi);
        rencana = findViewById(R.id.rencana);
        entry = findViewById(R.id.entry);
        lv_penyakit = findViewById(R.id.lv_penyakit);
        lv_ketrampilan = findViewById(R.id.lv_ketrampilan);
        tanggal = findViewById(R.id.tanggal);
        id_stase = findViewById(R.id.tanggal_mulai);
        coba = findViewById(R.id.cobacoba);
        j_penyakit = findViewById(R.id.j_penyakit);
        j_ketrampilan = findViewById(R.id.j_ketrampilan);
        Intent intent = getIntent();
        final String id = intent.getStringExtra(mainIsiJurnal.KEY_ID);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        judul();
        re();
        create();
        process();
        j_penyakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(IsiJurnalDetail.this, IsiJurnalKP.class);
                a.putExtra("jurnal","jurnal_penyakit");
                a.putExtra("stase", stase.getText());
                a.putExtra("id_stase", id_stase.getText());
                a.putExtra("id", id);
                startActivity(a);
                finish();
            }
        });

        j_ketrampilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(IsiJurnalDetail.this, IsiJurnalKP.class);
                b.putExtra ("jurnal","jurnal_ketrampilan");
                b.putExtra("stase", stase.getText());
                b.putExtra("id_stase", id_stase.getText());
                b.putExtra("id", id);
                startActivity(b);
                finish();
            }
        });

    }


        private void judul() {
            Intent intent = getIntent();
            final String id = intent.getStringExtra(mainIsiJurnal.KEY_ID);
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


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    private void create() {
        JSONObject request = new JSONObject();
        try {
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

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, create_evaluasi, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Display error message whenever an error occurs


                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    private void re(){
        JSONObject request = new JSONObject();
        try {
            Intent intent = getIntent();
            final String id = intent.getStringExtra("id");
            session = new SessionHandler(getApplicationContext());
            User user = session.getUserDetails();
            String username = user.getUsername();
            SimpleDateFormat convert = new SimpleDateFormat("yyyy-MM-dd");
            Date now = Calendar.getInstance().getTime();
            String tanggal = convert.format(now);
            request.put(KEY_USERNAME, username);
            request.put(KEY_ID, id);
            request.put(KEY_TANGGAL, tanggal);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, url_re, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray re = response.getJSONArray("re");
                            JSONObject c = re.getJSONObject(0);
                            String TEvaluasi=c.getString("evaluasi");
                            String TRencana=c.getString("rencana");
                            evaluasi.setText(TEvaluasi);
                            rencana.setText(TRencana);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Display error message whenever an error occurs


                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
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
            Toast.makeText(IsiJurnalDetail.this, "Entry Berhasil",
                    Toast.LENGTH_LONG).show();
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }


        }

    }