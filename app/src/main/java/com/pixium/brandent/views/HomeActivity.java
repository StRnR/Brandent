package com.pixium.brandent.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pixium.brandent.ActiveUser;
import com.pixium.brandent.R;
import com.pixium.brandent.adapters.TodayHomeAdapter;
import com.pixium.brandent.db.entities.Appointment;
import com.pixium.brandent.db.entities.Clinic;
import com.pixium.brandent.models.TodayItem;
import com.pixium.brandent.viewmodels.HomeViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import saman.zamani.persiandate.PersianDate;

public class HomeActivity extends AppCompatActivity {
    private HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        TextView headerTv = findViewById(R.id.tv_header_home);
        TextView dateTv = findViewById(R.id.tv_date_home);

        RecyclerView todayRv = findViewById(R.id.rv_today_home);

        homeViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).
                get(HomeViewModel.class);

        // Set Selected NavBar item
        bottomNavigationView.setSelectedItemId(R.id.home_page);

        // NavBar ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_page:
                        return true;
                    case R.id.finance_page:
                        startActivity(new Intent(getApplicationContext(), FinanceActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.add_appointment_page:
                        startActivity(new Intent(getApplicationContext(), AddPatientActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.tasks_page:
                        startActivity(new Intent(getApplicationContext(), TasksActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile_page:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });


        // Header
        try {
            String headerStr = "سلام دکتر " + homeViewModel.getDentistById(ActiveUser.getInstance()
                    .getId()).getLastName();
            headerTv.setText(headerStr);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        // Display Date
        PersianDate pDate = new PersianDate();
        String date = pDate.dayName() + " " + pDate.getShDay() + " " + pDate.monthName();
        dateTv.setText(date);


        // Today Appointments
        ArrayList<TodayItem> todayList = new ArrayList<>();

        Calendar startCal = Calendar.getInstance();
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);

        Calendar endCal = (Calendar) startCal.clone();
        endCal.set(Calendar.HOUR_OF_DAY, 23);
        endCal.set(Calendar.MINUTE, 59);
        endCal.set(Calendar.SECOND, 59);
        endCal.set(Calendar.MILLISECOND, 999);

        ArrayList<Integer> todayClinics = new ArrayList<>();
        ArrayList<Integer> appointmentNumbers = new ArrayList<>();
        try {
            List<Appointment> todayAppointments = homeViewModel
                    .getAppointmentsByDate(startCal.getTimeInMillis(), endCal.getTimeInMillis());

            for (Appointment appointment : todayAppointments) {
                Clinic todayClinic = homeViewModel.getClinicById(appointment.getClinicForId());
                if (!todayClinics.contains(todayClinic.getClinicId())) {
                    todayClinics.add(todayClinic.getClinicId());
                    appointmentNumbers.add(1);
                } else {
                    int index = todayClinics.indexOf(todayClinic.getClinicId());
                    int prev = appointmentNumbers.get(index);
                    appointmentNumbers.set(index, prev + 1);
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < todayClinics.size(); i++) {
            Clinic clinic = null;
            try {
                clinic = homeViewModel.getClinicById(todayClinics.get(i));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            assert clinic != null;
            todayList.add(new TodayItem(clinic.getColor(), clinic.getTitle()
                    , appointmentNumbers.get(i)));
        }
        todayRv.setHasFixedSize(true);
        RecyclerView.LayoutManager todayLM = new LinearLayoutManager(this);
        TodayHomeAdapter todayAdapter = new TodayHomeAdapter(todayList);
        todayRv.setLayoutManager(todayLM);
        todayRv.setAdapter(todayAdapter);
        todayRv.setNestedScrollingEnabled(false);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        // Set Selected NavBar item
        bottomNavigationView.setSelectedItemId(R.id.home_page);
    }

}