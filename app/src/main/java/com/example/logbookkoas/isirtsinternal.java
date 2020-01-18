package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class isirtsinternal extends AppCompatActivity {
    TextView semhead;
    ListView lvsem9, lvsem10, lvsem11, lvsem12;
    final String urlsem = "http://192.168.1.9/logbook/rtssmstr.php";
    public static final String KEY_ID = "id";
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat convert = new SimpleDateFormat("dd MMM yyyy");
    ArrayList<HashMap<String, String>> MyArr= new ArrayList<HashMap<String,String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isirtsinternal);
        lvsem9 = findViewById(R.id.lvsem9);
        lvsem10 = findViewById(R.id.lvsem10);
        lvsem11 = findViewById(R.id.lvsem11);
        lvsem12 = findViewById(R.id.lvsem12);
        semhead=  findViewById(R.id.semester9rts);
        Intent m= getIntent();
        String n=m.getStringExtra(rtsinternal.KEY_ID);
        String o=n.substring(1,3);
        //Toast.makeText(this, o, Toast.LENGTH_SHORT).show();
        if(o.equals("09")){
            staserotasi9();
        }else if(o.equals("10")){
            staserotasi9();
            semhead.setText("Semester 10");
        }else if(o.equals("11")){
            staserotasi9();
            semhead.setText("Semester 11");
        }else if(o.equals("12")){
            staserotasi9();
            semhead.setText("Semester 12");
        }

     /*   sem10rtsstase=findViewById(R.id.rtskepaniteraan);
        sem10rtslama=findViewById(R.id.lmpelaksanaan);
        sem10rtsmli=findViewById(R.id.rtstglmli);
        sem10rtssls=findViewById(R.id.rtstglsls);
        sem10rtsstatus=findViewById(R.id.statusrts);
        sem10rtsdsn=findViewById(R.id.nmapprovalrts);
        sem10Approv=findViewById(R.id.approvalrts);
        sem10Edit=findViewById(R.id.btneditrts);
        sem11rtsstase=findViewById(R.id.rtskepaniteraan);
        sem11rtslama=findViewById(R.id.lmpelaksanaan);
        sem11rtsmli=findViewById(R.id.rtstglmli);
        sem11rtssls=findViewById(R.id.rtstglsls);
        sem11rtsstatus=findViewById(R.id.statusrts);
        sem11rtsdsn=findViewById(R.id.nmapprovalrts);
        sem11Approv=findViewById(R.id.approvalrts);
        sem11Edit=findViewById(R.id.btneditrts);
        sem12rtsstase=findViewById(R.id.rtskepaniteraan);
        sem12rtslama=findViewById(R.id.lmpelaksanaan);
        sem12rtsmli=findViewById(R.id.rtstglmli);
        sem12rtssls=findViewById(R.id.rtstglsls);
        sem12rtsstatus=findViewById(R.id.statusrts);
        sem12rtsdsn=findViewById(R.id.nmapprovalrts);
        sem12Approv=findViewById(R.id.approvalrts);
        sem12Edit=findViewById(R.id.btneditrts);*/


    }

    private void staserotasi9() {
        JSONObject request = new JSONObject();
        try {

            SessionHandler session;
            session = new SessionHandler(getApplicationContext());
            User user = session.getUserDetails();
            String username = user.getUsername();
            request.put("username", username);
            Intent i = getIntent();
            final String id = i.getStringExtra(rtsinternal.KEY_ID);
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
                    for (int i = 0; i < tmp.length(); i++) {
                        JSONObject c = tmp.getJSONObject(i);
                        JSONObject d = total.getJSONObject(i);
                        JSONObject e = array1.getJSONObject(i);
                        JSONObject f = array2.getJSONObject(i);
                        JSONObject g = array3.getJSONObject(i);
                        JSONObject h = array4.getJSONObject(i);
                        JSONObject j = array5.getJSONObject(i);
                        JSONObject k = array6.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("stase", c.getString("internal"));
                        map.put("hari", c.getString("hari"));
                      if (d.getString("id").equals("null")) {
                            map.put("tglmli", "-");
                            map.put("tglsls", "-");
                            map.put("status", "-");
                        } else {
                          String tgl="0000-00-00";
                          Date tglml=format.parse(tgl);
                          Date tglMulai = tglml;
                          Date tglselesai = tglml;
                          String a = "";
                          String b = "";
                          if (i == 0) {
                              tglMulai = format.parse(e.getString("tgl1"));
                              a = e.getString("tgl1");
                              b = e.getString("dosen1");
                              map.put("dosen1",b);
                              map.put("tglmli", a);
                              String wktu = c.getString("hari");
                              int waktu = Integer.parseInt(wktu);
                              int sum = 1000 * 60 * 60 * 24 * waktu;
                              if (tglMulai != null) {
                                  tglselesai = new Date(tglMulai.getTime() + sum);
                              }
                          } else if (i == 1) {
                              a = f.getString("tgl2");
                              b = f.getString("dosen2");
                              map.put("dosen",b);
                              tglMulai = format.parse(f.getString("tgl2"));
                              map.put("tglmli", a);
                              String wktu = c.getString("hari");
                              int waktu = Integer.parseInt(wktu);
                              int sum = 1000 * 60 * 60 * 24 * waktu;
                              if (tglMulai != null) {
                                  tglselesai = new Date(tglMulai.getTime() + sum);
                              }
                          } else if (i == 2) {
                              tglMulai = format.parse(g.getString("tgl3"));
                              a = g.getString("tgl3");
                              b = g.getString("dosen3");
                              map.put("dosen",b);
                              map.put("tglmli", a);
                              String wktu = c.getString("hari");
                              int waktu = Integer.parseInt(wktu);
                              int sum = 1000 * 60 * 60 * 24 * waktu;
                              if (tglMulai != null) {
                                  tglselesai = new Date(tglMulai.getTime() + sum);
                              }
                          } else if (i == 3) {
                              tglMulai = format.parse(h.getString("tgl4"));
                              a = h.getString("tgl4");
                              b = h.getString("dosen4");
                              map.put("dosen",b);
                              map.put("tglmli", a);
                              String wktu = c.getString("hari");
                              int waktu = Integer.parseInt(wktu);
                              int sum = 1000 * 60 * 60 * 24 * waktu;
                              if (tglMulai != null) {
                                  tglselesai = new Date(tglMulai.getTime() + sum);
                              }
                          } else if (i == 4) {
                              tglMulai = format.parse(j.getString("tgl5"));
                              a = j.getString("tgl5");
                              b = j.getString("dosen5");
                              map.put("dosen",b);
                              map.put("tglmli", a);

                              String wktu = c.getString("hari");
                              int waktu = Integer.parseInt(wktu);
                              int sum = 1000 * 60 * 60 * 24 * waktu;
                              if (tglMulai != null) {
                                  tglselesai = new Date(tglMulai.getTime() + sum);
                              }
                          } else if (i == 5) {
                              tglMulai = format.parse(h.getString("tgl6"));
                              b = k.getString("dosen6");
                              map.put("dosen",b);
                              a = k.getString("tgl6");
                              map.put("tglmli", a);

                              String wktu = c.getString("hari");
                              int waktu = Integer.parseInt(wktu);
                              int sum = 1000 * 60 * 60 * 24 * waktu;
                              if (tglMulai != null) {
                                  tglselesai = new Date(tglMulai.getTime() + sum);
                              }
                          }


                           /* if (tglMulai1 != null) {
                                String tglMulaiText1 = convert.format(tglMulai1);
                            }
                            if (tglMulai2 != null) {
                                String tglMulaiText2 = convert.format(tglMulai2);
                            }
                            if (tglMulai3 != null) {
                                String tglMulaiText3 = convert.format(tglMulai3);
                            }
                            if (tglMulai4 != null) {
                                String tglMulaiText4 = convert.format(tglMulai4);
                            }
                            if (tglMulai5 != null) {
                                String tglMulaiText5 = convert.format(tglMulai5);
                            }
                            if (tglMulai6 != null) {
                                String tglMulaiText6 = convert.format(tglMulai6);
                            }*/
                          String tglSelesaiText = "";
                          if (tglselesai != null) {
                              tglSelesaiText = convert.format(tglselesai);
                          }
                          String tglmulaiText = "";
                          if (tglMulai != null) {
                              tglmulaiText = convert.format(tglMulai);
                          }
                          //   Date tglSelesai1 = new Date(tglSelesai.getTime() + (1000 * 60 * 60 * 24));

                          if(tglmulaiText.equals("30 Nov 0002")){
                              tglmulaiText="-";
                              tglSelesaiText="-";
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

                         /*   if (status.equals("null")&&status2.equals("null")&&status3.equals("null")&&
                                    status4.equals("null")&&status5.equals("null")&&status6.equals("null")) {
                                map.put("status", "");*/
                          map.put("status", status);
                          map.put("Approve","Approve");
                          switch (status) {
                              case "null":
                                  map.put("status", "");
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
                    SimpleAdapter sAdap;
                    sAdap = new SimpleAdapter(isirtsinternal.this, MyArr, R.layout.lvrtsinternal,
                            new String[]{"stase", "hari", "tglmli", "tglsls","status","dosen","Approve"},
                            new int[]{R.id.rtskepaniteraan, R.id.lmpelaksanaan, R.id.rtstglmli, R.id.rtstglsls,R.id.statusrts,R.id.nmapprovalrts,R.id.approvalrts}) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View lv = super.getView(position, convertView, parent);
                            final TextView stase = lv.findViewById(R.id.rtskepaniteraan);
                            final TextView lamahari = lv.findViewById(R.id.lmpelaksanaan);
                            final TextView status = lv.findViewById(R.id.statusrts);
                            final TextView tgl_mulai = lv.findViewById(R.id.rtstglmli);
                            final TextView tgl_selesai = lv.findViewById(R.id.rtstglsls);
                            final TextView dosen = lv.findViewById(R.id.nmapprovalrts);
                            final TextView approval = lv.findViewById(R.id.approvalrts);
                            lamahari.append(" hari");
                            if(tgl_mulai.getText().equals("-")){
                                status.setVisibility(View.GONE);
                                dosen.setVisibility(View.GONE);
                                approval.setVisibility(View.GONE);
                            }
                            if (status.getText().equals("Approve")) {
                                status.setTextColor(getResources().getColor(R.color.md_green_900));
                                dosen.setVisibility(View.GONE);
                                status.setVisibility(View.VISIBLE);
                                approval.setVisibility(View.GONE);
                            } else if (status.getText().equals("Unapprove")) {
                                status.setTextColor(getResources().getColor(R.color.md_red_900));
                                status.setVisibility(View.VISIBLE);
                                dosen.setVisibility(View.VISIBLE);
                                approval.setVisibility(View.VISIBLE);
                            } else if(status.getText().equals("null")){
                                status.setVisibility(View.GONE);
                                dosen.setVisibility(View.GONE);
                                approval.setVisibility(View.GONE);
                            }
                            if(dosen.getText().equals("null")){
                                dosen.setVisibility(View.GONE);
                                status.setVisibility(View.GONE);
                            }

                            return lv;
                        }
                    };

                    lvsem9.setAdapter(sAdap);

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

}
