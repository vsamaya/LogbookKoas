package logbook.koas.logbookkoas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ri_main_approval extends AppCompatActivity {
    LinearLayout qr, otp, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_approve);
        pass = findViewById(R.id.pass);
        otp = findViewById(R.id.otp);
        qr = findViewById(R.id.qr);
        Intent intent = getIntent();
        final String id_internal = intent.getStringExtra("id_internal");
        final String id = intent.getStringExtra("id");
        final String rotasi = intent.getStringExtra("rotasi");
        final String dosen = intent.getStringExtra("dosen");


        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getApplicationContext(), ri_PassApprove.class);
                a.putExtra("id_internal",id_internal);
                a.putExtra("id",id);
                a.putExtra("rotasi",rotasi);
                a.putExtra("dosen",dosen);
                startActivity(a);
            }
        });

        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(getApplicationContext(), ri_OtpApprove.class);
                b.putExtra("id_internal",id_internal);
                b.putExtra("id",id);
                b.putExtra("rotasi",rotasi);
                b.putExtra("dosen",dosen);
                startActivity(b);
            }
        });

        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(getApplicationContext(), ri_QrApprove.class);
                c.putExtra("id_internal",id_internal);
                c.putExtra("id",id);
                c.putExtra("rotasi",rotasi);
                c.putExtra("dosen",dosen);
                startActivity(c);
            }
        });
    }
}
