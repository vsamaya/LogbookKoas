package com.example.logbookkoas;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Base64;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import cyd.awesome.material.AwesomeText;
import cyd.awesome.material.FontCharacterMaps;

public class editprofil extends AppCompatActivity {
    private SearchableSpinner spinnerkota, spinnerpropus, spinnerwali, spinnerkota1, spinnerkota2, spinnerpropal;
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
    private String UPLOAD_URL = "http://192.168.1.7/logbook/upload.php";
    private String HAPUS_URL = "http://192.168.1.7/logbook/hpsfoto.php";
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private String simpan_url = "http://192.168.1.7/logbook/updateprofil.php";
    private String data_url = "http://192.168.1.7/logbook/getdataprofilms.php";
    private String data_url1 = "http://192.168.1.7/logbook/getdataprofilms1.php";
    public static final String KOTA_URL = "http://192.168.1.7/logbook/getKota.php";
    private String foto_image = "http://192.168.1.7/koas/foto/";
    public static final String PROP_URL = "http://192.168.1.7/logbook/getpropkota.php";
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
    private Bitmap bitmap, decoded,drawable;
    private Uri filePath;
    TextView usernamems, nmlengkap, id;
    String propusers, kotausers, propals, kotaals, propwalis, kotawalis;
    Button simpan;
    String tag_json_obj = "json_obj_req";
    final ArrayList<String> MyArrList = new ArrayList<String>();
    final ArrayList<String> MyArrList1 = new ArrayList<String>();
    final ArrayList<String> MyArrList2 = new ArrayList<String>();
    final ArrayList<String> MyArrList3 = new ArrayList<String>();
    final ArrayList<String> MyArrList4 = new ArrayList<String>();
    final ArrayList<String> MyArrList5 = new ArrayList<String>();
    final ArrayList<HashMap<String, String>> list_data = new ArrayList<HashMap<String, String>>();
    final ArrayList<HashMap<String, String>> list_data1 = new ArrayList<HashMap<String, String>>();
    final ArrayList<HashMap<String, String>> list_data2 = new ArrayList<HashMap<String, String>>();
    final ArrayList<HashMap<String, String>> list_data3 = new ArrayList<HashMap<String, String>>();
    final ArrayList<HashMap<String, String>> list_data4 = new ArrayList<HashMap<String, String>>();
    final ArrayList<HashMap<String, String>> list_data5 = new ArrayList<HashMap<String, String>>();
    final ArrayList<String> idpropu = new ArrayList<String>();
    final ArrayList<String> propu = new ArrayList<String>();
    final ArrayList<String> idpropa = new ArrayList<String>();
    final ArrayList<String> propa = new ArrayList<String>();
    final ArrayList<String> idpropwal = new ArrayList<String>();
    final ArrayList<String> propwal = new ArrayList<String>();
    final ArrayList<String> idkotau = new ArrayList<String>();
    final ArrayList<String> kotau = new ArrayList<String>();
    final ArrayList<String> idkotaa = new ArrayList<String>();
    final ArrayList<String> kotaa = new ArrayList<String>();
    final ArrayList<String> idkotawal = new ArrayList<String>();
    final ArrayList<String> kotawal = new ArrayList<String>();
    final ArrayList<String> sidpropu = new ArrayList<String>();
    final ArrayList<String> spropu = new ArrayList<String>();
    final ArrayList<String> sidpropa = new ArrayList<String>();
    final ArrayList<String> spropa = new ArrayList<String>();
    final ArrayList<String> sidpropwal = new ArrayList<String>();
    final ArrayList<String> spropwal = new ArrayList<String>();
    final ArrayList<String> sidkotau = new ArrayList<String>();
    final ArrayList<String> skotau = new ArrayList<String>();
    final ArrayList<String> sidkotaa = new ArrayList<String>();
    final ArrayList<String> skotaa = new ArrayList<String>();
    final ArrayList<String> sidkotawal = new ArrayList<String>();
    final ArrayList<String> skotawal = new ArrayList<String>();
    AwesomeText imgShowhidepassword;
    boolean pwd_status = true;
    int bitmap_size = 100; // range 1 - 100
    String map;
    // array list for spinner adapter
    ArrayList<String> kotaList1, kotalist2, kotalist3;
    ImageView imgpropus, imgkotaus, imgpropal, imgkotaal, imgpropwali, imgkotawali;

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
        spinnerpropus = findViewById(R.id.propuser);
        spinnerkota = findViewById(R.id.kotauser);
        dateimg = (ImageView) findViewById(R.id.imgbtn);
        ganti = (TextView) findViewById(R.id.ganti);
        hapus = (TextView) findViewById(R.id.hapus);
        spinnerkota1 = findViewById(R.id.kotaal);
        spinnerkota2 = findViewById(R.id.spinkotawali);
        kotaList1 = new ArrayList<String>();
        kotalist2 = new ArrayList<String>();
        kotalist3 = new ArrayList<String>();
        imageView = findViewById(R.id.imgprofil);
        spinnerwali = findViewById(R.id.spinwali);
        spinnerpropal = findViewById(R.id.propoal);
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
        drawable=BitmapFactory.decodeResource(getResources(), R.drawable.icaccount1);
        getData1(user1);
        getProp();
        //    getData(usernamems.getText().toString());
        imgShowhidepassword = (AwesomeText) findViewById(R.id.ImgShowPasswordms);
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
                if (passwordt.getText().toString().equals("")) {
                    passwordbr = list_data.get(0).get("pass");
                } else {
                    passwordbr = md5(passwordt.getText().toString());
                }
                namabr = namat.getText().toString();
                String[] idu = getStringArray(idpropu);
                propusers = idu[spinnerpropus.getSelectedItemPosition()];
                //  kotauserbr = spinnerkota.getSelectedItem().toString();
                //  kotausers = kotauserbr.substring(kotauserbr.indexOf("id=")+3,kotauserbr.indexOf("}"));
                String[] ktu = getStringArray(MyArrList);
                kotausers = new String();
                kotausers = ktu[spinnerkota.getSelectedItemPosition()];
                //Toast.makeText(editprofil.this, kotausers, Toast.LENGTH_SHORT).show();
                tgllahirbr = btDatePicker.getText().toString();
                alamatbr = alamatt.getText().toString();
                String[] ida = getStringArray(idpropa);
                propals = ida[spinnerpropal.getSelectedItemPosition()];
                String[] kta = getStringArray(MyArrList4);
                kotaals = new String();
                kotaals = kta[spinnerkota1.getSelectedItemPosition()];
                //Toast.makeText(editprofil.this, kotaals, Toast.LENGTH_SHORT).show();
                nohpbr = nohpt.getText().toString();
                emailbr = emailt.getText().toString();
                nmwalibr = nmwlt.getText().toString();
                String[] idwal = getStringArray(idpropwal);
                propwalis = idwal[spinnerwali.getSelectedItemPosition()];
                String[] ktwal = getStringArray(MyArrList5);
                kotawalis = new String();
                kotawalis = ktwal[spinnerkota2.getSelectedItemPosition()];
                almtwlbr = almtwlt.getText().toString();
                nohpwalibr = nohpwlt.getText().toString();
                namalengkap = nmlengkap.getText().toString();
                idbr = id.getText().toString();
                if (validateInputs()) {
                    getsimpan();
                    uploadimage();
                    Handler h =new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            Intent i = new Intent(editprofil.this, MahasiswaActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }
                    };
                    h.sendEmptyMessageDelayed(0,1500);
                }


            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusfoto();
            }
        });
        dateimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
       /* spinnerwali.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MyArrList2.clear();
                String prov = spinnerwali.getSelectedItem().toString();
                String prov1 = prov.substring(prov.indexOf("id=")+3,prov.indexOf("}"));
                getKota(prov1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerpropal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MyArrList1.clear();
                String prov = spinnerpropal.getSelectedItem().toString();
                String prov1 = prov.substring(prov.indexOf("id=")+3,prov.indexOf("}"));
                getKota(prov1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerpropus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                MyArrList.clear();
                // Refresh Spinner
                String prov = spinnerpropus.getSelectedItem().toString();
                String prov1 = prov.substring(prov.indexOf("id=")+3,prov.indexOf("}"));
                getKota(prov1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/

        ganti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

    }


    public static final String md5 ( final String s){
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void kosong() {
        imageView.setImageResource(0);
    }

    private boolean validateInputs() {
        if (KEY_EMPTY.equals(namabr)) {
            namat.setError("Username cannot be empty");
            namat.requestFocus();
            return false;
        }
        return true;
    }

    private void getKota(String provinsi) {
        JSONObject request = new JSONObject();
        try {
            request.put("provinsi", provinsi);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                KOTA_URL, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray kota = response.getJSONArray("kota");
                    MyArrList1.clear();
                    MyArrList.clear();
                    for (int i = 0; i < kota.length(); i++) {
                        JSONObject j = kota.getJSONObject(i);
                        String id = j.getString("id_kota");
                        String kt = j.getString("kota");
                        MyArrList.add(id);
                        MyArrList1.add(kt);
                    }
                    String[] idk = getStringArray(MyArrList);
                    String[] bgn = getStringArray(MyArrList1);
                    ArrayAdapter<String> p = new ArrayAdapter<String>(editprofil.this, android.R.layout.simple_spinner_item, bgn);
                    spinnerkota.setAdapter(p);
                    String[] pen1 = getStringArray(skotau);
                    spinnerkota.setSelection(getIndex(spinnerkota, pen1[0]));
                    spinnerkota.setTitle("Pilih Kota");

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERRORkt", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(json);
    }

    private void getKota1(String provinsi) {
        JSONObject request = new JSONObject();
        try {
            request.put("provinsi", provinsi);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                KOTA_URL, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray kota = response.getJSONArray("kota");
                    MyArrList2.clear();
                    MyArrList4.clear();
                    for (int i = 0; i < kota.length(); i++) {
                        JSONObject j = kota.getJSONObject(i);
                        String id = j.getString("id_kota");
                        String kt = j.getString("kota");
                        MyArrList4.add(id);
                        MyArrList2.add(kt);
                    }

                    String[] bgn1 = getStringArray(MyArrList2);
                    ArrayAdapter<String> q = new ArrayAdapter<String>(editprofil.this, android.R.layout.simple_spinner_item, bgn1);
                        spinnerkota1.setAdapter(q);
                        String[] pen2 = getStringArray(skotaa);
                        spinnerkota1.setSelection(getIndex(spinnerkota1, pen2[0]));
                        spinnerkota1.setTitle("Pilih Kota");

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERRORkt", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(json);
    }

    private void hapusfoto() {
        JSONObject request = new JSONObject();
        try {
            request.put(KEY_USERNAME, usernamems.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                HAPUS_URL, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(editprofil.this, response.getString("response"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERRORkt", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(json);
    }



    private void getKota2(String provinsi) {
        JSONObject request = new JSONObject();
        try {
            request.put("provinsi", provinsi);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                KOTA_URL, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray kota = response.getJSONArray("kota");
                    MyArrList3.clear();
                    MyArrList5.clear();
                    for (int i = 0; i < kota.length(); i++) {
                        JSONObject j = kota.getJSONObject(i);
                        String id = j.getString("id_kota");
                        String kt = j.getString("kota");
                        MyArrList5.add(id);
                        MyArrList3.add(kt);
                    }
                    String[] bgn2 = getStringArray(MyArrList3);
                    ArrayAdapter<String> r = new ArrayAdapter<String>(editprofil.this, android.R.layout.simple_spinner_item, bgn2);
                        spinnerkota2.setAdapter(r);
                        String[] pen3 = getStringArray(skotawal);
                        spinnerkota2.setSelection(getIndex(spinnerkota2, pen3[0]));
                        spinnerkota2.setTitle("Pilih Kota");
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERRORkt", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(json);
    }

    private void getProp() {
        JSONObject request = new JSONObject();
        try {
            SessionHandler session = new SessionHandler(getApplicationContext());
            User user = session.getUserDetails();
            String username = user.getUsername();
            request.put(KEY_USERNAME, username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                PROP_URL, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray kota = response.getJSONArray("dfprop");
                    JSONArray kota1 = response.getJSONArray("hsllhr");
                    JSONArray kota2 = response.getJSONArray("hslalmt");
                    JSONArray kota3 = response.getJSONArray("hslortu");
                    for (int i = 0; i < kota.length(); i++) {
                        JSONObject j = kota.getJSONObject(i);
                        String prp = j.getString("prop");
                        String idp = j.getString("id_prop");
                        idpropu.add(idp);
                        idpropa.add(idp);
                        idpropwal.add(idp);
                        propu.add(prp);
                        propa.add(prp);
                        propwal.add(prp);
                    }
                    for (int i = 0; i < kota1.length(); i++) {
                        JSONObject k = kota1.getJSONObject(i);
                        String prplhr = k.getString("prop");
                        String idprlhr = k.getString("prop_lahir");
                        String idktlhr = k.getString("kota_lahir");
                        String ktlhr = k.getString("kota");
                        sidkotau.add(idktlhr);
                        skotau.add(ktlhr);
                        spropu.add(prplhr);
                        sidpropu.add(idprlhr);
                    }
                    for (int i = 0; i < kota2.length(); i++) {
                        JSONObject l = kota2.getJSONObject(i);
                        String prpalmt = l.getString("prop");
                        String idpralmt = l.getString("prop_alamat");
                        String idktalmt = l.getString("kota_alamat");
                        String ktalmt = l.getString("kota");
                        sidkotaa.add(idktalmt);
                        skotaa.add(ktalmt);
                        spropa.add(prpalmt);
                        sidpropa.add(idpralmt);
                    }
                    for (int i = 0; i < kota3.length(); i++) {
                        JSONObject m = kota3.getJSONObject(i);
                        String prportu = m.getString("prop");
                        String idprortu = m.getString("prop_ortu");
                        String idktortu = m.getString("kota_ortu");
                        String ktortu = m.getString("kota");
                        sidkotawal.add(idktortu);
                        skotawal.add(ktortu);
                        spropwal.add(prportu);
                        sidpropwal.add(idprortu);
                    }
                    final String[] idkotdf = getStringArray(MyArrList);
                    final String[] kotdf = getStringArray(MyArrList1);
                    String[] sidu = getStringArray(sidpropu);
                    String[] sprpu = getStringArray(spropu);
                    String[] sidktu = getStringArray(sidkotau);
                    final String[] sktu = getStringArray(skotau);
                    String[] sida = getStringArray(sidpropa);
                    String[] sprpa = getStringArray(spropa);
                    String[] sidkta = getStringArray(sidkotaa);
                    final String[] skta = getStringArray(skotaa);
                    String[] sidwal = getStringArray(sidpropwal);
                    String[] sprpwal = getStringArray(spropwal);
                    String[] sidktwal = getStringArray(sidkotawal);
                    final String[] sktwal = getStringArray(skotawal);
                    final String[] idu = getStringArray(idpropu);
                    final String[] ida = getStringArray(idpropa);
                    final String[] idwal = getStringArray(idpropwal);
                    String[] prpu = getStringArray(propu);
                    String[] prpa = getStringArray(propa);
                    String[] prpwal = getStringArray(propwal);
                    ArrayAdapter<String> a = new ArrayAdapter<String>(editprofil.this, android.R.layout.simple_spinner_item, prpu);
                    final ArrayAdapter<String> b = new ArrayAdapter<String>(editprofil.this, android.R.layout.simple_spinner_item, prpa);
                    ArrayAdapter<String> c = new ArrayAdapter<String>(editprofil.this, android.R.layout.simple_spinner_item, prpwal);
                    spinnerpropus.setAdapter(a);
                    spinnerpropus.setSelection(getIndex(spinnerpropus, sprpu[0]));
                    spinnerpropal.setAdapter(b);
                    spinnerpropal.setSelection(getIndex(spinnerpropal, sprpa[0]));
                    spinnerwali.setAdapter(c);
                    spinnerwali.setSelection(getIndex(spinnerwali, sprpwal[0]));
                    spinnerpropus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String idpus = idu[position];
                            getKota(idpus);
                            /*ArrayAdapter<String>a=new ArrayAdapter<String>(editprofil.this,android.R.layout.simple_spinner_item,kotdf);
                            spinnerkota.setAdapter(a);
                            spinnerkota.setSelection(getIndex(spinnerkota2,sktu[0]));*/
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    spinnerpropal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String idpa = ida[position];
                            getKota1(idpa);
                           /* ArrayAdapter<String>a=new ArrayAdapter<String>(editprofil.this,android.R.layout.simple_spinner_item,kotdf);
                            spinnerkota1.setAdapter(a);
                            spinnerkota1.setSelection(getIndex(spinnerkota2,skta[0]));*/
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    spinnerwali.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String idpwal = idwal[position];
                            getKota2(idpwal);
                           /* ArrayAdapter<String>a=new ArrayAdapter<String>(editprofil.this,android.R.layout.simple_spinner_item,kotdf);
                            spinnerkota2.setAdapter(a);
                            spinnerkota2.setSelection(getIndex(spinnerkota2,sktwal[0]));*/
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERRORpr", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(json);
    }

    private int getIndex(SearchableSpinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }
        return 0;
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
                    JSONArray kota = response.getJSONArray("datalhr");
                    for (int i = 0; i < kota.length(); i++) {
                        JSONObject j = kota.getJSONObject(i);
                        HashMap<String, String> map1 = new HashMap<String, String>();
                        map1 = new HashMap<String, String>();
                        map1.put("id", j.getString("id"));
                        map1.put("nama", j.getString("nama"));
                        map1.put("tgllhr", j.getString("tanggal_lahir"));
                        map1.put("alamat", j.getString("alamat"));
                        map1.put("nohp", j.getString("no_hp"));
                        map1.put("email", j.getString("email"));
                        map1.put("nmortu", j.getString("nama_ortu"));
                        map1.put("almtortu", j.getString("alamat_ortu"));
                        map1.put("nohportu", j.getString("no_hportu"));
                        map1.put("foto", j.getString("foto"));
                        map1.put("pass", j.getString("password"));
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
                            .load(foto_image + list_data.get(0).get("foto"))
                            .placeholder(R.drawable.ic_account)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)//.placeholder(R.drawable.ic_account)
                            .skipMemoryCache(true)
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

    /* public void getData(String username) {

         JSONObject request = new JSONObject();
         try {
             request.put(KEY_USERNAME, username);
         } catch (JSONException e) {
             e.printStackTrace();
         }
         JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                 data_url, request, new Response.Listener<JSONObject>() {
             @Override
             public void onResponse(JSONObject response) {
                 try {
                     JSONArray kota = response.getJSONArray("datalhr");
                     JSONArray kota1 = response.getJSONArray("dataalmt");
                     JSONArray kota2 = response.getJSONArray("datawali");
                     for (int i = 0; i < kota.length(); i++) {
                         JSONObject j = kota.getJSONObject(i);
                         JSONObject a = kota1.getJSONObject(i);
                         JSONObject b = kota2.getJSONObject(i);
                         HashMap<String, String> map1 = new HashMap<String, String>();
                         map1 = new HashMap<String, String>();
                        map1.put("kotalhr", j.getString("kota"));
                        map1.put("idlhrkota", j.getString("kota_lahir"));
                         map1.put("idlhrprop", j.getString("prop_lahir"));
                         map1.put("proplhr", j.getString("prop"));
                         map1.put("kotaalmt", a.getString("kota"));
                         map1.put("idkotaalmt", a.getString("id_kota"));
                         map1.put("idpropalmt", a.getString("id_prop"));
                         map1.put("propalmt", a.getString("prop"));
                         map1.put("kotaortu", b.getString("kota"));
                         map1.put("idkotaortu", b.getString("id_kota"));
                         map1.put("idproportu", b.getString("id_prop"));
                         map1.put("proportu", b.getString("prop"));
                         list_data4.add(map1);


                     }
                     final SimpleAdapter s4dap;
                     final String idpropus=list_data4.get(0).get("idlhrprop");
                     String idkotaus=list_data4.get(0).get("idlhrkota");
                     String idpropal=list_data4.get(0).get("idpropalmt");
                     String idkotaal=list_data4.get(0).get("idkotaalmt");
                     String idpropwali=list_data4.get(0).get("idortuprop");
                     String idkotawali=list_data4.get(0).get("idortukota");
                     final SimpleAdapter[] s4dap1 = new SimpleAdapter[1];
                     if (idpropus != null&&idpropal != null&&idpropwali != null) {
                             final SimpleAdapter s4dap2;
                             final SimpleAdapter s4dap3;
                             final SimpleAdapter[] s4dap4 = new SimpleAdapter[1];
                             final SimpleAdapter[] s4dap5 = new SimpleAdapter[1];
                             final SimpleAdapter[] s4dap6 = new SimpleAdapter[1];
                             final SimpleAdapter[] s4dap7 = new SimpleAdapter[1];
                             final SimpleAdapter[] s4dap8 = new SimpleAdapter[1];
                             s4dap = new SimpleAdapter(editprofil.this, list_data4, R.layout.prop, new String[]{"idlhrprop", "proplhr"}, new int[]{R.id.idprop, R.id.nmprop});
                             spinnerpropus.setAdapter(s4dap);
                             spinnerpropus.setSelection(0);
                             s4dap2 = new SimpleAdapter(editprofil.this, list_data4, R.layout.prop, new String[]{"idpropalmt", "propalmt"}, new int[]{R.id.idprop, R.id.nmprop});
                             spinnerpropal.setAdapter(s4dap2);
                             spinnerpropal.setSelection(0);
                             s4dap3 = new SimpleAdapter(editprofil.this, list_data4, R.layout.prop, new String[]{"idproportu", "proportu"}, new int[]{R.id.idprop, R.id.nmprop});
                             spinnerwali.setAdapter(s4dap3);
                             spinnerwali.setSelection(0);
                             spinnerwali.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                 @Override
                                 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                     MyArrList2.clear();
                                     final SimpleAdapter s4dap11;
                                     s4dap11 = new SimpleAdapter(editprofil.this, list_data4, R.layout.columnkota, new String[]{"idkotaortu", "kotaortu"}, new int[]{R.id.idkota, R.id.nmkota});
                                     spinnerkota2.setAdapter(s4dap11);
                                     spinnerkota2.setSelection(0);

                                 }

                                 @Override
                                 public void onNothingSelected(AdapterView<?> parent) {

                                 }
                             });
                             spinnerpropal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                 @Override
                                 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                     MyArrList1.clear();
                                     final SimpleAdapter s4dap10;
                                     s4dap10 = new SimpleAdapter(editprofil.this, list_data4, R.layout.columnkota, new String[]{"idkotaalmt", "kotaalmt"}, new int[]{R.id.idkota, R.id.nmkota});
                                     spinnerkota1.setAdapter(s4dap10);
                                     spinnerkota1.setSelection(0);
                                 }

                                 @Override
                                 public void onNothingSelected(AdapterView<?> parent) {

                                 }
                             });
                             spinnerpropus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                 @Override
                                 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                     MyArrList.clear();
                                     final SimpleAdapter s4dap9;
                                     // Refresh Spinner

                                     s4dap9 = new SimpleAdapter(editprofil.this, list_data4, R.layout.columnkota, new String[]{"idlhrkota", "kotalhr"}, new int[]{R.id.idkota, R.id.nmkota});
                                     spinnerkota.setAdapter(s4dap9);
                                     spinnerkota.setSelection(0);
                                 }

                                 @Override
                                 public void onNothingSelected(AdapterView<?> parent) {
                                 }
                             });

                             imgpropus.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     String prov = spinnerpropus.getSelectedItem().toString();
                                     String prov1 = prov.substring(prov.indexOf("id=")+3,prov.indexOf("}"));
                                     getKota(prov1);
                                     s4dap1[0] = new SimpleAdapter(editprofil.this, list_data1, R.layout.prop, new String[]{"id", "prop"}, new int[]{R.id.idprop, R.id.nmprop});
                                     spinnerpropus.setAdapter(s4dap1[0]);
                                    // Toast.makeText(editprofil.this,idpropus, Toast.LENGTH_SHORT).show();
                                 }
                             });
                             imgpropal.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     String prov = spinnerpropal.getSelectedItem().toString();
                                     String prov1 = prov.substring(prov.indexOf("id=")+3,prov.indexOf("}"));
                                     getKota(prov1);
                                     s4dap4[0] = new SimpleAdapter(editprofil.this, list_data2, R.layout.prop, new String[]{"id", "prop"}, new int[]{R.id.idprop, R.id.nmprop});
                                     spinnerpropal.setAdapter(s4dap4[0]);
                                     //Toast.makeText(editprofil.this,spinnerpropus.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                                 }
                             });
                             imgpropwali.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     String prov = spinnerwali.getSelectedItem().toString();
                                     String prov1 = prov.substring(prov.indexOf("id=")+3,prov.indexOf("}"));
                                     getKota(prov1);
                                     s4dap5[0] = new SimpleAdapter(editprofil.this, list_data3, R.layout.prop, new String[]{"id", "prop"}, new int[]{R.id.idprop, R.id.nmprop});
                                     spinnerwali.setAdapter(s4dap5[0]);
                                   //  Toast.makeText(editprofil.this,spinnerpropus.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                                 }

                             });
                             imgkotaus.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     s4dap6[0] = new SimpleAdapter(editprofil.this, MyArrList, R.layout.prop, new String[]{"id", "kota"}, new int[]{R.id.idprop, R.id.nmprop});
                                     spinnerkota.setAdapter(s4dap6[0]);
                                     // Toast.makeText(editprofil.this,spinnerpropus.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                                 }
                             });
                             imgkotaal.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {

                                     s4dap7[0] = new SimpleAdapter(editprofil.this, MyArrList1, R.layout.prop, new String[]{"id", "kota"}, new int[]{R.id.idprop, R.id.nmprop});
                                     spinnerkota1.setAdapter(s4dap7[0]);
                                     //  Toast.makeText(editprofil.this,spinnerpropus.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                                 }
                             });
                             imgkotawali.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     s4dap8[0] = new SimpleAdapter(editprofil.this, MyArrList2, R.layout.prop, new String[]{"id", "kota"}, new int[]{R.id.idprop, R.id.nmprop});
                                     spinnerkota2.setAdapter(s4dap8[0]);
                                     //  Toast.makeText(editprofil.this,spinnerpropus.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                                 }});
                         }
                     else{
                         final SimpleAdapter s4dap2;
                         final SimpleAdapter s4dap3;
                         final SimpleAdapter s4dap4;
                         final SimpleAdapter s4dap5;
                         final SimpleAdapter s4dap6;
                         s4dap = new SimpleAdapter(editprofil.this, list_data1, R.layout.prop, new String[]{"id", "prop"}, new int[]{R.id.idprop, R.id.nmprop});
                         spinnerpropus.setAdapter(s4dap);
                         s4dap2 = new SimpleAdapter(editprofil.this, list_data2, R.layout.prop, new String[]{"id", "prop"}, new int[]{R.id.idprop, R.id.nmprop});
                         spinnerpropal.setAdapter(s4dap2);
                         s4dap3 = new SimpleAdapter(editprofil.this, list_data3, R.layout.prop, new String[]{"id", "prop"}, new int[]{R.id.idprop, R.id.nmprop});
                         spinnerwali.setAdapter(s4dap3);
                         s4dap4 = new SimpleAdapter(editprofil.this, MyArrList, R.layout.columnkota, new String[]{"id", "kota"}, new int[]{R.id.idkota, R.id.nmkota});
                         spinnerkota.setAdapter(s4dap4);
                         s4dap5 = new SimpleAdapter(editprofil.this, MyArrList1, R.layout.columnkota, new String[]{"id", "kota"}, new int[]{R.id.idkota, R.id.nmkota});
                         spinnerkota1.setAdapter(s4dap5);
                         s4dap6 = new SimpleAdapter(editprofil.this, MyArrList2, R.layout.columnkota, new String[]{"id", "kota"}, new int[]{R.id.idkota, R.id.nmkota});
                         spinnerkota2.setAdapter(s4dap6);

                     }




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
                Random angkaacak= new Random();
                int a= angkaacak.nextInt(1000);
                String ac= Integer.toString(a);
                params.put("id", idbr);
                params.put(KEY_NAMA, namabr);
                params.put(KEY_USERNAME, usernamems.getText().toString());
                params.put("foto", usernamems.getText().toString()+(ac));
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





