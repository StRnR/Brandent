package com.pixium.brandent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pixium.brandent.db.FinanceViewModel;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import saman.zamani.persiandate.PersianDate;

public class FinanceActivity extends AppCompatActivity {
    private FinanceViewModel financeViewModel;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        Button addBtn = findViewById(R.id.btn_add_finance);
        Button hideBtn = findViewById(R.id.btn_hide_finance);

        TextView sumTv = findViewById(R.id.tv_month_sum_finance);
        TextView visitsSumTv = findViewById(R.id.tv_visits_sum_finance);
        TextView manualIncomeTv = findViewById(R.id.tv_manual_income_finance);
        TextView manualExpenseTv = findViewById(R.id.tv_manual_expense_finance);

        financeViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).
                get(FinanceViewModel.class);

        // Set Selected NavBar item
        bottomNavigationView.setSelectedItemId(R.id.finance_page);

        // NavBar ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_page:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.finance_page:
                        return true;
                    case R.id.add_appointment_page:
                        startActivity(new Intent(getApplicationContext(), AddPatientActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.tasks_page:
                        startActivity(new Intent(getApplicationContext(), TasksActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile_page:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });


        // Filling Finance Data
        PersianDate pDate = new PersianDate();
        pDate.setShDay(1);

        Calendar startCal = Calendar.getInstance();
        startCal.set(Calendar.YEAR, pDate.getGrgYear());
        startCal.set(Calendar.MONTH, pDate.getGrgMonth() - 1);
        startCal.set(Calendar.DAY_OF_MONTH, pDate.getGrgDay());
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);

        pDate.setShDay(pDate.getMonthDays());
        Calendar endCal = Calendar.getInstance();
        endCal.set(Calendar.YEAR, pDate.getGrgYear());
        endCal.set(Calendar.MONTH, pDate.getGrgMonth() - 1);
        endCal.set(Calendar.DAY_OF_MONTH, pDate.getGrgDay());
        endCal.set(Calendar.HOUR_OF_DAY, 0);
        endCal.set(Calendar.MINUTE, 0);
        endCal.set(Calendar.SECOND, 0);
        endCal.set(Calendar.MILLISECOND, 0);

        // Visits Sum
        AppointmentIncomeTaskParams appointmentParams = new AppointmentIncomeTaskParams(
                startCal.getTimeInMillis(), endCal.getTimeInMillis(), "DONE");
        List<Integer> appointmentIncomes;
        int visitSum = 0;
        try {
            appointmentIncomes = financeViewModel.getAppointmentIncomeByDate(appointmentParams);
            for (Integer amount : appointmentIncomes) {
                visitSum += amount;
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        String visitsStr = visitSum + " " + visitsSumTv.getText().toString();
        visitsSumTv.setText(visitsStr);

        // External Income Sum
        FinanceTaskParams incomeParams = new FinanceTaskParams(startCal.getTimeInMillis()
                , endCal.getTimeInMillis(), "INCOME");
        List<Integer> externalIncomes;
        int externalIncomeSum = 0;
        try {
            externalIncomes = financeViewModel.getFianceSumByDateAndType(incomeParams);
            for (Integer amount : externalIncomes) {
                externalIncomeSum += amount;
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        String externalIncomeStr = externalIncomeSum + " " + manualIncomeTv.getText().toString();
        manualIncomeTv.setText(externalIncomeStr);

        // External Expense Sum
        FinanceTaskParams expenseParams = new FinanceTaskParams(startCal.getTimeInMillis()
                , endCal.getTimeInMillis(), "EXPENSE");
        List<Integer> externalExpenses;
        int externalExpenseSum = 0;
        try {
            externalExpenses = financeViewModel.getFianceSumByDateAndType(expenseParams);
            for (Integer amount : externalExpenses) {
                externalExpenseSum += amount;
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        String externalExpenseStr = externalExpenseSum + " " + manualExpenseTv.getText().toString();
        manualExpenseTv.setText(externalExpenseStr);

        // Total Sum
        String totalSumStr = (visitSum + externalIncomeSum - externalExpenseSum) + " "
                + sumTv.getText().toString();
        sumTv.setText(totalSumStr);


        addBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), AddFinanceActivity.class)));

        hideBtn.setOnClickListener(v -> {
            if (sumTv.getTransformationMethod() != null) {
                sumTv.setTransformationMethod(null);
                visitsSumTv.setTransformationMethod(null);
                manualIncomeTv.setTransformationMethod(null);
                manualExpenseTv.setTransformationMethod(null);
                hideBtn.setBackground(getDrawable(R.drawable.ic_hide));
            } else {
                sumTv.setTransformationMethod(new PasswordTransformationMethod());
                visitsSumTv.setTransformationMethod(new PasswordTransformationMethod());
                manualIncomeTv.setTransformationMethod(new PasswordTransformationMethod());
                manualExpenseTv.setTransformationMethod(new PasswordTransformationMethod());
                hideBtn.setBackground(getDrawable(R.drawable.ic_hide_orange));
            }
        });
    }

    @Override
    protected void onResume() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        // Set Selected NavBar item
        bottomNavigationView.setSelectedItemId(R.id.finance_page);
        super.onResume();
    }
}