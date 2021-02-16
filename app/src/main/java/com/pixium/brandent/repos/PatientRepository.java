package com.pixium.brandent.repos;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.pixium.brandent.ActiveUser;
import com.pixium.brandent.db.AppDatabase;
import com.pixium.brandent.db.daos.PatientDao;
import com.pixium.brandent.db.entities.Patient;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PatientRepository {
    private final PatientDao patientDao;
    private LiveData<List<Patient>> allPatients;

    public PatientRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        patientDao = database.patientDao();
        allPatients = patientDao.getAllPatients(ActiveUser.getInstance().getId());
    }

    public void insert(Patient patient) {
        new InsertPatientAsyncTask(patientDao).execute(patient);
    }

    public void update(Patient patient) {
        new UpdatePatientAsyncTask(patientDao).execute(patient);
    }

    public void delete(Patient patient) {
        new DeletePatientAsyncTask(patientDao).execute(patient);
    }

    public LiveData<List<Patient>> getAllPatients() {
        return allPatients;
    }

    public List<Patient> findPatientsByName(String string)
            throws ExecutionException, InterruptedException {
        return new FindPatientsByNameAsyncTask(patientDao).execute(string).get();
    }

    public List<Patient> getPatientByNumber(String string)
            throws ExecutionException, InterruptedException {
        return new GetPatientByNumberAsyncTask(patientDao).execute(string).get();
    }

    public Patient getById(int id) throws ExecutionException, InterruptedException {
        return new GetPatientByIdAsyncTask(patientDao).execute(id).get();
    }


    private static class InsertPatientAsyncTask extends AsyncTask<Patient, Void, Void> {
        private PatientDao patientDao;

        private InsertPatientAsyncTask(PatientDao patientDao) {
            this.patientDao = patientDao;
        }

        @Override
        protected Void doInBackground(Patient... patients) {
            patientDao.insert(patients[0]);
            return null;
        }
    }

    private static class UpdatePatientAsyncTask extends AsyncTask<Patient, Void, Void> {
        private PatientDao patientDao;

        private UpdatePatientAsyncTask(PatientDao patientDao) {
            this.patientDao = patientDao;
        }

        @Override
        protected Void doInBackground(Patient... patients) {
            patientDao.update(patients[0]);
            return null;
        }
    }

    private static class DeletePatientAsyncTask extends AsyncTask<Patient, Void, Void> {
        private PatientDao patientDao;

        private DeletePatientAsyncTask(PatientDao patientDao) {
            this.patientDao = patientDao;
        }

        @Override
        protected Void doInBackground(Patient... patients) {
            patientDao.delete(patients[0]);
            return null;
        }
    }

    private static class FindPatientsByNameAsyncTask
            extends AsyncTask<String, Void, List<Patient>> {
        private PatientDao patientDao;

        private FindPatientsByNameAsyncTask(PatientDao patientDao) {
            this.patientDao = patientDao;
        }

        @Override
        protected List<Patient> doInBackground(String... strings) {
            return patientDao.findPatientsByName(strings[0], ActiveUser.getInstance().getId());
        }
    }

    private static class GetPatientByNumberAsyncTask
            extends AsyncTask<String, Void, List<Patient>> {
        private PatientDao patientDao;

        private GetPatientByNumberAsyncTask(PatientDao patientDao) {
            this.patientDao = patientDao;
        }

        @Override
        protected List<Patient> doInBackground(String... strings) {
            return patientDao.getPatientByNumber(strings[0], ActiveUser.getInstance().getId());
        }
    }

    private static class GetPatientByIdAsyncTask extends AsyncTask<Integer, Void, Patient> {
        private PatientDao patientDao;

        private GetPatientByIdAsyncTask(PatientDao patientDao) {
            this.patientDao = patientDao;
        }

        @Override
        protected Patient doInBackground(Integer... integers) {
            return patientDao.getPatientById(integers[0], ActiveUser.getInstance().getId());
        }
    }
}
