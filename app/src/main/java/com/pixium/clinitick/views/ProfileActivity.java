package com.pixium.clinitick.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pixium.clinitick.ActiveUser;
import com.pixium.clinitick.R;
import com.pixium.clinitick.db.entities.Dentist;
import com.pixium.clinitick.viewmodels.ProfileViewModel;

import java.util.concurrent.ExecutionException;

public class ProfileActivity extends AppCompatActivity {
    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        TextView nameTv = findViewById(R.id.tv_name_profile);
        TextView specialityTv = findViewById(R.id.tv_speciality_profile);

        Button clinicsBtn = findViewById(R.id.btn_clinics_profile);
        Button patientsBtn = findViewById(R.id.btn_patients_profile);
        Button logoutBtn = findViewById(R.id.btn_logout_profile);
        Button aboutBtn = findViewById(R.id.btn_about_us_profile);
        Button membershipBtn = findViewById(R.id.btn_membership_profile);

        profileViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).
                get(ProfileViewModel.class);


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

        // Dentist Data
        try {
            Dentist dentist = profileViewModel.getDentistById(ActiveUser.getInstance().getId());
            String nameStr = dentist.getFirstName() + " " + dentist.getLastName();
            nameTv.setText(nameStr);
            specialityTv.setText(dentist.getSpeciality());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


        logoutBtn.setOnClickListener(v -> {
            try {
                Dentist dentist = profileViewModel.getDentistById(ActiveUser.getInstance().getId());
                Dentist updateDentist = new Dentist(dentist.getDentistId(), dentist.getFirstName()
                        , dentist.getLastName(), dentist.getPhone(), dentist.getSpeciality()
                        , dentist.getImageName(), 0, dentist.getToken()
                        , dentist.getLastUpdated());
                profileViewModel.updateDentist(updateDentist);
                Intent intent = new Intent(this, IntroActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        clinicsBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext()
                , ClinicActivity.class)));

        patientsBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext()
                , PatientsActivity.class)));

        aboutBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra(WebViewActivity.EXTRA_REQUEST, WebViewActivity.ABOUT_REQUEST);
            startActivity(intent);
        });

        membershipBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra(WebViewActivity.EXTRA_REQUEST, WebViewActivity.MEMBERSHIP_REQUEST);
            startActivity(intent);
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