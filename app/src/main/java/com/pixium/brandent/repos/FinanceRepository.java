package com.pixium.brandent.repos;

import android.app.Application;
import android.os.AsyncTask;

import com.pixium.brandent.models.FinanceTaskParams;
import com.pixium.brandent.db.AppDatabase;
import com.pixium.brandent.db.entities.Finance;
import com.pixium.brandent.db.daos.FinanceDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FinanceRepository {
    private FinanceDao financeDao;

    public FinanceRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        financeDao = database.financeDao();
    }

    public void insert(Finance finance) {
        new InsertFinanceAsyncTask(financeDao).execute(finance);
    }

    public void update(Finance finance) {
        new UpdateFinanceAsyncTask(financeDao).execute(finance);
    }

    public void delete(Finance finance) {
        new DeleteFinanceAsyncTask(financeDao).execute(finance);
    }

    public List<Integer> getFinanceSumByDateAndType(FinanceTaskParams params)
            throws ExecutionException, InterruptedException {
        return new GetFinanceSumByDateAndTypeAsyncTask(financeDao).execute(params).get();
    }


    private static class InsertFinanceAsyncTask extends AsyncTask<Finance, Void, Void> {
        private FinanceDao financeDao;

        private InsertFinanceAsyncTask(FinanceDao financeDao) {
            this.financeDao = financeDao;
        }

        @Override
        protected Void doInBackground(Finance... finances) {
            financeDao.insert(finances[0]);
            return null;
        }
    }

    private static class UpdateFinanceAsyncTask extends AsyncTask<Finance, Void, Void> {
        private FinanceDao financeDao;

        private UpdateFinanceAsyncTask(FinanceDao financeDao) {
            this.financeDao = financeDao;
        }

        @Override
        protected Void doInBackground(Finance... finances) {
            financeDao.update(finances[0]);
            return null;
        }
    }

    private static class DeleteFinanceAsyncTask extends AsyncTask<Finance, Void, Void> {
        private FinanceDao financeDao;

        private DeleteFinanceAsyncTask(FinanceDao financeDao) {
            this.financeDao = financeDao;
        }

        @Override
        protected Void doInBackground(Finance... finances) {
            financeDao.delete(finances[0]);
            return null;
        }
    }

    private static class GetFinanceSumByDateAndTypeAsyncTask extends AsyncTask<FinanceTaskParams, Void, List<Integer>> {
        private FinanceDao financeDao;

        private GetFinanceSumByDateAndTypeAsyncTask(FinanceDao financeDao) {
            this.financeDao = financeDao;
        }

        @Override
        protected List<Integer> doInBackground(FinanceTaskParams... params) {
            return financeDao.getFinanceSumByDateAndType(params[0].start, params[0].end, params[0].type);
        }
    }
}
