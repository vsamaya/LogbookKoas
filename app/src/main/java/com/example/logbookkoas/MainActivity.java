package com.example.logbookkoas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import cyd.awesome.material.AwesomeText;
import cyd.awesome.material.FontCharacterMaps;

public class MainActivity extends AppCompatActivity {
    EditText usernamet,passwordt;
    AwesomeText imgShowhidepassword;
    boolean pwd_status = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernamet = (EditText) findViewById(R.id.username);
        passwordt = (EditText) findViewById(R.id.password);
        imgShowhidepassword =(AwesomeText)findViewById(R.id.ImgShowPassword);
        imgShowhidepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pwd_status) {
                    passwordt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    pwd_status = false;
                    imgShowhidepassword.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY);
                    passwordt.setSelection(passwordt.length());
                } else {
                    passwordt.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    pwd_status = true;
                    imgShowhidepassword.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY_OFF);
                    passwordt.setSelection(passwordt.length());
                }
            }
        });

    }

    public void onLogin(View view){
        String Username=usernamet.getText().toString();
        String Password=passwordt.getText().toString();
        String type = "login";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type,Username,Password);


    }

}

