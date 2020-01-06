package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
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
import java.util.concurrent.TimeUnit;

import javax.sql.StatementEvent;

public class tambahJurnal extends AppCompatActivity {
    String spinnerURL = "http://192.168.0.109/logbook/spinnerTambahJurnal.php";
    String tanggalURL = "http://192.168.0.109/logbook/tanggalTambahJurnal.php";
    String submitURL = "http://192.168.0.109/logbook/submitTambahJurnal.php";
    TextView jurnal,potong;
    ArrayList<String> lokasi, id_lokasi, kegiatan, id_kegiatan;
    ArrayList<String> sistem_penyakit, id_sistemP,penyakit,id_penyakit;
    ArrayList<String> sistem_keterampilan, id_sistemK,keterampilan,id_keterampilan;
    ArrayList<String> nipDosen, namaDosen;
    TextView jenisJurnalJudul, sistem1, jenisJurnal1,tv_coba;
    TextView sistem2,sistem3,sistem4, jenisJurnal2,jenisJurnal3,jenisJurnal4;
    SearchableSpinner spinnerLokasi, spinnerKegiatan, spinnerSistem, spinnerJenis;
    SearchableSpinner spinnerSistem2, spinnerJenis2, spinnerSistem3, spinnerJenis3;
    SearchableSpinner spinnerSistem4, spinnerJenis4, spinnerDosen;
    Button submit;
    String tgl_skrg, hari_skrg;
    TextView stase, tanggalhari;
    TextView timePickerMulai, timePickerSelesai;
    TimePickerDialog timePickerDialog;

    private SessionHandler session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_jurnal);
