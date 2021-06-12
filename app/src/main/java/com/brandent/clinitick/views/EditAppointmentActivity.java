package com.brandent.clinitick.views;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.brandent.clinitick.R;
import com.brandent.clinitick.UiTools;
import com.brandent.clinitick.db.entities.Appointment;
import com.brandent.clinitick.viewmodels.EditAppointmentViewModel;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class EditAppointmentActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private Calendar visitCalendar;
    public static final String EXTRA_APPOINTMENT_ID =
            "com.pixium.brandent.EXTRA_APPOINTMENT_ID";
    private int prevAppointmentId;

    private EditAppointmentViewModel editAppointmentViewModel;

    private Button dateTimeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_appointment);

        EditText titleEt = findViewById(R.id.et_disease_appointment_edit);
        EditText priceEt = findViewById(R.id.et_price_appointment_edit);

        dateTimeBtn = findViewById(R.id.btn_time_appointment_edit);
        Button backBtn = findViewById(R.id.btn_back_appointment_edit);
        Button submitBtn = findViewById(R.id.btn_submit_appointment_edit);

        editAppointmentViewModel = new ViewModelProvider(this
                , ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(EditAppointmentViewModel.class);

        priceEt.addTextChangedListener(new TextWatcher() {
            String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    priceEt.removeTextChangedListener(this);

                    String formatted = UiTools.priceToString(UiTools.stringToPrice(s.toString()), true);

                    current = formatted;
                    priceEt.setText(formatted);
                    priceEt.setSelection(formatted.length() - 6);

                    priceEt.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Date & Time Picker
        PersianCalendar initDate = new PersianCalendar();

        Intent intent = getIntent();
        prevAppointmentId = intent.getIntExtra(EXTRA_APPOINTMENT_ID, -1);
        if (prevAppointmentId != -1) {
            try {
                Appointment prevAppointment = editAppointmentViewModel.getAppointmentById(prevAppointmentId);
                titleEt.setText(prevAppointment.getTitle());
                priceEt.setText(UiTools.priceToString(prevAppointment.getPrice(), true));
                PersianDate pDate = new PersianDate(prevAppointment.getVisitTime());
                initDate.setPersianDate(pDate.getShYear(), pDate.getShMonth(), pDate.getShDay());
                visitCalendar = GregorianCalendar.getInstance();
                visitCalendar.setTimeInMillis(initDate.getTimeInMillis());
                PersianDateFormat formatter = new PersianDateFormat("H:i");
                String timeStr = initDate.getPersianLongDate() + " - " + formatter.format(pDate);
                dateTimeBtn.setText(timeStr);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            finish();
        }

        Typeface typeface = Typeface.DEFAULT;

        dateTimeBtn.setOnClickListener(v -> {
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
                    dateTimeBtn.setText(dateTimeStr);
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
            try {
                if (!titleEt.getText().toString().trim().equals("")) {
                    Appointment appointment = editAppointmentViewModel.getAppointmentById(prevAppointmentId);
                    Appointment updateAppointment = new Appointment(appointment.getDentistForId()
                            , appointment.getUuid(), null, appointment.getClinicForId()
                            , appointment.getPatientForId(), visitCalendar.getTimeInMillis()
                            , UiTools.stringToPrice(priceEt.getText().toString())
                            , titleEt.getText().toString(), appointment.getState(), 0);
                    updateAppointment.setAppointmentId(appointment.getAppointmentId());
                    editAppointmentViewModel.updateAppointment(updateAppointment);
                    finish();
                } else {
                    Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT)
                            .show();
                }
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                finish();
                e.printStackTrace();
            }
        });


        backBtn.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        visitCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        visitCalendar.set(Calendar.MINUTE, minute);
        String tmpStr = dateTimeBtn.getText().toString() + " - " + hourOfDay + ":" + minute;
        dateTimeBtn.setText(tmpStr);
    }
}