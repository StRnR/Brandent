package com.pixium.brandent.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pixium.brandent.api.models.auth.AuthResponse;
import com.pixium.brandent.api.models.auth.RegisterRequest;
import com.pixium.brandent.api.repos.AuthRepository;
import com.pixium.brandent.db.entities.Dentist;
import com.pixium.brandent.repos.DentistRepository;

public class RegisterViewModel extends AndroidViewModel {
    private final AuthRepository authRepository;
    private final DentistRepository dentistRepository;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        authRepository = AuthRepository.getInstance();
        dentistRepository = new DentistRepository(application);
    }

    public LiveData<AuthResponse> registerDentist(RegisterRequest registerRequest) {
        return authRepository.registerDentist(registerRequest);
    }

    public void insertDentist(Dentist dentist) {
        dentistRepository.insert(dentist);
    }
}
