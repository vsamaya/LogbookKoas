package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ri_edit extends AppCompatActivity {
    private DatePickerDialog mDatePickerDialog;
    TextView tv_stase, tv_lama;
    Button  simpan;
    EditText tgl;
    SearchableSpinner spinnerDosen;
    final ArrayList<String> Arrspin = new ArrayList<String>();
    final ArrayList<String> Arrspin1 = new ArrayList<String>();
    String[] lspin;
    final String urlspin = "http://192.168.1.9/logbook/spinner_edit_rotasi.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ri_edit);
        tv_stase = findViewById(R.id.stase);
        tv_lama = findViewById(R.id.lama);
        simpan = findViewById(R.id.simpan);
        tgl = findViewById(R.id.tgl);
        spinnerDosen = findViewById(R.id.appr);
        Intent intent = getIntent();
        final String stase = intent.getStringExtra("stase");
        final String lama = intent.getStringExtra("lama");
        final String tgl_mulai = intent.getStringExtra("tgl_mulai");
        tv_stase.setText(stase);
        tv_lama.setText(lama);
        getApproval();
        setDateTimeField();
        tgl.setOnTouchListener(new View.OnTouchListener() {
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

                tgl.setText(fdate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private void getApproval() {
        JSONObject request = new JSONObject();
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                urlspin, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray spin = response.getJSONArray("nmapproval");
                    for (int i = 0; i < spin.length(); i++) {
                        JSONObject j = spin.getJSONObject(i);
                        // JSONObject k = kota1.getJSONObject(i);

                        String nama = j.getString("nama");
                        String username = j.getString("username");

                        //   String bgnhsl= k.getString("bagian");

                        //   MyArrList.add(bgnhsl);
                        //  list_data.add(map);
                        Arrspin.add(nama);
                        Arrspin1.add(username);
                    }

                    lspin = getStringArray(Arrspin);
                    //   idcb.setText(idilm[2]);
                    //   bagiancb.setText(bgnilmu[1]);
                    ArrayAdapter<String> bgnarr = new ArrayAdapter<String>(ri_edit.this, android.R.layout.simple_spinner_item, lspin);
                    spinnerDosen.setAdapter(bgnarr);
                    spinnerDosen.setTitle("Pilih Bagian");

                    // String bgn= MyArrList.get(0);
                    // spin.setSelection(getIndex(spin,bgnhsl[0]));
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(json);
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

}
