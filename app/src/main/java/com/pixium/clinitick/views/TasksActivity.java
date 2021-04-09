package com.pixium.clinitick.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pixium.clinitick.ActiveUser;
import com.pixium.clinitick.R;
import com.pixium.clinitick.adapters.CalendarAdapter;
import com.pixium.clinitick.adapters.TasksAdapter;
import com.pixium.clinitick.db.entities.Appointment;
import com.pixium.clinitick.db.entities.Task;
import com.pixium.clinitick.models.TasksCalendarItem;
import com.pixium.clinitick.models.TasksCardModel;
import com.pixium.clinitick.viewmodels.TasksViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import saman.zamani.persiandate.PersianDate;

public class TasksActivity extends AppCompatActivity {
    private TasksViewModel tasksViewModel;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        TextView todayBtn = findViewById(R.id.btn_today_tasks);
        TextView emptyTasksTv = findViewById(R.id.tv_empty_tasks);
        TextView month_tv = findViewById(R.id.tv_month_tasks);

        Button addBtn = findViewById(R.id.btn_add_task_tasks);

        ImageView illustrationIv = findViewById(R.id.iv_illustration_tasks);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Selected NavBar item
        bottomNavigationView.setSelectedItemId(R.id.tasks_page);

        tasksViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).
                get(TasksViewModel.class);

        // NavBar ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home_page:
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    overridePendingTransition(0, 0);
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
                    return true;
                case R.id.profile_page:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });

        // Appointments RecyclerView
        RecyclerView appointmentsRv = findViewById(R.id.rv_appointments_tasks);
        appointmentsRv.setLayoutManager(new LinearLayoutManager(this));
        appointmentsRv.setHasFixedSize(true);

        TasksAdapter tasksAdapter = new TasksAdapter();
        appointmentsRv.setAdapter(tasksAdapter);

        // Calendar RecyclerView

        // Calendar Init
        PersianDate pDate = new PersianDate();

        month_tv.setText(pDate.monthName());

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

        List<TasksCardModel> tasksCardModels = new ArrayList<>();
        try {
            List<Appointment> appointments = tasksViewModel
                    .getAppointmentsByDate(startCal.getTimeInMillis(), endCal.getTimeInMillis());
            List<Task> tasks = tasksViewModel.getTasksByDate(startCal.getTimeInMillis()
                    , endCal.getTimeInMillis());
            if (appointments.size() + tasks.size() > 0) {
                illustrationIv.setVisibility(View.GONE);
                emptyTasksTv.setVisibility(View.GONE);
                appointmentsRv.setVisibility(View.VISIBLE);
                tasksCardModels = tasksViewModel.getTasksCardModels(appointments, tasks);
            } else {
                illustrationIv.setVisibility(View.VISIBLE);
                emptyTasksTv.setVisibility(View.VISIBLE);
                appointmentsRv.setVisibility(View.GONE);
            }
        } catch (ExecutionException | InterruptedException e) {
            illustrationIv.setVisibility(View.VISIBLE);
            emptyTasksTv.setVisibility(View.VISIBLE);
            appointmentsRv.setVisibility(View.GONE);
            e.printStackTrace();
        }

        tasksAdapter.setTasksCardModels(tasksCardModels);


        // Calendar Items
        ArrayList<TasksCalendarItem> tasksCalendarItems = new ArrayList<>();
        int dow;
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

        RecyclerView calendarRecyclerView = findViewById(R.id.rv_calendar_tasks);
        calendarRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager calendarLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, true);
        CalendarAdapter calendarAdapter = new CalendarAdapter(tasksCalendarItems, 0);

        calendarRecyclerView.setLayoutManager(calendarLayoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

        calendarAdapter.setSelectedPos(pDate.getShDay() - 1);

        calendarAdapter.setOnItemClickListener(position -> {
            calendarAdapter.setSelectedPos(position);
            pDate.setShDay(position + 1);
            startCal.set(Calendar.YEAR, pDate.getGrgYear());
            startCal.set(Calendar.MONTH, pDate.getGrgMonth() - 1);
            startCal.set(Calendar.DAY_OF_MONTH, pDate.getGrgDay());

            endCal.set(Calendar.YEAR, pDate.getGrgYear());
            endCal.set(Calendar.MONTH, pDate.getGrgMonth() - 1);
            endCal.set(Calendar.DAY_OF_MONTH, pDate.getGrgDay());

            List<TasksCardModel> finalTasksCardModels = new ArrayList<>();
            try {
                List<Appointment> appointments = tasksViewModel
                        .getAppointmentsByDate(startCal.getTimeInMillis(), endCal.getTimeInMillis());
                List<Task> tasks = tasksViewModel.getTasksByDate(startCal.getTimeInMillis()
                        , endCal.getTimeInMillis());
                if (appointments.size() + tasks.size() > 0) {
                    illustrationIv.setVisibility(View.GONE);
                    emptyTasksTv.setVisibility(View.GONE);
                    appointmentsRv.setVisibility(View.VISIBLE);
                    finalTasksCardModels = tasksViewModel.getTasksCardModels(appointments, tasks);
                } else {
                    illustrationIv.setVisibility(View.VISIBLE);
                    emptyTasksTv.setVisibility(View.VISIBLE);
                    appointmentsRv.setVisibility(View.GONE);
                }
            } catch (ExecutionException | InterruptedException e) {
                illustrationIv.setVisibility(View.VISIBLE);
                emptyTasksTv.setVisibility(View.VISIBLE);
                appointmentsRv.setVisibility(View.GONE);
                e.printStackTrace();
            }

            tasksAdapter.setTasksCardModels(finalTasksCardModels);
        });

        tasksAdapter.setOnItemClickListener(tasksCardModel -> {
            Intent intent;
            if (tasksCardModel.getDescription().equals("")) {
                intent = new Intent(TasksActivity.this, AddEditTaskActivity.class);
                intent.putExtra(AddEditTaskActivity.EXTRA_TASK_ID, tasksCardModel.getId());
            } else {
                intent = new Intent(TasksActivity.this, EditAppointmentActivity.class);
                intent.putExtra(EditAppointmentActivity.EXTRA_APPOINTMENT_ID, tasksCardModel.getId());
            }
            startActivity(intent);
        });

        tasksAdapter.setOnItemCheckClickListener(tasksCardModel -> {
            if (tasksCardModel.getDescription().equals("")) {
                Task curTask = tasksViewModel.getTaskById(tasksCardModel.getId());
                Task updateTask = new Task(curTask.getClinicForId(), curTask.getDentistForId()
                        , curTask.getUuid(), null, curTask.getTime(), curTask.getTitle()
                        , "done", 0);
                updateTask.setTaskId(curTask.getTaskId());
                tasksViewModel.updateTask(updateTask);
                List<Appointment> appointments = tasksViewModel.
                        getAppointmentsByDate(startCal.getTimeInMillis(), endCal.getTimeInMillis());
                List<Task> tasks = tasksViewModel.getTasksByDate(startCal.getTimeInMillis()
                        , endCal.getTimeInMillis());
                tasksAdapter.setTasksCardModels(tasksViewModel.getTasksCardModels(appointments, tasks));
            } else {
                Appointment curAppointment = tasksViewModel.getAppointmentById(tasksCardModel.getId());
                Appointment updateAppointment = new Appointment(ActiveUser.getInstance().getId()
                        , curAppointment.getUuid(), null, curAppointment.getClinicForId()
                        , curAppointment.getPatientForId(), curAppointment.getVisitTime()
                        , curAppointment.getPrice(), curAppointment.getTitle(), "done", 0);
                updateAppointment.setAppointmentId(curAppointment.getAppointmentId());
                tasksViewModel.updateAppointment(updateAppointment);
                List<Appointment> appointments = tasksViewModel.
                        getAppointmentsByDate(startCal.getTimeInMillis(), endCal.getTimeInMillis());
                List<Task> tasks = tasksViewModel.getTasksByDate(startCal.getTimeInMillis()
                        , endCal.getTimeInMillis());
                tasksAdapter.setTasksCardModels(tasksViewModel.getTasksCardModels(appointments, tasks));
            }
        });

        tasksAdapter.setOnItemCancelClickListener(tasksCardModel -> {
            if (tasksCardModel.getDescription().equals("")) {
                Task curTask = tasksViewModel.getTaskById(tasksCardModel.getId());
                Task updateTask = new Task(curTask.getClinicForId(), curTask.getDentistForId()
                        , curTask.getUuid(), null, curTask.getTime(), curTask.getTitle()
                        , "canceled", 0);
                updateTask.setTaskId(curTask.getTaskId());
                tasksViewModel.updateTask(updateTask);
                List<Appointment> appointments = tasksViewModel.
                        getAppointmentsByDate(startCal.getTimeInMillis(), endCal.getTimeInMillis());
                List<Task> tasks = tasksViewModel.getTasksByDate(startCal.getTimeInMillis()
                        , endCal.getTimeInMillis());
                tasksAdapter.setTasksCardModels(tasksViewModel.getTasksCardModels(appointments, tasks));
            } else {
                Appointment curAppointment = tasksViewModel.getAppointmentById(tasksCardModel.getId());
                Appointment updateAppointment = new Appointment(ActiveUser.getInstance().getId()
                        , curAppointment.getUuid(), null, curAppointment.getClinicForId()
                        , curAppointment.getPatientForId(), curAppointment.getVisitTime()
                        , curAppointment.getPrice(), curAppointment.getTitle(), "canceled", 0);
                updateAppointment.setAppointmentId(curAppointment.getAppointmentId());
                tasksViewModel.updateAppointment(updateAppointment);
                List<Appointment> appointments = tasksViewModel.
                        getAppointmentsByDate(startCal.getTimeInMillis(), endCal.getTimeInMillis());
                List<Task> tasks = tasksViewModel.getTasksByDate(startCal.getTimeInMillis()
                        , endCal.getTimeInMillis());
                tasksAdapter.setTasksCardModels(tasksViewModel.getTasksCardModels(appointments, tasks));
            }
        });

        tasksAdapter.setOnItemUnknownClickListener(tasksCardModel -> {
            if (tasksCardModel.getDescription().equals("")) {
                Task curTask = tasksViewModel.getTaskById(tasksCardModel.getId());
                Task updateTask = new Task(curTask.getClinicForId(), curTask.getDentistForId()
                        , curTask.getUuid(), null, curTask.getTime(), curTask.getTitle()
                        , "unknown", 0);
                updateTask.setTaskId(curTask.getTaskId());
                tasksViewModel.updateTask(updateTask);
                List<Appointment> appointments = tasksViewModel.
                        getAppointmentsByDate(startCal.getTimeInMillis(), endCal.getTimeInMillis());
                List<Task> tasks = tasksViewModel.getTasksByDate(startCal.getTimeInMillis()
                        , endCal.getTimeInMillis());
                tasksAdapter.setTasksCardModels(tasksViewModel.getTasksCardModels(appointments, tasks));
            } else {
                Appointment curAppointment = tasksViewModel.getAppointmentById(tasksCardModel.getId());
                Appointment updateAppointment = new Appointment(ActiveUser.getInstance().getId()
                        , curAppointment.getUuid(), null, curAppointment.getClinicForId()
                        , curAppointment.getPatientForId(), curAppointment.getVisitTime()
                        , curAppointment.getPrice(), curAppointment.getTitle(), "unknown", 0);
                updateAppointment.setAppointmentId(curAppointment.getAppointmentId());
                tasksViewModel.updateAppointment(updateAppointment);
                List<Appointment> appointments = tasksViewModel.
                        getAppointmentsByDate(startCal.getTimeInMillis(), endCal.getTimeInMillis());
                List<Task> tasks = tasksViewModel.getTasksByDate(startCal.getTimeInMillis()
                        , endCal.getTimeInMillis());
                tasksAdapter.setTasksCardModels(tasksViewModel.getTasksCardModels(appointments, tasks));
            }
        });

        todayBtn.setOnClickListener(v -> {
            PersianDate pToday = new PersianDate();
            calendarAdapter.setSelectedPos(pToday.getShDay() - 1);

            startCal.set(Calendar.YEAR, pToday.getGrgYear());
            startCal.set(Calendar.MONTH, pToday.getGrgMonth() - 1);
            startCal.set(Calendar.DAY_OF_MONTH, pToday.getGrgDay());

            endCal.set(Calendar.YEAR, pToday.getGrgYear());
            endCal.set(Calendar.MONTH, pToday.getGrgMonth() - 1);
            endCal.set(Calendar.DAY_OF_MONTH, pToday.getGrgDay());
            List<TasksCardModel> finalTasksCardModels1 = new ArrayList<>();
            try {
                List<Appointment> appointments = tasksViewModel
                        .getAppointmentsByDate(startCal.getTimeInMillis(), endCal.getTimeInMillis());
                List<Task> tasks = tasksViewModel.getTasksByDate(startCal.getTimeInMillis()
                        , endCal.getTimeInMillis());
                if (appointments.size() + tasks.size() > 0) {
                    illustrationIv.setVisibility(View.GONE);
                    emptyTasksTv.setVisibility(View.GONE);
                    appointmentsRv.setVisibility(View.VISIBLE);
                    finalTasksCardModels1 = tasksViewModel.getTasksCardModels(appointments, tasks);
                } else {
                    illustrationIv.setVisibility(View.VISIBLE);
                    emptyTasksTv.setVisibility(View.VISIBLE);
                    appointmentsRv.setVisibility(View.GONE);
                }
            } catch (ExecutionException | InterruptedException e) {
                illustrationIv.setVisibility(View.VISIBLE);
                emptyTasksTv.setVisibility(View.VISIBLE);
                appointmentsRv.setVisibility(View.GONE);
                e.printStackTrace();
            }

            tasksAdapter.setTasksCardModels(finalTasksCardModels1);
        });

        addBtn.setOnClickListener(v -> startActivity(new Intent(this
                , AddEditTaskActivity.class)));
    }

    @Override
    protected void onResume() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        // Set Selected NavBar item
        bottomNavigationView.setSelectedItemId(R.id.tasks_page);
        super.onResume();
    }
}