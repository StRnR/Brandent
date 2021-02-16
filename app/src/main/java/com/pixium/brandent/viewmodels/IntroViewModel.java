package com.pixium.brandent.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pixium.brandent.api.models.auth.AuthResponse;
import com.pixium.brandent.api.models.auth.LoginRequest;
import com.pixium.brandent.api.repos.AuthRepository;
import com.pixium.brandent.db.entities.Dentist;
import com.pixium.brandent.repos.DentistRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class IntroViewModel extends AndroidViewModel {
    private final AuthRepository authRepository;
    private final DentistRepository dentistRepository;
    private LiveData<List<Dentist>> allDentists;

    public IntroViewModel(@NonNull Application application) {
        super(application);
        authRepository = AuthRepository.getInstance();
        dentistRepository = new DentistRepository(application);
        allDentists = dentistRepository.getAllLive();
    }

    public LiveData<AuthResponse> loginDentist(LoginRequest loginRequest) {
        return authRepository.loginDentist(loginRequest);
    }


    public void insertDentist(Dentist dentist) {
        dentistRepository.insert(dentist);
    }

    public void updateDentist(Dentist dentist) {
        dentistRepository.update(dentist);
    }

    public LiveData<List<Dentist>> getAllDentistsLive() {
        return allDentists;
    }

    public List<Dentist> getAllDentists() throws ExecutionException, InterruptedException {
        return dentistRepository.getAll();
    }

    public Dentist getCurrentDentist() throws ExecutionException, InterruptedException {
        return dentistRepository.getCurrent();
    }

    public Dentist getDentistById(int id) throws ExecutionException, InterruptedException {
        return dentistRepository.getById(id);
    }
}
