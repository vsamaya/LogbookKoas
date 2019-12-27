package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
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
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class showJurnal extends AppCompatActivity {
    private static final String KEY_TANGGAL = "tanggal";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private SessionHandler session;
    ArrayList<HashMap<String, String>> MyArr;
    ListView lv_re;
    LinearLayout iv_penyakit, iv_ketrampilan;
    TextView rencana, evaluasi;
    final String url_re = "http://192.168.3.10/cj_re.php";
    final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    final SimpleDateFormat convert = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_jurnal);
        lv_re=findViewById(R.id.lv_re);
        iv_penyakit = findViewById(R.id.iv_penyakit);
        iv_ketrampilan = findViewById(R.id.iv_ketrampilan);
        rencana = findViewById(R.id.rencana);
        evaluasi = findViewById(R.id.evaluasi);
        MyArr = new ArrayList<HashMap<String, String>>();
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        final String tgl = intent.getStringExtra("tgl");
        iv_penyakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getApplicationContext(), showJurnalPenyakit.class);
                a.putExtra ("tgl",tgl);
                a.putExtra("id",id);
                startActivity(a);
            }
        });

        iv_ketrampilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(getApplicationContext(), showJurnalKetrampilan.class);
                b.putExtra ("tgl",tgl);
                b.putExtra("id",id);
                startActivity(b);
            }
        });
        re();

    }

    private void re(){
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
        String re = url_re+"?username="+username+"&tanggal="+tanggal+"&id="+id;
        try {

            JSONArray data = new JSONArray(getJSONUrl(re));

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;
            for(int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(0);
                map = new HashMap<String, String>();
                map.put("evaluasi", c.getString("evaluasi"));
                map.put("rencana", c.getString("rencana"));
                MyArrList.add(map);
            }
            SimpleAdapter sAdap;
            sAdap = new SimpleAdapter(showJurnal.this, MyArrList, R.layout.item_row_re,
                    new String[] {"evaluasi","rencana"}, new int[] {R.id.evaluasi,R.id.rencana});
            lv_re.setAdapter(sAdap);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getJSONUrl(String url) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}
