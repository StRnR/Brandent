package com.pixium.brandent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ClinicsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinics);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        Button backBtn = findViewById(R.id.btn_back_clinics);
        Button clinicAddBtn = findViewById(R.id.btn_add_clinic_clinics);

        // Set Selected NavBar item
        bottomNavigationView.setSelectedItemId(R.id.profile_page);

        // NavBar ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_page:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.finance_page:
                        startActivity(new Intent(getApplicationContext(), FinanceActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.add_appointment_page:
                        startActivity(new Intent(getApplicationContext(), AddAppointmentActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.tasks_page:
                        return true;
                    case R.id.profile_page:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        clinicAddBtn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), AddClinicActivity.class));
        });

        backBtn.setOnClickListener(v -> {
            super.onBackPressed();
        });
    }

    @Override
    protected void onResume() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        // Set Selected NavBar item
        bottomNavigationView.setSelectedItemId(R.id.profile_page);
        super.onResume();
    }
}