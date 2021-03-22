package com.pixium.clinitick.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.pixium.clinitick.db.entities.Dentist;
import com.pixium.clinitick.db.repos.DentistRepository;

import java.util.concurrent.ExecutionException;

public class ProfileViewModel extends AndroidViewModel {
    private final DentistRepository dentistRepository;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        dentistRepository = new DentistRepository(application);
    }

    public void updateDentist(Dentist dentist) {
        dentistRepository.update(dentist);
    }

    public Dentist getDentistById(int id) throws ExecutionException, InterruptedException {
        return dentistRepository.getById(id);
    }
}
