package com.pixium.clinitick.db.repos;

import android.app.Application;
import android.os.AsyncTask;

import com.pixium.clinitick.ActiveUser;
import com.pixium.clinitick.db.AppDatabase;
import com.pixium.clinitick.db.daos.TaskDao;
import com.pixium.clinitick.db.entities.Task;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class TaskRepository {
    private final TaskDao taskDao;

    public TaskRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        taskDao = appDatabase.taskDao();
    }

    public void insert(Task... tasks) {
        new InsertTaskAsyncTask(taskDao).execute(tasks);
    }

    public void update(Task task) {
        new UpdateTaskAsyncTask(taskDao).execute(task);
    }

    public void delete(Task task) {
        new DeleteTaskAsyncTask(taskDao).execute(task);
    }

    public Task[] getUnsynced(long lastUpdated) throws ExecutionException, InterruptedException {
        return new GetNotSyncedTasksAsyncTask(taskDao).execute(lastUpdated).get();
    }

    public List<Task> getByDate(Long start, Long end)
            throws ExecutionException, InterruptedException {
        return new GetTasksByDateAsyncTask(taskDao).execute(start, end).get();
    }

    public Task getById(int id) throws ExecutionException, InterruptedException {
        return new GetTaskByIdAsyncTask(taskDao).execute(id).get();
    }

    public Task getByUuid(UUID uuid) throws ExecutionException, InterruptedException {
        return new GetTaskByUuidAsyncTask(taskDao).execute(uuid).get();
    }

    private static class GetTaskByIdAsyncTask extends AsyncTask<Integer, Void, Task> {
        private final TaskDao taskDao;

        private GetTaskByIdAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Task doInBackground(Integer... integers) {
            return taskDao.getById(integers[0], ActiveUser.getInstance().getId());
        }
    }

    private static class GetTaskByUuidAsyncTask extends AsyncTask<UUID, Void, Task> {
        private final TaskDao taskDao;

        private GetTaskByUuidAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Task doInBackground(UUID... uuids) {
            return taskDao.getByUuid(uuids[0], ActiveUser.getInstance().getId());
        }
    }

    private static class GetNotSyncedTasksAsyncTask extends AsyncTask<Long, Void, Task[]> {
        private final TaskDao taskDao;

        private GetNotSyncedTasksAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Task[] doInBackground(Long... longs) {
            return taskDao.getUnSynced(longs[0], ActiveUser.getInstance().getId());
        }
    }

    private static class GetTasksByDateAsyncTask
            extends AsyncTask<Long, Void, List<Task>> {
        private final TaskDao taskDao;

        private GetTasksByDateAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected List<Task> doInBackground(Long... longs) {
            return taskDao.getByDate(longs[0], longs[1], ActiveUser.getInstance().getId());
        }
    }

    private static class InsertTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private final TaskDao taskDao;

        private InsertTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            for (Task task : tasks) {
                taskDao.insert(task);
            }
            return null;
        }
    }

    private static class UpdateTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private final TaskDao taskDao;

        private UpdateTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.update(tasks[0]);
            return null;
        }
    }

    private static class DeleteTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private final TaskDao taskDao;

        private DeleteTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.delete(tasks[0]);
            return null;
        }
    }
}
