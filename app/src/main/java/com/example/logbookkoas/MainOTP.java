package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainOTP extends AppCompatActivity {
    private static final String KEY_STATUS = "status";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_MESSAGE = "message";
    private String setNull_url = "http://192.168.200.31/setNullOtp.php";
    private SessionHandler session;


    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main_otp);
        setNull();
    }

    public void generate(View view) {
        Intent a = new Intent(MainOTP.this,OTP.class);
        startActivity(a);
    }

    private void setNull() {
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            session = new SessionHandler(getApplicationContext());
            User user = session.getUserDetails();
            String username = user.getUsername();
            request.put(KEY_USERNAME, username);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, setNull_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if (response.getInt(KEY_STATUS) == 0) {

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