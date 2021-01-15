package com.pixium.brandent.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixium.brandent.R;
import com.pixium.brandent.adapters.PatientAdapter;
import com.pixium.brandent.db.entities.Patient;
import com.pixium.brandent.viewmodels.PatientsViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PatientsActivity extends AppCompatActivity {
    private PatientsViewModel patientsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);

        Button backBtn = findViewById(R.id.btn_back_patients);

        EditText searchEt = findViewById(R.id.et_search_patients);

        RecyclerView recyclerView = findViewById(R.id.rv_patients);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        PatientAdapter adapter = new PatientAdapter();
        recyclerView.setAdapter(adapter);

        patientsViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(PatientsViewModel.class);

        patientsViewModel.getAllPatients().observe(this, adapter::setPatients);

        adapter.setOnItemClickListener(patient -> {
            Intent intent = new Intent(PatientsActivity.this
                    , PatientDetailsActivity.class);
            intent.putExtra(PatientDetailsActivity.EXTRA_ID, patient.getPatientId());
            intent.putExtra(PatientDetailsActivity.EXTRA_NAME, patient.getName());
            intent.putExtra(PatientDetailsActivity.EXTRA_PHONE, patient.getPhone());
            intent.putExtra(PatientDetailsActivity.EXTRA_UUID, patient.getUuid());
            startActivity(intent);
        });

        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Patient> resPatients;
                if (!s.toString().equals("")) {
                    try {
                        resPatients = patientsViewModel.findPatientsByName(s.toString());
                        adapter.setPatients(resPatients);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    patientsViewModel.getAllPatients()
                            .observe(PatientsActivity.this, adapter::setPatients);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        backBtn.setOnClickListener(v -> super.onBackPressed());
    }
}