//        jurnal = findViewById(R.id.jurnal);
//        potong = findViewById(R.id.potong);
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
        spinnerSistem4 = findViewById(R.id.sispen_4_isi);
        spinnerJenis4 = findViewById(R.id.penyakit_4_isi);
        spinnerSistem2 = findViewById(R.id.sispen_2_isi);
        spinnerJenis2 = findViewById(R.id.penyakit_2_isi);
        spinnerSistem3 = findViewById(R.id.sispen_3_isi);
        spinnerJenis3 = findViewById(R.id.penyakit_3_isi);
        spinnerDosen = findViewById(R.id.dosen_isi);
        jenisJurnalJudul = findViewById(R.id.jenis_jurnal_isi_jurnal);
        sistem1 = findViewById(R.id.sistem_1_ket);
        sistem2 = findViewById(R.id.sistem_2_ket);
        sistem3 = findViewById(R.id.sistem_3_ket);
        sistem4 = findViewById(R.id.sistem_4_ket);
        jenisJurnal1 = findViewById(R.id.jenis_1_ket);
        jenisJurnal2 = findViewById(R.id.jenis_2_ket);
        jenisJurnal3 = findViewById(R.id.jenis_3_ket);
        jenisJurnal4 = findViewById(R.id.jenis_4_ket);
        submit = findViewById(R.id.submit_isi_jurnal);
        tv_coba = findViewById(R.id.coba);
        stase = findViewById(R.id.stase_isi_jurnal);
        tanggalhari = findViewById(R.id.tgl_hari_isi_jurnal);
        timePickerMulai = findViewById(R.id.jam_mulai_isi_jurnal);
        timePickerSelesai = findViewById(R.id.jam_selesai_isi_jurnal);


        lokasi = new ArrayList<String>();
        id_lokasi = new ArrayList<String>();
        kegiatan = new ArrayList<String>();
        id_kegiatan = new ArrayList<String>();
        sistem_penyakit = new ArrayList<String>();
        id_sistemP = new ArrayList<String>();
        id_sistemK = new ArrayList<String>();
        sistem_keterampilan = new ArrayList<String>();
        keterampilan = new ArrayList<String>();
        id_keterampilan = new ArrayList<String>();
        penyakit = new ArrayList<String>();
        id_penyakit = new ArrayList<String>();
        nipDosen = new ArrayList<String>();
        namaDosen = new ArrayList<String>();
        tgl_skrg = new String();
        hari_skrg = new String();
        stase.setText(getIntent().getStringExtra("stase"));

        final String jenis_jurnal = getIntent().getStringExtra("jurnal");

        if(jenis_jurnal.equals("jurnal_penyakit")){
            jenisJurnalJudul.setText("Penyakit (Level-Sumber)");
            sistem1.setText("Sistem Penyakit - 1");
            jenisJurnal1.setText("Penyakit - 1");
            sistem2.setText("Sistem Penyakit - 2");
            jenisJurnal2.setText("Penyakit - 2");
            sistem3.setText("Sistem Penyakit - 3");
            jenisJurnal3.setText("Penyakit - 3");
            sistem4.setText("Sistem Penyakit - 4");
            jenisJurnal4.setText("Penyakit - 4");


        } else {
            jenisJurnalJudul.setText("Ketrampilan (Level-Sumber)");
            sistem1.setText("Sistem Ketrampilan - 1");
            jenisJurnal1.setText("Ketrampilan- 1");
            sistem2.setText("Sistem Ketrampilan - 2");
            jenisJurnal2.setText("Ketrampilan- 2");
            sistem3.setText("Sistem Ketrampilan - 3");
            jenisJurnal3.setText("Ketrampilan- 3");
            sistem4.setText("Sistem Ketrampilan - 4");
            jenisJurnal4.setText("Ketrampilan- 4");
        }

        getSpinner();
        getTanggal();


    }

    private void getSpinner(){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            session = new SessionHandler(getApplicationContext());
            User user = session.getUserDetails();
            String username = user.getUsername();
            String idStase = getIntent().getStringExtra("id_stase");
            request.put("id_stase",idStase);
            request.put("username", username);


        } catch (JSONException e) {
            e.printStackTrace();
        }
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

                    JSONArray dosenArray = response.getJSONArray("dosen");
                    for (int i = 0; i < dosenArray.length(); i++) {
                        JSONObject j = dosenArray.getJSONObject(i);
                        String nip = j.getString("nip");
                        String dosen = j.getString("nama");
                        String gelar = j.getString("gelar");
                        nipDosen.add(nip);
                        namaDosen.add(dosen+", "+gelar);
                    }
                    String[] strdosen = getStringArray(namaDosen);
                    ArrayAdapter<String> dsn = new ArrayAdapter<>(tambahJurnal.this, android.R.layout.simple_spinner_item,strdosen);
                    spinnerDosen.setAdapter(dsn);
                    spinnerDosen.setTitle("Pilih Dosen");



                    //spinner sistem penyakit 1
                    String jenis_penyakit = getIntent().getStringExtra("jurnal");
                    if(jenis_penyakit.equals("jurnal_penyakit")) {
                        JSONArray sispen1Array = response.getJSONArray("sistem");
                        id_sistemP.add("0");
                        sistem_penyakit.add("<KOSONG>");
                        for (int i = 0; i < sispen1Array.length(); i++) {
                            JSONObject j = sispen1Array.getJSONObject(i);
                            String id = j.getString("id");
                            String sispen = j.getString("sistem_penyakit");
                            id_sistemP.add(id);
                            sistem_penyakit.add(sispen);
                        }
                        String[] strsispen = getStringArray(sistem_penyakit);
                        final String[] idsispen = getStringArray(id_sistemP);
                        ArrayAdapter<String> a = new ArrayAdapter<>(tambahJurnal.this, android.R.layout.simple_spinner_item, strsispen);
                        a.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        spinnerSistem.setAdapter(a);
                        spinnerSistem.setTitle("Pilih Sistem Penyakit");
                        spinnerSistem2.setAdapter(a);
                        spinnerSistem2.setTitle("Pilih Sistem Penyakit");
                        spinnerSistem3.setAdapter(a);
                        spinnerSistem3.setTitle("Pilih Sistem Penyakit");
                        spinnerSistem4.setAdapter(a);
                        spinnerSistem4.setTitle("Pilih Sistem Penyakit");

                        spinnerSistem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                String id_s = idsispen[i];

                                getPenyakit(id_s, 1);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        spinnerSistem2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                String id_s = idsispen[i];

                                getPenyakit(id_s,2);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        spinnerSistem3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                String id_s = idsispen[i];

                                getPenyakit(id_s,3);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        spinnerSistem4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                String id_s = idsispen[i];

                                getPenyakit(id_s,4);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    } else {
                        JSONArray sistem1Array = response.getJSONArray("sistem_ket");
                        id_sistemK.add("0");
                        sistem_keterampilan.add("<KOSONG>");
                        for (int i = 0; i < sistem1Array.length(); i++) {
                            JSONObject j = sistem1Array.getJSONObject(i);
                            String id = j.getString("id");
                            String sisket = j.getString("sistem_ketrampilan");
                            id_sistemK.add(id);
                            sistem_keterampilan.add(sisket);
                        }
                        String[] strsisket = getStringArray(sistem_keterampilan);
                        final String[] idsisket = getStringArray(id_sistemK);
                        ArrayAdapter<String> a = new ArrayAdapter<>(tambahJurnal.this, android.R.layout.simple_spinner_item, strsisket);
                        a.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        spinnerSistem.setAdapter(a);
                        spinnerSistem.setTitle("Pilih Sistem Keterampilan");
                        spinnerSistem2.setAdapter(a);
                        spinnerSistem2.setTitle("Pilih Sistem Keterampilan");
                        spinnerSistem3.setAdapter(a);
                        spinnerSistem3.setTitle("Pilih Sistem Keterampilan");
                        spinnerSistem4.setAdapter(a);
                        spinnerSistem4.setTitle("Pilih Sistem Keterampilan");

                        spinnerSistem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                String id_s = idsisket[i];

                                getPenyakit(id_s,1);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        spinnerSistem2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                String id_s = idsisket[i];

                                getPenyakit(id_s,2);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        spinnerSistem3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                String id_s = idsisket[i];

                                getPenyakit(id_s,3);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        spinnerSistem4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                String id_s = idsisket[i];

                                getPenyakit(id_s,4);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }








                    timePickerMulai.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Calendar calendar = Calendar.getInstance();

                            /**
                             * Initialize TimePicker Dialog
                             */
                            timePickerDialog = new TimePickerDialog(tambahJurnal.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    /**
                                     * Method ini dipanggil saat kita selesai memilih waktu di DatePicker
                                     */
                                    timePickerMulai.setText(hourOfDay+":"+minute);
                                }
                            },
                                    /**
                                     * Tampilkan jam saat ini ketika TimePicker pertama kali dibuka
                                     */
                                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                                    DateFormat.is24HourFormat(tambahJurnal.this));

                                    /**
                                     * Cek apakah format waktu menggunakan 24-hour format
                                     */
