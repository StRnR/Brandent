package com.pixium.brandent;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import saman.zamani.persiandate.PersianDate;

public class AddAppointmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_add);

        EditText nameEt = findViewById(R.id.et_name_appointment_add);
        EditText phoneEt = findViewById(R.id.et_number_appointment_add);
        EditText priceEt = findViewById(R.id.et_price_appointment_add);
        EditText allergyEt = findViewById(R.id.et_allergy_appointment_add);
        EditText noteEt = findViewById(R.id.et_notes_appointment_add);

        Button allergyTrueBtn = findViewById(R.id.btn_check_allergy_appointment_add);
        Button allergyFalseBtn = findViewById(R.id.btn_ignore_allergy_appointment_add);
        Button dateTimeBtn = findViewById(R.id.btn_time_appointment_add);
        Button addPhotoBtn = findViewById(R.id.btn_add_photo_appointment_add);
        Button submitBtn = findViewById(R.id.btn_submit_appointment_add);
        Button backBtn = findViewById(R.id.btn_back_appointment_add);

        // Visit Occasion Spinner
        Spinner categorySpinner = findViewById(R.id.spinner_category_appointment_add);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.appointment_category, R.layout.spinner_category_item);
        spinnerAdapter.setDropDownViewResource(R.layout.appointment_add_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);

        // Date & Time Picker
        PersianDate pDate = new PersianDate();
        PersianCalendar initDate = new PersianCalendar();
        initDate.setPersianDate(pDate.getShYear(), pDate.getShMonth(), pDate.getShDay());

        Typeface typeface = Typeface.DEFAULT;

        dateTimeBtn.setOnClickListener(v -> {

            PersianDatePickerDialog picker = new PersianDatePickerDialog(this)
                    .setPositiveButtonString("باشه")
                    .setNegativeButton("بیخیال")
                    .setTodayButton("امروز")
                    .setTodayButtonVisible(true)
                    .setMinYear(1300)
                    .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                    .setInitDate(initDate)
                    .setActionTextColor(getResources().getColor(R.color.orange))
                    .setTypeFace(typeface)
                    .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                    .setShowInBottomSheet(true)
                    .setListener(new Listener() {
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
                            dateTimeBtn.setText(persianCalendar.getPersianLongDate());
                            Toast.makeText(getApplicationContext(), persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onDismissed() {

                        }
                    });

            picker.show();
        });


        backBtn.setOnClickListener(v -> {
            onBackPressed();
        });

    }

}