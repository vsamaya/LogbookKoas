package com.example.logbookkoas;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.PatternSyntaxException;

public class editprofil extends AppCompatActivity {
    private Spinner spinnerkota, spinnerprop,spinnerwali,spinnerkota1,spinnerkota2;
    Button buttonSubmit;
    ProgressDialog pDialog;
    public static final String UPLOAD_URL = "http://192.168.1.11/upload.php";
    public static final String KOTA_URL = "http://192.168.1.11/getKota.php";
    public static final String KOTA_URL1 = "http://192.168.1.11/getKota1.php";
    public static final String KOTA_URL2 = "http://192.168.1.11/getKota2.php";
    public static final String UPLOAD_KEY = "image";
    private String propName;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TextView tvDateResult;
    private int PICK_IMAGE_REQUEST = 1;
    private TextView ganti;
    private ImageView imageView, dateimg;
    private TextView hapus;
    private EditText btDatePicker;
    private Bitmap bitmap;
    private Uri filePath;
    // array list for spinner adapter
    ArrayList<String> kotaList1,kotalist2,kotalist3;

    private void showDateDialog() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tvDateResult.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofil);
        String myformat = "yyyy-MM-dd";
        dateFormatter = new SimpleDateFormat(myformat, Locale.US);
        btDatePicker = (EditText) findViewById(R.id.tanggal);
        tvDateResult = (TextView) findViewById(R.id.tanggal);
        spinnerprop = (Spinner) findViewById(R.id.propuser);
        spinnerkota = (Spinner) findViewById(R.id.kotauser);
        dateimg = (ImageView) findViewById(R.id.imgbtn);
        ganti = (TextView) findViewById(R.id.ganti);
        hapus = (TextView) findViewById(R.id.hapus);
        spinnerkota1=(Spinner)findViewById(R.id.kotaal);
        spinnerkota2=(Spinner)findViewById(R.id.spinkotawali);
        kotaList1 = new ArrayList<String>();
        kotalist2=new ArrayList<String>();
        kotalist3=new ArrayList<String>();
        imageView = findViewById(R.id.imgprofil);
        spinnerwali = (Spinner) findViewById(R.id.spinwali);
        final Spinner spin_prop = findViewById(R.id.propoal);

        dateimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
       spinnerwali.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  kotalist3.clear();
                  String prov = spinnerwali.getSelectedItem().toString();
                  getKota2(prov);

           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });


        spin_prop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kotalist2.clear();
                String prov = spin_prop.getSelectedItem().toString();
               getKota1(prov);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerprop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                kotaList1.clear();
                // Refresh Spinner
                String prov = spinnerprop.getSelectedItem().toString();
                Toast.makeText(editprofil.this, prov, Toast.LENGTH_SHORT).show();
                getKota(prov);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ganti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void getKota(String provinsi) {
        JSONObject request = new JSONObject();
        try {
            String prov = provinsi;
            request.put("provinsi", prov);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                KOTA_URL, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray kota = response.getJSONArray("kota");
                    for (int i = 0; i < kota.length(); i++) {
                        JSONObject j = kota.getJSONObject(i);
                        String namaKota = j.getString("kota");
                        kotaList1.add(namaKota);

                    }
                    String[] strKota = getStringArray(kotaList1);
                    ArrayAdapter<String> kot4 = new ArrayAdapter<String>(editprofil.this, android.R.layout.simple_spinner_dropdown_item, strKota);
                    spinnerkota.setAdapter(kot4);



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
    private void getKota1(String provinsi) {
        JSONObject request = new JSONObject();
        try {
            String prov = provinsi;
            request.put("provinsi", prov);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                KOTA_URL1, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray kota = response.getJSONArray("kota1");
                    for (int i = 0; i < kota.length(); i++) {
                        JSONObject j = kota.getJSONObject(i);
                        String namaKota = j.getString("kota");
                        kotalist2.add(namaKota);

                    }

                    String[] strKota1=getStringArray(kotalist2);
                    ArrayAdapter<String> kot41 = new ArrayAdapter<String>(editprofil.this, android.R.layout.simple_spinner_dropdown_item, strKota1);
                    spinnerkota1.setAdapter(kot41);




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
    private void getKota2(String provinsi) {
        JSONObject request = new JSONObject();
        try {
            String prov = provinsi;
            request.put("provinsi", prov);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                KOTA_URL2, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray kota = response.getJSONArray("kota2");
                    for (int i = 0; i < kota.length(); i++) {
                        JSONObject j = kota.getJSONObject(i);
                        String namaKota = j.getString("kota");
                        kotalist3.add(namaKota);

                    }

                    String[] strKota2=getStringArray(kotalist3);
                    ArrayAdapter<String> kot41 = new ArrayAdapter<String>(editprofil.this, android.R.layout.simple_spinner_dropdown_item, strKota2);
                    spinnerkota2.setAdapter(kot41);




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
        String str[] = new String[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            str[i] = arr.get(i);
        }
        return str;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


}



