package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;

public class about extends AppCompatActivity {
    TextView tahun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        tahun = findViewById(R.id.tahun);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        tahun.setText(Integer.toString(year));
    }
}
