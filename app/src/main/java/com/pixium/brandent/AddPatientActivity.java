package com.pixium.brandent;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.pixium.brandent.db.Patient;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddPatientActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private AddPatientViewModel addPatientViewModel;

    public static final String EXTRA_PATIENT_NAME = "com.pixium.brandent.EXTRA_PATIENT_NAME";
    public static final String EXTRA_PATIENT_PHONE = "com.pixium.brandent.EXTRA_PATIENT_PHONE";
    public static final String EXTRA_PATIENT_ID = "com.pixium.brandent.EXTRA_PATIENT_ID";
    public static final String EXTRA_CLINIC_TITLE = "com.pixium.brandent.EXTRA_CLINIC_POSITION";

    private int clinicPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        Button backBtn = findViewById(R.id.btn_back_patient_add);
        Button submitBtn = findViewById(R.id.btn_submit_patient_add);

        EditText nameEt = findViewById(R.id.et_name_patient_add);
        EditText phoneEt = findViewById(R.id.et_number_patient_add);

        Spinner clinicSpinner = findViewById(R.id.spinner_clinic_patient_add);

        addPatientViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(AddPatientViewModel.class);

        // Clinic Spinner Init
        try {
            ArrayAdapter<String> clinicsArrayAdapter = new ArrayAdapter<>(this,
                    R.layout.spinner_category_item, addPatientViewModel.getAllClinicsTitles());
            clinicsArrayAdapter.setDropDownViewResource(R.layout.appointment_add_spinner_dropdown_item);
            clinicSpinner.setAdapter(clinicsArrayAdapter);
            clinicSpinner.setOnItemSelectedListener(this);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(this, "Couldn't load clinics", Toast.LENGTH_SHORT).show();
        }

        phoneEt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                try {
                    if (!phoneEt.getText().toString().equals("") && addPatientViewModel.getPatientByNumber(phoneEt.getText().toString()).size() != 0) {
                        Patient patient = addPatientViewModel.getPatientByNumber(phoneEt.getText().toString()).get(0);
                        nameEt.setText(patient.getName());
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return false;
        });

        submitBtn.setOnClickListener(v -> {
            if (!nameEt.getText().toString().equals("") && !phoneEt.getText().toString().equals("")) {
                Intent intent = new Intent(this, AddAppointmentActivity.class);
                intent.putExtra(EXTRA_PATIENT_NAME, nameEt.getText().toString());
                intent.putExtra(EXTRA_PATIENT_PHONE, phoneEt.getText().toString());
                intent.putExtra(EXTRA_CLINIC_TITLE, clinicSpinner.getSelectedItem().toString());
                try {
                    List<Patient> patients = addPatientViewModel.getPatientByNumber(phoneEt.getText().toString());

                    if (patients.size() != 0) {
                        intent.putExtra(EXTRA_PATIENT_ID, (Parcelable) patients.get(0));
                        startActivity(intent);
                    } else {
                        intent.putExtra(EXTRA_PATIENT_ID, -1);
                    }
                    startActivity(intent);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "Name or/and Number field empty!", Toast.LENGTH_SHORT).show();
            }
        });

        backBtn.setOnClickListener(v -> super.onBackPressed());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        clinicPosition = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}