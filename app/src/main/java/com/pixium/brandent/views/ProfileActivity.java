package com.pixium.brandent.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pixium.brandent.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        Button clinicsBtn = findViewById(R.id.btn_clinics_profile);
        Button patientsBtn = findViewById(R.id.btn_patients_profile);


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
                        startActivity(new Intent(getApplicationContext(), AddPatientActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.tasks_page:
                        startActivity(new Intent(getApplicationContext(), TasksActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile_page:
                        return true;
                }
                return false;
            }
        });

        clinicsBtn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ClinicActivity.class));
        });

        patientsBtn.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), PatientsActivity.class));
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