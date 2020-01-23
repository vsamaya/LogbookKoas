package com.example.logbookkoas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ri_tambah extends AppCompatActivity {
    private DatePickerDialog mDatePickerDialog;
    TextView tv_stase, tv_lama;
    Button  simpan;
    EditText datepicker;
    SearchableSpinner spinnerDosen;
    private static final String KEY_USERNAME = "username";
    private static final String KEY_TGL = "tgl";
    private static final String KEY_DOSEN = "dosen";
    private static final String KEY_ID = "id";
    private static final String KEY_ID_INTERNAL = "id_internal";
    private static final String KEY_ROTASI = "rotasi";
    final ArrayList<String> Arrspin = new ArrayList<String>();
    final ArrayList<String> Arrspin1 = new ArrayList<String>();
    SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat convert = new SimpleDateFormat("yyyy-MM-dd");
    ArrayList<String> nipDosen, namaDosen;
    String[] lspin;
    private SessionHandler session;
    String spinnerURL = "http://192.168.1.7/logbook/spinner_edit_rotasi.php";
    String simpanedit = "http://192.168.1.7/logbook/simpaneditrotasi.php";
    String tambahedit = "http://192.168.1.7/logbook/tambahRotasi.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ri_edit);
        tv_stase = findViewById(R.id.stase);
        tv_lama = findViewById(R.id.lama);
        simpan = findViewById(R.id.simpan);
        datepicker = findViewById(R.id.tgl);
        spinnerDosen = findViewById(R.id.appr);

        nipDosen = new ArrayList<String>();
        namaDosen = new ArrayList<String>();

        Intent intent = getIntent();
        final String stase = intent.getStringExtra("stase");
        final String lama = intent.getStringExtra("lama");
        final String id_internal = intent.getStringExtra("id_internal");
        final String id = intent.getStringExtra("id");
        final String rotasi = intent.getStringExtra("rotasi");
        tv_stase.setText(stase);
        tv_lama.setText(lama);
        tambah();
        getSpinner();
        setDateTimeField();
        datepicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDatePickerDialog.show();
                return false;
            }
        });



    }
    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);

                datepicker.setText(fdate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }


    private void getSpinner(){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            Intent intent = getIntent();
            final String id = intent.getStringExtra("id");
            request.put("id_stase",id);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonstase = new JsonObjectRequest(
                spinnerURL, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray dosenArray = response.getJSONArray("nmapproval");
                    for (int i = 0; i < dosenArray.length(); i++) {
                        JSONObject j = dosenArray.getJSONObject(i);
                        String nip = j.getString("username");
                        String dosen = j.getString("nama");
                        nipDosen.add(nip);
                        namaDosen.add(dosen);
                    }
                    String[] strdosen = getStringArray(namaDosen);
                    ArrayAdapter<String> dsn = new ArrayAdapter<>(ri_tambah.this, android.R.layout.simple_spinner_item,strdosen);
                    spinnerDosen.setAdapter(dsn);
                    spinnerDosen.setTitle("Pilih Dosen");


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

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sdate = datepicker.getText().toString();
                if(sdate.trim().length() == 0){
                    Toast.makeText(ri_tambah.this, "Maaf data belum lengkap",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    JSONObject request = new JSONObject();
                    try {
                        session = new SessionHandler(getApplicationContext());
                        User user = session.getUserDetails();
                        String username = user.getUsername();
                        Intent intent = getIntent();
                        String id_internal = intent.getStringExtra("id_internal");
                        String id = intent.getStringExtra("id");
                        String rotasi = intent.getStringExtra("rotasi");
                        String tgl1 = datepicker.getText().toString();
                        Date tgl2 = dateFormat.parse(tgl1);
                        String tanggal = convert.format(tgl2);
                        String[] nip = getStringArray(nipDosen);
                        String niptmpl = nip[spinnerDosen.getSelectedItemPosition()];

                        request.put(KEY_USERNAME, username);
                        request.put(KEY_TGL, tanggal);
                        request.put(KEY_DOSEN, niptmpl);
                        request.put(KEY_ID, id);
                        request.put(KEY_ID_INTERNAL, id_internal);
                        request.put(KEY_ROTASI, rotasi);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                            (Request.Method.POST, simpanedit, request, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        if (response.getInt("pesan") == 1) {
                                            finish();

                                        } else {
                                            Toast.makeText(ri_tambah.this, "Gagal",
                                                    Toast.LENGTH_LONG).show();

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {


                                }
                            });
                    // Access the RequestQueue through your singleton class.
                    MySingleton.getInstance(ri_tambah.this).addToRequestQueue(jsArrayRequest);
                }
            }
        });

        MySingleton.getInstance(ri_tambah.this).addToRequestQueue(jsonstase);

    }

    public void tambah() {

            JSONObject request = new JSONObject();
            try {
                //Populate the request parameters
                Intent intent = getIntent();
                session = new SessionHandler(getApplicationContext());
                User user = session.getUserDetails();
                String id = intent.getStringExtra("id");
                String username = user.getUsername();
                request.put(KEY_USERNAME, username);
                request.put(KEY_ID, id);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                    (Request.Method.POST, tambahedit, request, new Response.Listener<JSONObject>() {
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



    }


    private static String[] getStringArray(ArrayList<String> arr) {
        // declaration and initialise String Array
        String[] str = new String[arr.size()];

        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++) {

            // Assign each value to String array
            str[j] = arr.get(j);
        }

        return str;
    }

    private int getIndex(SearchableSpinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

}
