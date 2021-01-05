package com.pixium.brandent;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.pixium.brandent.db.Clinic;
import com.pixium.brandent.db.Patient;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddPatientViewModel extends AndroidViewModel {
    private PatientRepository patientRepository;
    private ClinicRepository clinicRepository;

    public AddPatientViewModel(@NonNull Application application) {
        super(application);
        patientRepository = new PatientRepository(application);
        clinicRepository = new ClinicRepository(application);
    }

    public List<Patient> getPatientByNumber(String string) throws ExecutionException, InterruptedException {
        return patientRepository.getPatientByNumber(string);
    }

    public String[] getAllClinicsTitles() throws ExecutionException, InterruptedException {
        List<Clinic> clinics = clinicRepository.getAllClinics();
        String[] titles = new String[clinics.size()];
        for (int i = 0; i < clinics.size(); i++) {
            titles[i] = clinics.get(i).getTitle();
        }
        return titles;
    }

}