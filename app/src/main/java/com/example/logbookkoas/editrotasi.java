package com.example.logbookkoas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.HashMap;
import java.util.Locale;

public class editrotasi extends AppCompatActivity {
    TextView stase, hari;
    EditText tgl;
    String rotasi1, tgl1, dosen1, status1, rotasi2, tgl2, dosen2, status2, rotasi3, tgl3, dosen3, status3, rotasi4, tgl4,
            dosen4, status4, rotasi5, tgl5, dosen5, status5, rotasi6, tgl6, dosen6, status6, no, nostase,
            sts, hr;
    SessionHandler sessionHandler;
    private SimpleDateFormat dateFormatter;
    SearchableSpinner searchableSpinner;
    private ImageView btndate;
    String[] lspin;
    TextView tvdateResult;
    ArrayList<HashMap<String, String>> MyArr = new ArrayList<HashMap<String, String>>();
    final ArrayList<String> Arrspin = new ArrayList<String>();
    final ArrayList<String> Arrspin1 = new ArrayList<String>();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat convert = new SimpleDateFormat("dd MMM yyyy");
    final String urledt = "http://192.168.1.7/logbook/simpaneditrts.php";
    private String url_username = "http://192.168.1.7/logbook/spinner_edit_username.php";
    final String urlsem = "http://192.168.1.7/logbook/rotasi_internal.php";
    final String urlspin = "http://192.168.1.7/logbook/spinner_edit_rotasi.php";
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editrotasi);
        stase = findViewById(R.id.editinterstase);
        hari = findViewById(R.id.editlm);
        String myformat = "yyyy-MM-dd";
        dateFormatter = new SimpleDateFormat(myformat, Locale.US);
        btndate=findViewById(R.id.imgbtnedit);
        tgl = findViewById(R.id.tanggaledit);
        searchableSpinner = findViewById(R.id.approvaledit);
        tvdateResult = (TextView) findViewById(R.id.tanggaledit);
        submit = findViewById(R.id.simpanedit);
        getApproval();
     //   getUsername();
        Intent i = getIntent();
        sts = i.getStringExtra("stase");
        hr = i.getStringExtra("hari");
        final String idrot = i.getStringExtra("id");
        final String idst = i.getStringExtra("idst");
        final String idur = i.getStringExtra("id_internal");
        rotasi1=idst+"1";
        tgl1=i.getStringExtra("tgl1");
        dosen1=i.getStringExtra("dosen1");
        status1=i.getStringExtra("status1");
        rotasi2=idst+"2";
        tgl2=i.getStringExtra("tgl2");
        dosen2=i.getStringExtra("dosen2");
        status2=i.getStringExtra("status2");
        rotasi3=idst+"3";;
        tgl3=i.getStringExtra("tgl3");
        dosen3=i.getStringExtra("dosen3");
        status3=i.getStringExtra("status3");
        rotasi4=idst+"4";;
        tgl4=i.getStringExtra("tgl4");
        dosen4=i.getStringExtra("dosen4");
        status4=i.getStringExtra("status4");
        rotasi5=idst+"5";;
        tgl5=i.getStringExtra("tgl5");
        dosen5=i.getStringExtra("dosen5");
        status5=i.getStringExtra("status5");
        rotasi6=idst+"6";
        tgl6=i.getStringExtra("tgl6");
        dosen6=i.getStringExtra("dosen6");
        status6=i.getStringExtra("status6");
        stase.setText(sts);
        hari.setText(hr);
        hari.append(" hari");

        final String idr = idrot.substring(4, 5);
        btndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });


     //   Toast.makeText(this, nospin[0], Toast.LENGTH_SHORT).show();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dosena= new String();
                String[] nospin = getStringArray(Arrspin1);

                if (idr.equals("1")) {

                    String tgla = tgl.getText().toString();
                    dosena=nospin[searchableSpinner.getSelectedItemPosition()];
                 //   Toast.makeText(editrotasi.this, dosena, Toast.LENGTH_SHORT).show();
                        simpan(idur,idst,rotasi1, tgla, dosena, rotasi2, tgl2, dosen2,
                                rotasi3, tgl3, dosen3, rotasi4, tgl4, dosen4,
                                rotasi5, tgl5, dosen5,  rotasi6, tgl6, dosen6);

                } else if (idr.equals("2")) {
                    String tgla = tgl.getText().toString();
                    dosena=nospin[searchableSpinner.getSelectedItemPosition()];
                  //  Toast.makeText(editrotasi.this, dosena, Toast.LENGTH_SHORT).show();
                      simpan(idur,idst,rotasi1, tgl1, dosen1,  rotasi2, tgla, dosena,
                               rotasi3, tgl3, dosen3, rotasi4, tgl4, dosen4,
                              rotasi5, tgl5, dosen5,  rotasi6, tgl6, dosen6);
                } else if (idr.equals("3")) {

                    String tgla = tgl.getText().toString();
                    dosena=nospin[searchableSpinner.getSelectedItemPosition()];
                   // Toast.makeText(editrotasi.this, dosena, Toast.LENGTH_SHORT).show();
                       simpan(idur,idst,rotasi1, tgl1, dosen1, rotasi2, tgl2, dosen2,
                            rotasi3, tgla, dosena,  rotasi4, tgl4, dosen4,
                              rotasi5, tgl5, dosen5, rotasi6, tgl6, dosen6);

                } else if (idr.equals("4")) {
                    String tgla = tgl.getText().toString();
                    dosena=nospin[searchableSpinner.getSelectedItemPosition()];
                 //   Toast.makeText(editrotasi.this, dosena, Toast.LENGTH_SHORT).show();
                      simpan(idur,idst,rotasi1, tgl1, dosen1,  rotasi2, tgl2, dosen2,
                             rotasi3, tgl3, dosen3, rotasi4, tgla, dosena,
                             rotasi5, tgl5, dosen5, rotasi6, tgl6, dosen6);

                } else if (idr.equals("5")) {

                    String tgla = tgl.getText().toString();
                    dosena=nospin[searchableSpinner.getSelectedItemPosition()];
                  //  Toast.makeText(editrotasi.this, dosena, Toast.LENGTH_SHORT).show();
                        simpan(idur,idst,rotasi1, tgl1, dosen1, rotasi2, tgl2, dosen2,
                             rotasi3, tgl3, dosen3,  rotasi4, tgl4, dosen4,
                             rotasi5, tgla, dosena, rotasi6, tgl6, dosen6);
                } else if (idr.equals("6")) {

                    String tgla = tgl.getText().toString();
                    dosena=nospin[searchableSpinner.getSelectedItemPosition()];
                  //  Toast.makeText(editrotasi.this, dosena, Toast.LENGTH_SHORT).show();
                      simpan(idur,idst,rotasi1, tgl1, dosen1,  rotasi2, tgl2,dosen2,
                         rotasi3, tgl3, dosen3,rotasi4, tgl4, dosen4,
                         rotasi5, tgl5, dosen5, rotasi6, tgla, dosena);
                }
            }
        });

    }
    private void showDateDialog() {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tvdateResult.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
    private void simpan(String idur, String idrot,String rotasi1, String tgl1, String dosen1,  String rotasi2, String tgl2, String dosen2,
                        String rotasi3, String tgl3, String dosen3, String rotasi4, String tgl4, String dosen4,
                        String rotasi5, String tgl5, String dosen5, String rotasi6, String tgl6, String dosen6) {
        JSONObject request = new JSONObject();
        try {
            sessionHandler = new SessionHandler(getApplicationContext());
            User user = sessionHandler.getUserDetails();
            String username = user.getUsername();
            request.put("username", username);
            request.put("id", idur);
            request.put("stase",idrot);
            request.put("rotasi1", rotasi1);
            request.put("tgl1", tgl1);
            request.put("dosen1", dosen1);
            request.put("rotasi2", rotasi2);
            request.put("tgl2", tgl2);
            request.put("dosen2", dosen2);
            request.put("rotasi3", rotasi3);
            request.put("tgl3", tgl3);
            request.put("dosen3", dosen3);
            request.put("rotasi4", rotasi4);
            request.put("tgl4", tgl4);
            request.put("dosen4", dosen4);
            request.put("rotasi5", rotasi5);
            request.put("tgl5", tgl5);
            request.put("dosen5", dosen5);
            request.put("rotasi6", rotasi6);
            request.put("tgl6", tgl6);
            request.put("dosen6", dosen6);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urledt, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(editrotasi.this, response.getString("response"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(editrotasi.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }


    private void getApproval() {
        JSONObject request = new JSONObject();
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                urlspin, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {Arrspin.clear();
                    Arrspin1.clear();
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
                    ArrayAdapter<String> bgnarr = new ArrayAdapter<String>(editrotasi.this, android.R.layout.simple_spinner_item, lspin);
                    searchableSpinner.setAdapter(bgnarr);
                    searchableSpinner.setTitle("Pilih Bagian");

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
    private void getUsername() {
        JSONObject request = new JSONObject();
        try{
            String []id=getStringArray(Arrspin);
            request.put("nama", id[searchableSpinner.getSelectedItemPosition()]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                url_username, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Arrspin1.clear();
                    JSONArray spin = response.getJSONArray("userappr");
                    for (int i = 0; i < spin.length(); i++) {
                        JSONObject j = spin.getJSONObject(i);
                        // JSONObject k = kota1.getJSONObject(i);


                        String username = j.getString("username");

                        //   String bgnhsl= k.getString("bagian");

                        //   MyArrList.add(bgnhsl);
                        //  list_data.add(map);

                        Arrspin1.add(username);
                    }


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

