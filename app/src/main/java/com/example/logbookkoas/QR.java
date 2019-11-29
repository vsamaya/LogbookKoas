package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QR extends AppCompatActivity {
    ImageView iv_qr;
    TextView Waktu;
    String TAG = "GenerateQRCode";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        Random rand=new Random();
        Waktu = (TextView) findViewById(R.id.waktu);
        iv_qr = (ImageView) findViewById(R.id.iv_qr);
        new CountDownTimer(300000, 1000) {
            public void onTick(long millisUntilFinished) {
                String waktu = String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

                Waktu.setText(waktu);
            }
            public void onFinish() {
                finish();

            }
        }.start();
        int number = rand.nextInt(8999)+1000;
        String otp = String.valueOf(number);

        QRGEncoder qrgEncoder = new QRGEncoder(otp, null, QRGContents.Type.TEXT, 500);
        try {
            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            iv_qr.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Log.v(TAG, e.toString());
        }
    }
}

