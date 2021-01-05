package com.pixium.brandent;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.pixium.brandent.db.Finance;

import java.util.Calendar;
import java.util.GregorianCalendar;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import saman.zamani.persiandate.PersianDate;

public class AddFinanceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private AddFinanceViewModel addFinanceViewModel;
    String financeType;
    private Calendar visitCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_finance);

        EditText titleEt = findViewById(R.id.et_title_finance_add);
        EditText amountEt = findViewById(R.id.et_amount_finance_add);

        Button backBtn = findViewById(R.id.btn_back_finance_add);
        Button dateBtn = findViewById(R.id.btn_date_finance_add);
        Button submitBtn = findViewById(R.id.btn_submit_finance_add);

        addFinanceViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).
                get(AddFinanceViewModel.class);

        Spinner financeTypeSpinner = findViewById(R.id.spinner_type_finance_add);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this
                , R.array.finance_type, R.layout.spinner_finance_type_item);
        adapter.setDropDownViewResource(R.layout.appointment_add_spinner_dropdown_item);
        financeTypeSpinner.setAdapter(adapter);
        financeTypeSpinner.setOnItemSelectedListener(this);


        // Date & Time Picker
        PersianDate pDate = new PersianDate();
        PersianCalendar initDate = new PersianCalendar();
        initDate.setPersianDate(pDate.getShYear(), pDate.getShMonth(), pDate.getShDay());

        Typeface typeface = Typeface.DEFAULT;

        dateBtn.setOnClickListener(v -> {

            PersianDatePickerDialog picker = new PersianDatePickerDialog(this);
            picker.setPositiveButtonString("باشه");
            picker.setNegativeButton("بیخیال");
            picker.setTodayButton("امروز");
            picker.setTodayButtonVisible(true);
            picker.setMinYear(1300);
            picker.setMaxYear(PersianDatePickerDialog.THIS_YEAR);
            picker.setInitDate(initDate);
            picker.setActionTextColor(getResources().getColor(R.color.orange));
            picker.setTypeFace(typeface);
            picker.setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR);
            picker.setShowInBottomSheet(true);
            picker.setListener(new Listener() {
                @Override
                public void onDateSelected(PersianCalendar persianCalendar) {
                    String dateStr = persianCalendar.getPersianLongDate();
                    dateBtn.setText(dateStr);
                    visitCalendar = GregorianCalendar.getInstance();
                    visitCalendar.setTimeInMillis(persianCalendar.getTimeInMillis());
                    visitCalendar.set(Calendar.HOUR_OF_DAY, 0);
                    visitCalendar.set(Calendar.MINUTE, 0);
                    visitCalendar.set(Calendar.SECOND, 0);
                    visitCalendar.set(Calendar.MILLISECOND, 0);
                }

                @Override
                public void onDismissed() {
                }
            });

            picker.show();
        });

        submitBtn.setOnClickListener(v -> {
            if (titleEt.getText().toString().equals("") || amountEt.getText().toString().equals("")) {
                Toast.makeText(this, "Please fill the title and/or the amount field"
                        , Toast.LENGTH_SHORT).show();
            } else if (financeType.equals("")) {
                Toast.makeText(this, "Please select either expense or income"
                        , Toast.LENGTH_SHORT).show();
            } else {
                Finance finance = new Finance(null, null
                        , visitCalendar.getTimeInMillis()
                        , Integer.parseInt(amountEt.getText().toString())
                        , titleEt.getText().toString()
                        , financeType);
                addFinanceViewModel.insertFinance(finance);
                startActivity(new Intent(this, FinanceActivity.class));
            }
        });


        backBtn.setOnClickListener(v -> onBackPressed());

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 1) {
            financeType = "EXPENSE";
        } else if (position == 2) {
            financeType = "INCOME";
        } else {
            financeType = "";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}