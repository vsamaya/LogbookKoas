package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.v4.app.INotificationSideChannel;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

public class TampilanDaftarKegiatan extends AppCompatActivity {
    ListView rvdafkeg;
    ArrayList<HashMap<String, String>> rv_dafkeg;
    SessionHandler session;
    TextView appr,unapp;
    private static final String KEY_USERNAME = "username";
    private static final String KEY_NIM = "nim";
    private static final String KEY_STASE = "stase";
    private static final String KEY_JENIS_JURNAL = "jenisJurnal";
    private static final String KEY_STATUS = "status";
    TextView tv_coba;
    String showURL = "http://192.168.1.6/logbook/daftar_kegiatan_dosen.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampilan_daftar_kegiatan);

        rv_dafkeg = new ArrayList<HashMap<String, String>>();
        rvdafkeg = findViewById(R.id.rv_daftarkegiatan);



        getArray();
    }

    private void getArray() {
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            session = new SessionHandler(getApplicationContext());
            User user = session.getUserDetails();
            String username = user.getUsername();
            String nim = getIntent().getStringExtra("nim");
            String stase = getIntent().getStringExtra("stase");
            String jns_jurnal = getIntent().getStringExtra("jenis_jurnal");
            String status = getIntent().getStringExtra("status");
            request.put(KEY_USERNAME, username);
            request.put(KEY_NIM, nim);
            request.put(KEY_JENIS_JURNAL,jns_jurnal);
            request.put(KEY_STASE, stase);
            request.put(KEY_STATUS, status);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                showURL,request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray lain = response.getJSONArray("lain");
                    JSONArray nama = response.getJSONArray("nama");
                    JSONArray kegiatan = response.getJSONArray("kegiatan");
                    JSONArray lokasi = response.getJSONArray("lokasi");
                    JSONArray penyakit1 = response.getJSONArray("penyakit1");
                    JSONArray penyakit2 = response.getJSONArray("penyakit2");
                    JSONArray penyakit3 = response.getJSONArray("penyakit3");
                    JSONArray penyakit4 = response.getJSONArray("penyakit4");
                    for (int i = 0; i < lain.length(); i++) {
                        JSONObject j = lain.getJSONObject(i);
                        JSONObject j_nama = nama.getJSONObject(i);
                        JSONObject j_kegiatan = kegiatan.getJSONObject(i);
                        JSONObject j_lokasi = lokasi.getJSONObject(i);
                        JSONObject j_p1 = penyakit1.getJSONObject(i);
                        JSONObject j_p2 = penyakit2.getJSONObject(i);
                        JSONObject j_p3 = penyakit3.getJSONObject(i);
                        JSONObject j_p4 = penyakit4.getJSONObject(i);
                        HashMap<String, String> item = new HashMap<String, String>();
                        item.put("nim", j.getString("nim"));
                        item.put("tanggal",j.getString("tanggal"));
                        item.put("waktu",j.getString("jam_awal")+"-"+j.getString("jam_akhir"));
                        if(j.getString("status").equals("1")){
                            item.put("status","Approved");
                        }else item.put("status","Unapproved");
                        item.put("nama", j_nama.getString("nama"));
                        item.put("kegiatan", j_kegiatan.getString("kegiatan"));
                        item.put("level",j_kegiatan.getString("level"));
                        item.put("kegiatan_dosen",j_kegiatan.getString("kegiatan_dosen"));
                        item.put("kategori",j_kegiatan.getString("kategori"));
                        item.put("lokasi", j_lokasi.getString("lokasi"));
                        item.put("p1", j_p1.getString("penyakit").toUpperCase()+
                                "("+j_p1.getString("skdi_level")+"-"+j_p1.getString("sumber")+")");

                        if(j_p2.getString("penyakit").equals("null")){
                            item.put("p2"," ");

                        }else{
                            item.put("p2",j_p2.getString("penyakit").toUpperCase()+
                                    "("+j_p2.getString("skdi_level")+"-"
                                    +j_p2.getString("sumber")+")");
                        }
                        if(j_p3.getString("penyakit").equals("null")){
                            item.put("p3"," ");
                        }else{
                            item.put("p3",j_p3.getString("penyakit").toUpperCase()
                                    +"("+j_p3.getString("skdi_level")+"-"
                                    +j_p3.getString("sumber")+")");
                        }
                        if(j_p4.getString("penyakit").equals("null")){
                            item.put("p4"," ");
                        }else{
                            item.put("p4",j_p4.getString("penyakit").toUpperCase()
                                    +"("+j_p4.getString("skdi_level")+"-"
                                    +j_p4.getString("sumber")+")");
                        }

                        rv_dafkeg.add(item);

                    }

//
//
//                    }
                    ListAdapter adapter =new SimpleAdapter(
                            getApplicationContext(), rv_dafkeg,R.layout.recycler_view_dafkeg,
                            new String[]{"nama","nim","tanggal","waktu","kegiatan","level",
                                    "kegiatan_dosen","kategori","lokasi","p1","p2","p3","p4","status"},
                            new int[]{R.id.namamhsw_dafkeg,R.id.nim_dafkeg,R.id.tgl_dafkeg,
                                    R.id.waktu_dafkeg,R.id.keg_dafkef,R.id.lvkeg_dafkef,R.id.keg_dosen_dafkef,
                                    R.id.kategori_dafkef,R.id.lokasi_dafkef,R.id.p1_dafkeg,R.id.p2_dafkeg,
                                    R.id.p3_dafkeg,R.id.p4_dafkeg,R.id.status}
                    )
                    {
                        @Override
                        public View getView (int position, View convertView, ViewGroup parent)
                        {
                            View v = super.getView(position, convertView, parent);

                            final TextView status=(TextView) v.findViewById(R.id.status);
                            final TextView statuslain=(TextView) v.findViewById(R.id.another_status);
                            final TextView show_more=(TextView) v.findViewById(R.id.show_more);
                            final LinearLayout kegiatan=(LinearLayout) v.findViewById(R.id.layout_keiatan);
                            final LinearLayout lokasi=(LinearLayout) v.findViewById(R.id.layout_lokasi);
                            final LinearLayout lv_keg=(LinearLayout) v.findViewById(R.id.layout_level);
                            final LinearLayout keg_dosen=(LinearLayout) v.findViewById(R.id.layout_keg_dosen);
                            final LinearLayout kategori=(LinearLayout) v.findViewById(R.id.layout_kategori);
                            final LinearLayout penyakit=(LinearLayout) v.findViewById(R.id.layout_penyakit);

//                            final TextView show_less=(TextView) v.findViewById(R.id.show_less);


                            if(status.getText().equals("Approved")){
                                statuslain.setText("Unapprove");
                                statuslain.setBackgroundResource(R.drawable.background_unapproved);
                            }else {
                                statuslain.setText("Approve");
                                statuslain.setBackgroundResource(R.drawable.background_approved);
                            }

                            statuslain.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View arg0) {
                                    if(status.getText().equals("Approved")){
                                        status.setText("Unapproved");
                                        statuslain.setText("Approve");
                                        statuslain.setBackgroundResource(R.drawable.background_approved);

                                    }else {
                                        status.setText("Approved");
                                        statuslain.setText("Unapprove");
                                        statuslain.setBackgroundResource(R.drawable.background_unapproved);
                                    }

                                }
                            });
                            show_more.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (show_more.getText().equals("show more")) {
                                        kegiatan.setVisibility(View.VISIBLE);
                                        lokasi.setVisibility(View.VISIBLE);
                                        lv_keg.setVisibility(View.VISIBLE);
                                        keg_dosen.setVisibility(View.VISIBLE);
                                        kategori.setVisibility(View.VISIBLE);
                                        penyakit.setVisibility(View.VISIBLE);
                                        show_more.setText("show less");
                                    }else {
                                        kegiatan.setVisibility(View.GONE);
                                        lokasi.setVisibility(View.GONE);
                                        lv_keg.setVisibility(View.GONE);
                                        keg_dosen.setVisibility(View.GONE);
                                        kategori.setVisibility(View.GONE);
                                        penyakit.setVisibility(View.GONE);
                                        show_more.setText("show more");

                                    }

                                }
                            });

                            return v;
                        }


                    };
                    rvdafkeg.setAdapter(adapter);
//                    rvdafkeg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                            TextView app = view.findViewById(R.id.unapproved);
//                            Toast.makeText(getApplicationContext(), "selected Item Name is " + unapp.getText(), Toast.LENGTH_LONG).show();
//
//
//                        }
//                    });



                } catch (JSONException e) {
                    e.printStackTrace();
                }



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
