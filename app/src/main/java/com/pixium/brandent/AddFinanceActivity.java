package com.pixium.brandent;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AddFinanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_finance);

        Button backBtn = findViewById(R.id.btn_back_finance_add);

        backBtn.setOnClickListener(v -> onBackPressed());

    }
}