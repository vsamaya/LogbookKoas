package com.example.logbookkoas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import cyd.awesome.material.AwesomeText;
import cyd.awesome.material.FontCharacterMaps;

public class editprofilds extends Activity {
    private static final String KEY_STATUS = "status";
    private static final String KEY_MENU = "level";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_NAMA = "nama";
    private static final String KEY_GELAR = "gelar";
    private static final String KEY_BAGIAN = "bagian";
    private static final String KEY_EMPTY = "";
    private String simpan_url = "http://192.168.0.104/android/updateprofilds.php";
    private String data_url = "http://192.168.0.104/android/getdatads.php";
    String url = "http://192.168.0.104/android/getidbagian.php";
    private String username;
    private String passwordbr;
    private String namabr;
    private String gelarbr;
    private String bagianbr;
    String[] idilm1 = new String[]{"coba", "coba1", "coba2"};
    private String baru;
    private ProgressDialog pDialog;
    private SessionHandler session;
    EditText usernamet, passwordt, namat, gelart;
    Button simpan;
    String id_sis1;
    String sName;
    Spinner coba;
    TextView usernametx, namatx, namaed, bagiant, gelartx, idcb, bagiancb;
    SearchableSpinner spin;
    ArrayList<String> MyArrList = new ArrayList<String>();
    ArrayList<HashMap<String, String>> list_data1 = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> list_data = new ArrayList<HashMap<String, String>>();
    ArrayList<String> idilmu = new ArrayList<String>();
    ArrayList<String> bagianilmu = new ArrayList<String>();
    boolean pwd_status = true;
    AwesomeText imgShowhidepassword;
    ImageView clr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofilds);
        spin = findViewById(R.id.bgnbr);
        simpan = findViewById(R.id.simpan);
        usernamet = (EditText) findViewById(R.id.username);
        passwordt = (EditText) findViewById(R.id.pass);
        usernametx = findViewById(R.id.usernamedos);
        namatx = findViewById(R.id.namads);
        namaed = findViewById(R.id.editnmds);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        gelartx = findViewById(R.id.gelards);
        Bundle bundle = getIntent().getExtras();
        usernametx.setText(bundle.getString("datads"));
        namaed.setText(bundle.getString("datads1"));
        namat = findViewById(R.id.editnmds);
        gelart = (EditText) findViewById(R.id.gelarbrds);
        bagiant = (TextView) findViewById(R.id.ColCustomerID);
        getData1(usernametx.getText().toString());
        getBagian();

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_sis1 = new String();
                String[] idilm = getStringArray(idilmu);
                id_sis1=idilm[spin.getSelectedItemPosition()];
               // Toast.makeText(editprofilds.this, id_sis1, Toast.LENGTH_SHORT).show();
                if(passwordt.getText().toString().equals("")){
                    passwordbr = list_data.get(0).get("pass");
                }
                else{
                    passwordbr = md5(passwordt.getText().toString());
                }
                namabr = namat.getText().toString();
                gelarbr = gelart.getText().toString();
                if (validateInputs()) {
                    getsimpan();
                    Handler h =new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            Intent i = new Intent(editprofilds.this, DosenActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }
                    };
                    h.sendEmptyMessageDelayed(0,1000);

                }
            }
        });
        imgShowhidepassword = (AwesomeText) findViewById(R.id.ImgShowPasswordds);
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

    }

    private boolean validateInputs() {
        if (KEY_EMPTY.equals(namabr)) {
            namat.setError("Username cannot be empty");
            namat.requestFocus();
            return false;
        }
        return true;
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

    private void getBagian() {
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
                url, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray kota = response.getJSONArray("idbagian");
                    JSONArray kota1 = response.getJSONArray("idhsl");
                    idilmu.clear();
                    bagianilmu.clear();
                    for (int i = 0; i < kota.length(); i++) {
                        JSONObject j = kota.getJSONObject(i);
                        // JSONObject k = kota1.getJSONObject(i);
                     /*   HashMap<String,String> map;
                        map=new HashMap<String,String>();
                        map.put("id",j.getString("id"));
                        map.put("bgn",j.getString("bagian"));
                        map.put("bgn1",k.getString("bagian"));*/
                        String id = j.getString("id");
                        String bagian = j.getString("bagian");
                        //   String bgnhsl= k.getString("bagian");
                        idilmu.add(id);
                        bagianilmu.add(bagian);
                        //   MyArrList.add(bgnhsl);
                        //  list_data.add(map);

                    }
                    for (int i = 0; i < kota1.length(); i++) {
                         JSONObject k = kota1.getJSONObject(i);
                         String bgnhsl= k.getString("bagian");
                           MyArrList.add(bgnhsl);
                        //  list_data.add(map);

                    }
                    String[] bgnilmu = getStringArray(bagianilmu);
                    String[] bgnhsl = getStringArray(MyArrList);
                   //   idcb.setText(idilm[2]);
                    //   bagiancb.setText(bgnilmu[1]);
                    ArrayAdapter<String> bgnarr = new ArrayAdapter<String>(editprofilds.this, android.R.layout.simple_spinner_item, bgnilmu);
                    spin.setAdapter(bgnarr);
                     spin.setTitle("Pilih Bagian");
                   // String bgn= MyArrList.get(0);
                    spin.setSelection(getIndex(spin,bgnhsl[0]));
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

    public String getJSONUrl(String url) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Download OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    private void getsimpan() {
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            session = new SessionHandler(getApplicationContext());
            User user = session.getUserDetails();
            String username = user.getUsername();
            request.put(KEY_USERNAME, usernametx.getText().toString());
            request.put(KEY_PASSWORD, passwordbr);
            request.put(KEY_NAMA, namabr);
            request.put(KEY_GELAR, gelarbr);
            request.put(KEY_BAGIAN, id_sis1);

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

    public void getData1(String username) {

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
                    JSONArray kota = response.getJSONArray("datads");
                    JSONArray kota1 = response.getJSONArray("pass");
                    for (int i = 0; i < kota.length(); i++) {
                        JSONObject j = kota.getJSONObject(i);
                        HashMap<String, String> map1 = new HashMap<String, String>();
                        map1 = new HashMap<String, String>();
                        map1.put("nama", j.getString("nama"));
                        map1.put("gelar", j.getString("gelar"));
                        list_data1.add(map1);
                    }
                    for (int i = 0; i < kota1.length(); i++) {
                        JSONObject k = kota1.getJSONObject(i);
                        HashMap<String, String> map2 = new HashMap<String, String>();
                        map2 = new HashMap<String, String>();
                        map2.put("pass", k.getString("password"));
                        list_data.add(map2);
                    }

                    //  namat.setText(list_data1.get(0).get("nama"));
                    gelart.setText(list_data1.get(0).get("gelar"));
                  //  passwordt.setText(list_data.get(0).get("pass"));

                    // HashMap <String,String> map= list_data1.get(0);
                    //     Toast.makeText(editprofilds.this, id, Toast.LENGTH_SHORT).show();
                    //   Toast.makeText(editprofilds.this, bgn, Toast.LENGTH_SHORT).show();
                  /*  final SimpleAdapter[] s4dap1 = new SimpleAdapter[1];
                    final SimpleAdapter s4dap;
                    s4dap = new SimpleAdapter(editprofilds.this, list_data1, R.layout.column, new String[]{"id", "bagian"}, new int[]{R.id.ColCustomerID, R.id.ColName});
                    spin.setAdapter(s4dap);
                    spin.setSelection(0,false);
                    clr.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    s4dap1[0] = new SimpleAdapter(editprofilds.this, MyArrList, R.layout.column, new String[]{"id", "bagian"}, new int[]{R.id.ColCustomerID, R.id.ColName});
                                    spin.setAdapter(s4dap1[0]);
                                }
                            });*/


                    //   int index=MyArrList.get(0).get("kdbagian").indexOf(value);
                    //   spin.setSelection(Integer.parseInt(Objects.requireNonNull(list_data1.get(0).get("kdbagian"))));
                    //String value=MyArrList.get(0).get(spin.getSelectedItemPosition();


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

    private int getIndex(SearchableSpinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }
        return 0;
    }

    private static String[] getStringArray(ArrayList<String> arr) {
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

// Permission StrictMode


// spinner1
