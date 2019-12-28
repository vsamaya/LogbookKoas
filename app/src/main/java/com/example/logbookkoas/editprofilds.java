package com.example.logbookkoas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.renderscript.Sampler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

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
    private String simpan_url = "http://192.168.1.9/updateprofilds.php";
    private String data_url = "http://192.168.1.9/getdatads.php";
    String url = "http://192.168.1.9/getidbagian.php";
    private String username;
    private String passwordbr;
    private String namabr;
    private String gelarbr;
    private String bagianbr;
    private String baru;
    private ProgressDialog pDialog;
    private SessionHandler session;
    TextView bagiant,gelartx;
    EditText usernamet, passwordt, namat, gelart;
    Button simpan;
    String sName;
    TextView usernametx,namatx,namaed;
    Spinner spin;
    ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> list_data1 = new ArrayList<HashMap<String, String>>();
    boolean pwd_status = true;
    AwesomeText imgShowhidepassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofilds);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        spin = (Spinner) findViewById(R.id.bgnbr);
        simpan = findViewById(R.id.simpan);
        usernamet = (EditText) findViewById(R.id.username);
        passwordt = (EditText) findViewById(R.id.pass);
        usernametx=findViewById(R.id.usernamedos);
        namatx=findViewById(R.id.namads);
        namaed=findViewById(R.id.editnmds);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        gelartx=findViewById(R.id.gelards);
        Bundle bundle = getIntent().getExtras();
        usernametx.setText(bundle.getString("datads"));
        namaed.setText(bundle.getString("datads1"));
        namat = findViewById(R.id.editnmds);
        gelart = (EditText) findViewById(R.id.gelarbrds);
        bagiant = (TextView) findViewById(R.id.ColCustomerID);
        getBagian();
        getData1(usernametx.getText().toString());
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordbr=passwordt.getText().toString();
                namabr=namat.getText().toString();
                gelarbr=gelart.getText().toString();
                bagianbr=spin.getSelectedItem().toString();
                baru=bagianbr.substring(4,9);
               if(validateInputs()){
                getsimpan();
                Intent i= new Intent(editprofilds.this,DosenActivity.class);
                startActivity(i);}
            }
        });
        imgShowhidepassword =(AwesomeText)findViewById(R.id.ImgShowPasswordds);
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
    private void getBagian() {
        JSONObject request = new JSONObject();
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                url, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray kota = response.getJSONArray("idbagian");
                    for (int i = 0; i < kota.length(); i++) {
                        JSONObject j = kota.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<>();
                        map = new HashMap<String, String>();
                        map.put("id", j.getString("id"));
                        map.put("bagian", j.getString("bagian"));
                        MyArrList.add(map);

                    }

                    SimpleAdapter s4dap;
                    s4dap = new SimpleAdapter(editprofilds.this, MyArrList, R.layout.column, new String[]{"id", "bagian"}, new int[]{R.id.ColCustomerID, R.id.ColName});
                    spin.setAdapter(s4dap);
                    spin.setPrompt("---select bagian baru---");
                }

             catch (JSONException e) {
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
            request.put(KEY_BAGIAN, baru);

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
                    for (int i = 0; i < kota.length(); i++) {
                        JSONObject j = kota.getJSONObject(i);
                        HashMap<String, String> map1 = new HashMap<String, String>();
                        map1 = new HashMap<String, String>();
                        map1.put("nama", j.getString("nama"));
                        map1.put("gelar", j.getString("gelar"));
                        map1.put("kdbagian", j.getString("kode_bagian"));
                        list_data1.add(map1);
                    }

                   // namat.setText(list_data1.get(0).get("nama"));
                    gelart.setText(list_data1.get(0).get("gelar"));
                    gelartx.setText(list_data1.get(0).get("gelar"));
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
    }

}

// Permission StrictMode


// spinner1
