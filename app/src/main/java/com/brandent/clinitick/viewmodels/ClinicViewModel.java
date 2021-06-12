package com.brandent.clinitick.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.brandent.clinitick.db.entities.Clinic;
import com.brandent.clinitick.db.repos.ClinicRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClinicViewModel extends AndroidViewModel {
    private final ClinicRepository repository;
    private final LiveData<List<Clinic>> allClinics;

    public ClinicViewModel(@NonNull Application application) {
        super(application);
        repository = new ClinicRepository(application);
        allClinics = repository.getAllClinicsLive();
    }

    public void insert(Clinic cLinic) {
        repository.insert(cLinic);
    }

    public void update(Clinic clinic) {
        repository.update(clinic);
    }

    public void delete(Clinic clinic) {
        repository.delete(clinic);
    }

    public LiveData<List<Clinic>> getAllClinics() {
        return allClinics;
    }

    public List<Clinic> getClinicByTitle(String title) throws ExecutionException, InterruptedException {
        return repository.getClinicBytTitle(title);
    }
}
