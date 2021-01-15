package com.pixium.brandent.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixium.brandent.R;
import com.pixium.brandent.adapters.PatientAppointmentAdapter;
import com.pixium.brandent.db.entities.Appointment;
import com.pixium.brandent.viewmodels.PatientDetailsViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PatientDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.pixium.brandent.views.EXTRA_ID";
    public static final String EXTRA_NAME = "com.pixium.brandent.views.EXTRA_NAME";
    public static final String EXTRA_PHONE = "com.pixium.brandent.views.EXTRA_PHONE";
    public static final String EXTRA_UUID = "com.pixium.brandent.views.EXTRA_UUID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        TextView headerTv = findViewById(R.id.tv_header_patient_detail);
        TextView clinicsTv = findViewById(R.id.tv_clinics_patient_details);
        TextView editPhoneTv = findViewById(R.id.tv_edit_phone_patient_detail);
        TextView editNameTv = findViewById(R.id.tv_edit_name_patient_detail);

        EditText phoneEt = findViewById(R.id.et_phone_patient_detail);
        EditText nameEt = findViewById(R.id.et_name_patient_detail);

        Button backBtn = findViewById(R.id.btn_back_patient_detail);

        RecyclerView appointmentsRv = findViewById(R.id.rv_appointments_patient_details);
        appointmentsRv.setLayoutManager(new LinearLayoutManager(this));
        appointmentsRv.setHasFixedSize(true);

        phoneEt.setEnabled(false);
        nameEt.setEnabled(false);


        Intent intent = getIntent();
        headerTv.setText(intent.getStringExtra(EXTRA_NAME));
        phoneEt.setText(intent.getStringExtra(EXTRA_PHONE));
        nameEt.setText(intent.getStringExtra(EXTRA_NAME));
        int patientId = intent.getIntExtra(EXTRA_ID, -1);

        if (patientId == -1) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }


        PatientDetailsViewModel patientDetailsViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(PatientDetailsViewModel.class);


        // Patient Clinics
        try {
            List<String> clinicTitles = patientDetailsViewModel.getPatientClinics(patientId);
            StringBuilder clinicsStr = new StringBuilder();
            for (int i = 0; i < clinicTitles.size(); i++) {
                if (i != 0) {
                    clinicsStr.append(" ،");
                }
                clinicsStr.append(clinicTitles.get(i));
            }
            clinicsTv.setText(clinicsStr.toString());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        // Patient Appointments
        PatientAppointmentAdapter adapter = new PatientAppointmentAdapter();
        appointmentsRv.setAdapter(adapter);
        try {
            List<Appointment> appointments = patientDetailsViewModel
                    .getAppointmentsByPatient(patientId);
            adapter.setAppointments(appointments);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        adapter.setOnItemCheckClickListener(id -> {
            Appointment curAppointment = patientDetailsViewModel.getAppointmentById(id);
            Appointment updateAppointment = new Appointment(curAppointment.getUuid()
                    , null, curAppointment.getClinicForId()
                    , curAppointment.getPatientForId(), curAppointment.getVisitTime()
                    , curAppointment.getPrice(), curAppointment.getTitle(), "DONE");
            updateAppointment.setAppointmentId(curAppointment.getAppointmentId());
            patientDetailsViewModel.updateAppointment(updateAppointment);
            try {
                List<Appointment> appointments = patientDetailsViewModel
                        .getAppointmentsByPatient(patientId);
                adapter.setAppointments(appointments);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        adapter.setOnItemCancelClickListener(id -> {
            Appointment curAppointment = patientDetailsViewModel.getAppointmentById(id);
            Appointment updateAppointment = new Appointment(curAppointment.getUuid()
                    , null, curAppointment.getClinicForId()
                    , curAppointment.getPatientForId(), curAppointment.getVisitTime()
                    , curAppointment.getPrice(), curAppointment.getTitle(), "CANCELED");
            updateAppointment.setAppointmentId(curAppointment.getAppointmentId());
            patientDetailsViewModel.updateAppointment(updateAppointment);
            try {
                List<Appointment> appointments = patientDetailsViewModel
                        .getAppointmentsByPatient(patientId);
                adapter.setAppointments(appointments);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        adapter.setOnItemUnknownClickListener(id -> {
            Appointment curAppointment = patientDetailsViewModel.getAppointmentById(id);
            Appointment updateAppointment = new Appointment(curAppointment.getUuid()
                    , null, curAppointment.getClinicForId()
                    , curAppointment.getPatientForId(), curAppointment.getVisitTime()
                    , curAppointment.getPrice(), curAppointment.getTitle(), "UNKNOWN");
            updateAppointment.setAppointmentId(curAppointment.getAppointmentId());
            patientDetailsViewModel.updateAppointment(updateAppointment);
            try {
                List<Appointment> appointments = patientDetailsViewModel
                        .getAppointmentsByPatient(patientId);
                adapter.setAppointments(appointments);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        });


        backBtn.setOnClickListener(v -> onBackPressed());
    }
}