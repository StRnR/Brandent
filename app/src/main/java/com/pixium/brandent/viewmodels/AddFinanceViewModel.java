package com.pixium.brandent.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.pixium.brandent.db.entities.Finance;
import com.pixium.brandent.db.repos.FinanceRepository;

public class AddFinanceViewModel extends AndroidViewModel {
    private final FinanceRepository financeRepository;

    public AddFinanceViewModel(@NonNull Application application) {
        super(application);
        financeRepository = new FinanceRepository(application);
    }

    public void insertFinance(Finance finance) {
        financeRepository.insert(finance);
    }
}
