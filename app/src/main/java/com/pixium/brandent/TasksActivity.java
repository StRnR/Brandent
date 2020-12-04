package com.pixium.brandent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import saman.zamani.persiandate.PersianDate;

public class TasksActivity extends AppCompatActivity {
    private RecyclerView calendarRecyclerView;
    private RecyclerView.Adapter calendarAdapter;
    private RecyclerView.LayoutManager calendarLayoutManager;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        TextView month_tv = findViewById(R.id.tv_month_tasks);

        // Set Selected NavBar item
        bottomNavigationView.setSelectedItemId(R.id.tasks_page);

        // NavBar ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
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
        });

        // Calendar Init
        PersianDate pDate = new PersianDate();

        month_tv.setText(pDate.monthName());

        // Calendar Items
        ArrayList<TasksCalendarItem> tasksCalendarItems = new ArrayList<>();
        int dow = 0;
        for (int i = 1; i <= pDate.getMonthDays(); i++) {
            if (i > pDate.getShDay())
                dow = (((i - pDate.getShDay()) % 7) + pDate.dayOfWeek()) % 7;
            else if (i < pDate.getShDay())
                dow = pDate.dayOfWeek() - ((pDate.getShDay() - i) % 7);
            else
                dow = pDate.dayOfWeek();

            if (dow == 0)
                tasksCalendarItems.add(new TasksCalendarItem("ش", Integer.toString(i)));
            else if (dow == 1 || dow == -6)
                tasksCalendarItems.add(new TasksCalendarItem("ی", Integer.toString(i)));
            else if (dow == 2 || dow == -5)
                tasksCalendarItems.add(new TasksCalendarItem("د", Integer.toString(i)));
            else if (dow == 3 || dow == -4)
                tasksCalendarItems.add(new TasksCalendarItem("س", Integer.toString(i)));
            else if (dow == 4 || dow == -3)
                tasksCalendarItems.add(new TasksCalendarItem("چ", Integer.toString(i)));
            else if (dow == 5 || dow == -2)
                tasksCalendarItems.add(new TasksCalendarItem("پ", Integer.toString(i)));
            else
                tasksCalendarItems.add(new TasksCalendarItem("ج", Integer.toString(i)));
        }

        calendarRecyclerView = findViewById(R.id.rv_calendar_tasks);
        calendarRecyclerView.setHasFixedSize(true);
        calendarLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        calendarAdapter = new CalendarAdapter(tasksCalendarItems);

        calendarRecyclerView.setLayoutManager(calendarLayoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

    }

    @Override
    protected void onResume() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        // Set Selected NavBar item
        bottomNavigationView.setSelectedItemId(R.id.tasks_page);
        super.onResume();
    }
}