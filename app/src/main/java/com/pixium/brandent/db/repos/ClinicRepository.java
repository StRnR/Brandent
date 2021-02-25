package com.pixium.brandent.db.repos;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.pixium.brandent.ActiveUser;
import com.pixium.brandent.db.AppDatabase;
import com.pixium.brandent.db.daos.ClinicDao;
import com.pixium.brandent.db.entities.Clinic;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class ClinicRepository {
    private final ClinicDao clinicDao;

    public ClinicRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        clinicDao = database.clinicDao();
    }

    public void insert(Clinic... clinics) {
        new InsertClinicAsyncTask(clinicDao).execute(clinics);
    }

    public void update(Clinic clinic) {
        new UpdateClinicAsyncTask(clinicDao).execute(clinic);
    }

    public void delete(Clinic clinic) {
        new DeleteClinicAsyncTask(clinicDao).execute(clinic);
    }

    public LiveData<List<Clinic>> getAllClinicsLive() {
        return clinicDao.getAllClinicsLive(ActiveUser.getInstance().getId());
    }

    public List<Clinic> getAllClinics() throws ExecutionException, InterruptedException {
        return new GetAllClinicsAsyncTask(clinicDao).execute().get();
    }

    public Clinic[] getUnsynced(long lastUpdated) throws ExecutionException, InterruptedException {
        return new GetNotSyncedClinicsAsyncTask(clinicDao).execute(lastUpdated)
                .execute(lastUpdated).get();
    }

    public List<Clinic> getClinicBytTitle(String title) throws ExecutionException, InterruptedException {
        return new GetClinicByTitleAsyncTask(clinicDao).execute(title).get();
    }

    public Clinic getById(int id) throws ExecutionException, InterruptedException {
        return new GetClinicByIdAsyncTask(clinicDao).execute(id).get();
    }

    public Clinic getByUuid(UUID uuid) throws ExecutionException, InterruptedException {
        return new GetClinicByUuidAsyncTask(clinicDao).execute(uuid).get();
    }


    private static class InsertClinicAsyncTask extends AsyncTask<Clinic, Void, Void> {
        private final ClinicDao clinicDao;

        private InsertClinicAsyncTask(ClinicDao clinicDao) {
            this.clinicDao = clinicDao;
        }

        @Override
        protected Void doInBackground(Clinic... clinics) {
            for (Clinic clinic : clinics) {
                clinicDao.insert(clinic);
            }
            return null;
        }
    }

    private static class UpdateClinicAsyncTask extends AsyncTask<Clinic, Void, Void> {
        private final ClinicDao clinicDao;

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
        private final ClinicDao clinicDao;

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
        private final ClinicDao clinicDao;

        private GetAllClinicsAsyncTask(ClinicDao clinicDao) {
            this.clinicDao = clinicDao;
        }

        @Override
        protected List<Clinic> doInBackground(Void... voids) {
            return clinicDao.getAllClinics(ActiveUser.getInstance().getId());
        }
    }

    private static class GetNotSyncedClinicsAsyncTask extends AsyncTask<Long, Void, Clinic[]> {
        private final ClinicDao clinicDao;

        private GetNotSyncedClinicsAsyncTask(ClinicDao clinicDao) {
            this.clinicDao = clinicDao;
        }

        @Override
        protected Clinic[] doInBackground(Long... longs) {
            return clinicDao.getUnSynced(longs[0], ActiveUser.getInstance().getId());
        }
    }

    private static class GetClinicByTitleAsyncTask extends AsyncTask<String, Void, List<Clinic>> {
        private final ClinicDao clinicDao;

        private GetClinicByTitleAsyncTask(ClinicDao clinicDao) {
            this.clinicDao = clinicDao;
        }

        @Override
        protected List<Clinic> doInBackground(String... strings) {
            return clinicDao.getClinicByTitle(strings[0], ActiveUser.getInstance().getId());
        }
    }

    private static class GetClinicByIdAsyncTask extends AsyncTask<Integer, Void, Clinic> {
        private final ClinicDao clinicDao;

        private GetClinicByIdAsyncTask(ClinicDao clinicDao) {
            this.clinicDao = clinicDao;
        }

        @Override
        protected Clinic doInBackground(Integer... integers) {
            return clinicDao.getById(integers[0], ActiveUser.getInstance().getId());
        }
    }

    private static class GetClinicByUuidAsyncTask extends AsyncTask<UUID, Void, Clinic> {
        private final ClinicDao clinicDao;

        private GetClinicByUuidAsyncTask(ClinicDao clinicDao) {
            this.clinicDao = clinicDao;
        }

        @Override
        protected Clinic doInBackground(UUID... uuids) {
            return clinicDao.getByUuid(uuids[0], ActiveUser.getInstance().getId());
        }
    }
}
