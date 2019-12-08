package com.example.logbookkoas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

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
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

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
    private String simpan_url = "http://192.168.43.93/setOtp.php";
    private String username;
    private String passwordbr;
    private String namabr;
    private String gelarbr;
    private String bagianbr;
    private ProgressDialog pDialog;
    private SessionHandler session;
    EditText usernamet,passwordt,namat,gelart;
    Button simpan;
    Spinner bagiant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofilds);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final Spinner spin = (Spinner)findViewById(R.id.bgnbr);
        simpan = findViewById(R.id.simpan);
        usernamet = (EditText) findViewById(R.id.username);
        passwordt = (EditText) findViewById(R.id.pass);
        session = new SessionHandler(getApplicationContext());
        User user= session.getUserDetails();
        namat = findViewById(R.id.editnmds);
        gelart = (EditText) findViewById(R.id.gelarbrds);



        String url = "http://192.168.43.93/getidbagian.php";

        try {

            JSONArray data = new JSONArray(getJSONUrl(url));

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("id", c.getString("id"));
                map.put("bagian", c.getString("bagian"));
                MyArrList.add(map);

            }
            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(editprofilds.this, MyArrList, R.layout.column,
                    new String[] {"id", "bagian"}, new int[] {R.id.ColCustomerID, R.id.ColName});
            spin.setAdapter(sAdap);

            final AlertDialog.Builder viewDetail = new AlertDialog.Builder(this);

            spin.setOnItemSelectedListener(new OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> arg0, View selectedItemView,
                                           int position, long id) {
                    String sCustomerID = MyArrList.get(position).get("id")
                            .toString();
                    String sName = MyArrList.get(position).get("bagian")
                            .toString();
                    viewDetail.setIcon(android.R.drawable.btn_star_big_on);
                    viewDetail.setTitle("Customer Detail");
                    viewDetail.setMessage("id : " + sCustomerID + "\n"
                            + "bagian : " + sName + "\n" );
                    viewDetail.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();
                                }
                            });
                    viewDetail.show();

                }

                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                    Toast.makeText(editprofilds.this,
                            "Your Selected : Nothing",
                            Toast.LENGTH_SHORT).show();
                }
            });

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        //Retrieve the data entered in the edit texts
                        passwordbr = passwordt.getText().toString();
                        namabr = namat.getText().toString();
                        gelarbr = gelart.getText().toString();
                        bagianbr = spin.getSelectedItem().toString();

                        getsimpan();
            }
        });

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
            request.put(KEY_USERNAME, username);
            request.put(KEY_PASSWORD,passwordbr);
            request.put(KEY_NAMA, namabr);
            request.put(KEY_GELAR, gelarbr);
            request.put(KEY_BAGIAN, bagianbr);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST,simpan_url , request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if (response.getInt(KEY_STATUS) == 0) {
                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();


                            }
                            else{
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

}

    // Permission StrictMode


    // spinner1
