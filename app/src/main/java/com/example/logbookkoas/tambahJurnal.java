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

public class tambahJurnal extends AppCompatActivity {
    String spinnerURL = "http://192.168.1.6/logbook/spinnerTambahJurnal.php";
    TextView jurnal,potong;
    ArrayList<String> lokasi, id_lokasi, kegiatan, id_kegiatan;
    ArrayList<String> sistem_penyakit, id_sistemP,penyakit,id_penyakit;
    ArrayList<String> sistem_keterampilan, id_sistemK,keterampilan,id_keterampilan;
    TextView jenisJurnalJudul, sistem1, jenisJurnal1,tv_coba;
    SearchableSpinner spinnerLokasi, spinnerKegiatan, spinnerSistem, spinnerJenis;
    SearchableSpinner spinnerSistem2, spinnerJenis2, spinnerSistem3, spinnerJenis3;
    SearchableSpinner spinnerSistem4, spinnerJenis4;
    Button submit;
    TextView stase, tanggalhari;
    EditText timePickerMulai, timePickerSelesai;
    TimePickerDialog timePickerDialog;

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
        spinnerSistem4 = findViewById(R.id.sispen_4_isi);
        spinnerJenis4 = findViewById(R.id.penyakit_4_isi);
        spinnerSistem2 = findViewById(R.id.sispen_2_isi);
        spinnerJenis2 = findViewById(R.id.penyakit_2_isi);
        spinnerSistem3 = findViewById(R.id.sispen_3_isi);
        spinnerJenis3 = findViewById(R.id.penyakit_3_isi);
        jenisJurnalJudul = findViewById(R.id.jenis_jurnal_isi_jurnal);
        sistem1 = findViewById(R.id.sistem_1_ket);
        jenisJurnal1 = findViewById(R.id.jenis_1_ket);
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
        stase.setText(getIntent().getStringExtra("stase"));
        if(jenis_jurnal.equals("jurnal_penyakit")){
            jenisJurnalJudul.setText("Penyakit (Level-Sumber)");
            sistem1.setText("Sistem Penyakit - 1");
            jenisJurnal1.setText("Penyakit - 1");


        } else {
            jenisJurnalJudul.setText("Ketrampilan (Level-Sumber)");
            sistem1.setText("Sistem Ketrampilan - 1");
            jenisJurnal1.setText("Ketrampilan- 1");
        }

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
                        spinnerSistem2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                String id_s = idsisket[i];

                                getPenyakit(id_s,3);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        spinnerSistem2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String lokasi = spinnerLokasi.getSelectedItem().toString();
                            tv_coba.append(lokasi+" ");
                            String keg = spinnerKegiatan.getSelectedItem().toString();
                            tv_coba.append(keg+" ");
                            String Sis1 = spinnerSistem.getSelectedItem().toString();
                            tv_coba.append(Sis1+" ");

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