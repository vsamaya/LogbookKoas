package logbook.koas.logbookkoas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class profil extends AppCompatActivity {
    Button editprofile;
    private SessionHandler session;
    ArrayList<HashMap<String, String>> list_data = new ArrayList<HashMap<String, String>>();
    private static final String KEY_USERNAME = "username";
    Button logout,informasi;
    private String foto_url = "https://logbook.fk.undip.ac.id/koas/android/getdatafoto.php";
    private String foto_image = "https://logbook.fk.undip.ac.id/koas/foto/";
    TextView username, nama;
    ImageView foto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        editprofile = findViewById(R.id.editp);
        informasi = findViewById(R.id.informasi);
        session = new SessionHandler(getApplicationContext());
        final User user = session.getUserDetails();
        final String username1 = user.getUsername();
        final String password = user.getPassword();
        String level1 = user.getLevel();
        Bundle bundle = getIntent().getExtras();
        username = findViewById(R.id.usernamemahasiswa);
        nama = findViewById(R.id.namamahasiswa);
        username.setText(bundle.getString("data1"));
        nama.setText(bundle.getString("data2"));
        foto = findViewById(R.id.imgprof);
        getData1(username.getText().toString());
        logout = findViewById(R.id.logout);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("data1", username.getText().toString());
                bundle.putString("data2", nama.getText().toString());
                Intent edit = new Intent(profil.this, editprofil.class);
                edit.putExtras(bundle);
                startActivity(edit);

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                user.setUsername("");
                user.setLevel("");

                Intent edit = new Intent(profil.this, MainActivity.class);
                edit.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(edit);
                finish();

            }
        });
        informasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://logbook.fk.undip.ac.id/koas/bridge_informasi.php?usr="+username1+"&pwd="+password));
                String title = "Pilih browser untuk membuka  tautan";
                Intent chooser = Intent.createChooser(intent, title);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }

            }
        });
    }

    public void getData1(String username) {

        JSONObject request = new JSONObject();
        try {
            request.put(KEY_USERNAME, username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                foto_url, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray kota = response.getJSONArray("data1");
                    for (int i = 0; i < kota.length(); i++) {
                        JSONObject j = kota.getJSONObject(i);
                        HashMap<String, String> map1 = new HashMap<String, String>();
                        map1 = new HashMap<String, String>();
                        map1.put("foto", j.getString("foto"));
                        list_data.add(map1);

                    }

                    Glide.with(getApplicationContext())
                            .load(foto_image + list_data.get(0).get("foto"))
                            .placeholder(R.drawable.ic_account)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)//.placeholder(R.drawable.ic_account)
                            .skipMemoryCache(true)
                            .into(foto);


                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(json);
    }
}