package com.pixium.brandent;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.pixium.brandent.db.AppDatabase;
import com.pixium.brandent.db.Clinic;
import com.pixium.brandent.db.ClinicDao;

import java.util.List;

public class ClinicRepository {
    private ClinicDao clinicDao;
    private LiveData<List<Clinic>> allClinics;

    public ClinicRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        clinicDao = database.clinicDao();
        allClinics = clinicDao.getAllClinics();
    }

    public void insert(Clinic clinic) {
        new InsertClinicAsyncTask(clinicDao).execute(clinic);
    }

    public void update(Clinic clinic) {
        new UpdateClinicAsyncTask(clinicDao).execute(clinic);
    }

    public void delete(Clinic clinic) {
        new DeleteClinicAsyncTask(clinicDao).execute(clinic);
    }

    public LiveData<List<Clinic>> getAllClinics() {
        return allClinics;
    }

    private static class InsertClinicAsyncTask extends AsyncTask<Clinic, Void, Void> {
        private ClinicDao clinicDao;

        private InsertClinicAsyncTask(ClinicDao clinicDao) {
            this.clinicDao = clinicDao;
        }

        @Override
        protected Void doInBackground(Clinic... clinics) {
            clinicDao.insert(clinics[0]);
            return null;
        }
    }

    private static class UpdateClinicAsyncTask extends AsyncTask<Clinic, Void, Void> {
        private ClinicDao clinicDao;

        private UpdateClinicAsyncTask(ClinicDao clinicDao) {
            this.clinicDao = clinicDao;
        }

        @Override
        protected Void doInBackground(Clinic... clinics) {
            clinicDao.update(clinics[0]);
            return null;
        }
    }

    private static class DeleteClinicAsyncTask extends AsyncTask<Clinic, Void, Void> {
        private ClinicDao clinicDao;

        private DeleteClinicAsyncTask(ClinicDao clinicDao) {
            this.clinicDao = clinicDao;
        }

        @Override
        protected Void doInBackground(Clinic... clinics) {
            clinicDao.delete(clinics[0]);
            return null;
        }
    }

}
