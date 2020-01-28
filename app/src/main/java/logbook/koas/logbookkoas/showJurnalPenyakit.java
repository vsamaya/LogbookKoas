package logbook.koas.logbookkoas;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class showJurnalPenyakit extends AppCompatActivity {
    private static final String KEY_TANGGAL = "tanggal";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private SessionHandler session;
    ListView lv_penyakit;
    TextView empty;
    ArrayList<HashMap<String, String>> MyArr;
    final String url_penyakit = "https://logbook.fk.undip.ac.id/koas/android/penyakit.php";
    final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    final SimpleDateFormat convert = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_jurnal_penyakit);
        MyArr = new ArrayList<HashMap<String, String>>();
        lv_penyakit = findViewById(R.id.lv_penyakit);
        empty = findViewById(R.id.empty);
        penyakit();
    }

    private void penyakit(){
        JSONObject request = new JSONObject();
        try {
            Intent intent = getIntent();
            final String id = intent.getStringExtra("id");
            final String tgl = intent.getStringExtra("tgl");
            session = new SessionHandler(getApplicationContext());
            User user = session.getUserDetails();
            String username = user.getUsername();
            Date date = null;
            try {
                date = format.parse(tgl);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            final String tanggal = convert.format(date);
            request.put(KEY_USERNAME, username);
            request.put(KEY_ID, id);
            request.put(KEY_TANGGAL, tanggal);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                url_penyakit,request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray tmp = response.getJSONArray("tmp");
                    JSONArray kegiatan = response.getJSONArray("kegiatan");
                    JSONArray lokasi = response.getJSONArray("lokasi");
                    JSONArray kelas = response.getJSONArray("kelas");
                    JSONArray dosen = response.getJSONArray("dosen");
                    JSONArray penyakit1 = response.getJSONArray("penyakit1");
                    JSONArray penyakit2 = response.getJSONArray("penyakit2");
                    JSONArray penyakit3 = response.getJSONArray("penyakit3");
                    JSONArray penyakit4 = response.getJSONArray("penyakit4");
                    for (int i = 0; i < tmp.length(); i++) {
                        JSONObject j = tmp.getJSONObject(i);
                        JSONObject j_kegiatan = kegiatan.getJSONObject(i);
                        JSONObject j_lokasi = lokasi.getJSONObject(i);
                        JSONObject j_kelas = kelas.getJSONObject(i);
                        JSONObject j_dosen = dosen.getJSONObject(i);
                        JSONObject j_p1 = penyakit1.getJSONObject(i);
                        JSONObject j_p2 = penyakit2.getJSONObject(i);
                        JSONObject j_p3 = penyakit3.getJSONObject(i);
                        JSONObject j_p4 = penyakit4.getJSONObject(i);
                        HashMap<String, String> item = new HashMap<String, String>();
                        item.put("jam_awal", j.getString("jam_awal"));
                        item.put("jam_akhir", j.getString("jam_akhir"));
                        item.put("nama", j_dosen.getString("nama")+", "+j_dosen.getString("gelar"));
                        if (j.getString("status").equals("1")) {
                            item.put("status", "Approved");
                        } else item.put("status", "Unapproved");
                        item.put("kegiatan", j_kegiatan.getString("kegiatan"));
                        item.put("lokasi", j_lokasi.getString("lokasi"));
                        item.put("kelas", j_kelas.getString("kelas"));
                        item.put("p1", j_p1.getString("penyakit")+" ("+j_p1.getString("skdi_level")+"/"+j_p1.getString("k_level")+")");
                        if (j_p2.getString("penyakit").equals("null")) {
                            item.put("p2", " ");
                        } else {
                            item.put("p2", j_p2.getString("penyakit")+" ("+j_p2.getString("skdi_level")+"/"+j_p2.getString("k_level")+")");
                        }
                        if (j_p3.getString("penyakit").equals("null")) {
                            item.put("p3", " ");
                        } else {
                            item.put("p3", j_p3.getString("penyakit")+" ("+j_p3.getString("skdi_level")+"/"+j_p3.getString("k_level")+")");
                        }
                        if (j_p4.getString("penyakit").equals("null")) {
                            item.put("p4", " ");
                        } else {
                            item.put("p4", j_p4.getString("penyakit")+" ("+j_p4.getString("skdi_level")+"/"+j_p4.getString("k_level")+")");
                        }
                        MyArr.add(item);

                    }

                    if(MyArr.isEmpty()){
                        empty.setVisibility(View.VISIBLE);
                        lv_penyakit.setVisibility(View.GONE);
                    }else {
                        ListAdapter sAdap = new SimpleAdapter(showJurnalPenyakit.this, MyArr, R.layout.item_row_cek,
                                new String[] {"jam_awal","jam_akhir","lokasi","kelas","kegiatan","nama","status","p1","p2","p3","p4"},
                                new int[] {R.id.tv_jam,R.id.tv_jam2,R.id.tv_lokasi,R.id.tv_kelas,R.id.tv_kegiatan,
                                        R.id.tv_dosen,R.id.tv_status,R.id.tv_sumber1,R.id.tv_sumber2,
                                        R.id.tv_sumber3,R.id.tv_sumber4,})
                        {
                            @Override
                            public View getView (int position, View convertView, ViewGroup parent)
                            {
                                View v = super.getView(position, convertView, parent);

                                final TextView status = v.findViewById(R.id.tv_status);

                                if (status.getText().equals("Unapproved")) {
                                    status.setTextColor(getResources().getColor(R.color.md_red_500));
                                }

                                return v;
                            }
                        }
                                ;
                        lv_penyakit.setAdapter(sAdap);

                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(json);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}
