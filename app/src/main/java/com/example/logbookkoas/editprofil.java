package com.example.logbookkoas;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

import cyd.awesome.material.AwesomeText;
import cyd.awesome.material.FontCharacterMaps;

public class editprofil extends AppCompatActivity {
    private Spinner spinnerkota, spinnerprop, spinnerwali, spinnerkota1, spinnerkota2;
    Button buttonSubmit;
    ProgressDialog pDialog;
    private static final String KEY_STATUS = "status";
    private static final String KEY_MENU = "level";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_NAMA = "nama";
    private static final String KEY_PROPLHR = "proplahir";
    private static final String KEY_KOTALHR = "kotalahir";
    private static final String KEY_TGLLHR = "tgllahir";
    private static final String KEY_ALAMAT = "alamat";
    private static final String KEY_PROPALAMAT = "propinsialamat";
    private static final String KEY_KOTAALAMAT = "kotalamat";
    private static final String KEY_NOHP = "nohp";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NMWALI = "namawali";
    private static final String KEY_ALAMATWALI = "alamatwali";
    private static final String KEY_PROPWALI = "propwali";
    private static final String KEY_KOTAWALI = "kotawali";
    private static final String KEY_NOHPWALI = "nohpwali";
    private static final String KEY_EMPTY = "";
    private String UPLOAD_URL = "http://192.168.1.9/upload.php";
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private String simpan_url = "http://192.168.1.9/updateprofil.php";
    private String data_url = "http://192.168.1.9/getdataprofilms.php";
    private String data_url1 = "http://192.168.1.9/getdataprofilms1.php";
    public static final String KOTA_URL = "http://192.168.1.9/getKota.php";
    public static final String PROP_URL = "http://192.168.1.9/getProp.php";
    private static final String TAG_MESSAGE = "message";
    public static final String KEY_IMAGE = "image";
    private String username;
    private String passwordbr;
    private String namabr;
    private String propuserbr;
    private String kotauserbr;
    private String tgllahirbr;
    private String alamatbr;
    private String propalmtbr;
    private String kotaalmtbr;
    private String nohpbr;
    private String emailbr;
    private String nmwalibr;
    int success;
    private String almtwlbr;
    private String propwalibr;
    private String kotawalibr;
    private String nohpwalibr;
    private String namalengkap;
    private String idbr;
    EditText passwordt, namat, tgllhrt, alamatt, nohpt, emailt, nmwlt, nohpwlt, almtwlt;
    Spinner propusert, kotausert, propalmtt, kotaalmtt, propwalit, kotawalit;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TextView tvDateResult;
    private int PICK_IMAGE_REQUEST = 1;
    private TextView ganti;
    private ImageView imageView, dateimg;
    SessionHandler session;
    private TextView hapus;
    private EditText btDatePicker;
    private Bitmap bitmap, decoded;
    private Uri filePath;
    TextView usernamems, nmlengkap, id;
    String propusers, kotausers, propals, kotaals, propwalis, kotawalis;
    Button simpan;
    String tag_json_obj = "json_obj_req";
    final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
    final ArrayList<HashMap<String, String>> MyArrList1 = new ArrayList<HashMap<String, String>>();
    final ArrayList<HashMap<String, String>> MyArrList2 = new ArrayList<HashMap<String, String>>();
    final ArrayList<HashMap<String, String>> list_data = new ArrayList<HashMap<String, String>>();
    final ArrayList<HashMap<String, String>> list_data1 = new ArrayList<HashMap<String, String>>();
    final ArrayList<HashMap<String, String>> list_data2 = new ArrayList<HashMap<String, String>>();
    final ArrayList<HashMap<String, String>> list_data3 = new ArrayList<HashMap<String, String>>();
    AwesomeText imgShowhidepassword;
    Spinner spin_prop;
    boolean pwd_status = true;
    int bitmap_size = 100; // range 1 - 100
    HashMap<String, String> map;
    // array list for spinner adapter
    ArrayList<String> kotaList1, kotalist2, kotalist3;

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
        SessionHandler session = new SessionHandler(getApplicationContext());
        dateFormatter = new SimpleDateFormat(myformat, Locale.US);
        usernamems = findViewById(R.id.usernamems);
        id = findViewById(R.id.idms);
        btDatePicker = (EditText) findViewById(R.id.tanggal);
        tvDateResult = (TextView) findViewById(R.id.tanggal);
        spinnerprop = (Spinner) findViewById(R.id.propuser);
        spinnerkota = (Spinner) findViewById(R.id.kotauser);
        dateimg = (ImageView) findViewById(R.id.imgbtn);
        ganti = (TextView) findViewById(R.id.ganti);
        hapus = (TextView) findViewById(R.id.hapus);
        spinnerkota1 = (Spinner) findViewById(R.id.kotaal);
        spinnerkota2 = (Spinner) findViewById(R.id.spinkotawali);
        kotaList1 = new ArrayList<String>();
        kotalist2 = new ArrayList<String>();
        kotalist3 = new ArrayList<String>();
        imageView = findViewById(R.id.imgprofil);
        spinnerwali = (Spinner) findViewById(R.id.spinwali);
        spin_prop = findViewById(R.id.propoal);
        passwordt = findViewById(R.id.passms);
        namat = findViewById(R.id.namalngkpms);
        nmlengkap = findViewById(R.id.nmlengkap);
        alamatt = findViewById(R.id.almt);
        nohpt = findViewById(R.id.nohp);
        emailt = findViewById(R.id.email);
        nmwlt = findViewById(R.id.nmwl);
        almtwlt = findViewById(R.id.almtwl);
        nohpwlt = findViewById(R.id.nohpwl);
        simpan = findViewById(R.id.simpanms);
        User user = session.getUserDetails();
        String user1 = user.getUsername();
        Bundle bundle = getIntent().getExtras();
        usernamems.setText(bundle.getString("data1"));
        namat.setText(bundle.getString("data2"));
        getData1(usernamems.getText().toString());

