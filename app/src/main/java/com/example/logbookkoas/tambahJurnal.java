package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

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

public class tambahJurnal extends AppCompatActivity {
    String spinnerURL = "http://192.168.1.6/logbook/spinnerTambahJurnal.php";
    TextView jurnal,potong;
    ArrayList<String> lokasi, id_lokasi, kegiatan, id_kegiatan, sistem_penyakit, id_sistemP,penyakit,id_penyakit;
    SearchableSpinner spinnerLokasi, spinnerKegiatan, spinnerSistem, spinnerJenis;

    private SessionHandler session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_jurnal);
//        jurnal = findViewById(R.id.jurnal);
//        potong = findViewById(R.id.potong);
        Intent intent = getIntent();
        final String jenis_jurnal = intent.getStringExtra("jurnal");
        String nim = "22010118220192";
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        String username = user.getUsername();
        String ang = username.substring(6,8);
        String angkatan = "20"+ang;

//        jurnal.setText(jenis_jurnal);
//        potong.setText(angkatan);
        spinnerLokasi = findViewById(R.id.lokasi_isi_jurnal);
        spinnerKegiatan = findViewById(R.id.keg_isi_jurnal);
        spinnerSistem = findViewById(R.id.sispen_1_isi);
        spinnerJenis = findViewById(R.id.penyakit_1_isi);

        lokasi = new ArrayList<String>();
        id_lokasi = new ArrayList<String>();
        kegiatan = new ArrayList<String>();
        id_kegiatan = new ArrayList<String>();
        sistem_penyakit = new ArrayList<String>();
        id_sistemP = new ArrayList<String>();
        penyakit = new ArrayList<String>();
        id_penyakit = new ArrayList<String>();


        getSpinner();


    }

    private void getSpinner(){
        JsonObjectRequest jsonstase = new JsonObjectRequest(
                spinnerURL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //spiner lokasi
                    JSONArray locArray = response.getJSONArray("lokasi");
                    for (int i = 0; i < locArray.length(); i++) {
                        JSONObject j = locArray.getJSONObject(i);
                        String id = j.getString("id");
                        String loc = j.getString("lokasi");
                        id_lokasi.add(id);
                        lokasi.add(loc);
                    }
                    String[] str = getStringArray(lokasi);
                    ArrayAdapter<String> c = new ArrayAdapter<>(tambahJurnal.this, android.R.layout.simple_spinner_item,str);
                    spinnerLokasi.setAdapter(c);
                    spinnerLokasi.setTitle("Pilih Lokasi");

                    //spinner kegiatan
                    JSONArray kegArray = response.getJSONArray("kegiatan");
                    for (int i = 0; i < kegArray.length(); i++) {
                        JSONObject j = kegArray.getJSONObject(i);
                        String id = j.getString("id");
                        String keg = j.getString("kegiatan");
                        id_kegiatan.add(id);
                        kegiatan.add(keg);
                    }
                    String[] strnama = getStringArray(kegiatan);
                    ArrayAdapter<String> d = new ArrayAdapter<>(tambahJurnal.this, android.R.layout.simple_spinner_item,strnama);
                    spinnerKegiatan.setAdapter(d);
                    spinnerKegiatan.setTitle("Pilih Kegiatan");

                    //spinner sistem penyakit 1
                    JSONArray sispen1Array = response.getJSONArray("sistem_penyakit");
                    for (int i = 0; i < sispen1Array.length(); i++) {
                        JSONObject j = sispen1Array.getJSONObject(i);
                        String id = j.getString("id");
                        String sispen = j.getString("sistem_penyakit");
                        id_sistemP.add(id);
                        sistem_penyakit.add(sispen);
                    }
                    String[] strsispen = getStringArray(sistem_penyakit);
                    final String[] idsispen = getStringArray(id_sistemP);
                    ArrayAdapter<String> a = new ArrayAdapter<>(tambahJurnal.this, android.R.layout.simple_spinner_item,strsispen);
                    a.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spinnerSistem.setAdapter(a);

                    spinnerSistem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String id_s = idsispen[i];

                            getPenyakit(id_s);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


//                    ArrayAdapter<String> b = new ArrayAdapter<>(DaftarKegiatan.this, android.R.layout.simple_spinner_item, status);
//                    b.setDropDownViewResource(android.R.layout.simple_spinner_item);
//                    spinnerStatus.setAdapter(b);
//
//
//                    datePicker.setText("Semua Tanggal");
//                    datePicker.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Calendar calendar = Calendar.getInstance();
//                            final int year = calendar.get(Calendar.YEAR);
//                            final int month= calendar.get(Calendar.MONTH);
//                            final int day = calendar.get(Calendar.DAY_OF_MONTH);
////                            datePicker = view.findViewById(R.id.filter_tanggal);
//                            DatePickerDialog dpDialog = new DatePickerDialog(DaftarKegiatan.this, R.style.datepicker,
//                                    new DatePickerDialog.OnDateSetListener() {
//                                        @Override
//                                        public void onDateSet(DatePicker view, int year, int month1, int day) {
//                                            month1 = month1+1;
//                                            String date = year+"-"+month1+"-"+day;
//                                            datePicker.setText(date);
//                                        }
//                                    },year,month,day);
//                            dpDialog.show();
//                        }
//                    });
//
//
//
//                    btnfilter.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            filterTanggal = datePicker.getText().toString().toLowerCase();
//                            if(filterTanggal.equals("") || filterTanggal.equals("semua tanggal")){
//                                filterTanggal="semua tanggal";}
//                            final String[] NIM = getStringArray(nimMahasiswa);
//                            filterNim = NIM[spinnerNamaMahasiswa.getSelectedItemPosition()];
//                            final String[] id_stase = getStringArray(idStase);
//                            filterIdStase = id_stase[spinnerStase.getSelectedItemPosition()];
//                            filterJenisJurnal = spinnerJenisJurnal.getSelectedItem().toString().trim();
//                            filterStatus = spinnerStatus.getSelectedItem().toString().trim();
//                            Intent intent = new Intent(DaftarKegiatan.this,TampilanDaftarKegiatan.class);
//                            intent.putExtra("nim",filterNim);
//                            intent.putExtra("stase",filterIdStase);
//                            intent.putExtra("jenis_jurnal",filterJenisJurnal);
//                            intent.putExtra("status",filterStatus);
//                            intent.putExtra("tanggal",filterTanggal);
//                            startActivity(intent);
//
//                        }
//                    });

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
        MySingleton.getInstance(tambahJurnal.this).addToRequestQueue(jsonstase);

    }

    private void getPenyakit(String sispen){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put("id_sistemP", sispen);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonpenyakit = new JsonObjectRequest(
                spinnerURL,request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //spiner penyakit
                    JSONArray penyakitArray = response.getJSONArray("penyakit");
                    penyakit.clear();
                    for (int i = 0; i < penyakitArray.length(); i++) {
                        JSONObject j = penyakitArray.getJSONObject(i);
                        String id = j.getString("id");
                        String pen = j.getString("penyakit");
                        id_penyakit.add(id);
                        penyakit.add(pen);
                    }
                    String[] str = getStringArray(penyakit);
                    ArrayAdapter<String> p = new ArrayAdapter<>(tambahJurnal.this, android.R.layout.simple_spinner_item,str);
                    spinnerJenis.setAdapter(p);
                    spinnerJenis.setTitle("Pilih Penyakit");

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
        MySingleton.getInstance(tambahJurnal.this).addToRequestQueue(jsonpenyakit);





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