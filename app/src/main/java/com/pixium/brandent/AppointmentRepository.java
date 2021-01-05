package com.pixium.brandent;

import android.app.Application;
import android.os.AsyncTask;

import com.pixium.brandent.db.AppDatabase;
import com.pixium.brandent.db.Appointment;
import com.pixium.brandent.db.AppointmentDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AppointmentRepository {
    private AppointmentDao appointmentDao;

    public AppointmentRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        appointmentDao = appDatabase.appointmentDao();
    }

    public void insert(Appointment appointment) {
        new InsertAppointmentAsyncTask(appointmentDao).execute(appointment);
    }

    public void update(Appointment appointment) {
        new UpdateAppointmentAsyncTask(appointmentDao).execute(appointment);
    }

    public void delete(Appointment appointment) {
        new DeleteAppointmentAsyncTask(appointmentDao).execute(appointment);
    }

    public List<Appointment> getByDate(Long start, Long end) throws ExecutionException, InterruptedException {
        return new GetAppointmentsByDateAsyncTask(appointmentDao).execute(start, end).get();
    }

    public List<Integer> getIncomeByDate(AppointmentIncomeTaskParams params)
            throws ExecutionException, InterruptedException {
        return new GetAppointmentIncomeByDateAsyncTask(appointmentDao).execute(params).get();
    }


    private static class GetAppointmentsByDateAsyncTask extends AsyncTask<Long, Void, List<Appointment>> {
        private AppointmentDao appointmentDao;

        private GetAppointmentsByDateAsyncTask(AppointmentDao appointmentDao) {
            this.appointmentDao = appointmentDao;
        }

        @Override
        protected List<Appointment> doInBackground(Long... longs) {
            return appointmentDao.getByDate(longs[0], longs[1]);
        }
    }

    private static class GetAppointmentIncomeByDateAsyncTask extends AsyncTask<AppointmentIncomeTaskParams, Void, List<Integer>> {
        private AppointmentDao appointmentDao;

        private GetAppointmentIncomeByDateAsyncTask(AppointmentDao appointmentDao) {
            this.appointmentDao = appointmentDao;
        }

        @Override
        protected List<Integer> doInBackground(AppointmentIncomeTaskParams... params) {
            return appointmentDao.getIncomeByDate(params[0].start, params[0].end, params[0].state);
        }
    }


    private static class InsertAppointmentAsyncTask extends AsyncTask<Appointment, Void, Void> {
        private AppointmentDao appointmentDao;

        private InsertAppointmentAsyncTask(AppointmentDao appointmentDao) {
            this.appointmentDao = appointmentDao;
        }

        @Override
        protected Void doInBackground(Appointment... appointments) {
            appointmentDao.insert(appointments[0]);
            return null;
        }
    }

    private static class UpdateAppointmentAsyncTask extends AsyncTask<Appointment, Void, Void> {
        private AppointmentDao appointmentDao;

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
        private AppointmentDao appointmentDao;

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
