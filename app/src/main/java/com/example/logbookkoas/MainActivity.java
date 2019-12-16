package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import cyd.awesome.material.AwesomeText;
import cyd.awesome.material.FontCharacterMaps;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_STATUS = "status";
    private static final String KEY_MENU = "level";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMPTY = "";
    private String username;
    private String password;
    private ProgressDialog pDialog;
    private String login_url = "http://192.168.1.5/logbook/login.php";
    private SessionHandler session;
    EditText usernamet,passwordt;
    AwesomeText imgShowhidepassword;
    Button login;
    boolean pwd_status = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());

        if(session.isLoggedIn()){
            if(session.Dosen()){
                loadDashboardDosen();

            } else if(session.Mahasiswa()){
                loadDashboardMahasiswa();
            } else session.logoutUser();
        }
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.signin);
        usernamet = (EditText) findViewById(R.id.username);
        passwordt = (EditText) findViewById(R.id.password);
        imgShowhidepassword =(AwesomeText)findViewById(R.id.ImgShowPassword);
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
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                username = usernamet.getText().toString().trim();
                password = passwordt.getText().toString().trim();
                if (validateInputs()) {
                    login();
                }
            }
        });

    }

    private void loadDashboardDosen() {
        Intent i = new Intent(getApplicationContext(), DosenActivity.class);
        startActivity(i);
        finish();

    }

    private void loadDashboardMahasiswa() {
        Intent i = new Intent(getApplicationContext(), MahasiswaActivity.class);
        startActivity(i);
        finish();

    }

    private void login() {
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, username);
            request.put(KEY_PASSWORD, password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, login_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Check if user got logged in successfully

                            if (response.getInt(KEY_STATUS) == 0) {
                                session.loginUser(username, response.getString(KEY_MENU),
                                        response.getString("nama"));
                                loadDashboardDosen();

                            }else if (response.getInt(KEY_STATUS) == 3){
                                session.loginUser(username, response.getString(KEY_MENU),
                                        response.getString("nama"));
                                loadDashboardMahasiswa();
                            }else{
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

    /**
     * Validates inputs and shows error if any
     * @return
     */
    private boolean validateInputs() {
        if(KEY_EMPTY.equals(username)){
            usernamet.setError("Username cannot be empty");
            passwordt.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(password)){
            usernamet.setError("Password cannot be empty");
            passwordt.requestFocus();
            return false;
        }
        return true;
    }
/*
    public void onLogin(View view){
        String Username=usernamet.getText().toString();
        String Password=passwordt.getText().toString();
        String type = "login";
        String levelds="4";
        String levelms="5";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type,Username,Password,levelds,levelms);



    }

 */



}

