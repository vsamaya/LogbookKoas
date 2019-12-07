package com.example.logbookkoas;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class editprofilds extends Activity {
    Spinner sp;
    private static String URL="http://192.168.0.134/";
    ProgressDialog  pDialog;
    JSONArray JsonArraySiswa = null;
    List<String> valueId = new ArrayList<String>();
    List<String> valueBagian = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofilds);
        sp=(Spinner)findViewById(R.id.bgnbr);
    }
    public void LoadData(View v){
        // Menggunakan async task untuk "ngeload" data JSON
        new getDataBagian().execute();
    }



    private class getDataBagian extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(editprofilds.this);
            pDialog.setMessage("Mohon Tunggu...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //Membuat Service "ServiceHandler"
            ServiceHandler sh = new ServiceHandler();

            // Memanggil URL untuk mendapatkan respon data
            String jsonStr = sh.makeServiceCall(URL+"bagianManager.php?mode=getAllDataBagian", ServiceHandler.GET); //siswaManager.php?mode=getAllDataSiswa

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Mendapatkan data Array JSON
                    JsonArraySiswa = jsonObj.getJSONArray("values");

                    ArrayList<SpinnerData> listData = new ArrayList<SpinnerData>();
                    listData.clear();

                    //Melakukan perulangan untuk memecah data
                    for (int i = 0; i < JsonArraySiswa.length(); i++) {
                        JSONObject obj = JsonArraySiswa.getJSONObject(i);

                       SpinnerData spinnerData = new SpinnerData();
                        spinnerData.setId(obj.getString("id"));
                        spinnerData.setBagian(obj.getString("bagian"));
                        listData.add(spinnerData);

                        System.out.println("data "+spinnerData.getId());
                    }

                    valueId = new ArrayList<String>();
                    valueBagian = new ArrayList<String>();

                    for (int i = 0; i < listData.size(); i++) {
                        valueId.add(listData.get(i).getId());
                        valueBagian.add(listData.get(i).getBagian());

                        System.out.println("data 2 "+listData.get(i).getBagian());

                    }

                    System.out.println("id + "+valueId.get(0));



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();


            // Membuat adapter untuk spinner
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(editprofilds.this,
                    android.R.layout.simple_spinner_item, valueBagian);

            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            //Mengaitkan adapter spinner dengan spinner yang ada di layout
            sp.setAdapter(spinnerAdapter);
            sp.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    String idn = valueId.get(position);
                    String bagianbr = valueBagian.get(position);
                    Toast.makeText(editprofilds.this, "Anda memilih id: "+idn+", Bagian Baru: "+bagianbr, Toast.LENGTH_LONG).show();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });

        }

    }




}

