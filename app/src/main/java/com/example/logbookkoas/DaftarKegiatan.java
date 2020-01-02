package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class DaftarKegiatan extends AppCompatActivity {

    String spinnerURL = "http://192.168.200.31/logbook/spinnerkegiatandosen.php";
    Spinner spinnerJenisJurnal, spinnerStatus;
    SearchableSpinner spinnerStase, spinnerNamaMahasiswa;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    String[] jenis_jurnal ={"Jurnal Penyakit", "Jurnal Keterampilan"};
    String[] status ={"Semua status","Approved", "Unapproved"};
    Button btnfilter;
    ArrayList<String> idStase, Stase, namaMahasiswa, nimMahasiswa;

    private ProgressDialog pDialog;

    EditText datePicker;
    String filterNim, filterIdStase, filterJenisJurnal, filterStatus, filterTanggal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kegiatan);
        filterJenisJurnal = new String();
        filterStatus = new String();
        namaMahasiswa = new ArrayList<String>();
        nimMahasiswa = new ArrayList<String>();


        btnfilter = findViewById(R.id.btn_filter);
        spinnerStatus = findViewById(R.id.spinner_status);
        spinnerJenisJurnal = findViewById(R.id.spinner_jnsjurnal);
        spinnerStase = findViewById(R.id.spinner_stase);
        spinnerNamaMahasiswa = findViewById(R.id.spinner_nama_mahasiswa);

        Stase = new ArrayList<String>();
        idStase = new ArrayList<String>();
        datePicker = findViewById(R.id.spinner_tanggal);
        getArrayStase();


    }



    private void getArrayStase(){
        JsonObjectRequest jsonstase = new JsonObjectRequest(
                spinnerURL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonStase = response.getJSONArray("stase");
                    Stase.add("Semua stase");
                    idStase.add("all");
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
                    JSONArray jsonmhs = response.getJSONArray("nama");
                    namaMahasiswa.add("Semua mahasiswa");
                    nimMahasiswa.add("all");
                    for (int i = 0; i < jsonmhs.length(); i++) {
                        JSONObject j = jsonmhs.getJSONObject(i);
                        String nim = j.getString("nim");
                        String nama = j.getString("nama");
                        nimMahasiswa.add(nim);
                        namaMahasiswa.add(nama);
                    }
                    String[] strnama = getStringArray(namaMahasiswa);
                    ArrayAdapter<String> d = new ArrayAdapter<>(DaftarKegiatan.this, android.R.layout.simple_spinner_item,strnama);
                    spinnerNamaMahasiswa.setAdapter(d);
                    spinnerNamaMahasiswa.setTitle("Pilih Mahasiswa");
                    ArrayAdapter<String> a = new ArrayAdapter<>(DaftarKegiatan.this, android.R.layout.simple_spinner_item,jenis_jurnal);
                    a.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spinnerJenisJurnal.setAdapter(a);
                    ArrayAdapter<String> b = new ArrayAdapter<>(DaftarKegiatan.this, android.R.layout.simple_spinner_item, status);
                    b.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spinnerStatus.setAdapter(b);


                    datePicker.setText("Semua Tanggal");
                    datePicker.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Calendar calendar = Calendar.getInstance();
                            final int year = calendar.get(Calendar.YEAR);
                            final int month= calendar.get(Calendar.MONTH);
                            final int day = calendar.get(Calendar.DAY_OF_MONTH);
//                            datePicker = view.findViewById(R.id.filter_tanggal);
                            DatePickerDialog dpDialog = new DatePickerDialog(DaftarKegiatan.this, R.style.datepicker,
                                    new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month1, int day) {
                                            month1 = month1+1;
                                            String date = year+"-"+month1+"-"+day;
                                            datePicker.setText(date);
                                        }
                                    },year,month,day);
                            dpDialog.show();
                        }
                    });



                    btnfilter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            filterTanggal = datePicker.getText().toString().toLowerCase();
                            if(filterTanggal.equals("") || filterTanggal.equals("semua tanggal")){
                                filterTanggal="semua tanggal";}
                            final String[] NIM = getStringArray(nimMahasiswa);
                            filterNim = NIM[spinnerNamaMahasiswa.getSelectedItemPosition()];
                            final String[] id_stase = getStringArray(idStase);
                            filterIdStase = id_stase[spinnerStase.getSelectedItemPosition()];
                            filterJenisJurnal = spinnerJenisJurnal.getSelectedItem().toString().trim();
                            filterStatus = spinnerStatus.getSelectedItem().toString().trim();
                            Intent intent = new Intent(DaftarKegiatan.this,TampilanDaftarKegiatan.class);
                            intent.putExtra("nim",filterNim);
                            intent.putExtra("stase",filterIdStase);
                            intent.putExtra("jenis_jurnal",filterJenisJurnal);
                            intent.putExtra("status",filterStatus);
                            intent.putExtra("tanggal",filterTanggal);
                            startActivity(intent);

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


