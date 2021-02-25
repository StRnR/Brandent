package com.pixium.brandent.views;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.pixium.brandent.ActiveUser;
import com.pixium.brandent.R;
import com.pixium.brandent.db.entities.Appointment;
import com.pixium.brandent.db.entities.Clinic;
import com.pixium.brandent.db.entities.Patient;
import com.pixium.brandent.viewmodels.AddAppointmentViewModel;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import saman.zamani.persiandate.PersianDate;

public class AddAppointmentActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private String patientName;
    private String patientPhone;
    private String clinicTitle;
    private int patientId;
    private Calendar visitCalendar;

    private AddAppointmentViewModel addAppointmentViewModel;

    private Button dateTimeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_add);

        EditText priceEt = findViewById(R.id.et_price_appointment_add);
        EditText diseaseEt = findViewById(R.id.et_disease_appointment_add);

        dateTimeBtn = findViewById(R.id.btn_time_appointment_add);
        Button submitBtn = findViewById(R.id.btn_submit_appointment_add);
        Button backBtn = findViewById(R.id.btn_back_appointment_add);

        addAppointmentViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).
                get(AddAppointmentViewModel.class);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                patientName = extras.getString(AddPatientActivity.EXTRA_PATIENT_NAME);
                patientPhone = extras.getString(AddPatientActivity.EXTRA_PATIENT_PHONE);
                patientId = extras.getInt(AddPatientActivity.EXTRA_PATIENT_ID);
                clinicTitle = extras.getString(AddPatientActivity.EXTRA_CLINIC_TITLE);
            }
        } else {
            patientName = (String) savedInstanceState.getSerializable(AddPatientActivity.EXTRA_PATIENT_NAME);
            patientPhone = (String) savedInstanceState.getSerializable(AddPatientActivity.EXTRA_PATIENT_PHONE);
            patientId = (int) savedInstanceState.getSerializable(AddPatientActivity.EXTRA_PATIENT_NAME);
            clinicTitle = (String) savedInstanceState.getSerializable(AddPatientActivity.EXTRA_CLINIC_TITLE);
        }

        // Date & Time Picker
        PersianDate pDate = new PersianDate();
        PersianCalendar initDate = new PersianCalendar();
        initDate.setPersianDate(pDate.getShYear(), pDate.getShMonth(), pDate.getShDay());

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
            picker.setActionTextColor(getResources().getColor(R.color.clinitickPurple));
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
//
                @Override
                public void onDismissed() {
                }
            });

            picker.show();
        });

        submitBtn.setOnClickListener(v -> {
            if (patientId == -1) {
                Patient patient = new Patient(ActiveUser.getInstance().getId(), null
                        , null, patientName, patientPhone);
                addAppointmentViewModel.insertPatient(patient);
                try {
                    List<Patient> patients = addAppointmentViewModel.getPatientByNumber(patientPhone);
                    List<Clinic> clinics = addAppointmentViewModel.getClinicByTitle(clinicTitle);
                    Appointment appointment = new Appointment(ActiveUser.getInstance().getId()
                            , null, null, clinics.get(0).getClinicId()
                            , patients.get(0).getPatientId(), visitCalendar.getTimeInMillis()
                            , Long.parseLong(priceEt.getText().toString())
                            , diseaseEt.getText().toString(), "unknown", 0);
                    addAppointmentViewModel.insertAppointment(appointment);
                    startActivity(new Intent(this, HomeActivity.class));
                } catch (ExecutionException | InterruptedException e) {
                    Toast.makeText(this
                            , "Something went wrong with inserted patient or clinic"
                            , Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            } else {
                try {
                    List<Patient> patients = addAppointmentViewModel.getPatientByNumber(patientPhone);
                    Patient patient = new Patient(ActiveUser.getInstance().getId()
                            , patients.get(0).getUuid(), null, patientName, patientPhone);
                    patient.setPatientId(patients.get(0).getPatientId());
                    addAppointmentViewModel.updatePatient(patient);
                    List<Clinic> clinics = addAppointmentViewModel.getClinicByTitle(clinicTitle);
                    Appointment appointment = new Appointment(ActiveUser.getInstance().getId()
                            , null, null, clinics.get(0).getClinicId()
                            , patients.get(0).getPatientId(), visitCalendar.getTimeInMillis()
                            , Long.parseLong(priceEt.getText().toString())
                            , diseaseEt.getText().toString(), "unknown", 0);
                    addAppointmentViewModel.insertAppointment(appointment);
                    startActivity(new Intent(this, HomeActivity.class));
                } catch (ExecutionException | InterruptedException e) {
                    Toast.makeText(this
                            , "Something went wrong with inserted patient or clinic"
                            , Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
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