package com.example.logbookkoas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CamScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private static final String KEY_USERNAME = "username";
    private static final String KEY_DOSEN = "dosen";
    private static final String KEY_QR_CODE= "qr_code";
    private static final String KEY_ID= "id";
    private static final String KEY_JURNAL= "jurnal";
    private static final String KEY_STATUS= "status";
    private ZXingScannerView mScannerView;
    SessionHandler session;
    private String qr_aprv = "http://192.168.43.159/qr_aprv.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
    }
    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.v("TAG", rawResult.getText()); // Prints scan results
        Log.v("TAG", rawResult.getBarcodeFormat().toString());
        String qr_code = rawResult.getText();
        if (qr_code != null) {
            JSONObject request = new JSONObject();
            try {
                session = new SessionHandler(getApplicationContext());
                User user = session.getUserDetails();
                String username = user.getUsername();
                Intent intent = getIntent();
                final String id = intent.getStringExtra("id_jurnal");
                final String jurnal = intent.getStringExtra("jurnal");
                final String dosen = intent.getStringExtra("dosen");
                request.put(KEY_USERNAME, username);
                request.put(KEY_DOSEN, dosen);
                request.put(KEY_QR_CODE, qr_code);
                request.put(KEY_ID, id);
                request.put(KEY_JURNAL, jurnal);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                    (Request.Method.POST, qr_aprv, request, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getInt(KEY_STATUS) == 1) {
                                    Toast.makeText(CamScanner.this, "Approvement Berhasil",
                                            Toast.LENGTH_LONG).show();
                                    finish();

                                } else {
                                    Toast.makeText(CamScanner.this, "Approvement Gagal, QR Code Salah atau Kadaluarsa",
                                            Toast.LENGTH_LONG).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {


                        }
                    });
            // Access the RequestQueue through your singleton class.
            MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
        }
        else{

        }
        mScannerView.resumeCameraPreview(this);
    }


}
