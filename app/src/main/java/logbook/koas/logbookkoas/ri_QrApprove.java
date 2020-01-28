package logbook.koas.logbookkoas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ri_QrApprove extends AppCompatActivity {
    Button qr_approve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ri_qr_approve);
        qr_approve = findViewById(R.id.qr_approve);
        Intent intent = getIntent();
        final String id_internal = intent.getStringExtra("id_internal");
        final String id = intent.getStringExtra("id");
        final String rotasi = intent.getStringExtra("rotasi");
        final String dosen = intent.getStringExtra("dosen");
        qr_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera = new Intent(ri_QrApprove.this,ri_CamScanner.class);
                camera.putExtra("id_internal",id_internal);
                camera.putExtra("id",id);
                camera.putExtra("rotasi",rotasi);
                camera.putExtra("dosen",dosen);
                startActivity(camera);
            }
        });
    }


}