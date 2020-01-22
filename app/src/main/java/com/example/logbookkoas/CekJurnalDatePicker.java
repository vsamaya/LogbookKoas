package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CekJurnalDatePicker extends AppCompatActivity {
EditText date_picker;
    private DatePickerDialog mDatePickerDialog;
    public static final String KEY_ID = "id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_jurnal_date_picker);
        date_picker = findViewById(R.id.date_picker);

        setDateTimeField();
        date_picker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDatePickerDialog.show();
                return false;
            }
        });

    }

    public void cek(View view) {

        String tgl = date_picker.getText().toString();
        if(tgl.trim().length() == 0){
            Toast.makeText(CekJurnalDatePicker.this, "Maaf tanggal masih kosong",
                    Toast.LENGTH_LONG).show();
        }
        else {
            Intent intent = getIntent();
            final String id = intent.getStringExtra(CekJurnal.KEY_ID);
            Intent a = new Intent(getApplicationContext(), showJurnal.class);
            a.putExtra("tgl", tgl);
            a.putExtra("id", id);
            startActivity(a);
        }
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
                final Date startDate = newDate.getTime();
                String fdate = sd.format(startDate);

                date_picker.setText(fdate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }
}