//                                    DateFormat.is24HourFormat(this)

                            timePickerDialog.show();
                        }


                    });

                    timePickerSelesai.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Calendar calendar = Calendar.getInstance();
                            /**
                             * Initialize TimePicker Dialog
                             */
                            timePickerDialog = new TimePickerDialog(tambahJurnal.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    /**
                                     * Method ini dipanggil saat kita selesai memilih waktu di DatePicker
                                     */
                                    timePickerSelesai.setText(hourOfDay+":"+minute);
                                }
                            },
                                    /**
                                     * Tampilkan jam saat ini ketika TimePicker pertama kali dibuka
                                     */
                                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                                    DateFormat.is24HourFormat(tambahJurnal.this));

                            /**
                             * Cek apakah format waktu menggunakan 24-hour format
                             */
//                                    DateFormat.is24HourFormat(this)

                            timePickerDialog.show();

                        }
                    });

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if ((!(timePickerMulai.equals("00:00:00")||timePickerSelesai.equals("00:00:00") ||spinnerSistem.getSelectedItem().equals("<KOSONG>")))) {
                                User user = session.getUserDetails();
                                String username = user.getUsername();
                                String ang = username.substring(6, 8);
                                String angkatan = "20" + ang;
                                String jenis = getIntent().getStringExtra("jurnal");
                                String id_sis1 = new String();
                                String id_sis2 = new String();
                                String id_sis3 = new String();
                                String id_sis4 = new String();

                                if (jenis.equals("jurnal_penyakit")) {
                                    String[] sis1 = getStringArray(id_sistemP);
                                    id_sis1 = sis1[spinnerSistem.getSelectedItemPosition()];
                                    id_sis2 = sis1[spinnerSistem2.getSelectedItemPosition()];
                                    id_sis3 = sis1[spinnerSistem3.getSelectedItemPosition()];
                                    id_sis4 = sis1[spinnerSistem4.getSelectedItemPosition()];
//
                                } else {
                                    String[] sis1 = getStringArray(id_sistemK);
//                                String[] jenis1 = getStringArray(id_keterampilan);
                                    id_sis1 = sis1[spinnerSistem.getSelectedItemPosition()];
                                    id_sis2 = sis1[spinnerSistem2.getSelectedItemPosition()];
                                    id_sis3 = sis1[spinnerSistem3.getSelectedItemPosition()];
                                    id_sis4 = sis1[spinnerSistem4.getSelectedItemPosition()];
//                                id_jenis1 = jenis1[spinnerJenis.getSelectedItemPosition()];
//                                id_jenis2 = jenis1[spinnerJenis2.getSelectedItemPosition()];
//                                id_jenis3 = jenis1[spinnerJenis3.getSelectedItemPosition()];
//                                id_jenis4 = jenis1[spinnerJenis4.getSelectedItemPosition()];
//
                                }
                                String jenis2 = new String();
                                String jenis3 = new String();
                                String jenis4 = new String();
                                String jenis1 = spinnerJenis.getSelectedItem().toString();
                                if (id_sis2 != "0") {
                                    jenis2 = spinnerJenis2.getSelectedItem().toString();
                                }
                                if (id_sis3 != "0") {
                                    jenis3 = spinnerJenis3.getSelectedItem().toString();
                                }
                                if (id_sis4 != "0") {
                                    jenis4 = spinnerJenis4.getSelectedItem().toString();
                                }

                                String[] nip = getStringArray(nipDosen);
                                String niptmpl = nip[spinnerDosen.getSelectedItemPosition()];
                                String staseid = getIntent().getStringExtra("id_stase");
                                String stase = staseid.substring(6);
                                String jam_awal = timePickerMulai.getText().toString() + ":00";
                                String jam_akhir = timePickerSelesai.getText().toString() + ":00";
                                String[] lokasi = getStringArray(id_lokasi);
                                String id_lokasi = lokasi[spinnerLokasi.getSelectedItemPosition()];
                                String[] kegiatan = getStringArray(id_kegiatan);
                                String id_keg = kegiatan[spinnerKegiatan.getSelectedItemPosition()];
                                if (jam_awal.equals("00:00:00") || jam_akhir.equals("00:00:00") ||
                                        id_sis1.equals("0")) {
                                    tv_coba.setText("semua required kolom harus diisi");
                                } else {
                                    JSONObject request = new JSONObject();
                                    try {
                                        //Populate the request parameters
                                        request.put("jenis", jenis);
                                        request.put("username", username);
                                        request.put("angkatan", angkatan);
                                        request.put("hari", hari_skrg);
                                        request.put("tanggal", tgl_skrg);
                                        request.put("stase", stase);
                                        request.put("dosen", niptmpl);
                                        request.put("jam_awal", jam_awal);
                                        request.put("jam_akhir", jam_akhir);
                                        request.put("lokasi", id_lokasi);
                                        request.put("kegiatan", id_keg);
                                        request.put("id_sis1", id_sis1);
                                        request.put("id_sis2", id_sis2);
                                        request.put("id_sis3", id_sis3);
                                        request.put("id_sis4", id_sis4);
                                        request.put("id_jenis1", method(jenis1));
                                        request.put("id_jenis2", method(jenis2));
                                        request.put("id_jenis3", method(jenis3));
                                        request.put("id_jenis4", method(jenis4));
                                        Intent intent = new Intent(tambahJurnal.this, IsiJurnalDetail.class);
                                        intent.putExtra(mainIsiJurnal.KEY_ID, stase);
                                        startActivity(intent);
                                        finish();


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                                            (submitURL, request, new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {

                                                }
                                            }, new Response.ErrorListener() {

                                                @Override
                                                public void onErrorResponse(VolleyError error) {

                                                }
                                            });

                                    // Access the RequestQueue through your singleton class.
                                    MySingleton.getInstance(tambahJurnal.this).addToRequestQueue(jsArrayRequest);

                                }
                            } else {
                                Toast.makeText(tambahJurnal.this, "data belum lengkap",Toast.LENGTH_SHORT).show();
                            }





