package com.pixium.brandent;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pixium.brandent.db.Clinic;

import java.util.List;

public class ClinicViewModel extends AndroidViewModel {
    private ClinicRepository repository;
    private LiveData<List<Clinic>> allClinics;

    public ClinicViewModel(@NonNull Application application) {
        super(application);
        repository = new ClinicRepository(application);
        allClinics = repository.getAllClinics();
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
}
