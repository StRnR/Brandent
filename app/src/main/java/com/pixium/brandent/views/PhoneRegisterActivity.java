package com.pixium.brandent.views;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.pixium.brandent.R;

public class PhoneRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_register);

        Button submitBtn = findViewById(R.id.btn_submit_phone_register);


    }
}