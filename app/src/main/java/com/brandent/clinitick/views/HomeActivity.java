package com.brandent.clinitick.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brandent.clinitick.ActiveUser;
import com.brandent.clinitick.R;
import com.brandent.clinitick.adapters.BlogAdapter;
import com.brandent.clinitick.adapters.TodayHomeAdapter;
import com.brandent.clinitick.api.models.blog.Post;
import com.brandent.clinitick.db.entities.Appointment;
import com.brandent.clinitick.db.entities.Clinic;
import com.brandent.clinitick.models.BlogCardModel;
import com.brandent.clinitick.models.TodayItem;
import com.brandent.clinitick.viewmodels.HomeViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
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
        TextView upcomingPatientTv = findViewById(R.id.tv_upcoming_patient_name_home);
        TextView upcomingAppointmentTv = findViewById(R.id.tv_upcoming_appointment_home);
        TextView upcomingTimeTv = findViewById(R.id.tv_time_upcoming_home);
        TextView emptyTodayTv = findViewById(R.id.tv_empty_today);
        TextView emptyUpcomingTv = findViewById(R.id.tv_empty_upcoming);

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

        // Upcoming Appointment
        Calendar endCal = Calendar.getInstance();
        endCal.set(Calendar.HOUR_OF_DAY, 23);
        endCal.set(Calendar.MINUTE, 59);
        endCal.set(Calendar.SECOND, 59);
        endCal.set(Calendar.MILLISECOND, 999);
        try {
            List<Appointment> appointments = homeViewModel.
                    getAppointmentsByDate(System.currentTimeMillis(), endCal.getTimeInMillis());
            if (appointments.size() > 0) {
                upcomingAppointmentTv.setText(appointments.get(0).getTitle());
                Timestamp timestamp = new Timestamp(appointments.get(0).getVisitTime());
                String timeStr = Character.toString(timestamp.toString().charAt(11))
                        + timestamp.toString().charAt(12)
                        + timestamp.toString().charAt(13)
                        + timestamp.toString().charAt(14)
                        + timestamp.toString().charAt(15);
                upcomingTimeTv.setText(timeStr);
                upcomingPatientTv.setText(homeViewModel.getPatientById(appointments.get(0)
                        .getPatientForId()).getName());
                upcomingAppointmentTv.setVisibility(View.VISIBLE);
                upcomingTimeTv.setVisibility(View.VISIBLE);
                upcomingPatientTv.setVisibility(View.VISIBLE);
                emptyUpcomingTv.setVisibility(View.GONE);
            } else {
                upcomingAppointmentTv.setVisibility(View.GONE);
                upcomingTimeTv.setVisibility(View.GONE);
                upcomingPatientTv.setVisibility(View.GONE);
                emptyUpcomingTv.setVisibility(View.VISIBLE);
            }
        } catch (ExecutionException | InterruptedException e) {
            upcomingAppointmentTv.setVisibility(View.GONE);
            upcomingTimeTv.setVisibility(View.GONE);
            upcomingPatientTv.setVisibility(View.GONE);
            emptyUpcomingTv.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }

        // Today Appointments
        ArrayList<TodayItem> todayList = new ArrayList<>();

        Calendar startCal = Calendar.getInstance();
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);

        ArrayList<Integer> todayClinics = new ArrayList<>();
        ArrayList<Integer> appointmentNumbers = new ArrayList<>();
        try {
            List<Appointment> todayAppointments = homeViewModel
                    .getAppointmentsByDate(startCal.getTimeInMillis(), endCal.getTimeInMillis());

            if (todayAppointments.size() > 0) {
                emptyTodayTv.setVisibility(View.GONE);
                todayRv.setVisibility(View.VISIBLE);
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
            } else {
                emptyTodayTv.setVisibility(View.VISIBLE);
                todayRv.setVisibility(View.GONE);
            }
        } catch (ExecutionException | InterruptedException e) {
            emptyTodayTv.setVisibility(View.VISIBLE);
            todayRv.setVisibility(View.GONE);
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


        // Blog Recyclerview
        RecyclerView blogRecyclerView = findViewById(R.id.rv_home_blog);
        RecyclerView.LayoutManager blogLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, true);
        blogRecyclerView.setLayoutManager(blogLayoutManager);

        BlogAdapter blogAdapter = new BlogAdapter();
        blogRecyclerView.setAdapter(blogAdapter);

        MutableLiveData<List<BlogCardModel>> loadedBlogCards = new MutableLiveData<>();
        List<BlogCardModel> blogCardModels = new ArrayList<>();

        homeViewModel.getBolgPosts().observe(this, posts -> {
            try {
                if (!posts.isEmpty()) {
                    for (Post post : posts) {
                        homeViewModel.getBlogMedia(String.valueOf(post.getFeatured_media()))
                                .observe(HomeActivity.this, media -> {
                                    if (!Objects.isNull(media)) {
                                        blogCardModels.add(new BlogCardModel(post.getId()
                                                , post.getTitle().getRendered(), media.getSource_url()));
                                        loadedBlogCards.setValue(new ArrayList<>(blogCardModels));
                                    }
                                });
                    }
                }
            } catch (NullPointerException e) {
                Toast.makeText(HomeActivity.this, "Couldn't retrieve blog posts"
                        , Toast.LENGTH_SHORT).show();
            }
        });
        loadedBlogCards.observe(this, blogCardModels1 ->
                blogAdapter.setBlogCardModels(loadedBlogCards.getValue()));

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