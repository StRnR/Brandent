package com.brandent.clinitick.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.brandent.clinitick.api.models.auth.AuthResponse;
import com.brandent.clinitick.api.models.auth.RegisterRequest;
import com.brandent.clinitick.api.repos.AuthRepository;
import com.brandent.clinitick.db.entities.Clinic;
import com.brandent.clinitick.db.entities.Dentist;
import com.brandent.clinitick.db.repos.ClinicRepository;
import com.brandent.clinitick.db.repos.DentistRepository;

public class RegisterViewModel extends AndroidViewModel {
    private final AuthRepository authRepository;
    private final DentistRepository dentistRepository;
    private final ClinicRepository clinicRepository;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        authRepository = AuthRepository.getInstance();
        dentistRepository = new DentistRepository(application);
        clinicRepository = new ClinicRepository(application);
    }

    public LiveData<AuthResponse> registerDentist(RegisterRequest registerRequest) {
        return authRepository.registerDentist(registerRequest);
    }

    public void insertDentist(Dentist dentist) {
        dentistRepository.insert(dentist);
    }

    public void insertClinic(Clinic clinic) {
        clinicRepository.insert(clinic);
    }
}
