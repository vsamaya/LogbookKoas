package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class profilds extends AppCompatActivity {
    Button editprofile;
    private SessionHandler session;
    Button logout;
    private static final String KEY_USERNAME = "username";
    TextView username1, nama1,bagiansel;
    ArrayList<String> idilmu = new ArrayList<String>();
    ArrayList<String> bagianilmu = new ArrayList<String>();
    ArrayList<HashMap<String, String>> list_data = new ArrayList<HashMap<String, String>>();
    ArrayList<String> MyArrList = new ArrayList<String>();
    String url = "http://192.168.1.4/logbook1/getidbagian.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilds);
        final SessionHandler session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        username1 = findViewById(R.id.usernamedsp);
        nama1 = findViewById(R.id.namadsp);
        Bundle bundleds = getIntent().getExtras();
        assert bundleds != null;
        username1.setText(bundleds.getString("datads"));
        nama1.setText(bundleds.getString("datads1"));
        editprofile = findViewById(R.id.editds);
        logout = findViewById(R.id.logoutds);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundleds=new Bundle();
                bundleds.putString("datads",username1.getText().toString());
                bundleds.putString("datads1",nama1.getText().toString());
                Intent edit = new Intent(profilds.this, editprofilds.class);
                edit.putExtras(bundleds);
                startActivity(edit);


            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                Intent edit = new Intent(profilds.this, MainActivity.class);
                startActivity(edit);
                finish();

            }
        });
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
