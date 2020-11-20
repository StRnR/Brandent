package com.pixium.brandent;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

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

        Spinner categorySpinner = findViewById(R.id.spinner_category_appointment_add);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.appointment_category, R.layout.spinner_category_item);
        spinnerAdapter.setDropDownViewResource(R.layout.appointment_add_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);

        PersianDate pDate = new PersianDate();

        backBtn.setOnClickListener(v -> {
            onBackPressed();
        });

    }
}