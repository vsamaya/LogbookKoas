package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DaftarKegiatan extends FragmentActivity {
    String showURL = "http://192.168.1.6/logbook/daftar_kegiatan_dosen.php";
    RequestQueue requestQueue;
    ListView rvdafkeg;
    private static final String KEY_USERNAME = "username";
    Button btnfilter;
    SessionHandler session;
    private ProgressDialog pDialog;
    //    JSONArray result;
    TextView tv_coba;
    ArrayList<HashMap<String, String>> rv_dafkeg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kegiatan);
        rv_dafkeg = new ArrayList<HashMap<String, String>>();
        rvdafkeg = findViewById(R.id.rv_daftarkegiatan);
        btnfilter = findViewById(R.id.btn_filter);
        tv_coba = findViewById(R.id.cobajson);




        btnfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getArray();
            }
        });


    }


    private void getArray() {
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
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                showURL,request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jurnal_penyakit = response.getJSONArray("jurnal_penyakit");
                    HashMap<String, String> item = new HashMap<String, String>();
                    for (int i = 0; i < jurnal_penyakit.length(); i++) {
                        JSONObject j = jurnal_penyakit.getJSONObject(i);
                        String id = j.getString("id");
                        String nim = j.getString("nim");
                        String tanggal = j.getString("tanggal");
                        item.put("id",id);
                        item.put("nim", nim);
                        item.put("tanggal",tanggal);
                        rv_dafkeg.add(item);

                        tv_coba.append(id + " " + nim + " " + tanggal);

                    }
                    tv_coba.append("===\n");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ListAdapter adapter =new SimpleAdapter(
                        getApplicationContext(), rv_dafkeg,R.layout.recycler_view_dafkeg,
                        new String[]{"id","nim","tanggal"},
                        new int[]{R.id.namamhsw_dafkeg,R.id.nim_dafkeg,R.id.tgl_dafkeg}
                );
                rvdafkeg.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(), "Gagal mengambil data, silahkan cek koneksi Anda",Toast.LENGTH_LONG).show();


            }
        });
        MySingleton.getInstance(this).addToRequestQueue(json);

    }

}


