package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.ArrayList;
import java.util.HashMap;

public class IsiJurnalKP extends AppCompatActivity {
    private SessionHandler session;
    private static final String KEY_USERNAME = "username";
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    public static final String KEY_ID = "id";
    private String showURL = "http://192.168.43.44/logbook/daftar_isi_jurnal.php";
    private String deleteJurnalURL= "http://192.168.43.44/logbook/deleteJurnal.php";
    ListView list;
    LinearLayout buttonPenyakit;
    TextView head1,tmp;
    ArrayList<String> id_penyakit, id_ketrampilan, statusPenyakit, statusKetrampilan;
    ArrayList<HashMap<String,String>> list_jurnal_penyakit, list_jurnal_ketrampilan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_jurnal_kp);
        buttonPenyakit = findViewById(R.id.buttonPenyakit);
        list = findViewById(R.id.list);
        head1 = findViewById(R.id.head1);
        tmp = findViewById(R.id.tmp);
        id_penyakit= new ArrayList<String>();
        id_ketrampilan= new ArrayList<String>();
        statusPenyakit= new ArrayList<String>();
        statusKetrampilan= new ArrayList<String>();
        list_jurnal_penyakit = new ArrayList<HashMap<String, String>>();
        list_jurnal_ketrampilan = new ArrayList<HashMap<String, String>>();
        Intent intent = getIntent();
        final String jurnal = intent.getStringExtra("jurnal");
        final String stase = intent.getStringExtra("stase");
        final String id_stase = intent.getStringExtra("id_stase");
        tmp.setText(jurnal);
        if (tmp.getText().equals("jurnal_penyakit")) {
            showListPenyakit();}
        else if (tmp.getText().equals("jurnal_ketrampilan")) {
            head1.setText("JURNAL KETRAMPILAN");
            showListKetrampilan();}


        buttonPenyakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(IsiJurnalKP.this, tambahJurnal.class);
                a.putExtra("jurnal",jurnal);
                a.putExtra("stase", stase);
                a.putExtra("id_stase", id_stase);
                startActivity(a);
                finish();
            }
        });

    }


    private void showListPenyakit(){
        Intent intent = getIntent();
        final String jurnal = intent.getStringExtra("jurnal");
        final String stase = intent.getStringExtra("stase");
        final String id_stase = intent.getStringExtra("id_stase");
        final String id = intent.getStringExtra("id");
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            session = new SessionHandler(getApplicationContext());
            User user = session.getUserDetails();
            String username = user.getUsername();
            request.put("username", username);
            request.put("id_stase", id);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json = new JsonObjectRequest(
                showURL,request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
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
                        item.put("dos", j_nama.getString("nama"));
                        item.put("dosen", j_nama.getString("nama")+", "+j_nama.getString("gelar"));
                        item.put("kegiatan", j_kegiatan.getString("kegiatan"));
                        item.put("level",j_kegiatan.getString("level"));
                        item.put("kategori",j_kegiatan.getString("kategori"));
                        id_penyakit.add(j.getString("id"));
                        statusPenyakit.add(j.getString("status"));
                        item.put("lokasi", j_lokasi.getString("lokasi"));
                        item.put("p1", j_p1.getString("penyakit").toUpperCase());

                        if (j_p2.getString("penyakit").equals("null")) {
                            item.put("p2", "-");

                        } else {
                            item.put("p2", j_p2.getString("penyakit").toUpperCase());
                        }
                        if (j_p3.getString("penyakit").equals("null")) {
                            item.put("p3", "-");
                        } else {
                            item.put("p3", j_p3.getString("penyakit").toUpperCase());
                        }
                        if (j_p4.getString("penyakit").equals("null")) {
                            item.put("p4", "-");
                        } else {
                            item.put("p4", j_p4.getString("penyakit").toUpperCase());
                        }
                        list_jurnal_penyakit.add(item);

                    }

                    ListAdapter adapter =new SimpleAdapter(
                            getApplicationContext(), list_jurnal_penyakit, R.layout.item_row_show,
                            new String[]{"waktu","lokasi","kegiatan","p1","p2","p3","p4","dosen","status","dos"},
                            new int[]{R.id.tv_jam, R.id.tv_lokasi, R.id.tv_kegiatan, R.id.tv_sumber, R.id.tv_sumber2,
                                    R.id.tv_sumber3, R.id.tv_sumber4, R.id.tv_dosen, R.id.tv_status, R.id.tv_dos}
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
                            final TextView dos = v.findViewById(R.id.tv_dos);
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
                                    Intent a=new Intent(IsiJurnalKP.this, MainApprove.class);
                                    a.putExtra("jurnal","jurnal_penyakit");
                                    a.putExtra("dos",dos.getText());
                                    a.putExtra("dosen_lengkap",dosen.getText());
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
                                    deleteJurnalPenyakit(jenis,id);


                                }
                            });
                            edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(IsiJurnalKP.this, editJurnal.class);
                                    i.putExtra("jurnal","jurnal_penyakit");
                                    i.putExtra("stase", stase);
                                    i.putExtra("id_stase", id_stase);
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
                                    finish();
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
                    list.setAdapter(adapter);

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

    private void showListKetrampilan(){
        Intent intent = getIntent();
        final String jurnal = intent.getStringExtra("jurnal");
        final String stase = intent.getStringExtra("stase");
        final String id_stase = intent.getStringExtra("id_stase");
        final String id = intent.getStringExtra("id");
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            session = new SessionHandler(getApplicationContext());
            User user = session.getUserDetails();
            String username = user.getUsername();
            request.put("username", username);
            request.put("id_stase", id);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json = new JsonObjectRequest(
                showURL,request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    list_jurnal_ketrampilan.clear();
                    id_ketrampilan.clear();
                    statusKetrampilan.clear();
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
                        item.put("dos",j_nama.getString("nama"));
                        item.put("dosen", j_nama.getString("nama")+", "+j_nama.getString("gelar"));
                        item.put("kegiatan", j_kegiatan.getString("kegiatan"));
                        item.put("level",j_kegiatan.getString("level"));
                        item.put("kategori",j_kegiatan.getString("kategori"));
                        id_ketrampilan.add(j.getString("id"));
                        statusKetrampilan.add(j.getString("status"));
                        item.put("lokasi", j_lokasi.getString("lokasi"));

                        item.put("p1", j_p1.getString("ketrampilan").toUpperCase());

                        if (j_p2.getString("ketrampilan").equals("null")) {
                            item.put("p2", "-");

                        } else {
                            item.put("p2", j_p2.getString("ketrampilan").toUpperCase());
                        }
                        if (j_p3.getString("ketrampilan").equals("null")) {
                            item.put("p3", "-");
                        } else {
                            item.put("p3", j_p3.getString("ketrampilan").toUpperCase());
                        }
                        if (j_p4.getString("ketrampilan").equals("null")) {
                            item.put("p4", "-");
                        } else {
                            item.put("p4", j_p4.getString("ketrampilan").toUpperCase());
                        }
                        list_jurnal_ketrampilan.add(item);

                    }

                    ListAdapter adapter =new SimpleAdapter(
                            getApplicationContext(), list_jurnal_ketrampilan, R.layout.item_row_show,
                            new String[]{"waktu","lokasi","kegiatan","p1","p2","p3","p4","dosen","status","dos"},
                            new int[]{R.id.tv_jam, R.id.tv_lokasi, R.id.tv_kegiatan, R.id.tv_sumber, R.id.tv_sumber2,
                                    R.id.tv_sumber3, R.id.tv_sumber4, R.id.tv_dosen, R.id.tv_status, R.id.tv_dos}
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
                            final TextView status = v.findViewById(R.id.tv_status);
                            final TextView lokasi = v.findViewById(R.id.tv_lokasi);
                            final TextView kegiatan = v.findViewById(R.id.tv_kegiatan);
                            final TextView dosen = v.findViewById(R.id.tv_dosen);
                            final TextView dos = v.findViewById(R.id.tv_dos);
                            TextView jam = v.findViewById(R.id.tv_jam);
                            String jam1 = (String) jam.getText();
                            final String jam_awal = jam1.substring(0,5);
                            final String jam_akhir = jam1.substring(9,14);
                            final TextView ketrampilan1 = v.findViewById(R.id.tv_sumber);
                            final TextView ketrampilan2 = v.findViewById(R.id.tv_sumber2);
                            final TextView ketrampilan3 = v.findViewById(R.id.tv_sumber3);
                            final TextView ketrampilan4 = v.findViewById(R.id.tv_sumber4);

                            final TextView sumber1 = v.findViewById(R.id.sumber1);
                            final TextView sumber2 = v.findViewById(R.id.sumber2);
                            final TextView sumber3 = v.findViewById(R.id.sumber3);
                            final TextView sumber4 = v.findViewById(R.id.sumber4);

                            sumber1.setText("Ketrampilan 1 : ");
                            sumber2.setText("Ketrampilan 2 : ");
                            sumber3.setText("Ketrampilan 3 : ");
                            sumber4.setText("Ketrampilan 4 : ");

                            if (status.getText().equals("Approved")) {
                                approve.setVisibility(View.GONE);
                                edit.setVisibility(View.GONE);}

                            approve.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent a=new Intent(IsiJurnalKP.this, MainApprove.class);
                                    a.putExtra("jurnal","jurnal_ketrampilan");
                                    a.putExtra("dos",dos.getText());
                                    a.putExtra("dosen_lengkap",dosen.getText());
                                    String[] idArray = getStringArray(id_ketrampilan);
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
                                    deleteJurnalKetrampilan(jenis,id);
                                }
                            });
                            edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(IsiJurnalKP.this, editJurnal.class);
                                    i.putExtra("jurnal","jurnal_ketrampilan");
                                    i.putExtra("stase", stase);
                                    i.putExtra("id_stase", id_stase);
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
                                    finish();
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
                    list.setAdapter(adapter);

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

    private void deleteJurnalPenyakit(String jns, String id){
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
                            showListPenyakit();

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

    private void deleteJurnalKetrampilan(String jns, String id){
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
                            showListKetrampilan();

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
