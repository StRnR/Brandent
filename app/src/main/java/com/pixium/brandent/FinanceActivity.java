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

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FinanceActivity extends AppCompatActivity {

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
                        startActivity(new Intent(getApplicationContext(), AddAppointmentActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
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