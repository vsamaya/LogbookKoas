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
    final String urledt = "http://192.168.1.9/logbook/simpaneditrts.php";
    private String url_username = "http://192.168.1.9/logbook/spinner_edit_username.php";
    final String urlsem = "http://192.168.1.9/logbook/rotasi_internal.php";
    final String urlspin = "http://192.168.1.9/logbook/spinner_edit_rotasi.php";
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


       /* rotasi1= MyArr.get(0).get("rotasi1");
        dosen1=MyArr.get(0).get("dosen1");
        tgl1=MyArr.get(0).get("tgl1");
        status1=MyArr.get(0).get("status1");
        rotasi2=MyArr.get(1).get("rotasi2");
        tgl2=MyArr.get(1).get("tgl2");
        dosen2=MyArr.get(1).get("dosen2");
        status2=MyArr.get(1).get("status2");
        rotasi3=MyArr.get(2).get("rotasi3");
        tgl3=MyArr.get(2).get("tgl3");
        dosen3=MyArr.get(2).get("dosen3");
        status3=MyArr.get(2).get("status3");
        rotasi4= MyArr.get(3).get("rotasi4");
        tgl4=MyArr.get(3).get("tgl4");
        dosen4=MyArr.get(3).get("dosen4");
        status4=MyArr.get(3).get("status4");
        rotasi5= MyArr.get(4).get("rotasi5");
        tgl5=MyArr.get(4).get("tgl5");
        dosen5=MyArr.get(4).get("dosen5");
        status5=MyArr.get(4).get("status5");
        rotasi6= MyArr.get(5).get("rotasi6");
        tgl6=MyArr.get(5).get("tgl6");
        dosen6=MyArr.get(5).get("dosen6");
        status6=MyArr.get(5).get("status6");*/

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

    /*  private void staserotasi() {
          Intent i = getIntent();
          final String id = i.getStringExtra(RotasiInternal.KEY_ID);
          JSONObject request = new JSONObject();
          try {
              SessionHandler session;
              session = new SessionHandler(getApplicationContext());
              User user = session.getUserDetails();
              String username = user.getUsername();
              request.put("username", username);
              request.put("id", id);
          } catch (JSONException e) {
              e.printStackTrace();
          }
          JsonObjectRequest json;
          json = new JsonObjectRequest(Request.Method.POST,
                  urlsem, request, new Response.Listener<JSONObject>() {
              @Override
              public void onResponse(JSONObject response) {
                  try {
                      JSONArray tmp = response.getJSONArray("tmp");
                      JSONArray total = response.getJSONArray("total");
                      JSONArray array1 = response.getJSONArray("array1");
                      JSONArray array2 = response.getJSONArray("array2");
                      JSONArray array3 = response.getJSONArray("array3");
                      JSONArray array4 = response.getJSONArray("array4");
                      JSONArray array5 = response.getJSONArray("array5");
                      JSONArray array6 = response.getJSONArray("array6");
                      for (int i = 0; i < tmp.length(); i++) {
                          JSONObject c = tmp.getJSONObject(i);
                          JSONObject d = total.getJSONObject(i);
                          JSONObject e = array1.getJSONObject(i);
                          JSONObject f = array2.getJSONObject(i);
                          JSONObject g = array3.getJSONObject(i);
                          JSONObject h = array4.getJSONObject(i);
                          JSONObject j = array5.getJSONObject(i);
                          JSONObject k = array6.getJSONObject(i);
                          HashMap<String, String> map = new HashMap<String, String>();
                          map.put("id_rotasi",c.getString("id"));
                          map.put("stase", c.getString("internal"));
                          map.put("hari", c.getString("hari"));
                          map.put("id_internal", d.getString("id"));
                          if (d.getString("id").equals("null")) {
                              map.put("tgl1",e.getString(""));
                              map.put("dosen1",e.getString(""));
                              map.put("rotasi1",e.getString(""));
                              map.put("status1",e.getString(""));
                              map.put("tgl2",f.getString(""));
                              map.put("dosen2",f.getString(""));
                              map.put("rotasi2",f.getString(""));
                              map.put("status2",f.getString(""));
                              map.put("tgl3",g.getString(""));
                              map.put("dosen3",g.getString(""));
                              map.put("rotasi3",g.getString(""));
                              map.put("status3",g.getString(""));
                              map.put("tgl4",h.getString(""));
                              map.put("dosen4",h.getString(""));
                              map.put("rotasi4",h.getString(""));
                              map.put("status4",h.getString(""));
                              map.put("tgl5",j.getString(""));
                              map.put("dosen5",j.getString(""));
                              map.put("rotasi5",j.getString(""));
                              map.put("status5",j.getString(""));
                              map.put("tgl6",k.getString(""));
                              map.put("dosen6",k.getString(""));
                              map.put("rotasi6",k.getString(""));
                              map.put("status6",k.getString(""));
                          } else {
                              String tgl="0000-00-00";

                              Date tglml=format.parse(tgl);
                              Date tglMulai = tglml;
                              Date tglselesai = tglml;
                              String a = "";
                              String b = "";
                              String x = "";
                              if (i == 0) {
                                  tglMulai = format.parse(e.getString("tgl1"));
                                  a = e.getString("tgl1");
                                  b = e.getString("dosen1");
                                  x = e.getString("rotasi1");
                                  map.put("dosen",b);
                                  map.put("tglmli", a);
                                  String wktu = c.getString("hari");
                                  int waktu = Integer.parseInt(wktu);
                                  int sum = 1000 * 60 * 60 * 24 * waktu;
                                  int minus = 1000 * 60 * 60 * 24;
                                  if (tglMulai != null) {
                                      tglselesai = new Date(tglMulai.getTime() + sum - minus);
                                  }
                              } else if (i == 1) {
                                  a = f.getString("tgl2");
                                  b = f.getString("dosen2");
                                  x = f.getString("rotasi2");
                                  map.put("dosen",b);
                                  tglMulai = format.parse(f.getString("tgl2"));
                                  map.put("tglmli", a);
                                  String wktu = c.getString("hari");
                                  int waktu = Integer.parseInt(wktu);
                                  int sum = 1000 * 60 * 60 * 24 * waktu;
                                  int minus = 1000 * 60 * 60 * 24;
                                  if (tglMulai != null) {
                                      tglselesai = new Date(tglMulai.getTime() + sum - minus);
                                  }
                              } else if (i == 2) {
                                  tglMulai = format.parse(g.getString("tgl3"));
                                  a = g.getString("tgl3");
                                  b = g.getString("dosen3");
                                  x = g.getString("rotasi3");
                                  map.put("dosen",b);
                                  map.put("tglmli", a);
                                  String wktu = c.getString("hari");
                                  int waktu = Integer.parseInt(wktu);
                                  int sum = 1000 * 60 * 60 * 24 * waktu;
                                  int minus = 1000 * 60 * 60 * 24;
                                  if (tglMulai != null) {
                                      tglselesai = new Date(tglMulai.getTime() + sum - minus);
                                  }
                              } else if (i == 3) {
                                  tglMulai = format.parse(h.getString("tgl4"));
                                  a = h.getString("tgl4");
                                  b = h.getString("dosen4");
                                  x = h.getString("rotasi4");
                                  map.put("dosen",b);
                                  map.put("tglmli", a);
                                  String wktu = c.getString("hari");
                                  int waktu = Integer.parseInt(wktu);
                                  int sum = 1000 * 60 * 60 * 24 * waktu;
                                  int minus = 1000 * 60 * 60 * 24;
                                  if (tglMulai != null) {
                                      tglselesai = new Date(tglMulai.getTime() + sum - minus);
                                  }
                              } else if (i == 4) {
                                  tglMulai = format.parse(j.getString("tgl5"));
                                  a = j.getString("tgl5");
                                  b = j.getString("dosen5");
                                  x = j.getString("rotasi5");
                                  map.put("dosen",b);
                                  map.put("tglmli", a);

                                  String wktu = c.getString("hari");
                                  int waktu = Integer.parseInt(wktu);
                                  int sum = 1000 * 60 * 60 * 24 * waktu;
                                  int minus = 1000 * 60 * 60 * 24;
                                  if (tglMulai != null) {
                                      tglselesai = new Date(tglMulai.getTime() + sum - minus);
                                  }
                              } else if (i == 5) {
                                  tglMulai = format.parse(h.getString("tgl6"));
                                  b = k.getString("dosen6");
                                  a = k.getString("tgl6");
                                  x = k.getString("rotasi6");
                                  map.put("tglmli", a);
                                  map.put("dosen",b);

                                  String wktu = c.getString("hari");
                                  int waktu = Integer.parseInt(wktu);
                                  int sum = 1000 * 60 * 60 * 24 * waktu;
                                  int minus = 1000 * 60 * 60 * 24;
                                  if (tglMulai != null) {
                                      tglselesai = new Date(tglMulai.getTime() + sum - minus);
                                  }
                              }





                          }

                          MyArr.add(map);

                      }






                  } catch (JSONException e) {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
                  } catch (ParseException e) {
                      e.printStackTrace();
                  }
              }
          }, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                  Toast.makeText(getApplicationContext(),
                          error.getMessage(), Toast.LENGTH_SHORT).show();
              }
          });
          MySingleton.getInstance(this).addToRequestQueue(json);

      }*/
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

