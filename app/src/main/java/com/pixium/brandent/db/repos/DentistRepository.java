package com.pixium.brandent.db.repos;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.pixium.brandent.db.AppDatabase;
import com.pixium.brandent.db.daos.DentistDao;
import com.pixium.brandent.db.entities.Dentist;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DentistRepository {
    private final DentistDao dentistDao;
    private final LiveData<List<Dentist>> allDentists;

    public DentistRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        dentistDao = appDatabase.dentistDao();
        allDentists = dentistDao.getAllLive();
    }

    public void insert(Dentist dentist) {
        new InsertDentistAsyncTask(dentistDao).execute(dentist);
    }

    public void update(Dentist dentist) {
        new UpdateDentistAsyncTask(dentistDao).execute(dentist);
    }

    public void delete(Dentist dentist) {
        new DeleteDentistAsyncTask(dentistDao).execute(dentist);
    }

    public LiveData<List<Dentist>> getAllLive() {
        return allDentists;
    }

    public List<Dentist> getAll() throws ExecutionException, InterruptedException {
        return new GetAllDentistsAsyncTask(dentistDao).execute().get();
    }

    public Dentist getCurrent() throws ExecutionException, InterruptedException {
        return new GetCurrentDentistAsyncTask(dentistDao).execute().get();
    }

    public Dentist getById(int id) throws ExecutionException, InterruptedException {
        return new GetDentistByIdAsyncTask(dentistDao).execute(id).get();
    }


    private static class GetCurrentDentistAsyncTask extends AsyncTask<Void, Void, Dentist> {
        private final DentistDao dentistDao;

        private GetCurrentDentistAsyncTask(DentistDao dentistDao) {
            this.dentistDao = dentistDao;
        }

        @Override
        protected Dentist doInBackground(Void... voids) {
            return dentistDao.getCurrent();
        }
    }

    private static class GetAllDentistsAsyncTask extends AsyncTask<Void, Void, List<Dentist>> {
        private final DentistDao dentistDao;

        private GetAllDentistsAsyncTask(DentistDao dentistDao) {
            this.dentistDao = dentistDao;
        }

        @Override
        protected List<Dentist> doInBackground(Void... voids) {
            return dentistDao.getAll();
        }
    }

    private static class GetDentistByIdAsyncTask extends AsyncTask<Integer, Void, Dentist> {
        private final DentistDao dentistDao;

        private GetDentistByIdAsyncTask(DentistDao dentistDao) {
            this.dentistDao = dentistDao;
        }

        @Override
        protected Dentist doInBackground(Integer... integers) {
            return dentistDao.getById(integers[0]);
        }
    }

    private static class InsertDentistAsyncTask extends AsyncTask<Dentist, Void, Void> {
        private final DentistDao dentistDao;

        private InsertDentistAsyncTask(DentistDao dentistDao) {
            this.dentistDao = dentistDao;
        }

        @Override
        protected Void doInBackground(Dentist... dentists) {
            dentistDao.insert(dentists[0]);
            return null;
        }
    }

    private static class UpdateDentistAsyncTask extends AsyncTask<Dentist, Void, Void> {
        private final DentistDao dentistDao;

        private UpdateDentistAsyncTask(DentistDao dentistDao) {
            this.dentistDao = dentistDao;
        }

        @Override
        protected Void doInBackground(Dentist... dentists) {
            dentistDao.update(dentists[0]);
            return null;
        }
    }

    private static class DeleteDentistAsyncTask extends AsyncTask<Dentist, Void, Void> {
        private final DentistDao dentistDao;

        private DeleteDentistAsyncTask(DentistDao dentistDao) {
            this.dentistDao = dentistDao;
        }

        @Override
        protected Void doInBackground(Dentist... dentists) {
            dentistDao.delete(dentists[0]);
            return null;
        }
    }
}
