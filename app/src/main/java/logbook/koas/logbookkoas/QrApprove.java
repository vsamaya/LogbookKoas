package logbook.koas.logbookkoas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class QrApprove extends AppCompatActivity {
    TextView qr_dosen;
    Button qr_approve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_approve);
        qr_dosen = findViewById(R.id.qr_dosen);
        qr_approve = findViewById(R.id.qr_approve);
        Intent intent = getIntent();
        final String id_jurnal = intent.getStringExtra("id_jurnal");
        final String jurnal = intent.getStringExtra("jurnal");
        final String dosen = intent.getStringExtra("dosen");
        final String dosen_lengkap = intent.getStringExtra("dosen_lengkap");
        qr_dosen.setText(dosen_lengkap);
        qr_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera = new Intent(QrApprove.this,CamScanner.class);
                camera.putExtra("id_jurnal",id_jurnal);
                camera.putExtra("jurnal",jurnal);
                camera.putExtra("dosen",dosen);
                startActivity(camera);
            }
        });
    }


}