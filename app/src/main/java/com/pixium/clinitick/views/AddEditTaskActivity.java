package com.pixium.clinitick.views;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.pixium.clinitick.ActiveUser;
import com.pixium.clinitick.AlertReceiver;
import com.pixium.clinitick.NotificationStatic;
import com.pixium.clinitick.R;
import com.pixium.clinitick.db.entities.Clinic;
import com.pixium.clinitick.db.entities.Task;
import com.pixium.clinitick.viewmodels.AddEditTaskViewModel;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class AddEditTaskActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    public static final String EXTRA_TASK_ID =
            "com.pixium.brandent.EXTRA_TASK_ID";
    private int prevTaskId = -1;

    private AddEditTaskViewModel addEditTaskViewModel;

    private Calendar visitCalendar;
    private Button timeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        TextView headerTv = findViewById(R.id.tv_header_task_add);

        EditText titleEt = findViewById(R.id.et_title_task_add);

        timeBtn = findViewById(R.id.btn_time_task_add);
        Button submitBtn = findViewById(R.id.btn_submit_task_add);
        Button backBtn = findViewById(R.id.btn_back_task_add);

        Spinner clinicSpinner = findViewById(R.id.spinner_clinic_task_add);

        addEditTaskViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(AddEditTaskViewModel.class);

        // Clinic Spinner Init
        try {
            ArrayAdapter<String> clinicsArrayAdapter = new ArrayAdapter<>(this,
                    R.layout.spinner_category_item, addEditTaskViewModel.getAllClinicsTitles());
            clinicsArrayAdapter.setDropDownViewResource(R.layout.appointment_add_spinner_dropdown_item);
            clinicSpinner.setAdapter(clinicsArrayAdapter);
            clinicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(this, "Couldn't load clinics", Toast.LENGTH_SHORT).show();
        }


        // Date & Time picker
        PersianDate pDate = new PersianDate();
        PersianCalendar initDate = new PersianCalendar();
        initDate.setPersianDate(pDate.getShYear(), pDate.getShMonth(), pDate.getShDay());

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_TASK_ID)) {
            try {
                Task task = addEditTaskViewModel.getTaskById(intent.getIntExtra(EXTRA_TASK_ID
                        , -1));
                prevTaskId = task.getTaskId();
                headerTv.setText("ویرایش کار");
                titleEt.setText(task.getTitle());

                String[] strings = addEditTaskViewModel.getAllClinicsTitles();
                for (int i = 0; i < strings.length; i++) {
                    if (addEditTaskViewModel.getClinicById(task.getClinicForId()).getTitle()
                            .equals(strings[i])) {
                        clinicSpinner.setSelection(i);
                        break;
                    }
                }

                pDate = new PersianDate(task.getTime());
                initDate.setPersianDate(pDate.getShYear(), pDate.getShMonth(), pDate.getShDay());
                visitCalendar = GregorianCalendar.getInstance();
                visitCalendar.setTimeInMillis(initDate.getTimeInMillis());
                PersianDateFormat formatter = new PersianDateFormat("H:i");
                String timeStr = initDate.getPersianLongDate() + " - " + formatter.format(pDate);
                timeBtn.setText(timeStr);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        Typeface typeface = Typeface.DEFAULT;

        timeBtn.setOnClickListener(v -> {
            PersianDatePickerDialog picker = new PersianDatePickerDialog(this);
            picker.setPositiveButtonString("باشه");
            picker.setNegativeButton("بیخیال");
            picker.setTodayButton("امروز");
            picker.setTodayButtonVisible(true);
            picker.setMinYear(1300);
            picker.setMaxYear(PersianDatePickerDialog.THIS_YEAR);
            picker.setInitDate(initDate);
            picker.setActionTextColor(ContextCompat.getColor(this, R.color.clinitickPurple));
            picker.setTypeFace(typeface);
            picker.setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR);
            picker.setShowInBottomSheet(true);
            picker.setListener(new Listener() {
                @Override
                public void onDateSelected(PersianCalendar persianCalendar) {
//                            Log.d(TAG, "onDateSelected: " + persianCalendar.getGregorianChange());//Fri Oct 15 03:25:44 GMT+04:30 1582
//                            Log.d(TAG, "onDateSelected: " + persianCalendar.getTimeInMillis());//1583253636577
//                            Log.d(TAG, "onDateSelected: " + persianCalendar.getTime());//Tue Mar 03 20:10:36 GMT+03:30 2020
//                            Log.d(TAG, "onDateSelected: " + persianCalendar.getDelimiter());//  /
//                            Log.d(TAG, "onDateSelected: " + persianCalendar.getPersianLongDate());// سه‌شنبه  13  اسفند  1398
//                            Log.d(TAG, "onDateSelected: " + persianCalendar.getPersianLongDateAndTime()); //سه‌شنبه  13  اسفند  1398 ساعت 20:10:36
//                            Log.d(TAG, "onDateSelected: " + persianCalendar.getPersianMonthName()); //اسفند
//                            Log.d(TAG, "onDateSelected: " + persianCalendar.isPersianLeapYear());//false
                    String dateTimeStr = persianCalendar.getPersianLongDate();
                    timeBtn.setText(dateTimeStr);
                    visitCalendar = GregorianCalendar.getInstance();
                    visitCalendar.setTimeInMillis(persianCalendar.getTimeInMillis());
                    DialogFragment timePicker = new TimePickerFragment();
                    timePicker.show(getSupportFragmentManager(), "time picker");
                }

                @Override
                public void onDismissed() {
                }
            });

            picker.show();
        });


        submitBtn.setOnClickListener(v -> {
            if (titleEt.getText().toString().trim().equals("")) {
                Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show();
            } else if (visitCalendar == null) {
                Toast.makeText(this, "Please enter a date and time"
                        , Toast.LENGTH_SHORT).show();
            } else {
                if (prevTaskId == -1) {
                    try {
                        Clinic clinic = addEditTaskViewModel.getClinicByTitle(clinicSpinner.getSelectedItem()
                                .toString()).get(0);
                        Task task = new Task(clinic.getClinicId(), ActiveUser.getInstance().getId()
                                , null, null, visitCalendar.getTimeInMillis()
                                , titleEt.getText().toString(), "unknown", 0);
                        addEditTaskViewModel.insertTask(task);

                        // Notification
                        NotificationStatic.createNotificationChannel(this);

                        Intent intent1 = new Intent(this, AlertReceiver.class);
                        NotificationStatic.setLastTitle("کار");
                        NotificationStatic.setLastText(task.getTitle());
                        NotificationStatic.setLastId(task.getTaskId() * 100);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(this
                                , 0, intent1, 0);

                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, task.getTime(), pendingIntent);

                        finish();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Clinic clinic = addEditTaskViewModel.getClinicByTitle(clinicSpinner
                                .getSelectedItem().toString()).get(0);
                        Task task = addEditTaskViewModel.getTaskById(prevTaskId);
                        Task updateTask = new Task(clinic.getClinicId()
                                , ActiveUser.getInstance().getId(), task.getUuid(), null
                                , visitCalendar.getTimeInMillis(), titleEt.getText().toString()
                                , task.getState(), 0);
                        updateTask.setTaskId(task.getTaskId());
                        addEditTaskViewModel.updateTask(updateTask);
                        Toast.makeText(this, "Task Updated!", Toast.LENGTH_SHORT).show();

                        // Notification
                        NotificationStatic.createNotificationChannel(this);

                        Intent intent1 = new Intent(this, AlertReceiver.class);
                        NotificationStatic.setLastTitle("کار");
                        NotificationStatic.setLastText(task.getTitle());
                        NotificationStatic.setLastId(task.getTaskId() * 100);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(this
                                , 0, intent1, 0);

                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, task.getTime(), pendingIntent);

                        finish();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        backBtn.setOnClickListener(v -> super.onBackPressed());
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        visitCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        visitCalendar.set(Calendar.MINUTE, minute);
        String tmpStr = timeBtn.getText().toString() + " - " + hourOfDay + ":" + minute;
        timeBtn.setText(tmpStr);
    }
}