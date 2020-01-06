package com.example.logbookkoas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class mainIsiJurnal extends Activity {
    private SessionHandler session;
    public static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    ArrayList<HashMap<String, String>> MyArr;
    ListView lv_isi_jurnal;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat convert = new SimpleDateFormat("dd MMM yyyy");
    final String url_judul = "http://192.168.1.9/logbook/showalljurnal.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_isi_jurnal);
        lv_isi_jurnal = findViewById(R.id.lv_isi_jurnal);
        MyArr = new ArrayList<HashMap<String, String>>();
        showalljurnal();

        /*try {

            JSONArray data = new JSONArray(getJSONUrl(url_judul));

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);
                map = new HashMap<String, String>();
                map.put("kepaniteraan", c.getString("kepaniteraan"));
                map.put("id", c.getString("id"));
                MyArrList.add(map);
                String stase="stase_"+c.getString("kepaniteraan");
                String url_jadwal= "http://192.168.0.103/jadwal1.php?stase="+stase;
            }
            SimpleAdapter lAdap;
            lAdap = new SimpleAdapter(mainIsiJurnal.this, MyArrList, R.layout.item_row_jurnal,
                    new String[] {"kepaniteraan"}, new int[] {R.id.tv_judul});
            lv_isi_jurnal.setAdapter(lAdap);

            final AlertDialog.Builder viewDetail = new AlertDialog.Builder(this);

            lv_isi_jurnal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    HashMap<String, String> map = MyArrList.get(pos);
                    Intent i = new Intent(mainIsiJurnal.this,IsiJurnalDetail.class);
                    i.putExtra (KEY_ID,(map.get("id")));
                    startActivity(i);
                }
            });

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
    }

    /*public String getJSONUrl(String url) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Download OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str.toString();
    }
*/

    private void showalljurnal(){
        JSONObject request = new JSONObject();
        try {
            session = new SessionHandler(getApplicationContext());
            User user = session.getUserDetails();
            String username = user.getUsername();
            request.put(KEY_USERNAME, username);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json;
        json = new JsonObjectRequest(Request.Method.POST,
                url_judul,request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray tmp = response.getJSONArray("tmp");
                    JSONArray mulai = response.getJSONArray("mulai");
                    JSONArray selesai = response.getJSONArray("selesai");
                    for (int i = 0; i < tmp.length(); i++) {
                        JSONObject c = tmp.getJSONObject(i);
                        JSONObject cmulai = mulai.getJSONObject(i);
                        JSONObject cselesai = selesai.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("id", c.getString("id"));
                        map.put("kepaniteraan", c.getString("kepaniteraan"));
                        if(cmulai.getString("tgl_mulai").equals("null") || cselesai.getString("tgl_selesai").equals("null")){
                            map.put("tgl_mulai"," ");
                            map.put("tgl_selesai"," ");
                        } else {
                            Date tglMulai = format.parse(cmulai.getString("tgl_mulai"));
                            String tglMulaiText = convert.format(tglMulai);
                            Date tglSelesai = format.parse(cselesai.getString("tgl_selesai"));
                            String tglSelesaiText = convert.format(tglSelesai);
                            map.put("tgl_mulai", tglMulaiText);
                            map.put("tgl_selesai", tglSelesaiText);
                            Date tglSelesai1 = new Date(tglSelesai.getTime() + (1000 * 60 * 60 * 24));
                            Date now = Calendar.getInstance().getTime();
                            if(now.after(tglMulai) && now.before(tglSelesai1)){
                                map.put("status","Aktif");
                            }
                            else if(now.before(tglMulai)){
                                map.put("status","Belum Aktif");
                            }
                            else{
                                map.put("status", "Sudah Terlewat");
                            }
                        }

                        MyArr.add(map);
                    }
                    ListAdapter sAdap = new SimpleAdapter(mainIsiJurnal.this, MyArr, R.layout.item_row_jurnal,
                            new String[] {"kepaniteraan","tgl_mulai","tgl_selesai","status"},
                            new int[] {R.id.tv_judul,R.id.tv_tgl_mulai,R.id.tv_tgl_selesai,R.id.tv_status})
                    {
                        @Override
                        public View getView (int position, View convertView, ViewGroup parent)
                        {
                            View lv = super.getView(position, convertView, parent);
                            final TextView pembatas = lv.findViewById(R.id.pembatas);
                            final TextView status = lv.findViewById(R.id.tv_status);
                            final TextView tgl_mulai = lv.findViewById(R.id.tv_tgl_mulai);
                            final TextView tgl_selesai = lv.findViewById(R.id.tv_tgl_selesai);

                            if(status.getText().equals("Aktif")){
                                status.setBackgroundColor(getResources().getColor(R.color.aktif));
                            }
                            else if(status.getText().equals("Belum Aktif")){
                                status.setBackgroundColor(getResources().getColor(R.color.belum_aktif));
                            }
                            else if(status.getText().equals("Sudah Terlewat")) {
                                status.setBackgroundColor(getResources().getColor(R.color.terlewat));
                            }
                            else{
                                status.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                            }

                            return  lv;
                        }
                    };

                    lv_isi_jurnal.setAdapter(sAdap);
                    lv_isi_jurnal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                            HashMap<String, String> map = MyArr.get(pos);
                            Intent i = new Intent(mainIsiJurnal.this,IsiJurnalDetail.class);
                            i.putExtra (KEY_ID,(map.get("id")));
                            startActivity(i);
                        }
                    });
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ParseException e) {
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

}