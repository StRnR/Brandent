package com.pixium.clinitick.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixium.clinitick.R;
import com.pixium.clinitick.UiTools;
import com.pixium.clinitick.adapters.FinanceAdapter;
import com.pixium.clinitick.models.FinanceCardModel;
import com.pixium.clinitick.viewmodels.MonthlyFinanceViewModel;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import saman.zamani.persiandate.PersianDate;

public class MonthlyFinanceActivity extends AppCompatActivity {
    private MonthlyFinanceViewModel monthlyFinanceViewModel;
    public static final String EXTRA_REQUEST =
            "com.pixium.brandent.EXTRA_REQUEST";
    private int extraRequest;
    private String financeType = "ALL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_finance);

        TextView sumTv = findViewById(R.id.tv_sum_monthly_finance);

        Button backBtn = findViewById(R.id.btn_back_monthly_finance);

        monthlyFinanceViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).
                get(MonthlyFinanceViewModel.class);

        Intent intent = getIntent();
        extraRequest = intent.getIntExtra(EXTRA_REQUEST, FinanceActivity.TOTAL_SUM_REQUEST);
        if (extraRequest == FinanceActivity.EXPENSE_REQUEST) {
            financeType = "EXPENSE";
        } else if (extraRequest == FinanceActivity.EXTERNAL_INCOME_REQUEST) {
            financeType = "INCOME";
        }


        // Recyclerview
        RecyclerView recyclerView = findViewById(R.id.rv_monthly_finance);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        FinanceAdapter adapter = new FinanceAdapter();
        recyclerView.setAdapter(adapter);

        // init data
        PersianDate persianDate = new PersianDate();
        persianDate.setShDay(1);
        Calendar startCal = Calendar.getInstance();
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);
        startCal.set(Calendar.YEAR, persianDate.getGrgYear());
        startCal.set(Calendar.MONTH, persianDate.getGrgMonth() - 1);
        startCal.set(Calendar.DAY_OF_MONTH, persianDate.getGrgDay());

        Calendar endCal = (Calendar) startCal.clone();
        persianDate.setShDay(persianDate.getMonthLength());
        endCal.set(Calendar.HOUR_OF_DAY, 23);
        endCal.set(Calendar.MINUTE, 59);
        endCal.set(Calendar.SECOND, 59);
        endCal.set(Calendar.MILLISECOND, 999);
        endCal.set(Calendar.YEAR, persianDate.getGrgYear());
        endCal.set(Calendar.MONTH, persianDate.getGrgMonth() - 1);
        endCal.set(Calendar.DAY_OF_MONTH, persianDate.getGrgDay());
        try {
            List<FinanceCardModel> financeCardModels = monthlyFinanceViewModel.makeCardModels
                    (monthlyFinanceViewModel.getAppointments(startCal.getTimeInMillis()
                            , endCal.getTimeInMillis()), monthlyFinanceViewModel.getFinances
                                    (startCal.getTimeInMillis(), endCal.getTimeInMillis(), financeType)
                            , extraRequest);
            adapter.setFinances(financeCardModels);
            long sum = 0;
            for (FinanceCardModel financeCardModel : financeCardModels) {
                if (financeCardModel.isExpense()) {
                    sum -= financeCardModel.getAmount();
                } else {
                    sum += financeCardModel.getAmount();
                }
            }
            if (sum < 0) {
                sumTv.setTextColor(ContextCompat.getColor(MonthlyFinanceActivity.this
                        , R.color.expenseRed));
                sum *= -1;
            }
            sumTv.setText(UiTools.priceToString(sum, true));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        adapter.setOnItemClickListener(financeCardModel -> {
            if (financeCardModel.getDescription().trim().equals("")) {
                Intent intent1 = new Intent(MonthlyFinanceActivity.this
                        , AddEditFinanceActivity.class);
                intent1.putExtra(AddEditFinanceActivity.EXTRA_FINANCE_ID, financeCardModel.getId());
                startActivity(intent1);
            }
        });

        // Month Spinner
        Spinner monthSpinner = findViewById(R.id.spinner_monthly_finance_month);
        ArrayAdapter<CharSequence> monthSpinnerAdapter = ArrayAdapter.createFromResource(this
                , R.array.month_names, R.layout.spinner_month_name_item);
        monthSpinnerAdapter.setDropDownViewResource(R.layout.month_spinner_dropdown_item);
        monthSpinner.setAdapter(monthSpinnerAdapter);
        monthSpinner.setSelection(PersianDate.today().getShMonth() - 1);
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                persianDate.setShDay(1);
                persianDate.setShMonth(position + 1);
                startCal.set(Calendar.YEAR, persianDate.getGrgYear());
                startCal.set(Calendar.MONTH, persianDate.getGrgMonth() - 1);
                startCal.set(Calendar.DAY_OF_MONTH, persianDate.getGrgDay());

                persianDate.setShDay(persianDate.getMonthLength());
                endCal.set(Calendar.YEAR, persianDate.getGrgYear());
                endCal.set(Calendar.MONTH, persianDate.getGrgMonth() - 1);
                endCal.set(Calendar.DAY_OF_MONTH, persianDate.getGrgDay());

                try {
                    List<FinanceCardModel> financeCardModels = monthlyFinanceViewModel.makeCardModels
                            (monthlyFinanceViewModel.getAppointments(startCal.getTimeInMillis()
                                    , endCal.getTimeInMillis()), monthlyFinanceViewModel.getFinances
                                            (startCal.getTimeInMillis(), endCal.getTimeInMillis(), financeType)
                                    , extraRequest);
                    adapter.setFinances(financeCardModels);
                    long sum = 0;
                    for (FinanceCardModel financeCardModel : financeCardModels) {
                        if (financeCardModel.isExpense()) {
                            sum -= financeCardModel.getAmount();
                        } else {
                            sum += financeCardModel.getAmount();
                        }
                    }
                    if (sum < 0) {
                        sumTv.setTextColor(ContextCompat.getColor(MonthlyFinanceActivity.this
                                , R.color.expenseRed));
                        sum *= -1;
                    }
                    sumTv.setText(UiTools.priceToString(sum, true));
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Year Spinner
        Spinner yearSpinner = findViewById(R.id.spinner_monthly_finance_year);
        ArrayAdapter<CharSequence> yearSpinnerAdapter = ArrayAdapter.createFromResource(this
                , R.array.years, R.layout.spinner_year_item);
        yearSpinnerAdapter.setDropDownViewResource(R.layout.month_spinner_dropdown_item);
        yearSpinner.setAdapter(yearSpinnerAdapter);
        yearSpinner.setSelection(PersianDate.today().getShYear() - 1399);
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                persianDate.setShDay(1);
                persianDate.setShYear(position + 1399);
                startCal.set(Calendar.YEAR, persianDate.getGrgYear());
                startCal.set(Calendar.MONTH, persianDate.getGrgMonth() - 1);
                startCal.set(Calendar.DAY_OF_MONTH, persianDate.getGrgDay());

                persianDate.setShDay(persianDate.getMonthLength());
                endCal.set(Calendar.YEAR, persianDate.getGrgYear());
                endCal.set(Calendar.MONTH, persianDate.getGrgMonth() - 1);
                endCal.set(Calendar.DAY_OF_MONTH, persianDate.getGrgDay());

                try {
                    List<FinanceCardModel> financeCardModels = monthlyFinanceViewModel.makeCardModels
                            (monthlyFinanceViewModel.getAppointments(startCal.getTimeInMillis()
                                    , endCal.getTimeInMillis()), monthlyFinanceViewModel.getFinances
                                            (startCal.getTimeInMillis(), endCal.getTimeInMillis(), financeType)
                                    , extraRequest);
                    adapter.setFinances(financeCardModels);
                    long sum = 0;
                    for (FinanceCardModel financeCardModel : financeCardModels) {
                        if (financeCardModel.isExpense()) {
                            sum -= financeCardModel.getAmount();
                        } else {
                            sum += financeCardModel.getAmount();
                        }
                    }
                    if (sum < 0) {
                        sumTv.setTextColor(ContextCompat.getColor(MonthlyFinanceActivity.this
                                , R.color.expenseRed));
                        sum *= -1;
                    }
                    sumTv.setText(UiTools.priceToString(sum, true));
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        backBtn.setOnClickListener(v -> onBackPressed());
    }
}