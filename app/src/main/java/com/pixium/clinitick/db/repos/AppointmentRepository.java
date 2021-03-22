package com.pixium.clinitick.db.repos;

import android.app.Application;
import android.os.AsyncTask;

import com.pixium.clinitick.ActiveUser;
import com.pixium.clinitick.db.AppDatabase;
import com.pixium.clinitick.db.daos.AppointmentDao;
import com.pixium.clinitick.db.entities.Appointment;
import com.pixium.clinitick.models.AppointmentIncomeTaskParams;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class AppointmentRepository {
    private final AppointmentDao appointmentDao;

    public AppointmentRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        appointmentDao = appDatabase.appointmentDao();
    }

    public void insert(Appointment... appointments) {
        new InsertAppointmentAsyncTask(appointmentDao).execute(appointments);
    }

    public void update(Appointment appointment) {
        new UpdateAppointmentAsyncTask(appointmentDao).execute(appointment);
    }

    public void delete(Appointment appointment) {
        new DeleteAppointmentAsyncTask(appointmentDao).execute(appointment);
    }

    public Appointment[] getUnsynced(long lastUpdated) throws ExecutionException, InterruptedException {
        return new GetNotSyncedAppointmentsAsyncTask(appointmentDao).execute(lastUpdated).get();
    }

    public List<Appointment> getByDate(Long start, Long end)
            throws ExecutionException, InterruptedException {
        return new GetAppointmentsByDateAsyncTask(appointmentDao).execute(start, end).get();
    }

    public List<Appointment> getByPatient(int id) throws ExecutionException, InterruptedException {
        return new GetAppointmentsByPatientAsyncTask(appointmentDao).execute(id).get();
    }

    public List<Integer> getIncomeByDate(AppointmentIncomeTaskParams params)
            throws ExecutionException, InterruptedException {
        return new GetAppointmentIncomeByDateAsyncTask(appointmentDao).execute(params).get();
    }

    public Appointment getById(int id) throws ExecutionException, InterruptedException {
        return new GetAppointmentByIdAsyncTask(appointmentDao).execute(id).get();
    }

    public Appointment getByUuid(UUID uuid) throws ExecutionException, InterruptedException {
        return new GetAppointmentByUuidAsyncTask(appointmentDao).execute(uuid).get();
    }


    private static class GetAppointmentByIdAsyncTask extends AsyncTask<Integer, Void, Appointment> {
        private final AppointmentDao appointmentDao;

        private GetAppointmentByIdAsyncTask(AppointmentDao appointmentDao) {
            this.appointmentDao = appointmentDao;
        }

        @Override
        protected Appointment doInBackground(Integer... integers) {
            return appointmentDao.getById(integers[0], ActiveUser.getInstance().getId());
        }
    }

    private static class GetAppointmentByUuidAsyncTask extends AsyncTask<UUID, Void, Appointment> {
        private final AppointmentDao appointmentDao;

        private GetAppointmentByUuidAsyncTask(AppointmentDao appointmentDao) {
            this.appointmentDao = appointmentDao;
        }

        @Override
        protected Appointment doInBackground(UUID... uuids) {
            return appointmentDao.getByUuid(uuids[0], ActiveUser.getInstance().getId());
        }
    }

    private static class GetNotSyncedAppointmentsAsyncTask extends AsyncTask<Long, Void, Appointment[]> {
        private final AppointmentDao appointmentDao;

        private GetNotSyncedAppointmentsAsyncTask(AppointmentDao appointmentDao) {
            this.appointmentDao = appointmentDao;
        }

        @Override
        protected Appointment[] doInBackground(Long... longs) {
            return appointmentDao.getUnSynced(longs[0], ActiveUser.getInstance().getId());
        }
    }

    private static class GetAppointmentsByDateAsyncTask
            extends AsyncTask<Long, Void, List<Appointment>> {
        private final AppointmentDao appointmentDao;

        private GetAppointmentsByDateAsyncTask(AppointmentDao appointmentDao) {
            this.appointmentDao = appointmentDao;
        }

        @Override
        protected List<Appointment> doInBackground(Long... longs) {
            return appointmentDao.getByDate(longs[0], longs[1], ActiveUser.getInstance().getId());
        }
    }

    private static class GetAppointmentsByPatientAsyncTask
            extends AsyncTask<Integer, Void, List<Appointment>> {
        private final AppointmentDao appointmentDao;

        private GetAppointmentsByPatientAsyncTask(AppointmentDao appointmentDao) {
            this.appointmentDao = appointmentDao;
        }

        @Override
        protected List<Appointment> doInBackground(Integer... integers) {
            return appointmentDao.getByPatient(integers[0], ActiveUser.getInstance().getId());
        }
    }

    private static class GetAppointmentIncomeByDateAsyncTask
            extends AsyncTask<AppointmentIncomeTaskParams, Void, List<Integer>> {
        private final AppointmentDao appointmentDao;

        private GetAppointmentIncomeByDateAsyncTask(AppointmentDao appointmentDao) {
            this.appointmentDao = appointmentDao;
        }

        @Override
        protected List<Integer> doInBackground(AppointmentIncomeTaskParams... params) {
            return appointmentDao.getIncomeByDate(params[0].start, params[0].end, params[0].state
                    , ActiveUser.getInstance().getId());
        }
    }


    private static class InsertAppointmentAsyncTask extends AsyncTask<Appointment, Void, Void> {
        private final AppointmentDao appointmentDao;

        private InsertAppointmentAsyncTask(AppointmentDao appointmentDao) {
            this.appointmentDao = appointmentDao;
        }

        @Override
        protected Void doInBackground(Appointment... appointments) {
            for (Appointment appointment : appointments) {
                appointmentDao.insert(appointment);
            }
            return null;
        }
    }

    private static class UpdateAppointmentAsyncTask extends AsyncTask<Appointment, Void, Void> {
        private final AppointmentDao appointmentDao;

        private UpdateAppointmentAsyncTask(AppointmentDao appointmentDao) {
            this.appointmentDao = appointmentDao;
        }

        @Override
        protected Void doInBackground(Appointment... appointments) {
            appointmentDao.update(appointments[0]);
            return null;
        }
    }

    private static class DeleteAppointmentAsyncTask extends AsyncTask<Appointment, Void, Void> {
        private final AppointmentDao appointmentDao;

        private DeleteAppointmentAsyncTask(AppointmentDao appointmentDao) {
            this.appointmentDao = appointmentDao;
        }

        @Override
        protected Void doInBackground(Appointment... appointments) {
            appointmentDao.delete(appointments[0]);
            return null;
        }
    }
}
