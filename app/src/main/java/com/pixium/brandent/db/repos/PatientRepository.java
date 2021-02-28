package com.pixium.brandent.db.repos;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.pixium.brandent.ActiveUser;
import com.pixium.brandent.db.AppDatabase;
import com.pixium.brandent.db.daos.PatientDao;
import com.pixium.brandent.db.entities.Patient;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class PatientRepository {
    private final PatientDao patientDao;

    public PatientRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        patientDao = database.patientDao();
    }

    public void insert(Patient... patients) {
        new InsertPatientAsyncTask(patientDao).execute(patients);
    }

    public void update(Patient patient) {
        new UpdatePatientAsyncTask(patientDao).execute(patient);
    }

    public void delete(Patient patient) {
        new DeletePatientAsyncTask(patientDao).execute(patient);
    }

    public LiveData<List<Patient>> getAllPatients() {
        return patientDao.getAllPatients(ActiveUser.getInstance().getId());
    }

    public Patient[] getUnsynced(long lastUpdated) throws ExecutionException, InterruptedException {
        return new GetNotSyncedPatientsAsyncTask(patientDao).execute(lastUpdated).get();
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

    public Patient getByUuid(UUID uuid) throws ExecutionException, InterruptedException {
        return new GetPatientByUuidAsyncTask(patientDao).execute(uuid).get();
    }


    private static class InsertPatientAsyncTask extends AsyncTask<Patient, Void, Void> {
        private final PatientDao patientDao;

        private InsertPatientAsyncTask(PatientDao patientDao) {
            this.patientDao = patientDao;
        }

        @Override
        protected Void doInBackground(Patient... patients) {
            for (Patient patient : patients) {
                patientDao.insert(patient);
            }
            return null;
        }
    }

    private static class UpdatePatientAsyncTask extends AsyncTask<Patient, Void, Void> {
        private final PatientDao patientDao;

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
        private final PatientDao patientDao;

        private DeletePatientAsyncTask(PatientDao patientDao) {
            this.patientDao = patientDao;
        }

        @Override
        protected Void doInBackground(Patient... patients) {
            patientDao.delete(patients[0]);
            return null;
        }
    }

    private static class GetNotSyncedPatientsAsyncTask extends AsyncTask<Long, Void, Patient[]> {
        private final PatientDao patientDao;

        private GetNotSyncedPatientsAsyncTask(PatientDao patientDao) {
            this.patientDao = patientDao;
        }

        @Override
        protected Patient[] doInBackground(Long... longs) {
            return patientDao.getUnSynced(longs[0], ActiveUser.getInstance().getId());
        }
    }

    private static class FindPatientsByNameAsyncTask
            extends AsyncTask<String, Void, List<Patient>> {
        private final PatientDao patientDao;

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
        private final PatientDao patientDao;

        private GetPatientByNumberAsyncTask(PatientDao patientDao) {
            this.patientDao = patientDao;
        }

        @Override
        protected List<Patient> doInBackground(String... strings) {
            return patientDao.getPatientByNumber(strings[0], ActiveUser.getInstance().getId());
        }
    }

    private static class GetPatientByIdAsyncTask extends AsyncTask<Integer, Void, Patient> {
        private final PatientDao patientDao;

        private GetPatientByIdAsyncTask(PatientDao patientDao) {
            this.patientDao = patientDao;
        }

        @Override
        protected Patient doInBackground(Integer... integers) {
            return patientDao.getPatientById(integers[0], ActiveUser.getInstance().getId());
        }
    }

    private static class GetPatientByUuidAsyncTask extends AsyncTask<UUID, Void, Patient> {
        private final PatientDao patientDao;

        private GetPatientByUuidAsyncTask(PatientDao patientDao) {
            this.patientDao = patientDao;
        }

        @Override
        protected Patient doInBackground(UUID... uuids) {
            return patientDao.getByUuid(uuids[0], ActiveUser.getInstance().getId());
        }
    }
}
