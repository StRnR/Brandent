package com.pixium.clinitick.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.pixium.clinitick.db.entities.Clinic;
import com.pixium.clinitick.db.entities.Task;
import com.pixium.clinitick.db.repos.ClinicRepository;
import com.pixium.clinitick.db.repos.TaskRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddEditTaskViewModel extends AndroidViewModel {
    private final TaskRepository taskRepository;
    private final ClinicRepository clinicRepository;

    public AddEditTaskViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new TaskRepository(application);
        clinicRepository = new ClinicRepository(application);
    }

    public void insertTask(Task task) {
        taskRepository.insert(task);
    }

    public void updateTask(Task task) {
        taskRepository.update(task);
    }

    public Task getTaskById(int id) throws ExecutionException, InterruptedException {
        return taskRepository.getById(id);
    }

    public String[] getAllClinicsTitles() throws ExecutionException, InterruptedException {
        List<Clinic> clinics = clinicRepository.getAllClinics();
        String[] titles = new String[clinics.size()];
        for (int i = 0; i < clinics.size(); i++) {
            titles[i] = clinics.get(i).getTitle();
        }
        return titles;
    }

    public List<Clinic> getClinicByTitle(String string) throws ExecutionException
            , InterruptedException {
        return clinicRepository.getClinicBytTitle(string);
    }

    public Clinic getClinicById(int id) throws ExecutionException, InterruptedException {
        return clinicRepository.getById(id);
    }
}
