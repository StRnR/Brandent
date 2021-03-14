package com.pixium.brandent.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.pixium.brandent.DateTools;
import com.pixium.brandent.db.entities.Appointment;
import com.pixium.brandent.db.entities.Finance;
import com.pixium.brandent.db.entities.Patient;
import com.pixium.brandent.db.repos.AppointmentRepository;
import com.pixium.brandent.db.repos.FinanceRepository;
import com.pixium.brandent.db.repos.PatientRepository;
import com.pixium.brandent.models.FinanceCardModel;
import com.pixium.brandent.views.FinanceActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MonthlyFinanceViewModel extends AndroidViewModel {
    private AppointmentRepository appointmentRepository;
    private FinanceRepository financeRepository;
    private PatientRepository patientRepository;

    public MonthlyFinanceViewModel(@NonNull Application application) {
        super(application);
        appointmentRepository = new AppointmentRepository(application);
        financeRepository = new FinanceRepository(application);
        patientRepository = new PatientRepository(application);
    }

    public List<Appointment> getAppointments(long start, long end) throws ExecutionException
            , InterruptedException {
        List<Appointment> appointments = appointmentRepository.getByDate(start, end);
        List<Appointment> res = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getState().equals("done")) {
                res.add(appointment);
            }
        }
        return res;
    }

    public List<Finance> getFinances(long start, long end, String type) throws ExecutionException
            , InterruptedException {
        List<Finance> finances = financeRepository.getByDate(start, end);
        if (type.equals("ALL")) {
            return finances;
        }
        List<Finance> res = new ArrayList<>();
        for (Finance finance : finances) {
            if (finance.getType().equals(type)) {
                res.add(finance);
            }
        }
        return res;
    }

    public Patient getPatientById(int id) throws ExecutionException, InterruptedException {
        return patientRepository.getById(id);
    }

    public List<FinanceCardModel> makeCardModels(List<Appointment> appointments
            , List<Finance> finances, int scenario)
            throws ExecutionException, InterruptedException {
        List<FinanceCardModel> res = new ArrayList<>();
        int size = appointments.size() + finances.size();
        int appointmentsIndex = 0;
        int financesIndex = 0;
        boolean appointmentsDone = false;
        boolean financesDone = false;

        if (scenario == FinanceActivity.APPOINTMENTS_REQUEST) {
            financesDone = true;
            size = appointments.size();
        } else if (scenario == FinanceActivity.EXTERNAL_INCOME_REQUEST ||
                scenario == FinanceActivity.EXPENSE_REQUEST) {
            appointmentsDone = true;
            size = finances.size();
        }
        for (int i = 0; i < size; i++) {
            if (appointmentsIndex >= appointments.size()) {
                appointmentsDone = true;
            }
            if (financesIndex >= finances.size()) {
                financesDone = true;
            }
            if (!financesDone && !appointmentsDone) {
                if (appointments.get(appointmentsIndex).getVisitTime() <= finances.get(financesIndex).getDate()) {
                    Appointment appointment = appointments.get(appointmentsIndex);
                    FinanceCardModel financeCardModel =
                            new FinanceCardModel(getPatientById(appointment.getPatientForId()).getName()
                                    , appointment.getTitle(), appointment.getPrice()
                                    , DateTools.timestampToSimpleJalali(appointment.getVisitTime())
                                    , false);
                    res.add(financeCardModel);
                    appointmentsIndex++;
                } else {
                    Finance finance = finances.get(financesIndex);
                    FinanceCardModel financeCardModel = new FinanceCardModel(finance.getTitle()
                            , "", finance.getPrice()
                            , DateTools.timestampToSimpleJalali(finance.getDate())
                            , finance.getType().equals("EXPENSE"));
                    res.add(financeCardModel);
                    financesIndex++;
                }
            } else if (financesDone) {
                Appointment appointment = appointments.get(appointmentsIndex);
                FinanceCardModel financeCardModel =
                        new FinanceCardModel(getPatientById(appointment.getPatientForId()).getName()
                                , appointment.getTitle(), appointment.getPrice()
                                , DateTools.timestampToSimpleJalali(appointment.getVisitTime())
                                , false);
                res.add(financeCardModel);
                appointmentsIndex++;
            } else {
                Finance finance = finances.get(financesIndex);
                FinanceCardModel financeCardModel = new FinanceCardModel(finance.getTitle()
                        , "", finance.getPrice()
                        , DateTools.timestampToSimpleJalali(finance.getDate())
                        , finance.getType().equals("EXPENSE"));
                res.add(financeCardModel);
                financesIndex++;
            }
        }
        return res;
    }
}
