package com.pixium.brandent.db.repos;

import android.app.Application;
import android.os.AsyncTask;

import com.pixium.brandent.ActiveUser;
import com.pixium.brandent.db.AppDatabase;
import com.pixium.brandent.db.daos.FinanceDao;
import com.pixium.brandent.db.entities.Finance;
import com.pixium.brandent.models.FinanceTaskParams;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class FinanceRepository {
    private final FinanceDao financeDao;

    public FinanceRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        financeDao = database.financeDao();
    }

    public void insert(Finance... finances) {
        new InsertFinanceAsyncTask(financeDao).execute(finances);
    }

    public void update(Finance finance) {
        new UpdateFinanceAsyncTask(financeDao).execute(finance);
    }

    public void delete(Finance finance) {
        new DeleteFinanceAsyncTask(financeDao).execute(finance);
    }

    public Finance[] getUnsynced(long lastUpdated) throws ExecutionException, InterruptedException {
        return new GetNotSyncedFinancesAsyncTask(financeDao).execute(lastUpdated)
                .execute(lastUpdated).get();
    }

    public Finance getByUuid(UUID uuid) throws ExecutionException, InterruptedException {
        return new GetFinanceByUuidAsyncTask(financeDao).execute(uuid).get();
    }

    public List<Integer> getFinanceSumByDateAndType(FinanceTaskParams params)
            throws ExecutionException, InterruptedException {
        return new GetFinanceSumByDateAndTypeAsyncTask(financeDao).execute(params).get();
    }


    private static class InsertFinanceAsyncTask extends AsyncTask<Finance, Void, Void> {
        private final FinanceDao financeDao;

        private InsertFinanceAsyncTask(FinanceDao financeDao) {
            this.financeDao = financeDao;
        }

        @Override
        protected Void doInBackground(Finance... finances) {
            for (Finance finance : finances) {
                financeDao.insert(finance);
            }
            return null;
        }
    }

    private static class UpdateFinanceAsyncTask extends AsyncTask<Finance, Void, Void> {
        private final FinanceDao financeDao;

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
        private final FinanceDao financeDao;

        private DeleteFinanceAsyncTask(FinanceDao financeDao) {
            this.financeDao = financeDao;
        }

        @Override
        protected Void doInBackground(Finance... finances) {
            financeDao.delete(finances[0]);
            return null;
        }
    }

    private static class GetFinanceByUuidAsyncTask extends AsyncTask<UUID, Void, Finance> {
        private final FinanceDao financeDao;

        private GetFinanceByUuidAsyncTask(FinanceDao financeDao) {
            this.financeDao = financeDao;
        }

        @Override
        protected Finance doInBackground(UUID... uuids) {
            return financeDao.getByUuid(uuids[0], ActiveUser.getInstance().getId());
        }
    }

    private static class GetNotSyncedFinancesAsyncTask extends AsyncTask<Long, Void, Finance[]> {
        private final FinanceDao financeDao;

        private GetNotSyncedFinancesAsyncTask(FinanceDao financeDao) {
            this.financeDao = financeDao;
        }

        @Override
        protected Finance[] doInBackground(Long... longs) {
            return financeDao.getUnSynced(longs[0], ActiveUser.getInstance().getId());
        }
    }

    private static class GetFinanceSumByDateAndTypeAsyncTask extends AsyncTask<FinanceTaskParams, Void, List<Integer>> {
        private final FinanceDao financeDao;

        private GetFinanceSumByDateAndTypeAsyncTask(FinanceDao financeDao) {
            this.financeDao = financeDao;
        }

        @Override
        protected List<Integer> doInBackground(FinanceTaskParams... params) {
            return financeDao.getFinanceSumByDateAndType(params[0].start, params[0].end
                    , params[0].type, ActiveUser.getInstance().getId());
        }
    }
}
