package com.pixium.clinitick.views;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixium.clinitick.ActiveUser;
import com.pixium.clinitick.AlertReceiver;
import com.pixium.clinitick.DateTools;
import com.pixium.clinitick.NotificationStatic;
import com.pixium.clinitick.R;
import com.pixium.clinitick.adapters.AddAppointmentAdapter;
import com.pixium.clinitick.db.entities.Appointment;
import com.pixium.clinitick.db.entities.Clinic;
import com.pixium.clinitick.db.entities.Patient;
import com.pixium.clinitick.models.AddAppointmentModel;
import com.pixium.clinitick.viewmodels.AddAppointmentViewModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import saman.zamani.persiandate.PersianDate;

public class AddAppointmentActivity extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener {
    private String patientName;
    private String patientPhone;
    private String clinicTitle;
    private int patientId;
    private int curPosition;
    private Calendar visitCalendar;
    private AddAppointmentAdapter adapter;

    private AddAppointmentViewModel addAppointmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_add);

        RecyclerView recyclerView = findViewById(R.id.rv_appointment_add);

        Button backBtn = findViewById(R.id.btn_back_appointment_add);
        Button submitBtn = findViewById(R.id.btn_submit_appointment_add);

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

        // RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new AddAppointmentAdapter();
        recyclerView.setAdapter(adapter);
        List<AddAppointmentModel> initModels = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            AddAppointmentModel addAppointmentModel = new AddAppointmentModel("", null
                    , null);
            initModels.add(addAppointmentModel);
        }
        adapter.setAddAppointments(initModels);

        // Date & Time Picker
        adapter.setOnDateClickListener(position -> {
            curPosition = position;
            AddAppointmentModel addAppointmentModel = adapter.getAddAppointments().get(position);
            PersianDate pDate = new PersianDate();
            if (addAppointmentModel.getTimeStamp() != null) {
                pDate = new PersianDate(addAppointmentModel.getTimeStamp());
            }
            PersianCalendar initDate = new PersianCalendar();
            initDate.setPersianDate(pDate.getShYear(), pDate.getShMonth(), pDate.getShDay());
            visitCalendar = GregorianCalendar.getInstance();

            Typeface typeface = Typeface.DEFAULT;

            PersianDatePickerDialog picker = new PersianDatePickerDialog(AddAppointmentActivity.this);
            picker.setPositiveButtonString("باشه");
            picker.setNegativeButton("بیخیال");
            picker.setTodayButton("امروز");
            picker.setTodayButtonVisible(true);
            picker.setMinYear(1300);
            picker.setMaxYear(PersianDatePickerDialog.THIS_YEAR);
            picker.setInitDate(initDate);
            picker.setActionTextColor(ContextCompat.getColor(AddAppointmentActivity.this
                    , R.color.clinitickPurple));
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
            if (patientId == -1) {
                Patient patient = new Patient(ActiveUser.getInstance().getId(), null
                        , null, patientName, patientPhone, 0);
                addAppointmentViewModel.insertPatient(patient);
                try {
                    List<Patient> patients = addAppointmentViewModel.getPatientByNumber(patientPhone);
                    List<Clinic> clinics = addAppointmentViewModel.getClinicByTitle(clinicTitle);
                    List<AddAppointmentModel> addAppointmentModels = adapter.getAddAppointments();
                    for (AddAppointmentModel addAppointmentModel : addAppointmentModels) {
                        if (!addAppointmentModel.getTitle().trim().equals("")) {
                            long visitTime;
                            long price;
                            if (addAppointmentModel.getTimeStamp() == null) {
                                visitTime = DateTools.timestampFromString(DateTools.oldLastUpdated
                                        , DateTools.apiTimeFormat);
                            } else {
                                visitTime = addAppointmentModel.getTimeStamp();
                            }
                            if (addAppointmentModel.getPrice() == null) {
                                price = -1;
                            } else {
                                price = addAppointmentModel.getPrice();
                            }
                            Appointment appointment = new Appointment(ActiveUser.getInstance().getId()
                                    , null, null, clinics.get(0).getClinicId()
                                    , patients.get(0).getPatientId(), visitTime, price
                                    , addAppointmentModel.getTitle(), "unknown", 0);
                            addAppointmentViewModel.insertAppointment(appointment);

                            // Notification
                            NotificationStatic.createNotificationChannel(this);

                            Intent intent = new Intent(this, AlertReceiver.class);

                            NotificationStatic.setLastTitle(appointment.getTitle());
                            String notificationTxt = patients.get(0).getName() + " در " + clinics.get(0).getTitle();
                            NotificationStatic.setLastText(notificationTxt);
                            NotificationStatic.setLastId(appointment.getAppointmentId() * 1000);

                            PendingIntent pendingIntent = PendingIntent.getBroadcast(this
                                    , 0, intent, 0);

                            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, appointment.getVisitTime(), pendingIntent);
                        }
                    }
                    startActivity(new Intent(this, HomeActivity.class));
                } catch (ExecutionException | InterruptedException | ParseException e) {
                    Toast.makeText(this
                            , "Something went wrong with inserted patient or clinic"
                            , Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            } else {
                try {
                    List<Patient> patients = addAppointmentViewModel.getPatientByNumber(patientPhone);
                    Patient patient = new Patient(ActiveUser.getInstance().getId()
                            , patients.get(0).getUuid(), null, patientName, patientPhone
                            , 0);
                    patient.setPatientId(patients.get(0).getPatientId());
                    addAppointmentViewModel.updatePatient(patient);
                    List<Clinic> clinics = addAppointmentViewModel.getClinicByTitle(clinicTitle);
                    List<AddAppointmentModel> addAppointmentModels = adapter.getAddAppointments();
                    for (AddAppointmentModel addAppointmentModel : addAppointmentModels) {
                        if (!addAppointmentModel.getTitle().trim().equals("")) {
                            long visitTime;
                            long price;
                            if (addAppointmentModel.getTimeStamp() == null) {
                                visitTime = DateTools.timestampFromString(DateTools.oldLastUpdated
                                        , DateTools.apiTimeFormat);
                            } else {
                                visitTime = addAppointmentModel.getTimeStamp();
                            }
                            if (addAppointmentModel.getPrice() == null) {
                                price = -1;
                            } else {
                                price = addAppointmentModel.getPrice();
                            }
                            Appointment appointment = new Appointment(ActiveUser.getInstance().getId()
                                    , null, null, clinics.get(0).getClinicId()
                                    , patients.get(0).getPatientId(), visitTime, price
                                    , addAppointmentModel.getTitle(), "unknown", 0);
                            addAppointmentViewModel.insertAppointment(appointment);

                            // Notification
                            NotificationStatic.createNotificationChannel(this);

                            Intent intent = new Intent(this, AlertReceiver.class);

                            NotificationStatic.setLastTitle(appointment.getTitle());
                            String notificationTxt = patients.get(0).getName() + " در " + clinics.get(0).getTitle();
                            NotificationStatic.setLastText(notificationTxt);
                            NotificationStatic.setLastId(appointment.getAppointmentId() * 1000);

                            PendingIntent pendingIntent = PendingIntent.getBroadcast(this
                                    , 0, intent, 0);

                            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, appointment.getVisitTime(), pendingIntent);
                        }
                    }
                    startActivity(new Intent(this, HomeActivity.class));
                } catch (ExecutionException | InterruptedException | ParseException e) {
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
        AddAppointmentModel addAppointmentModel = adapter.getAddAppointments().get(curPosition);
        addAppointmentModel.setTimeStamp(visitCalendar.getTimeInMillis());
        List<AddAppointmentModel> addAppointmentModels = new ArrayList<>(adapter.getAddAppointments());
        addAppointmentModels.set(curPosition, addAppointmentModel);
        adapter.setAddAppointments(addAppointmentModels);
    }
}