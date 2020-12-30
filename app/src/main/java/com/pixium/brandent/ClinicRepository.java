package com.pixium.brandent;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.pixium.brandent.db.AppDatabase;
import com.pixium.brandent.db.Clinic;
import com.pixium.brandent.db.ClinicDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClinicRepository {
    private ClinicDao clinicDao;
    private LiveData<List<Clinic>> allClinics;

    public ClinicRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        clinicDao = database.clinicDao();
        allClinics = clinicDao.getAllClinicsLive();
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

    public LiveData<List<Clinic>> getAllClinicsLive() {
        return allClinics;
    }

    public List<Clinic> getAllClinics() throws ExecutionException, InterruptedException {
        return new GetAllClinicsAsyncTask(clinicDao).execute().get();
    }

    public List<Clinic> getClinicBytTitle(String title) throws ExecutionException, InterruptedException {
        return new GetClinicByTitleAsyncTask(clinicDao).execute(title).get();
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

    private static class GetAllClinicsAsyncTask extends AsyncTask<Void, Void, List<Clinic>> {
        private ClinicDao clinicDao;

        private GetAllClinicsAsyncTask(ClinicDao clinicDao) {
            this.clinicDao = clinicDao;
        }

        @Override
        protected List<Clinic> doInBackground(Void... voids) {
            return clinicDao.getAllClinics();
        }
    }

    private static class GetClinicByTitleAsyncTask extends AsyncTask<String, Void, List<Clinic>> {
        private ClinicDao clinicDao;

        private GetClinicByTitleAsyncTask(ClinicDao clinicDao) {
            this.clinicDao = clinicDao;
        }

        @Override
        protected List<Clinic> doInBackground(String... strings) {
            return clinicDao.getClinicByTitle(strings[0]);
        }
    }

}
