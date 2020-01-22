package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class RotasiInternalDetail extends AppCompatActivity {
    private SessionHandler session;
    ListView lv_rot;
    private String header = "http://192.168.1.7/logbook/getKepaniteraan.php";
    final String urlsem = "http://192.168.1.7/logbook/rotasi_internal.php";
    final String url_namadosen = "http://192.168.1.7/logbook/namadosen.php";
    private String judul = "http://192.168.1.7/logbook/getJadwal.php";
    private String update_status = "http://192.168.1.7/logbook/updateStatus.php";
    public static final String KEY_ID = "id";
    ArrayList<HashMap<String, String>> MyArr1= new ArrayList<HashMap<String,String>>();
    TextView stase,tanggal,id_stase;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat convert = new SimpleDateFormat("dd MMM yyyy");
    ArrayList<HashMap<String, String>> MyArr= new ArrayList<HashMap<String,String>>();


    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_rotasi_internal_detail);
        lv_rot = findViewById(R.id.lv_rotasi);
        stase = findViewById(R.id.stase);
        tanggal = findViewById(R.id.tanggal);
        id_stase = findViewById(R.id.tanggal_mulai);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        judul();
        process();
        staserotasi();
    }

    private void judul() {
        Intent intent = getIntent();
        final String id = intent.getStringExtra(RotasiInternal.KEY_ID);
        String url_judul = header+"?id="+id;

        try {

            JSONArray data = new JSONArray(getJSONUrl(url_judul));

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;
            JSONObject c = data.getJSONObject(0);
            map = new HashMap<String, String>();
            map.put("kepaniteraan", c.getString("kepaniteraan"));
            MyArrList.add(map);
            String namaStrase = c.getString("kepaniteraan");
            stase.setText(namaStrase);


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void process(){
        Intent intent = getIntent();
        final String id = intent.getStringExtra(mainIsiJurnal.KEY_ID);
        final String idStase = "stase_"+id;
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        String username = user.getUsername();
        String url_judul = judul+"?username="+username+"&stase="+idStase;

        try {

            JSONArray data = new JSONArray(getJSONUrl(url_judul));

            final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;
            JSONObject c = data.getJSONObject(0);
            map = new HashMap<String, String>();
            map.put("tgl_mulai", c.getString("tgl_mulai"));
            map.put("tgl_selesai", c.getString("tgl_selesai"));
            MyArrList.add(map);
            String mulai = c.getString("tgl_mulai");
            String selesai = c.getString("tgl_selesai");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat convert = new SimpleDateFormat("dd MMM yyyy");
            Date tglMulai = format.parse(mulai);
            String tglMulaiText = convert.format(tglMulai);
            Date tglSelesai = format.parse(selesai);
            Date tglSelesai1 = new Date(tglSelesai.getTime() + (1000 * 60 * 60 * 24));;
            String tglSelesaiText = convert.format(tglSelesai);
            String jadwal = tglMulaiText + " - " + tglSelesaiText;
            tanggal.setText(jadwal);
            id_stase.setText(idStase);


            Date now = Calendar.getInstance().getTime();
            if(now.after(tglMulai) && now.before(tglSelesai1)) {
                String status = "1";
                String url_status = update_status+"?username="+username+"&stase="+idStase+"&status="+status;
                try {
                    new JSONArray(getJSONUrl(url_status));

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            else if(now.before(tglMulai)){
                String status = "0";
                lv_rot.setVisibility(LinearLayout.GONE);
                String url_status = update_status+"?username="+username+"&stase="+idStase+"&status="+status;
                try {
                    new JSONArray(getJSONUrl(url_status));

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else{
                String status = "2";
                lv_rot.setVisibility(LinearLayout.GONE);
                String url_status = update_status+"?username="+username+"&stase="+idStase+"&status="+status;
                try {
                    new JSONArray(getJSONUrl(url_status));

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void staserotasi() {
        Intent i = getIntent();
        final String id = i.getStringExtra(RotasiInternal.KEY_ID);
       // final String tglml=i.getStringExtra(RotasiInternal.KEY_MULAI);


       // Toast.makeText(this, tglsl, Toast.LENGTH_SHORT).show();
        JSONObject request = new JSONObject();
        try {

            SessionHandler session;
            session = new SessionHandler(getApplicationContext());
            User user = session.getUserDetails();
            String username = user.getUsername();
            request.put("username", username);
            request.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json;
        json = new JsonObjectRequest(Request.Method.POST,
                urlsem, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    MyArr.clear();
                    JSONArray tmp = response.getJSONArray("tmp");
                    JSONArray total = response.getJSONArray("total");
                    JSONArray array1 = response.getJSONArray("array1");
                    JSONArray array2 = response.getJSONArray("array2");
                    JSONArray array3 = response.getJSONArray("array3");
                    JSONArray array4 = response.getJSONArray("array4");
                    JSONArray array5 = response.getJSONArray("array5");
                    JSONArray array6 = response.getJSONArray("array6");
                    JSONArray array7 = response.getJSONArray("array7");
                    JSONArray array8 = response.getJSONArray("array8");
                    JSONArray array9 = response.getJSONArray("array9");
                    JSONArray array10 = response.getJSONArray("array10");
                    JSONArray array11 = response.getJSONArray("array11");
                    JSONArray array12 = response.getJSONArray("array12");
                    for (int i = 0; i < tmp.length(); i++) {
                        JSONObject c = tmp.getJSONObject(i);
                        JSONObject d = total.getJSONObject(i);
                        JSONObject e = array1.getJSONObject(i);
                        JSONObject f = array2.getJSONObject(i);
                        JSONObject g = array3.getJSONObject(i);
                        JSONObject h = array4.getJSONObject(i);
                        JSONObject j = array5.getJSONObject(i);
                        JSONObject k = array6.getJSONObject(i);
                        JSONObject l = array7.getJSONObject(i);
                        JSONObject m = array8.getJSONObject(i);
                        JSONObject n = array9.getJSONObject(i);
                        JSONObject o = array10.getJSONObject(i);
                        JSONObject p = array11.getJSONObject(i);
                        JSONObject q = array12.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("id_rotasi",c.getString("id"));
                        map.put("stase", c.getString("internal"));
                        map.put("hari", c.getString("hari"));
                        map.put("id_internal", d.getString("id"));
                        map.put("tgl1",e.getString("tgl1"));
                        map.put("dosen1",e.getString("dosen1"));
                        map.put("rotasi1",e.getString("rotasi1"));
                        map.put("status1",e.getString("status1"));
                        map.put("tgl2",f.getString("tgl2"));
                        map.put("dosen2",f.getString("dosen2"));
                        map.put("rotasi2",f.getString("rotasi2"));
                        map.put("status2",f.getString("status2"));
                        map.put("tgl3",g.getString("tgl3"));
                        map.put("dosen3",g.getString("dosen3"));
                        map.put("rotasi3",g.getString("rotasi3"));
                        map.put("status3",g.getString("status3"));
                        map.put("tgl4",h.getString("tgl4"));
                        map.put("dosen4",h.getString("dosen4"));
                        map.put("rotasi4",h.getString("rotasi4"));
                        map.put("status4",h.getString("status4"));
                        map.put("tgl5",j.getString("tgl5"));
                        map.put("dosen5",j.getString("dosen5"));
                        map.put("rotasi5",j.getString("rotasi5"));
                        map.put("status5",j.getString("status5"));
                        map.put("tgl6",k.getString("tgl6"));
                        map.put("dosen6",k.getString("dosen6"));
                        map.put("rotasi6",k.getString("rotasi6"));
                        map.put("status6",k.getString("status6"));
                        if(e.getString("tgl1").equals("null")){
                            map.put("tgl1","");
                            map.put("dosen1","");
                            map.put("rotasi1","");
                            map.put("status1","");
                        }else if(f.getString("tgl2").equals("null")){
                            map.put("tgl2","");
                            map.put("dosen2","");
                            map.put("rotasi2","");
                            map.put("status2","");
                        }
                        else if(g.getString("tgl3").equals("null")){
                            map.put("tgl3","");
                            map.put("dosen3","");
                            map.put("rotasi3","");
                            map.put("status3","");
                        }
                        else if(h.getString("tgl4").equals("null")){
                            map.put("tgl4","");
                            map.put("dosen4","");
                            map.put("rotasi4","");
                            map.put("status4","");
                        }
                        else if(j.getString("tgl5").equals("null")){
                            map.put("tgl5","");
                            map.put("dosen5","");
                            map.put("rotasi5","");
                            map.put("status5","");
                        }
                        else if(k.getString("tgl6").equals("null")){
                            map.put("tgl6","");
                            map.put("dosen6","");
                            map.put("rotasi6","");
                            map.put("status6","");
                        }
                        if (d.getString("id").equals("null")) {
                            map.put("tglmli", "-");
                            map.put("tglsls", "-");
                            map.put("status", "-");
                            map.put("tgl1","");
                            map.put("dosen1","");
                            map.put("rotasi1","");
                            map.put("status1","");
                            map.put("tgl2","");
                            map.put("dosen2","");
                            map.put("rotasi2","");
                            map.put("status2","");
                            map.put("tgl3","");
                            map.put("dosen3","");
                            map.put("rotasi3","");
                            map.put("status3","");
                            map.put("tgl4","");
                            map.put("dosen4","");
                            map.put("rotasi4","");
                            map.put("status4","");
                            map.put("tgl5","");
                            map.put("dosen5","");
                            map.put("rotasi5","");
                            map.put("status5","");
                            map.put("tgl6","");
                            map.put("dosen6","");
                            map.put("rotasi6","");
                            map.put("status6","");
                        } else {
                            String tgl="0000-00-00";
                            Date tglml=format.parse(tgl);
                            Date tglaw= null;
                            Date tglMulai = tglml;
                            Date tglselesai = tglml;
                            Date tglselesai1 = tglml;
                            String a = "";
                            String b = "";
                            if (i == 0) {
                                tglMulai = format.parse(e.getString("tgl1"));
                                a = e.getString("tgl1");
                                b = l.getString("nama");
                                map.put("dosen",b);
                                map.put("tglmli", a);
                                String wktu = c.getString("hari");
                                int waktu = Integer.parseInt(wktu);
                                int sum = 1000 * 60 * 60 * 24 * waktu;
                                int minus = 1000 * 60 * 60 * 24;
                                if (tglMulai != null) {
                                    tglselesai = new Date(tglMulai.getTime() + sum - minus);

                                }
                            } else if (i == 1) {

                                a = f.getString("tgl2");
                                b = m.getString("nama");
                                map.put("dosen",b);
                                tglMulai = format.parse(f.getString("tgl2"));
                                map.put("tglmli", a);
                                String wktu = c.getString("hari");
                                int waktu = Integer.parseInt(wktu);
                                int sum = 1000 * 60 * 60 * 24 * waktu;
                                int minus = 1000 * 60 * 60 * 24;
                                if (tglMulai != null) {
                                    tglselesai = new Date(tglMulai.getTime() + sum - minus);

                                }
                            } else if (i == 2) {

                                tglMulai = format.parse(g.getString("tgl3"));
                                a = g.getString("tgl3");
                                b = n.getString("nama");
                                map.put("dosen",b);
                                map.put("tglmli", a);
                                String wktu = c.getString("hari");
                                int waktu = Integer.parseInt(wktu);
                                int sum = 1000 * 60 * 60 * 24 * waktu;
                                int minus = 1000 * 60 * 60 * 24;
                                if (tglMulai != null) {
                                    tglselesai = new Date(tglMulai.getTime() + sum - minus);

                                }
                            } else if (i == 3) {

                                tglMulai = format.parse(h.getString("tgl4"));
                                a = h.getString("tgl4");
                                b = o.getString("nama");
                                map.put("dosen",b);
                                map.put("tglmli", a);
                                String wktu = c.getString("hari");
                                int waktu = Integer.parseInt(wktu);
                                int sum = 1000 * 60 * 60 * 24 * waktu;
                                int minus = 1000 * 60 * 60 * 24;
                                if (tglMulai != null) {
                                    tglselesai = new Date(tglMulai.getTime() + sum - minus);

                                }
                            } else if (i == 4) {

                                tglMulai = format.parse(j.getString("tgl5"));
                                a = j.getString("tgl5");
                                b = p.getString("nama");
                                map.put("dosen",b);
                                map.put("tglmli", a);

                                String wktu = c.getString("hari");
                                int waktu = Integer.parseInt(wktu);
                                int sum = 1000 * 60 * 60 * 24 * waktu;
                                int minus = 1000 * 60 * 60 * 24;
                                if (tglMulai != null) {
                                    tglselesai = new Date(tglMulai.getTime() + sum - minus);

                                }
                            } else if (i == 5) {

                                tglMulai = format.parse(h.getString("tgl6"));
                                b = q.getString("dosen6");
                                map.put("dosen",b);
                                a = k.getString("tgl6");
                                map.put("tglmli", a);

                                String wktu = c.getString("hari");
                                int waktu = Integer.parseInt(wktu);
                                int sum = 1000 * 60 * 60 * 24 * waktu;
                                int minus = 1000 * 60 * 60 * 24;
                                if (tglMulai != null) {
                                    tglselesai = new Date(tglMulai.getTime() + sum - minus);

                                }
                            }

                            String tglSelesaiText = "";
                            if (tglselesai != null) {
                                tglSelesaiText = convert.format(tglselesai);
                            }
                            String tglmulaiText = "";
                            if (tglMulai != null) {
                                tglmulaiText = convert.format(tglMulai);
                            }

                            if(tglmulaiText.equals("30 Nov 0002")){
                                tglmulaiText=" ";
                                tglSelesaiText=" ";
                            }
                            map.put("tglsls", tglSelesaiText);
                            map.put("tglmli", tglmulaiText);
                            Date now = Calendar.getInstance().getTime();
                            String status = "";
                            if (i == 0) {
                                status = e.getString("status1");
                            } else if (i == 1) {
                                status = f.getString("status2");
                            } else if (i == 2) {
                                status = g.getString("status3");
                            } else if (i == 3) {
                                status = h.getString("status4");
                            } else if (i == 4) {
                                status = j.getString("status5");
                            } else if (i == 5) {
                                status = k.getString("status6");
                            }

                            map.put("status", status);
                            map.put("Approve","Approve");
                            switch (status) {
                                case "null":
                                    map.put("status", "-");
                                    break;
                                case "0":
                                    map.put("status", "Unapprove");
                                    break;
                                case "1":
                                    map.put("status", "Approve");
                                    break;
                            }
                        }

                        MyArr.add(map);

                    }
               /*     for (int w = 0; w < tmp.length(); w++) {
                        JSONObject c1 = tmp.getJSONObject(w);
                        JSONObject d1 = total.getJSONObject(w);
                        JSONObject e1 = array1.getJSONObject(w);
                        JSONObject f1 = array2.getJSONObject(w);
                        JSONObject g1 = array3.getJSONObject(w);
                        JSONObject h1 = array4.getJSONObject(w);
                        JSONObject j1 = array5.getJSONObject(w);
                        JSONObject k1 = array6.getJSONObject(w);
                        HashMap<String, String> map1 = new HashMap<String, String>();
                        map1.put("tgl1",e1.getString("tgl1"));
                        map1.put("dosen1",e1.getString("dosen1"));
                        map1.put("rotasi1",e1.getString("rotasi1"));
                        map1.put("status1",e1.getString("status1"));
                        map1.put("tgl2",f1.getString("tgl2"));
                        map1.put("dosen2",f1.getString("dosen2"));
                        map1.put("rotasi2",f1.getString("rotasi2"));
                        map1.put("status2",f1.getString("status2"));
                        map1.put("tgl3",g1.getString("tgl3"));
                        map1.put("dosen3",g1.getString("dosen3"));
                        map1.put("rotasi3",g1.getString("rotasi3"));
                        map1.put("status3",g1.getString("status3"));
                        map1.put("tgl4",h1.getString("tgl4"));
                        map1.put("dosen4",h1.getString("dosen4"));
                        map1.put("rotasi4",h1.getString("rotasi4"));
                        map1.put("status4",h1.getString("status4"));
                        map1.put("tgl5",j1.getString("tgl5"));
                        map1.put("dosen5",j1.getString("dosen5"));
                        map1.put("rotasi5",j1.getString("rotasi5"));
                        map1.put("status5",j1.getString("status5"));
                        map1.put("tgl6",k1.getString("tgl6"));
                        map1.put("dosen6",k1.getString("dosen6"));
                        map1.put("rotasi6",k1.getString("rotasi6"));
                        map1.put("status6",k1.getString("status6"));
                        MyArr1.add(map1);
                    }*/
                    SimpleAdapter sAdap;
                    sAdap = new SimpleAdapter(RotasiInternalDetail.this, MyArr, R.layout.item_row_rotasi_internal,
                            new String[]{"stase", "hari", "tglmli", "tglsls","status","dosen","Approve","id_internal","id_rotasi",
                                    "tgl1","rotasi1","dosen1","status1", "tgl2","rotasi2","dosen2","status2", "tgl3","rotasi3","dosen3","status3",
                                    "tgl4","rotasi4","dosen4","status4", "tgl5","rotasi5","dosen5","status5", "tgl6","rotasi6","dosen6","status6"},
                            new int[]{R.id.tv_judul, R.id.lama, R.id.tv_tgl_mulai, R.id.tv_tgl_selesai,
                                    R.id.tv_status,R.id.tv_approval,R.id.btn_approve,R.id.tv_id,R.id.tv_rotasi,
                                    R.id.tv_tgl1,R.id.tv_rotasi1,R.id.tv_dosen1,R.id.tv_status1,
                                    R.id.tv_tgl2,R.id.tv_rotasi2,R.id.tv_dosen1,R.id.tv_status2,
                                    R.id.tv_tgl3,R.id.tv_rotasi3,R.id.tv_dosen1,R.id.tv_status3,
                                    R.id.tv_tgl4,R.id.tv_rotasi4,R.id.tv_dosen1,R.id.tv_status4,
                                    R.id.tv_tgl5,R.id.tv_rotasi5,R.id.tv_dosen1,R.id.tv_status5,
                                    R.id.tv_tgl6,R.id.tv_rotasi6,R.id.tv_dosen1,R.id.tv_status6}) {
                        @Override
                        public View getView(final int position, View convertView, ViewGroup parent) {
                            View lv = super.getView(position, convertView, parent);
                            final HashMap<String,String>map=MyArr.get(position);
                            Intent er= getIntent();
                            final String tglsl=er.getStringExtra(RotasiInternal.KEY_SELESAI);
                            final TextView stase = lv.findViewById(R.id.tv_judul);
                            final TextView lamahari = lv.findViewById(R.id.lama);
                            final TextView status = lv.findViewById(R.id.tv_status);
                            final TextView tgl_mulai = lv.findViewById(R.id.tv_tgl_mulai);
                            final TextView tgl_selesai = lv.findViewById(R.id.tv_tgl_selesai);
                            final TextView dosen = lv.findViewById(R.id.tv_approval);
                            final TextView id_internal = lv.findViewById(R.id.tv_id);
                            final TextView rotasi= lv.findViewById(R.id.tv_rotasi);
                            final LinearLayout lay_dos = lv.findViewById(R.id.lay_dos);
                            final Button approval = lv.findViewById(R.id.btn_approve);
                            final Button edit = lv.findViewById(R.id.btn_edit);
                            lamahari.append(" hari");

                            if(tgl_mulai.getText().equals(" ")){
                                lay_dos.setVisibility(View.GONE);
                                approval.setVisibility(View.GONE);
                                status.setVisibility(View.GONE);
                            }
                            if (status.getText().equals("Approve")) {
                                status.setTextColor(getResources().getColor(R.color.md_green_900));
                                lay_dos.setVisibility(View.GONE);
                                approval.setVisibility(View.GONE);
                            } else if (status.getText().equals("Unapprove")) {
                                status.setTextColor(getResources().getColor(R.color.md_red_900));
                            } else if(status.getText().equals("-")){
                                lay_dos.setVisibility(View.GONE);
                                approval.setVisibility(View.GONE);
                            }else if(status.getText().equals("Unapprove")&&tgl_mulai.getText().equals(" ")){
                                lay_dos.setVisibility(View.GONE);
                                approval.setVisibility(View.GONE);
                                status.setVisibility(View.GONE);
                            }
                            if(dosen.getText().equals("null")){
                                lay_dos.setVisibility(View.GONE);
                            }
                            edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i=new Intent(RotasiInternalDetail.this,editrotasi.class);
                                    int nomor= position+1;
                                    String nmr=Integer.toString(nomor);
                                    i.putExtra("id_internal",id_internal.getText());
                                    i.putExtra("idst",id);
                                    i.putExtra("nomor", nmr);
                                    i.putExtra("id",map.get("id_rotasi"));
                                    i.putExtra("stase",map.get("stase"));
                                    i.putExtra("hari",map.get("hari"));
                                    i.putExtra("dosen",dosen.getText());
                                    i.putExtra("id_rotasi",map.get("rotasi"));
                                    i.putExtra("tgl1",map.get("tgl1"));
                                    i.putExtra("dosen1",map.get("dosen1"));
                                    i.putExtra("rotasi1",map.get("rotasi1"));
                                    i.putExtra("status1",map.get("status1"));
                                    i.putExtra("tgl2",map.get("tgl2"));
                                    i.putExtra("dosen2",map.get("dosen2"));
                                    i.putExtra("rotasi2",map.get("rotasi2"));
                                    i.putExtra("status2",map.get("status2"));
                                    i.putExtra("tgl3",map.get("tgl3"));
                                    i.putExtra("dosen3",map.get("dosen3"));
                                    i.putExtra("rotasi3",map.get("rotasi3"));
                                    i.putExtra("status3",map.get("status3"));
                                    i.putExtra("tgl4",map.get("tgl4"));
                                    i.putExtra("dosen4",map.get("dosen4"));
                                    i.putExtra("rotasi4",map.get("rotasi4"));
                                    i.putExtra("status4",map.get("status4"));
                                    i.putExtra("tgl5",map.get("tgl5"));
                                    i.putExtra("dosen5",map.get("dosen5"));
                                    i.putExtra("rotasi5",map.get("rotasi5"));
                                    i.putExtra("status5",map.get("status5"));
                                    i.putExtra("tgl6",map.get("tgl6"));
                                    i.putExtra("dosen6",map.get("dosen6"));
                                    i.putExtra("rotasi6",map.get("rotasi6"));
                                    i.putExtra("status6",map.get("status6"));
                                    startActivity(i);
                                }
                            });
                            approval.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent a=new Intent(RotasiInternalDetail.this, ri_main_approval.class);
                                    a.putExtra("id_internal",id_internal.getText());
                                    a.putExtra("id",id);
                                    a.putExtra("rotasi",rotasi.getText());
                                    a.putExtra("dosen",dosen.getText());
                                    startActivity(a);
                                }
                            });

                            return lv;
                        }
                    };

                    lv_rot.setAdapter(sAdap);

                } catch (JSONException | ParseException e) {
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
    private static String[] getStringArray(ArrayList<String> arr) {
        // declaration and initialise String Array
        String[] str = new String[arr.size()];

        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++) {

            // Assign each value to String array
            str[j] = arr.get(j);
        }

        return str;
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
