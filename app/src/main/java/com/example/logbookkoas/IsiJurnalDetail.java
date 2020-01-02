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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
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
    private String showURL = "http://192.168.3.5/logbook/daftar_isi_jurnal.php";
    private String deleteJurnalURL= "http://192.168.3.5/logbook/deleteJurnal.php";
    public static final String KEY_ID = "id";
    TextView stase,tanggal,id_stase,coba;
    EditText evaluasi,rencana;
    LinearLayout MainLayout;
    ListView lv_penyakit,lv_ketrampilan;
    Button buttonPenyakit, buttonKetrampilan, entry;
    ArrayList<String> id_penyakit, id_ketrampilan, statusPenyakit, statusKetrampilan;
    ArrayList<HashMap<String,String>> list_jurnal_penyakit, list_jurnal_ketrampilan;


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
        id_stase = findViewById(R.id.tanggal_mulai);
        coba = findViewById(R.id.cobacoba);
        id_penyakit= new ArrayList<String>();
        id_ketrampilan= new ArrayList<String>();
        statusPenyakit= new ArrayList<String>();
        statusKetrampilan= new ArrayList<String>();
        list_jurnal_penyakit = new ArrayList<HashMap<String, String>>();
        list_jurnal_ketrampilan = new ArrayList<HashMap<String, String>>();
        Intent intent = getIntent();
        final String id = intent.getStringExtra(mainIsiJurnal.KEY_ID);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        MainLayout.setVisibility(LinearLayout.GONE);
        judul();
        process();
        showList();
        buttonKetrampilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(IsiJurnalDetail.this, tambahJurnal.class);
                a.putExtra("jurnal","jurnal_ketrampilan");
                a.putExtra("stase", stase.getText());
                a.putExtra("id_stase", id_stase.getText());
                startActivity(a);
                finish();
            }
        });

        buttonPenyakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(IsiJurnalDetail.this, tambahJurnal.class);
                b.putExtra ("jurnal","jurnal_penyakit");
                b.putExtra("stase", stase.getText());
                b.putExtra("id_stase", id_stase.getText());
                startActivity(b);
                finish();
            }
        });

    }


        private void judul() {
            Intent intent = getIntent();
            final String id = intent.getStringExtra(mainIsiJurnal.KEY_ID);
            String url_judul = "http://192.168.3.5/logbook/getKepaniteraan.php?id="+id;

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
            String url_judul = "http://192.168.3.5/logbook/getJadwal.php?username="+username+"&stase="+idStase;

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
                    MainLayout.setVisibility(LinearLayout.VISIBLE);

                    String status = "1";
                    String url_status = "http://192.168.3.5/logbook/updateStatus.php?username="+username+"&stase="+idStase+"&status="+status;
                    try {
                        new JSONArray(getJSONUrl(url_status));

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
                else if(now.after(tglMulai)){
                    String status = "0";
                    String url_status = "http://192.168.3.5/logbook/updateStatus.php?username="+username+"&stase="+idStase+"&status="+status;
                    try {
                        new JSONArray(getJSONUrl(url_status));

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                else{
                    String status = "2";
                    String url_status = "http://192.168.3.5/logbook/updateStatus.php?username="+username+"&stase="+idStase+"&status="+status;
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
            String updateEntry = "http://192.168.3.5/logbook/updateEntry.php";
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
        private void showList(){
            JSONObject request = new JSONObject();
            try {
                //Populate the request parameters
                session = new SessionHandler(getApplicationContext());
                User user = session.getUserDetails();
                String username = user.getUsername();
                request.put("username", username);
                Intent intent = getIntent();
                String id = intent.getStringExtra(mainIsiJurnal.KEY_ID);
                request.put("id_stase", id);



            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest json = new JsonObjectRequest(
                    showURL,request, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        list_jurnal_penyakit.clear();
                        JSONArray lain_penyakit = response.getJSONArray("lain_penyakit");
                        JSONArray dosen_penyakit = response.getJSONArray("dosen_penyakit");
                        JSONArray kegiatan_penyakit = response.getJSONArray("kegiatan_penyakit");
                        JSONArray lokasi_penyakit = response.getJSONArray("lokasi_penyakit");
                        JSONArray penyakit1 = response.getJSONArray("penyakit1");
                        final JSONArray penyakit2 = response.getJSONArray("penyakit2");
                        JSONArray penyakit3= response.getJSONArray("penyakit3");
                        JSONArray penyakit4= response.getJSONArray("penyakit4");


                        for (int i = 0; i < lain_penyakit.length(); i++) {
                            JSONObject j = lain_penyakit.getJSONObject(i);
                            JSONObject j_nama = dosen_penyakit.getJSONObject(i);
                            JSONObject j_kegiatan = kegiatan_penyakit.getJSONObject(i);
                            JSONObject j_lokasi = lokasi_penyakit.getJSONObject(i);
                            JSONObject j_p1 = penyakit1.getJSONObject(i);
                            JSONObject j_p2 = penyakit2.getJSONObject(i);
                            JSONObject j_p3 = penyakit3.getJSONObject(i);
                            JSONObject j_p4 = penyakit4.getJSONObject(i);
                            HashMap<String, String> item = new HashMap<String, String>();
                            item.put("id",j.getString("id"));
                            item.put("nim", j.getString("nim"));
                            String tanggal = changeDate(j.getString("tanggal"));
                            item.put("tanggal",tanggal+" ");
                            item.put("waktu",j.getString("jam_awal")+"-"+j.getString("jam_akhir")+" ");
                            if(j.getString("status").equals("1")){
                                item.put("status","Approved");
                            }else item.put("status","Unapproved");
                            item.put("dosen", j_nama.getString("nama")+", "+j_nama.getString("gelar"));
                            item.put("kegiatan", j_kegiatan.getString("kegiatan"));
                            item.put("level",j_kegiatan.getString("level"));
                            item.put("kategori",j_kegiatan.getString("kategori"));
                            id_penyakit.add(j.getString("id"));
                            statusPenyakit.add(j.getString("status"));
                            item.put("lokasi", j_lokasi.getString("lokasi"));
                            item.put("p1", j_p1.getString("penyakit").toUpperCase() +
                                    " (" + j_p1.getString("skdi_level") + "-" + j_p1.getString("sumber") + ") ");

                            if (j_p2.getString("penyakit").equals("null")) {
                                item.put("p2", "-");

                            } else {
                                item.put("p2", j_p2.getString("penyakit").toUpperCase() +
                                        " (" + j_p2.getString("skdi_level") + "-"
                                        + j_p2.getString("sumber") + ") ");
                            }
                            if (j_p3.getString("penyakit").equals("null")) {
                                item.put("p3", "-");
                            } else {
                                item.put("p3", j_p3.getString("penyakit").toUpperCase()
                                        + " (" + j_p3.getString("skdi_level") + "-"
                                        + j_p3.getString("sumber") + ") ");
                            }
                            if (j_p4.getString("penyakit").equals("null")) {
                                item.put("p4", "-");
                            } else {
                                item.put("p4", j_p4.getString("penyakit").toUpperCase()
                                        + " (" + j_p4.getString("skdi_level") + "-"
                                        + j_p4.getString("sumber") + ") ");
                            }
                            list_jurnal_penyakit.add(item);

                        }

                        list_jurnal_ketrampilan.clear();
                        JSONArray lain_ketrampilan = response.getJSONArray("lain_ketrampilan");
                        JSONArray dosen_ketrampilan = response.getJSONArray("dosen_ketrampilan");
                        JSONArray kegiatan_ketrampilan = response.getJSONArray("kegiatan_ketrampilan");
                        JSONArray lokasi_ketrampilan = response.getJSONArray("lokasi_ketrampilan");
                        JSONArray ketrampilan1= response.getJSONArray("keterampilan1");
                        JSONArray ketrampilan2= response.getJSONArray("keterampilan2");
                        JSONArray ketrampilan3= response.getJSONArray("keterampilan3");
                        JSONArray ketrampilan4= response.getJSONArray("keterampilan4");
                        for (int i = 0; i < lain_ketrampilan.length(); i++) {
                            JSONObject j = lain_ketrampilan.getJSONObject(i);
                            JSONObject j_nama = dosen_ketrampilan.getJSONObject(i);
                            JSONObject j_lokasi = lokasi_ketrampilan.getJSONObject(i);
                            JSONObject j_kegiatan = kegiatan_ketrampilan.getJSONObject(i);
                            JSONObject j_p1 = ketrampilan1.getJSONObject(i);
                            JSONObject j_p2 = ketrampilan2.getJSONObject(i);
                            JSONObject j_p3 = ketrampilan3.getJSONObject(i);
                            JSONObject j_p4 = ketrampilan4.getJSONObject(i);
                            HashMap<String, String> item = new HashMap<String, String>();
                            item.put("id",j.getString("id"));
                            item.put("nim", j.getString("nim"));
                            String tanggal = changeDate(j.getString("tanggal"));
                            item.put("tanggal",tanggal+" ");
                            item.put("waktu",j.getString("jam_awal")+"-"+j.getString("jam_akhir")+" ");
                            if(j.getString("status").equals("1")){
                                item.put("status","Approved");
                            }else item.put("status","Unapproved");
                            item.put("dosen", j_nama.getString("nama")+", "+j_nama.getString("gelar"));
                            item.put("kegiatan", j_kegiatan.getString("kegiatan"));
                            item.put("level",j_kegiatan.getString("level"));
                            item.put("kategori",j_kegiatan.getString("kategori"));
                            id_ketrampilan.add(j.getString("id"));
                            statusKetrampilan.add(j.getString("status"));
                            item.put("lokasi", j_lokasi.getString("lokasi"));

                            item.put("p1", j_p1.getString("ketrampilan").toUpperCase() +
                                    " (" + j_p1.getString("skdi_level") + "-" + j_p1.getString("sumber") + ") ");

                            if (j_p2.getString("ketrampilan").equals("null")) {
                                item.put("p2", "-");

                            } else {
                                item.put("p2", j_p2.getString("ketrampilan").toUpperCase() +
                                        " (" + j_p2.getString("skdi_level") + "-"
                                        + j_p2.getString("sumber") + ") ");
                            }
                            if (j_p3.getString("ketrampilan").equals("null")) {
                                item.put("p3", "-");
                            } else {
                                item.put("p3", j_p3.getString("ketrampilan").toUpperCase()
                                        + " (" + j_p3.getString("skdi_level") + "-"
                                        + j_p3.getString("sumber") + ") ");
                            }
                            if (j_p4.getString("ketrampilan").equals("null")) {
                                item.put("p4", "-");
                            } else {
                                item.put("p4", j_p4.getString("ketrampilan").toUpperCase()
                                        + " (" + j_p4.getString("skdi_level") + "-"
                                        + j_p4.getString("sumber") + ") ");
                            }
                            list_jurnal_ketrampilan.add(item);

                        }


                        ListAdapter adapter =new SimpleAdapter(
                                getApplicationContext(), list_jurnal_penyakit,R.layout.item_row_show,
                                new String[]{"waktu","lokasi","kegiatan","p1","p2","p3","p4","dosen","status"},
                                new int[]{R.id.tv_jam,R.id.tv_lokasi,R.id.tv_kegiatan,R.id.tv_sumber,R.id.tv_sumber2,
                                        R.id.tv_sumber3,R.id.tv_sumber4,R.id.tv_dosen,R.id.tv_status}
                        )
                        {
                            @Override
                            public View getView (final int position, View convertView, ViewGroup parent)
                            {
                                View v = super.getView(position, convertView, parent);

                                Button edit = v.findViewById(R.id.btn_Edit);
                                Button delete = v.findViewById(R.id.btn_Delete);
                                Button approve = v.findViewById(R.id.btn_approve);
                                String statP[] = getStringArray(statusPenyakit);
                                final TextView lokasi = v.findViewById(R.id.tv_lokasi);
                                final TextView kegiatan = v.findViewById(R.id.tv_kegiatan);
                                final TextView dosen = v.findViewById(R.id.tv_dosen);
                                TextView jam = v.findViewById(R.id.tv_jam);
                                String jam1 = (String) jam.getText();
                                final String jam_awal = jam1.substring(0,5);
                                final String jam_akhir = jam1.substring(9,14);
                                final TextView penyakit1 = v.findViewById(R.id.tv_sumber);
                                final TextView penyakit2 = v.findViewById(R.id.tv_sumber2);
                                final TextView penyakit3 = v.findViewById(R.id.tv_sumber3);
                                final TextView penyakit4 = v.findViewById(R.id.tv_sumber4);

                                approve.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent a=new Intent(IsiJurnalDetail.this, MainApprove.class);
                                        a.putExtra("jurnal","jurnal_penyakit");
                                        a.putExtra("dosen",dosen.getText());
                                        String[] idArray = getStringArray(id_penyakit);
                                        String id = idArray[position];
                                        a.putExtra("id_jurnal",id);
                                        startActivity(a);
                                    }
                                });

                               delete.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       String jenis = "Jurnal Penyakit";
                                       String[] idArray = getStringArray(id_penyakit);
                                       String id = idArray[position];
                                       deleteJurnal(jenis,id);


                                   }
                               });
                                edit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i = new Intent(IsiJurnalDetail.this, editJurnal.class);
                                        i.putExtra("jurnal","jurnal_penyakit");
                                        i.putExtra("stase", stase.getText());
                                        i.putExtra("id_stase", id_stase.getText());
                                        i.putExtra("lokasi",lokasi.getText());
                                        i.putExtra("kegiatan", kegiatan.getText());
                                        i.putExtra("dosen", dosen.getText());
                                        i.putExtra("jam_awal",jam_awal);
                                        i.putExtra("jam_akhir", jam_akhir);
                                        i.putExtra("jenis1",penyakit1.getText());
                                        i.putExtra("jenis2",penyakit2.getText());
                                        i.putExtra("jenis3",penyakit3.getText());
                                        i.putExtra("jenis4",penyakit4.getText());
                                        String[] idArray = getStringArray(id_penyakit);
                                        String id = idArray[position];
                                        i.putExtra("id_jurnal",id);

                                        startActivity(i);
                                    }
                                });
                               if(statP[position].equals("0")){
                                   approve.setVisibility(View.VISIBLE);
                               }else {
                                   approve.setVisibility(View.GONE);
                               }




                                return v;
                            }


                        };
                        ListAdapter adapter1 =new SimpleAdapter(
                                getApplicationContext(), list_jurnal_ketrampilan,R.layout.item_row_show,
                                new String[]{"waktu","lokasi","kegiatan","p1","p2","p3","p4","dosen","status"},
                                new int[]{R.id.tv_jam,R.id.tv_lokasi,R.id.tv_kegiatan,R.id.tv_sumber,R.id.tv_sumber2,
                                        R.id.tv_sumber3,R.id.tv_sumber4,R.id.tv_dosen,R.id.tv_status}
                        )
                        {
                            @Override
                            public View getView (final int position, View convertView, ViewGroup parent)
                            {
                                View v = super.getView(position, convertView, parent);

                                Button edit = v.findViewById(R.id.btn_Edit);
                                Button delete = v.findViewById(R.id.btn_Delete);
                                Button approve = v.findViewById(R.id.btn_approve);
                                String statK[] = getStringArray(statusKetrampilan);
                                final TextView lokasi = v.findViewById(R.id.tv_lokasi);
                                final TextView kegiatan = v.findViewById(R.id.tv_kegiatan);
                                final TextView dosen = v.findViewById(R.id.tv_dosen);
                                TextView jam = v.findViewById(R.id.tv_jam);
                                String jam1 = (String) jam.getText();
                                final String jam_awal = jam1.substring(0,5);
                                final String jam_akhir = jam1.substring(9,14);
                                final TextView ketrampilan1 = v.findViewById(R.id.tv_sumber);
                                final TextView ketrampilan2 = v.findViewById(R.id.tv_sumber2);
                                final TextView ketrampilan3 = v.findViewById(R.id.tv_sumber3);
                                final TextView ketrampilan4 = v.findViewById(R.id.tv_sumber4);

                                approve.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent a=new Intent(IsiJurnalDetail.this, MainApprove.class);
                                        a.putExtra("jurnal","jurnal_ketrampilan");
                                        a.putExtra("dosen",dosen.getText());
                                        String[] idArray = getStringArray(id_penyakit);
                                        String id = idArray[position];
                                        a.putExtra("id_jurnal",id);
                                        startActivity(a);
                                    }
                                });

                                delete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String jenis = "Jurnal Ketrampilan";
                                        String[] idArray = getStringArray(id_ketrampilan);
                                        String id = idArray[position];
                                        deleteJurnal(jenis,id);
                                    }
                                });
                                edit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i = new Intent(IsiJurnalDetail.this, editJurnal.class);
                                        i.putExtra("jurnal","jurnal_ketrampilan");
                                        i.putExtra("stase", stase.getText());
                                        i.putExtra("id_stase", id_stase.getText());
                                        i.putExtra("lokasi",lokasi.getText());
                                        i.putExtra("kegiatan",kegiatan.getText());
                                        i.putExtra("dosen", dosen.getText());
                                        i.putExtra("jam_awal",jam_awal);
                                        i.putExtra("jam_akhir", jam_akhir);
                                        i.putExtra("jenis1",ketrampilan1.getText());
                                        i.putExtra("jenis2",ketrampilan2.getText());
                                        i.putExtra("jenis3",ketrampilan3.getText());
                                        i.putExtra("jenis4",ketrampilan4.getText());
                                        String[] idArray = getStringArray(id_ketrampilan);
                                        String id = idArray[position];
                                        i.putExtra("id_jurnal", id);
                                        startActivity(i);
                                    }
                                });
                                if(statK[position].equals("0")){
                                    approve.setVisibility(View.VISIBLE);
                                }else {
                                    approve.setVisibility(View.GONE);
                                }



                                return v;
                            }


                        };
                        lv_penyakit.setAdapter(adapter);
                        lv_ketrampilan.setAdapter(adapter1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(), "Gagal mengambil data, silahkan cek koneksi Anda",Toast.LENGTH_LONG).show();

                }
            });
            MySingleton.getInstance(this).addToRequestQueue(json);


        }

    private static String  changeDate(String date){
        String[] namaBulan ={"Januari","Februari","Maret","April","Mei","Juni","Juli",
                "Agustus","September","Oktober","November","Desember"};
        final String Tahun = date.substring(0,4);
        String Bulan = date.substring(5,7);
        int Bulan1 = Integer.parseInt(Bulan);
        final String Tanggal = date.substring(8);
        String changedDate = Tanggal+" "+namaBulan[Bulan1-1]+" "+Tahun;
        return changedDate;
    }


    private void deleteJurnal(String jns, String id){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            session = new SessionHandler(getApplicationContext());
            User user = session.getUserDetails();
            String username = user.getUsername();
            request.put(KEY_USERNAME, username);
            request.put("jenisJurnal",jns);
            request.put("id",id);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, deleteJurnalURL, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            showList();

                            if (response.getInt(KEY_STATUS) == 0) {

                            }
                            else{
                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);

    }
    private static String[] getStringArray(ArrayList<String> arr){
        // declaration and initialise String Array
        String str[] = new String[arr.size()];

        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++) {

            // Assign each value to String array
            str[j] = arr.get(j);
        }

        return str;
    }

    }