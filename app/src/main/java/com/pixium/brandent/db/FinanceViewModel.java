package com.pixium.brandent.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.pixium.brandent.AppointmentIncomeTaskParams;
import com.pixium.brandent.AppointmentRepository;
import com.pixium.brandent.FinanceRepository;
import com.pixium.brandent.FinanceTaskParams;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FinanceViewModel extends AndroidViewModel {
    private FinanceRepository financeRepository;
    private AppointmentRepository appointmentRepository;


    public FinanceViewModel(@NonNull Application application) {
        super(application);
        financeRepository = new FinanceRepository(application);
        appointmentRepository = new AppointmentRepository(application);
    }


    public List<Integer> getFianceSumByDateAndType(FinanceTaskParams financeTaskParams)
            throws ExecutionException, InterruptedException {
        return financeRepository.getFinanceSumByDateAndType(financeTaskParams);
    }

    public List<Integer> getAppointmentIncomeByDate(AppointmentIncomeTaskParams params)
            throws ExecutionException, InterruptedException {
        return appointmentRepository.getIncomeByDate(params);
    }

}
