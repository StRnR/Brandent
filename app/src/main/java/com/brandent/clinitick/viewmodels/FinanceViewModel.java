package com.brandent.clinitick.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.brandent.clinitick.db.repos.AppointmentRepository;
import com.brandent.clinitick.db.repos.FinanceRepository;
import com.brandent.clinitick.models.AppointmentIncomeTaskParams;
import com.brandent.clinitick.models.FinanceTaskParams;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FinanceViewModel extends AndroidViewModel {
    private final FinanceRepository financeRepository;
    private final AppointmentRepository appointmentRepository;


    public FinanceViewModel(@NonNull Application application) {
        super(application);
        financeRepository = new FinanceRepository(application);
        appointmentRepository = new AppointmentRepository(application);
    }


    public List<Long> getFianceSumByDateAndType(FinanceTaskParams financeTaskParams)
            throws ExecutionException, InterruptedException {
        return financeRepository.getFinanceSumByDateAndType(financeTaskParams);
    }

    public List<Integer> getAppointmentIncomeByDate(AppointmentIncomeTaskParams params)
            throws ExecutionException, InterruptedException {
        return appointmentRepository.getIncomeByDate(params);
    }

}
