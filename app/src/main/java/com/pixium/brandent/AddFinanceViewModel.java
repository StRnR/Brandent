package com.pixium.brandent;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.pixium.brandent.db.Finance;

public class AddFinanceViewModel extends AndroidViewModel {
    private FinanceRepository financeRepository;

    public AddFinanceViewModel(@NonNull Application application) {
        super(application);
        financeRepository = new FinanceRepository(application);
    }

    public void insertFinance(Finance finance) {
        financeRepository.insert(finance);
    }
}