//                            Intent intent = new Intent(DaftarKegiatan.this,TampilanDaftarKegiatan.class);
//                            intent.putExtra("nim",filterNim);
//                            intent.putExtra("stase",filterIdStase);
//                            intent.putExtra("jenis_jurnal",filterJenisJurnal);
//                            intent.putExtra("status",filterStatus);
//                            intent.putExtra("tanggal",filterTanggal);
//                            startActivity(intent);

                        }
                    });

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

    private void getPenyakit(String sispen, final int key){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            String jenis_jurnal = getIntent().getStringExtra("jurnal");
            request.put("jenis_jurnal", jenis_jurnal);
            request.put("id_sistemP", sispen);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonpenyakit = new JsonObjectRequest(
                spinnerURL,request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String jenis_jurnal = getIntent().getStringExtra("jurnal");
                    if(jenis_jurnal.equals("jurnal_penyakit")) {
                        //spiner penyakit
                        JSONArray penyakitArray = response.getJSONArray("jenis");
                        penyakit.clear();
                        for (int i = 0; i < penyakitArray.length(); i++) {
                            JSONObject j = penyakitArray.getJSONObject(i);
                            String id = j.getString("id");
                            String pen = j.getString("penyakit");
                            id_penyakit.add(id);
                            penyakit.add(pen+" ("+j.getString("skdi_level")+"-"+j.getString("sumber")+") ");
                        }
                        String[] str = getStringArray(penyakit);
                        ArrayAdapter<String> p = new ArrayAdapter<>(tambahJurnal.this, android.R.layout.simple_spinner_item, str);
                        if(key == 1){
                            spinnerJenis.setAdapter(p);
                            spinnerJenis.setTitle("Pilih Penyakit");}
                        else if (key==2){
                            spinnerJenis2.setAdapter(p);
                            spinnerJenis2.setTitle("Pilih Penyakit");
                        } else if (key == 3){
                            spinnerJenis3.setAdapter(p);
                            spinnerJenis3.setTitle("Pilih Penyakit");
                        } else {
                            spinnerJenis4.setAdapter(p);
                            spinnerJenis4.setTitle("Pilih Penyakit");
                        }

                    } else {
                        //spiner keterampilan
                        JSONArray keterampilanArray = response.getJSONArray("jenis");
                        keterampilan.clear();
                        for (int i = 0; i < keterampilanArray.length(); i++) {
                            JSONObject j = keterampilanArray.getJSONObject(i);
                            String id = j.getString("id");
                            String ketr = j.getString("ketrampilan");
                            id_keterampilan.add(id);
                            keterampilan.add(ketr+" ("+j.getString("skdi_level")+"-"+j.getString("sumber")+") ");
                        }
                        String[] str = getStringArray(keterampilan);
                        ArrayAdapter<String> k = new ArrayAdapter<>(tambahJurnal.this, android.R.layout.simple_spinner_item, str);
                        if(key==1){
                        spinnerJenis.setAdapter(k);
                        spinnerJenis.setTitle("Pilih Ketrampilan");}
                        else if (key==2){
                            spinnerJenis2.setAdapter(k);
                            spinnerJenis2.setTitle("Pilih Ketrampilan");
                        }else if (key==3){
                            spinnerJenis3.setAdapter(k);
                            spinnerJenis3.setTitle("Pilih Ketrampilan");
                        } else {
                            spinnerJenis4.setAdapter(k);
                            spinnerJenis4.setTitle("Pilih Ketrampilan");
                        }

                    }

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
    private void getTanggal(){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            session = new SessionHandler(getApplicationContext());
            User user = session.getUserDetails();
            String username = user.getUsername();
            String id_stase = getIntent().getStringExtra("id_stase");
            request.put("username",username);
            request.put("id_stase",id_stase);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonstase = new JsonObjectRequest(
                tanggalURL,request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //spiner lokasi
                    JSONArray skrgArray = response.getJSONArray("tgl");
                    for (int i = 0; i < skrgArray.length(); i++) {
                        JSONObject j = skrgArray.getJSONObject(i);
                        String skrg = j.getString("tgl_isi");
                        String skrg_date = j.getString("tgl_isi_date");
                        String jml = j.getString("jml_hari_skrg");
                        String jmlstase = j.getString("jml_hari_stase");
                        tanggalhari.setText(skrg+" / Hari ke-"+jml+" dari "+jmlstase+" hari masa kepaniteraan(stase)");
                        tgl_skrg = skrg_date;
                        hari_skrg = jml;
//                        tgl_isi.setText(skrg_date);
                    }


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
    public String method(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 6) == '(') {
            str = str.substring(0, str.length() - 6);
        } else if(str != null && str.length() > 0 && str.charAt(str.length() - 7) == '('){
            str = str.substring(0, str.length() - 7);
        }
        return str;
    }



}