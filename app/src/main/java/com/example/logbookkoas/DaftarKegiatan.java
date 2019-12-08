package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DaftarKegiatan extends AppCompatActivity {
    String showURL = "http://192.168.1.6/logbook/daftar_kegiatan_dosen.php";
    String spinnerURL = "http://192.168.1.6/logbook/spinnerkegiatandosen.php";
    RequestQueue requestQueue;
    ListView rvdafkeg;
    Spinner spinnerJenisJurnal, spinnerStatus;
    SearchableSpinner spinnerStase, spinnerNamaMahasiswa;
    private static final String KEY_USERNAME = "username";
    String[] jenis_jurnal ={"Jurnal Penyakit", "Jurnal Keterampilan"};
    String[] status ={"Semua status","Approved", "Unapproved"};
    Button btnfilter;
    ArrayList<String> idStase, Stase, namaMahasiswa, nimMahasiswa;
//    String [] arrayStase = getStringArray(Stase);
    SessionHandler session;
    private ProgressDialog pDialog;
    TextView tv_coba;
    ArrayList<HashMap<String, String>> rv_dafkeg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kegiatan);
        namaMahasiswa = new ArrayList<String>();
        nimMahasiswa = new ArrayList<String>();
        rv_dafkeg = new ArrayList<HashMap<String, String>>();
        rvdafkeg = findViewById(R.id.rv_daftarkegiatan);
        btnfilter = findViewById(R.id.btn_filter);
        tv_coba = findViewById(R.id.cobajson);
        spinnerStatus = findViewById(R.id.spinner_status);
        spinnerJenisJurnal = findViewById(R.id.spinner_jnsjurnal);
        spinnerStase = findViewById(R.id.spinner_stase);
        spinnerNamaMahasiswa = findViewById(R.id.spinner_nama_mahasiswa);
        Stase = new ArrayList<String>();
        idStase = new ArrayList<String>();
//        Stase.add("Semua stase");
//        String[] str = getStringArray(Stase);

        getArrayStase();
        getArrayNamaMahasiswa();


        ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,jenis_jurnal);
        a.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerJenisJurnal.setAdapter(a);

        ArrayAdapter<String> b = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, status);
        b.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerStatus.setAdapter(b);

        btnfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvdafkeg.setAdapter(null);
                getArray();

            }
        });


    }


    private void getArray() {
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            session = new SessionHandler(getApplicationContext());
            User user = session.getUserDetails();
            String username = user.getUsername();
            request.put(KEY_USERNAME, username);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                showURL,request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jurnal_penyakit = response.getJSONArray("jurnal_penyakit");
                    HashMap<String, String> item = new HashMap<String, String>();
                    rv_dafkeg.clear();
                    item.clear();
                    for (int i = 0; i < jurnal_penyakit.length(); i++) {
                        JSONObject j = jurnal_penyakit.getJSONObject(i);
                        String id = j.getString("id");
                        String nim = j.getString("nim");
                        String tanggal = j.getString("tanggal");
                        item.put("id",id);
                        item.put("nim", nim);
                        item.put("tanggal",tanggal);
                        rv_dafkeg.add(item);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ListAdapter adapter =new SimpleAdapter(
                        getApplicationContext(), rv_dafkeg,R.layout.recycler_view_dafkeg,
                        new String[]{"id","nim","tanggal"},
                        new int[]{R.id.namamhsw_dafkeg,R.id.nim_dafkeg,R.id.tgl_dafkeg}
                );
                rvdafkeg.setAdapter(adapter);

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
    private void getArrayStase(){
        JsonObjectRequest jsonstase = new JsonObjectRequest(
                spinnerURL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonStase = response.getJSONArray("stase");
                    Stase.add("Semua stase");
                    for (int i = 0; i < jsonStase.length(); i++) {
                        JSONObject j = jsonStase.getJSONObject(i);
                        String id = j.getString("id");
                        String kepaniteraan = j.getString("kepaniteraan");
                        idStase.add(id);
                        Stase.add(kepaniteraan);
                    }
                    String[] str = getStringArray(Stase);
                    ArrayAdapter<String> c = new ArrayAdapter<>(DaftarKegiatan.this, android.R.layout.simple_spinner_item,str);
                    spinnerStase.setAdapter(c);
                    spinnerStase.setTitle("Pilih Stase");
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
        MySingleton.getInstance(DaftarKegiatan.this).addToRequestQueue(jsonstase);

    }
    private void getArrayNamaMahasiswa(){
        JsonObjectRequest jsonstase = new JsonObjectRequest(
                spinnerURL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonStase = response.getJSONArray("nama");
                    namaMahasiswa.add("Semua mahasiswa");
                    for (int i = 0; i < jsonStase.length(); i++) {
                        JSONObject j = jsonStase.getJSONObject(i);
                        String nim = j.getString("nim");
                        String nama = j.getString("nama");
                        nimMahasiswa.add(nim);
                        namaMahasiswa.add(nama);
                    }
                    String[] str = getStringArray(namaMahasiswa);
                    ArrayAdapter<String> c = new ArrayAdapter<>(DaftarKegiatan.this, android.R.layout.simple_spinner_item,str);
                    spinnerNamaMahasiswa.setAdapter(c);
                    spinnerNamaMahasiswa.setTitle("Pilih Mahasiswa");

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
        MySingleton.getInstance(DaftarKegiatan.this).addToRequestQueue(jsonstase);

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


