package com.pixium.brandent.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.pixium.brandent.R;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Button registerBtn = findViewById(R.id.btn_register_intro);

        registerBtn.setOnClickListener(v ->
                startActivity(new Intent(this, PhoneRegisterActivity.class)));
    }
}