package com.pixium.brandent.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pixium.brandent.db.entities.Patient;
import com.pixium.brandent.db.repos.PatientRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PatientsViewModel extends AndroidViewModel {
    private final PatientRepository repository;
    private final LiveData<List<Patient>> allPatients;

    public PatientsViewModel(@NonNull Application application) {
        super(application);
        repository = new PatientRepository(application);
        allPatients = repository.getAllPatients();
    }

    public void update(Patient patient) {
        repository.update(patient);
    }

    public void delete(Patient patient) {
        repository.delete(patient);
    }

    public List<Patient> findPatientsByName(String string) throws ExecutionException, InterruptedException {
        return repository.findPatientsByName(string);
    }

    public LiveData<List<Patient>> getAllPatients() {
        return allPatients;
    }
}