        imgShowhidepassword =(AwesomeText)findViewById(R.id.ImgShowPasswordms);
        imgShowhidepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pwd_status) {
                    passwordt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    pwd_status = false;
                    imgShowhidepassword.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY);
                    passwordt.setSelection(passwordt.length());
                } else {
                    passwordt.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    pwd_status = true;
                    imgShowhidepassword.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY_OFF);
                    passwordt.setSelection(passwordt.length());
                }
            }
        });
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                passwordbr = passwordt.getText().toString();
                namabr = namat.getText().toString();
                propuserbr = spinnerprop.getSelectedItem().toString();
                propusers = propuserbr.substring(0, 2);
                kotauserbr = spinnerkota.getSelectedItem().toString();
                kotausers = kotauserbr.substring(4, 9);
                tgllahirbr = btDatePicker.getText().toString();
                alamatbr = alamatt.getText().toString();
                propalmtbr = spin_prop.getSelectedItem().toString();
                propals = propalmtbr.substring(0, 2);
                kotaalmtbr = spinnerkota1.getSelectedItem().toString();
                kotaals = kotaalmtbr.substring(4, 9);
                nohpbr = nohpt.getText().toString();
                emailbr = emailt.getText().toString();
                nmwalibr = nmwlt.getText().toString();
                propwalibr = spinnerwali.getSelectedItem().toString();
                propwalis = propwalibr.substring(0, 2);
                kotawalibr = spinnerkota2.getSelectedItem().toString();
                kotawalis = kotawalibr.substring(4, 9);
                almtwlbr = almtwlt.getText().toString();
                nohpwalibr = nohpwlt.getText().toString();
                namalengkap = nmlengkap.getText().toString();
                idbr = id.getText().toString();
                if(validateInputs()){
                getsimpan();
                uploadimage();
                Intent i= new Intent(editprofil.this,MahasiswaActivity.class);
                startActivity(i);}


            }
        });


        dateimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
        spinnerwali.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MyArrList2.clear();
                String prov = spinnerwali.getSelectedItem().toString();
                String prov1 = prov.substring(3);
                getKota(prov1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spin_prop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MyArrList1.clear();
                String prov = spin_prop.getSelectedItem().toString();
                String prov1 = prov.substring(3);
                getKota(prov1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerprop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                MyArrList.clear();
                // Refresh Spinner
                String prov = spinnerprop.getSelectedItem().toString();
                String prov1 = prov.substring(3);
                getKota(prov1);
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


    private void kosong() {
        imageView.setImageResource(0);
    }

    private boolean validateInputs() {
        if(KEY_EMPTY.equals(namabr)){
            namat.setError("Username cannot be empty");
            passwordt.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(passwordbr)){
            passwordt.setError("Password cannot be empty");
            passwordt.requestFocus();
            return false;
        }
        return true;
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
                        map = new HashMap<String, String>();
                        map.put("id", j.getString("id_kota"));
                        map.put("kota", j.getString("kota"));
                        String namaKota = j.getString("kota");
                        MyArrList.add(map);
                        MyArrList1.add(map);
                        MyArrList2.add(map);

                    }
                    SimpleAdapter s4dap;
                    s4dap = new SimpleAdapter(editprofil.this, MyArrList, R.layout.columnkota, new String[]{"id", "kota"}, new int[]{R.id.idkota, R.id.nmkota});
                    spinnerkota.setAdapter(s4dap);
                    SimpleAdapter s4dap1;
                    s4dap1 = new SimpleAdapter(editprofil.this, MyArrList1, R.layout.columnkota, new String[]{"id", "kota"}, new int[]{R.id.idkota, R.id.nmkota});
                    spinnerkota1.setAdapter(s4dap1);
                    SimpleAdapter s4dap2;
                    s4dap2 = new SimpleAdapter(editprofil.this, MyArrList2, R.layout.columnkota2, new String[]{"id", "kota"}, new int[]{R.id.idkota2, R.id.nmkota2});
                    spinnerkota2.setAdapter(s4dap2);


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
    private void getProp() {
        JSONObject request = new JSONObject();
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                KOTA_URL, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray kota = response.getJSONArray("prop");
                    for (int i = 0; i < kota.length(); i++) {
                        JSONObject j = kota.getJSONObject(i);
                        map = new HashMap<String, String>();
                        map.put("id", j.getString("id_prop"));
                        map.put("prop", j.getString("prop"));
                        String namaKota = j.getString("kota");
                        list_data1.add(map);
                        list_data2.add(map);
                        list_data3.add(map);
                    }
                    SimpleAdapter s4dap;
                    s4dap = new SimpleAdapter(editprofil.this, list_data1, R.layout.prop, new String[]{"id", "prop"}, new int[]{R.id.idprop, R.id.nmprop});
                    spin_prop.setAdapter(s4dap);


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

  /*  public void getData(String user1){

        JSONObject request = new JSONObject();
        try {
            request.put(KEY_USERNAME, user1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                data_url, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray kota = response.getJSONArray("data");
                    for (int i = 0; i < kota.length(); i++) {
                        JSONObject j = kota.getJSONObject(i);
                        HashMap<String, String> map1  = new HashMap<String, String>();
                        map1 = new HashMap<String, String>();
                        map1.put("username",j.getString("username"));
                        map1.put("nama",j.getString("nama"));
                        list_data.add(map1);

                    }

                    usernamems.setText(list_data.get(0).get("username"));
                    namat.setText(list_data.get(0).get("nama"));
                    nmlengkap.setText(list_data.get(0).get("nama"));




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
        MySingleton.getInstance(this).addToRequestQueue(json);}*/

    public void getData1(String username) {

        JSONObject request = new JSONObject();
        try {
            request.put(KEY_USERNAME, username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                data_url1, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray kota = response.getJSONArray("data1");
                    for (int i = 0; i < kota.length(); i++) {
                        JSONObject j = kota.getJSONObject(i);
                        HashMap<String, String> map1 = new HashMap<String, String>();
                        map1 = new HashMap<String, String>();
                        map1.put("id", j.getString("id"));
                        map1.put("nama", j.getString("nama"));
                        map1.put("kotalhr", j.getString("kota_lahir"));
                        map1.put("proplhr", j.getString("prop_lahir"));
                        map1.put("tgllhr", j.getString("tanggal_lahir"));
                        map1.put("alamat", j.getString("alamat"));
                        map1.put("kotaalmt", j.getString("kota_alamat"));
                        map1.put("propalmt", j.getString("prop_alamat"));
                        map1.put("nohp", j.getString("no_hp"));
                        map1.put("email", j.getString("email"));
                        map1.put("nmortu", j.getString("nama_ortu"));
                        map1.put("almtortu", j.getString("alamat_ortu"));
                        map1.put("kotaortu", j.getString("kota_ortu"));
                        map1.put("proportu", j.getString("prop_ortu"));
                        map1.put("nohportu", j.getString("no_hportu"));
                        map1.put("foto", j.getString("foto"));
                        list_data.add(map1);

                    }

                    id.setText(list_data.get(0).get("id"));
                    tvDateResult.setText(list_data.get(0).get("tgllhr"));
                    //.setText(list_data1.get(0).get("kotalhr"));
                    //gelartx.setText(list_data1.get(0).get("proplhr"));
                    alamatt.setText(list_data.get(0).get("alamat"));
                    //gelartx.setText(list_data1.get(0).get("kotaalmt"));
                    //gelartx.setText(list_data1.get(0).get("propalmt"));
                    nohpt.setText(list_data.get(0).get("nohp"));
                    emailt.setText(list_data.get(0).get("email"));
                    nmwlt.setText(list_data.get(0).get("nmortu"));
                    almtwlt.setText(list_data.get(0).get("almtortu"));
                    //gelartx.setText(list_data1.get(0).get("kotaortu"));
                    //gelartx.setText(list_data1.get(0).get("proportu"));
                    nohpwlt.setText(list_data.get(0).get("nohportu"));
                    Glide.with(getApplicationContext())
                            .load("http://192.168.1.9/upload/" + list_data.get(0).get("foto"))
                            .into(imageView);


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

    private void getsimpan() {
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            session = new SessionHandler(getApplicationContext());
            User user = session.getUserDetails();
            String username = user.getUsername();
            request.put(KEY_USERNAME, usernamems.getText().toString());
            request.put(KEY_PASSWORD, passwordbr);
            request.put(KEY_NAMA, namabr);
            request.put(KEY_PROPLHR, propusers);
            request.put(KEY_KOTALHR, kotausers);
            request.put(KEY_TGLLHR, tgllahirbr);
            request.put(KEY_ALAMAT, alamatbr);
            request.put(KEY_PROPALAMAT, propals);
            request.put(KEY_KOTAALAMAT, kotaals);
            request.put(KEY_NOHP, nohpbr);
            request.put(KEY_EMAIL, emailbr);
            request.put(KEY_NMWALI, nmwalibr);
            request.put(KEY_ALAMATWALI, almtwlbr);
            request.put(KEY_PROPWALI, propwalis);
            request.put(KEY_KOTAWALI, kotawalis);
            request.put(KEY_NOHPWALI, nohpwalibr);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, simpan_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if (response.getInt(KEY_STATUS) == 0) {
                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        imageView.setImageBitmap(decoded);
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);

    }

    private void uploadimage() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String Response = jsonObject.getString("response");
                    Toast.makeText(editprofil.this, Response, Toast.LENGTH_SHORT).show();
                    imageView.setImageResource(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", idbr);
                params.put(KEY_NAMA, namabr);
                params.put(KEY_USERNAME, usernamems.getText().toString());
                params.put(KEY_IMAGE, getStringImage(bitmap));
                params.put(KEY_PASSWORD, passwordbr);
                params.put("namalengkap", namalengkap);
                params.put(KEY_PROPLHR, propusers);
                params.put(KEY_KOTALHR, kotausers);
                params.put(KEY_TGLLHR, tgllahirbr);
                params.put(KEY_ALAMAT, alamatbr);
                params.put(KEY_PROPALAMAT, propals);
                params.put(KEY_KOTAALAMAT, kotaals);
                params.put(KEY_NOHP, nohpbr);
                params.put(KEY_EMAIL, emailbr);
                params.put(KEY_NMWALI, nmwalibr);
                params.put(KEY_ALAMATWALI, almtwlbr);
                params.put(KEY_PROPWALI, propwalis);
                params.put(KEY_KOTAWALI, kotawalis);
                params.put(KEY_NOHPWALI, nohpwalibr);
                return params;


            }
        };
        MySingleton.getInstance(editprofil.this).addToRequestQueue(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //mengambil fambar dari Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // 512 adalah resolusi tertinggi setelah image di resize, bisa di ganti.
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
                nmlengkap.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
   /* public void getData(String nmlengkap) {

        JSONObject request = new JSONObject();
        try {
            request.put(KEY_NAMA, nmlengkap);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                data_url, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray kota = response.getJSONArray("datads");
                    for (int i = 0; i < kota.length(); i++) {
                        JSONObject j = kota.getJSONObject(i);
                        HashMap<String, String> map1 = new HashMap<String, String>();
                        map1 = new HashMap<String, String>();
                        map1.put("nama", j.getString("nama"));
                        map1.put("kotalhr", j.getString("kota_lahir"));
                        map1.put("proplhr", j.getString("prop_lahir"));
                        map1.put("tgllhr", j.getString("tanggal_lahir"));
                        map1.put("alamat", j.getString("alamat"));
                        map1.put("kotaalmt", j.getString("kota_alamat"));
                        map1.put("propalmt", j.getString("prop_alamat"));
                        map1.put("nohp", j.getString("no_hp"));
                        map1.put("email", j.getString("email"));
                        map1.put("nmortu", j.getString("nama_ortu"));
                        map1.put("almtortu", j.getString("alamat_ortu"));
                        map1.put("kotaortu", j.getString("kota_ortu"));
                        map1.put("proportu", j.getString("prop_ortu"));
                        map1.put("nohportu", j.getString("no_hportu"));
                        list_data1.add(map1);
                    }

                    namat.setText(list_data1.get(0).get("nama"));
                    tvDateResult.setText(list_data1.get(0).get("tgllhr"));
                //    gelartx.setText(list_data1.get(0).get("gelar"));
                    //      spin.setSelection(list_data1.indexOf("kdbagian"));



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
    }*/

}





