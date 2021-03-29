package com.pixium.clinitick.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.pixium.clinitick.db.entities.Finance;
import com.pixium.clinitick.db.repos.FinanceRepository;

import java.util.concurrent.ExecutionException;

public class AddFinanceViewModel extends AndroidViewModel {
    private final FinanceRepository financeRepository;

    public AddFinanceViewModel(@NonNull Application application) {
        super(application);
        financeRepository = new FinanceRepository(application);
    }

    public void insertFinance(Finance finance) {
        financeRepository.insert(finance);
    }

    public void updateFinance(Finance finance) {
        financeRepository.update(finance);
    }

    public Finance getFinanceById(int id) throws ExecutionException, InterruptedException {
        return financeRepository.getById(id);
    }
}