package com.example.logbookkoas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class profilds extends AppCompatActivity {
    Button editprofile;
    private SessionHandler session;
    Button logout,informasi;
    private static final String KEY_USERNAME = "username";
    TextView username1, nama1,bagiansel;
    ArrayList<String> idilmu = new ArrayList<String>();
    ArrayList<String> bagianilmu = new ArrayList<String>();
    ArrayList<HashMap<String, String>> list_data = new ArrayList<HashMap<String, String>>();
    ArrayList<String> MyArrList = new ArrayList<String>();
    String url = "http://192.168.0.104/logbook/getidbagian.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilds);
        final SessionHandler session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        username1 = findViewById(R.id.usernamedsp);
        nama1 = findViewById(R.id.namadsp);
        informasi = findViewById(R.id.informasi);
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
        informasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://logbook.fk.undip.ac.id/koas/informasi.php"));
                String title = "Pilih browser untuk membuka  tautan";
                Intent chooser = Intent.createChooser(intent, title);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }

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
