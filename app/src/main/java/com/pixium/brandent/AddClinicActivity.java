package com.pixium.brandent;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.pixium.brandent.db.Clinic;

public class AddClinicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clinic);

        Button backBtn = findViewById(R.id.btn_back_clinic_add);
        Button submitBtn = findViewById(R.id.btn_submit_clinic_add);

        EditText nameEt = findViewById(R.id.et_name_clinic_add);
        EditText addressEt = findViewById(R.id.et_address_clinic_add);



        submitBtn.setOnClickListener(v -> {
            Clinic clinic = new Clinic(null, null, nameEt.getText().toString(), addressEt.getText().toString(), null);
        });

        backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });

    }
}